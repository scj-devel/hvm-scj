package test.ARM.ATSAMe70;


import devices.arm.ATSAMe70.ATSAMe70MinimalTargetConfiguration;
import devices.arm.ATSAMe70.ATSAMe70Config;

public class HelloATSAM extends ATSAMe70MinimalTargetConfiguration {
	
	@Override
	protected String getSrcASFLocation() {	
		return ATSAMe70Config.SAME_src_ASF;
	}
	
	@Override
	protected String getDebugSrcASFLocation() {
		return ATSAMe70Config.SAME_Debug_src_ASF;
	}
	
	@Override
	protected String getSrcLocation() {
		return ATSAMe70Config.SAME_src;
	}

	@Override
	public String getOutputFolder() { 
		return ATSAMe70Config.OUTPUT_FOLDER;
	}

	public static void main(String[] args) {
		init();

		devices.Console.println("HelloATSAM started: HSO!!!");
		
		turnOnFrontLight();
		delay (10000);		
		turnOffFrontLight();
		
		devices.Console.println("HelloATSAM end");
	}

	

}
