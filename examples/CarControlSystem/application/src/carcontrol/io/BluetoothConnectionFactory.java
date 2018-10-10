/**
 * 
 */
package carcontrol.io;

import java.io.IOException;

import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import javax.safetycritical.io.ConnectionFactory;

public class BluetoothConnectionFactory extends ConnectionFactory {

	protected BluetoothConnectionFactory(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see javax.safetycritical.io.ConnectionFactory#create(java.lang.String)
	 */
	@Override
	public Connection create(String url) throws IOException, ConnectionNotFoundException {
		BluetoothConnection bluetoothConnection = new BluetoothConnection(url);
		return bluetoothConnection;
	}
}
