/*
 * scalectrix_board_def.h
 *
 * Created: 15-03-2016 13:36:18
 *  Author: IHA
 */ 

#include <asf.h>
#include "stdio_serial.h"

#ifndef SCALECTRIX_BOARD_DEF_H_
#define SCALECTRIX_BOARD_DEF_H_

/** Board oscillator settings */
#define BOARD_FREQ_SLCK_XTAL            (32768U)
#define BOARD_FREQ_SLCK_BYPASS          (32768U)
#define BOARD_FREQ_MAINCK_XTAL          (12000000U)
#define BOARD_FREQ_MAINCK_BYPASS        (12000000U)

/** Master clock frequency */
#define BOARD_MCK                       CHIP_FREQ_CPU_MAX

/** board main clock xtal statup time */
#define BOARD_OSC_STARTUP_US            15625

/*----------------------------------------------------------------------------*/
/**
 * \page scalectrix_info "Scalectrix - Main Board informations"
 * This page lists several definition related to the board description.
 *
 * \section Definitions
 * - \ref BOARD_NAME
 */

/** Name of the board */
//#define BOARD_NAME "SCALECTRIX_BOARD"
/** Board definition */
//#define same70xpld
/** Family definition (already defined) */
#define same70
/** Core definition */
#define cortexm7


#define USE_UART3_CFG

#endif /* SCALECTRIX_BOARD_DEF_H_ */