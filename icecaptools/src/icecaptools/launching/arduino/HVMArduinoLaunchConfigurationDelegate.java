package icecaptools.launching.arduino;

import icecaptools.IcecapEclipseProgressMonitor;
import icecaptools.compiler.utils.OsCheck;
import icecaptools.debugging.DebugChannel;
import icecaptools.launching.AbstractHVMPOSIXLaunchConfigurationDelegate;
import icecaptools.launching.ShellCommand;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

public class HVMArduinoLaunchConfigurationDelegate extends AbstractHVMPOSIXLaunchConfigurationDelegate {

    public static final byte EVENTCHANNEL = 1;
    public static final byte REQUESTRESPONSECHANNEL = 2;
    public static final byte STDOUTCHANNEL = 3;

    private ArduinoProcess rProcess;

    @Override
    protected String getTargetIPAddress(ILaunchConfiguration configuration) throws CoreException {
        return "0.0.0.0";
    }

    @Override
    protected String getStripper() {
        return "avr-strip";
    }

    @Override
    protected void addAdditionalFiles(StringBuffer command, ILaunchConfiguration configuration) throws CoreException {
        command.append("natives_arduino.c natives_avr.c avr_interrupt.s");

    }

    @Override
    protected String getCompilerExecutable() {
        return "avr-gcc";
    }

    @Override
    protected StringBuffer getCompilerCommand(ILaunchConfiguration configuration) throws CoreException {
        StringBuffer compilerCommand = new StringBuffer();
        compilerCommand.append("avr-gcc -DF_CPU=16000000UL -mmcu=atmega328p -Wall -pedantic " + getOptimizationLevel(configuration) + " ");

        return compilerCommand;
    }

    @Override
    protected Process startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path, String sourceFolder, PrintStream consoleOutputStream, IProgressMonitor monitor) throws CoreException {
        OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
        switch (ostype) {
        case Linux:
            rProcess = new ArduinoProcessLinuxHost(sourceFolder);
            break;
        case Windows:
            rProcess = new ArduinoProcessWindowsHost(sourceFolder);
            break;
        case MacOS:
        case Other:
            AbstractHVMPOSIXLaunchConfigurationDelegate.notify("Unsupported host OS for Arduino development");
            return null;
        }
        
        rProcess = rProcess.startProcessOnTarget(launch, configuration, path, consoleOutputStream, monitor, getShell());
        return rProcess;
    }

    @Override
    protected void stripExecutable(StringBuffer path, PrintStream consoleOutputStream, String sourceFolder, IProgressMonitor monitor) {
        super.stripExecutable(path, consoleOutputStream, sourceFolder, monitor);
        ShellCommand.executeCommand("avr-objcopy -O ihex -R .eeprom main.exe main.hex", consoleOutputStream, true, sourceFolder.toString(), null, 20, new IcecapEclipseProgressMonitor(monitor));
    }

    @Override
    protected String getHeapSize(int size) {
        return "" + size;
    }

    @Override
    protected DebugChannel getChannel(Process p, int requestResponseChannel, int eventChannel, String targetIPAddress) throws IOException {
        ArduinoProcess process = (ArduinoProcess) p;

        return process.getChannel();
    }

    @Override
    protected int getEventChannel() {
        return EVENTCHANNEL;
    }

    @Override
    protected int getRequestResponseChannel() {
        return REQUESTRESPONSECHANNEL;
    }
}
