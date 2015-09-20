package javax.safetycritical;

import javax.realtime.AbsoluteTime;
import javax.realtime.HighResolutionTime;
import javax.realtime.RelativeTime;

final class MulticoreHandlerBehavior extends HandlerBehavior {
	@Override
	void aperiodicHandlerRelease(AperiodicEventHandler handler) {
		handler.fireNextRelease();
		handler.isReleased = true;
	}

	@Override
	boolean oneshotHandlerDeschedule(OneShotEventHandler handler) {
		if (handler.process.executable.startTimer_c > 0 || handler.state == 0) {
			handler.deschedulePending = true;
			OSProcess.setTimerfd(handler.process.executable.startTimer_c, 0);
			return false;
		} else {
			return true;
		}
	}

	@Override
	void oneshotHandlerScheduleNextReleaseTime(OneShotEventHandler handler, HighResolutionTime time) {
		if (time == null)
			handler.deschedule();
		else {
			if (time instanceof AbsoluteTime) {
				handler.releaseTime = new RelativeTime(0, 0);
			} else if (time instanceof RelativeTime) {
				if (handler.releaseTime.getMilliseconds() < 0
						|| (handler.releaseTime.getMilliseconds() == 0 && handler.releaseTime.getNanoseconds() < 0))
					throw new IllegalArgumentException("release time < 0");
				handler.releaseTime = time;
			} else {
				throw new IllegalArgumentException("wrong time form");
			}

			if (handler.state == 0) {
				OSProcess.setTimerfd(handler.process.executable.startTimer_c, handler.getStart());
			}
			if (handler.state == 2) {
				OSProcess.setTimerfd(handler.process.executable.startTimer_c, handler.getStart());
				handler.fireNextRelease();
			}
		}

	}

	@Override
	void initMissionSequencer(MissionSequencer<?> handler) {
		if (MissionSequencer.isOuterMostSeq) {

			MissionSequencer.outerMostSeq = handler;
			MissionSequencer.isOuterMostSeq = false;

			OSProcess.setOuterMostMissionSequencer(handler.priority.getPriority());
			handler.set = Launcher.level == 1 ? findAffinitySetForLevel1()
					: AffinitySet.AFFINITY_SET[0];
		}

	}
	
	private AffinitySet findAffinitySetForLevel1() {
		int processor = OSProcess.getCurrentCPUID();
		for (int i = 0; i < AffinitySet.AFFINITY_SET.length; i++) {
			if (AffinitySet.AFFINITY_SET[i].processorSet[0] == processor) {
				return AffinitySet.AFFINITY_SET[i];
			}
		}
		throw new NullPointerException();
	}

	@Override
	void cleanOuterMissionSequencer(MissionSequencer<?> handler) {

	}

	@Override
	void missionSequencerSingleTermination(MissionSequencer<?> handler) {
		handler.terminateSeq = true;
		handler.currMission.requestTermination();
	}

	@Override
	void missionSequencerExecutePhase(MissionSequencer<?> handler) {
		handler.missionMemory.enterToExecute(handler.currMission);
	}
}


