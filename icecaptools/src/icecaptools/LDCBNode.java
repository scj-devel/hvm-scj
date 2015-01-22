package icecaptools;

import icecaptools.compiler.LDCConstant;

public class LDCBNode extends BNode {
    public LDCConstant constant;

    public LDCBNode(LDCConstant constant, int address, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
        this.constant = constant;
    }

    public LDCConstant getLDCConstant() {
        return constant;
    }

    @Override
    public boolean requiresExtension() {
        if (rawBytes[0] == RawByteCodes.ldc_opcode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void relocateForward(int address, int extension) {
    }

    @Override
    public void relocateBackward(int address, int extension) {
    }

    @Override
    public int extend() throws Exception {
        byte[] rawBytes = new byte[3];
        rawBytes[0] = this.rawBytes[0];
        rawBytes[1] = this.rawBytes[1];
        this.rawBytes = rawBytes;
        return 1;
    }

    @Override
    protected String print() {
        return "ldc";
    }

}
