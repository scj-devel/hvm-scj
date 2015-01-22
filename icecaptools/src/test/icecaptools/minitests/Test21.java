package test.icecaptools.minitests;

public class Test21 {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        Super sup;
        if (args[0] == "") {
            sup = createSub();
            throw new Exception("");
        } else {
            sup = new Sub1();
        }
        sup.foo();
    }

    private static Super createSub() throws Exception {
        return new Sub2();
    }

}
