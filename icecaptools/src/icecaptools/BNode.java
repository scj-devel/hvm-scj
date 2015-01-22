package icecaptools;

import icecaptools.compiler.aot.StackManager;
import icecaptools.compiler.utils.CallGraph;
import icecaptools.conversion.TargetAddressMap;
import icecaptools.stackanalyser.AbstractStack;
import icecaptools.stackanalyser.ProducerConsumerNodeInfo;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class BNode {
    private ArrayList<BNode> children;
    protected int address;
    protected int originalAddress;
    private NewList newList;
    private ArrayList<String> exceptionsThrownList;
    public String locationClass;
    public String locationMethod;
    public String locationMethodSignature;
    protected byte[] rawBytes;

    private RawByteCodes.RawBytecode rawByteCode;

    private boolean redundant;

    protected ProducerConsumerNodeInfo ainfo;
    protected AbstractStack stackLayout;
    private StringBuffer translation;
    private StackManager sm;

    public BNode(int address, String locationClass, String locationMethod, String locationMethodSignature) {
        this.address = address;
        this.originalAddress = address;
        this.locationClass = locationClass;
        this.locationMethod = locationMethod;
        this.locationMethodSignature = locationMethodSignature;
        this.redundant = false;
        children = new ArrayList<BNode>();
        this.newList = null;
        exceptionsThrownList = new ArrayList<String>();

        ainfo = new ProducerConsumerNodeInfo();
    }

    public ProducerConsumerNodeInfo getAinfo() {
        return ainfo;
    }

    public int getAddress() {
        return address;
    }

    public int getOriginalAddress() {
        return originalAddress;
    }

    public void addChild(BNode child) {
        children.add(child);
    }

    public Iterator<BNode> getChildren() {
        return children.iterator();
    }

    public void link(BNode[] nodes, TargetAddressMap tmap) throws Exception {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == this) {
                if (i + 1 < nodes.length) {
                    addChild(nodes[i + 1]);
                }
            }
        }
        if (throwsExceptions()) {
            Iterator<String> exceptions = getExceptionsThrown();
            while (exceptions.hasNext()) {
                SpecialMethodCallBNode initException = new RuntimeMethodCallBNode(address, exceptions.next(), "<init>", "()V", locationClass, locationMethod, locationMethodSignature);
                byte[] invokespecial = { RawByteCodes.invokespecial_opcode };
                initException.setRawBytes(invokespecial);
                addChild(initException);
            }
        }
    }

    protected static BNode findNodeWithAddress(BNode[] nodes, int address) throws Exception {
        for (int i = 0; i < nodes.length; i++) {
            BNode current = nodes[i];
            if (current.getAddress() == address) {
                return current;
            }
        }
        throw new Exception("Could not find branch target");
    }

    public NewList getNewList() {
        return newList;
    }

    public void setNewList(NewList newList) {
        this.newList = newList;
    }

    public boolean throwsExceptions() {
        return exceptionsThrownList != null;
    }

    public Iterator<String> getExceptionsThrown() {
        return exceptionsThrownList.iterator();
    }

    public void setExceptionsThrown(IcecapIterator<String> exceptionsThrown) {
        ArrayList<String> exceptionsThrownList = new ArrayList<String>();
        while (exceptionsThrown.hasNext()) {
            exceptionsThrownList.add(exceptionsThrown.next());
        }
        if (exceptionsThrownList.size() > 0) {
            this.exceptionsThrownList = exceptionsThrownList;
        }
    }

    public void setRawBytes(byte[] rawBytes) {
        this.rawBytes = rawBytes;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public abstract boolean requiresExtension();

    public int extend() throws Exception {
        throw new Exception("Not implemented");
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public abstract void relocateForward(int address, int extension);

    public abstract void relocateBackward(int address, int extension);

    protected abstract String print();

    public byte getOpCode() {
        return rawBytes[0];
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("   " + this.originalAddress + ": ");
        buffer.append(print());
        return buffer.toString();
    }

    public void setStackLayout(AbstractStack stackLayout) {
        this.stackLayout = stackLayout;
    }

    public AbstractStack getStackLayout() {
        return stackLayout;
    }

    public StringBuffer getTranslation() {
        return translation;
    }

    public void setTranslation(StringBuffer translation) {
        this.translation = translation;
    }

    public void setStackManager(StackManager sm) {
        this.sm = sm;
    }

    public StackManager getStackManager() {
        return this.sm;
    }

    public boolean isRedundant() {
        return redundant;
    }

    public void setRedundant(boolean redundant) {
        this.redundant = redundant;
    }

    public void setRawBytecode(RawByteCodes.RawBytecode rawByteCode) {
        this.rawByteCode = rawByteCode;
    }

    public RawByteCodes.RawBytecode getRawByteCode() {
        return rawByteCode;
    }

    private String targetClassName;
    private String targetMethodName;
    private String targetMethodSignature;

    public void registerInCallGraph(CallGraph cg, String targetClassName, String targetMethodName, String targetMethodSignature) {
        if (this.targetClassName == null) {
            this.targetClassName = targetClassName;
            this.targetMethodName = targetMethodName;
            this.targetMethodSignature = targetMethodSignature;
            cg.register(this, locationClass, locationMethod, locationMethodSignature, targetClassName, targetMethodName, targetMethodSignature);
        } else {
            if ((this.targetMethodName != targetMethodName) || (this.targetMethodSignature != targetMethodSignature) || (this.targetClassName != targetClassName)) {
                cg.register(this, locationClass, locationMethod, locationMethodSignature, targetClassName, targetMethodName, targetMethodSignature);
            } else {
                // System.out.println("Saved a call to register");
            }
        }
    }

    private boolean exceptionsHandled;
    
    public boolean exceptionsHandled() {
        return exceptionsHandled;
    }

    public void setExceptionsHandled() {
        exceptionsHandled = true;
    }
}
