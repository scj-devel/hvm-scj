package test;

import java.util.ArrayList;

public class TestClone2 {

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        ArrayList<Integer> clone = (ArrayList<Integer>) array.clone();
        if (clone.equals(array)) {
            if (clone != array) {
                args = null;
            }
            else
            {
                devices.Console.println("Object not cloned!");
            }
        }
    }
}
