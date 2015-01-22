package icecaptools.launching.ev3;

import icecaptools.launching.TargetSpecificLauncherTab;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EV3LauncherTab extends TargetSpecificLauncherTab {

    public static final String DEVICE_IPADDRESS = "DEVICE_IPADDRESS";

    protected String defaultDeviceAddress;
    private String deviceName;
    
    Text ipText;

    protected EV3LauncherTab(String defaultDeviceAddress, String deviceName)
    {
        this.defaultDeviceAddress = defaultDeviceAddress;
        this.deviceName = deviceName;
    }
    
    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        Group ipGroup = new Group(root, SWT.SHADOW_IN);

        FormLayout layout = new FormLayout();
        ipGroup.setLayout(layout);

        ipGroup.setText(deviceName + " IP address");
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(super.optimizationLevelGroup, 5);
        formData.right = new FormAttachment(100, -5);
        ipGroup.setLayoutData(formData);

        Label ipLabel = new Label(ipGroup, SWT.LEAD);
        ipLabel.setText(deviceName + " IP Address:");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 9);
        ipLabel.setLayoutData(formData);

        ipText = new Text(ipGroup, SWT.SINGLE | SWT.BORDER);
        formData = new FormData();
        formData.left = new FormAttachment(ipLabel, 5);
        formData.top = new FormAttachment(0, 5);
        formData.right = new FormAttachment(100, -5);
        ipText.setLayoutData(formData);

        ipText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                lcd.updateButtons();
            }
        });
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        super.setDefaults(configuration);
        configuration.setAttribute(DEVICE_IPADDRESS, defaultDeviceAddress);
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        super.initializeFrom(configuration);
        try {
            String targetIPAddress = configuration.getAttribute(DEVICE_IPADDRESS, "");
            if (targetIPAddress != null) {
                ipText.setText(targetIPAddress);
            }
        } catch (CoreException e) {
        }
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        boolean ipAddressValid = false;
        if ((ipText.getText() != null) && (ipText.getText().trim().length() > 0)) {
            ipAddressValid = true;
        }
        return super.isValid(launchConfig) && ipAddressValid;
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        super.performApply(configuration);
        String targetIPAddress = ipText.getText();
        if (targetIPAddress != null) {
            configuration.setAttribute(DEVICE_IPADDRESS, targetIPAddress);
        } else {
            configuration.setAttribute(DEVICE_IPADDRESS, defaultDeviceAddress);
        }
    }

    @Override
    protected String getHeapSizeUnit() {
        return "kB";
    }

    @Override
    protected int getHeapSizeMaximum() {
        return 2048;
    }

    @Override
    protected int getDefaultHeapSize() {
        return 64;
    }
}
