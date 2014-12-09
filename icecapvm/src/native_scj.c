
#include <stdio.h>
#ifdef USEGETTIMEOFDAY
#include <sys/time.h>
#else
#include <time.h>
#endif

#include "ostypes.h"
#include "types.h"
#include "allocation_point.h"
#include "gc.h"
#include "classes.h"
#include "methods.h"

#if defined(N_VM_REALTIMECLOCK_GETNATIVETIME) || defined(N_VM_REALTIMECLOCK_DELAYNATIVEUNTIL)
static unsigned long time_offset = -1;
#endif

/* ==== Clock and Time ==================================================*/

/* getNativeResolution
 * See:
 * http://pubs.opengroup.org/onlinepubs/009695399/functions/clock_getres.html
 * http://pficheux.free.fr/eyrolles/linux_embarque/docs_externes/POSIX4.html
 *
 * getNativeResolution
 * param : javax.safetycritical.RelativeTime
 * return: int  A return value of 0 shall indicate that the call succeeded.
 *              A return value of -1 shall indicate that an error occurred.
 */

#if defined(N_VM_REALTIMECLOCK_GETNATIVERESOLUTION)
int16 n_vm_RealtimeClock_getNativeResolution(int32 *sp) {
    /*struct timespec clock_resolution;

    clock_getres(CLOCK_REALTIME, &clock_resolution);
    */

    *sp = MS_10; /* 10 ms */ /* clock_resolution.tv_nsec + (clock_resolution.tv_sec * 10 ^ 9);*/

    return -1;
}
#endif

/* getNativeTime
 * param : javax.safetycritical.AbsoluteTime
 * return: int
 */
#if defined(N_VM_REALTIMECLOCK_GETNATIVETIME)
extern int16 javax_realtime_HighResolutionTime_setNormalized(int32 *fp);
int16 n_vm_RealtimeClock_getNativeTime(int32 *sp) {
#ifdef USEGETTIMEOFDAY
	struct timeval timevalue;
#else
    struct timespec timevalue;
#endif
    int32 stat;
    long millis;

#ifdef USEGETTIMEOFDAY
    stat = gettimeofday(&timevalue, 0);
#else
    stat = clock_gettime(CLOCK_REALTIME, &timevalue);
#endif

    if (sizeof(long) != 8) {
        if (time_offset == (unsigned long) -1) {
            time_offset = timevalue.tv_sec;
        }
        timevalue.tv_sec = timevalue.tv_sec - time_offset;
    }

    millis = timevalue.tv_sec * 1000;
    sp[1] = ((millis >> 16) >> 16);
    sp[2] = millis & 0xFFFFFFFF;
#ifdef USEGETTIMEOFDAY
    sp[3] = timevalue.tv_usec * 1000;
#else
    sp[3] = timevalue.tv_nsec;
#endif

    javax_realtime_HighResolutionTime_setNormalized(sp);

    *sp = stat;

    return -1;
}
#endif

/* delayNativeUntil
 * param : javax.safetycritical.AbsoluteTime
 * return: void
 */
/*
 * AbsoluteTime has long millis and int nanos
 * timevalue.tv_sec and timevalue.tv_nsec are of type long
 *
 * Result should be:
 *   timevalue.tv_sec  = millis / 1000
 *
 *   timevalue.tv_nsec = (millis % 1000)  * 1000000 + nanos
 *
 */
#if defined(N_VM_REALTIMECLOCK_DELAYNATIVEUNTIL)
extern int32 ldiv32(int32* sp, uint32 xmsb, uint32 xlsb, uint32 ymsb, uint32 ylsb);
extern unsigned char lrem32(int32* sp, uint32 xmsb, uint32 xlsb, uint32 ymsb, uint32 ylsb);
extern void ladd(uint32* msb1, uint32* lsb1, uint32 msb2, uint32 lsb2);

int16 n_vm_RealtimeClock_delayNativeUntil(int32 *sp) {
    Object* absoluteTimeObj = (Object*) (pointer) (*sp);
    int32 nanos;
    long millis, temp;
    struct timespec timevalue;

    absoluteTimeObj = HEAP_REF(absoluteTimeObj, Object*);

    millis = ((javax_realtime_AbsoluteTime_c *) absoluteTimeObj)->lsbmillis_f; /* msb */
    millis = (millis << 16) << 16;
    millis |= ((javax_realtime_AbsoluteTime_c *) absoluteTimeObj)->millis_f; /* lsb */

    nanos = ((javax_realtime_AbsoluteTime_c *) absoluteTimeObj)->nanos_f;

    timevalue.tv_sec = millis / 1000;

    if (sizeof(long) != 8)
    {
        timevalue.tv_sec += time_offset;
    }

    temp = millis % 1000;

    timevalue.tv_nsec = nanos + (temp * 1000000);

    clock_nanosleep(CLOCK_REALTIME, TIMER_ABSTIME, &timevalue, NULL);

    return -1;
}
#endif

/* ==== Sleeping =========================================================*/

