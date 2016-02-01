package test;

import vm.VMTest;

public class TestDoWhile {
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		int i = 1;

        do {
            i = i << 2;
        } while (i < 255);

        if (i == 256) {
            return false;
        }
        return true;
	}
}
