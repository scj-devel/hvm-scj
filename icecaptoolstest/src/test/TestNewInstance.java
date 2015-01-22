package test;

public class TestNewInstance {

    private static class A
    {
        private int x;
        
        @SuppressWarnings("unused")
        public A()
        {
            x = 42;
        }

        @SuppressWarnings("unused")
        public A(int x)
        {
            this.x = x;
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();
        if (!failure)
        {
            args = null;
        }
    }

    public static boolean test() {
        Class<A> clazz = A.class;

        try {
            A a = clazz.newInstance();
            if (a.x != 42)
            {
                return true;
            }
        } catch (InstantiationException e) {
            return true;
        } catch (IllegalAccessException e) {
            return true;
        }
        return false;
    }
}
