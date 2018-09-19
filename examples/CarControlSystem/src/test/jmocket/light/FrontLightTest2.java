package test.jmocket.light;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import carcontrol.device.impl.FrontLightImpl;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class FrontLightTest2 {

	@Test
    public void testTurnOn(@Mocked FrontLightImpl light)
    {
		// does not work
//        new Expectations() {{ 
//        	light.front_light_turnon(); times = 1;
//        }};
//        
//        light.turnOn();
//        
//        new Verifications() {{
//        	light.front_light_turnon(); times = 1;
//    	}};

    }
}
