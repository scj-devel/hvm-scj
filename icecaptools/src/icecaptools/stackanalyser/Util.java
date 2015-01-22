package icecaptools.stackanalyser;

import icecaptools.BNode;
import icecaptools.FieldAccessBNode;
import icecaptools.JavaArrayClass;
import icecaptools.LDCBNode;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodOrFieldDesc;
import icecaptools.RawByteCodes;
import icecaptools.StaticFieldAccessBNode;
import icecaptools.compiler.LDCConstant;

import java.util.ArrayList;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;

public class Util {

    public static ArrayList<String> getCellType(MethodEntryPoints method, ProducerConsumerCellInfo cell) throws Exception {
        ArrayList<String> types = new ArrayList<String>();
        collectCellTypes(types, method, cell);
        return types;
    }

    private static void collectCellTypes(ArrayList<String> types, MethodEntryPoints method, ProducerConsumerCellInfo cell) throws Exception {
        ArrayList<BNode> producers = cell.getProducers();

        for (BNode bNode : producers) {
            getProducerType(types, bNode, method, bNode.getOriginalAddress());
        }
    }
    
    private static void getProducerType(ArrayList<String> types, BNode bNode, MethodEntryPoints method, int pc) throws Exception {
        switch (bNode.getOpCode()) {
        case RawByteCodes.getfield_opcode:
        {
            FieldAccessBNode fieldNode = (FieldAccessBNode) bNode;
            String signature = fieldNode.getSignature();
            String typeName = JavaArrayClass.getReferredType(signature).replace("/", ".");
            types.add(typeName);
            break;
        }
        case RawByteCodes.getstatic_opcode:
        {
            StaticFieldAccessBNode fieldNode = (StaticFieldAccessBNode) bNode;
            String signature = fieldNode.getSignature();
            String typeName = JavaArrayClass.getReferredType(signature).replace("/", ".");
            types.add(typeName);
            break;
        }
        case RawByteCodes.dup_opcode:
        {
            ProducerConsumerCellInfo cell = bNode.getAinfo().entryStack.peek();
            collectCellTypes(types, method, cell);
            break;
        }
        case RawByteCodes.aload_0_opcode:
            if (!method.getMethod().isStatic()) {
                types.add(method.getClazz().getClassName());
                break;
            }
        case RawByteCodes.aload_1_opcode:
        case RawByteCodes.aload_2_opcode:
        case RawByteCodes.aload_3_opcode: {
            LocalVariableTable localVariableTable = method.getMethod().getLocalVariableTable();
            if (localVariableTable != null) {
                int index = bNode.getOpCode() - RawByteCodes.aload_0_opcode;
                LocalVariable var = localVariableTable.getLocalVariable(index, pc);
                if (var != null)
                {
                    @SuppressWarnings("unused")
                    String signature = var.getSignature();
                    signature = null;
                }
            }
            break;
        }
        case RawByteCodes.aload_opcode:
            LocalVariableTable localVariableTable = method.getMethod().getLocalVariableTable();
            if (localVariableTable != null) {
                int index = bNode.getRawBytes()[1];
                LocalVariable var = localVariableTable.getLocalVariable(index, pc);
                if (var != null)
                {
                    @SuppressWarnings("unused")
                    String signature = var.getSignature();
                    signature = null;
                }
            }
            break;
        case RawByteCodes.ldc_opcode:
        case RawByteCodes.ldc2_w_opcode:
        case RawByteCodes.ldc_w_opcode:
        {
            LDCBNode ldc = (LDCBNode) bNode;
            LDCConstant constant = ldc.getLDCConstant();
            switch (constant.getType())
            {
            case LDCConstant.STRING:
                types.add("java.lang.String");
                break;
            case LDCConstant.CLASS:
                types.add("java.lang.Class");
                break;                
            }
            break;
        }
        default:
            throw new Exception("Unsupported object type for locking");
        }
    }

    public static MethodOrFieldDesc getMethodOrFieldDesc(JavaClass clazz, Method method) {
        return new MethodOrFieldDesc(clazz.getClassName(), method.getName(), method.getSignature());
    }

}
