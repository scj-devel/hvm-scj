package test;

import vm.VMTest;

public class TestTrackHeap {

    public static void main(String args[])
    {
        //Memory.startMemoryAreaTracking();
    	VMTest.markResult(test());
        //Memory.reportMemoryUsage();
    }

	public static boolean test() {
		return false;
	}
}
