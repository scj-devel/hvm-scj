package devices.arm.ATSAMe70;

import java.io.File;

public abstract class ConfigPath {
	
	// The following are highly installation dependent:
	
	// At HSO: C:\Users\hso\icecapout
	public static final String OUTPUT_FOLDER = 
		System.getProperty("user.home") +  File.separator + "icecapout";
	
	// At HSO: C:\Users\hso\same70
	public static final String SAME_LOCATION = 
		System.getProperty("user.home") +  File.separator + "same70";
	
	// The name of the AtmelStudio project
	public static final String SAME_SPACE = "SAME70Xplained-sandbox";
	
	
	// These are unlikely to change:
	
	public static final String SAME_ASF_src =
		SAME_LOCATION + File.separator + SAME_SPACE + File.separator 
		+ "src" + File.separator + "ASF";
	
	public static final String SAME_DEBUG_ASF_src = 
		SAME_LOCATION + File.separator + SAME_SPACE + File.separator 
		+ "Debug" + File.separator + "src" + File.separator + "ASF";
	
	public static final String SAME_CONFIG_src =
		SAME_LOCATION + File.separator + SAME_SPACE + File.separator 
		+ "src" + File.separator + "config" + File.separator;
	
	
}
