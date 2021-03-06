package icecaptools.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import icecaptools.Activator;
import icecaptools.IcecapCFunc;
import icecaptools.IcecapInlineNative;
import icecaptools.compiler.utils.StringUtils;

public class NativeFileManager {
	private StringBuffer sourceFileContent;
	private StringBuffer headerFileContent;
	private StringBuffer nativeFileHeader;
	private StringBuffer nativeFileHostSource;
	private StringBuffer nativeFileTargetSource;

	private ArrayList<String> nativeFunctions;
	private boolean functionsSorted;
	private ArrayList<String> compiledFunctions;
	private LabeledMemorySegment requiredIncludes;

	public static final String UserNativeFunctionExtensionPointId = Activator.PLUGIN_ID + ".UserNativeFunction";
	public static final String UserNativeFunctionExtensionPointElement = "class";

	public NativeFileManager(boolean nUMBEROFCLASSES_varUsed, int numberOfClasses,
			LabeledMemorySegment requiredIncludes) {
		this.requiredIncludes = requiredIncludes;

		sourceFileContent = new StringBuffer();
		headerFileContent = new StringBuffer();

		nativeFileHeader = new StringBuffer();
		nativeFileHostSource = new StringBuffer();
		nativeFileTargetSource = new StringBuffer();

		nativeFileHeader.append("#ifndef NATIVES_H_\n");
		nativeFileHeader.append("#define NATIVES_H_\n");
		nativeFileHeader.append("#include \"ostypes.h\"\n\n");

		nativeFileHeader.append("typedef int16 (*fptr)(int32 *sp);\n\n");
		nativeFileHeader.append("fptr readNativeFunc(void);\n\n");
		nativeFileHeader.append("void dumpNativeFunc(int16(*nativeFunc)(int32 *sp), const char* functionName);\n");
		nativeFileHeader.append("int16 unknownNativeFunc(int32 *sp);\n");
		nativeFileHeader.append("#define UNKNOWNNATIVEFUNC 42\n");

		nativeFileTargetSource.append("#include \"natives.h\"\n\n");
		nativeFileTargetSource.append("extern unsigned char readByte();\n");
		nativeFileTargetSource.append("extern void printStr(const char* str);\n");
		if (nUMBEROFCLASSES_varUsed) {
			nativeFileTargetSource.append("\nRANGE uint16 NUMBEROFCLASSES_var = " + numberOfClasses + ";\n\n");
		}
		nativeFileTargetSource.append("fptr readNativeFunc(void) {\n");
		nativeFileTargetSource.append("    unsigned char b = readByte();\n");
		nativeFileTargetSource.append("    switch (b) {\n");

		nativeFileHostSource.append("#include \"natives.h\"\n\n");
		nativeFileHostSource.append("#include <stdio.h>\n\n");
		nativeFileHostSource.append("#include <stdlib.h>\n\n");
		nativeFileHostSource.append("extern void dumpByte(unsigned char b);\n");
		nativeFileHostSource.append("void dumpNativeFunc(int16(*nativeFunc)(int32 *sp), const char* functionName) {\n");

		nativeFunctions = new ArrayList<String>();
		compiledFunctions = new ArrayList<String>();
		functionsSorted = false;
	}

	public void addCompiledMethod(int methodNumber, String uniqueMethodId, Method javaMethod, boolean skipMethodHack) {
		compiledFunctions.add(uniqueMethodId);
	}

	public void addNativeMethod(int methodNumber, String uniqueMethodId, Method javaMethod, boolean skipMethod) {
		if (javaMethod.isNative() || skipMethod) {
			sourceFileContent.append("/* " + javaMethod.getName() + "\n");
			sourceFileContent.append(" * param : " + getParameters(javaMethod) + "\n");
			sourceFileContent.append(" * return: " + javaMethod.getReturnType().toString() + "\n");
			sourceFileContent.append(" */\n");
			String signature;

			IcecapCFunc cfunc = Compiler.hasAnnotation(javaMethod, IcecapCFunc.class);
			if (cfunc == null) {
				signature = "int16 " + uniqueMethodId + "(int32* sp)";
			} else {
				signature = cfunc.signature();
				String includes = cfunc.requiredIncludes();
				if ((includes != null) && (includes.trim().length() > 0))
				{
					StringUtils.forEachLine(includes, (include) -> this.requiredIncludes.print(include + "\n"));
				}
			}

			if (skipMethod) {
				sourceFileContent.append("#ifndef EXCLUDESTUB_" + uniqueMethodId.toUpperCase() + "\n");
				sourceFileContent.append(signature + "\n");
				sourceFileContent.append("{\n");
				sourceFileContent.append("   unimplemented_native_function(" + uniqueMethodId.toUpperCase() + ");\n");
				sourceFileContent.append("   return -1;\n");
				sourceFileContent.append("}\n");
				sourceFileContent.append("#else\n");

				StringBuffer prototype = new StringBuffer();
				prototype.append("#ifndef EXCLUDESTUB_" + uniqueMethodId.toUpperCase() + "\n");
				prototype.append(signature + ";\n");
				prototype.append("#endif\n");
				this.requiredIncludes.print(prototype.toString());
			}
			IcecapInlineNative inline = Compiler.hasAnnotation(javaMethod, IcecapInlineNative.class);
			if (inline != null) {
				sourceFileContent.append(signature + "\n");
				sourceFileContent.append(inline.functionBody());
				sourceFileContent.append("\n");

				String requiredIncludes = inline.requiredIncludes();
				if ((requiredIncludes != null) && (requiredIncludes.trim().length() > 0)) {
					StringUtils.forEachLine(requiredIncludes, (include) -> this.requiredIncludes.print(include + "\n"));
				}
				this.requiredIncludes.print(signature + ";\n");
			} else {
				sourceFileContent.append("extern int16 " + uniqueMethodId + "(int32 *sp);\n");
			}
			if (skipMethod) {
				sourceFileContent.append("#endif\n\n");
			}

			if (cfunc == null) {
				nativeFunctions.add(uniqueMethodId);
			}	

			headerFileContent.append("#define ");
			headerFileContent.append(uniqueMethodId.toUpperCase());
			headerFileContent.append(" " + methodNumber);
			headerFileContent.append("\n");
		}
	}

	public static String getParameters(Method javaMethod) {
		StringBuffer buffer = new StringBuffer();
		Type[] arguments = javaMethod.getArgumentTypes();
		if (arguments != null) {
			for (int count = 0; count < arguments.length; count++) {
				buffer.append(arguments[count].toString());
				if (count < arguments.length - 1) {
					buffer.append(", ");
				}
			}
		}
		return buffer.toString();
	}

	public String getDeclerations(StringBuffer additionalHeaderFileContent) {
		additionalHeaderFileContent.append(headerFileContent);
		return sourceFileContent.toString();
	}

	public String getNativeHeader() {
		int numCount = 43;
		ensureArray();

		Iterator<String> functionsItr = nativeFunctions.iterator();
		while (functionsItr.hasNext()) {
			numCount = addMethodToHeader(numCount, functionsItr);
		}
		functionsItr = compiledFunctions.iterator();
		while (functionsItr.hasNext()) {
			numCount = addMethodToHeader(numCount, functionsItr);
		}
		nativeFileHeader.append("#endif /* NATIVES_H_ */\n");
		return nativeFileHeader.toString();
	}

	private int addMethodToHeader(int numCount, Iterator<String> functionsItr) {
		String uniqueMethodId = functionsItr.next();
		nativeFileHeader.append("#define ");
		nativeFileHeader.append(uniqueMethodId.toUpperCase());
		nativeFileHeader.append("_NUM " + numCount + "\n");
		nativeFileHeader.append("int16 " + uniqueMethodId + "(int32 *sp);\n\n");
		numCount++;
		return numCount;
	}

	private void ensureArray() {
		if (!functionsSorted) {
			Object[] array = nativeFunctions.toArray();
			Arrays.sort(array);

			nativeFunctions = new ArrayList<String>();
			for (int i = 0; i < array.length; i++) {
				nativeFunctions.add((String) array[i]);
			}
			functionsSorted = true;
		}
	}

	public String getNativeTargetSource() {
		ensureArray();
		Iterator<String> functionsItr = nativeFunctions.iterator();

		while (functionsItr.hasNext()) {
			handleMethodInTargetFile(functionsItr.next());
		}
		functionsItr = compiledFunctions.iterator();

		while (functionsItr.hasNext()) {
			handleMethodInTargetFile(functionsItr.next());
		}

		nativeFileTargetSource.append("    }\n");
		nativeFileTargetSource.append("    return unknownNativeFunc;\n");
		nativeFileTargetSource.append("}\n");
		return nativeFileTargetSource.toString();
	}

	public String getNativeDispatcher() {
		StringBuffer dispatcher = new StringBuffer();
		dispatcher.append("int16 dispatch_native_func(int16 functionNumber, int32 *sp)\n");
		dispatcher.append("{\n");
		dispatcher.append("   switch (functionNumber) {\n");
		Iterator<String> functionsItr = nativeFunctions.iterator();

		while (functionsItr.hasNext()) {
			String functionName = functionsItr.next();
			dispatcher.append("    case " + functionName.toUpperCase() + ":\n");
			dispatcher.append("       return " + functionName + "(sp);\n");
		}
		functionsItr = compiledFunctions.iterator();

		while (functionsItr.hasNext()) {
			String functionLabel = functionsItr.next();
			dispatcher.append("    case " + functionLabel.toUpperCase() + ":\n");
			dispatcher.append("       return " + functionLabel + "(sp);\n");
		}

		dispatcher.append("    default:\n");
		dispatcher.append("       return JAVA_LANG_NULLPOINTEREXCEPTION_var;\n");
		dispatcher.append("   }\n");
		dispatcher.append("}\n");
		return dispatcher.toString();
	}

	public String getNativeHostSource() {
		StringBuffer nativeStubs = new StringBuffer();
		boolean oneTime = true;
		ensureArray();

		Iterator<String> functionsItr = nativeFunctions.iterator();
		while (functionsItr.hasNext()) {
			oneTime = handleMethodInHostFile(nativeStubs, oneTime, functionsItr, false);
		}

		functionsItr = compiledFunctions.iterator();
		while (functionsItr.hasNext()) {
			oneTime = handleMethodInHostFile(nativeStubs, oneTime, functionsItr, false);
		}

		nativeFileHostSource.append("    } else {\n");
		nativeFileHostSource.append("        dumpByte(UNKNOWNNATIVEFUNC);\n");
		nativeFileHostSource.append("    }\n");
		nativeFileHostSource.append("}\n\n");

		nativeFileHostSource.append(nativeStubs.toString());
		return nativeFileHostSource.toString();
	}

	private void handleMethodInTargetFile(String uniqueMethodId) {
		nativeFileTargetSource.append("    case " + uniqueMethodId.toUpperCase() + "_NUM:\n");
		nativeFileTargetSource.append("        return " + uniqueMethodId + ";\n");
	}

	private boolean handleMethodInHostFile(StringBuffer nativeStubs, boolean oneTime, Iterator<String> functionsItr,
			boolean addStub) {
		String uniqueMethodId = functionsItr.next();
		if (oneTime) {
			nativeFileHostSource.append("    if (nativeFunc == " + uniqueMethodId + ") {\n");
			oneTime = false;
		} else {
			nativeFileHostSource.append("    } else if (nativeFunc == " + uniqueMethodId + ") {\n");
		}
		nativeFileHostSource.append("        dumpByte(" + uniqueMethodId.toUpperCase() + "_NUM);\n");

		if (addStub) {
			nativeStubs.append("int16 " + uniqueMethodId + "(int32 *sp) {\n");
			nativeStubs.append("   return -1;\n");
			nativeStubs.append("}\n\n");
		}
		return oneTime;
	}

}
