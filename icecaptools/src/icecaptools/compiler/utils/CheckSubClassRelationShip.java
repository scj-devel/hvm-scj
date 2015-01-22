package icecaptools.compiler.utils;

import org.apache.bcel.classfile.JavaClass;

public interface CheckSubClassRelationShip {
    public boolean isSubclassOf(JavaClass subClazz, JavaClass clazz) throws ClassNotFoundException;
}
