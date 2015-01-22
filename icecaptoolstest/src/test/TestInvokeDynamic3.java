package test;

public class TestInvokeDynamic3 {
    private static interface Adder {
        int add(int x, int y);
    }

    public static void main(String[] args) {
        Adder adder = (x, y) -> {
            return x + y;
        };

        if (!test()) {
            if (adder.add(30, 12) == 42) {
                args = null;
            }
        }
    }

    public static boolean test() {
        Adder adder2 = new Adder() {
            @Override
            public int add(int x, int y) {
                return x + y;
            }
        };
        if (adder2.add(39, 3) == 42) {
            return false;
        } else {
            return true;
        }

    }
}
