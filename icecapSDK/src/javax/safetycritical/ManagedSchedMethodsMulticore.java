package javax.safetycritical;

public class ManagedSchedMethodsMulticore {

	static void executeManagedSchedulable(ManagedSchedulable ms) {
		if (ms instanceof ManagedThread) {
			runManagedThread((ManagedThread) ms);
		} else if (ms instanceof MissionSequencer) {
			runMissionSequencer((MissionSequencer<?>) ms);
		} else if (ms instanceof PeriodicEventHandler) {
			runPeriodicHandler((PeriodicEventHandler) ms);
		} else if (ms instanceof AperiodicEventHandler) {
			runAperiodicEventHandler((AperiodicEventHandler) ms);
		} else if (ms instanceof OneShotEventHandler) {
			runOneShotHandler((OneShotEventHandler) ms);
		} else {
			devices.Console.println("ManagedLongEventHandler not implemented");
		}
	}

	private static void runPeriodicHandler(PeriodicEventHandler handler) {
		handler.privateMemory.enter(handler);
		if (handler.mission.terminationPending()) {
			handler.mission.currMissSeq.seqNotify();
			OSProcess.requestTermination_c(handler.process.executable);
			OSProcess.testCancel_c();
		}
	}

	private static void runAperiodicEventHandler(AperiodicEventHandler handler) {
		if (handler.mission.terminationPending()) {
			handler.mission.currMissSeq.seqNotify();
			OSProcess.requestTermination_c(handler.process.executable);
			OSProcess.testCancel_c();
		}

		handler.waitForNextRelease();

		//		if (handler.mission.terminationPending()) {
		//			handler.mission.currMissSeq.decrementActiveCount();
		//			OSProcess.requestTermination_c(handler.osProcess.executable);
		OSProcess.testCancel_c();
		//		}

		handler.privateMemory.enter(handler);
	}

	private static void runOneShotHandler(OneShotEventHandler handler) {
		handler.state = 1;

		if (!handler.deschedulePending) {
			handler.privateMemory.enter(handler);
		}
		handler.deschedulePending = false;

		if (handler.mission.terminationPending()) {
			handler.mission.currMissSeq.seqNotify();
			OSProcess.requestTermination_c(handler.process.executable);
			OSProcess.testCancel_c();
		}

		handler.state = 2;
		handler.waitForNextRelease();

		//		if (handler.mission.terminationPending()) {
		//			handler.mission.currMissSeq.decrementActiveCount();
		//			OSProcess.requestTermination_c(handler.osProcess.executable);
		OSProcess.testCancel_c();
		//		}

		handler.state = 0;
	}

	private static void runMissionSequencer(MissionSequencer<?> ms) {
		ms.privateMemory.enter(ms);
		ms.mission.currMissSeq.seqNotify();
	}

	private static void runManagedThread(ManagedThread thread) {
		thread.privateMemory.enter(thread);
		thread.mission.currMissSeq.seqNotify();
	}
}
