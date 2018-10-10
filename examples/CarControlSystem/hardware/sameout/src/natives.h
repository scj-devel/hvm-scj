#ifndef NATIVES_H_
#define NATIVES_H_
#include "ostypes.h"

typedef int16 (*fptr)(int32 *sp);

fptr readNativeFunc(void);

void dumpNativeFunc(int16 (*nativeFunc)(int32 *sp), const char* functionName);
int16 unknownNativeFunc(int32 *sp);
#define UNKNOWNNATIVEFUNC 42
#define N_DEVICES_DEFAULTWRITER_NWRITE_NUM 43
int16 n_devices_DefaultWriter_nwrite(int32 *sp);

#define N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_ATSAME70WRITER_PUTC_NUM 44
int16 n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_ATSAMe70Writer_putc(
		int32 *sp);

#define N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_FRONT_LIGHT_TURN_OFF_NUM 45
int16 n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_front_light_turn_off(
		int32 *sp);

#define N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_FRONT_LIGHT_TURN_ON_NUM 46
int16 n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_front_light_turn_on(
		int32 *sp);

#define N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_INITNATIVE_NUM 47
int16 n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_initNative(
		int32 *sp);

#define N_JAVA_LANG_CLASS_CLINIT__NUM 48
int16 n_java_lang_Class_clinit_(int32 *sp);

#define N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS_NUM 49
int16 n_java_lang_Class_desiredAssertionStatus(int32 *sp);

#define N_JAVA_LANG_CLASS_GETPRIMITIVECLASS_NUM 50
int16 n_java_lang_Class_getPrimitiveClass(int32 *sp);

#define N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS_NUM 51
int16 n_java_lang_Double_doubleToRawLongBits(int32 *sp);

#define N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE_NUM 52
int16 n_java_lang_Double_longBitsToDouble(int32 *sp);

#define N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS_NUM 53
int16 n_java_lang_Float_floatToRawIntBits(int32 *sp);

#define N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM 54
int16 n_java_lang_System_arraycopy(int32 *sp);

#define N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM 55
int16 n_java_lang_System_registerNatives(int32 *sp);

#define N_JAVA_LANG_THROWABLE_CLINIT__NUM 56
int16 n_java_lang_Throwable_clinit_(int32 *sp);

#define N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM 57
int16 n_java_lang_Throwable_fillInStackTrace_(int32 *sp);

#define DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_DELAY_NUM 58
int16 devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_delay(int32 *sp);

#define DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_INIT_NUM 59
int16 devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_init(int32 *sp);

#endif /* NATIVES_H_ */
