package test;

public class TestUSHR {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed)
        {
            args = null;
        }
    }

    public static boolean test() {
        byte number = (byte) 0x87;
        byte msn, lsn;
        msn = (byte) ((number & 0xf0) >> 4 );
        lsn = (byte) (number & 0x0f);
        if (msn == 8)
        {
            if (lsn == 7)
            {
                return false;
            }
        }
        return true;
    }
}
