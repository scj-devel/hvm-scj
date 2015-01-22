package test.icecaptools;

import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.ICompilationRegistry;

public class DefaultCompilationRegistry implements ICompilationRegistry {

    @Override
    public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
        if (mdesc.getClassName().contains("jml"))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
        if (clazz.startsWith("sun."))
        {
            return true;
        }
        if (clazz.startsWith("java.util.concurrent"))
        {
            return true;
        }
        if (clazz.startsWith("java.lang.reflect"))
        {
            if (clazz.equals("java.lang.reflect.Array") && targetMethodName.equals("newInstance"))
            {
                return false;
            }
            return true;
        }
        if (clazz.startsWith("java.io.File"))
        {
            return true;
        }
        if (clazz.startsWith("java.lang.Thread"))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean alwaysClearOutputFolder() {
        return false;
    }
}
