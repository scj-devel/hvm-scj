package test;

import vm.VMTest;

public class TestWrite {

    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    public static boolean test() {
        devices.Console.println("hello");
        return false;
    }
}
