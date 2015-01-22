package icecaptools;

import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class RawByteCodes {

    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_invokeInterface = 9;

    public static final byte nop_opcode = 0x00;
    public static final byte aconst_null_opcode = 0x01;
    public static final byte iconst_m1_opcode = 0x02;
    public static final byte iconst_0_opcode = 0x03;
    public static final byte iconst_1_opcode = 0x04;
    public static final byte iconst_2_opcode = 0x05;
    public static final byte iconst_3_opcode = 0x06;
    public static final byte iconst_4_opcode = 0x07;
    public static final byte iconst_5_opcode = 0x08;
    public static final byte lconst_0_opcode = 0x09;
    public static final byte lconst_1_opcode = 0x0A;
    public static final byte fconst_0_opcode = 0x0B;
    public static final byte fconst_1_opcode = 0x0C;
    public static final byte fconst_2_opcode = 0x0D;
    public static final byte dconst_0_opcode = 0x0E;
    public static final byte dconst_1_opcode = 0x0F;
    public static final byte bipush_opcode = 0x10;
    public static final byte sipush_opcode = 0x11;
    public static final byte ldc_opcode = 0x12;
    public static final byte ldc_w_opcode = 0x13;
    public static final byte ldc2_w_opcode = 0x14;
    public static final byte iload_opcode = 0x15;
    public static final byte lload_opcode = 0x16;
    public static final byte fload_opcode = 0x17;
    public static final byte dload_opcode = 0x18;
    public static final byte aload_opcode = 0x19;
    public static final byte iload_0_opcode = 0x1A;
    public static final byte iload_1_opcode = 0x1B;
    public static final byte iload_2_opcode = 0x1C;
    public static final byte iload_3_opcode = 0x1D;
    public static final byte lload_0_opcode = 0x1E;
    public static final byte lload_1_opcode = 0x1F;
    public static final byte lload_2_opcode = 0x20;
    public static final byte lload_3_opcode = 0x21;
    public static final byte fload_0_opcode = 0x22;
    public static final byte fload_1_opcode = 0x23;
    public static final byte fload_2_opcode = 0x24;
    public static final byte fload_3_opcode = 0x25;
    public static final byte dload_0_opcode = 0x26;
    public static final byte dload_1_opcode = 0x27;
    public static final byte dload_2_opcode = 0x28;
    public static final byte dload_3_opcode = 0x29;
    public static final byte aload_0_opcode = 0x2A;
    public static final byte aload_1_opcode = 0x2B;
    public static final byte aload_2_opcode = 0x2C;
    public static final byte aload_3_opcode = 0x2D;
    public static final byte iaload_opcode = 0x2E;
    public static final byte laload_opcode = 0x2F;
    public static final byte faload_opcode = 0x30;
    public static final byte daload_opcode = 0x31;
    public static final byte aaload_opcode = 0x32;
    public static final byte baload_opcode = 0x33;
    public static final byte caload_opcode = 0x34;
    public static final byte saload_opcode = 0x35;
    public static final byte istore_opcode = 0x36;
    public static final byte lstore_opcode = 0x37;
    public static final byte fstore_opcode = 0x38;
    public static final byte dstore_opcode = 0x39;
    public static final byte astore_opcode = 0x3A;
    public static final byte istore_0_opcode = 0x3B;
    public static final byte istore_1_opcode = 0x3C;
    public static final byte istore_2_opcode = 0x3D;
    public static final byte istore_3_opcode = 0x3E;
    public static final byte lstore_0_opcode = 0x3F;
    public static final byte lstore_1_opcode = 0x40;
    public static final byte lstore_2_opcode = 0x41;
    public static final byte lstore_3_opcode = 0x42;
    public static final byte fstore_0_opcode = 0x43;
    public static final byte fstore_1_opcode = 0x44;
    public static final byte fstore_2_opcode = 0x45;
    public static final byte fstore_3_opcode = 0x46;
    public static final byte dstore_0_opcode = 0x47;
    public static final byte dstore_1_opcode = 0x48;
    public static final byte dstore_2_opcode = 0x49;
    public static final byte dstore_3_opcode = 0x4A;
    public static final byte astore_0_opcode = 0x4B;
    public static final byte astore_1_opcode = 0x4C;
    public static final byte astore_2_opcode = 0x4D;
    public static final byte astore_3_opcode = 0x4E;
    public static final byte iastore_opcode = 0x4F;
    public static final byte lastore_opcode = 0x50;
    public static final byte fastore_opcode = 0x51;
    public static final byte dastore_opcode = 0x52;
    public static final byte aastore_opcode = 0x53;
    public static final byte bastore_opcode = 0x54;
    public static final byte castore_opcode = 0x55;
    public static final byte sastore_opcode = 0x56;
    public static final byte pop_opcode = 0x57;
    public static final byte pop2_opcode = 0x58;
    public static final byte dup_opcode = 0x59;
    public static final byte dup_x1_opcode = 0x5A;
    public static final byte dup_x2_opcode = 0x5B;
    public static final byte dup2_opcode = 0x5C;
    public static final byte dup2_x1_opcode = 0x5D;
    public static final byte dup2_x2_opcode = 0x5E;
    public static final byte swap_opcode = 0x5F;
    public static final byte iadd_opcode = 0x60;
    public static final byte ladd_opcode = 0x61;
    public static final byte fadd_opcode = 0x62;
    public static final byte dadd_opcode = 0x63;
    public static final byte isub_opcode = 0x64;
    public static final byte lsub_opcode = 0x65;
    public static final byte fsub_opcode = 0x66;
    public static final byte dsub_opcode = 0x67;
    public static final byte imul_opcode = 0x68;
    public static final byte lmul_opcode = 0x69;
    public static final byte fmul_opcode = 0x6A;
    public static final byte dmul_opcode = 0x6B;
    public static final byte idiv_opcode = 0x6C;
    public static final byte ldiv_opcode = 0x6D;
    public static final byte fdiv_opcode = 0x6E;
    public static final byte ddiv_opcode = 0x6F;
    public static final byte irem_opcode = 0x70;
    public static final byte lrem_opcode = 0x71;
    public static final byte frem_opcode = 0x72;
    public static final byte drem_opcode = 0x73;
    public static final byte ineg_opcode = 0x74;
    public static final byte lneg_opcode = 0x75;
    public static final byte fneg_opcode = 0x76;
    public static final byte dneg_opcode = 0x77;
    public static final byte ishl_opcode = 0x78;
    public static final byte lshl_opcode = 0x79;
    public static final byte ishr_opcode = 0x7A;
    public static final byte lshr_opcode = 0x7B;
    public static final byte iushr_opcode = 0x7C;
    public static final byte lushr_opcode = 0x7D;
    public static final byte iand_opcode = 0x7E;
    public static final byte land_opcode = 0x7F;
    public static final byte ior_opcode = (byte) 0x80;
    public static final byte lor_opcode = (byte) 0x81;
    public static final byte ixor_opcode = (byte) 0x82;
    public static final byte lxor_opcode = (byte) 0x83;
    public static final byte iinc_opcode = (byte) 0x84;
    public static final byte i2l_opcode = (byte) 0x85;
    public static final byte i2f_opcode = (byte) 0x86;
    public static final byte i2d_opcode = (byte) 0x87;
    public static final byte l2i_opcode = (byte) 0x88;
    public static final byte l2f_opcode = (byte) 0x89;
    public static final byte l2d_opcode = (byte) 0x8A;
    public static final byte f2i_opcode = (byte) 0x8B;
    public static final byte f2l_opcode = (byte) 0x8C;
    public static final byte f2d_opcode = (byte) 0x8D;
    public static final byte d2i_opcode = (byte) 0x8E;
    public static final byte d2l_opcode = (byte) 0x8F;
    public static final byte d2f_opcode = (byte) 0x90;
    public static final byte i2b_opcode = (byte) 0x91;
    public static final byte i2c_opcode = (byte) 0x92;
    public static final byte i2s_opcode = (byte) 0x93;
    public static final byte lcmp_opcode = (byte) 0x94;
    public static final byte fcmpl_opcode = (byte) 0x95;
    public static final byte fcmpg_opcode = (byte) 0x96;
    public static final byte dcmpl_opcode = (byte) 0x97;
    public static final byte dcmpg_opcode = (byte) 0x98;
    public static final byte ifeq_opcode = (byte) 0x99;
    public static final byte ifne_opcode = (byte) 0x9A;
    public static final byte iflt_opcode = (byte) 0x9B;
    public static final byte ifge_opcode = (byte) 0x9C;
    public static final byte ifgt_opcode = (byte) 0x9D;
    public static final byte ifle_opcode = (byte) 0x9E;
    public static final byte if_icmpeq_opcode = (byte) 0x9F;
    public static final byte if_icmpne_opcode = (byte) 0xA0;
    public static final byte if_icmplt_opcode = (byte) 0xA1;
    public static final byte if_icmpge_opcode = (byte) 0xA2;
    public static final byte if_icmpgt_opcode = (byte) 0xA3;
    public static final byte if_icmple_opcode = (byte) 0xA4;
    public static final byte if_acmpeq_opcode = (byte) 0xA5;
    public static final byte if_acmpne_opcode = (byte) 0xA6;
    public static final byte goto_opcode = (byte) 0xA7;
    public static final byte jsr_opcode = (byte) 0xA8;
    public static final byte ret_opcode = (byte) 0xA9;
    public static final byte tableswitch_opcode = (byte) 0xAA;
    public static final byte lookupswitch_opcode = (byte) 0xAB;
    public static final byte ireturn_opcode = (byte) 0xAC;
    public static final byte lreturn_opcode = (byte) 0xAD;
    public static final byte freturn_opcode = (byte) 0xAE;
    public static final byte dreturn_opcode = (byte) 0xAF;
    public static final byte areturn_opcode = (byte) 0xB0;
    public static final byte return_opcode = (byte) 0xB1;
    public static final byte getstatic_opcode = (byte) 0xB2;
    public static final byte putstatic_opcode = (byte) 0xB3;
    public static final byte getfield_opcode = (byte) 0xB4;
    public static final byte putfield_opcode = (byte) 0xB5;
    public static final byte invokevirtual_opcode = (byte) 0xB6;
    public static final byte invokespecial_opcode = (byte) 0xB7;
    public static final byte invokestatic_opcode = (byte) 0xB8;
    public static final byte invokeinterface_opcode = (byte) 0xB9;
    public static final byte invokedynamic_opcode = (byte) 0xBA;
    public static final byte new_opcode = (byte) 0xBB;
    public static final byte newarray_opcode = (byte) 0xBC;
    public static final byte anewarray_opcode = (byte) 0xBD;
    public static final byte arraylength_opcode = (byte) 0xBE;
    public static final byte athrow_opcode = (byte) 0xBF;
    public static final byte checkcast_opcode = (byte) 0xC0;
    public static final byte instanceof_opcode = (byte) 0xC1;
    public static final byte monitorenter_opcode = (byte) 0xC2;
    public static final byte monitorexit_opcode = (byte) 0xC3;
    public static final byte wide_opcode = (byte) 0xC4;
    public static final byte multianewarray_opcode = (byte) 0xC5;
    public static final byte ifnull_opcode = (byte) 0xC6;
    public static final byte ifnonnull_opcode = (byte) 0xC7;
    public static final byte goto_w_opcode = (byte) 0xC8;
    public static final byte jsr_w_opcode = (byte) 0xC9;
    public static final byte invoke_clone_onarray = (byte) 0xca;
    public static final byte gethwfield_opcode = (byte) 0xcb;
    public static final byte puthwfield_opcode = (byte) 0xcc;
    public static final byte newflasharray_opcode = (byte) 0xcd;

    public static class RawBytecode implements ByteCollector {
        private int opcode;
        private int size;
        private int address;
        private ArrayList<Byte> bytes;

        public RawBytecode() {
            bytes = new ArrayList<Byte>();
        }

        protected void continueReading(ByteCodeStream input) {
            ;
        }

        public void read(int opcode, ByteCodeStream input) {
            this.opcode = opcode;
            bytes.add(new Byte((byte) opcode));
            address = (int) (input.pos() - 1);
            int start = (int) input.pos();
            input.setByteCollector(this);
            continueReading(input);
            int stop = (int) input.pos();
            size = stop - start + 1;
        }

        public int getOpcode() {
            return opcode;
        }

        public int getSize() {
            return size;
        }

        public int getAddress() {
            return address;
        }

        public static int bitLeftShift(long value, int shift) {
            return (int) (value << shift);
        }

        public static int bitwiseOr(long value1, long value2) {
            return (int) (value1 | value2);
        }

        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("");
        }

        @Override
        public void collect(byte c) {
            bytes.add(new Byte(c));
        }

        public byte[] getRawBytes() {
            byte[] bytes = new byte[this.bytes.size()];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = this.bytes.get(i).byteValue();
            }
            return bytes;
        }
    }

    private static class StringIterator implements IcecapIterator<String> {
        private StringTokenizer tokenizer;

        public StringIterator(String init) {
            tokenizer = new StringTokenizer(init, ";");
        }

        @Override
        public boolean hasNext() {
            return tokenizer.hasMoreElements();
        }

        @Override
        public String next() {
            return tokenizer.nextToken();
        }
    }

    public static class RawByteLocalVariableOperation extends RawBytecode {
        public int localVariableIndex;

        protected void continueReading(ByteCodeStream input) {
            localVariableIndex = input.get();
        }
    }

    public static class RawShortLocalVariableOperation extends RawByteLocalVariableOperation {
        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            localVariableIndex = bitwiseOr(bitLeftShift(localVariableIndex & 0xff, 8), input.get() & 0xff);
        }

    }

    public static class RawByteOperation extends RawBytecode {
        int byte1;

        protected void continueReading(ByteCodeStream input) {
            byte1 = input.get();
        }
    }

    public static class RawByteCpIndexOperation extends RawBytecode {
        public int cpIndex;

        protected void continueReading(ByteCodeStream input) {
            cpIndex = input.get();
        }
    }

    public static class RawCpIndexOperation extends RawByteCpIndexOperation {
        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            cpIndex = bitwiseOr(bitLeftShift(cpIndex & 0xff, 8), input.get() & 0xff);
        }
    }

    public static class RawShortBranchOperation extends RawBytecode {
        public long branchoffset;

        protected void continueReading(ByteCodeStream input) {
            short offset;
            int byte1, byte2;

            byte1 = input.get();
            byte2 = input.get();
            offset = (short) bitwiseOr(bitLeftShift(byte1 & 0xff, 8), byte2 & 0xff);
            branchoffset = offset;
        }
    }

    public static class RawWideBranchOperation extends RawShortBranchOperation {
        protected void continueReading(ByteCodeStream input) {
            short low;
            int byte1, byte2;

            super.continueReading(input);

            byte1 = input.get();
            byte2 = input.get();
            low = (short) bitwiseOr(bitLeftShift(byte1 & 0xff, 8), byte2 & 0xff);
            branchoffset = bitwiseOr(bitLeftShift(branchoffset, 16), low & 0xffff);
        }
    }

    public static class RawConstantByteLocalVariableOperation extends RawByteLocalVariableOperation {
        int constant;

        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            constant = input.get();
        }
    }

    public static class RawCountCpIndexOperation extends RawCpIndexOperation {
        public int count;

        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            count = input.get();
            input.get();
        }
    }

    public static class RawSwitch extends RawBytecode {
        public long defaultVal;

        protected long getLong(ByteCodeStream input) {
            long result;
            int byte1;
            int byte2;
            int byte3;
            int byte4;
            byte1 = input.get();
            byte2 = input.get();
            byte3 = input.get();
            byte4 = input.get();
            result = byte4 & 0xff;
            result = bitwiseOr(bitLeftShift(byte3 & 0xff, 8), result);
            result = bitwiseOr(bitLeftShift(byte2 & 0xff, 16), result & 0xffff);
            result = bitwiseOr(bitLeftShift(byte1 & 0xff, 24), result & 0xffffff);

            return result;
        }

        protected void continueReading(ByteCodeStream input) {
            while (!(((input.pos()) % 4) == 0)) {
                input.get();
            }
            defaultVal = getLong(input);
        }
    }

    public static class RawTypeOperation extends RawBytecode {
        public int type;

        protected void continueReading(ByteCodeStream input) {
            type = input.get();
        }
    }

    public static class RawShortOperation extends RawByteOperation {
        short shortVal;

        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            shortVal = (short) bitwiseOr(bitLeftShift(byte1 & 0xff, 8), input.get() & 0xff);
        }

    }

    public static class RawReturnOperation extends RawBytecode {

    }

    public static class Raw_methodEntryByteCode extends RawBytecode {
    }

    public static class Raw_aaload extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }

    }

    public static class Raw_checkcast extends RawCpIndexOperation {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.ClassCastException");
        }
    }

    public static class Raw_aastore extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException;java.lang.ArrayStoreException");
        }

    }

    public static class Raw_aconst_null extends RawBytecode {
    }

    public static class Raw_aload extends RawByteLocalVariableOperation {
    }

    public static class Raw_aload_0 extends RawBytecode {
    }

    public static class Raw_aload_1 extends RawBytecode {
    }

    public static class Raw_aload_2 extends RawBytecode {
    }

    public static class Raw_aload_3 extends RawBytecode {
    }

    public static class Raw_areturn extends RawReturnOperation {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_arraylength extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_astore extends RawByteLocalVariableOperation {
    }

    public static class Raw_astore_0 extends RawBytecode {
    }

    public static class Raw_astore_1 extends RawBytecode {
    }

    public static class Raw_astore_2 extends RawBytecode {
    }

    public static class Raw_astore_3 extends RawBytecode {
    }

    public static class Raw_athrow extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_baload extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_bastore extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_bipush extends RawByteOperation {
    }

    public static class Raw_caload extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_castore extends RawBytecode {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_anewarray extends RawCpIndexOperation {

        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NegativeArraySizeException;java.lang.OutOfMemoryError");
        }
    }

    public static class Raw_d2f extends RawBytecode {
    }

    public static class Raw_d2i extends RawBytecode {
    }

    public static class Raw_d2l extends RawBytecode {
    }

    public static class Raw_dadd extends RawBytecode {
    }

    public static class Raw_daload extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_dastore extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_dcmpg extends RawBytecode {
    }

    public static class Raw_dcmpl extends RawBytecode {
    }

    public static class Raw_dconst extends RawBytecode {
        public long constValue;
    }

    public static class Raw_dconst_0 extends Raw_dconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 0;
        }

    }

    public static class Raw_dconst_1 extends Raw_dconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 1;
        }

    }

    public static class Raw_ddiv extends RawBytecode {
    }

    public static class Raw_dmul extends RawBytecode {
    }

    public static class Raw_dneg extends RawBytecode {
    }

    public static class Raw_drem extends RawBytecode {
    }

    public static class Raw_dsub extends RawBytecode {
    }

    public static class Raw_dreturn extends RawReturnOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_dload extends RawByteLocalVariableOperation {
    }

    public static class Raw_dload_0 extends RawBytecode {
    }

    public static class Raw_dload_1 extends RawBytecode {
    }

    public static class Raw_dload_2 extends RawBytecode {
    }

    public static class Raw_dload_3 extends RawBytecode {
    }

    public static class Raw_dstore extends RawByteLocalVariableOperation {
    }

    public static class Raw_dstore_0 extends RawBytecode {
    }

    public static class Raw_dstore_1 extends RawBytecode {
    }

    public static class Raw_dstore_2 extends RawBytecode {
    }

    public static class Raw_dstore_3 extends RawBytecode {
    }

    public static class Raw_dup extends RawBytecode {
    }

    public static class Raw_dup_x1 extends RawBytecode {
    }

    public static class Raw_dup_x2 extends RawBytecode {
    }

    public static class Raw_dup2 extends RawBytecode {
    }

    public static class Raw_dup2_x1 extends RawBytecode {
    }

    public static class Raw_dup2_x2 extends RawBytecode {
    }

    public static class Raw_f2d extends RawBytecode {
    }

    public static class Raw_f2i extends RawBytecode {
    }

    public static class Raw_f2l extends RawBytecode {
    }

    public static class Raw_fadd extends RawBytecode {
    }

    public static class Raw_faload extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_fastore extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_fcmpg extends RawBytecode {
    }

    public static class Raw_fcmpl extends RawBytecode {
    }

    public static class Raw_fconst extends RawBytecode {
        public long constValue;
    }

    public static class Raw_fconst_0 extends Raw_fconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 0;
        }

    }

    public static class Raw_fconst_1 extends Raw_fconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 1;
        }

    }

    public static class Raw_fconst_2 extends Raw_fconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 2;
        }

    }

    public static class Raw_fdiv extends RawBytecode {
    }

    public static class Raw_fmul extends RawBytecode {
    }

    public static class Raw_fneg extends RawBytecode {
    }

    public static class Raw_frem extends RawBytecode {
    }

    public static class Raw_fsub extends RawBytecode {
    }

    public static class Raw_freturn extends RawReturnOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_fload extends RawByteLocalVariableOperation {
    }

    public static class Raw_fload_0 extends RawBytecode {
    }

    public static class Raw_fload_1 extends RawBytecode {
    }

    public static class Raw_fload_2 extends RawBytecode {
    }

    public static class Raw_fload_3 extends RawBytecode {
    }

    public static class Raw_fstore extends RawByteLocalVariableOperation {
    }

    public static class Raw_fstore_0 extends RawBytecode {
    }

    public static class Raw_fstore_1 extends RawBytecode {
    }

    public static class Raw_fstore_2 extends RawBytecode {
    }

    public static class Raw_fstore_3 extends RawBytecode {
    }

    public static class Raw_getfield extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_getstatic extends RawCpIndexOperation {
    }

    public static class Raw_goto extends RawShortBranchOperation {
    }

    public static class Raw_goto_w extends RawWideBranchOperation {
    }

    public static class Raw_i2b extends RawBytecode {
    }

    public static class Raw_i2c extends RawBytecode {
    }

    public static class Raw_i2d extends RawBytecode {
    }

    public static class Raw_i2f extends RawBytecode {
    }

    public static class Raw_i2l extends RawBytecode {
    }

    public static class Raw_i2s extends RawBytecode {
    }

    public static class Raw_iadd extends RawBytecode {
    }

    public static class Raw_iaload extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_iand extends RawBytecode {
    }

    public static class Raw_iastore extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_iconst extends RawBytecode {
        public long constValue;
    }

    public static class Raw_iconst_m1 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = -1;
        }
    }

    public static class Raw_iconst_0 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 0;
        }
    }

    public static class Raw_iconst_1 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 1;
        }
    }

    public static class Raw_iconst_2 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 2;
        }
    }

    public static class Raw_iconst_3 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 3;
        }
    }

    public static class Raw_iconst_4 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 4;
        }
    }

    public static class Raw_iconst_5 extends Raw_iconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constValue = 5;
        }
    }

    public static class Raw_idiv extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.ArithmeticException");
        }
    }

    public static class Raw_if_acmpeq extends RawShortBranchOperation {
    }

    public static class Raw_if_acmpne extends RawShortBranchOperation {
    }

    public static class Raw_if_icmpeq extends RawShortBranchOperation {
    }

    public static class Raw_if_icmpne extends RawShortBranchOperation {
    }

    public static class Raw_if_icmplt extends RawShortBranchOperation {
    }

    public static class Raw_if_icmpge extends RawShortBranchOperation {
    }

    public static class Raw_if_icmpgt extends RawShortBranchOperation {
    }

    public static class Raw_if_icmple extends RawShortBranchOperation {
    }

    public static class Raw_ifeq extends RawShortBranchOperation {
    }

    public static class Raw_ifne extends RawShortBranchOperation {
    }

    public static class Raw_iflt extends RawShortBranchOperation {
    }

    public static class Raw_ifge extends RawShortBranchOperation {
    }

    public static class Raw_ifgt extends RawShortBranchOperation {
    }

    public static class Raw_ifle extends RawShortBranchOperation {
    }

    public static class Raw_ifnonnull extends RawShortBranchOperation {
    }

    public static class Raw_ifnull extends RawShortBranchOperation {
    }

    public static class Raw_iinc extends RawConstantByteLocalVariableOperation {
    }

    public static class Raw_iload extends RawByteLocalVariableOperation {
    }

    public static class Raw_iload_0 extends RawBytecode {
    }

    public static class Raw_iload_1 extends RawBytecode {
    }

    public static class Raw_iload_2 extends RawBytecode {
    }

    public static class Raw_iload_3 extends RawBytecode {
    }

    public static class Raw_imul extends RawBytecode {
    }

    public static class Raw_ineg extends RawBytecode {
    }

    public static class Raw_instanceof extends RawCpIndexOperation {
    }

    public static class Raw_invokeinterface extends RawCountCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_invokespecial extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_invokestatic extends RawCpIndexOperation {
    }

    public static class Raw_invokevirtual extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_invokedynamic extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_ior extends RawBytecode {
    }

    public static class Raw_irem extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.ArithmeticException");
        }
    }

    public static class Raw_ireturn extends RawReturnOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_ishl extends RawBytecode {
    }

    public static class Raw_ishr extends RawBytecode {
    }

    public static class Raw_istore extends RawByteLocalVariableOperation {
    }

    public static class Raw_istore_0 extends RawBytecode {
    }

    public static class Raw_istore_1 extends RawBytecode {
    }

    public static class Raw_istore_2 extends RawBytecode {
    }

    public static class Raw_istore_3 extends RawBytecode {
    }

    public static class Raw_isub extends RawBytecode {
    }

    public static class Raw_iushr extends RawBytecode {
    }

    public static class Raw_ixor extends RawBytecode {
    }

    public static class Raw_jsr extends RawShortBranchOperation {
    }

    public static class Raw_jsr_w extends RawWideBranchOperation {
    }

    public static class Raw_l2d extends RawBytecode {
    }

    public static class Raw_l2f extends RawBytecode {
    }

    public static class Raw_l2i extends RawBytecode {
    }

    public static class Raw_ladd extends RawBytecode {
    }

    public static class Raw_laload extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_land extends RawBytecode {
    }

    public static class Raw_lastore extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_lcmp extends RawBytecode {
    }

    public static class Raw_lconst extends RawBytecode {
        public long constVal;
    }

    public static class Raw_lconst_0 extends Raw_lconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constVal = 0;
        }

    }

    public static class Raw_lconst_1 extends Raw_lconst {
        @Override
        protected void continueReading(ByteCodeStream input) {
            constVal = 1;
        }

    }

    public static class Raw_ldc extends RawByteCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.OutOfMemoryError");
        }
    }

    public static class Raw_ldc_w extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.OutOfMemoryError");
        }
    }

    public static class Raw_ldc2_w extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.OutOfMemoryError");
        }
    }

    public static class Raw_ldiv extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.ArithmeticException");
        }
    }

    public static class Raw_lload extends RawByteLocalVariableOperation {
    }

    public static class Raw_lload_0 extends RawBytecode {
    }

    public static class Raw_lload_1 extends RawBytecode {
    }

    public static class Raw_lload_2 extends RawBytecode {
    }

    public static class Raw_lload_3 extends RawBytecode {
    }

    public static class Raw_lmul extends RawBytecode {
    }

    public static class Raw_lneg extends RawBytecode {
    }

    public static class Raw_lor extends RawBytecode {
    }

    public static class Raw_lrem extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.ArithmeticException");
        }
    }

    public static class Raw_lreturn extends RawReturnOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_lshl extends RawBytecode {
    }

    public static class Raw_lshr extends RawBytecode {
    }

    public static class Raw_lstore extends RawByteLocalVariableOperation {
    }

    public static class Raw_lstore_0 extends RawBytecode {
    }

    public static class Raw_lstore_1 extends RawBytecode {
    }

    public static class Raw_lstore_2 extends RawBytecode {
    }

    public static class Raw_lstore_3 extends RawBytecode {
    }

    public static class Raw_lsub extends RawBytecode {
    }

    public static class Raw_lushr extends RawBytecode {
    }

    public static class Raw_lxor extends RawBytecode {
    }

    public static class Raw_monitorenter extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_monitorexit extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_multianewarray extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NegativeArraySizeException;java.lang.OutOfMemoryError");
        }

        public int count;

        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            count = input.get();
        }
    }

    public static class Raw_new extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.OutOfMemoryError");
        }
    }

    public static class Raw_newArray extends RawTypeOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NegativeArraySizeException;java.lang.OutOfMemoryError");
        }
    }

    public static class Raw_nop extends RawBytecode {
    }

    public static class Raw_pop extends RawBytecode {
    }

    public static class Raw_pop2 extends RawBytecode {
    }

    public static class Raw_putfield extends RawCpIndexOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException");
        }
    }

    public static class Raw_putstatic extends RawCpIndexOperation {
    }

    public static class Raw_ret extends RawByteLocalVariableOperation {
    }

    public static class Raw_return extends RawReturnOperation {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.IllegalMonitorStateException");
        }
    }

    public static class Raw_saload extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_sastore extends RawBytecode {
        @Override
        public IcecapIterator<String> exceptionsThrown() {
            return new StringIterator("java.lang.NullPointerException;java.lang.ArrayIndexOutOfBoundsException");
        }
    }

    public static class Raw_sipush extends RawShortOperation {
    }

    public static class Raw_swap extends RawBytecode {
    }

    public static class Raw_lookupswitch extends RawSwitch {
        public long npairs;

        public class Pair {
            public int match;
            public int offset;

            public Pair(int match, int offset) {
                this.match = match;
                this.offset = offset;

            }
        };

        public Pair[] pairs;

        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            npairs = getLong(input);
            pairs = new Pair[(int) npairs];
            for (int count = 0; count < npairs; count++) {
                pairs[count] = new Pair((int) getLong(input), (int) getLong(input));
            }
        }
    }

    public static class Raw_tableswitch extends RawSwitch {
        public long getLow() {
            return low;
        }

        public long getHigh() {
            return high;
        }

        long low;
        long high;
        public long[] offsets;

        @Override
        protected void continueReading(ByteCodeStream input) {
            super.continueReading(input);
            low = getLong(input);
            high = getLong(input);
            offsets = new long[(int) (high - low + 1)];

            for (int count = 0; count < (high - low + 1); count++) {
                offsets[count] = getLong(input);
            }
        }
    }

    public static class Raw_wide extends RawBytecode {
        int index;
        public byte actualOpcode;
        int constVal;

        @Override
        protected void continueReading(ByteCodeStream input) {
            byte byte1, byte2;
            actualOpcode = (byte) input.get();
            byte1 = (byte) input.get();
            byte2 = (byte) input.get();
            index = bitwiseOr(bitLeftShift(byte1 & 0xff, 8), byte2 & 0xff);

            if (actualOpcode == iinc_opcode) {
                byte1 = (byte) input.get();
                byte2 = (byte) input.get();
                constVal = bitwiseOr(bitLeftShift(byte1 & 0xff, 8), byte2 & 0xff);
            }
        }
    }
}
