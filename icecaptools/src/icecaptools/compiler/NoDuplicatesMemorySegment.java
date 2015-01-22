package icecaptools.compiler;

import icecaptools.HVMProperties;

import java.util.ArrayList;

public class NoDuplicatesMemorySegment extends MemorySegment {

    private ArrayList<String> entries;
    
    public NoDuplicatesMemorySegment(HVMProperties props) {
        super(props);
        entries = new ArrayList<String>();
    }

    @Override
    public void print(String entry) {
        if (!entries.contains(entry))
        {
            super.print(entry);
            entries.add(entry);
        }
    }
}
