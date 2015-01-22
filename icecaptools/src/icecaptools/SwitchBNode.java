package icecaptools;

import icecaptools.RawByteCodes.RawSwitch;
import icecaptools.conversion.TargetAddressMap;

import java.util.ArrayList;
import java.util.Iterator;

public class SwitchBNode extends BNode {
    ArrayList<Long> targets;
    private int initialAddress;
    private RawSwitch tlSwitch;
    
    public SwitchBNode(RawSwitch tableSwitch, int address, long defaultVal, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, locationClass, locationMethod, locationMethodSignature);
        targets = new ArrayList<Long>();
        initialAddress = address;
        addTargetAddress(defaultVal);
        this.tlSwitch = tableSwitch;
    }

    public RawSwitch getTableSwitch() {
        return tlSwitch;
    }

    public void addTargetAddress(long l) {
        targets.add(new Long(l));
    }

    public int getNumberOfTargets()
    {
        return targets.size();
    }
    
    @Override
    public void link(BNode[] nodes, TargetAddressMap tmap) throws Exception {
        Iterator<Long> iterator = targets.iterator();
        while (iterator.hasNext()) {
            Long next = iterator.next();
            BNode branchTarget = findNodeWithAddress(nodes, address + next.intValue());
            tmap.markJumpTarget(branchTarget);
            addChild(branchTarget);
        }
    }

    @Override
    public boolean requiresExtension() {
        return true;
    }

   
    @Override
    public void relocateForward(int address, int extension) {
        for (int i = 0; i < targets.size(); i++) {
            Long current = targets.get(i);
            long branchoffset = current.longValue();
            if (branchoffset < 0) {
                if (this.address + branchoffset <= address) {
                    branchoffset -= extension;
                    targets.set(i, new Long(branchoffset));
                }
            }
        }
    }

    @Override
    public void relocateBackward(int address, int extension) {
        for (int i = 0; i < targets.size(); i++) {
            Long current = targets.get(i);
            long branchoffset = current.longValue();
            if (branchoffset > 0) {
                if (this.address + branchoffset > address) {
                    branchoffset += extension;
                    targets.set(i, new Long(branchoffset));
                }
            }
        }
    }

    @Override
    public byte[] getRawBytes() {
        int startOffset, step;
        int padding = 3;

        startOffset = 1 + padding + 12;
        
        if (rawBytes[0] == RawByteCodes.tableswitch_opcode) {
            step = 4;
        } else {
            step = 8;
        }

        byte branchbyte1, branchbyte2, branchbyte3, branchbyte4;

        long defaultVal = targets.get(0);
        
        branchbyte1 = (byte) ((defaultVal >> 24) & 0xff);
        branchbyte2 = (byte) ((defaultVal >> 16) & 0xff);
        branchbyte3 = (byte) ((defaultVal >> 8) & 0xff);
        branchbyte4 = (byte) (defaultVal & 0xff);
        
        rawBytes[4] = branchbyte1;
        rawBytes[5] = branchbyte2;
        rawBytes[6] = branchbyte3;
        rawBytes[7] = branchbyte4;
        
        for (int i = 1; i < targets.size(); i++)
        {
            Long offset = targets.get(i);

            branchbyte1 = (byte) ((offset >> 24) & 0xff);
            branchbyte2 = (byte) ((offset >> 16) & 0xff);
            branchbyte3 = (byte) ((offset >> 8) & 0xff);
            branchbyte4 = (byte) (offset & 0xff);

            rawBytes[startOffset] = branchbyte1;
            rawBytes[startOffset + 1] = branchbyte2;
            rawBytes[startOffset + 2] = branchbyte3;
            rawBytes[startOffset + 3] = branchbyte4;

            startOffset += step;
        }
        
        return super.getRawBytes();
    }
    
    @Override
    public int extend() throws Exception {
        int padding = 0;
        
        initialAddress++;
        
        while ((initialAddress >> 2) << 2 != initialAddress)
        {
            initialAddress++;
            padding++;
        }
        initialAddress -= padding + 1;
        
        byte[] rawBytes = new byte[this.rawBytes.length + (3 - padding)];
        rawBytes[0] = this.rawBytes[0];
        
        int src = padding + 1;
        int dst = 4;
        while (src < this.rawBytes.length)
        {
            rawBytes[dst++] = this.rawBytes[src++];
        }
        
        this.rawBytes = rawBytes;
        
        return 3 - padding;
    }

    @Override
    protected String print() {
        StringBuffer buffer = new StringBuffer();
        Iterator<Long> iterator = targets.iterator();
        buffer.append("switch ");
        while (iterator.hasNext()) {
            buffer.append(iterator.next());
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public BNode findTarget(long offset) {
        Iterator<BNode> iterator = getChildren();
        while (iterator.hasNext())
        {
            BNode next = iterator.next();
            if (next.getOriginalAddress() == getOriginalAddress() + offset)
            {
                return next;
            }
        }
        return null;
    }
}
