package icecaptools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Visitor;

public class LambdaClass extends JavaClass {

    private static final long serialVersionUID = 1L;

    public static final String LAMBDACLASSPOSTFIX = "$lambda";

    private String className;

    private Method[] methods;

    private static ArrayList<Method> lambdaMethods;
    
    static
    {
        lambdaMethods = new ArrayList<Method>();
    }
    
    public LambdaClass(String className, JavaClass iface) {
        super(iface.getClassNameIndex(), iface.getSuperclassNameIndex(), iface.getFileName(), iface.getMajor(), iface.getMinor(), iface.getAccessFlags(), iface.getConstantPool(), iface.getInterfaceIndices(), iface.getFields(), iface.getMethods(), iface.getAttributes());
        this.className = className;
    }

    public static boolean isLambdaClass(String className) {
        return className.endsWith(LAMBDACLASSPOSTFIX);
    }

    public static String getInterfaceName(String className) {
        return getToken(className, 0);
    }

    private static String getToken(String str, int index) {
        int start = str.indexOf("$$");
        while (index > 0) {
            str = str.substring(start + 2);
            start = str.indexOf("$$");
            index--;
        }
        if (start != -1) {
            return str.substring(0, start);
        } else {
            return str;
        }
    }

    void unimplemented() {
        System.out.println("unimplemented");
    }

    @Override
    public void accept(Visitor v) {
        unimplemented();
        super.accept(v);
    }

    @Override
    public void dump(File file) throws IOException {
        unimplemented();
        super.dump(file);
    }

    @Override
    public void dump(String _file_name) throws IOException {
        unimplemented();
        super.dump(_file_name);
    }

    @Override
    public byte[] getBytes() {
        unimplemented();
        return super.getBytes();
    }

    @Override
    public void dump(OutputStream file) throws IOException {
        unimplemented();
        super.dump(file);
    }

    @Override
    public void dump(DataOutputStream file) throws IOException {
        unimplemented();
        super.dump(file);
    }

    @Override
    public Attribute[] getAttributes() {
        unimplemented();
        return super.getAttributes();
    }

    @Override
    public AnnotationEntry[] getAnnotationEntries() {
        unimplemented();
        return super.getAnnotationEntries();
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getPackageName() {
        unimplemented();
        return super.getPackageName();
    }

    @Override
    public int getClassNameIndex() {
        unimplemented();
        return super.getClassNameIndex();
    }

    @Override
    public ConstantPool getConstantPool() {
        String implementationClassName = getToken(className, 1);
        try {
            JavaClass implementationClass = Repository.lookupClass(implementationClassName);
            return implementationClass.getConstantPool();
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    @Override
    public Field[] getFields() {
        return new Field[0];
    }

    @Override
    public String getFileName() {
        unimplemented();
        return super.getFileName();
    }

    @Override
    public String[] getInterfaceNames() {
        unimplemented();
        return super.getInterfaceNames();
    }

    @Override
    public int[] getInterfaceIndices() {
        unimplemented();
        return super.getInterfaceIndices();
    }

    @Override
    public int getMajor() {
        unimplemented();
        return super.getMajor();
    }

    @Override
    public Method[] getMethods() {
        if (methods == null) {
            String interfaceName = getToken(className, 0);
            String implementationClassName = getToken(className, 1);
            String implementationMethodName = getToken(className, 2);

            implementationMethodName = implementationMethodName.substring(0, implementationMethodName.length() - LAMBDACLASSPOSTFIX.length());

            try {
                JavaClass implementationClass = Repository.lookupClass(implementationClassName);
                Method[] methods = implementationClass.getMethods();
                for (Method method : methods) {
                    if (method.getName().equals(implementationMethodName)) {
                        Method[] methodArray = new Method[1];
                        JavaClass iface = Repository.lookupClass(interfaceName);

                        Method[] interfaceMethods = iface.getMethods();
                        String methodName = interfaceMethods[0].getName();

                        ConstantPool constantPool = implementationClass.getConstantPool();
                        
                        Method implementationMethod = method.copy(constantPool);

                        Constant[] constants = constantPool.getConstantPool();
                        Constant[] extendedConstant = new Constant[constants.length + 1];
                        for (int i = 0; i < constants.length; i++)
                        {
                            extendedConstant[i] = constants[i];
                        }
                        
                        ConstantUtf8 implementationMethodNameConstant = new ConstantUtf8(methodName);
                        extendedConstant[constants.length] = implementationMethodNameConstant;
                        
                        implementationMethod.setConstantPool(new ConstantPool(extendedConstant));
                        
                        implementationMethod.setNameIndex(constants.length);
                        
                        methodArray[0] = implementationMethod;
                        registerLambdaMethod(implementationMethod);
                        this.methods = methodArray;
                    }
                }
            } catch (ClassNotFoundException e) {
            }
        }
        return methods;
    }


    private static void registerLambdaMethod(Method implementationMethod) {
        lambdaMethods.add(implementationMethod);
    }
    
    public static boolean isLambdaMethod(Method javaMethod) {
        Iterator<Method> lambdas = lambdaMethods.iterator();
        while (lambdas.hasNext())
        {
            if (lambdas.next() == javaMethod)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Method getMethod(java.lang.reflect.Method m) {
        unimplemented();
        return super.getMethod(m);
    }

    @Override
    public int getMinor() {
        unimplemented();
        return super.getMinor();
    }

    @Override
    public String getSourceFileName() {
        unimplemented();
        return super.getSourceFileName();
    }

    @Override
    public String getSuperclassName() {
        return "java.lang.Object";
    }

    @Override
    public int getSuperclassNameIndex() {
        unimplemented();
        return super.getSuperclassNameIndex();
    }

    @Override
    public void setAttributes(Attribute[] attributes) {
        unimplemented();
        super.setAttributes(attributes);
    }

    @Override
    public void setClassName(String class_name) {
        unimplemented();
        super.setClassName(class_name);
    }

    @Override
    public void setClassNameIndex(int class_name_index) {
        unimplemented();
        super.setClassNameIndex(class_name_index);
    }

    @Override
    public void setConstantPool(ConstantPool constant_pool) {
        unimplemented();
        super.setConstantPool(constant_pool);
    }

    @Override
    public void setFields(Field[] fields) {
        unimplemented();
        super.setFields(fields);
    }

    @Override
    public void setFileName(String file_name) {
        unimplemented();
        super.setFileName(file_name);
    }

    @Override
    public void setInterfaceNames(String[] interface_names) {
        unimplemented();
        super.setInterfaceNames(interface_names);
    }

    @Override
    public void setInterfaces(int[] interfaces) {
        unimplemented();
        super.setInterfaces(interfaces);
    }

    @Override
    public void setMajor(int major) {
        unimplemented();
        super.setMajor(major);
    }

    @Override
    public void setMethods(Method[] methods) {
        unimplemented();
        super.setMethods(methods);
    }

    @Override
    public void setMinor(int minor) {
        unimplemented();
        super.setMinor(minor);
    }

    @Override
    public void setSourceFileName(String source_file_name) {
        unimplemented();
        super.setSourceFileName(source_file_name);
    }

    @Override
    public void setSuperclassName(String superclass_name) {
        unimplemented();
        super.setSuperclassName(superclass_name);
    }

    @Override
    public void setSuperclassNameIndex(int superclass_name_index) {
        unimplemented();
        super.setSuperclassNameIndex(superclass_name_index);
    }

    @Override
    public String toString() {
        unimplemented();
        return super.toString();
    }

    @Override
    public JavaClass copy() {
        unimplemented();
        return super.copy();
    }

    @Override
    public org.apache.bcel.util.Repository getRepository() {
        return super.getRepository();
    }

    @Override
    public void setRepository(org.apache.bcel.util.Repository repository) {
        super.setRepository(repository);
    }

    @Override
    public boolean implementationOf(JavaClass inter) throws ClassNotFoundException {
        if (inter.getClassName().equals(getInterfaceName(className))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JavaClass getSuperClass() throws ClassNotFoundException {
        return Repository.lookupClass("java.lang.Object");
    }

    @Override
    public JavaClass[] getSuperClasses() throws ClassNotFoundException {
        unimplemented();
        return super.getSuperClasses();
    }

    @Override
    public JavaClass[] getInterfaces() throws ClassNotFoundException {
        unimplemented();
        return super.getInterfaces();
    }

    @Override
    public JavaClass[] getAllInterfaces() throws ClassNotFoundException {
        String interfaceName = getToken(className, 0);
        JavaClass[] interfaces = new JavaClass[1];
        interfaces[0] = Repository.lookupClass(interfaceName);
        return interfaces;
    }

    @Override
    public boolean equals(Object obj) {
        unimplemented();
        return super.equals(obj);
    }

    @Override
    public int compareTo(JavaClass obj) {
        unimplemented();
        return super.compareTo(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

   

}
