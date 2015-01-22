package icecaptools;

import icecaptools.compiler.FieldInfo;
import icecaptools.compiler.utils.MethodMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.RuntimeInvisibleAnnotations;

public class UsedElementsObserver implements AnalysisObserver {

    private MethodMap<MethodOrFieldDesc> usedMethods;
    private HashSet<String> usedInterfaces;
    private HashSet<String> usedClasses;
    private HashSet<MethodOrFieldDesc> usedMethodDescriptors;
    private HashSet<MethodOrFieldDesc> usedClassFields;
    private IcecapProgressMonitor progressMonitor;
    private HashSet<String> lockingTypes;
    private ArrayList<String> classInitializationSequence;
    private HashMap<String, NativeFieldInfo> nativeFields;
    private HashMap<String, CFuncInfo> cFunctions;
    private boolean[] usedByteCodes;

    private ArrayList<MethodOrFieldDesc> sortedMethodDescriptors;
    private int maxVTableSize;

    public UsedElementsObserver() {
        usedMethods = new MethodMap<MethodOrFieldDesc>();
        usedClasses = new HashSet<String>();
        usedInterfaces = new HashSet<String>();
        usedMethodDescriptors = new HashSet<MethodOrFieldDesc>();
        usedClassFields = new HashSet<MethodOrFieldDesc>();
        lockingTypes = new HashSet<String>();
        usedByteCodes = new boolean[255];
        nativeFields = new HashMap<String, NativeFieldInfo>();
        sortedMethodDescriptors = null;
        classInitializationSequence = new ArrayList<String>();
        cFunctions = new HashMap<String, CFuncInfo>();
    }

    @Override
    public void classUsed(String newType) {
        if (!usedClasses.contains(newType)) {
            usedClasses.add(newType);
        }
    }

    @Override
    public void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature, boolean report) throws CanceledByUserException {
        if (!usedMethods.contains(className, targetMethodName, targetMethodSignature)) {
            MethodOrFieldDesc methodDesc = new MethodOrFieldDesc(className, targetMethodName, targetMethodSignature);

            usedMethodDescriptors.add(methodDesc);
            usedMethods.put(className, targetMethodName, targetMethodSignature, methodDesc);
            sortedMethodDescriptors = null;
            if (report) {
                progressMonitor.worked(className + ": " + targetMethodName);
            }
            if (progressMonitor.isCanceled()) {
                throw new CanceledByUserException();
            }
        }
    }

    public Iterator<String> getUsedClasses() {
        return usedClasses.iterator();
    }

    public boolean isMethodUsed(String className, String methodName, String methodSignature) {
        return usedMethods.contains(className, methodName, methodSignature);
    }

    public IcecapIterator<MethodOrFieldDesc> getUsedMethods() {
        if (sortedMethodDescriptors == null) {
            sortedMethodDescriptors = new ArrayList<MethodOrFieldDesc>(usedMethodDescriptors);
            Collections.sort(sortedMethodDescriptors);
        }

        return new ToIcecapIterator<MethodOrFieldDesc>(sortedMethodDescriptors.iterator());
    }

    @Override
    public void interfaceUsed(String className) {

        usedInterfaces.add(className);
    }

    @Override
    public boolean isInterfaceUsed(String className) {

        return usedInterfaces.contains(className);
    }

    @Override
    public void setProgressMonitor(IcecapProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }

    @Override
    public void classFieldUsed(String className, String fieldName) {

        if (!isClassFieldUsed(className, fieldName)) {
            usedClassFields.add(new MethodOrFieldDesc(className, fieldName, ""));
        }
    }

    @Override
    public boolean isClassFieldUsed(String className, String fieldName) {

        for (MethodOrFieldDesc m : usedClassFields) {
            if (m.getName().equals(fieldName) && m.getSignature().equals("") && m.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isClassUsed(String className) {

        if (this.usedClasses.contains(className)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void registerLockingTypes(ArrayList<String> types) {
        for (String type : types) {
            if (!lockingTypes.contains(type)) {
                this.lockingTypes.add(type);
            }
        }
    }

    @Override
    public boolean isLockingType(String className) {
        while (!"java.lang.Object".equals(className)) {
            if (this.lockingTypes.contains(className)) {
                return true;
            }
            className = ClassfileUtils.getSuperClassName(className);
        }
        return false;
    }

    @Override
    public void registerLockingType(String type) {
        this.lockingTypes.add(type);
    }

    @Override
    public void byteCodeUsed(byte opCode) {
        int index = ((int) opCode) & 0xff;
        this.usedByteCodes[index] = true;
    }

    @Override
    public boolean isBytecodeUsed(int i) {
        return usedByteCodes[i];
    }

    protected String getNativeFieldKey(String containingClass, FieldInfo field) {
        StringBuffer key = new StringBuffer();
        key.append(containingClass);
        key.append(field.getName());
        return key.toString();
    }

    @Override
    public void registerNativeField(String containingClass, FieldInfo field, IcecapCVar cvar) {
        String key = getNativeFieldKey(containingClass, field);
        nativeFields.put(key.toString(), new NativeFieldInfo(field, cvar));
    }

    @Override
    public void registerCFunc(String className, String methodName, String methodSignature, IcecapCFunc cfunc) {
        String key = className + methodName + methodSignature;
        if (!cFunctions.containsKey(key)) {
            cFunctions.put(key, new CFuncInfo(new MethodOrFieldDesc(className, methodName, methodSignature), cfunc));
        }
    }

    @Override
    public CFuncInfo isCFunc(String className, String methodName, String methodSignature) {
        String key = className + methodName + methodSignature;
        return cFunctions.get(key);
    }

    @Override
    public IcecapIterator<CFuncInfo> getCFunctions() throws Exception {
        Iterator<String> usedClasses = getUsedClasses();

        while (usedClasses.hasNext()) {
            String currentClassName = usedClasses.next();
            JavaClass clss = Repository.lookupClass(currentClassName);

            Method[] methods = clss.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    Attribute[] attributes = method.getAttributes();
                    for (Attribute current : attributes) {
                        IcecapCFunc cfunc = null;
                        if (current instanceof AnnotationsAttribute) {
                            AnnotationsAttribute icecapAttribute = (AnnotationsAttribute) current;
                            cfunc = icecapAttribute.getAnnotation(IcecapCFunc.class);
                        } else if (current instanceof RuntimeInvisibleAnnotations) {
                            AnnotationsAttribute icecapAttribute = AnnotationsAttribute.getAttribute((RuntimeInvisibleAnnotations) current);
                            cfunc = icecapAttribute.getAnnotation(IcecapCFunc.class);
                        }
                        if (cfunc != null) {
                            registerCFunc(currentClassName, method.getName(), method.getSignature(), cfunc);
                        }
                    }
                }
            }
        }

        return new CFunctionIterator(cFunctions);
    }

    @Override
    public NativeFieldInfo isNativeField(String containingClass, FieldInfo field) {
        String key = getNativeFieldKey(containingClass, field);
        return nativeFields.get(key);
    }

    @Override
    public void classInitializerUsed(String className) {
        if (!classInitializationSequence.contains(className)) {
            classInitializationSequence.add(className);
        }
    }

    @Override
    public Iterator<String> getUsedClassInitializers() {
        return classInitializationSequence.iterator();
    }

    @Override
    public IcecapIterator<MethodOrFieldDesc> getUsedMethods(String nextClass) {
        return usedMethods.getMethods(nextClass);
    }

    @Override
    public void reportVtableSize(int s) {
        if (maxVTableSize < s) {
            maxVTableSize = s;
        }
    }

    @Override
    public int getMaxVtableSize() {
        return maxVTableSize;
    }

    private static class CFunctionIterator implements IcecapIterator<CFuncInfo> {

        private Iterator<Entry<String, CFuncInfo>> cFunctions;

        public CFunctionIterator(HashMap<String, CFuncInfo> cFunctions) {
            this.cFunctions = cFunctions.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return cFunctions.hasNext();
        }

        @Override
        public CFuncInfo next() {
            Entry<String, CFuncInfo> next = cFunctions.next();
            return next.getValue();
        }
    }
}