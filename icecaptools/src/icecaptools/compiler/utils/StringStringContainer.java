package icecaptools.compiler.utils;

public class StringStringContainer extends StringContainer {
    private String string;

    public StringStringContainer(String string) {
        this.string = string;
    }

    @Override
    public int length() {
        return string.length();
    }

    @Override
    public char charAt(int i) {
        return string.charAt(i);
    }
}
