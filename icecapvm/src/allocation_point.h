/*
 * allocation_point.h
 *
 *  Created on: Dec 6, 2011
 *      Author: sek
 */

#ifndef ALLOCATION_POINT_H_
#define ALLOCATION_POINT_H_

#include "ostypes.h"
#include "types.h"

typedef struct allocationPoint AllocationPoint;

struct allocationPoint
{
    unsigned char* store;
    uint32 top;
    uint32 size;
};

typedef struct PACKED _VMMemory
{
    Object header;
    uint32 base;
    uint32 size;
    uint32 free;
} VMMemory;

void initDefaultRAMAllocationPoint();
AllocationPoint* getDefaultROMAllocationPoint();
Object* allocData(uint32 objectSize, unsigned char* store, uint32 *top, uint32 size, char clear);
uint8 readStore(uint16 offset, unsigned char* store);
uint8 isInStore(uint8* ptr);
uint32 getOffsetOf(Object* dobject, unsigned char* heap);
uint32 getHeapPointer(uint32 offset, unsigned char* store);
void initAllocationPoints();
void freeAllocationPoint(AllocationPoint* ap);

#endif /* ALLOCATION_POINT_H_ */
