package icecaptools.compiler;

import icecaptools.compiler.utils.CompareableStringBuffer;

import java.util.HashMap;
import java.util.HashSet;

public class IDGenerator {

    private HashSet<String> ids;

    private HashMap<String, String> generatedNames;

    public IDGenerator() {
        ids = new HashSet<String>();
        generatedNames = new HashMap<String, String>();

    }

    public String getUniqueId(String className, String methodName, String signature) {
        return getUniqueId(className, methodName, signature, null);
    }

    public String getUniqueId(String className, String methodName, String signature, String preferredName) {
        StringBuffer key = new StringBuffer();

        key.append(className);
        key.append(methodName);
        key.append(signature);

        String result = generatedNames.get(key.toString());

        if (result == null) {
            if (preferredName == null) {
                CompareableStringBuffer name = new CompareableStringBuffer();
                int length = className.length();

                for (int i = 0; i < length; i++) {
                    char next = className.charAt(i);

                    if ((next >= 'a') && (next <= 'z')) {
                        name.append(next);
                    } else if ((next >= 'A') && (next <= 'Z')) {
                        name.append(next);
                    } else if ((next >= '0') && (next <= '9')) {
                        name.append(next);
                    } else {
                        name.append('_');
                    }
                }

                if (methodName.charAt(0) == '<') {
                    methodName = methodName.replace("<", "_").replace(">", "_");
                } else {
                    name.append('_');
                }

                methodName = methodName.replace("$", "_");

                name.append(methodName);

                while (ids.contains(name.toString())) {
                    name.append('_');
                }
                result = name.toString();
            } else {
                result = preferredName;
            }

            ids.add(result);
            generatedNames.put(key.toString(), result);
        }
        // String s = (className + "_" + methodName +
        // signature).toLowerCase().replace("/", "_").replace("(",
        // "_").replace(")", "_").replace("<", "_").replace(">",
        // "_").replace(";", "_").replace(".", "_").replace("[",
        // "_").replace("$", "_");

        return result;
    }
}
