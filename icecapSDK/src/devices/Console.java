package devices;

import util.StringUtil;

public class Console {

	private static byte[] bytes;

	public static Writer writer;

	public static void println(String string) {
		println(string, true);
	}

	private static void println(String string, boolean addNewLine) {
		short length = getBytes(string, addNewLine);
		writer.write(bytes, length);
	}

	private static short getBytes(String string, boolean addNewLine) {
		short index = 0;
		short length = (short) string.length();

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
			index++;
		}
		return (short) index;
	}

	public static void print(long l) {
		print(StringUtil.constructString("", (int) l));
	}

	public static void print(String space) {
		println(space, false);
	}

	public static void println(int i) {
		println(StringUtil.constructString("", i));
	}
	
	public static void print(float f) {  // print f with 4 decimals
		
		print (floatToString(f));
	}
	
	public static String floatToString (float f) {  // HSO: perhaps later: improve to n decimals
		float x = f;
		if (f < 0) 
			x = -f;
		
		// Extract integer part
	    int ipart = (int) x;
	    
	    // Extract floating part
	    int fpart = Math.round((x - ipart) * 10000);  // four decimals
	    
	    String str = StringUtil.constructString("", ipart) + "." + StringUtil.constructString("", fpart);
		
	    if (f < 0) 
		    return "-" + str;
		else 		    
		    return str;
	}
}
