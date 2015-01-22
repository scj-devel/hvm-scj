package icecaptools.launching.arduino;

import icecaptools.IcecapEclipseProgressMonitor;
import icecaptools.launching.AbstractHVMPOSIXLaunchConfigurationDelegate;
import icecaptools.launching.ShellCommand;

import java.io.File;
import java.io.PrintStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ArduinoProcessLinuxHost extends ArduinoProcess {
    private static String rootPasswd;

    public static final String downloadPort = "/dev/ttyACM0";

    static {
        rootPasswd = null;
    }

    public ArduinoProcessLinuxHost(String sourceFolder) {
        super(sourceFolder);
        processOutputPort = "/dev/ttyS80";
    }

    @Override
    protected ArduinoProcess startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path, PrintStream consoleOutputStream, IProgressMonitor monitor, Shell shell) throws CoreException {
        String[] commands = new String[3];
        commands[0] = "su";
        commands[1] = "-c";
        commands[2] = "avrdude -F -V -c arduino -p ATMEGA328P -P " + downloadPort + " -b 115200 -U flash:w:main.hex";

        ensureRootPasswd(shell);

        if (rootPasswd == null) {
            monitor.setCanceled(true);
        } else {
            OutputStreamParser commandOutputParser = new OutputStreamParser(consoleOutputStream);
            InputStreamGenerator commandInputGenerator = new InputStreamGenerator(commandOutputParser, rootPasswd + "\n");

            int result = ShellCommand.executeCommand(commands, commandOutputParser, commandInputGenerator, true, sourceFolder.toString(), null, 20, new IcecapEclipseProgressMonitor(monitor));

            if (result != 0) {
                StringBuffer message = new StringBuffer();

                message.append("Could not download to Arduino\n");
                File f = new File(downloadPort);
                if (!f.exists()) {
                    message.append("Is Arduino plugged into USB?");
                } else {
                    message.append("Is root password correct?");
                    rootPasswd = null;
                }
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify(message.toString());
                return null;
            } else {
                result = ensurePortAccessible(downloadPort, rootPasswd, sourceFolder, monitor, consoleOutputStream);

                if (result == 0) {

                    result = connectProcessOutput(consoleOutputStream);

                    if (result == 0) {
                        return this;
                    } else {
                        AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not connect to process output");
                        return null;
                    }
                }
            }
        }
        return null;
    }
    
    private void ensureRootPasswd(final Shell shell) {
        if (rootPasswd == null) {
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    PasswordDialog id = new PasswordDialog(shell, "Enter root password", "Root password required to download to Arduino", "", new PasswordValidator());
                    id.open();
                    rootPasswd = id.getValue();
                }
            });
        }
    }
    
    private int ensurePortAccessible(String portName, String rootPasswd, String sourceFolder, IProgressMonitor monitor, PrintStream consoleOutputStream) {
        File f = new File(processOutputPort);
        String[] commands;
        OutputStreamParser commandOutputParser;
        InputStreamGenerator commandInputGenerator;
        if (!f.exists()) {
            commands = new String[3];
            commands[0] = "su";
            commands[1] = "-c";
            commands[2] = "ln -s " + portName + " " + processOutputPort;

            commandOutputParser = new OutputStreamParser(consoleOutputStream);
            commandInputGenerator = new InputStreamGenerator(commandOutputParser, rootPasswd + "\n");

            int result = ShellCommand.executeCommand(commands, commandOutputParser, commandInputGenerator, true, sourceFolder, null, 20, new IcecapEclipseProgressMonitor(monitor));
            if (result != 0) {
                return result;
            }
        }
        commands = new String[3];
        commands[0] = "su";
        commands[1] = "-c";
        commands[2] = "chmod 777 " + portName;
        commandOutputParser = new OutputStreamParser(consoleOutputStream);
        commandInputGenerator = new InputStreamGenerator(commandOutputParser, rootPasswd + "\n");
        return ShellCommand.executeCommand(commands, commandOutputParser, commandInputGenerator, true, sourceFolder, null, 20, new IcecapEclipseProgressMonitor(monitor));
    }
    
    private static class PasswordValidator implements IInputValidator {

        @Override
        public String isValid(String newText) {
            if (newText != null) {
                if (newText.trim().length() > 0) {
                    return null;
                }
            }
            return "Please enter password";
        }
    }
}
