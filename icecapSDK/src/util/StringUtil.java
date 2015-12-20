package util;

public class StringUtil {

	public static int parseInt(byte[] str, boolean discardNullTerminator) throws NumberFormatException {
		if (str.length > 0) {
			int value = 0;
			int idx;
			int factor = 1;

			idx = str.length - 1 - (discardNullTerminator ? 1 : 0);

			while (idx >= 0) {
				byte digit = str[idx];

				if ((digit >= '0') && (digit <= '9')) {
					int x = (digit - '0') * factor;
					value += x;
					factor *= 10;
				} else {
					throw new NumberFormatException();
				}
				idx--;
			}
			return value;
		} else {
			throw new NumberFormatException();
		}
	}

	public static byte[] getBytes(String str, boolean addNullTerminator) {
		byte[] value = new byte[str.length() + (addNullTerminator ? 1 : 0)];
		int cnt = 0;
		while (cnt < str.length()) {
			value[cnt] = (byte) str.charAt(cnt);
			cnt++;
		}
		if (addNullTerminator) {
			value[cnt] = 0;
		}
		return value;
	}

	public static byte[] getBytes(String str) {
		return getBytes(str, false);
	}

	public static int parseInt(byte[] bytes) {
		return parseInt(bytes, false);
	}
}
