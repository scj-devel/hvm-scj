package test;

import vm.Process;
import vm.ProcessLogic;
import icecaptools.IcecapCompileMe;

public class TestExecuteWithStack2 {
    private static boolean failed = true;
    private static Process p;
    private static Process mainProcess;

    private static class ProcessExecutor implements ProcessLogic {
        String nullReference;

        @Override
        @IcecapCompileMe
        public void run() {
            /* Provoke an intentional NullPointer dereference */
            if (nullReference.length() > 0) {
                p.transferTo(mainProcess);
            }
        }

        @Override
        public void catchError(Throwable t) {
            failed = false;
            devices.Console.println("Exception caught: " + t);
            p.transferTo(mainProcess);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        ProcessExecutor processExecuter = new ProcessExecutor();
        p = new Process(processExecuter, new int[4096]);
        mainProcess = new vm.Process(null, null);
        p.initialize();
        mainProcess.transferTo(p);

        if (!failed) {
            args = null;
        }
    }
}
