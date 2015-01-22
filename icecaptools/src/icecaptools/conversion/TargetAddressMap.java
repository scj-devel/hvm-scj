package icecaptools.conversion;

import icecaptools.BNode;

import java.util.ArrayList;

/* Change this to contain nodes instead of absolute addresses */

public class TargetAddressMap {

    private ArrayList<BNode> branchTargets;
    
    TargetAddressMap()
    {
        branchTargets = new ArrayList<BNode>();
    }
    
    public void markJumpTarget(BNode target) {
        branchTargets.add(target);
    }

    public boolean isJumpTarget(BNode target) {
        return branchTargets.contains(target);
    }
}
