package icecaptools.debugging;

import java.util.ArrayList;

import icecaptools.BNode;
import icecaptools.ClassfileUtils;
import icecaptools.CompilationSequence;
import icecaptools.ConverterJob;
import icecaptools.MethodAndClass;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.ByteCodePatcher;
import icecaptools.debugging.variables.HVMVariableFactory;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

public class HVMStackFrame extends HVMDebugElement implements IStackFrame {

    private int methodNumber;
    private int pc;
    private HVMThread thread;
    private int originalAddress;
    private IVariable[] variables;
    private MethodAndClass methodAndClass;

    public HVMStackFrame(HVMThread thread, int methodNumber, int pc) throws DebugException {
        super(thread.getDebugTarget());
        this.methodNumber = methodNumber;
        this.pc = pc;
        this.thread = thread;

        setupVariables();
    }

    private void setupVariables() throws DebugException {
        CompilationSequence compilationSequence = ConverterJob.mostRecentSequence;
        if (compilationSequence != null) {
            ByteCodePatcher patcher = compilationSequence.getPatcher();
            MethodOrFieldDesc method = patcher.getMethodDescriptor(methodNumber);

            if (method != null) {
                MethodEntryPoints ep = compilationSequence.getDependencyExtent().getMethod(method.getClassName(), method.getName(), method.getSignature());
                BNode bnode = ep.getBNodeFromHVMAddress(pc);
                this.originalAddress = bnode.getOriginalAddress();

                try {
                    methodAndClass = ClassfileUtils.findMethod(method.getClassName(), method.getName(), method.getSignature());
                    LocalVariableTable lvTable = methodAndClass.getMethod().getLocalVariableTable();
                    LocalVariable[] table = lvTable.getLocalVariableTable();
                    ArrayList<IVariable> variables = new ArrayList<IVariable>();
                    for (int i = 0; i < table.length; i++) {
                        LocalVariable lv = table[i];
                        if ((lv.getStartPC() <= originalAddress) && (originalAddress < lv.getStartPC() + lv.getLength())) {
                            variables.add(HVMVariableFactory.getHVMVariable(getDebugTarget(), lv, methodAndClass.getMethod()));
                        }
                    }
                    this.variables = new IVariable[variables.size()];
                    for (int i = 0; i < variables.size(); i++) {
                        this.variables[i] = variables.get(i);
                    }
                    return;
                } catch (Exception e) {
                    variables = new IVariable[0];
                    throw new DebugException(new IStatus() {

                        private static final String message = "Could not get method information";

                        @Override
                        public IStatus[] getChildren() {
                            return null;
                        }

                        @Override
                        public int getCode() {
                            return IStatus.ERROR;
                        }

                        @Override
                        public Throwable getException() {
                            return new Exception(message);
                        }

                        @Override
                        public String getMessage() {
                            return message;
                        }

                        @Override
                        public String getPlugin() {
                            return DebugPlugin.getUniqueIdentifier();
                        }

                        @Override
                        public int getSeverity() {
                            return IStatus.ERROR;
                        }

                        @Override
                        public boolean isMultiStatus() {
                            return false;
                        }

                        @Override
                        public boolean isOK() {
                            return false;
                        }

                        @Override
                        public boolean matches(int severityMask) {
                            return false;
                        };
                    });
                }
            }
        }
        variables = new IVariable[0];
    }

    @Override
    public boolean canStepInto() {
        return getThread().canStepInto();
    }

    @Override
    public boolean canStepOver() {
        return getThread().canStepOver();
    }

    @Override
    public boolean canStepReturn() {
        return getThread().canStepReturn();
    }

    @Override
    public boolean isStepping() {
        return getThread().isStepping();
    }

    @Override
    public void stepInto() throws DebugException {
        getThread().stepInto();
    }

    @Override
    public void stepOver() throws DebugException {
        getThread().stepOver();
    }

    @Override
    public void stepReturn() throws DebugException {
        getThread().stepReturn();
    }

    @Override
    public boolean canResume() {
        return getThread().canResume();
    }

    @Override
    public boolean canSuspend() {
        return getThread().canSuspend();
    }

    @Override
    public boolean isSuspended() {
        return getThread().isSuspended();
    }

    @Override
    public void resume() throws DebugException {
        getThread().resume();
    }

    @Override
    public void suspend() throws DebugException {
        getThread().suspend();
    }

    @Override
    public boolean canTerminate() {
        return getThread().isTerminated();
    }

    @Override
    public boolean isTerminated() {
        return getThread().isTerminated();
    }

    @Override
    public void terminate() throws DebugException {
        getThread().terminate();
    }

    @Override
    public IThread getThread() {
        return thread;
    }

    @Override
    public IVariable[] getVariables() throws DebugException {
        return this.variables;
    }

    @Override
    public boolean hasVariables() throws DebugException {
        return this.variables.length > 0;
    }

    @Override
    public int getLineNumber() throws DebugException {
        if (methodAndClass != null) {
            int lineNumber = ClassfileUtils.getLineNumber(methodAndClass.getMethod(), originalAddress);
            return lineNumber;
        }
        return 0;
    }

    @Override
    public int getCharStart() throws DebugException {
        return -1;
    }

    @Override
    public int getCharEnd() throws DebugException {
        return -1;
    }

    @Override
    public String getName() throws DebugException {
        CompilationSequence compilationSequence = ConverterJob.mostRecentSequence;
        if (compilationSequence != null) {
            StringBuffer buffer = new StringBuffer();
            ByteCodePatcher patcher = compilationSequence.getPatcher();
            MethodOrFieldDesc desc = patcher.getMethodDescriptor(methodNumber);
            buffer.append(desc.getClassName());
            buffer.append(".");
            buffer.append(desc.getName());
            buffer.append("(");
            buffer.append(desc.getSignature());
            buffer.append(") pc: ");
            buffer.append(this.pc);
            return buffer.toString();
        } else {
            return "unknown";
        }
    }

    @Override
    public IRegisterGroup[] getRegisterGroups() throws DebugException {
        return new IRegisterGroup[0];
    }

    @Override
    public boolean hasRegisterGroups() throws DebugException {
        return false;
    }

    public int getMethodNumber() {
        return this.methodNumber;
    }

    public void clear() {
        // TODO Auto-generated method stub
        
    }
}
