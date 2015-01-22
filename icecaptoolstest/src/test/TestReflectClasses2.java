package test;

import reflect.ClassInfo;
import reflect.ObjectInfo;
import util.ReferenceIterator;

public class TestReflectClasses2 {

    private static class A {
        @SuppressWarnings("unused")
        private B b1;
        @SuppressWarnings("unused")
        private B b2;

        A(B b1, B b2) {
            this.b1 = b1;
            this.b2 = b2;
        }
    }

    private static class B {
        @SuppressWarnings("unused")
        private Object x;

        public void setX(Object x) {
            this.x = x;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    public static boolean test() {
        B b1;
        B b2;
        b1 = new B();
        b2 = new B();

        A a = new A(b1, b2);

        b1.setX(a);
        b2.setX(b2);

        ObjectInfo oia = new ObjectInfo(a);

        short classId = oia.classId;

        ClassInfo cInfo = ClassInfo.getClassInfo(classId);

        StringBuffer name = cInfo.getName();

        if (name.toString().equals("test.TestReflectClasses2$A")) {
            ReferenceIterator referenceOffsets = cInfo.getReferenceOffsets(ObjectInfo.getAddress(a));
            int ib1 = oia.getRef((short) referenceOffsets.next());

            ObjectInfo oib1 = new ObjectInfo(ib1);

            short b1id = oib1.classId;

            cInfo = ClassInfo.getClassInfo(b1id);

            name = cInfo.getName();

            if (name.toString().equals("test.TestReflectClasses2$B")) {
                return false;
            }
        }
        return true;
    }

}
