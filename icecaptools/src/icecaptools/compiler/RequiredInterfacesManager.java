package icecaptools.compiler;

public class RequiredInterfacesManager extends RequiredEntryManager {

    public RequiredInterfacesManager()
    {
        requiredEntries.add("java.security.PrivilegedAction");
        requiredEntries.add("devices.Handler");
        requiredEntries.add("java.lang.Runnable");
    }
}
