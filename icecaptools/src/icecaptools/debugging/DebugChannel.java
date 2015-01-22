package icecaptools.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;

public abstract class DebugChannel {

    public abstract void connectToTarget(IProgressMonitor monitor) throws UnknownHostException, IOException;

    public abstract InputStream getEventChannel() throws IOException;

    public abstract OutputStream getRequestOutputStream() throws IOException;

    public abstract InputStream getRequestInputStream() throws IOException;

    public abstract void disconnectFromTarget() throws IOException;
}
