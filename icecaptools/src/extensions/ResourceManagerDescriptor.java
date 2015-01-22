package extensions;


public class ResourceManagerDescriptor {

	private String[] resourceNames;
	private String bundleName;
	
	public ResourceManagerDescriptor(String bundleName,String ... resourceNames) {
		super();
		this.resourceNames = resourceNames;
		this.bundleName = bundleName;
	}

	public String getBundleName() {
		return bundleName;
	}
	public String[] getResourceNames() {
		return resourceNames;
	}
}
