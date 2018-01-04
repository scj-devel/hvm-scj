package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70MinimalTargetConfiguration;

public class HelloATSAM extends ATSAMe70MinimalTargetConfiguration {

	@Override
	protected String getASFIncludeLocation() {
		return "C:\\Users\\chfs\\Downloads\\SAME70Xplained-sandbox\\src\\ASF";
	}
	
	@Override
	protected String getASFObjectLocation() {
		return "C:\\Users\\chfs\\Downloads\\SAME70Xplained-sandbox\\Debug\\src\\ASF";
	}

	public static void main(String[] args) {
		init();

		devices.Console.println("ZAPPA!");

		blink(10000);
	}

	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}

}
