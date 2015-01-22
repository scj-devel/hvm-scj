package test.icecaptools.minitests;

public class TestException {

    public static void main(String args[]) {
        try {
            throw new Exception("test");
        } catch (Exception ex) {
            new Sub1();
        }
        args = null;
    }
    
}
