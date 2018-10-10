package test.jmocket.light;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import carcontrol.device.impl.FrontLightImpl;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class FrontLightTest {
	
	int nativeMethodHasBeenCalled; // HSO: for some  test
	
	@Before
	public void setup() {
		nativeMethodHasBeenCalled = 0;
	}
	
	@Test
	public void testTurnOn() {	   
	  
	  new MockUp<FrontLightImpl>() {	   
			  
		// Override the private method; Don't provide any ACCESSS MODIFIER!
	    @Mock
	    void front_light_turn_on() {	
	    	nativeMethodHasBeenCalled++;
	    }	
	    
	    @Mock
	    void front_light_low_beam() {	
	    	nativeMethodHasBeenCalled++;
	    }
	  };
	   
	  new FrontLightImpl().turnOn();  
	  
	  Assert.assertEquals(2, nativeMethodHasBeenCalled);
	 }
	
	@Test
	public void testTurnOff() {	   
	  
	  new MockUp<FrontLightImpl>() {	   
			  
		// Override the private method; Don't provide any ACCESSS MODIFIER!
	    @Mock
	    void front_light_turn_off() {	
	    	nativeMethodHasBeenCalled++;
	    }	    
	  };
	   
	  new FrontLightImpl().turnOff();  
	  
	  Assert.assertEquals(1, nativeMethodHasBeenCalled);
	 }

	@Test
	public void testShortLight() {	   
	  
	  new MockUp<FrontLightImpl>() {	   
			  
		// Override the private method; Don't provide any ACCESSS MODIFIER!
	    @Mock
	    void front_light_low_beam() {	
	    	nativeMethodHasBeenCalled++;
	    }	    
	  };
	   
	  new FrontLightImpl().shortLight();  
	  
	  Assert.assertEquals(1, nativeMethodHasBeenCalled);
	 }
	
	@Test
	public void testLongLight() {	   
	  
	  new MockUp<FrontLightImpl>() {	   
			  
		// Override the private method; Don't provide any ACCESSS MODIFIER!
	    @Mock
	    void front_light_high_beam() {	
	    	nativeMethodHasBeenCalled++;
	    }	    
	  };
	   
	  new FrontLightImpl().longLight();  
	  
	  Assert.assertEquals(1, nativeMethodHasBeenCalled);
	 }
}
