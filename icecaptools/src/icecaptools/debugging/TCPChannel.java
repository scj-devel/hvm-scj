package icecaptools.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;

public class TCPChannel extends DebugChannel {
    private int requestResponseChannel;
    private int eventChannel;
    private String targetIPAddress;
    private Socket requestResponseSocket;
    private Socket eventSocket;

    public TCPChannel(int requestResponseChannel, int eventChannel, String targetIPAddress) {
        this.requestResponseChannel = requestResponseChannel;
        this.eventChannel = eventChannel;
        this.targetIPAddress = targetIPAddress;
    }
    
    public void connectToTarget(IProgressMonitor monitor) throws UnknownHostException, IOException {
        monitor.subTask("Connect debug request port");
        requestResponseSocket = connectToPort(targetIPAddress, requestResponseChannel, monitor);
        monitor.subTask("Connect debug response port");
        eventSocket = connectToPort(targetIPAddress, eventChannel, monitor);
    }
    
    private static Socket connectToPort(String host, int port, IProgressMonitor monitor) throws UnknownHostException, IOException {
        int i = 0;
        while (true) {
            try {
                Socket socket = new Socket(host, port);
                return socket;
            } catch (IOException e) {
                i++;
                if (i > 10) {
                    throw e;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                }
                monitor.worked(1);
            }
        }
    }

    @Override
    public InputStream getEventChannel() throws IOException {
        return eventSocket.getInputStream();
    }

    @Override
    public OutputStream getRequestOutputStream() throws IOException {
        return requestResponseSocket.getOutputStream();
    }

    @Override
    public InputStream getRequestInputStream() throws IOException {
        return requestResponseSocket.getInputStream();
    }

    @Override
    public void disconnectFromTarget() throws IOException {
        requestResponseSocket.close();
        
    }
}
