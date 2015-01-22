package icecaptools.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

public abstract class CommonLauncherTab extends AbstractLaunchConfigurationTab {
    private static final String tabName = "Main";

    public static final String SOURCE_FOLDER = "SOURCE_FOLDER";

    protected Composite root;
    private Text sourceText;
    protected ILaunchConfigurationDialog lcd;
    protected Group sourceGroup;

    @Override
    public void createControl(Composite parent) {
        root = new Composite(parent, SWT.NONE);
        Layout layout = new FormLayout();
        root.setLayout(layout);
        FormData formData;

        sourceGroup = new Group(root, SWT.SHADOW_IN);
        sourceGroup.setText(getSourceGroupHeader());
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 10);
        formData.right = new FormAttachment(100, -5);
        sourceGroup.setLayoutData(formData);

        layout = new FormLayout();
        sourceGroup.setLayout(layout);

        Label sourceFolder = new Label(sourceGroup, SWT.LEAD);
        sourceFolder.setText(getSourceLabelText());
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 10);
        sourceFolder.setLayoutData(formData);

        Button select;
        select = new Button(sourceGroup, SWT.PUSH);
        select.setText("...");
        formData = new FormData();
        formData.top = new FormAttachment(0, 10);
        formData.right = new FormAttachment(100, -5);
        select.setLayoutData(formData);

        sourceText = new Text(sourceGroup, SWT.SINGLE | SWT.BORDER);
        formData = new FormData();
        formData.left = new FormAttachment(sourceFolder, 5);
        formData.top = new FormAttachment(0, 10);
        formData.right = new FormAttachment(select, -5);
        sourceText.setLayoutData(formData);

        sourceText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                lcd.updateButtons();
            }
        });

        select.addMouseListener(new SelectSourceFolderListener(select, sourceText));
    }

    protected abstract String getSourceLabelText();

    protected abstract String getSourceGroupHeader();

    protected static abstract class SelectSourceFolderOrFileListener implements MouseListener {
        protected Button select;
        protected Text text;

        SelectSourceFolderOrFileListener(Button select, Text text) {
            this.select = select;
            this.text = text;
        }

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
        }

    }

    private class SelectSourceFolderListener extends SelectSourceFolderOrFileListener {

        SelectSourceFolderListener(Button select, Text text) {
            super(select, text);
        }

        @Override
        public void mouseUp(MouseEvent e) {
            DirectoryDialog dialog = new DirectoryDialog(select.getShell(), SWT.OPEN);
            dialog.setText("Locate source folder");
            String filter = text.getText();
            if (filter != null) {
                if (filter.trim().length() == 0) {
                    filter = null;
                }
            }
            dialog.setFilterPath(filter);
            String path = dialog.open();
            if (path == null)
                return;
            text.setText(path);
            lcd.updateButtons();
        }
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(SOURCE_FOLDER, "");
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        String sourceFolder;
        try {
            sourceFolder = configuration.getAttribute(SOURCE_FOLDER, "");
            if (sourceFolder.trim().length() > 0) {
                sourceText.setText(sourceFolder);
            }
        } catch (CoreException e) {
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        String folder = sourceText.getText();
        configuration.setAttribute(SOURCE_FOLDER, folder);
    }

    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        String folder = sourceText.getText();
        if (folder != null) {
            if (folder.trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setLaunchConfigurationDialog(ILaunchConfigurationDialog dialog) {
        this.lcd = dialog;
    }
    
    @Override
    public String getName() {
        return tabName;
    }
}
