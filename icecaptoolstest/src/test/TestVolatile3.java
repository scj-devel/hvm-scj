package test;

public class TestVolatile3 {

	private static class Configuration
	{
		volatile String name;
		volatile int age;
		volatile Configuration other;
		
		Configuration()
		{
			name = "default";
			age = 42;
		}
	}
	
	private static class DefaultConfiguration extends Configuration
	{
		DefaultConfiguration()
		{
			other = new Configuration();			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration conf = new DefaultConfiguration();
		
		devices.System.lockROM();
		
		if (conf.name.equals("default"))
		{
			if (conf.age == 42)
			{
			    if (conf.other.name.equals("default"))
			    {
			        if (conf.other.age == 42)
			        {
			            if (conf.other.other == null)
			            {
			                args = null; 
			            }
			        }
			    }
			}
		}
	}
}
