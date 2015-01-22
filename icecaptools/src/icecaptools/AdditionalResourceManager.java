package icecaptools;

import icecaptools.extensions.ExtensionManager;
import extensions.ResourceManagerDescriptor;

public class AdditionalResourceManager extends ExtensionManager<ResourceManagerDescriptor> {

	@Override
	protected String getCodeGeneratorExtensionPoint() {
		return Activator.PLUGIN_ID + ".AdditionalResourceManager";
	}

	@Override
	protected String getCodeGeneratorExtensionElement() {
		return "class";
	}

	public ResourceManager createResorceManager() {
		PluginResourceManager manager = new PluginResourceManager();
		if(this.getExtensions().isEmpty()) {
			return manager;
		}
		CompositeResourceManager composite = new CompositeResourceManager();
		composite.addResourceManager(manager);
		
	    for(ResourceManagerDescriptor descriptor : this.getExtensions()) {
			composite.addResourceManager(new PluginResourceManager(descriptor.getBundleName(), descriptor.getResourceNames()));
		}
	    return composite;
	}
	
}