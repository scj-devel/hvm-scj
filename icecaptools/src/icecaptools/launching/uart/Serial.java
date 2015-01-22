package icecaptools.launching.uart;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

import gnu.io.CommPortIdentifier;

public class Serial {

    private static LinkedList<String> ports;

    @SuppressWarnings("unchecked")
    private static void findPorts() {
        ports = new LinkedList<String>();

        Enumeration<CommPortIdentifier> portList = (Enumeration<CommPortIdentifier>) CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                ports.add(portId.getName());
            }
        }
    }

    public static Iterator<String> getAvailablePorts() {
        findPorts();
        return ports.iterator();
    }
}
