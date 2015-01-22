package test;

import reflect.ObjectInfo;
import util.ReferenceIterator;
import vm.HVMHeap;
import vm.Heap;

public class TestStaticReferences {

    @SuppressWarnings("unused")
    private static int x = 42;
    private static Object obj;
    @SuppressWarnings("unused")
    private static int y = 42;

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
        obj = new Object();
        Heap heap = HVMHeap.getHeap();

        ReferenceIterator refIterator = heap.getStaticRef();

        while (refIterator.hasNext()) {
            int ref = refIterator.next();

            if (ObjectInfo.getAddress(obj) == ref) {
                return false;
            }
        }
        return true;
    }
}
