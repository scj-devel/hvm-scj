package icecaptools.compiler;

import icecaptools.HVMProperties;

public class MemorySegment {

    public static int codeBytes;

    public static int dataBytes;

    private StringBuffer buffer;

    private HVMProperties props;

    static
    {
        codeBytes = 0;
        dataBytes = 0;
    }
    
    public MemorySegment(HVMProperties props) {
        buffer = new StringBuffer();
        this.props = props;
    }

    public void appendCode(String string, int size) {
        buffer.append(string);
        codeBytes += size;
    }
    
    public void appendData(String string, int size) {
        buffer.append(string);
        dataBytes += size;
    }

    public void print(String string) {
        buffer.append(string);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public void startProgmem() {
        print(props.getProgmemStart() + "\n");
    }

    public void stopProgmem() {
        print(props.getProgmemEnd() + "\n");
    }
}
