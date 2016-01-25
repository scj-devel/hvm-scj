package devices.AVR.ATMega2560;

import icecaptools.IcecapCFunc;
import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;

public abstract class ATMega2560SCJTargetConfiguration extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	protected static void init() {
		/* enable timer 0 overflow interrupt */
	    TIMSK0 = (1 << TOIE0);

	    /* start timer without presscaler */
	    TCCR0B = (1 << CS01) | (1 << CS00);

	    /* at 4 Mhz schedule gets called approx every 15th miliseconds */
	    /* enable interrupts */
	    sei();
	}
	
	@IcecapCVar(expression = "systemTick", requiredIncludes = "extern volatile uint8 systemTick;")
	private static byte systemTick;
	
	@IcecapCFunc(signature = "ISR(TIMER0_OVF_vect)", requiredIncludes = "#include <avr/interrupt.h>")
	private static void timerTick()
	{
		systemTick++;
	}
	
	@Override
	public String[] getBuildCommands() {
		String[] buildCommands = super.getBuildCommands();
		StringBuffer gcccommand = new StringBuffer(buildCommands[0]);
		gcccommand.append(" -DLAZY_INITIALIZE_CONSTANTS atmega2560_interrupt.s ");
		buildCommands[0] = gcccommand.toString();
		return buildCommands;
	}
	
	@Override
	public int getJavaHeapSize() {
		return 7100;
	}
}
