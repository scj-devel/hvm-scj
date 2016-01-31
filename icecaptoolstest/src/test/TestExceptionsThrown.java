package test;

import vm.VMTest;

public class TestExceptionsThrown {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = foo(args);
        VMTest.markResult(failure);
    }

    private static boolean foo(String[] args) {
        return test(args);
    }

    private static boolean test(String[] args) {
        int x = 10;
        int y = 20;
        
        if (x + y == 30)
        {
            return false;
        }
        return true;
    }
}
