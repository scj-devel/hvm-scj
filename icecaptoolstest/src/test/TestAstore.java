package test;

public class TestAstore {

    private static class A
    {
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @SuppressWarnings("unused")
    public static String[] test(String[] args) {
        int x, y, z, a, b, c;
        A object = new A();
        if (object != null)
        {
            return null;
        }
        return args;
    }

}
