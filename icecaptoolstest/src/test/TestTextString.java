package test;

import util.TextString;

public class TestTextString {

    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }

    }

    private static boolean test() {
        TextString ts1 = new TextString((byte) 4);
        ts1.add('A');
        ts1.add('B');
        ts1.add('B');
        ts1.add('A');

        TextString ts2 = new TextString((byte) 4);
        ts2.add('A');
        ts2.add('B');
        ts2.add('B');
        ts2.add('A');

        TextString ts3 = new TextString((byte) 5);
        ts3.add('A');
        ts3.add('B');
        ts3.add('B');
        ts3.add('A');
        ts3.add('A');

        if (ts1.compareTo(ts2) == 0) {
            if (ts1.compareTo(ts3) < 0) {
                if (ts3.compareTo(ts1) > 0) {
                    TextString ts4 = new TextString((byte) 5);
                    ts4.add('Z');
                    ts4.add('A');
                    ts4.add('P');
                    ts4.add('P');
                    ts4.add('A');
                    if ((ts1.compareTo(ts4) < 0) && (ts2.compareTo(ts4) < 0) && (ts3.compareTo(ts4) < 0))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
