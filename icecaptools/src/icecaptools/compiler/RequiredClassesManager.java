package icecaptools.compiler;

import icecaptools.FieldOffsetCalculator;

public class RequiredClassesManager extends RequiredEntryManager {

    public RequiredClassesManager(boolean supportLoading) {
    	super(supportLoading);
        requiredEntries.add("java.lang.String");
        requiredEntries.add("java.lang.Object");
        requiredEntries.add("java.lang.Integer");
        requiredEntries.add("java.lang.Byte");        
        requiredEntries.add("java.lang.Short");
        requiredEntries.add("java.lang.Character");
        requiredEntries.add("java.lang.Boolean");        
        requiredEntries.add("java.lang.Long");        
        requiredEntries.add("java.lang.Class");
        requiredEntries.add("java.lang.ClassCastException");
        requiredEntries.add("java.lang.OutOfMemoryError");
        requiredEntries.add("java.lang.Throwable");
        requiredEntries.add("java.lang.ArithmeticException");
        requiredEntries.add("java.lang.NoSuchMethodException");
        requiredEntries.add("java.lang.reflect.Method");
        requiredEntries.add("java.lang.reflect.InvocationTargetException");
        requiredEntries.add("java.lang.reflect.Constructor");
        requiredEntries.add(FieldOffsetCalculator.HWObjectClassName);
        requiredEntries.add(FieldOffsetCalculator.ReferenceAddressClassName);
        requiredEntries.add(FieldOffsetCalculator.Address32bitClassName);
        requiredEntries.add(FieldOffsetCalculator.Address64bitClassName);
        requiredEntries.add("java.lang.NullPointerException");
        requiredEntries.add("[C");
        requiredEntries.add("[I");
        requiredEntries.add("vm.Memory");
    }
}
