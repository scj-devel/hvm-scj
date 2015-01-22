package icecaptools;


public class NewArrayMultiBNode extends NewArrayBNode {
    private int dimension;
    
    public NewArrayMultiBNode(int type, int address, String locationClass, String locationMethod, String locationMethodSignature, int dimension) {
        super(type, address, locationClass, locationMethod, locationMethodSignature);
        this.dimension = dimension;
    }

    public NewArrayMultiBNode(JavaArrayClass newType, int address, String className, String targetMethodName, String targetMethodSignature, int dimension) {
        super(newType, address, className, targetMethodName, targetMethodSignature);
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }
}
