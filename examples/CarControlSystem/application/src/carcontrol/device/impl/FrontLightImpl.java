package carcontrol.device.impl;

import java.io.File;

import carcontrol.device.FrontLight;
import carcontrol.scjlevel0.CarConfiguration;
import icecaptools.IcecapInlineNative;

import test.same70.configuration.ConfigSAME;

public class FrontLightImpl implements FrontLight {
	
	@Override
	public void turnOn() {
		front_light_turn_on();
		front_light_low_beam();
	}

	@Override
	public void turnOff() {
		front_light_turn_off();
	}

	@Override
	public void shortLight() {
		front_light_low_beam();
	}

	@Override
	public void longLight() {
		front_light_high_beam();
	}

	// --- native methods ------------------------------
	
	//static final String includePath = "#include " + ConfigSAME.SAME_src + File.separator + "scalextric" + File.separator + "front_light.h";
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   front_light_turn_on();\n"
		+ "   return -1;\n"
		+ "}\n",
			
//		+ "{\n"
//		+ " #ifndef TEST \n"
//		+ "   front_light_turn_off();\n"
//		+ "   return -1;\n"
//		+ " #endif \n"
//		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\front_light.h\"\n"  // relative path: works
		
			//+ "#include \"C:\\Users\\hso\\sameout\\src\\scalextric\\front_light.h\"\n"  // absolute path: works
			//+ "#include \"C:\\Users\\hso\\CarControlSystem\\hal\\front_light.h\"\n"
			
			//+ "#include <stdio.h>\n"
		
//			+ " #ifndef TEST //  \n" 
//			+ " #include \"C:\\Users\\hso\\sameout\\src\\scalextric\\front_light.h\"\n"
//			+ " #endif \n"
		)
	native void front_light_turn_on();	

	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   front_light_turn_off();\n"
		+ "   return -1;\n"
		+ "}\n",
		
//		+ "{\n"
//		+ " #ifndef DEBUG \n"
//		+ "   front_light_turn_off();\n"
//		+ "   return -1;\n"
//		+ " #endif \n"
//		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\front_light.h\"\n"
				
			//+ "#include \"C:\\Users\\hso\\sameout\\src\\scalextric\\front_light.h\"\n"
			
			//+ "#include \"C:\\Users\\hso\\CarControlSystem\\hal\\front_light.h\"\n"
			
			//+ "#include <stdio.h>\n"
		)
	native void front_light_turn_off();
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   front_light_low_beam();\n"
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\front_light.h\"\n"
				
			//+ "#include \"C:\\Users\\hso\\sameout\\src\\scalextric\\front_light.h\"\n"
			
			//+ "#include \"C:\\Users\\hso\\CarControlSystem\\hal\\front_light.h\"\n"
		)
	native void front_light_low_beam();	
	
	@IcecapInlineNative(functionBody = ""
		+ "{\n"
		+ "   front_light_high_beam();\n"
		+ "   return -1;\n"
		+ "}\n",
		requiredIncludes = ""
			+ "#include \"..\\scalextric\\front_light.h\"\n"
			//+ "#include \"C:\\Users\\hso\\sameout\\src\\scalextric\\front_light.h\"\n"
			
			//+ "#include \"C:\\Users\\hso\\CarControlSystem\\hal\\front_light.h\"\n"
		)
	native void front_light_high_beam();
}
