/*
 * io_allOS.c
 *
 *  Created on: Jan 9, 2013
 *      Author: sek
 */

#include "io.h"

int byteCount;

char* readName() {
    int count, length;
    char* name;

    length = readShort();
    if (length > 0) {
        name = _malloc_(length + 1);

        for (count = 0; count < length; count++) {
            name[count] = readByte();
        }
        name[count] = '\0';
        return name;
    } else {
        return "unknown";
    }
}

uint32 readInt() {
    uint32 i;
    i = ((uint32)readShort()) << 16;
    i |= readShort();
    return i;
}

int getByteCount(void) {
    return byteCount;
}

char* getOutputFile(void) {
    return OUTPUTFILE;
}

void dumpInt(uint32 i) {
    dumpShort((i >> 16) & 0xffff);
    dumpShort(i & 0xffff);
}

void dumpShort(unsigned short s) {
    dumpByte((s >> 8) & 0xff);
    dumpByte(s & 0xff);
}

unsigned short readShort() {
    unsigned short s;
    s = readByte() << 8;
    s |= readByte();
    return s;
}

