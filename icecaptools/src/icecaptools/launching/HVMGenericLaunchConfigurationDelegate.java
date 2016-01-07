package icecaptools.launching;

import icecaptools.debugging.DebugChannel;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

public class HVMGenericLaunchConfigurationDelegate extends AbstractHVMPOSIXLaunchConfigurationDelegate {

	public HVMGenericLaunchConfigurationDelegate() {
	}

	@Override
	protected int getEventChannel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getRequestResponseChannel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected DebugChannel getChannel(Process p, int requestResponseChannel, int eventChannel, String targetIPAddress)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getHeapSize(int string) {
		return "" + string;
	}

	@Override
	protected String getTargetIPAddress(ILaunchConfiguration configuration) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getStripper(ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(GenericLauncherTab.POSTBUILDCOMMAND, "");
	}

	@Override
	protected void addAdditionalFiles(StringBuffer compilerCommand, ILaunchConfiguration configuration)
			throws CoreException {
	}

	@Override
	protected String getCompilerExecutable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected StringBuffer getCompilerCommand(ILaunchConfiguration configuration) throws CoreException {
		return new StringBuffer(configuration.getAttribute(CommonLauncherTab.COMPILER_COMMAND, ""));
	}

	@Override
	protected Process startProcessOnTarget(ILaunch launch, ILaunchConfiguration configuration, StringBuffer path,
			String sourceFolder, PrintStream consoleOutputStream, IProgressMonitor monitor) throws CoreException {
		String deployCommand = configuration.getAttribute(GenericLauncherTab.DEPLOYCOMMAND, "");
		Process process = DebugPlugin.exec(new String[] { path.toString() }, null);
		return process;
	}

}
