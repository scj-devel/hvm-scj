package test;

import gc.GarbageCollector;
import reflect.ObjectInfo;
import test.icecapvm.DefaultGCMonitor;
import thread.Thread;

public class TestGC1 {

    @SuppressWarnings("unused")
    private static boolean error;
    
	private static class MyMonitor extends DefaultGCMonitor {
        private int dontFree;
        public boolean error;
        
        MyMonitor()
        {
            error = false;
        }
        
        @Override
        public void freeObject(int ref) {
            super.freeObject(ref);
            if ((dontFree != 0) && (ref == dontFree))
            {
                error = true;
            }
        }

        public void ensureNotFree(int ref) {
            this.dontFree = ref;
        }

        @Override
        public void reset() {
            super.reset();
            error = false;
            this.dontFree = 0;
        }
	}

	private static class TempClass {
		@SuppressWarnings("unused")
        public TempClass next;
	}

	private static class Mutator implements Runnable {

		private MyMonitor monitor;
		private int[] array;

		public Mutator(MyMonitor monitor, int[] array) {
			this.monitor = monitor;
			this.array = array;
		}

		@Override
		public void run() {
			GarbageCollector.requestCollection();
			GarbageCollector.waitForNextCollection();
			array[0] = monitor.getFreeedObjects();
			monitor.reset();

            TempClass t = new TempClass();

			int ref = ObjectInfo.getAddress(t);
			
			monitor.ensureNotFree(ref);
			
            GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();
        
			array[1] = monitor.getFreeedObjects();
			
			error |= monitor.error;
			
			monitor.reset();
			
			t = null;
			
			monitor.ensureNotFree(ref);
			
			GarbageCollector.requestCollection();
            GarbageCollector.waitForNextCollection();
        
            array[2] = monitor.getFreeedObjects();
            if (monitor.error == false)
            {
                error |= true;
            }
            monitor.reset();			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyMonitor monitor = new MyMonitor();
		GarbageCollector.registerMonitor(monitor);
        GarbageCollector.start();

		int[] array = new int[3];

		Thread thr1 = new Thread(new Mutator(monitor, array));

		thr1.start();
		try {
			thr1.join();
		} catch (InterruptedException e) {
		}
		for (int i = 0; i < array.length; i++) {
			devices.Console.println("array[" + i + "] = " + array[i]);
		}
		if (monitor.error)
		{
		    devices.Console.println("Unexpected free!");
		}
		else
		{
		    args = null;
		}
	}
}
