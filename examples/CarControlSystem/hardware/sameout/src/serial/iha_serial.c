/* ################################################## Standard includes ################################################# */
#include <asf.h>
#ifdef SERIAL_DEBUG
#include <string.h>
#endif
/* ################################################### Project includes ################################################# */
#include "../config/scalectrix_board_def.h"
#include "iha_serial.h"

/* ################################################### Global Variables ################################################# */
/* ############################################ Module Variables/Declarations ########################################### */
#ifdef SERIAL_DEBUG
uint8_t _debug_buf[2000];
uint16_t _debug_index = 0;
#endif

#if defined(__SAME70Q21__) || defined(__SAMS70N21__)
#define MAX_NO_OF_UARTS 5
#endif

// Serial instance data
struct serial_instance_t {
	Uart *uart;
	fifo_desc_t *tx_fifo_desc;
	fifo_desc_t *rx_fifo_desc;
	void ( *call_back )( serial_p, uint8_t );
};

// FIFO Element definition
union serial_buffer_element {
	uint8_t  byte;
	uint16_t halfword;
	uint32_t word;
};

// Holds a copy of the serial handlers to be used in ISRs
static serial_p _handlers[MAX_NO_OF_UARTS];

/* ======================================================================= */
serial_p serial_new_instance( Uart *uart, uint32_t baud,
e_parity_t parity, uint8_t buffer_size,
void ( *handler_call_back )( serial_p, uint8_t ) )
{
	serial_p _handle = malloc( sizeof * _handle );

	if ( _handle == NULL ) {
		return NULL;
	}

	sam_uart_opt_t _uart_settings;
	_handle->call_back = handler_call_back;
	_handle->uart = uart;
	_handle->rx_fifo_desc = ( fifo_desc_t * )malloc( sizeof( fifo_desc_t ) );
	_handle->tx_fifo_desc = ( fifo_desc_t * )malloc( sizeof( fifo_desc_t ) );
	union serial_buffer_element *_tx_fifo_buffer = ( union
	serial_buffer_element * )( malloc( sizeof( union serial_buffer_element )
	* buffer_size ) );
	union serial_buffer_element *_rx_fifo_buffer = ( union
	serial_buffer_element * )( malloc( sizeof( union serial_buffer_element )
	* buffer_size ) );
	fifo_init( _handle->rx_fifo_desc, _rx_fifo_buffer, buffer_size );
	fifo_init( _handle->tx_fifo_desc, _tx_fifo_buffer, buffer_size );
	_uart_settings.ul_baudrate = baud;
	_uart_settings.ul_mck = sysclk_get_peripheral_hz();
	_uart_settings.ul_mode = UART_MR_PAR( parity );
	//enable the uart peripherial clock and store handler
	#ifdef USE_UART0_CFG

	if ( uart == UART0 ) {
		pmc_enable_periph_clk( ID_UART0 );
		_handlers[0] = _handle;
	}

	#endif
	#ifdef USE_UART1_CFG

	if ( uart == UART1 ) {
		pmc_enable_periph_clk( ID_UART1 );
		_handlers[1] = _handle;
	}

	#endif
	#ifdef USE_UART2_CFG

	if ( uart == UART2 ) {
		pmc_enable_periph_clk( ID_UART2 );
		_handlers[2] = _handle;
	}

	#endif
	#ifdef USE_UART3_CFG

	if ( uart == UART3 ) {
		pmc_enable_periph_clk( ID_UART3 );
		_handlers[3] = _handle;
	}

	#endif
	#ifdef USE_UART4_CFG

	if ( uart == UART4 ) {
		pmc_enable_periph_clk( ID_UART4 );
		_handlers[4] = _handle;
	}

	#endif
	uart_init( uart, &_uart_settings );
	uart_enable_interrupt( uart, UART_IER_RXRDY );  //Enable RX ready interrupt
	return _handle;
}

/* ======================================================================= */
e_serial_return_code_t serial_send_bytes( serial_p handle, uint8_t *buf,
uint8_t len )
{
	e_serial_return_code_t result = SERIAL_OK;
	uint8_t tmp = 0;

	if ( handle == NULL ) {
		return SERIAL_ILLEGAL_INSTANCE;
	}

	// Critical section
	{
		// disable interrupt
		uart_disable_interrupt( handle->uart, UART_IDR_TXEMPTY | UART_IDR_RXRDY );

		// Check if buffer is free
		if ( len > fifo_get_free_size( handle->tx_fifo_desc ) ) {
			result = SERIAL_NO_ROOM_IN_TX_BUFFER;
			} else {
			// If Transmitter idle - send first byte
			if ( handle->uart->UART_SR & UART_SR_TXEMPTY ) {
				// Send first byte
				#ifdef SERIAL_DEBUG
				_debug_buf[_debug_index++] = 'T';
				_debug_buf[_debug_index++] =  buf[0];
				#endif
				uart_write( handle->uart, buf[0] );
				tmp = 1;
			}

			// Put rest in the tx buffer
			for ( uint8_t i = tmp; i < len; i++ ) {
				if ( fifo_push_uint8( handle->tx_fifo_desc,
				buf[i] ) == FIFO_ERROR_OVERFLOW ) {
					result = SERIAL_NO_ROOM_IN_TX_BUFFER;
				}
			}
		}

		// restore interrupt state
		uart_enable_interrupt( handle->uart, UART_IER_TXEMPTY | UART_IER_RXRDY );
	}
	return result;
}

/* ======================================================================= */
e_serial_return_code_t serial_send_byte( serial_p handle, uint8_t byte )
{
	e_serial_return_code_t _result = SERIAL_OK;

	if ( handle == NULL ) {
		return SERIAL_ILLEGAL_INSTANCE;
	}

	// Critical section
	{
		// disable interrupt
		uart_disable_interrupt( handle->uart, UART_IDR_TXEMPTY | UART_IDR_RXRDY );

		// If Transmitter idle - send the byte
		if ( handle->uart->UART_SR & UART_SR_TXEMPTY ) {
			// Send byte
			#ifdef SERIAL_DEBUG
			_debug_buf[_debug_index++] = 'T';
			_debug_buf[_debug_index++] =  byte;
			#endif
			uart_write( handle->uart, byte );
			} else {
			// Put in the TX buffer
			if ( fifo_push_uint8( handle->tx_fifo_desc,
			byte ) == FIFO_ERROR_OVERFLOW ) {
				_result = SERIAL_NO_ROOM_IN_TX_BUFFER;
			}
		}

		// Enable interrupt
		uart_enable_interrupt( handle->uart, UART_IER_TXEMPTY | UART_IER_RXRDY );
	}
	return _result;
}

/* ======================================================================= */
e_serial_return_code_t serial_get_byte( serial_p handle, uint8_t *byte )
{
	uint8_t _result = fifo_pull_uint8(handle->rx_fifo_desc, byte );

	if (_result == FIFO_ERROR_UNDERFLOW)
	{
		return SERIAL_RX_BUFFER_EMPTY;
	}

	return SERIAL_OK;
}

/* ======================================================================= */
void serial_flush_buffers(serial_p handle)
{
	fifo_flush(handle->rx_fifo_desc);
	fifo_flush(handle->tx_fifo_desc);
}

/* ======================================================================= */
e_serial_return_code_t serial_set_call_back_handler( serial_p handle,
void ( *handler_call_back )( serial_p, uint8_t ) )
{
	if ( handle == NULL ) {
		return SERIAL_ILLEGAL_INSTANCE;
	}

	handle->call_back = handler_call_back;
	return SERIAL_OK;
}

/* ======================================================================= */
#ifdef SERIAL_DEBUG
void serial_get_debug_buffer(serial_p handle, uint8_t *buf, uint16_t *len)
{
	// Critical section
	{
		// disable interrupt
		uart_disable_interrupt( handle->uart, UART_IDR_TXEMPTY | UART_IDR_RXRDY );

		memcpy(buf, _debug_buf, _debug_index);
		*len = _debug_index;
		_debug_index = 0;

		// Enable interrupt
		uart_enable_interrupt( handle->uart, UART_IER_TXEMPTY | UART_IER_RXRDY );
	}
}
#endif

/* ======================================================================= */
static inline void handler_helper(Uart * uart, uint8_t handler_no)
{
uint8_t _tmp_byte;
uint32_t _dw_status = uart_get_status( uart );

if ( _dw_status & UART_SR_RXRDY ) {
	if ( uart_read( uart, &_tmp_byte ) == 0 ) {
		#ifdef SERIAL_DEBUG
		_debug_buf[_debug_index++] = 'R';
		_debug_buf[_debug_index++] =  _tmp_byte;
		#endif
		fifo_push_uint8( _handlers[handler_no]->rx_fifo_desc, _tmp_byte );

		if ( _handlers[handler_no]->call_back != NULL ) {
			_handlers[handler_no]->call_back( _handlers[handler_no], _tmp_byte );
		}
	}
}

if ( _dw_status & UART_SR_TXEMPTY ) {
	// more bytes to send?
	if ( fifo_pull_uint8( _handlers[handler_no]->tx_fifo_desc,
	&_tmp_byte ) == FIFO_OK ) {
		#ifdef SERIAL_DEBUG
		_debug_buf[_debug_index++] = 'T';
		_debug_buf[_debug_index++] =  _tmp_byte;
		#endif
		uart_write( uart, _tmp_byte );
		} else {
		// Disable UART TX interrupt
		uart_disable_interrupt( uart, UART_IDR_TXEMPTY );
	}
}
}

#ifdef USE_UART0_CFG
/* ======================================================================= */
void UART0_Handler()
{
	handler_helper(UART0, 0);
}
#endif

#ifdef USE_UART1_CFG
/* ======================================================================= */
void UART1_Handler()
{
	handler_helper(UART1, 1);
}
#endif

#ifdef USE_UART2_CFG
/* ======================================================================= */
void UART2_Handler()
{
	handler_helper(UART2, 2);
}
#endif

#ifdef USE_UART3_CFG
/* ======================================================================= */
void UART3_Handler()
{
	handler_helper(UART3, 3);
}
#endif

#ifdef USE_UART4_CFG
/* ======================================================================= */
void UART4_Handler()
{
	handler_helper(UART4, 4);
}
#endif