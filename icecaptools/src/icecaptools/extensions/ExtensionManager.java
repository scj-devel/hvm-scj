package icecaptools.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public abstract class ExtensionManager<Extension> {

	private List<Extension> extensions;

	public ExtensionManager() {
		extensions = this.createExtensions();
	}

	@SuppressWarnings("unchecked")
	private List<Extension> createExtensions() {
		List<Extension> out = new ArrayList<Extension>();

		IConfigurationElement[] configurationElements = Platform
				.getExtensionRegistry().getConfigurationElementsFor(
						getCodeGeneratorExtensionPoint());
		for (IConfigurationElement element : configurationElements) {
			try {
				Object executableExtension = element
						.createExecutableExtension(getCodeGeneratorExtensionElement());
				out.add(((Extension) executableExtension));
			} catch (CoreException e) {
				// TODO Skip and proccess another element?;
				e.printStackTrace();
			}
		}
		return out;
	}
	
	public List<Extension> getExtensions() {
		return extensions;
	}

	/**
	 * The extension point is something like: Activator.PLUGIN_ID + "." +
	 * ExtensionPointName;
	 * 
	 * @return the extension point
	 * */
	protected abstract String getCodeGeneratorExtensionPoint();

	/**
	 * The extension point element with the implementation class :
	 * 
	 * @return the extension element
	 * */
	protected abstract String getCodeGeneratorExtensionElement();

}
