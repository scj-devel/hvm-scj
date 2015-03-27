package icecaptools;

import java.util.Properties;

public class HVMLoadedProperties extends AbstractHVMProperties {

    private Properties props;

    public HVMLoadedProperties(Properties props) {
        this.props = props;
    }

    public String getProgmemStart() {
        return getProp("PROGMEMSTART");
    }

    public String getProgmemEnd() {
        return getProp("PROGMEMEND");
    }

    private String getProp(String key) {
        String prop = props.getProperty(key);
        if (prop != null) {
            return trimAndClean(props.getProperty(key));
        } else {
            return "";
        }
    }

    public String getNewlineSequence() {
        String prop = getProp("NEWLINESEQ");
        if (prop.length() > 0) {
            return prop;
        } else {
            return "\n";
        }
    }

    public boolean includeMethodAndClassNames() {
        String prop = getProp("INCLUDENAMES");
        if (prop.length() > 0) {
            if (prop.equalsIgnoreCase("false")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isIncludeJMLMethods() {
        String prop = getProp("INCLUDEJMLMETHODS");
        if (prop.length() > 0) {
            if (prop.equalsIgnoreCase("true")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setIncludeJMLMethods(boolean includeJMLMethods) {
    }

	@Override
	public String getProperty(String key) {
		return props.getProperty(key);
	}
}
