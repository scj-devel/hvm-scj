package test.AVR.ATMega2560;

import vm.Process;
import vm.ProcessLogic;
import devices.AVR.ATMega2560.ATMega2560SCJTargetConfiguration;
import icecaptools.IcecapCompileMe;

public class TestProcessTransfer1 extends ATMega2560SCJTargetConfiguration {
    private static Process p1;
    private static Process p2;
    private static Process mainProcess;

    private static byte count;

    private static class P1 implements ProcessLogic {
        @Override
        @IcecapCompileMe
        public void run() {
            while (count < 10) {
                p1.transferTo(p2);
            }
            p1.transferTo(mainProcess);
        }
        @Override
        public void catchError(Throwable t) {
        	blink(1000);
        }
    }

    private static class P2 implements ProcessLogic {
        @Override
        @IcecapCompileMe
        public void run() {
            while (true) {
                count++;
                p2.transferTo(p1);
            }
        }
        @Override
        public void catchError(Throwable t) {
        	blink(1000);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        p1 = new vm.Process(new P1(), new int[getReasonableProcessStackSize()]);
        p2 = new vm.Process(new P2(), new int[getReasonableProcessStackSize()]);

        mainProcess = new vm.Process(null, null);
        
        p1.initialize();
        p2.initialize();
        
        count = 0;
        
        mainProcess.transferTo(p1);

        if (count == 10) {
            blink(16000);
        }
        else
        {
        	blink(1000);
        }
    }
    
    @Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}

	@Override
	public int getJavaHeapSize() {
		return 4049;
	}
}
