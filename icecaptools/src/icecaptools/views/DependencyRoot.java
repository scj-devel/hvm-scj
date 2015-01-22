package icecaptools.views;

public class DependencyRoot {

    DependencyExtent extent;
    
    public DependencyRoot()
    {
        extent = new DependencyExtent();
    }

    public void add(String className, String targetMethodName, String targetMethodSignature) {
        extent.add(className, targetMethodName, targetMethodSignature);
    }

    public DependencyExtent getRoot() {
        return extent;
    }

}
