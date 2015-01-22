package icecaptools.compiler.aot;

import icecaptools.BNode;
import icecaptools.GotoBNode;
import icecaptools.IcecapIterator;
import icecaptools.MethodEntryPoints;
import icecaptools.ReturnBNode;
import icecaptools.SwitchBNode;
import icecaptools.compiler.NoDuplicatesMemorySegment;
import icecaptools.conversion.TargetAddressMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.bcel.classfile.Method;

public abstract class FlowBasedCompiler extends AOTCompiler {

    @Override
    protected boolean mayThrowExceptions(String className, String targetMethodName, String targetMethodSignature) {
        MethodEntryPoints entryPoints = toolBox.getDependencyExtent().getMethod(className, targetMethodName, targetMethodSignature);
        if (entryPoints != null) {
            return entryPoints.getMayThrowException();
        } else {
            return true;
        }
    }

    public FlowBasedCompiler(Method javaMethod, byte[] methodCode, String uniqueMethodId, int methodNumber, NoDuplicatesMemorySegment requiredIncludes, AOTToolBox toolBox) {
        super(javaMethod, methodCode, uniqueMethodId, methodNumber, requiredIncludes, toolBox);
    }

    private static class TranslationWorkItem {
        public BNode bnode;
    }

    private LinkedList<TranslationWorkItem> workItems;

    @Override
    protected void handleBranch(StackManager sm, byte opcode, int src, int dst, boolean doit) throws Exception {
        BNode decendant = toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(), javaMethod.getName(), javaMethod.getSignature(), dst);

        if (decendant.getStackManager() == null) {
            TranslationWorkItem targetItem = new TranslationWorkItem();
            targetItem.bnode = decendant;
            decendant.setStackManager(sm.copy());
            workItems.addLast(targetItem);
        } else {
            StackManager targetStack = decendant.getStackManager();

            if (!targetStack.equals(sm)) {
                StackManager temp = sm.copy();
                temp.merge(targetStack);
            }
        }
    }

    @Override
    public void compileByteCode(StackManager sm, LabelsManager labelsManager, TargetAddressMap tMap, StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc, boolean compileOne, MethodEntryPoints entryPoints) throws Exception {
        workItems = new LinkedList<TranslationWorkItem>();

        IcecapIterator<BNode> handlers = entryPoints.getHandlerEntryPoints();

        while (handlers.hasNext()) {
            TranslationWorkItem handlerWorkItem = new TranslationWorkItem();
            BNode nextHandler = handlers.next();
            handlerWorkItem.bnode = nextHandler;
            nextHandler.setStackManager(sm.copy());
            workItems.addLast(handlerWorkItem);
        }

        BNode bnode = entryPoints.getMainEntryPoint();

        TranslationWorkItem initialWorkItem = new TranslationWorkItem();

        initialWorkItem.bnode = bnode;
        bnode.setStackManager(sm.copy());
        workItems.addLast(initialWorkItem);

        while (!(workItems.size() == 0)) {
            TranslationWorkItem next = workItems.removeFirst();
            if (next.bnode.getTranslation() == null) {
                handleWorkItem(next, labelsManager, tMap, localVariables, entryPoints);
            }
        }

        ArrayList<BNode> bnodes = entryPoints.getBNodes();

        Iterator<BNode> bnodeIterator = bnodes.iterator();
        while (bnodeIterator.hasNext()) {
            BNode next = bnodeIterator.next();
            StringBuffer nextTranslation = next.getTranslation();
            output.append(nextTranslation);
        }
    }

    private void handleWorkItem(TranslationWorkItem current, LabelsManager labelsManager, TargetAddressMap tMap, NoDuplicatesMemorySegment localVariables, MethodEntryPoints entryPoints) throws Exception {
        int address = current.bnode.getAddress();
        int codeLength = current.bnode.getRawBytes().length;
        byte[] code = new byte[codeLength];
        System.arraycopy(this.currentMethodCode, address, code, 0, codeLength);

        StringBuffer translation = new StringBuffer();

        current.bnode.getStackManager().setOutput(translation);

        StackManager exitStack = current.bnode.getStackManager().copy();
        
        super.compileByteCode(exitStack, labelsManager, tMap, translation, localVariables, address, true, entryPoints);

        current.bnode.setTranslation(translation);

        if ((current.bnode instanceof ReturnBNode) || (current.bnode instanceof GotoBNode) || (current.bnode instanceof SwitchBNode)) {
            return;
        }

        address = address + codeLength;

        BNode decendant = toolBox.getDependencyExtent().getBNodeFromMethod(toolBox.getCurrentClassName(), javaMethod.getName(), javaMethod.getSignature(), address);

        if (decendant.getStackManager() == null) {
            TranslationWorkItem childItem = new TranslationWorkItem();
            childItem.bnode = decendant;
            decendant.setStackManager(exitStack);
            workItems.addLast(childItem);
        } else {
            StackManager targetStack = decendant.getStackManager();

            if (!targetStack.equals(exitStack)) {
                StackManager temp = exitStack.copy();
                temp.merge(targetStack);
            }
        }
    }

    @Override
    protected void handleLabel(StackManager sm) {
    }
}
