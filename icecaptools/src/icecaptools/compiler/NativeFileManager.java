package icecaptools.compiler;

import icecaptools.Activator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

public class NativeFileManager {
    private StringBuffer sourceFileContent;
    private StringBuffer headerFileContent;
    private StringBuffer nativeFileHeader;
    private StringBuffer nativeFileHostSource;
    private StringBuffer nativeFileTargetSource;

    private ArrayList<String> functions;
    private boolean functionsSorted;
    
    public static final String UserNativeFunctionExtensionPointId = Activator.PLUGIN_ID + ".UserNativeFunction";
    public static final String UserNativeFunctionExtensionPointElement = "class";

    public NativeFileManager() {
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

        nativeFileTargetSource.append("#include \"natives.h\"\n\n");
        nativeFileTargetSource.append("extern unsigned char readByte();\n");
        nativeFileTargetSource.append("extern void printStr(const char* str);\n");
        nativeFileTargetSource.append("fptr readNativeFunc(void) {\n");
        nativeFileTargetSource.append("    unsigned char b = readByte();\n");
        nativeFileTargetSource.append("    switch (b) {\n");

        nativeFileHostSource.append("#include \"natives.h\"\n\n");
        nativeFileHostSource.append("#include <stdio.h>\n\n");
        nativeFileHostSource.append("#include <stdlib.h>\n\n");
        nativeFileHostSource.append("extern void dumpByte(unsigned char b);\n");
        nativeFileHostSource.append("void dumpNativeFunc(int16(*nativeFunc)(int32 *sp), const char* functionName) {\n");

        functions = new ArrayList<String>();
        functionsSorted = false;
    }

    public void addNativeMethod(int methodNumber, String uniqueMethodId, Method javaMethod, boolean skipMethod) {
        if (javaMethod.isNative() || skipMethod) {
        	
        	
            sourceFileContent.append("/* " + javaMethod.getName() + "\n");
            sourceFileContent.append(" * param : " + getParameters(javaMethod) + "\n");
            sourceFileContent.append(" * return: " + javaMethod.getReturnType().toString() + "\n");
            sourceFileContent.append(" */\n");
            if (skipMethod) {
                sourceFileContent.append("#ifndef EXCLUDESTUB_" + uniqueMethodId.toUpperCase() + "\n");
                sourceFileContent.append("int16 " + uniqueMethodId + "(int32 *sp)\n");
                sourceFileContent.append("{\n");
                sourceFileContent.append("   unimplemented_native_function(" + uniqueMethodId.toUpperCase() + ");\n");
                sourceFileContent.append("   return -1;\n");
                sourceFileContent.append("}\n");
                sourceFileContent.append("#else\n");
            }
            sourceFileContent.append("extern int16 " + uniqueMethodId + "(int32 *sp);\n");
            if (skipMethod) {
                sourceFileContent.append("#endif\n\n");
            }
            functions.add(uniqueMethodId);

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
        int numCount = 42;
        ensureArray();

        Iterator<String> functionsItr = functions.iterator();
        while (functionsItr.hasNext()) {
            String uniqueMethodId = functionsItr.next();
            nativeFileHeader.append("#define ");
            nativeFileHeader.append(uniqueMethodId.toUpperCase());
            nativeFileHeader.append("_NUM " + numCount + "\n");
            nativeFileHeader.append("int16 " + uniqueMethodId + "(int32 *sp);\n\n");
            numCount++;
        }
        nativeFileHeader.append("#endif /* NATIVES_H_ */\n");
        return nativeFileHeader.toString();
    }

    private void ensureArray() {
        if (!functionsSorted) {
            Object[] array = functions.toArray();
            Arrays.sort(array);

            functions = new ArrayList<String>();
            for (int i = 0; i < array.length; i++) {
                functions.add((String) array[i]);
            }
            functionsSorted = true;
        }
    }

    public String getNativeTargetSource() {
        ensureArray();
        Iterator<String> functionsItr = functions.iterator();

        while (functionsItr.hasNext()) {
            String uniqueMethodId = functionsItr.next();
            nativeFileTargetSource.append("    case " + uniqueMethodId.toUpperCase() + "_NUM:\n");
            nativeFileTargetSource.append("        return " + uniqueMethodId + ";\n");
        }

        nativeFileTargetSource.append("    }\n");
        nativeFileTargetSource.append("    printStr(\"Unsupported native function\");\n");
        nativeFileTargetSource.append("    return 0;\n");
        nativeFileTargetSource.append("}\n");
        return nativeFileTargetSource.toString();
    }

    public String getNativeHostSource() {
        StringBuffer nativeStubs = new StringBuffer();
        boolean oneTime = true;
        ensureArray();

        Iterator<String> functionsItr = functions.iterator();
        while (functionsItr.hasNext()) {
            String uniqueMethodId = functionsItr.next();
            if (oneTime) {
                nativeFileHostSource.append("    if (nativeFunc == " + uniqueMethodId + ") {\n");
                oneTime = false;
            } else {
                nativeFileHostSource.append("    } else if (nativeFunc == " + uniqueMethodId + ") {\n");
            }
            nativeFileHostSource.append("        dumpByte(" + uniqueMethodId.toUpperCase() + "_NUM);\n");

            nativeStubs.append("int16 " + uniqueMethodId + "(int32 *sp) {\n");
            nativeStubs.append("   return -1;\n");
            nativeStubs.append("}\n\n");
        }

        nativeFileHostSource.append("    } else {\n");
        nativeFileHostSource.append("        printf(\"Unsupported native function (%s)\\n\", functionName);\n");
        nativeFileHostSource.append("        exit(3);\n");
        nativeFileHostSource.append("    }\n");
        nativeFileHostSource.append("}\n\n");

        nativeFileHostSource.append(nativeStubs.toString());
        return nativeFileHostSource.toString();
    }
}
