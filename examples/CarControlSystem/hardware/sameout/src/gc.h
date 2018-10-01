/*
 * gc.h
 *
 *  Created on: 21/09/2010
 *      Author: sek
 */

#ifndef GC_H_
#define GC_H_

#include "types.h"
#include "allocation_point.h"

void initGC(void);
Object* gc_allocateObject(uint32 dobjectSize, uint32 pobjectsize);
Object* gc_allocateException(int objectSize);

#endif /* GC_H_ */
