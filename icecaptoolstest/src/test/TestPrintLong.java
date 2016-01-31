package test;

import vm.VMTest;

public class TestPrintLong {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    public static boolean test() {
        long l = 1234567887654321L;
        String str = "" + l;
        if (str.equals("1234567887654321"))
        {
            devices.Console.print(l);
            return false;
        }
        return true;
    }
}
