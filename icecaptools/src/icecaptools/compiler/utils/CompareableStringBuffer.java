package icecaptools.compiler.utils;

public class CompareableStringBuffer {
    private StringBuffer buffer;

    public CompareableStringBuffer() {
        this.buffer = new StringBuffer();
    }

    public void append(char next) {
        buffer.append(next);
    }

    public void append(String str) {
        buffer.append(str);
    }
    
    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompareableStringBuffer)
        {
            CompareableStringBuffer other = (CompareableStringBuffer) obj;
            int thisLength = buffer.length();
            int otherLength = other.length();
            if (thisLength == otherLength)
            {
                return buffer.toString().equals(other.toString());
            }
        }
        return false;
    }

    private int length() {
        return buffer.length();
    }    
}
