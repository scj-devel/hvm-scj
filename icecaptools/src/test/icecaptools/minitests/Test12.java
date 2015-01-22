package test.icecaptools.minitests;

public class Test12 {

    public static void main(String args[]) {

        Super sup;

        if (args[0] == null) {
            sup = new Sub3();
        }
        else
        {
            sup = new Sub2();
        }

        sup.foo();
    }
}
