package icecaptools.views;

import icecaptools.MethodOrFieldDesc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class UsedClasses {

    private HashMap<String, UsedClass> usedClasses;

    public UsedClasses() {
        usedClasses = new HashMap<String, UsedClass>();
    }

    public void add(String className, String targetMethodName, String targetMethodSignature) {
        UsedClass usedClass = usedClasses.get(className);
        if (usedClass == null) {
            usedClass = new UsedClass(className);
            usedClasses.put(className, usedClass);
        }
        usedClass.add(targetMethodName, targetMethodSignature);
    }

    public Object[] toArray() {
        UsedClass[] classes = new UsedClass[usedClasses.size()];
        Set<Entry<String, UsedClass>> entries = usedClasses.entrySet();
        Iterator<Entry<String, UsedClass>> iterator = entries.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Entry<String, UsedClass> next = iterator.next();
            classes[i++] = next.getValue();
        }

        sort(classes);

        return classes;
    }

    private void sort(UsedClass[] classes) {
        int top = 0;
        while (top < classes.length - 1) {
            UsedClass arg0 = classes[top];
            UsedClass arg1 = classes[top + 1];
            if (arg0.getClassName().compareTo(arg1.getClassName()) < 0) {
                top++;
            }
            else
            {
                UsedClass temp = classes[top];
                classes[top] = classes[top+1];
                classes[top+1] = temp;
                if (top > 0)
                {
                    top--;
                }
            }
        }
    }

    public UsedClass getUsedClass(MethodOrFieldDesc element) {
        return usedClasses.get(element.getClassName());
    }

    public int getSize() {
        return usedClasses.size();
    }
}
