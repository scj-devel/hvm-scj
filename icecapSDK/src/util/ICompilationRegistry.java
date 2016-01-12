package util;

public interface ICompilationRegistry {

    public boolean isMethodCompiled(MethodOrFieldDesc mdesc);

    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature);
    
    public boolean didIcareHuh();
    
    public boolean alwaysClearOutputFolder();
}
