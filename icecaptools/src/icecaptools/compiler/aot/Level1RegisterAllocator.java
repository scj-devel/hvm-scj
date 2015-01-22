package icecaptools.compiler.aot;

import icecaptools.compiler.NoDuplicatesMemorySegment;

public class Level1RegisterAllocator extends StackManager {

    protected StackCellInfo top;

    public Level1RegisterAllocator(StringBuffer output, NoDuplicatesMemorySegment localVariables, int maxLocals, SPManipulator spManipulator) {
        super(output, localVariables, maxLocals, false, spManipulator);
        top = null;
    }

    @Override
    public void pop(String dst, int srcSize) {
        if (top == null) {
            super.pop(dst, srcSize);
        } else {
            output.append(dst + " = " + top.v_name + ";\n");
            top = null;
        }
    }

    @Override
    public void popRef(String dst) {
        if (top == null) {
            super.popRef(dst);
        } else {
            output.append(dst + " = (unsigned char *) (pointer)" + top.v_name + ";\n");
            top = null;
        }
    }

    @Override
    public String peekTop(int index, int srcSize) {
        if (top == null) {
            return super.peekTop(index, srcSize);
        } else {
            if (index == 1) {
                return top.v_name;
            } else {
                return super.peekTop(index - 1, srcSize);
            }
        }
    }

    @Override
    public void push(int dstSize, String value) {
        if (top == null) {
            top = new StackCellInfo();
            top.size = dstSize;
            switch (top.size) {
            case Size.BYTE:
                top.v_name = "b_val";
                break;
            case Size.SHORT:
                top.v_name = "s_val";
                break;
            case Size.INT:
            default:
                top.v_name = "i_val";
                break;
            }
            output.append("   " + top.v_name + " = " + value + ";\n");
            localVariables.print("   " + AOTCompiler.getTypeCast(dstSize) + " " + top.v_name + ";\n");
        } else {
            flush(true);
            push(dstSize, value);
        }
    }

    @Override
    public int flush(boolean doit) {
        if (top != null) {
            super.push(top.size, top.v_name);
            if (doit) {
                top = null;
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int topSize() {
        if (top != null)
        {
            return top.size;
        }
        else
        {
            return Size.INT;
        }
    }
}
