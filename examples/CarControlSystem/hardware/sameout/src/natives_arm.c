/*
 * natives_avr.c
 *
 *  Created on: Nov 17, 2010
 *      Author: user
 */
#include "types.h"
#include "ostypes.h"
#include "methods.h"
#include "classes.h"

#if defined(ENABLE_DEBUG)
#define STDOUTCHANNEL 3
#endif

volatile uint8 systemTick;

void init_compiler_specifics(void); 
void init_memory_lock(void); 
void lock_memory(void); 
void unlock_memory(void);
static void sendbyteraw(unsigned char byte);
void sendbyte(unsigned char byte); 
void initNatives(void);
void writeLongToIO(pointer address, unsigned short offset, int32 msb, int32 lsb);
void writeIntToIO(pointer address, unsigned short offset, int32 lsb);
void writeShortToIO(pointer address, unsigned short offset, unsigned short lsb);
void writeByteToIO(pointer address, unsigned short offset, unsigned char lsb);
void writeBitToIO(pointer address, unsigned short offset, unsigned char bit);
void readLongFromIO(pointer address, unsigned short offset, int32* msb, int32* lsb);
int32 readIntFromIO(pointer address, unsigned short offset);
unsigned short readShortFromIO(pointer address, unsigned short offset);
unsigned char readByteFromIO(pointer address, unsigned short offset);
unsigned char readBitFromIO(pointer address, unsigned short offset);

#if defined(ENABLE_DEBUG)
void stopProgram(int exitValue);
void closeChannel(int channelID);
void closeStdout(void);
int32 connectToChannel(int32 channelID);
void readFromDebugger(unsigned char *buf, unsigned short length);
void writeToDebugger(int channelID, const unsigned char *buf, unsigned short length);
void checkForRequests(int32* fp);
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
   
}

void sendbyte(unsigned char byte) {
#if defined(ENABLE_DEBUG)
    sendbyteraw(STDOUTCHANNEL);
#endif
    sendbyteraw(byte);
}

void initNatives(void) {
    
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
   
}
#endif
