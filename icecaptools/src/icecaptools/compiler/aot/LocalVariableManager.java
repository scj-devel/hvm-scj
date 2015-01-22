package icecaptools.compiler.aot;

import org.apache.bcel.classfile.Method;

import icecaptools.compiler.NoDuplicatesMemorySegment;

public class LocalVariableManager {

    protected static class LocalVariable {
        public int size;
        public String modifier;
        public boolean used;
        
        public LocalVariable()
        {
            modifier = "";
        }
    }

    protected LocalVariable[] locals;
    
    public LocalVariableManager(int maxLocals) {
        locals = new LocalVariable[maxLocals];
    }

    public void copyLocals(LocalVariableManager right) {
        for (int i = 0; i < right.locals.length; i++) {
            if (right.locals[i] != null) {
                locals[i] = new LocalVariable();
                locals[i].size = right.locals[i].size;
                locals[i].used = right.locals[i].used;
            }
        }
        copy(right);
    }
    
    protected void copy(LocalVariableManager right) {
    }

    public int getMaxLocals() {
        return locals.length;
    }

    public void merge(LocalVariableManager other, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) throws Exception {
        
    }

    public void load(int size, int index, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) {
        
    }

    public String getLocal(int index, int pc) throws Exception {
        return "fp[" + index + "]";
    }

    public void setLocal(int size, int index, int pc, NoDuplicatesMemorySegment localVariables) throws Exception {
    
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    public void flush(StringBuffer output, boolean doIt) {
    }
    
    public void init(StringBuffer output, NoDuplicatesMemorySegment localVariables, int level, Method javaMethod)
    {
        
    }

    public String generateLocalInitialization(NoDuplicatesMemorySegment localVariables) {
        return generateLocalInitialization(localVariables, 0, locals.length, ";\n", false);
    }
    
    public String generateLocalInitialization(NoDuplicatesMemorySegment localVariables, int from) {
        return generateLocalInitialization(localVariables, from, locals.length, ";\n", false);
    }
    
    public String generateLocalInitialization(NoDuplicatesMemorySegment localVariables, int from, int to, String seperator, boolean forceLoad) {
        return "";
    }

    public void clear() {
    }
}
