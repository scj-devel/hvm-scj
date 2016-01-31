package test;

import java.util.ArrayList;

import vm.VMTest;

public class TestClone2 {

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        ArrayList<Integer> clone = (ArrayList<Integer>) array.clone();
        boolean failed = true;
        if (clone.equals(array)) {
            if (clone != array) {
            	failed = false;
            }
            else
            {
                devices.Console.println("Object not cloned!");
            }
        }
        VMTest.markResult(failed);
    }
}
