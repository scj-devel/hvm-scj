package test;

import util.StringUtil;
import vm.VMTest;

public class TestStringUtil {

	public static void main(String[] args) {
		boolean failed = test();
		VMTest.markResult(failed);
	}

	private static boolean test() {
		byte[] buffer = new byte[StringUtil.stringSize(1234)];

		int x = StringUtil.toString(1234, buffer, 0);

		if (x != 4) {
			System.out.println("fail 1");
			return true;
		}

		String str = new String(buffer);
		if (!str.equals("1234")) {
			System.out.println("fail 2");
			return true;
		}

		buffer = new byte[StringUtil.stringSize(0)];
		x = StringUtil.toString(0, buffer, 0);

		if (x != 1) {
			System.out.println("fail 3");
			return true;
		}

		str = new String(buffer);
		if (!str.equals("0")) {
			System.out.println("fail 4");
			return true;
		}

		buffer = new byte[StringUtil.stringSize(4)];
		x = StringUtil.toString(4, buffer, 0);

		if (x != 1) {
			System.out.println("fail 3");
			return true;
		}

		str = new String(buffer);
		if (!str.equals("4")) {
			System.out.println("fail 4");
			return true;
		}

		buffer = new byte[StringUtil.stringSize(9)];
		x = StringUtil.toString(9, buffer, 0);

		if (x != 1) {
			System.out.println("fail 3");
			return true;
		}

		str = new String(buffer);
		if (!str.equals("9")) {
			System.out.println("fail 4");
			return true;
		}

		buffer = new byte[StringUtil.stringSize(42)];
		x = StringUtil.toString(42, buffer, 0);

		if (x != 2) {
			System.out.println("fail 3");
			return true;
		}

		str = new String(buffer);
		if (!str.equals("42")) {
			System.out.println("fail 4");
			return true;
		}

		buffer = new byte[StringUtil.stringSize(421)];
		x = StringUtil.toString(421, buffer, 0);

		if (x != 3) {
			System.out.println("fail 3");
			return true;
		}

		str = new String(buffer);
		if (!str.equals("421")) {
			System.out.println("fail 4");
			return true;
		}

		String result = StringUtil.constructString("temp", 0);
		if (!result.equals("temp0")) {
			return true;
		}

		result = StringUtil.constructString("temp", 1);
		if (!result.equals("temp1")) {
			return true;
		}

		result = StringUtil.constructString("temp", 42);
		if (!result.equals("temp42")) {
			return true;
		}

		result = StringUtil.constructString("temp", 1234);
		if (!result.equals("temp1234")) {
			return true;
		}
		return false;
	}
}
