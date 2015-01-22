package icecaptools;

import java.util.ArrayList;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public abstract class MethodCallBNode extends BNode {
    protected String className;
    protected String methodName;
    protected String methodSig;

    protected ArrayList<JavaClass> targetClasses;
    private int numArgs;

    private MethodOrFieldDesc calleeDescriptor;

    private NewList nativesChecked;

    public MethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
        this.className = className;
        this.methodName = methodName;
        this.methodSig = methodSig;
        targetClasses = new ArrayList<JavaClass>();
        numArgs = -1;
        nativesChecked = new NewList();
    }

    public boolean isNativesChecked(NewList newList) {
        if (newList.lessThanOrEquals(nativesChecked)) {
            return true;
        } else {
            return false;
        }
    }

    public void setNativesChecked(NewList newList) {
        this.nativesChecked = new NewList(newList);
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodSig() {
        return methodSig;
    }

    @Override
    public int extend() throws Exception {
        if (this.rawBytes.length == 3) {
            int extendedLength = getExtendedLength();
            byte[] newRawBytes = new byte[extendedLength];

            newRawBytes[0] = this.rawBytes[0];

            for (int i = 1; i < this.rawBytes.length; i++) {
                newRawBytes[i + 1] = this.rawBytes[i];
            }
            this.rawBytes = newRawBytes;
            return extendedLength - 3;
        } else {
            return super.extend();
        }
    }

    protected int getExtendedLength() {
        return 4;
    }

    @Override
    public void relocateForward(int address, int extension) {
    }

    @Override
    public void relocateBackward(int address, int extension) {
    }

    @Override
    public boolean requiresExtension() {
        return false;
    }

    @Override
    protected String print() {
        return "call " + className + "." + methodName;
    }

    public void addTarget(JavaClass targetClass) {
        if (!targetClasses.contains(targetClass)) {
            targetClasses.add(targetClass);
        }
    }

    public int getNumberOfTargets() {
        return targetClasses.size();
    }

    public String getTargetClassName() {
        return targetClasses.get(0).getClassName();
    }

    public void setNumArgs(int numArgs) {
        this.numArgs = numArgs;
    }

    public int getNumArgs() {
        return numArgs;
    }

    public void setMethodDescriptor(MethodOrFieldDesc callee) {
        this.calleeDescriptor = callee;
    }

    public MethodOrFieldDesc getMethodDescriptor() {
        return calleeDescriptor;
    }

    private JavaClass targetClazz;
    private Method targetMetod;

    public Method findMethodInClass(JavaClass clazz) {
        if (targetClazz != clazz) {
            targetClazz = clazz;
            targetMetod = ClassfileUtils.findMethodInClass(clazz, getMethodName(), getMethodSig());
        }
        return targetMetod;
    }

    public boolean isTarget(JavaClass t) {
        return this.targetClasses.contains(t);
    }
}
