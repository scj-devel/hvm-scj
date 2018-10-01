/*
 * rom_heap.h
 *
 *  Created on: 10/02/2011
 *      Author: sek
 */

#ifndef ROM_HEAP_H_
#define ROM_HEAP_H_

unsigned char* get_rom_data(unsigned char* object, unsigned short offset);
unsigned char* get_ram_data(unsigned char* object, unsigned short offset);
int32 get_rom_reference(unsigned char* object);
int set_rom_reference(unsigned char* object, int32 reference);
uint8 isRomRef(uint8* ptr);

uint16 get_rom_heap_size(void);
uint8 get_rom_heap_address(uint16 address);

#endif /* ROM_HEAP_H_ */
