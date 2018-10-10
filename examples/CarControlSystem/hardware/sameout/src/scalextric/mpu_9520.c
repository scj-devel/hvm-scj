/*
 * mpu_9520.c
 *
 * Created: 09-05-2016 12:52:19
 *  Author: IHA
 */ 

#include <asf.h>
#include "../scalextric/mpu_9520.h"
#include "../spi/iha_spi.h"

// MPU-9250 Gyro/Acc definitions
// Read bit or to register address to read from it
#define	MPU9520_READ				0x80

// MPU-9250 Registers
#define MPU9520_GYRO_CONFIG_REG		0x1B
#define MPU9520_ACCEL_CONFIG_REG	0x1C
#define MPU9520_ACCEL_XOUT_H_REG	0x3B
#define MPU9520_GYRO_XOUT_H_REG		0x43

#define GYRO_FULL_SCALE_250_DPS		0x00
#define GYRO_250_DPS_DIVIDER		131.0
#define GYRO_FULL_SCALE_500_DPS		0x08
#define GYRO_500_DPS_DIVIDER		65.5
#define GYRO_FULL_SCALE_1000_DPS	0x10
#define GYRO_1000_DPS_DIVIDER		32.8
#define GYRO_FULL_SCALE_2000_DPS	0x18
#define GYRO_2000_DPS_DIVIDER		16.4

#define ACC_FULL_SCALE_2_G			0x00
#define ACC_2G_DIVIDER				16384
#define ACC_FULL_SCALE_4_G			0x08
#define ACC_4G_DIVIDER				8192
#define ACC_FULL_SCALE_8_G			0x10
#define ACC_8G_DIVIDER				4096
#define ACC_FULL_SCALE_16_G			0x18
#define ACC_16G_DIVIDER				2048

typedef enum {_mpu9520_init_acc,_mpu9520_init_gyro, _mpu9520_poll_acc, _mpu9520_read_acc, _mpu9520_poll_gyro, _mpu9520_read_gyro} _mpu9520_states_t;

// Handle to SPI
static spi_p _spi_mpu9520 = 0;

fifo_desc_t *_spi_rx_fifo_desc = NULL;

// mpu9520
static int16_t _x_acc = 0;
static int16_t _y_acc = 0;
static int16_t _z_acc = 0;

static int16_t _x_gyro = 0;
static int16_t _y_gyro = 0;
static int16_t _z_gyro = 0;

static void _mpu9250_call_back(spi_p spi_instance, uint8_t spi_last_received_byte);

void mpu_9520_init(void) {
	/* Configure SPI interrupts for Master only. */
	NVIC_ClearPendingIRQ(SPI0_IRQn);
	NVIC_DisableIRQ(SPI0_IRQn);
	NVIC_SetPriority(SPI0_IRQn, 0);
	NVIC_EnableIRQ(SPI0_IRQn);
	
	ioport_set_pin_peripheral_mode(SPI0_MISO_GPIO, SPI0_MISO_FLAGS);
	ioport_set_pin_peripheral_mode(SPI0_MOSI_GPIO, SPI0_MOSI_FLAGS);
	ioport_set_pin_peripheral_mode(SPI0_SPCK_GPIO, SPI0_SPCK_FLAGS);
	ioport_set_pin_peripheral_mode(SPI0_NPCS1_GPIO, SPI0_NPCS1_FLAGS);
	
	_spi_mpu9520 = spi_new_instance(SPI0, 1, 300000UL, 0, 16, _mpu9250_call_back);
	
	_spi_rx_fifo_desc = spi_get_rx_fifo_desc(_spi_mpu9520);
	
	_mpu9250_call_back(0,0);
}
	
// ----------------------------------------------------------------------------------------------------------------------
float get_x_accel() {
	NVIC_DisableIRQ(SPI0_IRQn);
	float _tmp = ((float)_x_acc)/ACC_2G_DIVIDER;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
float get_y_accel() {
	NVIC_DisableIRQ(SPI0_IRQn);
	float _tmp = ((float)_y_acc)/ACC_2G_DIVIDER;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
float get_z_accel() {
	NVIC_DisableIRQ(SPI0_IRQn);
	float _tmp = ((float)_z_acc)/ACC_2G_DIVIDER;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
int16_t get_raw_x_accel() {
	NVIC_DisableIRQ(SPI0_IRQn);
	int16_t _tmp = _x_acc;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
int16_t get_raw_y_accel() {
	NVIC_DisableIRQ(SPI0_IRQn);
	int16_t _tmp = _y_acc;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
int16_t get_raw_z_accel() {
	NVIC_DisableIRQ(SPI0_IRQn);
	int16_t _tmp = _z_acc;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
float get_x_rotation() {
	NVIC_DisableIRQ(SPI0_IRQn);
	float _tmp = ((float)_x_gyro)/GYRO_500_DPS_DIVIDER;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
float get_y_rotation() {
	NVIC_DisableIRQ(SPI0_IRQn);
	float _tmp = ((float)_y_gyro)/GYRO_500_DPS_DIVIDER;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
float get_z_rotation() {
	NVIC_DisableIRQ(SPI0_IRQn);
	float _tmp = ((float)_z_gyro)/GYRO_500_DPS_DIVIDER;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
int16_t get_raw_x_rotation() {
	NVIC_DisableIRQ(SPI0_IRQn);
	int16_t _tmp = _x_gyro;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
int16_t get_raw_y_rotation() {
	NVIC_DisableIRQ(SPI0_IRQn);
	int16_t _tmp = _y_gyro;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
int16_t get_raw_z_rotation() {
	NVIC_DisableIRQ(SPI0_IRQn);
	int16_t _tmp = _z_gyro;
	NVIC_EnableIRQ(SPI0_IRQn);
	return _tmp;
}

// ----------------------------------------------------------------------------------------------------------------------
static void _mpu9250_write_2_reg(uint8_t reg, uint8_t value) {
	uint8_t buf[2];
	buf[0] = reg;
	buf[1] = value;

	spi_send_bytes(_spi_mpu9520, buf , 2);
}

// ----------------------------------------------------------------------------------------------------------------------
static void _mpu9250_read_reg(uint8_t reg, uint8_t no_of_bytes)
{
	uint8_t send[no_of_bytes+1];
	send[0] = MPU9520_READ | reg;
	for (uint8_t i = 1; i <= no_of_bytes; i++)
	{
		send[i] = 0;
	}
	
	fifo_flush(_spi_rx_fifo_desc);
	spi_send_bytes(_spi_mpu9520, send , no_of_bytes+1);
}

// ----------------------------------------------------------------------------------------------------------------------
static void _poll_acc(void) {
	_mpu9250_read_reg(MPU9520_ACCEL_XOUT_H_REG ,6);
}

// ----------------------------------------------------------------------------------------------------------------------
static void _poll_gyro(void) {
	_mpu9250_read_reg(MPU9520_GYRO_XOUT_H_REG ,6);
}
// ----------------------------------------------------------------------------------------------------------------------
static void _mpu9250_call_back(spi_p spi_instance, uint8_t spi_last_received_byte)
{
	uint8_t lsb = 0, msb = 0;
	static _mpu9520_states_t state = _mpu9520_init_acc;

	switch (state)
	{
		case _mpu9520_init_acc:
		{
			// Setup Acc
			state = _mpu9520_init_gyro;
			_mpu9250_write_2_reg(MPU9520_ACCEL_CONFIG_REG, ACC_FULL_SCALE_2_G);
			break;
		}
		
		case _mpu9520_init_gyro:
		{
			if (fifo_get_used_size(_spi_rx_fifo_desc) == 2) {
				// Setup Gyro
				state = _mpu9520_poll_acc;
				fifo_flush(_spi_rx_fifo_desc);
				_mpu9250_write_2_reg(MPU9520_GYRO_CONFIG_REG, GYRO_FULL_SCALE_500_DPS);
			}
			break;
		}

		case _mpu9520_poll_acc:
		{
			if (fifo_get_used_size(_spi_rx_fifo_desc) == 2) {
				state = _mpu9520_read_acc;
				fifo_flush(_spi_rx_fifo_desc);
				_poll_acc();
			}
			break;
		}

		case _mpu9520_read_acc:
		{
			if (fifo_get_used_size(_spi_rx_fifo_desc) == 7) {
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb); // Throw away the command response
				fifo_pull_uint8(_spi_rx_fifo_desc, &msb);
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb);
				_x_acc = (msb << 8) | lsb;
				fifo_pull_uint8(_spi_rx_fifo_desc, &msb);
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb);
				_y_acc = (msb << 8) | lsb;
				fifo_pull_uint8(_spi_rx_fifo_desc, &msb);
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb);
				_z_acc = (msb << 8) | lsb;
				
				state = _mpu9520_read_gyro;
				_poll_gyro();
			}
			break;
		}

		case _mpu9520_read_gyro:
		{
			if (fifo_get_used_size(_spi_rx_fifo_desc) == 7)  {
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb); // Throw away the command response
				fifo_pull_uint8(_spi_rx_fifo_desc, &msb);
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb);
				_x_gyro = (msb << 8) | lsb;
				fifo_pull_uint8(_spi_rx_fifo_desc, &msb);
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb);
				_y_gyro = (msb << 8) | lsb;
				fifo_pull_uint8(_spi_rx_fifo_desc, &msb);
				fifo_pull_uint8(_spi_rx_fifo_desc, &lsb);
				_z_gyro = (msb << 8) | lsb;

				state = _mpu9520_read_acc;
			}
			break;
		}

		default:
		break;
	}
}	

void poll_mpu_9520(void) {
	_poll_acc();
}