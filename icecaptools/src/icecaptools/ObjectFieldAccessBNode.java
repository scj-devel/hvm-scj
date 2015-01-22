package icecaptools;

public class ObjectFieldAccessBNode extends StaticFieldAccessBNode {

    public ObjectFieldAccessBNode(int address, String className, String fieldName, String signature, boolean isGet, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, fieldName, signature, isGet, locationClass, locationMethod, locationMethodSignature);
    }
}
