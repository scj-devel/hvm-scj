package test;

public class TestException5 {

    private static class TestRunner {
        private int x;
        
        public TestRunner()
        {
            x = 42;
        }
        
        public void runBare() {
            try {
                runTest();
            } catch (Throwable running) {
                tearDown();
            } 
        }

        private void tearDown() {
            if (x == 42)
            {
                x++;
            }
        }

        private void runTest() throws Throwable {
            throw new Throwable();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestRunner tr = new TestRunner();
        tr.runBare();
        if (tr.x == 43)
        {
            args = null;
        }
    }
}
