package test.same70.examples;


import test.same70.configuration.SCJTargetConfigurationSAME;
import icecaptools.IcecapCompileMe;
import vm.Machine;
import vm.MachineFactory;
import vm.Process;
import vm.ProcessLogic;

public class ExecuteWithStackAtSAME extends SCJTargetConfigurationSAME {

	private static boolean failed;
	private static Process p;
	private static Process mainProcess;

	private static class TestObject {
		byte x;

		ProcessExecutor processExecuter;

		private static class ProcessExecutor implements ProcessLogic {
			TestObject thisObject;

			ProcessExecutor(TestObject thisObject) {
				this.thisObject = thisObject;
			}

			@Override
			@IcecapCompileMe
			public void run() {
				if (thisObject.x == 42) {
					failed = false;
				} else {
				}
				p.transferTo(mainProcess);
			}

			@Override
			public void catchError(Throwable t) {
			}

		}

		TestObject() {
			processExecuter = new ProcessExecutor(this);

			x = 42;
		}

		public void start() {
			p = new Process(processExecuter, new int[getReasonableProcessStackSize()]);
			mainProcess = new vm.Process(null, null);
			p.initialize();
			mainProcess.transferTo(p);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();

		devices.Console.println("ExecuteWithStackAtSAME begin");
		
		MachineFactory mFac = Machine.getMachineFactory();		
		mFac.startSystemTick();
		
		blink (2000);
		
		failed = true;

		TestObject testObj = new TestObject();
		testObj.start();
		
		if (!failed) {
			devices.Console.println("not failed");
			blink(2000);
		} else {
			devices.Console.println("failed");
			blink(10000);
		}
		devices.Console.println("ExecuteWithStackAtSAME end");
	}
}
