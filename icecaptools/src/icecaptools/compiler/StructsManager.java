package icecaptools.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.bcel.classfile.JavaClass;

import icecaptools.AnalysisObserver;
import icecaptools.FieldOffsetCalculator;
import icecaptools.JavaArrayClass;
import icecaptools.NativeFieldInfo;

public class StructsManager {

    private static final String STATICCLASSFIELDS = "staticClassFields";
    private FieldOffsetCalculator foCalc;
    private StringBuffer objectFieldStructs;
    private StringBuffer classFieldStructs;
    private IDGenerator idGen;
    private HashMap<String, String> classToStructMap;

    public StructsManager(FieldOffsetCalculator foCalc, IDGenerator idGen) {
        this.foCalc = foCalc;
        this.idGen = idGen;
        this.classToStructMap = new HashMap<String, String>();
    }

    public StringBuffer generateObjectFieldStructs() throws Exception {
        objectFieldStructs = new StringBuffer();

        HashMap<JavaClass, ArrayList<FieldInfo>> classFieldsMap = foCalc.getObjectFields();

        Iterator<JavaClass> classes = classFieldsMap.keySet().iterator();

        while (classes.hasNext()) {
            JavaClass clazz = classes.next();
            if (!foCalc.isHardwareObject(clazz.getClassName())) {
                if (!(clazz instanceof JavaArrayClass)) {
                    ArrayList<FieldInfo> fields = classFieldsMap.get(clazz);
                    Iterator<FieldInfo> fieldIterator = fields.iterator();
                    if (fieldIterator.hasNext()) {
                        HashSet<String> fieldsInStruct = new HashSet<String>();
                        String structName = startStruct(clazz.getClassName(), objectFieldStructs, true);
                        while (fieldIterator.hasNext()) {
                            FieldInfo field = fieldIterator.next();
                            addField(structName, field, fieldsInStruct, objectFieldStructs);
                        }
                        endStruct(clazz.getClassName(), objectFieldStructs);
                        objectFieldStructs.append("\n");
                    }
                }
            }
        }
        return objectFieldStructs;
    }

    

    private String startStruct(String className, StringBuffer buffer, boolean addHeader) {
        String structName = "_" + idGen.getUniqueId(className, "c", "s");
        classToStructMap.put(className, structName);
        buffer.append("typedef struct PACKED ");
        buffer.append(structName);
        buffer.append(" {\n");
        if (addHeader)
        {
            buffer.append("    Object header;\n");
        }
        return structName;
    }

    private void endStruct(String className, StringBuffer buffer) {
        buffer.append("} ");
        buffer.append(idGen.getUniqueId(className, "c", "s"));
        buffer.append(";\n");
    }

    private void addField(String structName, FieldInfo field, HashSet<String> fieldsInStruct, StringBuffer buffer) throws Exception {
        int fieldSize = field.getSize() & 0xfc;
        String structMemberName = idGen.getUniqueId(field.getName(), "f", "s");
        field.setStructMemberName(structMemberName);
        while (fieldsInStruct.contains(structMemberName)) {
            structMemberName = idGen.getUniqueId(structMemberName + "_", "f", "s");
            field.setStructMemberName(structMemberName);
        }
        fieldsInStruct.add(structMemberName);
        switch (fieldSize) {
        case 8:
            buffer.append("    uint8 ");
            break;
        case 16:
            buffer.append("    uint16 ");
            break;
        case 32:
            if (field.isFloat) {
                buffer.append("    float ");
            } else {
                buffer.append("    uint32 ");
            }
            break;
        case 64:
            buffer.append("    uint32 ");
            buffer.append(structMemberName);
            buffer.append(";\n");
            structMemberName = "lsb" + structMemberName;
            buffer.append("    uint32 ");
            field.setStructMemberLSBName(structMemberName);
            break;
        default:
            throw new Exception("Unknown field size: " + fieldSize);

        }
        buffer.append(structMemberName);
        buffer.append(";\n");
        field.setStructName(structName);
    }

    public StringBuffer generateClassFieldStructs(AnalysisObserver observer) throws Exception {
        classFieldStructs = new StringBuffer();
        HashMap<JavaClass, ArrayList<FieldInfo>> classFieldsMap = foCalc.getClassFields();

        Iterator<JavaClass> classes = classFieldsMap.keySet().iterator();
        HashSet<String> fieldsInStruct = new HashSet<String>();
        String structName = startStruct(STATICCLASSFIELDS, classFieldStructs, false);
        
        while (classes.hasNext()) {
            JavaClass clazz = classes.next();
            ArrayList<FieldInfo> staticFields = classFieldsMap.get(clazz);
            Iterator<FieldInfo> staticFieldsIterator = staticFields.iterator();
            while (staticFieldsIterator.hasNext())
            {
                FieldInfo nextField = staticFieldsIterator.next();
                
                NativeFieldInfo nfi = observer.isNativeField(clazz.getClassName(), nextField);
                if (nfi == null)
                {
                    addField(structName, nextField, fieldsInStruct, classFieldStructs);
                }
            }
        }

        classFieldStructs.append("    uint8 _dummy_;\n");
        endStruct(STATICCLASSFIELDS, classFieldStructs);
        
        return classFieldStructs;
    }

    public String getStructName(String className) {
        return classToStructMap.get(className);
    }
}
