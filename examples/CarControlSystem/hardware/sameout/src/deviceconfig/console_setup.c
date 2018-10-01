/*
 * console_setup.c
 *
 * Created: 14/11/2017 10:51:20
 *  Author: IHA
 *
 * This UART configuration is from Atmel Software Framework:
 *   The Getting-started Example, section Peripherals Configuration and Usage, UART.
 *
 * http://asf.atmel.com/docs/latest/sam.appnote.getting-started.sam4c/html/appdoc_chap_06_title7.html
 *
 */ 

 #include "stdio_serial.h"
 #include "console_setup.h"
 /**
 Configure UART console.
 */
 void configure_console ( void )
 {
	 const usart_serial_options_t uart_serial_options = {
		 .baudrate = CONF_UART_BAUDRATE,
		 #ifdef CONF_UART_CHAR_LENGTH
		 .charlength = CONF_UART_CHAR_LENGTH,
		 #endif
		 .paritytype = CONF_UART_PARITY,
		 #ifdef CONF_UART_STOP_BITS
		 .stopbits = CONF_UART_STOP_BITS,
		 #endif
	 };
	 /* Configure console UART. */
	 sysclk_enable_peripheral_clock ( CONSOLE_UART_ID );
	 stdio_serial_init ( CONF_UART, &uart_serial_options );
 }