package icecaptools;

public class MethodLocation {
    private int methodNumber;
    private int address;
    private int lineNumber;
    
    public MethodLocation(int methodNumber, int address, int lineNumber) {
        this.methodNumber = methodNumber;
        this.address = address;
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public short getMethodNumber() {
        return (short) methodNumber;
    }

    public short getAddress() {
        return (short)address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodLocation)
        {
            MethodLocation other = (MethodLocation) obj;
            return (methodNumber == other.methodNumber) && (address == other.address);
        }
        return false;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public int hashCode() {
        return methodNumber << 16 | address;
    }
}
