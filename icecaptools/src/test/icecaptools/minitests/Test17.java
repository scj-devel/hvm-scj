package test.icecaptools.minitests;

public class Test17 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);

        switch (x) {
        case 1:
            new C();
            break;
        case 2:
            new D();
            break;
        default:
            new F();
        }
    }

}
