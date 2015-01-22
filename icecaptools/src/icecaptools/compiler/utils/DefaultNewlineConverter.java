package icecaptools.compiler.utils;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultNewlineConverter extends NewlineConverter {    
    @Override
    public void convert(StringBuffer buffer, char c) {
        buffer.append(c);
    }

    @Override
    public void convert(OutputStream writer, int next) throws IOException {
        writer.write(next);
    }
}
