#define NUMBEROFINTERFACES 23
#define NUMBEROFCLASSES 66
#define JAVA_LANG_STRING 45
#define JAVA_LANG_OBJECT 61
#define JAVA_LANG_INTEGER 5
#define JAVA_LANG_BYTE -1
#define JAVA_LANG_SHORT -1
#define JAVA_LANG_CHARACTER -1
#define JAVA_LANG_BOOLEAN -1
#define JAVA_LANG_LONG -1
#define JAVA_LANG_CLASS 62
#define JAVA_LANG_CLASSCASTEXCEPTION 37
#define JAVA_LANG_OUTOFMEMORYERROR 63
#define JAVA_LANG_THROWABLE 27
#define JAVA_LANG_ARITHMETICEXCEPTION 12
#define JAVA_LANG_NOSUCHMETHODEXCEPTION -1
#define JAVA_LANG_REFLECT_METHOD -1
#define JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION -1
#define JAVA_LANG_REFLECT_CONSTRUCTOR -1
#define VM_HARDWAREOBJECT -1
#define REFLECT_HEAPACCESSOR -1
#define VM_ADDRESS32BIT -1
#define VM_ADDRESS64BIT -1
#define JAVA_LANG_NULLPOINTEREXCEPTION 6
#define _C 33
#define _I 38
#define VM_MEMORY -1
#define JAVA_LANG_STRING_var JAVA_LANG_STRING
#define JAVA_LANG_OBJECT_var JAVA_LANG_OBJECT
#define JAVA_LANG_INTEGER_var JAVA_LANG_INTEGER
#define JAVA_LANG_BYTE_var JAVA_LANG_BYTE
#define JAVA_LANG_SHORT_var JAVA_LANG_SHORT
#define JAVA_LANG_CHARACTER_var JAVA_LANG_CHARACTER
#define JAVA_LANG_BOOLEAN_var JAVA_LANG_BOOLEAN
#define JAVA_LANG_LONG_var JAVA_LANG_LONG
#define JAVA_LANG_CLASS_var JAVA_LANG_CLASS
#define JAVA_LANG_CLASSCASTEXCEPTION_var JAVA_LANG_CLASSCASTEXCEPTION
#define JAVA_LANG_OUTOFMEMORYERROR_var JAVA_LANG_OUTOFMEMORYERROR
#define JAVA_LANG_THROWABLE_var JAVA_LANG_THROWABLE
#define JAVA_LANG_ARITHMETICEXCEPTION_var JAVA_LANG_ARITHMETICEXCEPTION
#define JAVA_LANG_NOSUCHMETHODEXCEPTION_var JAVA_LANG_NOSUCHMETHODEXCEPTION
#define JAVA_LANG_REFLECT_METHOD_var JAVA_LANG_REFLECT_METHOD
#define JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION_var JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION
#define JAVA_LANG_REFLECT_CONSTRUCTOR_var JAVA_LANG_REFLECT_CONSTRUCTOR
#define VM_HARDWAREOBJECT_var VM_HARDWAREOBJECT
#define REFLECT_HEAPACCESSOR_var REFLECT_HEAPACCESSOR
#define VM_ADDRESS32BIT_var VM_ADDRESS32BIT
#define VM_ADDRESS64BIT_var VM_ADDRESS64BIT
#define JAVA_LANG_NULLPOINTEREXCEPTION_var JAVA_LANG_NULLPOINTEREXCEPTION
#define _C_var _C
#define _I_var _I
#define VM_MEMORY_var VM_MEMORY
#define JAVA_LANG_STRING_VALUE_offset 0
#define CLASSDATASIZE 284
#define INHERITANCEMATRIXSIZE 2048
#define JAVA_SECURITY_PRIVILEGEDACTION -1
#define DEVICES_HANDLER -1
#define JAVA_LANG_RUNNABLE -1

typedef struct PACKED _carcontrol_io_BluetoothConnectionFactory_c {
    Object header;
    uint32 name_f;
} carcontrol_io_BluetoothConnectionFactory_c;

typedef struct PACKED _java_lang_Float_c {
    Object header;
    float value_f;
} java_lang_Float_c;

typedef struct PACKED _java_lang_IndexOutOfBoundsException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_IndexOutOfBoundsException_c;

typedef struct PACKED _java_lang_Integer_c {
    Object header;
    uint32 value_f;
} java_lang_Integer_c;

typedef struct PACKED _java_lang_NullPointerException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_NullPointerException_c;

typedef struct PACKED _test_same70_configuration_MachineFactorySAME_c {
    Object header;
    uint8 systemTickStarted_f;
} test_same70_configuration_MachineFactorySAME_c;

typedef struct PACKED _util_URL_c {
    Object header;
    uint32 raw_f;
    uint32 scheme_f;
    uint32 schemeSpecificPart_f;
    uint32 target_f;
    uint32 parameters_f;
} util_URL_c;

typedef struct PACKED _carcontrol_io_Port_c {
    Object header;
    uint32 message_f;
    uint32 commDevice_f;
} carcontrol_io_Port_c;

typedef struct PACKED _java_io_DataOutputStream_c {
    Object header;
    uint32 out_f;
    uint32 written_f;
    uint32 bytearr_f;
    uint32 writeBuffer_f;
} java_io_DataOutputStream_c;

typedef struct PACKED _java_lang_ArithmeticException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ArithmeticException_c;

typedef struct PACKED _java_io_ObjectStreamField_c {
    Object header;
    uint32 name_f;
    uint32 signature_f;
    uint32 type_f;
    uint8 unshared_f;
    uint32 field_f;
    uint32 offset_f;
} java_io_ObjectStreamField_c;

typedef struct PACKED _carcontrol_io_BluetoothConnection_c {
    Object header;
    uint32 bluetoothSerial_f;
} carcontrol_io_BluetoothConnection_c;

typedef struct PACKED _java_lang_StringIndexOutOfBoundsException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_StringIndexOutOfBoundsException_c;

typedef struct PACKED _java_io_FilterOutputStream_c {
    Object header;
    uint32 out_f;
} java_io_FilterOutputStream_c;

typedef struct PACKED _java_lang_Exception_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_Exception_c;

typedef struct PACKED _util_BaseTargetConfiguration_c {
    Object header;
    uint8 doICare_f;
} util_BaseTargetConfiguration_c;

typedef struct PACKED _vm_POSIX64BitMachineFactory_c {
    Object header;
    uint8 systemTickStarted_f;
} vm_POSIX64BitMachineFactory_c;

typedef struct PACKED _carcontrol_io_BluetoothCommunicationDeviceImpl_c {
    Object header;
    uint32 name_f;
    uint32 istream_f;
    uint32 ostream_f;
} carcontrol_io_BluetoothCommunicationDeviceImpl_c;

typedef struct PACKED _java_lang_StringBuilder_c {
    Object header;
    uint32 value_f;
    uint32 count_f;
} java_lang_StringBuilder_c;

typedef struct PACKED _java_lang_ArrayStoreException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ArrayStoreException_c;

typedef struct PACKED _java_lang_Throwable_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_Throwable_c;

typedef struct PACKED _java_io_DataInputStream_c {
    Object header;
    uint32 in_f;
    uint32 bytearr_f;
    uint32 chararr_f;
    uint32 readBuffer_f;
    uint32 lineBuffer_f;
} java_io_DataInputStream_c;

typedef struct PACKED _carcontrol_io_BluetoothConnection_1_c {
    Object header;
    uint32 this_0_f;
} carcontrol_io_BluetoothConnection_1_c;

typedef struct PACKED _java_lang_Double_c {
    Object header;
    uint32 value_f;
    uint32 lsbvalue_f;
} java_lang_Double_c;

typedef struct PACKED _carcontrol_io_BluetoothConnection_2_c {
    Object header;
    uint32 this_0_f;
} carcontrol_io_BluetoothConnection_2_c;

typedef struct PACKED _java_lang_Error_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_Error_c;

typedef struct PACKED _java_lang_AssertionError_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_AssertionError_c;

typedef struct PACKED _test_same70_examples_BluetoothAtSAME_c {
    Object header;
    uint8 doICare_f;
} test_same70_examples_BluetoothAtSAME_c;

typedef struct PACKED _java_lang_ClassCastException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ClassCastException_c;

typedef struct PACKED _java_lang_IllegalMonitorStateException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_IllegalMonitorStateException_c;

typedef struct PACKED _java_io_FilterInputStream_c {
    Object header;
    uint32 in_f;
} java_io_FilterInputStream_c;

typedef struct PACKED _java_lang_VirtualMachineError_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_VirtualMachineError_c;

typedef struct PACKED _java_lang_AbstractStringBuilder_c {
    Object header;
    uint32 value_f;
    uint32 count_f;
} java_lang_AbstractStringBuilder_c;

typedef struct PACKED _java_lang_String_c {
    Object header;
    uint32 value_f;
    uint32 hash_f;
} java_lang_String_c;

typedef struct PACKED _javax_safetycritical_io_ConnectionFactory_c {
    Object header;
    uint32 name_f;
} javax_safetycritical_io_ConnectionFactory_c;

typedef struct PACKED _vm_AbstractMachineFactory_c {
    Object header;
    uint8 systemTickStarted_f;
} vm_AbstractMachineFactory_c;

typedef struct PACKED _test_same70_configuration_BluetoothTargetConfigurationSAME_c {
    Object header;
    uint8 doICare_f;
} test_same70_configuration_BluetoothTargetConfigurationSAME_c;

typedef struct PACKED _java_lang_NegativeArraySizeException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_NegativeArraySizeException_c;

typedef struct PACKED _java_io_IOException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_io_IOException_c;

typedef struct PACKED _java_lang_RuntimeException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_RuntimeException_c;

typedef struct PACKED _java_lang_ArrayIndexOutOfBoundsException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ArrayIndexOutOfBoundsException_c;

typedef struct PACKED _java_lang_IllegalArgumentException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_IllegalArgumentException_c;

typedef struct PACKED _util_URLSyntaxException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} util_URLSyntaxException_c;

typedef struct PACKED _javax_microedition_io_ConnectionNotFoundException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} javax_microedition_io_ConnectionNotFoundException_c;

typedef struct PACKED _java_lang_Class_c {
    Object header;
    uint32 cachedConstructor_f;
    uint32 newInstanceCallerCache_f;
    uint32 name_f;
    uint32 classLoader_f;
    uint32 reflectionData_f;
    uint32 classRedefinedCount_f;
    uint32 genericInfo_f;
    uint32 enumConstants_f;
    uint32 enumConstantDirectory_f;
    uint32 annotationData_f;
    uint32 annotationType_f;
    uint32 classValueMap_f;
} java_lang_Class_c;

typedef struct PACKED _java_lang_OutOfMemoryError_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_OutOfMemoryError_c;

typedef struct PACKED _staticClassFields_c {
    uint32 TYPE_f;
    uint8 _assertionsDisabled_f;
    uint32 in_f;
    uint32 out_f;
    uint32 err_f;
    uint32 security_f;
    uint32 cons_f;
    uint32 TYPE_f__f;
    uint32 digits_f;
    uint32 DigitTens_f;
    uint32 DigitOnes_f;
    uint32 sizeTable_f;
    uint32 negativeZeroFloatBits_f;
    uint32 lsbnegativeZeroFloatBits_f;
    uint32 negativeZeroDoubleBits_f;
    uint32 lsbnegativeZeroDoubleBits_f;
    uint32 twoToTheDoubleScaleUp_f;
    uint32 lsbtwoToTheDoubleScaleUp_f;
    uint32 twoToTheDoubleScaleDown_f;
    uint32 lsbtwoToTheDoubleScaleDown_f;
    uint8 _assertionsDisabled_f__f;
    uint32 bytes_f;
    uint32 writer_f;
    uint32 mFactory_f;
    uint32 UNASSIGNED_STACK_f;
    uint32 SUPPRESSED_SENTINEL_f;
    uint32 TYPE_f__f__f;
    uint32 consoleCon_f;
    uint32 serialPersistentFields_f;
    uint32 CASE_INSENSITIVE_ORDER_f;
    uint32 connectionFactorySet_f;
    uint32 count_f;
    uint8 initialized_f;
    uint8 _dummy_;
} staticClassFields_c;
