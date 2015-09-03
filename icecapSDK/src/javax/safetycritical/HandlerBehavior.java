package javax.safetycritical;

import javax.realtime.HighResolutionTime;

abstract class HandlerBehavior {

	abstract void aperiodicHandlerRelease(AperiodicEventHandler handler);

	abstract boolean oneshotHandlerDeschedule(OneShotEventHandler handler);

	abstract void oneshotHandlerScheduleNextReleaseTime(OneShotEventHandler handler, HighResolutionTime time);

	abstract void initMissionSequencer(MissionSequencer<?> handler);

	abstract void cleanOuterMissionSequencer(MissionSequencer<?> handler);

	abstract void missionSequencerSingleTermination(MissionSequencer<?> handler);

	abstract void missionSequencerExecutePhase(MissionSequencer<?> handler);
	
}

