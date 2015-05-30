package vm;

import reflect.ObjectInfo;
import icecaptools.IcecapCompileMe;

public class Process {
	private ProcessLogic logic;
	private int[] stack;
	private ProcessExecutor processExecuter;
	private SP sp;
	private int runtime_data;

	private static StackAnalyser stackAnalyser;
	
	private boolean isFinished;

	public boolean isFinished() {
		return isFinished;
	}

	@IcecapCompileMe
	public Process(ProcessLogic logic, int[] stack) {
		this.logic = logic;
		this.stack = stack;
		this.isFinished = false;
		processExecuter = new ProcessExecutor(this);
		
		sp = Machine.getMachineFactory().getProcessSP();
		
		if (stackAnalyser != null)
		{
			stackAnalyser.addStack(stack);
		}
	}

	@IcecapCompileMe
	public void transferTo(Process nextProcess) {
		transfer(this, nextProcess);
	}

	public final void initialize() {
		executeWithStack(processExecuter, stack);
	}

	private static native void transfer(Process currentProcess,
			Process nextProcess);

	private static native void executeWithStack(Runnable runnable,
			int[] schedulerStack);

	private static class ProcessExecutor implements Runnable {
		private Process thisProcess;
		private boolean isStarted;

		ProcessExecutor(Process thisProcess) {
			this.thisProcess = thisProcess;
		}

		@Override
		@IcecapCompileMe
		public void run() {
			isStarted = false;
			thisProcess.transferTo(thisProcess);
			if (!isStarted) {
				isStarted = true;
			} else {
				try {
					thisProcess.logic.run();
				} catch (Throwable t) {
					thisProcess.logic.catchError(t);
				}
				vm.ClockInterruptHandler.instance.yield();
				thisProcess.isFinished = true;
				for (;;)
					;
			}
		}
	}

	public int[] getStack() {
		return stack;
	}

	public short getJavaStackTop() {
		int top = sp.getJSP();
		return getIndex(top);
	}

	public int getCStackTop() {
		int top = sp.getCSP();
		return getIndex(top);
	}

	private short getIndex(int top) {
		return (short) ((top - ObjectInfo.getAddress(stack)) >> 2);
	}
	
	public static void enableStackAnalysis()
	{
		stackAnalyser = new FullStackAnanlyser();
	}
	
	public static void reportStackUsage()
	{
		if (stackAnalyser != null)
		{
			stackAnalyser.reportStackUsage();
		}
	}
}
