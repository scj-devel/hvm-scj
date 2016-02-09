package devices.AVR.ATMega2560;

import javax.realtime.AbsoluteTime;

import icecaptools.IcecapCFunc;
import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import vm.RealtimeClock;

public abstract class ATMega2560SCJTargetConfiguration extends ATMega2560TargetConfiguration {

	private static int clock;

	@IcecapCompileMe
	protected static void init() {
		/* enable timer 0 overflow interrupt */
		TIMSK0 = (1 << TOIE0);

		/* start timer without presscaler */
		TCCR0B = (1 << CS01) | (1 << CS00);

		clock = 0;

		/* at 4 Mhz schedule gets called approx every 15th miliseconds */
		/* enable interrupts */
		sei();
	}

	@IcecapCVar(expression = "systemTick", requiredIncludes = "extern volatile uint8 systemTick;")
	private static byte systemTick;

	@IcecapCFunc(signature = "ISR(TIMER0_OVF_vect)", requiredIncludes = "#include <avr/interrupt.h>")
	private static void timerTick() {
		systemTick++;
		clock++;
	}

	@Override
	public String[][] getBuildCommands() {
		String[][] buildCommands = super.getBuildCommands();
		StringBuffer gcccommand = new StringBuffer(buildCommands[0][0]);
		gcccommand.append(" -DLAZY_INITIALIZE_CONSTANTS atmega2560_interrupt.s ");
		buildCommands[0][0] = gcccommand.toString();
		return buildCommands;
	}

	@Override
	public int getJavaHeapSize() {
		return 7100;
	}

	public static void deinit() {
		/* disable timer 0 overflow interrupt */
		TIMSK0 = 0;

		sdi();
	}

	static class ATMega2560RealtimeClock extends RealtimeClock {
		private AbsoluteTime now;

		ATMega2560RealtimeClock() {
			now = new AbsoluteTime();
		}

		@Override
		public int getGranularity() {
			/* 10 MS */
			return 10000000;
		}

		@Override
		public void getCurrentTime(AbsoluteTime now) {
			now.set(clock * 10, 0);
		}

		@Override
		public void delayUntil(AbsoluteTime time) {
			do {
				getCurrentTime(now);
			} while (now.compareTo(time) < 0);
		}

		@Override
		public void awaitTick() {
			int time = clock;
			/* Should call a wait assembler instruction instead */
			while (time == clock) {
				;
			}
		}
	}
}
