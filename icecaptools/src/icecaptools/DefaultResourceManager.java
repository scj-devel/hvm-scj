package icecaptools;

import java.io.PrintStream;

public class DefaultResourceManager extends ResourceManager {

    private static class EmptyIterator implements IcecapIterator<StreamResource>
    {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public StreamResource next() {
            return null;
        }
    }
    
    @Override
    public IcecapIterator<StreamResource> getResources(PrintStream out) {
        return new EmptyIterator();
    }

    @Override
    public StreamResource getResource(PrintStream out, String resourceName) {
        return null;
    }

}
