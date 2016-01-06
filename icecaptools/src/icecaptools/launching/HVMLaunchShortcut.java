package icecaptools.launching;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;

public class HVMLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(ISelection selection, String mode) {
		System.out.println("launch iselection");
	}

	@Override
	public void launch(IEditorPart delection, String mode) {
		System.out.println("launch editorpart");
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection arg0) {
		System.out.println("getLaunchConfigurations");
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart arg0) {
		System.out.println("getLaunchConfigurations");
		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection arg0) {
		System.out.println("getLaunchableResource");
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart arg0) {
		System.out.println("getLaunchableResource");
		return null;
	}
	 
}
