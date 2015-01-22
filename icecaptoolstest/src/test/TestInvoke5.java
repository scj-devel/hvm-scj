package test;

public class TestInvoke5 {

    private static interface Interface1 {
        public int manyArgs(int a, int b, int c, int d, int e, int f, int g);
    }

    private static class Super {
        public int foo(int x) {
            return x + 5;
        }

        public int bar(int x, int y) {
            return x + y - 1;
        }
    }

    private static class Sub extends Super implements Interface1 {
        public int foo(int x) {
            return x + 10;
        }

        public int manyArgs(int a, int b, int c, int d, int e, int f, int g) {
            return a + b + c + d + e + f + g;
        }
    }

    static int manyArgs(int a, int b, int c, int d, int e, int f, int g) {
        return new Sub().manyArgs(a, b, c, d, e, f, g);
    }

    static int fib(int n) {
        if ((n == 1) || (n == 2)) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    public static void main(String args[]) {
        args = test(args);
    }

	public static String[] test(String args[]) {

		Super s = new Sub();
        Interface1 ss;
        int x;
        x = s.bar(1, 2);

        x += s.foo(32);
        ss = (Sub) s;
        s = new Super();
        x += s.foo(10);
        x += ss.manyArgs(1, 2, 3, 4, 5, 6, x);
        x += manyArgs(-1, 2, -3, 4, -5, -6, x);
        int n = 1;
        while (fib(n) < x)
        {
            n++;
        }
        x += n;
        if (x == 283) {
            return null;
        }
        return args;
	}
}
