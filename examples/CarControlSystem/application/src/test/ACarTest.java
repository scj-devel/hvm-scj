package test;

import javax.safetycritical.MissionSequencer;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import carcontrol.configuration.Configuration;
import carcontrol.infrastructure.Adapter;
import carcontrol.infrastructure.VMConfiguration;
import carcontrol.test.Passive;
import vm.Memory;
import carcontrol.util.NullSequencer;

/**
 * A template for a Passive test.
 * @author HSO
 *
 */
public class ACarTest extends Passive {

	final int testCount = 2; 
	  
	public ACarTest() {
		super.setTestCount(testCount);
		super.setTestName(getClass().getName());
		System.out.println("\n*** ACarTest.constructor *** \n");
	}
	  	
	  
	/**
	 * Launch test application.
	 * A simple test application is launched at <code>LEVEL_0</code>.
	 * 
	 * @param args are not used.
	 */
	public static void main(String[] args) {
		
		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
		Memory.startMemoryAreaTracking();
		vm.Process.enableStackAnalysis();
		
		try {
			testApp = Adapter.instance().launch(Level.LEVEL_0, ACarTest.class);
		}
		catch (Throwable e) {
			if (testApp == null )
			  System.out.println("\n*** ACarTest.main: Fatal launch.error: \n"+ e);
			else
				testApp.getTestResult().addError(testApp, e);
		}	
		
		vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		
		System.out.println("ACarTest.main ending ...");
		if (VMConfiguration.CarTest)
		  testApp.cleanUp();
		else
		  System.out.println("ACarTest.main end: CarTest is set to " + VMConfiguration.CarTest);
			
	}
	
	/**
	 * The simple tests of a passive class are executed during application initialization.
	 */
	@Override
	public void initializeApplication(String[] args) {
	  System.out.println("\n ACarTest.initializeApplication: runTest ...");
	  runTest();
	  
	  System.out.println("\n ACarTest.initializeApplication: runTest finished \n");
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
	public void test(int i) {
		switch (i) { 
	      case  1: 
	    	  System.out.println("\n ACarTest.case 1 called");
	    	  break;
	      case  2: 
	    	  System.out.println("\n ACarTest.case 2 called");
	    	  break;
	      default: break;
		}
	}

}
