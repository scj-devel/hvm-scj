package test;

import icecaptools.IcecapCompileMe;
import unitTest.TestCase;
import unitTest.TestResult;

public class MathTest extends TestCase {
    int value1;
    int value2;

    public MathTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        devices.Console.println("setUp");
        value1 = 3;
        value2 = 2;
    }

    protected void tearDown() throws Exception {
        devices.Console.println("tearDown");
    }

    @IcecapCompileMe
    public void testAdd() {
        devices.Console.println("testAdd");
        int result = value1 + value2;
        assertTrue(result == 5);
    }

    public void testSub() {
        devices.Console.println("testSub");
        int result = value1 - value2;
        assertTrue(result == 1);
    }

    public static void main(String[] args) {
        TestCase testCase = new MathTest("math") {
            public void runTest() {
                testAdd();
                testSub();
            }
        };

        TestResult result = new TestResult();
        testCase.run(result);

        devices.Console.println("Math Test was successful: " + result.wasSuccessful());
        devices.Console.println("Math Test failures:       " + result.failureCount());
        devices.Console.println("Math Test errors:         " + result.errorCount());
        if (result.wasSuccessful()) {
            args = null;
        }
    }
}
