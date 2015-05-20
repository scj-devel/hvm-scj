#include "../ostypes.h"
#include "../types.h"
#include "io.h"

static unsigned short* readClassInterfaces(unsigned short length);

extern void printStr(const char* str);
extern void printShort(unsigned short c);

/* */

uint16 JAVA_LANG_STRING_var;
uint16 JAVA_LANG_OBJECT_var;
uint16 JAVA_LANG_INTEGER_var;
uint16 JAVA_LANG_BYTE_var;
uint16 JAVA_LANG_SHORT_var;
uint16 JAVA_LANG_BOOLEAN_var;
uint16 JAVA_LANG_LONG_var;
uint16 JAVA_LANG_CLASS_var;
uint16 JAVA_LANG_CLASSCASTEXCEPTION_var;
uint16 JAVA_LANG_OUTOFMEMORYERROR_var;
uint16 JAVA_LANG_THROWABLE_var;
uint16 JAVA_LANG_ARITHMETICEXCEPTION_var;
uint16 VM_HARDWAREOBJECT_var;
uint16 REFLECT_HEAPACCESSOR_var;
uint16 JAVA_LANG_NULLPOINTEREXCEPTION_var;
uint16 _C_var;
uint16 _I_var;
uint16 VM_MEMORY_var;

uint16 JAVA_LANG_STRING_VALUE_offset_var;
uint16 JAVA_LANG_STRING_OFFSET_offset_var;
uint16 JAVA_LANG_STRING_COUNT_offset_var;
uint16 JAVA_LANG_THROWABLE_BACKTRACE_offset_var;
uint16 JAVA_LANG_FLOAT_VALUE_offset_var;

ClassInfo *classes;
unsigned short** interfaces;
unsigned char* classData;
unsigned char *inheritanceMatrix;
uint8 tupac;

unsigned char initClasses(void) {
	unsigned short numberOfclasses;
	unsigned short count;
	uint16 classDataSize;
	uint16 inheritanceMatrixSize;

	numberOfclasses = readShort();
	printStr("deserializing ");
	printShort(numberOfclasses);
	printStr(" classes");

	classes = _malloc_(sizeof(ClassInfo) * numberOfclasses);

	if (!classes) {
		return 0;
	}

	for (count = 0; count < numberOfclasses; count++) {
		ClassInfo *current = &classes[count];
		printStr(".");
		current->superClass = readShort();
		current->dimension = readByte();
		current->hasLock = readByte();
		current->dobjectSize = readShort();
		current->pobjectSize = readShort();
		{
			unsigned short length;
			length = readShort();
			if (length) {
				current->interfaces = readClassInterfaces(length);
			}
		}
		current->name = readName();
	}
	printStr("done\n");

	classDataSize = readShort();
	printStr("Allocating class data [");
	printShort(classDataSize);
	printStr("]\n");

	classData = _malloc_(classDataSize);
	if (!classData) {
		return 0;
	}

	inheritanceMatrixSize = readShort();
	printStr("Allocating inheritanceMatrix [");
	printShort(inheritanceMatrixSize);
	printStr("]\n");

	inheritanceMatrix = _malloc_(inheritanceMatrixSize);

	for (count = 0; count < inheritanceMatrixSize; count++) {
		inheritanceMatrix[count] = readByte();
	}

	printStr("deserializing class store configuration variables\n");
	tupac = readByte();
	JAVA_LANG_STRING_var = readShort();
	JAVA_LANG_OBJECT_var = readShort();
	JAVA_LANG_INTEGER_var = readShort();
	JAVA_LANG_BYTE_var = readShort();
	JAVA_LANG_SHORT_var = readShort();
	JAVA_LANG_BOOLEAN_var = readShort();
	JAVA_LANG_LONG_var = readShort();
	JAVA_LANG_CLASS_var = readShort();
	JAVA_LANG_CLASSCASTEXCEPTION_var = readShort();
	JAVA_LANG_OUTOFMEMORYERROR_var = readShort();
	JAVA_LANG_THROWABLE_var = readShort();
	JAVA_LANG_ARITHMETICEXCEPTION_var = readShort();
	VM_HARDWAREOBJECT_var = readShort();
	JAVA_LANG_NULLPOINTEREXCEPTION_var = readShort();
	_C_var = readShort();
	_I_var = readShort();
	VM_MEMORY_var = readShort();
	printStr("done\n");
	closeIO();
	printStr("deserialized ");
	printShort(getByteCount());
	printStr(" bytes from ");
	printStr(getOutputFile());
	printStr("\n");
	return 1;
}

static unsigned short* readClassInterfaces(unsigned short length) {
	unsigned short* interfaces;
	unsigned short count = 0;
	interfaces = _malloc_(sizeof(unsigned short) * (length + 1));
	if (!interfaces) {
		return 0;
	}
	interfaces[count] = length;
	for (count = 1; count < length + 1; count++) {
		interfaces[count] = readShort();
	}
	return interfaces;
}
