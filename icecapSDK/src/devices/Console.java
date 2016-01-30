package devices;

public class Console {

	private static byte[] bytes;

	public static Writer writer;

	public static void println(String string) {
		println(string, true);
	}

	private static void println(String string, boolean addNewLine) {
		short length = (short) string.length();
		if (addNewLine) {
			length++;
		}
		getBytes(string, addNewLine);
		writer.write(bytes, length);
	}

	private static byte[] getBytes(String string, boolean addNewLine) {
		int index = 0;
		int length = string.length();

		if (writer == null) {
			writer = new DefaultWriter();
		}

		short maxLineLength = writer.getMaxLineLength();
		
		if (bytes == null) {
			bytes = new byte[maxLineLength + 1];
		}

		while ((index < length) && (index < maxLineLength - 1)) {
			bytes[index] = (byte) string.charAt(index);
			index++;
		}

		if (addNewLine) {
			bytes[index] = '\n';
		}
		return bytes;
	}

	public static void print(long l) {
		print("" + l);
	}

	public static void print(String space) {
		println(space, false);
	}

	public static void println(int i) {
		println("" + i);

	}

}
