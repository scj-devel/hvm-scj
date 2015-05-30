package test;

import vm.VMTest;

public class TestAload {

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
        A object1 = object;
        
        if (object1 == object)
        {
            return false;
        }
        return true;
    }
}
