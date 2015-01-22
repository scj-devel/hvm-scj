package icecaptools;

import org.apache.bcel.classfile.JavaClass;

public class JavaArrayClass extends JavaClass {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String getSuperclassName() {
        return "java.lang.Object";
    }

    private int dimension;
    private String className;
    private boolean isBasicType;

    @Override
    public String getClassName() {
        return className;
    }

    public int getDimension() {
        return dimension;
    }

    public JavaArrayClass(String className, JavaClass et, int dimension) {
        super(et.getClassNameIndex(), et.getSuperclassNameIndex(), et.getFileName(), et.getMajor(), et.getMinor(), et.getAccessFlags(), et.getConstantPool(), et.getInterfaceIndices(), et.getFields(), et.getMethods(), et.getAttributes());
        this.dimension = dimension;
        this.className = className;
        isBasicType = false;
    }

    public static boolean isArrayClass(String clazzName) {
        return (clazzName.startsWith("[") || clazzName.endsWith("[]"));
    }

    public static String getElementType(String clazzName) {
        String elementType = clazzName.substring(1);
        return elementType;
    }

    public static boolean isReferenceClass(String elementType) {
        return elementType.startsWith("L");
    }

    public static String getReferredType(String elementType) {
        return elementType.substring(1, elementType.length() - 1);
    }

    public int getBasicTypeSize() {
        char type = className.charAt(dimension);
        switch (type) {
        case 'Z':
        case 'B':
            return 1;
        case 'S':
            return 2;
        case 'C':
        case 'F':
        case 'I':
            return 4;
        case 'D':
        case 'J':
            return 8;
        }
        return -2;
    }

    public void setIsBasicType() {
        this.isBasicType = true;
    }

    public boolean isBasicType() {
        return isBasicType;
    }

    public int getBasicType() {
        char type = className.charAt(dimension);
        switch (type) {
        case 'Z':
            return 4;
        case 'C':
            return 5;
        case 'F':
            return 6;
        case 'D':
            return 7;
        case 'B':
            return 8;
        case 'S':
            return 9;
        case 'I':
            return 10;
        case 'J':
            return 11;
        }
        return -2;
    }
}
