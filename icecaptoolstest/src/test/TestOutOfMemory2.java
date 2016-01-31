package test;

import vm.VMTest;

public class TestOutOfMemory2 {

    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

    @SuppressWarnings("unused")
	public static boolean test() {
		try {
            int i = 0;
            while (true) {
                int[] array = new int[i];
                i++;
            }
        } catch (OutOfMemoryError error) {
            return false;
        }
	}
}
