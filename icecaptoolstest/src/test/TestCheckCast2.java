package test;

import test.icecaptools.minitests.Super;


public class TestCheckCast2 extends Super {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        args = test(args);
    }

    @SuppressWarnings("unused")
    public static String[] test(String[] args) {
        Object test = new TestCheckCast2();
        Super sup;

        try {
            sup = (Super) test;
            return null;
        } catch (ClassCastException ex) {
            ;
        }
        return args;
    }
}
