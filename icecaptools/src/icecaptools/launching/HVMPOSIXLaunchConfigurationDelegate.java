package icecaptools.launching;

import java.io.PrintStream;
import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

public class HVMPOSIXLaunchConfigurationDelegate extends TCPChannelLauncher {

    private static final String compilerExecutable = "gcc";

    protected Process startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path, String sourceFolder, PrintStream consoleOutputStream, IProgressMonitor monitor) throws CoreException {
        Process process = DebugPlugin.exec(new String[] { path.toString() }, null);

        return process;
    }

    private boolean is64bit(ILaunchConfiguration configuration) throws CoreException {
        String targetArchitecture = configuration.getAttribute(POSIXLauncherTab.TARGET_ARCHITECTURE, "");
        if ("64bit".equals(targetArchitecture)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected StringBuffer getCompilerCommand(ILaunchConfiguration configuration) throws CoreException {
        boolean bit64 = is64bit(configuration);
        StringBuffer compilerCommand = new StringBuffer();
        compilerCommand.append(compilerExecutable + " -g -Wall -pedantic " + getOptimizationLevel(configuration) + " -DPRINTFSUPPORT -DPC");
        if (bit64) {
            compilerCommand.append("64 ");
        } else {
            compilerCommand.append("32 ");
        }
        return compilerCommand;
    }

    @Override
    protected String getCompilerExecutable() {
        return compilerExecutable;
    }

    @Override
    protected void addAdditionalFiles(ArrayList<String> command, ILaunchConfiguration configuration) throws CoreException {
        StringBuffer buffer = new StringBuffer("x86_");
    	command.add("natives_i86.c");
        
        if (is64bit(configuration)) {
        	buffer.append("64");
        } else {
        	buffer.append("32");
        }
        
        String cygwinUsed = configuration.getAttribute(POSIXLauncherTab.CYGWIN_USED, "");
        
        if (cygwinUsed.equals("true"))
        {
        	buffer.append("_cygwin");
        }
        
        buffer.append("_interrupt.s");
        command.add(buffer.toString());
        
        command.add("-lpthread");
        
        if (cygwinUsed.equals("false"))
        {
            command.add("-lrt");
        }
        
        if (is64bit(configuration)) {
            command.add("-DREF_OFFSET");
        }
    }

    @Override
    protected String getStripper(ILaunchConfiguration configuration) throws CoreException {
        return "strip";
    }

    @Override
    protected String getTargetIPAddress(ILaunchConfiguration configuration) {
        return "localhost";
    }

    @Override
    protected String getHeapSize(int size) {
        return (size * 1024 * 1024) + "";
    }
}
