package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560SCJTargetConfiguration;
import icecaptools.IcecapCompileMe;
import vm.Process;
import vm.ProcessLogic;

public class TestExecuteWithStack extends ATMega2560SCJTargetConfiguration {

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
		failed = true;

		TestObject testObj = new TestObject();
		testObj.start();
		
		if (!failed) {
			blink(16000);
		} else {
			blink(1000);
		}
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}

	@Override
	public int getJavaHeapSize() {
		return 4049;
	}
}
