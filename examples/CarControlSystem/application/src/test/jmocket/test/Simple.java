package test.jmocket.test;

public class Simple {
	
	// returns String: works
	private native String iAmPrivate();
		  
	public String publicCallsPrivate(){
	  return iAmPrivate();
	}
	
	// ===============================================================
	
	static boolean nativeMethodHasBeenCalled = false;
	
	// return type is void
	private native void iAmPrivateAndReturnsVoid();
	  
	public void publicCallsPrivateVoid(){
	  iAmPrivateAndReturnsVoid();
	}
	
	// ==================================================================
	
	// has a parameter and returns String: works; static: does not matter anything
	private static native String iAmPrivateWithAParameter(int a);
		  
	public String publicCallsPrivateWithAParameter(int x) {
	  return "Value: " + iAmPrivateWithAParameter(x);
	}
}
