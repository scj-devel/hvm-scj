/*
 * rom_access.c
 *
 *  Created on: 24/02/2011
 *      Author: sek
 */
#include "ostypes.h"
#include "rom_access.h"

#ifdef FLASHSUPPORT
extern unsigned char rom_writeable(void);

uint32 get_rom_dword(unsigned char *object) {
    return (((uint32)get_rom_word(object)) << 16) | get_rom_word(object + 2);
}

uint16 get_rom_word(unsigned char *object) {
    return (get_rom_byte(object) << 8) | get_rom_byte(object + 1);
}

uint8 get_rom_byte(unsigned char *object) {
    return pgm_read_byte(object);
}

int set_rom_dword(unsigned char *object, uint32 msb) {
    return set_rom_word(object, msb >> 16) + set_rom_word(object + 2, msb);
}

int set_rom_word(unsigned char *object, uint32 lsb) {
    return set_rom_byte(object, lsb >> 8) + set_rom_byte(object + 1, lsb);
}

int set_rom_byte(unsigned char *object, uint8 lsb) {
    if (rom_writeable()) {
        *((unsigned char*) object) = lsb;
        return 0;
    } else {
        if (pgm_read_byte(object) == lsb) {
            return 0;
        } else {
            return 1;
        }
    }
}
#endif

