package icecaptools.stackanalyser;

import icecaptools.RawByteCodes.RawBytecode;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class FlashArrayLocation {

    private RawBytecode pusher;
    private Method javaMethod;
    private JavaClass clazz;

    public FlashArrayLocation(RawBytecode pusher, Method javaMethod, JavaClass clazz) {
        this.pusher = pusher;
        this.javaMethod = javaMethod;
        this.clazz = clazz;
    }

    public RawBytecode getPusher() {
        return pusher;
    }

    public Method getJavaMethod() {
        return javaMethod;
    }

    public JavaClass getClazz() {
        return clazz;
    }

}
