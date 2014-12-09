package icecaptools.compiler;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Iterator;

import icecaptools.IcecapTool;

public class ConstantGenerator {

	private ByteCodePatcher patcher;
	private IcecapTool manager;
	private StringBuffer defines;
	private LabeledMemorySegment requiredIncludes;
	private boolean supportLoading;

	public ConstantGenerator(ByteCodePatcher patcher, IcecapTool manager, StringBuffer defines,
			LabeledMemorySegment requiredIncludes, boolean supportLoading) {
		this.patcher = patcher;
		this.manager = manager;
		this.defines = defines;
		this.requiredIncludes = requiredIncludes;
		this.supportLoading = supportLoading;
	}

	public StringBuffer generateConstants() throws Exception {
		MemorySegment buffer = new MemorySegment(manager.getProperties());
		boolean LDC_string = false;
		boolean LDC_long = false;
		boolean LDC_float = false;
		boolean LDC_double = false;

		ArrayList<LDCConstant> constants = patcher.getConstants();

		if (constants.size() > 0) {

			Iterator<LDCConstant> iterator = constants.iterator();
			int count = 0;
			while (iterator.hasNext()) {
				LDCConstant current = iterator.next();
				if (current.getType() == LDCConstant.STRING) {
					if (!LDC_string) {
						defines.append("#define LDC_STRING\n");
						LDC_string = true;
					}
					String nonAscii = filterNonAscii(current.getString(), false);

					declareStringConstant(buffer, count, nonAscii, filterNonAscii(current.getString(), true));

				} else if (current.getType() == LDCConstant.LONG) {
					if (!LDC_long) {
						defines.append("#define LDC_LONG\n");
						LDC_long = true;
					}
					buffer.appendCode("RANGE static const unsigned char long_constant_" + count + "[8] PROGMEM = { ", 8);
					long val = current.getLong();
					buffer.print("0x" + Integer.toHexString((int) ((val >> 56) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) ((val >> 48) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) ((val >> 40) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) ((val >> 32) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) ((val >> 24) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) ((val >> 16) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) ((val >> 8) & 0xff)));
					buffer.print(", 0x" + Integer.toHexString((int) (val & 0xff)));
					buffer.print(" };\n");
				} else if (current.getType() == LDCConstant.FLOAT) {
					if (!LDC_float) {
						defines.append("#define LDC_FLOAT\n");
						LDC_float = true;
					}
				} else if (current.getType() == LDCConstant.DOUBLE) {
					if (!LDC_double) {
						defines.append("#define LDC_DOUBLE\n");
						LDC_double = true;
					}
					buffer.appendCode("RANGE static const double double_constant_" + count + " PROGMEM = ", 8);
					buffer.print(Double.toString(current.getDouble()));
					buffer.print(";\n");
				}
				count++;
			}
			if (count > 0) {
				buffer.print("\n");
			}
			buffer.appendData("RANGE static const ConstantInfo _constants[" + constants.size() + "] PROGMEM = {\n",
					9 * constants.size());
			iterator = constants.iterator();
			StringBuffer stringConstants = new StringBuffer();
			int stringID = 0;
			count = 0;
			while (iterator.hasNext()) {
				LDCConstant current = iterator.next();
				String type = "CONSTANT_UNKNOWN";
				switch (current.getType()) {
				case LDCConstant.STRING:
					type = "CONSTANT_STRING";
					buffer.print(" { " + type + ", " + ((stringID << 16) | (current.getString().length() & 0xffff))
							+ ", string_constant_" + count + ", 0 }");
					stringConstants.append("0, ");
					stringID++;
					break;
				case LDCConstant.INTEGER:
					type = "CONSTANT_INTEGER";
					buffer.print(" { " + type + ", " + current.getInt() + ", 0, 0 }");
					break;
				case LDCConstant.FLOAT: {
					float f = current.getFloat();
					String floatString;
					if (f == Float.POSITIVE_INFINITY) {
						requiredIncludes.print("#include <math.h>\n");
						floatString = "INFINITY";
					} else if (f == Float.NEGATIVE_INFINITY) {
						requiredIncludes.print("#include <math.h>\n");
						floatString = "-INFINITY";
					} else {
						floatString = "" + f;
					}

					type = "CONSTANT_FLOAT";

					buffer.print(" { " + type + ", 0, 0, " + floatString + " }");
				}
					break;
				case LDCConstant.LONG:
					type = "CONSTANT_LONG";
					buffer.print(" { " + type + ", " + 0 + ", long_constant_" + count + ", 0 }");
					break;
				case LDCConstant.DOUBLE:
					type = "CONSTANT_DOUBLE";
					buffer.print(" { " + type + ", " + 0 + ", &double_constant_" + count + ", 0 }");
					break;
				case LDCConstant.CLASS:
					type = "CONSTANT_CLASS";
					buffer.print(" { " + type + ", " + patcher.getClassNumber(current.getClassName()) + ", 0, 0 }");
					break;
				}

				if (iterator.hasNext()) {
					buffer.print(",");
				}
				buffer.print("\n");
				count++;
			}
			if (stringID > 0) {
				stringConstants = new StringBuffer(stringConstants.substring(0, stringConstants.length() - 2));
				stringConstants.insert(0, "Object* stringConstants[" + stringID + "] = { ");
				stringConstants.append(" };\n");
			} else {
				stringConstants.append("Object* stringConstants[1] = { 0 };\n");
			}
			buffer.print("};\n");
			buffer.appendData(stringConstants.toString(), 0);
			return buffer.getBuffer();
		} else {
			if (supportLoading) {
				buffer.print("Object* stringConstants[1] = { 0 };\n");
				return buffer.getBuffer();
			} else {
				return new StringBuffer();
			}
		}
	}

	private void declareStringConstant(MemorySegment buffer, int count, String nonAscii, String asComment) {
		byte[] bytes = nonAscii.getBytes();

		buffer.appendCode("RANGE static const unsigned char string_constant_" + count + "[" + (bytes.length + 1)
				+ "] PROGMEM = { ", bytes.length + 1);
		if (bytes.length > 0) {
			buffer.print(getHex(bytes, 16, null));
			buffer.print(",0x00 }; /* ");
		} else {
			buffer.print("0x00 }; /* ");
		}
		buffer.print(asComment + " */\n");
	}

	public static final String HEXES = "0123456789ABCDEF";

	public static String getHex(byte[] raw, int lineLen, FieldInfo[] fieldOffsets) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(4 * raw.length);
		byte b;
		for (int i = 0; i < raw.length; i++) {
			if ((fieldOffsets != null) && (fieldOffsets[i] != null)) {
				FieldInfo finfo = fieldOffsets[i];
				StringBuffer offsetof = new StringBuffer();
				if (finfo.isClassField) {
					String structName = finfo.getStructName();
					if (structName != null) {
						offsetof.append("(offsetof(struct ").append(structName).append(", ");
						offsetof.append(finfo.getStructMemberName()).append(") << 3)");
					} else {
						offsetof.append("0");
					}
				} else {
					offsetof.append("((offsetof(struct ").append(finfo.getStructName()).append(", ");
					offsetof.append(finfo.getStructMemberName()).append(") - sizeof(Object)) << 3)");
				}

				hex.append("\n  ");
				hex.append("/* offset: ");
				hex.append(raw[i]);
				hex.append(", ");
				hex.append(raw[i + 1]);
				hex.append("*/\n");
				hex.append("(uint8)(((uint16)");
				hex.append(offsetof);
				hex.append(") >> 8)");
				hex.append(", ");

				hex.append("(uint8)(((uint16)");
				hex.append(offsetof);
				hex.append(") & 0xff)");
				hex.append(",");
				hex.append("\n  ");
				i++;
			} else {
				b = raw[i];
				if (i % lineLen == 0 && i != 0) {
					hex.append("\n  ");
				}
				hex.append("0x").append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F))).append(",");
			}
		}
		// ugly hack to remove last comma
		return hex.toString().substring(0, hex.length() - 1);
	}

	private static String filterNonAscii(String inString, boolean escape) {
		// Create the encoder and decoder for the character encoding
		Charset charset = Charset.forName("US-ASCII");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		// This line is the key to removing "unmappable" characters.
		encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
		String result = inString;

		try {
			// Convert a string to bytes in a ByteBuffer
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(inString));

			// Convert bytes in a ByteBuffer to a character ByteBuffer and then
			// to a string.
			CharBuffer cbuf = decoder.decode(bbuf);

			StringBuffer nonNullBuf = new StringBuffer();

			int length = cbuf.length();
			for (int i = 0; i < length; i++) {
				char next = cbuf.charAt(i);
				if (next != 0) {
					if (escape) {
						if (next == '"') {
							nonNullBuf.append('\\');
							nonNullBuf.append(next);
						} else if (next == '\n') {
							nonNullBuf.append('\\');
							nonNullBuf.append('n');
						} else if (next == '\r') {
							nonNullBuf.append('\\');
							nonNullBuf.append('r');
						} else if (next == '\\') {
							nonNullBuf.append('\\');
							nonNullBuf.append('\\');
						} else {
							nonNullBuf.append(next);
						}
					} else {
						nonNullBuf.append(next);
					}
				}
			}

			if (nonNullBuf.length() > 509) {
				result = nonNullBuf.substring(0, 509);
			} else {
				result = nonNullBuf.toString();
			}
		} catch (CharacterCodingException cce) {
			;
		}
		return result.replace("*/", "__").replace("/*", "__");
	}

	public int numberOfConstants() {
		return patcher.getConstants().size();
	}
}
