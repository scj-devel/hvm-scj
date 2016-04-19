package reflect;

import icecaptools.IcecapCVar;
import icecaptools.IcecapInlineNative;

public class StaticRefInfo64 {
	@IcecapCVar(expression = "staticReferenceOffsets", requiredIncludes = "extern const uint32* staticReferenceOffsets;\n")
	static long staticReferenceOffsets;

	@IcecapInlineNative(functionBody = ""
			+ "{\n"
			+ "   unsigned char* sdata;\n"
			+ "   Object* heapRef = (Object*)(pointer)HEAP_REF(classData, unsigned char*);\n"
			+ "   sdata = (unsigned char*) &heapRef;\n"
			+ "   sp[0] = *(int32*) &sdata[4];\n"
			+ "   sp[1] = *(int32*) sdata;\n"
			+ "   return -1;\n"
			+ "}\n", 
			requiredIncludes = "extern unsigned char* classData;\n")
	public static native long getClassData();
}
