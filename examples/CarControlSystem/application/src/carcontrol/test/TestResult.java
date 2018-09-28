
package carcontrol.test;


/**
 * A <code>TestResult</code> collects the results of executing a test case.
 * It is an instance of the Collecting Parameter pattern. <br>
 *  
 * A JMLerror is anticipated and checked for with JML assertions. <br>
 * Errors are unanticipated problems like an
 * <code>ArrayIndexOutOfBoundsException</code>.<br>
 * 
 * The test framework does not distinguish between <i>JMLerros</i> and <i>errors</i>.
 * This emerges from the test report as demonstrated in the test example below with an JML assertion error<br>
 * 
 * <code> 
   Test.report:
   safetycritical.MissionTest1:
    Test cases:  1
    Test errors: 1
 
    Test errors are in:  (safetycritical.MissionTest1) 
 
    /home/hso/workspace/SafetyCriticalTCK/src/safetycritical/MissionTest1.java:76: JML postcondition is false
                        public void test (int i) 
                                    ^
    /home/hso/workspace/SafetyCriticalTCK/src/safetycritical/MissionTest1.java:69: Associated declaration: /home/hso/workspace/SafetyCriticalTCK/src/safetycritical/MissionTest1.java:76: 
                             ensures             
                             ^ 
   </code>
 * 
 * This <code>TestResult</code> class is a scaled down version for use in the TCK. 
 * 
 * It allocates room for the results in its declaration context.
 * For the TCK it is typically Immortal Memory.
 *  
 * There are no further allocations. If there are more than
 * <code>MAXERRORS</code> results, the list is terminated with  an entry
 * saying test case <code>null</code>.
 *  
 * @version 1.0; - January 2016.
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A> 
 */ 


public class TestResult
{
  /**
   * <code>TestFailure</code> contains a reference to a <code>TestCase</code> object, 
   * and the message part of the <code>Throwable</code> defining the failure. 
   * <code>caseNo</code> is the number case number in switch-statement in a <code>Passive</code> test.
   */
  private class TestFailure
  {
	  private static final int MAXTEXT = 200;
	  
	  protected Test fFailedTest;	  
	  protected StringBuilder fThrownException;
	  
	  protected int caseNo;

	  /**
	   * Constructs a TestFailure with the given test and exception.
	   */
//	  public TestFailure (Test failedTest, String thrownMessage)
//	  {
//	    fFailedTest = failedTest;
//	    fThrownException = new StringBuilder(MAXTEXT);
//	    fThrownException.replace(0, MAXTEXT-1, thrownMessage);
//	  }

	  public TestFailure (Test failedTest, String thrownMessage, int caseNo)
	  {
	    fFailedTest = failedTest;
	    fThrownException = new StringBuilder(MAXTEXT);
	    fThrownException.replace(0, MAXTEXT-1, thrownMessage);
	    
	    this.caseNo = caseNo;
	  }
	  /**
	   * prints a description of the failure.
	   */
	  public void print()
	  {
		System.out.print("\n  "); System.out.println(fFailedTest);  
		System.out.print("\n  "); 
		if (caseNo > 0) {
			System.out.print("case ");
			System.out.print(caseNo + ": ");
		}
		
		System.out.println(fThrownException);
	  }

	  public void insert(Test test, Throwable t) {
		fFailedTest = test;
		fThrownException.replace(0, MAXTEXT-1, t.getMessage());
	  }
	  
	  public void insert(Test test, Throwable t, int caseNo) {
		fFailedTest = test;
		fThrownException.replace(0, MAXTEXT-1, t.getMessage());
		this.caseNo = caseNo;
	  }
  }
  
  //static TestResult instance = null;
  
  private static final int MAXERRORS = 15;
  
  private static TestFailure[] fErrors;
  private static int fErrorCount;
  
  private void initialize(TestFailure[] test) {
	  for (int i = 0; i <= MAXERRORS; i++) 
		  test[i] = new TestFailure(null, "...", 0); 
  }

  public TestResult ()
  {   
    fErrors    = new TestFailure[MAXERRORS+1]; 
    initialize(fErrors); fErrorCount = 0;
  }
  
//  public static TestResult instance() {
//	  if (instance == null)
//		  instance = new TestResult ();
//	  return instance;
//  }

  /**
   * Adds an error to the list of errors. The passed in exception caused
   * the error.
   */
  public void addError (Test test, Throwable t) {
    if (fErrorCount < MAXERRORS) {
    	fErrors[fErrorCount++].insert(test, t);
    }
  }
  
  public void addError (Test test, Throwable t, int caseNo) {
    if (fErrorCount < MAXERRORS) {
    	fErrors[fErrorCount++].insert(test, t, caseNo);
    }
  }

  public int errorCount ()  {
    return fErrorCount;
  }

  public void reportTest(String testName, int numberOfCases) {
	System.out.print("  "); System.out.print(testName);
  	System.out.print(":\n    Test cases:  "); System.out.println("" + numberOfCases);
  	System.out.print("    Test errors: "); System.out.println("" + fErrorCount);
  	System.out.println();
  	
  	if (fErrorCount > 0) {
  		System.out.println("Test errors are in:");
  		for (int i = 0;i < fErrorCount; i++) 
  			fErrors[i].print();
  		System.out.println();
  	}
  }
}



