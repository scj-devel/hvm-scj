package icecaptools;

import java.io.InputStream;

public class StreamResource {

    InputStream stream;

    String resourceName;
    
    public StreamResource(InputStream stream, String resourceName) {
        this.stream = stream;
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public InputStream getStream() {
        return stream;
    }

}
