/**************************************************************************
 * File name  : TestSCJMemoryModel1.java
 * 
 * This code is available under the license:
 * Creative Commons, http://creativecommons.org/licenses/by-nc-nd/3.0/
 * It is free for non-commercial use. 
 * 
 * VIA University College, Horsens, Denmark, 2014
 * Hans Soendergaard, hso@viauc.dk
 * 
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/

package test;

import javax.realtime.AperiodicParameters;
import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.VMTest;

public class TestSCJSingleMemoryModel1
{  
  private static class MyPEH extends PeriodicEventHandler {
    private int count = 0;
    private AperiodicEventHandler myAEH;

    public MyPEH(PriorityParameters priority, PeriodicParameters release, 
                 ScopeParameters storage, AperiodicEventHandler myAEH) {
      super(priority, release, storage, configParameters);
      this.myAEH = myAEH;
    }

    @Override
    public void handleAsyncEvent() {
      devices.Console.println("Mission --- MyPEH: " + count);
      count++;
      if (count == 5) {
        myAEH.release();
      }
    }
  }

  private static class MyAEH extends AperiodicEventHandler {
    private Mission m;

    public MyAEH(PriorityParameters priority, AperiodicParameters release, 
                 ScopeParameters storage, Mission m) {
      super(priority, release, storage, configParameters);
      this.m = m;
    }

    @Override
    public void handleAsyncEvent() {
      devices.Console.println("Mission --- AEH: terminate mission");
      m.requestTermination();
    }
  }
  
  private static class MyMission extends Mission {

    @Override
    protected void initialize() {
      AperiodicEventHandler myAEH = 
          new MyAEH(new PriorityParameters(10), new AperiodicParameters(new RelativeTime(50, 0,
          Clock.getRealtimeClock()), null), storageParameters_Handlers, this);
      myAEH.register();

      PeriodicEventHandler myPEH = 
          new MyPEH(new PriorityParameters(20), 
                    new PeriodicParameters(new RelativeTime(Clock.getRealtimeClock()), 
                                           new RelativeTime(1000, 0, Clock.getRealtimeClock())),
          storageParameters_Handlers, myAEH);
      myPEH.register();
    }

    @Override
    public long missionMemorySize() {
      return Const.MISSION_MEM;
    }
  }
  
  private static class MySequencer extends MissionSequencer {
    private MyMission mission;

    public MySequencer(PriorityParameters priority, ScopeParameters storage) {
      super(priority, storage, configParameters, "MySeq");
      // initialize missions here
      mission = new MyMission();
    }

    @Override
    protected MyMission getNextMission() {    
    
      if (mission.terminationPending())
      {
        devices.Console.println("\nMySeq.getNextMission:null"); 
        return null;
      }
      else
      {
        devices.Console.println("\nMySeq.getNextMission"); 
        return mission;
      }
    }
  }

  private static class MyApp implements Safelet{
    
    @Override
    public MissionSequencer getSequencer() {
      return new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
                             storageParameters_Sequencer);
    }

    @Override
    public long immortalMemorySize() {
      return Const.IMMORTAL_MEM; 
    }

    @Override
    public void initializeApplication(String[] args) {
        // TODO Auto-generated method stub        
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
  
  static ScopeParameters storageParameters_Sequencer;
  static ScopeParameters storageParameters_Handlers;
  static ConfigurationParameters configParameters;
  
  public static void main(String[] args) 
  {   
//    storageParameters_Sequencer = 
//        new ScopeParameters(
//            Const.OUTERMOST_SEQ_BACKING_STORE,
//            Const.IMMORTAL_MEM, 
//            Const.PRIVATE_MEM, 
//            Const.MISSION_MEM);
//    
//    storageParameters_Handlers = 
//        new ScopeParameters(
//            Const.PRIVATE_BACKING_STORE,
//            0, 
//            Const.PRIVATE_MEM, 
//            0);
	  
	storageParameters_Sequencer = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO		
	storageParameters_Handlers = new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0); // HSO
    
    configParameters = new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE });

    devices.Console.println("\n***** TestSCJMemoryModel1 main.begin ******************" );
    new LaunchLevel1(new MyApp());
    devices.Console.println("***** TestSCJMemoryModel1 main.end *******************");
    
    VMTest.markResult(false);
  }  
}
