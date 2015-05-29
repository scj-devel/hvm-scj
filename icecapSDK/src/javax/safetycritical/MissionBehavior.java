package javax.safetycritical;

abstract class MissionBehavior {

	Mission[] missionSet = null;
	boolean isMissionSetInit = false;

	protected abstract Mission getMission();

	protected abstract boolean requestTermination(Mission mission);

	protected abstract void runInitialize(Mission mission);

	protected abstract void runExecute(Mission mission);

	protected abstract void runCleanup(Mission mission, MissionMemory missMem);

	protected abstract Process getProcess(int index);

	protected abstract ManagedSchedulable getManageSched(int index);
}
