package icecaptools.views;

import icecaptools.MethodOrFieldDesc;

import java.util.ArrayList;
import java.util.Iterator;

public class UsedClass {

    private String className;
    
    private ArrayList<MethodOrFieldDesc> methods;
    
    public UsedClass(String className)
    {
        methods = new ArrayList<MethodOrFieldDesc>();
        this.className = className;
    }
    
    public void add(String targetMethodName, String targetMethodSignature) {
        methods.add(new MethodOrFieldDesc(className, targetMethodName, targetMethodSignature));
    }

    public Object[] toArray() {
        return methods.toArray();
    }

    public String getClassName() {
        return className;
    }

    public int getSize() {
        return methods.size();
    }

    public Iterator<MethodOrFieldDesc> getUsedMethods() {
        return methods.iterator();
    }
}
