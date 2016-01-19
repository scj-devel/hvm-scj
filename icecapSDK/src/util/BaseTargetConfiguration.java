package util;

public abstract class BaseTargetConfiguration implements ICompilationRegistry {

	protected enum EBOOL {
		YES, NO, DONTCARE
	};

	private boolean doICare;

	@Override
	public final boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature) {
		EBOOL eb = compileMethod(clazz, targetMethodName, targetMethodSignature);
		boolean result = convertValue(eb);
		return result;
	}

	@Override
	public final boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
		EBOOL eb = excludeMethod(clazz, targetMethodName, targetMethodSignature);
		boolean result = convertValue(eb);
		return result;
	}
	
	protected EBOOL excludeMethod(String clazz, String targetMethodName, String targetMethodSignature) {
		return EBOOL.DONTCARE;
	}

	protected EBOOL compileMethod(String clazz, String targetMethodName, String targetMethodSignature) {
		return EBOOL.DONTCARE;
	}

	@Override
	public boolean alwaysClearOutputFolder() {
		doICare = false;
		return false;
	}

	@Override
	public final boolean didICareHuh() {
		return doICare;
	}

	private boolean convertValue(EBOOL eb) {
		boolean result;

		switch (eb) {
		case YES:
			result = true;
			break;
		default:
			result = false;
		}

		if (eb == EBOOL.DONTCARE) {
			doICare = false;
		} else {
			doICare = true;
		}
		return result;
	}
}
