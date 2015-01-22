package icecaptools;

import org.apache.bcel.classfile.JavaClass;

public class CheckcastBNode extends NewBNode {

    public CheckcastBNode(int address, JavaClass type, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, type, locationClass, locationMethod, locationMethodSignature);
    }

    public boolean typeIsInterface() {
        return super.type.isInterface();
    }

    @Override
    protected String print() {
        return "checkcast " + type.getClassName();
    }
}
