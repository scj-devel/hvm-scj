package icecaptools.launching;

import java.io.IOException;
import java.net.ServerSocket;

import icecaptools.debugging.DebugChannel;
import icecaptools.debugging.TCPChannel;

public abstract class TCPChannelLauncher extends AbstractHVMPOSIXLaunchConfigurationDelegate {

    @Override
    protected int getEventChannel() {
        return findFreePort();
    }

    @Override
    protected int getRequestResponseChannel() {
        return findFreePort();
    }

    @Override
    protected DebugChannel getChannel(Process p, int requestResponseChannel, int eventChannel, String targetIPAddress) {
        return new TCPChannel(requestResponseChannel, eventChannel, targetIPAddress);
    }
    
    public static int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            return socket.getLocalPort();
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        return -1;
    }
}
