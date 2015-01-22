package icecaptools.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.bcel.classfile.JavaClass;

import util.ReferenceOffsets;
import icecaptools.ClassfileUtils;
import icecaptools.FieldOffsetCalculator;
import icecaptools.JavaArrayClass;

public class ReferencesManager {
    private FieldOffsetCalculator foCalc;
    private HashSet<JavaClass> referenceClasses;

    public ReferencesManager(FieldOffsetCalculator foCalc) {
        this.foCalc = foCalc;
        referenceClasses = new HashSet<JavaClass>();
    }

    public StringBuffer generateReferenceFieldOffsets(String GCSUPPORTTAG) throws Exception {
        StringBuffer buffer = new StringBuffer();

        HashMap<JavaClass, ArrayList<FieldInfo>> classFieldsMap = foCalc.getObjectFields();

        Iterator<JavaClass> classes = classFieldsMap.keySet().iterator();
        buffer.append("#if defined(" + GCSUPPORTTAG + ")\n");
        while (classes.hasNext()) {
            JavaClass next = classes.next();
            if (!(next instanceof JavaArrayClass)) {

                ReferenceOffsets offsets = new ReferenceOffsets();
                ArrayList<FieldInfo> fields = classFieldsMap.get(next);
                for (FieldInfo fieldInfo : fields) {
                    if (fieldInfo.isReference) {
                        offsets.insert((short) (fieldInfo.getOffset() >> 3));
                    }
                }

                if (offsets.size() > 0) {
                    setHasReferences(next);
                    buffer.append("RANGE const static unsigned short ");
                    buffer.append(ClassfileUtils.getClassNameIdentifier(next.getClassName()));
                    buffer.append("_references[");

                    buffer.append(1 + offsets.shortOffsetsSize() + ((offsets.byteOffsetsSize() + 1) / 2));
                    buffer.append("] PROGMEM = {\n");

                    int x = offsets.shortOffsetsSize() << 8;
                    x += offsets.byteOffsetsSize();

                    buffer.append("    0x");
                    buffer.append(Integer.toHexString(x));

                    offsets.startScanShortOffsets();
                    for (int i = 0; i < offsets.shortOffsetsSize(); i++) {
                        buffer.append(",\n");
                        buffer.append("    0x");
                        buffer.append(Integer.toHexString(offsets.getNextShortOffset()));
                    }

                    offsets.startScanByteOffsets();
                    int i = 0;

                    while (i < offsets.byteOffsetsSize()) {
                        x = offsets.getNextByteOffset();
                        i++;
                        if (i < offsets.byteOffsetsSize()) {
                            x = x | (offsets.getNextByteOffset() << 8);
                            i++;
                        }

                        buffer.append(",\n");
                        buffer.append("    0x");
                        buffer.append(Integer.toHexString(x));
                    }
                    buffer.append("\n");
                    buffer.append("};\n");
                }
            }
        }
        buffer.append("#endif\n");

        return buffer;
    }

    private void setHasReferences(JavaClass next) {
        referenceClasses.add(next);
    }

    public boolean hasReferences(JavaClass currentClass) {
        String className = currentClass.getClassName();
        if ("java.lang.Class".equals(className)) {
            return false;
        } else {
            return referenceClasses.contains(currentClass);
        }
    }
}
