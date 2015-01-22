package icecaptools.compiler.utils;

import icecaptools.IcecapIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MethodMap<T> {

    private HashMap<String, Object> classes;

    private ArrayList<T> values;

    private ArrayList<String> classesList;

    public MethodMap() {
        classes = new HashMap<String, Object>();
        values = new ArrayList<T>();
        classesList = null;
    }

    public boolean contains(String className) {
        return classes.containsKey(className);
    }

    @SuppressWarnings("unchecked")
    public void put(String className) {
        HashMap<String, Object> methods;
        methods = (HashMap<String, Object>) classes.get(className);
        if (methods == null) {
            methods = new HashMap<String, Object>();
            classes.put(className, methods);
            classesList = null;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean put(String className, String targetMethodName, String targetMethodSignature, T value) {
        HashMap<String, Object> methods;

        if (classes.containsKey(className)) {
            HashMap<String, T> signatures;
            methods = (HashMap<String, Object>) classes.get(className);
            if (methods.containsKey(targetMethodName)) {
                signatures = (HashMap<String, T>) methods.get(targetMethodName);
                if (signatures.containsKey(targetMethodSignature)) {
                    return false;
                } else {
                    signatures.put(targetMethodSignature, value);
                    values.add(value);
                    return true;
                }
            } else {
                signatures = new HashMap<String, T>();
                methods.put(targetMethodName, signatures);
                return put(className, targetMethodName, targetMethodSignature, value);
            }
        } else {
            methods = new HashMap<String, Object>();
            classes.put(className, methods);
            classesList = null;
            return put(className, targetMethodName, targetMethodSignature, value);
        }
    }

    public Iterator<String> getClasses() {
        if (classesList == null) {
            Set<String> keys = classes.keySet();
            classesList = new ArrayList<String>(keys);
            Collections.sort(classesList);
        }
        return classesList.iterator();
    }

    public boolean contains(String className, String methodName, String methodSignature) {
        return get(className, methodName, methodSignature) != null;
    }

    @SuppressWarnings("unchecked")
    public T get(String className, String methodName, String methodSignature) {
        HashMap<String, Object> methods;
        methods = (HashMap<String, Object>) classes.get(className);

        if (methods != null) {
            HashMap<String, T> signatures;
            signatures = (HashMap<String, T>) methods.get(methodName);
            if (signatures != null) {
                T value = signatures.get(methodSignature);
                return value;
            }
        }
        return null;
    }

    public Iterator<T> getValues() {
        return this.values.iterator();
    }

    public IcecapIterator<T> getMethods(String nextClass) {
        return get(nextClass);
    }

    private class ValueIterator implements IcecapIterator<T> {
        private Iterator<String> methods;
        private Iterator<String> signatures;
        
        private String className;

        private HashMap<String, Object> ms;
        private HashMap<String, T> ss;
        
        ValueIterator(String className) {
            this.className = className;
            this.methods = null;
            this.signatures = null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean hasNext() {
            if (methods == null) {
                if (classes.containsKey(className)) {
                    ms = (HashMap<String, Object>) classes.get(className);
                    methods = ms.keySet().iterator();
                    return hasNext();
                } 
            }
            else
            {
                if (signatures == null)
                {
                    if (methods.hasNext())
                    {
                        String nextMethod = methods.next();
                        ss = (HashMap<String, T>) ms.get(nextMethod);
                        signatures = ss.keySet().iterator();
                        return hasNext();
                    }
                }
                else
                {
                    return signatures.hasNext();
                }
            }
            return false;
        }

        @Override
        public T next() {
            T n = ss.get(signatures.next());
            if (!signatures.hasNext())
            {
                signatures = null;
            }
            return n;
        }
    }

    private IcecapIterator<T> get(String nextClass) {
        return new ValueIterator(nextClass);
    }
}
