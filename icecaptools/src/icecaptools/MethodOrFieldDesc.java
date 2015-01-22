package icecaptools;

/**
 * This class encapsulates method description by set of String parameters
 * 
 * @TODO: consult use of StringTokenizer instead
 */
public class MethodOrFieldDesc implements Comparable<MethodOrFieldDesc>, MethodIdentifier {
    private String className;
    private String name;
    private String signature;

    public MethodOrFieldDesc(String className, String name, String signature) {
        this.className = className;
        this.name = name;
        this.signature = signature;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object oth) {
        if (oth instanceof MethodOrFieldDesc) {
            MethodOrFieldDesc other = (MethodOrFieldDesc) oth;

            if (other.getName().equals(getName())) {
                if (other.getSignature().equals(getSignature())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return className + ":" + name + ":" + signature;
    }

    public boolean equalsWithClass(String className, MethodOrFieldDesc desc) {
        return this.className.equals(className) && equals(desc);
    }

    @Override
    public int compareTo(MethodOrFieldDesc o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public int getGenericValue() {
        return 0;
    }

    @Override
    public void setGenericValue(int m) {
    }
}
