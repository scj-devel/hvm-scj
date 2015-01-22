package test;

public class TestReflectForName {
    
    private static class Number
    {
        private int x;
        
        @SuppressWarnings("unused")
        public Number()
        {
            x = 42;
        }
        
        public int getX()
        {
            return x;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        @SuppressWarnings("rawtypes")
        Class cls = Class.forName("test.TestReflectForName$Number");
        
        Number n = (Number) cls.newInstance();
        if (n.getX() == 42)
        {
            args = null;
        }
    }
}
