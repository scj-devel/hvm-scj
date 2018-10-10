package carcontrol.test;


public abstract class Passive extends Test {
		
	// Switch case number
	private int caseNumber;
			
	public abstract void test (int i); 
	
	/**
	 * @exception Throwable if any exception is thrown
	 */
	public void runTest() {
		System.out.println ("Passive.runTest called");
		int i = 1;
//		try {
//			for (; i <= testCount; i++) 
//				test(i);
//		} catch (Throwable e) {
//			System.out.println("===> Begin Passive.runTest.test: " + i + "; Throwable: " + e.getMessage() );
//			
//			testResult.addError(this, e);
//			//TestResult.instance().addError(this, e);
//			setCaseNumber(i);
//			
//			System.out.println("===> End Passive.runTest.test: Throwable \n");
//		}
		
		
			for (; i <= testCount; i++) {
				try {
					test(i);
				} catch (Throwable e) {
					System.out.println("===> Begin Passive.runTest.test: " + i + "; Throwable: " + e.getMessage() );
					
					testResult.addError(this, e, i);
					//TestResult.instance().addError(this, e);
					setCaseNumber(i);
					
					System.out.println("===> End Passive.runTest.test: Throwable \n");
				}
			}
	}
	
	public void setCaseNumber(int number) {
		caseNumber = number;
	}

}
