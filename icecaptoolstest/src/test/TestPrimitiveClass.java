package test;

import vm.VMTest;

public class TestPrimitiveClass {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		Class<Integer> intClass = int.class;
        if (intClass == int.class)
        {
            return false;
        }
        return true;
	}
}
