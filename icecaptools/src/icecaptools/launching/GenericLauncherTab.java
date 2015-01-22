package icecaptools.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

public class GenericLauncherTab extends CommonLauncherTab {

    @Override
    protected String getSourceGroupHeader() {
        return "Working Directory";
    }

    @Override
    public Control getControl() {
        return root;
    }

    @Override
    protected String getSourceLabelText() {
        return "Folder:";
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        Group compileGroup = new Group(root, SWT.SHADOW_IN);
        compileGroup.setText("Compiling");
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(sourceGroup, 10);
        formData.right = new FormAttachment(100, -5);
        compileGroup.setLayoutData(formData);

        Layout layout = new FormLayout();
        compileGroup.setLayout(layout);

        Label commandLabel = new Label(compileGroup, SWT.LEAD);
        commandLabel.setText("Command:");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 10);
        commandLabel.setLayoutData(formData);

        Text commandText = new Text(compileGroup, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
        formData = new FormData();
        formData.left = new FormAttachment(commandLabel, 5);
        formData.top = new FormAttachment(0, 10);
        formData.right = new FormAttachment(100, -5);
        commandText.setLayoutData(formData);

        commandText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                lcd.updateButtons();
            }
        });

    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        super.setDefaults(configuration);
        configuration.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        super.initializeFrom(configuration);
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        super.performApply(configuration);
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        if (super.isValid(launchConfig)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canSave() {
        return isValid(null);
    }

}
