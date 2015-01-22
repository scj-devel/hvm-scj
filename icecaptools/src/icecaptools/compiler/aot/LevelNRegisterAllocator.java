package icecaptools.compiler.aot;

import icecaptools.compiler.NoDuplicatesMemorySegment;

public class LevelNRegisterAllocator extends StackManager {

    private static class StackCell extends StackCellInfo {
        public boolean inUse;
        public byte postfix;
    }

    private StackCell[] stackCache;

    public LevelNRegisterAllocator(StringBuffer output, NoDuplicatesMemorySegment localVariables, int level, int maxLocals, boolean isSynthetic, SPManipulator spManipulator) {
        super(output, localVariables, maxLocals, isSynthetic, spManipulator);
        stackCache = new StackCell[level];
        for (int i = 0; i < level; i++) {
            StackCell next = new StackCell();
            next.inUse = false;
            next.size = -1;
            next.postfix = (byte) i;
            next.v_name = "";
            stackCache[i] = next;
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = stackCache.length - 1; i >= 0; i--) {
            StackCell next = stackCache[i];
            if (next.inUse) {
                switch (next.size) {
                case Size.BYTE:
                    buffer.append("byte ");
                    break;
                case Size.SHORT:
                    buffer.append("short ");
                    break;
                case Size.INT:
                    buffer.append("int ");
                    break;
                default:
                    buffer.append("??? ");
                }

                buffer.append(next.v_name);
            } else {
                buffer.append("---");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public LevelNRegisterAllocator(NoDuplicatesMemorySegment localVariables, int level, int maxLocals, boolean isSynthetic, SPManipulator spManipulator) {
        this(null, localVariables, level, maxLocals, isSynthetic, spManipulator);
    }

    @Override
    public void push(int dstSize, String value) {
        StackCell top = null;
        int index;
        for (index = stackCache.length - 1; index >= 0; index--) {
            if (stackCache[index].inUse == false) {
                top = stackCache[index];
                break;
            }
        }
        if (top != null) {
            if (top.size != dstSize) {
                top.size = dstSize;
                switch (top.size) {
                case Size.BYTE:
                    top.v_name = "b_val" + top.postfix;
                    break;
                case Size.SHORT:
                    top.v_name = "s_val" + top.postfix;
                    break;
                case Size.FLOAT:
                    top.v_name = "f_val" + top.postfix;
                    break;
                case Size.INT:
                default:
                    top.v_name = "i_val" + top.postfix;
                    break;
                }
            }
            output.append("   " + top.v_name + " = " + value + ";\n");
            localVariables.print("   " + AOTCompiler.getTypeCast(dstSize) + " " + top.v_name + ";\n");
            top.inUse = true;
        } else {
            top = stackCache[stackCache.length - 1];
            pushIt(top.size, top.v_name);
            top.inUse = false;

            for (index = stackCache.length - 1; index > 0; index--) {
                stackCache[index] = stackCache[index - 1];
            }
            stackCache[0] = top;
            push(dstSize, value);
        }
    }

    @Override
    public void pop(String dst, int srcSize) {
        StackCell top = findTop();

        if (top != null) {
            if (dst != null) {
                output.append(dst + " = " + top.v_name + ";\n");
            }
            top.inUse = false;
        } else {
            super.pop(dst, srcSize);
        }
    }

    protected StackCell findTop() {
        int index;
        StackCell top = null;

        for (index = 0; index < stackCache.length; index++) {
            if (stackCache[index].inUse) {
                top = stackCache[index];
                break;
            }
        }
        return top;
    }

    @Override
    public void popRef(String dst) {
        StackCell top = findTop();

        if (top != null) {
            output.append(dst + " = (unsigned char *) (pointer)" + top.v_name + ";\n");
            top.inUse = false;
        } else {
            super.popRef(dst);
        }
    }

    @Override
    public int flush(boolean doit) {
        int index;
        int count = 0;
        for (index = stackCache.length - 1; index >= 0; index--) {
            StackCell top = stackCache[index];
            if (top.inUse) {
                super.push(top.size, top.v_name);
                if (doit) {
                    top.inUse = false;
                }
                count++;
            }
        }
        return count;
    }

    @Override
    public void flushTop(boolean doit) {
        int index;
        for (index = 0; index < stackCache.length; index++) {
            StackCell top = stackCache[index];
            if (top.inUse) {
                super.push(top.size, top.v_name);
                if (doit) {
                    top.inUse = false;
                }
                break;
            }
        }
    }

    @Override
    public String peekTop(int index, int srcSize) {
        StackCell top = null;
        int i;

        for (i = 0; i < stackCache.length; i++) {
            if (stackCache[i].inUse) {
                if (index > 1) {
                    index--;
                } else {
                    top = stackCache[i];
                    break;
                }
            }
        }
        if (top != null) {
            return top.v_name;
        } else {
            return super.peekTop(index, srcSize);
        }
    }

    @Override
    public StackManager copy() {
        LevelNRegisterAllocator clone = new LevelNRegisterAllocator(output, localVariables, this.stackCache.length, localVariableManager.getMaxLocals(), this.isSynthetic, this.spManipulator);

        for (int i = 0; i < stackCache.length; i++) {
            StackCell next = clone.stackCache[i];
            next.inUse = stackCache[i].inUse;
            next.size = stackCache[i].size;
            next.postfix = stackCache[i].postfix;
            next.v_name = stackCache[i].v_name;
        }
        clone.localVariableManager.copyLocals(localVariableManager);
        return clone;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() == LevelNRegisterAllocator.class) {
            LevelNRegisterAllocator otherStack = (LevelNRegisterAllocator) other;
            if (otherStack.stackCache.length == stackCache.length) {
                for (int i = 0; i < stackCache.length; i++) {
                    StackCell thisCell = stackCache[i];
                    StackCell otherCell = otherStack.stackCache[i];

                    if (thisCell.inUse != otherCell.inUse) {
                        return false;
                    } else {
                        if (!thisCell.inUse) {
                            continue;
                        }
                    }

                    if (thisCell.postfix != otherCell.postfix) {
                        return false;
                    }
                    if (thisCell.size != otherCell.size) {
                        return false;
                    }

                    if (!thisCell.v_name.equals(otherCell.v_name)) {
                        return false;
                    }
                }
                return localVariableManager.equals(otherStack.localVariableManager);
            }
        }
        return false;
    }

    public void merge(StackManager other) throws Exception {
        super.merge(other);
        LevelNRegisterAllocator targetStack = (LevelNRegisterAllocator) other;

        if (targetStack.stackCache.length == stackCache.length) {
            for (int i = stackCache.length - 1; i >= 0; i--) {
                StackCell left, right;
                left = stackCache[i];
                right = targetStack.stackCache[i];

                if ((left.inUse) && (!right.inUse)) {
                    super.push(left.size, left.v_name);
                    left.inUse = false;
                } else if ((!left.inUse) && right.inUse) {
                    int rightCacheSize = targetStack.getCacheSize();
                    int leftCacheSize = getCacheSize();
                    int cacheSize = rightCacheSize - leftCacheSize;
                    output.append("   sp -= " + cacheSize + ";\n");
                    spManipulator.setSPUsed(true);
                    while (cacheSize > 0) {
                        right = targetStack.stackCache[i];
                        left = stackCache[i];

                        String srctype = "";
                        if (right.size != Size.INT) {
                            srctype = AOTCompiler.getPointerCast(right.size);
                        }
                        output.append("   " + right.v_name + " = *" + srctype + "sp;\n");
                        output.append("   sp++;\n");

                        cacheSize--;
                        i--;
                        left.inUse = right.inUse;
                        left.postfix = right.postfix;
                        left.v_name = right.v_name;
                        left.size = right.size;
                    }
                    while (i >= 0) {
                        right = targetStack.stackCache[i];
                        left = stackCache[i];
                        left.inUse = right.inUse;
                        left.postfix = right.postfix;
                        left.v_name = right.v_name;
                        left.size = right.size;
                        i--;
                    }
                } else if ((left.inUse) && right.inUse) {
                    if (left.size == right.size) {
                        if (left.v_name.equals(right.v_name)) {
                            continue;
                        }
                    }

                    System.out.println("Unsupported merge [" + spManipulator.getCurrentMethod() + "]");
                    System.out.println("failure on index: " + i);
                    System.out.println("src: ");
                    System.out.println(this.toString());
                    System.out.println("dst: ");
                    System.out.println(other.toString());

                    throw new Exception("Unsupported merge");
                }
            }
        } else {
            throw new Exception("Unmergeable merge");
        }
    }

    private int getCacheSize() {
        int count = 0;
        for (int i = stackCache.length - 1; i >= 0; i--) {
            if (stackCache[i].inUse) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    @Override
    public boolean isCached(int offset) {
        int index;

        offset++;

        for (index = 0; index < stackCache.length; index++) {
            if (stackCache[index].inUse) {
                break;
            }
        }

        while (index < stackCache.length) {
            if (offset > 0) {
                index++;
                offset--;
            } else {
                break;
            }
        }

        if (offset == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int topSize() {
        StackCell top = findTop();

        if (top != null) {
            return top.size;
        } else {
            return Size.INT;
        }
    }
}
