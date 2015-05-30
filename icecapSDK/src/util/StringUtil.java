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

	public static byte toString(int x, byte[] buffer, int from)
	{
		byte i = 0;
		while (x > 9)
		{
			byte digit = (byte) (x % 10);
			buffer[from + i] = (byte) ('0' + digit);
			i++;
			x = x / 10;
		}
		
		buffer[from + i] = (byte) ('0' + x);
		i++;
		
		for (byte j = 0; j < (i >> 1); j++)
		{
			byte temp = buffer[from + j];
			buffer[from + j] = buffer[from + i - j - 1];
			buffer[from + i - j - 1] = temp;
		}
		return i;
	}
	
	public static byte[] getBytes(String str) {
		return getBytes(str, false);
	}

	public static int parseInt(byte[] bytes) {
		return parseInt(bytes, false);
	}

	public static byte stringSize(int x) {
		byte i = 0;
		while (x > 9)
		{
			i++;
			x = x / 10;
		}
		
		return ++i;
	}

	public static String constructString(String defaultName, int nameCount) {
		return constructStringBuffer(defaultName, nameCount).toString();
	}
	
	public static StringBuffer constructStringBuffer(String message, int number) {
		byte nameCountSize = stringSize(number);
		byte defaultNameSize = (byte) message.length();
		StringBuffer strBuf = new StringBuffer(defaultNameSize + nameCountSize);
	
		byte index = 0;
		while (index < defaultNameSize)
		{
			strBuf.append(message.charAt(index));
			index++;
		}
		byte[] nameCountBuffer = new byte[nameCountSize];
		toString(number, nameCountBuffer, 0);
		index = 0;
		while (index < nameCountSize)
		{
			strBuf.append((char)nameCountBuffer[index]);
			index++;
		}
		return strBuf;
	}	
}
