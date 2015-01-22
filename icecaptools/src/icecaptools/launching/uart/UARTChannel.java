package icecaptools.launching.uart;

import icecaptools.debugging.DebugChannel;
import icecaptools.launching.arduino.HVMArduinoLaunchConfigurationDelegate;
import icecaptools.launching.arduino.ProcessOutputReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;

public class UARTChannel extends DebugChannel {
    private ProcessOutput eventStream;
    private ProcessOutput responseStream;
    private ProcessOutput stdoutStream;
    private OutputStream requestStream;

    private ProcessOutputReaderThread reader;
    
    private static class ProcessOutput extends ProcessOutputReader {

        public void putToQueue(byte b) {
            queue.add(b);
        }
    }

    private class ProcessOutputReaderThread extends Thread {

        private InputStream outputFromProcess;
        private boolean stop;

        public ProcessOutputReaderThread(InputStream input) {
            this.outputFromProcess = input;
            stop = false;
        }

        @Override
        public void run() {
            boolean eventChannelClosed = false;
            boolean responseChannelClosed = false;
            boolean stdoutChannelClosed = false;
            while (stop == false) {
                try {
                    int tag = outputFromProcess.read();
                    int c = outputFromProcess.read();

                    switch (tag) {
                    case HVMArduinoLaunchConfigurationDelegate.EVENTCHANNEL:
                        eventStream.putToQueue((byte) c);
                        eventChannelClosed = (c == -1); 
                        break;
                    case HVMArduinoLaunchConfigurationDelegate.REQUESTRESPONSECHANNEL:
                        responseStream.putToQueue((byte) c);
                        responseChannelClosed = (c == -1); 
                        break;
                    case HVMArduinoLaunchConfigurationDelegate.STDOUTCHANNEL:
                        stdoutStream.putToQueue((byte) c);
                        stdoutChannelClosed = (c == -1); 
                        break;
                    }
                    stop = (eventChannelClosed && responseChannelClosed && stdoutChannelClosed);
                } catch (IOException e) {
                    stop = true;
                }
            }
        }

        public void stopReading() {
            stop = true;
        }
    }

    private static class InputToProcessStream extends OutputStream {
        private OutputStream inputToProcess;

        public InputToProcessStream(OutputStream output) {
            this.inputToProcess = output;
        }

        @Override
        public void write(int b) throws IOException {
            inputToProcess.write(b);
            inputToProcess.flush();
        }
    }

    public UARTChannel(InputStream input, OutputStream output) {
        requestStream = new InputToProcessStream(output);
        eventStream = new ProcessOutput();
        responseStream = new ProcessOutput();
        stdoutStream = new ProcessOutput();

        reader = new ProcessOutputReaderThread(input);
        reader.start();
    }

    @Override
    public void connectToTarget(IProgressMonitor monitor) throws UnknownHostException, IOException {
    }

    @Override
    public InputStream getEventChannel() throws IOException {
        return eventStream;
    }
    
    public InputStream getStdoutChannel() throws IOException {
        return this.stdoutStream;
    }

    @Override
    public OutputStream getRequestOutputStream() throws IOException {
        return requestStream;
    }

    @Override
    public InputStream getRequestInputStream() throws IOException {
        return responseStream;
    }

    @Override
    public void disconnectFromTarget() throws IOException {
        reader.stopReading();
        try {
            reader.join();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}
