package test.same70.examples;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.PriorityScheduler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;


public class SCJLevel1 implements Safelet {

	// --- Stub classes ----------------------------------
	
	class MissionSequencerStub1 extends MissionSequencer
	{
	    private MissionStub1 miss;
	    private int activation = 0;
	    
	    public MissionSequencerStub1()
	    {
		  super (new PriorityParameters (PriorityScheduler.instance().getMinPriority()+1), 
	               new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0),
	               new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE })); 
		  
	      miss = new MissionStub1();
	    }
	    
	    public MissionStub1 getNextMission() 
	    {      
	      if (activation >= 1)
	      {
	        return null;
	      }
	      else
	      {
	    	System.out.println("*** MissionSequencerStub1.getNextMission.activation is " + activation);
	    	activation++;
	        return miss;
	      }
	    }
	}

	class MissionStub1 extends Mission {
		
		public void initialize() {
			
			ScopeParameters storageParameters_Handlers = 
				new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0);
	        
	        PeriodicEventHandler pevh = 
	        	new PeriodicEvhStub1(
	        			new PriorityParameters(Priorities.PR97), 
	             		new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
					                           new RelativeTime(200, 0, Clock.getRealtimeClock())), 
			            storageParameters_Handlers,
			            "PeriodicEvhStub1");  
	        pevh.register();
	        
	        System.out.println("*** MissionStub1.initialized");
	    }

	    public long missionMemorySize() {
	        return Const.MISSION_MEM;
	    }
	}
	
	class PeriodicEvhStub1 extends PeriodicEventHandler
	{
	    int count = 0;
	    
	    public PeriodicEvhStub1(PriorityParameters priority, 
	            PeriodicParameters periodicParameters, 
	            ScopeParameters storage,
	            String name) 
	    {
	        super(priority, periodicParameters, storage, 
	        	  new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }), 
	        	  name);
	    }
	    
	    public void handleAsyncEvent() 
	    {
	      System.out.println(this.getName() + " ==> " + count++);
	      
	      if (count > 3)
	    	  Mission.getMission().requestTermination();
	    }
	  } 
	// --- Stub classes end  ----------------------------------
	
	// --- Safelet methods begin ------------------------------
	
	@Override
	public MissionSequencer getSequencer() {
		System.out.println("\n*** SCJLevel1.getSequencer ...");
		
		return new MissionSequencerStub1();
	}

	@Override
	public long managedMemoryBackingStoreSize() {
		return Const.IMMORTAL_MEM_DEFAULT;
	}

	@Override
	public long immortalMemorySize() {
		return Const.IMMORTAL_MEM;
	}

	@Override
	public void initializeApplication(String[] args) {
		devices.Console.println("SCJLevel1.initializeApplication");
	}

	@Override
	public boolean handleStartupError(int cause, long val) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cleanUp() {
		devices.Console.println("SCJLevel1.cleanUp called");
	}

}
