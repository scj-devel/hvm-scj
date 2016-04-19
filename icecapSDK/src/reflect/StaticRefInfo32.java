package reflect;

import icecaptools.IcecapCVar;
import icecaptools.IcecapInlineNative;

public class StaticRefInfo32 {
	@IcecapCVar(expression = "(pointer)staticReferenceOffsets", requiredIncludes = "extern const uint32* staticReferenceOffsets;\n")
	static int staticReferenceOffsets;
	
	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   unsigned char* sdata;\n"
			+ "   Object* heapRef = (Object*)(pointer)HEAP_REF(classData, unsigned char*);\n"
			+ "   sdata = (unsigned char*) &heapRef;\n"
			+ "   sp[0] = *(int32*) sdata;\n"
			+ "   sp[1] = 0;\n"
			+ "   return -1;\n"
			+ "}\n", 
			requiredIncludes = "extern unsigned char* classData;\n")
	public static native long getClassData();
}
