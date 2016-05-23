package javax.safetycritical;

import vm.Memory;

class ScjAperiodicEventHandlerProcess extends ScjProcess {

	ScjAperiodicEventHandlerProcess(ManagedSchedulable handler, int[] stack) {
		super(handler, stack);
	}

	protected void gotoNextState(PriorityFrame pFrame) {
		if (state == ScjProcess.State.HANDLED) {
			Mission m = Mission.getMission();
			// AperiodicEventHandler finished handling
			if (m.terminationPending()) {
				m.removeMSObject(msObject);

				// remove the rest of aperiodic handlers
				m.removeAperiodicHandlers();

				state = ScjProcess.State.TERMINATED;
			} else {
				// block it and can be released again
				state = ScjProcess.State.BLOCKED;
			}
			return;
		} else if (state == ScjProcess.State.WAITING) {
			;
		} else if (state == ScjProcess.State.REQUIRELOCK) {
			;
		} else {
			state = ScjProcess.State.READY;
			pFrame.readyQueue.insert(this);
		}
	}

	void switchToPrivateMemArea() {
		Memory.switchToArea(((ManagedEventHandler) msObject).privateMemory.getDelegate());
	}
}
