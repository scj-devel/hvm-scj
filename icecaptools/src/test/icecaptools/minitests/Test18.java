package test.icecaptools.minitests;

public class Test18 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Interface foo;

        foo = getImplementor(args[0]);

        new B();
        foo.method();
    }

    private static Interface getImplementor(String string) {
        if (string == "dotit") {
            return new Implementor1();
        } else {
            return new Implementor2();
        }
    }

}
