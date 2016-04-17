package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70SCJTargetConfiguration;
import icecaptools.IcecapCompileMe;
import vm.Process;
import vm.ProcessLogic;

public class TestExecuteWithStack extends ATSAMe70SCJTargetConfiguration {

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
			blink(20000);
		} else {
			blink(10000);
		}
	}

	@Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}
}
