package test;

// import devices.Console;

public class TestLong1 extends TestLong {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		long x = 1;
        for (int i = 0; i < 31; i++) {
            x = x << 1;
        }
        x = x << 1;
        x = x << 1;
        x = x << 1;

        // Console.println("x: " + x);

        if (x == 17179869184L) {
            return testlmul32();
        }
        return args;
	}

    public static String[] testlmul32() {
        long[][] operands = { { 0x100000000L, 0xFFFFL }, { 0x100000000L, 0x2 }, { 0x1FFFFFFFFL, 0x3 }, { 0xFFFFFFFFL, 0xFFFFFFFFL }, { 0xFFFFFFFFL, 0x00000003 }, { 0xFFFFFFFFL, 0x00000002 }, { 0x00000001, 0xFFFFFFFFL },
                { 0xFFFFFFFFL, 0x00000001 }, { 0, 0 }, { 1, 1 }, { -10, 10 },
                { 10, 0 } };
        for (int i = operands.length - 1; i >= 0; i--) {
            long[] nextPair = operands[i];
            long x = nextPair[0];
            long y = nextPair[1];
            long expected = x * y;
            long actual1 = lmul32(x, y);
            long actual2 = lmul32(y, x);

            if ((expected != actual1) || (actual1 != actual2)) {
                return failed();
            }

            x = -x;
            actual1 = lmul32(x, y);
            expected = -expected;
            if (expected != actual1) {
                return failed();
            }

            x = -x;
            y = -y;
            actual1 = lmul32(x, y);
            if (expected != actual1) {
                return failed();
            }

            x = -x;
            expected = -expected;
            actual1 = lmul32(x, y);
            if (expected != actual1) {
                return failed();
            }
        }
        return null;
    }

    public static long lmul32(long x, long y) {
        long xmsb, xlsb;
        long ymsb, ylsb;
        long rmsb = 0, rlsb = 0;

        long result = 0;

        boolean isMinus = false;

        if ((x != 0) && (y != 0)) {
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

            xmsb = x >> 32;
            xlsb = x & 0xffffffffL;

            ymsb = y >> 32;
            ylsb = y & 0xffffffffL;

            while (!((ymsb == 0) && (ylsb <= 1))) {
                if ((ylsb & 0x1) == 1) {
                    long temp = rlsb;
                    rlsb = (rlsb + xlsb) & 0xFFFFFFFFL;
                    if (rlsb < temp) {
                        rmsb += 1;
                    }
                    rmsb = (rmsb + xmsb) & 0xFFFFFFFFL;

                    if (ylsb == 0) {
                        ymsb = ymsb - 1;
                    }
                    ylsb = ylsb - 1;
                } else {
                    long carry = xlsb >> 31;

                    xlsb = (xlsb << 1) & 0xFFFFFFFFL;
                    xmsb = (xmsb << 1) & 0xFFFFFFFFL;
                    xmsb = xmsb | carry;

                    carry = ymsb & 0x1;
                    ymsb = ymsb >> 1;
                    ylsb = ylsb >> 1;
                    ylsb |= carry << 31;
                }
            }

            long temp = rlsb;
            rlsb = (rlsb + xlsb) & 0xFFFFFFFFL;
            if (rlsb < temp) {
                rmsb += 1;
            }
            rmsb = (rmsb + xmsb) & 0xFFFFFFFFL;
        }
        result = rmsb;
        result = result << 32;
        result = result | rlsb;

        if (isMinus) {
            result = ~result + 1;
        }

        return result;
    }
}
