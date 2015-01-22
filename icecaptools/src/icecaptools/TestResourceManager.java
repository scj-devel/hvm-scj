package icecaptools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class TestResourceManager extends ResourceManager {

    private String vmSourceFolder;
    
    public TestResourceManager(String vmSourceFolder)
    {   
        this.vmSourceFolder = vmSourceFolder;
    }
    
    private class TestResourceIterator implements IcecapIterator<StreamResource> {
        private int top;
        private PrintStream out;

        public TestResourceIterator(PrintStream out) {
            top = 0;
        }

        @Override
        public boolean hasNext() {
            return top < getResourcesToLoad().length;
        }

        @Override
        public StreamResource next() {
            String resourceName = getResourcesToLoad()[top++];
            InputStream stream;
            String path = vmSourceFolder + File.separatorChar + resourceName;
            try { 
                stream = new FileInputStream(new File(path));
                StreamResource streamResource = new StreamResource(stream, resourceName);
                return streamResource;
            } catch (IOException e) {
                out.println("Could not open file: [" + path + "]");
                e.printStackTrace(out);
            }
            return null;
        }
    }

    @Override
    public IcecapIterator<StreamResource> getResources(PrintStream out) {
        return new TestResourceIterator(out);
    }

    @Override
    public StreamResource getResource(PrintStream out, String resourceName) {
        InputStream stream;
        try { 
            stream = new FileInputStream(new File(vmSourceFolder + File.separatorChar + resourceName));
            StreamResource streamResource = new StreamResource(stream, resourceName);
            return streamResource;
        } catch (IOException e) {
            e.printStackTrace(out);
        }
        return null;
    }
}
