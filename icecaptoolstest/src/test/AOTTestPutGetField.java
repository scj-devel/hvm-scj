package test;

import icecaptools.IcecapCompileMe;


public class AOTTestPutGetField {

    public int answer;

    private static class Super {
        int x;
    }

    private static class Sub extends Super {
        @SuppressWarnings("unused")
        int a, b, c;

    }

    public static void main(String[] args) {
        args = test(args);
    }

    @IcecapCompileMe
    public static String[] test(String[] args) {
        TestPutGetField test = new TestPutGetField();
        test.answer = 42;

        if (test.answer == 42) {
            Super sup = new Sub();
            sup.x = 12;
            ((Sub) sup).c = 30;

            int x = sup.x;
            x += ((Sub) sup).c;

            if (x == 42) {
                return null;
            }
        }
        return args;
    }
}
