package test.carcontrol.level0;

import javax.safetycritical.MissionSequencer;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;

import vm.Memory;
import carcontrol.scjlevel0.CarConfiguration;
import carcontrol.scjlevel0.CarSequencer;
import carcontrol.test.Active;
import carcontrol.infrastructure.Adapter;
import carcontrol.infrastructure.VMConfiguration;

public class Car0Test extends Active {
	
	CarConfiguration carConfig;
		
	public Car0Test() {
		super.setTestName(getClass().getName());
		System.out.println("\n*** Car0Test.constructor *** \n");
	}
	 
	/**
	 * Launch test application.
	 * 
	 * @param args are not used.
	 */ 
	// Does not work any more, because Car uses scaletrix C files, such as: ..\scalextric\front_light.h
	public static void main(String[] args) {
		
		Const.OVERALL_BACKING_STORE = 250 * 1000;
		Const.IMMORTAL_MEM = 60*1000; // 40*1000;
		Const.MISSION_MEM = 10*1000;
		Const.PRIVATE_MEM = 5*1000;
		
		Const.HANDLER_STACK_SIZE = 1024; // 420; // 2048;
		
		Const.MEMORY_TRACKER_AREA_SIZE = 15*1000;
		Const.CYCLIC_SCHEDULER_STACK_SIZE = 1024; // 420; // 2048; 
		
		Const.MEMORY_TRACKER_AREA_SIZE = 30000;
		
		Memory.startMemoryAreaTracking();
		vm.Process.enableStackAnalysis();
		
		try {
			testApp = Adapter.instance().launch(Level.LEVEL_0, Car0Test.class);	 
			System.out.println("Car0Test.testApp: " + testApp);
		}
		catch (Throwable e) {
			if (testApp == null )
			  System.out.println("\n** Car0Test.main: Fatal launch.error \n"+ e);
			else
				testApp.getTestResult().addError(testApp, e);
		}
		
		vm.Process.reportStackUsage();
		Memory.reportMemoryUsage();
		
		System.out.println("Car0Test.main ending ...");
		if (VMConfiguration.CarTest)
		  testApp.cleanUp();
		else
		  System.out.println("Car0Test.main end: CarTest is set to " + VMConfiguration.CarTest);
	}
	
	@Override
	public MissionSequencer getSequencer() {
		System.out.println("Car0Test.getSequencer: " + CarConfiguration.SOnames.get(0));
		return new CarSequencer(CarConfiguration.SOnames.get(0)); 
	}	
	
	@Override
	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}

	@Override
	public void initializeApplication(String[] args) {
		System.out.println("Car0Test.initializeApplication");
		
		carConfig = new CarConfiguration();
		carConfig.initMissionMemSizes();
		carConfig.initSOConfigTable();
		
		carConfig.initCar();
	}
	
}
