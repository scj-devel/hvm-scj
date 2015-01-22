package icecaptools.compiler.aot;

import icecaptools.compiler.NoDuplicatesMemorySegment;

import org.apache.bcel.classfile.Method;

public class PreloadingVariableManager extends CachingLocalVariableManager {
    protected int level;

    public PreloadingVariableManager(int maxLocals, StringBuffer output, NoDuplicatesMemorySegment localVariables) {
        super(maxLocals);
    }

    @Override
    public void init(StringBuffer output, NoDuplicatesMemorySegment localVariables, int level, Method javaMethod) {
        for (int index = 0; index < getMaxLocals(); index++)
        {
            if (index <= level)
            {
                if (locals[index] == null) {
                    locals[index] = new LocalVariable();
                    locals[index].size = Size.INT;
                } else {
                    ;
                }
            }
        }
        this.level = level;
    }

    @Override
    public void merge(LocalVariableManager other, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) throws Exception {
    }

    @Override
    public void load(int size, int index, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) {
    }

    @Override
    public String getLocal(int index, int pc) throws Exception {
        if (index <= level) {
            return super.getLocal(index, pc);
        } else {
            return "fp[" + index + "]";
        }
    }

    @Override
    public void flush(StringBuffer output, boolean doit) {
        ;
    }

    @Override
    public void setLocal(int size, int index, int pc, NoDuplicatesMemorySegment localVariables) throws Exception {
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
    
    @Override
    protected void copy(LocalVariableManager right) {
        locals = right.locals;
        this.level = ((PreloadingVariableManager)right).level;
    }
}
