package test;

import vm.VMTest;

public class TestException1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		int result = 0;
        try {
            result++;
            if (result == 1) {
                throw new Exception();
            }
            result++;
        } catch (Exception e) {
            result++;
        }
        if (result == 2) {
            return false;
        }
        return true;
	}

}
