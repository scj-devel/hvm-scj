package icecaptools.compiler;

public class MethodsFile {

	private MemorySegment header;
	private StringBuffer implementation;
	private boolean supportLoader;

	public MethodsFile(boolean supportLoader) {
		this.supportLoader = supportLoader;
	}

	public void setHeader(MemorySegment header) {
		this.header = header;
	}

	public void generateNumberOfClassInitializersDecl() {
		if (supportLoader) {
			header.print("extern uint16 NUMBEROFCLASSINITIALIZERS_var;\n");
		} else {
			header.print("#define NUMBEROFCLASSINITIALIZERS_var NUMBEROFCLASSINITIALIZERS\n");
		}
	}

	public void setImplementation(StringBuffer result) {
		this.implementation = result;
	}

	public void generateNumberOfClassInitializersImpl() {
		if (supportLoader) {
			implementation.append("uint16 NUMBEROFCLASSINITIALIZERS_var = NUMBEROFCLASSINITIALIZERS;\n");
		} else {
		}

	}

	public void generateNumberOfConstantsDecl() {
		if (supportLoader) {
			header.print("extern uint16 NUMBEROFCONSTANTS_var;\n");
		} else {
			header.print("#define NUMBEROFCONSTANTS_var NUMBEROFCONSTANTS\n");
		}
	}

	public void generateNumberOfConstantsImpl() {
		if (supportLoader) {
			implementation.append("uint16 NUMBEROFCONSTANTS_var = NUMBEROFCONSTANTS;\n");
		} else {
		}
	}

	public void generateMainMethodIndexDecl() {
		if (supportLoader) {
			header.print("extern uint16 mainMethodIndex;\n");
		} else {
			header.print("#define mainMethodIndex MAINMETHODINDEX\n");
		}
	}

	public void generateMainMethodIndexImpl() {
		if (supportLoader) {
			implementation.append("uint16 mainMethodIndex = MAINMETHODINDEX;\n");
		} else {
		}		
	}
}
