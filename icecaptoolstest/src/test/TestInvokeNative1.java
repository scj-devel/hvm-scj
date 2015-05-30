package test;

import vm.VMTest;

public class TestInvokeNative1 {

    private static class Super
    {
        public native int testNative();    
        
        public native static int testNativeStatic();    
    }
       
    private static class SubClass extends Super
    {
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
       VMTest.markResult(test());
    }

	public static boolean test() {
	    Super super_ = new Super();
        
		if (super_.testNative() == 42)
        {
		    SubClass sc = new SubClass();
		    if (sc.testNative() == 42)
		    {
		        if (Super.testNativeStatic() == 42)
		        {
		            if (SubClass.testNativeStatic() == 42)
		            {
	                    return false;		                
		            }
		        }
		    }
        }
        return true; 
	}
}
