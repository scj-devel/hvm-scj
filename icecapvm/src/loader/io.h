#ifndef IO_H
#define IO_H

#include "../ostypes.h"

#define OUTPUTFILE "app.ice"

void openOutput();
void openInput();
void closeIO(void); 
void dumpInt(uint32 i);
void dumpShort(unsigned short s);
void dumpByte(unsigned char b);
int getByteCount(void);
char* getOutputFile(void);
unsigned short readShort();
unsigned char readByte();
uint32 readInt();
char* readName();
void* _malloc_(int size);

#endif
