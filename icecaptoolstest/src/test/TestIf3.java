package test;

import vm.VMTest;

public class TestIf3 {


    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		int x = 42;
        int y = 40;
        if (y == x)
        {
            ;
        }
        else
        {
            return false;
        }
        return true;
	}
}
