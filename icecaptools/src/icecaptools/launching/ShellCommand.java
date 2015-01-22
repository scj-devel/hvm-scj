package icecaptools.launching;

import icecaptools.IcecapProgressMonitor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class ShellCommand {

    public static final int PROCESS_START_FAILED = -2;
    public static final int PROCESS_HANGED = -1;
    public static final int ILLEGAL_WORKINGDIRECTORY = -3;

    public ShellCommand() {
        super();
    }

    public static int executeCommand(String command, OutputStream consoleOutputStream, boolean doExecute, String workingDirectoryPath, String[] envp, int timeout, IcecapProgressMonitor monitor) {
        return executeIt(null, command, consoleOutputStream, null, doExecute, workingDirectoryPath, envp, timeout, monitor);
    }

    public static int executeCommand(String[] commands, OutputStream consoleOutputStream, InputStream consoleInputStream, boolean doExecute, String workingDirectoryPath, String[] envp, int timeout, IcecapProgressMonitor monitor) {
        return executeIt(commands, null, consoleOutputStream, consoleInputStream, doExecute, workingDirectoryPath, envp, timeout, monitor);
    }

    private static int executeIt(String[] commands, String command, OutputStream consoleOutputStream, InputStream consoleInputStream, boolean doExecute, String workingDirectoryPath, String[] envp, int timeout, IcecapProgressMonitor monitor) {
        Runtime runTime = Runtime.getRuntime();

        PrintWriter writer = new PrintWriter(consoleOutputStream);
        if (command != null) {
            writer.println(command);
        }
        writer.flush();

        if (envp != null) {
            envp = addToProcessEnvironment(envp);
        }

        if (timeout == 0) {
            timeout = 20;
        }
        if (doExecute) {
            try {
                File workingDirectory = new File(workingDirectoryPath);
                if (workingDirectory.exists()) {
                    Process compilerProcess;

                    monitor.subTask("Compiling");

                    if (command != null) {
                        compilerProcess = runTime.exec(command, envp, workingDirectory);
                    } else {
                        compilerProcess = runTime.exec(commands, envp, workingDirectory);
                    }

                    ProcessTerminator waiter = new ProcessTerminator(compilerProcess, timeout, monitor);
                    waiter.startWaiting();

                    // Make sure to eat the error and output right away.
                    StreamGobler inputStreamGobler = new StreamGobler(compilerProcess.getInputStream(), consoleOutputStream);
                    StreamGobler errorStreamGobler = new StreamGobler(compilerProcess.getErrorStream(), consoleOutputStream);
                    StreamGobler outputStreamGobler;

                    if (consoleInputStream != null) {
                        outputStreamGobler = new StreamGobler(consoleInputStream, compilerProcess.getOutputStream());
                        outputStreamGobler.start();
                    } else {
                        outputStreamGobler = null;
                    }

                    inputStreamGobler.start();
                    errorStreamGobler.start();

                    compilerProcess.waitFor();

                    waiter.stopWaiting();

                    inputStreamGobler.join();
                    errorStreamGobler.join();

                    if (outputStreamGobler != null) {
                        outputStreamGobler.join();
                    }
                    consoleOutputStream.flush();
                    if (waiter.killedProcess()) {
                        return PROCESS_HANGED;
                    } else {
                        return waiter.exitValue;
                    }
                } else {
                    return ILLEGAL_WORKINGDIRECTORY;
                }
            } catch (IOException e) {
                writer.println("Could not execute command: [" + e.getMessage() + "]");
                return PROCESS_START_FAILED;
            } catch (InterruptedException e) {
                return PROCESS_START_FAILED;
            }
        } else {
            return 0;
        }
    }

    public static String[] addToProcessEnvironment(String[] envp) {
        try {
            Map<String, String> parentEnvironment = new HashMap<String, String>();

            Map<String, String> systemEnv = System.getenv();

            Iterator<String> systemEnvIterator = systemEnv.keySet().iterator();

            while (systemEnvIterator.hasNext()) {
                String nextKey = systemEnvIterator.next();
                String nextValue = systemEnv.get(nextKey);
                parentEnvironment.put(nextKey.toUpperCase(), nextValue);
            }

            for (int i = 0; i < envp.length; i++) {
                String current = envp[i];
                StringTokenizer tokenizer = new StringTokenizer(current, "=");
                String name;
                String value;
                if (tokenizer.hasMoreElements()) {
                    name = tokenizer.nextToken().toUpperCase();
                    if (tokenizer.hasMoreElements()) {
                        value = tokenizer.nextToken();
                        if (parentEnvironment.containsKey(name)) {
                            String parentValue = parentEnvironment.get(name);
                            parentValue = value + File.pathSeparatorChar + parentValue;
                            parentEnvironment.put(name, parentValue);
                        }
                    }
                }
            }
            Set<String> keys = parentEnvironment.keySet();
            String[] childEnvp;
            childEnvp = new String[keys.size()];
            int i = 0;
            Iterator<String> keysIterator = parentEnvironment.keySet().iterator();
            while (keysIterator.hasNext()) {
                String nextKey = keysIterator.next();
                StringBuffer nextEntry = new StringBuffer();
                nextEntry.append(nextKey);
                nextEntry.append("=");
                nextEntry.append(parentEnvironment.get(nextKey));
                childEnvp[i] = nextEntry.toString();
                i++;
            }
            return childEnvp;
        } catch (Exception ex) {
            return null;
        }

    }

    private static class ProcessTerminator extends Thread {
        private Process process;

        private int numberOfSecondsToWait;

        private boolean continueToRun;

        private boolean killedProcess;

        private IcecapProgressMonitor monitor;

        public int exitValue;

        public ProcessTerminator(Process process, int numberOfSecondsToWait, IcecapProgressMonitor monitor) {
            this.process = process;
            this.numberOfSecondsToWait = numberOfSecondsToWait;
            this.continueToRun = true;
            killedProcess = false;
            this.monitor = monitor;
        }

        public boolean killedProcess() {
            return killedProcess;
        }

        public void stopWaiting() {
            exitValue = process.exitValue();
            continueToRun = false;
        }

        public void startWaiting() {
            start();
        }

        @Override
        public void run() {
            // Sleep for timeout seconds
            while ((numberOfSecondsToWait > 0) && continueToRun) {
                try {
                    Thread.sleep(1000);
                    numberOfSecondsToWait--;
                    if (monitor.isCanceled()) {
                        numberOfSecondsToWait = 0;
                    } else {
                        monitor.worked(1);
                    }
                } catch (InterruptedException e) {
                }
            }
            // We have no more patience with this process, and will terminate it
            // if it hasn't finished yet.
            try {
                exitValue = process.exitValue();
            } catch (IllegalThreadStateException ex) {
                // This process has not yet terminated
                killedProcess = true;
                process.destroy();
            }
        }
    }
}
