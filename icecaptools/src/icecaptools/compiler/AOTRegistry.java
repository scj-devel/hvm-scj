package icecaptools.compiler;

import test.icecaptools.DefaultCompilationRegistry;
import icecaptools.MethodOrFieldDesc;

public class AOTRegistry extends DefaultCompilationRegistry {

    @Override
    public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
        if (mdesc.getName().compareTo("main") == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
        return super.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
    }

    @Override
    public boolean alwaysClearOutputFolder() {
        return super.alwaysClearOutputFolder();
    }
}
