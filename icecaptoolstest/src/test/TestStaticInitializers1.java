package test;

import vm.VMTest;

public class TestStaticInitializers1 {

    static int x;
    
    static
    {
        x = 10;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		x = 43;
        if (x == 43)
        {
           return false;
        }
        return true;
	}
}
