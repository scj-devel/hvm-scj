package test.icecaptools.minitests;

public class Test10 {

    public static void main(String args[]) {
       Super sup = new Sub1();
       sup.foo();

       if(sup != null){
           args = null;
       }
    }
}
