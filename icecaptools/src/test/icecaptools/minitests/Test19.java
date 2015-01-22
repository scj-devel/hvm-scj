package test.icecaptools.minitests;

public class Test19 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Super sup;
        if (args[0] == "") {
            sup = new Sub2();
            return;
        } else {
            sup = new Sub1();
        }
        sup.foo();
    }

}
