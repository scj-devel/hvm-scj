package icecaptools.launching.ev3;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class HVMEV3LaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

    private ILaunchConfigurationTab[] tabs;
    
    @Override
    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        tabs = new ILaunchConfigurationTab[] { new EV3LauncherTab("192.168.0.0", "EV3") };
    }

    @Override
    public ILaunchConfigurationTab[] getTabs() {
        return tabs;
    }
}
