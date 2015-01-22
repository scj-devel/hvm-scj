package icecaptools.stackanalyser;

import icecaptools.BNode;
import icecaptools.BasicBNode;
import icecaptools.ByteCodeStream;
import icecaptools.ClassfileUtils;
import icecaptools.FieldAccessBNode;
import icecaptools.HackInterfaceMethodCallBNode;
import icecaptools.IcecapIterator;
import icecaptools.JavaArrayClass;
import icecaptools.LDCBNode;
import icecaptools.MethodCallBNode;
import icecaptools.MethodEntryPoints;
import icecaptools.RawByteCodes;
import icecaptools.RawByteCodes.RawBytecode;
import icecaptools.RawByteCodes.Raw_multianewarray;
import icecaptools.RawByteCodes.Raw_wide;
import icecaptools.RuntimeMethodCallBNode;
import icecaptools.compiler.LDCConstant;

import java.util.Iterator;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ReferenceType;

public class ProducerConsumerAnalyser {

    public static void annotate(MethodEntryPoints entryPoints) throws Exception {
        BNode mainEntryPoint = entryPoints.getMainEntryPoint();
        analyseNode(mainEntryPoint, new ProducerConsumerStack());

        IcecapIterator<BNode> handlers = entryPoints.getHandlerEntryPoints();
        while (handlers.hasNext()) {
            ProducerConsumerStack exitStack = new ProducerConsumerStack();
            BNode aloadBNode = new BasicBNode(0, "exceptionHandler", "entryPoint", "");
            byte[] rawBytes = { RawByteCodes.aload_opcode, 0 };
            aloadBNode.setRawBytes(rawBytes);
            exitStack.push(aloadBNode);
            ProducerConsumerNodeInfo aInfo = aloadBNode.getAinfo();
            aInfo.entryStack = new ProducerConsumerStack();
            aInfo.exitStack = exitStack;

            BNode nextHandler = handlers.next();

            analyseNode(nextHandler, exitStack);
        }
    }

    public static void analyseNode(BNode node, ProducerConsumerStack entryStack) throws Exception {
        ProducerConsumerNodeInfo aInfo = node.getAinfo();
        boolean continueAnalysis;

        if (aInfo.entryStack == null) {
            aInfo.entryStack = entryStack;
            continueAnalysis = true;
        } else {
            continueAnalysis = aInfo.entryStack.merge(entryStack);
        }

        ProducerConsumerStack exitStack = handleNode(node, entryStack.copy());

        if (aInfo.exitStack == null) {
            aInfo.exitStack = exitStack;
            continueAnalysis = true;
        } else {
            continueAnalysis |= aInfo.exitStack.merge(exitStack);
        }

        if (continueAnalysis) {
            Iterator<BNode> children = node.getChildren();
            while (children.hasNext()) {
                BNode child = children.next();
                if ((child instanceof RuntimeMethodCallBNode) || (child instanceof HackInterfaceMethodCallBNode)) {
                } else {
                    analyseNode(child, aInfo.exitStack.copy());
                }
            }
        }
    }

    private static ProducerConsumerStack handleNode(BNode node, ProducerConsumerStack stack) throws Exception {
        byte code = node.getOpCode();

        switch (code) {
        case RawByteCodes.nop_opcode:
            break;
        case RawByteCodes.aconst_null_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_m1_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_0_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_1_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_2_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_3_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_4_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iconst_5_opcode:
            stack.push(node);
            break;
        case RawByteCodes.lconst_0_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.lconst_1_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fconst_0_opcode:
            stack.push(node);
            break;
        case RawByteCodes.fconst_1_opcode:
            stack.push(node);
            break;
        case RawByteCodes.fconst_2_opcode:
            stack.push(node);
            break;
        case RawByteCodes.dconst_0_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.dconst_1_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.bipush_opcode:
            stack.push(node);
            break;
        case RawByteCodes.sipush_opcode:
            stack.push(node);
            break;
        case RawByteCodes.ldc_w_opcode:
        case RawByteCodes.ldc2_w_opcode:
        case RawByteCodes.ldc_opcode:
            LDCBNode ldcNode = (LDCBNode) node;
            switch (ldcNode.constant.getType()) {
            case LDCConstant.LONG:
            case LDCConstant.DOUBLE:
                stack.push(node);
            case LDCConstant.STRING:
            case LDCConstant.INTEGER:
            case LDCConstant.FLOAT:
            case LDCConstant.CLASS:
                stack.push(node);
                break;
            }
            break;
        case RawByteCodes.iload_opcode:
            stack.push(node);
            break;
        case RawByteCodes.lload_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fload_opcode:
            stack.push(node);
            break;
        case RawByteCodes.dload_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.aload_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iload_0_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iload_1_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iload_2_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iload_3_opcode:
            stack.push(node);
            break;
        case RawByteCodes.lload_0_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.lload_1_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.lload_2_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.lload_3_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fload_0_opcode:
            stack.push(node);
            break;
        case RawByteCodes.fload_1_opcode:
            stack.push(node);
            break;
        case RawByteCodes.fload_2_opcode:
            stack.push(node);
            break;
        case RawByteCodes.fload_3_opcode:
            stack.push(node);
            break;
        case RawByteCodes.dload_0_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.dload_1_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.dload_2_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.dload_3_opcode:
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.aload_0_opcode:
            stack.push(node);
            break;
        case RawByteCodes.aload_1_opcode:
            stack.push(node);
            break;
        case RawByteCodes.aload_2_opcode:
            stack.push(node);
            break;
        case RawByteCodes.aload_3_opcode:
            stack.push(node);
            break;
        case RawByteCodes.iaload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.laload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.faload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.daload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.aaload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.baload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.caload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.saload_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.istore_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.lstore_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.fstore_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.dstore_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.astore_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.istore_0_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.istore_1_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.istore_2_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.istore_3_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.lstore_0_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.lstore_1_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.lstore_2_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.lstore_3_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.fstore_0_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.fstore_1_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.fstore_2_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.fstore_3_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.dstore_0_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.dstore_1_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.dstore_2_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.dstore_3_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.astore_0_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.astore_1_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.astore_2_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.astore_3_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.iastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.lastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.fastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.dastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.aastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.bastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.castore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.sastore_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.pop_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.pop2_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.dup_opcode:
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.dup_x1_opcode: {
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
        }
            break;
        case RawByteCodes.dup_x2_opcode: {
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
        }
            break;
        case RawByteCodes.dup2_opcode: {
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
        }
            break;
        case RawByteCodes.dup2_x1_opcode: {
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
        }
            break;
        case RawByteCodes.dup2_x2_opcode: {
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
            stack.push(node);
        }
            break;
        case RawByteCodes.swap_opcode: {
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
        }
            break;
        case RawByteCodes.iadd_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.ladd_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fadd_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.dadd_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.isub_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lsub_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fsub_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.dsub_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.imul_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lmul_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fmul_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.dmul_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.idiv_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.ldiv_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fdiv_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.ddiv_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.irem_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lrem_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.frem_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.drem_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.ineg_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lneg_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.fneg_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.dneg_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.ishl_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lshl_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.ishr_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lshr_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.iushr_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lushr_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.iand_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.land_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.ior_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.ixor_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lor_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.lxor_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.iinc_opcode:
            break;
        case RawByteCodes.i2l_opcode:
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.i2f_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.i2d_opcode:
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.l2i_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.l2f_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.l2d_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.f2i_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.f2l_opcode:
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.f2d_opcode:
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.d2i_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.d2l_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            stack.push(node);
            break;
        case RawByteCodes.d2f_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.i2b_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.i2c_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.i2s_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.lcmp_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.fcmpl_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.fcmpg_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.dcmpl_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.dcmpg_opcode:
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.ifeq_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.ifne_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.iflt_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.ifge_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.ifgt_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.ifle_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.if_icmpeq_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_icmpne_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_icmplt_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_icmpge_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_icmpgt_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_icmple_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_acmpeq_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.if_acmpne_opcode:
            stack.pop(node);
            stack.pop(node);
            break;
        case RawByteCodes.goto_opcode:
            break;
        case RawByteCodes.jsr_opcode:
            break;
        case RawByteCodes.ret_opcode:
            break;
        case RawByteCodes.tableswitch_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.lookupswitch_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.lreturn_opcode:
        case RawByteCodes.dreturn_opcode:
            stack.pop(node);
        case RawByteCodes.ireturn_opcode:
        case RawByteCodes.freturn_opcode:
        case RawByteCodes.areturn_opcode:
            stack.pop(node);
        case RawByteCodes.return_opcode:
            break;
        case RawByteCodes.getstatic_opcode: {
            FieldAccessBNode fieldAccess = (FieldAccessBNode) node;
            handlePutGetField(stack, fieldAccess);
            break;
        }
        case RawByteCodes.putstatic_opcode: {
            FieldAccessBNode fieldAccess = (FieldAccessBNode) node;
            handlePutGetField(stack, fieldAccess);
            break;
        }
        case RawByteCodes.getfield_opcode: {
            FieldAccessBNode fieldAccess = (FieldAccessBNode) node;
            handlePutGetField(stack, fieldAccess);
            break;
        }
        case RawByteCodes.putfield_opcode: {
            FieldAccessBNode fieldAccess = (FieldAccessBNode) node;
            handlePutGetField(stack, fieldAccess);
            break;
        }
        case RawByteCodes.invokedynamic_opcode: {
            stack.push(node);
            break;
        }
        case RawByteCodes.invokevirtual_opcode: {
            MethodCallBNode methodCall = (MethodCallBNode) node;
            handleInvoke(stack, methodCall, false);
            break;
        }
        case RawByteCodes.invokespecial_opcode: {
            MethodCallBNode methodCall = (MethodCallBNode) node;
            handleInvoke(stack, methodCall, false);
            break;
        }
        case RawByteCodes.invokestatic_opcode: {
            MethodCallBNode methodCall = (MethodCallBNode) node;
            handleInvoke(stack, methodCall, false);
            break;
        }
        case RawByteCodes.invokeinterface_opcode: {
            MethodCallBNode methodCall = (MethodCallBNode) node;

            handleInvoke(stack, methodCall, true);
            break;
        }
        case RawByteCodes.new_opcode: {
            stack.push(node);
            break;
        }
        case RawByteCodes.anewarray_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.newarray_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.arraylength_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.athrow_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.checkcast_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.instanceof_opcode:
            stack.pop(node);
            stack.push(node);
            break;
        case RawByteCodes.monitorenter_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.monitorexit_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.wide_opcode: {
            Raw_wide wide = new RawByteCodes.Raw_wide();

            ByteCodeStream stream = new ByteCodeStream();
            stream.init(node.getRawBytes());

            wide.read(code, stream);

            switch (wide.actualOpcode) {
            case RawByteCodes.iload_opcode:
            case RawByteCodes.fload_opcode:
                stack.push(node);
                break;
            case RawByteCodes.aload_opcode:
                stack.push(node);
                break;
            case RawByteCodes.lload_opcode:
            case RawByteCodes.dload_opcode:
                stack.push(node);
                stack.push(node);
                break;
            case RawByteCodes.istore_opcode:
            case RawByteCodes.fstore_opcode:
                stack.pop(node);
                stack.pop(node);
                break;
            case RawByteCodes.astore_opcode:
                stack.pop(node);
                break;
            case RawByteCodes.lstore_opcode:
            case RawByteCodes.dstore_opcode:
                stack.pop(node);
                stack.pop(node);
                break;
            case RawByteCodes.ret_opcode:
            case RawByteCodes.iinc_opcode:
            }
            break;
        }
        case RawByteCodes.multianewarray_opcode: {
            RawBytecode currentRawBytecode;
            currentRawBytecode = new RawByteCodes.Raw_multianewarray();
            ByteCodeStream stream = new ByteCodeStream();
            stream.init(node.getRawBytes());

            stream.get();

            currentRawBytecode.read(code, stream);

            Raw_multianewarray multi = (Raw_multianewarray) currentRawBytecode;
            int dimensions = multi.count;
            while (dimensions > 0) {
                stack.pop(node);
                dimensions--;
            }
            stack.push(node);
        }
            break;
        case RawByteCodes.ifnull_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.ifnonnull_opcode:
            stack.pop(node);
            break;
        case RawByteCodes.goto_w_opcode:
            break;
        case RawByteCodes.jsr_w_opcode:
            stack.push(node);
            break;
        default:
            throw new Exception("Unknown byte code [" + code + "]");
        }
        return stack;
    }

    private static void handleInvoke(ProducerConsumerStack stack, MethodCallBNode methodCall, boolean isInterfaceInvocation) throws Exception {
        String class_name;
        String className, methodName, methodSig;

        className = methodCall.getClassName();
        methodName = methodCall.getMethodName();
        methodSig = methodCall.getMethodSig();

        if (JavaArrayClass.isArrayClass(className) && methodName.getBytes().equals("clone") && methodSig.getBytes().equals("()Ljava/lang/Object;")) {
            class_name = "java.lang.Object";
        } else {
            class_name = className;
        }

        JavaClass clazz = Repository.lookupClass(class_name);

        Method method;

        if (isInterfaceInvocation) {
            method = ClassfileUtils.findDeclaringInterface(clazz, methodName, methodSig).getMethod();
        } else {
            method = ClassfileUtils.findMethodInClassHierarchy(clazz, methodName, methodSig).getMethod();
        }

        popArgs(stack, method, methodCall);
        if (!(methodCall.getOpCode() == RawByteCodes.invokestatic_opcode)) {
            stack.pop(methodCall);
        }
        pushReturnValue(stack, method, methodCall);
    }

    private static void popArgs(ProducerConsumerStack stack, Method method, MethodCallBNode methodCall) throws Exception {
        org.apache.bcel.generic.Type[] args = method.getArgumentTypes();
        for (int i = args.length; i > 0; i--) {
            org.apache.bcel.generic.Type currentType = args[i - 1];
            pushPopType(stack, currentType, true, methodCall);
        }
        methodCall.setNumArgs(args.length);
    }

    private static void pushPopType(ProducerConsumerStack stack, org.apache.bcel.generic.Type currentType, boolean pop, MethodCallBNode methodCall) throws Exception {
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
                    stack.pop(methodCall);
                } else {
                    stack.push(methodCall);
                }
            } else {
                if (pop) {
                    stack.pop(methodCall);
                } else {
                    stack.push(methodCall);
                }
            }
            size--;
        }
    }

    private static void pushReturnValue(ProducerConsumerStack stack, Method method, MethodCallBNode methodCall) throws Exception {
        org.apache.bcel.generic.Type returnType = method.getReturnType();
        if (returnType.getSize() > 0) {
            pushPopType(stack, returnType, false, methodCall);
        }
    }

    private static void handlePutGetField(ProducerConsumerStack stack, FieldAccessBNode node) throws Exception {
        Field field = StackReferencesAnalyser.findField(node.getClassName(), node.getFieldName(), node.getSignature());
        boolean isRef = StackReferencesAnalyser.fieldIsRef(field);

        int size = field.getType().getSize();

        node.setSize(size);

        if (size == 0) {
            throw new Exception("Zero size field");
        }

        if (node.getOpCode() == RawByteCodes.getfield_opcode) {
            stack.pop(node);
        }

        while (size > 0) {
            if (isRef) {
                if ((node.getOpCode() == RawByteCodes.putfield_opcode) || ((node.getOpCode() == RawByteCodes.putstatic_opcode))) {
                    stack.pop(node);
                } else if ((node.getOpCode() == RawByteCodes.getfield_opcode) || ((node.getOpCode() == RawByteCodes.getstatic_opcode))) {
                    stack.push(node);
                }
            } else {
                if ((node.getOpCode() == RawByteCodes.putfield_opcode) || ((node.getOpCode() == RawByteCodes.putstatic_opcode))) {
                    stack.pop(node);
                } else if ((node.getOpCode() == RawByteCodes.getfield_opcode) || ((node.getOpCode() == RawByteCodes.getstatic_opcode))) {
                    stack.push(node);
                }
            }
            size--;
        }

        if (node.getOpCode() == RawByteCodes.putfield_opcode) {
            stack.pop(node);
        }
    }
}
