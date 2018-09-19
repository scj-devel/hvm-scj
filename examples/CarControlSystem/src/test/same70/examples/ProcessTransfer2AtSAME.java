package test.same70.examples;


import test.same70.configuration.SCJTargetConfigurationSAME;
import vm.Machine;
import vm.MachineFactory;
import vm.Process;
import vm.ProcessLogic;

public class ProcessTransfer2AtSAME extends SCJTargetConfigurationSAME {
    private static Process p1;
    private static Process p2;
    private static Process mainProcess;

    private static byte count;

    private static class P1 implements ProcessLogic {
    	P1() {
    		devices.Console.println("P1 constructor called");
    	}
        @Override
        public void run() {
        	devices.Console.println("P1.run");
            p1.transferTo(p2);
        }
        @Override
        public void catchError(Throwable t) {
        	blink(1000);
        	devices.Console.println("p1: " + t);
        }
    }

    private static class P2 implements ProcessLogic {
    	P2() {
    		devices.Console.println("P2 constructor called");
    	}
        @Override
        public void run() {
        	devices.Console.println("P2.run");
            while (count < 10) {
                count++;
                p1.initialize();
                p2.transferTo(p1);
            }
            p2.transferTo(mainProcess);
        }
        @Override
        public void catchError(Throwable t) {
        	blink(1000);
        	devices.Console.println("p2: " + t);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    	
    	init();

		devices.Console.println("ProcessTransfer2AtSAME begin");
		
		MachineFactory mFac = Machine.getMachineFactory();		
		mFac.startSystemTick();
		
		blink (2000);
		
        p1 = new vm.Process(new P1(), new int[getReasonableProcessStackSize()]);
        p2 = new vm.Process(new P2(), new int[getReasonableProcessStackSize()]);

        mainProcess = new vm.Process(null, null);
        
        p1.initialize();
        p2.initialize();
        
        devices.Console.println("ProcessTransfer2AtSAME: processes running");
        count = 0;
        
        mainProcess.transferTo(p1);
        
        devices.Console.println("ProcessTransfer2AtSAME: count = " + count);
        
        if (count == 10) {
        	blink(5000);
        }
        else
        {
        	blink(2000);
        }
        devices.Console.println("ProcessTransfer2AtSAME end");
    }
}
