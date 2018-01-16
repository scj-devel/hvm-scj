package test.ARM.ATSAMe70;


import devices.arm.ATSAMe70.ATSAMe70MinimalTargetConfiguration;
import devices.arm.ATSAMe70.ConfigPath;

public class HelloATSAM extends ATSAMe70MinimalTargetConfiguration {

	@Override
	protected String getASFIncludeLocation() {		
		//return "C:\\Users\\hso\\same70\\SAME70Xplained-sandbox\\src\\ASF";
		return ConfigPath.SAME_ASF_src;
	}
	
	@Override
	protected String getASFObjectLocation() {
		//return "C:\\Users\\hso\\same70\\SAME70Xplained-sandbox\\Debug\\src\\ASF";
		return ConfigPath.SAME_DEBUG_ASF_src;
	}

	public static void main(String[] args) {
		init();

		devices.Console.println("HelloATSAM started: HSO!");
		
		turnOnFrontLight();
		delay (10000);		
		turnOffFrontLight();
		
		devices.Console.println("HelloATSAM end");
	}

	@Override
	public String getOutputFolder() { 
		//return "C:\\Users\\hso\\same70ToBoard\\src";
		return ConfigPath.OUTPUT_FOLDER;
	}

}
