package icecaptools.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.IDebugUIConstants;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public abstract class TargetSpecificLauncherTab extends CommonLauncherTab {

    public static final String HEAPSIZE = "HEAPSIZE";
    public static final String IMPLEMENTATION_FILE = "IMPLEMENTATION_FILE";
    public static final String ENABLE_NATIVE_IMPLEMENTATION = "ENABLE_NATIVE_IMPLEMENTATION";
    public static final String GCC_OPTIMIZATION_LEVEL = "GCC_OPTIMIZATION_LEVEL";

    private Spinner heapSizeText;
    private Button nativesCheckbox;
    private Text nativesText;

    private NativesCheckboxListener nativesListener;

    protected Group optimizationLevelGroup;

    private Button level0button;
    private Button level1button;
    private Button level2button;
    private Button level3button;
    private Button levelsbutton;

    @Override
    protected String getSourceGroupHeader() {
        return "Source";
    }

    @Override
    protected String getSourceLabelText() {
        return "Source folder:";
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        Group heapSizeGroup = new Group(root, SWT.SHADOW_IN);
        heapSizeGroup.setText("Heap size");
        FormData formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(sourceGroup, 10);
        formData.right = new FormAttachment(100, -5);
        heapSizeGroup.setLayoutData(formData);

        Layout layout = new FormLayout();
        heapSizeGroup.setLayout(layout);

        Label heapSize = new Label(heapSizeGroup, SWT.LEAD);
        heapSize.setText("Heap size (" + getHeapSizeUnit() + "):");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 10);
        heapSize.setLayoutData(formData);

        heapSizeText = new Spinner(heapSizeGroup, SWT.READ_ONLY | SWT.BORDER);
        heapSizeText.setMaximum(getHeapSizeMaximum());
        formData = new FormData();
        formData.left = new FormAttachment(heapSize, 5);
        formData.top = new FormAttachment(0, 10);
        formData.right = new FormAttachment(100, -5);
        heapSizeText.setLayoutData(formData);

        heapSizeText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                lcd.updateButtons();
            }
        });

        Group nativesGroup = new Group(root, SWT.SHADOW_IN);

        layout = new FormLayout();
        nativesGroup.setLayout(layout);

        nativesGroup.setText("User defined natives");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(heapSizeGroup, 10);
        formData.right = new FormAttachment(100, -5);
        nativesGroup.setLayoutData(formData);

        nativesCheckbox = new Button(nativesGroup, SWT.CHECK);
        nativesCheckbox.setText("Enable user defined natives");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(0, 10);
        nativesCheckbox.setLayoutData(formData);

        Label nativesFile = new Label(nativesGroup, SWT.LEAD);
        nativesFile.setText("Implementation file:");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(nativesCheckbox, 10);
        nativesFile.setLayoutData(formData);

        Button selectNatives = new Button(nativesGroup, SWT.PUSH);
        selectNatives.setText("...");
        formData = new FormData();
        formData.top = new FormAttachment(nativesCheckbox, 10);
        formData.right = new FormAttachment(100, -5);
        selectNatives.setLayoutData(formData);

        nativesText = new Text(nativesGroup, SWT.SINGLE | SWT.BORDER);
        formData = new FormData();
        formData.left = new FormAttachment(nativesFile, 5);
        formData.top = new FormAttachment(nativesCheckbox, 10);
        formData.right = new FormAttachment(selectNatives, -5);
        nativesText.setLayoutData(formData);

        selectNatives.addMouseListener(new SelectImplementationFileListener(selectNatives, nativesText));

        nativesListener = new NativesCheckboxListener(nativesCheckbox, nativesFile, selectNatives, nativesText);
        nativesCheckbox.addMouseListener(nativesListener);

        optimizationLevelGroup = new Group(root, SWT.SHADOW_IN);

        layout = new FormLayout();
        optimizationLevelGroup.setLayout(layout);

        optimizationLevelGroup.setText("Compiler optimization level");
        formData = new FormData();
        formData.left = new FormAttachment(0, 5);
        formData.top = new FormAttachment(nativesGroup, 5);
        formData.right = new FormAttachment(100, -5);
        optimizationLevelGroup.setLayoutData(formData);

        level0button = new Button(optimizationLevelGroup, SWT.RADIO);
        level0button.setText("-O0");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(0, 5);
        level0button.setLayoutData(formData);

        level0button.addMouseListener(new MouseListener() {

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
        level1button = new Button(optimizationLevelGroup, SWT.RADIO);
        level1button.setText("-O1");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(level0button, 5);
        level1button.setLayoutData(formData);

        level1button.addMouseListener(new MouseListener() {

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

        level2button = new Button(optimizationLevelGroup, SWT.RADIO);
        level2button.setText("-O2");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(level1button, 5);
        level2button.setLayoutData(formData);

        level2button.addMouseListener(new MouseListener() {

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

        level3button = new Button(optimizationLevelGroup, SWT.RADIO);
        level3button.setText("-O3");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(level2button, 5);
        level3button.setLayoutData(formData);

        level3button.addMouseListener(new MouseListener() {

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

        levelsbutton = new Button(optimizationLevelGroup, SWT.RADIO);
        levelsbutton.setText("-Os");
        formData = new FormData();
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(level3button, 5);
        levelsbutton.setLayoutData(formData);

        levelsbutton.addMouseListener(new MouseListener() {

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

    protected abstract int getHeapSizeMaximum();

    protected abstract String getHeapSizeUnit();

    private class SelectImplementationFileListener extends SelectSourceFolderOrFileListener {

        SelectImplementationFileListener(Button select, Text text) {
            super(select, text);
        }

        @Override
        public void mouseUp(MouseEvent e) {
            FileDialog dialog = new FileDialog(select.getShell(), SWT.OPEN);
            dialog.setText("Locate implementation file");
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

    private class NativesCheckboxListener implements MouseListener {
        private Button checkbox;

        private Label nativesFile;
        private Button selectNatives;
        private Text nativesText;

        NativesCheckboxListener(Button checkbox, Label nativesFile, Button selectNatives, Text nativesText) {
            this.checkbox = checkbox;
            this.nativesFile = nativesFile;
            this.selectNatives = selectNatives;
            this.nativesText = nativesText;
            disableNativesSelection(false);
        }

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
        }

        @Override
        public void mouseUp(MouseEvent e) {
            if (checkbox.getSelection()) {
                enableNativesSelection(true);
            } else {
                disableNativesSelection(true);
            }
        }

        private void disableNativesSelection(boolean update) {
            nativesFile.setEnabled(false);
            selectNatives.setEnabled(false);
            nativesText.setEnabled(false);
            if (update) {
                lcd.updateButtons();
            }
        }

        private void enableNativesSelection(boolean update) {
            nativesFile.setEnabled(true);
            selectNatives.setEnabled(true);
            nativesText.setEnabled(true);
            if (update) {
                lcd.updateButtons();
            }
        }
    }

    @Override
    public Control getControl() {
        return root;
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        super.setDefaults(configuration);
        configuration.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
        configuration.setAttribute(HEAPSIZE, getDefaultHeapSize());
        configuration.setAttribute(ENABLE_NATIVE_IMPLEMENTATION, false);
        configuration.setAttribute(IMPLEMENTATION_FILE, "");
        configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 0);
    }

    protected abstract int getDefaultHeapSize();

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            super.initializeFrom(configuration);
            int heapSize = configuration.getAttribute(HEAPSIZE, getDefaultHeapSize());

            heapSizeText.setSelection(heapSize);

            int optimLevel = configuration.getAttribute(GCC_OPTIMIZATION_LEVEL, 0);
            switch (optimLevel) {
            case 1:
                level1button.setSelection(true);
                break;
            case 2:
                level2button.setSelection(true);
                break;
            case 3:
                level3button.setSelection(true);
                break;
            case 4:
                levelsbutton.setSelection(true);
                break;
            case 0:
            default:
                level0button.setSelection(true);
                break;
            }

            boolean natives = configuration.getAttribute(ENABLE_NATIVE_IMPLEMENTATION, false);
            nativesCheckbox.setSelection(natives);

            String implementationFile = configuration.getAttribute(IMPLEMENTATION_FILE, "");

            if (implementationFile.trim().length() > 0) {
                nativesText.setText(implementationFile);
            }

            if (natives) {
                nativesListener.enableNativesSelection(false);
            } else {
                nativesListener.disableNativesSelection(false);
            }
        } catch (CoreException e) {
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        super.performApply(configuration);
        configuration.setAttribute(HEAPSIZE, heapSizeText.getSelection());
        configuration.setAttribute(ENABLE_NATIVE_IMPLEMENTATION, nativesCheckbox.getSelection());
        configuration.setAttribute(IMPLEMENTATION_FILE, nativesText.getText());
        configuration.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, false);
        if (level0button.getSelection()) {
            configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 0);
        } else if (level1button.getSelection()) {
            configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 1);
        } else if (level2button.getSelection()) {
            configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 2);
        } else if (level3button.getSelection()) {
            configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 3);
        } else if (levelsbutton.getSelection()) {
            configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 4);
        } else {
            configuration.setAttribute(GCC_OPTIMIZATION_LEVEL, 0);
        }
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
            boolean enableNatives = nativesCheckbox.getSelection();

            if (enableNatives) {
                String natives = nativesText.getText();
                if (natives != null) {
                    if (natives.trim().length() > 0) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canSave() {
        return isValid(null);
    }
}
