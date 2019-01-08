package carcontrol.test;

/**
 * Thrown when an assertion failed.
 */
public class AssertionFailedError extends Error {

	// private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssertionFailedError() {
	}

	public AssertionFailedError(String message) {
		super(message);
	}
}