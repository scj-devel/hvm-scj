/*
 * mpu_9520.h
 *
 * Created: 15-03-2016 13:30:58
 *  Author: IHA
 */ 

#ifndef MPU_9520_H_
#define MPU_9520_H_

// This macro should be moved from here - IHA 2016-05-11
#define ioport_set_pin_peripheral_mode(pin, mode) \
do {\
	ioport_set_pin_mode(pin, mode);\
	ioport_disable_pin(pin);\
} while (0)


// ####################################### GYRO/ACC (MPU-9520) ############################################

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Polls the mpu_9520 GYRO/ACC.

@note Must be called every time a new GYRO/ACC measuring is needed.
*/
void poll_mpu_9520(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Initialize GYRO/ACC functions for the car.

@note Must be called once before the GYRO/ACC functions can be used.
*/
void mpu_9520_init(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest X acceleration.

@return X-Acceleration [g].
*/
float get_x_accel(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Y acceleration.

@return Y-Acceleration [g].
*/
float get_y_accel(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Z acceleration.

@return Z-Acceleration [g].
*/
float get_z_accel(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Raw X acceleration.

@return Raw X-Acceleration in binary value.
*/
int16_t get_raw_x_accel(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Raw Y acceleration.

@return Raw Y-Acceleration in binary value.
*/
int16_t get_raw_y_accel(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Raw Z acceleration.

@return Raw Z-Acceleration in binary value.
*/
int16_t get_raw_z_accel(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest X rotation.

@return X-rotation [degrees/s].
*/
float get_x_rotation(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Y rotation.

@return Y-rotation [degrees/s].
*/
float get_y_rotation(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Z rotation.

@return Z-rotation [degrees/s].
*/
float get_z_rotation(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Raw X rotation.

@return Raw X-rotation in binary value.
*/
int16_t get_raw_x_rotation(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Raw Y rotation.

@return Raw Y-rotation in binary value.
*/
int16_t get_raw_y_rotation(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Get newest Raw Z rotation.

@return Raw Z-rotation in binary value.
*/
int16_t get_raw_z_rotation(void);


#endif /* MPU_9520_H_ */