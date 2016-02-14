package icecaptools.compiler;

import java.util.HashMap;

public class CodeManager {

	private int codeCacheSize;

	private HashMap<String, Integer> segmentOffsets;

	private StringBuffer code;

	CodeManager() {
		segmentOffsets = new HashMap<>();

		code = new StringBuffer();
		code.append("0xFF,"); /* to avoid code offset 0, which we use to mark 'no code' */
		codeCacheSize = 1;  
	}

	public void addMethod(String uniqueMethodId, int length, String codeString) {
		segmentOffsets.put(uniqueMethodId, this.codeCacheSize);
		codeCacheSize += length;
		code.append("\n/* ");
		code.append(uniqueMethodId);
		code.append("*/\n");		
		code.append(codeString);
		code.append(",");
	}

	public String getCodeSegmentDeclaration() {
		StringBuffer decl = new StringBuffer();

		decl.append("RANGE const unsigned char _codecache[" + (codeCacheSize + 1) + "] PROGMEM = {\n  ");
		decl.append(code);
		decl.append("\n};");

		return decl.toString();
	}

	public int getOffset(String uniqueMethodId) {
		int offset = segmentOffsets.get(uniqueMethodId);
		return offset;
	}
}
