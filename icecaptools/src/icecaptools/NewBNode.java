package icecaptools;

import org.apache.bcel.classfile.JavaClass;

public class NewBNode extends BNode {

    protected JavaClass type;

    public NewBNode(int address, JavaClass type, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
        this.type = type;
    }

    public String getType() {
        String typeName = type.getClassName();
        return typeName;
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
        return "new " + type.getClassName();
    }
}
