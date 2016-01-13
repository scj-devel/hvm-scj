package icecaptools.compiler;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import icecaptools.AnalysisObserver;
import icecaptools.MethodOrFieldDesc;

public class VirtualTable {

    ArrayList<MethodOrFieldDesc> methods;
    
    public VirtualTable()
    {
        methods = new ArrayList<MethodOrFieldDesc>();
    }
    
    public short getIndexOf(MethodOrFieldDesc nextMethod) {
        short index = (short) methods.indexOf(nextMethod);
        
        return index;
    }

    public void add(MethodOrFieldDesc nextMethod) {
        methods.add(nextMethod);
        
    }

    @Override
    public String toString() {
        Iterator<MethodOrFieldDesc> iterator = methods.iterator();
        StringBuffer output = new StringBuffer();
        while (iterator.hasNext())
        {
            MethodOrFieldDesc next = iterator.next();
            output.append(next.getClassName() + ": [" + next.getName() + next.getSignature() + "]\n");
        }
        return output.toString();
    }

    public void updateClassName(int index, String className) {
        methods.get(index).setClassName(className);
    }

    public int getSize() {
        return methods.size();
    }

    public Iterator<MethodOrFieldDesc> iterator() {
        return  methods.iterator();
    }

    public MethodOrFieldDesc getMethod(short index) {
        return methods.get(index);
    }
    
    public static VirtualTable createVTable(String clazzName, AnalysisObserver observer) throws Exception {
        JavaClass clazz = Repository.lookupClass(clazzName);
        if (clazz.isInterface())
        {
            throw new Exception("Unexpected interface");
        }
        JavaClass superClass = clazz.getSuperClass();
        VirtualTable vtable;

        if (superClass != null) {
            vtable = createVTable(superClass.getClassName(), observer);
        } else {
            vtable = new VirtualTable();
        }

        Method[] methods = clazz.getMethods();

        for (int i = 0; i < methods.length; i++) {
            Method current = methods[i];
            if (observer.isMethodUsed(clazzName, current.getName(), current.getSignature())) {
                MethodOrFieldDesc nextMethod = new MethodOrFieldDesc(clazz.getClassName(), current.getName(), current.getSignature());

                int index = vtable.getIndexOf(nextMethod);

                if (index == -1) {
                    vtable.add(nextMethod);
                } else {
                    vtable.updateClassName(index, clazz.getClassName());
                }
            }
        }
        return vtable;
    }
    
    public static short getVTableIndex(MethodOrFieldDesc referredMethod, AnalysisObserver observer) throws Exception {
        VirtualTable table = VirtualTable.createVTable(referredMethod.getClassName(), observer);
        short index = table.getIndexOf(referredMethod);
        return index;
    }

    public static void init() {
        
    }
}
