package icecaptools.compiler.utils;

import java.io.IOException;
import java.io.OutputStream;

public class CustomNewlineConverter extends NewlineConverter {

    private String newlineSeq;

    public CustomNewlineConverter(String newlineSeq) {
        this.newlineSeq = newlineSeq;
    }

    @Override
    public void convert(StringBuffer normalized, char c) {
        if (c == '\n') {
            normalized.append(newlineSeq);
        } else {
            if (c != '\r') {
                normalized.append(c);
            }
        }
    }

    @Override
    public void convert(OutputStream writer, int next) throws IOException {
        if (next == (int) '\n') {
            for (int i = 0; i < newlineSeq.length(); i++) {
                writer.write(newlineSeq.charAt(i));
            }
        } else {
            if (next != (int) '\r') {
                writer.write(next);
            }
        }
    }
}
