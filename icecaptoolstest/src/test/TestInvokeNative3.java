package test;

import icecaptools.IcecapCompileMe;
import icecaptools.IcecapExistingNative;
import vm.VMTest;

public class TestInvokeNative3 {

	private static int x;

	@IcecapExistingNative(signature = "turn_on_light")
	private static native int turn_on_light();

	public static void main(String[] args) {
		if (test1() && test2()) {
			VMTest.markResult(false);
		}
	}

	@IcecapCompileMe
	private static boolean test1() {
		if (x == 0) {
			x = turn_on_light();
			if (x == 42) {
				return true;
			}
		}
		return false;
	}

	private static boolean test2() {
		x = turn_on_light();
		if (x == 42) {
			return true;
		}
		return false;
	}
}
