package icecaptools.launching.arduino;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class HVMArduinoLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

    private ILaunchConfigurationTab[] tabs;
    
    @Override
    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        tabs = new ILaunchConfigurationTab[] { new ArduinoLauncherTab() };
    }

    @Override
    public ILaunchConfigurationTab[] getTabs() {
        return tabs;
    }
}
