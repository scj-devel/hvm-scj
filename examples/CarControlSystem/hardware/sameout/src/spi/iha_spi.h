/*
 * iha_spi.h
 *
 * Created: 26-04-2016 09:35:58
 *  Author: IHA
 */ 


#ifndef IHA_SPI_H_
#define IHA_SPI_H_
// ------------- Includes -------------------
#include <asf.h>

// ------------- Defines --------------------


// Abstract Data Type (ADT)
typedef struct spi_struct *spi_p;

/**
\ingroup spi_mode
@{
	@brief Set SPI in Master mode*/
	#define SPI_MODE_MASTER _BV(MSTR)
	/** @brief Set SPI in Slave mode*/
	#define SPI_MODE_SLAVE NOT_IMPLEMENTED
	/**
@}

\ingroup spi_data_order
@{
	@brief LSB sent first*/
	#define SPI_DATA_ORDER_LSB _BV(DORD)
	/** @brief MSB sent first*/
	#define SPI_DATA_ORDER_MSB 0
	/**
@}

@ingroup spi_return_codes
@{
	@brief The function succeeded. */
	#define SPI_OK 0
	/** @brief Not enough room in tx buffer to store value, or buffer is not used. */
	#define SPI_NO_ROOM_IN_TX_BUFFER 1
	/** @brief SPI bus is busy - nothing done. */
	#define SPI_BUSY 2
	/** @brief The specified instance is > SPI_MAX_NO_OF_INSTANCES or is not instantiated yet. */
	#define SPI_ILLEGAL_INSTANCE 3
	/**
@}
*/

// ------------- Prototypes -----------------
spi_p spi_new_instance(Spi * spi_base, uint8_t spi_chip_sel, uint32_t spi_freq, uint8_t spi_mode, uint8_t buffer_size, void(*handler_call_back )(spi_p, uint8_t));

uint8_t spi_send_byte(spi_p spi, uint8_t byte, uint8_t last_byte);
uint8_t spi_send_bytes(spi_p spi, uint8_t buf[], uint8_t len) ;
fifo_desc_t * spi_get_rx_fifo_desc(spi_p spi);
#endif /* IHA_SPI_H_ */