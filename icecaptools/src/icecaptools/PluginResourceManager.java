package icecaptools;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class PluginResourceManager extends ResourceManager {
	
	
	private String pluginName;
	
	public PluginResourceManager(String pluginName, String [] resources) {
		this.pluginName = pluginName;
		this.setResourcesToLoad(resources);
	}
	
	public PluginResourceManager() {
		this.pluginName = Activator.PLUGIN_ID;
	}
    


    public class PluginResourceIterator implements IcecapIterator<StreamResource> {
        private int top;
        private Bundle bundle;
        private PrintStream out;

        public PluginResourceIterator(PrintStream out) {
            bundle = Platform.getBundle(pluginName);
            this.out = out;
            if (bundle == null) {
                top = getResourcesToLoad().length;
            } else {            	
                top = 0;
            }
        }

		@Override
        public boolean hasNext() {
            return top < getResourcesToLoad().length;
        }

        @Override
        public StreamResource next() {
            String resourceName = getResourcesToLoad()[top++];
            URL resource = bundle.getResource("resources/" + resourceName);
            InputStream stream;
            try {
                stream = resource.openStream();
                StreamResource streamResource = new StreamResource(stream, resourceName);
                return streamResource;
            } catch (IOException e) {
                e.printStackTrace(out);
            }
            return null;
        }
    }

    @Override
    public IcecapIterator<StreamResource> getResources(PrintStream out) {
        return new PluginResourceIterator(out);
    }

    @Override
    public StreamResource getResource(PrintStream out, String resourceName) {
        Bundle bundle = Platform.getBundle(this.pluginName);
        URL resource = bundle.getResource("resources/" + resourceName);
        InputStream stream;
        try {
            stream = resource.openStream();
            StreamResource streamResource = new StreamResource(stream, resourceName);
            return streamResource;
        } catch (IOException e) {
            e.printStackTrace(out);
        }
        return null;
    }
}
