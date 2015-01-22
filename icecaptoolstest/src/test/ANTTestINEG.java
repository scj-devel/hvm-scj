package test;

import icecaptools.IcecapCompileMe;

public class ANTTestINEG {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test((short)23);
        if (!failed)
        {
            args = null;
        }
    }

    @IcecapCompileMe
    public static boolean test(short i) {
        int x = -i;
        
        if (x == -23)
        {
            return false;
        }
        return true;
    }
}
