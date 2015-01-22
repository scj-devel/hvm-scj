package test;

public class TestArrayOfInterfaces {

    private interface Interface {
        int foo();
    }

    private static class Implementor implements Interface {
        private int value;

        public Implementor(int value) {
            this.value = value;
        }

        @Override
        public int foo() {
            return value;
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

    private static boolean test() {
        Interface[] interfaces = new Interface[2];
        for (int i = 0; i < interfaces.length; i++) {
            interfaces[i] = new Implementor(i);
        }

        int sum = 0;
        for (int i = 0; i < interfaces.length; i++) {
            sum += interfaces[i].foo();
        }
        
        if (sum == 1)
        {
            return false;
        }
        return true;
    }

}
