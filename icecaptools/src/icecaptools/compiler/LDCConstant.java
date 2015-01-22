package icecaptools.compiler;

public class LDCConstant {

    public static final int STRING = 1;

    public static final int INTEGER = 2;

    public static final int FLOAT = 3;
    
    public static final int LONG = 4;
    
    public static final int DOUBLE = 5;

    public static final int CLASS = 6;

    private int type;
    private String string;
    private int integer;
    private float floatVal;
    private String className;
    private long longVal;

    private double doubleVal;

    public LDCConstant(String string) {
        this.type = STRING;
        this.string = string;
    }

    public LDCConstant(int integer) {
        this.type = INTEGER;
        this.integer = integer;
    }

    public LDCConstant(float floatVal) {
        this.type = FLOAT;
        this.floatVal = floatVal;
    }

    public LDCConstant(long longVal) {
        this.type = LONG;
        this.longVal = longVal;
    }
    
    public LDCConstant(boolean isClassReference, String className) {
        this.type = CLASS;
        this.className = className;
    }

    public LDCConstant(double val) {
        this.type = DOUBLE;
        this.doubleVal = val;
    }

    public int getType() {
        return type;
    }

    public String getString() {
        return string;
    }

    public int getInt() {
        return integer;
    }

    public float getFloat() {
        return floatVal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LDCConstant) {
            LDCConstant other = (LDCConstant) obj;
            if (other.type == type) {
                if (type == STRING) {
                    return other.string.equals(string);
                } else if (type == INTEGER) {
                    return other.integer == this.integer;
                } else if (type == LONG) {
                    return other.longVal == this.longVal;
                } else if (type == DOUBLE) {
                    return other.doubleVal == this.doubleVal;
                } else if (type == FLOAT) {
                    return other.floatVal == this.floatVal;
                } else if (type == CLASS) {
                    return other.className.equals(className);
                }
            }
        }
        return false;
    }

    public String getClassName() {
        return className;
    }

    public long getLong() {
        return longVal;
    }

    public double getDouble() {
        return doubleVal;
    }
}
