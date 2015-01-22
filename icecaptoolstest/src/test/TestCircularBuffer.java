package test;

import java.io.IOException;

import devices.Console;

import util.CircularBuffer;

public class TestCircularBuffer {

    public static void main(String args[]) {
        args = test(args);
    }

    public static String[] test(String[] args) {
        CircularBuffer cbuf = new CircularBuffer((short) 10);
        try {
            if (cbuf.isEmpty()) {
                cbuf.write('h');
                if (!cbuf.isEmpty()) {
                    cbuf.write('e');
                    cbuf.write('l');
                    cbuf.write('l');
                    cbuf.write('o');

                    StringBuffer strbuf = new StringBuffer();
                    strbuf.append((char) cbuf.read());
                    strbuf.append((char) cbuf.read());
                    strbuf.append((char) cbuf.read());
                    strbuf.append((char) cbuf.read());
                    strbuf.append((char) cbuf.read());

                    if (cbuf.isEmpty()) {
                        // Console.println(strbuf.toString());
                        if (strbuf.toString().equals("hello")) {
                            return testSpecialCases(cbuf);
                        }
                    } else {
                        throw new IOException("failed to empty buffer");
                    }
                } else {
                    throw new IOException("failed to write to empty buffer");
                }
            } else {
                throw new IOException("failed to create empty buffer");
            }
        } catch (IOException e) {
            Console.println(e.getMessage());
        }
        return args;
    }

    private static String[] testSpecialCases(CircularBuffer cbuf) throws IOException {
        testWrapAround(cbuf);
        testReadFromEmptyBuffer(cbuf);
        testWriteToFullBuffer(cbuf);
        return null;
    }

    private static void testWrapAround(CircularBuffer cbuf) throws IOException {
        cbuf.write('h');
        cbuf.write('e');
        cbuf.write('l');
        cbuf.write('l');
        cbuf.write('o');
        cbuf.write('!');

        StringBuffer strBuf = new StringBuffer();
        while (!cbuf.isEmpty()) {
            strBuf.append((char) cbuf.read());
        }
        if (strBuf.toString().equals("hello!")) {
            return;
        }
        throw new IOException("testWrapAround failed");
    }

    private static void testWriteToFullBuffer(CircularBuffer cbuf) throws IOException {
        for (int i = 0; i < cbuf.capacity(); i++) {
            try {
                cbuf.write('x');
            } catch (IOException e) {
                throw new IOException("Unexpected overflow");
            }
        }

        try {
            cbuf.write((byte) 0);
        } catch (IOException e) {
            return;
        }
        throw new IOException("Expected overflow missing");
    }

    private static void testReadFromEmptyBuffer(CircularBuffer cbuf) throws IOException {
        try {
            cbuf.read();
        } catch (IOException e) {
            return;
        }
        throw new IOException("testReadFromEmptyBuffer failed");
    }
}
