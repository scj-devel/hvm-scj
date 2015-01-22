package icecaptools.compiler;

import icecaptools.IcecapIterator;
import icecaptools.MethodOrFieldDesc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class CompilationRegistry implements ICompilationRegistry {

    private HashMap<String, ArrayList<MethodOrFieldDesc>> compiledClasses;
    private HashMap<String, ArrayList<MethodOrFieldDesc>> excludedClasses;

    private boolean clearOutputFolder;

    public CompilationRegistry() {
        compiledClasses = new HashMap<String, ArrayList<MethodOrFieldDesc>>();
        excludedClasses = new HashMap<String, ArrayList<MethodOrFieldDesc>>();
        clearOutputFolder = false;
    }

    public void initializeFromString(String encoded) {
        compiledClasses = new HashMap<String, ArrayList<MethodOrFieldDesc>>();
        excludedClasses = new HashMap<String, ArrayList<MethodOrFieldDesc>>();
        StringTokenizer tokenizer = new StringTokenizer(encoded, "!");

        initializeFromString(compiledClasses, tokenizer);
        initializeFromString(excludedClasses, tokenizer);
        initializeFromString(tokenizer);
    }

    private void initializeFromString(StringTokenizer tokenizer) {
        if (tokenizer.hasMoreTokens()) {
            String next = tokenizer.nextToken();
            if ("true".equals(next))
            {
                this.clearOutputFolder = true;
            }
            else
            {
                this.clearOutputFolder = false;
            }
        }
    }

    private static void initializeFromString(HashMap<String, ArrayList<MethodOrFieldDesc>> map, StringTokenizer tokenizer) {
        int numberOfClasses;

        if (tokenizer.hasMoreTokens()) {
            numberOfClasses = Integer.parseInt(tokenizer.nextToken());
        } else {
            numberOfClasses = 0;
        }

        while (numberOfClasses > 0) {
            String className = tokenizer.nextToken();

            int numberOfMethods = Integer.parseInt(tokenizer.nextToken());

            ArrayList<MethodOrFieldDesc> methods = new ArrayList<MethodOrFieldDesc>();

            while (numberOfMethods > 0) {
                String methodName = tokenizer.nextToken();
                String signature = tokenizer.nextToken();
                methods.add(new MethodOrFieldDesc(className, methodName, signature));
                numberOfMethods--;
            }
            map.put(className, methods);
            numberOfClasses--;
        }
    }

    public void toggleMethodCompilation(MethodOrFieldDesc mdesc) {
        toggleMethodProperty(mdesc, compiledClasses);
    }

    private static void toggleMethodProperty(MethodOrFieldDesc mdesc, HashMap<String, ArrayList<MethodOrFieldDesc>> map) {
        ArrayList<MethodOrFieldDesc> methods = map.get(mdesc.getClassName());
        if (methods == null) {
            methods = new ArrayList<MethodOrFieldDesc>();
            map.put(mdesc.getClassName(), methods);
        }

        if (methods.contains(mdesc)) {
            methods.remove(mdesc);
        } else {
            methods.add(mdesc);
        }
    }

    public void toggleMethodExclusion(MethodOrFieldDesc mdesc) {
        toggleMethodProperty(mdesc, excludedClasses);
    }

    public boolean isClassCompiled(String className) {
        ArrayList<MethodOrFieldDesc> compiledMethods = compiledClasses.get(className);

        if (compiledMethods != null) {
            if (compiledMethods.size() > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean isMethodCompiled(MethodOrFieldDesc mdesc) {
        return isMethodSelected(mdesc, compiledClasses);
    }

    public boolean isMethodExcluded(MethodOrFieldDesc mdesc) {
        return isMethodSelected(mdesc, excludedClasses);
    }

    @Override
    public boolean isMethodExcluded(String clazz, String targetMethodName, String targetMethodSignature) {
        return isMethodExcluded(new MethodOrFieldDesc(clazz, targetMethodName, targetMethodSignature));
    }

    private static boolean isMethodSelected(MethodOrFieldDesc mdesc, HashMap<String, ArrayList<MethodOrFieldDesc>> map) {
        ArrayList<MethodOrFieldDesc> methods = map.get(mdesc.getClassName());

        if (methods != null) {
            if (methods.contains(mdesc)) {
                return true;
            }
        }
        return false;
    }

    public String encodeToString() {
        return encodeToString(compiledClasses) + "!" + encodeToString(excludedClasses) + "!" + this.clearOutputFolder;
    }

    private static String encodeToString(HashMap<String, ArrayList<MethodOrFieldDesc>> map) {
        StringBuffer buffer = new StringBuffer();
        int count = 0;
        Iterator<Entry<String, ArrayList<MethodOrFieldDesc>>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, ArrayList<MethodOrFieldDesc>> next = entries.next();
            String key = next.getKey();
            ArrayList<MethodOrFieldDesc> value = next.getValue();
            if (value.size() > 0) {
                count++;
                buffer.append(key + "!");
                buffer.append(value.size() + "!");
                Iterator<MethodOrFieldDesc> values = value.iterator();

                while (values.hasNext()) {
                    MethodOrFieldDesc nextMethod = values.next();
                    buffer.append(nextMethod.getName() + "!");
                    buffer.append(nextMethod.getSignature() + "!");
                }
            }
        }
        if (count > 0) {
            return count + "!" + buffer.substring(0, buffer.length() - 1);
        } else {
            return count + "!";
        }
    }

    private class ExcludedMethodsIterator implements IcecapIterator<MethodOrFieldDesc> {
        private Iterator<Entry<String, ArrayList<MethodOrFieldDesc>>> classes;
        private Iterator<MethodOrFieldDesc> methods;

        ExcludedMethodsIterator() {
            classes = excludedClasses.entrySet().iterator();
            methods = null;
        }

        @Override
        public boolean hasNext() {
            if (methods == null) {
                if (classes.hasNext()) {
                    Entry<String, ArrayList<MethodOrFieldDesc>> next = classes.next();
                    ArrayList<MethodOrFieldDesc> value = next.getValue();
                    methods = value.iterator();
                    return hasNext();
                } else {
                    return false;
                }
            } else {
                if (methods.hasNext()) {
                    return true;
                } else {
                    methods = null;
                    return hasNext();
                }
            }
        }

        @Override
        public MethodOrFieldDesc next() {
            return methods.next();
        }
    }

    public IcecapIterator<MethodOrFieldDesc> getExcludedMethods() {
        return new ExcludedMethodsIterator();
    }

    @Override
    public boolean alwaysClearOutputFolder() {
        return this.clearOutputFolder;
    }

    public void toggleFolderClearing() {
        if (this.clearOutputFolder) {
            this.clearOutputFolder = false;
        } else {
            this.clearOutputFolder = true;
        }
    }
}
