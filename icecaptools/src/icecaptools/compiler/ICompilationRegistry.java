package icecaptools.compiler;

import icecaptools.MethodOrFieldDesc;

public interface ICompilationRegistry {

    public boolean isMethodCompiled(MethodOrFieldDesc mdesc);

    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature);
    
    public boolean alwaysClearOutputFolder();
}
