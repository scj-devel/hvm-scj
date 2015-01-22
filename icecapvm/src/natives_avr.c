/*
 * natives_avr.c
 *
 *  Created on: Nov 17, 2010
 *      Author: user
 */

#include <avr/interrupt.h>
#include <util/delay.h>
#include <avr/io.h>

#include "types.h"
#include "ostypes.h"
#include "methods.h"
#include "classes.h"

#if defined(ENABLE_DEBUG)
#define STDOUTCHANNEL 3
#endif

static unsigned char java_stack[256];

int32* get_java_stack_base(int16 size) {
    return (int32*) &java_stack[0];
}

volatile uint8 systemTick;

void start_system_tick(void)
{
  ;
}

void stop_system_tick(void)
{
  ;
}

#if defined(N_VM_REALTIMECLOCK_AWAITNEXTTICK)
int16 n_vm_RealtimeClock_awaitNextTick(int32 *sp) {
    return -1;
}
#endif

#if defined(N_VM_REALTIMECLOCK_GETNATIVERESOLUTION)
int16 n_vm_RealtimeClock_getNativeResolution(int32 *sp) {

    *sp = MS_10; /* 10 ms */ 

    return -1;
}
#endif

#if defined(N_VM_REALTIMECLOCK_GETNATIVETIME)
extern int16 javax_realtime_HighResolutionTime_setNormalized(int32 *fp);
int16 n_vm_RealtimeClock_getNativeTime(int32 *sp) {
  sp[1] = 0;
  sp[2] = 0;
  sp[3] = 0;

  javax_realtime_HighResolutionTime_setNormalized(sp);
  
  *sp = 0;
  
  return -1;
}
#endif


void init_compiler_specifics(void) {
}

void mark_error(void) {
    ;
}

void mark_success(void) {
    ;
}

void init_memory_lock(void) {
}

void lock_memory(void) {
}

void unlock_memory(void) {
}

static void sendbyteraw(unsigned char byte) {
    while (!(UCSR0A & (1 << UDRE0)))
        ;
    UDR0 = byte;
}

void sendbyte(unsigned char byte) {
#if defined(ENABLE_DEBUG)
    sendbyteraw(STDOUTCHANNEL);
#endif
    sendbyteraw(byte);
}

#define BAUD 9600
#define MYUBRR ( ( F_CPU / ( BAUD * 8UL ) ) - 1 )

void initNatives(void) {
    UBRR0H = ( MYUBRR >> 8);
    UBRR0L = MYUBRR;
    UCSR0A |= (1 << U2X0);
    UCSR0B |= (1 << RXEN0) | (1 << TXEN0) | (1 << RXCIE0);
    UCSR0C |= (1 << UCSZ00) | (1 << UCSZ01);
}

void writeLongToIO(pointer address, unsigned short offset, int32 msb, int32 lsb) {
}

void writeIntToIO(pointer address, unsigned short offset, int32 lsb) {
    ;
}

void writeShortToIO(pointer address, unsigned short offset, unsigned short lsb) {
    ;
}

void writeByteToIO(pointer address, unsigned short offset, unsigned char lsb) {
    unsigned char *ptr;
    address += offset >> 3;
    ptr = (unsigned char*) (pointer) (address);
    *ptr = lsb;
}

void writeBitToIO(pointer address, unsigned short offset, unsigned char bit) {

}

void readLongFromIO(pointer address, unsigned short offset, int32* msb, int32* lsb) {
    *msb = 0;
    *lsb = 0;
}

int32 readIntFromIO(pointer address, unsigned short offset) {
    return 0;
}

unsigned short readShortFromIO(pointer address, unsigned short offset) {
    return 0;
}

unsigned char readByteFromIO(pointer address, unsigned short offset) {
    unsigned char *ptr;
    address += offset >> 3;
    ptr = (unsigned char*) (pointer) (address);
    return *ptr;
}

unsigned char readBitFromIO(pointer address, unsigned short offset) {
    return 0;
}

int16 n_arduino_Arduino_delay_ms(int32 *sp) {
    _delay_ms(400 /*sp[0]*/);
    return -1;
}

#if defined(ENABLE_DEBUG)

extern int requestResponseChannel;

extern unsigned char awaitCommandFromDebugger(int32* fp, unsigned short methodNumber, unsigned short pc);
extern void disconnectFromDebugger(void);
extern void sendOnChannel(int channelID, unsigned char event);

void stopProgram(int exitValue)
{
    disconnectFromDebugger();
    while (1)
    {
        _delay_ms(400);
    }
}

void closeChannel(int channelID)
{
    sendOnChannel(channelID, -1);
}

void closeStdout(void)
{
    sendbyte(-1);
}

int32 connectToChannel(int32 channelID)
{
    return channelID;
}

void readFromDebugger(unsigned char *buf, unsigned short length)
{
    while (length > 0)
    {
        while ( !(UCSR0A & (1<<RXC0)) )
        {
            ;
        }
        *buf++ = UDR0;
        length--;
    }
}

void writeToDebugger(int channelID, const unsigned char *buf, unsigned short length)
{
    while (length > 0)
    {
        sendbyteraw(channelID);
        sendbyteraw(*buf++);
        length--;
    }
}

void checkForRequests(int32* fp)
{
    if (UCSR0A & (1<<RXC0))
    {
        awaitCommandFromDebugger(fp, 0, 0);
    }
}
#endif
