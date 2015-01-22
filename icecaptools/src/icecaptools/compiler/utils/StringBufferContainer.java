package icecaptools.compiler.utils;

public class StringBufferContainer extends StringContainer {

    private StringBuffer buffer;

    public StringBufferContainer(StringBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int length() {
        return buffer.length();
    }

    @Override
    public char charAt(int i) {
        return buffer.charAt(i);
    }

}
