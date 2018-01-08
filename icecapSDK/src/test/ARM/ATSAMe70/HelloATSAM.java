package test.ARM.ATSAMe70;


import devices.arm.ATSAMe70.ATSAMe70MinimalTargetConfiguration;
import devices.arm.ATSAMe70.ConfigPath;

public class HelloATSAM extends ATSAMe70MinimalTargetConfiguration {

	@Override
	protected String getASFIncludeLocation() {
		return ConfigPath.SAME_ASF_src;
		//return "C:\\Users\\hso\\same70\\SAME70Xplained-sandbox\\src\\ASF";
	}
	
	@Override
	protected String getASFObjectLocation() {
		return ConfigPath.SAME_DEBUG_ASF_src;
		//return "C:\\Users\\hso\\same70\\SAME70Xplained-sandbox\\Debug\\src\\ASF";
	}

	public static void main(String[] args) {
		init();

		devices.Console.println("ZAPPA!");

		blink(10000);
	}

	@Override
	public String getOutputFolder() {
		return ConfigPath.OUTPUT_FOLDER;
	}

}
