package main;


/*
 * To run the automated tests make sure that gcc is installed and can be 
 * started from a normal command prompt.
 * 
 * If you run cygwin, include the path to gcc in your path environment 
 * variable (usually C:\cygwin\bin). Test that it works by starting gcc from
 * a CMD prompt (not a cygwin prompt). After changing environment variables, it may
 * be necessary to restart eclipse.
 * 
 * Then select your gcc compile command from one of the predefined commands below 
 * (look at the beginning of the 'compileAndExecute' method
 * 
 */
public class TestSCJ extends TestAll {

	public static void main(String[] args) throws Throwable {
		new TestSCJ().performTest();
	}

	protected boolean includeFileInTest(String test) {
		return test.endsWith(".java") && test.startsWith("TestSCJ");
	}

}
