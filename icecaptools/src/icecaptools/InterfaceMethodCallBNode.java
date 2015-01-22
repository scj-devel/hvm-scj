package icecaptools;


public class InterfaceMethodCallBNode extends VirtualOrInterfaceMethodCallBNode {

    public InterfaceMethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public boolean requiresExtension() {
        return true;
    }

    @Override
    public int extend() throws Exception {
        if (this.rawBytes.length == 5)
        {
            int extendedLength = 5 + 1 + getNumberOfTargets() * 4;
            byte[] newRawBytes = new byte[extendedLength];

            for (int i = 0; i < this.rawBytes.length; i++)
            {
                newRawBytes[i] = this.rawBytes[i];
            }
            this.rawBytes = newRawBytes;
            return extendedLength - 5;
        }
        else
        {
            return super.extend();
        }
    }

    @Override
    protected int targetThreshold() {
        return 0;
    }
}
