/*
* front_light.c
*
* Created: 14/11/2017 11:00:04
*  Author: IHA
*/
#include <asf.h>
#include "front_light.h"
#include "rear_light.h"
#include "brake_light.h"
#include "scalextric.h"

/*
* pwm_drivers.c
*
* Created: 15-03-2016 13:23:29
*  Author: IHA
*/

#include <asf.h>
#include "front_light.h"

/*----------------------------------------------------------------------------*/
// General PWM definitions
#define PWM_ID							ID_PWM0
#define PWM_INST						PWM0

/*----------------------------------------------------------------------------*/
// PWM definitions for light control

/** PWM frequency in Hz */
#define LIGHT_PWM_FREQUENCY				200
/** Period value of PWM output waveform */
#define LIGHT_PWM_PERIOD_VALUE			100
/** Initial duty cycle value */
#define LIGHT_PWM_INIT_DUTY_VALUE		0

// Head light PIN definitions
#define PIN_PWM_HEAD_LIGHT_GPIO			PIO_PC19_IDX
#define PIN_PWM_HEAD_LIGHT_FLAGS		(IOPORT_MODE_MUX_B)
#define PIN_PWM_HEAD_LIGHT_CHANNEL		PWM_CHANNEL_2

// Head Light duty cycle values
#define HEAD_LIGHT_OFF_DUTY				0
#define HEAD_LOW_BEAM_DUTY_VALUE		30
#define HEAD_HIGH_BEAM_DUTY_VALUE		100

// Rear light PIN definitions
#define PIN_PWM_REAR_LIGHT_GPIO		PIO_PC13_IDX
#define PIN_PWM_H_REAR_LIGHT_FLAGS		(IOPORT_MODE_MUX_B)
#define PIN_PWM_REAR_LIGHT_CHANNEL		PWM_CHANNEL_3

// Read Light duty cycle values
#define REAR_LIGHT_DUTY_VALUE			25
#define REAR_BRAKE_LIGHT_DUTY_VALUE		100

/** PWM channel instance */
static pwm_channel_t g_pwm_channel;

/** Rear/Brake light states */
static int _rear_light_on = 0;
static int _brake_light_on = 0;

/** Head light states */
static int _front_light_on = 0;

// ----------------------------------------------------------------------------------------------------------------------
void front_light_init()
{
	// Enable PWM peripheral clock
	pmc_enable_periph_clk(PWM_ID);

	/* Set PWM clock B as LIGHT_PWM_FREQUENCY*LIGHT_PWM_PERIOD_VALUE */
	pwm_clock_t clock_setting = {
		.ul_clka = 0,
		.ul_clkb = LIGHT_PWM_FREQUENCY * LIGHT_PWM_PERIOD_VALUE,
		.ul_mck = sysclk_get_peripheral_hz()  // Ex. 150000000ul
	};
	pwm_init(PWM0, &clock_setting);
	
	// Set Light PWM Channels to use output pins instead of GPIO
	ioport_disable_pin(PIN_PWM_HEAD_LIGHT_GPIO);
	ioport_set_pin_mode(PIN_PWM_HEAD_LIGHT_GPIO, PIN_PWM_HEAD_LIGHT_FLAGS);
	ioport_disable_pin(PIN_PWM_REAR_LIGHT_GPIO);
	ioport_set_pin_mode(PIN_PWM_REAR_LIGHT_GPIO, PIN_PWM_H_REAR_LIGHT_FLAGS);

	// Disable Light PWM channels
	pwm_channel_disable(PWM_INST, PIN_PWM_HEAD_LIGHT_CHANNEL);
	pwm_channel_disable(PWM_INST, PIN_PWM_REAR_LIGHT_CHANNEL);

	// --- Setup PWM channel for head light
	/* Period is left-aligned */
	g_pwm_channel.alignment = PWM_ALIGN_LEFT;
	/* Output waveform starts at a LOW level */
	g_pwm_channel.polarity = PWM_LOW;
	/* Use PWM clock B as source clock */
	g_pwm_channel.ul_prescaler = PWM_CMR_CPRE_CLKB;
	/* Period value of output waveform */
	g_pwm_channel.ul_period = LIGHT_PWM_PERIOD_VALUE;
	/* Duty cycle value of output waveform */
	g_pwm_channel.ul_duty = LIGHT_PWM_INIT_DUTY_VALUE;
	g_pwm_channel.channel = PIN_PWM_HEAD_LIGHT_CHANNEL;
	
	g_pwm_channel.b_sync_ch = false;
	pwm_channel_init(PWM_INST, &g_pwm_channel);

	// --- Setup PWM for rear light
	g_pwm_channel.channel = PIN_PWM_REAR_LIGHT_CHANNEL;
	g_pwm_channel.ul_duty = LIGHT_PWM_INIT_DUTY_VALUE;
	pwm_channel_init(PWM_INST, &g_pwm_channel);

	/* Enable PWM channels for light */
	pwm_channel_enable(PWM_INST, PIN_PWM_HEAD_LIGHT_CHANNEL);
	pwm_channel_enable(PWM_INST, PIN_PWM_REAR_LIGHT_CHANNEL);
}

// ----------------------------------------------------------------------------------------------------------------------
void front_light_turn_on() {
	_front_light_on = 1;
}

// ----------------------------------------------------------------------------------------------------------------------
void front_light_turn_off()
{
	g_pwm_channel.channel = PIN_PWM_HEAD_LIGHT_CHANNEL;
	pwm_channel_update_duty(PWM_INST, &g_pwm_channel, HEAD_LIGHT_OFF_DUTY);
	_front_light_on = 0;
}

// ----------------------------------------------------------------------------------------------------------------------
void front_light_high_beam()
{
	if (_front_light_on) {
		g_pwm_channel.channel = PIN_PWM_HEAD_LIGHT_CHANNEL;
		pwm_channel_update_duty(PWM_INST, &g_pwm_channel, HEAD_HIGH_BEAM_DUTY_VALUE);
	}
}

// ----------------------------------------------------------------------------------------------------------------------
void front_light_low_beam()
{
	if (_front_light_on) {
		g_pwm_channel.channel = PIN_PWM_HEAD_LIGHT_CHANNEL;
		pwm_channel_update_duty(PWM_INST, &g_pwm_channel, HEAD_LOW_BEAM_DUTY_VALUE);
	}
}

// ----------------------------------------------------------------------------------------------------------------------
void rear_light_turn_on(void)
{
	g_pwm_channel.channel = PIN_PWM_REAR_LIGHT_CHANNEL;
	pwm_channel_update_duty(PWM_INST, &g_pwm_channel, REAR_LIGHT_DUTY_VALUE);
	_rear_light_on = 1;
}

// ----------------------------------------------------------------------------------------------------------------------
void rear_light_turn_off(void)
{
	if (!_brake_light_on)
	{
		g_pwm_channel.channel = PIN_PWM_REAR_LIGHT_CHANNEL;
		pwm_channel_update_duty(PWM_INST, &g_pwm_channel, 0);
	}

	_rear_light_on = 0;
}

// ----------------------------------------------------------------------------------------------------------------------
void brake_light_turn_on(void)
{
	g_pwm_channel.channel = PIN_PWM_REAR_LIGHT_CHANNEL;
	pwm_channel_update_duty(PWM_INST, &g_pwm_channel, REAR_BRAKE_LIGHT_DUTY_VALUE);
	_brake_light_on = 1;
}

// ----------------------------------------------------------------------------------------------------------------------
void brake_light_turn_off(void)
{
	if (_rear_light_on) {
		g_pwm_channel.channel = PIN_PWM_REAR_LIGHT_CHANNEL;
		pwm_channel_update_duty(PWM_INST, &g_pwm_channel, REAR_LIGHT_DUTY_VALUE);
		} else {
		g_pwm_channel.channel = PIN_PWM_REAR_LIGHT_CHANNEL;
		pwm_channel_update_duty(PWM_INST, &g_pwm_channel, 0);
	}

	_brake_light_on = 0;
}
