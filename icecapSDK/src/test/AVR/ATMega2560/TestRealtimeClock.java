package test.AVR.ATMega2560;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.RelativeTime;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import vm.MachineFactory;
import vm.RealtimeClock;
import vm.VMTest;

public class TestRealtimeClock extends ATMega2560TargetConfiguration {
	static final int ticks = 100;

	public static void main(String[] args) {
		MachineFactory mFactory = getConfiguration();

		mFactory.startSystemTick();

		devices.Console.println("Start...");

		Clock rtClock = Clock.getRealtimeClock();

		RelativeTime granularity = rtClock.getResolution();

		AbsoluteTime start = rtClock.getTime();

		int count = ticks;

		devices.Console.print("Waiting...");

		while (count > 0) {
			RealtimeClock.waitForNextTick();
			count--;
		}

		devices.Console.println("done");

		AbsoluteTime stop = rtClock.getTime();

		RelativeTime duration = stop.subtract(start);

		devices.Console.println(granularity.toString());

		devices.Console.println(duration.toString());

		int actualDuration = (int) duration.getMilliseconds();

		int expectedDuration = (int) (granularity.getMilliseconds() * ticks);

		if (actualDuration < expectedDuration) {
			devices.Console.println("waiting less than expected");
		} else if (actualDuration > 2 * expectedDuration) {
			devices.Console.println("waiting more than expected");
		} else {
			devices.Console.println("waiting as expected");
			VMTest.markResult(false);
		}

		mFactory.stopSystemTick();
		blink(2000);
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}

	@Override
	public int getJavaHeapSize() {
		return 4096;
	}
}
