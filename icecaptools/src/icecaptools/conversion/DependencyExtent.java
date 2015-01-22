package icecaptools.conversion;

import icecaptools.BNode;
import icecaptools.MethodEntryPoints;
import icecaptools.compiler.utils.MethodMap;

import java.util.ArrayList;
import java.util.Iterator;

public class DependencyExtent {

    private MethodMap<MethodEntryPoints> extent;

    public DependencyExtent()
    {
        extent = new MethodMap<MethodEntryPoints>();
    }
    
    public void insertMethod(MethodEntryPoints value, String className, String targetMethodName, String targetMethodSignature) {
        extent.put(className, targetMethodName, targetMethodSignature, value);
    }

    public MethodEntryPoints getMethod(String className, String targetMethodName, String targetMethodSignature) {
        return extent.get(className, targetMethodName, targetMethodSignature);
    }
    
    public Iterator<MethodEntryPoints> getMethods() {
        return extent.getValues();
    }
    
    public BNode getBNodeFromMethod(String className, String methodName, String methodSignature, int address) {
        MethodEntryPoints entryPoints = getMethod(className, methodName, methodSignature);
        if (entryPoints != null) {
            ArrayList<BNode> bnodes = entryPoints.getBNodes();
            for (BNode bNode : bnodes) {
                if (bNode.getAddress() == address) {
                    return bNode;
                }
            }
        }
        return null;
    }
}
