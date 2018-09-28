package test.jmocket.test;

public class Foo
{
    public native int getBar();

    public String toString()
    {
        return "Bar: " + getBar();
    }
}
