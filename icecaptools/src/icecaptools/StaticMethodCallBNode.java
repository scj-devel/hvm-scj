package icecaptools;

import icecaptools.conversion.TargetAddressMap;

public class StaticMethodCallBNode extends MethodCallBNode {

    public StaticMethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public void link(BNode[] nodes, TargetAddressMap tmap) throws Exception {
        super.link(nodes, tmap);
        if (className.equals("java/security/AccessController")) {
            if (methodName.equals("doPrivileged")) {
                if (methodSig.equals("(Ljava/security/PrivilegedAction;)Ljava/lang/Object;")) {
                    BNode interfaceCall = new HackInterfaceMethodCallBNode(address, "java/security/PrivilegedAction", "run", "()Ljava/lang/Object;", locationClass, locationMethod, locationMethodSignature);
                    byte[] rawBytes = { RawByteCodes.invokeinterface_opcode };
                    interfaceCall.setRawBytes(rawBytes);
                    addChild(interfaceCall);
                }
            }
        }
    }

    @Override
    public boolean requiresExtension() {
        return true;
    }
}
