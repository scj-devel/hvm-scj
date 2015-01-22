package icecaptools.compiler.utils;

import java.io.IOException;
import java.io.OutputStream;

public abstract class NewlineConverter {

    public abstract void convert(StringBuffer normalized, char charAt);

    public abstract void convert(OutputStream writer, int next) throws IOException;
}
