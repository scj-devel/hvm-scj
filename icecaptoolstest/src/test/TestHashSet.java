package test;

import java.util.HashSet;

public class TestHashSet {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test1();
        if (!failure) {
            args = null;
        }

    }

    private static boolean test1() {
        HashSet<String> map = new HashSet<String>();
        if (map != null) {
            map.add("Abba");
            if (map.size() == 1) {
                map.add("Zappa");
                if (map.size() == 2) {
                    if (map.contains("Abba")) {
                        if (map.contains("Zappa")) {
                            if (!map.contains("Zabba")) {
                                map.remove("Abba");
                                if (!map.contains("Abba")) {
                                    return map.size() != 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
