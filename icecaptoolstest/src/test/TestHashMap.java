package test;

import java.util.HashMap;

import vm.VMTest;

public class TestHashMap {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test1();
        VMTest.markResult(failure);
    }

    private static boolean test1() {
        HashMap<String, String> relation = new HashMap<String, String>();
        relation.put("Astrid", "Stephan");
        relation.put("Linus", "Lisbeth");
        if (relation.containsKey("Astrid")) {
            if (relation.get("Astrid").equals("Stephan")) {
                if (relation.containsKey("Linus")) {
                    if (relation.get("Linus").equals("Lisbeth")) {
                        if (relation.get("Astrid").equals("Lisbeth")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
