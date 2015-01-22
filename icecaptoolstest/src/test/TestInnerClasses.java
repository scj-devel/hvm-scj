package test;

public class TestInnerClasses {

    public static class A {

        public int foo() {
            return 42;
        }
        
        public static void main(String[] args) {
            args = null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		A a = new A();
        if (a != null) {
            int x = a.foo();
            if (x == 42) {
                return null;
            }
        }
        return args;
	}
}
