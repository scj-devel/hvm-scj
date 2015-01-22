package icecaptools.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class POSIXLauncherTab extends TargetSpecificLauncherTab {

    private Button arch64Button;
    private Button arch32Button;
    private Button cygwinButton;

    public static final String TARGET_ARCHITECTURE = "TARGET_ARCHITECTURE";
    public static final String CYGWIN_USED = "CYGWIN_USED";

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        Group architecureGroup = new Group(root, SWT.SHADOW_IN);

        FormLayout layout = new FormLayout();
        architecureGroup.setLayout(layout);

        architecureGroup.setText("Target architecture");
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(super.optimizationLevelGroup, 5);
        formData.right = new FormAttachment(100, -5);
        architecureGroup.setLayoutData(formData);

        arch64Button = new Button(architecureGroup, SWT.RADIO);
        arch64Button.setText("64 bit");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(0, 5);
        arch64Button.setLayoutData(formData);

        arch64Button.addMouseListener(new MouseListener() {

            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
            }

            @Override
            public void mouseUp(MouseEvent e) {
                lcd.updateButtons();
            }
        });
        arch32Button = new Button(architecureGroup, SWT.RADIO);
        arch32Button.setText("32 bit");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(arch64Button, 5);
        arch32Button.setLayoutData(formData);

        arch32Button.addMouseListener(new MouseListener() {

            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
            }

            @Override
            public void mouseUp(MouseEvent e) {
                lcd.updateButtons();
            }
        });

        cygwinButton = new Button(architecureGroup, SWT.CHECK);
        cygwinButton.setText("Cygwin?");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(arch32Button, 5);
        cygwinButton.setLayoutData(formData);

        cygwinButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
            }

            @Override
            public void mouseUp(MouseEvent e) {
                lcd.updateButtons();
            }
        });
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        super.setDefaults(configuration);
        configuration.setAttribute(TARGET_ARCHITECTURE, "64bit");
        configuration.setAttribute(CYGWIN_USED, "false");
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        super.initializeFrom(configuration);
        try {
            String targetArchitecture = configuration.getAttribute(TARGET_ARCHITECTURE, "");
            if ("64bit".equals(targetArchitecture)) {
                arch64Button.setSelection(true);
            } else {
                arch32Button.setSelection(true);
            }
            String cygwinUsed = configuration.getAttribute(CYGWIN_USED, "false");
            if (cygwinUsed.equalsIgnoreCase("true")) {
                cygwinButton.setSelection(true);
            }
            else
            {
                cygwinButton.setSelection(false);
            }
        } catch (CoreException e) {
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        super.performApply(configuration);
        if (arch64Button.getSelection()) {
            configuration.setAttribute(TARGET_ARCHITECTURE, "64bit");
        } else {
            configuration.setAttribute(TARGET_ARCHITECTURE, "32bit");
        }
        if (cygwinButton.getSelection()) {
            configuration.setAttribute(CYGWIN_USED, "true");
        } else {
            configuration.setAttribute(CYGWIN_USED, "false");
        }
    }

    @Override
    protected String getHeapSizeUnit() {
        return "MB";
    }

    @Override
    protected int getHeapSizeMaximum() {
        return 16;
    }

    @Override
    protected int getDefaultHeapSize() {
        return 1;
    }
}
