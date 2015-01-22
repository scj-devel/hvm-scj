package icecaptools.views;

import icecaptools.MethodOrFieldDesc;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class MethodListContentProvider implements ITreeContentProvider {

    private DependencyRoot currentInput;

    public MethodListContentProvider() {
        currentInput = null;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (newInput != null) {
            currentInput = (DependencyRoot) newInput;
        }
    }

    @Override
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof DependencyRoot) {
            DependencyExtent[] root = new DependencyExtent[1];
            root[0] = ((DependencyRoot)currentInput).getRoot();
            return root;
        } else if (inputElement instanceof DependencyExtent) {
            return ((DependencyExtent)inputElement).getUsedClasses().toArray();
        } else if (inputElement instanceof UsedClass) {
            UsedClass usedClass = (UsedClass) inputElement;
            return usedClass.toArray();
        }
        return null;
    }
    
    @Override
    public Object[] getChildren(Object parentElement) {
        return getElements(parentElement);
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof UsedClass) {
            return currentInput.getRoot().getUsedClasses();
        } else if (element instanceof MethodOrFieldDesc) {
            return currentInput.getRoot().getUsedClasses().getUsedClass((MethodOrFieldDesc) element);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof UsedClasses) {
            return true;
        } else if (element instanceof UsedClass) {
            return true;
        } else if (element instanceof DependencyRoot) {
            return true;
        } else if (element instanceof DependencyExtent) {
            return true;
        } else {
            return false;
        }
    }
}
