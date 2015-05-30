package test;

import vm.VMTest;

public class ANTTestArray1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    public static boolean test() {
        byte array[] = new byte[5];

        return testArrayAccess(array);
    }

    public static boolean testArrayAccess(byte[] array) {
        if (array != null) {
            if (array.length > 0) {
                return (array[0] != 0);
            }
        }
        return true;
    }
}
