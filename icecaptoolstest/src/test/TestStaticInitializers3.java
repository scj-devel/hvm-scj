package test;

import icecaptools.IcecapCompileMe;

public class TestStaticInitializers3 {

    private static int x = 10;
    private static int y = 20;

    private static class StaticClass {
        public static int x = 42;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        failed |= test1(true);
        failed |= test1(false);
        failed |= test2(true);
        failed |= test3();
        failed |= test4();
        if (!failed) {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test4() {
        int sum = 0;
        for (int i = 0; i < StaticClass.x; i++)
        {
            sum++;
        }
        
        for (int i = 0; i < sum; i++)
        {
            StaticClass.x--;
        }
        return (StaticClass.x != 0);
    }

    @IcecapCompileMe
    public static boolean test3() {
        for (int i = 0; i < 10; i++)
        {
            StaticClass.x += i;
        }
        for (int i = 0; i < 10; i++)
        {
            StaticClass.x += 1;
        }
        return !(StaticClass.x == 97);
    }

    @IcecapCompileMe
    public static boolean test1(boolean b) {
        if (b) {
            if (StaticClass.x == 42) {
                return false;
            }
        } else {
            if (StaticClass.x == 43) {
                return true;
            }
        }

        return (StaticClass.x != 42);
    }

    @IcecapCompileMe
    public static boolean test2(boolean b) {
        if (b) {
            if (!b) {
                return true;
            }
        } else {
            if (StaticClass.x == 43) {
                return true;
            }
        }

        return (StaticClass.x != 42);
    }

    @IcecapCompileMe
    public static boolean test() {
        if (x == 10) {
            if (y == 20) {
                if (x + y == 30) {
                    return false;
                }
            }
        }
        return true;
    }
}
