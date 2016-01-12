package icecaptools.compiler;

import util.ICompilationRegistry;
import util.MethodOrFieldDesc;

public class AOTRegistry implements ICompilationRegistry {

	private ICompilationRegistry delegate;

	public AOTRegistry(ICompilationRegistry delegate) {
		this.delegate = delegate;
	}

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
		return delegate.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
	}

	@Override
	public boolean alwaysClearOutputFolder() {
		return delegate.alwaysClearOutputFolder();
	}
}
