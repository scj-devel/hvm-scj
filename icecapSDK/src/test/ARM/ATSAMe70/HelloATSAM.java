package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70TargetConfiguration;

public class HelloATSAM extends ATSAMe70TargetConfiguration {

	public static void main(String[] args) {
		init();

		devices.Console.println("ZAPPA!");

		blink(20000);
	}

	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}

	@Override
	protected String getASFLocation() {
		return "../ASF";
	}
}
