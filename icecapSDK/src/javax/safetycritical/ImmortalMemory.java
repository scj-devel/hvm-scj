package javax.safetycritical;

import javax.realtime.MemoryArea;

public class ImmortalMemory extends ManagedMemory {  // HSO: not public
	
	ImmortalMemory(int sizeOfArea) {
		super(sizeOfArea, sizeOfArea, MemoryArea.overAllBackingStore, "Imm");
	}

	/*public*/ static ImmortalMemory instance() {
		MemoryArea result = MemoryArea.getNamedMemoryArea("Imm");
		if (result != null) {
			return (ImmortalMemory) result;
		}
		return null;
	}

}
