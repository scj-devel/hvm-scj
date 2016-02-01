package test;

import vm.VMTest;

public class TestWhile1 {

    public static void main(String args[]) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		int count = -5;
        int result = 10;
        while (count < 5) {
            result--;
            count++;
        }
        if (result == 0) {
            return false;
        }
        return true;
	}
}
