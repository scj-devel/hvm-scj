#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "io.h"
#include "../natives.h"

/* gcc -Wall -Werror -pedantic -DPC64 serializer.c ../methods.c ../classes.c ../natives_host.c io_i86.c io_allOS.c ../methodinterpreter.c ../natives_allOS.c ../print.c ../gc.c ../allocation_point.c ../natives_i86.c */

#include "../types.h"
#include "../ostypes.h"
#include "../methods.h"
#include "../classes.h"

extern unsigned char initClasses(void);
extern unsigned char initMethods(void);

extern const MethodInfo *methods;
extern const ClassInfo *classes;

extern uint16 JAVA_LANG_STRING_INITFROMCHARARRAY_var;
extern uint16 JAVA_LANG_CLASSCASTEXCEPTION_INIT__var;
extern uint16 JAVA_LANG_ARITHMETICEXCEPTION_INIT__var;
extern uint16 JAVA_LANG_OUTOFMEMORYERROR_INIT__var;
extern uint16 JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var;
extern uint16 JAVA_LANG_OBJECT_CLONE_var;
extern uint16 VM_INTERRUPTDISPATCHER_INTERRUPT_var;
extern uint16 TASKS_MODBUSDISPATCHER_DISPATCH_var;
extern uint16 JAVA_LANG_STRINGBUFFER_APPEND_var;
extern uint16 JAVA_LANG_STRINGBUILDER_APPEND_var;
extern uint16 JAVA_LANG_STRING_INIT__var;
extern uint16 JAVA_LANG_FLOAT_TOSTRING_var;

extern uint16 JAVA_LANG_STRING_var;
extern uint16 JAVA_LANG_OBJECT_var;
extern uint16 JAVA_LANG_INTEGER_var;
extern uint16 JAVA_LANG_BYTE_var;
extern uint16 JAVA_LANG_SHORT_var;
extern uint16 JAVA_LANG_BOOLEAN_var;
extern uint16 JAVA_LANG_LONG_var;
extern uint16 JAVA_LANG_CLASS_var;
extern uint16 JAVA_LANG_CLASSCASTEXCEPTION_var;
extern uint16 JAVA_LANG_OUTOFMEMORYERROR_var;
extern uint16 JAVA_LANG_THROWABLE_var;
extern uint16 JAVA_LANG_ARITHMETICEXCEPTION_var;
extern uint16 VM_HARDWAREOBJECT_var;
extern uint16 JAVA_LANG_NULLPOINTEREXCEPTION_var;
extern uint16 _C_var;
extern uint16 _I_var;
extern uint16 VM_MEMORY_var;

extern ConstantInfo *constants;
extern uint16 mainMethodIndex;
extern unsigned short *classInitializerSequence;
extern const unsigned char *inheritanceMatrix;
extern const uint8 tupac;

static void dumpExceptionHandlers(unsigned char numExceptionHandlers,
		const ExceptionHandler* handlers);
static void dumpClassInterfaces(const unsigned short* interfaces);
static void dumpCode(unsigned short codeSize, const unsigned char* code);
static void dumpName(const char* name);

int main(int argv, char** args) {
	int count;

	initClasses();
	initMethods();

	printf("serializing to %s\n", getOutputFile());
	printf("serializing %d methods ", NUMBEROFMETHODS);
	openOutput();
	dumpShort(NUMBEROFMETHODS);
	for (count = 0; count < NUMBEROFMETHODS; count++) {
		const MethodInfo* current = &methods[count];

		printf(".");
		dumpShort(current->maxStack);
		dumpShort(current->maxLocals);
		dumpByte(current->numArgs);
		dumpByte(current->minfo);
		dumpByte(current->numExceptionHandlers);
		dumpExceptionHandlers(current->numExceptionHandlers, current->handlers);
		dumpShort(current->codeSize);
		dumpCode(current->codeSize, current->code);
		dumpName(current->name);
		if (current->codeSize == 0) {
			dumpNativeFunc(current->nativeFunc, current->name);
		}
	}
	printf("done\n");
	printf("serializing %d constants ", NUMBEROFCONSTANTS);
	dumpShort(NUMBEROFCONSTANTS);
	for (count = 0; count < NUMBEROFCONSTANTS; count++) {
		ConstantInfo *current = &constants[count];
		printf(".");
		dumpByte(current->type);
		if (current->type == CONSTANT_STRING) {
			unsigned short length;
			char* str = (char*) current->data;
			dumpInt(current->value);
			length = current->value & 0xffff;
			printf("string [%d][%s]\n", length, str);
			while (length) {
				dumpByte(*str++);
				length--;
			}
		} else if (current->type == CONSTANT_INTEGER) {
			dumpInt(current->value);
		} else if (current->type == CONSTANT_FLOAT) {
			dumpInt((uint32) current->fvalue);
		} else if (current->type == CONSTANT_CLASS) {
			dumpInt(current->value);
		} else if ((current->type == CONSTANT_LONG)
				|| (current->type == CONSTANT_DOUBLE)) {
			const unsigned char *data = current->data;
			unsigned char i;
			for (i = 0; i < 8; i++) {
				dumpByte(*data++);
			}
		}
	}
	printf("done\n");
	printf("serializing %d class initializers ", NUMBEROFCLASSINITIALIZERS);
	dumpShort(NUMBEROFCLASSINITIALIZERS);
	for (count = 0; count < NUMBEROFCLASSINITIALIZERS; count++) {
		printf(".");
		dumpShort(classInitializerSequence[count]);
	}


	printf("done\n");
	printf("serializing method store configuration variables \n");
	dumpShort(mainMethodIndex);
	dumpShort(JAVA_LANG_STRING_INITFROMCHARARRAY_var);
	dumpShort(JAVA_LANG_CLASSCASTEXCEPTION_INIT__var);
	dumpShort(JAVA_LANG_ARITHMETICEXCEPTION_INIT__var);
	dumpShort(JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
	dumpShort(JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	dumpShort(JAVA_LANG_OBJECT_CLONE_var);
	dumpShort(VM_INTERRUPTDISPATCHER_INTERRUPT_var);
	dumpShort(TASKS_MODBUSDISPATCHER_DISPATCH_var);
	dumpShort(JAVA_LANG_STRINGBUFFER_APPEND_var);
	dumpShort(JAVA_LANG_STRINGBUILDER_APPEND_var);
	dumpShort(JAVA_LANG_STRING_INIT__var);
	dumpShort(JAVA_LANG_FLOAT_TOSTRING_var);
	printf("done\n");

	printf("serializing %d classes ", NUMBEROFCLASSES);
	dumpShort(NUMBEROFCLASSES);

	for (count = 0; count < NUMBEROFCLASSES; count++) {
		const ClassInfo *current = &classes[count];
		printf(".");
		dumpShort(current->superClass);
		dumpByte(current->dimension);
		dumpByte(current->hasLock);
		dumpShort(current->dobjectSize);
		dumpShort(current->pobjectSize);
		dumpClassInterfaces(current->interfaces);
		dumpName(current->name);
	}
	printf("done\n");

	dumpShort(CLASSDATASIZE);

	printf("serializing %d bytes of inheritance matrix ",
			INHERITANCEMATRIXSIZE);
	dumpShort(INHERITANCEMATRIXSIZE);
	for (count = 0; count < INHERITANCEMATRIXSIZE; count++) {
		if (count % 8 == 0) {
			printf(".");
		}
		dumpByte(inheritanceMatrix[count]);
	}
	printf("done\n");

	printf("serializing class store configuration variables\n");
	dumpByte(tupac);
	printf("JAVA_LANG_STRING_var = %d\n", JAVA_LANG_STRING_var);
	dumpShort(JAVA_LANG_STRING_var);
	dumpShort(JAVA_LANG_OBJECT_var);
	dumpShort(JAVA_LANG_INTEGER_var);
	dumpShort(JAVA_LANG_BYTE_var);
	dumpShort(JAVA_LANG_SHORT_var);
	dumpShort(JAVA_LANG_BOOLEAN_var);
	dumpShort(JAVA_LANG_LONG_var);
	dumpShort(JAVA_LANG_CLASS_var);
	dumpShort(JAVA_LANG_CLASSCASTEXCEPTION_var);
	dumpShort(JAVA_LANG_OUTOFMEMORYERROR_var);
	dumpShort(JAVA_LANG_THROWABLE_var);
	dumpShort(JAVA_LANG_ARITHMETICEXCEPTION_var);
	dumpShort(VM_HARDWAREOBJECT_var);
	dumpShort(JAVA_LANG_NULLPOINTEREXCEPTION_var);
	dumpShort(_C_var);
	dumpShort(_I_var);
	dumpShort(VM_MEMORY_var);
	printf("done\n");
	closeIO();
	printf("serialized %d bytes to %s\n", getByteCount(), getOutputFile());
	return 0;
}

static void dumpClassInterfaces(const unsigned short* interfaces) {
	if (interfaces) {
		unsigned short size = *interfaces++;
		dumpShort(size);
		while (size) {
			dumpShort(*interfaces++);
			size--;
		}
	} else {
		dumpShort(0);
	}
}

static void dumpExceptionHandlers(unsigned char numExceptionHandlers,
		const ExceptionHandler* handlers) {
	int count;
	for (count = 0; count < numExceptionHandlers; count++) {
		const ExceptionHandler* current = handlers++;
		dumpShort(current->start_pc);
		dumpShort(current->end_pc);
		dumpShort(current->handle_pc);
		dumpShort(current->clazz);
	}
}

static void dumpCode(unsigned short codeSize, const unsigned char* code) {
	int count;
	for (count = 0; count < codeSize; count++) {
		dumpByte(code[count]);
	}
}

static void dumpName(const char* name) {
	int count;

	unsigned short length = strlen(name);

	dumpShort(length);

	for (count = 0; count < length; count++) {
		dumpByte(name[count]);
	}
}

