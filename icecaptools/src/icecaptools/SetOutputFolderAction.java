package icecaptools;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class SetOutputFolderAction implements IObjectActionDelegate {

    private String folder;
    public static final String ICECAP_OUTPUTFOLDER = "ICECAP_OUTPUTFOLDER";

    @Override
    public void run(IAction arg0) {
        setOutputFolder(folder);
    }

    public static void setOutputFolder(String folder) {
        if ((folder != null) && (folder.length() > 0)) {
            IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
            String outputFolder = preferenceStore.getString(ICECAP_OUTPUTFOLDER);
            if (!folder.equals(outputFolder)) {
                preferenceStore.setValue(ICECAP_OUTPUTFOLDER, folder);
            }
        }
    }

    public static String getOutputFolder() {
        IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
        String outputFolder = preferenceStore.getString(ICECAP_OUTPUTFOLDER);
        return outputFolder;
    }

    @Override
    public void selectionChanged(IAction arg0, ISelection selection) {
        if (selection != null) {
            if (selection instanceof TreeSelection) {
                TreeSelection treeSelection = (TreeSelection) selection;
                Object firstElement = treeSelection.getFirstElement();
                if (firstElement instanceof IFolder) {
                    IFolder folder = (IFolder) firstElement;
                    this.folder = folder.getLocation().toOSString().trim();
                }
            }
        }
    }

    @Override
    public void setActivePart(IAction action, IWorkbenchPart part) {

    }

}
