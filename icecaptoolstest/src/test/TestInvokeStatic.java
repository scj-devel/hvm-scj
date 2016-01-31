package test;

import vm.VMTest;

public class TestInvokeStatic {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		int x = staticMethod(10, 32);
        
        if (x == 42)
        {
            return false;
        }
        return true;
	}

    private static int staticMethod(int i, int j) {
        return i + j;
    }

}
