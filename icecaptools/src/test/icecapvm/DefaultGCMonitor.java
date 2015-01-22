package test.icecapvm;

import reflect.ClassInfo;
import reflect.ObjectInfo;
import gc.GCMonitor;

public class DefaultGCMonitor implements GCMonitor {

    private short objectsFreeed;

    public DefaultGCMonitor() {
        reset();
    }
    
    @Override
    public void freeObject(int ref) {
        objectsFreeed++;
    }

    @Override
    public void addStaticRoot(int ref) {
    }

    @Override
    public int getFreeedObjects() {
        return objectsFreeed;
    }

    @Override
    public void reset() {
        objectsFreeed = 0;
    }

    @Override
    public void addStackRoot(int ref) {
    }

    protected void printRef(int ref, String message) {
        ObjectInfo oinfo = new ObjectInfo(ref);
        short classid = oinfo.classId;
        ClassInfo cinfo = ClassInfo.getClassInfo(classid);
        devices.Console.println(cinfo.getName().toString());
    }

    @Override
    public void visitChild(int parent, int child) {
    }
}
