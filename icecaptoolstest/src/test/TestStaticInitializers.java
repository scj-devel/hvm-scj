package test;

import vm.VMTest;

public class TestStaticInitializers {

    private static int result;
    
    private static class A
    {
        static int x;
        
        static
        {
            x = 5;
            result = 6;
        }
    }
    
    private static class B
    {
        static int y;
        
        static
        {
            y = 30;
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		int temp = A.x;
        if (result + temp + B.y  + 1== 42)
        {
            return false;
        }
        return true;
	}

}
