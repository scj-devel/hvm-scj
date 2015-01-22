package test;

public class TestLong2 extends TestLong {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	args = test(args);
    }

	public static String[] test(String[] args) {
		long x = 100;
        long y = 0;
        try {
            x = x / y;
        } catch (ArithmeticException ae) {
            return testldiv32();
        }
        return args;
	}

    public static String[] testldiv32() {
        long[][] operands = { { 100, 10 }, { 10, 100 }, { 1000000000000L, 1 }, { -100, 10 }, { 0x1, 0x1 }, { 0x0, 0x1 }, {0x143f3278a61L, 1000} };
        for (int i = operands.length - 1; i >= 0; i--) {
            long[] nextPair = operands[i];
            long x = nextPair[0];
            long y = nextPair[1];
            long expected = x / y;
            long actual1 = ldiv32(x, y);

            if (expected != actual1) {
                return failed();
            }

            x = -x;
            expected = -expected;
            actual1 = ldiv32(x, y);
            if (expected != actual1) {
                return failed();
            }

            x = -x;
            y = -y;
            actual1 = ldiv32(x, y);
            if (expected != actual1) {
                return failed();
            }

            x = -x;
            expected = -expected;
            actual1 = ldiv32(x, y);
            if (expected != actual1) {
                return failed();
            }
        }
        return null;
    }

    private static long ldiv32(long x, long y) {
        long result = 0;
        long k, sum;
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
