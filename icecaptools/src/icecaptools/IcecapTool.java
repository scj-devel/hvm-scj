package icecaptools;

import icecaptools.compiler.CodeDetector;
import icecaptools.compiler.IcecapCodeFormatter;
import icecaptools.compiler.IcecapSourceCodeLinker;
import icecaptools.compiler.NativeMethodDetector;

public interface IcecapTool {
    HVMProperties getProperties() throws Exception;
    IcecapCodeFormatter getCodeFormatter();
    IcecapSourceCodeLinker getSourceCodeLinker();
    CodeDetector getCodeDetector();
    NativeMethodDetector getNativeMethodDetector();
}
