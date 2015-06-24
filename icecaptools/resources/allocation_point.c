#include "types.h"
#include "classes.h"
#include "allocation_point.h"
#include "methods.h"

/* Not static. This way it is visible in the map file */
uint32 heap[JAVA_HEAP_SIZE >> 2];
#ifdef REF_OFFSET
extern void printStr(const char* str);
long heap_base;
#endif

#ifdef FLASHSUPPORT
extern const unsigned char pheap[PHEAP_SIZE] PROGMEM;
#endif

VMMemory* currentMemoryArea;

#if defined(VM_HVMHEAP_INIT_)
uint32 java_heap_size = JAVA_HEAP_SIZE;
#endif

extern VMMemory* heapArea;

#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
#include <stdlib.h>
#include <pthread.h>
#endif

#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
pthread_key_t key;
#endif

void initDefaultRAMAllocationPoint() {
	/*Behaviours for multicore version*/
	#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
	pthread_key_create(&key, NULL);
	#endif

	currentMemoryArea = (VMMemory*) &heap[0];

#ifdef REF_OFFSET
	if (sizeof(long) == 8)
	{
		heap_base = ((pointer) &heap[0]) - 4;
		currentMemoryArea->base = (uint32)(pointer) 4; /* base */

		/* for testing
		 heap_base = 100000;
		 currentMemoryArea->base = currentMemoryArea->base - heap_base; */
	}
	else
	{
		printStr("Cannot use REF_OFFSET on this architecture");
		for (;;);
	}
#else
	currentMemoryArea->base = (uint32) (pointer) &heap[0]; /* base */
#endif
	currentMemoryArea->size = JAVA_HEAP_SIZE; /* size */
	currentMemoryArea->free = 22; /* free */

	*(Object*) currentMemoryArea = VM_MEMORY_var;

	heapArea = currentMemoryArea = HEAP_UNREF(currentMemoryArea, VMMemory*);

	/*Behaviours for multicore version*/
	#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
	pthread_setspecific(key, currentMemoryArea);
	#endif
}

#ifdef PRINTFSUPPORT
#include <stdio.h>
#endif

#if defined(N_VM_HVMHEAP_GETHEAPSTART)
/* getHeapStart
 * param :
 * return: int
 */
int16 n_vm_HVMHeap_getHeapStart(int32 *sp)
{
	sp[0] = (int32)(pointer)HEAP_UNREF(&heap[0], pointer);
	return -1;
}
#endif

#ifdef USEMALLOCFREE
#include <stdlib.h>
#endif
static unsigned char* allocRaw(uint32 objectSize, unsigned char* store,
		uint32 *top, uint32 size, char clear) {
#ifdef USEMALLOCFREE
	return calloc(objectSize, 1);
#else
	unsigned char* obj = 0;
	int count;

	while (((uint32) (pointer) (store + *top)) & 0x3) {
		(*top)++;
	};

	count = *top;
	*top += objectSize;

	if (*top < size) {
		obj = HEAP_REF(store, unsigned char*) + count;
		if (clear) {
			unsigned char* src;
			src = (unsigned char*) obj;
			count = *top - count;
			while (count > 0) {
				*src++ = 0;
				count--;
			}
		}
	}
	if (obj == 0) {
		return obj;
	} else {
		return HEAP_UNREF(obj, unsigned char*);
	}
#endif
}

Object* allocData(uint32 objectSize, unsigned char* store, uint32 *top,
		uint32 size, char clear) {
	return (Object*) allocRaw(objectSize, store, top, size, clear);
}

#ifdef FLASHSUPPORT
static AllocationPoint defaultROMAllocationPoint;

AllocationPoint* getDefaultROMAllocationPoint() {
	defaultROMAllocationPoint.store = (unsigned char*) &pheap[0];
	defaultROMAllocationPoint.top = 0;
	defaultROMAllocationPoint.size = PHEAP_SIZE;

	return &defaultROMAllocationPoint;
}

uint8 readStore(uint16 offset, unsigned char* store) {
	return store[offset];
}

uint8 isInStore(uint8* ptr) {
	int offset = ptr - defaultROMAllocationPoint.store;

	if (offset > 0) {
		if (offset < defaultROMAllocationPoint.top) {
			return 1;
		}
	}
	return 0;
}

uint32 getOffsetOf(Object* dobject, unsigned char* store) {
	return (uint32) ((pointer) dobject - (pointer) store);
}

uint32 getHeapPointer(uint32 offset, unsigned char* store) {
	return (uint32) (pointer) (store + offset);
}
#endif

