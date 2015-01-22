package icecaptools.compiler.utils;

import java.util.HashMap;
import java.util.Iterator;

public class StaticInitializationAnnotationInfo {

    private static class ClassInfo
    {
        boolean initialized;
        String className;
        
        ClassInfo(String className)
        {
            this.className = className;
            initialized = false;
        }
    }
    
    public HashMap<String, ClassInfo> classInfo;

    public StaticInitializationAnnotationInfo() {
        classInfo = new HashMap<String, ClassInfo>();
    }

    public void classIsDefinatelyInitialized(String className) {
        if (classInfo.containsKey(className))
        {
            ClassInfo info = classInfo.get(className);
            info.initialized = true;
        } else
        {
            ClassInfo info = new ClassInfo(className);
            info.initialized = true;
            classInfo.put(className, info);
        }
    }

    public boolean isClassInitialized(String className) {
        if (classInfo.containsKey(className))
        {
            ClassInfo info = classInfo.get(className);
            return info.initialized;
        }
        else
        {
            return false;
        }
    }

    public boolean merge(StaticInitializationAnnotationInfo other) {
        boolean mergeRequired = false;
        Iterator<ClassInfo> infos = classInfo.values().iterator();
        while (infos.hasNext())
        {
            ClassInfo thisInfo = infos.next();
            ClassInfo otherInfo = other.getClassInfo(thisInfo.className);
            if (otherInfo != null)
            {
                if (thisInfo.initialized != otherInfo.initialized)
                {
                    mergeRequired = true;
                    thisInfo.initialized = false;
                }
                else
                {
                    ;
                }
            }
            else
            {
                if (thisInfo.initialized == true)
                {
                    mergeRequired = true;
                }
                thisInfo.initialized = false;
            }
        }
        
        infos = other.classInfo.values().iterator();
        while (infos.hasNext())
        {
            ClassInfo otherInfo = infos.next();
            if (!classInfo.containsKey(otherInfo.className))
            {
                classInfo.put(otherInfo.className, new ClassInfo(otherInfo.className));
                mergeRequired = true;
            }
        }
            
        return mergeRequired;
    }

    private ClassInfo getClassInfo(String className) {
        return classInfo.get(className);
    }

    public StaticInitializationAnnotationInfo copy() {
        StaticInitializationAnnotationInfo other = new StaticInitializationAnnotationInfo();
        Iterator<ClassInfo> infos = classInfo.values().iterator();
        while (infos.hasNext())
        {
            ClassInfo thisInfo = infos.next();
            ClassInfo otherInfo = new ClassInfo(thisInfo.className);
            otherInfo.initialized = thisInfo.initialized;
            other.classInfo.put(otherInfo.className, otherInfo);
        }
        return other;
    }
}
