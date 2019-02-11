package carcontrol.scjlevel0.bt;

import javax.scj.util.Const;

import carcontrol.configuration.Configuration;
import carcontrol.configuration.SOConfig;

import carcontrol.data.RunData;
import carcontrol.device.Engine;
import carcontrol.device.FrontLight;
import carcontrol.device.impl.EngineImpl;
import carcontrol.device.impl.FakeFrontLight;
import carcontrol.device.impl.FrontLightImpl;
import carcontrol.io.BluetoothCommunicationDeviceImpl;
import carcontrol.io.CommunicationDevice;
import carcontrol.io.CommunicationDeviceImpl;
import carcontrol.io.Port;
import icecaptools.IcecapCompileMe;
import mockit.Mock;
import mockit.MockUp;

import test.same70.configuration.TargetConfigurationSAME;

import java.io.IOException;

/**
 * The Car configuration is set up in initializeApplication() in the Safelet of the car.
 * @author hso
 *
 */
public class CarConfiguration extends Configuration {
	 
	/* The set up of the car */
	
//	public final static String host = "HOST???";
//	public final static int port = 1234;
	
	//String target = "/dev/ttyUSB0";
	//String property = "baudrate=19200";
	
	public static Port port;	
	public static RunData data;		
	
	public static Engine engine;
	public static FrontLight frontLight;
	
	static final byte base = (byte)432;
		
	// called in Safelet.initializeApplication()
	public void initCar() {
		try {
			//port = new Port(new CommunicationDeviceImpl(target, property));
			
			port = new Port(new BluetoothCommunicationDeviceImpl());
			// delaying so that bluetooth have time to initialize
			TargetConfigurationSAME.delay(2000);
			
			System.out.println("CarConfiguration.initCar: port = " + port);
		}
		catch (IOException e) {
			System.out.println("CarCyclicExecutive.initialize error: " + e.getStackTrace());
		}
		data = new RunData();
		
		frontLight =  new FrontLightImpl();	
				      //new FakeFrontLight();

		engine = new EngineImpl();
		engine.engineOn();
		engine.setEngineSpeed((byte)75);
	}	
	
	// The mission memory sizes in this Level 0 car control system
	public final long[] MISSION_MEM_SIZES = 
		{ Const.MISSION_MEM , 
		  Const.MISSION_MEM ,  
		  Const.MISSION_MEM ,  
		  Const.MISSION_MEM   
		};
	
	
	// The names of the handlers in this Level 0 car control system
	final String[] names =
		new String[] {
			"CarSequencer",  // the sequencer
			
			"ParkPEvhInput",  // the handlers in mission Park
			"ParkPEvhOutput",
			
			"NeutralPEvhInput",  // the handlers in mission Neutral
			"NeutralPEvhOutput",
			
			"ReversePEvhInput",  // the handlers in mission Reverse
			"ReversePEvhOutput",
			"ReversePEvhSpeed",
			
			"DrivePEvhInput",  // the handlers in mission Drive
			"DrivePEvhOutput",
			"DrivePEvhSpeed"
	};
	
	final int [][] values = new int[][] {
	/*
	 The parameter values of a schedulable object are:
	 
	  { priority,     
	    start_ms, start_ns,    period_ms, period_ns,     deadline_ms, deadline_ns,
	    maxInitialArea, maxImmortal, maxContainingArea, maxInitialBackingStore, 
	    messageLength, stackTraceLength, stackSize 
	  }
	 
	*/
		
	// CarSequencer:
	/*priority*/	{ 1,         
	/*times*/	      0, 0,   0, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,     
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE 
					},
	
	// ParkPEvhInput:
	/*priority*/	{ 1, 
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,        
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE 
					},
	
	// ParkPEvhOutput:
	/*priority*/    { 1,       
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,      
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE 
					},
	
	// NeutralPEvhInput:
	/*priority*/	{ 1, 
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,        
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE
					},
	
	// NeutralPEvhOutput:
	/*priority*/    { 1,       
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,      
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE 
					},
					
	// ReversePEvhInput:
	/*priority*/	{ 1, 
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,        
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE
					},
	
	// ReversePEvhOutput:
	/*priority*/    { 1,       
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,      
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE
					},
	
	// ReversePEvhSpeed:
	/*priority*/    { 1,       
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,      
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE
					},
	
	// DrivePEvhInput:
	/*priority*/	{ 1, 
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,        
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE 
					},
	
	// DrivePEvhOutput:
	/*priority*/    { 1,       
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,      
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE 
					},
	
	// DrivePEvhSpeed:
	/*priority*/    { 1,       
	/*times*/		  0, 0,   5000, 0,   0, 0,
	/*scopeParam*/	  Const.PRIVATE_MEM, 0, 0, 0,      
	/*configParam*/	  0, 0, Const.HANDLER_STACK_SIZE
					}
		  
	};
	
	// called in Safelet.initializeApplication()
	public void initMissionMemSizes() {
		for (int i = 0; i < MISSION_MEM_SIZES.length; i++) {
			Configuration.missionMemSizes.add(new Long (MISSION_MEM_SIZES[i]));
		}		
	}
	
	// called in Safelet.initializeApplication()
	public void initSOConfigTable() {
		
		for (int i = 0; i < names.length; i++) {
			Configuration.SOnames.add(names[i]);
			Configuration.table.put(names[i], new SOConfig(values[i]));
		}		
	}

}
