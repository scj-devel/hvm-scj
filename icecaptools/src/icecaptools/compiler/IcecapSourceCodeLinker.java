package icecaptools.compiler;

import org.apache.bcel.classfile.Method;

public interface IcecapSourceCodeLinker {

    void getSourceCode(String className, Method javaMethod, int pc, StringBuffer output);

}
