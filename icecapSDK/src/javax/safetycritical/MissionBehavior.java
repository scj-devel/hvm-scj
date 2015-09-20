package javax.safetycritical;

abstract class MissionBehavior {
	
	abstract Mission getMission();

	abstract boolean requestTermination(Mission mission);

	abstract void runInitialize(Mission mission);

	abstract void runExecute(Mission mission);

	abstract void runCleanup(Mission mission, MissionMemory missMem);

	abstract Process getProcess(int index);

	abstract ManagedSchedulable getManageSched(int index);

}


