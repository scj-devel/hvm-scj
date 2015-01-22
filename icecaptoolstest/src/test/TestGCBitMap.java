package test;

import gc.BitMap;
import util.ReferenceIterator;
import util.ReferenceList;

public class TestGCBitMap {

    static boolean pcTest;
    public BitMap bitMap;

    /**
     * @param args
     */
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        boolean success;

        pcTest = true;

        TestGCBitMap bitMapTest = new TestGCBitMap();

        if (simpleTest()) {
            if (bitMapTest.test()) {
                args = null;
                return;
            } else {
                devices.Console.println("\nbitMapTest failed");
            }
        } else {
            devices.Console.println("\nsimpleTest failed");
        }
    }

    public static boolean simpleTest() {
        BitMap bitMap = new BitMap(4, 2000, 400);

        bitMap.shadeRefBlack(2004);
        if (!bitMap.isRefBlack(2004)) {
            return false;
        }
        return true;
    }

    public boolean test() {
        bitMap = new BitMap(4, 2000, 400);

        ReferenceList whiteList;
        whiteList = new ReferenceList();

        ReferenceList blackList;
        blackList = new ReferenceList();

        blackList.add(2040);
        blackList.add(2064);

        whiteList.add(2148);

        blackList.add(2160);

        whiteList.add(2180);
        whiteList.add(2200);

        blackList.add(2000);
        blackList.add(2032);

        whiteList.add(2220);

        blackList.add(2080);
        blackList.add(2100);

        whiteList.add(2120);
        whiteList.add(2240);

        blackList.add(2268);
        blackList.add(2280);

        whiteList.add(2360);

        blackList.add(2384);

        whiteList.add(2300);
        whiteList.add(2320);
        whiteList.add(2340);

        ReferenceIterator blackListItr = blackList.iterator();
        while (blackListItr.hasNext()) {
            int ref = blackListItr.next();
            bitMap.shadeRefBlack(ref);
            if (pcTest) {
                // devices.Console.println("Marked black: " + ref);
            }
        }

        blackListItr = blackList.iterator();
        while (blackListItr.hasNext()) {
            int ref = blackListItr.next();
            bitMap.shadeRefBlack(ref);
            if (!bitMap.isRefBlack(ref)) {
                devices.Console.println("Error1");
                return false;
            }
        }
        // devices.Console.println("\n");

        blackListItr = blackList.iterator();
        while (blackListItr.hasNext()) {
            int ref = blackListItr.next();
            if (bitMap.isRefWhite(ref)) {
                devices.Console.println("Error2");
                return false;
            }
        }
        // devices.Console.println("\n");

        bitMap.shadeRefGrey(2140);
        bitMap.shadeRefGrey(2320);
        bitMap.shadeRefGrey(2220);

        if (!((bitMap.isRefGrey(2140) && (bitMap.isRefGrey(2220)) && (bitMap.isRefGrey(2320))))) {
            devices.Console.println("Error3");
            return false;
        } else {
            // devices.Console.println("Refs marked grey OK");
        }

        whiteList.add(2140);
        bitMap.shadeRefBlack(2140);

        blackList.add(2220);
        bitMap.shadeRefBlack(2220);

        blackList.add(2320);
        bitMap.shadeRefBlack(2320);

        if (((bitMap.isRefGrey(2140) || bitMap.isRefGrey(2220)) || (bitMap.isRefGrey(2320))) || (bitMap.isRefWhite(2140) || bitMap.isRefWhite(2220)) || (bitMap.isRefWhite(2320))) {
            devices.Console.println("Error4");
            return false;
        } else {
            // devices.Console.println("Refs now marked  black OK");
        }
        // devices.Console.println("\n");

        ReferenceIterator whites = whiteList.iterator();
        while (whites.hasNext()) {
            int next = whites.next();
            bitMap.shadeRefWhite(next);
            if (!bitMap.isRefWhite(next)) {
                devices.Console.println("Error5");
                return false;
            }
        }

        ReferenceIterator freeList;

        boolean foundRef = false;
        freeList = bitMap.getFreeList();
        while (freeList.hasNext()) {
            int ref = freeList.next();
            ReferenceIterator wl = whiteList.iterator();
            while (wl.hasNext()) {
                int wlRef = wl.next();
                if (wlRef == ref) {
                    foundRef = true;
                }
            }
            if (!foundRef) {
                // devices.Console.println("\nThe free list is not equal to the white list: ");
                devices.Console.println("Error6");
                return false;
            }
            foundRef = false;
        }
        return true;
    }
}
