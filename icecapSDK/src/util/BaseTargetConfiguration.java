package util;

public class BaseTargetConfiguration implements ICompilationRegistry {

	@Override
	public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
		return false;
	}

	@Override
	public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
		return false;
	}

	@Override
	public boolean alwaysClearOutputFolder() {
		return false;
	}
}
