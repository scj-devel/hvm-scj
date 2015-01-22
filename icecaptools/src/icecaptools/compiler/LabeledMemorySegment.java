package icecaptools.compiler;

import icecaptools.HVMProperties;

import java.util.HashSet;
import java.util.StringTokenizer;

public class LabeledMemorySegment extends NoDuplicatesMemorySegment {

    private StringBuffer defines;

    private HashSet<String> included;

    public LabeledMemorySegment(HVMProperties props) {
        super(props);
        defines = new StringBuffer();
        included = new HashSet<String>();
    }

    @Override
    public void print(String entry) {
        super.print(entry);

        if (entry.contains("(")) {
            if (!entry.endsWith("(int32* fp);\n")) {
                StringTokenizer tokenizer = new StringTokenizer(entry, "(");
                String token = tokenizer.nextToken();
                tokenizer = new StringTokenizer(token);

                while (tokenizer.hasMoreElements()) {
                    token = tokenizer.nextToken();
                }
                if (!included.contains(token)) {
                    defines.append("#define ");
                    defines.append(token.toUpperCase());
                    defines.append("_USED\n");
                    included.add(token);
                }
            }
        }
    }

    public StringBuffer getDefines() {
        return defines;
    }
}
