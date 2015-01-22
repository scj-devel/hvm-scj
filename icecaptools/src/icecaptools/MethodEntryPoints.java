package icecaptools;

import icecaptools.RawByteCodes.RawBytecode;
import icecaptools.compiler.Compiler;
import icecaptools.compiler.aot.AOTCompiler;
import icecaptools.compiler.aot.Size;
import icecaptools.conversion.TargetAddressMap;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MethodEntryPoints {

    private ArrayList<BNode> bnodes;
    private Method method;
    private boolean mayThrowException;
    private TargetAddressMap tmap;
    private JavaClass clazz;
    private boolean callWithArgs;
    private boolean neverCallWithArgs;
    
    public MethodEntryPoints(ArrayList<BNode> bnodes, Method method, TargetAddressMap tmap, JavaClass clazz) {
        this.bnodes = bnodes;
        this.method = method;
        this.clazz = clazz;
        this.tmap = tmap;
        mayThrowException = false;
        callWithArgs = false;
        neverCallWithArgs = false;
    }

    public Method getMethod() {
        return method;
    }

    public JavaClass getClazz() {
        return clazz;
    }

    public BNode getMainEntryPoint() {
        return bnodes.get(0);
    }

    public IcecapIterator<BNode> getHandlerEntryPoints() {

        return new HandlerIterator();
    }

    private class HandlerIterator implements IcecapIterator<BNode> {
        private CodeException[] execptionTable;
        int top;

        public HandlerIterator() {
            execptionTable = method.getCode().getExceptionTable();
            top = 0;
        }

        @Override
        public boolean hasNext() {
            return (execptionTable != null) && (top < execptionTable.length);
        }

        @Override
        public BNode next() {
            CodeException ce = execptionTable[top++];
            int handlerPC = ce.getHandlerPC();
            Iterator<BNode> nodeIterator = bnodes.iterator();
            while (nodeIterator.hasNext()) {
                BNode nextBNode = nodeIterator.next();
                if (nextBNode.getAddress() == handlerPC) {
                    return nextBNode;
                }
            }
            return null;
        }
    }

    public ArrayList<BNode> getBNodes() {
        return this.bnodes;
    }

    public void setMayThrowException() {
        mayThrowException = true;
    }

    public boolean getMayThrowException() {
        return mayThrowException;
    }

    public TargetAddressMap getJumpTargets() {
        return this.tmap;
    }

    public BNode getBNodeFromHVMAddress(int pc) {
        for (BNode bNode : bnodes) {
            if (bNode.getAddress() == pc) {
                return bNode;
            }
        }
        return null;
    }

    public BNode getBNodeFromOriginalAddress(int address) {
        for (BNode bNode : bnodes) {
            if (bNode.getOriginalAddress() == address) {
                return bNode;
            }
        }
        return null;
    }

    public boolean isEqualTo(String targetClassName, String targetMethodName, String targetMethodSignature) {
        if (clazz.getClassName().equals(targetClassName)) {
            if (method.getName().equals(targetMethodName)) {
                if (method.getSignature().equals(targetMethodSignature)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setCallWithArgs(boolean b) {
        this.callWithArgs = b;
    }

    public boolean canCallWithArgs() {
        if (neverCallWithArgs)
        {
            return false;
        }
        return this.callWithArgs;
    }

    public boolean useCombinedReturnType() throws Exception {
        if (canCallWithArgs()) {
            if ((Compiler.getNumReturnValues(method) & 0x3) > 0) {
                switch (AOTCompiler.getReturntypeSize(method)) {
                case Size.BYTE:
                case Size.SHORT:
                    return true;
                }
            }
        }
        return false;
    }

    public int getReturnTypeSize() throws Exception {
        if (useCombinedReturnType()) {
            return AOTCompiler.getReturntypeSize(method) + 1;
        } else {
            return Size.SHORT;
        }
    }

    public void neverCallWithArgs() {
        this.neverCallWithArgs  = true;
        
    }

    public RawBytecode getRawByteCode(int address) {
        BNode bnode = getBNodeFromOriginalAddress(address);
        return bnode.getRawByteCode();
    }
}
