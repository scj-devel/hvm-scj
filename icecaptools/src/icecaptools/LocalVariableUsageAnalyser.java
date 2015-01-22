package icecaptools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LocalVariableUsageAnalyser {

    private MethodEntryPoints entryPoints;

    public LocalVariableUsageAnalyser(MethodEntryPoints entryPoints) {
        this.entryPoints = entryPoints;
    }

    public void analyse() {
        ArrayList<BNode> nodes = entryPoints.getBNodes();
        for (BNode bNode : nodes) {
            byte opcode = bNode.getOpCode();
            switch (opcode) {
            case RawByteCodes.astore_opcode:
            case RawByteCodes.astore_0_opcode:
            case RawByteCodes.astore_1_opcode:
            case RawByteCodes.astore_2_opcode:
            case RawByteCodes.astore_3_opcode:
            case RawByteCodes.istore_opcode:
            case RawByteCodes.istore_0_opcode:
            case RawByteCodes.istore_1_opcode:
            case RawByteCodes.istore_2_opcode:
            case RawByteCodes.istore_3_opcode: {
                int index;
                if (isIstoreOpcode(opcode)) {
                    index = getLocalVariableIndex(bNode, RawByteCodes.istore_opcode, RawByteCodes.istore_0_opcode);
                } else {
                    index = getLocalVariableIndex(bNode, RawByteCodes.astore_opcode, RawByteCodes.astore_0_opcode);
                }
                ArrayList<Integer> accessedLocals = new ArrayList<Integer>();
                Set<BNode> visitedNodes = new HashSet<BNode>();
                analyseIt(bNode, visitedNodes, accessedLocals);
                if (!accessedLocals.contains(index)) {
                    bNode.setRedundant(true);
                }
                break;
            }
            }
        }
    }

    private boolean isIstoreOpcode(byte opcode) {
        switch (opcode) {
        case RawByteCodes.istore_opcode:
        case RawByteCodes.istore_0_opcode:
        case RawByteCodes.istore_1_opcode:
        case RawByteCodes.istore_2_opcode:
        case RawByteCodes.istore_3_opcode:
            return true;
        }
        return false;
    }

    private void analyseIt(BNode bNode, Set<BNode> visitedNodes, ArrayList<Integer> accessedLocals) {
        switch (bNode.getOpCode()) {
        case RawByteCodes.dload_opcode:
        case RawByteCodes.dload_0_opcode:
        case RawByteCodes.dload_1_opcode:
        case RawByteCodes.dload_2_opcode:
        case RawByteCodes.dload_3_opcode:
            addIndex(accessedLocals, bNode, RawByteCodes.dload_opcode, RawByteCodes.dload_0_opcode);
            break;
        case RawByteCodes.fload_opcode:
        case RawByteCodes.fload_0_opcode:
        case RawByteCodes.fload_1_opcode:
        case RawByteCodes.fload_2_opcode:
        case RawByteCodes.fload_3_opcode:
            addIndex(accessedLocals, bNode, RawByteCodes.fload_opcode, RawByteCodes.fload_0_opcode);
            break;
        case RawByteCodes.lload_opcode:
        case RawByteCodes.lload_0_opcode:
        case RawByteCodes.lload_1_opcode:
        case RawByteCodes.lload_2_opcode:
        case RawByteCodes.lload_3_opcode:
            addIndex(accessedLocals, bNode, RawByteCodes.lload_opcode, RawByteCodes.lload_0_opcode);
            break;
        case RawByteCodes.iload_opcode:
        case RawByteCodes.iload_0_opcode:
        case RawByteCodes.iload_1_opcode:
        case RawByteCodes.iload_2_opcode:
        case RawByteCodes.iload_3_opcode:
            addIndex(accessedLocals, bNode, RawByteCodes.iload_opcode, RawByteCodes.iload_0_opcode);
            break;
        case RawByteCodes.aload_opcode:
        case RawByteCodes.aload_0_opcode:
        case RawByteCodes.aload_1_opcode:
        case RawByteCodes.aload_2_opcode:
        case RawByteCodes.aload_3_opcode:
            addIndex(accessedLocals, bNode, RawByteCodes.aload_opcode, RawByteCodes.aload_0_opcode);
            break;
        case RawByteCodes.iinc_opcode: {
            addIndex(accessedLocals, bNode, RawByteCodes.iinc_opcode, RawByteCodes.iinc_opcode);
            break;
        }
        }

        visitedNodes.add(bNode);
        Iterator<BNode> children = bNode.getChildren();
        while (children.hasNext()) {
            BNode next = children.next();
            if (!visitedNodes.contains(next)) {
                analyseIt(next, visitedNodes, accessedLocals);
            }
        }
    }

    private void addIndex(ArrayList<Integer> accessedLocals, BNode bNode, byte dynamic_opcode, byte static_opcode) {
        int index = getLocalVariableIndex(bNode, dynamic_opcode, static_opcode);
        accessedLocals.add(index);
    }

    protected int getLocalVariableIndex(BNode bNode, byte dynamic_opcode, byte static_opcode) {
        int index;
        if (bNode.getOpCode() == dynamic_opcode) {
            index = bNode.getRawBytes()[1];
        } else {
            index = bNode.getOpCode() - static_opcode;
        }
        return index;
    }
}
