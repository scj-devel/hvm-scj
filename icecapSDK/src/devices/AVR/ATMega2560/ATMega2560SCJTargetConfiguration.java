package devices.AVR.ATMega2560;

import javax.realtime.AbsoluteTime;

import icecaptools.IcecapCFunc;
import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import vm.RealtimeClock.DefaultRealtimeClock;

public abstract class ATMega2560SCJTargetConfiguration extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	protected static void init() {
		/* enable timer 0 overflow interrupt */
		TIMSK0 = (1 << TOIE0);

		/* start timer without presscaler */
		TCCR0B = (1 << CS01) | (1 << CS00);

		systemClock = systemTick = 0;

		/* at 4 Mhz schedule gets called approx every 15th miliseconds */
		/* enable interrupts */
		sei();
	}

	@IcecapCVar(expression = "systemTick", requiredIncludes = "extern volatile uint8 systemTick;")
	private static byte systemTick;
	
	@IcecapCVar(expression = "systemClock", requiredIncludes = "extern volatile uint32 systemClock;")
	private static byte systemClock;

	@IcecapCFunc(signature = "ISR(TIMER0_OVF_vect)", requiredIncludes = "#include <avr/interrupt.h>")
	private static void timerTick() {
		systemTick++;
		systemClock++;
	}

	@Override
	public String[][] getBuildCommands() {
		String[][] buildCommands = super.getBuildCommands();
		String[] gcccommand = new String[buildCommands[0].length + 2];
		int commandIndex = 0;
		gcccommand[commandIndex++] = buildCommands[0][0];
		gcccommand[commandIndex++] = "-DLAZY_INITIALIZE_CONSTANTS";
		
		for (int inx = 1; inx < buildCommands[0].length; inx++)
		{
			gcccommand[commandIndex++] = buildCommands[0][inx];
		}
		gcccommand[commandIndex++] = "atmega2560_interrupt.s";

		buildCommands[0] = gcccommand;
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

	static class ATMega2560RealtimeClock extends DefaultRealtimeClock {

		@Override
		public int getGranularity() {
			/* 10 MS */
			return 10000000;
		}

		@IcecapCompileMe
		@Override
		public void getCurrentTime(AbsoluteTime now) {
			now.set(systemClock * 10, 0);
		}
	}
}
