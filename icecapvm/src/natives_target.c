#include "natives.h"

extern unsigned char readByte();
extern void printStr(const char* str);
fptr readNativeFunc(void) {
    unsigned char b = readByte();
    switch (b) {
    case N_DEVICES_X86WRITER_NWRITE_NUM:
        return n_devices_X86Writer_nwrite;
    case N_JAVA_LANG_CLASS_CLINIT__NUM:
        return n_java_lang_Class_clinit_;
    case N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS_NUM:
        return n_java_lang_Class_desiredAssertionStatus;
    case N_JAVA_LANG_CLASS_GETCOMPONENTTYPE_NUM:
        return n_java_lang_Class_getComponentType;
    case N_JAVA_LANG_CLASS_GETGENERICINTERFACES_NUM:
        return n_java_lang_Class_getGenericInterfaces;
    case N_JAVA_LANG_CLASS_GETNAME0_NUM:
        return n_java_lang_Class_getName0;
    case N_JAVA_LANG_CLASS_GETPRIMITIVECLASS_NUM:
        return n_java_lang_Class_getPrimitiveClass;
    case N_JAVA_LANG_CLASS_TOSTRING_NUM:
        return n_java_lang_Class_toString;
    case N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS_NUM:
        return n_java_lang_Double_doubleToRawLongBits;
    case N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE_NUM:
        return n_java_lang_Double_longBitsToDouble;
    case N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS_NUM:
        return n_java_lang_Float_floatToRawIntBits;
    case N_JAVA_LANG_OBJECT_GETCLASS_NUM:
        return n_java_lang_Object_getClass;
    case N_JAVA_LANG_OBJECT_HASHCODE_NUM:
        return n_java_lang_Object_hashCode;
    case N_JAVA_LANG_STRINGBUILDER_APPEND_NUM:
        return n_java_lang_StringBuilder_append;
    case N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM:
        return n_java_lang_System_arraycopy;
    case N_JAVA_LANG_SYSTEM_IDENTITYHASHCODE_NUM:
        return n_java_lang_System_identityHashCode;
    case N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM:
        return n_java_lang_System_registerNatives;
    case N_JAVA_LANG_THREAD_CLINIT__NUM:
        return n_java_lang_Thread_clinit_;
    case N_JAVA_LANG_THREAD_CURRENTTHREAD_NUM:
        return n_java_lang_Thread_currentThread;
    case N_JAVA_LANG_THREAD_TOSTRING_NUM:
        return n_java_lang_Thread_toString;
    case N_JAVA_LANG_THROWABLE_CLINIT__NUM:
        return n_java_lang_Throwable_clinit_;
    case N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM:
        return n_java_lang_Throwable_fillInStackTrace_;
    case N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY_NUM:
        return n_java_lang_reflect_Array_newArray;
    }
    printStr("Unsupported native function");
    return 0;
}
