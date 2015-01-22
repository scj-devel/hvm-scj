package icecaptools.launching.uart;

import java.io.IOException;

import gnu.io.UnsupportedCommOperationException;

public interface OutputParser {

	void parseByte(byte b) throws IOException;

	void setPortSpeed(int i) throws UnsupportedCommOperationException;

	void enableOutputTrace();

	void flush() throws IOException;

	void increaseWorkCount();

	void addProgressMessage(String string);

	void parseBytes(byte[] data, int top) throws IOException;
}
