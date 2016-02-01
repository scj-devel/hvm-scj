package test;

import vm.VMTest;

public class TestIf4 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		int x = 42;
        
        if (x < 10)
        {
            ;
        }
        else if (x > 10)
        {
            if (x < 50)
            {
                if (x == 42)
                {
                    return false;
                }
            }
        }
        return true;
	}
}
