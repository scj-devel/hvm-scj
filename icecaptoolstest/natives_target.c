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
    case N_JAVA_LANG_OBJECT_NOTIFY_NUM:
        return n_java_lang_Object_notify;
    case N_JAVA_LANG_OBJECT_WAIT__NUM:
        return n_java_lang_Object_wait_;
    case N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM:
        return n_java_lang_System_arraycopy;
    case N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM:
        return n_java_lang_System_registerNatives;
    case N_JAVA_LANG_THROWABLE_CLINIT__NUM:
        return n_java_lang_Throwable_clinit_;
    case N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM:
        return n_java_lang_Throwable_fillInStackTrace_;
    case N_JAVA_LANG_THROWABLE_PRINTSTACKTRACE_NUM:
        return n_java_lang_Throwable_printStackTrace;
    case N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY_NUM:
        return n_java_lang_reflect_Array_newArray;
    case N_VM_MONITOR_ATTACHMONITOR_NUM:
        return n_vm_Monitor_attachMonitor;
    case N_VM_MONITOR_GETATTACHEDMONITOR_NUM:
        return n_vm_Monitor_getAttachedMonitor;
    case N_VM_PROCESS_EXECUTEWITHSTACK_NUM:
        return n_vm_Process_executeWithStack;
    case N_VM_PROCESS_TRANSFER_NUM:
        return n_vm_Process_transfer;
    case N_VM_REALTIMECLOCK_AWAITNEXTTICK_NUM:
        return n_vm_RealtimeClock_awaitNextTick;
    case N_VM_REALTIMECLOCK_GETNATIVERESOLUTION_NUM:
        return n_vm_RealtimeClock_getNativeResolution;
    case N_VM_REALTIMECLOCK_GETNATIVETIME_NUM:
        return n_vm_RealtimeClock_getNativeTime;
    }
    printStr("Unsupported native function");
    return 0;
}
