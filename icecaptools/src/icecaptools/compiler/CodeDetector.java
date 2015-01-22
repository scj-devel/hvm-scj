package icecaptools.compiler;

import java.io.FileOutputStream;

public interface CodeDetector {

    void newRead(char next);

    void fileStart(String resourceName, FileOutputStream writer);

}
