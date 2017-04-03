package javax.safetycritical;

import java.util.Iterator;

import javax.safetycritical.annotate.Phase;
import javax.scj.util.Const;


final class SinglecoreMissionBehavior extends MissionBehavior {

	@Override
	Mission getMission() {
		Mission mission = null;

		if (Launcher.level == 0 && CyclicScheduler.instance().seq != null) {
			mission = CyclicScheduler.instance().seq.currMission;
		} else if (Launcher.level > 0 && PriorityScheduler.instance().getCurrentProcess() != null) {

			if (PriorityScheduler.instance().getCurrentProcess().getTarget() instanceof MissionSequencer) {
				mission = ((MissionSequencer) PriorityScheduler.instance().getCurrentProcess().getTarget()).currMission;
			} else {
				mission = ManagedSchedMethods.getMission(PriorityScheduler.instance().getCurrentProcess()
						.getTarget());
			}
		}
		return mission;
	}

	@Override
	boolean requestTermination(Mission mission) {
		if (mission.missionTerminate == false) { // called the first time during mission execution	

			mission.missionTerminate = true; // ensure that no further calls have effect
			
			// terminate all the sequencer's MSObjects that were created by the mission.
			Iterator<ManagedSchedulable> schedulables = mission.getManagedSchedulables();
			while (schedulables.hasNext()) {
				ManagedSchedulable ms = schedulables.next();
				if (ms != null) {
					ms.signalTermination();
				}
			}
			
			//System.out.println("SinglecoreMissionBehavior.requestTermination");

			return false;
		} else
			return true; // called more than once: nothing done
	}

	int addNewMission(Mission mission) {
		for (int i = 0; i < Mission.missionSet.length; i++) {
			if (Mission.missionSet[i] == null) {
				Mission.missionSet[i] = mission;
				return i;
			}
		}
		throw new IndexOutOfBoundsException("Mission set: too small");
	}

	@Override
	void runInitialize(Mission mission) {
		vm.ClockInterruptHandler.instance.disable();

		if (Mission.missionSet == null || Mission.isMissionSetInit == false) {
			Mission.missionSet = new Mission[Const.DEFAULT_HANDLER_NUMBER];
			mission.isMissionSetInitByThis = true;
			Mission.isMissionSetInit = true;
		}
		mission.missionIndex = addNewMission(mission);
		mission.gotoInitPhase();

		vm.ClockInterruptHandler.instance.enable();
	}

	@Override
	void runExecute(Mission mission) {
		vm.ClockInterruptHandler.instance.disable();

		mission.phaseOfMission = Phase.RUN;
		PriorityFrame frame = PriorityScheduler.instance().pFrame;

		int index = mission.missionIndex * 20;

		Iterator<ManagedSchedulable> schedulables = mission.getManagedSchedulables();
		
		while (schedulables.hasNext()) {

			ManagedSchedulable ms = schedulables.next();

			ScjProcess p = ManagedSchedMethods.createScjProcess(ms);
			
			p.setIndex(index);
			index++;
			frame.addProcess(p);
		}

		vm.ClockInterruptHandler.instance.enable();

	}

	@Override
	void runCleanup(Mission mission, MissionMemory missMem) {
		mission.phaseOfMission = Phase.CLEANUP;
		// wait until (all handlers in mission have terminated)	

		//			while (mission.msSetForMission.msCount > 0) {
		//				vm.RealtimeClock.awaitNextTick();
		//			}

		vm.ClockInterruptHandler.instance.disable();

		mission.deleteSchedulables();

		Mission.missionSet[mission.missionIndex] = null;
		if (mission.isMissionSetInitByThis == true) {
			Mission.isMissionSetInit = false;
		}
		mission.cleanUp();
		missMem.resetArea();
		vm.ClockInterruptHandler.instance.enable();
	}
}


