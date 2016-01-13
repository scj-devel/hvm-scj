package test.icecaptools;

import util.ICompilationRegistry;

public class DefaultCompilationRegistry implements ICompilationRegistry {

	private boolean doIcareHuh;

	@Override
	public boolean isMethodCompiled(String clazz, String targetMethodName, String targetMethodSignature) {
		doIcareHuh = true;
		if (clazz.contains("jml")) {
			return true;
		}
		if (clazz.startsWith("sun.security.action.GetPropertyAction")) {
			return true;
		}
		if (clazz.startsWith("java.io.BufferedWriter")) {
			return true;
		}

		if (clazz.startsWith("java.io.PrintStream")) {
			return true;
		}
		doIcareHuh = false;
		return false;
	}

	@Override
	public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
		doIcareHuh = true;
		if (clazz.startsWith("sun.")) {
			if (clazz.startsWith("sun.security.action.GetPropertyAction")) {
				return false;
			}
			return true;
		}
		if (clazz.startsWith("java.util.concurrent")) {
			return true;
		}
		if (clazz.startsWith("java.io")) {
			if (clazz.startsWith("java.io.FilterInputStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.InputStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.OutputStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.DataInputStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.DataOutputStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.PrintStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.FilterOutputStream")) {
				return false;
			}
			if (clazz.startsWith("java.io.Writer")) {
				return false;
			}
			if (clazz.startsWith("java.io.BufferedWriter")) {
				return false;
			}
			if (clazz.startsWith("java.io.OutputStreamWriter")) {
				return false;
			}
			if (clazz.startsWith("java.io.OutputStream") && targetMethodName.contains("init")) {
				return false;
			}
			return true;
		}
		if (clazz.startsWith("java.nio")) {
			return true;
		}
		if (clazz.startsWith("java.lang.Thread")) {
			return true;
		}
		if (clazz.startsWith("java.lang.ClassLoader")) {
			return true;
		}
		if (clazz.startsWith("java.lang.System")) {
			if (targetMethodName.startsWith("initProperties")) {
				return true;
			}
			if (targetMethodName.startsWith("setErr0")) {
				return true;
			}
			if (targetMethodName.startsWith("setIn0")) {
				return true;
			}
			if (targetMethodName.startsWith("setOut")) {
				return true;
			}
			if (targetMethodName.startsWith("getProperty")) {
				return true;
			}
		}
		if (clazz.startsWith("java.lang.reflect")) {
			if (clazz.equals("java.lang.reflect.Array") && targetMethodName.equals("newInstance")) {
				return false;
			}
			return true;
		}
		doIcareHuh = false;
		return false;
	}

	@Override
	public boolean alwaysClearOutputFolder() {
		doIcareHuh = false;
		return false;
	}

	@Override
	public boolean didICareHuh() {
		return doIcareHuh;
	}
}
