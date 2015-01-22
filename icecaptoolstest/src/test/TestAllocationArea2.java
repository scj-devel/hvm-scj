package test;

import vm.Memory;

public class TestAllocationArea2 {

    public static void main(String[] args) {
        Memory mainArea = Memory.getHeapArea();

        int waterMark = mainArea.consumedMemory();
        int count = 0;
        while (true) {
            devices.Console.println("Hello World [" + count + "]");
            count++;
            if (count < 10) {
                mainArea.reset(waterMark);
            } else {
                break;
            }
        }
        args = null;
    }
}
