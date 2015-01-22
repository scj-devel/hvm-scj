package test;

import reflect.ClassInfo;
import vm.Address;

public class TestReflectClasses1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            failed = test();
            if (!failed) {
                args = null;
            }
        }
    }

    public static boolean test() {
        boolean foundIt = false;

        short numberOfClasses = ClassInfo.getNumberOfClasses();

        devices.Console.println("dumping " + numberOfClasses + " classes:");

        for (short index = 0; index < numberOfClasses; index++) {
            ClassInfo cInfo = ClassInfo.getClassInfo(index);

            StringBuffer buffer = new StringBuffer();
            buffer.append(cInfo.superClass);
            buffer.append(", ");
            buffer.append(cInfo.dimension);
            buffer.append(", ");
            buffer.append(cInfo.hasLock);
            buffer.append(", ");
            buffer.append(cInfo.dobjectSize);
            buffer.append(", ");
            buffer.append(cInfo.pobjectSize);
            buffer.append(", ");
            appendRef(cInfo.getInterfacesRef(), buffer);

            buffer.append(", \"");
            StringBuffer className = cInfo.getName();

            if (className.toString().equals("test.TestReflectClasses1")) {
                foundIt = true;
                devices.Console.println("Found it at index " + index);
            }
            buffer.append(cInfo.getName());
            buffer.append("\"");

            devices.Console.println(buffer.toString());
        }
        return (foundIt == false);
    }

    protected static void appendRef(Address address, StringBuffer buffer) {
        if (address.isNull()) {
            buffer.append(", 0");
        } else {
            buffer.append(", ref");
        }
    }
}
