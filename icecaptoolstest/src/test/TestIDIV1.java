package test;

import vm.VMTest;

public class TestIDIV1 extends TestLong {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		int x = 100;
        int y = 0;
        boolean result = true;
        try {
            x = x / y;
        } catch (ArithmeticException ae) {
        	result = testidiv(); 
        }
        return result;
	}
    
    public static boolean testidiv() {
        int[][] operands = { { 100, 10 }, { 10, 100 }, { -100, 10 }, { 0x1, 0x1 }, { 0x0, 0x1 } };
        for (int i = operands.length - 1; i >= 0; i--) {
            int[] nextPair = operands[i];
            int x = nextPair[0];
            int y = nextPair[1];
            int expected = x / y;
            int actual1 = idiv(x, y);

            if (expected != actual1) {
                return failed();
            }

            x = -x;
            expected = -expected;
            actual1 = idiv(x, y);
            if (expected != actual1) {
                return failed();
            }

            x = -x;
            y = -y;
            actual1 = idiv(x, y);
            if (expected != actual1) {
                return failed();
            }

            x = -x;
            expected = -expected;
            actual1 = idiv(x, y);
            if (expected != actual1) {
                return failed();
            }
        }
        return false;
    }
    
    private static int idiv(int x, int y) {
        int result = 0;
        int k, sum;
        boolean isMinus;

        if (x < 0) {
            x = ~x + 1;
            if (y < 0) {
                y = ~y + 1;
                isMinus = false;
            } else {
                isMinus = true;
            }
        } else {
            if (y < 0) {
                y = ~y + 1;
                isMinus = true;
            } else {
                isMinus = false;
            }
        }

        while (y <= x) {
            k = 1;
            sum = y;
            while ((sum << 1) <= x) {
                sum = sum << 1;
                k = k << 1;
            }
            x = x - sum;
            result += k;
        }

        if (isMinus) {
            result = ~result + 1;
        }

        return result;
    }
}
