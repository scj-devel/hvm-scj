package vm;

import icecaptools.IcecapCVar;

public class Architecture {
	public static final byte X86_64 = 1;
	public static final byte X86_32 = 2;
	public static final byte CR16_C = 3;
	public static final byte ATMEGA2560 = 4;

	@IcecapCVar
	public static byte architecture;
}
