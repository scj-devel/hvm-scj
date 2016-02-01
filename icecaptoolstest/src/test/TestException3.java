package test;

import vm.VMTest;

public class TestException3 {

    private static class TestException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public TestException(String string) {
            super(string);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		try {
            foo(10, 100L, false);
        } catch (TestException ex) {
            String msg = ex.getMessage();
            if (msg == "Hej") {
                return false;
            }
        }
        return true;
	}

    public static void foo(int i, long l, boolean b) throws TestException {
        bar("Hej", (short) 42);

    }

    public static void bar(String string, short s) throws TestException {
        if (s == 42) {
            throw new TestException(string);
        }
    }
}
