package test;

public class TestClone1 {

    private static class A implements Cloneable {
        public int x;

        public A(int i) {
            this.x = i;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            A a = new A(x);
            return a;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        int[] intArray = new int[3];
        intArray[0] = 1;
        intArray[1] = 2;
        intArray[2] = 3;
        int[] clone = intArray.clone();
        if (clone.length == intArray.length) {
            if (clone[0] == intArray[0])
                if (clone[1] == intArray[1])
                    if (clone[2] == intArray[2]) {
                        A aArray[] = new A[2];
                        aArray[0] = new A(1);
                        aArray[1] = new A(2);
                        A[] aClone = aArray.clone();
                        if (aArray[0] == aClone[0]) {
                            if (aArray[1] == aClone[1]) {
                                return null;

                            }
                        }
                    }
        }
        return args;
    }
}
