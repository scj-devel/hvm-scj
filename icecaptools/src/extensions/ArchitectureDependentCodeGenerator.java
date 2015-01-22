package extensions;

import java.io.IOException;
import java.io.OutputStream;


public interface ArchitectureDependentCodeGenerator {

	/** Token to include architecture dependent code detected*/
	public void tokenDetected(String file, OutputStream Stream) throws IOException;

}
