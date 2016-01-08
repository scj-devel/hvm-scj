package icecaptools.launching;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import icecaptools.actions.ConvertJavaFileAction;

public class IsHVMapplicationPropertyTester extends PropertyTester {

	public IsHVMapplicationPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		try {
			if (receiver != null) {
				if (receiver instanceof ICompilationUnit) {
					return checkCompilationUnit((ICompilationUnit) receiver);
				} else if (receiver instanceof IType) {
					return checkSourceType((IType) receiver);
				} else if (receiver instanceof IMethod) {
					return checkMethod((IMethod) receiver);
				}
			}
		} catch (Throwable t) {
			return false;
		}
		return false;
	}

	private boolean checkMethod(IMethod receiver) {
		IType type = receiver.getDeclaringType();
		while (type.getDeclaringType() != null) {
			type = type.getDeclaringType();
		}
		try {
			return checkType(type);
		} catch (JavaModelException e) {
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return false;
	}

	private boolean checkSourceType(IType receiver) {
		IType type = receiver;
		while (type.getDeclaringType() != null) {
			type = type.getDeclaringType();
		}

		try {
			return checkType(type);
		} catch (JavaModelException e) {
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return false;
	}

	private boolean checkCompilationUnit(ICompilationUnit receiver) {
		IType type = receiver.findPrimaryType();
		try {
			return checkType(type);
		} catch (JavaModelException e) {
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return false;
	}

	private boolean checkType(IType type) throws JavaModelException, ClassNotFoundException, IOException {
		String mainClass = type.getFullyQualifiedName();

		StringBuffer classPath = ConvertJavaFileAction.getClasspathFromProject(type.getJavaProject());

		System.out.println("Got class path!");

		String[] elements = classPath.toString().split(System.getProperty("path.separator"));

		URL[] urls = new URL[elements.length];

		for (int i = 0; i < elements.length; i++) {
			urls[i] = new File(elements[i]).toURI().toURL();
		}

		URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());

		Class<?> mainClazz = null;

		mainClazz = loader.loadClass(mainClass);

		while (mainClazz != null) {
			Class<?>[] interfaces = mainClazz.getInterfaces();
			for (Class<?> iface : interfaces) {
				if (iface.getName().equals(devices.TargetConfiguration.class.getName())) {
					loader.close();
					return true;
				}
			}
			mainClazz = mainClazz.getSuperclass();
		}
		loader.close();
		return false;
	}
}
