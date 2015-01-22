package test.icecaptools.minitests;

public class TestIAND {
    public static void main(String[] args) {
        int x = 42;
        int y = 21;
        x = x & y;

        if(x == 0){
            args = null;
        }
    }
}