package icecaptools.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class RequiredEntryManager {
    protected ArrayList<String> requiredEntries;

    private HashMap<String, Integer> createdEntries;

	private boolean supportLoading;

    public void registerEntry(String name, int number) {
        createdEntries.put(name, new Integer(number));
    }

    protected RequiredEntryManager(boolean supportLoading) {
        requiredEntries = new ArrayList<String>();
        createdEntries = new HashMap<String, Integer>();
        this.supportLoading = supportLoading;
    }

    protected StringBuffer getDeclarations(boolean includeRegistered) {
        StringBuffer buffer = new StringBuffer();

        Iterator<String> iterator = requiredEntries.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String itemName = getItemName(next);
            int val;

            val = getValue(next); 
            
            if ((val == -1) || includeRegistered) {
                buffer.append("#define " + itemName + " " + val + "\n");
            }
        }
        return buffer;
    }

    private int getValue(String next) {
        int val;
        if (createdEntries.containsKey(next)) {
            val = createdEntries.get(next).intValue();
        } else {
            val = -1;
        }
        return val;
    }

    protected String getItemName(String next) {
        return next.toUpperCase().replace(".", "_").replace("[", "_");
    }

    public StringBuffer getDeclarations() {
        return getDeclarations(true);
    }

    public StringBuffer getUnregisteredDeclarations() {
        return getDeclarations(false);
    }
    
    public void getVariableDeclarations(StringBuffer declarations, StringBuffer initializations)
    {
        Iterator<String> iterator = requiredEntries.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String itemName = getItemName(next);
            if (supportLoading)
            {
                declarations.append("extern uint16 " + itemName + "_var;\n");
                initializations.append("RANGE uint16 " + itemName + "_var = " + itemName + ";\n");            	
            }
            else
            {
                declarations.append("#define " + itemName + "_var " + itemName + "\n");
                // initializations.append("RANGE uint16 " + itemName + "_var = " + itemName + ";\n");            	
            }
        }
    }
}
