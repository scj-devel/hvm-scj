package icecaptools.launching.arduino;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import icecaptools.debugging.DebugChannel;
import icecaptools.launching.uart.InputParser;
import icecaptools.launching.uart.PortReader;
import icecaptools.launching.uart.Serial;
import icecaptools.launching.uart.UARTChannel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.TooManyListenersException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.widgets.Shell;

public abstract class ArduinoProcess extends Process {
    private InputStream errorStreamFromProcess;
    private InputStream outputStreamFromProcess;
    private OutputStream inputStreamToProcess;

    private PortReader pReader;

    protected String processOutputPort;
    protected String sourceFolder;

    private class DummyInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            while (true) {
                if (pReader.running()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    return -1;
                }
            }
        }
    }

    private static class DummyOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }

    private static class UARTProcessOutput extends ProcessOutputReader implements InputParser {
        @Override
        public InputParser parseByte(byte b) throws IOException, UnsupportedCommOperationException {
            queue.add(b);
            if (b != -1) {
                return this;
            } else {
                return null;
            }
        }
    }

    private class UARTProcessInput extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            pReader.parseByte((byte) b);
            pReader.flush();
        }
    }

    public ArduinoProcess(String sourceFolder) {
        outputStreamFromProcess = new DummyInputStream();
        inputStreamToProcess = new DummyOutputStream();
        errorStreamFromProcess = new DummyInputStream();
        this.sourceFolder = sourceFolder;
    }

    @Override
    public OutputStream getOutputStream() {
        return inputStreamToProcess;
    }

    @Override
    public InputStream getInputStream() {
        return outputStreamFromProcess;
    }

    @Override
    public InputStream getErrorStream() {
        return errorStreamFromProcess;
    }

    @Override
    public int waitFor() throws InterruptedException {
        if (pReader != null) {
            pReader.join();
        }
        return 0;
    }

    @Override
    public int exitValue() {
        if (pReader.running()) {
            throw new IllegalThreadStateException();
        } else {
            return 0;
        }
    }

    @Override
    public void destroy() {
        pReader.stopPort();
    }

    public int connectProcessOutput(PrintStream consoleOutputStream) {
        Iterator<String> availablePorts = Serial.getAvailablePorts();

        while (availablePorts.hasNext()) {
            String port = availablePorts.next();
            if (port.equals(processOutputPort)) {
                try {
                    outputStreamFromProcess = new UARTProcessOutput();
                    pReader = new PortReader(processOutputPort, (UARTProcessOutput) outputStreamFromProcess);
                    this.inputStreamToProcess = new UARTProcessInput();
                    consoleOutputStream.println("Starting port reader");
                    pReader.startPort();
                } catch (NoSuchPortException e) {
                    consoleOutputStream.println("NoSuchPortException");
                    return -1;
                } catch (PortInUseException e) {
                    consoleOutputStream.println("PortInUseException");
                    return -1;
                } catch (IOException e) {
                    consoleOutputStream.println("IOException");
                    return -1;
                } catch (TooManyListenersException e) {
                    consoleOutputStream.println("TooManyListenersException");
                    return -1;
                } catch (UnsupportedCommOperationException e) {
                    consoleOutputStream.println("UnsupportedCommOperationException");
                    return -1;
                }
            }
        }
        if (pReader == null) {
            consoleOutputStream.println("Unable to read from Arduino");
            return -1;
        } else {
            return 0;
        }
    }

  

    public void setInputStream(InputStream stdoutChannel) {
        this.outputStreamFromProcess = stdoutChannel;
    }

    protected abstract ArduinoProcess startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path, PrintStream consoleOutputStream, IProgressMonitor monitor, Shell shell) throws CoreException;

    public static class OutputStreamParser extends OutputStream {
        private PrintStream consoleOutputStream;
        private boolean ready;

        public OutputStreamParser(PrintStream consoleOutputStream) {
            this.consoleOutputStream = consoleOutputStream;
            ready = false;
        }

        @Override
        public void write(int b) throws IOException {
            consoleOutputStream.write(b);
            if (b == ':') {
                ready = true;
            }
        }

        public boolean getReady() {
            return ready;
        }
    }

    public static class InputStreamGenerator extends InputStream {
        private OutputStreamParser commandOutputParser;
        private String passwd;
        private int top;

        public InputStreamGenerator(OutputStreamParser commandOutputParser, String passwd) {
            this.commandOutputParser = commandOutputParser;
            this.passwd = passwd;
            top = 0;
        }

        @Override
        public int read() throws IOException {
            while (!commandOutputParser.getReady()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            if (top < passwd.length()) {
                return passwd.charAt(top++);
            } else {
                return -1;
            }
        }
    }

    public DebugChannel getChannel() throws IOException {
        UARTChannel channel = new UARTChannel(getInputStream(), getOutputStream());

        setInputStream(channel.getStdoutChannel());
        return channel;
    }

}