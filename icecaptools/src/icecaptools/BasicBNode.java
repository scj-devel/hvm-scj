package icecaptools;

public class BasicBNode extends BNode {

    public BasicBNode(int address, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public boolean requiresExtension() {
        return false;
    }

    @Override
    public void relocateForward(int address, int extension) {
    }

    @Override
    public void relocateBackward(int address, int extension) {
    }

    @Override
    protected String print() {
        return "basic";
    }
}
