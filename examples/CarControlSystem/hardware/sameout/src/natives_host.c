#include "natives.h"

#include <stdio.h>

#include <stdlib.h>

extern void dumpByte(unsigned char b);
void dumpNativeFunc(int16 (*nativeFunc)(int32 *sp), const char* functionName) {
	if (nativeFunc == n_devices_DefaultWriter_nwrite) {
		dumpByte (N_DEVICES_DEFAULTWRITER_NWRITE_NUM);
	} else if (nativeFunc
			== n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_ATSAMe70Writer_putc) {
		dumpByte (N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_ATSAME70WRITER_PUTC_NUM);
	} else if (nativeFunc
			== n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_front_light_turn_off) {
		dumpByte (N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_FRONT_LIGHT_TURN_OFF_NUM);
	} else if (nativeFunc
			== n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_front_light_turn_on) {
		dumpByte (N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_FRONT_LIGHT_TURN_ON_NUM);
	} else if (nativeFunc
			== n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_initNative) {
		dumpByte (N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_INITNATIVE_NUM);
	} else if (nativeFunc == n_java_lang_Class_clinit_) {
		dumpByte (N_JAVA_LANG_CLASS_CLINIT__NUM);
	} else if (nativeFunc == n_java_lang_Class_desiredAssertionStatus) {
		dumpByte (N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS_NUM);
	} else if (nativeFunc == n_java_lang_Class_getPrimitiveClass) {
		dumpByte (N_JAVA_LANG_CLASS_GETPRIMITIVECLASS_NUM);
	} else if (nativeFunc == n_java_lang_Double_doubleToRawLongBits) {
		dumpByte (N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS_NUM);
	} else if (nativeFunc == n_java_lang_Double_longBitsToDouble) {
		dumpByte (N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE_NUM);
	} else if (nativeFunc == n_java_lang_Float_floatToRawIntBits) {
		dumpByte (N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS_NUM);
	} else if (nativeFunc == n_java_lang_System_arraycopy) {
		dumpByte (N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM);
	} else if (nativeFunc == n_java_lang_System_registerNatives) {
		dumpByte (N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM);
	} else if (nativeFunc == n_java_lang_Throwable_clinit_) {
		dumpByte (N_JAVA_LANG_THROWABLE_CLINIT__NUM);
	} else if (nativeFunc == n_java_lang_Throwable_fillInStackTrace_) {
		dumpByte (N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM);
	} else if (nativeFunc
			== devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_delay) {
		dumpByte (DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_DELAY_NUM);
	} else if (nativeFunc
			== devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_init) {
		dumpByte (DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_INIT_NUM);
	} else {
		dumpByte (UNKNOWNNATIVEFUNC);
	}
}

