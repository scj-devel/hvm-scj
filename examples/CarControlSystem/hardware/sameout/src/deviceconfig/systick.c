/*
 * systick.c
 *
 * Created: 10/02/2018 09:10
 *  Author: HSO
 *
 * Those SysTick functions is from Atmel Software Framework:
 *   The Getting-started Example, section Peripherals Configuration and Usage, SysTick.
 *
 * http://asf.atmel.com/docs/latest/sam.appnote.getting-started.sam4c/html/appdoc_chap_06_title7.html
 *
 */
#include "asf.h"
#include "stdio_serial.h"
#include "conf_board.h"
#include "conf_clock.h"
#include "../ostypes.h"
#include "systick.h"
#include "../scalextric/bluetooth.h"


extern uint8_t bt_initialised;

 /**
   Configure sys tick.
 */

 /**
   Global g_ul_ms_ticks in milliseconds since start of application
 */
 volatile uint32_t g_ul_ms_ticks = 0;

 /**
   Extern global systemTick variable defined in natives_allOS.c
   It is used to be able to make process switch
 */
 extern volatile uint8 systemTick;


 /**
   Configure systick for 1 ms
   To generate 1ms period, the only parameter of this function should be
   system clock frequency / 1000.
 */
 void systick_init(void)
 {
	 if (SysTick_Config(sysclk_get_cpu_hz() / 1000)) {
		 puts("-F- Systick configuration error\r");
		 while (1);
	 }
 }

/**
  Handler for System Tick interrupt.

  Process System Tick Event
  Increments the g_ul_ms_ticks counter.
 */

void SysTick_Handler(void)
{
	static uint8_t ms100_count = 100;

    g_ul_ms_ticks++;
	systemTick++;

    // As long as the BT-module is in initialisation state bluetooth_tick() needs to be called every 100 ms
    if ( !bt_initialised ) {
        if ( --ms100_count == 0 ) {
            ms100_count = 100;
            bluetooth_tick();
        }
    }
}

/**
  Wait for the given number of milliseconds (using the g_ul_ms_ticks
  generated by the SAM's microcontrollers's system tick).

  \param ul_dly_ticks  Delay to wait for, in milliseconds.
 */
void mdelay(uint32_t ul_dly_ticks)
{
	uint32_t ul_cur_ticks;

	ul_cur_ticks = g_ul_ms_ticks;
	while ((g_ul_ms_ticks - ul_cur_ticks) < ul_dly_ticks);
}

uint32_t get_ticks(void)
{
	uint32_t ul_cur_ticks;
	ul_cur_ticks = g_ul_ms_ticks;
	return ul_cur_ticks;
}

