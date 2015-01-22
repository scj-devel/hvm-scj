package icecaptools.compiler;


public class FieldInfo {
    private String name;
    private int offset;
    private int size;
    public boolean isVolatile;
    public boolean isReference;
    public boolean isFloat;
    private String structMemberName;
    private String structName;
    private String structMemberLSBName;
    public boolean isClassField;
    private boolean isHWObjectField;
    
    public FieldInfo(String name, int offset, int size, boolean isVolatile, boolean isReference, boolean isFloat, boolean isClassField, boolean isHWObjectField) {
        this.name = name;
        this.offset = offset;
        this.size = size;
        this.isVolatile = isVolatile;
        this.isReference = isReference;
        this.isFloat = isFloat;
        this.isClassField = isClassField;
        this.isHWObjectField = isHWObjectField;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        int result = size;
        
        if (isVolatile) {
            result |= 1;
        } 

        if (isReference) {
            result |= 2;
        } 
        
        return result;
    }

    public String getName() {
        return name;
    }

    public void setStructMemberName(String memberName) {
        this.structMemberName = memberName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public String getStructName() {
        return structName;
    }

    public String getStructMemberName() {
        return structMemberName;
    }

    public void setStructMemberLSBName(String structMemberLSBName) {
        this.structMemberLSBName = structMemberLSBName;
    }

    public Object getStructMemberLSBName() {
        return structMemberLSBName;
    }    
    
    public boolean isHwObjectAddressField()
    {
        return name.equals("address") && isHWObjectField;
    }
}
