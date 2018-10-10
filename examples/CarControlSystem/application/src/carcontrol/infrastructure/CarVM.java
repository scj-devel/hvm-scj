package carcontrol.infrastructure;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.realtime.TestPortalRT;
import javax.safetycritical.LauncherTCK;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;
import javax.scj.util.SCJErrorReporter;

import carcontrol.test.Test;
import carcontrol.infrastructure.VMConfiguration;
import carcontrol.configuration.Configuration;
import vm.VMTest;

public class CarVM extends Adapter {	

	private static UncaughtExceptionHandler uh;
	private static SCJErrorReporter scjh = new SCJErrorReporterVM();
	
	protected CarVM() {
		TestPortalRT.setupVM();	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Class app) {
		
		SingleTCKTest tester = new SingleTCKTest(app);
		try {
			tester.performTest();
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public Test launch (Level level, Class<? extends Safelet> app) {
		System.out.println("\nCarVM.launch begin ");
		
		Safelet testApp = null;
		try {
			LauncherTCK launcher = new LauncherTCK(level, app);
			
			System.out.println("\n*** CarVM.launch: launcher is: " + launcher);
			
			if (VMConfiguration.CarTest)
			  testApp = launcher.getTestApplication();
		}		
	    catch (Throwable e) {
	    	throw e;
	    }
		System.out.println("\n*** CarVM.launch end: " + testApp);
		
		return (Test)testApp;
	}
	
	@Override
	public void markResult(int errorCount) {
		 VMTest.markResult(errorCount > 0);
	}
	
	@Override
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler h) {
		javax.scj.util.Const.setErrorReporter(scjh);
		uh = h;
	}
	
	// --- SCJErrorReporter methods --------------------------------------------------------------------
	
	/**
	 * Called when a SCJ process underlying the handlers or real-time threads throws an unhandled exception or error.
	 * The <code>Throwable</code> is added to the test result.
	 */	
	private static class SCJErrorReporterVM implements SCJErrorReporter {

		@Override
		public final void processExecutionError(Throwable e) { 
			System.out.println("HSO CarVM.SCJErrorReporterVM.processExecutionError: \n" + e);
			uh.uncaughtException(null, e);
		}

		@Override
		public final void processOutOfMemoryError(OutOfMemoryError o) {
			System.out.println("HSO CarVM.SCJErrorReporterVM.processOutOfMemoryError: \n" + o);
			uh.uncaughtException(null, o);		
		}

		@Override
		public final void schedulerError(Throwable t) {
			System.out.println("HSO CarVM.SCJErrorReporterVM.schedulerError: \n" + t);
			uh.uncaughtException(null, t);
		}

		@Override
		public String processOutOfBackingStoreError(int start, int end, int left) {
			System.out.println("HSO CarVM.SCJErrorReporterVM.processOutOfBackingStoreError \n");
            uh.uncaughtException(null, null);    		
    		return null;
		}
	}
	
}


