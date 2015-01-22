package icecaptools;

import java.io.IOException;
import java.io.InputStream;

import org.apache.bcel.util.ClassPath;

public class IcecapClassPath extends ClassPath {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public IcecapClassPath(String path) {
        super(path);
    }

    @Override
    public InputStream getInputStream(String name, String suffix) throws IOException {
        try {
            ClassFile cfile = super.getClassFile(name, suffix);
            return cfile.getInputStream();
        } catch (IOException ioe) {
            return super.getInputStream(name, suffix);
        }
    }
}
