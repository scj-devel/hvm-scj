#include "ostypes.h"
#include "types.h"
#include "rom_heap.h"
#include "rom_access.h"
#include "allocation_point.h"

#ifdef FLASHSUPPORT
typedef struct _pobject {
    uint16 dobject;
    unsigned short classIndex;
} PObject;

extern Object* gc_allocatepdObject(uint32 objectSize, unsigned char* store, uint32 *top, uint32 size, char clear);
extern unsigned char rom_writeable(void);

extern AllocationPoint* ramHeap;
static AllocationPoint* romHeap;

void initROMHeap(void) {
    romHeap = getDefaultROMAllocationPoint();
    allocData(1, romHeap.store, &romHeap.top, romHeap.size, 0); /* so we can use 0 as null pointer */
}

uint16 get_rom_heap_size(void)
{
    return romHeap -> top;
}

uint8 get_rom_heap_address(uint16 address)
{
    return readStore(address, romHeap->store);
}

uint8 isRomRef(uint8* ptr)
{
    return isInStore(ptr, romHeap);
}


Object* gc_allocatepObject(unsigned short dobjectSize, unsigned short pobjectsize) {
    PObject *pobject = (PObject *) gc_allocatepdObject(pobjectsize + sizeof(PObject), romHeap.store, &romHeap.top, romHeap.size, rom_writeable());
    if (pobject) {
        Object* dobject = gc_allocatepdObject(dobjectSize, ramHeap->store, &ramHeap->top, ramHeap->size, 1);
        if (dobject) {
            unsigned short offset = getOffsetOf(dobject, ramHeap->store);
            set_rom_word((uint8*)&pobject->dobject, offset);
            return (Object*) &(pobject -> classIndex);
        }
    }
    return 0;
}

unsigned char* get_rom_data(unsigned char* object, unsigned short offset) {
    return &object[sizeof(Object) + offset];
}

unsigned char* get_ram_data(unsigned char* object, unsigned short offset) {
    PObject *pobject = (PObject *) (object - sizeof(uint16));
    unsigned short doffset = get_rom_word((uint8*)&pobject->dobject);
    object = (unsigned char*)(pointer)getHeapPointer(doffset + offset, ramHeap -> store);
    return object;
}

int32 get_rom_reference(unsigned char* object) {
    uint32 offset = get_rom_dword(object);

    if (offset == 0) {
        return 0;
    } else if (offset & 1) {
        offset = offset >> 1;
        return getHeapPointer(offset, ramHeap -> store);
    } else {
        offset = offset >> 1;
        return getHeapPointer(offset, romHeap -> store);
    }
}

int set_rom_reference(unsigned char* object, int32 reference) {
    uint32 offset;

    if (reference == 0) {
        offset = 0;
    } else if (isRomRef((uint8*)(pointer)reference)) {
        offset = getOffsetOf((Object*)(pointer)reference, romHeap);
        offset = offset << 1;
    } else {
        offset = getOffsetOf((Object*)(pointer)reference, ramHeap->store);
        offset = offset << 1;
        offset = offset | 1;
    }
    return set_rom_dword(object, offset);
}
#endif
