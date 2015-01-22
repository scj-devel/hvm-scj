package test;

import reflect.MethodInfo;
import vm.Memory;

public class TestReflectMethods {

    public static void main(String[] args) {
        short numberOfMethods = MethodInfo.getNumberOfMethods();

        devices.Console.println("numberOfMethods = " + numberOfMethods);
    
        boolean found = false;
        
        for (short index = 0; index < numberOfMethods; index++) {
            MethodInfo mInfo = MethodInfo.getMethodInfo(index);
            if (mInfo.getName(index).equals("test.TestReflectMethods.main"))
            {
                found = true;
            }
            devices.Console.println(mInfo.getName(index));
        }

        if (!found)
        {
            devices.Console.println("Did not find expected method");
            return;
        }
        
        Memory mainArea = Memory.getHeapArea();

        found = false;
        int waterMark = mainArea.consumedMemory();
        for (short index = 0; index < numberOfMethods; index++) {
            MethodInfo mInfo = MethodInfo.getMethodInfo(index);
            if (mInfo.getName(index).equals("test.TestReflectMethods.main"))
            {
                found = true;
            }
        }
        
        if (!found)
        {
            devices.Console.println("Did not find expected method");
            return;
        }
        
        int memoryConsumed = mainArea.consumedMemory() - waterMark;
        if (memoryConsumed > 0)
        {
            devices.Console.println("Consumed too much memory: " + memoryConsumed);
        }
        else
        {
            args = null;
        }
    }
}
