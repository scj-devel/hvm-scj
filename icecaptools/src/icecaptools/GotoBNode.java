package icecaptools;

import icecaptools.conversion.TargetAddressMap;

public class GotoBNode extends BranchBNode {

    public GotoBNode(int address, int branchoffset, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, branchoffset, locationClass, locationMethod, locationMethodSignature);
    }

    @Override
    public void link(BNode[] nodes, TargetAddressMap tmap) throws Exception {
        BNode branchTarget = findNodeWithAddress(nodes, address + branchoffset);
        tmap.markJumpTarget(branchTarget);
        addChild(branchTarget);
    }
    
    @Override
    public void setAddress(int address) {
        super.setAddress(address);
    }
}
