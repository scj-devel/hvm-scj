package test;

public class TestSimple1 {

    public static void main(String[] args) {
        devices.Console.println("tick!");
        delay1((byte) 10);
        args = null;
    }

    private static void delay1(byte count) {
        for (short x = 0; x < count; x++) {
            for (short y = 0; y < 1000; y++) {

            }
        }
    }
}
