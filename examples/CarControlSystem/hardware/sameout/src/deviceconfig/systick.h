/*
 * systick_setup.h
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


#ifndef SYSTICK_SETUP_H_
#define SYSTICK_SETUP_H_

void systick_init(void);

void SysTick_Handler(void);

void mdelay(uint32_t ul_dly_ticks);

uint32_t get_ticks(void);

#endif /* SYSTICK_SETUP_H_ */