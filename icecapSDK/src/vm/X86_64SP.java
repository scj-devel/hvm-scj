package vm;

public class X86_64SP extends SP {
	public long csp;
	public long jsp;

	@Override
	public int getCSP() {
		return (int) csp;
	}

	@Override
	public int getJSP() {
		return (int) jsp;
	}
}
