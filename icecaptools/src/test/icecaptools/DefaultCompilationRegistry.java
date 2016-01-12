package test.icecaptools;

import util.ICompilationRegistry;
import util.MethodOrFieldDesc;

public class DefaultCompilationRegistry implements ICompilationRegistry {

	private ICompilationRegistry delegate;

	public DefaultCompilationRegistry(ICompilationRegistry delegate) {
		this.delegate = delegate;
	}

	public DefaultCompilationRegistry() {
		delegate = new ICompilationRegistry() {

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
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

	@Override
	public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
		if (mdesc.getClassName().contains("jml")) {
			return true;
		}
		if (mdesc.getClassName().startsWith("sun.security.action.GetPropertyAction")) {
			return true;
		}
		if (mdesc.getClassName().startsWith("java.io.BufferedWriter")) {
			return true;
		}

		if (mdesc.getClassName().startsWith("java.io.PrintStream")) {
			return true;
		}
		return delegate.isMethodCompiled(mdesc);
	}

	@Override
	public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
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
		return delegate.isMethodExcluded(clazz, targetMethodName, targetMethodSignature);
	}

	@Override
	public boolean alwaysClearOutputFolder() {
		return false;
	}
}
