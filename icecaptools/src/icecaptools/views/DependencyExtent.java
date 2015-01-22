package icecaptools.views;

public class DependencyExtent {
    
    private UsedClasses discoveredMethods;

    public DependencyExtent() {
        discoveredMethods = new UsedClasses();
    }

    public void add(String className, String targetMethodName, String targetMethodSignature) {
        discoveredMethods.add(className, targetMethodName, targetMethodSignature);
    }

    public UsedClasses getUsedClasses() {
        return discoveredMethods;
    }
}
