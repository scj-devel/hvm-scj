package test.same70.examples;


import icecaptools.IcecapCompileMe;
import test.same70.configuration.SCJTargetConfigurationSAME;
import vm.Machine;
import vm.MachineFactory;
import vm.Process;
import vm.ProcessLogic;

public class ProcessTransfer1AtSAME extends SCJTargetConfigurationSAME {
    private static Process p1;
    private static Process p2;
    private static Process mainProcess;

    private static byte count;

    private static class P1 implements ProcessLogic {
    	
    	P1() {
    		devices.Console.println("P1 constructor called");
    	}
    	
        @Override
        @IcecapCompileMe
        public void run() {
        	devices.Console.println("P1.run");
            while (count < 10) {
                p1.transferTo(p2);
            }
            p1.transferTo(mainProcess);
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
        @IcecapCompileMe
        public void run() {
        	devices.Console.println("P2.run");
            while (true) {
                count++;
                p2.transferTo(p1);
            }
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

		devices.Console.println("Hello ProcessTransfer1AtSAME 2018 HSO");
		
		MachineFactory mFac = Machine.getMachineFactory();		
		mFac.startSystemTick();
		
		blink (2000);
				
        p1 = new vm.Process(new P1(), new int[getReasonableProcessStackSize()]);
        p2 = new vm.Process(new P2(), new int[getReasonableProcessStackSize()]);
       
        p1.initialize();
        p2.initialize();
        
        mainProcess = new vm.Process(null, null);
        
        devices.Console.println("ProcessTransfer1AtSAME: processes running");
        
        count = 0;
        
        mainProcess.transferTo(p1);

        devices.Console.println("ProcessTransfer1AtSAME: count = " + count);
        if (count == 10) {
        	
            blink(5000);
        }
        else {
        	blink(2000);
        }
        
        devices.Console.println("Hello ProcessTransfer1AtSAME end");
    }
}
