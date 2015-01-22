package icecaptools;

import icecaptools.compiler.LDCConstant;
import icecaptools.compiler.aot.Size;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

public class ClassfileUtils {

    private ClassfileUtils() {
        ;
    }

    public static int getSizeOfType(Type t) throws Exception {
        if (t == Type.BOOLEAN) {
            return 1;
        } else if (t == Type.BYTE) {
            return 8;
        } else if (t == Type.SHORT) {
            return 16;
        } else if (t == Type.CHAR || t == Type.INT || t == Type.FLOAT || t == Type.OBJECT || t.getType() == 13 /*
                                                                                                                * array
                                                                                                                * types
                                                                                                                */|| t == Type.CLASS || t == Type.STRING || t == Type.STRINGBUFFER || t == Type.THROWABLE || t.getType() == 14) {
            return 32;
        } else if (t == Type.LONG || t == Type.DOUBLE) {
            return 64;
        } else if (t == Type.VOID || t == Type.NULL) {
            return 0;
        }
        throw new Exception("Unhandled data type found : " + t + " / " + t.getType());
    }

    public static int getType(Type t) throws Exception {
        if (t == Type.BOOLEAN) {
            return 0;
        } else if (t == Type.BYTE) {
            return 1;
        } else if (t == Type.SHORT) {
            return 2;
        } else if (t == Type.CHAR) {
            return 3;
        } else if (t == Type.INT) {
            return 4;
        } else if (t == Type.LONG) {
            return 5;
        } else if (t == Type.DOUBLE) {
            return 6;
        } else if (t == Type.FLOAT) {
            return 7;
        } else {
            return 8;
        }
    }

    public static LDCConstant getLDCConstant(String className, int index) throws ClassNotFoundException {
        JavaClass clazz = Repository.lookupClass(className);
        Constant constant = clazz.getConstantPool().getConstant(index);
        if (constant instanceof ConstantString) {
            ConstantString str = (ConstantString) constant;
            return new LDCConstant(str.getBytes(clazz.getConstantPool()));
        } else if (constant instanceof ConstantInteger) {
            ConstantInteger integer = (ConstantInteger) constant;
            return new LDCConstant(integer.getBytes());
        } else if (constant instanceof ConstantFloat) {
            ConstantFloat floatVal = (ConstantFloat) constant;
            return new LDCConstant(floatVal.getBytes());
        } else if (constant instanceof ConstantDouble) {
            ConstantDouble doubleVal = (ConstantDouble) constant;
            double val = doubleVal.getBytes();
            return new LDCConstant(val);
        } else if (constant instanceof ConstantLong) {
            ConstantLong longVal = (ConstantLong) constant;
            long val = longVal.getBytes();
            return new LDCConstant(val);
        } else if (constant instanceof ConstantClass) {
            ConstantClass type = (ConstantClass) constant;
            int nameIndex = type.getNameIndex();
            constant = clazz.getConstantPool().getConstant(nameIndex);
            ConstantUtf8 name = (ConstantUtf8) constant;
            return new LDCConstant(true, name.getBytes().replace("/", "."));
        }
        return null;
    }

    public static MethodOrFieldDesc findField(String className, String fieldName, String fieldSignature) throws ClassNotFoundException {
        JavaClass clazz = Repository.lookupClass(className);
        Field[] fields = clazz.getFields();
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                Field current = fields[i];
                if (current.getName().equals(fieldName)) {
                    if (current.getSignature().equals(fieldSignature)) {
                        return new MethodOrFieldDesc(className, fieldName, fieldSignature);
                    }
                }
            }
        }
        JavaClass superClass = clazz.getSuperClass();
        if (superClass != null) {
            return findField(superClass.getClassName(), fieldName, fieldSignature);
        } else {
            return null;
        }
    }

    public static int getNumArgs(MethodOrFieldDesc referredMethod) throws ClassNotFoundException, Exception {
        JavaClass clazz = Repository.lookupClass(referredMethod.getClassName());
        MethodAndClass m = findMethodInClassHierarchy(clazz, referredMethod.getName(), referredMethod.getSignature());
        if (m != null) {
            return getNumArgs(m.getMethod());
        } else {
            throw new Exception("Could not find num args for [" + referredMethod.toString() + "]");
        }
    }

    public static Method findMethodInClass(JavaClass clazz, String methodName, String methodSignature) {
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method current = methods[i];
            if (current.getName().equals(methodName)) {
                if (current.getSignature().equals(methodSignature)) {
                    return current;
                }
            }
        }
        return null;
    }

    public static Method findMethodInClass(JavaClass clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method current = methods[i];
            if (current.getName().equals(methodName)) {
                return current;
            }
        }
        return null;
    }
    
    public static int getNumArgs(Method m) throws Exception {
        Type[] argTypes = m.getArgumentTypes();
        int nargs = 0;
        for (int i = 0; i < argTypes.length; i++) {
            Type current = argTypes[i];
            if ((current == Type.LONG) || (current == Type.DOUBLE)) {
                nargs += 2;
            } else {
                nargs++;
            }
        }
        return nargs;
    }

    public static int getArgSize(Method m, int argNo) throws Exception {
        int argSize = getArgSize_(m, argNo);
        switch (argSize) {
        case 1:
        case 8:
            return Size.BYTE;
        case 16:
            return Size.SHORT;
        case 32:
        case 64:
            return Size.INT;
        default:
            throw new Exception("Unexpected arg size");
        }
    }

    private static int getArgSize_(Method m, int argNo) throws Exception {
        int index = 0;

        if (!m.isStatic()) {
            argNo--;
            if (argNo == 0) {
                return 32;
            }
        }

        argNo--;

        Type[] argTypes = m.getArgumentTypes();

        for (int i = 0; i < argTypes.length; i++) {
            Type current = argTypes[i];

            if (index == argNo) {
                return getSizeOfType(current);
            }

            if ((current == Type.LONG) || (current == Type.DOUBLE)) {
                index++;
                if (index == argNo) {
                    return getSizeOfType(current);
                }
                index++;
            } else {
                index++;
            }
        }
        throw new Exception("Couldn't get size of argument");
    }

    public static MethodAndClass findMethodInClassHierarchy(JavaClass clazz, String methodName, String methodSignature) throws Exception {
        if (clazz != null) {
            Method m = ClassfileUtils.findMethodInClass(clazz, methodName, methodSignature);
            if (m != null) {
                return new MethodAndClass(m, clazz);
            }
            return findMethodInClassHierarchy(clazz.getSuperClass(), methodName, methodSignature);
        } else {
            return null;
        }
    }

    public static MethodAndClass findDeclaringInterface(String className, String name, String signature) throws ClassNotFoundException {
        JavaClass clazz = Repository.lookupClass(className);
        return findDeclaringInterface(clazz, name, signature);
    }

    public static MethodAndClass findDeclaringInterface(JavaClass clazz, String targetMethodName, String targetMethodSignature) throws ClassNotFoundException {
        Method method;
        if ((method = findMethodInClass(clazz, targetMethodName, targetMethodSignature)) != null) {
            return new MethodAndClass(method, clazz);
        }
        JavaClass[] interfaces = clazz.getAllInterfaces();
        for (JavaClass javaClass : interfaces) {
            if (javaClass == clazz) {
                continue;
            }

            MethodAndClass declaring = findDeclaringInterface(javaClass, targetMethodName, targetMethodSignature);
            if (declaring != null) {
                return declaring;
            }
        }
        return null;
    }

    public static MethodAndClass findMethod(String className, String methodName, String methodSignature) throws Exception {
        JavaClass clazz = Repository.lookupClass(className);
        return ClassfileUtils.findMethodInClassHierarchy(clazz, methodName, methodSignature);
    }

    public static int getLineNumber(Method javaMethod, int pc) {
        LineNumberTable lineNumberTable = javaMethod.getLineNumberTable();
        if (lineNumberTable != null) {
            LineNumber[] lineNumbers = lineNumberTable.getLineNumberTable();
            for (int i = lineNumbers.length - 1; i >= 0; i--) {
                LineNumber current = lineNumbers[i];
                if (pc >= current.getStartPC()) {
                    return current.getLineNumber();
                }
            }
        }
        return -1;
    }

    public static String getSuperClassName(String referredClassName) {
        try {
            JavaClass clazz = Repository.lookupClass(referredClassName);
            return clazz.getSuperclassName();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static String getClassName(String className, int cpIndex) throws Exception {
        JavaClass clazz;
        JavaClass referredClass;

        try {
            clazz = Repository.lookupClass(className);
        } catch (ClassNotFoundException e) {
            throw new Exception("Could not lookup class [" + className + "], check classpath for containing jar/project");
        }

        try {
            referredClass = getType(clazz, cpIndex);
        } catch (ClassNotFoundException e) {
            throw e;
        }
        if (referredClass != null) {
            return referredClass.getClassName();
        } else {
            return null;
        }
    }

    public static String getClassName(String className, int indexByte1, int indexByte2) throws Exception {
        int cpIndex = RawByteCodes.RawBytecode.bitwiseOr(RawByteCodes.RawBytecode.bitLeftShift(indexByte1 & 0xff, 8), indexByte2 & 0xff);
        return getClassName(className, cpIndex);
    }

    public static JavaClass getType(JavaClass clazz, int cpIndex) throws Exception {
        ConstantClass referredType = (ConstantClass) clazz.getConstantPool().getConstant(cpIndex);
        if (referredType != null) {
            ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(referredType.getNameIndex());
            try {
                JavaClass referredClass = Repository.lookupClass(className.getBytes().replace("/", "."));
                return referredClass;
            } catch (ClassNotFoundException e) {
                throw new Exception("Could not lookup class [" + className.getBytes() + "], check classpath for containing jar/project");
            }
        } else {
            throw new Exception("Could not get constant [" + cpIndex + "] from class [" + clazz.getClassName() + "]");
        }
    }

    public static MethodOrFieldDesc getMethodDesc(String className, int indexByte1, int indexByte2) throws Exception {
        try {
            JavaClass clazz = Repository.lookupClass(className);
            int cpIndex = RawByteCodes.RawBytecode.bitwiseOr(RawByteCodes.RawBytecode.bitLeftShift(indexByte1 & 0xff, 8), indexByte2 & 0xff);

            Constant constant = clazz.getConstantPool().getConstant(cpIndex);
            int classIndex;
            int nameAndTypeIndex;
            if (constant instanceof ConstantMethodref) {
                classIndex = ((ConstantMethodref) constant).getClassIndex();
                nameAndTypeIndex = ((ConstantMethodref) constant).getNameAndTypeIndex();
            } else if (constant instanceof ConstantInterfaceMethodref) {
                classIndex = ((ConstantInterfaceMethodref) constant).getClassIndex();
                nameAndTypeIndex = ((ConstantInterfaceMethodref) constant).getNameAndTypeIndex();
            } else if (constant instanceof ConstantFieldref) {
                classIndex = ((ConstantFieldref) constant).getClassIndex();
                nameAndTypeIndex = ((ConstantFieldref) constant).getNameAndTypeIndex();
            } else {
                throw new Exception("Unknow type [" + constant.getClass().toString() + "]");
            }

            ConstantClass constantClass = (ConstantClass) clazz.getConstantPool().getConstant(classIndex);
            ConstantNameAndType constantNameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(nameAndTypeIndex);

            ConstantUtf8 invokedClassName = (ConstantUtf8) clazz.getConstantPool().getConstant(constantClass.getNameIndex());
            ConstantUtf8 invokedMethodName = (ConstantUtf8) clazz.getConstantPool().getConstant(constantNameAndType.getNameIndex());
            ConstantUtf8 invokedMethodSignature = (ConstantUtf8) clazz.getConstantPool().getConstant(constantNameAndType.getSignatureIndex());

            return new MethodOrFieldDesc(invokedClassName.getBytes().replace("/", "."), invokedMethodName.getBytes(), invokedMethodSignature.getBytes());
        } catch (ClassNotFoundException e) {
            throw new Exception("Could not lookup class [" + className + "]");
        }
    }

    public static boolean hasClassInitializer(JavaClass clazz) {
        Method method = ClassfileUtils.findMethodInClass(clazz, "<clinit>", "()V");
        if (method != null) {
            return true;
        }
        return false;
    }

    public static boolean hasDefaultConstructor(JavaClass clazz) {
        Method method = ClassfileUtils.findMethodInClass(clazz, "<init>", "()V");
        if (method != null) {
            return true;
        }
        return false;
    }

    public static String getClassNameIdentifier(String next) {
        return next.toUpperCase().replace(".", "_").replace("[", "_").replace("$", "_");
    }

    public static MethodLocation getMethodLocation(String tName, int lineNumber, CompilationSequence sequence) throws ClassNotFoundException {
        JavaClass clazz = Repository.lookupClass(tName);
        if (clazz != null) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                LineNumberTable lineNumberTable = method.getLineNumberTable();
                if (lineNumberTable != null) {
                    LineNumber[] lnt = lineNumberTable.getLineNumberTable();
                    for (LineNumber ln : lnt) {
                        if (ln.getLineNumber() >= lineNumber) {
                            int pc = ln.getStartPC();
                            MethodEntryPoints ep = sequence.getDependencyExtent().getMethod(clazz.getClassName(), method.getName(), method.getSignature());

                            if (ep != null) {
                                BNode bnode = ep.getBNodeFromOriginalAddress(pc);
                                if (bnode != null) {
                                    IcecapIterator<MethodOrFieldDesc> methodDescriptors = sequence.getObserver().getUsedMethods(clazz.getClassName());
                                    while (methodDescriptors.hasNext()) {
                                        MethodOrFieldDesc nextMethod = methodDescriptors.next();
                                        if (nextMethod.getName().equals(method.getName())) {
                                            if (nextMethod.getSignature().equals(method.getSignature())) {

                                                int methodNumber = sequence.getPatcher().getMethodNumber(nextMethod, sequence.getIDGen());
                                                if (methodNumber != -1) {
                                                    MethodLocation ml = new MethodLocation(methodNumber, bnode.getAddress(), ln.getLineNumber());

                                                    return ml;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }
}
