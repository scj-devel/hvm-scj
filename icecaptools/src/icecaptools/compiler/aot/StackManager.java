package icecaptools.compiler.aot;

import icecaptools.compiler.NoDuplicatesMemorySegment;

public class StackManager {

    protected StringBuffer output;
    protected NoDuplicatesMemorySegment localVariables;
    protected LocalVariableManager localVariableManager;
    protected boolean isSynthetic;
    protected SPManipulator spManipulator;

    public StackManager(StringBuffer output, NoDuplicatesMemorySegment localVariables, int maxLocals, boolean isSynthetic, SPManipulator spManipulator) {
        this.output = output;
        this.localVariables = localVariables;
        this.spManipulator = spManipulator;
        if (isSynthetic) {
            this.localVariableManager = new PreloadingVariableManager(maxLocals, output, localVariables);
        } else {
            // this.localVariableManager = new
            // CachingLocalVariableManager(maxLocals);
            // this.localVariableManager = new LocalVariableManager(maxLocals);
            // this.localVariableManager = new
            // PreloadingVariableManager(maxLocals, output, localVariables);
            this.localVariableManager = new TypeAwareVariableManager(maxLocals, output, localVariables);
        }
        this.isSynthetic = isSynthetic;
    }

    public void push(String value) {
        push(Size.INT, value);
    }

    public void pop(String dst, int srcSize) {
        popIt(dst, srcSize);
    }

    private void popIt(String dst, int srcSize) {
        String srctype = AOTCompiler.getTypeCast(srcSize);
        if (srcSize == Size.FLOAT) {
            srctype = AOTCompiler.getPointerCast(srcSize);
        } else {
            srctype = AOTCompiler.getTypeCast(srcSize);
        }

        output.append("   sp--;\n");
        spManipulator.setSPUsed(true);
        if (dst != null) {
            if (srcSize == Size.FLOAT) {
                output.append(dst + " = *" + srctype + "sp;\n");
            } else {
                output.append(dst + " = (" + srctype + ")(*sp);\n");
            }
        }
    }

    public void popRef(String dst) {
        spManipulator.setSPUsed(true);
        output.append("   sp--;\n");
        output.append(dst + " = (unsigned char *) (pointer) *sp;\n");
    }

    public String peekTop(int index, int srcSize) {
        String srctype = "";
        if (srcSize != Size.INT) {
            srctype = AOTCompiler.getPointerCast(srcSize);
        }

        String topValue;

        if (index > 0) {
            topValue = "*" + srctype + "(sp - " + index + ")";
        } else {
            topValue = "*" + srctype + "sp";
        }
        spManipulator.setSPUsed(true);
        return topValue;
    }

    public void push(int dstSize, String value) {
        pushIt(dstSize, value);
    }

    protected void pushIt(int dstSize, String value) {
        String dsttype = "";
        if (dstSize != Size.INT) {
            if (dstSize == Size.FLOAT) {
                dsttype = AOTCompiler.getPointerCast(dstSize);
            } else {
                dsttype = AOTCompiler.getTypeCast(dstSize);
            }
        }

        if (dstSize == Size.FLOAT) {
            output.append("   *(" + dsttype + "sp) = " + value + ";\n");
        } else {
            if (dsttype.length() > 0) {
                output.append("   *sp = (int32)(" + dsttype + ")" + value + ";\n");
            } else {
                output.append("   *sp = (int32)" + value + ";\n");
            }
        }
        output.append("   sp++;\n");
        spManipulator.setSPUsed(true);
    }

    public int flush(boolean doit) {
        return 0;
    }

    public void setOutput(StringBuffer output) {
        this.output = output;
    }

    public StackManager copy() {
        StackManager clone = new StackManager(output, this.localVariables, localVariableManager.getMaxLocals(), isSynthetic, this.spManipulator);
        clone.localVariableManager.copyLocals(localVariableManager);
        return clone;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() == StackManager.class) {
            return localVariableManager.equals(((StackManager) other).localVariableManager);
        } else {
            return false;
        }
    }

    public void merge(StackManager other) throws Exception {
        localVariableManager.merge(other.localVariableManager, 0, output, localVariables);
    }

    public void load(int size, int index, int pc) {
        localVariableManager.load(size, index, pc, output, localVariables);
    }

    public String getLocal(int index, int pc) throws Exception {
        return localVariableManager.getLocal(index, pc);
    }

    public void setLocal(int size, int index, int pc) throws Exception {
        localVariableManager.setLocal(size, index, pc, localVariables);
    }

    public void flushLocals() {
        localVariableManager.flush(output, false);
    }

    public StringBuffer getOutput() {
        return output;
    }

    public void flushTop(boolean doit) {

    }

    public LocalVariableManager getLocalVariableManager() {
        return this.localVariableManager;
    }

    public boolean isCached(int i) {
        return false;
    }

    public int topSize() {
        return Size.INT;
    }
}
