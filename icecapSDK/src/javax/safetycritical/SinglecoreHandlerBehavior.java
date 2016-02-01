package javax.safetycritical;

import javax.realtime.HighResolutionTime;


final class SinglecoreHandlerBehavior extends HandlerBehavior {
	@Override
	void aperiodicHandlerRelease(AperiodicEventHandler handler) {
		PriorityScheduler.instance().release(handler);
	}

	@Override
	boolean oneshotHandlerDeschedule(OneShotEventHandler handler) {
		ManagedSchedulableSet hs = Mission.getMission().msSetForMission;

		if (hs.contains(handler)) {
			hs.removeMSObject(handler);
			return true;
		} else
			return false;

	}

	@Override
	void oneshotHandlerScheduleNextReleaseTime(OneShotEventHandler handler, HighResolutionTime time) {
		// to be implement
	}

	@Override
	void initMissionSequencer(MissionSequencer<?> handler) {
		if (MissionSequencer.isOuterMostSeq) {
			MissionSequencer.outerMostSeq = handler;
			if (Launcher.level != 0) {
				PriorityScheduler.instance().addOuterMostSeq(handler);
			}
		} else {
			if (Launcher.level < 2)
				throw new IllegalStateException("MissSeq not outer-most");
			else
				handler.outerSeq = Mission.getMission().currMissSeq;

		}
		MissionSequencer.isOuterMostSeq = false;
		/* Line below commented out - since cyclic sheduling tests fail */
		// handler.lock = Monitor.getMonitor(handler);
		//System.out.println("SinglecoreBehavior.initMissionSequencer");
	}

	@Override
	void cleanOuterMissionSequencer(MissionSequencer<?> handler) {
		if (Launcher.level == 2) {
			//devices.Console.println("MS.T: " + handler.name + "; #Missions: " + MissionSequencer.howManyMissions
			//		+ "; outerSeq: " + handler.outerSeq);

			vm.ClockInterruptHandler.instance.disable();
			if (handler.outerSeq != null)
				handler.outerSeq.currMission.msSetForMission.removeMSObject(handler);
			vm.ClockInterruptHandler.instance.enable();
		}
	}

	@Override
	void missionSequencerSingleTermination(MissionSequencer<?> handler) {
		vm.ClockInterruptHandler.instance.disable();
		//devices.Console.println("------ MS.signalTermination: " + handler.name);
		handler.terminateSeq = true;
		handler.currMission.requestTermination();
		vm.ClockInterruptHandler.instance.enable();
	}

	@Override
	void missionSequencerExecutePhase(MissionSequencer<?> handler) {
		handler.missionMemory.enterToExecute(handler.currMission);
		//System.out.println("SinglecoreBehavior.missionSequencerExecutePhase");
		// the ms will wait here until it is notified
		if (Launcher.level > 0) {
			handler.seqWait();
		} else {
			while (!handler.currMission.terminationPending() && handler.currMission.msSetForMission.msCount > 0) {
				vm.RealtimeClock.waitForNextTick();
			}
		}

		
	}
	
}


