package extensions;

import org.apache.bcel.classfile.Method;

public interface NativeMethodCodeGenerator {

	public void startNativeFunctionAnalysis();

	public void newUserNativeFunction(int methodNumber,String uniqueMethodIdentifier, Method method);

	public void endNativeFunctionAnalysis();

}
