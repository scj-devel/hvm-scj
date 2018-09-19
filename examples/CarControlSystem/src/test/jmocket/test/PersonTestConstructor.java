package test.jmocket.test;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

public class PersonTestConstructor {
	
	@Test
	 public void testGetName() {
		  new MockUp<Person>() {
			   @Mock
			   public void $init() {
			    //Dont assign name variable at all
			    //Leave it null
			   }	 
		  };
	   
	  Person p = new Person();
	  String name = p.getName();
	   
	  assertNull("Name of person is null",name);
	 }
}
