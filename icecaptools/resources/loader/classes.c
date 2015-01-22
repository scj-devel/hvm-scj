#include "../ostypes.h"
#include "../types.h"
#include "io.h"

static unsigned short* readVtable(unsigned short size);
static unsigned short* readClassInterfaces(unsigned short length);
static unsigned short** readInterfaces(unsigned short numberOfinterfaces);

extern void printStr(const char* str);
extern void printShort(unsigned short c);
extern Object* gc_allocateObject(unsigned short dobjectSize, unsigned short pobjectsize);

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

unsigned char initClasses(void) {
    unsigned short numberOfclasses;
    unsigned short numberOfinterfaces;
    unsigned short count;

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
        current->classSize = readShort();
        if (current->classSize) {
            unsigned char* ptr;
            unsigned short count;
            count = current->classSize;
            ptr = _malloc_(count);
            if (!ptr) {
                return 0;
            }
            current->cfielddata = ptr;
            while (count) {
                *ptr++ = 0;
                count--;
            }
        } else {
            current->cfielddata = 0;
        }
        current->vtableSize = readShort();
        if (current->vtableSize) {
            current->vtable = readVtable(current->vtableSize);
            if (!current->vtable) {
                return 0;
            }
        }
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
    numberOfinterfaces = readShort();
    printStr("deserializing ");
    printShort(numberOfinterfaces);
    printStr(" numberOfinterfaces");
    if (numberOfinterfaces) {
        interfaces = readInterfaces(numberOfinterfaces);
        if (!interfaces) {
            return 0;
        }
    }
    printStr("done\n");
    printStr("deserializing class store configuration variables\n");
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
    JAVA_LANG_STRING_VALUE_offset_var = readShort();
    JAVA_LANG_STRING_OFFSET_offset_var = readShort();
    JAVA_LANG_STRING_COUNT_offset_var = readShort();
    JAVA_LANG_THROWABLE_BACKTRACE_offset_var = readShort();
    JAVA_LANG_FLOAT_VALUE_offset_var = readShort();
    printStr("done\n");
    closeIO();
    printStr("deserialized ");
    printShort(getByteCount());
    printStr(" bytes from ");
    printStr(getOutputFile());
    printStr("\n");
    return 1;
}

static unsigned short** readInterfaces(unsigned short numberOfinterfaces) {
    int count;
    unsigned short** interfaces = _malloc_(sizeof(unsigned short*) * numberOfinterfaces);
    if (!interfaces) {
        return 0;
    }
    for (count = 0; count < numberOfinterfaces; count++) {
        unsigned short currentLength = readShort();
        printStr(".");
        if (currentLength) {
            unsigned short* current;
            current = _malloc_(sizeof(unsigned short) * (currentLength + 1));
            if (!current) {
                return 0;
            }
            interfaces[count] = current;
            *current++ = currentLength;
            while (currentLength) {
                *current++ = readShort();
                currentLength--;
            }
        } else {
            interfaces[count] = 0;
        }
    }
    return interfaces;
}

static unsigned short* readVtable(unsigned short size) {
    unsigned short* vtable = _malloc_(sizeof(unsigned short) * size);
    unsigned short count;
    if (!vtable) {
        return 0;
    }
    for (count = 0; count < size; count++) {
        vtable[count] = readShort();
    }
    return vtable;
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
