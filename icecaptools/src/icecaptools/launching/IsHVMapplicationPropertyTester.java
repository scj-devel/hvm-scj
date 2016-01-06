package icecaptools.launching;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class IsHVMapplicationPropertyTester extends PropertyTester {

	public IsHVMapplicationPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver != null) {
			if (receiver instanceof ICompilationUnit) {
				return checkCompilationUnit((ICompilationUnit) receiver);
			} else if (receiver instanceof IType) {
				return checkSourceType((IType) receiver);
			} else if (receiver instanceof IMethod) {
				return checkMethod((IMethod) receiver);
			}
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
			return false;
		}
	}

	private boolean checkSourceType(IType receiver) {
		IType type = receiver;
		while (type.getDeclaringType() != null) {
			type = type.getDeclaringType();
		}

		try {
			return checkType(type);
		} catch (JavaModelException e) {
			return false;
		}
	}

	private boolean checkCompilationUnit(ICompilationUnit receiver) {
		IType type = receiver.findPrimaryType();
		try {
			return checkType(type);
		} catch (JavaModelException e) {
		}
		return false;
	}

	private boolean checkType(IType type) throws JavaModelException {
		String[] interfaces = type.getSuperInterfaceNames();
		String name = devices.TargetConfiguration.class.getName();
		String elements[] = name.split("\\.");
		for (String str : interfaces) {
			if (str.equals(elements[elements.length - 1])) {
				return true;
			}
		}
		return false;
	}
}
