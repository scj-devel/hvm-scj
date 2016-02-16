package test;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.RelativeTime;

import vm.Machine;
import vm.MachineFactory;
import vm.POSIX64BitMachineFactory;
import vm.RealtimeClock;
import vm.VMTest;

public class TestRealtimeClock {
	static final int ticks = 100;

	public static void main(String[] args) {
		MachineFactory mFactory = new POSIX64BitMachineFactory();

		Machine.setMachineFactory(mFactory);

		mFactory.startSystemTick();

		Clock rtClock = Clock.getRealtimeClock();

		RelativeTime granularity = rtClock.getResolution();
		AbsoluteTime start = rtClock.getTime();

		int count = ticks;

		while (count > 0) {
			RealtimeClock.waitForNextTick();
			count--;
		}

		AbsoluteTime stop = rtClock.getTime();

		RelativeTime duration = stop.subtract(start);

		devices.Console.println(granularity.toString());

		devices.Console.println(duration.toString());

		int actualDuration = (int) duration.getMilliseconds();

		int expectedDuration = (int) (granularity.getMilliseconds() * ticks);

		if (actualDuration < expectedDuration) {
			devices.Console.println("waiting less than expected");
		} else if (actualDuration > 3 * expectedDuration) {
			devices.Console.println("waiting more than expected");
		} else {
			devices.Console.println("waiting as expected");
			VMTest.markResult(false);
		}

		mFactory.stopSystemTick();
	}
}
