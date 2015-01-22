package icecaptools;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.StringTokenizer;

public class PropertyManager implements HVMProperties {

    private HVMProperties loadedProps;
    private boolean includeJMLMethods;

    @Override
    public String getProgmemStart() {
        return loadedProps.getProgmemStart();
    }

    @Override
    public String getProgmemEnd() {
        return loadedProps.getProgmemEnd();
    }

    @Override
    public String getNewlineSequence() {
        return loadedProps.getNewlineSequence();
    }

    @Override
    public boolean includeMethodAndClassNames() {
        return loadedProps.includeMethodAndClassNames();
    }

    public void reloadLoadedProperties(String inputSourceFileName) throws Exception {

        if (inputSourceFileName != null) {
            StringTokenizer tokenizer = new StringTokenizer(inputSourceFileName, File.separatorChar + "");
            StringBuffer propertiesFileName = new StringBuffer();

            propertiesFileName.append(File.separatorChar);
            while (tokenizer.hasMoreTokens()) {
                String nextSegment = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    propertiesFileName.append(nextSegment);
                    propertiesFileName.append(File.separatorChar);
                }
            }

            propertiesFileName.append("hvm.properties");

            File propertiesFile = new File(propertiesFileName.toString());

            if (propertiesFile.exists()) {
                if (propertiesFile.canRead()) {
                    Properties props = new Properties();
                    try {
                        props.load(new FileInputStream(propertiesFile));
                        loadedProps = new HVMLoadedProperties(props);
                        return;
                    } catch (Exception e) {
                        throw new Exception("Unable to load from existing properties file at [" + propertiesFileName.toString() + "]");
                    }
                }
            }
            loadedProps = new HVMLoadedProperties(new Properties());
        } else {
            loadedProps = new HVMLoadedProperties(new Properties());
        }
    }

    @Override
    public boolean isIncludeJMLMethods() {
        return this.includeJMLMethods || loadedProps.isIncludeJMLMethods();
    }

    @Override
    public void setIncludeJMLMethods(boolean includeJMLMethods) {
        this.includeJMLMethods = includeJMLMethods;
    }
}
