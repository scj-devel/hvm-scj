#define NUMBEROFINTERFACES 24
#define NUMBEROFCLASSES 147
#define JAVA_LANG_STRING 57
#define JAVA_LANG_OBJECT 144
#define JAVA_LANG_INTEGER 10
#define JAVA_LANG_BYTE -1
#define JAVA_LANG_SHORT -1
#define JAVA_LANG_CHARACTER -1
#define JAVA_LANG_BOOLEAN -1
#define JAVA_LANG_LONG 11
#define JAVA_LANG_CLASS 145
#define JAVA_LANG_CLASSCASTEXCEPTION 39
#define JAVA_LANG_OUTOFMEMORYERROR 81
#define JAVA_LANG_THROWABLE 29
#define JAVA_LANG_ARITHMETICEXCEPTION 14
#define JAVA_LANG_NOSUCHMETHODEXCEPTION -1
#define JAVA_LANG_REFLECT_METHOD -1
#define JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION -1
#define JAVA_LANG_REFLECT_CONSTRUCTOR -1
#define VM_HARDWAREOBJECT 102
#define REFLECT_HEAPACCESSOR -1
#define VM_ADDRESS32BIT 110
#define VM_ADDRESS64BIT -1
#define JAVA_LANG_NULLPOINTEREXCEPTION 91
#define _C 35
#define _I 43
#define VM_MEMORY 123
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
#define JAVA_SECURITY_PRIVILEGEDACTION -1
#define DEVICES_HANDLER -1
#define JAVA_LANG_RUNNABLE 10

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

typedef struct PACKED _javax_safetycritical_PriorityQueueForLockAndWait_c {
    Object header;
    uint32 queue_f;
    uint32 tail_f;
    uint32 queueSize_f;
} javax_safetycritical_PriorityQueueForLockAndWait_c;

typedef struct PACKED _javax_safetycritical_MemoryInfo_c {
    Object header;
    uint32 privateMemory_f;
    uint32 currentMemory_f;
    uint32 topMemory_f;
} javax_safetycritical_MemoryInfo_c;

typedef struct PACKED _javax_safetycritical_ManagedMemory_c {
    Object header;
    uint32 backingStoreProvider_f;
    uint32 containedMemories_f;
    uint32 nextContainedMemory_f;
    uint32 delegate_f;
    uint32 reservedEnd_f;
    uint32 maxUsage_f;
} javax_safetycritical_ManagedMemory_c;

typedef struct PACKED _vm_Process_X86_64SP_c {
    Object header;
    uint32 csp_f;
    uint32 lsbcsp_f;
    uint32 jsp_f;
    uint32 lsbjsp_f;
} vm_Process_X86_64SP_c;

typedef struct PACKED _javax_safetycritical_Launcher_c {
    Object header;
    uint32 app_f;
} javax_safetycritical_Launcher_c;

typedef struct PACKED _java_lang_Integer_c {
    Object header;
    uint32 value_f;
} java_lang_Integer_c;

typedef struct PACKED _java_lang_Long_c {
    Object header;
    uint32 value_f;
    uint32 lsbvalue_f;
} java_lang_Long_c;

typedef struct PACKED _javax_safetycritical_ManagedThread_c {
    Object header;
    uint32 priority_f;
    uint32 logic_f;
    uint32 priority_f__f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
} javax_safetycritical_ManagedThread_c;

typedef struct PACKED _java_lang_ArithmeticException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ArithmeticException_c;

typedef struct PACKED _javax_safetycritical_ScjPeriodicEventHandlerProcess_c {
    Object header;
    uint32 process_f;
    uint32 msObject_f;
    uint32 state_f;
    uint32 rtClock_f;
    uint32 next_f;
    uint32 start_f;
    uint32 period_f;
    uint32 index_f;
    uint32 monitorLock_f;
    uint32 next_temp_f;
    uint8 isNotified_f;
    uint32 exceptionReporter_f;
} javax_safetycritical_ScjPeriodicEventHandlerProcess_c;

typedef struct PACKED _java_io_ObjectStreamField_c {
    Object header;
    uint32 name_f;
    uint32 signature_f;
    uint32 type_f;
    uint8 unshared_f;
    uint32 field_f;
    uint32 offset_f;
} java_io_ObjectStreamField_c;

typedef struct PACKED _vm_Process_c {
    Object header;
    uint32 logic_f;
    uint32 stack_f;
    uint32 processExecuter_f;
    uint32 sp_f;
    uint32 runtime_data_f;
    uint8 isFinished_f;
} vm_Process_c;

typedef struct PACKED _javax_safetycritical_Mission_c {
    Object header;
    uint32 currMissSeq_f;
    uint8 missionTerminate_f;
    uint32 msSetForMission_f;
    uint32 missionPhase_f;
    uint32 missionIndex_f;
    uint8 isMissionSetInitByThis_f;
} javax_safetycritical_Mission_c;

typedef struct PACKED _java_lang_StringIndexOutOfBoundsException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_StringIndexOutOfBoundsException_c;

typedef struct PACKED _javax_safetycritical_annotate_Phase_c {
    Object header;
    uint32 name_f;
    uint32 ordinal_f;
} javax_safetycritical_annotate_Phase_c;

typedef struct PACKED _java_lang_Exception_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_Exception_c;

typedef struct PACKED _javax_realtime_RealtimeThread_c {
    Object header;
    uint32 priority_f;
    uint32 logic_f;
} javax_realtime_RealtimeThread_c;

typedef struct PACKED _java_lang_StringBuilder_c {
    Object header;
    uint32 value_f;
    uint32 count_f;
} java_lang_StringBuilder_c;

typedef struct PACKED _javax_realtime_NoHeapRealtimeThread_c {
    Object header;
    uint32 priority_f;
    uint32 logic_f;
} javax_realtime_NoHeapRealtimeThread_c;

typedef struct PACKED _java_util_ConcurrentModificationException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_util_ConcurrentModificationException_c;

typedef struct PACKED _java_lang_Throwable_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_Throwable_c;

typedef struct PACKED _javax_safetycritical_ScjAperiodicEventHandlerProcess_c {
    Object header;
    uint32 process_f;
    uint32 msObject_f;
    uint32 state_f;
    uint32 rtClock_f;
    uint32 next_f;
    uint32 start_f;
    uint32 period_f;
    uint32 index_f;
    uint32 monitorLock_f;
    uint32 next_temp_f;
    uint8 isNotified_f;
    uint32 exceptionReporter_f;
} javax_safetycritical_ScjAperiodicEventHandlerProcess_c;

typedef struct PACKED _javax_safetycritical_Monitor_c {
    Object header;
    uint32 ceiling_f;
    uint32 synchCounter_f;
    uint32 priority_f;
    uint32 owner_f;
    uint32 clock_f;
} javax_safetycritical_Monitor_c;

typedef struct PACKED _javax_realtime_RealtimeClock_c {
    Object header;
    uint8 active_f;
} javax_realtime_RealtimeClock_c;

typedef struct PACKED _java_lang_Double_c {
    Object header;
    uint32 value_f;
    uint32 lsbvalue_f;
} java_lang_Double_c;

typedef struct PACKED _java_lang_Error_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_Error_c;

typedef struct PACKED _javax_safetycritical_ScjMissionSequencerProcess_c {
    Object header;
    uint32 process_f;
    uint32 msObject_f;
    uint32 state_f;
    uint32 rtClock_f;
    uint32 next_f;
    uint32 start_f;
    uint32 period_f;
    uint32 index_f;
    uint32 monitorLock_f;
    uint32 next_temp_f;
    uint8 isNotified_f;
    uint32 exceptionReporter_f;
} javax_safetycritical_ScjMissionSequencerProcess_c;

typedef struct PACKED _java_lang_AssertionError_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_AssertionError_c;

typedef struct PACKED _java_lang_ClassCastException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ClassCastException_c;

typedef struct PACKED _vm_Process_ProcessExecutor_c {
    Object header;
    uint32 thisProcess_f;
    uint8 isStarted_f;
} vm_Process_ProcessExecutor_c;

typedef struct PACKED _javax_safetycritical_PeriodicEventHandler_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
} javax_safetycritical_PeriodicEventHandler_c;

typedef struct PACKED _java_util_ArrayList_c {
    Object header;
    uint32 modCount_f;
    uint32 elementData_f;
    uint32 size_f;
} java_util_ArrayList_c;

typedef struct PACKED _test_TestSCJThreadMemory_MySequencer_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
    uint32 missionMemory_f;
    uint32 currMission_f;
    uint32 currState_f;
    uint8 terminateSeq_f;
    uint32 lock_f;
    uint32 outerSeq_f;
    uint32 mission_f__f;
    uint32 count_f;
} test_TestSCJThreadMemory_MySequencer_c;

typedef struct PACKED _java_lang_IllegalMonitorStateException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_IllegalMonitorStateException_c;

typedef struct PACKED _javax_safetycritical_ManagedLongEventHandler_c {
    Object header;
    uint32 data_f;
    uint32 lsbdata_f;
    uint32 priority_f;
    uint32 release_f;
    uint32 name_f;
    uint32 storage_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 process_f;
} javax_safetycritical_ManagedLongEventHandler_c;

typedef struct PACKED _java_lang_VirtualMachineError_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_VirtualMachineError_c;

typedef struct PACKED _javax_safetycritical_SleepingQueue_c {
    Object header;
    uint32 heapSize_f;
    uint32 tree_f;
} javax_safetycritical_SleepingQueue_c;

typedef struct PACKED _java_lang_StringBuffer_c {
    Object header;
    uint32 value_f;
    uint32 count_f;
    uint32 toStringCache_f;
} java_lang_StringBuffer_c;

typedef struct PACKED _java_lang_String_c {
    Object header;
    uint32 value_f;
    uint32 hash_f;
} java_lang_String_c;

typedef struct PACKED _javax_safetycritical_StorageParameters_c {
    Object header;
    uint32 totalBackingStore_f;
    uint32 lsbtotalBackingStore_f;
    uint32 configurationSizes_f;
    uint32 messageLength_f;
    uint32 stackTraceLength_f;
    uint32 maxMemoryArea_f;
    uint32 lsbmaxMemoryArea_f;
    uint32 maxImmortal_f;
    uint32 lsbmaxImmortal_f;
    uint32 maxMissionMemory_f;
    uint32 lsbmaxMissionMemory_f;
} javax_safetycritical_StorageParameters_c;

typedef struct PACKED _javax_realtime_PriorityParameters_c {
    Object header;
    uint32 priority_f;
} javax_realtime_PriorityParameters_c;

typedef struct PACKED _javax_safetycritical_MissionMemory_c {
    Object header;
    uint32 backingStoreProvider_f;
    uint32 containedMemories_f;
    uint32 nextContainedMemory_f;
    uint32 delegate_f;
    uint32 reservedEnd_f;
    uint32 maxUsage_f;
    uint32 runInitialize_f;
    uint32 runExecute_f;
    uint32 runCleanup_f;
    uint32 m_f;
} javax_safetycritical_MissionMemory_c;

typedef struct PACKED _java_lang_NegativeArraySizeException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_NegativeArraySizeException_c;

typedef struct PACKED _java_lang_UnsupportedOperationException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_UnsupportedOperationException_c;

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

typedef struct PACKED _test_TestSCJThreadMemory_SmallObject_c {
    Object header;
    uint32 x_f;
    uint16 y_f;
} test_TestSCJThreadMemory_SmallObject_c;

typedef struct PACKED _vm_Memory_MemoryInfo_c {
    Object header;
    uint32 name_f;
    uint32 size_f;
    uint32 maxUsed_f;
    uint32 instanceCount_f;
} vm_Memory_MemoryInfo_c;

typedef struct PACKED _javax_safetycritical_MissionSequencer_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
    uint32 missionMemory_f;
    uint32 currMission_f;
    uint32 currState_f;
    uint8 terminateSeq_f;
    uint32 lock_f;
    uint32 outerSeq_f;
} javax_safetycritical_MissionSequencer_c;

typedef struct PACKED _javax_safetycritical_ManagedMemory_BackingStore_c {
    Object header;
    uint32 backingStoreProvider_f;
    uint32 containedMemories_f;
    uint32 nextContainedMemory_f;
    uint32 delegate_f;
    uint32 reservedEnd_f;
    uint32 maxUsage_f;
} javax_safetycritical_ManagedMemory_BackingStore_c;

typedef struct PACKED _javax_safetycritical_CyclicScheduler_c {
    Object header;
    uint32 seq_f;
    uint32 current_f;
    uint32 mainProcess_f;
} javax_safetycritical_CyclicScheduler_c;

typedef struct PACKED _java_lang_OutOfMemoryError_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_OutOfMemoryError_c;

typedef struct PACKED _javax_realtime_PeriodicParameters_c {
    Object header;
    uint32 deadline_f;
    uint32 missHandler_f;
    uint32 start_f;
    uint32 period_f;
} javax_realtime_PeriodicParameters_c;

typedef struct PACKED _javax_safetycritical_ScjProcess_c {
    Object header;
    uint32 process_f;
    uint32 msObject_f;
    uint32 state_f;
    uint32 rtClock_f;
    uint32 next_f;
    uint32 start_f;
    uint32 period_f;
    uint32 index_f;
    uint32 monitorLock_f;
    uint32 next_temp_f;
    uint8 isNotified_f;
    uint32 exceptionReporter_f;
} javax_safetycritical_ScjProcess_c;

typedef struct PACKED _java_util_AbstractList_c {
    Object header;
    uint32 modCount_f;
} java_util_AbstractList_c;

typedef struct PACKED _javax_safetycritical_PrivateMemory_c {
    Object header;
    uint32 backingStoreProvider_f;
    uint32 containedMemories_f;
    uint32 nextContainedMemory_f;
    uint32 delegate_f;
    uint32 reservedEnd_f;
    uint32 maxUsage_f;
} javax_safetycritical_PrivateMemory_c;

typedef struct PACKED _javax_safetycritical_OneShotEventHandler_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
    uint32 releaseTime_f;
} javax_safetycritical_OneShotEventHandler_c;

typedef struct PACKED _java_lang_NullPointerException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_NullPointerException_c;

typedef struct PACKED _javax_realtime_AperiodicParameters_c {
    Object header;
    uint32 deadline_f;
    uint32 missHandler_f;
} javax_realtime_AperiodicParameters_c;

typedef struct PACKED _java_lang_Enum_c {
    Object header;
    uint32 name_f;
    uint32 ordinal_f;
} java_lang_Enum_c;

typedef struct PACKED _javax_safetycritical_ScjProcess_ExceptionReporter_c {
    Object header;
    uint32 t_f;
} javax_safetycritical_ScjProcess_ExceptionReporter_c;

typedef struct PACKED _javax_realtime_BoundAsyncLongEventHandler_c {
    Object header;
    uint32 data_f;
    uint32 lsbdata_f;
} javax_realtime_BoundAsyncLongEventHandler_c;

typedef struct PACKED _javax_safetycritical_ManagedMemory_ImmortalMemory_c {
    Object header;
    uint32 backingStoreProvider_f;
    uint32 containedMemories_f;
    uint32 nextContainedMemory_f;
    uint32 delegate_f;
    uint32 reservedEnd_f;
    uint32 maxUsage_f;
} javax_safetycritical_ManagedMemory_ImmortalMemory_c;

typedef struct PACKED _vm_HardwareObject_c {
    Object header;
    uint32 address_f;
} vm_HardwareObject_c;

typedef struct PACKED _javax_safetycritical_ManagedSchedulableSet_c {
    Object header;
    uint32 managedSchObjects_f;
    uint32 noOfRegistered_f;
    uint32 scjProcesses_f;
    uint32 msCount_f;
} javax_safetycritical_ManagedSchedulableSet_c;

typedef struct PACKED _javax_realtime_HighResolutionTime_c {
    Object header;
    uint32 clock_f;
    uint32 millis_f;
    uint32 lsbmillis_f;
    uint32 nanos_f;
} javax_realtime_HighResolutionTime_c;

typedef struct PACKED _test_TestSCJThreadMemory_Thread1_c {
    Object header;
    uint32 priority_f;
    uint32 logic_f;
    uint32 priority_f__f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 count_f;
    uint32 delayTime_f;
} test_TestSCJThreadMemory_Thread1_c;

typedef struct PACKED _test_TestSCJThreadMemory_Thread2_c {
    Object header;
    uint32 priority_f;
    uint32 logic_f;
    uint32 priority_f__f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 delayTime_f;
    uint32 count_f;
} test_TestSCJThreadMemory_Thread2_c;

typedef struct PACKED _vm_Process_X86_32SP_c {
    Object header;
    uint32 csp_f;
    uint32 jsp_f;
} vm_Process_X86_32SP_c;

typedef struct PACKED _javax_realtime_Clock_c {
    Object header;
    uint8 active_f;
} javax_realtime_Clock_c;

typedef struct PACKED _vm_Address32Bit_c {
    Object header;
    uint32 address_f;
} vm_Address32Bit_c;

typedef struct PACKED _javax_safetycritical_PriorityFrame_c {
    Object header;
    uint32 readyQueue_f;
    uint32 sleepingQueue_f;
    uint32 waitQueue_f;
    uint32 lockQueue_f;
} javax_safetycritical_PriorityFrame_c;

typedef struct PACKED _javax_safetycritical_AperiodicEventHandler_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
    uint32 sch_f;
} javax_safetycritical_AperiodicEventHandler_c;

typedef struct PACKED _devices_CR16C_KT4585_CR16CRealtimeClock_c {
    Object header;
    uint32 INT1_PRIORITY_REG_f;
    uint32 TIMER1_RELOAD_N_REG_f;
    uint32 TIMER_CTRL_REG_f;
    uint32 RESET_INT_PENDING_REG_f;
    uint16 tickCount_f;
} devices_CR16C_KT4585_CR16CRealtimeClock_c;

typedef struct PACKED _javax_safetycritical_ScjProcess_1_c {
    Object header;
    uint32 this_0_f;
} javax_safetycritical_ScjProcess_1_c;

typedef struct PACKED _javax_safetycritical_ScjOneShotEventHandlerProcess_c {
    Object header;
    uint32 process_f;
    uint32 msObject_f;
    uint32 state_f;
    uint32 rtClock_f;
    uint32 next_f;
    uint32 start_f;
    uint32 period_f;
    uint32 index_f;
    uint32 monitorLock_f;
    uint32 next_temp_f;
    uint8 isNotified_f;
    uint32 exceptionReporter_f;
} javax_safetycritical_ScjOneShotEventHandlerProcess_c;

typedef struct PACKED _javax_safetycritical_ScjProcess_2_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
} javax_safetycritical_ScjProcess_2_c;

typedef struct PACKED _java_lang_ArrayStoreException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_ArrayStoreException_c;

typedef struct PACKED _javax_realtime_ReleaseParameters_c {
    Object header;
    uint32 deadline_f;
    uint32 missHandler_f;
} javax_realtime_ReleaseParameters_c;

typedef struct PACKED _javax_safetycritical_ScjManagedThreadProcess_c {
    Object header;
    uint32 process_f;
    uint32 msObject_f;
    uint32 state_f;
    uint32 rtClock_f;
    uint32 next_f;
    uint32 start_f;
    uint32 period_f;
    uint32 index_f;
    uint32 monitorLock_f;
    uint32 next_temp_f;
    uint8 isNotified_f;
    uint32 exceptionReporter_f;
} javax_safetycritical_ScjManagedThreadProcess_c;

typedef struct PACKED _vm_Memory_c {
    Object header;
    uint32 base_f;
    uint32 size_f;
    uint32 free_f;
    uint32 name_f;
    uint32 memoryInfo_f;
} vm_Memory_c;

typedef struct PACKED _javax_realtime_RelativeTime_c {
    Object header;
    uint32 clock_f;
    uint32 millis_f;
    uint32 lsbmillis_f;
    uint32 nanos_f;
} javax_realtime_RelativeTime_c;

typedef struct PACKED _javax_safetycritical_PriorityScheduler_c {
    Object header;
    uint32 pFrame_f;
    uint32 current_f;
    uint32 prioritySchedulerImpl_f;
    uint32 rtClock_f;
    uint32 timeGrain_f;
    uint32 now_f;
    uint32 outerMostSeqProcess_f;
    uint32 mainProcess_f;
} javax_safetycritical_PriorityScheduler_c;

typedef struct PACKED _java_lang_AbstractStringBuilder_c {
    Object header;
    uint32 value_f;
    uint32 count_f;
} java_lang_AbstractStringBuilder_c;

typedef struct PACKED _java_util_NoSuchElementException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_util_NoSuchElementException_c;

typedef struct PACKED _javax_realtime_AbsoluteTime_c {
    Object header;
    uint32 clock_f;
    uint32 millis_f;
    uint32 lsbmillis_f;
    uint32 nanos_f;
} javax_realtime_AbsoluteTime_c;

typedef struct PACKED _java_util_ArrayList_Itr_c {
    Object header;
    uint32 cursor_f;
    uint32 lastRet_f;
    uint32 expectedModCount_f;
    uint32 this_0_f;
} java_util_ArrayList_Itr_c;

typedef struct PACKED _javax_realtime_AsyncLongEventHandler_c {
    Object header;
    uint32 data_f;
    uint32 lsbdata_f;
} javax_realtime_AsyncLongEventHandler_c;

typedef struct PACKED _javax_safetycritical_PriorityQueue_c {
    Object header;
    uint32 heapSize_f;
    uint32 tree_f;
} javax_safetycritical_PriorityQueue_c;

typedef struct PACKED _javax_safetycritical_MissionMemory_2_c {
    Object header;
    uint32 this_0_f;
} javax_safetycritical_MissionMemory_2_c;

typedef struct PACKED _javax_safetycritical_MissionMemory_3_c {
    Object header;
    uint32 this_0_f;
} javax_safetycritical_MissionMemory_3_c;

typedef struct PACKED _javax_safetycritical_ManagedEventHandler_c {
    Object header;
    uint32 priority_f;
    uint32 storage_f;
    uint32 process_f;
    uint32 mission_f;
    uint32 memInfo_f;
    uint32 release_f;
    uint32 name_f;
} javax_safetycritical_ManagedEventHandler_c;

typedef struct PACKED _javax_realtime_MemoryArea_c {
    Object header;
    uint32 backingStoreProvider_f;
    uint32 containedMemories_f;
    uint32 nextContainedMemory_f;
    uint32 delegate_f;
    uint32 reservedEnd_f;
    uint32 maxUsage_f;
} javax_realtime_MemoryArea_c;

typedef struct PACKED _javax_safetycritical_MissionMemory_1_c {
    Object header;
    uint32 this_0_f;
} javax_safetycritical_MissionMemory_1_c;

typedef struct PACKED _test_TestSCJThreadMemory_BiggerObject_c {
    Object header;
    uint32 x_f;
    uint32 y_f;
    uint16 z_f;
} test_TestSCJThreadMemory_BiggerObject_c;

typedef struct PACKED _java_lang_IllegalArgumentException_c {
    Object header;
    uint32 backtrace_f;
    uint32 detailMessage_f;
    uint32 cause_f;
    uint32 stackTrace_f;
    uint32 suppressedExceptions_f;
} java_lang_IllegalArgumentException_c;

typedef struct PACKED _vm_ClockInterruptHandler_c {
    Object header;
    uint32 currentProcess_f;
    uint32 scheduler_f;
    uint32 handlerProcess_f;
} vm_ClockInterruptHandler_c;

typedef struct PACKED _java_lang_Class_c {
    Object header;
    uint32 cachedConstructor_f;
    uint32 newInstanceCallerCache_f;
    uint32 name_f;
    uint32 reflectionData_f;
    uint32 classRedefinedCount_f;
    uint32 genericInfo_f;
    uint32 enumConstants_f;
    uint32 enumConstantDirectory_f;
    uint32 annotationData_f;
    uint32 annotationType_f;
    uint32 classValueMap_f;
} java_lang_Class_c;

typedef struct PACKED _test_TestSCJThreadMemory_MyMission_c {
    Object header;
    uint32 currMissSeq_f;
    uint8 missionTerminate_f;
    uint32 msSetForMission_f;
    uint32 missionPhase_f;
    uint32 missionIndex_f;
    uint8 isMissionSetInitByThis_f;
} test_TestSCJThreadMemory_MyMission_c;

typedef struct PACKED _staticClassFields_c {
    uint32 TYPE_f;
    uint8 _assertionsDisabled_f;
    uint32 in_f;
    uint32 out_f;
    uint32 err_f;
    uint32 security_f;
    uint32 cons_f;
    uint8 flag_f;
    uint32 exception_f;
    uint32 level_f;
    uint32 TYPE_f__f;
    uint32 digits_f;
    uint32 DigitTens_f;
    uint32 DigitOnes_f;
    uint32 sizeTable_f;
    uint32 TYPE_f__f__f;
    uint32 bytes_f;
    uint32 writer_f;
    uint32 stackAnalyser_f;
    uint32 missionSet_f;
    uint8 isMissionSetInit_f;
    uint32 currentScheduler_f;
    uint32 INITIALIZE_f;
    uint32 EXECUTE_f;
    uint32 CLEANUP_f;
    uint32 ALL_f;
    uint32 ENUM_VALUES_f;
    uint32 UNASSIGNED_STACK_f;
    uint32 SUPPRESSED_SENTINEL_f;
    uint32 nativeClock_f;
    uint32 rtClock_f;
    uint32 resolution_f;
    uint32 TYPE_f__f__f__f;
    uint32 EMPTY_ELEMENTDATA_f;
    uint32 serialPersistentFields_f;
    uint32 CASE_INSENSITIVE_ORDER_f;
    uint32 OVERALL_BACKING_STORE_f;
    uint32 IDLE_BACKING_STORE_f;
    uint32 IMMORTAL_MEM_f;
    uint32 OUTERMOST_SEQ_BACKING_STORE_f;
    uint32 MISSION_MEM_f;
    uint32 PRIVATE_BACKING_STORE_f;
    uint32 PRIVATE_MEM_f;
    uint32 PRIORITY_SCHEDULER_STACK_SIZE_f;
    uint32 IDLE_PROCESS_STACK_SIZE_f;
    uint32 HANDLER_STACK_SIZE_f;
    uint32 DEFAULT_QUEUE_SIZE_f;
    uint32 DEFAULT_HANDLER_NUMBER_f;
    uint32 DEFAULT_PRIORITY_QUEUE_SIZE_f;
    uint32 DEFAULT_SLEEPING_QUEUE_SIZE_f;
    uint32 reporter_f;
    uint32 DEFAULT_TIME_INTERVAL_f;
    uint32 INFINITE_TIME_f;
    uint32 SUSPEND_TIME_f;
    uint32 MEMORY_TRACKER_AREA_SIZE_f;
    uint32 handlers_f;
    uint32 numberOfInterrupts_f;
    uint8 init_f;
    uint8 isOuterMostSeq_f;
    uint32 missSeqProcess_f;
    uint32 howManyMissions_f;
    uint32 scheduler_f;
    uint32 idleProcess_f;
    uint32 sizeOfSmallObject_f;
    uint32 sizeOfBiggerObject_f;
    uint8 fail_f;
    uint32 storageParameters_Sequencer_f;
    uint32 storageParameters_Handlers_f;
    uint32 instance_f;
    uint32 negativeZeroFloatBits_f;
    uint32 lsbnegativeZeroFloatBits_f;
    uint32 negativeZeroDoubleBits_f;
    uint32 lsbnegativeZeroDoubleBits_f;
    uint32 twoToTheDoubleScaleUp_f;
    uint32 lsbtwoToTheDoubleScaleUp_f;
    uint32 twoToTheDoubleScaleDown_f;
    uint32 lsbtwoToTheDoubleScaleDown_f;
    uint8 _assertionsDisabled_f__f;
    uint8 memoryAreaTrackingEnabled_f;
    uint32 areaToUseForTracking_f;
    uint32 createdMemories_f;
    uint32 nameCount_f;
    uint32 scheduler_f__f;
    uint32 backingStore_f;
    uint8 disableCount_f;
    uint32 instance_f__f;
    uint8 _dummy_;
} staticClassFields_c;
