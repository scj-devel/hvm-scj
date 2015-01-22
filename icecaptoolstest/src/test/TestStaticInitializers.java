package test;

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
        args = test(args);
    }

	public static String[] test(String[] args) {
		int temp = A.x;
        if (result + temp + B.y  + 1== 42)
        {
            return null;
        }
        return args;
	}

}
