package test.same70.examples;

import javax.realtime.Clock;
import javax.realtime.ConfigurationParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RelativeTime;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.Frame;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.PriorityScheduler;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.Priorities;

public class SCJLevel0 implements Safelet {

	// --- Stub classes ----------------------------------
	
	class SequencerStub extends MissionSequencer {
		private CyclicExecutiveStub mission;

		SequencerStub() {
			super(new PriorityParameters(PriorityScheduler.instance().getMinPriority() + 1),
					new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0), 
					new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
			mission = new CyclicExecutiveStub();
		}

		public CyclicExecutiveStub getNextMission() {
			if (mission.terminationPending())
				return null;
			else
				return mission;
		}
	}

	class CyclicExecutiveStub extends CyclicExecutive {
		public void initialize() {
			int NOR_PRIORITY = Priorities.NORM_PRIORITY;

			new PeriodicEvhStub1(new PriorityParameters(NOR_PRIORITY), 
					new PeriodicParameters(new RelativeTime(), // start  
					    new RelativeTime(2000, 0)), // period 
					new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0),
					new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE })).
			register();
			
			new PeriodicEvhStub2(new PriorityParameters(NOR_PRIORITY), 
					new PeriodicParameters(new RelativeTime(), // start  
					    new RelativeTime(4000, 0)), // period 
					new ScopeParameters(Const.PRIVATE_MEM, 0, 0, 0),
					new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE })).
			register();
		}

		public long missionMemorySize() {
			return Const.MISSION_MEM;
		}

		public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
			RelativeTime minorCycle = new RelativeTime(1000, 0, Clock.getRealtimeClock());

			Frame[] frames = new Frame[4];
			PeriodicEventHandler[] frame0 = new PeriodicEventHandler[2];
			PeriodicEventHandler[] frame1 = new PeriodicEventHandler[1];
	        PeriodicEventHandler[] frame2 = new PeriodicEventHandler[1];
	        PeriodicEventHandler[] frame3 = new PeriodicEventHandler[0]; // empty
			
	        frame0[0] = handlers[0]; frame0[1] = handlers[1];
	        frame1[0] = handlers[1];
	        frame2[0] = handlers[0];

			frames[0] = new Frame(minorCycle, frame0);
			frames[1] = new Frame(minorCycle, frame1);
			frames[2] = new Frame(minorCycle, frame2);
			frames[3] = new Frame(minorCycle, frame3);
			
			return new CyclicSchedule(frames);
		}
	}

	class PeriodicEvhStub1 extends PeriodicEventHandler {
		
		int count = 0;
		
		public PeriodicEvhStub1(PriorityParameters priority,PeriodicParameters periodic, 
				ScopeParameters storage, ConfigurationParameters configParameters) {
			super(priority, periodic, storage, configParameters);
		}

		public void handleAsyncEvent() {
			devices.Console.println("pevh 1; count: " + count);
			count++;
			if (count > 2)
				Mission.getMission().requestTermination();
		}
	}
	
	class PeriodicEvhStub2 extends PeriodicEventHandler {

		protected PeriodicEvhStub2(PriorityParameters priority, PeriodicParameters periodic, 
				ScopeParameters storage, ConfigurationParameters configParameters) {
			super(priority, periodic, storage, configParameters);
		}

		public void handleAsyncEvent() {
			devices.Console.println("pevh 2");
		}
	}

	// --- Stub classes end  ----------------------------------
	
	// --- Safelet methods begin ------------------------------
	
	@Override
	public MissionSequencer getSequencer() {
		MissionSequencer seq = new SequencerStub();
		return seq;
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
		devices.Console.println("SCJLevel0.initializeApplication");
	}

	@Override
	public boolean handleStartupError(int cause, long val) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

}
