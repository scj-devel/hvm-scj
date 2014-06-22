package vm;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import devices.CR16C.KT4585.CR16CInterruptDispatcher;
import devices.i86.I86InterruptDispatcher;

/* Add setScheduler(Scheduler sch)  */
public class ClockInterruptHandler implements InterruptHandler, ProcessLogic {
	private Process currentProcess;
	private vm.Scheduler scheduler;

	@IcecapCVar
	private static boolean hvmClockReady;

	@Override
	@IcecapCompileMe
	public void run() {
		while (true) {
			currentProcess = (Process) scheduler.getNextProcess();
			enable();
			handlerProcess.transferTo(currentProcess);
		}
	}

	@Override
	public void catchError(Throwable t) {
		devices.Console.println("Exception thrown from scheduler");
	}

	private Process handlerProcess;

	private ClockInterruptHandler(Scheduler scheduler, int[] stack) {
		setScheduler(scheduler);
		handlerProcess = new vm.Process(this, stack);
		handlerProcess.initialize();
	}

	@IcecapCompileMe
	@Override
	public void handle() {
		disable();
		currentProcess.transferTo(handlerProcess);
	}

	@IcecapCompileMe
	@Override
	public void disable() {
		hvmClockReady = false;

	}

	@IcecapCompileMe
	@Override
	public void enable() {
		hvmClockReady = true;
	}

	public static ClockInterruptHandler instance;

	@IcecapCompileMe
	public static void initialize(Scheduler scheduler, int[] stack) {
		switch (Architecture.architecture) {
		case Architecture.X86_64:
		case Architecture.X86_32:
			I86InterruptDispatcher.init();
			break;
		case Architecture.CR16_C:
			CR16CInterruptDispatcher.init();
			break;
		}
		instance = new ClockInterruptHandler(scheduler, stack);
	}

	public void yield() {
		handle();
	}

	@Override
	public void register() {
		InterruptDispatcher
				.registerHandler(this, InterruptDispatcher.HVM_CLOCK);
	}

	public void startClockHandler(Process process) {
		this.currentProcess = process;
	}

	private static vm.Scheduler currentScheduler;

	public void setScheduler(Scheduler sch) {
		this.scheduler = sch;
		if (currentScheduler == sch)
		{
			/* Hack to force inclusion of service methods */
			/* This code should never actually be executed */
			Monitor.notify(null);
			Monitor.wait(null);
		}
		currentScheduler = sch;
	}

	public static vm.Scheduler getCurrentScheduler() {
		return currentScheduler;
	}
}