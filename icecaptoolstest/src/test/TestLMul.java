package test;

public class TestLMul {

    public static void main(String[] args) {
        long[][] operands = { { 0x100000000L, 0xFFFFL }, { 0x100000000L, 0x2 }, { 0x1FFFFFFFFL, 0x3 }, { 0xFFFFFFFFL, 0xFFFFFFFFL }, { 0xFFFFFFFFL, 0x00000003 }, { 0xFFFFFFFFL, 0x00000002 }, { 0x00000001, 0xFFFFFFFFL }, { 0xFFFFFFFFL, 0x00000001 }, { 0, 0 }, { 1, 1 },
                { 10, 10 }, { 10, 0 } };
        for (int i = operands.length - 1; i >= 0; i--) {
            long op1 = operands[i][0];
            long op2 = operands[i][1];

            long expected = op1 * op2;
            long actual = lmul((int) (op1 >> 32), (int) op1, (int) (op2 >> 32), (int) op2);
            if (actual != expected) {
                devices.Console.println(op1 + " * " + op2 + " = [" + expected + ", " + actual + "]");
                return;
            }
        }
        args = null;
    }

    private static long lmul(int mx, int lx, int my, int ly) {
        long a, b, c, result;
        a = (mx & 0xFFFFFFFFL) * (ly & 0xFFFFFFFFL);
        b = (lx & 0xFFFFFFFFL) * (my & 0xFFFFFFFFL);
        c = (lx & 0xFFFFFFFFL) * (ly & 0xFFFFFFFFL);
        result = (a + b) << 32;
        result += c;
        return result;
    }
}
