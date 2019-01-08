#include "natives.h"

extern unsigned char readByte();
extern void printStr(const char* str);
fptr readNativeFunc(void) {
	unsigned char b = readByte();
	switch (b) {
	case N_DEVICES_DEFAULTWRITER_NWRITE_NUM:
		return n_devices_DefaultWriter_nwrite;
	case N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_ATSAME70WRITER_PUTC_NUM:
		return n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_ATSAMe70Writer_putc;
	case N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_FRONT_LIGHT_TURN_OFF_NUM:
		return n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_front_light_turn_off;
	case N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_FRONT_LIGHT_TURN_ON_NUM:
		return n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_front_light_turn_on;
	case N_DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_INITNATIVE_NUM:
		return n_devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_initNative;
	case N_JAVA_LANG_CLASS_CLINIT__NUM:
		return n_java_lang_Class_clinit_;
	case N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS_NUM:
		return n_java_lang_Class_desiredAssertionStatus;
	case N_JAVA_LANG_CLASS_GETPRIMITIVECLASS_NUM:
		return n_java_lang_Class_getPrimitiveClass;
	case N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS_NUM:
		return n_java_lang_Double_doubleToRawLongBits;
	case N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE_NUM:
		return n_java_lang_Double_longBitsToDouble;
	case N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS_NUM:
		return n_java_lang_Float_floatToRawIntBits;
	case N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM:
		return n_java_lang_System_arraycopy;
	case N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM:
		return n_java_lang_System_registerNatives;
	case N_JAVA_LANG_THROWABLE_CLINIT__NUM:
		return n_java_lang_Throwable_clinit_;
	case N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM:
		return n_java_lang_Throwable_fillInStackTrace_;
	case DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_DELAY_NUM:
		return devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_delay;
	case DEVICES_ARM_ATSAME70_ATSAME70MINIMALTARGETCONFIGURATION_INIT_NUM:
		return devices_arm_ATSAMe70_ATSAMe70MinimalTargetConfiguration_init;
	}
	return unknownNativeFunc;
}