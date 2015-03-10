package icecaptools.compiler;

import icecaptools.MethodOrFieldDesc;

import java.util.ArrayList;

public class RequiredMethodsManager extends RequiredEntryManager {

    private ArrayList<String> entries;
    
    public RequiredMethodsManager(IDGenerator idGen, boolean supportLoading) {
    	super(supportLoading);
        entries = new ArrayList<String>();
        addRequiredMethod(idGen, "java.lang.String", "<init>", "([C)V", "java_lang_String_initFromCharArray");
        addRequiredMethod(idGen, "java.lang.ClassCastException", "<init>", "()V", "java_lang_ClassCastException_init_");
        addRequiredMethod(idGen, "java.lang.ArithmeticException", "<init>", "()V", "java_lang_ArithmeticException_init_");
        addRequiredMethod(idGen, "java.lang.OutOfMemoryError", "<init>", "()V", "java_lang_OutOfMemoryError_init_");
        addRequiredMethod(idGen, "java.lang.NullPointerException", "<init>", "()V", "java_lang_NullPointerException_init_");
        addRequiredMethod(idGen, "java.lang.Object", "clone", "()Ljava/lang/Object;", null);
        addRequiredMethod(idGen, "vm.InterruptDispatcher", "interrupt", "(B)V", null);
        addRequiredMethod(idGen, "tasks.ModbusDispatcher", "dispatch", "(BB)Z", null);
        addRequiredMethod(idGen, "java.lang.StringBuffer", "append", "(F)Ljava/lang/StringBuffer;", "java_lang_StringBuffer_append");        
        addRequiredMethod(idGen, "java.lang.StringBuilder", "append", "(F)Ljava/lang/StringBuilder;", "java_lang_StringBuilder_append");        
        addRequiredMethod(idGen, "java.lang.String", "<init>", "([BII)V", "java_lang_String_init_"); 
        addRequiredMethod(idGen, "java.lang.Float", "toString", "()Ljava/lang/String;", "java_lang_Float_toString");
        addRequiredMethod(idGen, "java.lang.Thread", "<init>", "()V", "java_lang_Thread_init_default");
    }

    private void addRequiredMethod(IDGenerator idGen, String clazz, String name, String sig, String preferred) {
        if (preferred != null) {
            requiredEntries.add(idGen.getUniqueId(clazz, name, sig, preferred));
        } else {
            requiredEntries.add(idGen.getUniqueId(clazz, name, sig));
        }
        entries.add(clazz + name + sig);
    }

    public boolean isRequieredMethod(MethodOrFieldDesc mdesc) {
        return entries.contains(mdesc.getClassName() + mdesc.getName() + mdesc.getSignature());
    }
}
