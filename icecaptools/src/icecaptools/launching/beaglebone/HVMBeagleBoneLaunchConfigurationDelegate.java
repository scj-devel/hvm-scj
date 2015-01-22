package icecaptools.launching.beaglebone;

import icecaptools.launching.ev3.HVMEV3LaunchConfigurationDelegate;

import java.io.PrintStream;

import org.eclipse.debug.core.ILaunchConfiguration;

public class HVMBeagleBoneLaunchConfigurationDelegate extends HVMEV3LaunchConfigurationDelegate {

    @Override
    protected boolean ensureProgramWrapper(String iPAddress, PrintStream consoleOutputStream) {
        return true;
    }

    @Override
    protected String getRemoteProgramLocation() {
        return "/home/root/";
    }

    @Override
    protected void startSSHOnTarget(String iPAddress) {
        ;
    }

    @Override
    protected String getDeviceName() {
        return "BeagleBone";
    }

    @Override
    protected void addAdditionalFiles(StringBuffer command, ILaunchConfiguration configuration) {
        command.append("natives_i86.c natives_beaglebone.c");
    }
}
