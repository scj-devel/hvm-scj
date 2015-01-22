package test.icecaptools.minitests;

public class Test20 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Super sup;
        if (args[0] == "") {
            sup = createSub();
            return;
        } else {
            sup = new Sub1();
        }
        sup.foo();
    }

    private static Super createSub() {
        return new Sub2();
    }

}
