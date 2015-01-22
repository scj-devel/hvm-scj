package icecaptools.views;

import icecaptools.Activator;
import icecaptools.MethodOrFieldDesc;
import icecaptools.SetOutputFolderAction;
import icecaptools.compiler.CompilationRegistry;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class MethodListLabelProvider implements ILabelProvider {

    private static Image class_obj_int;
    private static Image method_obj_int;
    private static Image class_obj_excluded;
    private static Image method_obj_excluded;
    private static Image class_obj_comp;
    private static Image method_obj_comp;

    private CompilationRegistry cregistry;

    static {
        ImageDescriptor image;
        URL url;
        try {
            url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/method_obj_int.gif");
            image = ImageDescriptor.createFromURL(url);

            method_obj_int = image.createImage();
        } catch (MalformedURLException e) {
        }
        try {
            url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/method_obj_excluded.gif");
            image = ImageDescriptor.createFromURL(url);

            method_obj_excluded = image.createImage();
        } catch (MalformedURLException e) {
        }
        try {
            url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/class_obj_int.gif");
            image = ImageDescriptor.createFromURL(url);

            class_obj_int = image.createImage();
        } catch (MalformedURLException e) {
        }
        try {
            url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/class_obj_excluded.gif");
            image = ImageDescriptor.createFromURL(url);

            class_obj_excluded = image.createImage();
        } catch (MalformedURLException e) {
        }
        try {
            url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/method_obj_comp.gif");
            image = ImageDescriptor.createFromURL(url);

            method_obj_comp = image.createImage();
        } catch (MalformedURLException e) {
        }

        try {
            url = new URL(Activator.getDefault().getBundle().getEntry("/"), "icons/class_obj_comp.gif");
            image = ImageDescriptor.createFromURL(url);

            class_obj_comp = image.createImage();
        } catch (MalformedURLException e) {
        }

    }

    public MethodListLabelProvider(CompilationRegistry cregistry) {
        this.cregistry = cregistry;
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    @Override
    public void dispose() {
        ;
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof MethodOrFieldDesc) {
            if (cregistry.isMethodExcluded((MethodOrFieldDesc) element)) {
                return method_obj_excluded;
            }
            if (cregistry.isMethodCompiled((MethodOrFieldDesc) element)) {
                return method_obj_comp;
            } else {
                return method_obj_int;
            }
        } else if (element instanceof UsedClass) {
            UsedClass usedClass = (UsedClass) element;
            Iterator<MethodOrFieldDesc> methods = usedClass.getUsedMethods();
            while (methods.hasNext()) {
                MethodOrFieldDesc next = methods.next();
                if (cregistry.isMethodExcluded(next)) {
                    return class_obj_excluded;
                }
            }
            methods = usedClass.getUsedMethods();
            while (methods.hasNext()) {
                MethodOrFieldDesc next = methods.next();
                if (cregistry.isMethodCompiled(next)) {
                    return class_obj_comp;
                }
            }
            return class_obj_int;
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof MethodOrFieldDesc) {
            MethodOrFieldDesc mdesc = (MethodOrFieldDesc) element;
            String signature = mdesc.getSignature();

            StringBuffer buffer = new StringBuffer();

            String sig = Signature.getReturnType(signature);

            buffer.append(Signature.toString(sig));
            buffer.append(" ");
            buffer.append(mdesc.getName());
            buffer.append("(");

            int parameterCount = Signature.getParameterCount(signature);
            String[] parameters = Signature.getParameterTypes(signature);

            for (int i = 0; i < parameterCount; i++) {
                buffer.append(Signature.toString(parameters[i]));
                if (i + 1 < parameterCount) {
                    buffer.append(", ");
                }
            }
            buffer.append(")");
            return buffer.toString();
        } else if (element instanceof UsedClass) {
            UsedClass usedClass = (UsedClass) element;
            int size = usedClass.getSize();
            return usedClass.getClassName() + " (" + size + ")";
        } else if (element instanceof DependencyExtent) {
            DependencyExtent extent = (DependencyExtent) element;
            int size = extent.getUsedClasses().getSize();
            String outputFolder = SetOutputFolderAction.getOutputFolder();

            if ((outputFolder != null) && (outputFolder.trim().length() > 0)) {
                outputFolder = " (output: " + outputFolder + ")";
            } else {
                outputFolder = "";
            }

            if (cregistry.alwaysClearOutputFolder())
            {
            	outputFolder += " - always clear folder";
            }
            else
            {
            	outputFolder += " - never clear folder";
            }
            return "Dependency Extent (" + size + " elements)" + outputFolder;
        } else {
            return element.getClass().getName();
        }
    }
}
