package test;

public class TestInvokeDynamic1 {
    private static interface Adder {
        int add(int x, int y);
    }

    public static void main(String[] args) {

        Runnable r = () -> devices.Console.println("hello world");
        r.run();

        Adder adder = (x, y) -> {
            return x + y;
        };

        int x = adder.add(40, 2);

        devices.Console.println("x = " + x);
        
        if (x == 42) {
            args = null;
        }
    }
}
