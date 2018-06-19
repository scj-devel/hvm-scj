package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AperiodicLongEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.LauncherTCK;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.PriorityScheduler;
import javax.safetycritical.Safelet;
import javax.safetycritical.annotate.Level;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleAperiodicLongEvh1 {
	
	private static int testCount;
	
	static {
		testCount = 0;
	}

	static class AperiodicLongEvhStub1 extends AperiodicLongEventHandler {

		public AperiodicLongEvhStub1(PriorityParameters priority,
				AperiodicParameters release,
				ScopeParameters storageParameters,
				ConfigurationParameters config) {
			super(priority, release, storageParameters, config);
		}

		public void handleAsyncEvent(long data) {			
			System.out.println("***** AperiodicLongEvhStub1.handleAsyncEvent: data = " + data);
			System.out.println("***** AperiodicLongEvhStub1.handleAsyncEvent: release data = " + super.aperiodicLongData);
			Mission.getMission().requestTermination();
		}
	}
	
	static class PeriodicEvhStub1 extends PeriodicEventHandler
	{
	    AperiodicLongEventHandler apevh;
	    
	    public PeriodicEvhStub1(PriorityParameters priority, 
	            PeriodicParameters periodicParameters, 
	            ScopeParameters storage,
	            String name,
	            AperiodicLongEventHandler apevh) 
	    {
	        super(priority, periodicParameters, storage, 
	        	  new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }), 
	        	  name);
	        this.apevh = apevh;
	    }
	    
	    public void handleAsyncEvent() 
	    {
	      System.out.println(this.getName() + " ==> " + testCount++);
	      
	      if (testCount > 3)
	    	  apevh.release(123);
	    }
	} 

	static class MissionStub1 extends Mission {
		
		public void initialize() {
			
			ScopeParameters storageParameters_Handlers = 
				new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0);
			ConfigurationParameters configParameters = 
				new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });
		    
			AperiodicLongEventHandler apevh = 
	        	new AperiodicLongEvhStub1(
	        		new PriorityParameters(Priorities.PR98), 
	        		new AperiodicParameters(),
		            storageParameters_Handlers,
		            configParameters);  
	        apevh.register();
	        
	        PeriodicEventHandler pevh = 
	        	new PeriodicEvhStub1(
	        			new PriorityParameters(Priorities.PR97), 
	             		new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
					                           new RelativeTime(200, 0, Clock.getRealtimeClock())), 
			            storageParameters_Handlers,
			            "PeriodicEvhStub1",
			            apevh);  
	        pevh.register();
	        
	        System.out.println("*** MissionStub1.initialized");
	    }

	    public long missionMemorySize() {
	        return Const.MISSION_MEM;
	    }
	}
	
	static class MissionSequencerStub1 extends MissionSequencer
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
	      if (activation >= 1)  {
	        return null;
	      }
	      else  {
	    	System.out.println("*** MissionSequencerStub1.getNextMission.activation is " + activation);
	    	activation++;
	        return miss;
	      }
	    }
	}

	private static class MyApp implements Safelet {
		
		public MissionSequencer getSequencer() {
			MissionSequencer seq = new MissionSequencerStub1();
			System.out.println("MyApp.getSequencer: " + seq);
			return seq;
		}

		public long immortalMemorySize() {
			return Const.IMMORTAL_MEM;
		}
		public void initializeApplication(String[] args) {
		}		
		public long managedMemoryBackingStoreSize() {
			return 0;
		}		
		public final boolean handleStartupError(int cause, long val) {
			return false;
		}		
		public void cleanUp() {
		}
	}

	public static void main(String[] args) {

		System.out.println("***** TestSCJSingleAperiodicLongEvh1 begin *****");
		
		new LaunchLevel1(new MyApp());  // original: works
		
		//new LauncherTCK(Level.LEVEL_1, TestSCJSingleAperiodicLongEvh1.MyApp.class);  // using Launcher version with .class; works
		
		System.out.println("***** TestSCJSingleAperiodicLongEvh1 end *****; testCount = " + testCount);
		if (testCount == 5) {
			VMTest.markResult(false);
		}
	}
}