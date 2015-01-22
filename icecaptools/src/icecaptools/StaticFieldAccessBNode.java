package icecaptools;

public class StaticFieldAccessBNode extends FieldAccessBNode {
    public StaticFieldAccessBNode(int address, String className, String fieldName, String signature, boolean isGet, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, fieldName, signature, isGet, locationClass, locationMethod, locationMethodSignature);
    }
}
