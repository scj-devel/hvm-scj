package test;

import vm.Memory;

public class TestAllocationArea1 {

    private static final int SCRATCHPADSTORESIZE = 4000;

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    public static boolean test() {
        Memory mainArea = Memory.getHeapArea();

        int start = Memory.allocateInHeap(SCRATCHPADSTORESIZE).getBase();

        Memory scratchPadStore = new Memory(start, SCRATCHPADSTORESIZE, "scratchPadStore");

        int free = scratchPadStore.consumedMemory();

        if (free == 0) {
            Memory.switchToArea(scratchPadStore);

            int mainAreaBefore = mainArea.consumedMemory();

            devices.Console.println("entering new area");

            int mainAreaAfter = mainArea.consumedMemory();

            free = scratchPadStore.consumedMemory();

            if (free == 0) {

                devices.Console.println("used " + (mainAreaAfter - mainAreaBefore) + " bytes to allocate above");

                try {
                    for (int i = 0; i < 100; i++) {
                        devices.Console.println("free: " + scratchPadStore.consumedMemory());
                    }
                    return true;
                } catch (OutOfMemoryError e1) {
                    scratchPadStore.reset(0);
                    try {
                        for (int i = 0; i < 10; i++) {
                            devices.Console.println("free: " + scratchPadStore.consumedMemory());
                        }
                        scratchPadStore.reset(0);
                        for (int i = 0; i < 10; i++) {
                            devices.Console.println("free: " + scratchPadStore.consumedMemory());
                        }
                        return false;
                    } catch (OutOfMemoryError e2) {
                        return true;
                    }
                }
            } else {
                devices.Console.println("Allocated string constant in wrong memory area???");
            }
        }
        return true;
    }
}
