package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import icecaptools.IcecapCFunc;
import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;

public class TimerTest extends ATMega2560TargetConfiguration {

	@IcecapCVar(expression = "systemTick", requiredIncludes = "extern volatile uint8 systemTick;")
	private static byte systemTick;
	
	@IcecapCFunc(signature = "ISR( TIMER0_OVF_vect)", requiredIncludes = "#include <avr/interrupt.h>")
	private static void timerTick()
	{
		systemTick++;
		if (systemTick++ > 50)
		{
			PORTG ^= 0x3;
			systemTick = 0;
		}
	}
	
	@IcecapCompileMe
	public static void main(String[] args) {
		DDRG |= 0x3;
		
		/* enable timer 0 overflow interrupt */
	    TIMSK0 = (1 << TOIE0);

	    /* start timer without presscaler */
	    TCCR0B = (1 << CS01) | (1 << CS00);

	    /* at 4 Mhz schedule gets called approx every 15th miliseconds */
	    /* enable interrupts */
	    sei();

	    for (;;) {
	    }

	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
