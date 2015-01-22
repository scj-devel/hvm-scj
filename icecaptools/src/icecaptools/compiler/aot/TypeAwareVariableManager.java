package icecaptools.compiler.aot;

import icecaptools.AnnotationsAttribute;
import icecaptools.IcecapVolatile;
import icecaptools.compiler.NoDuplicatesMemorySegment;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

public class TypeAwareVariableManager extends PreloadingVariableManager {

    private Set<String> volatiles;
    private String[] lvNames;

    public TypeAwareVariableManager(int maxLocals, StringBuffer output, NoDuplicatesMemorySegment localVariables) {
        super(maxLocals, output, localVariables);
        volatiles = new HashSet<String>();
    }

    @Override
    public void init(StringBuffer output, NoDuplicatesMemorySegment localVariables, int level, Method javaMethod) {
        LocalVariableTable localVariableTable = javaMethod.getLocalVariableTable();

        handleVolatileLocals(javaMethod);

        if (localVariableTable != null) {
            org.apache.bcel.classfile.LocalVariable[] vars = localVariableTable.getLocalVariableTable();

            for (org.apache.bcel.classfile.LocalVariable localVariable : vars) {
                insert(localVariable, localVariables);

                String sig = Utility.signatureToString(localVariable.getSignature());
                if (("double".equals(sig)) || ("long".equals(sig))) {
                    org.apache.bcel.classfile.LocalVariable twoIndex = new org.apache.bcel.classfile.LocalVariable(localVariable.getStartPC(), localVariable.getLength(), localVariable.getNameIndex(), localVariable.getSignatureIndex(), localVariable.getIndex() + 1,
                            localVariable.getConstantPool());
                    insert(twoIndex, localVariables);
                }
            }
        }

        populateLocals(output, localVariables);

        initNameTable(javaMethod);
        
        this.level = level;
    }

    @Override
    protected String getPreferredName(int index) {
        return lvNames[index];
    }

    private void initNameTable(Method javaMethod) {
        lvNames = new String[locals.length];
        
        for (int i = 0; i < lvNames.length; i++) {
            lvNames[i] = super.getPreferredName(i);
        }
        
        if (javaMethod != null)
        {
            LocalVariableTable lvTable = javaMethod.getLocalVariableTable();
            if (lvTable != null)
            {
                HashSet<String> names = new HashSet<String>();
                org.apache.bcel.classfile.LocalVariable[] locals = lvTable.getLocalVariableTable();
                for (org.apache.bcel.classfile.LocalVariable localVariable : locals) {
                    int index = localVariable.getIndex();
                    String preferredName = localVariable.getName();
                    preferredName = preferredName.replace("$", "_");
                    if (names.contains(preferredName))
                    {
                        preferredName = preferredName + "_" + index;
                    }
                    lvNames[index] = preferredName;
                    names.add(preferredName);
                }
            }
        }
            
    }

    private void handleVolatileLocals(Method javaMethod) {
        Attribute[] attributes = javaMethod.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute instanceof AnnotationsAttribute) {
                AnnotationsAttribute icecapAttribute = (AnnotationsAttribute) attribute;
                IcecapVolatile volatiles = icecapAttribute.getAnnotation(IcecapVolatile.class);
                if (volatiles != null) {
                    String volatileList = volatiles.value();
                    StringTokenizer tokenizer = new StringTokenizer(volatileList, " ,:;.");
                    while (tokenizer.hasMoreTokens()) {
                        this.volatiles.add(tokenizer.nextToken());
                    }
                }
            }
        }
    }

    private void populateLocals(StringBuffer output, NoDuplicatesMemorySegment localVariables) {
        for (int index = 0; index < locals.length; index++) {
            if (locals[index] == null) {
                locals[index] = new LocalVariable();
                locals[index].size = Size.INT;
            }
        }
    }

    private void insert(org.apache.bcel.classfile.LocalVariable localVariable, NoDuplicatesMemorySegment localVariables) {
        int size = AOTCompiler.getVariableSize(localVariable);
        int index = localVariable.getIndex();

        if (locals[index] == null) {
            locals[index] = new LocalVariable();
            locals[index].size = size;
        } else if (locals[index].size < size) {
            locals[index].size = size;
        }

        if (volatiles.contains(localVariable.getName())) {
            locals[index].modifier = "volatile";
        }
    }

    @Override
    public void merge(LocalVariableManager other, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) throws Exception {
    }

    @Override
    public void load(int size, int index, int pc, StringBuffer output, NoDuplicatesMemorySegment localVariables) {

    }

    @Override
    public void flush(StringBuffer output, boolean doit) {
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    protected void copy(LocalVariableManager right) {
        locals = right.locals;
        level = ((TypeAwareVariableManager) right).level;
        lvNames = ((TypeAwareVariableManager) right).lvNames;
    }

    @Override
    public String getLocal(int index, int pc) throws Exception {
        return super.getLocal(index, pc);
    }

    @Override
    public void setLocal(int size, int index, int pc, NoDuplicatesMemorySegment localVariables) throws Exception {
    }
}
