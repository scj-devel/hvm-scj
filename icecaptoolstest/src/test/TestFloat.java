package test;

public class TestFloat {

    public static void main(String[] args) {
        boolean failure = test1();
        failure |= test2();
        failure |= test3();
        failure |= test4(4.20f);
        if (!failure) {
            args = null;
        }
    }

    public static boolean test4(float f) {
        f = -f;
        f = f * -1.0f;
        if (f != 4.20f)
        {
            return true;
        }
        return false;
    }

    public static boolean test3() {
        float x = 2.10f;
        x = x * 2.0f;
        x = x / 2.0f;
        x = x - 2.0f;
        if (x > 0.09f) {
            if (x < 0.10f) {
                return false;
            }
        }
        return true;
    }

    public static boolean test2() {
        float f1 = 3.12f;
        float f2 = 1.10f;

        if (f1 + f2 == 4.22f) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean test1() {
        float x = (float) 3.141592;

        if (x == 3.141592f) {
            return false;
        } else {
            return true;
        }
    }

}
