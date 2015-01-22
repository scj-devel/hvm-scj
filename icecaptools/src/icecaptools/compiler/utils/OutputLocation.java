package icecaptools.compiler.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputLocation {

    FileOutputStream fos;
    
    public OutputLocation(File nhFile) throws FileNotFoundException {
        fos = new FileOutputStream(nhFile);
    }

    public OutputLocation() {
        fos = null;
    }

    public void write(byte[] bytes) throws IOException {
        if (fos != null)
        {
            fos.write(bytes);
        }
        
    }

    public void flush() throws IOException {
        if (fos != null)
        {
            fos.flush();
        }
    }

    public void close() throws IOException {
        if (fos != null)
        {
            fos.close();
        }
    }
}
