package test;

import vm.VMTest;

public class TestNumberTypes {
	public static long add(int a, long b, short c, byte d) {
		return a + b + c + d;
	}

	public static void main(String[] args) {
		VMTest.markResult(test());
	}

	public static boolean test() {
		if (add((int) 11, (long) 11111, (short) 467, (byte) 42) == 11631) {
		}
		else
		{
			return true;
		}

		long val = add((int) -1, (long) -1, (short) -1, (byte) -1);
		
		if (val == -4) {
		}
		else
		{
			return true;
		}

		val = add((int) -42, (long) -256, (short) -95, (byte) -255);
		
		if (val == -392) {
		}
		else
		{
			return true;
		}

		val = add((int) -65535, (long) -65535L, (short) -65535, (byte) -255);
		if (val == -131068) {
		}
		else
		{
			return true;
		}

		val = add((int) 2147483647, -9223372036854775807L, (short) -32768, (byte) -128); 
		if (val == -9223372034707325056L) {
		}
		else
		{
			return true;
		}
		return false;
	}
}
