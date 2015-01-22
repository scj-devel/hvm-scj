package test;

public class TestException6 {

    private static abstract class TestException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        protected int i;

        public TestException() {
            i = 42;
        }

        abstract public int getI();
    }

    private static class TestTestException extends TestException {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
       

        public int getI() {
            return i;
        }
    }
    
    public static void main(String[] args) {
        if (!test()) {
            args = null;
        }
    }

    private static boolean test() {

        try {
            throw new TestTestException();
        } catch (TestException te) {
            if (te.getI() == 42) {
                return false;
            }
        }
        return true;
    }
}
