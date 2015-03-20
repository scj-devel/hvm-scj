#include "types.h"
#include "classes.h"
#include "methods.h"
#include "rom_heap.h"
#include "rom_access.h"
#include "allocation_point.h"

#ifdef FLASHSUPPORT
extern void initROMHeap(void);
extern Object* gc_allocatepObject(unsigned short dobjectSize, unsigned short pobjectsize);
#endif
extern void init_memory_lock(void);
extern void lock_memory(void);
extern void unlock_memory(void);

extern VMMemory* currentMemoryArea;

void initGC(void) {
    init_memory_lock();
#ifdef FLASHSUPPORT
    initROMHeap();
#endif
    initDefaultRAMAllocationPoint();

}

static Object* gc_allocatepdObject(uint32 objectSize, unsigned char* store, uint32 *top, uint32 size, char clear) {
    return allocData(objectSize, store, top, size, clear);
}

#if defined(VM_MEMORY_UPDATEMAXUSED_USED)
extern int16 vm_Memory_updateMaxUsed(int32 *fp, int32 m);
#endif
Object* gc_allocateObject(uint32 dobjectSize, uint32 pobjectsize) {
    if (pobjectsize == 0) {
        Object* obj;
        uint32 top;
        lock_memory();
        top = HEAP_REF(currentMemoryArea, VMMemory* )->free;
        obj = gc_allocatepdObject(dobjectSize + sizeof(Object), (unsigned char*) (pointer) HEAP_REF(currentMemoryArea, VMMemory* )->base, &top, HEAP_REF(currentMemoryArea, VMMemory* )->size, 1);

        HEAP_REF(currentMemoryArea, VMMemory* )->free = top;
#if defined(VM_MEMORY_UPDATEMAXUSED_USED)
        vm_Memory_updateMaxUsed(0, (int32)(pointer)currentMemoryArea);
#endif
        unlock_memory();
        return obj;
    }
#ifdef FLASHSUPPORT
    else {
        return gc_allocatepObject(dobjectSize, pobjectsize);
    }
#else
    return 0;
#endif
}

unsigned short getClassIndex(Object* obj) {
#ifdef FLASHSUPPORT
    if (isRomRef((uint8*) obj)) {
        return get_rom_word((uint8*) &obj->classIndexAndRefcount) & 0x3ff;
    } else {
#endif
    return *HEAP_REF(obj, Object*);
#ifdef FLASHSUPPORT
}
#endif
}

void setClassIndex(Object* obj, unsigned short classIndex) {
#ifdef FLASHSUPPORT
    if (isRomRef((uint8*) obj)) {
        uint16 value;
        value = get_rom_word((uint8*) &obj->classIndexAndRefcount);
        value &= 0xfc00;
        value |= (classIndex & 0x3ff);
        set_rom_word((uint8*) &obj->classIndexAndRefcount, value);
    } else {
#endif
    *HEAP_REF(obj, Object*) = classIndex;
#ifdef FLASHSUPPORT
}
#endif
}
