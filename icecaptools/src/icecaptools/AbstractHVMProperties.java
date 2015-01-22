package icecaptools;

public abstract class AbstractHVMProperties implements HVMProperties {
    
    protected String trimAndClean(String property) {
        if (property.startsWith("\"")) {
            return trimAndClean(property.substring(1));
        } else if (property.endsWith("\"")) {
            return trimAndClean(property.substring(0, property.length() - 1));
        } else if (property.startsWith(" ")) {
            return trimAndClean(property.trim());
        } else if (property.endsWith(" ")) {
            return trimAndClean(property.trim());
        } else {
            return property;
        }
    }
}
