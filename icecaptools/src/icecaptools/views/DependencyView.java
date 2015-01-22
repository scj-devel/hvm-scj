package icecaptools.views;

import icecaptools.CanceledByUserException;
import icecaptools.IcecapIterator;
import icecaptools.MethodOrFieldDesc;
import icecaptools.RestartableMethodObserver;
import icecaptools.SetOutputFolderAction;
import icecaptools.compiler.CompilationRegistry;
import icecaptools.compiler.ICompilationRegistry;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class DependencyView extends ViewPart implements RestartableMethodObserver, DELabelProvider, IDoubleClickListener {
    private TreeViewer viewer;

    public static final String VIEWID = "icecaptools.views.dependencyextent";

    private DependencyRoot root;

    private IMethod prototype;

    private CompilationRegistry cregistry;

    private static final String COMPILATION_REGISTRY_STORE = "CompilationRegistry";

    private HashMap<String, String> addedElements;

    @Override
    public void createPartControl(Composite parent) {
        addedElements = new HashMap<String, String>();
        cregistry = new CompilationRegistry();
        viewer = new TreeViewer(parent);
        ITreeContentProvider contentProvider = new MethodListContentProvider();
        viewer.setContentProvider(contentProvider);
        ILabelProvider labelProvider = new MethodListLabelProvider(cregistry);
        viewer.setLabelProvider(labelProvider);

        viewer.addDoubleClickListener(this);

        MenuManager menuMgr = new MenuManager("PopupMenu"); //$NON-NLS-1$
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                Action toggleAction = new Action() {
                    public void run() {
                        super.run();
                        togglePropertyOnSelection(new CompilationToggler());
                    }
                };
                toggleAction.setText("Toggle Compilation");
                manager.add(toggleAction);

                Action setOutputFolderAction = new Action() {
                    public void run() {
                        super.run();
                        setOutputFolder();
                    }

                };
                setOutputFolderAction.setText("Set output folder");
                manager.add(setOutputFolderAction);

                Action excludeMethodAction = new Action() {
                    public void run() {
                        super.run();
                        togglePropertyOnSelection(new ExclusionToggler());
                    }
                };
                excludeMethodAction.setText("Toggle exclusion");
                manager.add(excludeMethodAction);

                Action alwaysClearOutputFolder = new Action() {
                    public void run() {
                        super.run();
                        togglePropertyOnSelection(new ClearOutputFolderToggler());
                    }
                };
                alwaysClearOutputFolder.setText("Clear output folder");
                manager.add(alwaysClearOutputFolder);
            }
        });

        Menu menu = menuMgr.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);

    }

    private interface PropertyToggler {

        void toggleMethod(MethodOrFieldDesc next);
        void toggleRootProperty();
        
    }

    private class CompilationToggler implements PropertyToggler {
        @Override
        public void toggleMethod(MethodOrFieldDesc next) {
            cregistry.toggleMethodCompilation(next);
        }

        @Override
        public void toggleRootProperty() {
            
        }
    }

    private class ExclusionToggler implements PropertyToggler {
        @Override
        public void toggleMethod(MethodOrFieldDesc next) {
            cregistry.toggleMethodExclusion(next);
        }

        @Override
        public void toggleRootProperty() {
            
        }
    }

    private class ClearOutputFolderToggler implements PropertyToggler {

        @Override
        public void toggleMethod(MethodOrFieldDesc next) {
        }

        @Override
        public void toggleRootProperty() {
            cregistry.toggleFolderClearing();
        }

    }

    private void togglePropertyOnSelection(PropertyToggler toggler) {
        ISelection selection = viewer.getSelection();
        if (selection instanceof TreeSelection) {
            TreeSelection tselection = (TreeSelection) selection;
            @SuppressWarnings("rawtypes")
            Iterator selectedElements = tselection.iterator();
            while (selectedElements.hasNext()) {
                Object next = selectedElements.next();
                if (next instanceof UsedClass) {
                    UsedClass usedClass = (UsedClass) next;
                    Iterator<MethodOrFieldDesc> methods = usedClass.getUsedMethods();
                    while (methods.hasNext()) {
                        toggler.toggleMethod(methods.next());
                    }

                } else if (next instanceof MethodOrFieldDesc) {
                    MethodOrFieldDesc mdesc = (MethodOrFieldDesc) next;
                    toggler.toggleMethod(mdesc);
                } else if (next instanceof DependencyExtent) {
                    toggler.toggleRootProperty();
                }
            }
        }
        refresh();
    }

    private void setOutputFolder() {
        DirectoryDialog dialog = new DirectoryDialog(getSite().getShell(), SWT.OPEN);
        String folder = dialog.open();
        SetOutputFolderAction.setOutputFolder(folder);
    }

    @Override
    public void setFocus() {

    }

    @Override
    public void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature, boolean report) throws CanceledByUserException {

        addToRoot(className, targetMethodName, targetMethodSignature);

    }

    private void addToRoot(String className, String targetMethodName, String targetMethodSignature) {
        StringBuffer key = new StringBuffer();
        key.append(className);
        key.append(targetMethodName);
        key.append(targetMethodSignature);

        String keyString = key.toString();

        if (!addedElements.containsKey(keyString)) {
            root.add(className, targetMethodName, targetMethodSignature);
            addedElements.put(keyString, keyString);
        }
    }

    @Override
    public void restart() {
        addedElements = new HashMap<String, String>();
        root = new DependencyRoot();
        IcecapIterator<MethodOrFieldDesc> excludedMethods = cregistry.getExcludedMethods();

        while (excludedMethods.hasNext()) {
            MethodOrFieldDesc next = excludedMethods.next();
            addToRoot(next.getClassName(), next.getName(), next.getSignature());
        }

        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                viewer.setInput(root);
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                viewer.refresh();
            }
        });
    }

    @Override
    public void setLabelSource(IMethod prototype) {
        if (this.prototype != prototype) {
            saveCompilationPreferencesToStore();
            this.prototype = prototype;
            loadCompilationPreferences();
        }
    }

    @Override
    public IMethod getLabelSource() {
        return prototype;
    }

    @Override
    public void doubleClick(DoubleClickEvent event) {
        ISelection selection = viewer.getSelection();
        if (selection instanceof TreeSelection) {
            TreeSelection tselection = (TreeSelection) selection;
            Object fselection = tselection.getFirstElement();
            if (fselection instanceof UsedClass) {
                UsedClass selectedClass = (UsedClass) fselection;
                openJavaClassInEditor(selectedClass.getClassName());
            } else if (fselection instanceof MethodOrFieldDesc) {
                MethodOrFieldDesc selectedMethod = (MethodOrFieldDesc) fselection;
                openJavaMethodInEditor(selectedMethod);
            }
        }
    }

    private void openJavaMethodInEditor(MethodOrFieldDesc selectedMethod) {
        IJavaProject project = prototype.getJavaProject();

        try {
            IType element = project.findType(selectedMethod.getClassName());
            IMethod[] methods = element.getMethods();
            for (IMethod iMethod : methods) {
                if (iMethod.getElementName().equals(selectedMethod.getName())) {
                    if (compareReturnType(element.isBinary(), iMethod.getReturnType(), selectedMethod.getSignature())) {
                        if (compareParameters(element.isBinary(), iMethod.getParameterTypes(), selectedMethod.getSignature())) {
                            try {
                                JavaUI.openInEditor(iMethod);
                                return;
                            } catch (PartInitException e) {
                            }
                        }
                    }
                }
            }
        } catch (JavaModelException e) {
        }
    }

    private boolean compareParameters(boolean isBinary, String[] parameterTypes, String methodSignature) {
        String[] pTypes = Signature.getParameterTypes(methodSignature);
        if (pTypes.length == parameterTypes.length) {
            for (int i = 0; i < pTypes.length; i++) {
                String current = pTypes[i].replace('/', '.');
                if (isBinary) {
                    if (!current.equals(parameterTypes[i])) {
                        return false;

                    }
                } else {
                    String p1 = current;
                    String p2 = parameterTypes[i];

                    p1 = Signature.toString(p1);
                    p2 = Signature.toString(p2);

                    return p1.endsWith(p2);
                }
            }
            return true;
        }
        return false;
    }

    private boolean compareReturnType(boolean isBinary, String returnType, String methodSignature) {
        String rType = Signature.getReturnType(methodSignature);
        rType = rType.replace('/', '.');
        return returnType.equals(rType);
    }

    private void openJavaClassInEditor(String className) {
        IJavaProject project = prototype.getJavaProject();
        try {
            IType element = project.findType(className);
            JavaUI.openInEditor(element);
        } catch (JavaModelException e) {
        } catch (PartInitException e) {
        }
    }

    private void loadCompilationPreferences() {
        IJavaProject project = prototype.getJavaProject();
        IScopeContext projectScope = new ProjectScope(project.getProject());
        Preferences projectNode = projectScope.getNode(VIEWID);
        if (projectNode != null) {
            String value = projectNode.get(COMPILATION_REGISTRY_STORE, null);
            if (value != null) {
                cregistry.initializeFromString(value);
            }
        }
    }

    private void saveCompilationPreferencesToStore() {
        if (prototype != null) {
            IJavaProject project = prototype.getJavaProject();
            IScopeContext projectScope = new ProjectScope(project.getProject());
            Preferences projectNode = projectScope.getNode(VIEWID);
            if (projectNode != null) {
                projectNode.put(COMPILATION_REGISTRY_STORE, cregistry.encodeToString());
                try {
                    projectNode.flush();
                } catch (BackingStoreException e) {
                }
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        saveCompilationPreferencesToStore();
    }

    @Override
    public ICompilationRegistry getCompilationRegistry() {
        return this.cregistry;
    }
}
