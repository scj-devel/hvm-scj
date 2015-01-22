package test;

public class TestInstanceof {

    static private class A
    {
        @SuppressWarnings("unused")
        int x;
    }
    
    static private class B extends A implements Interface1
    {
        @SuppressWarnings("unused")
        int y;

        @Override
        public void foo() {

        }
    }
    
    static private interface Interface1
    {
        void foo();
    }
    
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		Object a = new A();
        Object b = new B();
        
        if (a instanceof B)
        {
            return args;
        }

        if (a instanceof Interface1)
        {
            return args;
        }

        if (b instanceof String)
        {
            return args;
        }
        
        if (a instanceof A)
        {
            if (b instanceof B)
            {
                if (b instanceof Interface1)
                {
                    if (b instanceof A)
                    {
                        return null;
                    }
                }
            }
        }
        return args;
	}
}
