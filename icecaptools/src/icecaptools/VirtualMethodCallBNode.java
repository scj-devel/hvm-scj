package icecaptools;

import org.apache.bcel.classfile.Method;

public class VirtualMethodCallBNode extends VirtualOrInterfaceMethodCallBNode {

    @Override
    protected int getExtendedLength() {
        
        if (JavaArrayClass.isArrayClass(className) && methodName.equals("clone") && methodSig.equals("()Ljava/lang/Object;"))
        {
            return super.getExtendedLength();
        }
        
        int numberOfTargets = getNumberOfTargets();
        
        if (numberOfTargets == 1) {
            return super.getExtendedLength();
        }

        if (numberOfTargets < 0)
        {
            numberOfTargets = 0;
        }
        
        int length = 4 + 1 + numberOfTargets * 4;
        return length;
    }

    private Method callee;

    public VirtualMethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public boolean requiresExtension() {
        return true;
    }

    public Method getCallee() {
        return callee;
    }

    public void setCallee(Method callee) {
        this.callee = callee;
    }

    @Override
    protected int targetThreshold() {
        return 1;
    }
}
