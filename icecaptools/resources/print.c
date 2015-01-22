/*
 * print.c
 *
 *  Created on: Oct 16, 2012
 *      Author: sek
 */
#include "ostypes.h"

extern void sendbyte(unsigned char byte);

void printStr(const char* str) {
    while (*str) {
        sendbyte(*str);
        str++;
    }
}

void printROMStr(const char* str) {
  while (pgm_read_byte(str)) {
    sendbyte(pgm_read_byte(str));
        str++;
    }
}

static void printNibble(unsigned char nibble) {
    if (nibble < 10) {
        sendbyte('0' + nibble);
    } else {
        sendbyte('A' + (nibble - 10));
    }
}

void printByte(unsigned char c) {
    printNibble((c & 0xf0) >> 4);
    printNibble(c & 0x0f);
}

void printShort(unsigned short c) {
    printStr("0x");
    printByte((c & 0xff00) >> 8);
    printByte(c & 0xff);
}

void printMem(char* str, int length) {
    while (length > 0) {
        printByte(*str);
        printStr(" ");
        length--;
        str++;
    }
}

void printAddress(uint32 addr) {
    unsigned char i = 0;

    printStr("0x");

    while (i < 8) {
        printNibble((addr & 0xf0000000) >> 28);
        i++;
        addr = addr << 4;
    }
}
