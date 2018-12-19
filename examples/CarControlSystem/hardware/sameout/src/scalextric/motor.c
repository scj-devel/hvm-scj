/*
 * motor.c
 *
 * Created: 19/12/2018 08:38:31
 *  Author: IHA
 */ 
 #include <asf.h>
 #include "motor.h"
 #include "scalectrix_board_def.h"

 /*----------------------------------------------------------------------------*/
 // General PWM definitions
 #define PWM_ID							ID_PWM0
 #define PWM_INST						PWM0

 /*----------------------------------------------------------------------------*/
 // PWM definitions for motor control
 // H-Bridge: TLE5205-2G

 /** PWM frequency in Hz */
 #define MOTOR_PWM_FREQUENCY			5000
 /** Period value of PWM output waveform */
 #define MOTOR_PWM_PERIOD_VALUE		100
 /** Initial duty cycle value */
 #define MOTOR_PWM_INIT_DUTY_VALUE	0

  // H-Bridge IN1 PIN definitions
 #define PIN_PWM_H_BRIDGE_IN1_GPIO		PIO_PD11_IDX
 #define PIN_PWM_H_BRIDGE_IN1_FLAGS		(IOPORT_MODE_MUX_B)
 #define PIN_PWM_H_BRIDGE_IN1_CHANNEL	PWM_CHANNEL_0

  // H-Bridge IN2 PIN definitions
 #define PIN_PWM_H_BRIDGE_IN2_GPIO		PIO_PA2_IDX
 #define PIN_PWM_H_BRIDGE_IN2_FLAGS		(IOPORT_MODE_MUX_A)
 #define PIN_PWM_H_BRIDGE_IN2_CHANNEL	PWM_CHANNEL_1



 /** PWM channel instance */
 static pwm_channel_t g_pwm_channel;
 
 void motor_init(void) 
 {
 	// Set H-Bridge PWM Channels to use output pins instead of GPIO
	ioport_disable_pin(PIN_PWM_H_BRIDGE_IN1_GPIO);
	ioport_set_pin_mode(PIN_PWM_H_BRIDGE_IN1_GPIO, PIN_PWM_H_BRIDGE_IN1_FLAGS);	
	ioport_disable_pin(PIN_PWM_H_BRIDGE_IN2_GPIO);
	ioport_set_pin_mode(PIN_PWM_H_BRIDGE_IN2_GPIO, PIN_PWM_H_BRIDGE_IN2_FLAGS);
	
	// Enable PWM peripheral clock
	pmc_enable_periph_clk(PWM_ID);
	
/*		## Normal way to setup PWM clocks a and b ##
		// Set PWM clock B as LIGHT_PWM_FREQUENCY*LIGHT_PWM_PERIOD_VALUE
		pwm_clock_t clock_setting = {
			.ul_clka = 0,
			.ul_clkb = LIGHT_PWM_FREQUENCY * LIGHT_PWM_PERIOD_VALUE,
			.ul_mck = sysclk_get_peripheral_hz()  // Ex. 150000000ul
		};
		pwm_init(PWM0, &clock_setting);
		*/

		// IHA Way to only setup clock a (taken from pwm.c function uint32_t pwm_init(Pwm *p_pwm, pwm_clock_t *clock_config))
		// Set PWM clock A as MOTOR_PWM_FREQUENCY * MOTOR_PWM_PERIOD_VALUE
		pwm_clock_t clock_setting = {
		.ul_clka = MOTOR_PWM_FREQUENCY * MOTOR_PWM_PERIOD_VALUE, // Ex. 1000 * 100,
		.ul_clkb = 0,
		.ul_mck = sysclk_get_peripheral_hz()  // Ex. 150000000ul
		};

		// remember clkb setting
		uint32_t tmp = PWM_INST->PWM_CLK;
		pwm_init(PWM_INST, &clock_setting);
		// restore clkb setting
		PWM_INST->PWM_CLK |= tmp & 0xFFFF0000;
		
	// Disable H-Bridge PWM channels
	pwm_channel_disable(PWM_INST, PIN_PWM_H_BRIDGE_IN1_CHANNEL);
	pwm_channel_disable(PWM_INST, PIN_PWM_H_BRIDGE_IN2_CHANNEL);
			
	// --- Setup PWM channel for IN1 input on H-Bridge
	/* Period is left-aligned */
	g_pwm_channel.alignment = PWM_ALIGN_LEFT;
	/* Output waveform starts at a low level */
	g_pwm_channel.polarity = PWM_LOW;
	/* Use PWM clock A as source clock */
	g_pwm_channel.ul_prescaler = PWM_CMR_CPRE_CLKA;
	/* Period value of output waveform */
	g_pwm_channel.ul_period = MOTOR_PWM_PERIOD_VALUE;
	/* Duty cycle value of output waveform */
	g_pwm_channel.ul_duty = MOTOR_PWM_INIT_DUTY_VALUE;
	g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN1_CHANNEL;
	pwm_channel_init(PWM_INST, &g_pwm_channel);	
	
	// --- Setup PWM for IN2 input on H-Bridge
	// channel should use channel 0 counter to be sure they are running in sync
	g_pwm_channel.b_sync_ch = true;
	g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN2_CHANNEL;
	g_pwm_channel.ul_duty = MOTOR_PWM_INIT_DUTY_VALUE; 
	pwm_channel_init(PWM_INST, &g_pwm_channel);	
	
	/* Enable PWM channels for H-Bridge */
	// It is only needed to enable CH0, because CH1 is synchronized
	pwm_channel_enable(PWM_INST, PIN_PWM_H_BRIDGE_IN1_CHANNEL);

	// Initialize update mode of the Sync channels to: "Method 1: Manual write of duty-cycle and manual trigger of the update"
	pwm_sync_init(PWM_INST, PWM_SYNC_UPDATE_MODE_0,	0);
 }

 // ----------------------------------------------------------------------------------------------------------------------
 void motor_set_speed(uint8_t speed_percent)
 {
	 if (speed_percent > 100) {
		 speed_percent = 100;
	 }
	 
	 if  (speed_percent > 0) {
		 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN1_CHANNEL;
		 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, speed_percent);
		 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN2_CHANNEL;
		 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, speed_percent);
		 } else { // Free Run
		 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN1_CHANNEL;
		 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, 0);
		 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN2_CHANNEL;
		 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, 0);
	 }
	 
	 // Trig sync update
	 pwm_sync_unlock_update(PWM_INST);
 }

 // ----------------------------------------------------------------------------------------------------------------------
 void motor_set_brake(uint8_t brake_percent)
 {
	 if (brake_percent > 100) {
		 brake_percent = 100;
	 }
	 
	 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN2_CHANNEL;
	 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, 0);
	 
	 if  (brake_percent > 0) {
		 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN1_CHANNEL;
		 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, brake_percent);
		 
		 } else { // Free Run
		 g_pwm_channel.channel = PIN_PWM_H_BRIDGE_IN1_CHANNEL;
		 pwm_channel_update_duty(PWM_INST, &g_pwm_channel, 0);
	 }
	 
	 // Trig sync update
	 pwm_sync_unlock_update(PWM_INST);
 }


