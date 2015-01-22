package test;

public class TestAppendFloat {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test(0.42f);
        if (!failed) {
            args = null;
        }
    }

    public static boolean test(float x) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hej");
        buffer.append(x);
        if (buffer.toString().equals("hej")) {
            buffer.append("hej" +  x);
            if (buffer.toString().equals("hejhej")) {
                buffer.append("1" +  x + (float) x);
                if (buffer.toString().equals("hejhej1")) {
                    return false;
                }
            }
        }

        return true;
    }
}
