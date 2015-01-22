package test;

public class TestStaticFieldTypes {
    static class StaticTypes{
        private static boolean boolVal = false;
        private static byte bVal = -1;
        private static short sVal = -1;
        private static int iVal = -1;
        private static long lVal = 1122334455667788L;
        public static boolean isBoolVal() {
            return boolVal;
        }
        public static void setBoolVal(boolean boolVal) {
            StaticTypes.boolVal = boolVal;
        }
        public static byte getbVal() {
            return bVal;
        }
        public static void setbVal(byte bVal) {
            StaticTypes.bVal = bVal;
        }
        public static short getsVal() {
            return sVal;
        }
        public static void setsVal(short sVal) {
            StaticTypes.sVal = sVal;
        }
        public static int getiVal() {
            return iVal;
        }
        public static void setiVal(int iVal) {
            StaticTypes.iVal = iVal;
        }
        public static long getlVal() {
            return lVal;
        }
        public static void setlVal(long lVal) {
            StaticTypes.lVal = lVal;
        }
    }

    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		StaticTypes.setBoolVal(true);
        StaticTypes.setbVal((byte)255);
        StaticTypes.setsVal((short)65535);
        StaticTypes.setiVal(9000);
        StaticTypes.setlVal((long)0xCAFEBABECAFED00DL);

        if(StaticTypes.isBoolVal() &&
           StaticTypes.getbVal() == (byte)255 &&
           StaticTypes.getsVal() == (short)65535 &&
           StaticTypes.getiVal() == 9000 &&
           StaticTypes.getlVal() == (long)0xCAFEBABECAFED00DL
            )
        {
            return null;
        }
        return args;
	}
}
