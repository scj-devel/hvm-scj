package test.icecaptools.minitests;

public class Test13 {

    public static void main(String args[]) {

        Super sup;
        sup = new Sub4();
        
        for (int i = 0; i < 10; i++)
        {
            sup = sup.getNewSub();
        }

    }
}
