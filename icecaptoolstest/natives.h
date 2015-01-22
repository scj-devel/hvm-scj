#ifndef NATIVES_H_
#define NATIVES_H_
#include "ostypes.h"

typedef int16 (*fptr)(int32 *sp);

fptr readNativeFunc(void);

void dumpNativeFunc(int16(*nativeFunc)(int32 *sp), const char* functionName);
#define N_DEVICES_X86WRITER_NWRITE_NUM 42
int16 n_devices_X86Writer_nwrite(int32 *sp);

#define N_JAVA_LANG_CLASS_CLINIT__NUM 43
int16 n_java_lang_Class_clinit_(int32 *sp);

#define N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS_NUM 44
int16 n_java_lang_Class_desiredAssertionStatus(int32 *sp);

#define N_JAVA_LANG_CLASS_GETCOMPONENTTYPE_NUM 45
int16 n_java_lang_Class_getComponentType(int32 *sp);

#define N_JAVA_LANG_CLASS_GETNAME0_NUM 46
int16 n_java_lang_Class_getName0(int32 *sp);

#define N_JAVA_LANG_CLASS_GETPRIMITIVECLASS_NUM 47
int16 n_java_lang_Class_getPrimitiveClass(int32 *sp);

#define N_JAVA_LANG_CLASS_TOSTRING_NUM 48
int16 n_java_lang_Class_toString(int32 *sp);

#define N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS_NUM 49
int16 n_java_lang_Double_doubleToRawLongBits(int32 *sp);

#define N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE_NUM 50
int16 n_java_lang_Double_longBitsToDouble(int32 *sp);

#define N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS_NUM 51
int16 n_java_lang_Float_floatToRawIntBits(int32 *sp);

#define N_JAVA_LANG_OBJECT_GETCLASS_NUM 52
int16 n_java_lang_Object_getClass(int32 *sp);

#define N_JAVA_LANG_OBJECT_HASHCODE_NUM 53
int16 n_java_lang_Object_hashCode(int32 *sp);

#define N_JAVA_LANG_OBJECT_NOTIFY_NUM 54
int16 n_java_lang_Object_notify(int32 *sp);

#define N_JAVA_LANG_OBJECT_WAIT__NUM 55
int16 n_java_lang_Object_wait_(int32 *sp);

#define N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM 56
int16 n_java_lang_System_arraycopy(int32 *sp);

#define N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM 57
int16 n_java_lang_System_registerNatives(int32 *sp);

#define N_JAVA_LANG_THROWABLE_CLINIT__NUM 58
int16 n_java_lang_Throwable_clinit_(int32 *sp);

#define N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM 59
int16 n_java_lang_Throwable_fillInStackTrace_(int32 *sp);

#define N_JAVA_LANG_THROWABLE_PRINTSTACKTRACE_NUM 60
int16 n_java_lang_Throwable_printStackTrace(int32 *sp);

#define N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY_NUM 61
int16 n_java_lang_reflect_Array_newArray(int32 *sp);

#define N_VM_MONITOR_ATTACHMONITOR_NUM 62
int16 n_vm_Monitor_attachMonitor(int32 *sp);

#define N_VM_MONITOR_GETATTACHEDMONITOR_NUM 63
int16 n_vm_Monitor_getAttachedMonitor(int32 *sp);

#define N_VM_PROCESS_EXECUTEWITHSTACK_NUM 64
int16 n_vm_Process_executeWithStack(int32 *sp);

#define N_VM_PROCESS_TRANSFER_NUM 65
int16 n_vm_Process_transfer(int32 *sp);

#define N_VM_REALTIMECLOCK_AWAITNEXTTICK_NUM 66
int16 n_vm_RealtimeClock_awaitNextTick(int32 *sp);

#define N_VM_REALTIMECLOCK_GETNATIVERESOLUTION_NUM 67
int16 n_vm_RealtimeClock_getNativeResolution(int32 *sp);

#define N_VM_REALTIMECLOCK_GETNATIVETIME_NUM 68
int16 n_vm_RealtimeClock_getNativeTime(int32 *sp);

#endif /* NATIVES_H_ */
