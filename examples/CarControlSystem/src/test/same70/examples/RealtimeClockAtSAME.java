package test.same70.examples;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.RelativeTime;

import test.same70.configuration.MachineFactorySAME;
import test.same70.configuration.TargetConfigurationSAME;
import vm.MachineFactory;
import vm.RealtimeClock;
import vm.VMTest;

public class RealtimeClockAtSAME extends TargetConfigurationSAME {
	static final int ticks = 1000;

	public static void main(String[] args) {
		init();
		
		devices.Console.println("Hello RealtimeClockAtSAME started");
		
		MachineFactory mFac = new MachineFactorySAME();
		mFac.startSystemTick();
		
		devices.Console.println("Start...");

		Clock rtClock = Clock.getRealtimeClock();

		RelativeTime granularity = rtClock.getQueryPrecision();

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

		devices.Console.println("granularity: " + granularity.toString());

		devices.Console.println("duration: " + duration.toString());

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

		//mFac.stopSystemTick();
		
		blink (4000);		
		devices.Console.println("Hello RealtimeClockAtSAME end");
	}
}
