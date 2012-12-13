package javax.safetycritical;

import icecaptools.IcecapCompileMe;
import vm.ProcessSequencer;
import vm.VM;

class Monitor {
	private int ceiling;
	private int synchCounter;
	private int priority;
	private ManagedEventHandler owner;
	private ProcessSequencer sequencer;
	
	public Monitor(int ceiling) {
		this.ceiling = ceiling;
		sequencer = VM.getProcessSequencer();
	}

	@IcecapCompileMe
	void lock() {
		sequencer.disable();
		ManagedEventHandler handler = PriorityScheduler.instance().current.target;
		if (owner == null) {
			owner = handler;
		}

		if (owner == handler) {
			synchCounter++;
			if (synchCounter == 1) {
				priority = handler.priority.getPriority();
				handler.priority.setPriority(max(priority, ceiling) + 1);
			}
		} else {
			sequencer.enable();
			throw new IllegalMonitorStateException("Not owner on entry");
		}
		sequencer.enable();
	}

	@IcecapCompileMe
	void unlock() {
		sequencer.disable();
		ManagedEventHandler handler = PriorityScheduler.instance().current.target;
		if (owner == handler) {
			synchCounter--;
			if (synchCounter == 0) {
				owner = null;
				handler.priority.setPriority(priority);
				sequencer.enable();
				VM.yield();
			}
		}
		else
		{
			sequencer.enable();
			throw new IllegalMonitorStateException("Not owner on exit");
		}
	}

	private static int max(int x, int y) {
		if (x > y)
			return x;
		else
			return y;
	}
}
