package test;

import reflect.ObjectInfo;
import util.ReferenceIterator;
import vm.HVMHeap;
import vm.Heap;

public class TestArrayTraversal {

    private static class TempClass {
        ;
    }

    private static String[] testArray;

    private static TempClass[] tempClasses;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Heap heap = HVMHeap.getHeap();
        boolean failed = test1(heap);
        failed |= test2(heap);
        if (!failed) {
            args = null;
        }
    }

    public static boolean test2(Heap heap) {
        tempClasses = new TempClass[1];

        int aAddress = ObjectInfo.getAddress(tempClasses);

        ReferenceIterator references = heap.getRefFromObj(aAddress);
        if (references.hasNext()) {
            return true;
        }

        return false;
    }

    public static boolean test1(Heap heap) {
        String obj1 = new String("hej");
        String obj2 = new String("med");
        String obj3 = new String("dig");

        testArray = new String[3];

        testArray[0] = obj1;
        testArray[1] = obj2;
        testArray[2] = obj3;

        int aAddress = ObjectInfo.getAddress(testArray);

        ReferenceIterator references = heap.getRefFromObj(aAddress);

        if (references.hasNext()) {
            while (references.hasNext()) {
                int nextReference = references.next();
                devices.Console.println("Object at offset " + nextReference);
                if (ObjectInfo.getAddress(obj1) == nextReference) {
                    devices.Console.println(obj1);
                } else if (ObjectInfo.getAddress(obj2) == nextReference) {
                    devices.Console.println(obj2);
                } else if (ObjectInfo.getAddress(obj3) == nextReference) {
                    devices.Console.println(obj3);
                } else
                    return true;
            }
            return false;
        }
        else
        {
            devices.Console.println("No references in array?");
        }
        return true;
    }
}
