package test;

import reflect.ObjectInfo;
import util.ReferenceIterator;
import vm.HVMHeap;
import vm.Heap;
import vm.VMTest;

public class TestObjectTraversal {

    private static class A
    {
        @SuppressWarnings("unused")
        A ref1;
        @SuppressWarnings("unused")
        A ref2;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
       boolean failed = test();
       VMTest.markResult(failed);
    }

    public static boolean test() {
        Heap heap = HVMHeap.getHeap();
        
        A a = new A();
        A b = new A();
        
        a.ref1 = a;
        a.ref2 = b;
        
        int aAddress = ObjectInfo.getAddress(a);
        
        devices.Console.println("Object address a = " + aAddress);
        
        ReferenceIterator references = heap.getRefFromObj(aAddress);
        
        if (references.hasNext())
        {
            int ref1 = references.next();
            if (ref1 == aAddress)
            {
                if (references.hasNext())
                {
                    int ref2 = references.next();
                    if (ref2 == ObjectInfo.getAddress(b))
                    {
                        return false;
                    }
                    else
                    {
                    	devices.Console.println("Object address b unexepcted");
                    }
                }
                else
                {
                	devices.Console.println("Only one ref");
                }
                
            }
            else
            {
            	devices.Console.println("Object address a unexepcted [" + aAddress + "], was [" + ref1 + "]");
            }
        }
        else
        {
        	devices.Console.println("No references");
        }
        return true;
    }
}
