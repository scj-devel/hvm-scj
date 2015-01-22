package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import devices.Console;

public class TestArrayList {

    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        list.add("hej");
        list.add("med");
        list.add("dig");
        list.add("Stephan");
        list.add("Korsholm");

        Object[] array = list.toArray();
        Arrays.sort(array);

        list = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            list.add((String) array[i]);
        }

        if (array.length == 5) {
            Iterator<String> sortedNames = list.iterator();
            String previous = null;
            String next;
            while (sortedNames.hasNext()) {
                next = sortedNames.next();
                if (previous != null) {
                    if (previous.compareTo(next) > 0) {
                        return args;
                    }
                }
                Console.println(next);
                previous = next;

            }
            return null;
        }
        return args;
    }
}
