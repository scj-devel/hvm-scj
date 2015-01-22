/*
 * ostypes.h
 *
 *  Created on: Nov 3, 2010
 *      Author: user
 */

#ifndef OSTYPES_H_
#define OSTYPES_H_

#define MS_10 10000000

/*x86*/

typedef unsigned short uint16;
typedef signed short int16;
typedef unsigned char uint8;
typedef signed char int8;

#ifdef FLASHSUPPORT
#define FLASHARG(x) , x
#else
#define FLASHARG(x)
#endif

#if defined(__GNUC__)
#ifndef _NOINLINE_
#define _NOINLINE_ /* __attribute__((noinline)) */
#endif
#include <stddef.h>
#ifndef PACKED
#define PACKED __attribute__ ((__packed__))
#endif
#else
#define _NOINLINE_
#error Please define PACKED, leave as empty definition if not supported
#endif

#if defined(PC32) || defined(WIN32)
#define DATAMEM
#define PROGMEM
#define RANGE
#define pgm_read_byte(x) *((unsigned char*)x)
#define pgm_read_word(x) *((unsigned short*)x)
#define pgm_read_pointer(x, typeofx) *((typeofx)x)
#define pgm_read_dword(x) pgm_read_pointer(x, uint32*)
typedef int int32;
typedef unsigned int uint32;
typedef unsigned int pointer;
#else
#ifdef PC64
#define DATAMEM
#define PROGMEM
#define RANGE
#define pgm_read_byte(x) *((unsigned char*)x)
#define pgm_read_word(x) *((unsigned short*)x)
#define pgm_read_pointer(x, typeofx) *((typeofx)x)
#define pgm_read_dword(x) pgm_read_pointer(x, uint32*)
typedef int int32;
typedef unsigned int uint32;
typedef unsigned long pointer;
#else
#ifdef CR16C
#define DATAMEM __attribute__ ((section (".data")))
#define PROGMEM
#define RANGE
#define pgm_read_byte(x) *((unsigned char*)x)
#define pgm_read_word(x) *((unsigned short*)x)
#define pgm_read_pointer(x, typeofx) *((typeofx)x)
#define pgm_read_dword(x) pgm_read_pointer(x, uint32*)
typedef signed long int32;
typedef unsigned long uint32;
typedef unsigned long pointer;
#else
#if defined(__IAR_SYSTEMS_ICC__)
#define DATAMEM
#include "platform.h"
#if defined(V850ES)
#define DATAMEM
#define PROGMEM
#define RANGE __huge
#define pgm_read_byte(x) *((unsigned char*)x)
#define pgm_read_word(x) *((unsigned short*)x)
#define pgm_read_pointer(x, typeofx) *((typeofx)x)
#define pgm_read_dword(x) pgm_read_pointer(x, uint32*)
typedef signed long int32;
typedef unsigned long uint32;
typedef unsigned long pointer;
#else
#error Unknown IAR environment 
#endif
#else
#if defined(SAM7S256)
#undef PACKED
#define PACKED
#define DATAMEM __attribute__ ((section (".data")))
#define PROGMEM
#define RANGE
#define pgm_read_byte(x) *((unsigned char*)x)
#define pgm_read_word(x) *((unsigned short*)x)
#define pgm_read_pointer(x, typeofx) *((typeofx)x)
#define pgm_read_dword(x) pgm_read_pointer(x, uint32*)
typedef int int32;
typedef unsigned int uint32;
typedef unsigned int pointer;
#else
/* We default to AVR - this may not be right. Add new section if introducing new architecture */
#define RANGE
#define DATAMEM
#include <avr/pgmspace.h> 
#define pgm_read_pointer(x, typeofx) pgm_read_word(x)
/*AVR*/
typedef signed long int32;
typedef unsigned long uint32;
typedef unsigned short pointer;
#ifndef AVR
#define AVR
#endif
#endif
#endif
#endif
#endif
#endif
#endif /* OSTYPES_H_ */
