package carcontrol.device.impl;

//import carcontrol.device.FrontLight;
//import carcontrol.scjlevel0.CarConfiguration;
//import icecaptools.IcecapInlineNative;

public class FakeFrontLight extends FrontLightImpl 
{
	
	// Override the native methods
	@Override	
    void front_light_turn_on() {
		System.out.println("\n** FrontLightFake.front_light_turn_on **\n");
	}
    
    @Override
    void front_light_turn_off() {
    	System.out.println("\n** FrontLightFake.front_light_turn_off **\n");
    }	
   
    @Override
    void front_light_low_beam() {	
    	//FrontLightImpl.nativeMethodHasBeenCalled++;
    }
   
    @Override
    void front_light_high_beam() {	
    	//FrontLightImpl.nativeMethodHasBeenCalled++;
    }
    
    

}
