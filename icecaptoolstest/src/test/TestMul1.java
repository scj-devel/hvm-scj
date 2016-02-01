package test;

import vm.VMTest;

public class TestMul1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		int[][] pairs = { { 10, 10 }, { 1, 10 }, { 0, 0 }, { 100, 10 }, { 1, 0 }, { 0x7fffffff, 0xff } };

        for (int i = 0; i < pairs.length; i++) {
            int[] pair = pairs[i];
            int x = pair[0];
            int y = pair[1];
            int expected = x * y;
            int actual = imul(x, y);
            if (expected != actual) {
                return true;
            }
            
            actual = imul(y, x);
            if (expected != actual) {
                return true;
            }
            
            actual = imul(x, -y);
            if (expected != -actual) {
                return true;
            }
            
            actual = imul(-x, y);
            if (expected != -actual) {
                return true;
            }
            
            actual = imul(-x, -y);
            if (expected != actual) {
                return true;
            }
        }
        return false;
	}

    public static int imul(int x, int y) {
        int result = 0;
        boolean sign = false;
        if (x < 0)
        {
            x = -x;
            sign = !sign;
        }
        if (y < 0)
        {
            y = -y;
            sign = !sign;
        }
         
        if (y != 0) {
            while (y > 1) {
                if ((y & 1) == 1) {
                    result = result + x;
                    y = y - 1;
                } else {
                    x = x << 1;
                    y = y >> 1;
                }
            }
            result = result + x;
        }
        if (sign)
        {
            result = -result;
        }
        return result;
    }
}
