package icecaptools;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MethodAndClass {

    private Method method;
    private JavaClass clazz;

    public MethodAndClass(Method method, JavaClass clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public JavaClass getClazz() {
        return clazz;
    }

}
