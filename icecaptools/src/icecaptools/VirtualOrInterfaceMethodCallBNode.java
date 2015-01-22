package icecaptools;

import icecaptools.compiler.ByteCodePatcher;
import icecaptools.compiler.IDGenerator;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.classfile.JavaClass;

public abstract class VirtualOrInterfaceMethodCallBNode extends MethodCallBNode {

    public VirtualOrInterfaceMethodCallBNode(int address, String className, String methodName, String methodSig, String locationClass, String locationMethod, String locationMethodSignature) {
        super(address, className, methodName, methodSig, locationClass, locationMethod, locationMethodSignature);
    }

    public byte[] getJumpTable(MethodOrFieldDesc calledMethod, ByteCodePatcher patcher, IDGenerator idGen) throws Exception {
        ArrayList<Byte> jTable = new ArrayList<Byte>();
        int numberOfTargets = getNumberOfTargets();
        if (numberOfTargets > targetThreshold()) {
            jTable.add((byte) numberOfTargets);
            Iterator<JavaClass> targets = targetClasses.iterator();
            while (targets.hasNext()) {
                String nextTarget = targets.next().getClassName();
                int classNumber = patcher.getClassNumber(nextTarget);

                MethodOrFieldDesc referredMethod = new MethodOrFieldDesc(nextTarget, calledMethod.getName(), calledMethod.getSignature());
                int methodNumber = patcher.getMethodNumber(referredMethod, idGen);

                jTable.add((byte) (classNumber >> 8));
                jTable.add((byte) (classNumber & 0xff));
                jTable.add((byte) (methodNumber >> 8));
                jTable.add((byte) (methodNumber & 0xff));
            }
        } else {
            jTable.add((byte) 0);
        }
        byte[] jumpTable = new byte[jTable.size()];
        for (int i = 0; i < jumpTable.length; i++) {
            jumpTable[i] = jTable.get(i);
        }
        return jumpTable;
    }

    protected abstract int targetThreshold();

    public Iterator<JavaClass> getTargetClasses() {
        return targetClasses.iterator();
    }

}
