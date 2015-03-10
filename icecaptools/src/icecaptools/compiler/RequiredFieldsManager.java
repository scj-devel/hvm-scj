package icecaptools.compiler;

import java.util.HashMap;
import java.util.Iterator;

public class RequiredFieldsManager extends RequiredEntryManager {

    private HashMap<String, FieldInfo> fields;

    public RequiredFieldsManager(boolean supportLoading) {
    	super(supportLoading);
        requiredEntries.add("java.lang.String.value");
        requiredEntries.add("java.lang.String.offset");
        requiredEntries.add("java.lang.String.count");
        fields = new HashMap<String, FieldInfo>();
    }

    public void registerEntry(String name, int fieldNumber, FieldInfo currentField) {
        super.registerEntry(name, fieldNumber);
        fields.put(name, currentField);
    }

    protected String getItemName(String next) {
        return next.toUpperCase().replace(".", "_").replace("[", "_") + "_offset";
    }
    
    protected StringBuffer getDeclarations(boolean includeRegistered) {
        StringBuffer buffer = new StringBuffer();

        Iterator<String> iterator = requiredEntries.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            FieldInfo val;

            if (fields.containsKey(next)) {
                val = fields.get(next);
            } else {
                val = null;
            }
            if ((val == null) || includeRegistered) {
                if (val != null) {
                    buffer.append("#define " + next.toUpperCase().replace(".", "_").replace("[", "_") + "_offset " + val.getOffset() + "\n");
                } else {
                }
            }
        }
        //buffer.append(super.getDeclarations(includeRegistered));
        return buffer;
    }
}
