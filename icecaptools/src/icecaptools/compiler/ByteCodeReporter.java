package icecaptools.compiler;

import icecaptools.AnalysisObserver;

public class ByteCodeReporter {

    public static String reportUsedByteCodes(AnalysisObserver observer) {
        StringBuffer defines = new StringBuffer();

        for (int i = 0; i < 255; i++) {
            String name = getName(i);
            if (name != null) {
                if (observer.isBytecodeUsed(i)) {
                    defines.append("#define ");
                    defines.append(getName(i).toUpperCase());
                    defines.append("_used ".toUpperCase());
                    defines.append("\n");
                }
            }
        }

        return defines.toString();
    }

    private static String getName(int i) {
        String name = null;

        switch (i) {
        case 0x00:
            name = "nop_opcode";
            break;
        case 0x01:
            name = "aconst_null_opcode";
            break;
        case 0x02:
            name = "iconst_m1_opcode";
            break;
        case 0x03:
            name = "iconst_0_opcode";
            break;
        case 0x04:
            name = "iconst_1_opcode";
            break;
        case 0x05:
            name = "iconst_2_opcode";
            break;
        case 0x06:
            name = "iconst_3_opcode";
            break;
        case 0x07:
            name = "iconst_4_opcode";
            break;
        case 0x08:
            name = "iconst_5_opcode";
            break;
        case 0x09:
            name = "lconst_0_opcode";
            break;
        case 0x0A:
            name = "lconst_1_opcode";
            break;
        case 0x0B:
            name = "fconst_0_opcode";
            break;
        case 0x0C:
            name = "fconst_1_opcode";
            break;
        case 0x0D:
            name = "fconst_2_opcode";
            break;
        case 0x0E:
            name = "dconst_0_opcode";
            break;
        case 0x0F:
            name = "dconst_1_opcode";
            break;
        case 0x10:
            name = "bipush_opcode";
            break;
        case 0x11:
            name = "sipush_opcode";
            break;
        case 0x12:
            name = "ldc_opcode";
            break;
        case 0x13:
            name = "ldc_w_opcode";
            break;
        case 0x14:
            name = "ldc2_w_opcode";
            break;
        case 0x15:
            name = "iload_opcode";
            break;
        case 0x16:
            name = "lload_opcode";
            break;
        case 0x17:
            name = "fload_opcode";
            break;
        case 0x18:
            name = "dload_opcode";
            break;
        case 0x19:
            name = "aload_opcode";
            break;
        case 0x1A:
            name = "iload_0_opcode";
            break;
        case 0x1B:
            name = "iload_1_opcode";
            break;
        case 0x1C:
            name = "iload_2_opcode";
            break;
        case 0x1D:
            name = "iload_3_opcode";
            break;
        case 0x1E:
            name = "lload_0_opcode";
            break;
        case 0x1F:
            name = "lload_1_opcode";
            break;
        case 0x20:
            name = "lload_2_opcode";
            break;
        case 0x21:
            name = "lload_3_opcode";
            break;
        case 0x22:
            name = "fload_0_opcode";
            break;
        case 0x23:
            name = "fload_1_opcode";
            break;
        case 0x24:
            name = "fload_2_opcode";
            break;
        case 0x25:
            name = "fload_3_opcode";
            break;
        case 0x26:
            name = "dload_0_opcode";
            break;
        case 0x27:
            name = "dload_1_opcode";
            break;
        case 0x28:
            name = "dload_2_opcode";
            break;
        case 0x29:
            name = "dload_3_opcode";
            break;
        case 0x2A:
            name = "aload_0_opcode";
            break;
        case 0x2B:
            name = "aload_1_opcode";
            break;
        case 0x2C:
            name = "aload_2_opcode";
            break;
        case 0x2D:
            name = "aload_3_opcode";
            break;
        case 0x2E:
            name = "iaload_opcode";
            break;
        case 0x2F:
            name = "laload_opcode";
            break;
        case 0x30:
            name = "faload_opcode";
            break;
        case 0x31:
            name = "daload_opcode";
            break;
        case 0x32:
            name = "aaload_opcode";
            break;
        case 0x33:
            name = "baload_opcode";
            break;
        case 0x34:
            name = "caload_opcode";
            break;
        case 0x35:
            name = "saload_opcode";
            break;
        case 0x36:
            name = "istore_opcode";
            break;
        case 0x37:
            name = "lstore_opcode";
            break;
        case 0x38:
            name = "fstore_opcode";
            break;
        case 0x39:
            name = "dstore_opcode";
            break;
        case 0x3A:
            name = "astore_opcode";
            break;
        case 0x3B:
            name = "istore_0_opcode";
            break;
        case 0x3C:
            name = "istore_1_opcode";
            break;
        case 0x3D:
            name = "istore_2_opcode";
            break;
        case 0x3E:
            name = "istore_3_opcode";
            break;
        case 0x3F:
            name = "lstore_0_opcode";
            break;
        case 0x40:
            name = "lstore_1_opcode";
            break;
        case 0x41:
            name = "lstore_2_opcode";
            break;
        case 0x42:
            name = "lstore_3_opcode";
            break;
        case 0x43:
            name = "fstore_0_opcode";
            break;
        case 0x44:
            name = "fstore_1_opcode";
            break;
        case 0x45:
            name = "fstore_2_opcode";
            break;
        case 0x46:
            name = "fstore_3_opcode";
            break;
        case 0x47:
            name = "dstore_0_opcode";
            break;
        case 0x48:
            name = "dstore_1_opcode";
            break;
        case 0x49:
            name = "dstore_2_opcode";
            break;
        case 0x4A:
            name = "dstore_3_opcode";
            break;
        case 0x4B:
            name = "astore_0_opcode";
            break;
        case 0x4C:
            name = "astore_1_opcode";
            break;
        case 0x4D:
            name = "astore_2_opcode";
            break;
        case 0x4E:
            name = "astore_3_opcode";
            break;
        case 0x4F:
            name = "iastore_opcode";
            break;
        case 0x50:
            name = "lastore_opcode";
            break;
        case 0x51:
            name = "fastore_opcode";
            break;
        case 0x52:
            name = "dastore_opcode";
            break;
        case 0x53:
            name = "aastore_opcode";
            break;
        case 0x54:
            name = "bastore_opcode";
            break;
        case 0x55:
            name = "castore_opcode";
            break;
        case 0x56:
            name = "sastore_opcode";
            break;
        case 0x57:
            name = "pop_opcode";
            break;
        case 0x58:
            name = "pop2_opcode";
            break;
        case 0x59:
            name = "dup_opcode";
            break;
        case 0x5A:
            name = "dup_x1_opcode";
            break;
        case 0x5B:
            name = "dup_x2_opcode";
            break;
        case 0x5C:
            name = "dup2_opcode";
            break;
        case 0x5D:
            name = "dup2_x1_opcode";
            break;
        case 0x5E:
            name = "dup2_x2_opcode";
            break;
        case 0x5F:
            name = "swap_opcode";
            break;
        case 0x60:
            name = "iadd_opcode";
            break;
        case 0x61:
            name = "ladd_opcode";
            break;
        case 0x62:
            name = "fadd_opcode";
            break;
        case 0x63:
            name = "dadd_opcode";
            break;
        case 0x64:
            name = "isub_opcode";
            break;
        case 0x65:
            name = "lsub_opcode";
            break;
        case 0x66:
            name = "fsub_opcode";
            break;
        case 0x67:
            name = "dsub_opcode";
            break;
        case 0x68:
            name = "imul_opcode";
            break;
        case 0x69:
            name = "lmul_opcode";
            break;
        case 0x6A:
            name = "fmul_opcode";
            break;
        case 0x6B:
            name = "dmul_opcode";
            break;
        case 0x6C:
            name = "idiv_opcode";
            break;
        case 0x6D:
            name = "ldiv_opcode";
            break;
        case 0x6E:
            name = "fdiv_opcode";
            break;
        case 0x6F:
            name = "ddiv_opcode";
            break;
        case 0x70:
            name = "irem_opcode";
            break;
        case 0x71:
            name = "lrem_opcode";
            break;
        case 0x72:
            name = "frem_opcode";
            break;
        case 0x73:
            name = "drem_opcode";
            break;
        case 0x74:
            name = "ineg_opcode";
            break;
        case 0x75:
            name = "lneg_opcode";
            break;
        case 0x76:
            name = "fneg_opcode";
            break;
        case 0x77:
            name = "dneg_opcode";
            break;
        case 0x78:
            name = "ishl_opcode";
            break;
        case 0x79:
            name = "lshl_opcode";
            break;
        case 0x7A:
            name = "ishr_opcode";
            break;
        case 0x7B:
            name = "lshr_opcode";
            break;
        case 0x7C:
            name = "iushr_opcode";
            break;
        case 0x7D:
            name = "lushr_opcode";
            break;
        case 0x7E:
            name = "iand_opcode";
            break;
        case 0x7F:
            name = "land_opcode";
            break;
        case 0x80:
            name = "ior_opcode";
            break;
        case 0x81:
            name = "lor_opcode";
            break;
        case 0x82:
            name = "ixor_opcode";
            break;
        case 0x83:
            name = "lxor_opcode";
            break;
        case 0x84:
            name = "iinc_opcode";
            break;
        case 0x85:
            name = "i2l_opcode";
            break;
        case 0x86:
            name = "i2f_opcode";
            break;
        case 0x87:
            name = "i2d_opcode";
            break;
        case 0x88:
            name = "l2i_opcode";
            break;
        case 0x89:
            name = "l2f_opcode";
            break;
        case 0x8A:
            name = "l2d_opcode";
            break;
        case 0x8B:
            name = "f2i_opcode";
            break;
        case 0x8C:
            name = "f2l_opcode";
            break;
        case 0x8D:
            name = "f2d_opcode";
            break;
        case 0x8E:
            name = "d2i_opcode";
            break;
        case 0x8F:
            name = "d2l_opcode";
            break;
        case 0x90:
            name = "d2f_opcode";
            break;
        case 0x91:
            name = "i2b_opcode";
            break;
        case 0x92:
            name = "i2c_opcode";
            break;
        case 0x93:
            name = "i2s_opcode";
            break;
        case 0x94:
            name = "lcmp_opcode";
            break;
        case 0x95:
            name = "fcmpl_opcode";
            break;
        case 0x96:
            name = "fcmpg_opcode";
            break;
        case 0x97:
            name = "dcmpl_opcode";
            break;
        case 0x98:
            name = "dcmpg_opcode";
            break;
        case 0x99:
            name = "ifeq_opcode";
            break;
        case 0x9A:
            name = "ifne_opcode";
            break;
        case 0x9B:
            name = "iflt_opcode";
            break;
        case 0x9C:
            name = "ifge_opcode";
            break;
        case 0x9D:
            name = "ifgt_opcode";
            break;
        case 0x9E:
            name = "ifle_opcode";
            break;
        case 0x9F:
            name = "if_icmpeq_opcode";
            break;
        case 0xA0:
            name = "if_icmpne_opcode";
            break;
        case 0xA1:
            name = "if_icmplt_opcode";
            break;
        case 0xA2:
            name = "if_icmpge_opcode";
            break;
        case 0xA3:
            name = "if_icmpgt_opcode";
            break;
        case 0xA4:
            name = "if_icmple_opcode";
            break;
        case 0xA5:
            name = "if_acmpeq_opcode";
            break;
        case 0xA6:
            name = "if_acmpne_opcode";
            break;
        case 0xA7:
            name = "goto_opcode";
            break;
        case 0xA8:
            name = "jsr_opcode";
            break;
        case 0xA9:
            name = "ret_opcode";
            break;
        case 0xAA:
            name = "tableswitch_opcode";
            break;
        case 0xAB:
            name = "lookupswitch_opcode";
            break;
        case 0xAC:
            name = "ireturn_opcode";
            break;
        case 0xAD:
            name = "lreturn_opcode";
            break;
        case 0xAE:
            name = "freturn_opcode";
            break;
        case 0xAF:
            name = "dreturn_opcode";
            break;
        case 0xB0:
            name = "areturn_opcode";
            break;
        case 0xB1:
            name = "return_opcode";
            break;
        case 0xB2:
            name = "getstatic_opcode";
            break;
        case 0xB3:
            name = "putstatic_opcode";
            break;
        case 0xB4:
            name = "getfield_opcode";
            break;
        case 0xB5:
            name = "putfield_opcode";
            break;
        case 0xB6:
            name = "invokevirtual_opcode";
            break;
        case 0xB7:
            name = "invokespecial_opcode";
            break;
        case 0xB8:
            name = "invokestatic_opcode";
            break;
        case 0xB9:
            name = "invokeinterface_opcode";
            break;
        case 0xBB:
            name = "new_opcode";
            break;
        case 0xBC:
            name = "newarray_opcode";
            break;
        case 0xBD:
            name = "anewarray_opcode";
            break;
        case 0xBE:
            name = "arraylength_opcode";
            break;
        case 0xBF:
            name = "athrow_opcode";
            break;
        case 0xC0:
            name = "checkcast_opcode";
            break;
        case 0xC1:
            name = "instanceof_opcode";
            break;
        case 0xC2:
            name = "monitorenter_opcode";
            break;
        case 0xC3:
            name = "monitorexit_opcode";
            break;
        case 0xC4:
            name = "wide_opcode";
            break;
        case 0xC5:
            name = "multianewarray_opcode";
            break;
        case 0xC6:
            name = "ifnull_opcode";
            break;
        case 0xC7:
            name = "ifnonnull_opcode";
            break;
        case 0xC8:
            name = "goto_w_opcode";
            break;
        case 0xC9:
            name = "jsr_w_opcode";
            break;
        case 0xca:
            name = "invoke_clone_onarray";
            break;
        case 0xcb:
            name = "gethwfield_opcode";
            break;
        case 0xcc:
            name = "puthwfield_opcode";
            break;
        case 0xcd:
            name = "newflasharray_opcode";
            break;
        }
        return name;
    }
}
