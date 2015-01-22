package icecaptools.compiler;

import icecaptools.Activator;
import icecaptools.extensions.ExtensionManager;

import org.apache.bcel.classfile.Method;

import extensions.NativeMethodCodeGenerator;

public class EclipseNativeMethodDetector extends ExtensionManager<NativeMethodCodeGenerator> implements NativeMethodDetector {

	public void startAnalysis() {
		for (NativeMethodCodeGenerator extension : this.getExtensions()) {
			extension.startNativeFunctionAnalysis();
		}
	}
	
	public void endAnalysis() {
		for (NativeMethodCodeGenerator extension : this.getExtensions()) {
			extension.endNativeFunctionAnalysis();
		}
	}

	public void newUserDefinedNativeMehtod(int methodNumber,
			String uniqueMethodIdentifier, Method javaMethod) {

		for (NativeMethodCodeGenerator extension : this.getExtensions()) {
			extension.newUserNativeFunction(methodNumber,
					uniqueMethodIdentifier, javaMethod);
		}

	}

	@Override
	protected String getCodeGeneratorExtensionPoint() {
		return Activator.PLUGIN_ID + ".NativeMethodCodeGenerator";
	}

	@Override
	protected String getCodeGeneratorExtensionElement() {
		return "class";
	}


}

