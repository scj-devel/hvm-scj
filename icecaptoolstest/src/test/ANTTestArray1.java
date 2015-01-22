package test;

public class ANTTestArray1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failure = test();
        if (!failure) {
            args = null;
        }
    }

    public static boolean test() {
        byte array[] = new byte[5];

        return testArrayAccess(array);
    }

    public static boolean testArrayAccess(byte[] array) {
        if (array != null) {
            if (array.length > 0) {
                return (array[0] != 0);
            }
        }
        return true;
    }
}
