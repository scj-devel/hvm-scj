package icecaptools;

public class SpecialMethodCallBNode extends MethodCallBNode {

    public SpecialMethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public boolean requiresExtension() {
        return true;
    }
}
