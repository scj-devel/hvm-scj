package test.AVR.ATMega2560;

import devices.AVR.ATMega2560.ATMega2560TargetConfiguration;
import icecaptools.IcecapCompileMe;

public class TestUART extends ATMega2560TargetConfiguration {

	@IcecapCompileMe
	private static void Uart_Init(short enBaudRate) {
		UBRR2 = enBaudRate;
		UCSR2B = (1 << RXEN2) | (1 << TXEN2);
		UCSR2C = (3 << UCSZ20);
	}

	@IcecapCompileMe
	private static void WriteUartChar(byte ui8Char) {
		while ((UCSR2A & (1 << UDRE2)) == 0) {
			;
		}
		UDR2 = ui8Char;
	}

	@IcecapCompileMe
	public static void main(String[] args) {
		char toggle = 0;
		DDRA = (byte) 0xFF;

		Uart_Init((short) 64);

		while (true) {
			if (toggle == 0) {
				PORTA = (byte) 0xaa;
				toggle = 1;
			} else {
				PORTA = 0x55;
				toggle = 0;
			}

			WriteUartChar((byte) 'X');
			devices.System.delay(16000);
		}
	}

	@Override
	public String getOutputFolder() {
		return "/home/skr/hvmsrc";
	}
}
