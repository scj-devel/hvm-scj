package test;

import test.icecaptools.minitests.Super;
import vm.VMTest;


public class TestCheckCast2 extends Super {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        VMTest.markResult(test());
    }

    @SuppressWarnings("unused")
    public static boolean test() {
        Object test = new TestCheckCast2();
        Super sup;

        try {
            sup = (Super) test;
            return false;
        } catch (ClassCastException ex) {
            ;
        }
        return true;
    }
}
