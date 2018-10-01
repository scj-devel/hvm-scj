/*! \file iha_serial.h
    \ifnot NO_DOCUMENTATION_IHA_SERIAL
    \brief Serial driver for the UARTs.

    \author iha

    \defgroup  serial_driver Driver for SAME70Q21
    \{
    \brief A driver for UARTs

    \note Only implemented for 8,x,1 Data format!!!!

    \note Peripherals pins to be used for RXD and TXD must be configured by the application, an example for using UART0 can be seen here:
    \code
	// set the pins to use the uart peripheral
	pio_configure(PIOA, PIO_PERIPH_A, (PIO_PA9A_URXD0 | PIO_PA10A_UTXD0), PIO_DEFAULT);
    \endcode

    \note IRQ must be enabled from application, an example for UART0 can be seen here:
    \code
	// Configure UART interrupts.
	NVIC_ClearPendingIRQ(UART0_IRQn);
	NVIC_DisableIRQ(UART0_IRQn);
	NVIC_SetPriority(UART0_IRQn, irq_priority);
	NVIC_EnableIRQ(UART0_IRQn);
    \endcode
    \}
*/
#ifndef IHA_SERIAL_H_
#define IHA_SERIAL_H_

#if defined(__SAME70Q21__) || defined(__SAMS70N21__)
#else
	error Serial driver only implemented for SAME70Q21 and SAMS70N21
#endif

#include <asf.h>

// Abstract Data Type (ADT)
typedef struct serial_instance_t *serial_p;

// Serial return codes
typedef enum {
	SERIAL_OK,
	SERIAL_ILLEGAL_INSTANCE,
	SERIAL_NO_ROOM_IN_TX_BUFFER
} e_serial_return_code_t;

typedef enum {
	ser_EVEN_PARITY = 0,
	ser_ODD_PARITY,
	ser_SPACE_PARITY,
	ser_MARK_PARITY,
	ser_NO_PARITY
} e_parity_t;

/* ======================================================================= */
/**
    \ingroup serial_driver
    \brief Create serial driver instants.

    \param uart Uart to setup [UART0, UART1, UART2, UART3, UART4]
    \param baud a standard baudrate
    \param parity Parity, use e_parity_t enum
    \param buffer_size size of receiver and transmitter buffer to be used (unit is in number of 'elements').
                      It must be a 2-power and <= to 128.
    \param handler_call_back pointer to handler that will be called for each received byte. Will not be called if NULL

    \return serial_p handle to serial instants. If NULL then serial instance is not created.
*/
serial_p serial_new_instance( Uart *uart, uint32_t baud, e_parity_t parity,
                              uint8_t buffer_size, void ( *handler_call_back )( serial_p, uint8_t ) );

/* ======================================================================= */
/**
    \ingroup serial_driver
    \brief Send an array of bytes.

    \param handle serial instance to use.
    \param buf pointer to a byte array to be send.
    \param len number of bytes to send from the beginning of the buffer.

    \return e_serial_return_code_t return code.
*/
e_serial_return_code_t serial_send_bytes( serial_p handle, uint8_t *buf,
        uint8_t len );

/* ======================================================================= */
/**
    \ingroup serial_driver
    \brief Send a single byte.

    \param handle serial instance to use.
    \param byte to be send.

    \return e_serial_return_code_t return code.
*/
e_serial_return_code_t serial_send_byte( serial_p handle, uint8_t byte );

/* ======================================================================= */
/**
    \ingroup serial_driver
    \brief Set new call back handler for serial instance.

    \param handle serial instance to use.
    \param handler_call_back pointer to handler that will be called for each received byte.\n
		Will not be called if NULL.

    \return e_serial_return_code_t return code.
*/
e_serial_return_code_t serial_set_call_back_handler( serial_p handle,
        void ( *handler_call_back )( serial_p, uint8_t ) );

/* ======================================================================= */
/**
    \ingroup serial_driver
    \brief Returns pointer to the fifo descriptor used for the RX fifo.

    \param handle  serial instance to use.

    \return fifo_desc_t * rx_fifo_descriptor
*/
fifo_desc_t *serial_get_rx_fifo_desc( serial_p handle );

#ifdef SERIAL_DEBUG
void serial_get_debug_buffer(serial_p handle, uint8_t *buf, uint16_t *len);
#endif

/**
    \endif
*/
#endif /* IHA_SERIAL_H_ */