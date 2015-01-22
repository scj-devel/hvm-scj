package test;

import vm.Process;
import vm.ProcessLogic;
import icecaptools.IcecapCompileMe;

public class TestExecuteWithStack {

    private static boolean failed = true;
    private static Process p;
    private static Process mainProcess;

    private static class TestObject {
        byte x;

        ProcessExecutor processExecuter;

        private static class ProcessExecutor implements ProcessLogic {
            TestObject thisObject;

            ProcessExecutor(TestObject thisObject) {
                this.thisObject = thisObject;
            }

            @Override
            @IcecapCompileMe
            public void run() {
                if (thisObject.x == 42) {
                    devices.Console.println("OK");
                    failed = false;
                } else {
                }
                p.transferTo(mainProcess);
            }

            @Override
            public void catchError(Throwable t) {
                devices.Console.println("Process: exception -> " + t);
            }

        }

        TestObject() {
            processExecuter = new ProcessExecutor(this);

            x = 42;
        }

        public void start() {
            p = new Process(processExecuter, new int[4096]);
            mainProcess = new vm.Process(null, null);
            p.initialize();
            mainProcess.transferTo(p);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestObject testObj = new TestObject();
        testObj.start();
        if (!failed) {
            args = null;
        }
    }
}
