package carcontrol.test;

import javax.safetycritical.MissionSequencer;
import javax.safetycritical.Safelet;
import javax.scj.util.Const;

import carcontrol.infrastructure.Adapter;

import java.lang.Thread.UncaughtExceptionHandler;

public abstract class Test implements Safelet, UncaughtExceptionHandler {
	
	TestResult testResult; 
	String testClassName;
	
	int testCount = 1;
	
	protected static Test testApp = null; // HSO
	
	
	public Test()  {		
		testResult = new TestResult(); //TestResult.instance();  //new TestResult();
		Adapter.instance().setUncaughtExceptionHandler(this);
	}
	
	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}
	
	public void setTestName(String testClassName) {
		this.testClassName = testClassName;
	}
	
	public String getTestName() {
		return this.testClassName;
	}
	
	public TestResult getTestResult() {
		return testResult;
	}
	
	/**
	 * The <code>report</code> method must be called by the test application as
	 * the very last step.
	 * 
	 * For tests of the initial infrastructure, it must be called from the 
	 * executing <code>RunTest</code> object.
	 * 
	 * For tests of passive classes, where the tests are run as part of <code>initializeApplication</code>,
	 * it must be called after the test runs in <code>initializeApplication</code>.
	 * 
	 * For tests of active classes, where one or more missions are active, it must be called as part of
	 * <code>Mission.cleanUp</code> for the last mission to execute.
	 */
	public int report() {
		System.out.println("\nTest.report:");
		
		testResult.reportTest(testClassName, testCount);		
		return testResult.errorCount();
	}
	
	/**
	 * Returns a string representation of the test
	 */
	public String toString() {
	    return "(" + getClass().getName() + ")" ; 
	}
	
	/**
	 * <code>unCaughtException</code> is 
	 * called when a SCJ process underlying the handlers or real-time threads throws an exception or error not handled locally.
	 * The <code>Throwable</code> is added to the test result.
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("*** Test.uncaughtException: " + e);
		testResult.addError(this, e);
	}
	
	// --- Safelet methods ----------------------------------------
	
	/**
	 * May be overridden by test applications..
	 */
	@Override
	public void initializeApplication(String[] args) {
	}
	
	/**
	 * May be replaced by a proper mission sequencer in a test application
	 */
	@Override
	public MissionSequencer getSequencer() {
		return null;
	}
	
	/**
	 * Sets a standard size. May be overridden by test applications.
	 */
	@Override
	public long immortalMemorySize() {
		System.out.println("HSO Test.immortalMemorySize called ");
		
		// In our HVM setup, we have in HVM.initMemory():
		//   Const.IMMORTAL_MEM = 3 * Const.IMMORTAL_MEM_DEFAULT; 
		// So, Const.IMMORTAL_MEM - Const.IMMORTAL_MEM_DEFAULT should be plenty for our tests
		
		return Const.IMMORTAL_MEM - Const.IMMORTAL_MEM_DEFAULT;
	}
	
	/**
	 * @return   the amount of additional backing store memory.
	 */
	@Override
	public long managedMemoryBackingStoreSize() {
		//System.out.println("HSO Test.managedMemoryBackingStoreSize called ");
		return Const.IMMORTAL_MEM_DEFAULT;
	}
		
	@Override
	public boolean handleStartupError(int cause, long val) {
		
		testResult.addError(this, new Error("InfraStructure fails " + cause + " " + val));
		report();
		return false;
	}

	@Override
	public void cleanUp() {
		System.out.println("HSO Test.cleanUp called ");
				
		//int errorCount = testApp == null ? 1 : testApp.report();
		
		int errorCount = 1;
		if (testApp != null) {
		  testApp.report();
		  errorCount = 0;
		}
		Adapter.instance().markResult (errorCount);
	}

}
