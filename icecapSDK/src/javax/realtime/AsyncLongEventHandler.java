package javax.realtime;

public class AsyncLongEventHandler extends AsyncBaseEventHandler {

	protected long data = 0L;
	
	public final void run() {
		handleAsyncEvent(data);
	}
	
	/**
	 * This method must be overridden by the application to provide the handling code.
	 * 
	 * @param data
	 *    is the data that was passed when the associated event handler was released.
	 */
	public void handleAsyncEvent(long data) {
	}
}
