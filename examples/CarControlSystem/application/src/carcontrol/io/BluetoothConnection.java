/**
 * 
 */
package carcontrol.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

import carcontrol.device.BluetoothSerial;
import carcontrol.device.impl.BluetoothSerialImpl;

public class BluetoothConnection implements StreamConnection {

	private BluetoothSerial bluetoothSerial;

	public BluetoothConnection(String url) {
		this.bluetoothSerial = new BluetoothSerialImpl();
		this.bluetoothSerial.openSerial();
	}

	/* (non-Javadoc)
	 * @see javax.microedition.io.InputConnection#openDataInputStream()
	 */
	@Override
	public DataInputStream openDataInputStream() {
		return new DataInputStream(openInputStream());
	}

	/* (non-Javadoc)
	 * @see javax.microedition.io.InputConnection#openInputStream()
	 */
	@Override
	public InputStream openInputStream() {
		return new InputStream() {

			@Override
			public int read() throws IOException {
				return bluetoothSerial.read();
			}
			
			@Override
			public int available() {
				// TODO do something more clever
				// see if bluetooth are initialized and check if there is anything in the fifo
				return 1;
			}
		};
	}

	/* (non-Javadoc)
	 * @see javax.microedition.io.Connection#close()
	 */
	@Override
	public void close() {
		this.bluetoothSerial.close();

	}

	/* (non-Javadoc)
	 * @see javax.microedition.io.OutputConnection#openDataOutputStream()
	 */
	@Override
	public DataOutputStream openDataOutputStream() {
		return new DataOutputStream(openOutputStream());
	}

	/* (non-Javadoc)
	 * @see javax.microedition.io.OutputConnection#openOutputStream()
	 */
	@Override
	public OutputStream openOutputStream() {
		return new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				bluetoothSerial.write(b);
			}
		};
	}
}
