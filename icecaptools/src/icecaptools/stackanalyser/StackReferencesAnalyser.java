package icecaptools.stackanalyser;

import icecaptools.ClassfileUtils;
import icecaptools.JavaArrayClass;
import icecaptools.MethodEntryPoints;
import icecaptools.RawByteCodes;
import icecaptools.RawByteCodes.RawBytecode;
import icecaptools.RawByteCodes.RawCpIndexOperation;
import icecaptools.RawByteCodes.RawShortBranchOperation;
import icecaptools.RawByteCodes.Raw_invokeinterface;
import icecaptools.RawByteCodes.Raw_invokespecial;
import icecaptools.RawByteCodes.Raw_invokestatic;
import icecaptools.RawByteCodes.Raw_invokevirtual;
import icecaptools.RawByteCodes.Raw_jsr;
import icecaptools.RawByteCodes.Raw_jsr_w;
import icecaptools.RawByteCodes.Raw_ldc;
import icecaptools.RawByteCodes.Raw_ldc2_w;
import icecaptools.RawByteCodes.Raw_ldc_w;
import icecaptools.RawByteCodes.Raw_lookupswitch;
import icecaptools.RawByteCodes.Raw_lookupswitch.Pair;
import icecaptools.RawByteCodes.Raw_multianewarray;
import icecaptools.RawByteCodes.Raw_tableswitch;
import icecaptools.RawByteCodes.Raw_wide;
import icecaptools.compiler.LDCConstant;
import icecaptools.stackanalyser.AbstractStack.StackCell;
import icecaptools.stackanalyser.RefType.RefState;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.bcel.Constants;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

public class StackReferencesAnalyser {
    protected Method method;
    protected JavaClass clazz;
    private MethodEntryPoints methodEntryPoints;
    
    private Map<Integer, AbstractStack> stackUsage;

    private static class WorkItem {
        public int address;
        public byte[] byteCode;
        public AbstractStack entryStack;
    }

    Stack<WorkItem> workItems;
    

    public StackReferencesAnalyser(MethodEntryPoints javaMethod, JavaClass clazz) {
        this.methodEntryPoints = javaMethod;
        this.method = javaMethod.getMethod();
        this.clazz = clazz;
    }

    public StackReferencesAnalyser(Method method, JavaClass clazz2) {
        this.method = method;
    }

    private void analyseIt(int address, byte[] byteCode, AbstractStack entryStack) throws Exception {
        WorkItem newWorkItem = new WorkItem();
        newWorkItem.address = address;
        newWorkItem.byteCode = byteCode;
        newWorkItem.entryStack = entryStack;

        workItems = new Stack<WorkItem>();

        workItems.push(newWorkItem);

        while (workItems.size() > 0) {
            newWorkItem = workItems.pop();
            analyseByteCode(newWorkItem.address, newWorkItem.byteCode, newWorkItem.entryStack);
        }
    }

    private void pushWorkItem(int address, byte[] byteCode, AbstractStack entryStack) {
        WorkItem newWorkItem = new WorkItem();
        newWorkItem.address = address;
        newWorkItem.byteCode = byteCode;
        newWorkItem.entryStack = entryStack;

        workItems.push(newWorkItem);
    }

    public void analyseStackUsage() throws Exception {
        Code code = method.getCode();
        if (code != null) {
            byte[] byteCode = code.getCode();
            AbstractStack aStack = createAbstractStack(code, method);
            stackUsage = new HashMap<Integer, AbstractStack>();

            analyseIt(0, byteCode, aStack);

            CodeException[] execptionTable = code.getExceptionTable();

            for (CodeException codeException : execptionTable) {
                int handlerPC = codeException.getHandlerPC();
                aStack = createAbstractStack(code, method);
                aStack.pushRef();
                analyseIt(handlerPC, byteCode, aStack);
            }
        }
    }

    private AbstractStack createAbstractStack(Code code, Method javaMethod2) {
        AbstractStack aStack = new AbstractStack();

        int index = 0;

        if (!javaMethod2.isStatic()) {
            aStack.pushRef();
            ((RefType) (aStack.peek().content)).setState(RefState.NONNULL);
            index++;
        }

        Type[] args = javaMethod2.getArgumentTypes();

        for (Type type : args) {
            switch (type.getType()) {
            case Constants.T_ARRAY:
            case Constants.T_OBJECT:
                aStack.pushRef();
                break;
            default:
                aStack.pushUnknown();
            }
            index++;
        }
        while (index < code.getMaxLocals()) {
            aStack.pushUnknown();
            index++;
        }
        return aStack;
    }

    private void analyseByteCode(int address, byte[] byteCode, AbstractStack entryStack) throws Exception {
        AbstractStack currentStack = stackUsage.get(address);
        AbstractStack exitStack;

        if (currentStack != null) {
            if (currentStack.equals(entryStack)) {
                return;
            } else {
                currentStack.merge(entryStack);
                exitStack = currentStack.copy();
            }
        } else {
            exitStack = entryStack.copy();
            stackUsage.put(address, entryStack);
        }

        RawBytecode currentRawBytecode;

        StackCell objAtTop = null;

        currentRawBytecode = methodEntryPoints.getRawByteCode(address);
        
        byte code = (byte) currentRawBytecode.getOpcode();
        
        switch (code) {
        case RawByteCodes.nop_opcode:
            break;
        case RawByteCodes.aconst_null_opcode: {
            exitStack.pushRef();
            StackCell top = exitStack.peek();
            ((RefType) top.content).setState(RefState.NULL);
            break;
        }
        case RawByteCodes.iconst_m1_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iconst_0_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iconst_1_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iconst_2_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iconst_3_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iconst_4_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iconst_5_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lconst_0_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lconst_1_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fconst_0_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fconst_1_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fconst_2_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dconst_0_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dconst_1_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.bipush_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.sipush_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ldc_opcode:
            handleLDC(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.ldc_w_opcode:
            handleLDC(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.ldc2_w_opcode:
            handleLDC(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.iload_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lload_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fload_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dload_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iload_0_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iload_1_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iload_2_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iload_3_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lload_0_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lload_1_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lload_2_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lload_3_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fload_0_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fload_1_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fload_2_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fload_3_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dload_0_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dload_1_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dload_2_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dload_3_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.aload_0_opcode:
        case RawByteCodes.aload_1_opcode:
        case RawByteCodes.aload_2_opcode:
        case RawByteCodes.aload_3_opcode:
        case RawByteCodes.aload_opcode: {
            int index;
            switch (code) {
            case RawByteCodes.aload_0_opcode:
                index = 0;
                break;
            case RawByteCodes.aload_1_opcode:
                index = 1;
                break;
            case RawByteCodes.aload_2_opcode:
                index = 2;
                break;
            case RawByteCodes.aload_3_opcode:
                index = 3;
                break;
            default:
                index = ((RawByteCodes.Raw_aload) currentRawBytecode).localVariableIndex;
            }
            StackCell element = exitStack.getAt(index);
            if (element.content.getClass() == UnknownType.class) {
                element.content = new RefType();
            }
            element = element.copy();
            ((RefType) element.content).identicleWith = index;
            exitStack.push(element);
            break;
        }
        case RawByteCodes.iaload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.laload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.faload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.daload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.aaload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushRef();
            break;
        case RawByteCodes.baload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.caload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.saload_opcode:
            exitStack.popNonRef();
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.istore_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.lstore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.fstore_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.dstore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.istore_0_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.istore_1_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.istore_2_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.istore_3_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.lstore_0_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.lstore_1_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.lstore_2_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.lstore_3_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.fstore_0_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.fstore_1_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.fstore_2_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.fstore_3_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.dstore_0_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.dstore_1_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.dstore_2_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.dstore_3_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.astore_opcode:
        case RawByteCodes.astore_0_opcode:
        case RawByteCodes.astore_1_opcode:
        case RawByteCodes.astore_2_opcode:
        case RawByteCodes.astore_3_opcode: {
            int index;
            switch (code) {
            case RawByteCodes.astore_0_opcode:
                index = 0;
                break;
            case RawByteCodes.astore_1_opcode:
                index = 1;
                break;
            case RawByteCodes.astore_2_opcode:
                index = 2;
                break;
            case RawByteCodes.astore_3_opcode:
                index = 3;
                break;
            default:
                index = ((RawByteCodes.Raw_astore) currentRawBytecode).localVariableIndex;
                break;
            }
            StackCell cell = exitStack.popRef();
            exitStack.setAt(index, cell.copy());
            exitStack.clear(index);
            break;
        }
        case RawByteCodes.iastore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.lastore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.fastore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.dastore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.aastore_opcode:
            exitStack.popRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.bastore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.castore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.sastore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popRef();
            break;
        case RawByteCodes.pop_opcode:
            exitStack.popAny();
            break;
        case RawByteCodes.pop2_opcode:
            exitStack.popAny();
            exitStack.popAny();
            break;
        case RawByteCodes.dup_opcode:

            StackCell top = exitStack.popAny();
            int index = exitStack.getSize();
            exitStack.push(top);
            top = top.copy();

            if (top.content instanceof RefType) {
                ((RefType) top.content).identicleWith = index;
            }
            exitStack.push(top);
            break;
        case RawByteCodes.dup_x1_opcode: {
            StackCell value1 = exitStack.popAny();
            StackCell value2 = exitStack.popAny();
            exitStack.push(value1);
            exitStack.push(value2);
            exitStack.push(value1);
        }
            break;
        case RawByteCodes.dup_x2_opcode: {
            StackCell value1 = exitStack.popAny();
            StackCell value2 = exitStack.popAny();
            StackCell value3 = exitStack.popAny();
            exitStack.push(value1);
            exitStack.push(value3);
            exitStack.push(value2);
            exitStack.push(value1);
        }
            break;
        case RawByteCodes.dup2_opcode: {
            StackCell value1 = exitStack.popAny();
            StackCell value2 = exitStack.popAny();
            exitStack.push(value2);
            exitStack.push(value1);
            exitStack.push(value2);
            exitStack.push(value1);
        }
            break;
        case RawByteCodes.dup2_x1_opcode: {
            StackCell value1 = exitStack.popAny();
            StackCell value2 = exitStack.popAny();
            StackCell value3 = exitStack.popAny();
            exitStack.push(value2);
            exitStack.push(value1);
            exitStack.push(value3);
            exitStack.push(value2);
            exitStack.push(value1);
        }
            break;
        case RawByteCodes.dup2_x2_opcode: {
            StackCell value1 = exitStack.popAny();
            StackCell value2 = exitStack.popAny();
            StackCell value3 = exitStack.popAny();
            StackCell value4 = exitStack.popAny();
            exitStack.push(value2);
            exitStack.push(value1);
            exitStack.push(value4);
            exitStack.push(value3);
            exitStack.push(value2);
            exitStack.push(value1);

        }
            break;
        case RawByteCodes.swap_opcode: {
            StackCell value1 = exitStack.popAny();
            StackCell value2 = exitStack.popAny();
            exitStack.push(value1);
            exitStack.push(value2);
        }
            break;
        case RawByteCodes.iadd_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ladd_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fadd_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dadd_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.isub_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lsub_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fsub_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dsub_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.imul_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lmul_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fmul_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dmul_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.idiv_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ldiv_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fdiv_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ddiv_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.irem_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lrem_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.frem_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.drem_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ineg_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lneg_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fneg_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dneg_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ishl_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lshl_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ishr_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lshr_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iushr_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lushr_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iand_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.land_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ior_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ixor_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lor_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lxor_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.iinc_opcode:
            break;
        case RawByteCodes.i2l_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.i2f_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.i2d_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.l2i_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.l2f_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.l2d_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.f2i_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.f2l_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.f2d_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.d2i_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.d2l_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.d2f_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.i2b_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.i2c_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.i2s_opcode:
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.lcmp_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fcmpl_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.fcmpg_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dcmpl_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.dcmpg_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.popNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.ifeq_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.ifne_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.iflt_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.ifge_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.ifgt_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.ifle_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_icmpeq_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_icmpne_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_icmplt_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_icmpge_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_icmpgt_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_icmple_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.if_acmpeq_opcode:
            exitStack.popRef();
            exitStack.popRef();
            break;
        case RawByteCodes.if_acmpne_opcode:
            exitStack.popRef();
            exitStack.popRef();
            break;
        case RawByteCodes.goto_opcode:
            break;
        case RawByteCodes.jsr_opcode:
            break;
        case RawByteCodes.ret_opcode:
            return;
        case RawByteCodes.tableswitch_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.lookupswitch_opcode:
            exitStack.popNonRef();
            break;
        case RawByteCodes.ireturn_opcode:
        case RawByteCodes.lreturn_opcode:
        case RawByteCodes.freturn_opcode:
        case RawByteCodes.dreturn_opcode:
        case RawByteCodes.areturn_opcode:
        case RawByteCodes.return_opcode:
            return;
        case RawByteCodes.getstatic_opcode:
            handlePutGetField(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.putstatic_opcode:
            handlePutGetField(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.getfield_opcode:
            handlePutGetField(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.putfield_opcode:
            handlePutGetField(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.invokedynamic_opcode:
            exitStack.pushRef();
            break;
        case RawByteCodes.invokevirtual_opcode:
            handleInvoke(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.invokespecial_opcode:
            handleInvoke(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.invokestatic_opcode:
            handleInvoke(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.invokeinterface_opcode:
            handleInvoke(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.new_opcode:
            exitStack.pushRef();
            ((RefType) exitStack.peek().content).setState(RefState.NONNULL);
            break;
        case RawByteCodes.anewarray_opcode:
            handleANewArray(exitStack, currentRawBytecode);
            ((RefType) exitStack.peek().content).setState(RefState.NONNULL);
            break;
        case RawByteCodes.newarray_opcode:
            handleNewArray(exitStack, currentRawBytecode);
            ((RefType) exitStack.peek().content).setState(RefState.NONNULL);
            break;
        case RawByteCodes.arraylength_opcode:
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.athrow_opcode:
            return;
        case RawByteCodes.checkcast_opcode:
            break;
        case RawByteCodes.instanceof_opcode:
            exitStack.popRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.monitorenter_opcode:
            exitStack.popRef();
            break;
        case RawByteCodes.monitorexit_opcode:
            exitStack.popRef();
            break;
        case RawByteCodes.wide_opcode:
            handleWide(exitStack, currentRawBytecode, code);
            break;
        case RawByteCodes.multianewarray_opcode: {

            Raw_multianewarray multi = (Raw_multianewarray) currentRawBytecode;
            int dimensions = multi.count;
            while (dimensions > 0) {
                exitStack.popNonRef();
                dimensions--;
            }
            exitStack.pushRef();
        }
            break;
        case RawByteCodes.ifnull_opcode:
            objAtTop = exitStack.popRef();
            break;
        case RawByteCodes.ifnonnull_opcode:
            objAtTop = exitStack.popRef();
            break;
        case RawByteCodes.goto_w_opcode:
            break;
        case RawByteCodes.jsr_w_opcode:
            exitStack.pushNonRef();
            break;
        default:
            throw new Exception("Unknown byte code [" + code + "]");
        }


        if ((currentRawBytecode instanceof Raw_jsr) || (currentRawBytecode instanceof Raw_jsr_w)) {
            RawShortBranchOperation jsr = (RawShortBranchOperation) currentRawBytecode;
            exitStack.pushRef();
            analyseByteCode((int) (address + jsr.branchoffset), byteCode, exitStack.copy());
            exitStack.popRef();
            pushWorkItem((int) (address + currentRawBytecode.getSize()), byteCode, exitStack);
        } else if (currentRawBytecode instanceof RawShortBranchOperation) {
            RawShortBranchOperation branch = (RawShortBranchOperation) currentRawBytecode;

            if (code == RawByteCodes.ifnull_opcode) {
                RefType ref = (RefType) objAtTop.content;
                AbstractStack branchStack = exitStack.copy();
                storeInfoInLocal(branchStack, ref, RefState.NULL);
                pushWorkItem((int) (address + branch.branchoffset), byteCode, branchStack);

                storeInfoInLocal(exitStack, ref, RefState.NONNULL);
            } else {
                pushWorkItem((int) (address + branch.branchoffset), byteCode, exitStack);
            }

            if (!((currentRawBytecode instanceof RawByteCodes.Raw_goto) || (currentRawBytecode instanceof RawByteCodes.Raw_jsr) || (currentRawBytecode instanceof RawByteCodes.RawWideBranchOperation))) {
                pushWorkItem((int) (address + branch.getSize()), byteCode, exitStack);
            }
        } else if (currentRawBytecode instanceof Raw_lookupswitch) {
            Raw_lookupswitch lookup = (Raw_lookupswitch) currentRawBytecode;
            Pair[] pairs = lookup.pairs;
            int npairs = (int) lookup.npairs;

            for (int i = 0; i < npairs; i++) {
                Pair pair = pairs[i];
                pushWorkItem((int) (address + pair.offset), byteCode, exitStack);
            }
            pushWorkItem((int) (address + lookup.defaultVal), byteCode, exitStack);

        } else if (currentRawBytecode instanceof Raw_tableswitch) {
            Raw_tableswitch table = (Raw_tableswitch) currentRawBytecode;
            long[] offsets = table.offsets;
            for (int i = 0; i < offsets.length; i++) {
                pushWorkItem((int) (address + offsets[i]), byteCode, exitStack);
            }
            pushWorkItem((int) (address + table.defaultVal), byteCode, exitStack);
        } else {
            pushWorkItem((int) (address + currentRawBytecode.getSize()), byteCode, exitStack);
        }

    }

    protected void handleNewArray(AbstractStack exitStack, RawBytecode currentRawBytecode) throws UnexpectedTypeException {
        exitStack.popNonRef();
        exitStack.pushArrayRef(currentRawBytecode);
    }

    protected void handleANewArray(AbstractStack exitStack, RawBytecode currentRawBytecode) throws UnexpectedTypeException {
        exitStack.popNonRef();
        exitStack.pushArrayRef(currentRawBytecode);
    }

    private void handleWide(AbstractStack exitStack, RawBytecode currentRawBytecode, byte code) throws UnexpectedTypeException {
        Raw_wide wide = (Raw_wide) currentRawBytecode;
        switch (wide.actualOpcode) {
        case RawByteCodes.iload_opcode:
        case RawByteCodes.fload_opcode:
            exitStack.pushNonRef();
            break;
        case RawByteCodes.aload_opcode:
            exitStack.pushRef();
            break;
        case RawByteCodes.lload_opcode:
        case RawByteCodes.dload_opcode:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        case RawByteCodes.istore_opcode:
        case RawByteCodes.fstore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.astore_opcode:
            exitStack.popRef();
            break;
        case RawByteCodes.lstore_opcode:
        case RawByteCodes.dstore_opcode:
            exitStack.popNonRef();
            exitStack.popNonRef();
            break;
        case RawByteCodes.ret_opcode:
        case RawByteCodes.iinc_opcode:
        }
    }

    private void handleInvoke(AbstractStack exitStack, RawBytecode currentRawBytecode, byte code) throws Exception {
        int index;
        ConstantMethodref methodRef = null;
        ConstantClass classRef = null;
        int methodNameAndTypIndex = -1;

        if (currentRawBytecode instanceof Raw_invokevirtual) {
            Raw_invokevirtual rawInvokeVirtual = (Raw_invokevirtual) currentRawBytecode;
            index = rawInvokeVirtual.cpIndex;
            methodRef = (ConstantMethodref) clazz.getConstantPool().getConstant(index);
            classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
            methodNameAndTypIndex = methodRef.getNameAndTypeIndex();
        } else if (currentRawBytecode instanceof Raw_invokespecial) {
            Raw_invokespecial rawInvokeSpecial = (Raw_invokespecial) currentRawBytecode;
            index = rawInvokeSpecial.cpIndex;
            methodRef = (ConstantMethodref) clazz.getConstantPool().getConstant(index);
            classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
            methodNameAndTypIndex = methodRef.getNameAndTypeIndex();
        } else if (currentRawBytecode instanceof Raw_invokestatic) {
            Raw_invokestatic rawInvokeStatic = (Raw_invokestatic) currentRawBytecode;
            index = rawInvokeStatic.cpIndex;
            methodRef = (ConstantMethodref) clazz.getConstantPool().getConstant(index);
            classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
            methodNameAndTypIndex = methodRef.getNameAndTypeIndex();
        } else if (currentRawBytecode instanceof Raw_invokeinterface) {
            Raw_invokeinterface rawInvokeInterface = (Raw_invokeinterface) currentRawBytecode;
            index = rawInvokeInterface.cpIndex;
            ConstantInterfaceMethodref imethodRef = (ConstantInterfaceMethodref) clazz.getConstantPool().getConstant(index);
            classRef = (ConstantClass) clazz.getConstantPool().getConstant(imethodRef.getClassIndex());
            methodNameAndTypIndex = imethodRef.getNameAndTypeIndex();
        }

        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodNameAndTypIndex);
        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
        ConstantUtf8 methodName = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getNameIndex());
        ConstantUtf8 methodSig = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getSignatureIndex());

        String class_name;

        if (JavaArrayClass.isArrayClass(className.getBytes()) && methodName.getBytes().equals("clone") && methodSig.getBytes().equals("()Ljava/lang/Object;")) {
            class_name = "java.lang.Object";
        } else {
            class_name = className.getBytes();
        }

        JavaClass clazz = Repository.lookupClass(class_name);

        Method method;
        
        if (currentRawBytecode instanceof Raw_invokeinterface)
        {
            method = ClassfileUtils.findDeclaringInterface(clazz, methodName.getBytes(), methodSig.getBytes()).getMethod();
        }
        else
        {
            method = ClassfileUtils.findMethodInClassHierarchy(clazz, methodName.getBytes(), methodSig.getBytes()).getMethod();
        }
        

        popArgs(exitStack, method);
        if (!(currentRawBytecode instanceof Raw_invokestatic)) {
            StackCell thisPointer = exitStack.popRef();
            RefType refType = (RefType) thisPointer.content;

            storeInfoInLocal(exitStack, refType, RefState.NONNULL);
        }
        pushReturnValue(exitStack, method);
    }

    protected void storeInfoInLocal(AbstractStack exitStack, RefType refType, RefState refstate) {
        if (refType.identicleWith != -1) {
            StackCell lv = exitStack.getAt(refType.identicleWith);
            refType = (RefType) lv.content;
            refType.setState(refstate);
        }
    }

    private void pushReturnValue(AbstractStack exitStack, Method method) throws Exception {
        org.apache.bcel.generic.Type returnType = method.getReturnType();
        if (returnType.getSize() > 0) {
            pushPopType(exitStack, returnType, false);
        }
    }

    private void popArgs(AbstractStack exitStack, Method method) throws Exception {
        org.apache.bcel.generic.Type[] args = method.getArgumentTypes();
        for (int i = args.length; i > 0; i--) {
            org.apache.bcel.generic.Type currentType = args[i - 1];
            pushPopType(exitStack, currentType, true);
        }
    }

    private void pushPopType(AbstractStack exitStack, org.apache.bcel.generic.Type currentType, boolean pop) throws Exception {
        boolean isRef;
        if (currentType instanceof BasicType) {
            isRef = false;
        } else if (currentType instanceof ReferenceType) {
            isRef = true;
        } else {
            throw new Exception("Unknown field type");
        }
        int size = currentType.getSize();
        if (size == 0) {
            throw new Exception("Zero size field");
        }
        while (size > 0) {
            if (isRef) {
                if (pop) {
                    exitStack.popRef();
                } else {
                    exitStack.pushRef();
                }
            } else {
                if (pop) {
                    exitStack.popNonRef();
                } else {
                    exitStack.pushNonRef();
                }
            }
            size--;
        }
    }

    protected Field handlePutGetField(AbstractStack exitStack, RawBytecode currentRawBytecode, byte code) throws Exception {
        RawCpIndexOperation putgetfield = (RawCpIndexOperation) currentRawBytecode;
        int index = putgetfield.cpIndex;
        ConstantFieldref fieldRef = (ConstantFieldref) clazz.getConstantPool().getConstant(index);
        ConstantClass classIndex = (ConstantClass) clazz.getConstantPool().getConstant(fieldRef.getClassIndex());
        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classIndex.getNameIndex());
        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(fieldRef.getNameAndTypeIndex());
        String fieldName = nameAndType.getName(clazz.getConstantPool());
        String signature = nameAndType.getSignature(clazz.getConstantPool());

        Field field = findField(className.getBytes(), fieldName, signature);
        boolean isRef = fieldIsRef(field);

        if (field != null) {
            if (field.getType() instanceof BasicType) {
                isRef = false;
            } else if (field.getType() instanceof ReferenceType) {
                isRef = true;
            } else {
                throw new Exception("Unknown field type");
            }
        } else {
            throw new Exception("Could not find field");
        }

        int size = field.getType().getSize();

        if (size == 0) {
            throw new Exception("Zero size field");
        }

        if (code == RawByteCodes.getfield_opcode) {
            StackCell objPointer = exitStack.popRef();
            RefType refType = (RefType) objPointer.content;

            storeInfoInLocal(exitStack, refType, RefState.NONNULL);
        }

        while (size > 0) {
            if (isRef) {
                if ((code == RawByteCodes.putfield_opcode) || ((code == RawByteCodes.putstatic_opcode))) {
                    exitStack.popRef();
                } else if ((code == RawByteCodes.getfield_opcode) || ((code == RawByteCodes.getstatic_opcode))) {
                    exitStack.pushRef();
                }
            } else {
                if ((code == RawByteCodes.putfield_opcode) || ((code == RawByteCodes.putstatic_opcode))) {
                    exitStack.popNonRef();
                } else if ((code == RawByteCodes.getfield_opcode) || ((code == RawByteCodes.getstatic_opcode))) {
                    exitStack.pushNonRef();
                }
            }
            size--;
        }

        if (code == RawByteCodes.putfield_opcode) {
            StackCell objPointer = exitStack.popRef();
            RefType refType = (RefType) objPointer.content;

            storeInfoInLocal(exitStack, refType, RefState.NONNULL);
        }

        return field;
    }

    public static boolean fieldIsRef(Field field) throws Exception {
        if (field != null) {
            if (field.getType() instanceof BasicType) {
                return false;
            } else if (field.getType() instanceof ReferenceType) {
                return true;
            } else {
                throw new Exception("Unknown field type");
            }
        } else {
            throw new Exception("Could not find field");
        }
    }

    public static Field findField(String className, String fieldName, String fieldSignature) throws ClassNotFoundException {
        JavaClass clazz = Repository.lookupClass(className);
        Field[] fields = clazz.getFields();
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                Field current = fields[i];
                if (current.getName().equals(fieldName)) {
                    if (current.getSignature().equals(fieldSignature)) {
                        return current;
                    }
                }
            }
        }
        JavaClass superClass = clazz.getSuperClass();
        if (superClass != null) {
            return findField(superClass.getClassName(), fieldName, fieldSignature);
        } else {
            return null;
        }
    }

    private void handleLDC(AbstractStack exitStack, RawBytecode currentRawBytecode, byte code) throws ClassNotFoundException {
        int cpIndex;
        if (currentRawBytecode instanceof Raw_ldc) {
            cpIndex = ((Raw_ldc) currentRawBytecode).cpIndex;
        } else if (currentRawBytecode instanceof Raw_ldc2_w) {
            cpIndex = ((Raw_ldc2_w) currentRawBytecode).cpIndex;
        } else {
            cpIndex = ((Raw_ldc_w) currentRawBytecode).cpIndex;
        }

        LDCConstant constant = ClassfileUtils.getLDCConstant(clazz.getClassName(), cpIndex);
        switch (constant.getType()) {
        case LDCConstant.STRING:
        case LDCConstant.CLASS:
            exitStack.pushRef();
            break;
        case LDCConstant.INTEGER:
        case LDCConstant.FLOAT:
            exitStack.pushNonRef();
            break;
        case LDCConstant.LONG:
        case LDCConstant.DOUBLE:
            exitStack.pushNonRef();
            exitStack.pushNonRef();
            break;
        }
    }

    public AbstractStack getStackLayout(int address) {
        return stackUsage.get(address);
    }
}
