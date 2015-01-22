package icecaptools;

public class DynamicMethodCallBNode extends BNode {

    private int bootstrapIndex;
    protected String methodName;
    protected String methodSig;

    public DynamicMethodCallBNode(int address, int bootstrapIndex, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super( address,  locationClass,  locationMethod,  locationMethodSignature);
        this.bootstrapIndex = bootstrapIndex;
        this.methodName = methodName;
        this.methodSig = methodSig;
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
        return "invokedynamic " + bootstrapIndex + "[" + methodName + methodSig + "]";
    }

    public int getBootstrapIndex() {
        return bootstrapIndex;
    }

    public String getClassName() {
        return locationClass;
    }
}
