package carcontrol.scjlevel0.motor_and_bt;

import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;

/**
 * This SCJ level 0 example uses BluetoothCommunicationDeviceImpl
 * 
 * @author hso
 *
 */
public class Car implements Safelet  {
	
	CarConfiguration carConfig;
	
	// --- Safelet methods begin ------------------------------
	
	@Override
	public MissionSequencer getSequencer() {
		System.out.println("Car.getSequencer: " + CarConfiguration.SOnames.get(0));
		return new CarSequencer(CarConfiguration.SOnames.get(0));
	}

	@Override
	public long managedMemoryBackingStoreSize() {
		System.out.println("HSO Car.managedMemoryBackingStoreSize called ");
		return Const.IMMORTAL_MEM_DEFAULT;  // ToDo: returns the amount of additional backing store memory
	}
	
	@Override
	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;  
	}

	@Override
	public void initializeApplication(String[] args) {
		System.out.println("Car.initializeApplication");
		
		carConfig = new CarConfiguration();
		carConfig.initMissionMemSizes();
		carConfig.initSOConfigTable();
		
		carConfig.initCar();
	}	
		
	@Override
	public boolean handleStartupError(int cause, long val) {		
		System.out.println("HSO Car.handleStartupError called ");
		return false;
	}

	@Override
	public void cleanUp() {
		System.out.println("HSO Car.cleanUp called ");
	}

}
