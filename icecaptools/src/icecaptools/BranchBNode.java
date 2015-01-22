package icecaptools;

import icecaptools.conversion.TargetAddressMap;

public class BranchBNode extends BNode {
    protected int branchoffset;
    protected int originalBranchoffset;

    public BranchBNode(int address, int branchoffset, String locationClass, String locationMethod, String locationMethodSignature) {
        super((int) address, locationClass, locationMethod, locationMethodSignature);
        this.branchoffset = branchoffset;
        this.originalBranchoffset = branchoffset;
    }

    public int getBranchoffset() {
        return branchoffset;
    }

    @Override
    public void link(BNode[] nodes, TargetAddressMap tmap) throws Exception {
        super.link(nodes, tmap);
        BNode branchTarget = findNodeWithAddress(nodes, address + branchoffset);
        tmap.markJumpTarget(branchTarget);
        addChild(branchTarget);
    }

    @Override
    public boolean requiresExtension() {
        return false;
    }

    @Override
    public void relocateForward(int address, int extension) {
        if (branchoffset < 0) {
            if (this.address + branchoffset <= address) {
                branchoffset -= extension;
            }
        }

    }

    @Override
    public void relocateBackward(int address, int extension) {
        if (branchoffset > 0) {
            if (this.address + branchoffset > address) {
                branchoffset += extension;
            }
        }
    }

    @Override
    public byte[] getRawBytes() {
        byte branchbyte1, branchbyte2;
        if ((rawBytes[0] == RawByteCodes.goto_w_opcode) || (rawBytes[0] == RawByteCodes.jsr_w_opcode)) {
            byte branchbyte3, branchbyte4;
            branchbyte1 = (byte) ((branchoffset >> 24) & 0xff);
            branchbyte2 = (byte) ((branchoffset >> 16) & 0xff);
            branchbyte3 = (byte) ((branchoffset >> 8) & 0xff);
            branchbyte4 = (byte) (branchoffset & 0xff);
            rawBytes[3] = branchbyte3;
            rawBytes[4] = branchbyte4;
        } else {
            branchbyte1 = (byte) ((branchoffset >> 8) & 0xff);
            branchbyte2 = (byte) (branchoffset & 0xff);
        }
        rawBytes[1] = branchbyte1;
        rawBytes[2] = branchbyte2;
        return super.getRawBytes();
    }

    @Override
    protected String print() {
        return "branch " + (originalAddress + originalBranchoffset) + "(" + originalBranchoffset + ")";
    }
}
