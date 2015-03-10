package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.NativeFieldInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class ClassFieldsManager {

    public static final String classDataVariable = "_classData";

    public static final String staticReferenceOffsetsVariable = "staticReferenceOffsets";

    public static final String NUMBEROFCLASSES_varVariable = "NUMBEROFCLASSES_var";
    
    private ArrayList<FieldInfo> referenceFields;

    private int classDataLength;

    private boolean staticReferenceOffsetsVariableUsed;
    public boolean NUMBEROFCLASSES_varUsed;
    
    ClassFieldsManager() {
        classDataLength = 0;
        referenceFields = new ArrayList<FieldInfo>();
        staticReferenceOffsetsVariableUsed = false;
        NUMBEROFCLASSES_varUsed = false;
    }

    public void addClassFields(String currentClassName, ArrayList<FieldInfo> fieldSet, AnalysisObserver observer) throws ClassNotFoundException {
        if (fieldSet.size() > 0) {
            Iterator<FieldInfo> itField = fieldSet.iterator();
            while (itField.hasNext()) {
                FieldInfo currentField = itField.next();
                if (staticReferenceOffsetsVariable.equals(currentField.getName())) {
                    staticReferenceOffsetsVariableUsed = true;
                }
                
                if (NUMBEROFCLASSES_varVariable.equals(currentField.getName()))
                {
                    NUMBEROFCLASSES_varUsed = true;
                }
                NativeFieldInfo nfi = observer.isNativeField(currentClassName, currentField);
                if (nfi == null) {
                    int fieldSize = currentField.getSize() & 0xfc;
                    int currentEnd = currentField.getOffset() + fieldSize;
                    if (currentEnd > classDataLength) {
                        classDataLength = currentEnd;
                    }

                    if (currentField.isReference) {
                        referenceFields.add(currentField);
                    }
                }
            }
        }
    }

    public boolean finalizeClassfieldDeclarations(MemorySegment buffer) {
        while (classDataLength % 8 > 0) {
            classDataLength++;
        }

        int numberOfBytes = classDataLength >> 3;

        if (numberOfBytes > 0) {
            buffer.appendData("static unsigned char " + classDataVariable + "[" + numberOfBytes + "] = {", 0);
            while (numberOfBytes > 0) {
                buffer.appendData(" 0", 1);
                numberOfBytes--;
                if (numberOfBytes > 0) {
                    buffer.appendData(",", 0);
                }
            }
            buffer.print("};\n\n");
        }

        if (staticReferenceOffsetsVariableUsed) {
            buffer.appendData("RANGE static const uint32 _" + staticReferenceOffsetsVariable + "[" + (referenceFields.size() + 1) + "] PROGMEM = { ", 0);

            buffer.appendData(referenceFields.size() + "", 0);
            Iterator<FieldInfo> staticReferenceFieldsIterator = referenceFields.iterator();
            while (staticReferenceFieldsIterator.hasNext()) {
                FieldInfo next = staticReferenceFieldsIterator.next();
                StringBuffer offsetof = new StringBuffer();
                offsetof.append("offsetof(struct ").append(next.getStructName()).append(", ");
                offsetof.append(next.getStructMemberName()).append(")");
                buffer.appendData(", ", 0);
                buffer.appendData(offsetof.toString(), 0);
            }
            buffer.appendData("};\n", 0);
        }
        
        return staticReferenceOffsetsVariableUsed;
    }

    public boolean hasClassFields() {
        return classDataLength > 0;
    }

	public int getClassDataSize() {
		return classDataLength >> 3;
	}
}
