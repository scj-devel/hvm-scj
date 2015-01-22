package test;

import vm.Process;
import vm.ProcessLogic;
import icecaptools.IcecapCompileMe;

public class TestProcessTransfer2 {
    private static Process p1;
    private static Process p2;
    private static Process mainProcess;

    private static byte count;

    private static class P1 implements ProcessLogic {
        @Override
        @IcecapCompileMe
        public void run() {
            devices.Console.println("P1");
            p1.transferTo(p2);
        }
        @Override
        public void catchError(Throwable t) {
            devices.Console.println("Process: exception -> " + t);
        }
    }

    private static class P2 implements ProcessLogic {
        @Override
        @IcecapCompileMe
        public void run() {
            while (count < 10) {
                devices.Console.println("P2");
                count++;
                p1.initialize();
                p2.transferTo(p1);
            }
            p2.transferTo(mainProcess);
        }
        @Override
        public void catchError(Throwable t) {
            devices.Console.println("Process: exception -> " + t);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        p1 = new vm.Process(new P1(), new int[1024]);
        p2 = new vm.Process(new P2(), new int[1024]);

        mainProcess = new vm.Process(null, null);
        
        p1.initialize();
        p2.initialize();
        
        count = 0;
        
        mainProcess.transferTo(p1);

        if (count == 10) {
            args = null;
        }

        devices.Console.println("done");
    }

}
