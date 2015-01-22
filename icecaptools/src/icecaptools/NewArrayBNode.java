package icecaptools;

import org.apache.bcel.classfile.JavaClass;

public class NewArrayBNode extends NewBNode {
    private int type;
    private boolean isFlashArray;

    public NewArrayBNode(JavaClass newType, int address, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, newType, locationClass, locationMethod, locationMethodSignature);
        type = -1;
        isFlashArray = false;
    }

    public NewArrayBNode(int type, int address, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, null, locationClass, locationMethod, locationMethodSignature);
        this.type = type;
    }

    public int getBasicType()
    {
        return type;
    }
    
    public static String getBasicTypeAsString(int type) {
        switch (type) {
        case 4:
            return "[Z";
        case 8:
            return "[B";
        case 9:
            return "[S";
        case 5:
            return "[C";
        case 6:
            return "[F";
        case 10:
            return "[I";
        case 7:
            return "[D";
        case 11:
            return "[J";
        }
        return null;
    }

    public boolean isBasicType()
    {
        return (type != -1);
    }
    
    @Override
    public String getType() {
        if (type != -1) {
            return getBasicTypeAsString(type);
        } else {
            return "[L" + super.getType() + ";";
        }
    }

    @Override
    public boolean requiresExtension() {
        if (rawBytes[0] == RawByteCodes.newarray_opcode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int extend() throws Exception {
        byte[] rawBytes = new byte[3];
        rawBytes[0] = this.rawBytes[0];
        rawBytes[1] = this.rawBytes[1];
        this.rawBytes = rawBytes;
        return 1;
    }

    public void setFlashArray() {
        this.isFlashArray = true;
    }

    public boolean isFlashArray() {
        return isFlashArray;
    }
}
