package icecaptools.launching.uart;

import java.io.IOException;

import gnu.io.UnsupportedCommOperationException;

public interface InputParser {

	InputParser parseByte(byte b) throws IOException, UnsupportedCommOperationException;
}
