package icecaptools.compiler.utils;

import org.apache.bcel.classfile.JavaClass;

public class SubClassChecker implements CheckSubClassRelationShip {
    public boolean isSubclassOf(JavaClass subClazz, JavaClass clazz) throws ClassNotFoundException {
        
        while (subClazz != null) {
            if (subClazz == clazz) {
                return true;
            }
            subClazz = subClazz.getSuperClass();
        }

        return false;
    }
}

