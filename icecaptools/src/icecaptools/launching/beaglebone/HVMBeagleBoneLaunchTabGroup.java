package icecaptools.launching.beaglebone;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class HVMBeagleBoneLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

    private ILaunchConfigurationTab[] tabs;
    
    @Override
    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        tabs = new ILaunchConfigurationTab[] { new BeagleBoneLauncherTab("192.168.7.2", "BeagleBone") };
    }

    @Override
    public ILaunchConfigurationTab[] getTabs() {
        return tabs;
    }
}
