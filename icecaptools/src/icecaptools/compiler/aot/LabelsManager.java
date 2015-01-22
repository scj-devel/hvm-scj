package icecaptools.compiler.aot;

import icecaptools.MethodEntryPoints;
import icecaptools.compiler.NoDuplicatesMemorySegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;

public class LabelsManager {

    private MethodEntryPoints javaMethod;
    private HashMap<String, StringBuffer> labels;
    private int methodNumber;
    private NoDuplicatesMemorySegment localVariables;
    private NoDuplicatesMemorySegment requiredIncludes;
    private SPManipulator spManipulator;

    public static final String LExceptionSwitch = "LExceptionSwitch";
    public static final String LThrowIt = "throwIt";
    public static final String LThrowNullPointer = "throwNullPointer";
    public static final String LThrowClassCast = "throwClassCastException";
    public static final String LThrowOutOfMemory = "throwOutOfMemory";
    public static final String LThrowArithmeticException = "throwArithmeticException";

    public LabelsManager(MethodEntryPoints javaMethod, int methodNumber, NoDuplicatesMemorySegment localVariables, NoDuplicatesMemorySegment requiredIncludes, SPManipulator spManipulator) {
        this.javaMethod = javaMethod;
        labels = new HashMap<String, StringBuffer>();
        this.methodNumber = methodNumber;
        this.localVariables = localVariables;
        this.requiredIncludes = requiredIncludes;
        this.spManipulator = spManipulator;
    }

    public void generateExceptionSwitch() throws Exception {
        if (!labels.containsKey(LExceptionSwitch)) {
            ArrayList<String> generatedLabels = new ArrayList<String>();

            StringBuffer switchStatement = new StringBuffer();
            switchStatement.append("   switch(handler_pc) {\n");

            Code codeAttr = javaMethod.getMethod().getCode();
            if (codeAttr != null) {
                CodeException[] handlers = codeAttr.getExceptionTable();
                for (CodeException codeException : handlers) {
                    String label = "L" + codeException.getHandlerPC();
                    if (!generatedLabels.contains(label)) {
                        switchStatement.append("      case " + codeException.getHandlerPC() + ":\n");
                        switchStatement.append("         goto " + label + ";\n");
                        generatedLabels.add(label);
                    }
                }
            }

            switchStatement.append("      case (unsigned short)-1: /* Not handled */\n");
            switchStatement.append("      default:\n");
            switchStatement.append("         fp[0] = *(sp - 1);\n");
            if (javaMethod.useCombinedReturnType())
            {
                switchStatement.append("         return -excep;\n");
            }
            else
            {
                switchStatement.append("         return excep;\n");
            }
            switchStatement.append("   }\n");
            spManipulator.setSPUsed(true);
            localVariables.print("   " + AOTCompiler.getTypeCast(javaMethod.getReturnTypeSize()) + " excep;\n");
            
            labels.put(LExceptionSwitch, switchStatement);
        }
    }

    private static class Label {
        public String label;
        public StringBuffer code;
    }

    public StringBuffer getLabels() {
        StringBuffer exceptionSwitch = labels.get(LExceptionSwitch);
        StringBuffer result = new StringBuffer();

        if (exceptionSwitch != null) {
            labels.remove(LExceptionSwitch);
        }

        StringBuffer throwItLabel = labels.remove(LThrowIt);

        Iterator<Entry<String, StringBuffer>> iterator = labels.entrySet().iterator();

        ArrayList<Label> orderedLabels = new ArrayList<Label>();

        while (iterator.hasNext()) {
            Entry<String, StringBuffer> next = iterator.next();
            Label nextLabel = new Label();
            nextLabel.label = next.getKey();
            nextLabel.code = next.getValue();
            orderedLabels.add(nextLabel);
        }
        if (throwItLabel != null) {
            Label nextLabel = new Label();
            nextLabel.label = LThrowIt;
            nextLabel.code = throwItLabel;
            orderedLabels.add(nextLabel);
        }

        Iterator<Label> orderedIterator = orderedLabels.iterator();

        while (orderedIterator.hasNext()) {
            Label next = orderedIterator.next();
            result.append("   " + next.label + ":\n");
            result.append(next.code.toString());
            if (orderedIterator.hasNext()) {
                result.append("      goto " + LThrowIt + ";\n");
            }
        }

        if (exceptionSwitch != null) {
            result.append(exceptionSwitch.toString());
        }

        return result;
    }

    public void generateThrowIt() throws Exception {
        if (!labels.containsKey(LThrowIt)) {
            StringBuffer throwIt = new StringBuffer();
            throwIt.append("      handler_pc = handleAthrow(&methods[" + methodNumber + "], excep, pc);\n");
            throwIt.append("      sp++;\n");
            spManipulator.setSPUsed(true);
            labels.put(LThrowIt, throwIt);
            localVariables.print("   unsigned short handler_pc;\n");

            requiredIncludes.print("RANGE extern const MethodInfo *methods;\n");
            requiredIncludes.print("#include \"methods.h\"\n");

            requiredIncludes.print("extern unsigned short handleAthrow(const MethodInfo* method, unsigned short classIndex, unsigned short pc);\n");
            generateExceptionSwitch();
        }
    }

    public void generateThrowClassCast() throws Exception {
        if (!labels.containsKey(LThrowClassCast)) {
            StringBuffer throwClassCast = new StringBuffer();
            spManipulator.setSPUsed(true);
            throwClassCast.append("      excep = initializeException(sp, JAVA_LANG_CLASSCASTEXCEPTION, JAVA_LANG_CLASSCASTEXCEPTION_INIT_);\n");
            labels.put(LThrowClassCast, throwClassCast);
            requiredIncludes.print("int16 initializeException(int32* sp, int16 exceptionClass, int16 exceptionInitMethod);\n");
            requiredIncludes.print("#include \"classes.h\"\n");
            localVariables.print("   " + AOTCompiler.getTypeCast(javaMethod.getReturnTypeSize()) + " excep;\n");
            
            generateThrowIt();
            generateExceptionSwitch();
        }
    }

    public void generateThrowNullPointer() throws Exception {
        if (!labels.containsKey(LThrowNullPointer)) {
            StringBuffer throwNullPointer = new StringBuffer();
            spManipulator.setSPUsed(true);
            throwNullPointer.append("      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);\n");
            labels.put(LThrowNullPointer, throwNullPointer);

            requiredIncludes.print("int16 initializeException(int32* sp, int16 exceptionClass, int16 exceptionInitMethod);\n");
            requiredIncludes.print("#include \"classes.h\"\n");
            localVariables.print("   " + AOTCompiler.getTypeCast(javaMethod.getReturnTypeSize()) + " excep;\n");
            generateThrowIt();
            generateExceptionSwitch();
        }
    }

    public void generateOutOfMemory() throws Exception {
        if (!labels.containsKey(LThrowOutOfMemory)) {
            StringBuffer throwOutOfMemory = new StringBuffer();
            spManipulator.setSPUsed(true);
            throwOutOfMemory.append("      excep = initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR, JAVA_LANG_OUTOFMEMORYERROR_INIT_);\n");
            labels.put(LThrowOutOfMemory, throwOutOfMemory);

            requiredIncludes.print("int16 initializeException(int32* sp, int16 exceptionClass, int16 exceptionInitMethod);\n");
            requiredIncludes.print("#include \"classes.h\"\n");
            localVariables.print("   " + AOTCompiler.getTypeCast(javaMethod.getReturnTypeSize()) + " excep;\n");
            generateThrowIt();
            generateExceptionSwitch();
        }
    }

    public void generateArithmeticException() throws Exception {
        if (!labels.containsKey(LThrowArithmeticException)) {
            StringBuffer throwArithmetic = new StringBuffer();
            spManipulator.setSPUsed(true);
            throwArithmetic.append("      excep = initializeException(sp, JAVA_LANG_ARITHMETICEXCEPTION, JAVA_LANG_ARITHMETICEXCEPTION_INIT_);\n");
            labels.put(LThrowArithmeticException, throwArithmetic);

            requiredIncludes.print("int16 initializeException(int32* sp, int16 exceptionClass, int16 exceptionInitMethod);\n");
            requiredIncludes.print("#include \"classes.h\"\n");
            localVariables.print("   " + AOTCompiler.getTypeCast(javaMethod.getReturnTypeSize()) + " excep;\n");
            generateThrowIt();
            generateExceptionSwitch();
        }
    }

    public void jumpTo(StackManager sm) throws Exception {
        jumpTo(sm, false);
    }

    public void jumpTo(StackManager sm, boolean saveTop) throws Exception {
        if (saveTop) {
            spManipulator.setSPUsed(true);
            sm.flushTop(false);
            sm.getOutput().append("   sp--;\n");
        }
        sm.flushLocals();
    }
}
