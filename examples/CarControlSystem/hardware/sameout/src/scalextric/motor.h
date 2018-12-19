/*
 * motor.h
 *
 * Created: 19/12/2018 08:38:11
 *  Author: IHA
 */ 


#ifndef MOTOR_H_
#define MOTOR_H_

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Initialise the H-Bridge driver to control the cars motor.

@note Must be once called before any other functions are used in this driver.
*/
void motor_init(void);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Set speed of the Car Motor.

@note The motor will not be able to start for speed percents lower than ~50%.

@param[in] speed_percent [0 ... 100].
*/
void motor_set_speed(uint8_t speed_percent);

//-------------------------------------------------
/**
@ingroup board_public_function
@brief Set brake intensity of the Car Motor.

@param[in] brake_percent [0 ... 100].
*/
void motor_set_brake(uint8_t brake_percent);

#endif /* MOTOR_H_ */