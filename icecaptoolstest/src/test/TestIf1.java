package test;

import vm.VMTest;

public class TestIf1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	@SuppressWarnings("unused")
	public static boolean test() {
		if (true)
        {
            return false;
        }
		return true;
	}
}
