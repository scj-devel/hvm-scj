package reflect;

import icecaptools.IcecapCVar;

public class StaticRefInfo32 {
	@IcecapCVar(expression = "(pointer)staticReferenceOffsets", requiredIncludes = "extern const uint32* staticReferenceOffsets;\n")
	static int staticReferenceOffsets;
	
	@IcecapCVar(expression = "(pointer)classData", requiredIncludes = "extern unsigned char* classData;\n")
	static int classData;
}
