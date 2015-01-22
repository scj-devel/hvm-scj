package icecaptools.launching.arduino;

import icecaptools.compiler.utils.OsCheck;
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

public class ArduinoLauncherTab extends TargetSpecificLauncherTab {

    public static final String COMPORT = "COMPORT";
    
    private Text comPortText;

    public ArduinoLauncherTab() {
        super();
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        Group comGroup = new Group(root, SWT.SHADOW_IN);

        FormLayout layout = new FormLayout();
        comGroup.setLayout(layout);

        comGroup.setText("COM port");
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(super.optimizationLevelGroup, 5);
        formData.right = new FormAttachment(100, -5);
        comGroup.setLayoutData(formData);

        Label comLabel = new Label(comGroup, SWT.LEAD);
        comLabel.setText(" COM port:");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 9);
        comLabel.setLayoutData(formData);

        comPortText = new Text(comGroup, SWT.SINGLE | SWT.BORDER);
        formData = new FormData();
        formData.left = new FormAttachment(comLabel, 5);
        formData.top = new FormAttachment(0, 5);
        formData.right = new FormAttachment(100, -5);
        comPortText.setLayoutData(formData);

        comPortText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                lcd.updateButtons();
            }
        });
    }
    
    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        super.setDefaults(configuration);
        configuration.setAttribute(COMPORT, getDefaultComPort());
    }

    public static String getDefaultComPort() {
        OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
        switch (ostype) {
        case Linux:
            return ArduinoProcessLinuxHost.downloadPort;
        case Windows:
            return "COM11";            
        case MacOS:
        case Other:
            return "";
        }
        return "";
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        super.initializeFrom(configuration);
        try {
            String targetComPort = configuration.getAttribute(COMPORT, getDefaultComPort());
            if (targetComPort != null) {
                comPortText.setText(targetComPort);
            }
        } catch (CoreException e) {
        }
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        boolean comPortValid = false;
        if ((comPortText.getText() != null) && (comPortText.getText().trim().length() > 0)) {
            comPortValid = true;
        }
        return super.isValid(launchConfig) && comPortValid;
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        super.performApply(configuration);
        String targetComPort = comPortText.getText();
        if (targetComPort != null) {
            configuration.setAttribute(COMPORT, targetComPort);
        } else {
            configuration.setAttribute(COMPORT, getDefaultComPort());
        }
    }
    
    @Override
    protected String getHeapSizeUnit() {
        return "bytes";
    }

    @Override
    protected int getHeapSizeMaximum() {
        return 1024 + 512;
    }

    @Override
    protected int getDefaultHeapSize() {
        return 1024;
    }
}
