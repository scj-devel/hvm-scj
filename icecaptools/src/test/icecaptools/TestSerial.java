package test.icecaptools;

import icecaptools.launching.arduino.ArduinoProcess;
import icecaptools.launching.arduino.ArduinoProcessLinuxHost;
import icecaptools.launching.uart.Serial;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class TestSerial {

    @Test
    public void testSerial() {
        Iterator<String> availablePorts = Serial.getAvailablePorts();
        
        String target = null;
        
        while (availablePorts.hasNext())
        {
            String nextPort = availablePorts.next();
            
            System.out.println("port: " + nextPort);
            if ("/dev/ttyS80".equalsIgnoreCase(nextPort))
            {
                target = nextPort;
            }
        }
        Assert.assertNotNull(target);
    }
    
    @Test
    public void testArduinoOutput()  {
        ArduinoProcess process = new ArduinoProcessLinuxHost(".");
        // String tmpFolder = System.getProperty("java.io.tmpdir");
        int result = process.connectProcessOutput(System.out);
        Assert.assertEquals(0, result);
        
        InputStream output = process.getInputStream();
        while (true)
        {
            try {
                System.out.print((char)output.read());
            } catch (IOException e) {
               Assert.fail();
            }
        }
    }
}
