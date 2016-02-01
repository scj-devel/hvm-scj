package test;

import vm.VMTest;

public class TestSimple {

    public static void main(String args[])
    {
        VMTest.markResult(test(args) != null);
    }

	public static String[] test(String args[]) {
		return null;
	}
}
