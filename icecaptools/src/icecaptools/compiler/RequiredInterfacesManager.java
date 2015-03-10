package icecaptools.compiler;

public class RequiredInterfacesManager extends RequiredEntryManager {

    public RequiredInterfacesManager(boolean supportLoading)
    {
    	super(supportLoading);
        requiredEntries.add("java.security.PrivilegedAction");
        requiredEntries.add("devices.Handler");
        requiredEntries.add("java.lang.Runnable");
    }
}
