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

volatile uint8 systemTick;

#if defined(N_VM_REALTIMECLOCK_DELAYNATIVEUNTIL)
int16 n_vm_RealtimeClock_delayNativeUntil(int32 *sp) {
	return -1;
}
#endif

void init_compiler_specifics(void) {
}

void init_memory_lock(void) {
}

void lock_memory(void) {
}

void unlock_memory(void) {
}

static void sendbyteraw(unsigned char byte) {
	while (!(UCSR2A & (1 << UDRE2))) {
		;
	}
	UDR2 = byte;
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
	UBRR2 = 64;
	UCSR2B = (1 << RXEN2) | (1 << TXEN2);
	UCSR2C = (3 << UCSZ20);
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
