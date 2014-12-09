#include "../ostypes.h"
#include "../types.h"
#include "io.h"
#include "../natives.h"

/* */
extern void printStr(const char* str);
extern void printShort(unsigned short c);

extern uint16 NUMBEROFCLASSINITIALIZERS_var;
extern uint16 NUMBEROFCONSTANTS_var;
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

MethodInfo *methods;
unsigned short *classInitializerSequence;
ConstantInfo *constants;
uint16 mainMethodIndex;
Object** stringConstants;

/* gcc -Wall -pedantic -g -O0 -DPC64 -DSUPPORT_LOADING -DPRINTFSUPPORT -DJAVA_HEAP_SIZE=1310720 loader/classesLoader.c  icecapvm.c  methodinterpreter.c  loader/methodsLoader.c loader/io_allOS.c loader/io_i86.c gc.c natives_allOS.c natives_i86.c rom_heap.c natives_target.c allocation_point.c rom_access.c native_scj.c print.c methods.c -lpthread x86_64_interrupt.s -lrt */

static ExceptionHandler* readExceptionHandlers(unsigned char numExceptionHandlers);
static unsigned char* readCode(unsigned short codeSize);

unsigned char loadApp(void) {
    unsigned short numberOfMethods;
    int count;
    ConstantInfo *currentConstant;

    printStr("Reading from ");
    printStr(getOutputFile());
    printStr("\n");

    openInput();

    numberOfMethods = readShort();

    printStr("deserializing ");
    printShort(numberOfMethods);
    printStr(" methods\n");
    methods = _malloc_(sizeof(MethodInfo) * numberOfMethods);

    if (!methods) {
        return 0;
    }

    for (count = 0; count < numberOfMethods; count++) {
        MethodInfo* current = &methods[count];
        current->classIndex = count << 1;
        current->maxStack = readShort();
        current->maxLocals = readShort();
        current->numArgs = readByte();
        current->minfo = readByte();
        current->numExceptionHandlers = readByte();
        if (current->numExceptionHandlers) {
            *(ExceptionHandler**) (&current->handlers) = readExceptionHandlers(current->numExceptionHandlers);
            if (!current->handlers) {
                return 0;
            }
        }
        current->codeSize = readShort();

        if (current->codeSize) {
            *(unsigned char**) (&current->code) = readCode(current->codeSize);
            if (!current->code) {
                return 0;
            }
        }
        else
        {
        	*(unsigned char**) (&current->code) = 0;
        }

        current->name = readName();
        printStr("method [");
        printShort(count);
        printStr("] = ");
        printStr(current->name);
        printStr("\n");
        if (current->codeSize == 0) {
            current->nativeFunc = readNativeFunc();
        }
    }
    printStr("done\n");
    NUMBEROFCONSTANTS_var = readShort();
    printStr("deserializing ");
    printShort(NUMBEROFCONSTANTS_var);
    printStr(" constants ");
    constants = _malloc_(sizeof(ConstantInfo) * NUMBEROFCONSTANTS_var);

    if (!constants) {
        return 0;
    }

    for (count = 0; count < NUMBEROFCONSTANTS_var; count++) {
        currentConstant = &constants[count];
        printStr(".");
        currentConstant->type = readByte();
        if (currentConstant->type == CONSTANT_STRING) {
            unsigned short length;
            char* str;
            currentConstant->value = readInt();
            length = currentConstant->value & 0xffff;
            str = _malloc_(length);

            if (!str) {
                return 0;
            }

            while (length) {
                *str++ = readByte();
                length--;
            }
            str -= currentConstant->value & 0xffff;
            currentConstant->data = str;
            printStr("string [");
            printShort(currentConstant->value & 0xffff);
            printStr("][");
            printStr(str);
            printStr("]\n");
        } else if (currentConstant->type == CONSTANT_INTEGER) {
        	currentConstant->value = readInt();
        } else if (currentConstant->type == CONSTANT_FLOAT) {
        	currentConstant->fvalue = (float) readInt();
        } else if (currentConstant->type == CONSTANT_CLASS) {
        	currentConstant->value = readInt();
        } else if ((currentConstant->type == CONSTANT_LONG) || (currentConstant->type == CONSTANT_DOUBLE)) {
            unsigned char *data = _malloc_(8);
            unsigned char i;

            if (!data) {
                return 0;
            }

            for (i = 0; i < 8; i++) {
                *data++ = readByte();
            }
            currentConstant->data = data - 8;
        }
    }
    printStr("done\n");
    NUMBEROFCLASSINITIALIZERS_var = readShort();
    printStr("deserializing ");
    printShort(NUMBEROFCLASSINITIALIZERS_var);
    printStr(" class initializers");

    classInitializerSequence = _malloc_(sizeof(unsigned short) * NUMBEROFCLASSINITIALIZERS_var);

    if (!classInitializerSequence) {
        return 0;
    }

    for (count = 0; count < NUMBEROFCLASSINITIALIZERS_var; count++) {
        printStr(".");
        classInitializerSequence[count] = readShort();
    }
    printStr("done\n");

    printStr("deserializing method store configuration variables\n");
    mainMethodIndex = readShort();
    JAVA_LANG_STRING_INITFROMCHARARRAY_var = readShort();
    JAVA_LANG_CLASSCASTEXCEPTION_INIT__var = readShort();
    JAVA_LANG_ARITHMETICEXCEPTION_INIT__var = readShort();
    JAVA_LANG_OUTOFMEMORYERROR_INIT__var = readShort();
    JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var = readShort();
    JAVA_LANG_OBJECT_CLONE_var = readShort();
    VM_INTERRUPTDISPATCHER_INTERRUPT_var = readShort();
    TASKS_MODBUSDISPATCHER_DISPATCH_var = readShort();
    JAVA_LANG_STRINGBUFFER_APPEND_var = readShort();
    JAVA_LANG_STRINGBUILDER_APPEND_var = readShort();
    JAVA_LANG_STRING_INIT__var = readShort();
    JAVA_LANG_FLOAT_TOSTRING_var = readShort();
    printStr("done\n");
    return 1;
}

static ExceptionHandler* readExceptionHandlers(unsigned char numExceptionHandlers) {
    int count;
    ExceptionHandler* handlers = _malloc_(sizeof(ExceptionHandler) * numExceptionHandlers);
    ExceptionHandler* current = handlers;

    if (!handlers) {
        return 0;
    }

    for (count = 0; count < numExceptionHandlers; count++) {
        current->start_pc = readShort();
        current->end_pc = readShort();
        current->handle_pc = readShort();
        current->clazz = readShort();
        current++;
    }
    return handlers;
}

static unsigned char* readCode(unsigned short codeSize) {
    if (codeSize > 0) {
        int count;
        unsigned char* code = _malloc_(codeSize);
        if (!code) {
            return 0;
        }
        for (count = 0; count < codeSize; count++) {
            code[count] = readByte();
        }
        return code;
    } else {
        return 0;
    }
}

int16 unknownNativeFunc(int32 *sp)
{
	printStr("Unknown native function called\n");

	return -1;
}
