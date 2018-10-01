/* */
#include "ostypes.h"

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
extern uint16 JAVA_LANG_STRING_VALUE_offset_var;
extern uint16 JAVA_LANG_STRING_OFFSET_offset_var;
extern uint16 JAVA_LANG_STRING_COUNT_offset_var;
extern uint16 JAVA_LANG_THROWABLE_BACKTRACE_offset_var;
extern uint16 JAVA_LANG_FLOAT_VALUE_offset_var;
extern uint16 REFLECT_HEAPACCESSOR_var;

typedef struct PACKED _vm_Address32Bit_c {
    Object header;
    uint32 address_f;
} vm_Address32Bit_c;

typedef struct PACKED _vm_HardwareObject_c {
    Object header;
    uint32 address_f;
} vm_HardwareObject_c;

typedef struct PACKED _java_lang_String_c {
    Object header;
    uint32 value_f;
    uint32 hash_f;
} java_lang_String_c;
