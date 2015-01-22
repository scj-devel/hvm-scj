package icecaptools;

public class DynamicMethodCallVirtualMethodBNode extends DynamicMethodCallBNode {

    private String classNameHandle;
    private String methodNameHandle;
    private String methodSigHandle; 
    private String lambdaClass;
    private String lambdaMethodName;
    private String lambdaMethodSignature;

    public DynamicMethodCallVirtualMethodBNode(int address, int bootstrapIndex, String methodName, String methodSig, String classNameHandle, String methodNameHandle, String methodSigHandle, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, bootstrapIndex, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
        this.classNameHandle = classNameHandle;
        this.methodNameHandle = methodNameHandle;
        this.methodSigHandle = methodSigHandle;
    }

    public String getClassNameHandle() {
        return classNameHandle.replace('/', '.');
    }

    public String getMethodNameHandle() {
        return methodNameHandle;
    }

    public String getMethodSigHandle() {
        return methodSigHandle;
    }

    public void setLambdaMethod(String lambdaClass, String lambdaMethodName, String lambdaMethodSignature) {
        this.lambdaClass = lambdaClass;
        this.lambdaMethodName = lambdaMethodName;
        this.lambdaMethodSignature = lambdaMethodSignature;
    }

    public String getLambdaClass() {
        return lambdaClass.replace('/', '.');
    }

    public String getLambdaMethodName() {
        return lambdaMethodName;
    } 

    public String getLambdaMethodSignature() {
        return lambdaMethodSignature;
    }

    public String getLambdaInterface() {
        int index = methodSig.indexOf(")");
        return methodSig.substring(index + 2, methodSig.length() - 1).replace('/', '.');
    }

    public String getLambdaClassName() {
        return (getLambdaInterface() + "$$" + lambdaClass + "$$" + lambdaMethodName + LambdaClass.LAMBDACLASSPOSTFIX).replace('/', '.');
    }
}
