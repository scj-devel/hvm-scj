/*
 * rom_access.h
 *
 *  Created on: 24/02/2011
 *      Author: sek
 */

#ifndef ROM_ACCESS_H_
#define ROM_ACCESS_H_

#include "ostypes.h"

uint32 get_rom_dword(unsigned char *object);
uint16 get_rom_word(unsigned char *object);
uint8 get_rom_byte(unsigned char *object);
int set_rom_dword(unsigned char *object, uint32 msb);
int set_rom_word(unsigned char *object, uint32 lsb);
int set_rom_byte(unsigned char *object, uint8 lsb);

#endif /* ROM_ACCESS_H_ */
