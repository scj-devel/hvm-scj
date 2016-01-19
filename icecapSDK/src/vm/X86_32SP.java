package vm;

public class X86_32SP extends SP {
	public int csp;
	public int jsp;

	@Override
	public int getCSP() {
		return csp;
	}

	@Override
	public int getJSP() {
		return jsp;
	}
}
