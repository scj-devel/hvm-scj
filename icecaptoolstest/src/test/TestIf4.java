package test;

public class TestIf4 {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	args = test(args);
    }

	public static String[] test(String[] args) {
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
                    return null;
                }
            }
        }
        return args;
	}
}
