package test;

import vm.VMTest;

public class TestFor2 {
    public static void main(String[] args) {
       VMTest.markResult(test());
    }

	public static boolean test() {
		for (int i = 42; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j==2) {
                    return false;
                }
            }
        }
		return true;
	}
}
