package test;

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
        args = test(args);
    }

	public static String[] test(String[] args) {
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
	                    return null;		                
		            }
		        }
		    }
        }
        return args; 
	}
}
