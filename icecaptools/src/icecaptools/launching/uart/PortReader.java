package icecaptools.launching.uart;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class PortReader implements SerialPortEventListener, Runnable, OutputParser {
    private SerialPort serialPort;
    private CommPortIdentifier portId;
    private InputStream inputStream;
    private BufferedOutputStream outputStream;
    private Thread readThread;
    private InputParser inputParser;
    private volatile boolean stopReader;
    private boolean outputTrace;
    private int workCount;
    private String progressMessage;

    public PortReader(String comport, InputParser inputParser) throws NoSuchPortException {
        this.portId = CommPortIdentifier.getPortIdentifier(comport);
        this.inputParser = inputParser;
        stopReader = false;
        outputTrace = false;
        workCount = 0;
    }

    public void startPort() throws PortInUseException, IOException, TooManyListenersException, UnsupportedCommOperationException {
        serialPort = (SerialPort) portId.open("PortReader", 2000);
        inputStream = serialPort.getInputStream();
        outputStream = new BufferedOutputStream(serialPort.getOutputStream(), 8192 * 2);
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
        setPortSpeed(9600);
        readThread = new Thread(this);
        readThread.start();
    }

    public void stopPort() {
        stopReader = true;
    }

    public void serialEvent(SerialPortEvent event) {

        switch (event.getEventType()) {

        case SerialPortEvent.BI:

        case SerialPortEvent.OE:

        case SerialPortEvent.FE:

        case SerialPortEvent.PE:

        case SerialPortEvent.CD:

        case SerialPortEvent.CTS:

        case SerialPortEvent.DSR:

        case SerialPortEvent.RI:

        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;

        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];

            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);

                    for (int i = 0; i < numBytes; i++) {
                        if (inputParser.parseByte(readBuffer[i]) == null) {
                            stopPort();
                        }
                    }
                }
            } catch (IOException e) {
            } catch (UnsupportedCommOperationException e) {
            }
            break;
        }
    }

    public void run() {
        while (!stopReader) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        serialPort.close();
    }

    public void parseByte(byte b) throws IOException {
        outputStream.write(b);
        if (outputTrace) {
            addProgressMessage("out: " + b);
        }
    }

    public void setPortSpeed(int speed) throws UnsupportedCommOperationException {
        addProgressMessage("Baudrate " + speed);
        serialPort.setSerialPortParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    }

    public synchronized void increaseWorkCount() {
        workCount++;
    }

    public synchronized int getWorkCount() {
        return workCount;
    }

    public void enableOutputTrace() {
        outputTrace = true;
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void join() throws InterruptedException {
        readThread.join();
    }

    public boolean running() {
        return (stopReader != true);
    }

    public synchronized void addProgressMessage(String message) {
        this.progressMessage = message;
    }

    public synchronized String getProgressMessage() {
        return progressMessage;
    }

    public synchronized void setInputParser(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    @Override
    public void parseBytes(byte[] data, int top) throws IOException {
        outputStream.write(data, 0, top);

    }

    public void parseBytes(byte[] data) throws IOException {
        parseBytes(data, data.length);
    }
}
