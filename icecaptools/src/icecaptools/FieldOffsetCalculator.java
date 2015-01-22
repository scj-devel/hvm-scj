package icecaptools;

import icecaptools.compiler.FieldInfo;
import icecaptools.compiler.OffsetPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.RuntimeInvisibleAnnotations;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

public class FieldOffsetCalculator {
    private HashMap<JavaClass, ArrayList<FieldInfo>> objectFields;
    private HashMap<JavaClass, ArrayList<FieldInfo>> classFields;
    private HashMap<JavaClass, OffsetPair> objectSize;
    private ArrayList<String> additionalDependencies;
    private int offset;
    public static final String HWObjectClassName = "vm.HardwareObject";
    public static final String ReferenceAddressClassName = "reflect.HeapAccessor";
    public static final String Address32bitClassName = "vm.Address32Bit";
    public static final String Address64bitClassName = "vm.Address64Bit";
    private HashSet<String> nonHWObjectClasses;
    private LinkedList<String> maxHeirarchy;
    
    public FieldOffsetCalculator() {
        objectFields = new HashMap<JavaClass, ArrayList<FieldInfo>>();
        classFields = new HashMap<JavaClass, ArrayList<FieldInfo>>();
        objectSize = new HashMap<JavaClass, OffsetPair>();
        additionalDependencies = new ArrayList<String>();
        offset = 0;
        nonHWObjectClasses = new HashSet<String>();
        maxHeirarchy = new LinkedList<String>();
    }

    @SuppressWarnings("unchecked")
    private OffsetPair calculateOffset(JavaClass clazz, HashMap<JavaClass, ArrayList<FieldInfo>> objectFields, HashMap<JavaClass, ArrayList<FieldInfo>> classFields, boolean includeClassFields, AnalysisObserver observer, LinkedList<String> heirarchy) throws Exception {
        OffsetPair currentObjectOffset;
        int currentClassOffset = offset;
        ArrayList<FieldInfo> fieldsInSuperClass = null;
        
        heirarchy.add(clazz.getClassName());
        
        JavaClass superClass = clazz.getSuperClass();
        
        if (superClass != null) {
            currentObjectOffset = calculateOffset(clazz.getSuperClass(), objectFields, classFields, true, observer, heirarchy);
            fieldsInSuperClass = objectFields.get(superClass);
        } else {
            currentObjectOffset = new OffsetPair(0, 0);
            fieldsInSuperClass = new ArrayList<FieldInfo>();
            
            if (maxHeirarchy.size() < heirarchy.size())
            {
                maxHeirarchy = (LinkedList<String>) heirarchy.clone();
            }
        }

        if (observer.isLockingType(clazz.getClassName())) {
            currentObjectOffset.makeRoomForLock();
        }

        String fieldName;
        Type fieldType;
        int fieldSize = 0;
        int fieldOffset = 0;

        ArrayList<FieldInfo> objectFieldSet = new ArrayList<FieldInfo>();
        ArrayList<FieldInfo> classFieldSet = new ArrayList<FieldInfo>();

        objectFieldSet.addAll(fieldsInSuperClass);
        Field[] fields = clazz.getFields();
        boolean isHWObject = isHardwareObject(clazz.getClassName());
        for (int i = 0; i < fields.length; i++) {
            fieldName = fields[i].getName();
            fieldType = fields[i].getType();
            fieldSize = ClassfileUtils.getSizeOfType(fieldType);

            if (!isHWObject) {
                if (fieldSize == 1) {
                    fieldSize = 8;
                }
            }

            if (!fields[i].isStatic()) {
                // Disabled flash objects as it does not work for AOT complation
                // yet
                //
                // if (fields[i].isVolatile()) {
                // fieldOffset = currentObjectOffset.pheapoffset;
                // currentObjectOffset.pheapoffset += fieldSize;
                // } else {

                fieldOffset = currentObjectOffset.dheapoffset;
                currentObjectOffset.dheapoffset += fieldSize;
                // }
                objectFieldSet.add(new FieldInfo(fieldName, fieldOffset, fieldSize, fields[i].isVolatile(), fields[i].getType() instanceof ReferenceType, fieldType == Type.FLOAT, false, isHWObject));
            } else {
                if (includeClassFields) {
                    if (observer.isClassFieldUsed(clazz.getClassName(), fieldName)) {
                        fieldOffset = currentClassOffset;
                        FieldInfo fInfo = new FieldInfo(fieldName, fieldOffset, fieldSize, false, fields[i].getType() instanceof ReferenceType, fieldType == Type.FLOAT, true, false);
                        
                        if (!isNativeField(observer, clazz.getClassName(), fieldOffset, fields[i], fInfo))
                        {
                            currentClassOffset += fieldSize;
                            offset += fieldSize;                            
                        }                        
                        classFieldSet.add(fInfo);
                    }
                }
            }
        }
        objectFields.put(clazz, objectFieldSet);
        classFields.put(clazz, classFieldSet);
        objectSize.put(clazz, new OffsetPair(currentObjectOffset));

        if (!observer.isClassUsed(clazz.getClassName())) {
            additionalDependencies.add(clazz.getClassName());
        }
        return currentObjectOffset;
    }

    public boolean isHardwareObject(String className) {
        if (nonHWObjectClasses.contains(className)) {
            return false;
        } else {
            boolean isHWObject = false;
            do {
                className = ClassfileUtils.getSuperClassName(className);
                if (HWObjectClassName.equals(className)) {
                    isHWObject = true;
                    break;
                }

            } while (!"java.lang.Object".equals(className));
            if (!isHWObject) {
                nonHWObjectClasses.add(className);
            }
            return isHWObject;
        }
    }

    private boolean isNativeField(AnalysisObserver observer, String containingClass, int fieldOffset, Field field, FieldInfo fInfo) {
        boolean isNativeField = false;
        Attribute[] attributes = field.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute instanceof AnnotationsAttribute) {
                AnnotationsAttribute icecapAttribute = (AnnotationsAttribute) attribute;
                IcecapCVar cvar = icecapAttribute.getAnnotation(IcecapCVar.class);
                if (cvar != null) {
                    observer.registerNativeField(containingClass, fInfo, cvar);
                    isNativeField = true;
                }
            }
            if (attribute instanceof RuntimeInvisibleAnnotations) {
                AnnotationsAttribute icecapAttribute = AnnotationsAttribute.getAttribute((RuntimeInvisibleAnnotations)attribute);
                IcecapCVar cvar = icecapAttribute.getAnnotation(IcecapCVar.class);
                if (cvar != null) {
                    observer.registerNativeField(containingClass, fInfo, cvar);
                    isNativeField = true;
                }
            }
        }
        return isNativeField;
    }

    public HashMap<JavaClass, ArrayList<FieldInfo>> getObjectFields() {
        return objectFields;
    }

    public HashMap<JavaClass, ArrayList<FieldInfo>> getClassFields() {
        return classFields;
    }

    public List<FieldInfo> getObjectFields(String className) {
        Iterator<JavaClass> classes = objectFields.keySet().iterator();
        while (classes.hasNext()) {
            JavaClass nextClass = classes.next();
            if (nextClass.getClassName().equals(className)) {
                return objectFields.get(nextClass);
            }
        }
        return null;
    }

    public void calculate(Iterator<String> usedClasses, AnalysisObserver observer) throws Exception {
        while (usedClasses.hasNext()) {
            String className = usedClasses.next();
            JavaClass next = Repository.lookupClass(className);
            calculateOffset(next, objectFields, classFields, true, observer, new LinkedList<String>());
        }

        Iterator<String> additionalClasses = additionalDependencies.iterator();
        while (additionalClasses.hasNext()) {
            observer.classUsed(additionalClasses.next());
        }
    }

    public HashMap<JavaClass, OffsetPair> getObjectSizes() {
        return this.objectSize;
    }

    public LinkedList<String> getMaxClassHeirarchy() {
        return this.maxHeirarchy;
    }
}
