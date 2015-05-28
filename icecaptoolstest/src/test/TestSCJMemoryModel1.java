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
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.safetycritical.AperiodicEventHandler;
import javax.safetycritical.LaunchLevel1;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class TestSCJMemoryModel1
{  
  private static class MyPEH extends PeriodicEventHandler {
    private int count = 0;
    private AperiodicEventHandler myAEH;

    public MyPEH(PriorityParameters priority, PeriodicParameters release, 
                 StorageParameters storage, AperiodicEventHandler myAEH) {
      super(priority, release, storage);
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
                 StorageParameters storage, Mission m) {
      super(priority, release, storage);
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
  
  private static class MySequencer extends MissionSequencer<Mission> {
    private Mission mission;

    public MySequencer(PriorityParameters priority, StorageParameters storage) {
      super(priority, storage, "MySeq");
      // initialize missions here
      mission = new MyMission();
    }

    @Override
    protected Mission getNextMission() {    
    
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

  private static class MyApp implements Safelet<Mission>{
    
    @Override
    public MissionSequencer<Mission> getSequencer() {
      return new MySequencer(new PriorityParameters(Priorities.SEQUENCER_PRIORITY),
                             storageParameters_Sequencer);
    }

    @Override
    public long immortalMemorySize() {
      return Const.IMMORTAL_MEM; 
    }

    @Override
    public void initializeApplication() {
        // TODO Auto-generated method stub        
    }
  }
  
  static StorageParameters storageParameters_Sequencer;
  static StorageParameters storageParameters_Handlers;
  
  public static void main(String[] args) 
  {   
    storageParameters_Sequencer = 
        new StorageParameters(
            Const.OUTERMOST_SEQ_BACKING_STORE,
            new long[] { Const.HANDLER_STACK_SIZE },
            Const.PRIVATE_MEM, 
            Const.IMMORTAL_MEM, 
            Const.MISSION_MEM);
    
    storageParameters_Handlers = 
        new StorageParameters(
            Const.PRIVATE_BACKING_STORE, 
            new long[] { Const.HANDLER_STACK_SIZE },
            Const.PRIVATE_MEM, 
            0, 
            0);

    devices.Console.println("\n***** TestSCJMemoryModel1 main.begin ******************" );
    new LaunchLevel1(new MyApp());
    devices.Console.println("***** TestSCJMemoryModel1 main.end *******************");
    
    args = null;
  }  
}
