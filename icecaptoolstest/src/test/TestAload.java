package test;

public class TestAload {

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
        A object1 = object;
        if (object1 == object)
        {
            return null;
        }
        return args;
    }
}
