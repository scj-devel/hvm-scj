package icecaptools.compiler;

import test.icecaptools.DefaultCompilationRegistry;

public class TestCompilationRegistry extends DefaultCompilationRegistry {

    @Override
    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
        return super.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
    }
}
