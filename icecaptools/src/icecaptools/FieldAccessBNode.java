package icecaptools;

public abstract class FieldAccessBNode extends BNode {
    private String className;
    private boolean isGet;
    private String fieldName;
    private String signature;
    private int fieldSize;

    public FieldAccessBNode(int address, String className, String fieldName, String signature, boolean isGet, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
        this.className = className;
        this.fieldName = fieldName;
        this.signature = signature;
        this.isGet = isGet;
        fieldSize = 0;
    }

    @Override
    public boolean requiresExtension() {
        return true;
    }

    @Override
    public void relocateForward(int address, int extension) {
    }

    @Override
    public void relocateBackward(int address, int extension) {
    }

    @Override
    public int extend() throws Exception {
        byte[] newRawBytes = new byte[this.rawBytes.length + 3];

        newRawBytes[0] = this.rawBytes[0];

        for (int i = 1; i < this.rawBytes.length; i++) {
            newRawBytes[i + 1] = this.rawBytes[i];
        }

        this.rawBytes = newRawBytes;
        return 3;
    }

    @Override
    protected String print() {
        return "fieldaccess";
    }

    public String getSignature() {
        return signature;
    }

    public String getClassName() {
        return className;
    }

    public boolean isGet() {
        return isGet;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setSize(int size) {
        this.fieldSize = size;
    }
    
    public int getSize() {
        return this.fieldSize;
    }
}
