package util;

public interface ICompilationRegistry {

	public boolean didICareHuh();
	
    public boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature);

    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature);
    
    public boolean alwaysClearOutputFolder();
}
