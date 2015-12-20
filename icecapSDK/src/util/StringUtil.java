package util;

public class StringUtil {

	public static int parseInt(String str) throws NumberFormatException {
		if (str.length() > 0) {
			int value = 0;
			int idx;
			int factor = 1;

			idx = str.length() - 1;

			while (idx >= 0) {
				char digit = str.charAt(idx);

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
}
