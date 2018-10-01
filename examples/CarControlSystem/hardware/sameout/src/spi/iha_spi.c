/*
 * spi.c
 *
 * Created: 26-04-2016 09:36:10
 *  Author: IHA
 */ 

#include <asf.h>
#include "iha_spi.h"

union spi_buffer_element {
	uint8_t  byte;
	uint16_t halfword;
	uint32_t word;
};

// Instance data
struct spi_struct{
	Spi * _spi_base;
	uint8_t _cs_pin;
	
	fifo_desc_t *_spi_tx_fifo_desc;
	fifo_desc_t *_spi_rx_fifo_desc;

	void(*_call_back )(spi_p instance,uint8_t byte);
};

static Spi * _spi_base;
static uint8_t _spi_active = 0;
static spi_p _this = NULL;

// Get receiver FIFO descriptor for instance
fifo_desc_t * spi_get_rx_fifo_desc(spi_p spi) {
	return spi->_spi_rx_fifo_desc;
}

// Select the instance
static void _select_instance(spi_p inst) {
	_this = inst;
	
	// are instance valid?
	if (inst != NULL) {
		_spi_active = 0;
	}
}

static void _spi_init_base(Spi * spi_base)
{
	spi_enable_clock(spi_base);
	spi_disable(spi_base);
	spi_reset(spi_base);
	spi_set_lastxfer(spi_base);
	spi_set_master_mode(spi_base);
	spi_disable_mode_fault_detect(spi_base);
	spi_set_variable_peripheral_select(spi_base);
	spi_enable_interrupt(_spi_base, SPI_IER_RDRF);
	
	// spi_enable_loopback(spi_base); // ??????????????????
}

spi_p spi_new_instance(Spi * spi_base, uint8_t spi_chip_sel, uint32_t spi_freq, uint8_t spi_mode, uint8_t buffer_size, void(*handler_call_back )(spi_p, uint8_t))
{
	_spi_base = spi_base;
	
	if (!spi_is_enabled(_spi_base)) {
		_spi_init_base(spi_base);
	}
	
	spi_p _spi = malloc(sizeof *_spi);
	
	_spi->_call_back = handler_call_back;
	_spi->_cs_pin = spi_chip_sel;
	_spi->_spi_rx_fifo_desc = (fifo_desc_t *)malloc(sizeof(fifo_desc_t));
	_spi->_spi_tx_fifo_desc = (fifo_desc_t *)malloc(sizeof(fifo_desc_t));
	
	union spi_buffer_element *spi_tx_fifo_buffer = (union spi_buffer_element *)(malloc(sizeof(union spi_buffer_element) * buffer_size));
	union spi_buffer_element *spi_rx_fifo_buffer = (union spi_buffer_element *)(malloc(sizeof(union spi_buffer_element) * buffer_size));
	fifo_init(_spi->_spi_rx_fifo_desc, spi_rx_fifo_buffer, buffer_size);
	fifo_init(_spi->_spi_tx_fifo_desc, spi_tx_fifo_buffer, buffer_size);
		
	spi_set_peripheral_chip_select_value(spi_base, spi_get_pcs(spi_chip_sel));
	switch (spi_mode)
	{
		case 0:
			spi_set_clock_polarity(spi_base, spi_chip_sel,0);
			spi_set_clock_phase(spi_base, spi_chip_sel, 1);
		break;
		case 1:
			spi_set_clock_polarity(spi_base, spi_chip_sel, 0);
			spi_set_clock_phase(spi_base, spi_chip_sel, 0);
		break;
		case 2:
			spi_set_clock_polarity(spi_base, spi_chip_sel, 1);
			spi_set_clock_phase(spi_base, spi_chip_sel, 1);
		break;
		case 3:
			spi_set_clock_polarity(spi_base, spi_chip_sel, 1);
			spi_set_clock_phase(spi_base, spi_chip_sel, 0);
		break;
	}

	spi_set_bits_per_transfer(spi_base, spi_chip_sel, SPI_CSR_BITS_8_BIT);
	spi_configure_cs_behavior(spi_base, spi_chip_sel, SPI_CS_KEEP_LOW);
	spi_set_baudrate_div(spi_base, spi_chip_sel, (sysclk_get_peripheral_hz() / spi_freq));
	spi_set_delay_between_chip_select(spi_base, 0x10);
	spi_set_transfer_delay(spi_base, spi_chip_sel, 0x01, 0x10);
	spi_enable(spi_base);
	
	return _spi;
}

uint8_t spi_send_byte(spi_p spi, uint8_t byte, uint8_t last_byte)
{
	uint8_t result = SPI_OK;
	uint32_t value;
	
	if (spi == NULL) {
		return SPI_ILLEGAL_INSTANCE;
	}

	// Select correct instance
	if (_this != spi ) {
		_select_instance(spi);
	}

	// Critical section
	{
		// disable interrupt
		spi_disable_interrupt(_spi_base, SPI_IDR_TDRE | SPI_IDR_RDRF);
		
		value = SPI_TDR_TD(byte) | SPI_TDR_PCS(spi_get_pcs(spi->_cs_pin));
		if (last_byte) {
			value |= SPI_TDR_LASTXFER;
		}

		// If SPI in idle send the byte
		if (!_spi_active) {
			_spi_active = 1;

			// Send byte
			_spi_base->SPI_TDR = value;
		} else {
			// Put in the TX buffer
			if ( fifo_push_uint32(spi->_spi_tx_fifo_desc, value) == FIFO_ERROR_UNDERFLOW )
				result = SPI_NO_ROOM_IN_TX_BUFFER;
		}
		// Enable interrupt
		spi_enable_interrupt(_spi_base, SPI_IER_TDRE | SPI_IER_RDRF);
	}

	return result;
}

/* ======================================================================================================================= */
/**
@ingroup spi_function
@brief Send an array of bytes to to the SPI bus.

@note Can only be used if SPI_USE_BUFFER are enabled in spi_iha_defs.h

@see spi_iha_defs.h for SPI_USE_BUFFER setup.

@return SPI_OK: OK byte send to SPI bus or put in tx_buffer.\n
SPI_NO_ROOM_IN_TX_BUFFER: Buffer full no data send\n
SPI_ILLEGAL_INSTANCE: instance is null.
@param spi to send to.
@param *buf pointer to buffer to be send.
@param len no of bytes to send.
*/
uint8_t spi_send_bytes(spi_p spi, uint8_t buf[], uint8_t len) {
	uint8_t result = SPI_OK;
	uint32_t value;
	uint8_t tmp = 0;	
	
	if (spi == NULL) {
		return SPI_ILLEGAL_INSTANCE;
	}

	// Select correct instance
	if (_this != spi ) {
		_select_instance(spi);
	}

	// Critical section
	 	{
 		// disable interrupt
		spi_disable_interrupt(_spi_base, SPI_IDR_TDRE | SPI_IDR_RDRF);

		// Check if buffer is free
		if (len > fifo_get_free_size(spi->_spi_tx_fifo_desc)) {
			result = SPI_NO_ROOM_IN_TX_BUFFER;
		} else {
			// If SPI in idle send the first byte
			if (!_spi_active) {
				_spi_active = 1;
					
				// Send first byte
				value = SPI_TDR_TD(buf[0]) | SPI_TDR_PCS(spi_get_pcs(spi->_cs_pin));
				if (len == 1) {
					// It was last byte
					value |= SPI_TDR_LASTXFER;
				}					

				// Send byte
				_spi_base->SPI_TDR = value;
				//spi_enable_interrupt(_spi_base, SPI_IER_TDRE);
				tmp = 1;
			}
				
			// Put in the tx buffer
			for (uint8_t i = tmp; i < len; i++) {
				value = SPI_TDR_TD(buf[i]) | SPI_TDR_PCS(spi_get_pcs(spi->_cs_pin));
				if (i == len-1) {
					// It was last byte
					value |= SPI_TDR_LASTXFER;
				}
				if ( fifo_push_uint32(spi->_spi_tx_fifo_desc, value) == FIFO_ERROR_OVERFLOW ) {
					result = SPI_NO_ROOM_IN_TX_BUFFER;			
				}
			}
		}

 		// restore interrupt state
		spi_enable_interrupt(_spi_base, SPI_IER_TDRE | SPI_IER_RDRF);
	}
	return result;
}

void SPI0_Handler(void) {	
	uint16_t us_data;
	uint32_t us_tx_data;	
			
	uint8_t p_pcs;
	
	uint32_t status = spi_read_status(_spi_base);
		
	if (status & SPI_SR_RDRF) {		
		if ( spi_read( _spi_base, &us_data,	&p_pcs ) == SPI_OK ) {
			// store received byte
			fifo_push_uint8(_this->_spi_rx_fifo_desc, us_data);
			
			// If handler defined - call it with instance and received byte.
			if (_this->_call_back)
			{
				_this->_call_back(_this, (uint8_t)us_data);
			}
		}
	}	
	
	if (status & SPI_SR_TDRE) {
		// more bytes to send?		
		if ( fifo_pull_uint32(_this->_spi_tx_fifo_desc, &us_tx_data) == FIFO_OK ) {
			_spi_base->SPI_TDR = us_tx_data;
		} else {
			// No
			// Disable SPI TX interrupt
			spi_disable_interrupt(_spi_base, SPI_IDR_TDRE);
			_spi_active = 0;
		}
	}
}
