package test;

public class TestStaticField {

    private static class Super
    {
        public int y;
        public static int x;
        
        public void foo()
        {
            x = x + 1;
            y = y + 1;
        }
    }
    
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		Super sup1 = new Super();
        Super sup2 = new Super();
        
        sup1.foo();
        sup2.foo();
        
        int res = sup1.y + sup2.y;
        if (Super.x == 2)
        {
            if (res == 2)
            {
                return null;
            }
        }
        return args;
	}
}
