package test;

import vm.VMTest;

public class TestAstore {

    private static class A
    {
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @SuppressWarnings("unused")
    public static boolean test() {
        int x, y, z, a, b, c;
        A object = new A();
        if (object != null)
        {
            return false;
        }
        return true;
    }
}
