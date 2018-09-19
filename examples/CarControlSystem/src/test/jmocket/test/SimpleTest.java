package test.jmocket.test;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * How to mock private methods in JMockit?
 * From: Abhi, JMockit Tutorial:Learn it today with examples. 
 * http://abhinandanmk.blogspot.dk/2012/06/jmockit-tutoriallearn-it-today-with.html#private_method
 * 
 * JMockit Advanced Usage
 * From: Baeldung. http://www.baeldung.com/jmockit-advanced-usage
 * 
 * @author HSO
 *
 */
@RunWith(JMockit.class)
public class SimpleTest {
	
	@Test
	 public void testPublicInvokesPrivateMockUp(){	   
	  
		  new MockUp<Simple>() {	    
			  
		    //Override the private method; don't provide any ACCESSS MODIFIER!
		    @Mock
		    String iAmPrivate() {
		      return "MockUp Invoke";
		    }	    
		  };
		   
		  String str = new Simple().publicCallsPrivate();
		  
		  Assert.assertEquals("MockUp - String returned: ", "MockUp Invoke", str);	   
	 }
	 
	 @Test
	 public void testPublicInvokesPrivateVoidMockUp() {	   
	  
		  new MockUp<Simple>() {	
			  
			//Override the private method; don't provide any ACCESSS MODIFIER!
		    @Mock
		    void iAmPrivateAndReturnsVoid(){	
			   Simple.nativeMethodHasBeenCalled = true;
		    }	    
		  };
		   
		  Simple simple = new Simple();		   
		  simple.publicCallsPrivateVoid();
		  
		  Assert.assertEquals("MockUp - void returned: ", true, Simple.nativeMethodHasBeenCalled);
	 }
	 
	 @Test
	 public void testPublicInvokesPrivateWithAParameterMockUp(){	   
	  
		  new MockUp<Simple>() {	    
			  
		    //Override the private method; don't provide any ACCESSS MODIFIER!
		    @Mock
		    String iAmPrivateWithAParameter(int x){
		      return "MockUp Invoke " + x;
		    }	    
		  };
		   
		  Simple simple = new Simple();		   
		  String str = simple.publicCallsPrivateWithAParameter(2);
		  
		  Assert.assertEquals("MockUp - String returned: ", "Value: " + "MockUp Invoke 2", str);	   
	 }
}

