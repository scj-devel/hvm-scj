package test.inttable;

import javax.safetycritical.MissionSequencer;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import carcontrol.configuration.Configuration;
import carcontrol.infrastructure.Adapter;
import carcontrol.test.Assert;
import carcontrol.test.Passive;
import carcontrol.test.TestResult;
import vm.Memory;
import carcontrol.util.NullSequencer;

public class IntTableTest extends Passive {
	
	//--- IntTableTest ---------------------------------
	
	final int testCount = 3; 
	  
	public IntTableTest() {
		super.setTestCount(testCount);
		super.setTestName(getClass().getName());
	}
	  
	/**
	 * Launch test application.
	 * A simple test application is launched at <code>LEVEL_0</code>.
	 * 
	 * @param args are not used.
	 */
	public static void main(String[] args) {
		
//		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
//		Memory.startMemoryAreaTracking();
//		vm.Process.enableStackAnalysis();
		
		try {
			testApp = Adapter.instance().launch(Level.LEVEL_0, IntTableTest.class);
		}
		catch (Throwable e) {
			if (testApp == null )
			  System.out.println("\n*** IntTableTest.main: Fatal launch.error: \n"+ e);
			else
				testApp.getTestResult().addError(testApp, e);
		}	
		
//		vm.Process.reportStackUsage();
//		Memory.reportMemoryUsage();
		
		testApp.cleanUp();
	}
	
	/**
	 * The simple tests of a passive class are executed during application initialization.
	 */
	@Override
	public void initializeApplication(String[] args) {
	  System.out.println("\nIntTableTest.initializeApplication: runTest ...");
	  runTest();
	  
	  System.out.println("\nIntTableTest.initializeApplication: runTest finished \n");
	}
	
	@Override
	public MissionSequencer getSequencer() {
		return new NullSequencer();
	}
	
	@Override
	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}


   @Override  
   public void test(int i) 
   {
     IntTable table;
     int max;
     switch (i) {    
       // ...
       case  1:    	 
    	 System.out.println("\nIntTableTest.test: case = " + i);
    	 max = 3;
    	 table = new IntTable(max);
    	 Assert.assertEquals(0, table.size());
    	 break;
       case  2:
    	 System.out.println("\nIntTableTest.test: case = " + i);
    	 max = 3;
    	 table = new IntTable(max);
    	 Assert.assertEquals(3, table.length());
    	 break;	 
       case 3:
    	 System.out.println("\nIntTableTest.test: case = " + i);
      	 max = 5;
      	 table = new IntTable(max);
	     for (int j = 0; j < max; j++)
	        table.insert(10 * j);	     
	     Assert.assertEquals(max, table.size());
	     
	     table.remove (10); // element 1
	     Assert.assertEquals(max-1, table.size());
	     
	     Assert.assertEquals(40, table.getValue(1));
	    
	     table.remove (40);  // the last element
	     Assert.assertEquals (max - 2, table.size());
	     
	     Assert.assertEquals(30, table.getValue(1));
	    
	     table.remove (22);  // not in table
	     Assert.assertEquals(max-2, table.size()); 
       default: break;
     } 
  }
}
