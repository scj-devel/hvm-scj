package test.icecaptools.minitests;

public class Sub5 extends Super {

    @Override
    public Super getNewSub() {
        new D();
        return this;
    }

}
