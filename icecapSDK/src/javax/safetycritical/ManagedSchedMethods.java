package javax.safetycritical;

import javax.realtime.PriorityParameters;
import javax.realtime.memory.ScopeParameters;
import javax.realtime.ConfigurationParameters;

final class ManagedSchedMethods {

	static PriorityParameters getPriorityParameter(ManagedSchedulable target) {
		if (target instanceof ManagedEventHandler)
			return ((ManagedEventHandler) target).priority;
		else if (target instanceof ManagedThread)
			return ((ManagedThread) target).priority;
		else if (target instanceof ManagedLongEventHandler)
			return ((ManagedLongEventHandler) target).priority;
		else
			return null;
	}

	static ScjProcess getScjProcess(ManagedSchedulable target) {
		Process process = null;
		if (target instanceof ManagedEventHandler)
			process = ((ManagedEventHandler) target).process;
		else if (target instanceof ManagedThread)
			process = ((ManagedThread) target).process;
		else if (target instanceof ManagedLongEventHandler)
			process = ((ManagedLongEventHandler) target).process;

		return (ScjProcess) process;
	}

	static ScopeParameters getStorage(ManagedSchedulable target) {
		if (target instanceof ManagedEventHandler)
			return ((ManagedEventHandler) target).storage;
		else if (target instanceof ManagedThread)
			return ((ManagedThread) target).storage;
		else if (target instanceof ManagedLongEventHandler)
			return ((ManagedLongEventHandler) target).storage;
		else
			return null;
	}
	
	static ConfigurationParameters getConfig(ManagedSchedulable target) {
		if (target instanceof ManagedEventHandler)
			return ((ManagedEventHandler) target).config;
		else if (target instanceof ManagedThread)
			return ((ManagedThread) target).config;
		else if (target instanceof ManagedLongEventHandler)
			return ((ManagedLongEventHandler) target).config;
		else
			return null;
	}

	private static ScjProcess createScjProcess(ManagedSchedulable target, int[] ps) {
		if (target instanceof PeriodicEventHandler) {
			return new ScjPeriodicEventHandlerProcess(target, ps);
		} else if (target instanceof OneShotEventHandler) {
			return new ScjOneShotEventHandlerProcess(target, ps);
		} else if (target instanceof MissionSequencer) {
			return new ScjMissionSequencerProcess(target, ps);
		} else if (target instanceof AperiodicEventHandler) {
			return new ScjAperiodicEventHandlerProcess(target, ps);
		} else if (target instanceof AperiodicLongEventHandler) {
			return new ScjAperiodicLongEventHandlerProcess(target, ps);
		} else if (target instanceof ManagedThread) {
			return new ScjManagedThreadProcess(target, ps);
		} else {
			return null;
		}
	}

	static ScjProcess createScjProcess(ManagedSchedulable target) {
		//return createScjProcess(target, new int[(int) getStorage(target).configurationSizes[0]]);
		return createScjProcess(target, new int[(int) (getConfig(target)).getSizes()[0]]);
	}

	static Mission getMission(ManagedSchedulable target) {
		if (target instanceof ManagedEventHandler)
			return ((ManagedEventHandler) target).mission;
		else if (target instanceof ManagedThread)
			return ((ManagedThread) target).mission;
		else if (target instanceof ManagedLongEventHandler)
			return ((ManagedLongEventHandler) target).mission;
		else
			return null;
	}

	static boolean isRegistered(ManagedSchedulable target) {
		if (target instanceof ManagedEventHandler) {
			System.out.println("ManagedSchedMethods. isRegistered: " + target + "; " + ((ManagedEventHandler) target).isRegistered);
			return ((ManagedEventHandler) target).isRegistered;
		}			
		else if (target instanceof ManagedThread)
			return ((ManagedThread) target).isRegistered;
		else if (target instanceof ManagedLongEventHandler) {
			System.out.println("ManagedSchedMethods. isRegistered: " + target + "; " + ((ManagedLongEventHandler) target).isRegistered);
			return ((ManagedLongEventHandler) target).isRegistered;
		}
		else {
			System.out.println("ManagedSchedMethods. is not Registered: " + target);
			return false;
		}
	}

	static boolean isInMissionScope(ManagedSchedulable target) {
		if (target instanceof ManagedEventHandler){
			//System.out.println("ManagedSchedMethods. isInMissionScope: " + target + "; " + ((ManagedEventHandler) target).isInMissionScope);
			return ((ManagedEventHandler) target).isInMissionScope;
		}
		else if (target instanceof ManagedThread)
			return ((ManagedThread) target).isInMissionScope;
		else if (target instanceof ManagedLongEventHandler)
			return ((ManagedLongEventHandler) target).isInMissionScope;
		else
			return false;
	}
}
