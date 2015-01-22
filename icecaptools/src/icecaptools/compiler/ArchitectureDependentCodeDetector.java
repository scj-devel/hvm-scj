package icecaptools.compiler;

import icecaptools.Activator;
import icecaptools.extensions.ExtensionManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import extensions.ArchitectureDependentCodeGenerator;

public class ArchitectureDependentCodeDetector extends ExtensionManager<ArchitectureDependentCodeGenerator> implements CodeDetector {

	private String currentFile;
	private OutputStream stream;
	private CharSequence token;
	int counter;


	public ArchitectureDependentCodeDetector(CharSequence token) {
		super();
		this.token = token;
	}

	public ArchitectureDependentCodeDetector() {
		this("/* Insert architecture dependent code here */");
	}

	public void fileStart(String file, FileOutputStream stream) {
		currentFile = file;
		this.stream = stream;
		counter = 0;
	}

	public void newRead(char character) {
		counter = token.charAt(counter) == character ? counter + 1 : 0;
		if (counter == token.length()) {
			dispatchTokenDetectedEvent();
			counter = 0;
		}
	}


	private void dispatchTokenDetectedEvent() {
		for (ArchitectureDependentCodeGenerator extension : this.getExtensions()) {
			try {
				extension.tokenDetected(this.currentFile, this.stream);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	protected String getCodeGeneratorExtensionPoint() {
		return Activator.PLUGIN_ID
		+ ".ArchitectureDependentCodeGenerator";
	}

	@Override
	protected String getCodeGeneratorExtensionElement() {
		return "class";
	}

}
