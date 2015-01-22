package icecaptools;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CompositeResourceManager extends ResourceManager {

	private List<ResourceManager> inners = new ArrayList<ResourceManager>();
	
    @Override
	public IcecapIterator<StreamResource> getResources(PrintStream out) {
		return new CompositeResourceManagerIterator(inners, out);
	}
	
	public void addResourceManager(ResourceManager r) {
		inners.add(r);
	}

	@Override
	public StreamResource getResource(PrintStream out, String resourceName) {
		for (ResourceManager rm : inners) {
			StreamResource resource = rm.getResource(out, resourceName);
			if(resource != null) {
				return resource;
			}
		}
		return null; 
	}

	@Override
	protected void setResourcesToLoad(String[] resources) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String[] getResourcesToLoad() {
		throw new UnsupportedOperationException();
	}

    protected class CompositeResourceManagerIterator implements IcecapIterator<StreamResource> {

		Iterator<ResourceManager> resourcesManagerIt;
		IcecapIterator<StreamResource> current;
		private PrintStream out;
		
		CompositeResourceManagerIterator(List<ResourceManager> resourcesManager, PrintStream out){
			this.out = out;
			resourcesManagerIt = resourcesManager.iterator();
		}
		
		public boolean hasNext() {
			while(current == null || (!current.hasNext() && resourcesManagerIt.hasNext())) {
				current = resourcesManagerIt.next().getResources(out);
			};
			return current != null && current.hasNext();
		}

		public StreamResource next() {
			return current.next();
		}	
	}
}
