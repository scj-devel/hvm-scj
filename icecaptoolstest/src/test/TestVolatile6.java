package test;

import vm.VMTest;

public class TestVolatile6 {

    public static final int NOACCESS = -1;

    public static final int REG1 = 1;
    public static final int REG2 = 2;
    public static final int REG3 = 15;
    public static final int REG4 = 3;
    public static final int REG5 = 14;
    public static final int REG6 = 16;
    public static final int REG7 = 17;
    public static final int REG8 = 18;
    public static final int REG9 = 4;
    public static final int REG10 = 5;
    public static final int REG11 = 6;
    public static final int REG12 = 7;
    public static final int REG13 = 8;
    public static final int REG14 = 9;
    public static final int REG15 = 10;
    public static final int REG16 = 11;
    public static final int REG17 = 12;
    public static final int REG512 = 13;

    private static class ConstantData {
        public int[] fieldUserRegisterMap = { REG1, REG2, NOACCESS, REG4, REG5, NOACCESS, NOACCESS, NOACCESS, REG9, REG10, REG11, REG12, REG13, REG14, REG15, REG16, REG17, REG512, REG1, REG2, NOACCESS, REG4, REG5, NOACCESS, NOACCESS, NOACCESS, REG9, REG10, REG11, REG12, REG13, REG14, REG15, REG16, REG17, REG512 };

        public int[] configuratorRegisterMap = { REG1, REG2, REG3, REG4, REG5, REG6, REG7, REG8, REG9, REG10, REG11, REG12, REG13, REG14, REG15, REG16, REG17, REG512, REG1, REG2, REG3, REG4, REG5, REG6, REG7, REG8, REG9, REG10, REG11, REG12, REG13, REG14, REG15, REG16, REG17, REG512 };

        public ConstantData() {

        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ConstantData cdata = new ConstantData();
        // devices.System.lockROM();

        if (cdata.fieldUserRegisterMap.length == 36) {
            if (cdata.configuratorRegisterMap.length == 36) {
                int sum = count(cdata.fieldUserRegisterMap);
                sum += count(cdata.configuratorRegisterMap);
                if (sum == 544)
                {
                	VMTest.markResult(false);
                }
            }
        }
    }

    public static int count(int[] fieldUserRegisterMap) {
        int sum = 0;
        for (int i : fieldUserRegisterMap) {
            sum += i;
        }
        return sum;
    }
}
