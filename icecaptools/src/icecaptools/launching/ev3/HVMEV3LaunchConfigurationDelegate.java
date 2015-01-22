package icecaptools.launching.ev3;

import icecaptools.PluginResourceManager;
import icecaptools.StreamResource;
import icecaptools.debugging.DebugChannel;
import icecaptools.debugging.TCPChannel;
import icecaptools.launching.AbstractHVMPOSIXLaunchConfigurationDelegate;
import icecaptools.launching.TCPChannelLauncher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.commons.net.telnet.TelnetClient;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class HVMEV3LaunchConfigurationDelegate extends TCPChannelLauncher {

    private static final String compilerExecutable = "arm-none-linux-gnueabi-gcc";

    protected String deviceName;
    
    public HVMEV3LaunchConfigurationDelegate()
    {
        deviceName = getDeviceName();
    }
    
    protected String getDeviceName() {
        return "EV3";
    }

    @Override
    protected Process startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path, String sourceFolder, PrintStream consoleOutputStream, IProgressMonitor monitor) throws CoreException {
        String IPAddress = configuration.getAttribute(EV3LauncherTab.DEVICE_IPADDRESS, "0.0.0.0");

        try {
            monitor.subTask("ping " + deviceName);
            if (!pingDevice(IPAddress)) {
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not connect to " + deviceName + " at " + IPAddress);
                return null;
            }

            monitor.subTask("start ssh on " + deviceName);
            if (!ensureSSH(IPAddress)) {
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not establish ssh connection to " + deviceName + " at " + IPAddress);
                return null;
            }

            monitor.subTask("upload program wrapper to " + deviceName);            
            if (!ensureProgramWrapper(IPAddress, consoleOutputStream)) {
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not copy program wrapper to " + deviceName + " at " + IPAddress);
                return null;
            }
            
            monitor.subTask("upload program to " + deviceName);            
            if (!ensureProgram(IPAddress, consoleOutputStream, path))
            {
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not copy program to " + deviceName + " at " + IPAddress);
                return null;
            }

            monitor.subTask("Start program on " + deviceName);
            

            Process rProcess = startProgram(IPAddress, consoleOutputStream);
            if (rProcess == null)
            {
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not start program on " + deviceName + " at " + IPAddress);
                return null;
            }
            
            return rProcess;
        } catch (IOException e) {
            IStatus status = new IStatus() {

                private final String message = "Could not ping " + deviceName + " - IOException";

                @Override
                public IStatus[] getChildren() {
                    return null;
                }

                @Override
                public int getCode() {
                    return IStatus.ERROR;
                }

                @Override
                public Throwable getException() {
                    return new Exception(message);
                }

                @Override
                public String getMessage() {
                    return message;
                }

                @Override
                public String getPlugin() {
                    return DebugPlugin.getUniqueIdentifier();
                }

                @Override
                public int getSeverity() {
                    return IStatus.ERROR;
                }

                @Override
                public boolean isMultiStatus() {
                    return false;
                }

                @Override
                public boolean isOK() {
                    return false;
                }

                @Override
                public boolean matches(int severityMask) {
                    return false;
                };
            };
            throw new CoreException(status);
        } catch (InterruptedException e) {
            IStatus status = new IStatus() {

                private final String message = "Could not ping " + deviceName +" - Interrupted";

                @Override
                public IStatus[] getChildren() {
                    return null;
                }

                @Override
                public int getCode() {
                    return IStatus.ERROR;
                }

                @Override
                public Throwable getException() {
                    return new Exception(message);
                }

                @Override
                public String getMessage() {
                    return message;
                }

                @Override
                public String getPlugin() {
                    return DebugPlugin.getUniqueIdentifier();
                }

                @Override
                public int getSeverity() {
                    return IStatus.ERROR;
                }

                @Override
                public boolean isMultiStatus() {
                    return false;
                }

                @Override
                public boolean isOK() {
                    return false;
                }

                @Override
                public boolean matches(int severityMask) {
                    return false;
                };
            };
            throw new CoreException(status);
        }
    }

    private RemoteProcess startProgram(String iPAddress, PrintStream consoleOutputStream) {
        RemoteProcess rPocess = new RemoteProcess(iPAddress, consoleOutputStream, getRemoteProgramLocation() + "program");
        return rPocess;
    }

    private boolean ensureProgram(String iPAddress, PrintStream consoleOutputStream, StringBuffer path) throws FileNotFoundException {
        File executable = new File(path.toString());
        InputStream dataStream = new FileInputStream(executable);
        return copyDataTo(iPAddress, dataStream, consoleOutputStream, "program");
    }

    private static final String rfile_name = "run_program.rbf";

    protected boolean ensureProgramWrapper(String iPAddress, PrintStream consoleOutputStream) {
        PluginResourceManager rManager = new PluginResourceManager();
        StreamResource sresource = rManager.getResource(consoleOutputStream, rfile_name);
        InputStream dataStream = sresource.getStream();

        return copyDataTo(iPAddress, dataStream, consoleOutputStream, rfile_name);
    }

    private boolean copyDataTo(String iPAddress, InputStream dataStream, PrintStream consoleOutputStream, String rfile_name) {
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession("root", iPAddress, 22);
            session.setPassword("");

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect(5000);

            String rpath = getRemoteProgramLocation() + rfile_name;
            // exec 'scp -t rpath' remotely
            String command = "scp -t " + rpath;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            if (checkAck(in) != 0) {
                channel.disconnect();
                session.disconnect();
                return false;
            }

            ArrayList<Byte> bytes = readResource(dataStream);

            long filesize = bytes.size();
            command = "C0744 " + filesize + " " + rfile_name + "\n";

            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                channel.disconnect();
                session.disconnect();
                return false;
            }

            byte[] raw = new byte[(int) filesize];
            for (int i = 0; i < filesize; i++) {
                raw[i] = bytes.get(i);
            }
            
            out.write(raw);

            out.write(0);
            out.flush();
            if (checkAck(in) != 0) {
                channel.disconnect();
                session.disconnect();
                return false;
            }
            out.close();
            channel.disconnect();
            session.disconnect();

            return true;
        } catch (JSchException e) {
        } catch (IOException e) {
        }
        return false;
    }

    protected String getRemoteProgramLocation() {
        return "/mnt/ramdisk/prjs/BrkProg_SAVE/";
    }

    private ArrayList<Byte> readResource(InputStream dataStream) throws IOException {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        int next = dataStream.read();
        while (next != -1) {
            bytes.add((byte) next);
            next = dataStream.read();
        }
        return bytes;
    }

    static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        // 1 for error,
        // 2 for fatal error,
        // -1
        if (b == 0)
            return b;
        if (b == -1)
            return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
        }
        return b;
    }

    private boolean ensureSSH(String iPAddress) {
        return ensureSSH(iPAddress, true);
    }

    private boolean ensureSSH(String iPAddress, boolean retry) {
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession("root", iPAddress, 22);
            session.setPassword("");

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect(5000);
            session.disconnect();
            return true;
        } catch (JSchException e) {
            if (retry) {
                startSSHOnTarget(iPAddress);
                return ensureSSH(iPAddress, false);
            }
            return false;
        }
    }

    protected void startSSHOnTarget(String iPAddress) {
        TelnetClient telnet = new TelnetClient();
        try {
            telnet.connect(iPAddress, 23);

            TelnetReader telnetReader = new TelnetReader(telnet, "login: ");
            telnetReader.start();
            telnetReader.awaitExpected();
            telnetReader.stopReading();
            telnetReader.join();

            if (telnetReader.didReadExpected()) {
                OutputStream outstream = telnet.getOutputStream();
                outstream.write(new byte[] { 'r', 'o', 'o', 't', '\n' });
                outstream.flush();

                telnetReader = new TelnetReader(telnet, "root@EV3:~# ");
                telnetReader.start();
                telnetReader.awaitExpected();
                telnetReader.stopReading();
                telnetReader.join();

                if (telnetReader.didReadExpected()) {
                    outstream.write(new byte[] { 'd', 'r', 'o', 'p', 'b', 'e', 'a', 'r', '\n' });
                    outstream.flush();

                    telnetReader = new TelnetReader(telnet, "root@EV3:~# ");
                    telnetReader.start();
                    telnetReader.awaitExpected();
                    telnetReader.stopReading();
                    telnetReader.join();
                }
                telnet.disconnect();
            }
        } catch (SocketException e) {
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }

    private static class TelnetReader extends Thread {
        private String expectedOutput;
        private InputStream instream;
        private boolean stop;
        private int top;

        public TelnetReader(TelnetClient telnet, String expectedOutput) {
            instream = telnet.getInputStream();
            stop = false;
            top = 0;
            this.expectedOutput = expectedOutput;
        }

        public void awaitExpected() {
            for (int i = 0; i < 10; i++) {
                if (didReadExpected()) {
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

        }

        public void stopReading() {
            stop = true;
        }

        boolean didReadExpected() {
            return top == expectedOutput.length();
        }

        @Override
        public void run() {

            try {
                while (!stop) {
                    if (instream.available() > 0) {
                        byte[] buf = new byte[128];
                        int count = instream.read(buf);
                        if (count == -1) {
                            return;
                        } else if (count > 0) {
                            for (int i = 0; i < count; i++) {
                                if (handleByte(buf[i])) {
                                    return;
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (IOException e) {
            }
        }

        private boolean handleByte(byte b) {
            char n = (char) b;
            if (n == expectedOutput.charAt(top)) {
                top++;
            } else {
                top = 0;
            }
            return top == expectedOutput.length();
        }
    }

    private static boolean pingDevice(String host) throws InterruptedException, IOException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows ? "-n" : "-c", "1", host);
        Process proc = processBuilder.start();

        int returnVal = proc.waitFor();
        return returnVal == 0;
    }

    @Override
    protected StringBuffer getCompilerCommand(ILaunchConfiguration configuration) throws CoreException {
        StringBuffer compilerCommand = new StringBuffer();
        compilerCommand.append(compilerExecutable + " -Wall -pedantic " + getOptimizationLevel(configuration) + " -DPC32 -DPRINTFSUPPORT ");

        return compilerCommand;

    }

    @Override
    protected String getCompilerExecutable() {
        return compilerExecutable;
    }

    @Override
    protected void addAdditionalFiles(StringBuffer command, ILaunchConfiguration configuration) {
        command.append("natives_i86.c natives_ev3.c native_scj.c arm_interrupt.s -lrt ");
    }

    @Override
    protected String getStripper() {
        return "arm-none-linux-gnueabi-strip";
    }

    @Override
    protected String getTargetIPAddress(ILaunchConfiguration configuration) throws CoreException {
        String IPAddress = configuration.getAttribute(EV3LauncherTab.DEVICE_IPADDRESS, "0.0.0.0");

        return IPAddress;
    }

    @Override
    protected String getHeapSize(int size) {
        return (size * 1024) + "";
    }

    @Override
    protected DebugChannel getChannel(Process p, int requestResponseChannel, int eventChannel, String targetIPAddress) {
        return new TCPChannel(requestResponseChannel, eventChannel, targetIPAddress);
    }
}
