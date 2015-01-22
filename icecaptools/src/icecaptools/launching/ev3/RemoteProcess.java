package icecaptools.launching.ev3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

class RemoteProcess extends Process {
    private InputStream errorStreamFromProcess;
    private InputStream outputStreamFromProcess;
    private OutputStream inputStreamToProcess;
    
    private Channel channel;
    private Session session;
    private SessionMonitor sessionMonitor;
    
    public RemoteProcess(String iPAddress, PrintStream consoleOutputStream, String executable) {
        JSch jsch = new JSch();

        try {
            session = jsch.getSession("root", iPAddress, 22);
            session.setPassword("");

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            String command = executable;

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            errorStreamFromProcess = ((ChannelExec)channel).getErrStream();
            outputStreamFromProcess = channel.getInputStream();
            inputStreamToProcess = channel.getOutputStream();
            
            channel.connect();
            
            sessionMonitor = new SessionMonitor();
            sessionMonitor.start();
            
        } catch (JSchException e) {
            consoleOutputStream.print(e.getMessage());
        } catch (IOException e) {
            consoleOutputStream.print(e.getMessage());
        }
    }

    private class SessionMonitor extends Thread
    {
        private boolean stop;
        
        SessionMonitor()
        {
            stop = false;
        }
        
        @Override
        public void run() {
            while (!stop)
            {
                if (channel.isClosed())
                {
                    stop = true;
                }
                else
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
            channel.disconnect();
            session.disconnect();
        }

        public void destroySession() {
            stop = true;
        }        
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
        sessionMonitor.join();
        return channel.getExitStatus();
    }

    @Override
    public int exitValue() {
        if (channel.isClosed())
        {
            return channel.getExitStatus();
        }
        throw new IllegalThreadStateException();
    }

    @Override
    public void destroy() {
        sessionMonitor.destroySession();
    }
}