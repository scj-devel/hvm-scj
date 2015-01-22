package icecaptools.launching.arduino;

import icecaptools.IcecapEclipseProgressMonitor;
import icecaptools.launching.AbstractHVMPOSIXLaunchConfigurationDelegate;
import icecaptools.launching.ShellCommand;

import java.io.PrintStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.widgets.Shell;

public class ArduinoProcessWindowsHost extends ArduinoProcess {

    public ArduinoProcessWindowsHost(String sourceFolder) {
        super(sourceFolder);
    }

    @Override
    protected ArduinoProcess startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path, PrintStream consoleOutputStream, IProgressMonitor monitor, Shell shell) throws CoreException {
        
        processOutputPort = configuration.getAttribute(ArduinoLauncherTab.COMPORT, ArduinoLauncherTab.getDefaultComPort());
        
        String command = "avrdude -F -V -c arduino -p ATMEGA328P -P " + processOutputPort + " -b 115200 -U flash:w:main.hex";

        int result = ShellCommand.executeCommand(command, consoleOutputStream, true, sourceFolder.toString(), null, 20, new IcecapEclipseProgressMonitor(monitor));

        if (result == 0) {

            result = connectProcessOutput(consoleOutputStream);
            if (result == 0) {
                return this;
            } else {
                AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Could not connect to process output (" + processOutputPort + ")\nIs Arduino plugged in?");
                return null;
            }
        } else {
            return null;
        }
    }
}
