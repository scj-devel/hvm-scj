package test.icecaptools.minitests;

public class Sub4 extends Super {

    @Override
    public Super getNewSub() {
        new C();
        return new Sub5();
    }

}
