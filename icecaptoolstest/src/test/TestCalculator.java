package test;

import java.util.Enumeration;

import unitTest.TestCase;
import unitTest.TestFailure;
import unitTest.TestResult;
import unitTest.TestSuite;

public class TestCalculator extends TestCase {
    private static class Calculator {
        int intValue;
        long longValue;
        float floatValue;
        double doubleValue;
        short shortValue;
        byte byteValue;

        @SuppressWarnings("unused")
        char ch;

        boolean bool;

        String str;

        public Calculator() {
        }

        public void addInt(int value) {
            intValue += value;
        }

        public void addLong(long value) {
            longValue += value;
        }

        public void addShort(short value) {
            shortValue += value;
        }

        public void addByte(byte value) {
            byteValue += value;
        }

        @SuppressWarnings("unused")
        public void setBoolean(boolean value) {
            bool = value;
        }

        public void addFloat(float value) {
            floatValue += value;
        }

        public void addDouble(double value) {
            doubleValue += value;
        }
    }

    Calculator calc;
    Object obj;

    public TestCalculator(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        // devices.Console.println("setUp");
        Calculator calc = new Calculator();
        calc.intValue = 10;
        calc.longValue = 100L;
        calc.floatValue = 1000.0F;
        calc.doubleValue = 10000.0D;

        calc.shortValue = 7;
        calc.byteValue = 3;

        calc.bool = true;

        calc.str = "Hello";
        this.calc = calc;
        obj = new String("HSO");

    }

    protected void tearDown() throws Exception {
        // devices.Console.println("tearDown");
    }

    public void testIntegers() {
        devices.Console.println("testNumbers");

        int value1 = 2;
        calc.addInt(value1);
        assertEquals(12, calc.intValue);

        long value2 = 22L;
        calc.addLong(value2);
        assertEquals(122L, calc.longValue);

        short value3 = 12;
        calc.addShort(value3);
        assertEquals(19, calc.shortValue);

        byte value4 = 4;
        calc.addByte(value4);
        assertEquals(7, calc.byteValue);
    }

    public void testRealNumbers() {
        devices.Console.println("testRealNumbers");

        float value1 = 1.23F;
        calc.addFloat(value1);
        assertEquals(1001.23F, calc.floatValue, 0.001F);

        double value2 = 1.234D;
        calc.addDouble(value2);
        assertEquals(10001.234D, calc.doubleValue, 0.0001D);

    }

    public void testBoolean() {
        devices.Console.println("testBoolean");

        boolean value1 = true;
        boolean value2 = false;

        calc.bool = value1;
        assertEquals(true, calc.bool);

        assertEquals(false, value1 && value2);
        assertEquals(true, value1 || value2);
        assertEquals(false, !value1);
    }

    public void testStrings() {
        devices.Console.println("testStrings");

        calc.str += "HSO";

        assertEquals("HelloHSO", calc.str);
    }

    public void testObjects() {
        devices.Console.println("testObjects");

        Object value1 = obj;
        Object value2 = new String("HSO");

        assertSame(obj, value1);

        assertNotSame(obj, value2);
        assertEquals(obj, value2);
    }

    public static void main(String[] args) {
        TestCase numberTestCase = new TestCalculator("numbers") {
            public void runTest() {
                testIntegers();
            }
        };
        TestCase testRealNumbers = new TestCalculator("realNumbers") {
            public void runTest() {
                testRealNumbers();
            }
        };
        TestCase booleanTestCase = new TestCalculator("boolean") {
            public void runTest() {
                testBoolean();
            }
        };
        TestCase stringTestCase = new TestCalculator("strings") {
            public void runTest() {
                testStrings();
            }
        };
        TestCase objectTestCase = new TestCalculator("objects") {
            public void runTest() {
                testObjects();
            }
        };

        TestSuite suite = new TestSuite();

        suite.addTest(numberTestCase);
        suite.addTest(testRealNumbers);
        suite.addTest(booleanTestCase);
        suite.addTest(stringTestCase);
        suite.addTest(objectTestCase);

        TestResult result = new TestResult();
        suite.run(result);

        devices.Console.println("Test runs:           " + result.runCount());
        devices.Console.println("Test failures:       " + result.failureCount());
        devices.Console.println("Test errors:         " + result.errorCount());

        if (result.failureCount() > 0) {
            devices.Console.println("Test failures are in:");

            for (Enumeration<TestFailure> e = result.failures(); e.hasMoreElements();) {
                devices.Console.println("\t" + e.nextElement());
            }
        } else {
            args = null;
        }

    }
}
