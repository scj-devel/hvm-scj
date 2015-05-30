package vm;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;

public class VMTest {

	@IcecapCVar
	static byte exitValue;

	@IcecapCompileMe
	public static void markResult(boolean failed) {
		if (failed) {
			exitValue = 1;
		} else {
			exitValue = 0;
		}
	}
}
