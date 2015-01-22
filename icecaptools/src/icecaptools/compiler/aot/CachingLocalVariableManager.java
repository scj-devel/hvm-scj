package icecaptools.compiler.aot;

import icecaptools.compiler.NoDuplicatesMemorySegment;

public class CachingLocalVariableManager extends LocalVariableManager {

    private static final String LV_PREFIX = "lv_";

    public CachingLocalVariableManager(int maxLocals) {
        super(maxLocals);
    }

    @Override
    public void merge(LocalVariableManager other, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) throws Exception {
        for (int i = 0; i < locals.length; i++) {
            LocalVariable left;
            LocalVariable right;

            left = locals[i];
            right = other.locals[i];

            if ((left == null) && (right != null)) {
                load(right.size, i, pc, output, localVariables);
            } else if ((left != null) && (right == null)) {
                String lvType = AOTCompiler.getTypeCast(left.size);
                String lvName = localVariableCacheName(i);
                output.append("   *(" + lvType + "*)(fp + " + i + ") = " + lvName + ";\n");
                locals[i] = null;
            }
        }
    }

    private String localVariableCacheName(int i) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getPreferredName(i));

        locals[i].used = true;

        return buffer.toString();
    }

    @Override
    public void clear() {
        for (int i = 0; i < locals.length; i++) {
            if (locals[i] != null) {
                locals[i].used = false;
            }
        }
    }

    protected String getPreferredName(int index) {
        return LV_PREFIX + index;
    }

    @Override
    public void flush(StringBuffer output, boolean doit) {
        for (int i = 0; i < locals.length; i++) {
            LocalVariable current = locals[i];
            if (current != null) {
                String lvType = AOTCompiler.getTypeCast(current.size);
                String lvName = localVariableCacheName(i);
                output.append("   *(" + lvType + "*)(fp + " + i + ") = " + lvName + ";\n");
            }
            if (doit) {
                locals[i] = null;
            }
        }
    }

    @Override
    public void load(int size, int index, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) {
        if (locals[index] == null) {
            locals[index] = new LocalVariable();
            locals[index].size = size;
            loadIt(size, index, output, localVariables, ";\n");
        } else {
            ;
        }
    }

    @Override
    public String generateLocalInitialization(NoDuplicatesMemorySegment localVariables, int from, int to, String seperator, boolean forceLoad) {
        StringBuffer initBuffer = new StringBuffer();
        for (int index = from; index < to; index++) {
            if (locals[index].used || forceLoad) {
                loadIt(locals[index].size, index, initBuffer, localVariables, seperator);
            }
        }
        return initBuffer.toString();
    }

    protected void loadIt(int size, int index, StringBuffer output, NoDuplicatesMemorySegment localVariables, String seperator) {
        String lvName = localVariableCacheName(index);
        String lvType = AOTCompiler.getTypeCast(size);
        String modifier = locals[index].modifier;
        String init = "";
        if (size != Size.FLOAT) {
            output.append("   " + lvName + " = (" + lvType + ")(*(fp + " + index + "));\n");
        } else {
            output.append("   " + lvName + " = *(" + lvType + "*)(fp + " + index + ");\n");
        }
        if (lvName.startsWith(LV_PREFIX)) {
            if (seperator.contains("\n")) {
                init = " = 0";
            }
        }
        localVariables.print("   " + modifier + " " + lvType + " " + lvName + init + seperator);
    }

    @Override
    public String getLocal(int index, int pc) throws Exception {
        if (locals[index] != null) {
            String lvName = localVariableCacheName(index);
            return lvName;
        } else {
            throw new Exception("Accessing unloaded local variable");
        }
    }

    @Override
    public void setLocal(int size, int index, int pc, NoDuplicatesMemorySegment localVariables) throws Exception {
        setIt(size, index, localVariables);
    }

    protected void setIt(int size, int index, NoDuplicatesMemorySegment localVariables) {
        if (locals[index] == null) {
            locals[index] = new LocalVariable();
            locals[index].size = size;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CachingLocalVariableManager) {
            CachingLocalVariableManager other = (CachingLocalVariableManager) obj;

            for (int i = 0; i < this.locals.length; i++) {
                LocalVariable left = locals[i];
                LocalVariable right = other.locals[i];

                if ((left == null) && (right != null)) {
                    return false;
                }
                if ((left != null) && (right == null)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
