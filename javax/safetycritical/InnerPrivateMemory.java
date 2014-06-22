package javax.safetycritical;

class InnerPrivateMemory extends ManagedMemory {

	ManagedMemory prev;
	
	public InnerPrivateMemory(int size) {
		super(size);
	}

}
