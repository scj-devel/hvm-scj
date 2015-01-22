package icecaptools.compiler.aot;

import icecaptools.BNode;
import icecaptools.ClassfileUtils;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodOrFieldDesc;
import icecaptools.RawByteCodes;
import icecaptools.compiler.Compiler;
import icecaptools.compiler.NoDuplicatesMemorySegment;

import java.util.Stack;

import org.apache.bcel.classfile.Method;

public abstract class InvokeSpecialEmitter {

    private StackManager sm;
    private StringBuffer output;
    private NoDuplicatesMemorySegment localVariables;
    private int pc;
    private LabelsManager labelsManager;
    private BNode bnode;
    private byte[] currentMethodCode;
    private NoDuplicatesMemorySegment requiredIncludes;
    private MethodOrFieldDesc methodDesc;
    private AOTToolBox toolBox;
    private MethodEntryPoints entrypoints;

    InvokeSpecialEmitter(StackManager sm, StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc, LabelsManager labelsManager, BNode bnode, byte[] currentMethodCode, NoDuplicatesMemorySegment requiredIncludes, MethodOrFieldDesc methodDesc, AOTToolBox toolBox,
            MethodEntryPoints entrypoints) {
        this.sm = sm;
        this.output = output;
        this.localVariables = localVariables;
        this.pc = pc;
        this.labelsManager = labelsManager;
        this.bnode = bnode;
        this.currentMethodCode = currentMethodCode;
        this.requiredIncludes = requiredIncludes;
        this.methodDesc = methodDesc;
        this.toolBox = toolBox;
        this.entrypoints = entrypoints;
    }

    private abstract class Emitter implements InvokeEmitter {

        protected Method referredMethod;
        protected int numArgs;
        protected MethodEntryPoints calleeEntry;
        protected StackManager smCopy;
        protected String exceptionVariable;
        protected String uniqueMethodIDCallee;

        Emitter(MethodEntryPoints calleeEntry) {
            this.calleeEntry = calleeEntry;
        }

        public abstract void handleReturnValue() throws Exception;

        public void setup() throws Exception {
            referredMethod = ClassfileUtils.findMethod(methodDesc.getClassName(), methodDesc.getName(), methodDesc.getSignature()).getMethod();

            numArgs = ClassfileUtils.getNumArgs(referredMethod);

            if (currentMethodCode[pc] != RawByteCodes.invokestatic_opcode) {
                numArgs++;
            }
        }

        public void callSetupException() throws Exception {
            smCopy = sm.copy();

            exceptionVariable = "";

            if (a_mayThrowExceptions(methodDesc.getClassName(), methodDesc.getName(), methodDesc.getSignature())) {
                exceptionVariable = "rval_m_" + pc;
                int returnTypeSize = 0;
                if (calleeEntry != null) {
                    returnTypeSize = calleeEntry.getReturnTypeSize();
                } else {
                    returnTypeSize = Size.SHORT;
                }

                localVariables.print("   " + AOTCompiler.getTypeCast(returnTypeSize) + " " + exceptionVariable + ";\n");
            }
        }

        public void performCall() throws Exception {
            if (AOTCompiler.synchronizedSupported(referredMethod)) {
                output.append("   handleMonitorEnterExit((Object*)(pointer)" + getHandleMonitorArgs());
                requiredIncludes.print("extern unsigned char handleMonitorEnterExit(Object* lockObj, unsigned char isEnter, int32* sp, const char* fromMethod);\n");
            }

            uniqueMethodIDCallee = toolBox.getIdGenerator().getUniqueId(methodDesc.getClassName(), methodDesc.getName(), methodDesc.getSignature());
            if (AOTCompiler.interpretMethod(referredMethod, methodDesc, toolBox.getCregistry())) {
                output.append("   " + exceptionVariable + " = enterMethodInterpreter(" + uniqueMethodIDCallee.toUpperCase() + ", sp);\n");
            } else {
                if (referredMethod.isNative() || toolBox.getManager().skipMethodHack(methodDesc.getClassName(), methodDesc.getName(), methodDesc.getSignature())) {
                    uniqueMethodIDCallee = "n_" + uniqueMethodIDCallee;
                }

                output.append("   " + exceptionVariable + " = " + uniqueMethodIDCallee + "(sp");
                addCallParameters();
                output.append(");\n");
            }
            a_setSPUsed(true);
        }

        @Override
        public MethodOrFieldDesc getMethodDesc() {
            return methodDesc;
        }

        @Override
        public void setMethodDesc(MethodOrFieldDesc m) throws Exception {
            methodDesc = m;
            setup();
        }
        
        public abstract String callSetup() throws Exception;

        public abstract String getHandleMonitorArgs();

        public abstract void addCallParameters();
    }

    private class WithStackEmitter extends Emitter {

        WithStackEmitter(MethodEntryPoints calleeEntry) {
            super(calleeEntry);
        }

        public String callSetup() throws Exception {
            sm.flush(true);

            a_adjustStackAndCheckObject(output, localVariables, pc, labelsManager, numArgs, null, "", sm);
            
            return null;
        }

        @Override
        public String getHandleMonitorArgs() {
            return "*sp, 1, sp + " + numArgs + ", \"\");\n";
        }

        @Override
        public void addCallParameters() {
            requiredIncludes.print("int16 " + uniqueMethodIDCallee + "(int32* sp);\n");
        }

        @Override
        public void handleReturnValue() throws Exception {
            int numReturnValues = Compiler.getNumReturnValues(referredMethod) & 0x3;
            String returnValueString;
            if (numReturnValues > 0) {
                returnValueString = "      sp += " + numReturnValues + ";\n";
            } else {
                returnValueString = null;
            }

            String noExceptionCondition = exceptionVariable + " == -1";
            boolean negateExceptionValue = false;
            if ((calleeEntry != null) && calleeEntry.useCombinedReturnType()) {
                noExceptionCondition = exceptionVariable + " >= 0";
                negateExceptionValue = true;
            }

            a_handleExceptionOccurred(output, localVariables, pc, labelsManager, returnValueString, "", smCopy, methodDesc, exceptionVariable, entrypoints, noExceptionCondition, negateExceptionValue);
        }

        @Override
        public boolean isWithArgsEmitter() {
            return false;
        }

        @Override
        public MethodOrFieldDesc getMethodDesc() {
            return methodDesc;
        }        
    }

    private class WithArgsEmitter extends Emitter {

        private int spAdjustment;
        private StringBuffer currentOutput;
        private Stack<String> argNames;

        WithArgsEmitter(MethodEntryPoints calleeEntry) {
            super(calleeEntry);
        }

        public String callSetup() throws Exception {
            argNames = new Stack<String>();
            StringBuffer types = new StringBuffer();
            spAdjustment = 0;
            int n = numArgs;
            int offset = 1;
            while (n > 0) {
                int srcSize = a_normalizeProducersSize(bnode, bnode.getAinfo().entryStack.size() - offset);
                offset++;

                String argName;

                if (sm.isCached(0)) {
                    argName = sm.peekTop(1, srcSize);
                    sm.pop(null, srcSize);
                } else {
                    argName = "hvm_arg_no_" + n + "_" + pc;
                    sm.pop(argName, srcSize);
                    localVariables.print("   " + AOTCompiler.getTypeCast(srcSize) + " " + argName + ";\n");
                }

                argNames.push(argName);
                n--;

                types.append(AOTCompiler.getTypeCast(srcSize));
                if (n > 0) {
                    types.append(", ");
                }
            }

            if (currentMethodCode[pc] != RawByteCodes.invokestatic_opcode) {
                if (AOTCompiler.nullPointerCheckRequired(numArgs, bnode)) {
                    AOTCompiler.checkObject(output, localVariables, pc, labelsManager, null, "", sm, argNames.peek());
                }
            }

            currentOutput = sm.getOutput();
            StringBuffer discardThis = new StringBuffer();
            sm.setOutput(discardThis);

            spAdjustment = sm.flush(false);

            sm.setOutput(currentOutput);

            if (spAdjustment > 0) {
                output.append("   sp += " + spAdjustment + ";\n");
            }
            if (argNames.size() > 0)
            {
                return argNames.peek();
            }
            else
            {
                return null;
            }
        }

        @Override
        public String getHandleMonitorArgs() {
            return argNames.peek() + ", 1, sp, \"\");\n";
        }

        @Override
        public void addCallParameters() {
            Stack<String> argNamesReversed = new Stack<String>();
            while (argNames.size() > 0) {
                String arg = argNames.pop();
                argNamesReversed.push(arg);
                output.append(", ");
                output.append(arg);
            }
            
            while (argNamesReversed.size() > 0) {
                String arg = argNamesReversed.pop();
                argNames.push(arg);
            }
        }

        @Override
        public void handleReturnValue() throws Exception {
            int numReturnValues = Compiler.getNumReturnValues(referredMethod) & 0x3;
            String returnValueString;
            if (numReturnValues > 0) {
                StringBuffer loadResult = new StringBuffer();
                int size = AOTCompiler.getReturntypeSize(referredMethod);
                String rval = "rval_" + pc;
                String rvalType = AOTCompiler.getTypeCast(size);

                currentOutput = sm.getOutput();

                sm.setOutput(loadResult);
                switch (size) {
                case Size.LONG:
                    loadResult.append("   " + rval + " = *" + AOTCompiler.getPointerCast(size) + "sp;\n");
                    sm.push(size, rval);
                    loadResult.append("   " + rval + " = *" + AOTCompiler.getPointerCast(size) + "(sp + 1);\n");
                    sm.push(size, rval);
                    break;
                case Size.INT:
                case Size.SHORT:
                case Size.BYTE:
                    if (calleeEntry.useCombinedReturnType()) {
                        sm.push(size, exceptionVariable);
                        rval = null;
                    } else {
                        loadResult.append("   " + rval + " = *" + AOTCompiler.getPointerCast(size) + "sp;\n");
                        sm.push(size, rval);
                    }
                    break;
                }
                sm.setOutput(currentOutput);
                if (rval != null) {
                    localVariables.print("   " + rvalType + " " + rval + ";\n");
                }
                returnValueString = loadResult.toString();

            } else {
                returnValueString = null;
            }

            String noExceptionCondition = exceptionVariable + " == -1";
            boolean negateExceptionValue = false;
            if ((calleeEntry != null) && calleeEntry.useCombinedReturnType()) {
                noExceptionCondition = exceptionVariable + " >= 0";
                negateExceptionValue = true;
            }

            a_handleExceptionOccurred(output, localVariables, pc, labelsManager, returnValueString, "", smCopy, methodDesc, exceptionVariable, entrypoints, noExceptionCondition, negateExceptionValue);

            if (spAdjustment > 0) {
                output.append("   sp -= " + spAdjustment + ";\n");
            }
        }

        @Override
        public boolean isWithArgsEmitter() {
            return true;
        }
    }

    public InvokeEmitter getEmitter() throws Exception {
        MethodEntryPoints calleeEntry = toolBox.getDependencyExtent().getMethod(methodDesc.getClassName(), methodDesc.getName(), methodDesc.getSignature());
        Emitter emitter;

        if ((calleeEntry != null) && calleeEntry.canCallWithArgs()) {
            emitter = new WithArgsEmitter(calleeEntry);
        } else {
            emitter = new WithStackEmitter(calleeEntry);
        }

        return emitter;
    }

    protected abstract void a_handleExceptionOccurred(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc, LabelsManager labelsManager, String returnValueString, String string, StackManager smCopy, MethodOrFieldDesc methodDesc, String exceptionVariable,
            MethodEntryPoints entrypoints, String noExceptionCondition, boolean negateExceptionValue) throws Exception;

    protected abstract void a_setSPUsed(boolean b);

    protected abstract boolean a_mayThrowExceptions(String className, String name, String signature);

    protected abstract int a_normalizeProducersSize(BNode bnode, int i) throws Exception;

    protected abstract void a_adjustStackAndCheckObject(StringBuffer output, NoDuplicatesMemorySegment localVariables, int pc, LabelsManager labelsManager, int numArgs, String getObjectInfo, String string, StackManager sm) throws Exception;
}
