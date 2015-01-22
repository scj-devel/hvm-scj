package test;

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
        args = test(args);
    }

	public static String[] test(String[] args) {
		x = 43;
        if (x == 43)
        {
           return null;
        }
        return args;
	}
}
