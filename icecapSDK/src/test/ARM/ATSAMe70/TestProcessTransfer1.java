package test.ARM.ATSAMe70;

import devices.arm.ATSAMe70.ATSAMe70SCJTargetConfiguration;
import icecaptools.IcecapCompileMe;
import vm.Process;
import vm.ProcessLogic;

public class TestProcessTransfer1 extends ATSAMe70SCJTargetConfiguration {
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
            blink(8000);
        }
        else
        {
        	blink(1000);
        }
    }
    
    @Override
	public String getOutputFolder() {
		return "C:\\home\\icecapout";
	}
}
