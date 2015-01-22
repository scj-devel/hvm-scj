package icecaptools;

import icecaptools.conversion.TargetAddressMap;


public class ReturnBNode extends BNode {
    public ReturnBNode(int address, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public void link(BNode[] nodes, TargetAddressMap tmap) throws Exception {
    }

    @Override
    public boolean requiresExtension() {
        if ((rawBytes[0] != RawByteCodes.ret_opcode) && (rawBytes[0] != RawByteCodes.athrow_opcode))
        {
            return true;
        }
        else
        {
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
        int size = stackLayout.getSize();
        if (size < 256)
        {
            byte[] stackInfo = stackLayout.getStackInfo();
            int bytesRequired = stackInfo.length;
            int newSize = 1 + rawBytes.length + bytesRequired;
            byte[] newRawBytes = new byte[newSize];
            
            int index = 0;
            
            while (index < rawBytes.length)
            {
                newRawBytes[index] = rawBytes[index];
                index++;
            }
            newRawBytes[index++] = (byte) bytesRequired;
            
            for (int i = 0; i < stackInfo.length; i++)
            {
                newRawBytes[index++] = stackInfo[i];
            }
            int extension = newRawBytes.length - rawBytes.length;
            this.rawBytes = newRawBytes;
            return extension;
        }
        else
        {
            throw new Exception("Stack too large at return");
        }
    }
    
    @Override
    protected String print() {
        return "return";
    }
}
