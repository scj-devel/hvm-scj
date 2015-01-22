package icecaptools;

public class RuntimeMethodCallBNode extends SpecialMethodCallBNode {

    public RuntimeMethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
    }
}
