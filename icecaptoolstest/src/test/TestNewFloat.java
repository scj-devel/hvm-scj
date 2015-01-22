package test;


public class TestNewFloat {

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
        double d = 0.42d;
        float f = (float) d; 
        if (f == 0.42f)
        {
            Float flt = new Float(f);
            String str = flt.toString();
            if (str.equals("0.420000"))
            {
                return false;    
            }
            else
            {
                devices.Console.println("Unexpected: " + str);
            }
        }
        return true;
    }
}
