package javax.safetycritical;

import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

public class LinearMissionSequencer<MissionType extends Mission> extends MissionSequencer<MissionType> {

	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, StorageParameters storage, 
			ConfigurationParameters config, 
			boolean repeat,
			MissionType mission,
			String name)
			throws IllegalArgumentException, IllegalStateException {
		
		super(priority, storage, config, name);
		
		// ToDo
	}
	
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, StorageParameters storage, 
			ConfigurationParameters config, 
			boolean repeat,
			MissionType mission)
			throws IllegalArgumentException, IllegalStateException {
		
		this(priority, storage, config, repeat, mission, "LinearMS");
	}
	
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, StorageParameters storage, 
			ConfigurationParameters config, 
			boolean repeat,
			MissionType[] missions,
			String name)
			throws IllegalArgumentException, IllegalStateException {
		
		super(priority, storage, config, name);
		
		// ToDo
	}
	
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, StorageParameters storage, 
			ConfigurationParameters config, 
			boolean repeat,
			MissionType[] missions)
			throws IllegalArgumentException, IllegalStateException {
		
		this(priority, storage, config, repeat, missions, "LinearMS");
		
	}
	
	@SCJAllowed(Level.SUPPORT)
	protected final MissionType getNextMission() {
		// ToDo
		return null;
	}
}



