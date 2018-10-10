package carcontrol.util;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.Mission;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.PriorityScheduler;
import javax.scj.util.Const;

/**
 * A mission sequencer without any missions.
 */
public class NullSequencer extends MissionSequencer {

	public NullSequencer() {
		super(new PriorityParameters(PriorityScheduler.instance().getMinPriority() + 1),
				
//				Const.StorageSequencer,
//				Const.ConfigSequencer);
		
				new ScopeParameters(Const.OUTERMOST_SEQ_BACKING_STORE, 
						Const.IMMORTAL_MEM, Const.PRIVATE_MEM, Const.MISSION_MEM), 
				new ConfigurationParameters (-1, -1, new long[] { Const.HANDLER_STACK_SIZE }));
	}
	
	@Override
	public Mission getNextMission() {
		System.out.println ("NullSequencer.getNextMission: returns null");
		return null;
	}

	public void cleanUp() {			
	}
}
