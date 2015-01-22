#include "ostypes.h"
#include "types.h"
#include "classes.h"
#include "methods.h"
int16 devices_CR16C_KT4585_CR16CRealtimeClock_handle(int32 *fp);
int16 initializeException(int32* sp, int16 exceptionClass, int16 exceptionInitMethod);
RANGE extern const MethodInfo *methods;
extern unsigned short handleAthrow(const MethodInfo* method, unsigned short classIndex, unsigned short pc);
extern int16 readShortFromIO(pointer address, unsigned short offset);
extern void writeShortToIO(pointer address, unsigned short offset, unsigned short lsb);
int16 javax_realtime_HighResolutionTime_setNormalized(int32 *fp);
extern int32 idiv(int32 x, int32 y);
extern void handleLongOperator(unsigned char code, int32* sp);
extern int32 imod(int32 a, int32 b);
extern int32 handleLCMP(int32* sp);
int16 javax_realtime_MemoryArea_init_(int32 *fp,     int32 this,     int32 initialSize,     int32 reservedSize,     int32 backingStoreProvider,     int32 label);
extern int16 enterMethodInterpreter(unsigned short methodNumber, int32* sp);
extern unsigned char handleNewClassIndex(int32* sp, unsigned short classIndex);
extern unsigned short getClassIndex(Object* obj);
extern Object* stringConstants[];
extern const ConstantInfo *constants;
#ifndef PRE_INITIALIZE_CONSTANTS
extern int16 initializeStringConstant(const ConstantInfo* constant, int32* sp);
#endif
int16 javax_realtime_RealtimeClock_getTime(int32 *fp);
extern const unsigned char *classData;
extern const ClassInfo *classes;
RANGE extern const ClassInfo *classes;
extern signed short dispatchInterface(unsigned short methodIndex, unsigned char *vTableIndex, int32* sp);
int16 javax_safetycritical_CyclicScheduler_stop(int32 *fp);
int16 javax_safetycritical_ManagedMemory_init_(int32 *fp);
int16 javax_safetycritical_ManagedMemory_enter(int32 *fp);
int16 javax_safetycritical_Mission_getCurrentMission(int32 *fp);
extern unsigned char isSubClassOf(unsigned short subClass, unsigned short superClass);
unsigned char checkImplements(Object* object, unsigned short interfaceIndex);
int16 javax_safetycritical_Monitor_unlock(int32 *fp);
int16 javax_safetycritical_PriorityQueue_extractMax(int32 *fp);
int16 javax_safetycritical_PriorityQueue_insert(int32 *fp);
extern int16 yieldToScheduler(int32 *sp);
int16 javax_safetycritical_PriorityScheduler_move(int32 *fp);
int16 javax_safetycritical_PriorityQueue_insert(int32* sp);
int16 javax_realtime_RealtimeClock_getTime(int32* sp);
static uint32 dummy;
int16 javax_safetycritical_PriorityQueue_extractMax(int32* sp);
int16 javax_safetycritical_PriorityScheduler_stop(int32 *fp);
int16 javax_safetycritical_PrivateMemory_init_(int32 *fp);
int16 javax_safetycritical_ManagedMemory_init_(int32* sp);
int16 javax_safetycritical_ScjProcess_2_yield(int32 *fp);
int16 n_vm_RealtimeClock_awaitNextTick(int32* sp);
int16 javax_safetycritical_SleepingQueue_extractMin(int32 *fp,     int32 this);
int16 javax_safetycritical_SleepingQueue_getScjProcess(int32* sp);
int16 javax_safetycritical_SleepingQueue_getScjProcess(int32 *fp);
int16 javax_safetycritical_SleepingQueue_minimum(int32 *fp,     int32 this);
int16 vm_ClockInterruptHandler_disable(int32 *fp);
extern int8 hvmClockReady;
int16 vm_ClockInterruptHandler_enable(int32 *fp);
int16 vm_ClockInterruptHandler_handle(int32 *fp);
int16 vm_ClockInterruptHandler_disable(int32* sp);
int16 vm_ClockInterruptHandler_initialize(int32 *fp);
extern int8 architecture;
int16 vm_ClockInterruptHandler_run(int32 *fp);
int16 vm_ClockInterruptHandler_enable(int32* sp);
int16 vm_InterruptDispatcher_NullHandler_handle(int32 *fp);
int16 vm_InterruptDispatcher_interrupt(int32 *fp,     int8 n);
int16 vm_InterruptDispatcher_registerHandler(int32 *fp);
int16 vm_Machine_setCurrentScheduler(int32 *fp);
int16 n_vm_Monitor_attachMonitor(int32* sp);
int16 vm_Monitor_wait(int32* sp);
int16 vm_Monitor_notify(int32* sp);
int16 vm_Monitor_lock_(int32* sp);
int16 vm_Monitor_unlock_(int32* sp);
int16 vm_Memory_init__(int32 *fp,     int32 this,     int32 base,     int32 size,     int32 name);
int16 vm_Memory_addMemoryArea(int32 *fp,     int32 this,     int32 m);
int16 vm_Memory_switchToArea(int32* sp);
extern int32 heapArea;
int16 vm_Memory_allocateInHeap(int32 *fp);
int16 vm_Memory_executeInHeap(int32 *fp);
int16 vm_Memory_getCurrentMemoryArea(int32 *fp);
extern int32 currentMemoryArea;
int16 vm_Memory_getHeapArea(int32 *fp);
int16 vm_Memory_reportMemoryUsage(int32 *fp);
int16 vm_Memory_startMemoryAreaTracking(int32 *fp);
int16 vm_Memory_switchToArea(int32 *fp);
int16 vm_Memory_updateMaxUsed(int32 *fp,     int32 m);
int16 vm_Monitor_getDefaultMonitor(int32 *fp);
int16 vm_Monitor_lock_(int32 *fp);
int16 vm_Monitor_notify(int32 *fp);
int16 vm_Monitor_notifyAll(int32 *fp);
int16 vm_Monitor_unlock_(int32 *fp);
int16 javax_safetycritical_Monitor_unlock(int32* sp);
int16 vm_Monitor_wait(int32 *fp);
int16 vm_Process_ProcessExecutor_run(int32 *fp);
int16 vm_Process_init_(int32 *fp);
int16 vm_Process_transferTo(int32 *fp,     int32 this,     int32 nextProcess);
int16 n_vm_Process_transfer(int32* sp);
int16 vm_RealtimeClock_getRealtimeClock(int32 *fp);
int16 vm_RealtimeClock_getRealtimeClock(int32* sp);
extern void unimplemented_native_function(uint16 methodID);


/* Class: devices.CR16C.KT4585.CR16CInterruptDispatcher */
/* Method: init */
RANGE const unsigned char devices_CR16C_KT4585_CR16CInterruptDispatcher_init[45] PROGMEM = {
  0xB2,0x00,0x49,0x08,
  /* offset: 13, 72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) & 0xff),
  0x9A,0x00,0x25,0x10,0x1F,0xB3,0x00,0x49,0x20,
  /* offset: 13, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) & 0xff),
  0xB2,0x00,0x49,0x20,
  /* offset: 13, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) & 0xff),
  0xBD,0x00,0x53,0xB3,0x00,0x49,0x22,
  /* offset: 13, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, handlers_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, handlers_f) << 3)) & 0xff),
  
  0xB8,0x01,0xD2,0x4F,0x04,0xB3,0x00,0x49,0x08,
  /* offset: 13, 72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: devices.CR16C.KT4585.CR16CRealtimeClock */
/* Method: <init> */
RANGE const unsigned char devices_CR16C_KT4585_CR16CRealtimeClock_init_[142] PROGMEM = {
  0x2A,0xB7,0x02,0x0E,0x1A,0x2A,0xBB,0x00,0x2A,0x59,0x12,0x00,0x00,0xB7,0x00,0x07,
  0x1F,0xB5,0x00,0x71,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, INT1_PRIORITY_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, INT1_PRIORITY_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x2A,0x59,0x12,0x00,0x01,0xB7,
  0x00,0x07,0x1F,0xB5,0x00,0x71,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER1_RELOAD_N_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER1_RELOAD_N_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x2A,0x59,0x12,0x00,
  0x02,0xB7,0x00,0x07,0x1F,0xB5,0x00,0x71,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER_CTRL_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER_CTRL_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x2A,0x59,
  0x12,0x00,0x03,0xB7,0x00,0x07,0x1F,0xB5,0x00,0x71,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, RESET_INT_PENDING_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, RESET_INT_PENDING_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x71,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, INT1_PRIORITY_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, INT1_PRIORITY_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x59,0xCB,0x00,0x15,0x10,0x00,0x20,0x04,0x80,0x93,0xCC,0x00,
  0x15,0x10,0x00,0x20,0x2A,0xB4,0x00,0x71,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER1_RELOAD_N_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER1_RELOAD_N_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x11,0x2D,0x00,0xCC,0x00,
  0x15,0x10,0x00,0x20,0x2A,0xB4,0x00,0x71,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER_CTRL_REG_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, TIMER_CTRL_REG_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x2A,0xCC,0x00,0x15,
  0x10,0x00,0x20,0x2A,0x03,0xB5,0x00,0x71,0x10,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, tickCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, tickCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: devices.CR16C.KT4585.CR16CRealtimeClock */
/* Method: disable */
RANGE const unsigned char devices_CR16C_KT4585_CR16CRealtimeClock_disable[3] PROGMEM = {
  0xB1,0x01,0x01
};

/* Class: devices.CR16C.KT4585.CR16CRealtimeClock */
/* Method: enable */
RANGE const unsigned char devices_CR16C_KT4585_CR16CRealtimeClock_enable[3] PROGMEM = {
  0xB1,0x01,0x01
};

/* Class: devices.CR16C.KT4585.CR16CRealtimeClock */
/* Method: getCurrentTime */
RANGE const unsigned char devices_CR16C_KT4585_CR16CRealtimeClock_getCurrentTime[19] PROGMEM = {
  0x2B,0x2A,0xB4,0x00,0x71,0x10,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, tickCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _devices_CR16C_KT4585_CR16CRealtimeClock_c, tickCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x64,0x68,0x85,0xB7,0x00,0xB6,0x3B,
  0xB1,0x01,0x03
};

/* Class: devices.CR16C.KT4585.CR16CRealtimeClock */
/* Method: getGranularity */
RANGE const unsigned char devices_CR16C_KT4585_CR16CRealtimeClock_getGranularity[6] PROGMEM = {
  0x12,0x00,0x04,0xAC,0x01,0x01
};

/* Class: devices.CR16C.KT4585.CR16CRealtimeClock */
/* Method: handle */
/* handle
 * param : 
 * return: void
 */
int16 devices_CR16C_KT4585_CR16CRealtimeClock_handle(int32 *fp)
{  
   int32* sp;
   int32 i_val2;
   int32 i_val1;
   unsigned char* cobj;
   int16 s_val1;
   int8 b_val0;
   int8 msb_int8;
   int16 lsb_int16;
   short int sresult;
   unsigned char* hw_address;
   struct _vm_HardwareObject_c* hwObject;
   #if defined(VM_ADDRESS64BIT_INIT_)
   Object* _addressObj_;
#endif
   struct _vm_Address32Bit_c* addressObj32bit;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int32 i_val0;
   int32 msb_int32;
   int32 lsb_int32;
   struct _vm_Address32Bit_c* addressObj;
    int32 this;
   this = (int32)(*(fp + 0));
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val2 = this;
   i_val1 = i_val2;
cobj = (unsigned char *) (pointer)i_val1;
   s_val1 = ((struct _devices_CR16C_KT4585_CR16CRealtimeClock_c *)HEAP_REF(cobj, unsigned char*)) -> tickCount_f;
   b_val0 = 1;
      msb_int8 = b_val0;
      lsb_int16 = s_val1;
      lsb_int16 += msb_int8;
   i_val1 = lsb_int16;
      sresult = i_val1;
   i_val1 = sresult;
      lsb_int16 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _devices_CR16C_KT4585_CR16CRealtimeClock_c *)HEAP_REF(cobj, unsigned char*)) -> tickCount_f = lsb_int16;
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _devices_CR16C_KT4585_CR16CRealtimeClock_c *)HEAP_REF(cobj, unsigned char*)) -> RESET_INT_PENDING_REG_f;
   i_val1 = i_val2;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 25;
         goto throwNullPointer;
      }
      hwObject = (struct _vm_HardwareObject_c*)HEAP_REF(cobj, struct _vm_HardwareObject_c*);
#if defined(VM_ADDRESS64BIT_INIT_)
      _addressObj_ = (Object*)HEAP_REF((pointer)(hwObject->address_f), Object*);
      if (*_addressObj_ == VM_ADDRESS64BIT) {
          struct _vm_Address64Bit_c* addressObj64;
          long address;
          addressObj64 = (struct _vm_Address64Bit_c*)_addressObj_;
          address = addressObj64->lsbaddress_f;
          address = (address << 16) << 16;
          address |= addressObj64->address_f;
          hw_address = (unsigned char*) (pointer) address;
      } else {
#endif
          addressObj32bit = (struct _vm_Address32Bit_c*) HEAP_REF((pointer )(hwObject->address_f), struct _vm_Address32Bit_c*);
          hw_address = (unsigned char*) (pointer) (addressObj32bit->address_f);
#if defined(VM_ADDRESS64BIT_INIT_)
      }
#endif
   i_val1 = readShortFromIO((pointer)hw_address, 0);
   i_val0 = 256;
      msb_int32 = i_val0;
      lsb_int32 = i_val1;
      lsb_int32 |= msb_int32;
   i_val1 = lsb_int32;
      sresult = i_val1;
   i_val1 = sresult;
      lsb_int16 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      hwObject = (struct _vm_HardwareObject_c*)HEAP_REF(cobj, struct _vm_HardwareObject_c*);
      addressObj = (struct _vm_Address32Bit_c*)HEAP_REF((pointer)(hwObject->address_f), struct _vm_Address32Bit_c*);
      hw_address = (unsigned char*) (pointer) (addressObj->address_f);
      writeShortToIO((pointer)hw_address, 0, lsb_int16 & 0xffff);
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[6], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: devices.CR16C.KT4585.DeviceRegShort */
/* Method: <init> */
RANGE const unsigned char devices_CR16C_KT4585_DeviceRegShort_init_[17] PROGMEM = {
  0x2A,0xBB,0x00,0xDC,0x59,0x1B,0xB7,0x01,0xBD,0x11,0xB7,0x01,0xCB,0x13,0xB1,0x01,
  0x01
};

/* Class: devices.Console */
/* Method: <clinit> */
RANGE const unsigned char devices_Console_clinit_[28] PROGMEM = {
  0x11,0x02,0x01,0xBC,0x00,0x22,0xB3,0x00,0x11,0x22,
  /* offset: 2, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) & 0xff),
  0xBB,0x00,0x80,0x59,
  0xB7,0x00,0x0C,0x14,0xB3,0x00,0x11,0x22,
  /* offset: 2, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, writer_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, writer_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: devices.Console */
/* Method: getBytes */
RANGE const unsigned char devices_Console_getBytes[64] PROGMEM = {
  0x03,0x3D,0x2A,0xB7,0x00,0x5E,0x25,0x3E,0xA7,0x00,0x15,0xB2,0x00,0x11,0x22,
  /* offset: 2, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) & 0xff),
  0x1C,0x2A,0x1C,0xB7,0x00,0x59,0x39,0x91,0x54,0x84,0x02,0x01,0x1C,0x1D,0xA2,
  0x00,0x0A,0x1C,0x11,0x01,0xFF,0xA1,0xFF,0xE5,0x1B,0x99,0x00,0x0D,0xB2,0x00,0x11,
  0x22,
  /* offset: 2, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) & 0xff),
  0x1C,0x10,0x0A,0x54,0xB2,0x00,0x11,0x22,
  /* offset: 2, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) & 0xff),
  0xB0,0x01,0x11
};

/* Class: devices.Console */
/* Method: println */
RANGE const unsigned char devices_Console_println[9] PROGMEM = {
  0x2A,0x04,0xB8,0x00,0x0B,0x20,0xB1,0x01,0x01
};

/* Class: devices.Console */
/* Method: println */
RANGE const unsigned char devices_Console_println_[49] PROGMEM = {
  0x2A,0xB7,0x00,0x5E,0x25,0x93,0x3D,0x1B,0x99,0x00,0x08,0x1C,0x04,0x60,0x93,0x3D,
  0x2A,0x1B,0xB8,0x00,0x09,0x2B,0x57,0xB2,0x00,0x11,0x22,
  /* offset: 2, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, writer_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, writer_f) << 3)) & 0xff),
  0xB2,0x00,0x11,
  0x22,
  /* offset: 2, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, bytes_f) << 3)) & 0xff),
  0x1C,0xB9,0x00,0x00,0x02,0x14,0x01,0x00,0x40,0x00,0x0E,0xB1,0x01,
  0x01
};

/* Class: devices.X86Writer */
/* Method: <init> */
RANGE const unsigned char devices_X86Writer_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: devices.X86Writer */
/* Method: nwrite */
/* Class: devices.X86Writer */
/* Method: write */
RANGE const unsigned char devices_X86Writer_write[9] PROGMEM = {
  0x2B,0x1C,0xB8,0x00,0x0D,0x14,0xB1,0x01,0x03
};

/* Class: devices.i86.I86InterruptDispatcher */
/* Method: init */
RANGE const unsigned char devices_i86_I86InterruptDispatcher_init[44] PROGMEM = {
  0xB2,0x00,0x49,0x08,
  /* offset: 13, 72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) & 0xff),
  0x9A,0x00,0x24,0x04,0xB3,0x00,0x49,0x20,
  /* offset: 13, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) & 0xff),
  
  0xB2,0x00,0x49,0x20,
  /* offset: 13, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) & 0xff),
  0xBD,0x00,0x53,0xB3,0x00,0x49,0x22,
  /* offset: 13, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, handlers_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, handlers_f) << 3)) & 0xff),
  0xB8,
  0x01,0xD2,0x1D,0x04,0xB3,0x00,0x49,0x08,
  /* offset: 13, 72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: <init> */
RANGE const unsigned char java_lang_AbstractStringBuilder_init_[19] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x19,0x2A,0x1B,0xBC,0x00,0x23,0xB5,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB1,0x01,0x01
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_AbstractStringBuilder_append[44] PROGMEM = {
  0x2A,0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB7,0x00,0x16,0xFA,0x2A,0xB4,
  0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x59,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x5A,0x04,0x60,
  0xB5,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x55,0x2A,0xB0,0x01,0x05
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_AbstractStringBuilder_append_[91] PROGMEM = {
  0x1B,0x12,0x00,0x05,0xA0,0x00,0x19,0x2A,0x12,0x00,0x06,0xB6,0x01,0x0A,0x01,0x02,
  0x00,0x1A,0x00,0x6C,0x00,0x36,0x00,0x63,0x57,0x2A,0xB0,0x01,0x11,0x1B,0x9C,0x00,
  0x0E,0x1B,0x74,0xB8,0x00,0x3B,0x15,0x04,0x60,0xA7,0x00,0x08,0x1B,0xB8,0x00,0x3B,
  0x15,0x3D,0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x60,0x3E,0x2A,0x1D,0xB7,0x00,
  0x16,0xFA,0x1B,0x1D,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x00,0x39,0x16,0x2A,
  0x1D,0xB5,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB0,0x01,0x11
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_AbstractStringBuilder_append__[98] PROGMEM = {
  0x1F,0x14,0x00,0x07,0x94,0x9A,0x00,0x19,0x2A,0x12,0x00,0x08,0xB6,0x01,0x0A,0x01,
  0x02,0x00,0x1A,0x00,0x6C,0x00,0x36,0x00,0x63,0x57,0x2A,0xB0,0x01,0x21,0x1F,0x09,
  0x94,0x9C,0x00,0x0E,0x1F,0x75,0xB8,0x00,0x40,0x17,0x04,0x60,0xA7,0x00,0x08,0x1F,
  0xB8,0x00,0x40,0x17,0x3E,0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x60,0x36,0x04,
  0x2A,0x15,0x04,0xB7,0x00,0x16,0xFA,0x1F,0x15,0x04,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x00,0x3F,0x18,0x2A,0x15,0x04,0xB5,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB0,
  0x01,0x21
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_AbstractStringBuilder_append___[73] PROGMEM = {
  0x2B,0xC7,0x00,0x0B,0x2A,0xB7,0x00,0x15,0xFD,0xB0,0x01,0x0B,0x2B,0xB7,0x00,0x5E,
  0x1B,0x3D,0x2A,0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x60,0xB7,0x00,0x16,0xFA,
  0x2B,0x03,0x1C,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x5C,0x1C,0x2A,0x59,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x60,0xB5,
  0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB0,0x01,0x0B
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: appendNull */
RANGE const unsigned char java_lang_AbstractStringBuilder_appendNull[68] PROGMEM = {
  0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x3C,0x2A,0x1B,0x07,0x60,0xB7,0x00,0x16,0xFA,
  0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x2C,0x1B,0x84,0x01,0x01,0x10,0x6E,0x55,
  0x2C,0x1B,0x84,0x01,0x01,0x10,0x75,0x55,0x2C,0x1B,0x84,0x01,0x01,0x10,0x6C,0x55,
  0x2C,0x1B,0x84,0x01,0x01,0x10,0x6C,0x55,0x2A,0x1B,0xB5,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB0,0x01,0x0D
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: ensureCapacityInternal */
RANGE const unsigned char java_lang_AbstractStringBuilder_ensureCapacityInternal[22] PROGMEM = {
  0x1B,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x64,0x9E,0x00,0x09,0x2A,0x1B,0xB7,
  0x00,0x17,0xFB,0xB1,0x01,0x01
};

/* Class: java.lang.AbstractStringBuilder */
/* Method: expandCapacity */
RANGE const unsigned char java_lang_AbstractStringBuilder_expandCapacity[64] PROGMEM = {
  0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x05,0x68,0x05,0x60,0x3D,0x1C,0x1B,0x64,
  0x9C,0x00,0x05,0x1B,0x3D,0x1C,0x9C,0x00,0x14,0x1B,0x9C,0x00,0x0C,0xBB,0x00,0xA2,
  0x59,0xB7,0x00,0x4F,0x1A,0xBF,0x12,0x00,0x09,0x3D,0x2A,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0xB8,0x00,0x98,0x31,0xB5,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: java.lang.ArithmeticException */
/* Method: <init> */
RANGE const unsigned char java_lang_ArithmeticException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.ArrayIndexOutOfBoundsException */
/* Method: <init> */
RANGE const unsigned char java_lang_ArrayIndexOutOfBoundsException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x35,0x1F,0xB1,0x01,0x01
};

/* Class: java.lang.ArrayStoreException */
/* Method: <init> */
RANGE const unsigned char java_lang_ArrayStoreException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.AssertionError */
/* Method: <init> */
RANGE const unsigned char java_lang_AssertionError_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x2B,0x36,0xB1,0x01,0x01
};

/* Class: java.lang.Class */
/* Method: <clinit> */
/* Class: java.lang.Class */
/* Method: desiredAssertionStatus */
/* Class: java.lang.Class */
/* Method: getComponentType */
/* Class: java.lang.Class */
/* Method: getName0 */
/* Class: java.lang.Class */
/* Method: getName */
RANGE const unsigned char java_lang_Class_getName[30] PROGMEM = {
  0x2A,0xB4,0x00,0x91,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_Class_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_Class_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0x4C,0x2B,0xC7,0x00,0x11,0x2A,0x2A,0xB7,0x00,
  0x1F,0xD0,0x59,0x4C,0xB5,0x00,0x91,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_Class_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_Class_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB0,0x01,0x07
};

/* Class: java.lang.Class */
/* Method: getPrimitiveClass */
/* Class: java.lang.Class */
/* Method: toString */
/* Class: java.lang.ClassCastException */
/* Method: <init> */
RANGE const unsigned char java_lang_ClassCastException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.ClassCastException */
/* Method: <init> */
RANGE const unsigned char java_lang_ClassCastException_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x52,0x13,0xB1,0x01,0x03
};

/* Class: java.lang.Double */
/* Method: <clinit> */
RANGE const unsigned char java_lang_Double_clinit_[15] PROGMEM = {
  0x12,0x00,0x0A,0xB8,0x00,0x21,0xC1,0xB3,0x00,0x21,0x22,
  /* offset: 5, -96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f__f__f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f__f__f__f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.Double */
/* Method: doubleToRawLongBits */
/* Class: java.lang.Double */
/* Method: longBitsToDouble */
/* Class: java.lang.Enum */
/* Method: <init> */
RANGE const unsigned char java_lang_Enum_init_[24] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x70,0x2A,0x2B,0xB5,0x00,0x60,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_Enum_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_Enum_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB5,
  0x00,0x60,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_Enum_c, ordinal_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_Enum_c, ordinal_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: java.lang.Enum */
/* Method: hashCode */
RANGE const unsigned char java_lang_Enum_hashCode[8] PROGMEM = {
  0x2A,0xB7,0x00,0x4A,0x6F,0xAC,0x01,0x01
};

/* Class: java.lang.Enum */
/* Method: toString */
RANGE const unsigned char java_lang_Enum_toString[10] PROGMEM = {
  0x2A,0xB4,0x00,0x60,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_Enum_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_Enum_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: java.lang.Error */
/* Method: <init> */
RANGE const unsigned char java_lang_Error_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x74,0x18,0xB1,0x01,0x01
};

/* Class: java.lang.Error */
/* Method: <init> */
RANGE const unsigned char java_lang_Error_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x75,0x19,0xB1,0x01,0x03
};

/* Class: java.lang.Exception */
/* Method: <init> */
RANGE const unsigned char java_lang_Exception_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x74,0x18,0xB1,0x01,0x01
};

/* Class: java.lang.Exception */
/* Method: <init> */
RANGE const unsigned char java_lang_Exception_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x75,0x19,0xB1,0x01,0x03
};

/* Class: java.lang.Float */
/* Method: <clinit> */
RANGE const unsigned char java_lang_Float_clinit_[15] PROGMEM = {
  0x12,0x00,0x0B,0xB8,0x00,0x21,0x94,0xB3,0x00,0x00,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.Float */
/* Method: floatToRawIntBits */
/* Class: java.lang.IllegalArgumentException */
/* Method: <init> */
RANGE const unsigned char java_lang_IllegalArgumentException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x16,0xB1,0x01,0x01
};

/* Class: java.lang.IllegalArgumentException */
/* Method: <init> */
RANGE const unsigned char java_lang_IllegalArgumentException_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x52,0x17,0xB1,0x01,0x03
};

/* Class: java.lang.IllegalMonitorStateException */
/* Method: <init> */
RANGE const unsigned char java_lang_IllegalMonitorStateException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.IllegalMonitorStateException */
/* Method: <init> */
RANGE const unsigned char java_lang_IllegalMonitorStateException_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x52,0x13,0xB1,0x01,0x03
};

/* Class: java.lang.IndexOutOfBoundsException */
/* Method: <init> */
RANGE const unsigned char java_lang_IndexOutOfBoundsException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.IndexOutOfBoundsException */
/* Method: <init> */
RANGE const unsigned char java_lang_IndexOutOfBoundsException_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x52,0x13,0xB1,0x01,0x03
};

/* Class: java.lang.Integer */
/* Method: <clinit> */
RANGE const unsigned char java_lang_Integer_clinit_[1519] PROGMEM = {
  0x12,0x00,0x0C,0xB8,0x00,0x21,0x0C,0xB3,0x00,0x0A,0x22,
  /* offset: 1, -72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f__f) << 3)) & 0xff),
  0x10,0x24,0xBC,
  0x00,0x23,0x59,0x03,0x10,0x30,0x55,0x59,0x04,0x10,0x31,0x55,0x59,0x05,0x10,0x32,
  0x55,0x59,0x06,0x10,0x33,0x55,0x59,0x07,0x10,0x34,0x55,0x59,0x08,0x10,0x35,0x55,
  0x59,0x10,0x06,0x10,0x36,0x55,0x59,0x10,0x07,0x10,0x37,0x55,0x59,0x10,0x08,0x10,
  0x38,0x55,0x59,0x10,0x09,0x10,0x39,0x55,0x59,0x10,0x0A,0x10,0x61,0x55,0x59,0x10,
  0x0B,0x10,0x62,0x55,0x59,0x10,0x0C,0x10,0x63,0x55,0x59,0x10,0x0D,0x10,0x64,0x55,
  0x59,0x10,0x0E,0x10,0x65,0x55,0x59,0x10,0x0F,0x10,0x66,0x55,0x59,0x10,0x10,0x10,
  0x67,0x55,0x59,0x10,0x11,0x10,0x68,0x55,0x59,0x10,0x12,0x10,0x69,0x55,0x59,0x10,
  0x13,0x10,0x6A,0x55,0x59,0x10,0x14,0x10,0x6B,0x55,0x59,0x10,0x15,0x10,0x6C,0x55,
  0x59,0x10,0x16,0x10,0x6D,0x55,0x59,0x10,0x17,0x10,0x6E,0x55,0x59,0x10,0x18,0x10,
  0x6F,0x55,0x59,0x10,0x19,0x10,0x70,0x55,0x59,0x10,0x1A,0x10,0x71,0x55,0x59,0x10,
  0x1B,0x10,0x72,0x55,0x59,0x10,0x1C,0x10,0x73,0x55,0x59,0x10,0x1D,0x10,0x74,0x55,
  0x59,0x10,0x1E,0x10,0x75,0x55,0x59,0x10,0x1F,0x10,0x76,0x55,0x59,0x10,0x20,0x10,
  0x77,0x55,0x59,0x10,0x21,0x10,0x78,0x55,0x59,0x10,0x22,0x10,0x79,0x55,0x59,0x10,
  0x23,0x10,0x7A,0x55,0xB3,0x00,0x0A,0x22,
  /* offset: 1, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) & 0xff),
  0x10,0x64,0xBC,0x00,0x23,0x59,
  0x03,0x10,0x30,0x55,0x59,0x04,0x10,0x30,0x55,0x59,0x05,0x10,0x30,0x55,0x59,0x06,
  0x10,0x30,0x55,0x59,0x07,0x10,0x30,0x55,0x59,0x08,0x10,0x30,0x55,0x59,0x10,0x06,
  0x10,0x30,0x55,0x59,0x10,0x07,0x10,0x30,0x55,0x59,0x10,0x08,0x10,0x30,0x55,0x59,
  0x10,0x09,0x10,0x30,0x55,0x59,0x10,0x0A,0x10,0x31,0x55,0x59,0x10,0x0B,0x10,0x31,
  0x55,0x59,0x10,0x0C,0x10,0x31,0x55,0x59,0x10,0x0D,0x10,0x31,0x55,0x59,0x10,0x0E,
  0x10,0x31,0x55,0x59,0x10,0x0F,0x10,0x31,0x55,0x59,0x10,0x10,0x10,0x31,0x55,0x59,
  0x10,0x11,0x10,0x31,0x55,0x59,0x10,0x12,0x10,0x31,0x55,0x59,0x10,0x13,0x10,0x31,
  0x55,0x59,0x10,0x14,0x10,0x32,0x55,0x59,0x10,0x15,0x10,0x32,0x55,0x59,0x10,0x16,
  0x10,0x32,0x55,0x59,0x10,0x17,0x10,0x32,0x55,0x59,0x10,0x18,0x10,0x32,0x55,0x59,
  0x10,0x19,0x10,0x32,0x55,0x59,0x10,0x1A,0x10,0x32,0x55,0x59,0x10,0x1B,0x10,0x32,
  0x55,0x59,0x10,0x1C,0x10,0x32,0x55,0x59,0x10,0x1D,0x10,0x32,0x55,0x59,0x10,0x1E,
  0x10,0x33,0x55,0x59,0x10,0x1F,0x10,0x33,0x55,0x59,0x10,0x20,0x10,0x33,0x55,0x59,
  0x10,0x21,0x10,0x33,0x55,0x59,0x10,0x22,0x10,0x33,0x55,0x59,0x10,0x23,0x10,0x33,
  0x55,0x59,0x10,0x24,0x10,0x33,0x55,0x59,0x10,0x25,0x10,0x33,0x55,0x59,0x10,0x26,
  0x10,0x33,0x55,0x59,0x10,0x27,0x10,0x33,0x55,0x59,0x10,0x28,0x10,0x34,0x55,0x59,
  0x10,0x29,0x10,0x34,0x55,0x59,0x10,0x2A,0x10,0x34,0x55,0x59,0x10,0x2B,0x10,0x34,
  0x55,0x59,0x10,0x2C,0x10,0x34,0x55,0x59,0x10,0x2D,0x10,0x34,0x55,0x59,0x10,0x2E,
  0x10,0x34,0x55,0x59,0x10,0x2F,0x10,0x34,0x55,0x59,0x10,0x30,0x10,0x34,0x55,0x59,
  0x10,0x31,0x10,0x34,0x55,0x59,0x10,0x32,0x10,0x35,0x55,0x59,0x10,0x33,0x10,0x35,
  0x55,0x59,0x10,0x34,0x10,0x35,0x55,0x59,0x10,0x35,0x10,0x35,0x55,0x59,0x10,0x36,
  0x10,0x35,0x55,0x59,0x10,0x37,0x10,0x35,0x55,0x59,0x10,0x38,0x10,0x35,0x55,0x59,
  0x10,0x39,0x10,0x35,0x55,0x59,0x10,0x3A,0x10,0x35,0x55,0x59,0x10,0x3B,0x10,0x35,
  0x55,0x59,0x10,0x3C,0x10,0x36,0x55,0x59,0x10,0x3D,0x10,0x36,0x55,0x59,0x10,0x3E,
  0x10,0x36,0x55,0x59,0x10,0x3F,0x10,0x36,0x55,0x59,0x10,0x40,0x10,0x36,0x55,0x59,
  0x10,0x41,0x10,0x36,0x55,0x59,0x10,0x42,0x10,0x36,0x55,0x59,0x10,0x43,0x10,0x36,
  0x55,0x59,0x10,0x44,0x10,0x36,0x55,0x59,0x10,0x45,0x10,0x36,0x55,0x59,0x10,0x46,
  0x10,0x37,0x55,0x59,0x10,0x47,0x10,0x37,0x55,0x59,0x10,0x48,0x10,0x37,0x55,0x59,
  0x10,0x49,0x10,0x37,0x55,0x59,0x10,0x4A,0x10,0x37,0x55,0x59,0x10,0x4B,0x10,0x37,
  0x55,0x59,0x10,0x4C,0x10,0x37,0x55,0x59,0x10,0x4D,0x10,0x37,0x55,0x59,0x10,0x4E,
  0x10,0x37,0x55,0x59,0x10,0x4F,0x10,0x37,0x55,0x59,0x10,0x50,0x10,0x38,0x55,0x59,
  0x10,0x51,0x10,0x38,0x55,0x59,0x10,0x52,0x10,0x38,0x55,0x59,0x10,0x53,0x10,0x38,
  0x55,0x59,0x10,0x54,0x10,0x38,0x55,0x59,0x10,0x55,0x10,0x38,0x55,0x59,0x10,0x56,
  0x10,0x38,0x55,0x59,0x10,0x57,0x10,0x38,0x55,0x59,0x10,0x58,0x10,0x38,0x55,0x59,
  0x10,0x59,0x10,0x38,0x55,0x59,0x10,0x5A,0x10,0x39,0x55,0x59,0x10,0x5B,0x10,0x39,
  0x55,0x59,0x10,0x5C,0x10,0x39,0x55,0x59,0x10,0x5D,0x10,0x39,0x55,0x59,0x10,0x5E,
  0x10,0x39,0x55,0x59,0x10,0x5F,0x10,0x39,0x55,0x59,0x10,0x60,0x10,0x39,0x55,0x59,
  0x10,0x61,0x10,0x39,0x55,0x59,0x10,0x62,0x10,0x39,0x55,0x59,0x10,0x63,0x10,0x39,
  0x55,0xB3,0x00,0x0A,0x22,
  /* offset: 1, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) & 0xff),
  0x10,0x64,0xBC,0x00,0x23,0x59,0x03,0x10,0x30,
  0x55,0x59,0x04,0x10,0x31,0x55,0x59,0x05,0x10,0x32,0x55,0x59,0x06,0x10,0x33,0x55,
  0x59,0x07,0x10,0x34,0x55,0x59,0x08,0x10,0x35,0x55,0x59,0x10,0x06,0x10,0x36,0x55,
  0x59,0x10,0x07,0x10,0x37,0x55,0x59,0x10,0x08,0x10,0x38,0x55,0x59,0x10,0x09,0x10,
  0x39,0x55,0x59,0x10,0x0A,0x10,0x30,0x55,0x59,0x10,0x0B,0x10,0x31,0x55,0x59,0x10,
  0x0C,0x10,0x32,0x55,0x59,0x10,0x0D,0x10,0x33,0x55,0x59,0x10,0x0E,0x10,0x34,0x55,
  0x59,0x10,0x0F,0x10,0x35,0x55,0x59,0x10,0x10,0x10,0x36,0x55,0x59,0x10,0x11,0x10,
  0x37,0x55,0x59,0x10,0x12,0x10,0x38,0x55,0x59,0x10,0x13,0x10,0x39,0x55,0x59,0x10,
  0x14,0x10,0x30,0x55,0x59,0x10,0x15,0x10,0x31,0x55,0x59,0x10,0x16,0x10,0x32,0x55,
  0x59,0x10,0x17,0x10,0x33,0x55,0x59,0x10,0x18,0x10,0x34,0x55,0x59,0x10,0x19,0x10,
  0x35,0x55,0x59,0x10,0x1A,0x10,0x36,0x55,0x59,0x10,0x1B,0x10,0x37,0x55,0x59,0x10,
  0x1C,0x10,0x38,0x55,0x59,0x10,0x1D,0x10,0x39,0x55,0x59,0x10,0x1E,0x10,0x30,0x55,
  0x59,0x10,0x1F,0x10,0x31,0x55,0x59,0x10,0x20,0x10,0x32,0x55,0x59,0x10,0x21,0x10,
  0x33,0x55,0x59,0x10,0x22,0x10,0x34,0x55,0x59,0x10,0x23,0x10,0x35,0x55,0x59,0x10,
  0x24,0x10,0x36,0x55,0x59,0x10,0x25,0x10,0x37,0x55,0x59,0x10,0x26,0x10,0x38,0x55,
  0x59,0x10,0x27,0x10,0x39,0x55,0x59,0x10,0x28,0x10,0x30,0x55,0x59,0x10,0x29,0x10,
  0x31,0x55,0x59,0x10,0x2A,0x10,0x32,0x55,0x59,0x10,0x2B,0x10,0x33,0x55,0x59,0x10,
  0x2C,0x10,0x34,0x55,0x59,0x10,0x2D,0x10,0x35,0x55,0x59,0x10,0x2E,0x10,0x36,0x55,
  0x59,0x10,0x2F,0x10,0x37,0x55,0x59,0x10,0x30,0x10,0x38,0x55,0x59,0x10,0x31,0x10,
  0x39,0x55,0x59,0x10,0x32,0x10,0x30,0x55,0x59,0x10,0x33,0x10,0x31,0x55,0x59,0x10,
  0x34,0x10,0x32,0x55,0x59,0x10,0x35,0x10,0x33,0x55,0x59,0x10,0x36,0x10,0x34,0x55,
  0x59,0x10,0x37,0x10,0x35,0x55,0x59,0x10,0x38,0x10,0x36,0x55,0x59,0x10,0x39,0x10,
  0x37,0x55,0x59,0x10,0x3A,0x10,0x38,0x55,0x59,0x10,0x3B,0x10,0x39,0x55,0x59,0x10,
  0x3C,0x10,0x30,0x55,0x59,0x10,0x3D,0x10,0x31,0x55,0x59,0x10,0x3E,0x10,0x32,0x55,
  0x59,0x10,0x3F,0x10,0x33,0x55,0x59,0x10,0x40,0x10,0x34,0x55,0x59,0x10,0x41,0x10,
  0x35,0x55,0x59,0x10,0x42,0x10,0x36,0x55,0x59,0x10,0x43,0x10,0x37,0x55,0x59,0x10,
  0x44,0x10,0x38,0x55,0x59,0x10,0x45,0x10,0x39,0x55,0x59,0x10,0x46,0x10,0x30,0x55,
  0x59,0x10,0x47,0x10,0x31,0x55,0x59,0x10,0x48,0x10,0x32,0x55,0x59,0x10,0x49,0x10,
  0x33,0x55,0x59,0x10,0x4A,0x10,0x34,0x55,0x59,0x10,0x4B,0x10,0x35,0x55,0x59,0x10,
  0x4C,0x10,0x36,0x55,0x59,0x10,0x4D,0x10,0x37,0x55,0x59,0x10,0x4E,0x10,0x38,0x55,
  0x59,0x10,0x4F,0x10,0x39,0x55,0x59,0x10,0x50,0x10,0x30,0x55,0x59,0x10,0x51,0x10,
  0x31,0x55,0x59,0x10,0x52,0x10,0x32,0x55,0x59,0x10,0x53,0x10,0x33,0x55,0x59,0x10,
  0x54,0x10,0x34,0x55,0x59,0x10,0x55,0x10,0x35,0x55,0x59,0x10,0x56,0x10,0x36,0x55,
  0x59,0x10,0x57,0x10,0x37,0x55,0x59,0x10,0x58,0x10,0x38,0x55,0x59,0x10,0x59,0x10,
  0x39,0x55,0x59,0x10,0x5A,0x10,0x30,0x55,0x59,0x10,0x5B,0x10,0x31,0x55,0x59,0x10,
  0x5C,0x10,0x32,0x55,0x59,0x10,0x5D,0x10,0x33,0x55,0x59,0x10,0x5E,0x10,0x34,0x55,
  0x59,0x10,0x5F,0x10,0x35,0x55,0x59,0x10,0x60,0x10,0x36,0x55,0x59,0x10,0x61,0x10,
  0x37,0x55,0x59,0x10,0x62,0x10,0x38,0x55,0x59,0x10,0x63,0x10,0x39,0x55,0xB3,0x00,
  0x0A,0x22,
  /* offset: 2, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) & 0xff),
  0x10,0x0A,0xBC,0x00,0x2B,0x59,0x03,0x10,0x09,0x4F,0x59,0x04,
  0x10,0x63,0x4F,0x59,0x05,0x11,0x03,0xE7,0x4F,0x59,0x06,0x11,0x27,0x0F,0x4F,0x59,
  0x07,0x12,0x00,0x0D,0x4F,0x59,0x08,0x12,0x00,0x0E,0x4F,0x59,0x10,0x06,0x12,0x00,
  0x0F,0x4F,0x59,0x10,0x07,0x12,0x00,0x10,0x4F,0x59,0x10,0x08,0x12,0x00,0x11,0x4F,
  0x59,0x10,0x09,0x12,0x00,0x09,0x4F,0xB3,0x00,0x0A,0x22,
  /* offset: 2, 56*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeTable_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeTable_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.Integer */
/* Method: formatUnsignedInt */
RANGE const unsigned char java_lang_Integer_formatUnsignedInt[54] PROGMEM = {
  0x15,0x04,0x36,0x05,0x04,0x1B,0x78,0x36,0x06,0x15,0x06,0x04,0x64,0x36,0x07,0x2C,
  0x1D,0x84,0x05,0xFF,0x15,0x05,0x60,0xB2,0x00,0x0A,0x22,
  /* offset: 1, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) & 0xff),
  0x1A,0x15,0x07,
  0x7E,0x34,0x55,0x1A,0x1B,0x7C,0x3B,0x1A,0x99,0x00,0x08,0x15,0x05,0x9D,0xFF,0xE2,
  0x15,0x05,0xAC,0x02,0x04,0x00
};

/* Class: java.lang.Integer */
/* Method: getChars */
RANGE const unsigned char java_lang_Integer_getChars[144] PROGMEM = {
  0x1B,0x36,0x05,0x03,0x36,0x06,0x1A,0x9C,0x00,0x0A,0x10,0x2D,0x36,0x06,0x1A,0x74,
  0x3B,0x1A,0x12,0x00,0x12,0xA1,0x00,0x3D,0x1A,0x10,0x64,0x6C,0x3E,0x1A,0x1D,0x10,
  0x06,0x78,0x1D,0x08,0x78,0x60,0x1D,0x05,0x78,0x60,0x64,0x36,0x04,0x1D,0x3B,0x2C,
  0x84,0x05,0xFF,0x15,0x05,0xB2,0x00,0x0A,0x22,
  /* offset: 2, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) & 0xff),
  0x15,0x04,0x34,0x55,0x2C,
  0x84,0x05,0xFF,0x15,0x05,0xB2,0x00,0x0A,0x22,
  /* offset: 1, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) & 0xff),
  0x15,0x04,0x34,0x55,0xA7,
  0xFF,0xC2,0x1A,0x12,0x00,0x13,0x68,0x10,0x13,0x7C,0x3E,0x1A,0x1D,0x06,0x78,0x1D,
  0x04,0x78,0x60,0x64,0x36,0x04,0x2C,0x84,0x05,0xFF,0x15,0x05,0xB2,0x00,0x0A,0x22,
  /* offset: 1, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) & 0xff),
  0x15,0x04,0x34,0x55,0x1D,0x3B,0x1A,0x9A,0xFF,0xD9,0xA7,0x00,0x03,0x15,
  0x06,0x99,0x00,0x0C,0x2C,0x84,0x05,0xFF,0x15,0x05,0x15,0x06,0x55,0xB1,0x01,0x04
};

/* Class: java.lang.Integer */
/* Method: numberOfLeadingZeros */
RANGE const unsigned char java_lang_Integer_numberOfLeadingZeros[80] PROGMEM = {
  0x1A,0x9A,0x00,0x08,0x10,0x20,0xAC,0x01,0x00,0x04,0x3C,0x1A,0x10,0x10,0x7C,0x9A,
  0x00,0x0B,0x84,0x01,0x10,0x1A,0x10,0x10,0x78,0x3B,0x1A,0x10,0x18,0x7C,0x9A,0x00,
  0x0B,0x84,0x01,0x08,0x1A,0x10,0x08,0x78,0x3B,0x1A,0x10,0x1C,0x7C,0x9A,0x00,0x0A,
  0x84,0x01,0x04,0x1A,0x07,0x78,0x3B,0x1A,0x10,0x1E,0x7C,0x9A,0x00,0x0A,0x84,0x01,
  0x02,0x1A,0x05,0x78,0x3B,0x1B,0x1A,0x10,0x1F,0x7C,0x64,0x3C,0x1B,0xAC,0x01,0x00
};

/* Class: java.lang.Integer */
/* Method: stringSize */
RANGE const unsigned char java_lang_Integer_stringSize[26] PROGMEM = {
  0x03,0x3C,0x1A,0xB2,0x00,0x0A,0x22,
  /* offset: 2, 56*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeTable_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeTable_f) << 3)) & 0xff),
  0x1B,0x2E,0xA3,0x00,0x09,0x1B,0x04,
  0x60,0xAC,0x01,0x00,0x84,0x01,0x01,0xA7,0xFF,0xEB
};

/* Class: java.lang.Integer */
/* Method: toHexString */
RANGE const unsigned char java_lang_Integer_toHexString[9] PROGMEM = {
  0x1A,0x07,0xB8,0x00,0x3D,0x19,0xB0,0x01,0x02
};

/* Class: java.lang.Integer */
/* Method: toUnsignedString0 */
RANGE const unsigned char java_lang_Integer_toUnsignedString0[53] PROGMEM = {
  0x10,0x20,0x1A,0xB8,0x00,0x3A,0x0F,0x64,0x3D,0x1C,0x1B,0x04,0x64,0x60,0x1B,0x6C,
  0x04,0xB8,0x00,0x43,0x22,0x3E,0x1D,0xBC,0x00,0x23,0x3A,0x04,0x1A,0x1B,0x19,0x04,
  0x03,0x1D,0xB8,0x00,0x38,0x15,0x57,0xBB,0x00,0x72,0x59,0x19,0x04,0x04,0xB7,0x00,
  0x58,0x2A,0xB0,0x01,0x30
};

/* Class: java.lang.Long */
/* Method: <clinit> */
RANGE const unsigned char java_lang_Long_clinit_[15] PROGMEM = {
  0x12,0x00,0x14,0xB8,0x00,0x21,0x2D,0xB3,0x00,0x0B,0x22,
  /* offset: 2, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f__f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, TYPE_f__f__f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.Long */
/* Method: getChars */
RANGE const unsigned char java_lang_Long_getChars[241] PROGMEM = {
  0x1C,0x36,0x07,0x03,0x36,0x08,0x1E,0x09,0x94,0x9C,0x00,0x0A,0x10,0x2D,0x36,0x08,
  0x1E,0x75,0x3F,0x1E,0x14,0x00,0x15,0x94,0x9E,0x00,0x44,0x1E,0x14,0x00,0x16,0x6D,
  0x37,0x04,0x1E,0x16,0x04,0x10,0x06,0x79,0x16,0x04,0x08,0x79,0x61,0x16,0x04,0x05,
  0x79,0x61,0x65,0x88,0x36,0x06,0x16,0x04,0x3F,0x2D,0x84,0x07,0xFF,0x15,0x07,0xB2,
  0x00,0x0A,0x22,
  /* offset: 2, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) & 0xff),
  0x15,0x06,0x34,0x55,0x2D,0x84,0x07,0xFF,0x15,0x07,0xB2,
  0x00,0x0A,0x22,
  /* offset: 1, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) & 0xff),
  0x15,0x06,0x34,0x55,0xA7,0xFF,0xBA,0x1E,0x88,0x36,0x0A,
  0x15,0x0A,0x12,0x00,0x12,0xA1,0x00,0x45,0x15,0x0A,0x10,0x64,0x6C,0x36,0x09,0x15,
  0x0A,0x15,0x09,0x10,0x06,0x78,0x15,0x09,0x08,0x78,0x60,0x15,0x09,0x05,0x78,0x60,
  0x64,0x36,0x06,0x15,0x09,0x36,0x0A,0x2D,0x84,0x07,0xFF,0x15,0x07,0xB2,0x00,0x0A,
  0x22,
  /* offset: 2, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitOnes_f) << 3)) & 0xff),
  0x15,0x06,0x34,0x55,0x2D,0x84,0x07,0xFF,0x15,0x07,0xB2,0x00,0x0A,
  0x22,
  /* offset: 1, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DigitTens_f) << 3)) & 0xff),
  0x15,0x06,0x34,0x55,0xA7,0xFF,0xB9,0x15,0x0A,0x12,0x00,0x13,0x68,
  0x10,0x13,0x7C,0x36,0x09,0x15,0x0A,0x15,0x09,0x06,0x78,0x15,0x09,0x04,0x78,0x60,
  0x64,0x36,0x06,0x2D,0x84,0x07,0xFF,0x15,0x07,0xB2,0x00,0x0A,0x22,
  /* offset: 1, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, digits_f) << 3)) & 0xff),
  0x15,
  0x06,0x34,0x55,0x15,0x09,0x36,0x0A,0x15,0x0A,0x9A,0xFF,0xD1,0xA7,0x00,0x03,0x15,
  0x08,0x99,0x00,0x0C,0x2D,0x84,0x07,0xFF,0x15,0x07,0x15,0x08,0x55,0xB1,0x02,0x0C,
  0x00
};

/* Class: java.lang.Long */
/* Method: stringSize */
RANGE const unsigned char java_lang_Long_stringSize[42] PROGMEM = {
  0x14,0x00,0x17,0x41,0x04,0x36,0x04,0x15,0x04,0x10,0x13,0xA2,0x00,0x1A,0x1E,0x20,
  0x94,0x9C,0x00,0x08,0x15,0x04,0xAC,0x01,0x00,0x14,0x00,0x17,0x20,0x69,0x41,0x84,
  0x04,0x01,0xA7,0xFF,0xE5,0x10,0x13,0xAC,0x01,0x00
};

/* Class: java.lang.Math */
/* Method: <clinit> */
RANGE const unsigned char java_lang_Math_clinit_[76] PROGMEM = {
  0x12,0x00,0x18,0xB7,0x00,0x1D,0xEA,0x9A,0x00,0x07,0x04,0xA7,0x00,0x04,0x03,0xB3,
  0x00,0x61,0x08,
  /* offset: 15, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, _assertionsDisabled_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, _assertionsDisabled_f__f) << 3)) & 0xff),
  0x12,0x00,0x19,0xB8,0x00,0x30,0xEE,0x85,0xB3,0x00,0x61,
  0x40,
  /* offset: 14, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, negativeZeroFloatBits_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, negativeZeroFloatBits_f) << 3)) & 0xff),
  0x14,0x00,0x1A,0xB8,0x00,0x26,0xEB,0xB3,0x00,0x61,0x40,
  /* offset: 14, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, negativeZeroDoubleBits_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, negativeZeroDoubleBits_f) << 3)) & 0xff),
  
  0x11,0x02,0x00,0xB8,0x00,0x45,0xF7,0xB3,0x00,0x61,0x40,
  /* offset: 14, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, twoToTheDoubleScaleUp_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, twoToTheDoubleScaleUp_f) << 3)) & 0xff),
  0x11,0xFE,0x00,
  0xB8,0x00,0x45,0xF7,0xB3,0x00,0x61,0x40,
  /* offset: 14, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, twoToTheDoubleScaleDown_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, twoToTheDoubleScaleDown_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.Math */
/* Method: <init> */
RANGE const unsigned char java_lang_Math_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0xFE,0xB1,0x01,0x01
};

/* Class: java.lang.Math */
/* Method: max */
RANGE const unsigned char java_lang_Math_max[13] PROGMEM = {
  0x1A,0x1B,0xA1,0x00,0x07,0x1A,0xA7,0x00,0x04,0x1B,0xAC,0x01,0x00
};

/* Class: java.lang.Math */
/* Method: min */
RANGE const unsigned char java_lang_Math_min[13] PROGMEM = {
  0x1A,0x1B,0xA3,0x00,0x07,0x1A,0xA7,0x00,0x04,0x1B,0xAC,0x01,0x00
};

/* Class: java.lang.Math */
/* Method: powerOfTwoD */
RANGE const unsigned char java_lang_Math_powerOfTwoD[52] PROGMEM = {
  0xB2,0x00,0x61,0x08,
  /* offset: 15, 24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, _assertionsDisabled_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, _assertionsDisabled_f__f) << 3)) & 0xff),
  0x9A,0x00,0x1A,0x1A,0x11,0xFC,0x02,0xA1,0x00,0x0A,
  0x1A,0x11,0x03,0xFF,0xA4,0x00,0x0C,0xBB,0x00,0x4C,0x59,0xB7,0x00,0x1B,0xE9,0xBF,
  0x1A,0x85,0x14,0x00,0x1B,0x61,0x10,0x34,0x79,0x14,0x00,0x1C,0x7F,0xB8,0x00,0x27,
  0xED,0xAF,0x01,0x00
};

/* Class: java.lang.NegativeArraySizeException */
/* Method: <init> */
RANGE const unsigned char java_lang_NegativeArraySizeException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.NullPointerException */
/* Method: <init> */
RANGE const unsigned char java_lang_NullPointerException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.Object */
/* Method: <init> */
RANGE const unsigned char java_lang_Object_init_[3] PROGMEM = {
  0xB1,0x01,0x01
};

/* Class: java.lang.Object */
/* Method: getClass */
/* Class: java.lang.Object */
/* Method: hashCode */
/* Class: java.lang.Object */
/* Method: notify */
/* Class: java.lang.Object */
/* Method: toString */
RANGE const unsigned char java_lang_Object_toString[69] PROGMEM = {
  0xBB,0x00,0x34,0x59,0xB7,0x00,0x66,0x4B,0x2A,0xB7,0x00,0x49,0x4A,0xB7,0x00,0x20,
  0x44,0xB7,0x00,0x6D,0x4D,0x12,0x00,0x1D,0xB7,0x00,0x6D,0x4D,0x2A,0xB6,0x01,0x02,
  0x00,0x05,0x00,0x90,0x00,0x4A,0x00,0x39,0x00,0x5D,0x00,0x57,0x00,0x85,0x00,0x68,
  0x00,0xB4,0x00,0x60,0x00,0x29,0xB8,0x00,0x3C,0x46,0xB7,0x00,0x6D,0x4D,0xB7,0x00,
  0x6E,0x4C,0xB0,0x01,0x03
};

/* Class: java.lang.Object */
/* Method: wait */
RANGE const unsigned char java_lang_Object_wait[9] PROGMEM = {
  0x2A,0x09,0xB7,0x00,0x4E,0x49,0xB1,0x01,0x01
};

/* Class: java.lang.Object */
/* Method: wait */
/* Class: java.lang.OutOfMemoryError */
/* Method: <init> */
RANGE const unsigned char java_lang_OutOfMemoryError_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x7D,0x12,0xB1,0x01,0x01
};

/* Class: java.lang.OutOfMemoryError */
/* Method: <init> */
RANGE const unsigned char java_lang_OutOfMemoryError_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x7E,0x13,0xB1,0x01,0x03
};

/* Class: java.lang.RuntimeException */
/* Method: <init> */
RANGE const unsigned char java_lang_RuntimeException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x2D,0x18,0xB1,0x01,0x01
};

/* Class: java.lang.RuntimeException */
/* Method: <init> */
RANGE const unsigned char java_lang_RuntimeException_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x2E,0x19,0xB1,0x01,0x03
};

/* Class: java.lang.String$CaseInsensitiveComparator */
/* Method: <init> */
RANGE const unsigned char java_lang_String_CaseInsensitiveComparator_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x3C,0xB1,0x01,0x01
};

/* Class: java.lang.String$CaseInsensitiveComparator */
/* Method: <init> */
RANGE const unsigned char java_lang_String_CaseInsensitiveComparator_init__[8] PROGMEM = {
  0x2A,0xB7,0x00,0x53,0x3F,0xB1,0x01,0x03
};

/* Class: java.lang.String */
/* Method: <clinit> */
RANGE const unsigned char java_lang_String_clinit_[27] PROGMEM = {
  0x03,0xBD,0x00,0x76,0xB3,0x00,0x39,0x22,
  /* offset: 7, -88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, serialPersistentFields_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, serialPersistentFields_f) << 3)) & 0xff),
  0xBB,0x00,0x9A,0x59,0x01,0xB7,
  0x00,0x54,0xEA,0xB3,0x00,0x39,0x22,
  /* offset: 7, -56*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, CASE_INSENSITIVE_ORDER_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, CASE_INSENSITIVE_ORDER_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.String */
/* Method: <init> */
RANGE const unsigned char java_lang_String_initFromCharArray[22] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0xC8,0x2A,0x2B,0x2B,0xBE,0xB8,0x00,0x98,0xFC,0xB5,0x00,0x39,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: java.lang.String */
/* Method: <init> */
RANGE const unsigned char java_lang_String_init__[72] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0xC8,0x1C,0x9C,0x00,0x0D,0xBB,0x00,0x28,0x59,0x1C,0xB7,0x00,
  0x6F,0xF5,0xBF,0x1D,0x9C,0x00,0x0D,0xBB,0x00,0x28,0x59,0x1D,0xB7,0x00,0x6F,0xF5,
  0xBF,0x1C,0x2B,0xBE,0x1D,0x64,0xA4,0x00,0x0F,0xBB,0x00,0x28,0x59,0x1C,0x1D,0x60,
  0xB7,0x00,0x6F,0xF5,0xBF,0x2A,0x2B,0x1C,0x1C,0x1D,0x60,0xB8,0x00,0x9B,0xFD,0xB5,
  0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: java.lang.String */
/* Method: <init> */
RANGE const unsigned char java_lang_String_init___[16] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0xC8,0x2A,0x2B,0xB5,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: java.lang.String */
/* Method: charAt */
RANGE const unsigned char java_lang_String_charAt[38] PROGMEM = {
  0x1B,0x9B,0x00,0x0F,0x1B,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0xA1,0x00,0x0D,
  0xBB,0x00,0x28,0x59,0x1B,0xB7,0x00,0x6F,0xF5,0xBF,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x34,0xAC,0x01,0x01
};

/* Class: java.lang.String */
/* Method: compareTo */
RANGE const unsigned char java_lang_String_compareTo[97] PROGMEM = {
  0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x3D,0x2B,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xBE,0x3E,0x1C,0x1D,0xB8,0x00,0x44,0xC5,0x36,0x04,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x05,0x2B,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x06,0x03,0x36,0x07,0x15,
  0x07,0x15,0x04,0xA2,0x00,0x27,0x19,0x05,0x15,0x07,0x34,0x36,0x08,0x19,0x06,0x15,
  0x07,0x34,0x36,0x09,0x15,0x08,0x15,0x09,0x9F,0x00,0x0C,0x15,0x08,0x15,0x09,0x64,
  0xAC,0x02,0x63,0x00,0x84,0x07,0x01,0xA7,0xFF,0xD8,0x1C,0x1D,0x64,0xAC,0x02,0x63,
  0x00
};

/* Class: java.lang.String */
/* Method: equals */
RANGE const unsigned char java_lang_String_equals[101] PROGMEM = {
  0x2A,0x2B,0xA6,0x00,0x07,0x04,0xAC,0x01,0x03,0x2B,0xC1,0x00,0x72,0x99,0x00,0x54,
  0x2B,0xC0,0x00,0x72,0x4D,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x3E,0x1D,0x2C,
  0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0xA0,0x00,0x3A,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x04,0x2C,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x05,0x03,0x36,0x06,0x1D,
  0x84,0x03,0xFF,0x99,0x00,0x1A,0x19,0x04,0x15,0x06,0x34,0x19,0x05,0x15,0x06,0x34,
  0x9F,0x00,0x07,0x03,0xAC,0x01,0x37,0x84,0x06,0x01,0xA7,0xFF,0xE5,0x04,0xAC,0x01,
  0x37,0x03,0xAC,0x01,0x07
};

/* Class: java.lang.String */
/* Method: getChars */
RANGE const unsigned char java_lang_String_getChars[74] PROGMEM = {
  0x1B,0x9C,0x00,0x0D,0xBB,0x00,0x28,0x59,0x1B,0xB7,0x00,0x6F,0xF5,0xBF,0x1C,0x2A,
  0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0xA4,0x00,0x0D,0xBB,0x00,0x28,0x59,0x1C,0xB7,
  0x00,0x6F,0xF5,0xBF,0x1B,0x1C,0xA4,0x00,0x0F,0xBB,0x00,0x28,0x59,0x1C,0x1B,0x64,
  0xB7,0x00,0x6F,0xF5,0xBF,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x2D,0x15,0x04,
  0x1C,0x1B,0x64,0xB8,0x00,0x71,0xF6,0xB1,0x01,0x09
};

/* Class: java.lang.String */
/* Method: hashCode */
RANGE const unsigned char java_lang_String_hashCode[72] PROGMEM = {
  0x2A,0xB4,0x00,0x39,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, hash_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, hash_f) - sizeof(Object)) << 3)) & 0xff),
  0x3C,0x1B,0x9A,0x00,0x3B,0x2A,0xB4,0x00,0x39,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x9E,0x00,0x30,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x03,
  0x3E,0x1D,0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0xA2,0x00,0x12,0x10,0x1F,0x1B,
  0x68,0x2C,0x1D,0x34,0x60,0x3C,0x84,0x03,0x01,0xA7,0xFF,0xE8,0x2A,0x1B,0xB5,0x00,
  0x39,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, hash_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, hash_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0xAC,0x01,0x05
};

/* Class: java.lang.String */
/* Method: length */
RANGE const unsigned char java_lang_String_length[11] PROGMEM = {
  0x2A,0xB4,0x00,0x39,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_String_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0xAC,0x01,0x01
};

/* Class: java.lang.String */
/* Method: toString */
RANGE const unsigned char java_lang_String_toString[4] PROGMEM = {
  0x2A,0xB0,0x01,0x03
};

/* Class: java.lang.String */
/* Method: valueOf */
RANGE const unsigned char java_lang_String_valueOf[75] PROGMEM = {
  0x2A,0xC7,0x00,0x09,0x12,0x00,0x1E,0xA7,0x00,0x41,0x2A,0xB6,0x01,0x03,0x00,0x0E,
  0x00,0x90,0x00,0x4C,0x00,0x1D,0x00,0x7B,0x00,0x91,0x00,0x22,0x00,0x39,0x00,0x5F,
  0x00,0x1A,0x00,0x6E,0x00,0x5E,0x00,0x83,0x00,0x68,0x00,0xBA,0x00,0x8B,0x00,0xC6,
  0x00,0x7B,0x01,0xED,0x00,0x4C,0x01,0xD9,0x00,0x36,0x00,0x65,0x00,0x54,0x01,0x88,
  0x00,0x67,0x01,0x0E,0x00,0x60,0x00,0x2A,0xB0,0x01,0x03
};

/* Class: java.lang.StringBuffer */
/* Method: <init> */
RANGE const unsigned char java_lang_StringBuffer_init_[10] PROGMEM = {
  0x2A,0x10,0x10,0xB7,0x00,0x10,0x22,0xB1,0x01,0x01
};

/* Class: java.lang.StringBuffer */
/* Method: append */
RANGE const unsigned char java_lang_StringBuffer_append_[19] PROGMEM = {
  0x2A,0x01,0xB5,0x00,0x36,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1B,0xB7,0x00,0x12,0x2B,0x57,0x2A,
  0xB0,0x01,0x05
};

/* Class: java.lang.StringBuffer */
/* Method: append */
RANGE const unsigned char java_lang_StringBuffer_append__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x64,0x6C,0xB0,0x01,0x07
};

/* Class: java.lang.StringBuffer */
/* Method: append */
RANGE const unsigned char java_lang_StringBuffer_append___[19] PROGMEM = {
  0x2A,0x01,0xB5,0x00,0x36,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB7,0x00,0x14,0x46,0x57,0x2A,
  0xB0,0x01,0x07
};

/* Class: java.lang.StringBuffer */
/* Method: toString */
RANGE const unsigned char java_lang_StringBuffer_toString[55] PROGMEM = {
  0x2A,0xB4,0x00,0x36,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x1D,0x2A,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x03,0x2A,0xB4,0x00,0x7F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x00,0x9B,0x6F,0xB5,0x00,
  0x36,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) & 0xff),
  0xBB,0x00,0x72,0x59,0x2A,0xB4,0x00,0x36,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_StringBuffer_c, toStringCache_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,
  0xB7,0x00,0x58,0x49,0xB0,0x01,0x03
};

/* Class: java.lang.StringBuilder */
/* Method: <init> */
RANGE const unsigned char java_lang_StringBuilder_init_[10] PROGMEM = {
  0x2A,0x10,0x10,0xB7,0x00,0x10,0xF7,0xB1,0x01,0x01
};

/* Class: java.lang.StringBuilder */
/* Method: <init> */
RANGE const unsigned char java_lang_StringBuilder_init__[23] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x5E,0x22,0x10,0x10,0x60,0xB7,0x00,0x10,0xF7,0x2A,0x2B,0xB7,
  0x00,0x6D,0x42,0x57,0xB1,0x01,0x03
};

/* Class: java.lang.StringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_StringBuilder_append_[11] PROGMEM = {
  0x2A,0x1B,0xB7,0x00,0x11,0xFF,0x57,0x2A,0xB0,0x01,0x05
};

/* Class: java.lang.StringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_StringBuilder_append__[11] PROGMEM = {
  0x2A,0x1B,0xB7,0x00,0x12,0x02,0x57,0x2A,0xB0,0x01,0x05
};

/* Class: java.lang.StringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_StringBuilder_append___[11] PROGMEM = {
  0x2A,0x1F,0xB7,0x00,0x13,0x0E,0x57,0x2A,0xB0,0x01,0x09
};

/* Class: java.lang.StringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_StringBuilder_append____[13] PROGMEM = {
  0x2A,0x2B,0xB8,0x00,0x60,0x24,0xB7,0x00,0x6D,0x42,0xB0,0x01,0x07
};

/* Class: java.lang.StringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_StringBuilder_append_____[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x6D,0x42,0xB0,0x01,0x07
};

/* Class: java.lang.StringBuilder */
/* Method: append */
RANGE const unsigned char java_lang_StringBuilder_append______[11] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x14,0x20,0x57,0x2A,0xB0,0x01,0x07
};

/* Class: java.lang.StringBuilder */
/* Method: toString */
RANGE const unsigned char java_lang_StringBuilder_toString[26] PROGMEM = {
  0xBB,0x00,0x72,0x59,0x2A,0xB4,0x00,0x7F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, value_f) - sizeof(Object)) << 3)) & 0xff),
  0x03,0x2A,0xB4,0x00,0x7F,
  0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_AbstractStringBuilder_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x57,0x23,0xB0,0x01,0x03
};

/* Class: java.lang.StringIndexOutOfBoundsException */
/* Method: <init> */
RANGE const unsigned char java_lang_StringIndexOutOfBoundsException_init_[32] PROGMEM = {
  0x2A,0xBB,0x00,0x34,0x59,0xB7,0x00,0x66,0x21,0x12,0x00,0x1F,0xB7,0x00,0x6D,0x24,
  0x1B,0xB7,0x00,0x69,0x23,0xB7,0x00,0x6E,0x22,0xB7,0x00,0x36,0x20,0xB1,0x01,0x01
};

/* Class: java.lang.System */
/* Method: <clinit> */
RANGE const unsigned char java_lang_System_clinit_[41] PROGMEM = {
  0xB8,0x00,0x72,0x4E,0x01,0xB3,0x00,0x05,0x22,
  /* offset: 0, 104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, in_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, in_f) << 3)) & 0xff),
  0x01,0xB3,0x00,0x05,0x22,
  /* offset: 0, -120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, out_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, out_f) << 3)) & 0xff),
  0x01,0xB3,0x00,0x05,0x22,
  /* offset: 0, -88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, err_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, err_f) << 3)) & 0xff),
  0x01,0xB3,0x00,0x05,0x22,
  /* offset: 0, -56*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, security_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, security_f) << 3)) & 0xff),
  
  0x01,0xB3,0x00,0x05,0x22,
  /* offset: 0, -24*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, cons_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, cons_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.lang.System */
/* Method: arraycopy */
/* Class: java.lang.System */
/* Method: registerNatives */
/* Class: java.lang.Throwable */
/* Method: <clinit> */
/* Class: java.lang.Throwable */
/* Method: <init> */
RANGE const unsigned char java_lang_Throwable_init_[48] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x1B,0x2A,0x2A,0xB5,0x00,0x1D,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, cause_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, cause_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB2,0x00,
  0x1D,0x22,
  /* offset: 17, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, UNASSIGNED_STACK_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, UNASSIGNED_STACK_f) << 3)) & 0xff),
  0xB5,0x00,0x1D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB2,0x00,0x1D,0x22,
  /* offset: 17, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, SUPPRESSED_SENTINEL_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, SUPPRESSED_SENTINEL_f) << 3)) & 0xff),
  0xB5,0x00,0x1D,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, suppressedExceptions_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, suppressedExceptions_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x76,0x2B,0x57,0xB1,0x01,0x01
};

/* Class: java.lang.Throwable */
/* Method: <init> */
RANGE const unsigned char java_lang_Throwable_init__[56] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x1B,0x2A,0x2A,0xB5,0x00,0x1D,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, cause_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, cause_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB2,0x00,
  0x1D,0x22,
  /* offset: 17, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, UNASSIGNED_STACK_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, UNASSIGNED_STACK_f) << 3)) & 0xff),
  0xB5,0x00,0x1D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB2,0x00,0x1D,0x22,
  /* offset: 17, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, SUPPRESSED_SENTINEL_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, SUPPRESSED_SENTINEL_f) << 3)) & 0xff),
  0xB5,0x00,0x1D,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, suppressedExceptions_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, suppressedExceptions_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x76,0x2B,0x57,0x2A,0x2B,0xB5,
  0x00,0x1D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, detailMessage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, detailMessage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: java.lang.Throwable */
/* Method: fillInStackTrace */
RANGE const unsigned char java_lang_Throwable_fillInStackTrace[44] PROGMEM = {
  0x2A,0xB4,0x00,0x1D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x0D,0x2A,0xB4,0x00,0x1D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, backtrace_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, backtrace_f) - sizeof(Object)) << 3)) & 0xff),
  0xC6,0x00,0x17,0x2A,0x03,0xB7,0x00,0x77,0x2E,0x57,0x2A,0xB2,0x00,0x1D,0x22,
  /* offset: 17, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, UNASSIGNED_STACK_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, UNASSIGNED_STACK_f) << 3)) & 0xff),
  0xB5,0x00,0x1D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, stackTrace_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB0,0x01,0x03
};

/* Class: java.lang.Throwable */
/* Method: fillInStackTrace */
/* Class: java.lang.Throwable */
/* Method: getLocalizedMessage */
RANGE const unsigned char java_lang_Throwable_getLocalizedMessage[8] PROGMEM = {
  0x2A,0xB7,0x00,0x79,0x29,0xB0,0x01,0x03
};

/* Class: java.lang.Throwable */
/* Method: getMessage */
RANGE const unsigned char java_lang_Throwable_getMessage[10] PROGMEM = {
  0x2A,0xB4,0x00,0x1D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, detailMessage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_lang_IllegalArgumentException_c, detailMessage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: java.lang.Throwable */
/* Method: printStackTrace */
/* Class: java.lang.Throwable */
/* Method: toString */
RANGE const unsigned char java_lang_Throwable_toString[56] PROGMEM = {
  0x2A,0xB7,0x00,0x49,0x1C,0xB7,0x00,0x20,0x16,0x4C,0x2A,0xB7,0x00,0x78,0x28,0x4D,
  0x2C,0xC6,0x00,0x23,0xBB,0x00,0x34,0x59,0xB7,0x00,0x66,0x1E,0x2B,0xB7,0x00,0x6D,
  0x22,0x12,0x00,0x20,0xB7,0x00,0x6D,0x22,0x2C,0xB7,0x00,0x6D,0x22,0xB7,0x00,0x6E,
  0x1F,0xA7,0x00,0x04,0x2B,0xB0,0x01,0x0F
};

/* Class: java.lang.UnsupportedOperationException */
/* Method: <init> */
RANGE const unsigned char java_lang_UnsupportedOperationException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x16,0xB1,0x01,0x01
};

/* Class: java.lang.VirtualMachineError */
/* Method: <init> */
RANGE const unsigned char java_lang_VirtualMachineError_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x2B,0x16,0xB1,0x01,0x01
};

/* Class: java.lang.VirtualMachineError */
/* Method: <init> */
RANGE const unsigned char java_lang_VirtualMachineError_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x2C,0x17,0xB1,0x01,0x03
};

/* Class: java.lang.reflect.Array */
/* Method: newArray */
/* Class: java.lang.reflect.Array */
/* Method: newInstance */
RANGE const unsigned char java_lang_reflect_Array_newInstance[9] PROGMEM = {
  0x2A,0x1B,0xB8,0x00,0x7F,0x43,0xB0,0x01,0x05
};

/* Class: java.util.AbstractCollection */
/* Method: <init> */
RANGE const unsigned char java_util_AbstractCollection_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x76,0xB1,0x01,0x01
};

/* Class: java.util.AbstractCollection */
/* Method: iterator */
/* Class: java.util.AbstractCollection */
/* Method: toString */
RANGE const unsigned char java_util_AbstractCollection_toString[118] PROGMEM = {
  0x2A,0xB7,0x00,0x94,0x85,0x4C,0x2B,0xB9,0x00,0x00,0x00,0x04,0x01,0x00,0x83,0x00,
  0x8A,0x9A,0x00,0x09,0x12,0x00,0x21,0xB0,0x01,0x13,0xBB,0x00,0x34,0x59,0xB7,0x00,
  0x66,0x7A,0x4D,0x2C,0x10,0x5B,0xB7,0x00,0x68,0x7C,0x57,0x2B,0xB9,0x00,0x01,0x00,
  0x04,0x01,0x00,0x83,0x00,0x8B,0x4E,0x2C,0x2D,0x2A,0xA6,0x00,0x09,0x12,0x00,0x22,
  0xA7,0x00,0x04,0x2D,0xB7,0x00,0x6B,0x7D,0x57,0x2B,0xB9,0x00,0x00,0x00,0x04,0x01,
  0x00,0x83,0x00,0x8A,0x9A,0x00,0x11,0x2C,0x10,0x5D,0xB7,0x00,0x68,0x7C,0xB7,0x00,
  0x6E,0x7B,0xB0,0x01,0x1F,0x2C,0x10,0x2C,0xB7,0x00,0x68,0x7C,0x10,0x20,0xB7,0x00,
  0x68,0x7C,0x57,0xA7,0xFF,0xB8
};

/* Class: java.util.AbstractList */
/* Method: <init> */
RANGE const unsigned char java_util_AbstractList_init_[16] PROGMEM = {
  0x2A,0xB7,0x00,0x81,0x8D,0x2A,0x03,0xB5,0x00,0x57,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: java.util.AbstractList */
/* Method: hashCode */
RANGE const unsigned char java_util_AbstractList_hashCode[81] PROGMEM = {
  0x04,0x3C,0x2A,0xB7,0x00,0x94,0x93,0x4D,0x2C,0xB9,0x00,0x00,0x00,0x04,0x01,0x00,
  0x83,0x00,0x8A,0x99,0x00,0x3A,0x2C,0xB9,0x00,0x01,0x00,0x04,0x01,0x00,0x83,0x00,
  0x8B,0x4E,0x10,0x1F,0x1B,0x68,0x2D,0xC7,0x00,0x07,0x03,0xA7,0x00,0x1D,0x2D,0xB6,
  0x01,0x02,0x00,0x05,0x00,0x90,0x00,0x4A,0x00,0x39,0x00,0x5D,0x00,0x57,0x00,0x85,
  0x00,0x68,0x00,0xB4,0x00,0x60,0x00,0x29,0x60,0x3C,0xA7,0xFF,0xBE,0x1B,0xAC,0x01,
  0x0D
};

/* Class: java.util.AbstractList */
/* Method: iterator */
RANGE const unsigned char java_util_AbstractList_iterator[10] PROGMEM = {
  0xBB,0x00,0x4D,0x59,0x2A,0x01,0xB7,0x00,0x96,0xB0
};

/* Class: java.util.ArrayList$Itr */
/* Method: <init> */
RANGE const unsigned char java_util_ArrayList_Itr_init_[44] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x83,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x48,0x51,0x2A,0x02,0xB5,
  0x00,0x83,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, lastRet_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, lastRet_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x83,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x57,
  0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x83,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, expectedModCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, expectedModCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: java.util.ArrayList$Itr */
/* Method: <init> */
RANGE const unsigned char java_util_ArrayList_Itr_init__[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0x87,0x55,0xB1,0x01,0x07
};

/* Class: java.util.ArrayList$Itr */
/* Method: checkForComodification */
RANGE const unsigned char java_util_ArrayList_Itr_checkForComodification[35] PROGMEM = {
  0x2A,0xB4,0x00,0x83,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x57,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x83,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, expectedModCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, expectedModCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9F,0x00,0x0C,0xBB,0x00,0x38,0x59,0xB7,0x00,0x9C,0x56,0xBF,
  0xB1,0x01,0x01
};

/* Class: java.util.ArrayList$Itr */
/* Method: hasNext */
RANGE const unsigned char java_util_ArrayList_Itr_hasNext[29] PROGMEM = {
  0x2A,0xB4,0x00,0x83,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, cursor_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, cursor_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x83,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x00,
  0x8E,0x53,0x9F,0x00,0x07,0x04,0xA7,0x00,0x04,0x03,0xAC,0x01,0x01
};

/* Class: java.util.ArrayList$Itr */
/* Method: next */
RANGE const unsigned char java_util_ArrayList_Itr_next[90] PROGMEM = {
  0x2A,0xB7,0x00,0x89,0x54,0x2A,0xB4,0x00,0x83,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, cursor_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, cursor_f) - sizeof(Object)) << 3)) & 0xff),
  0x3C,0x1B,0x2A,0xB4,
  0x00,0x83,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x00,0x8E,0x53,0xA1,0x00,0x0C,0xBB,0x01,0x02,0x59,
  0xB7,0x00,0x9D,0x57,0xBF,0x2A,0xB4,0x00,0x83,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x1B,0x2C,0xBE,0xA1,0x00,0x0C,0xBB,0x00,0x38,0x59,0xB7,0x00,0x9C,
  0x56,0xBF,0x2A,0x1B,0x04,0x60,0xB5,0x00,0x83,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, cursor_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, cursor_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0x2A,0x1B,0x5A,
  0xB5,0x00,0x83,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, lastRet_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_Itr_c, lastRet_f) - sizeof(Object)) << 3)) & 0xff),
  0x32,0xB0,0x01,0x0D
};

/* Class: java.util.ArrayList */
/* Method: <clinit> */
RANGE const unsigned char java_util_ArrayList_clinit_[12] PROGMEM = {
  0x03,0xBD,0x00,0x4A,0xB3,0x00,0x2D,0x22,
  /* offset: 6, -96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, EMPTY_ELEMENTDATA_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, EMPTY_ELEMENTDATA_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.util.ArrayList */
/* Method: <init> */
RANGE const unsigned char java_util_ArrayList_init_[21] PROGMEM = {
  0x2A,0xB7,0x00,0x84,0x30,0x2A,0xB2,0x00,0x2D,0x22,
  /* offset: 6, -96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, EMPTY_ELEMENTDATA_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, EMPTY_ELEMENTDATA_f) << 3)) & 0xff),
  0xB5,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: java.util.ArrayList */
/* Method: access$100 */
RANGE const unsigned char java_util_ArrayList_access_100[10] PROGMEM = {
  0x2A,0xB4,0x00,0x2D,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: java.util.ArrayList */
/* Method: add */
RANGE const unsigned char java_util_ArrayList_add[44] PROGMEM = {
  0x2A,0x2A,0xB4,0x00,0x2D,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB7,0x00,0x90,0x32,0x2A,0xB4,
  0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x59,0xB4,0x00,0x2D,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0x5A,0x04,0x60,
  0xB5,0x00,0x2D,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0x53,0x04,0xAC,0x01,0x03
};

/* Class: java.util.ArrayList */
/* Method: ensureCapacityInternal */
RANGE const unsigned char java_util_ArrayList_ensureCapacityInternal[33] PROGMEM = {
  0x2A,0xB4,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0xB2,0x00,0x2D,0x22,
  /* offset: 6, -96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, EMPTY_ELEMENTDATA_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, EMPTY_ELEMENTDATA_f) << 3)) & 0xff),
  0xA6,0x00,0x0B,
  0x10,0x0A,0x1B,0xB8,0x00,0x43,0x26,0x3C,0x2A,0x1B,0xB7,0x00,0x91,0x33,0xB1,0x01,
  0x01
};

/* Class: java.util.ArrayList */
/* Method: ensureExplicitCapacity */
RANGE const unsigned char java_util_ArrayList_ensureExplicitCapacity[38] PROGMEM = {
  0x2A,0x59,0xB4,0x00,0x57,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x57,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_AbstractList_c, modCount_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x1B,0x2A,0xB4,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x64,0x9E,0x00,0x09,0x2A,0x1B,0xB7,
  0x00,0x92,0x35,0xB1,0x01,0x01
};

/* Class: java.util.ArrayList */
/* Method: grow */
RANGE const unsigned char java_util_ArrayList_grow[59] PROGMEM = {
  0x2A,0xB4,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x3D,0x1C,0x1C,0x04,0x7A,0x60,0x3E,0x1D,
  0x1B,0x64,0x9C,0x00,0x05,0x1B,0x3E,0x1D,0x12,0x00,0x23,0x64,0x9E,0x00,0x09,0x1B,
  0xB8,0x00,0x93,0x31,0x3E,0x2A,0x2A,0xB4,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0xB8,0x00,
  0x99,0x41,0xB5,0x00,0x2D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, elementData_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: java.util.ArrayList */
/* Method: hugeCapacity */
RANGE const unsigned char java_util_ArrayList_hugeCapacity[32] PROGMEM = {
  0x1A,0x9C,0x00,0x0C,0xBB,0x00,0xA2,0x59,0xB7,0x00,0x4F,0x2A,0xBF,0x1A,0x12,0x00,
  0x23,0xA4,0x00,0x09,0x12,0x00,0x09,0xA7,0x00,0x06,0x12,0x00,0x23,0xAC,0x01,0x00
};

/* Class: java.util.ArrayList */
/* Method: iterator */
RANGE const unsigned char java_util_ArrayList_iterator[13] PROGMEM = {
  0xBB,0x01,0x06,0x59,0x2A,0x01,0xB7,0x00,0x88,0x3E,0xB0,0x01,0x03
};

/* Class: java.util.ArrayList */
/* Method: size */
RANGE const unsigned char java_util_ArrayList_size[10] PROGMEM = {
  0x2A,0xB4,0x00,0x2D,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _java_util_ArrayList_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: java.util.Arrays */
/* Method: <clinit> */
RANGE const unsigned char java_util_Arrays_clinit_[23] PROGMEM = {
  0x12,0x00,0x24,0xB7,0x00,0x1D,0x69,0x9A,0x00,0x07,0x04,0xA7,0x00,0x04,0x03,0xB3,
  0x00,0x02,0x08,
  /* offset: 0, 96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, _assertionsDisabled_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, _assertionsDisabled_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: java.util.Arrays */
/* Method: <init> */
RANGE const unsigned char java_util_Arrays_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x71,0xB1,0x01,0x01
};

/* Class: java.util.Arrays */
/* Method: copyOf */
RANGE const unsigned char java_util_Arrays_copyOf[24] PROGMEM = {
  0x1B,0xBC,0x00,0x23,0x4D,0x2A,0x03,0x2C,0x03,0x2A,0xBE,0x1B,0xB8,0x00,0x44,0x6F,
  0xB8,0x00,0x71,0x80,0x2C,0xB0,0x01,0x0D
};

/* Class: java.util.Arrays */
/* Method: copyOf */
RANGE const unsigned char java_util_Arrays_copyOf_[17] PROGMEM = {
  0x2A,0x1B,0x2A,0xB7,0x00,0x49,0x72,0xB8,0x00,0x9A,0xC3,0xC0,0x01,0x20,0xB0,0x01,
  0x05
};

/* Class: java.util.Arrays */
/* Method: copyOf */
RANGE const unsigned char java_util_Arrays_copyOf__[53] PROGMEM = {
  0x2C,0x12,0x00,0x25,0xA6,0x00,0x0D,0x1B,0xBD,0x00,0x4A,0xC0,0x01,0x20,0xA7,0x00,
  0x13,0x2C,0xB7,0x00,0x1E,0x6B,0x1B,0xB8,0x00,0x80,0x82,0xC0,0x01,0x20,0xC0,0x01,
  0x20,0x4E,0x2A,0x03,0x2D,0x03,0x2A,0xBE,0x1B,0xB8,0x00,0x44,0x6F,0xB8,0x00,0x71,
  0x80,0x2D,0xB0,0x01,0x1D
};

/* Class: java.util.Arrays */
/* Method: copyOfRange */
RANGE const unsigned char java_util_Arrays_copyOfRange[75] PROGMEM = {
  0x1C,0x1B,0x64,0x3E,0x1D,0x9C,0x00,0x29,0xBB,0x01,0x1C,0x59,0xBB,0x00,0x34,0x59,
  0xB7,0x00,0x66,0x76,0x1B,0xB7,0x00,0x69,0x7C,0x12,0x00,0x26,0xB7,0x00,0x6D,0x7F,
  0x1C,0xB7,0x00,0x69,0x7C,0xB7,0x00,0x6E,0x78,0xB7,0x00,0x32,0x6E,0xBF,0x1D,0xBC,
  0x00,0x23,0x3A,0x04,0x2A,0x1B,0x19,0x04,0x03,0x2A,0xBE,0x1B,0x64,0x1D,0xB8,0x00,
  0x44,0x6F,0xB8,0x00,0x71,0x80,0x19,0x04,0xB0,0x01,0x31
};

/* Class: java.util.ConcurrentModificationException */
/* Method: <init> */
RANGE const unsigned char java_util_ConcurrentModificationException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x16,0xB1,0x01,0x01
};

/* Class: java.util.NoSuchElementException */
/* Method: <init> */
RANGE const unsigned char java_util_NoSuchElementException_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x51,0x12,0xB1,0x01,0x01
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AbsoluteTime_init_[10] PROGMEM = {
  0x2A,0x09,0x03,0xB7,0x00,0x9F,0x08,0xB1,0x01,0x01
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AbsoluteTime_init__[14] PROGMEM = {
  0x2A,0x1F,0x1D,0xB8,0x00,0xAC,0x0F,0xB7,0x00,0xAF,0x15,0xB1,0x01,0x01
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AbsoluteTime_init___[12] PROGMEM = {
  0x2A,0x1F,0x1D,0x19,0x04,0xB7,0x00,0xAF,0x15,0xB1,0x01,0x19
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AbsoluteTime_init____[30] PROGMEM = {
  0x2A,0xB7,0x00,0x9E,0x1D,0x2B,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x27,
  0xB7,0x00,0x32,0x23,0xBF,0x2A,0x2B,0xB7,0x00,0xB8,0x26,0xB1,0x01,0x03
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AbsoluteTime_init_____[11] PROGMEM = {
  0x2A,0x09,0x03,0x2B,0xB7,0x00,0xA0,0x2F,0xB1,0x01,0x03
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: add */
RANGE const unsigned char javax_realtime_AbsoluteTime_add[51] PROGMEM = {
  0x19,0x04,0xC7,0x00,0x14,0xBB,0x01,0x04,0x59,0x2A,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0xA2,0x39,0x3A,0x04,0x19,0x04,0x2A,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x1F,
  0x61,0x2A,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x60,0xB7,0x00,0xB7,0x3B,0x19,0x04,
  0xB0,0x01,0x39
};

/* Class: javax.realtime.AbsoluteTime */
/* Method: add */
RANGE const unsigned char javax_realtime_AbsoluteTime_add_[60] PROGMEM = {
  0x2B,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x28,0xB7,0x00,0x32,0x23,0xBF,
  0x2B,0xB7,0x00,0xB1,0x41,0x2A,0xB7,0x00,0xB1,0x46,0xA5,0x00,0x0F,0xBB,0x01,0x1C,
  0x59,0x12,0x00,0x29,0xB7,0x00,0x32,0x23,0xBF,0x2A,0x2B,0xB7,0x00,0xB2,0x49,0x2B,
  0xB7,0x00,0xB3,0x4D,0x2C,0xB7,0x00,0xA3,0x53,0xB0,0x01,0x0F
};

/* Class: javax.realtime.AbstractAsyncEventHandler */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AbstractAsyncEventHandler_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: javax.realtime.AperiodicParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AperiodicParameters_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0xDD,0x08,0xB1,0x01,0x01
};

/* Class: javax.realtime.AsyncEventHandler */
/* Method: <init> */
RANGE const unsigned char javax_realtime_AsyncEventHandler_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0xA5,0x08,0xB1,0x01,0x01
};

/* Class: javax.realtime.AsyncEventHandler */
/* Method: handleAsyncEvent */
RANGE const unsigned char javax_realtime_AsyncEventHandler_handleAsyncEvent[1] PROGMEM = {
  0xB1
};

/* Class: javax.realtime.AsyncEventHandler */
/* Method: run */
RANGE const unsigned char javax_realtime_AsyncEventHandler_run[17] PROGMEM = {
  0x2A,0xB6,0x00,0x08,0x00,0x02,0x00,0x74,0x01,0x75,0x00,0x4E,0x01,0x32,0xB1,0x01,
  0x01
};

/* Class: javax.realtime.BoundAsyncEventHandler */
/* Method: <init> */
RANGE const unsigned char javax_realtime_BoundAsyncEventHandler_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0xA7,0x0A,0xB1,0x01,0x01
};

/* Class: javax.realtime.Clock */
/* Method: <init> */
RANGE const unsigned char javax_realtime_Clock_init_[16] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0x2A,0x1B,0xB5,0x00,0x6C,0x08,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_Clock_c, active_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_Clock_c, active_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.realtime.Clock */
/* Method: getRealtimeClock */
RANGE const unsigned char javax_realtime_Clock_getRealtimeClock[7] PROGMEM = {
  0xB8,0x00,0xD5,0x19,0xB0,0x01,0x01
};

/* Class: javax.realtime.Clock */
/* Method: getResolution */
/* Class: javax.realtime.Clock */
/* Method: getTime */
/* Class: javax.realtime.HighResolutionTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_HighResolutionTime_init_[53] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x13,0x2A,0x1F,0x1D,0xB7,0x00,0xB5,0x16,0x9A,0x00,0x0D,0x2A,
  0x1F,0x1D,0xB7,0x00,0xB9,0x1A,0xA7,0x00,0x13,0x2A,0x1F,0xB5,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1D,0xB5,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x19,0x04,0xB5,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x19
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: compareTo */
RANGE const unsigned char javax_realtime_HighResolutionTime_compareTo[141] PROGMEM = {
  0x2B,0xC7,0x00,0x0C,0xBB,0x01,0x1C,0x59,0xB7,0x00,0x31,0x5A,0xBF,0x2A,0xB7,0x00,
  0x49,0x3B,0x2B,0xB7,0x00,0x49,0x3B,0xA5,0x00,0x0C,0xBB,0x00,0x4E,0x59,0xB7,0x00,
  0x23,0x5B,0xBF,0x2A,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,0xB1,0x4F,0xA5,
  0x00,0x0C,0xBB,0x01,0x1C,0x59,0xB7,0x00,0x31,0x5A,0xBF,0x2A,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,0xB2,0x4B,0x94,0x9C,0x00,0x07,0x02,0xAC,0x01,0x03,0x2A,
  0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,0xB2,0x4B,0x94,0x9E,0x00,0x07,0x04,
  0xAC,0x01,0x03,0x2A,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,0xB3,0x4D,0xA2,
  0x00,0x07,0x02,0xAC,0x01,0x03,0x2A,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,
  0xB3,0x4D,0xA4,0x00,0x07,0x04,0xAC,0x01,0x03,0x03,0xAC,0x01,0x03
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: getClock */
RANGE const unsigned char javax_realtime_HighResolutionTime_getClock[10] PROGMEM = {
  0x2A,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: getMilliseconds */
RANGE const unsigned char javax_realtime_HighResolutionTime_getMilliseconds[10] PROGMEM = {
  0x2A,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0xAD,0x01,0x01
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: getNanoseconds */
RANGE const unsigned char javax_realtime_HighResolutionTime_getNanoseconds[10] PROGMEM = {
  0x2A,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: hashCode */
RANGE const unsigned char javax_realtime_HighResolutionTime_hashCode[47] PROGMEM = {
  0x10,0x1F,0x3C,0x04,0x3D,0x10,0x1F,0x1C,0x68,0x2A,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x20,0x7D,0x83,0x88,0x60,0x3D,0x10,0x1F,
  0x1C,0x68,0x2A,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x60,0x3D,0x1C,0xAC,0x01,0x01
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: isNormalized */
RANGE const unsigned char javax_realtime_HighResolutionTime_isNormalized[42] PROGMEM = {
  0x1F,0x09,0x94,0x9B,0x00,0x0E,0x1D,0x9B,0x00,0x0A,0x1D,0x12,0x00,0x2A,0xA1,0x00,
  0x18,0x1F,0x09,0x94,0x9D,0x00,0x0E,0x12,0x00,0x2B,0x1D,0xA2,0x00,0x07,0x1D,0x9E,
  0x00,0x07,0x03,0xAC,0x01,0x01,0x04,0xAC,0x01,0x01
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: set */
RANGE const unsigned char javax_realtime_HighResolutionTime_set[19] PROGMEM = {
  0x2A,0x1F,0xB5,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB1,0x01,0x01
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: set */
RANGE const unsigned char javax_realtime_HighResolutionTime_set_[39] PROGMEM = {
  0x2A,0x1F,0x1D,0xB7,0x00,0xB5,0x16,0x9A,0x00,0x0D,0x2A,0x1F,0x1D,0xB7,0x00,0xB9,
  0x1A,0xA7,0x00,0x13,0x2A,0x1F,0xB5,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1D,0xB5,0x00,
  0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: set */
RANGE const unsigned char javax_realtime_HighResolutionTime_set__[86] PROGMEM = {
  0x2B,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x27,0xB7,0x00,0x32,0x38,0xBF,
  0x2A,0xB7,0x00,0x49,0x3B,0x2B,0xB7,0x00,0x49,0x3B,0xA5,0x00,0x0F,0xBB,0x00,0x4E,
  0x59,0x12,0x00,0x2C,0xB7,0x00,0x24,0x43,0xBF,0x2A,0x2B,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,
  0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x68,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.realtime.HighResolutionTime */
/* Method: setNormalized */
/* setNormalized
 * param : long, int
 * return: void
 */
int16 javax_realtime_HighResolutionTime_setNormalized(int32 *fp)
{  
   int32* sp;
   int32 i_val4;
   int32 i_val3;
   int32 i_val2;
   int32 i_val1;
   int32 i_val0;
   int32 res_int32;
   int32 lsb_int32;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int32 msb_int32;
   unsigned char* cobj;
   int32 l_res;
    int32 this;
    int32 ms;
    int32 lv_2 = 0;
    int32 ns;
   this = (int32)(*(fp + 0));
   ms = (int32)(*(fp + 1));
   lv_2 = (int32)(*(fp + 2));
   ns = (int32)(*(fp + 3));
   sp = &fp[6]; /* make room for local VM state on the stack */
   i_val4 = this;
   i_val3 = ms;
   i_val2 = lv_2;
   i_val1 = (int32)ns;
   i_val0 = 1000000;
      res_int32 = i_val0;
      lsb_int32 = i_val1;
      if (res_int32 == 0) {
         pc = 6;
         goto throwArithmeticException;
      }
      res_int32 = idiv(lsb_int32, res_int32);
   i_val1 = res_int32;
      lsb_int32 = i_val1;
      if (lsb_int32 < 0) {
         msb_int32 = -1;
      } else {
         msb_int32 = 0;
      }
   i_val1 = msb_int32;
   i_val0 = lsb_int32;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   handleLongOperator(97, sp);
   sp = sp - 2;
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
      msb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f = lsb_int32;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f = msb_int32;
   i_val4 = this;
   i_val3 = (int32)ns;
   i_val2 = 1000000;
      res_int32 = i_val2;
      lsb_int32 = i_val3;
      if (res_int32 == 0) {
         pc = 20;
         goto throwArithmeticException;
      }
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
      res_int32 = imod(res_int32, lsb_int32);
#else
      res_int32 = lsb_int32 % res_int32;
#endif
   i_val3 = res_int32;
      lsb_int32 = i_val3;
cobj = (unsigned char *) (pointer)i_val4;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f = lsb_int32;
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f;
   i_val3 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f;
   i_val2 = 0;
   i_val1 = 0;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
      l_res = handleLCMP(sp);
      sp -= 4;
      *sp++ = l_res;
   sp--;
      msb_int32 = (int32)(*sp);
      if (msb_int32 <= 0) {
         goto L86;
      }
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f;
      if (i_val4 >= 0) {
         goto L86;
      }
   i_val4 = this;
   i_val3 = i_val4;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f;
   i_val2 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f;
   i_val1 = 0;
   i_val0 = 1;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   handleLongOperator(101, sp);
   sp = sp - 2;
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
      msb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f = lsb_int32;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f = msb_int32;
   i_val4 = this;
   i_val3 = i_val4;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f;
   i_val2 = 1000000;
      msb_int32 = i_val2;
      lsb_int32 = i_val3;
      lsb_int32 += msb_int32;
   i_val3 = lsb_int32;
      lsb_int32 = i_val3;
cobj = (unsigned char *) (pointer)i_val4;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f = lsb_int32;
   goto L142;
   L86:
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f;
   i_val3 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f;
   i_val2 = 0;
   i_val1 = 0;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
      l_res = handleLCMP(sp);
      sp -= 4;
      *sp++ = l_res;
   sp--;
      msb_int32 = (int32)(*sp);
      if (msb_int32 >= 0) {
         goto L142;
      }
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f;
      if (i_val4 <= 0) {
         goto L142;
      }
   i_val4 = this;
   i_val3 = i_val4;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f;
   i_val2 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f;
   i_val1 = 0;
   i_val0 = 1;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   handleLongOperator(97, sp);
   sp = sp - 2;
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
      msb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> millis_f = lsb_int32;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> lsbmillis_f = msb_int32;
   i_val4 = this;
   i_val3 = i_val4;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f;
   i_val2 = 1000000;
      msb_int32 = i_val2;
      lsb_int32 = i_val3;
      lsb_int32 -= msb_int32;
   i_val3 = lsb_int32;
      lsb_int32 = i_val3;
cobj = (unsigned char *) (pointer)i_val4;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> nanos_f = lsb_int32;
   L142:
   return -1;
   throwArithmeticException:
      excep = initializeException(sp, JAVA_LANG_ARITHMETICEXCEPTION, JAVA_LANG_ARITHMETICEXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[185], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.realtime.HighResolutionTime */
/* Method: toString */
RANGE const unsigned char javax_realtime_HighResolutionTime_toString[54] PROGMEM = {
  0xBB,0x00,0x34,0x59,0x12,0x00,0x2D,0xB7,0x00,0x67,0x62,0x2A,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x6A,0x63,0x12,0x00,0x2E,0xB7,0x00,0x6D,0x69,0x2A,0xB4,0x00,
  0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x69,0x6C,0x12,0x00,0x2F,0xB7,0x00,0x6D,0x69,0xB7,
  0x00,0x6E,0x71,0xB0,0x01,0x03
};

/* Class: javax.realtime.MemoryArea */
/* Method: <init> */
/* <init>
 * param : int, int, javax.realtime.MemoryArea, java.lang.String
 * return: void
 */
int16 javax_realtime_MemoryArea_init_(int32 *fp,     int32 this,     int32 initialSize,     int32 reservedSize,     int32 backingStoreProvider,     int32 label)
{  
   int32* sp;
   int32 i_val5;
   int16 rval_m_1;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int32 i_val4;
   int16 rval_m_13;
   int32 msb_int32;
   int32 lsb_int32;
   int32 i_val3;
   int32 i_val2;
   int16 rval_m_56;
   int16 rval_m_79;
   const ConstantInfo* constant_;
   int16 rval_m_89;
   int16 rval_m_104;
   int16 rval_m_109;
   Object* i_obj;
   int16 rval_m_116;
   int16 rval_m_121;
   int16 rval_m_128;
   int16 rval_m_134;
   int16 rval_m_141;
   int16 rval_m_147;
   int16 rval_m_151;
   int16 rval_m_155;
   Object* ex_ception;
    int32 base;
    int32 endOfAvailableSpace;
   sp = &fp[9]; /* make room for local VM state on the stack */
   i_val5 = this;
   *sp = (int32)i_val5;
   sp++;
   sp -= 1;
   rval_m_1 = enterMethodInterpreter(JAVA_LANG_OBJECT_INIT_, sp);
   if (rval_m_1 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_1;
   }
   i_val5 = backingStoreProvider;
cobj = (unsigned char *) (pointer)i_val5;
      if (cobj == 0) {
         pc = 6;
         goto throwNullPointer;
      }
   i_val5 = ((struct _javax_realtime_MemoryArea_c *)HEAP_REF(cobj, unsigned char*)) -> reservedEnd_f;
   i_val4 = backingStoreProvider;
   *sp = (int32)i_val5;
   sp++;
   *sp = (int32)i_val4;
   sp++;
   sp -= 1;
   rval_m_13 = enterMethodInterpreter(JAVAX_REALTIME_MEMORYAREA_GETREMAININGBACKINGSTORESIZE, sp);
   if (rval_m_13 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_13;
   }
   sp--;
      msb_int32 = (int32)(*sp);
   sp--;
      lsb_int32 = (int32)(*sp);
      lsb_int32 -= msb_int32;
   i_val5 = lsb_int32;
   base = i_val5;
   i_val5 = backingStoreProvider;
cobj = (unsigned char *) (pointer)i_val5;
   i_val5 = ((struct _javax_realtime_MemoryArea_c *)HEAP_REF(cobj, unsigned char*)) -> reservedEnd_f;
   endOfAvailableSpace = i_val5;
   i_val5 = (int32)base;
   i_val4 = (int32)reservedSize;
      msb_int32 = i_val4;
      lsb_int32 = i_val5;
      lsb_int32 += msb_int32;
   i_val5 = lsb_int32;
   i_val4 = (int32)endOfAvailableSpace;
      if (i_val5 > i_val4) {
         goto L86;
      }
   i_val5 = this;
   i_val4 = backingStoreProvider;
      lsb_int32 = i_val4;
cobj = (unsigned char *) (pointer)i_val5;
      ((struct _javax_realtime_MemoryArea_c *)HEAP_REF(cobj, unsigned char*)) -> backingStoreProvider_f = lsb_int32;
   i_val5 = this;
   *sp = (int32)i_val5;
   sp++;
   if (handleNewClassIndex(sp, 123) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val5 = *(sp - 1);
   i_val4 = (int32)base;
   i_val3 = (int32)initialSize;
   i_val2 = label;
   rval_m_56 = vm_Memory_init__(sp, i_val5, i_val4, i_val3, i_val2);
   if (rval_m_56 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_56;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _javax_realtime_MemoryArea_c *)HEAP_REF(cobj, unsigned char*)) -> delegate_f = lsb_int32;
   i_val5 = this;
   i_val4 = (int32)base;
   i_val3 = (int32)reservedSize;
      msb_int32 = i_val3;
      lsb_int32 = i_val4;
      lsb_int32 += msb_int32;
   i_val4 = lsb_int32;
      lsb_int32 = i_val4;
cobj = (unsigned char *) (pointer)i_val5;
      ((struct _javax_realtime_MemoryArea_c *)HEAP_REF(cobj, unsigned char*)) -> reservedEnd_f = lsb_int32;
   i_val5 = backingStoreProvider;
   i_val4 = this;
   *sp = (int32)i_val5;
   sp++;
   *sp = (int32)i_val4;
   sp++;
   sp -= 2;
   rval_m_79 = enterMethodInterpreter(JAVAX_REALTIME_MEMORYAREA_ADDCONTAINEDMEMORY, sp);
   if (rval_m_79 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_79;
   }
   goto L160;
   L86:
   constant_ = &constants[48];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val5 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val5;
   sp++;
   sp -= 1;
   rval_m_89 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_89 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_89;
   }
   if (handleNewClassIndex(sp, 81) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val5 = *(sp - 1);
   *sp = (int32)i_val5;
   sp++;
   if (handleNewClassIndex(sp, 26) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val5 = *(sp - 1);
   constant_ = &constants[49];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val4 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val5;
   sp++;
   *sp = (int32)i_val4;
   sp++;
   sp -= 2;
   rval_m_104 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_INIT__, sp);
   if (rval_m_104 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_104;
   }
   i_val5 = (int32)initialSize;
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   rval_m_109 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND__, sp);
   if (rval_m_109 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_109;
   }
   constant_ = &constants[50];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val5 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 116;
         goto throwNullPointer;
      }
   rval_m_116 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND______, sp);
   if (rval_m_116 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_116;
   }
   i_val5 = (int32)reservedSize;
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 121;
         goto throwNullPointer;
      }
   rval_m_121 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND__, sp);
   if (rval_m_121 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_121;
   }
   constant_ = &constants[51];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val5 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 128;
         goto throwNullPointer;
      }
   rval_m_128 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND______, sp);
   if (rval_m_128 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_128;
   }
   i_val5 = (int32)base;
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 134;
         goto throwNullPointer;
      }
   rval_m_134 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND__, sp);
   if (rval_m_134 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_134;
   }
   constant_ = &constants[52];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val5 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 141;
         goto throwNullPointer;
      }
   rval_m_141 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND______, sp);
   if (rval_m_141 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_141;
   }
   i_val5 = (int32)endOfAvailableSpace;
   *sp = (int32)i_val5;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 147;
         goto throwNullPointer;
      }
   rval_m_147 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND__, sp);
   if (rval_m_147 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_147;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 151;
         goto throwNullPointer;
      }
   rval_m_151 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_TOSTRING, sp);
   if (rval_m_151 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_151;
   }
   sp -= 2;
   rval_m_155 = enterMethodInterpreter(JAVA_LANG_OUTOFMEMORYERROR_INIT__, sp);
   if (rval_m_155 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_155;
   }
   ex_ception = (Object*) (pointer) *(sp - 1);
   excep = getClassIndex(ex_ception);
   pc = 159;
   sp--;
   goto throwIt;
   L160:
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[187], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.realtime.MemoryArea */
/* Method: <init> */
RANGE const unsigned char javax_realtime_MemoryArea_init__[40] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x12,0x2A,0x2B,0xB5,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB7,
  0x01,0xE2,0x1A,0x2B,0xB7,0x01,0xE7,0x20,0x60,0xB5,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,
  0x03,0xB7,0x01,0xEA,0x25,0xB1,0x01,0x03
};

/* Class: javax.realtime.MemoryArea */
/* Method: addContainedMemory */
RANGE const unsigned char javax_realtime_MemoryArea_addContainedMemory[56] PROGMEM = {
  0x2B,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,
  0xB5,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x8B,0x20,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, maxUsage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, maxUsage_f) - sizeof(Object)) << 3)) & 0xff),
  0xA4,0x00,0x11,0x2A,0x2B,0xB4,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,
  0x00,0x8B,0x20,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, maxUsage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, maxUsage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.realtime.MemoryArea */
/* Method: getNamedMemoryArea */
RANGE const unsigned char javax_realtime_MemoryArea_getNamedMemoryArea[14] PROGMEM = {
  0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0x2A,0xB8,0x00,0xBF,0xA5,0xB0,0x01,0x03
};

/* Class: javax.realtime.MemoryArea */
/* Method: getNamedMemoryArea */
RANGE const unsigned char javax_realtime_MemoryArea_getNamedMemoryArea_[65] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xE5,0x6B,0x2B,0xB7,0x00,0x5B,0xA9,
  0x99,0x00,0x07,0x2A,0xB0,0x01,0x13,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0xA7,
  0x00,0x1A,0x2C,0x2B,0xB8,0x00,0xBF,0xA5,0x4E,0x2D,0xC6,0x00,0x07,0x2D,0xB0,0x01,
  0x1F,0x2C,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x2C,0xC7,0xFF,0xE8,0x01,0xB0,0x01,
  0x1F
};

/* Class: javax.realtime.MemoryArea */
/* Method: getRemainingBackingstoreSize */
RANGE const unsigned char javax_realtime_MemoryArea_getRemainingBackingstoreSize[82] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xE2,0x1A,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xE7,0x20,0x60,0x3C,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,
  0xA7,0x00,0x22,0x1B,0x2C,0xB4,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0xA4,0x00,0x07,0x1B,0xA7,
  0x00,0x0A,0x2C,0xB4,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0x3C,0x2C,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x2C,0xC7,0xFF,0xE0,0x2A,0xB4,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x64,0xAC,
  0x01,0x05
};

/* Class: javax.realtime.MemoryArea */
/* Method: getRemainingMemorySize */
RANGE const unsigned char javax_realtime_MemoryArea_getRemainingMemorySize[13] PROGMEM = {
  0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0xB7,0x00,0xC0,0x2C,0xAC,0x01,0x00
};

/* Class: javax.realtime.MemoryArea */
/* Method: memoryConsumed */
RANGE const unsigned char javax_realtime_MemoryArea_memoryConsumed[15] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xE0,0x74,0x85,0xAD,0x01,0x01
};

/* Class: javax.realtime.MemoryArea */
/* Method: removeContainedMemory */
RANGE const unsigned char javax_realtime_MemoryArea_removeContainedMemory[100] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xA6,0x00,0x1A,0x2A,0x2A,0xB4,0x00,0x8B,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,
  0x00,0x42,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0xA7,0x00,0x2D,0x2C,0xB4,0x00,
  0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xA6,0x00,0x1A,0x2C,0x2C,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x07,0x2C,
  0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x2C,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, nextContainedMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0xFF,
  0xCF,0xB1,0x01,0x07
};

/* Class: javax.realtime.MemoryArea */
/* Method: removeMemArea */
RANGE const unsigned char javax_realtime_MemoryArea_removeMemArea[25] PROGMEM = {
  0x2A,0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0xA5,0x00,0x0F,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, backingStoreProvider_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, backingStoreProvider_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0xC3,0x8E,0xB1,0x01,0x01
};

/* Class: javax.realtime.MemoryArea */
/* Method: resizeMemArea */
RANGE const unsigned char javax_realtime_MemoryArea_resizeMemArea[74] PROGMEM = {
  0x2A,0xB7,0x00,0xC2,0x97,0x1F,0x94,0x9C,0x00,0x37,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xE2,0x1A,0x85,0x1F,0x61,0x2A,0xB4,0x00,0x8B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, reservedEnd_f) - sizeof(Object)) << 3)) & 0xff),
  0x85,
  0x94,0x9C,0x00,0x1D,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, containedMemories_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x13,0x2A,0xB4,
  0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0x1F,0x88,0xB7,0x01,0xEA,0x25,0xB1,0x01,0x01,0xBB,0x00,
  0xA2,0x59,0x12,0x00,0x35,0xB7,0x00,0x50,0x59,0xBF
};

/* Class: javax.realtime.MemoryArea */
/* Method: toString */
RANGE const unsigned char javax_realtime_MemoryArea_toString[14] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xED,0xA1,0xB0,0x01,0x03
};

/* Class: javax.realtime.NoHeapRealtimeThread */
/* Method: <init> */
RANGE const unsigned char javax_realtime_NoHeapRealtimeThread_init_[10] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x00,0xD7,0x08,0xB1,0x01,0x07
};

/* Class: javax.realtime.PeriodicParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_PeriodicParameters_init_[12] PROGMEM = {
  0x2A,0x2B,0x2C,0x01,0x01,0xB7,0x00,0xC9,0x0B,0xB1,0x01,0x07
};

/* Class: javax.realtime.PeriodicParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_PeriodicParameters_init__[207] PROGMEM = {
  0x2A,0x2D,0xC7,0x00,0x07,0x2C,0xA7,0x00,0x04,0x2D,0x19,0x04,0xB7,0x00,0xDE,0x17,
  0x2B,0xC7,0x00,0x15,0x2A,0xBB,0x00,0xF8,0x59,0xB7,0x00,0xD8,0x1C,0xB5,0x00,0x52,
  0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, start_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, start_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x13,0x2A,0xBB,0x00,0xF8,0x59,0x2B,0xB7,0x00,0xDC,0x21,
  0xB5,0x00,0x52,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, start_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, start_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xC6,0x00,0x36,0x2C,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x09,0x94,0x9B,0x00,0x2A,0x2C,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x09,0x94,0x9A,
  0x00,0x0D,0x2C,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x99,0x00,0x14,0x2B,0xB4,0x00,0x68,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xA5,0x00,0x0F,0xBB,0x01,0x1C,
  0x59,0x12,0x00,0x36,0xB7,0x00,0x32,0x34,0xBF,0x2D,0xC6,0x00,0x42,0x2D,0xB4,0x00,
  0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x09,0x94,0x9B,0x00,0x2A,0x2D,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x09,0x94,0x9A,0x00,0x0D,0x2D,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x99,0x00,0x14,0x2C,
  0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2D,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xA5,0x00,0x0F,
  0xBB,0x01,0x1C,0x59,0x12,0x00,0x37,0xB7,0x00,0x32,0x34,0xBF,0x2A,0xBB,0x00,0xF8,
  0x59,0x2C,0xB7,0x00,0xDC,0x21,0xB5,0x00,0x52,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, period_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, period_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x1F
};

/* Class: javax.realtime.PeriodicParameters */
/* Method: getPeriod */
RANGE const unsigned char javax_realtime_PeriodicParameters_getPeriod[10] PROGMEM = {
  0x2A,0xB4,0x00,0x52,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, period_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, period_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.realtime.PeriodicParameters */
/* Method: getStart */
RANGE const unsigned char javax_realtime_PeriodicParameters_getStart[10] PROGMEM = {
  0x2A,0xB4,0x00,0x52,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, start_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PeriodicParameters_c, start_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.realtime.PriorityParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_PriorityParameters_init_[16] PROGMEM = {
  0x2A,0xB7,0x00,0xE0,0x0A,0x2A,0x1B,0xB5,0x00,0x3C,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PriorityParameters_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PriorityParameters_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.realtime.PriorityParameters */
/* Method: getPriority */
RANGE const unsigned char javax_realtime_PriorityParameters_getPriority[10] PROGMEM = {
  0x2A,0xB4,0x00,0x3C,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PriorityParameters_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PriorityParameters_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: javax.realtime.PriorityParameters */
/* Method: setPriority */
RANGE const unsigned char javax_realtime_PriorityParameters_setPriority[11] PROGMEM = {
  0x2A,0x1B,0xB5,0x00,0x3C,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_PriorityParameters_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_PriorityParameters_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.realtime.PriorityScheduler */
/* Method: <init> */
RANGE const unsigned char javax_realtime_PriorityScheduler_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0xDF,0x08,0xB1,0x01,0x01
};

/* Class: javax.realtime.RealtimeClock */
/* Method: <clinit> */
RANGE const unsigned char javax_realtime_RealtimeClock_clinit_[12] PROGMEM = {
  0xB8,0x02,0x16,0x0E,0xB3,0x00,0x20,0x22,
  /* offset: 5, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, nativeClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, nativeClock_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.realtime.RealtimeClock */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RealtimeClock_init_[9] PROGMEM = {
  0x2A,0x04,0xB7,0x00,0xAB,0x19,0xB1,0x01,0x01
};

/* Class: javax.realtime.RealtimeClock */
/* Method: getResolution */
RANGE const unsigned char javax_realtime_RealtimeClock_getResolution[17] PROGMEM = {
  0xBB,0x00,0xF8,0x59,0xB2,0x00,0x20,0x22,
  /* offset: 5, -128*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) & 0xff),
  0xB7,0x00,0xDC,0x3C,0xB0,0x01,
  0x03
};

/* Class: javax.realtime.RealtimeClock */
/* Method: getResolution */
RANGE const unsigned char javax_realtime_RealtimeClock_getResolution_[54] PROGMEM = {
  0x2B,0xC7,0x00,0x0B,0x2A,0xB7,0x00,0xD2,0x40,0xB0,0x01,0x07,0x2B,0xB2,0x00,0x20,
  0x22,
  /* offset: 5, -128*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) & 0xff),
  0xB7,0x00,0xB2,0x42,0xB2,0x00,0x20,0x22,
  /* offset: 5, -128*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) & 0xff),
  0xB7,0x00,0xB3,
  0x46,0xB7,0x00,0xB7,0x49,0x2B,0xB2,0x00,0x20,0x22,
  /* offset: 5, 96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) & 0xff),
  0xB5,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB0,0x01,0x07
};

/* Class: javax.realtime.RealtimeClock */
/* Method: getTime */
/* getTime
 * param : javax.realtime.AbsoluteTime
 * return: javax.realtime.AbsoluteTime
 */
int16 javax_realtime_RealtimeClock_getTime(int32 *fp)
{  
   int32* sp;
   int32 i_val3;
   int16 rval_m_8;
   int32 i_val2;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_20;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_35;
   int16 rval_m_40;
   int16 rval_m_44;
   int16 rval_m_49;
   unsigned char* cobj;
   int32 lsb_int32;
    int32 dest;
   dest = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val3 = dest;
      if (i_val3 != 0) {
         goto L13;
      }
   if (handleNewClassIndex(sp, 130) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   *sp = (int32)i_val3;
   sp++;
   sp -= 1;
   rval_m_8 = enterMethodInterpreter(JAVAX_REALTIME_ABSOLUTETIME_INIT_, sp);
   if (rval_m_8 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_8;
   }
   sp--;
   dest = (int32)(*sp);
   L13:
   i_val3 = ((struct _staticClassFields_c *)classData) -> nativeClock_f;
   i_val2 = dest;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 20;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 113:
            methodIndex = 4;
            continue;
          case 56:
            methodIndex = 524;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_20 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_20 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_20;
      }
   i_val3 = dest;
   i_val2 = dest;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 35;
         goto throwNullPointer;
      }
   rval_m_35 = enterMethodInterpreter(JAVAX_REALTIME_HIGHRESOLUTIONTIME_GETMILLISECONDS, sp);
   if (rval_m_35 == -1) {
      sp += 2;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_35;
   }
   i_val3 = dest;
   *sp = (int32)i_val3;
   sp++;
   sp -= 1;
   rval_m_40 = enterMethodInterpreter(JAVAX_REALTIME_HIGHRESOLUTIONTIME_GETNANOSECONDS, sp);
   if (rval_m_40 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_40;
   }
   sp -= 4;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 44;
         goto throwNullPointer;
      }
   rval_m_44 = enterMethodInterpreter(JAVAX_REALTIME_HIGHRESOLUTIONTIME_SET_, sp);
   if (rval_m_44 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_44;
   }
   i_val3 = dest;
   *sp = (int32)i_val3;
   sp++;
   sp -= 0;
   rval_m_49 = enterMethodInterpreter(JAVAX_REALTIME_CLOCK_GETREALTIMECLOCK, sp);
   if (rval_m_49 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_49;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _javax_realtime_AbsoluteTime_c *)HEAP_REF(cobj, unsigned char*)) -> clock_f = lsb_int32;
   i_val3 = dest;
   *((int32*)fp) = i_val3;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[212], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.realtime.RealtimeClock */
/* Method: instance */
RANGE const unsigned char javax_realtime_RealtimeClock_instance[42] PROGMEM = {
  0xB2,0x00,0x20,0x22,
  /* offset: 5, 96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) & 0xff),
  0xC7,0x00,0x1B,0xBB,0x00,0x40,0x59,0xB7,0x00,0xD1,
  0x33,0xB3,0x00,0x20,0x22,
  /* offset: 5, 96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) & 0xff),
  0xB8,0x00,0xD6,0x35,0xB3,0x00,0x20,0x22,
  /* offset: 5, -128*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, resolution_f) << 3)) & 0xff),
  0xB2,0x00,0x20,0x22,
  /* offset: 5, 96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) & 0xff),
  0xB0,0x01,0x01
};

/* Class: javax.realtime.RealtimeClock */
/* Method: setResolution */
RANGE const unsigned char javax_realtime_RealtimeClock_setResolution[52] PROGMEM = {
  0xB2,0x00,0x20,0x22,
  /* offset: 5, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, nativeClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, nativeClock_f) << 3)) & 0xff),
  0xB6,0x01,0x08,0x00,0x02,0x00,0x71,0x00,0x05,0x00,
  0x38,0x02,0x0D,0x3B,0x1A,0x12,0x00,0x2A,0x6C,0x85,0x40,0x1A,0x12,0x00,0x2A,0x70,
  0x3E,0xBB,0x00,0xF8,0x59,0x1F,0x1D,0xB2,0x00,0x20,0x22,
  /* offset: 5, 96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, rtClock_f) << 3)) & 0xff),
  0xB7,0x00,0xDA,
  0x29,0xB0,0x01,0x10
};

/* Class: javax.realtime.RealtimeThread */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RealtimeThread_init_[40] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0E,0x2B,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x38,
  0xB7,0x00,0x32,0x15,0xBF,0x2A,0x2B,0xB5,0x00,0x19,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2C,0xB5,
  0x00,0x19,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, logic_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, logic_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x07
};

/* Class: javax.realtime.RelativeTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RelativeTime_init_[10] PROGMEM = {
  0x2A,0x09,0x03,0xB7,0x00,0xD9,0x08,0xB1,0x01,0x01
};

/* Class: javax.realtime.RelativeTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RelativeTime_init__[14] PROGMEM = {
  0x2A,0x1F,0x1D,0xB8,0x00,0xAC,0x0F,0xB7,0x00,0xDA,0x15,0xB1,0x01,0x01
};

/* Class: javax.realtime.RelativeTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RelativeTime_init___[24] PROGMEM = {
  0x2A,0x1F,0x1D,0x19,0x04,0xC7,0x00,0x0A,0xB8,0x00,0xAC,0x0F,0xA7,0x00,0x05,0x19,
  0x04,0xB7,0x00,0xAF,0x20,0xB1,0x01,0x19
};

/* Class: javax.realtime.RelativeTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RelativeTime_init____[22] PROGMEM = {
  0x2A,0x09,0x03,0x2B,0xC7,0x00,0x0A,0xB8,0x00,0xAC,0x0F,0xA7,0x00,0x04,0x2B,0xB7,
  0x00,0xDA,0x15,0xB1,0x01,0x03
};

/* Class: javax.realtime.RelativeTime */
/* Method: <init> */
RANGE const unsigned char javax_realtime_RelativeTime_init_____[66] PROGMEM = {
  0x2A,0xB7,0x00,0xD8,0x22,0x2B,0xC6,0x00,0x30,0x2A,0x2B,0xB4,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x68,0x40,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, millis_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB4,0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,
  0x00,0x68,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, nanos_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB4,0x00,0x68,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x68,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_AbsoluteTime_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x0C,0xBB,0x01,0x1C,0x59,0xB7,0x00,0x31,0x2C,0xBF,0xB1,
  0x01,0x03
};

/* Class: javax.realtime.ReleaseParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_ReleaseParameters_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0E,0xB1,0x01,0x01
};

/* Class: javax.realtime.ReleaseParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_ReleaseParameters_init__[40] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0E,0x2A,0x2B,0xC7,0x00,0x07,0x01,0xA7,0x00,0x0C,0xBB,0x00,
  0xF8,0x59,0x2B,0xB7,0x00,0xDC,0x1C,0xB5,0x00,0x78,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_ReleaseParameters_c, deadline_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_ReleaseParameters_c, deadline_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2C,0xB5,
  0x00,0x78,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_ReleaseParameters_c, missHandler_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_ReleaseParameters_c, missHandler_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x07
};

/* Class: javax.realtime.Scheduler */
/* Method: <init> */
RANGE const unsigned char javax_realtime_Scheduler_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x08,0xB1,0x01,0x01
};

/* Class: javax.realtime.SchedulingParameters */
/* Method: <init> */
RANGE const unsigned char javax_realtime_SchedulingParameters_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_init_[21] PROGMEM = {
  0x2A,0xB7,0x00,0xDF,0x1D,0x11,0x08,0x00,0xBC,0x00,0x2B,0x4C,0x2A,0x2B,0xB8,0x01,
  0xC5,0x1E,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: getCurrentProcess */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_getCurrentProcess[10] PROGMEM = {
  0x2A,0xB4,0x00,0x50,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: getDefaultMonitor */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_getDefaultMonitor[4] PROGMEM = {
  0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: getNextProcess */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_getNextProcess[87] PROGMEM = {
  0xB8,0x00,0xE5,0x29,0xB7,0x00,0xE2,0x2B,0x4C,0x2B,0xB7,0x01,0x80,0x2F,0xC1,0x00,
  0x9C,0x99,0x00,0x3C,0x2B,0xB7,0x01,0x80,0x2F,0xC0,0x00,0x9C,0xB4,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x06,0xA0,0x00,0x29,0x2B,0xB7,0x01,0x80,0x2F,0xB9,0x00,0x01,0x00,
  0x13,0x03,0x00,0x8A,0x00,0xF1,0x00,0x4E,0x01,0x30,0x00,0x0C,0x01,0x11,0xB8,0x00,
  0xE5,0x29,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xEA,0x43,0x2B,0xB4,0x00,
  0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x07
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: instance */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_instance[32] PROGMEM = {
  0xB2,0x00,0x50,0x22,
  /* offset: 12, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f) << 3)) & 0xff),
  0xC7,0x00,0x11,0xBB,0x00,0xA0,0x59,0xB7,0x00,0xE1,
  0x16,0xB3,0x00,0x50,0x22,
  /* offset: 12, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f) << 3)) & 0xff),
  0xB2,0x00,0x50,0x22,
  /* offset: 12, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f) << 3)) & 0xff),
  0xB0,0x01,0x01
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: notify */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_notify[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: notifyAll */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_notifyAll[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: processStart */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_processStart[54] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0x4C,0x2A,0xBB,0x00,0x24,0x59,0x01,0x01,0xB7,0x02,
  0x03,0x4E,0xB5,0x00,0x50,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0xC6,0x53,0x2B,0xB7,0x01,
  0xC3,0x56,0x2B,0x2A,0xB4,0x00,0x50,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xC9,0x59,0x2B,0xB7,
  0x01,0xCA,0x5C,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: start */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_start[28] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x50,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, seq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, seq_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB8,0x01,0x03,0x6A,0xB5,0x00,
  0x50,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_CyclicScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0xE8,0x72,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: stop */
/* stop
 * param : vm.Process
 * return: void
 */
int16 javax_safetycritical_CyclicScheduler_stop(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int32 i_val0;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_8;
    int32 this;
    int32 current;
   this = (int32)(*(fp + 0));
   current = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val1 = current;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_CyclicScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> mainProcess_f;
      if (i_val1 == 0) {
         pc = 8;
         goto throwNullPointer;
      }
   rval_m_8 = vm_Process_transferTo(sp, i_val1, i_val0);
   if (rval_m_8 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_8;
   }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[234], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.CyclicScheduler */
/* Method: wait */
RANGE const unsigned char javax_safetycritical_CyclicScheduler_wait[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.Launcher */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_Launcher_init_[96] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x11,0x2A,0x2B,0xB5,0x00,0x09,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Launcher_c, app_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Launcher_c, app_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0xB3,0x00,
  0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0xB2,0x00,0x43,0x20,
  /* offset: 8, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, OVERALL_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, OVERALL_BACKING_STORE_f) << 3)) & 0xff),
  0xB8,0x00,0xF9,0x1D,0xB2,0x00,
  0x7B,0x08,
  /* offset: 16, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, memoryAreaTrackingEnabled_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, memoryAreaTrackingEnabled_f) << 3)) & 0xff),
  0x99,0x00,0x1F,0xBB,0x00,0xB0,0xB2,0x00,0x43,0x20,
  /* offset: 10, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, MEMORY_TRACKER_AREA_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, MEMORY_TRACKER_AREA_SIZE_f) << 3)) & 0xff),
  
  0xB2,0x00,0x43,0x20,
  /* offset: 10, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, MEMORY_TRACKER_AREA_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, MEMORY_TRACKER_AREA_SIZE_f) << 3)) & 0xff),
  0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0x12,0x00,0x39,0xB7,
  0x01,0x65,0x36,0xBB,0x00,0xC6,0x59,0xB2,0x00,0x43,0x20,
  /* offset: 8, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IMMORTAL_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IMMORTAL_MEM_f) << 3)) & 0xff),
  0xB7,0x00,0xF5,
  0x3E,0x4E,0x2D,0x2A,0xB7,0x00,0xFB,0x40,0x2D,0xB7,0x00,0xFE,0x44,0xB1,0x01,0x0B
};

/* Class: javax.safetycritical.Launcher */
/* Method: run */
RANGE const unsigned char javax_safetycritical_Launcher_run[25] PROGMEM = {
  0xB2,0x00,0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x9A,0x00,0x0B,0x2A,0xB7,0x00,0xEE,0x52,0xA7,0x00,
  0x08,0x2A,0xB7,0x00,0xEF,0x55,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.Launcher */
/* Method: startLevel0 */
RANGE const unsigned char javax_safetycritical_Launcher_startLevel0[30] PROGMEM = {
  0x2A,0xB4,0x00,0x09,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Launcher_c, app_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Launcher_c, app_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,0x00,0x00,0x0E,0x01,0x00,0x44,0x01,
  0xA8,0x4C,0xB8,0x00,0xE5,0x5C,0x2B,0xB7,0x00,0xE9,0x62,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.Launcher */
/* Method: startLevel1_2 */
RANGE const unsigned char javax_safetycritical_Launcher_startLevel1_2[43] PROGMEM = {
  0xB8,0x01,0x58,0x69,0x4C,0x2B,0xB8,0x01,0x7F,0x6E,0xB7,0x01,0x57,0x74,0x2A,0xB4,
  0x00,0x09,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Launcher_c, app_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Launcher_c, app_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,0x00,0x00,0x0E,0x01,0x00,0x44,0x01,0xA8,0x57,
  0xB8,0x01,0x58,0x69,0xB7,0x01,0x5C,0x78,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ManagedEventHandler */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ManagedEventHandler_init_[233] PROGMEM = {
  0x2A,0xB7,0x00,0xAA,0x18,0x2A,0x01,0xB5,0x00,0x8A,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xC7,0x00,
  0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x38,0xB7,0x00,0x32,0x21,0xBF,0x2C,0xC7,0x00,
  0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x3A,0xB7,0x00,0x32,0x21,0xBF,0x2A,0x2B,0xB5,
  0x00,0x8A,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2C,0xB5,0x00,0x8A,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) & 0xff),
  0x2D,0xC7,0x00,
  0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x3B,0xB7,0x00,0x32,0x21,0xBF,0x2A,0x2D,0xB5,
  0x00,0x8A,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB8,0x01,0x1A,0x2E,0xB5,0x00,0x8A,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x13,0x2A,0xC1,0x00,0x9C,0x99,0x00,
  0x0C,0xB8,0x00,0xC1,0x36,0x36,0x04,0xA7,0x00,0x13,0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x3B,0x40,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, totalBackingStore_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, totalBackingStore_f) - sizeof(Object)) << 3)) & 0xff),
  0x88,0x36,0x04,0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x0C,0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0xA7,0x00,0x16,0x2A,0xB4,0x00,
  0x8A,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x3A,0x05,0x12,0x00,0x3C,0xB8,0x01,0xE6,0x50,0x3A,0x06,0x2A,0xBB,0x00,0x08,0x59,
  0xBB,0x00,0xB0,0x59,0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x3B,0x40,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMemoryArea_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMemoryArea_f) - sizeof(Object)) << 3)) & 0xff),
  0x88,0x15,0x04,0x19,0x05,0x19,0x06,0xB7,0x01,0x65,0x5D,0xB7,0x01,0x15,0x60,
  0xB5,0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x6F
};

/* Class: javax.safetycritical.ManagedEventHandler */
/* Method: cleanUp */
RANGE const unsigned char javax_safetycritical_ManagedEventHandler_cleanUp[20] PROGMEM = {
  0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xFE,
  0x80,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedLongEventHandler */
/* Method: getCurrentMemory */
RANGE const unsigned char javax_safetycritical_ManagedLongEventHandler_getCurrentMemory[8] PROGMEM = {
  0x2A,0xB4,0x00,0x26,0xB4,0x00,0x28,0xB0
};

/* Class: javax.safetycritical.ManagedLongEventHandler */
/* Method: setCurrentMemory */
RANGE const unsigned char javax_safetycritical_ManagedLongEventHandler_setCurrentMemory[9] PROGMEM = {
  0x2A,0xB4,0x00,0x26,0x2B,0xB5,0x00,0x28,0xB1
};

/* Class: javax.safetycritical.ManagedMemory$BackingStore */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ManagedMemory_BackingStore_init_[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x00,0xBC,0x08,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ManagedMemory$ImmortalMemory */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ManagedMemory_ImmortalMemory_init_[19] PROGMEM = {
  0x2A,0x1B,0x1B,0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0x12,0x00,0x3D,0xB7,0x00,0xF8,0x10,
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedMemory$ImmortalMemory */
/* Method: instance */
RANGE const unsigned char javax_safetycritical_ManagedMemory_ImmortalMemory_instance[23] PROGMEM = {
  0x12,0x00,0x3D,0xB8,0x00,0xBE,0x1B,0x4B,0x2A,0xC6,0x00,0x0A,0x2A,0xC0,0x00,0xC6,
  0xB0,0x01,0x03,0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: <clinit> */
RANGE const unsigned char javax_safetycritical_ManagedMemory_clinit_[23] PROGMEM = {
  0x04,0xB3,0x00,0x07,0x08,
  /* offset: 15, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, flag_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, flag_f) << 3)) & 0xff),
  0xBB,0x01,0x1C,0x59,0xB7,0x00,0x31,0x12,0xB3,
  0x00,0x07,0x22,
  /* offset: 15, 72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, exception_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, exception_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: <init> */
/* <init>
 * param : int, int, javax.realtime.MemoryArea, java.lang.String
 * return: void
 */
int16 javax_safetycritical_ManagedMemory_init_(int32 *fp)
{  
   int32* sp;
   int32 i_val4;
   int32 i_val3;
   int32 i_val2;
   int32 i_val1;
   int32 i_val0;
   int16 rval_m_6;
    int32 this;
    int32 size;
    int32 BackingStoreOfThisMemory;
    int32 backingStoreProvider;
    int32 label;
   this = (int32)(*(fp + 0));
   size = (int32)(*(fp + 1));
   BackingStoreOfThisMemory = (int32)(*(fp + 2));
   backingStoreProvider = (int32)(*(fp + 3));
   label = (int32)(*(fp + 4));
   sp = &fp[7]; /* make room for local VM state on the stack */
   i_val4 = this;
   i_val3 = (int32)size;
   i_val2 = (int32)BackingStoreOfThisMemory;
   i_val1 = backingStoreProvider;
   i_val0 = label;
   rval_m_6 = javax_realtime_MemoryArea_init_(sp, i_val4, i_val3, i_val2, i_val1, i_val0);
   if (rval_m_6 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_6;
   }
   return -1;
}

/* Class: javax.safetycritical.ManagedMemory */
/* Method: allocateBackingStore */
RANGE const unsigned char javax_safetycritical_ManagedMemory_allocateBackingStore[22] PROGMEM = {
  0xBB,0x00,0x9E,0x59,0x1A,0xB8,0x01,0xDF,0x1D,0xB7,0x00,0xF4,0x23,0xB3,0x00,0x8B,
  0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0xB1,0x01,0x00
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: enter */
/* enter
 * param : java.lang.Runnable
 * return: void
 */
int16 javax_safetycritical_ManagedMemory_enter(int32 *fp)
{  
   int32* sp;
   int32 i_val2;
   int16 rval_m_8;
   Object* ex_ception;
   unsigned short handler_pc;
   int16 excep;
   unsigned short pc;
   int16 rval_m_14;
   Object* i_obj;
   int16 rval_m_18;
   int16 rval_m_23;
    int32 this;
    int32 logic;
   this = (int32)(*(fp + 0));
   logic = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val2 = logic;
      if (i_val2 != 0) {
         goto L13;
      }
   if (handleNewClassIndex(sp, 142) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_8 = enterMethodInterpreter(JAVA_LANG_ILLEGALARGUMENTEXCEPTION_INIT_, sp);
   if (rval_m_8 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_8;
   }
   ex_ception = (Object*) (pointer) *(sp - 1);
   excep = getClassIndex(ex_ception);
   pc = 12;
   sp--;
   goto throwIt;
   L13:
   i_val2 = this;
   *sp = (int32)i_val2;
   sp++;
   sp -= 0;
   rval_m_14 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MANAGEDMEMORY_GETCURRENTPROCESS, sp);
   if (rval_m_14 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_14;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 18;
         goto throwNullPointer;
      }
   rval_m_18 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_18 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_18;
   }
   i_val2 = logic;
   *sp = (int32)i_val2;
   sp++;
   sp -= 3;
   rval_m_23 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MANAGEDMEMORY_RUNENTER, sp);
   if (rval_m_23 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_23;
   }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[250], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.ManagedMemory */
/* Method: executeInArea */
RANGE const unsigned char javax_safetycritical_ManagedMemory_executeInArea[144] PROGMEM = {
  0x2B,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x3E,0xB7,0x00,0x32,0x7B,0xBF,
  0xB2,0x00,0x07,0x08,
  /* offset: 15, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, flag_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, flag_f) << 3)) & 0xff),
  0x99,0x00,0x57,0x03,0xB3,0x00,0x07,0x08,
  /* offset: 15, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, flag_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, flag_f) << 3)) & 0xff),
  
  0xB8,0x01,0xE4,0x7E,0x4D,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,
  0x57,0x2B,0xB9,0x00,0x00,0x00,0x0A,0x0B,0x00,0x09,0x00,0xED,0x00,0x8C,0x01,0x24,
  0x00,0x88,0x01,0x26,0x00,0x89,0x01,0x28,0x00,0x72,0x01,0x73,0x00,0x29,0x00,0xA9,
  0x00,0x5F,0x01,0x7A,0x00,0x69,0x01,0xB5,0x00,0x6A,0x01,0xB9,0x00,0x8F,0x01,0xC7,
  0x00,0x28,0x01,0xFC,0x2C,0xB8,0x01,0xEC,0x65,0x57,0xA7,0x00,0x23,0xB8,0x00,0xFC,
  0x3C,0x4D,0x2C,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x3F,0xB7,0x00,0x32,
  0x7B,0xBF,0x2A,0x2C,0xB7,0x01,0x80,0x40,0x2B,0xB7,0x01,0x02,0x84,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: getCurrentProcess */
RANGE const unsigned char javax_safetycritical_ManagedMemory_getCurrentProcess[31] PROGMEM = {
  0xB2,0x00,0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x99,0x00,0x0E,0xB8,0x01,0x58,0x06,0xB7,0x01,0x55,
  0x0B,0xB0,0x01,0x01,0xB8,0x00,0xE5,0x0C,0xB7,0x00,0xE2,0x11,0xB0,0x01,0x01
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: getDelegate */
RANGE const unsigned char javax_safetycritical_ManagedMemory_getDelegate[10] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: removeArea */
RANGE const unsigned char javax_safetycritical_ManagedMemory_removeArea[8] PROGMEM = {
  0x2A,0xB7,0x00,0xC4,0xF9,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: resetArea */
RANGE const unsigned char javax_safetycritical_ManagedMemory_resetArea[15] PROGMEM = {
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0x03,0xB7,0x01,0xE9,0x6E,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: resizeArea */
RANGE const unsigned char javax_safetycritical_ManagedMemory_resizeArea[9] PROGMEM = {
  0x2A,0x1F,0xB7,0x00,0xC5,0xFD,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: runEnter */
RANGE const unsigned char javax_safetycritical_ManagedMemory_runEnter[199] PROGMEM = {
  0x2B,0xC1,0x01,0x14,0x99,0x00,0x15,0x2B,0xC0,0x01,0x14,0x3A,0x04,0x19,0x04,0xB4,
  0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0x4E,0xA7,0x00,0x2B,0x2B,0xC1,0x00,0x18,0x99,0x00,0x15,
  0x2B,0xC0,0x00,0x18,0x3A,0x04,0x19,0x04,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0x4E,0xA7,
  0x00,0x12,0x2B,0xC0,0x00,0x64,0x3A,0x04,0x19,0x04,0xB4,0x00,0x32,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x4E,0x2D,0xB4,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x04,0x2D,0x2A,0xB5,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2D,0x2A,0xB5,0x00,0x04,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, topMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, topMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,0x57,0x2C,0xB9,0x00,0x00,0x00,0x0A,0x0B,0x00,0x09,0x00,
  0xED,0x00,0x8C,0x01,0x24,0x00,0x88,0x01,0x26,0x00,0x89,0x01,0x28,0x00,0x72,0x01,
  0x73,0x00,0x29,0x00,0xA9,0x00,0x5F,0x01,0x7A,0x00,0x69,0x01,0xB5,0x00,0x6A,0x01,
  0xB9,0x00,0x8F,0x01,0xC7,0x00,0x28,0x01,0xFC,0x19,0x04,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,0x57,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0x03,0xB7,0x01,
  0xE9,0x6E,0x2D,0x19,0x04,0xB5,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2D,0x19,0x04,0xB5,0x00,
  0x04,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, topMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, topMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x1F
};

/* Class: javax.safetycritical.ManagedMemory */
/* Method: runExecuteInArea */
RANGE const unsigned char javax_safetycritical_ManagedMemory_runExecuteInArea[377] PROGMEM = {
  0x2B,0xC1,0x01,0x14,0x99,0x00,0x83,0x2B,0xC0,0x01,0x14,0x4E,0x2D,0xB4,0x00,0x8A,
  0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x04,0x2D,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB5,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB8,0x01,0xEC,0x65,0x57,0x2C,0xB9,0x00,0x00,0x00,0x0A,0x0B,0x00,0x09,0x00,0xED,
  0x00,0x8C,0x01,0x24,0x00,0x88,0x01,0x26,0x00,0x89,0x01,0x28,0x00,0x72,0x01,0x73,
  0x00,0x29,0x00,0xA9,0x00,0x5F,0x01,0x7A,0x00,0x69,0x01,0xB5,0x00,0x6A,0x01,0xB9,
  0x00,0x8F,0x01,0xC7,0x00,0x28,0x01,0xFC,0x19,0x04,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB8,0x01,0xEC,0x65,0x57,0x2D,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0x19,0x04,0xB5,0x00,
  0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0xF2,0x2B,0xC1,0x00,0x18,0x99,0x00,0x83,0x2B,0xC0,
  0x00,0x18,0x4E,0x2D,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x3A,0x04,0x2D,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB5,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,0x57,0x2C,0xB9,0x00,0x00,
  0x00,0x0A,0x0B,0x00,0x09,0x00,0xED,0x00,0x8C,0x01,0x24,0x00,0x88,0x01,0x26,0x00,
  0x89,0x01,0x28,0x00,0x72,0x01,0x73,0x00,0x29,0x00,0xA9,0x00,0x5F,0x01,0x7A,0x00,
  0x69,0x01,0xB5,0x00,0x6A,0x01,0xB9,0x00,0x8F,0x01,0xC7,0x00,0x28,0x01,0xFC,0x19,
  0x04,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,0x57,0x2D,0xB4,0x00,0x0C,
  0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0x19,0x04,0xB5,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x6B,0x2B,0xC0,
  0x00,0x64,0x4E,0x2D,0xB6,0x01,0x08,0x00,0x00,0x3A,0x04,0x2D,0x2A,0xB6,0x00,0x07,
  0x01,0x00,0x2A,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,0x57,0x2C,0xB9,
  0x00,0x00,0x00,0x0A,0x0B,0x00,0x09,0x00,0xED,0x00,0x8C,0x01,0x24,0x00,0x88,0x01,
  0x26,0x00,0x89,0x01,0x28,0x00,0x72,0x01,0x73,0x00,0x29,0x00,0xA9,0x00,0x5F,0x01,
  0x7A,0x00,0x69,0x01,0xB5,0x00,0x6A,0x01,0xB9,0x00,0x8F,0x01,0xC7,0x00,0x28,0x01,
  0xFC,0x19,0x04,0xB4,0x00,0x8B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_realtime_MemoryArea_c, delegate_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xEC,0x65,0x57,0x2D,0x19,
  0x04,0xB6,0x00,0x07,0x01,0x00,0xB1,0x01,0x1F
};

/* Class: javax.safetycritical.ManagedSchedMethods */
/* Method: createScjProcess */
RANGE const unsigned char javax_safetycritical_ManagedSchedMethods_createScjProcess[25] PROGMEM = {
  0x2A,0x2A,0xB8,0x01,0x08,0x4C,0xB4,0x00,0x3B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, configurationSizes_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, configurationSizes_f) - sizeof(Object)) << 3)) & 0xff),
  0x03,0x2F,0x88,0xBC,
  0x00,0x2B,0xB8,0x01,0x04,0x54,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedMethods */
/* Method: createScjProcess */
RANGE const unsigned char javax_safetycritical_ManagedSchedMethods_createScjProcess_[104] PROGMEM = {
  0x2A,0xC1,0x00,0x54,0x99,0x00,0x10,0xBB,0x00,0x1E,0x59,0x2A,0x2B,0xB7,0x01,0x6F,
  0x35,0xB0,0x01,0x07,0x2A,0xC1,0x00,0xB2,0x99,0x00,0x10,0xBB,0x00,0xE6,0x59,0x2A,
  0x2B,0xB7,0x01,0x6D,0x3C,0xB0,0x01,0x07,0x2A,0xC1,0x00,0x9C,0x99,0x00,0x10,0xBB,
  0x00,0x4A,0x59,0x2A,0x2B,0xB7,0x01,0x6B,0x41,0xB0,0x01,0x07,0x2A,0xC1,0x00,0xE0,
  0x99,0x00,0x10,0xBB,0x00,0x3C,0x59,0x2A,0x2B,0xB7,0x01,0x66,0x46,0xB0,0x01,0x07,
  0x2A,0xC1,0x00,0x18,0x99,0x00,0x10,0xBB,0x00,0xF2,0x59,0x2A,0x2B,0xB7,0x01,0x68,
  0x49,0xB0,0x01,0x07,0x01,0xB0,0x01,0x07
};

/* Class: javax.safetycritical.ManagedSchedMethods */
/* Method: getMission */
RANGE const unsigned char javax_safetycritical_ManagedSchedMethods_getMission[64] PROGMEM = {
  0x2A,0xC1,0x01,0x14,0x99,0x00,0x10,0x2A,0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x18,0x99,0x00,0x10,0x2A,0xC0,0x00,0x18,0xB4,
  0x00,0x0C,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x64,0x99,0x00,0x10,0x2A,
  0xC0,0x00,0x64,0xB4,0x00,0x32,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedMethods */
/* Method: getPriorityParameter */
RANGE const unsigned char javax_safetycritical_ManagedSchedMethods_getPriorityParameter[64] PROGMEM = {
  0x2A,0xC1,0x01,0x14,0x99,0x00,0x10,0x2A,0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x18,0x99,0x00,0x10,0x2A,0xC0,0x00,0x18,0xB4,
  0x00,0x0C,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x64,0x99,0x00,0x10,0x2A,
  0xC0,0x00,0x64,0xB4,0x00,0x32,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedMethods */
/* Method: getScjProcess */
RANGE const unsigned char javax_safetycritical_ManagedSchedMethods_getScjProcess[64] PROGMEM = {
  0x2A,0xC1,0x01,0x14,0x99,0x00,0x10,0x2A,0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x18,0x99,0x00,0x10,0x2A,0xC0,0x00,0x18,0xB4,
  0x00,0x0C,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x64,0x99,0x00,0x10,0x2A,
  0xC0,0x00,0x64,0xB4,0x00,0x32,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedMethods */
/* Method: getStorage */
RANGE const unsigned char javax_safetycritical_ManagedSchedMethods_getStorage[64] PROGMEM = {
  0x2A,0xC1,0x01,0x14,0x99,0x00,0x10,0x2A,0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x18,0x99,0x00,0x10,0x2A,0xC0,0x00,0x18,0xB4,
  0x00,0x0C,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x2A,0xC1,0x00,0x64,0x99,0x00,0x10,0x2A,
  0xC0,0x00,0x64,0xB4,0x00,0x32,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03,0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedulableSet */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ManagedSchedulableSet_init_[48] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0F,0x2A,0xB2,0x00,0x43,0x20,
  /* offset: 10, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) & 0xff),
  0xBD,0x00,0x35,0xB5,
  0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB2,0x00,
  0x43,0x20,
  /* offset: 10, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) & 0xff),
  0xBD,0x00,0x56,0xB5,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedSchedulableSet */
/* Method: addMS */
RANGE const unsigned char javax_safetycritical_ManagedSchedulableSet_addMS[60] PROGMEM = {
  0x2A,0x2B,0xB7,0x01,0x0B,0x26,0x9A,0x00,0x33,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0x53,0x2A,0x59,0xB4,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x59,0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedulableSet */
/* Method: contains */
RANGE const unsigned char javax_safetycritical_ManagedSchedulableSet_contains[40] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x17,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x32,0x2B,0xA6,
  0x00,0x07,0x04,0xAC,0x01,0x03,0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0xA1,0xFF,0xE4,0x03,0xAC,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedulableSet */
/* Method: removeAperiodicHandlers */
RANGE const unsigned char javax_safetycritical_ManagedSchedulableSet_removeAperiodicHandlers[140] PROGMEM = {
  0x03,0x3C,0xA7,0x00,0x7C,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x32,0xC1,0x00,
  0xE0,0x99,0x00,0x4B,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x32,0xB9,0x00,0x01,
  0x00,0x13,0x03,0x00,0x8A,0x00,0xF1,0x00,0x4E,0x01,0x30,0x00,0x0C,0x01,0x11,0xB8,
  0x01,0x58,0x35,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x32,0xB7,0x01,0x49,0x76,0x2A,0x59,0xB4,0x00,
  0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xB5,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x67,
  0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9A,0x00,0x18,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x32,0xB8,
  0x01,0x05,0x5E,0xB7,0x01,0x1C,0x64,0xB7,0x01,0x33,0x6A,0x84,0x01,0x01,0x1B,0x2A,
  0xB4,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0xA1,0xFF,0x7F,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedSchedulableSet */
/* Method: removeMSObject */
RANGE const unsigned char javax_safetycritical_ManagedSchedulableSet_removeMSObject[212] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x8F,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x32,0x2B,0xA6,
  0x00,0x7F,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x32,0xB9,0x00,0x01,0x00,0x13,
  0x03,0x00,0x8A,0x00,0xF1,0x00,0x4E,0x01,0x30,0x00,0x0C,0x01,0x11,0x2A,0xB4,0x00,
  0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x01,0x53,0xB8,0x01,0x58,0x35,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x32,0xB7,0x01,0x3E,0x3F,0xBB,0x00,
  0x34,0x59,0x12,0x00,0x40,0xB7,0x00,0x67,0x49,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x1C,0x32,0xB4,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x69,0x4F,0xB7,0x00,0x6E,0x53,
  0xB8,0x00,0x0A,0x57,0x2A,0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x01,0x53,0x2A,0x59,
  0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xB5,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x84,0x02,
  0x01,0x1C,0x2A,0xB4,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0xA1,0xFF,0x6C,0xBB,0x00,0x34,0x59,
  0x12,0x00,0x41,0xB7,0x00,0x67,0x49,0x2A,0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0x69,0x4F,0xB7,0x00,0x6E,0x53,0xB8,0x00,0x0A,0x57,0x2A,0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9A,0x00,0x10,0x2B,0xB8,0x01,0x05,0x5E,0xB7,0x01,0x1C,0x64,0xB7,0x01,0x33,
  0x6A,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ManagedSchedulableSet */
/* Method: toString */
RANGE const unsigned char javax_safetycritical_ManagedSchedulableSet_toString[36] PROGMEM = {
  0xBB,0x00,0x34,0x59,0x12,0x00,0x42,0xB7,0x00,0x67,0x49,0x2A,0xB4,0x00,0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x69,0x4F,0x12,0x00,0x43,0xB7,0x00,0x6D,0x81,0xB7,0x00,0x6E,
  0x53,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ManagedThread */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ManagedThread_init_[11] PROGMEM = {
  0x2A,0x2B,0x2C,0x01,0xB7,0x01,0x10,0x14,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ManagedThread */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ManagedThread_init__[180] PROGMEM = {
  0x2A,0x2B,0x2D,0xB7,0x00,0xC7,0x1B,0x2A,0x01,0xB5,0x00,0x0C,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0x2B,0xB5,0x00,0x0C,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xC7,0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,
  0x00,0x3B,0xB7,0x00,0x32,0x26,0xBF,0x2A,0x2C,0xB5,0x00,0x0C,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xB8,0x01,0x1A,0x2B,0xB5,0x00,0x0C,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x0A,0xB8,0x00,0xC1,0x31,0xA7,0x00,0x11,0x2A,0xB4,0x00,0x0C,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x3B,0x40,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, totalBackingStore_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, totalBackingStore_f) - sizeof(Object)) << 3)) & 0xff),
  0x88,0x36,0x04,0x2A,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x0C,0xB2,0x00,0x8B,0x22,
  /* offset: 16, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, backingStore_f) << 3)) & 0xff),
  0xA7,0x00,0x16,0x2A,0xB4,
  0x00,0x0C,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, mission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x3A,0x05,0x12,0x00,0x3C,0xB8,0x01,0xE6,0x4D,0x3A,0x06,0x2A,0xBB,0x00,0x08,
  0x59,0xBB,0x00,0xB0,0x59,0x2C,0xB4,0x00,0x3B,0x40,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMemoryArea_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMemoryArea_f) - sizeof(Object)) << 3)) & 0xff),
  0x88,0x15,0x04,0x19,
  0x05,0x19,0x06,0xB7,0x01,0x65,0x5A,0xB7,0x01,0x15,0x5D,0xB5,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x6F
};

/* Class: javax.safetycritical.ManagedThread */
/* Method: cleanUp */
RANGE const unsigned char javax_safetycritical_ManagedThread_cleanUp[20] PROGMEM = {
  0x2A,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xFE,
  0x8B,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ManagedThread */
/* Method: register */
RANGE const unsigned char javax_safetycritical_ManagedThread_register[20] PROGMEM = {
  0xB8,0x01,0x1A,0x2B,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x4C,0x2B,0x2A,0xB7,0x01,0x0A,
  0x7D,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ManagedThread */
/* Method: sleep */
RANGE const unsigned char javax_safetycritical_ManagedThread_sleep[123] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x9C,0xB8,0x01,0x58,0x9F,0xB4,0x00,
  0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0x4C,0x2A,0xC1,0x00,0xF8,0x99,0x00,0x1D,0x2B,0xB4,0x00,0x54,
  0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xC0,0x00,0xF8,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0xA4,0xAF,0x57,0xA7,0x00,0x29,0x2A,0xC1,0x01,0x04,0x99,0x00,0x19,0x2B,0xBB,0x01,
  0x04,0x59,0x2A,0xC0,0x01,0x04,0xB7,0x00,0xA1,0xB5,0xB5,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xA7,0x00,0x0C,0xBB,0x00,0x84,0x59,0xB7,0x00,0x7C,0xBA,0xBF,0x2B,0x07,0xB5,0x00,
  0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0xBF,0xB2,0x00,
  0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xCA,0xC2,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MemoryInfo */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MemoryInfo_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0C,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MemoryInfo */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MemoryInfo_init__[32] PROGMEM = {
  0x2A,0xB7,0x01,0x14,0x13,0x2A,0x2B,0xB5,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB5,
  0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB5,0x00,0x04,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, topMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, topMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.Mission */
/* Method: <clinit> */
RANGE const unsigned char javax_safetycritical_Mission_clinit_[16] PROGMEM = {
  0x01,0xB3,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x03,0xB3,0x00,0x13,0x08,
  /* offset: 17, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.safetycritical.Mission */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_Mission_init_[32] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x21,0x2A,0x03,0xB5,0x00,0x13,0x08,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionTerminate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionTerminate_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x02,0xB5,
  0x00,0x13,0x20,
  /* offset: 0, 104*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x13,0x08,
  /* offset: 0, -120*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, isMissionSetInitByThis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, isMissionSetInitByThis_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.Mission */
/* Method: addNewMission */
RANGE const unsigned char javax_safetycritical_Mission_addNewMission[55] PROGMEM = {
  0x03,0x3C,0xA7,0x00,0x1E,0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x1B,0x32,0xC7,0x00,0x10,
  0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x1B,0x2A,0x53,0x1B,0xAC,0x01,0x01,0x84,0x01,0x01,
  0x1B,0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0xBE,0xA1,0xFF,0xDD,0xBB,0x00,0x02,0x59,0x12,
  0x00,0x44,0xB7,0x00,0x36,0x83,0xBF
};

/* Class: javax.safetycritical.Mission */
/* Method: cleanUp */
RANGE const unsigned char javax_safetycritical_Mission_cleanUp[4] PROGMEM = {
  0x04,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.Mission */
/* Method: getCurrentMission */
/* getCurrentMission
 * param : 
 * return: javax.safetycritical.Mission
 */
int16 javax_safetycritical_Mission_getCurrentMission(int32 *fp)
{  
   int32* sp;
   int32 i_val0;
   int16 rval_m_11;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_24;
   int16 rval_m_53;
   Object* i_obj;
   int16 rval_m_57;
   int32 msb_int32;
   int16 rval_m_64;
   int16 rval_m_68;
   int16 rval_m_72;
   unsigned char *object;
   uint8 i_res;
   signed short subClassIndex;
   int8 b_val0;
   int16 rval_m_82;
   int16 rval_m_86;
   int16 rval_m_90;
   int16 rval_m_107;
   int16 rval_m_111;
   int16 rval_m_115;
   int16 rval_m_119;
    int32 mission;
   mission = (int32)(*(fp + 0));
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val0 = 0;
   mission = i_val0;
   i_val0 = ((struct _staticClassFields_c *)classData) -> level_f;
      if (i_val0 != 0) {
         goto L44;
      }
   sp -= 0;
   rval_m_11 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_CYCLICSCHEDULER_INSTANCE, sp);
   if (rval_m_11 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_11;
   }
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      if (cobj == 0) {
         pc = 15;
         goto throwNullPointer;
      }
   i_val0 = ((struct _javax_safetycritical_CyclicScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> seq_f;
      if (i_val0 == 0) {
         goto L44;
      }
   sp -= 0;
   rval_m_24 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_CYCLICSCHEDULER_INSTANCE, sp);
   if (rval_m_24 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_24;
   }
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      if (cobj == 0) {
         pc = 28;
         goto throwNullPointer;
      }
   i_val0 = ((struct _javax_safetycritical_CyclicScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> seq_f;
cobj = (unsigned char *) (pointer)i_val0;
      if (cobj == 0) {
         pc = 34;
         goto throwNullPointer;
      }
   i_val0 = ((struct _javax_safetycritical_MissionSequencer_c *)HEAP_REF(cobj, unsigned char*)) -> currMission_f;
   mission = i_val0;
   goto L124;
   L44:
   i_val0 = ((struct _staticClassFields_c *)classData) -> level_f;
      if (i_val0 <= 0) {
         goto L124;
      }
   sp -= 0;
   rval_m_53 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_53 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_53;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 57;
         goto throwNullPointer;
      }
   rval_m_57 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_GETCURRENTPROCESS, sp);
   if (rval_m_57 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_57;
   }
   sp--;
      msb_int32 = (int32)(*sp);
      if (msb_int32 == 0) {
         goto L124;
      }
   sp -= 0;
   rval_m_64 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_64 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_64;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 68;
         goto throwNullPointer;
      }
   rval_m_68 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_GETCURRENTPROCESS, sp);
   if (rval_m_68 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_68;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 72;
         goto throwNullPointer;
      }
   rval_m_72 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_72 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_72;
   }
   sp--;
object = (unsigned char *) (pointer) *sp;
      excep = 1;
      if (object != 0) {
         subClassIndex = getClassIndex((Object*)object);
         excep = !isSubClassOf(subClassIndex, 78);
      }
      if (excep) {
      i_res = 0;
      }
      else
      {
      i_res = 1;
      }
   b_val0 = i_res;
      if (b_val0 == 0) {
         goto L107;
      }
   sp -= 0;
   rval_m_82 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_82 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_82;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 86;
         goto throwNullPointer;
      }
   rval_m_86 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_GETCURRENTPROCESS, sp);
   if (rval_m_86 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_86;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 90;
         goto throwNullPointer;
      }
   rval_m_90 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_90 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_90;
   }
   sp--;
object = (unsigned char *) (pointer) *sp;
      excep = 0;
   i_val0 = (int32)(pointer)object;
      if (object != 0) {
         subClassIndex = getClassIndex((Object*)object);
         excep = !isSubClassOf(subClassIndex, 78);
      }
      if (excep) {
         pc = 94;
         goto throwClassCastException;
      }
cobj = (unsigned char *) (pointer)i_val0;
      if (cobj == 0) {
         pc = 97;
         goto throwNullPointer;
      }
   i_val0 = ((struct _javax_safetycritical_MissionSequencer_c *)HEAP_REF(cobj, unsigned char*)) -> currMission_f;
   mission = i_val0;
   goto L124;
   L107:
   sp -= 0;
   rval_m_107 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_107 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_107;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 111;
         goto throwNullPointer;
      }
   rval_m_111 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_GETCURRENTPROCESS, sp);
   if (rval_m_111 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_111;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 115;
         goto throwNullPointer;
      }
   rval_m_115 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_115 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_115;
   }
   sp -= 1;
   rval_m_119 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MANAGEDSCHEDMETHODS_GETMISSION, sp);
   if (rval_m_119 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_119;
   }
   sp--;
   mission = (int32)(*sp);
   L124:
   i_val0 = mission;
   *((int32*)fp) = i_val0;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwClassCastException:
      excep = initializeException(sp, JAVA_LANG_CLASSCASTEXCEPTION, JAVA_LANG_CLASSCASTEXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[282], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.Mission */
/* Method: getScjProcess */
RANGE const unsigned char javax_safetycritical_Mission_getScjProcess[25] PROGMEM = {
  0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x1A,0x32,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,
  0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x32,0xB0,0x01,0x04
};

/* Class: javax.safetycritical.Mission */
/* Method: getSequencer */
RANGE const unsigned char javax_safetycritical_Mission_getSequencer[10] PROGMEM = {
  0x2A,0xB4,0x00,0x13,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.safetycritical.Mission */
/* Method: initialize */
/* Class: javax.safetycritical.Mission */
/* Method: runCleanup */
RANGE const unsigned char javax_safetycritical_Mission_runCleanup[159] PROGMEM = {
  0x2A,0xB2,0x00,0x17,0x22,
  /* offset: 4, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, CLEANUP_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, CLEANUP_f) << 3)) & 0xff),
  0xB5,0x00,0x13,0x22,
  /* offset: 0, 72*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionPhase_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionPhase_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x07,
  0xB8,0x02,0x11,0xC4,0x2A,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9D,0xFF,0xEF,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x8C,0x03,0x3D,
  0xA7,0x00,0x26,0x2A,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x1C,0x01,0x53,0x2A,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x1C,0x01,0x53,0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,
  0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0xA1,0xFF,0xCF,0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x13,0x20,
  /* offset: 0, 104*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) & 0xff),
  0x01,0x53,0x2A,0xB4,0x00,0x13,0x08,
  /* offset: 0, -120*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, isMissionSetInitByThis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, isMissionSetInitByThis_f) - sizeof(Object)) << 3)) & 0xff),
  0x99,0x00,0x0A,
  0x03,0xB3,0x00,0x13,0x08,
  /* offset: 17, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x19,0xCC,0x57,0x2B,0xB7,0x00,
  0xFF,0xCE,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0x99,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.Mission */
/* Method: runExecute */
RANGE const unsigned char javax_safetycritical_Mission_runExecute[148] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x8C,0x2A,0xB2,0x00,0x17,0x22,
  /* offset: 3, -32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, EXECUTE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, EXECUTE_f) << 3)) & 0xff),
  0xB5,0x00,0x13,0x22,
  /* offset: 0, 72*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionPhase_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionPhase_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x4C,0xB8,
  0x01,0x58,0x46,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0x4D,0x2A,0xB4,0x00,0x13,0x20,
  /* offset: 0, 104*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x14,0x68,0x3E,0x03,0x36,0x04,0xA7,0x00,0x43,0x2B,0xB4,0x00,0x67,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, managedSchObjects_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,0x32,0x3A,0x05,0x2B,0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,
  0x19,0x05,0xB8,0x01,0x03,0xAC,0x53,0x2B,0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,
  0x32,0x1D,0xB7,0x01,0x83,0xB0,0x84,0x03,0x01,0x2C,0x2B,0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,0x32,0xB7,0x01,0x3D,0xB4,0x84,0x04,0x01,0x15,0x04,0x2B,0xB4,0x00,
  0x67,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, noOfRegistered_f) - sizeof(Object)) << 3)) & 0xff),
  0xA1,0xFF,0xB7,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,
  0x99,0xB1,0x01,0x27
};

/* Class: javax.safetycritical.Mission */
/* Method: runInitialize */
RANGE const unsigned char javax_safetycritical_Mission_runInitialize[103] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x8C,0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  
  0xC6,0x00,0x0C,0xB2,0x00,0x13,0x08,
  /* offset: 17, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) & 0xff),
  0x9A,0x00,0x21,0xB2,0x00,0x43,0x20,
  /* offset: 10, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) & 0xff),
  0xBD,0x00,0x41,0xB3,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x2A,0x04,0xB5,0x00,0x13,
  0x08,
  /* offset: 0, -120*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, isMissionSetInitByThis_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, isMissionSetInitByThis_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0xB3,0x00,0x13,0x08,
  /* offset: 17, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isMissionSetInit_f) << 3)) & 0xff),
  0x2A,0x2A,0xB8,0x01,0x18,0x94,
  0xB5,0x00,0x13,0x20,
  /* offset: 0, 104*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionIndex_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0xCE,0x59,0xB7,0x01,0x09,0x96,0xB5,
  0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0xAB,0x97,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  
  0xB7,0x01,0xC3,0x99,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.Mission */
/* Method: setMissionSeq */
RANGE const unsigned char javax_safetycritical_Mission_setMissionSeq[11] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x13,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.Mission */
/* Method: terminationPending */
RANGE const unsigned char javax_safetycritical_Mission_terminationPending[10] PROGMEM = {
  0x2A,0xB4,0x00,0x13,0x08,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionTerminate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionTerminate_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: javax.safetycritical.MissionMemory$1 */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MissionMemory_1_init_[16] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x8C,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x48,0x0E,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MissionMemory$1 */
/* Method: run */
RANGE const unsigned char javax_safetycritical_MissionMemory_1_run[20] PROGMEM = {
  0x2A,0xB4,0x00,0x8C,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x3D,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x20,
  0x1C,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MissionMemory$2 */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MissionMemory_2_init_[16] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x88,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_2_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_2_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x48,0x0E,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MissionMemory$2 */
/* Method: run */
RANGE const unsigned char javax_safetycritical_MissionMemory_2_run[20] PROGMEM = {
  0x2A,0xB4,0x00,0x88,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_2_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_2_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x3D,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x1F,
  0x1C,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MissionMemory$3 */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MissionMemory_3_init_[16] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x89,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_3_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_3_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x48,0x0E,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MissionMemory$3 */
/* Method: run */
RANGE const unsigned char javax_safetycritical_MissionMemory_3_run[27] PROGMEM = {
  0x2A,0xB4,0x00,0x89,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_3_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_3_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x3D,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x89,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_3_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_3_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x1E,0x1C,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MissionMemory */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MissionMemory_init_[64] PROGMEM = {
  0x2A,0x1B,0x2C,0xB7,0x00,0xC0,0x0E,0x2C,0x2D,0xB7,0x00,0xF8,0x12,0x2A,0xBB,0x01,
  0x18,0x59,0x2A,0xB7,0x01,0x23,0x17,0xB5,0x00,0x3D,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runInitialize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runInitialize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x01,
  0x10,0x59,0x2A,0xB7,0x01,0x25,0x1E,0xB5,0x00,0x3D,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runExecute_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runExecute_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x01,
  0x12,0x59,0x2A,0xB7,0x01,0x27,0x23,0xB5,0x00,0x3D,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runCleanup_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runCleanup_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x0D
};

/* Class: javax.safetycritical.MissionMemory */
/* Method: enterToCleanup */
RANGE const unsigned char javax_safetycritical_MissionMemory_enterToCleanup[28] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x3D,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x3D,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runCleanup_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runCleanup_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0xFB,0x34,0x2A,0xB7,0x00,0xFF,0x3B,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MissionMemory */
/* Method: enterToExecute */
RANGE const unsigned char javax_safetycritical_MissionMemory_enterToExecute[23] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x3D,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x3D,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runExecute_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runExecute_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0xFB,0x34,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MissionMemory */
/* Method: enterToInitialize */
RANGE const unsigned char javax_safetycritical_MissionMemory_enterToInitialize[23] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x3D,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, m_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x3D,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runInitialize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionMemory_c, runInitialize_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0xFB,0x34,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: <clinit> */
RANGE const unsigned char javax_safetycritical_MissionSequencer_clinit_[23] PROGMEM = {
  0x04,0xB3,0x00,0x4E,0x08,
  /* offset: 11, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) & 0xff),
  0x01,0xB3,0x00,0x4E,0x22,
  /* offset: 11, -32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missSeqProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missSeqProcess_f) << 3)) & 0xff),
  0x02,0xB3,
  0x00,0x4E,0x20,
  /* offset: 12, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MissionSequencer_init_[13] PROGMEM = {
  0x2A,0x2B,0x2C,0x12,0x00,0x45,0xB7,0x01,0x2F,0x8E,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_MissionSequencer_init__[202] PROGMEM = {
  0x2A,0x2B,0xBB,0x00,0xBA,0x59,0xB7,0x00,0xA6,0x2D,0x2C,0xB7,0x00,0xF0,0x2F,0x2A,
  0x03,0xB5,0x00,0x4E,0x08,
  /* offset: 1, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, terminateSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, terminateSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x01,0xB5,0x00,0x4E,0x22,
  /* offset: 1, 104*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0x2D,0xB5,0x00,0x8A,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x7A,0x59,0x2C,0xB4,0x00,0x3B,
  0x40,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMissionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMissionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x88,0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2D,0xB7,0x01,0x29,0x4C,0xB5,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x04,0xB5,0x00,
  0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xB2,0x00,0x4E,0x08,
  /* offset: 11, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) & 0xff),
  0x99,0x00,0x15,0xB2,0x00,0x09,
  0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x99,0x00,0x0C,0xB8,0x01,0x58,0x58,0x2A,0xB7,0x01,0x53,0x5E,0xB2,
  0x00,0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x05,0xA0,0x00,0x0A,0x03,0xB3,0x00,0x4E,0x08,
  /* offset: 11, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) & 0xff),
  
  0xB2,0x00,0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x05,0xA0,0x00,0x24,0xB2,0x00,0x4E,0x08,
  /* offset: 11, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, isOuterMostSeq_f) << 3)) & 0xff),
  
  0x9A,0x00,0x1B,0xB8,0x01,0x1A,0x62,0xC6,0x00,0x14,0x2A,0xB8,0x01,0x1A,0x62,0xB4,
  0x00,0x13,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, currMissSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,0x4E,0x22,
  /* offset: 1, 104*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x8A,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xCD,0x6F,0xB8,0x01,0x89,0x75,0x2A,0x2A,0xB8,0x01,0x36,
  0x7B,0xB5,0x00,0x4E,0x22,
  /* offset: 1, 72*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, lock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, lock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x0F
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: cleanUp */
RANGE const unsigned char javax_safetycritical_MissionSequencer_cleanUp[19] PROGMEM = {
  0x2A,0xB7,0x00,0xF1,0x10,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xFE,0x12,
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: getNextMission */
/* Class: javax.safetycritical.MissionSequencer */
/* Method: handleAsyncEvent */
RANGE const unsigned char javax_safetycritical_MissionSequencer_handleAsyncEvent[493] PROGMEM = {
  0x2A,0xB4,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xAA,0x00,0x00,0x00,0x00,0x00,0x01,0xD7,0x00,
  0x00,0x00,0x01,0x00,0x00,0x00,0x05,0x00,0x00,0x00,0x24,0x00,0x00,0x00,0x99,0x00,
  0x00,0x00,0xC2,0x00,0x00,0x01,0x18,0x00,0x00,0x01,0x4D,0x2A,0x2A,0xB7,0x01,0xAD,
  0xC7,0xB5,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xC6,0x00,
  0x14,0xB2,0x00,0x4E,0x20,
  /* offset: 12, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) & 0xff),
  0x04,0x60,0xB3,0x00,0x4E,0x20,
  /* offset: 12, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) & 0xff),
  0xA7,
  0x00,0x11,0xB2,0x00,0x4E,0x20,
  /* offset: 12, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) & 0xff),
  0x04,0x64,0xB3,0x00,0x4E,0x20,
  /* offset: 12, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xC6,0x00,0x0D,0x2A,0xB4,0x00,0x4E,0x08,
  /* offset: 1, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, terminateSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, terminateSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0x99,0x00,0x16,0x2A,0x04,0xB5,0x00,0x4E,0x08,
  /* offset: 1, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, terminateSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, terminateSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x08,0xB5,0x00,
  0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x01,0x5A,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x03,0xB5,
  0x00,0x13,0x08,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionTerminate_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, missionTerminate_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x05,0xB5,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x01,0x41,
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x21,0xCD,0x2A,0xB4,0x00,0x4E,
  0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x2C,0xD0,0x2A,0x06,
  0xB5,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x01,0x18,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x2B,0xD4,0xB2,0x00,0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x9E,0x00,0x0F,0x2A,0xB7,0x01,0x34,0xD7,0xA7,0x00,0x2B,0xB8,0x02,0x11,0xD9,
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x22,0x9D,0x9A,0x00,0x19,0x2A,0xB4,
  0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9D,0xFF,0xDB,0x2A,0x07,0xB5,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0xC2,0x2A,
  0xB4,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x2A,
  0xDE,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, missionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, storage_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,
  0x00,0x3B,0x40,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMissionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMissionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x00,0xE3,0x2A,0x04,0xB5,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x8D,0xB2,0x00,0x09,0x20,
  /* offset: 1, -104*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, level_f) << 3)) & 0xff),
  0x05,0xA0,0x00,0x7A,0xBB,0x00,
  0x34,0x59,0x12,0x00,0x46,0xB7,0x00,0x67,0xB1,0x2A,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0x6D,0xE9,0x12,0x00,0x47,0xB7,0x00,0x6D,0xE9,0xB2,0x00,0x4E,0x20,
  /* offset: 12, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, howManyMissions_f) << 3)) & 0xff),
  0xB7,0x00,0x69,0xB4,0x12,0x00,0x48,0xB7,0x00,0x6D,0xE9,0x2A,0xB4,0x00,0x4E,
  0x22,
  /* offset: 1, 104*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x6B,0xF0,0xB7,0x00,0x6E,0xB8,0xB8,0x00,0x0A,0xBC,0xB2,
  0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0xF8,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 104*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xC6,0x00,0x1B,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 104*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, outerSeq_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x0D,0xFB,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0xFF,0x2A,0x10,0x06,0xB5,0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,
  0x00,0x4E,0x20,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currState_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x06,0xA1,0xFE,0x19,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: seqNotify */
RANGE const unsigned char javax_safetycritical_MissionSequencer_seqNotify[72] PROGMEM = {
  0xBB,0x00,0x34,0x59,0x12,0x00,0x49,0xB7,0x00,0x67,0xB1,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0x69,0xB4,0xB7,0x00,0x6E,0xB8,0xB8,0x00,0x0A,0xBC,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9A,0x00,0x08,
  0x2A,0xB7,0x00,0x4B,0xC1,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.MissionSequencer */
/* Method: seqWait */
RANGE const unsigned char javax_safetycritical_MissionSequencer_seqWait[57] PROGMEM = {
  0xA7,0x00,0x12,0x2A,0xB7,0x00,0x4D,0x91,0xA7,0x00,0x0A,0x4C,0x2B,0xB6,0x00,0x0A,
  0x00,0x00,0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x22,0x9D,0x9A,0x00,0x19,
  0x2A,0xB4,0x00,0x4E,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MissionSequencer_c, currMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,
  0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, msCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x9D,0xFF,0xD0,0xB1,0x01,0x03
};

RANGE const ExceptionHandler ex_javax_safetycritical_MissionSequencer_seqWait[1] PROGMEM = {
  { 3, 8, 11, -1}
};

/* Class: javax.safetycritical.Monitor */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_Monitor_init_[29] PROGMEM = {
  0x2A,0xB7,0x01,0xEF,0x10,0x2A,0x1B,0xB5,0x00,0x1F,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, ceiling_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, ceiling_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB2,0x00,
  0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB5,0x00,0x1F,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.Monitor */
/* Method: getMonitor */
RANGE const unsigned char javax_safetycritical_Monitor_getMonitor[114] PROGMEM = {
  0x2A,0xB8,0x01,0xF2,0x87,0x4C,0x2B,0xC6,0x00,0x0A,0x2B,0xC1,0x00,0x3E,0x9A,0x00,
  0x5D,0xBB,0x00,0x60,0x59,0xBB,0x00,0x34,0x59,0x12,0x00,0x4A,0xB7,0x00,0x67,0x8F,
  0x2B,0xB6,0x01,0x03,0x00,0x0E,0x00,0x90,0x00,0x4C,0x00,0x1D,0x00,0x7B,0x00,0x91,
  0x00,0x22,0x00,0x39,0x00,0x5F,0x00,0x1A,0x00,0x6E,0x00,0x5E,0x00,0x83,0x00,0x68,
  0x00,0xBA,0x00,0x8B,0x00,0xC6,0x00,0x7B,0x01,0xED,0x00,0x4C,0x01,0xD9,0x00,0x36,
  0x00,0x65,0x00,0x54,0x01,0x88,0x00,0x67,0x01,0x0E,0x00,0x60,0x00,0x2A,0xB7,0x00,
  0x6D,0x96,0xB7,0x00,0x6E,0x9A,0xB7,0x00,0x34,0x81,0xBF,0x2B,0xC0,0x00,0x3E,0xB0,
  0x01,0x07
};

/* Class: javax.safetycritical.Monitor */
/* Method: lock */
RANGE const unsigned char javax_safetycritical_Monitor_lock[228] PROGMEM = {
  0x2A,0xB4,0x00,0x1F,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,0x03,0x00,0x02,0x03,0x00,0x71,0x00,
  0x02,0x00,0x8F,0x01,0xC2,0x00,0x37,0x01,0xCE,0xB8,0x01,0x58,0x27,0xB7,0x01,0x55,
  0x2C,0xB7,0x01,0x80,0x30,0x4C,0x2A,0xB4,0x00,0x1F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, owner_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, owner_f) - sizeof(Object)) << 3)) & 0xff),
  0xC7,0x00,0x09,
  0x2A,0x2B,0xB7,0x01,0x39,0x38,0x2A,0xB4,0x00,0x1F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, owner_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, owner_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xA6,0x00,
  0x67,0x2A,0x59,0xB4,0x00,0x1F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, synchCounter_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, synchCounter_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x1F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, synchCounter_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, synchCounter_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x1F,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, synchCounter_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, synchCounter_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0xA0,0x00,0x30,0x2A,0x2B,0xB8,0x01,
  0x06,0x3E,0xB7,0x00,0xCD,0x44,0xB5,0x00,0x1F,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB8,0x01,0x06,
  0x3E,0x2A,0xB4,0x00,0x1F,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x1F,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, ceiling_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, ceiling_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,
  0x01,0x38,0x4C,0x04,0x60,0xB7,0x00,0xCE,0x50,0x2A,0xB4,0x00,0x1F,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB9,0x00,0x02,0x00,0x02,0x03,0x00,0x71,0x00,0x03,0x00,0x8F,0x01,0xC3,0x00,0x37,
  0x01,0xCF,0xA7,0x00,0x3F,0x2B,0xB8,0x01,0x07,0x56,0x4D,0x2C,0x10,0x08,0xB5,0x00,
  0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x58,0x27,0x2A,0x2C,0xB7,0x01,0x54,0x5D,0x2A,0xB4,
  0x00,0x1F,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, clock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,0x02,0x00,0x02,0x03,0x00,0x71,0x00,0x03,0x00,
  0x8F,0x01,0xC3,0x00,0x37,0x01,0xCF,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xCA,
  0x61,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.Monitor */
/* Method: max */
RANGE const unsigned char javax_safetycritical_Monitor_max[13] PROGMEM = {
  0x1A,0x1B,0xA4,0x00,0x07,0x1A,0xAC,0x01,0x00,0x1B,0xAC,0x01,0x00
};

/* Class: javax.safetycritical.Monitor */
/* Method: setOwner */
RANGE const unsigned char javax_safetycritical_Monitor_setOwner[11] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x1F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, owner_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_Monitor_c, owner_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.Monitor */
/* Method: unlock */
/* unlock
 * param : 
 * return: void
 */
int16 javax_safetycritical_Monitor_unlock(int32 *fp)
{  
   int32* sp;
   int32 i_val2;
   unsigned char* cobj;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_7;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_25;
   int16 rval_m_29;
   int16 rval_m_33;
   int32 i_val1;
   int32 op2;
   int32 op1;
   unsigned char c_result;
   int8 b_val0;
   int8 msb_int8;
   int32 lsb_int32;
   int16 rval_m_76;
   int16 rval_m_87;
   int16 rval_m_91;
   int16 rval_m_96;
   int16 rval_m_107;
   int16 rval_m_111;
   int16 rval_m_133;
   int16 rval_m_137;
   int16 rval_m_148;
   int16 rval_m_166;
   int8 b_val2;
   int16 rval_m_172;
   int16 rval_m_184;
   int16 rval_m_189;
   int16 rval_m_198;
   const ConstantInfo* constant_;
   int16 rval_m_208;
   int16 rval_m_219;
   int16 rval_m_244;
   Object* ex_ception;
   int16 rval_m_256;
    int32 this;
    int32 msObj;
    int32 process;
   this = (int32)(*(fp + 0));
   msObj = (int32)(*(fp + 1));
   process = (int32)(*(fp + 2));
   sp = &fp[5]; /* make room for local VM state on the stack */
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> clock_f;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 7;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 113:
            methodIndex = 2;
            continue;
          case 143:
            methodIndex = 450;
            continue;
          case 55:
            methodIndex = 462;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_7 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_7 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_7;
      }
   sp -= 0;
   rval_m_25 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_25 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_25;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 29;
         goto throwNullPointer;
      }
   rval_m_29 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_GETCURRENTPROCESS, sp);
   if (rval_m_29 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_29;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 33;
         goto throwNullPointer;
      }
   rval_m_33 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_33 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_33;
   }
   sp--;
   msObj = (int32)(*sp);
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> owner_f;
   i_val1 = msObj;
      op2 = i_val1;
      op1 = i_val2;
      c_result = (op1 != op2);
      if (c_result) {
         goto L205;
      }
   i_val2 = this;
   i_val1 = i_val2;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> synchCounter_f;
   b_val0 = 1;
      msb_int8 = b_val0;
      lsb_int32 = i_val1;
      lsb_int32 -= msb_int8;
   i_val1 = lsb_int32;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> synchCounter_f = lsb_int32;
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> synchCounter_f;
      if (i_val2 != 0) {
         goto L249;
      }
   i_val2 = msObj;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_76 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MANAGEDSCHEDMETHODS_GETPRIORITYPARAMETER, sp);
   if (rval_m_76 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_76;
   }
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> priority_f;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 87;
         goto throwNullPointer;
      }
   rval_m_87 = enterMethodInterpreter(JAVAX_REALTIME_PRIORITYPARAMETERS_SETPRIORITY, sp);
   if (rval_m_87 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_87;
   }
   sp -= 0;
   rval_m_91 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_91 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_91;
   }
   i_val2 = this;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 96;
         goto throwNullPointer;
      }
   rval_m_96 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_GETPROCESSFROMLOCKQUEUE, sp);
   if (rval_m_96 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_96;
   }
   sp--;
   process = (int32)(*sp);
   i_val2 = process;
      if (i_val2 == 0) {
         goto L196;
      }
   i_val2 = this;
   i_val1 = process;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_107 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_107 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_107;
   }
   sp -= 2;
   rval_m_111 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MONITOR_SETOWNER, sp);
   if (rval_m_111 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_111;
   }
   i_val2 = this;
   i_val1 = i_val2;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> synchCounter_f;
   b_val0 = 1;
      msb_int8 = b_val0;
      lsb_int32 = i_val1;
      lsb_int32 += msb_int8;
   i_val1 = lsb_int32;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> synchCounter_f = lsb_int32;
   i_val2 = this;
   i_val1 = msObj;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_133 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MANAGEDSCHEDMETHODS_GETPRIORITYPARAMETER, sp);
   if (rval_m_133 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_133;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 137;
         goto throwNullPointer;
      }
   rval_m_137 = enterMethodInterpreter(JAVAX_REALTIME_PRIORITYPARAMETERS_GETPRIORITY, sp);
   if (rval_m_137 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_137;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> priority_f = lsb_int32;
   i_val2 = msObj;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_148 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MANAGEDSCHEDMETHODS_GETPRIORITYPARAMETER, sp);
   if (rval_m_148 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_148;
   }
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> priority_f;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> ceiling_f;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_166 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MONITOR_MAX, sp);
   if (rval_m_166 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_166;
   }
   b_val2 = 1;
      msb_int8 = b_val2;
   sp--;
      lsb_int32 = (int32)(*sp);
      lsb_int32 += msb_int8;
   i_val2 = lsb_int32;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 172;
         goto throwNullPointer;
      }
   rval_m_172 = enterMethodInterpreter(JAVAX_REALTIME_PRIORITYPARAMETERS_SETPRIORITY, sp);
   if (rval_m_172 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_172;
   }
   i_val2 = process;
   i_val1 = 1;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _javax_safetycritical_ScjManagedThreadProcess_c *)HEAP_REF(cobj, unsigned char*)) -> state_f = lsb_int32;
   sp -= 0;
   rval_m_184 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSTANCE, sp);
   if (rval_m_184 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_184;
   }
   i_val2 = process;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 189;
         goto throwNullPointer;
      }
   rval_m_189 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYSCHEDULER_INSERTREADYQUEUE, sp);
   if (rval_m_189 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_189;
   }
   goto L249;
   L196:
   i_val2 = this;
   i_val1 = 0;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_198 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MONITOR_SETOWNER, sp);
   if (rval_m_198 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_198;
   }
   goto L249;
   L205:
   constant_ = &constants[75];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val2 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_208 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_208 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_208;
   }
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> clock_f;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 219;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 113:
            methodIndex = 3;
            continue;
          case 143:
            methodIndex = 451;
            continue;
          case 55:
            methodIndex = 463;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_219 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_219 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_219;
      }
   if (handleNewClassIndex(sp, 48) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   constant_ = &constants[76];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val1 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_244 = enterMethodInterpreter(JAVA_LANG_ILLEGALMONITORSTATEEXCEPTION_INIT__, sp);
   if (rval_m_244 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_244;
   }
   ex_ception = (Object*) (pointer) *(sp - 1);
   excep = getClassIndex(ex_ception);
   pc = 248;
   sp--;
   goto throwIt;
   L249:
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_Monitor_c *)HEAP_REF(cobj, unsigned char*)) -> clock_f;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 256;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 113:
            methodIndex = 3;
            continue;
          case 143:
            methodIndex = 451;
            continue;
          case 55:
            methodIndex = 463;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_256 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_256 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_256;
      }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[314], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.PeriodicEventHandler */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_PeriodicEventHandler_init_[11] PROGMEM = {
  0x2A,0x2B,0x2C,0x2D,0xB7,0x00,0xF0,0x08,0xB1,0x01,0x0F
};

/* Class: javax.safetycritical.PriorityFrame */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_PriorityFrame_init_[72] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0F,0x2A,0xBB,0x01,0x0C,0x59,0x1B,0xB7,0x01,0x3F,0x14,0xB5,
  0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x68,0x59,0x1B,0xB7,0x01,0x8A,0x1A,0xB5,
  0x00,0x6F,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x06,0x59,0x1B,0xB7,0x01,0x4B,0x1F,0xB5,
  0x00,0x6F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x06,0x59,0x1B,0xB7,0x01,0x4B,0x1F,0xB5,
  0x00,0x6F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityFrame */
/* Method: addProcess */
RANGE const unsigned char javax_safetycritical_PriorityFrame_addProcess[238] PROGMEM = {
  0x2B,0xB7,0x01,0x80,0x2C,0xC1,0x00,0x54,0x99,0x00,0x5B,0x2B,0xB7,0x01,0x80,0x2C,
  0xC0,0x00,0x54,0x4D,0x2C,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0xA4,0xB7,0x00,
  0xCB,0x3A,0x4E,0x2D,0xB7,0x00,0xB2,0x3E,0x09,0x94,0x9A,0x00,0x22,0x2D,0xB7,0x00,
  0xB3,0x44,0x9A,0x00,0x1A,0x2B,0x04,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x45,0x4B,0xA7,0x00,0xA2,0x2B,0x07,0xB5,0x00,
  0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x90,0x4E,
  0xA7,0x00,0x8B,0x2B,0xB7,0x01,0x80,0x2C,0xC1,0x00,0x9C,0x99,0x00,0x1A,0x2B,0x04,
  0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,
  0x45,0x4B,0xA7,0x00,0x69,0x2B,0xB7,0x01,0x80,0x2C,0xC1,0x00,0xE0,0x99,0x00,0x0E,
  0x2B,0x06,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x53,0x2B,0xB7,0x01,0x80,0x2C,
  0xC1,0x00,0xB2,0x99,0x00,0x1A,0x2B,0x07,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,
  0x00,0x6F,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x90,0x4E,0xA7,0x00,0x31,0x2B,0xB7,0x01,
  0x80,0x2C,0xC1,0x00,0x18,0x99,0x00,0x1A,0x2B,0x04,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x45,0x4B,0xA7,0x00,0x0F,0xBB,
  0x01,0x1C,0x59,0x12,0x00,0x4D,0xB7,0x00,0x32,0x5B,0xBF,0xB1,0x01,0x0F
};

/* Class: javax.safetycritical.PriorityFrame */
/* Method: removeFromQueue */
RANGE const unsigned char javax_safetycritical_PriorityFrame_removeFromQueue[51] PROGMEM = {
  0x2A,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x49,0x66,0x2A,0xB4,0x00,0x6F,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x95,0x69,0x2A,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,
  0xB7,0x01,0x50,0x6A,0x2A,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0x50,0x6A,
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_PriorityQueue_init_[41] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0C,0x2A,0x03,0xB5,0x00,0x86,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1B,0x04,
  0x60,0xBC,0x00,0x2B,0xB5,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x47,0x13,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: exchange */
RANGE const unsigned char javax_safetycritical_PriorityQueue_exchange[41] PROGMEM = {
  0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x2E,0x3E,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0x4F,0x2A,0xB4,0x00,0x86,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x1D,0x4F,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: extractMax */
/* extractMax
 * param : 
 * return: javax.safetycritical.ScjProcess
 */
int16 javax_safetycritical_PriorityQueue_extractMax(int32 *fp)
{  
   int32* sp;
   int32 i_val3;
   unsigned char* cobj;
   int8 b_val2;
   int32 i_val2;
   int8 b_val1;
   int8 index_int8;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_25;
   int32 i_val1;
   int32 i_val0;
   int32 index_int32;
   int32 lsb_int32;
   int8 msb_int8;
   int16 rval_m_72;
    int32 this;
    int32 max;
   this = (int32)(*(fp + 0));
   max = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val3 = this;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   b_val2 = 1;
      if (i_val3 >= b_val2) {
         goto L15;
      }
   i_val3 = 0;
   *((int32*)fp) = i_val3;
   return -1;
   L15:
   i_val3 = this;
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   b_val1 = 1;
      index_int8 = b_val1;
      cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 24;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val2 = *((uint32 *) cobj);
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_25 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYQUEUE_GETSCJPROCESS, sp);
   if (rval_m_25 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_25;
   }
   sp--;
   max = (int32)(*sp);
   i_val3 = this;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   b_val2 = 1;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
      index_int32 = i_val0;
      cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 52;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val1 = *((uint32 *) cobj);
      lsb_int32 = i_val1;
      index_int8 = b_val2;
      cobj = (unsigned char *) (pointer)i_val3;
      if (cobj == 0) {
         pc = 53;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
      *((uint32 *) cobj) = lsb_int32;
   i_val3 = this;
   i_val2 = i_val3;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   b_val1 = 1;
      msb_int8 = b_val1;
      lsb_int32 = i_val2;
      lsb_int32 -= msb_int8;
   i_val2 = lsb_int32;
      lsb_int32 = i_val2;
cobj = (unsigned char *) (pointer)i_val3;
      ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f = lsb_int32;
   i_val3 = this;
   i_val2 = 1;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_72 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYQUEUE_HEAPIFY, sp);
   if (rval_m_72 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_72;
   }
   i_val3 = max;
   *((int32*)fp) = i_val3;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[321], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.PriorityQueue */
/* Method: find */
RANGE const unsigned char javax_safetycritical_PriorityQueue_find[42] PROGMEM = {
  0x04,0x3D,0xA7,0x00,0x17,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0x1B,0xA0,
  0x00,0x07,0x1C,0xAC,0x01,0x01,0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,0x86,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA4,0xFF,0xE4,0x11,0xFC,0x19,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: getScjProcess */
RANGE const unsigned char javax_safetycritical_PriorityQueue_getScjProcess[79] PROGMEM = {
  0x1B,0x11,0xFC,0x19,0xA0,0x00,0x07,0x01,0xB0,0x01,0x11,0x1B,0x10,0xFE,0xA0,0x00,
  0x10,0xB8,0x01,0x58,0x4E,0xB4,0x00,0x7D,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x11,0x1B,0x02,
  0xA0,0x00,0x0C,0xB2,0x00,0x54,0x22,
  /* offset: 16, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) & 0xff),
  0xB0,0x01,0x11,0x1B,0x10,0x14,0x6C,
  0x3D,0x1B,0x10,0x14,0x70,0x3E,0xB2,0x00,0x13,0x22,
  /* offset: 17, 120*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missionSet_f) << 3)) & 0xff),
  0x1C,0x32,0xB4,0x00,
  0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x67,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedSchedulableSet_c, scjProcesses_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x32,0xB0,0x01,0x11
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: heapify */
RANGE const unsigned char javax_safetycritical_PriorityQueue_heapify[143] PROGMEM = {
  0x2A,0x1B,0xB7,0x01,0x46,0x28,0x3D,0x2A,0x1B,0xB7,0x01,0x4A,0x2A,0x3E,0x1C,0x2A,
  0xB4,0x00,0x86,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA3,0x00,0x2C,0x2A,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0xB7,0x01,0x43,0x2C,0x2A,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,
  0x2E,0xB7,0x01,0x43,0x2C,0xB7,0x01,0x7E,0x30,0x9E,0x00,0x09,0x1C,0x36,0x04,0xA7,
  0x00,0x06,0x1B,0x36,0x04,0x1D,0x2A,0xB4,0x00,0x86,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA3,0x00,0x2A,
  0x2A,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x2E,0xB7,0x01,0x43,0x2C,0x2A,0x2A,
  0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,0x2E,0xB7,0x01,0x43,0x2C,0xB7,0x01,0x7E,
  0x30,0x9E,0x00,0x06,0x1D,0x36,0x04,0x15,0x04,0x1B,0x9F,0x00,0x12,0x2A,0x1B,0x15,
  0x04,0xB7,0x01,0x40,0x36,0x2A,0x15,0x04,0xB7,0x01,0x44,0x38,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: insert */
/* insert
 * param : javax.safetycritical.ScjProcess
 * return: void
 */
int16 javax_safetycritical_PriorityQueue_insert(int32 *fp)
{  
   int32* sp;
   int32 i_val4;
   unsigned char* cobj;
   int8 b_val3;
   int8 msb_int8;
   int32 lsb_int32;
   int32 i_val3;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 s_val3;
   int16 rval_m_24;
   Object* ex_ception;
   int8 b_val2;
   int32 i_val2;
   int32 i_val1;
   int32 i_val0;
   int16 rval_m_73;
   int32 index_int32;
   int16 rval_m_81;
   int16 rval_m_101;
   int16 rval_m_106;
   Object* i_obj;
   int16 rval_m_111;
   int32 msb_int32;
    int32 this;
    int32 obj;
    int32 i;
   this = (int32)(*(fp + 0));
   obj = (int32)(*(fp + 1));
   i = (int32)(*(fp + 2));
   sp = &fp[5]; /* make room for local VM state on the stack */
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   b_val3 = 1;
      msb_int8 = b_val3;
      lsb_int32 = i_val4;
      lsb_int32 += msb_int8;
   i_val4 = lsb_int32;
   i_val3 = this;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
      cobj = (unsigned char *) (pointer)i_val3;
      if (cobj == 0) {
         pc = 16;
         goto throwNullPointer;
      }
   s_val3 = *(uint16*) ((HEAP_REF(cobj, unsigned char*)) + sizeof(Object));
      if (i_val4 != s_val3) {
         goto L29;
      }
   if (handleNewClassIndex(sp, 1) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val4 = *(sp - 1);
   *sp = (int32)i_val4;
   sp++;
   sp -= 1;
   rval_m_24 = enterMethodInterpreter(JAVA_LANG_INDEXOUTOFBOUNDSEXCEPTION_INIT_, sp);
   if (rval_m_24 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_24;
   }
   ex_ception = (Object*) (pointer) *(sp - 1);
   excep = getClassIndex(ex_ception);
   pc = 28;
   sp--;
   goto throwIt;
   L29:
   i_val4 = this;
   i_val3 = i_val4;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   b_val2 = 1;
      msb_int8 = b_val2;
      lsb_int32 = i_val3;
      lsb_int32 += msb_int8;
   i_val3 = lsb_int32;
      lsb_int32 = i_val3;
cobj = (unsigned char *) (pointer)i_val4;
      ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f = lsb_int32;
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   i = i_val4;
   goto L86;
   L56:
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   i_val3 = (int32)i;
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   i_val1 = this;
   i_val0 = (int32)i;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   rval_m_73 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYQUEUE_PARENT, sp);
   if (rval_m_73 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_73;
   }
   sp--;
      index_int32 = (int32)(*sp);
   sp--;
      cobj = (unsigned char *) (pointer) *sp;
      if (cobj == 0) {
         pc = 77;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val4 = *((uint32 *) cobj);
      lsb_int32 = i_val4;
   sp--;
      index_int32 = (int32)(*sp);
   sp--;
      cobj = (unsigned char *) (pointer) *sp;
      if (cobj == 0) {
         pc = 78;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
      *((uint32 *) cobj) = lsb_int32;
   i_val4 = this;
   i_val3 = (int32)i;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   sp -= 2;
   rval_m_81 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYQUEUE_PARENT, sp);
   if (rval_m_81 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_81;
   }
   sp--;
   i = (int32)(*sp);
   L86:
   i_val4 = (int32)i;
   b_val3 = 1;
      if (i_val4 <= b_val3) {
         goto L118;
      }
   i_val4 = this;
   i_val3 = this;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   i_val2 = this;
   i_val1 = (int32)i;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_101 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYQUEUE_PARENT, sp);
   if (rval_m_101 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_101;
   }
   sp--;
      index_int32 = (int32)(*sp);
   sp--;
      cobj = (unsigned char *) (pointer) *sp;
      if (cobj == 0) {
         pc = 105;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val4 = *((uint32 *) cobj);
   *sp = (int32)i_val4;
   sp++;
   sp -= 2;
   rval_m_106 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_PRIORITYQUEUE_GETSCJPROCESS, sp);
   if (rval_m_106 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_106;
   }
   i_val4 = obj;
   *sp = (int32)i_val4;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 111;
         goto throwNullPointer;
      }
   rval_m_111 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_COMPARETO, sp);
   if (rval_m_111 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_111;
   }
   sp--;
      msb_int32 = (int32)(*sp);
      if (msb_int32 < 0) {
   yieldToScheduler(sp);
         goto L56;
      }
   L118:
   i_val4 = this;
cobj = (unsigned char *) (pointer)i_val4;
   i_val4 = ((struct _javax_safetycritical_PriorityQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   i_val3 = (int32)i;
   i_val2 = obj;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 127;
         goto throwNullPointer;
      }
   i_val2 = ((struct _javax_safetycritical_ScjManagedThreadProcess_c *)HEAP_REF(cobj, unsigned char*)) -> index_f;
      lsb_int32 = i_val2;
      index_int32 = i_val3;
      cobj = (unsigned char *) (pointer)i_val4;
      if (cobj == 0) {
         pc = 133;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
      *((uint32 *) cobj) = lsb_int32;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[325], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.PriorityQueue */
/* Method: left */
RANGE const unsigned char javax_safetycritical_PriorityQueue_left[6] PROGMEM = {
  0x05,0x1B,0x68,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: makeEmptyTree */
RANGE const unsigned char javax_safetycritical_PriorityQueue_makeEmptyTree[23] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x0C,0x2B,0x1C,0x11,0xFC,0x19,0x4F,0x84,0x02,0x01,0x1C,0x2B,
  0xBE,0xA1,0xFF,0xF4,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: parent */
RANGE const unsigned char javax_safetycritical_PriorityQueue_parent[6] PROGMEM = {
  0x1B,0x05,0x6C,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: remove */
RANGE const unsigned char javax_safetycritical_PriorityQueue_remove[76] PROGMEM = {
  0x2B,0xC7,0x00,0x06,0xB1,0x01,0x03,0x2A,0x2B,0xB4,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,
  0x01,0x42,0x6E,0x3D,0x1C,0x11,0xFC,0x19,0x9F,0x00,0x31,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2A,0xB4,0x00,0x86,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x86,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2E,0x4F,0x2A,0x59,0xB4,0x00,0x86,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xB5,0x00,0x86,
  0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB7,0x01,0x44,0x38,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityQueue */
/* Method: right */
RANGE const unsigned char javax_safetycritical_PriorityQueue_right[8] PROGMEM = {
  0x05,0x1B,0x68,0x04,0x60,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_init_[47] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0D,0x2A,0x1B,0xBC,0x00,0x2B,0xB5,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0x02,0xB5,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x4F,0x16,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: addProcess */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_addProcess[267] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x2A,0x2A,0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0xBE,0x04,0x64,0xA2,0x00,0xD7,0x2A,0x59,
  0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,
  0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x3E,0x03,0x36,0x04,0xA7,0x00,0x4C,0x2A,0x2A,0xB4,0x00,
  0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,0x2E,0xB7,0x01,0x4E,0x2D,0x3A,0x05,0x19,0x05,0xC7,
  0x00,0x0F,0xBB,0x01,0x1C,0x59,0x12,0x00,0x4E,0xB7,0x00,0x32,0x35,0xBF,0x2C,0xB7,
  0x01,0x80,0x38,0xB8,0x01,0x06,0x3E,0xB7,0x00,0xCD,0x44,0x19,0x05,0xB7,0x01,0x80,
  0x38,0xB8,0x01,0x06,0x3E,0xB7,0x00,0xCD,0x44,0xA4,0x00,0x09,0x15,0x04,0x3E,0xA7,
  0x00,0x12,0x84,0x04,0x01,0x15,0x04,0x2A,0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0xA1,0xFF,
  0xAE,0x1D,0x2A,0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x9F,0x00,0x2E,0x2A,0xB4,0x00,0x03,
  0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x36,0x04,0xA7,0x00,0x1C,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,
  0x04,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,0x04,0x64,0x2E,0x4F,0x84,0x04,
  0xFF,0x15,0x04,0x1D,0xA3,0xFF,0xE4,0x2C,0x2B,0xB5,0x00,0x54,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x2C,0xB4,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0x4F,0x2A,
  0x59,0xB4,0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,
  0x00,0x0F,0xBB,0x00,0x02,0x59,0x12,0x00,0x4F,0xB7,0x00,0x36,0x55,0xBF,0xB2,0x00,
  0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0x56,0xB1,0x01,0x27
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: getNextProcess */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_getNextProcess[83] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x42,0x2A,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0xB7,
  0x01,0x4E,0x2D,0x4E,0x2D,0xB4,0x00,0x54,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xA6,0x00,0x25,0x2D,
  0x01,0xB5,0x00,0x54,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB7,0x01,0x51,0x5F,0x2A,0x59,0xB4,
  0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xB5,0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2D,0xB0,0x01,
  0x1B,0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0xA4,0xFF,0xB9,0x01,
  0xB0,0x01,0x1B
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: getScjProcess */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_getScjProcess[63] PROGMEM = {
  0x1B,0x11,0xFC,0x19,0xA0,0x00,0x07,0x01,0xB0,0x01,0x11,0x1B,0x10,0xFE,0xA0,0x00,
  0x10,0xB8,0x01,0x58,0x64,0xB4,0x00,0x7D,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x11,0x1B,0x02,
  0xA0,0x00,0x0C,0xB2,0x00,0x54,0x22,
  /* offset: 16, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) & 0xff),
  0xB0,0x01,0x11,0x1B,0x10,0x14,0x6C,
  0x3D,0x1B,0x10,0x14,0x70,0x3E,0x1C,0x1D,0xB8,0x01,0x1B,0x6F,0xB0,0x01,0x11
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: makeEmptyQueue */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_makeEmptyQueue[23] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x0C,0x2B,0x1C,0x11,0xFC,0x19,0x4F,0x84,0x02,0x01,0x1C,0x2B,
  0xBE,0xA1,0xFF,0xF4,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: removeProcess */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_removeProcess[71] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x37,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0x2B,0xB4,
  0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0xA0,0x00,0x21,0x2A,0x1C,0xB7,0x01,0x51,0x5F,0x2B,0x01,
  0xB5,0x00,0x54,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x59,0xB4,0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,
  0xB5,0x00,0x03,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queueSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0xA4,0xFF,0xC4,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityQueueForLockAndWait */
/* Method: reorderSet */
RANGE const unsigned char javax_safetycritical_PriorityQueueForLockAndWait_reorderSet[78] PROGMEM = {
  0x1B,0x3D,0xA7,0x00,0x1A,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2A,0xB4,0x00,
  0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x04,0x60,0x2E,0x4F,0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,
  0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xA4,0xFF,0xDF,0x2A,0xB4,0x00,0x03,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, queue_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x11,0xFC,0x19,0x4F,0x2A,0x59,0xB4,0x00,0x03,
  0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xB5,0x00,0x03,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityQueueForLockAndWait_c, tail_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_init_[176] PROGMEM = {
  0x2A,0xB7,0x00,0xCF,0x27,0x2A,0x01,0xB5,0x00,0x7D,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB2,0x00,0x43,
  0x20,
  /* offset: 9, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIORITY_SCHEDULER_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIORITY_SCHEDULER_STACK_SIZE_f) << 3)) & 0xff),
  0xBC,0x00,0x2B,0x4C,0x2A,0xBB,0x00,0xDE,0x59,0xB2,0x00,0x43,0x20,
  /* offset: 10, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_PRIORITY_QUEUE_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_PRIORITY_QUEUE_SIZE_f) << 3)) & 0xff),
  0xB7,0x01,0x3C,0x35,0xB5,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x01,0x0E,
  0x59,0xB7,0x01,0x5E,0x3C,0xB5,0x00,0x7D,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, prioritySchedulerImpl_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, prioritySchedulerImpl_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, prioritySchedulerImpl_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, prioritySchedulerImpl_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB8,0x01,0xC5,0x3F,0x2A,0xB8,0x00,0xAC,0x45,0xB5,0x00,0x7D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x01,0x04,0x59,0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0xA2,0x4F,0xB5,0x00,0x7D,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, now_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, now_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xB4,0x00,0x7D,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, now_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, now_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xD4,0x54,0x57,0x2A,0xBB,0x00,0xF8,0x59,
  0x09,0x03,0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xDA,0x5A,0xB5,0x00,0x7D,
  0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, timeGrain_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, timeGrain_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, timeGrain_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, timeGrain_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xD3,0x5F,0x57,0x2A,0xB3,0x00,0x7D,0x22,
  /* offset: 16, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: addOuterMostSeq */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_addOuterMostSeq[51] PROGMEM = {
  0x2B,0xB8,0x01,0x03,0x6A,0x4D,0x2C,0x10,0xFE,0xB5,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,
  0xB3,0x00,0x4E,0x22,
  /* offset: 11, -32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, missSeqProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, missSeqProcess_f) << 3)) & 0xff),
  0x2A,0x2B,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB5,0x00,
  0x7D,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, outerMostSeqProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xB7,0x01,0x3D,0x7D,
  0xB1,0x01,0x07
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: addProcessToLockQueue */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_addProcessToLockQueue[22] PROGMEM = {
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0x2C,0xB7,
  0x01,0x4C,0xFE,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: getCurrentProcess */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_getCurrentProcess[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: getProcessFromLockQueue */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_getProcessFromLockQueue[21] PROGMEM = {
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,
  0x4D,0x04,0xB0,0x01,0x07
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: insertReadyQueue */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_insertReadyQueue[21] PROGMEM = {
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,
  0x45,0xBD,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: instance */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_instance[32] PROGMEM = {
  0xB2,0x00,0x7D,0x22,
  /* offset: 16, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) & 0xff),
  0xC7,0x00,0x11,0xBB,0x00,0xFA,0x59,0xB7,0x01,0x52,
  0x20,0xB3,0x00,0x7D,0x22,
  /* offset: 16, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) & 0xff),
  0xB2,0x00,0x7D,0x22,
  /* offset: 16, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, scheduler_f__f) << 3)) & 0xff),
  0xB0,0x01,0x01
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: move */
/* move
 * param : 
 * return: javax.safetycritical.ScjProcess
 */
int16 javax_safetycritical_PriorityScheduler_move(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   unsigned char* cobj;
   int32 i_val0;
   int32 op2;
   int32 op1;
   unsigned char c_result;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_36;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_57;
   int16 rval_m_99;
   int32 rval_99;
   int16 rval_m_118;
   int32 lsb_int32;
   int16 rval_m_147;
   int32 rval_147;
   int16 rval_m_166;
   int16 rval_m_183;
   int32 rval_183;
   int16 rval_m_206;
   int32 msb_int32;
   int16 rval_m_226;
   int16 rval_m_336;
   int16 rval_m_340;
    int32 this;
    int32 process;
    int32 nextProcess;
   this = (int32)(*(fp + 0));
   process = (int32)(*(fp + 1));
   nextProcess = (int32)(*(fp + 2));
   sp = &fp[5]; /* make room for local VM state on the stack */
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> current_f;
   i_val0 = ((struct _staticClassFields_c *)classData) -> idleProcess_f;
      op2 = i_val0;
      op1 = i_val1;
      c_result = (op1 != op2);
      if (c_result) {
         goto L43;
      }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 23;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> readyQueue_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> current_f;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 36;
         goto throwNullPointer;
      }
   rval_m_36 = javax_safetycritical_PriorityQueue_insert(sp);
   if (rval_m_36 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_36;
   }
   goto L86;
   L43:
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> current_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 57;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 30:
            methodIndex = 359;
            continue;
          case 121:
            methodIndex = 361;
            continue;
          case 37:
            methodIndex = 364;
            continue;
          case 115:
            methodIndex = 366;
            continue;
          case 15:
            methodIndex = 368;
            continue;
          case 84:
            methodIndex = 385;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_57 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_57 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_57;
      }
   L86:
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 93;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> sleepingQueue_f;
      if (i_val1 == 0) {
         pc = 99;
         goto throwNullPointer;
      }
   rval_m_99 = javax_safetycritical_SleepingQueue_minimum(sp, i_val1);
   if (rval_m_99 == -1) {
   rval_99 = *(int32*)sp;
   i_val1 = rval_99;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_99;
   }
   process = i_val1;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> rtClock_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> now_f;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 118;
         goto throwNullPointer;
      }
   rval_m_118 = javax_realtime_RealtimeClock_getTime(sp);
   if (rval_m_118 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_118;
   }
   sp--;
dummy = (int32)(*sp);
   goto L188;
   L126:
   i_val1 = process;
   i_val0 = 1;
      lsb_int32 = i_val0;
cobj = (unsigned char *) (pointer)i_val1;
      ((struct _javax_safetycritical_ScjManagedThreadProcess_c *)HEAP_REF(cobj, unsigned char*)) -> state_f = lsb_int32;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 141;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> sleepingQueue_f;
      if (i_val1 == 0) {
         pc = 147;
         goto throwNullPointer;
      }
   rval_m_147 = javax_safetycritical_SleepingQueue_extractMin(sp, i_val1);
   if (rval_m_147 == -1) {
   rval_147 = *(int32*)sp;
   i_val1 = rval_147;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_147;
   }
   nextProcess = i_val1;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 159;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> readyQueue_f;
   i_val0 = nextProcess;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 166;
         goto throwNullPointer;
      }
   rval_m_166 = javax_safetycritical_PriorityQueue_insert(sp);
   if (rval_m_166 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_166;
   }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 177;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> sleepingQueue_f;
      if (i_val1 == 0) {
         pc = 183;
         goto throwNullPointer;
      }
   rval_m_183 = javax_safetycritical_SleepingQueue_minimum(sp, i_val1);
   if (rval_m_183 == -1) {
   rval_183 = *(int32*)sp;
   i_val1 = rval_183;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_183;
   }
   process = i_val1;
   L188:
   i_val1 = process;
      if (i_val1 == 0) {
         goto L213;
      }
   i_val1 = process;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_ScjManagedThreadProcess_c *)HEAP_REF(cobj, unsigned char*)) -> next_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> now_f;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 206;
         goto throwNullPointer;
      }
   rval_m_206 = enterMethodInterpreter(JAVAX_REALTIME_HIGHRESOLUTIONTIME_COMPARETO, sp);
   if (rval_m_206 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_206;
   }
   sp--;
      msb_int32 = (int32)(*sp);
      if (msb_int32 <= 0) {
   yieldToScheduler(sp);
         goto L126;
      }
   L213:
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 220;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> readyQueue_f;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 226;
         goto throwNullPointer;
      }
   rval_m_226 = javax_safetycritical_PriorityQueue_extractMax(sp);
   if (rval_m_226 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_226;
   }
   sp--;
   nextProcess = (int32)(*sp);
   i_val1 = nextProcess;
   i_val0 = 2;
      lsb_int32 = i_val0;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 233;
         goto throwNullPointer;
      }
      ((struct _javax_safetycritical_ScjManagedThreadProcess_c *)HEAP_REF(cobj, unsigned char*)) -> state_f = lsb_int32;
   i_val1 = this;
   i_val0 = nextProcess;
      lsb_int32 = i_val0;
cobj = (unsigned char *) (pointer)i_val1;
      ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> current_f = lsb_int32;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> current_f;
   i_val0 = ((struct _staticClassFields_c *)classData) -> idleProcess_f;
      op2 = i_val0;
      op1 = i_val1;
      c_result = (op1 != op2);
      if (c_result) {
         goto L362;
      }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 270;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> sleepingQueue_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 276;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
      if (i_val1 != 0) {
         goto L362;
      }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 292;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> waitQueue_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 298;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityQueueForLockAndWait_c *)HEAP_REF(cobj, unsigned char*)) -> queueSize_f;
      if (i_val1 != 0) {
         goto L362;
      }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> pFrame_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 314;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityFrame_c *)HEAP_REF(cobj, unsigned char*)) -> lockQueue_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 320;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_PriorityQueueForLockAndWait_c *)HEAP_REF(cobj, unsigned char*)) -> queueSize_f;
      if (i_val1 != 0) {
         goto L362;
      }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> current_f;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 336;
         goto throwNullPointer;
      }
   rval_m_336 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SCJPROCESS_GETTARGET, sp);
   if (rval_m_336 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_336;
   }
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 340;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 138:
            methodIndex = 241;
            continue;
          case 78:
            methodIndex = 304;
            continue;
          case 12:
            methodIndex = 273;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_340 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_340 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_340;
      }
   i_val1 = 0;
   *((int32*)fp) = i_val1;
   return -1;
   L362:
   i_val1 = nextProcess;
   *((int32*)fp) = i_val1;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[345], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: moveToNext */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_moveToNext[37] PROGMEM = {
  0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x41,
  0xA8,0x4C,0x2B,0x05,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB5,0x00,0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: processStart */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_processStart[54] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0x4C,0x2A,0xBB,0x00,0x24,0x59,0x01,0x01,0xB7,0x02,
  0x03,0x8B,0xB5,0x00,0x7D,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x01,0xC6,0x90,0x2B,0xB7,0x01,
  0xC3,0x93,0x2B,0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, mainProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0xC9,0x96,0x2B,0xB7,
  0x01,0xCA,0x9A,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: start */
RANGE const unsigned char javax_safetycritical_PriorityScheduler_start[32] PROGMEM = {
  0x2A,0x2A,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,
  0x41,0xA8,0xB5,0x00,0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x5B,0xB0,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PriorityScheduler */
/* Method: stop */
/* stop
 * param : vm.Process
 * return: void
 */
int16 javax_safetycritical_PriorityScheduler_stop(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int32 i_val0;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_8;
    int32 this;
    int32 current;
   this = (int32)(*(fp + 0));
   current = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val1 = current;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_PriorityScheduler_c *)HEAP_REF(cobj, unsigned char*)) -> mainProcess_f;
      if (i_val1 == 0) {
         pc = 8;
         goto throwNullPointer;
      }
   rval_m_8 = vm_Process_transferTo(sp, i_val1, i_val0);
   if (rval_m_8 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_8;
   }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[349], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: getDefaultMonitor */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_getDefaultMonitor[4] PROGMEM = {
  0x01,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: getDefaultMonitor */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_getDefaultMonitor_[8] PROGMEM = {
  0x2A,0xB7,0x01,0x5F,0x6E,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: getNextProcess */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_getNextProcess[95] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x18,0xB8,0x01,0x58,0x1B,0xB7,0x01,
  0x59,0x20,0x4C,0x2B,0xC6,0x00,0x25,0x2B,0xB6,0x00,0x11,0x00,0x02,0x00,0x54,0x01,
  0x87,0x00,0x79,0x01,0x6A,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0x29,0x2B,
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x07,0xB8,0x01,0x58,0x1B,0xB8,0x01,0x58,
  0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x5D,
  0x34,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0x29,0x01,0xB0,0x01,0x07
};

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: notify */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_notify[86] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x18,0x2B,0xB8,0x01,0x36,0x3C,0x4D,
  0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2C,0xB7,0x01,0x4D,0x64,0x4E,0x2D,0xC6,0x00,0x22,0x2D,0x10,0x08,0xB5,0x00,0x54,
  0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,
  0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0x2D,0xB7,0x01,0x4C,0x53,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,
  0x01,0xC3,0x29,0xB1,0x01,0x0F
};

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: notifyAll */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_notifyAll[111] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x18,0x2B,0xB8,0x01,0x36,0x3C,0x4D,
  0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2C,0xB7,0x01,0x4D,0x64,0x4E,0xA7,0x00,0x38,0x2D,0x10,0x08,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, lockQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0x2D,0xB7,0x01,0x4C,0x53,0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xB7,0x01,0x4D,0x64,0x4E,0x2D,0xC7,
  0xFF,0xCA,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0x29,0xB1,0x01,0x0F
};

/* Class: javax.safetycritical.PrioritySchedulerImpl */
/* Method: wait */
RANGE const unsigned char javax_safetycritical_PrioritySchedulerImpl_wait[101] PROGMEM = {
  0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC2,0x18,0x2B,0xB8,0x01,0x36,0x3C,0x4D,
  0x2C,0xB7,0x01,0x3A,0x42,0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,
  0x07,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, pFrame_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x6F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, waitQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xB8,0x01,0x58,0x1B,0xB4,0x00,0x7D,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityScheduler_c, current_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x4C,0x53,0xB8,0x01,0x58,0x1B,0xB7,0x01,0x5A,0x59,0xB2,0x00,
  0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,0xC3,0x29,0xB2,0x00,0x8F,0x22,
  /* offset: 17, 88*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, instance_f__f) << 3)) & 0xff),
  0xB7,0x01,
  0xCA,0x5C,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.PrivateMemory */
/* Method: <init> */
/* <init>
 * param : int, int, javax.realtime.MemoryArea, java.lang.String
 * return: void
 */
int16 javax_safetycritical_PrivateMemory_init_(int32 *fp)
{  
   int32* sp;
   int32 i_val4;
   int32 i_val3;
   int32 i_val2;
   int32 i_val1;
   int32 i_val0;
   int16 rval_m_6;
    int32 this;
    int32 size;
    int32 BackingStoreOfThisMemory;
    int32 backingStoreProvider;
    int32 label;
   this = (int32)(*(fp + 0));
   size = (int32)(*(fp + 1));
   BackingStoreOfThisMemory = (int32)(*(fp + 2));
   backingStoreProvider = (int32)(*(fp + 3));
   label = (int32)(*(fp + 4));
   sp = &fp[7]; /* make room for local VM state on the stack */
   i_val4 = this;
   i_val3 = (int32)size;
   i_val2 = (int32)BackingStoreOfThisMemory;
   i_val1 = backingStoreProvider;
   i_val0 = label;
   *sp = (int32)i_val4;
   sp++;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 5;
   rval_m_6 = javax_safetycritical_ManagedMemory_init_(sp);
   if (rval_m_6 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_6;
   }
   return -1;
}

/* Class: javax.safetycritical.ScjAperiodicEventHandlerProcess */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjAperiodicEventHandlerProcess_init_[10] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x7B,0x08,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjAperiodicEventHandlerProcess */
/* Method: gotoNextState */
RANGE const unsigned char javax_safetycritical_ScjAperiodicEventHandlerProcess_gotoNextState[133] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA0,0x00,0x48,0xB8,0x01,0x1A,0x18,0xB7,
  0x01,0x22,0x1E,0x99,0x00,0x32,0xB8,0x01,0x1A,0x18,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x0D,0x29,0xB8,0x01,0x1A,0x18,0xB4,
  0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x0C,0x2F,0x2A,0x10,0x06,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x0B,0x2A,0x06,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03,
  0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x07,0xA0,0x00,0x06,0xA7,0x00,0x26,0x2A,
  0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x08,0xA0,0x00,0x06,0xA7,0x00,0x17,0x2A,0x04,
  0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,
  0x45,0x39,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjManagedThreadProcess */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjManagedThreadProcess_init_[10] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x7B,0x08,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjManagedThreadProcess */
/* Method: gotoNextState */
RANGE const unsigned char javax_safetycritical_ScjManagedThreadProcess_gotoNextState[123] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA0,0x00,0x24,0xB8,0x01,0x1A,0x18,0xB4,
  0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x0D,0x25,
  0x2A,0x10,0x06,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x4F,0x2A,0xB4,0x00,0x54,
  0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x07,0xA0,0x00,0x12,0x2B,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,
  0x01,0x90,0x31,0xA7,0x00,0x35,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x07,0xA0,
  0x00,0x06,0xA7,0x00,0x26,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x08,0xA0,0x00,
  0x06,0xA7,0x00,0x17,0x2A,0x04,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x6F,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x45,0x3B,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjManagedThreadProcess */
/* Method: switchToArea */
RANGE const unsigned char javax_safetycritical_ScjManagedThreadProcess_switchToArea[34] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0x18,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, currentMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xFD,0x4F,0xB8,0x01,0xEC,0x55,0x57,0xB1,
  0x01,0x01
};

/* Class: javax.safetycritical.ScjMissionSequencerProcess */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjMissionSequencerProcess_init_[10] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x7B,0x08,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjMissionSequencerProcess */
/* Method: gotoNextState */
RANGE const unsigned char javax_safetycritical_ScjMissionSequencerProcess_gotoNextState[171] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA0,0x00,0x6E,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0xFE,0xA0,0x00,0x1F,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,
  0x01,0x00,0x13,0x03,0x00,0x8A,0x00,0xF1,0x00,0x4E,0x01,0x30,0x00,0x0C,0x01,0x11,
  0xA7,0x00,0x3A,0xB8,0x01,0x1A,0x24,0x4D,0x2C,0xC6,0x00,0x31,0x2A,0xB4,0x00,0x54,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,0x01,0x00,0x13,0x03,0x00,0x8A,0x00,0xF1,0x00,0x4E,0x01,
  0x30,0x00,0x0C,0x01,0x11,0xB8,0x01,0x1A,0x24,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x0D,0x2E,0x2A,0x10,0x06,0xB5,0x00,0x54,
  0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x07,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x07,0xA0,
  0x00,0x06,0xA7,0x00,0x26,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x08,0xA0,0x00,
  0x06,0xA7,0x00,0x17,0x2A,0x04,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x6F,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x45,0x3A,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjOneShotEventHandlerProcess */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjOneShotEventHandlerProcess_init_[10] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x7B,0x08,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjOneShotEventHandlerProcess */
/* Method: gotoNextState */
RANGE const unsigned char javax_safetycritical_ScjOneShotEventHandlerProcess_gotoNextState[97] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA0,0x00,0x24,0xB8,0x01,0x1A,0x18,0xB4,
  0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x0D,0x25,
  0x2A,0x10,0x06,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x35,0x2A,0xB4,0x00,0x54,
  0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x07,0xA0,0x00,0x06,0xA7,0x00,0x26,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x08,0xA0,0x00,0x06,0xA7,0x00,0x17,0x2A,0x04,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x45,0x31,0xB1,0x01,
  0x03
};

/* Class: javax.safetycritical.ScjPeriodicEventHandlerProcess */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjPeriodicEventHandlerProcess_init_[10] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x7B,0x08,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjPeriodicEventHandlerProcess */
/* Method: gotoNextState */
RANGE const unsigned char javax_safetycritical_ScjPeriodicEventHandlerProcess_gotoNextState[136] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA0,0x00,0x4B,0xB8,0x01,0x1A,0x18,0xB7,
  0x01,0x22,0x1E,0x99,0x00,0x24,0xB8,0x01,0x1A,0x18,0xB4,0x00,0x13,0x22,
  /* offset: 0, 40*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MyMission_c, msSetForMission_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x0D,0x29,0x2A,0x10,0x06,0xB5,0x00,
  0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x1C,0x2A,0x07,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xB7,0x01,0x86,0x2F,0x2B,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, sleepingQueue_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0x90,0x39,
  0xB1,0x01,0x03,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x07,0xA0,0x00,0x06,0xA7,
  0x00,0x26,0x2A,0xB4,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x10,0x08,0xA0,0x00,0x06,0xA7,0x00,
  0x17,0x2A,0x04,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x6F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_PriorityFrame_c, readyQueue_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB7,0x01,0x45,0x43,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess$1 */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjProcess_1_init_[16] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x00,0x48,0x0E,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess$1 */
/* Method: catchError */
RANGE const unsigned char javax_safetycritical_ScjProcess_1_catchError[89] PROGMEM = {
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x7D,0x65,0x2B,0xB5,0x00,0x5F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_ExceptionReporter_c, t_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_ExceptionReporter_c, t_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x00,0xF6,0x6F,0x4D,0x2C,0xC6,0x00,0x16,0x2C,0x2A,0xB4,0x00,0x72,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x7D,0x65,0xB7,0x00,0xFB,0x75,0xA7,0x00,0x2B,0x2A,0xB4,
  0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x7D,0x65,0xB8,0x01,0xE1,0x79,0xA7,0x00,0x19,
  0x4D,0xB2,0x00,0x43,0x22,
  /* offset: 10, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) & 0xff),
  0x2C,0xB9,0x00,0x01,0x01,0x0B,0x02,0x00,0x6D,
  0x01,0x9E,0x00,0x7A,0x01,0xA2,0xB1,0x01,0x07
};

RANGE const ExceptionHandler ex_javax_safetycritical_ScjProcess_1_catchError[1] PROGMEM = {
  { 18, 61, 64, 81}
};

/* Class: javax.safetycritical.ScjProcess$1 */
/* Method: run */
RANGE const unsigned char javax_safetycritical_ScjProcess_1_run[418] PROGMEM = {
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,
  0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x7C,0x1C,0xA7,0x01,0x0F,0x4C,0xB2,0x00,0x43,0x22,
  /* offset: 10, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) & 0xff),
  0x2B,0xB9,0x00,0x00,0x01,0x0B,0x02,0x00,0x6D,0x01,0x9D,0x00,0x7A,0x01,
  0xA1,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,
  0x54,0x99,0x00,0x2F,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,
  0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xA4,0x36,0x57,
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xBB,0x00,
  0x34,0x59,0x12,0x00,0x50,0xB7,0x00,0x67,0x44,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x6B,0x4B,0x12,0x00,0x51,0xB7,0x00,0x6D,
  0x51,0xB7,0x00,0x6E,0x54,0xB8,0x00,0x0A,0x58,0xA7,0x00,0xF6,0x4D,0x2A,0xB4,0x00,
  0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,0x54,0x99,0x00,0x2F,
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xA4,0x36,0x57,0x2A,0xB4,0x00,0x72,
  0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xBB,0x00,0x34,0x59,0x12,0x00,
  0x50,0xB7,0x00,0x67,0x44,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x6B,0x4B,0x12,0x00,0x51,0xB7,0x00,0x6D,0x51,0xB7,0x00,0x6E,
  0x54,0xB8,0x00,0x0A,0x58,0x2C,0xBF,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,
  0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,0x54,0x99,0x00,0x2F,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,
  0x54,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xA4,0x36,0x57,0x2A,0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xB5,0x00,
  0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0xBB,0x00,0x34,0x59,0x12,0x00,0x50,0xB7,0x00,0x67,0x44,0x2A,
  0xB4,0x00,0x72,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_1_c, this_0_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x6B,0x4B,
  0x12,0x00,0x51,0xB7,0x00,0x6D,0x51,0xB7,0x00,0x6E,0x54,0xB8,0x00,0x0A,0x58,0xB1,
  0x01,0x03
};

RANGE const ExceptionHandler ex_javax_safetycritical_ScjProcess_1_run[2] PROGMEM = {
  { 0, 24, 27, 24},
  { 0, 49, 172, -1}
};

/* Class: javax.safetycritical.ScjProcess$2 */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjProcess_2_init_[11] PROGMEM = {
  0x2A,0x2B,0x2C,0x2D,0xB7,0x01,0x3B,0x08,0xB1,0x01,0x0F
};

/* Class: javax.safetycritical.ScjProcess$2 */
/* Method: handleAsyncEvent */
RANGE const unsigned char javax_safetycritical_ScjProcess_2_handleAsyncEvent[8] PROGMEM = {
  0x2A,0xB7,0x01,0x77,0x16,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ScjProcess$2 */
/* Method: run */
RANGE const unsigned char javax_safetycritical_ScjProcess_2_run[17] PROGMEM = {
  0x2A,0xB6,0x00,0x08,0x00,0x02,0x00,0x74,0x01,0x75,0x00,0x4E,0x01,0x32,0xB1,0x01,
  0x01
};

/* Class: javax.safetycritical.ScjProcess$2 */
/* Method: yield */
/* yield
 * param : 
 * return: void
 */
int16 javax_safetycritical_ScjProcess_2_yield(int32 *fp)
{  
   int32* sp;
   int16 rval_m_0;
   sp = &fp[3]; /* make room for local VM state on the stack */
   L0:
   sp -= 0;
   rval_m_0 = n_vm_RealtimeClock_awaitNextTick(sp);
   if (rval_m_0 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_0;
   }
   yieldToScheduler(sp);
   goto L0;
}

/* Class: javax.safetycritical.ScjProcess$ExceptionReporter */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjProcess_ExceptionReporter_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0C,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ScjProcess$ExceptionReporter */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjProcess_ExceptionReporter_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0x78,0x22,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess$ExceptionReporter */
/* Method: run */
RANGE const unsigned char javax_safetycritical_ScjProcess_ExceptionReporter_run[30] PROGMEM = {
  0xB2,0x00,0x43,0x22,
  /* offset: 10, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x5F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_ExceptionReporter_c, t_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjProcess_ExceptionReporter_c, t_f) - sizeof(Object)) << 3)) & 0xff),
  0xB9,0x00,0x00,
  0x01,0x0B,0x02,0x00,0x6D,0x01,0x9D,0x00,0x7A,0x01,0xA1,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_ScjProcess_init_[186] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x21,0x2A,0x11,0xFC,0x19,0xB5,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0x01,0xB5,0x00,0x54,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, monitorLock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x01,0xB5,0x00,0x54,0x22,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_temp_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_temp_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0x03,0xB5,0x00,0x54,0x08,
  /* offset: 1, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, isNotified_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, isNotified_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB8,0x00,0xAC,0x2C,0xB5,0x00,0x54,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x01,0x04,0x59,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0xA2,0x36,0xB5,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB5,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0x03,0xB5,0x00,0x54,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, state_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0xBE,0x59,0x01,0xB7,0x01,
  0x79,0x41,0xB5,0x00,0x54,0x22,
  /* offset: 1, 72*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, exceptionReporter_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, exceptionReporter_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x00,0x24,0x59,0xBB,0x00,0xE4,
  0x59,0x2A,0xB7,0x01,0x71,0x4A,0x2C,0xB7,0x02,0x03,0x4D,0xB5,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x02,0x07,0x52,0x2A,0xB4,0x00,0x54,
  0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xD4,0x55,0x57,0x2A,
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x85,0x59,0x2A,0x2A,0xB4,0x00,0x54,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x84,0x5D,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: access$1 */
RANGE const unsigned char javax_safetycritical_ScjProcess_access_1[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x01,0x82,0x09,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: access$2 */
RANGE const unsigned char javax_safetycritical_ScjProcess_access_2[10] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 1, 72*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, exceptionReporter_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, exceptionReporter_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: compareTo */
RANGE const unsigned char javax_safetycritical_ScjProcess_compareTo[216] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x01,0x14,0x99,0x00,0x3C,0x2B,0xB4,0x00,
  0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x01,0x14,0x99,0x00,0x2F,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xCD,0xC9,0x2B,0xB4,
  0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0xCD,0xC9,0x64,0xAC,0x01,0x03,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,0x18,
  0x99,0x00,0x3C,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,0x18,0x99,0x00,0x2F,
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0x18,0xB4,0x00,0x0C,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0xCD,0xC9,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0x18,0xB4,0x00,
  0x0C,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, priority_f__f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xCD,0xC9,0x64,0xAC,0x01,0x03,0x2A,0xB4,0x00,0x54,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,0x64,0x99,0x00,0x3C,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xC1,0x00,0x64,0x99,0x00,0x2F,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0x64,
  0xB4,0x00,0x32,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xCD,0xC9,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0x64,0xB4,0x00,0x32,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, priority_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, priority_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xCD,0xC9,0x64,0xAC,
  0x01,0x03,0x11,0xFC,0x19,0xAC,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: createIdleProcess */
RANGE const unsigned char javax_safetycritical_ScjProcess_createIdleProcess[157] PROGMEM = {
  0xB2,0x00,0x54,0x22,
  /* offset: 16, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) & 0xff),
  0xC7,0x00,0x8E,0xBB,0x00,0xE8,0x59,0xBB,0x00,0x78,
  0x59,0x04,0xB7,0x00,0xCC,0xDB,0xBB,0x00,0xA4,0x59,0xBB,0x00,0xF8,0x59,0xB8,0x00,
  0xAC,0x2C,0xB7,0x00,0xDB,0xDD,0xB2,0x00,0x43,0x22,
  /* offset: 10, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, INFINITE_TIME_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, INFINITE_TIME_f) << 3)) & 0xff),
  0xB7,0x00,0xC8,0xE3,
  0xBB,0x00,0x76,0x59,0x05,0xB2,0x00,0x43,0x20,
  /* offset: 8, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_BACKING_STORE_f) << 3)) & 0xff),
  0x68,0x85,0x04,0xBC,0x00,
  0x2C,0x59,0x03,0xB2,0x00,0x43,0x20,
  /* offset: 9, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_PROCESS_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_PROCESS_STACK_SIZE_f) << 3)) & 0xff),
  0x85,0x50,0x05,0xB2,0x00,0x43,0x20,
  /* offset: 8, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_BACKING_STORE_f) << 3)) & 0xff),
  0x68,0x85,0x09,0x09,0xB7,0x01,0x97,0xEE,0xB7,0x01,0x74,0xF1,0x4B,0xBB,
  0x00,0xA8,0x59,0x2A,0xB2,0x00,0x43,0x20,
  /* offset: 9, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_PROCESS_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_PROCESS_STACK_SIZE_f) << 3)) & 0xff),
  0xBC,0x00,0x2B,0xB7,0x01,0x7B,
  0xF4,0x4C,0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB7,0x00,0xD4,0x55,0x57,0x2B,0x02,0xB5,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB3,0x00,
  0x54,0x22,
  /* offset: 16, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) & 0xff),
  0xB2,0x00,0x54,0x22,
  /* offset: 16, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, idleProcess_f) << 3)) & 0xff),
  0xB0,0x01,0x07
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: getTarget */
RANGE const unsigned char javax_safetycritical_ScjProcess_getTarget[10] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: gotoNextState */
RANGE const unsigned char javax_safetycritical_ScjProcess_gotoNextState[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: runLogic */
RANGE const unsigned char javax_safetycritical_ScjProcess_runLogic[86] PROGMEM = {
  0x2B,0xC1,0x01,0x14,0x99,0x00,0x1B,0x2B,0xC0,0x01,0x14,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,0xFA,0x73,0xA7,0x00,0x37,0x2B,
  0xC1,0x00,0x18,0x99,0x00,0x1B,0x2B,0xC0,0x00,0x18,0xB4,0x00,0x0C,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,0x00,0xFA,0x73,0xA7,0x00,0x18,0x2B,0xC0,
  0x00,0x64,0xB4,0x00,0x32,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, memInfo_f) - sizeof(Object)) << 3)) & 0xff),
  0xB4,0x00,0x04,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_MemoryInfo_c, privateMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB7,
  0x00,0xFA,0x73,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: setIndex */
RANGE const unsigned char javax_safetycritical_ScjProcess_setIndex[11] PROGMEM = {
  0x2A,0x1B,0xB5,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: setProcess */
RANGE const unsigned char javax_safetycritical_ScjProcess_setProcess[56] PROGMEM = {
  0x2B,0xC1,0x01,0x14,0x99,0x00,0x11,0x2B,0xC0,0x01,0x14,0x2A,0xB5,0x00,0x8A,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x23,0x2B,0xC1,0x00,0x18,0x99,0x00,0x11,0x2B,0xC0,0x00,0x18,
  0x2A,0xB5,0x00,0x0C,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xA7,0x00,0x0E,0x2B,0xC0,0x00,0x64,0x2A,0xB5,
  0x00,0x32,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedLongEventHandler_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: setRelease */
RANGE const unsigned char javax_safetycritical_ScjProcess_setRelease[236] PROGMEM = {
  0x2B,0xC1,0x00,0x54,0x99,0x00,0x50,0x2A,0x2B,0xC0,0x00,0x54,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0xA4,0xB7,0x00,0xCB,0x88,0xB5,0x00,0x54,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, start_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, start_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0x2B,0xC0,0x00,0x54,0xB4,0x00,0x8A,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ManagedEventHandler_c, release_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0xA4,0xB7,0x00,0xCA,
  0x8E,0xB5,0x00,0x54,0x22,
  /* offset: 0, -64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, period_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,
  0x00,0x54,0x22,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, start_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, start_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xA4,0x93,
  0x57,0xA7,0x00,0x98,0x2B,0xC1,0x00,0xB2,0x99,0x00,0x91,0x2B,0xC0,0x00,0xB2,0xB4,
  0x00,0x59,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_OneShotEventHandler_c, releaseTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_OneShotEventHandler_c, releaseTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xC1,0x00,0xF8,0x99,0x00,0x28,0x2B,0xC0,0x00,0xB2,0xB4,
  0x00,0x59,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_OneShotEventHandler_c, releaseTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_OneShotEventHandler_c, releaseTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x00,0xF8,0x4D,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2C,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xA4,0x93,0x57,0xA7,0x00,0x5C,
  0x2B,0xC0,0x00,0xB2,0xB4,0x00,0x59,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_OneShotEventHandler_c, releaseTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_OneShotEventHandler_c, releaseTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xC0,0x01,0x04,0x4D,0x2C,0xB8,
  0x00,0xAC,0x2C,0xBB,0x01,0x04,0x59,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, rtClock_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0xA2,0x36,0xB7,0x00,0xD4,0x55,0xB7,0x00,0xB0,0x9F,0x3E,0x1D,0x9C,0x00,0x21,0x2A,
  0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xBB,0x00,0xF8,0x59,0xB7,0x00,0xD8,0xA3,0x2A,0xB4,
  0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xA4,0x93,0x57,0xA7,0x00,0x0F,0x2A,0xB4,0x00,
  0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2C,0xB7,0x00,0xB8,0xA4,0xB1,0x01,0x07
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: start */
RANGE const unsigned char javax_safetycritical_ScjProcess_start[14] PROGMEM = {
  0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, process_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x02,0x07,0x52,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: switchToArea */
RANGE const unsigned char javax_safetycritical_ScjProcess_switchToArea[3] PROGMEM = {
  0xB1,0x01,0x01
};

/* Class: javax.safetycritical.ScjProcess */
/* Method: toString */
RANGE const unsigned char javax_safetycritical_ScjProcess_toString[47] PROGMEM = {
  0xBB,0x00,0x34,0x59,0x12,0x00,0x52,0xB7,0x00,0x67,0xB3,0x2A,0xB4,0x00,0x54,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, msObject_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x6B,0xB6,0x12,0x00,0x53,0xB7,0x00,0x6D,0xBC,0x2A,0xB4,0x00,
  0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x69,0xBF,0xB7,0x00,0x6E,0xC2,0xB0,0x01,0x03
};

/* Class: javax.safetycritical.Services */
/* Method: setCeiling */
RANGE const unsigned char javax_safetycritical_Services_setCeiling[19] PROGMEM = {
  0xBB,0x00,0x3E,0x59,0x1B,0xB7,0x01,0x35,0x41,0x4D,0x2C,0x2A,0xB7,0x01,0xF0,0x44,
  0xB1,0x01,0x05
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_SleepingQueue_init_[41] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0C,0x2A,0x03,0xB5,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1B,0x04,
  0x60,0xBC,0x00,0x2B,0xB5,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x01,0x92,0x13,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: exchange */
RANGE const unsigned char javax_safetycritical_SleepingQueue_exchange[41] PROGMEM = {
  0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x2E,0x3E,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1B,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0x4F,0x2A,0xB4,0x00,0x34,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x1D,0x4F,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: extractMin */
/* extractMin
 * param : 
 * return: javax.safetycritical.ScjProcess
 */
int16 javax_safetycritical_SleepingQueue_extractMin(int32 *fp,     int32 this)
{  
   int32* sp;
   int32 i_val3;
   unsigned char* cobj;
   int8 b_val2;
   int32 i_val2;
   int8 b_val1;
   int8 index_int8;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_25;
   int32 i_val1;
   int32 i_val0;
   int32 index_int32;
   int32 lsb_int32;
   int8 msb_int8;
   int16 rval_m_72;
    int32 min;
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val3 = this;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   b_val2 = 1;
      if (i_val3 >= b_val2) {
         goto L15;
      }
   i_val3 = 0;
   *((int32*)fp) = i_val3;
   return -1;
   L15:
   i_val3 = this;
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   b_val1 = 1;
      index_int8 = b_val1;
      cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 24;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val2 = *((uint32 *) cobj);
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_25 = javax_safetycritical_SleepingQueue_getScjProcess(sp);
   if (rval_m_25 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_25;
   }
   sp--;
   min = (int32)(*sp);
   i_val3 = this;
cobj = (unsigned char *) (pointer)i_val3;
   i_val3 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   b_val2 = 1;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
      index_int32 = i_val0;
      cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 52;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val1 = *((uint32 *) cobj);
      lsb_int32 = i_val1;
      index_int8 = b_val2;
      cobj = (unsigned char *) (pointer)i_val3;
      if (cobj == 0) {
         pc = 53;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
      *((uint32 *) cobj) = lsb_int32;
   i_val3 = this;
   i_val2 = i_val3;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
   b_val1 = 1;
      msb_int8 = b_val1;
      lsb_int32 = i_val2;
      lsb_int32 -= msb_int8;
   i_val2 = lsb_int32;
      lsb_int32 = i_val2;
cobj = (unsigned char *) (pointer)i_val3;
      ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f = lsb_int32;
   i_val3 = this;
   i_val2 = 1;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_72 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_SLEEPINGQUEUE_HEAPIFY, sp);
   if (rval_m_72 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_72;
   }
   i_val3 = min;
   *((int32*)fp) = i_val3;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[396], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.SleepingQueue */
/* Method: find */
RANGE const unsigned char javax_safetycritical_SleepingQueue_find[42] PROGMEM = {
  0x04,0x3D,0xA7,0x00,0x17,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0x1B,0xA0,
  0x00,0x07,0x1C,0xAC,0x01,0x01,0x84,0x02,0x01,0x1C,0x2A,0xB4,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA4,0xFF,0xE4,0x11,0xFC,0x19,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: getScjProcess */
/* getScjProcess
 * param : int
 * return: javax.safetycritical.ScjProcess
 */
int16 javax_safetycritical_SleepingQueue_getScjProcess(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int16 s_val0;
   int8 b_val0;
   int32 res_int32;
   int32 lsb_int32;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int32 i_val0;
   int32 index_int32;
   unsigned char* cobj;
    int32 processIdx;
    int32 missionIndex;
    int32 scjProcessIndex;
   processIdx = (int32)(*(fp + 1));
   missionIndex = (int32)(*(fp + 2));
   scjProcessIndex = (int32)(*(fp + 3));
   sp = &fp[6]; /* make room for local VM state on the stack */
   i_val1 = (int32)processIdx;
   s_val0 = -999;
      if (i_val1 != s_val0) {
         goto L11;
      }
   i_val1 = 0;
   *((int32*)fp) = i_val1;
   return -1;
   L11:
   i_val1 = (int32)processIdx;
   b_val0 = (signed char)-2;
      if (i_val1 != b_val0) {
         goto L26;
      }
   i_val1 = ((struct _staticClassFields_c *)classData) -> missSeqProcess_f;
   *((int32*)fp) = i_val1;
   return -1;
   L26:
   i_val1 = (int32)processIdx;
   b_val0 = -1;
      if (i_val1 != b_val0) {
         goto L40;
      }
   i_val1 = ((struct _staticClassFields_c *)classData) -> idleProcess_f;
   *((int32*)fp) = i_val1;
   return -1;
   L40:
   i_val1 = (int32)processIdx;
   b_val0 = (signed char)20;
      res_int32 = b_val0;
      lsb_int32 = i_val1;
      if (res_int32 == 0) {
         pc = 43;
         goto throwArithmeticException;
      }
      res_int32 = idiv(lsb_int32, res_int32);
   i_val1 = res_int32;
   missionIndex = i_val1;
   i_val1 = (int32)processIdx;
   b_val0 = (signed char)20;
      res_int32 = b_val0;
      lsb_int32 = i_val1;
      if (res_int32 == 0) {
         pc = 48;
         goto throwArithmeticException;
      }
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
      res_int32 = imod(res_int32, lsb_int32);
#else
      res_int32 = lsb_int32 % res_int32;
#endif
   i_val1 = res_int32;
   scjProcessIndex = i_val1;
   i_val1 = ((struct _staticClassFields_c *)classData) -> missionSet_f;
   i_val0 = (int32)missionIndex;
      index_int32 = i_val0;
      cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 57;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val1 = *((uint32 *) cobj);
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 58;
         goto throwNullPointer;
      }
   i_val1 = ((struct _test_TestSCJThreadMemory_MyMission_c *)HEAP_REF(cobj, unsigned char*)) -> msSetForMission_f;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 64;
         goto throwNullPointer;
      }
   i_val1 = ((struct _javax_safetycritical_ManagedSchedulableSet_c *)HEAP_REF(cobj, unsigned char*)) -> scjProcesses_f;
   i_val0 = (int32)scjProcessIndex;
      index_int32 = i_val0;
      cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 71;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int32 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val1 = *((uint32 *) cobj);
   *((int32*)fp) = i_val1;
   return -1;
   throwArithmeticException:
      excep = initializeException(sp, JAVA_LANG_ARITHMETICEXCEPTION, JAVA_LANG_ARITHMETICEXCEPTION_INIT_);
      goto throwIt;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[398], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.SleepingQueue */
/* Method: heapify */
RANGE const unsigned char javax_safetycritical_SleepingQueue_heapify[167] PROGMEM = {
  0x2A,0x1B,0xB7,0x01,0x91,0x28,0x3D,0x2A,0x1B,0xB7,0x01,0x96,0x2A,0x3E,0x1C,0x2A,
  0xB4,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA3,0x00,0x38,0x2A,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2E,0xB7,0x01,0x8E,0x2C,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2A,0xB4,
  0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x2E,0xB7,0x01,0x8E,0x2C,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xB0,0x36,0x9C,0x00,0x09,0x1C,0x36,0x04,0xA7,0x00,0x06,0x1B,0x36,
  0x04,0x1D,0x2A,0xB4,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0xA3,0x00,0x36,0x2A,0x2A,0xB4,0x00,
  0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1D,0x2E,0xB7,0x01,0x8E,0x2C,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x15,0x04,0x2E,0xB7,0x01,0x8E,0x2C,0xB4,
  0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xB0,0x36,0x9C,0x00,0x06,0x1D,0x36,0x04,0x15,
  0x04,0x1B,0x9F,0x00,0x12,0x2A,0x1B,0x15,0x04,0xB7,0x01,0x8B,0x3C,0x2A,0x15,0x04,
  0xB7,0x01,0x8F,0x3E,0xB1,0x01,0x01
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: insert */
RANGE const unsigned char javax_safetycritical_SleepingQueue_insert[149] PROGMEM = {
  0x2A,0xB4,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xBE,0xA0,0x00,0x0C,0xBB,0x00,0x02,0x59,0xB7,0x00,0x35,0x47,0xBF,0x2A,0x59,0xB4,
  0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,
  0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x3D,0xA7,0x00,0x21,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,
  0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB7,0x01,0x94,0x48,0x2E,0x4F,0x2A,
  0x1C,0xB7,0x01,0x94,0x48,0x3D,0x1C,0x04,0xA4,0x00,0x2A,0x2A,0x2A,0xB4,0x00,0x34,
  0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB7,0x01,0x94,0x48,0x2E,0xB7,0x01,0x8E,0x2C,0xB4,0x00,
  0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0x2B,0xB4,0x00,0x54,0x22,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, next_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0xB0,0x36,0x9D,
  0xFF,0xB9,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2B,0xB4,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0x4F,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: left */
RANGE const unsigned char javax_safetycritical_SleepingQueue_left[6] PROGMEM = {
  0x05,0x1B,0x68,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: makeEmptyTree */
RANGE const unsigned char javax_safetycritical_SleepingQueue_makeEmptyTree[23] PROGMEM = {
  0x03,0x3D,0xA7,0x00,0x0C,0x2B,0x1C,0x11,0xFC,0x19,0x4F,0x84,0x02,0x01,0x1C,0x2B,
  0xBE,0xA1,0xFF,0xF4,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: minimum */
/* minimum
 * param : 
 * return: javax.safetycritical.ScjProcess
 */
int16 javax_safetycritical_SleepingQueue_minimum(int32 *fp,     int32 this)
{  
   int32* sp;
   int32 i_val2;
   unsigned char* cobj;
   int32 i_val1;
   int8 b_val0;
   int8 index_int8;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_20;
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val2 = this;
cobj = (unsigned char *) (pointer)i_val2;
   i_val2 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> heapSize_f;
      if (i_val2 <= 0) {
         goto L27;
      }
   i_val2 = this;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _javax_safetycritical_SleepingQueue_c *)HEAP_REF(cobj, unsigned char*)) -> tree_f;
   b_val0 = 1;
      index_int8 = b_val0;
      cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 19;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val1 = *((uint32 *) cobj);
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_20 = javax_safetycritical_SleepingQueue_getScjProcess(sp);
   if (rval_m_20 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_20;
   }
   sp--;
   *((int32*)fp) = (int32)(*sp);
   return -1;
   L27:
   i_val2 = 0;
   *((int32*)fp) = i_val2;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[403], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: javax.safetycritical.SleepingQueue */
/* Method: parent */
RANGE const unsigned char javax_safetycritical_SleepingQueue_parent[6] PROGMEM = {
  0x1B,0x05,0x6C,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: remove */
RANGE const unsigned char javax_safetycritical_SleepingQueue_remove[76] PROGMEM = {
  0x2B,0xC7,0x00,0x06,0xB1,0x01,0x03,0x2A,0x2B,0xB4,0x00,0x54,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_ScjManagedThreadProcess_c, index_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,
  0x01,0x8D,0x71,0x3D,0x1C,0x11,0xFC,0x19,0x9F,0x00,0x31,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x1C,0x2A,0xB4,0x00,0x34,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, tree_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2E,0x4F,0x2A,0x59,0xB4,0x00,0x34,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x64,0xB5,0x00,0x34,
  0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_SleepingQueue_c, heapSize_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB7,0x01,0x8F,0x3E,0xB1,0x01,0x03
};

/* Class: javax.safetycritical.SleepingQueue */
/* Method: right */
RANGE const unsigned char javax_safetycritical_SleepingQueue_right[8] PROGMEM = {
  0x05,0x1B,0x68,0x04,0x60,0xAC,0x01,0x01
};

/* Class: javax.safetycritical.StorageParameters */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_StorageParameters_init_[104] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x18,0x16,0x04,0x14,0x00,0x54,0x94,0x9C,0x00,0x0C,0xBB,0x01,
  0x1C,0x59,0xB7,0x00,0x31,0x1D,0xBF,0x16,0x06,0x14,0x00,0x54,0x94,0x9C,0x00,0x0C,
  0xBB,0x01,0x1C,0x59,0xB7,0x00,0x31,0x1D,0xBF,0x2A,0x1F,0xB5,0x00,0x3B,0x40,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, totalBackingStore_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, totalBackingStore_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2D,0xB5,0x00,0x3B,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, configurationSizes_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, configurationSizes_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x3B,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, messageLength_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, messageLength_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x3B,0x20,
  /* offset: 0, -128*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, stackTraceLength_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, stackTraceLength_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x16,0x04,0xB5,0x00,0x3B,0x40,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMemoryArea_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMemoryArea_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x16,0x06,0xB5,0x00,0x3B,0x40,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxImmortal_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxImmortal_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x16,0x08,0xB5,0x00,
  0x3B,0x40,
  /* offset: 1, 32*/
(uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMissionMemory_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _javax_safetycritical_StorageParameters_c, maxMissionMemory_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x02,0x0D,0x00
};

/* Class: javax.safetycritical.annotate.Phase */
/* Method: <clinit> */
RANGE const unsigned char javax_safetycritical_annotate_Phase_clinit_[120] PROGMEM = {
  0xBB,0x00,0x2E,0x59,0x12,0x00,0x55,0x03,0xB7,0x01,0x99,0x10,0xB3,0x00,0x17,0x22,
  /* offset: 3, -64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, INITIALIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, INITIALIZE_f) << 3)) & 0xff),
  0xBB,0x00,0x2E,0x59,0x12,0x00,0x56,0x04,0xB7,0x01,0x99,0x10,0xB3,0x00,
  0x17,0x22,
  /* offset: 3, -32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, EXECUTE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, EXECUTE_f) << 3)) & 0xff),
  0xBB,0x00,0x2E,0x59,0x12,0x00,0x57,0x05,0xB7,0x01,0x99,0x10,
  0xB3,0x00,0x17,0x22,
  /* offset: 4, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, CLEANUP_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, CLEANUP_f) << 3)) & 0xff),
  0xBB,0x00,0x2E,0x59,0x12,0x00,0x58,0x06,0xB7,0x01,
  0x99,0x10,0xB3,0x00,0x17,0x22,
  /* offset: 4, 32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, ALL_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, ALL_f) << 3)) & 0xff),
  0x07,0xBD,0x00,0x0D,0x59,0x03,0xB2,0x00,
  0x17,0x22,
  /* offset: 3, -64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, INITIALIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, INITIALIZE_f) << 3)) & 0xff),
  0x53,0x59,0x04,0xB2,0x00,0x17,0x22,
  /* offset: 3, -32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, EXECUTE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, EXECUTE_f) << 3)) & 0xff),
  0x53,0x59,0x05,
  0xB2,0x00,0x17,0x22,
  /* offset: 4, 0*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, CLEANUP_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, CLEANUP_f) << 3)) & 0xff),
  0x53,0x59,0x06,0xB2,0x00,0x17,0x22,
  /* offset: 4, 32*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, ALL_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, ALL_f) << 3)) & 0xff),
  0x53,
  0xB3,0x00,0x17,0x22,
  /* offset: 4, 64*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, ENUM_VALUES_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, ENUM_VALUES_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.safetycritical.annotate.Phase */
/* Method: <init> */
RANGE const unsigned char javax_safetycritical_annotate_Phase_init_[10] PROGMEM = {
  0x2A,0x2B,0x1C,0xB7,0x00,0x28,0x23,0xB1,0x01,0x03
};

/* Class: javax.scj.util.Const */
/* Method: <clinit> */
RANGE const unsigned char javax_scj_util_Const_clinit_[205] PROGMEM = {
  0x12,0x00,0x59,0xB3,0x00,0x43,0x20,
  /* offset: 8, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, OVERALL_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, OVERALL_BACKING_STORE_f) << 3)) & 0xff),
  0x11,0x07,0xD0,0xB3,0x00,0x43,0x20,
  /* offset: 8, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_BACKING_STORE_f) << 3)) & 0xff),
  0x12,0x00,0x5A,0xB3,0x00,0x43,0x20,
  /* offset: 8, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IMMORTAL_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IMMORTAL_MEM_f) << 3)) & 0xff),
  0x12,0x00,0x5B,0xB3,0x00,
  0x43,0x20,
  /* offset: 9, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, OUTERMOST_SEQ_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, OUTERMOST_SEQ_BACKING_STORE_f) << 3)) & 0xff),
  0x12,0x00,0x5C,0xB3,0x00,0x43,0x20,
  /* offset: 9, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, MISSION_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, MISSION_MEM_f) << 3)) & 0xff),
  0x12,0x00,0x5D,
  0xB3,0x00,0x43,0x20,
  /* offset: 9, 80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_BACKING_STORE_f) << 3)) & 0xff),
  0x11,0x4E,0x20,0xB3,0x00,0x43,0x20,
  /* offset: 9, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_MEM_f) << 3)) & 0xff),
  0x11,
  0x04,0x00,0xB3,0x00,0x43,0x20,
  /* offset: 9, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIORITY_SCHEDULER_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIORITY_SCHEDULER_STACK_SIZE_f) << 3)) & 0xff),
  0x11,0x04,0x00,0xB3,0x00,0x43,0x20,
  /* offset: 9, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_PROCESS_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IDLE_PROCESS_STACK_SIZE_f) << 3)) & 0xff),
  0x11,0x08,0x00,0xB3,0x00,0x43,0x20,
  /* offset: 9, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, HANDLER_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, HANDLER_STACK_SIZE_f) << 3)) & 0xff),
  0x10,0x14,0xB3,0x00,0x43,0x20,
  /* offset: 9, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_QUEUE_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_QUEUE_SIZE_f) << 3)) & 0xff),
  0x10,0x14,0xB3,0x00,0x43,0x20,
  /* offset: 10, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_HANDLER_NUMBER_f) << 3)) & 0xff),
  0x10,0x14,0xB3,0x00,0x43,0x20,
  /* offset: 10, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_PRIORITY_QUEUE_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_PRIORITY_QUEUE_SIZE_f) << 3)) & 0xff),
  0x10,0x14,0xB3,0x00,0x43,0x20,
  /* offset: 10, 80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_SLEEPING_QUEUE_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_SLEEPING_QUEUE_SIZE_f) << 3)) & 0xff),
  0xBB,0x00,0xF4,0x59,0xB7,0x01,
  0xA0,0x6B,0xB3,0x00,0x43,0x22,
  /* offset: 10, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) & 0xff),
  0xBB,0x00,0xF8,0x59,0x0A,0x03,0x01,0xB7,
  0x00,0xDA,0x72,0xB3,0x00,0x43,0x22,
  /* offset: 10, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_TIME_INTERVAL_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, DEFAULT_TIME_INTERVAL_f) << 3)) & 0xff),
  0xBB,0x00,0xF8,0x59,0x14,0x00,0x5E,
  0x03,0xB8,0x00,0xAC,0x79,0xB7,0x00,0xDA,0x72,0xB3,0x00,0x43,0x22,
  /* offset: 10, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, INFINITE_TIME_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, INFINITE_TIME_f) << 3)) & 0xff),
  0xBB,
  0x00,0xF8,0x59,0x09,0x12,0x00,0x5A,0x01,0xB7,0x00,0xDA,0x72,0xB3,0x00,0x43,0x22,
  /* offset: 10, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, SUSPEND_TIME_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, SUSPEND_TIME_f) << 3)) & 0xff),
  0x11,0x3A,0x98,0xB3,0x00,0x43,0x20,
  /* offset: 10, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, MEMORY_TRACKER_AREA_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, MEMORY_TRACKER_AREA_SIZE_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.scj.util.Const */
/* Method: setDefaultErrorReporter */
RANGE const unsigned char javax_scj_util_Const_setDefaultErrorReporter[16] PROGMEM = {
  0xBB,0x00,0xDA,0x59,0xB7,0x01,0x9C,0x8D,0xB3,0x00,0x43,0x22,
  /* offset: 10, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: javax.scj.util.DefaultSCJErrorReporter */
/* Method: <init> */
RANGE const unsigned char javax_scj_util_DefaultSCJErrorReporter_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: javax.scj.util.DefaultSCJErrorReporter */
/* Method: processExecutionError */
RANGE const unsigned char javax_scj_util_DefaultSCJErrorReporter_processExecutionError[38] PROGMEM = {
  0xBB,0x00,0x34,0x59,0x12,0x00,0x5F,0xB7,0x00,0x67,0x16,0x2B,0xB7,0x00,0x6B,0x19,
  0xB7,0x00,0x6E,0x1D,0xB8,0x00,0x0A,0x21,0x2B,0xC1,0x00,0xA2,0x99,0x00,0x07,0xB8,
  0x01,0xE8,0x28,0xB1,0x01,0x03
};

/* Class: javax.scj.util.DefaultSCJErrorReporter */
/* Method: processOutOfMemoryError */
RANGE const unsigned char javax_scj_util_DefaultSCJErrorReporter_processOutOfMemoryError[17] PROGMEM = {
  0x12,0x00,0x60,0xB8,0x00,0x0A,0x21,0x12,0x00,0x61,0xB8,0x00,0x0A,0x21,0xB1,0x01,
  0x03
};

/* Class: javax.scj.util.DefaultSCJErrorReporter */
/* Method: schedulerError */
RANGE const unsigned char javax_scj_util_DefaultSCJErrorReporter_schedulerError[27] PROGMEM = {
  0xBB,0x00,0x34,0x59,0x12,0x00,0x62,0xB7,0x00,0x67,0x16,0x2B,0xB7,0x00,0x6B,0x19,
  0xB7,0x00,0x6E,0x1D,0xB8,0x00,0x0A,0x21,0xB1,0x01,0x03
};

/* Class: javax.scj.util.SilentSCJErrorReporter */
/* Method: <init> */
RANGE const unsigned char javax_scj_util_SilentSCJErrorReporter_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: javax.scj.util.SilentSCJErrorReporter */
/* Method: processExecutionError */
RANGE const unsigned char javax_scj_util_SilentSCJErrorReporter_processExecutionError[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: javax.scj.util.SilentSCJErrorReporter */
/* Method: processOutOfMemoryError */
RANGE const unsigned char javax_scj_util_SilentSCJErrorReporter_processOutOfMemoryError[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: javax.scj.util.SilentSCJErrorReporter */
/* Method: schedulerError */
RANGE const unsigned char javax_scj_util_SilentSCJErrorReporter_schedulerError[3] PROGMEM = {
  0xB1,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$BiggerObject */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_BiggerObject_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0D,0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory$BiggerObject */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_BiggerObject_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xA4,0x14,0xB1,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$MyApp */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_MyApp_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory$MyApp */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_MyApp_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xA6,0x29,0xB1,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$MyApp */
/* Method: getSequencer */
RANGE const unsigned char test_TestSCJThreadMemory_MyApp_getSequencer[18] PROGMEM = {
  0x12,0x00,0x63,0xB8,0x00,0x0A,0x16,0xBB,0x00,0x5E,0x59,0xB7,0x01,0xAC,0x1E,0xB0,
  0x01,0x03
};

/* Class: test.TestSCJThreadMemory$MyMission */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_MyMission_init_[8] PROGMEM = {
  0x2A,0xB7,0x01,0x17,0x08,0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory$MyMission */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_MyMission_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xA9,0x37,0xB1,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$MyMission */
/* Method: initialize */
RANGE const unsigned char test_TestSCJThreadMemory_MyMission_initialize[66] PROGMEM = {
  0x12,0x00,0x64,0xB8,0x00,0x0A,0x11,0xBB,0x00,0xD2,0x59,0xBB,0x00,0x78,0x59,0x10,
  0x60,0xB7,0x00,0xCC,0x1B,0xB2,0x00,0x5C,0x22,
  /* offset: 13, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Handlers_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Handlers_f) << 3)) & 0xff),
  0xB7,0x01,0xB2,0x24,0xB7,
  0x01,0x12,0x27,0xBB,0x00,0xD4,0x59,0xBB,0x00,0x78,0x59,0x10,0x5F,0xB7,0x00,0xCC,
  0x1B,0xB2,0x00,0x5C,0x22,
  /* offset: 13, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Handlers_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Handlers_f) << 3)) & 0xff),
  0xB7,0x01,0xB6,0x2C,0xB7,0x01,0x12,0x2D,0xB1,
  0x01,0x01
};

/* Class: test.TestSCJThreadMemory$MySequencer */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_MySequencer_init_[47] PROGMEM = {
  0x2A,0xBB,0x00,0x78,0x59,0x05,0xB7,0x00,0xCC,0x0E,0xB2,0x00,0x5C,0x22,
  /* offset: 13, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Sequencer_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Sequencer_f) << 3)) & 0xff),
  
  0xB7,0x01,0x2E,0x17,0x2A,0x02,0xB5,0x00,0x2F,0x20,
  /* offset: 1, -88*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xBB,0x01,0x24,
  0x59,0x01,0xB7,0x01,0xAA,0x1E,0xB5,0x00,0x2F,0x22,
  /* offset: 1, -120*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, mission_f__f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, mission_f__f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory$MySequencer */
/* Method: getNextMission */
RANGE const unsigned char test_TestSCJThreadMemory_MySequencer_getNextMission[8] PROGMEM = {
  0x2A,0xB7,0x01,0xAE,0x35,0xB0,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$MySequencer */
/* Method: getNextMission */
RANGE const unsigned char test_TestSCJThreadMemory_MySequencer_getNextMission_[55] PROGMEM = {
  0x2A,0x59,0xB4,0x00,0x2F,0x20,
  /* offset: 1, -88*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x2F,0x20,
  /* offset: 1, -88*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  
  0x2A,0xB4,0x00,0x2F,0x20,
  /* offset: 1, -88*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0xA1,0x00,0x0E,0x12,0x00,0x65,0xB8,0x00,
  0x0A,0x2B,0x01,0xB0,0x01,0x03,0x12,0x00,0x66,0xB8,0x00,0x0A,0x2B,0x2A,0xB4,0x00,
  0x2F,0x22,
  /* offset: 1, -120*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, mission_f__f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_MySequencer_c, mission_f__f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$MySequencer */
/* Method: run */
RANGE const unsigned char test_TestSCJThreadMemory_MySequencer_run[17] PROGMEM = {
  0x2A,0xB6,0x00,0x08,0x00,0x02,0x00,0x74,0x01,0x75,0x00,0x4E,0x01,0x32,0xB1,0x01,
  0x01
};

/* Class: test.TestSCJThreadMemory$SmallObject */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_SmallObject_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0C,0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory$SmallObject */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_SmallObject_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xB0,0x13,0xB1,0x01,0x03
};

/* Class: test.TestSCJThreadMemory$Thread1 */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_Thread1_init_[37] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x0F,0x0C,0x2A,0x03,0xB5,0x00,0x69,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,
  0xBB,0x00,0xF8,0x59,0x14,0x00,0x16,0x03,0xB7,0x00,0xD9,0x14,0xB5,0x00,0x69,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, delayTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, delayTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x07
};

/* Class: test.TestSCJThreadMemory$Thread1 */
/* Method: delay */
RANGE const unsigned char test_TestSCJThreadMemory_Thread1_delay[18] PROGMEM = {
  0x2A,0xB4,0x00,0x69,0x22,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, delayTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, delayTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x13,0x68,0xA7,0x00,0x04,0x4C,0xB1,
  0x01,0x03
};

RANGE const ExceptionHandler ex_test_TestSCJThreadMemory_Thread1_delay[1] PROGMEM = {
  { 0, 11, 14, -1}
};

/* Class: test.TestSCJThreadMemory$Thread1 */
/* Method: doWork */
RANGE const unsigned char test_TestSCJThreadMemory_Thread1_doWork[173] PROGMEM = {
  0x03,0x3C,0x03,0x3D,0xB8,0x01,0xE3,0x2E,0x4E,0xBB,0x00,0x34,0x59,0x12,0x00,0x67,
  0xB7,0x00,0x67,0x38,0x2D,0xB7,0x01,0xE5,0x3A,0xB7,0x00,0x6D,0x3E,0xB7,0x00,0x6E,
  0x42,0xB8,0x00,0x0A,0x25,0x2D,0xB7,0x01,0xE0,0x45,0x3D,0xA7,0x00,0x74,0x2D,0xB7,
  0x01,0xE0,0x45,0x3C,0x1B,0x1C,0x9F,0x00,0x11,0x12,0x00,0x68,0xB8,0x00,0x0A,0x25,
  0x04,0xB3,0x00,0x5C,0x08,
  /* offset: 13, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) & 0xff),
  0xBB,0x00,0x90,0x01,0xB7,0x01,0xB1,0x53,0x2D,
  0xB7,0x01,0xE0,0x45,0x3D,0x1C,0x1B,0xB8,0x01,0xBA,0x56,0x60,0x9F,0x00,0x2E,0xBB,
  0x00,0x34,0x59,0x12,0x00,0x69,0xB7,0x00,0x67,0x38,0x1C,0xB7,0x00,0x69,0x5B,0x12,
  0x00,0x6A,0xB7,0x00,0x6D,0x3E,0x1B,0xB7,0x00,0x69,0x5B,0xB7,0x00,0x6E,0x42,0xB8,
  0x00,0x0A,0x25,0x04,0xB3,0x00,0x5C,0x08,
  /* offset: 13, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) & 0xff),
  0x2A,0x59,0xB4,0x00,0x69,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x69,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0xB3,0x60,0x2A,
  0xB4,0x00,0x69,0x20,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread1_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA1,0xFF,0x87,0xB1,0x01,0x09
};

/* Class: test.TestSCJThreadMemory$Thread1 */
/* Method: run */
RANGE const unsigned char test_TestSCJThreadMemory_Thread1_run[15] PROGMEM = {
  0x12,0x00,0x6B,0xB8,0x00,0x0A,0x25,0x2A,0xB7,0x01,0xB4,0x2B,0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory$Thread2 */
/* Method: <init> */
RANGE const unsigned char test_TestSCJThreadMemory_Thread2_init_[29] PROGMEM = {
  0x2A,0x2B,0x2C,0xB7,0x01,0x0F,0x0C,0x2A,0xBB,0x00,0xF8,0x59,0x14,0x00,0x16,0x03,
  0xB7,0x00,0xD9,0x12,0xB5,0x00,0x6A,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, delayTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, delayTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x07
};

/* Class: test.TestSCJThreadMemory$Thread2 */
/* Method: delay */
RANGE const unsigned char test_TestSCJThreadMemory_Thread2_delay[18] PROGMEM = {
  0x2A,0xB4,0x00,0x6A,0x22,
  /* offset: 0, -32*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, delayTime_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, delayTime_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0x13,0x68,0xA7,0x00,0x04,0x4C,0xB1,
  0x01,0x03
};

RANGE const ExceptionHandler ex_test_TestSCJThreadMemory_Thread2_delay[1] PROGMEM = {
  { 0, 11, 14, -1}
};

/* Class: test.TestSCJThreadMemory$Thread2 */
/* Method: doWork */
RANGE const unsigned char test_TestSCJThreadMemory_Thread2_doWork[173] PROGMEM = {
  0x03,0x3C,0x03,0x3D,0xB8,0x01,0xE3,0x2C,0x4E,0xBB,0x00,0x34,0x59,0x12,0x00,0x6C,
  0xB7,0x00,0x67,0x36,0x2D,0xB7,0x01,0xE5,0x38,0xB7,0x00,0x6D,0x3C,0xB7,0x00,0x6E,
  0x40,0xB8,0x00,0x0A,0x23,0x2D,0xB7,0x01,0xE0,0x43,0x3D,0xA7,0x00,0x74,0x2D,0xB7,
  0x01,0xE0,0x43,0x3C,0x1B,0x1C,0x9F,0x00,0x11,0x12,0x00,0x6D,0xB8,0x00,0x0A,0x23,
  0x04,0xB3,0x00,0x5C,0x08,
  /* offset: 13, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) & 0xff),
  0xBB,0x01,0x1A,0x01,0xB7,0x01,0xA5,0x51,0x2D,
  0xB7,0x01,0xE0,0x43,0x3D,0x1C,0x1B,0xB8,0x01,0xBB,0x54,0x60,0x9F,0x00,0x2E,0xBB,
  0x00,0x34,0x59,0x12,0x00,0x6E,0xB7,0x00,0x67,0x36,0x1C,0xB7,0x00,0x69,0x59,0x12,
  0x00,0x6A,0xB7,0x00,0x6D,0x3C,0x1B,0xB7,0x00,0x69,0x59,0xB7,0x00,0x6E,0x40,0xB8,
  0x00,0x0A,0x23,0x04,0xB3,0x00,0x5C,0x08,
  /* offset: 13, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) & 0xff),
  0x2A,0x59,0xB4,0x00,0x6A,0x20,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x6A,0x20,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB7,0x01,0xB7,0x60,0x2A,
  0xB4,0x00,0x6A,0x20,
  /* offset: 1, 0*/
(uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, count_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _test_TestSCJThreadMemory_Thread2_c, count_f) - sizeof(Object)) << 3)) & 0xff),
  0x08,0xA1,0xFF,0x87,0xB1,0x01,0x09
};

/* Class: test.TestSCJThreadMemory$Thread2 */
/* Method: run */
RANGE const unsigned char test_TestSCJThreadMemory_Thread2_run[15] PROGMEM = {
  0x12,0x00,0x6F,0xB8,0x00,0x0A,0x23,0x2A,0xB7,0x01,0xB8,0x29,0xB1,0x01,0x01
};

/* Class: test.TestSCJThreadMemory */
/* Method: access$0 */
RANGE const unsigned char test_TestSCJThreadMemory_access_0[9] PROGMEM = {
  0xB2,0x00,0x5C,0x20,
  /* offset: 13, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfSmallObject_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfSmallObject_f) << 3)) & 0xff),
  0xAC,0x01,0x00
};

/* Class: test.TestSCJThreadMemory */
/* Method: access$1 */
RANGE const unsigned char test_TestSCJThreadMemory_access_1[9] PROGMEM = {
  0xB2,0x00,0x5C,0x20,
  /* offset: 13, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfBiggerObject_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfBiggerObject_f) << 3)) & 0xff),
  0xAC,0x01,0x00
};

/* Class: test.TestSCJThreadMemory */
/* Method: main */
RANGE const unsigned char test_TestSCJThreadMemory_main[284] PROGMEM = {
  0xB8,0x01,0xE4,0x18,0x4C,0x2B,0xB7,0x01,0xE0,0x1E,0x3D,0xBB,0x00,0x90,0x01,0xB7,
  0x01,0xB1,0x24,0x2B,0xB7,0x01,0xE0,0x1E,0x3E,0x1D,0x1C,0x64,0xB3,0x00,0x5C,0x20,
  /* offset: 13, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfSmallObject_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfSmallObject_f) << 3)) & 0xff),
  0x2B,0xB7,0x01,0xE0,0x1E,0x3D,0xBB,0x01,0x1A,0x01,0xB7,0x01,0xA5,0x2B,
  0x2B,0xB7,0x01,0xE0,0x1E,0x3E,0x1D,0x1C,0x64,0xB3,0x00,0x5C,0x20,
  /* offset: 13, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfBiggerObject_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfBiggerObject_f) << 3)) & 0xff),
  0xBB,
  0x00,0x34,0x59,0x12,0x00,0x70,0xB7,0x00,0x67,0x34,0xB2,0x00,0x5C,0x20,
  /* offset: 13, -112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfSmallObject_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfSmallObject_f) << 3)) & 0xff),
  
  0xB7,0x00,0x69,0x37,0x12,0x00,0x71,0xB7,0x00,0x6D,0x3D,0xB7,0x00,0x6E,0x40,0xB8,
  0x00,0x0A,0x44,0xBB,0x00,0x34,0x59,0x12,0x00,0x72,0xB7,0x00,0x67,0x34,0xB2,0x00,
  0x5C,0x20,
  /* offset: 13, -80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfBiggerObject_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, sizeOfBiggerObject_f) << 3)) & 0xff),
  0xB7,0x00,0x69,0x37,0x12,0x00,0x71,0xB7,0x00,0x6D,0x3D,0xB7,
  0x00,0x6E,0x40,0xB8,0x00,0x0A,0x44,0xB8,0x01,0x9B,0x4B,0xB8,0x01,0xEB,0x50,0xBB,
  0x00,0x76,0x59,0xB2,0x00,0x43,0x20,
  /* offset: 9, 16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, OUTERMOST_SEQ_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, OUTERMOST_SEQ_BACKING_STORE_f) << 3)) & 0xff),
  0x85,0x04,0xBC,0x00,0x2C,0x59,0x03,
  0xB2,0x00,0x43,0x20,
  /* offset: 9, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, HANDLER_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, HANDLER_STACK_SIZE_f) << 3)) & 0xff),
  0x85,0x50,0xB2,0x00,0x43,0x20,
  /* offset: 9, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_MEM_f) << 3)) & 0xff),
  0x85,0xB2,
  0x00,0x43,0x20,
  /* offset: 8, -16*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, IMMORTAL_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, IMMORTAL_MEM_f) << 3)) & 0xff),
  0x85,0xB2,0x00,0x43,0x20,
  /* offset: 9, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, MISSION_MEM_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, MISSION_MEM_f) << 3)) & 0xff),
  0x85,0xB7,0x01,0x97,
  0x64,0xB3,0x00,0x5C,0x22,
  /* offset: 13, -40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Sequencer_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Sequencer_f) << 3)) & 0xff),
  0xBB,0x00,0x76,0x59,0xB2,0x00,0x43,0x20,
  /* offset: 9, 80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_BACKING_STORE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, PRIVATE_BACKING_STORE_f) << 3)) & 0xff),
  0x85,0x04,0xBC,0x00,0x2C,0x59,0x03,0xB2,0x00,0x43,0x20,
  /* offset: 9, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, HANDLER_STACK_SIZE_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, HANDLER_STACK_SIZE_f) << 3)) & 0xff),
  0x85,0x50,
  0x14,0x00,0x73,0x09,0x09,0xB7,0x01,0x97,0x64,0xB3,0x00,0x5C,0x22,
  /* offset: 13, -8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Handlers_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, storageParameters_Handlers_f) << 3)) & 0xff),
  0x12,
  0x00,0x74,0xB8,0x00,0x0A,0x44,0xBB,0x00,0x12,0xBB,0x00,0x88,0x59,0x01,0xB7,0x01,
  0xA7,0x76,0x05,0xB7,0x00,0xEC,0x79,0x12,0x00,0x75,0xB8,0x00,0x0A,0x44,0xB2,0x00,
  0x5C,0x08,
  /* offset: 13, -48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, fail_f) << 3)) & 0xff),
  0x9A,0x00,0x05,0x01,0x4B,0xB1,0x01,0x03
};

/* Class: vm.Address32Bit */
/* Method: <init> */
RANGE const unsigned char vm_Address32Bit_init_[16] PROGMEM = {
  0x2A,0xB7,0x01,0xBE,0x0A,0x2A,0x1B,0xB5,0x00,0x6E,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Address32Bit_c, address_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Address32Bit_c, address_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: vm.Address */
/* Method: <init> */
RANGE const unsigned char vm_Address_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x08,0xB1,0x01,0x01
};

/* Class: vm.ClockInterruptHandler */
/* Method: <clinit> */
RANGE const unsigned char vm_ClockInterruptHandler_clinit_[9] PROGMEM = {
  0x04,0xB3,0x00,0x8F,0x08,
  /* offset: 17, 80*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, disableCount_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, disableCount_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: vm.ClockInterruptHandler */
/* Method: <init> */
RANGE const unsigned char vm_ClockInterruptHandler_init_[42] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x47,0x2A,0x2B,0xB7,0x01,0xC8,0x49,0x2A,0xBB,0x00,0x24,0x59,
  0x2A,0x2C,0xB7,0x02,0x03,0x4D,0xB5,0x00,0x8F,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, handlerProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, handlerProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x8F,
  0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, handlerProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, handlerProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x02,0x07,0x50,0xB1,0x01,0x07
};

/* Class: vm.ClockInterruptHandler */
/* Method: catchError */
RANGE const unsigned char vm_ClockInterruptHandler_catchError[24] PROGMEM = {
  0xB2,0x00,0x43,0x22,
  /* offset: 10, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, reporter_f) << 3)) & 0xff),
  0x2B,0xB9,0x00,0x02,0x01,0x0B,0x02,0x00,0x6D,0x01,
  0x9F,0x00,0x7A,0x01,0xA3,0xB1,0x01,0x03
};

/* Class: vm.ClockInterruptHandler */
/* Method: disable */
/* disable
 * param : 
 * return: void
 */
int16 vm_ClockInterruptHandler_disable(int32 *fp)
{  
   int32 i_val1;
   int8 b_val0;
   int8 msb_int8;
   int8 lsb_int8;
   int8 b_val1;
   i_val1 = ((struct _staticClassFields_c *)classData) -> disableCount_f;
   b_val0 = 1;
      msb_int8 = b_val0;
      lsb_int8 = i_val1;
      lsb_int8 += msb_int8;
   b_val1 = lsb_int8;
      lsb_int8 = b_val1;
      ((struct _staticClassFields_c *)classData) -> disableCount_f = lsb_int8;
   b_val1 = 0;
      lsb_int8 = b_val1;
      hvmClockReady = lsb_int8;
   return -1;
}

/* Class: vm.ClockInterruptHandler */
/* Method: enable */
/* enable
 * param : 
 * return: void
 */
int16 vm_ClockInterruptHandler_enable(int32 *fp)
{  
   int32 i_val1;
   int8 b_val0;
   int8 msb_int8;
   int8 lsb_int8;
   int8 b_val1;
   i_val1 = ((struct _staticClassFields_c *)classData) -> disableCount_f;
   b_val0 = 1;
      msb_int8 = b_val0;
      lsb_int8 = i_val1;
      lsb_int8 -= msb_int8;
   b_val1 = lsb_int8;
      lsb_int8 = b_val1;
      ((struct _staticClassFields_c *)classData) -> disableCount_f = lsb_int8;
   i_val1 = ((struct _staticClassFields_c *)classData) -> disableCount_f;
      if (i_val1 != 0) {
         goto L31;
      }
   b_val1 = 1;
      lsb_int8 = b_val1;
      hvmClockReady = lsb_int8;
   L31:
   return -1;
}

/* Class: vm.ClockInterruptHandler */
/* Method: handle */
/* handle
 * param : 
 * return: void
 */
int16 vm_ClockInterruptHandler_handle(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int16 rval_m_1;
   unsigned char* cobj;
   int32 i_val0;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_19;
    int32 this;
   this = (int32)(*(fp + 0));
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val1 = this;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_1 = vm_ClockInterruptHandler_disable(sp);
   if (rval_m_1 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_1;
   }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_ClockInterruptHandler_c *)HEAP_REF(cobj, unsigned char*)) -> currentProcess_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _vm_ClockInterruptHandler_c *)HEAP_REF(cobj, unsigned char*)) -> handlerProcess_f;
      if (i_val1 == 0) {
         pc = 19;
         goto throwNullPointer;
      }
   rval_m_19 = vm_Process_transferTo(sp, i_val1, i_val0);
   if (rval_m_19 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_19;
   }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[452], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.ClockInterruptHandler */
/* Method: initialize */
/* initialize
 * param : vm.Scheduler, int[]
 * return: void
 */
int16 vm_ClockInterruptHandler_initialize(int32 *fp)
{  
   int32* sp;
   int8 b_val3;
   int8 key_int8;
   int16 rval_m_34;
   int16 rval_m_41;
   int32 i_val3;
   int32 i_val2;
   int32 i_val1;
   int16 rval_m_51;
   int32 lsb_int32;
    int32 scheduler;
    int32 stack;
   scheduler = (int32)(*(fp + 0));
   stack = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   b_val3 = architecture;
   key_int8 = b_val3;
   switch (key_int8 - 1) {
   case 0:
      goto L34;
   case 1:
      goto L34;
   case 2:
      goto L41;
   default:
      goto L45;
   }
   L34:
   sp -= 0;
   rval_m_34 = enterMethodInterpreter(DEVICES_I86_I86INTERRUPTDISPATCHER_INIT, sp);
   if (rval_m_34 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_34;
   }
   goto L45;
   L41:
   sp -= 0;
   rval_m_41 = enterMethodInterpreter(DEVICES_CR16C_KT4585_CR16CINTERRUPTDISPATCHER_INIT, sp);
   if (rval_m_41 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_41;
   }
   L45:
   if (handleNewClassIndex(sp, 143) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   i_val2 = scheduler;
   i_val1 = stack;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 3;
   rval_m_51 = enterMethodInterpreter(VM_CLOCKINTERRUPTHANDLER_INIT_, sp);
   if (rval_m_51 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_51;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
      ((struct _staticClassFields_c *)classData) -> instance_f__f = lsb_int32;
   return -1;
}

/* Class: vm.ClockInterruptHandler */
/* Method: register */
RANGE const unsigned char vm_ClockInterruptHandler_register[9] PROGMEM = {
  0x2A,0x03,0xB8,0x01,0xD4,0x70,0xB1,0x01,0x01
};

/* Class: vm.ClockInterruptHandler */
/* Method: run */
/* run
 * param : 
 * return: void
 */
int16 vm_ClockInterruptHandler_run(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int32 i_val0;
   unsigned char* cobj;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_8;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int32 lsb_int32;
   int16 rval_m_29;
   int16 rval_m_47;
    int32 this;
   this = (int32)(*(fp + 0));
   sp = &fp[3]; /* make room for local VM state on the stack */
   L0:
   i_val1 = this;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _vm_ClockInterruptHandler_c *)HEAP_REF(cobj, unsigned char*)) -> scheduler_f;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 8;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 80:
            methodIndex = 228;
            continue;
          case 135:
            methodIndex = 353;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_8 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_8 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_8;
      }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _vm_ClockInterruptHandler_c *)HEAP_REF(cobj, unsigned char*)) -> currentProcess_f = lsb_int32;
   i_val1 = this;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_29 = vm_ClockInterruptHandler_enable(sp);
   if (rval_m_29 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_29;
   }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_ClockInterruptHandler_c *)HEAP_REF(cobj, unsigned char*)) -> handlerProcess_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _vm_ClockInterruptHandler_c *)HEAP_REF(cobj, unsigned char*)) -> currentProcess_f;
      if (i_val1 == 0) {
         pc = 47;
         goto throwNullPointer;
      }
   rval_m_47 = vm_Process_transferTo(sp, i_val1, i_val0);
   if (rval_m_47 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_47;
   }
   yieldToScheduler(sp);
   goto L0;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[455], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.ClockInterruptHandler */
/* Method: setScheduler */
RANGE const unsigned char vm_ClockInterruptHandler_setScheduler[39] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x8F,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, scheduler_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, scheduler_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x01,0xD5,0x78,0x2B,0xA6,0x00,0x12,
  0x01,0xB8,0x01,0xF6,0x7C,0x01,0xB8,0x01,0xFA,0x82,0x01,0xB8,0x01,0xF7,0x85,0x2B,
  0xB8,0x01,0xD6,0x88,0xB1,0x01,0x03
};

/* Class: vm.ClockInterruptHandler */
/* Method: startClockHandler */
RANGE const unsigned char vm_ClockInterruptHandler_startClockHandler[11] PROGMEM = {
  0x2A,0x2B,0xB5,0x00,0x8F,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, currentProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_ClockInterruptHandler_c, currentProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: vm.ClockInterruptHandler */
/* Method: yield */
RANGE const unsigned char vm_ClockInterruptHandler_yield[8] PROGMEM = {
  0x2A,0xB7,0x01,0xC4,0x6D,0xB1,0x01,0x01
};

/* Class: vm.HardwareObject */
/* Method: <init> */
RANGE const unsigned char vm_HardwareObject_init_[16] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0x2A,0x2B,0xB5,0x00,0x66,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_HardwareObject_c, address_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_HardwareObject_c, address_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: vm.InterruptDispatcher$NullHandler */
/* Method: <init> */
RANGE const unsigned char vm_InterruptDispatcher_NullHandler_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: vm.InterruptDispatcher$NullHandler */
/* Method: <init> */
RANGE const unsigned char vm_InterruptDispatcher_NullHandler_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xCC,0x17,0xB1,0x01,0x03
};

/* Class: vm.InterruptDispatcher$NullHandler */
/* Method: disable */
RANGE const unsigned char vm_InterruptDispatcher_NullHandler_disable[3] PROGMEM = {
  0xB1,0x01,0x01
};

/* Class: vm.InterruptDispatcher$NullHandler */
/* Method: enable */
RANGE const unsigned char vm_InterruptDispatcher_NullHandler_enable[3] PROGMEM = {
  0xB1,0x01,0x01
};

/* Class: vm.InterruptDispatcher$NullHandler */
/* Method: handle */
/* handle
 * param : 
 * return: void
 */
int16 vm_InterruptDispatcher_NullHandler_handle(int32 *fp)
{  
   return -1;
}

/* Class: vm.InterruptDispatcher */
/* Method: <clinit> */
RANGE const unsigned char vm_InterruptDispatcher_clinit_[9] PROGMEM = {
  0x03,0xB3,0x00,0x49,0x08,
  /* offset: 13, 72*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, init_f) << 3)) & 0xff),
  0xB1,0x00
};

/* Class: vm.InterruptDispatcher */
/* Method: init */
RANGE const unsigned char vm_InterruptDispatcher_init[42] PROGMEM = {
  0xBB,0x00,0x6E,0x59,0x01,0xB7,0x01,0xCD,0x32,0x4B,0x03,0x3C,0xA7,0x00,0x11,0xB2,
  0x00,0x49,0x22,
  /* offset: 13, 8*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, handlers_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, handlers_f) << 3)) & 0xff),
  0x1B,0x2A,0x53,0x1B,0x04,0x60,0x91,0x3C,0x1B,0xB2,0x00,
  0x49,0x20,
  /* offset: 13, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, numberOfInterrupts_f) << 3)) & 0xff),
  0xA1,0xFF,0xEB,0xB1,0x01,0x01
};

/* Class: vm.InterruptDispatcher */
/* Method: interrupt */
/* interrupt
 * param : byte
 * return: void
 */
int16 vm_InterruptDispatcher_interrupt(int32 *fp,     int8 n)
{  
   int32* sp;
   int32 i_val1;
   int8 b_val0;
   int8 index_int8;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_8;
   Object* i_obj;
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val1 = ((struct _staticClassFields_c *)classData) -> handlers_f;
   b_val0 = (int8)n;
      index_int8 = b_val0;
      cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 7;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val1 = *((uint32 *) cobj);
   *sp = (int32)i_val1;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 8;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 113:
            methodIndex = 6;
            continue;
          case 143:
            methodIndex = 452;
            continue;
          case 55:
            methodIndex = 464;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_8 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_8 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_8;
      }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[467], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.InterruptDispatcher */
/* Method: registerHandler */
/* registerHandler
 * param : vm.InterruptHandler, byte
 * return: void
 */
int16 vm_InterruptDispatcher_registerHandler(int32 *fp)
{  
   int32* sp;
   int8 b_val2;
   int32 i_val1;
   int32 i_val2;
   int8 b_val1;
   int8 index_int8;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_22;
   int32 i_val0;
   int32 lsb_int32;
    int32 iHandler;
    int8 n;
   iHandler = (int32)(*(fp + 0));
   n = (int8)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   b_val2 = (int8)n;
   i_val1 = ((struct _staticClassFields_c *)classData) -> numberOfInterrupts_f;
      if (b_val2 > i_val1) {
         goto L35;
      }
   i_val2 = ((struct _staticClassFields_c *)classData) -> handlers_f;
   b_val1 = (int8)n;
      index_int8 = b_val1;
      cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 17;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
   i_val2 = *((uint32 *) cobj);
      if (i_val2 != 0) {
         goto L26;
      }
   b_val2 = (int8)n;
   rval_m_22 = vm_InterruptDispatcher_interrupt(sp, b_val2);
   if (rval_m_22 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_22;
   }
   L26:
   i_val2 = ((struct _staticClassFields_c *)classData) -> handlers_f;
   b_val1 = (int8)n;
   i_val0 = iHandler;
      lsb_int32 = i_val0;
      index_int8 = b_val1;
      cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 34;
         goto throwNullPointer;
      }
      cobj = cobj + sizeof(Object) + 2 + (index_int8 << 2);
      cobj = HEAP_REF(cobj, unsigned char*);
      *((uint32 *) cobj) = lsb_int32;
   L35:
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[468], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Machine */
/* Method: getCurrentScheduler */
RANGE const unsigned char vm_Machine_getCurrentScheduler[9] PROGMEM = {
  0xB2,0x00,0x16,0x22,
  /* offset: 3, -96*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, currentScheduler_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, currentScheduler_f) << 3)) & 0xff),
  0xB0,0x01,0x01
};

/* Class: vm.Machine */
/* Method: setCurrentScheduler */
/* setCurrentScheduler
 * param : vm.Scheduler
 * return: void
 */
int16 vm_Machine_setCurrentScheduler(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int32 i_val0;
   int32 op2;
   int32 op1;
   unsigned char c_result;
   int16 rval_m_10;
   int32 rval_10;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_17;
   int16 rval_m_22;
   int16 rval_m_27;
   int16 rval_m_32;
   int16 rval_m_37;
   int32 lsb_int32;
    int32 sch;
    int32 mon;
   sch = (int32)(*(fp + 0));
   mon = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val1 = ((struct _staticClassFields_c *)classData) -> currentScheduler_f;
   i_val0 = sch;
      op2 = i_val0;
      op1 = i_val1;
      c_result = (op1 != op2);
      if (c_result) {
         goto L41;
      }
   rval_m_10 = vm_Monitor_getDefaultMonitor(sp);
   if (rval_m_10 == -1) {
   rval_10 = *(int32*)sp;
   i_val1 = rval_10;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_10;
   }
   mon = i_val1;
   i_val1 = mon;
   i_val0 = 0;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 17;
         goto throwNullPointer;
      }
   rval_m_17 = n_vm_Monitor_attachMonitor(sp);
   if (rval_m_17 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_17;
   }
   i_val1 = 0;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_22 = vm_Monitor_wait(sp);
   if (rval_m_22 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_22;
   }
   i_val1 = 0;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_27 = vm_Monitor_notify(sp);
   if (rval_m_27 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_27;
   }
   i_val1 = 0;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_32 = vm_Monitor_lock_(sp);
   if (rval_m_32 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_32;
   }
   i_val1 = 0;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_37 = vm_Monitor_unlock_(sp);
   if (rval_m_37 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_37;
   }
   L41:
   i_val1 = sch;
      lsb_int32 = i_val1;
      ((struct _staticClassFields_c *)classData) -> currentScheduler_f = lsb_int32;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[470], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Memory$MemoryInfo */
/* Method: <init> */
RANGE const unsigned char vm_Memory_MemoryInfo_init_[48] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0E,0x2A,0x2B,0xB8,0x01,0xDC,0x11,0xB5,0x00,0x4C,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x2B,0xB8,0x01,0xDD,0x19,0xB5,0x00,0x4C,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,
  0x00,0x4C,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, maxUsed_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, maxUsed_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x04,0xB5,0x00,0x4C,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: vm.Memory$MemoryInfo */
/* Method: increaseInstanceCount */
RANGE const unsigned char vm_Memory_MemoryInfo_increaseInstanceCount[19] PROGMEM = {
  0x2A,0x59,0xB4,0x00,0x4C,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) & 0xff),
  0x04,0x60,0xB5,0x00,0x4C,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) & 0xff),
  
  0xB1,0x01,0x01
};

/* Class: vm.Memory$MemoryInfo */
/* Method: toString */
RANGE const unsigned char vm_Memory_MemoryInfo_toString[123] PROGMEM = {
  0xBB,0x00,0x6C,0x59,0xB7,0x00,0x61,0x2D,0x4C,0x2B,0x2A,0xB4,0x00,0x4C,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x64,0x2E,0x57,0x2B,0xBB,0x00,0x34,0x59,0x12,0x00,0x76,0xB7,0x00,
  0x67,0x36,0x2A,0xB4,0x00,0x4C,0x20,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, instanceCount_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x69,0x39,0x12,0x00,0x77,
  0xB7,0x00,0x6D,0x3E,0xB7,0x00,0x6E,0x41,0xB7,0x00,0x64,0x2E,0x57,0x2B,0x12,0x00,
  0x78,0xB7,0x00,0x64,0x2E,0x57,0x2B,0x2A,0xB4,0x00,0x4C,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,
  0x62,0x45,0x57,0x2B,0xBB,0x00,0x34,0x59,0x12,0x00,0x79,0xB7,0x00,0x67,0x36,0x2A,
  0xB4,0x00,0x4C,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, maxUsed_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_MemoryInfo_c, maxUsed_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x69,0x39,0xB7,0x00,0x6E,0x41,0xB7,0x00,
  0x64,0x2E,0x57,0x2B,0xB7,0x00,0x65,0x4A,0xB0,0x01,0x07
};

/* Class: vm.Memory */
/* Method: <init> */
RANGE const unsigned char vm_Memory_init_[42] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x88,0x2A,0x1B,0xB5,0x00,0x7B,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, base_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, base_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x1C,0xB5,
  0x00,0x7B,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x03,0xB5,0x00,0x7B,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0x12,0x00,
  0x7A,0xB5,0x00,0x7B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: vm.Memory */
/* Method: <init> */
/* <init>
 * param : int, int, java.lang.String
 * return: void
 */
int16 vm_Memory_init__(int32 *fp,     int32 this,     int32 base,     int32 size,     int32 name)
{  
   int32* sp;
   int32 i_val2;
   int16 rval_m_1;
   int32 i_val1;
   unsigned char* cobj;
   int32 lsb_int32;
   const ConstantInfo* constant_;
   int32 i_val0;
   int16 rval_m_59;
   int32 rval_59;
   sp = &fp[6]; /* make room for local VM state on the stack */
   i_val2 = this;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_1 = enterMethodInterpreter(JAVA_LANG_OBJECT_INIT_, sp);
   if (rval_m_1 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_1;
   }
   i_val2 = this;
   i_val1 = (int32)base;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> base_f = lsb_int32;
   i_val2 = this;
   i_val1 = (int32)size;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> size_f = lsb_int32;
   i_val2 = this;
   i_val1 = 0;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f = lsb_int32;
   i_val2 = this;
   i_val1 = name;
      if (i_val1 != 0) {
   *sp = (int32)i_val2;
   sp++;
         goto L40;
      }
   constant_ = &constants[123];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val1 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   goto L41;
   L40:
   i_val2 = name;
   *sp = (int32)i_val2;
   sp++;
   L41:
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> name_f = lsb_int32;
   i_val2 = ((struct _staticClassFields_c *)classData) -> memoryAreaTrackingEnabled_f;
      if (i_val2 == 0) {
         goto L69;
      }
   i_val2 = this;
   i_val1 = this;
   i_val0 = this;
   sp += 1;
   rval_m_59 = vm_Memory_addMemoryArea(sp, i_val1, i_val0);
   if (rval_m_59 == -1) {
   rval_59 = *(int32*)sp;
   i_val1 = rval_59;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_59;
   }
   sp -= 1;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> memoryInfo_f = lsb_int32;
   L69:
   return -1;
}

/* Class: vm.Memory */
/* Method: access$0 */
RANGE const unsigned char vm_Memory_access_0[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: vm.Memory */
/* Method: access$1 */
RANGE const unsigned char vm_Memory_access_1[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7B,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: vm.Memory */
/* Method: addMemoryArea */
/* addMemoryArea
 * param : vm.Memory
 * return: vm.Memory$MemoryInfo
 */
int16 vm_Memory_addMemoryArea(int32 *fp,     int32 this,     int32 m)
{  
   int32* sp;
   int32 i_val2;
   int32 lsb_int32;
   int16 rval_m_22;
   int16 rval_m_40;
   const ConstantInfo* constant_;
   int32 i_val1;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_75;
   Object* i_obj;
   int16 rval_m_87;
   int16 rval_m_111;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_122;
   unsigned char *object;
   signed short subClassIndex;
   int16 rval_m_150;
   int32 msb_int32;
   int16 rval_m_158;
   int16 rval_m_163;
   int16 rval_m_174;
   int8 msb_int8;
   int16 rval_m_192;
   int16 rval_m_204;
   int16 rval_m_210;
    int32 current;
    int32 memory_3;
    int32 lv_4 = 0;
   sp = &fp[7]; /* make room for local VM state on the stack */
   i_val2 = ((struct _staticClassFields_c *)classData) -> areaToUseForTracking_f;
      if (i_val2 != 0) {
         goto L16;
      }
   i_val2 = m;
      lsb_int32 = i_val2;
      ((struct _staticClassFields_c *)classData) -> areaToUseForTracking_f = lsb_int32;
   L16:
   i_val2 = ((struct _staticClassFields_c *)classData) -> areaToUseForTracking_f;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_22 = vm_Memory_switchToArea(sp);
   if (rval_m_22 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_22;
   }
   sp--;
   current = (int32)(*sp);
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
      if (i_val2 != 0) {
         goto L105;
      }
   if (handleNewClassIndex(sp, 45) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_40 = enterMethodInterpreter(JAVA_UTIL_ARRAYLIST_INIT_, sp);
   if (rval_m_40 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_40;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
      ((struct _staticClassFields_c *)classData) -> createdMemories_f = lsb_int32;
   i_val2 = (int32)heapArea;
   constant_ = &constants[124];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val1 = (int32) (pointer) stringConstants[constant_->value >> 16];
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 59;
         goto throwNullPointer;
      }
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> name_f = lsb_int32;
   if (handleNewClassIndex(sp, 76) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   i_val1 = (int32)heapArea;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_75 = enterMethodInterpreter(VM_MEMORY_MEMORYINFO_INIT_, sp);
   if (rval_m_75 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_75;
   }
   sp--;
   memory_3 = (int32)(*sp);
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
   i_val1 = memory_3;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 87;
         goto throwNullPointer;
      }
   rval_m_87 = enterMethodInterpreter(JAVA_UTIL_ARRAYLIST_ADD, sp);
   if (rval_m_87 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_87;
   }
   sp--;
dummy = (int32)(*sp);
   i_val2 = (int32)heapArea;
   i_val1 = memory_3;
      lsb_int32 = i_val1;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 99;
         goto throwNullPointer;
      }
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> memoryInfo_f = lsb_int32;
   L105:
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 111;
         goto throwNullPointer;
      }
   rval_m_111 = enterMethodInterpreter(JAVA_UTIL_ARRAYLIST_ITERATOR, sp);
   if (rval_m_111 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_111;
   }
   sp--;
      lv_4 = (int32)(*sp);
   goto L172;
   L120:
   i_val2 = lv_4;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 131:
            methodIndex = 139;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_122 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_122 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_122;
      }
   sp--;
object = (unsigned char *) (pointer) *sp;
      excep = 0;
   i_val2 = (int32)(pointer)object;
      if (object != 0) {
         subClassIndex = getClassIndex((Object*)object);
         excep = !isSubClassOf(subClassIndex, 76);
      }
      if (excep) {
         pc = 132;
         goto throwClassCastException;
      }
   memory_3 = i_val2;
   i_val2 = memory_3;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 137;
         goto throwNullPointer;
      }
   i_val2 = ((struct _vm_Memory_MemoryInfo_c *)HEAP_REF(cobj, unsigned char*)) -> name_f;
   i_val1 = m;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 144;
         goto throwNullPointer;
      }
   i_val1 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> name_f;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 150;
         goto throwNullPointer;
      }
   rval_m_150 = enterMethodInterpreter(JAVA_LANG_STRING_COMPARETO, sp);
   if (rval_m_150 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_150;
   }
   sp--;
      msb_int32 = (int32)(*sp);
      if (msb_int32 != 0) {
         goto L172;
      }
   i_val2 = memory_3;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_158 = enterMethodInterpreter(VM_MEMORY_MEMORYINFO_INCREASEINSTANCECOUNT, sp);
   if (rval_m_158 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_158;
   }
   i_val2 = current;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_163 = vm_Memory_switchToArea(sp);
   if (rval_m_163 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_163;
   }
   sp--;
dummy = (int32)(*sp);
   i_val2 = memory_3;
   *((int32*)fp) = i_val2;
   return -1;
   L172:
   i_val2 = lv_4;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 174;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 131:
            methodIndex = 138;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_174 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_174 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_174;
      }
   sp--;
      msb_int8 = (int8)(*sp);
      if (msb_int8 != 0) {
   yieldToScheduler(sp);
         goto L120;
      }
   if (handleNewClassIndex(sp, 76) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   i_val1 = m;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_192 = enterMethodInterpreter(VM_MEMORY_MEMORYINFO_INIT_, sp);
   if (rval_m_192 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_192;
   }
   sp--;
   memory_3 = (int32)(*sp);
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
   i_val1 = memory_3;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 204;
         goto throwNullPointer;
      }
   rval_m_204 = enterMethodInterpreter(JAVA_UTIL_ARRAYLIST_ADD, sp);
   if (rval_m_204 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_204;
   }
   sp--;
dummy = (int32)(*sp);
   i_val2 = current;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_210 = vm_Memory_switchToArea(sp);
   if (rval_m_210 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_210;
   }
   sp--;
dummy = (int32)(*sp);
   i_val2 = memory_3;
   *((int32*)fp) = i_val2;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwClassCastException:
      excep = initializeException(sp, JAVA_LANG_CLASSCASTEXCEPTION, JAVA_LANG_CLASSCASTEXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[478], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Memory */
/* Method: allocateInHeap */
/* allocateInHeap
 * param : int
 * return: vm.Memory
 */
int16 vm_Memory_allocateInHeap(int32 *fp)
{  
   int32* sp;
   int32 i_val3;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int32 i_val2;
   int32 msb_int32;
   int32 lsb_int32;
   int16 rval_m_33;
   Object* ex_ception;
   int32 i_val1;
   int16 rval_m_91;
    int32 size;
    int32 startPtr;
    int32 memory;
   size = (int32)(*(fp + 0));
   startPtr = (int32)(*(fp + 1));
   memory = (int32)(*(fp + 2));
   sp = &fp[5]; /* make room for local VM state on the stack */
   i_val3 = (int32)heapArea;
cobj = (unsigned char *) (pointer)i_val3;
      if (cobj == 0) {
         pc = 6;
         goto throwNullPointer;
      }
   i_val3 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f;
   i_val2 = (int32)size;
      msb_int32 = i_val2;
      lsb_int32 = i_val3;
      lsb_int32 += msb_int32;
   i_val3 = lsb_int32;
   i_val2 = (int32)heapArea;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 20;
         goto throwNullPointer;
      }
   i_val2 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> size_f;
      if (i_val3 < i_val2) {
         goto L38;
      }
   if (handleNewClassIndex(sp, 81) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   *sp = (int32)i_val3;
   sp++;
   sp -= 1;
   rval_m_33 = enterMethodInterpreter(JAVA_LANG_OUTOFMEMORYERROR_INIT_, sp);
   if (rval_m_33 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_33;
   }
   ex_ception = (Object*) (pointer) *(sp - 1);
   excep = getClassIndex(ex_ception);
   pc = 37;
   sp--;
   goto throwIt;
   L38:
   i_val3 = (int32)heapArea;
cobj = (unsigned char *) (pointer)i_val3;
      if (cobj == 0) {
         pc = 44;
         goto throwNullPointer;
      }
   i_val3 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> base_f;
   i_val2 = (int32)heapArea;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 56;
         goto throwNullPointer;
      }
   i_val2 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f;
      msb_int32 = i_val2;
      lsb_int32 = i_val3;
      lsb_int32 += msb_int32;
   i_val3 = lsb_int32;
   startPtr = i_val3;
   i_val3 = (int32)heapArea;
   i_val2 = i_val3;
cobj = (unsigned char *) (pointer)i_val2;
      if (cobj == 0) {
         pc = 71;
         goto throwNullPointer;
      }
   i_val2 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f;
   i_val1 = (int32)size;
      msb_int32 = i_val1;
      lsb_int32 = i_val2;
      lsb_int32 += msb_int32;
   i_val2 = lsb_int32;
      lsb_int32 = i_val2;
cobj = (unsigned char *) (pointer)i_val3;
      ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f = lsb_int32;
   if (handleNewClassIndex(sp, 123) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   i_val2 = (int32)startPtr;
   i_val1 = (int32)size;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 3;
   rval_m_91 = enterMethodInterpreter(VM_MEMORY_INIT_, sp);
   if (rval_m_91 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_91;
   }
   sp--;
   memory = (int32)(*sp);
   i_val3 = memory;
   *((int32*)fp) = i_val3;
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[479], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Memory */
/* Method: consumedMemory */
RANGE const unsigned char vm_Memory_consumedMemory[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7B,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: vm.Memory */
/* Method: executeInHeap */
/* executeInHeap
 * param : java.lang.Runnable
 * return: void
 */
int16 vm_Memory_executeInHeap(int32 *fp)
{  
   int32* sp;
   int32 i_val0;
   int16 rval_m_6;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_12;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_63;
    int32 logic;
    int32 current;
   logic = (int32)(*(fp + 0));
   current = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val0 = ((struct _staticClassFields_c *)classData) -> areaToUseForTracking_f;
   *sp = (int32)i_val0;
   sp++;
   sp -= 1;
   rval_m_6 = vm_Memory_switchToArea(sp);
   if (rval_m_6 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_6;
   }
   sp--;
   current = (int32)(*sp);
   i_val0 = logic;
   *sp = (int32)i_val0;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 12;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 9:
            methodIndex = 237;
            continue;
          case 140:
            methodIndex = 292;
            continue;
          case 136:
            methodIndex = 294;
            continue;
          case 137:
            methodIndex = 296;
            continue;
          case 114:
            methodIndex = 371;
            continue;
          case 41:
            methodIndex = 169;
            continue;
          case 95:
            methodIndex = 378;
            continue;
          case 105:
            methodIndex = 437;
            continue;
          case 106:
            methodIndex = 441;
            continue;
          case 143:
            methodIndex = 455;
            continue;
          case 40:
            methodIndex = 508;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_12 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_12 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_12;
      }
   i_val0 = current;
   *sp = (int32)i_val0;
   sp++;
   sp -= 1;
   rval_m_63 = vm_Memory_switchToArea(sp);
   if (rval_m_63 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_63;
   }
   sp--;
dummy = (int32)(*sp);
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[481], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Memory */
/* Method: getBase */
RANGE const unsigned char vm_Memory_getBase[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7B,0x20,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, base_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, base_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: vm.Memory */
/* Method: getCurrentMemoryArea */
/* getCurrentMemoryArea
 * param : 
 * return: vm.Memory
 */
int16 vm_Memory_getCurrentMemoryArea(int32 *fp)
{  
   int32 i_val0;
   i_val0 = (int32)currentMemoryArea;
   *((int32*)fp) = i_val0;
   return -1;
}

/* Class: vm.Memory */
/* Method: getHeapArea */
/* getHeapArea
 * param : 
 * return: vm.Memory
 */
int16 vm_Memory_getHeapArea(int32 *fp)
{  
   int32 i_val0;
   i_val0 = (int32)heapArea;
   *((int32*)fp) = i_val0;
   return -1;
}

/* Class: vm.Memory */
/* Method: getName */
RANGE const unsigned char vm_Memory_getName[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: vm.Memory */
/* Method: getNextMemoryName */
RANGE const unsigned char vm_Memory_getNextMemoryName[76] PROGMEM = {
  0xB2,0x00,0x7B,0x08,
  /* offset: 16, 40*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, memoryAreaTrackingEnabled_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, memoryAreaTrackingEnabled_f) << 3)) & 0xff),
  0x99,0x00,0x42,0xB2,0x00,0x7B,0x22,
  /* offset: 16, 48*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, areaToUseForTracking_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, areaToUseForTracking_f) << 3)) & 0xff),
  0xB8,
  0x01,0xEC,0x20,0x4C,0xBB,0x00,0x34,0x59,0x2A,0xB8,0x00,0x60,0xD0,0xB7,0x00,0x67,
  0x63,0xB2,0x00,0x7B,0x20,
  /* offset: 16, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, nameCount_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, nameCount_f) << 3)) & 0xff),
  0xB7,0x00,0x69,0x69,0xB7,0x00,0x6E,0x72,0x4D,
  0xB2,0x00,0x7B,0x20,
  /* offset: 16, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, nameCount_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, nameCount_f) << 3)) & 0xff),
  0x04,0x60,0xB3,0x00,0x7B,0x20,
  /* offset: 16, 112*/
(uint8)(((uint16)(offsetof(struct _staticClassFields_c, nameCount_f) << 3)) >> 8), (uint8)(((uint16)(offsetof(struct _staticClassFields_c, nameCount_f) << 3)) & 0xff),
  0x2B,0xB8,
  0x01,0xEC,0x20,0x57,0x2C,0xB0,0x01,0x0F,0x2A,0xB0,0x01,0x09
};

/* Class: vm.Memory */
/* Method: getSize */
RANGE const unsigned char vm_Memory_getSize[10] PROGMEM = {
  0x2A,0xB4,0x00,0x7B,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xAC,0x01,0x01
};

/* Class: vm.Memory */
/* Method: reportMemoryUsage */
/* reportMemoryUsage
 * param : 
 * return: void
 */
int16 vm_Memory_reportMemoryUsage(int32 *fp)
{  
   int32* sp;
   int32 i_val2;
   int16 rval_m_24;
   const ConstantInfo* constant_;
   int32 i_val1;
   int16 rval_m_36;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_46;
   int16 rval_m_50;
   int16 rval_m_57;
   int16 rval_m_61;
   int16 rval_m_65;
   int16 rval_m_75;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_84;
   unsigned char *object;
   signed short subClassIndex;
   int16 rval_m_99;
   int16 rval_m_103;
   int16 rval_m_108;
   int8 msb_int8;
   int16 rval_m_128;
   int16 rval_m_132;
   int16 rval_m_136;
   int16 rval_m_140;
   int16 rval_m_144;
   int16 rval_m_149;
   int16 rval_m_160;
   int16 rval_m_170;
    int32 current;
    int32 memory;
    int32 lv_2 = 0;
   current = (int32)(*(fp + 0));
   memory = (int32)(*(fp + 1));
   lv_2 = (int32)(*(fp + 2));
   sp = &fp[5]; /* make room for local VM state on the stack */
   i_val2 = ((struct _staticClassFields_c *)classData) -> memoryAreaTrackingEnabled_f;
      if (i_val2 == 0) {
         goto L167;
      }
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
      if (i_val2 == 0) {
         goto L157;
      }
   i_val2 = ((struct _staticClassFields_c *)classData) -> areaToUseForTracking_f;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_24 = vm_Memory_switchToArea(sp);
   if (rval_m_24 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_24;
   }
   sp--;
   current = (int32)(*sp);
   if (handleNewClassIndex(sp, 26) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   constant_ = &constants[125];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val1 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_36 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_INIT__, sp);
   if (rval_m_36 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_36;
   }
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 46;
         goto throwNullPointer;
      }
   rval_m_46 = enterMethodInterpreter(JAVA_UTIL_ARRAYLIST_SIZE, sp);
   if (rval_m_46 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_46;
   }
   sp -= 2;
   rval_m_50 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND__, sp);
   if (rval_m_50 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_50;
   }
   constant_ = &constants[126];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val2 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 57;
         goto throwNullPointer;
      }
   rval_m_57 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND______, sp);
   if (rval_m_57 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_57;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 61;
         goto throwNullPointer;
      }
   rval_m_61 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_TOSTRING, sp);
   if (rval_m_61 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_61;
   }
   sp -= 1;
   rval_m_65 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_65 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_65;
   }
   i_val2 = ((struct _staticClassFields_c *)classData) -> createdMemories_f;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 75;
         goto throwNullPointer;
      }
   rval_m_75 = enterMethodInterpreter(JAVA_UTIL_ARRAYLIST_ITERATOR, sp);
   if (rval_m_75 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_75;
   }
   sp--;
   lv_2 = (int32)(*sp);
   goto L107;
   L83:
   i_val2 = lv_2;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 131:
            methodIndex = 139;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_84 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_84 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_84;
      }
   sp--;
object = (unsigned char *) (pointer) *sp;
      excep = 0;
   i_val2 = (int32)(pointer)object;
      if (object != 0) {
         subClassIndex = getClassIndex((Object*)object);
         excep = !isSubClassOf(subClassIndex, 76);
      }
      if (excep) {
         pc = 94;
         goto throwClassCastException;
      }
   memory = i_val2;
   i_val2 = memory;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 99;
         goto throwNullPointer;
      }
   rval_m_99 = enterMethodInterpreter(VM_MEMORY_MEMORYINFO_TOSTRING, sp);
   if (rval_m_99 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_99;
   }
   sp -= 1;
   rval_m_103 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_103 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_103;
   }
   L107:
   i_val2 = lv_2;
   *sp = (int32)i_val2;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 108;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 131:
            methodIndex = 138;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_108 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_108 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_108;
      }
   sp--;
      msb_int8 = (int8)(*sp);
      if (msb_int8 != 0) {
   yieldToScheduler(sp);
         goto L83;
      }
   if (handleNewClassIndex(sp, 26) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   constant_ = &constants[127];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val1 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_128 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_INIT__, sp);
   if (rval_m_128 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_128;
   }
   sp -= 0;
   rval_m_132 = enterMethodInterpreter(JAVAX_REALTIME_MEMORYAREA_GETREMAININGMEMORYSIZE, sp);
   if (rval_m_132 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_132;
   }
   sp -= 2;
   rval_m_136 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_APPEND__, sp);
   if (rval_m_136 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_136;
   }
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 140;
         goto throwNullPointer;
      }
   rval_m_140 = enterMethodInterpreter(JAVA_LANG_STRINGBUILDER_TOSTRING, sp);
   if (rval_m_140 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_140;
   }
   sp -= 1;
   rval_m_144 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_144 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_144;
   }
   i_val2 = current;
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_149 = vm_Memory_switchToArea(sp);
   if (rval_m_149 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_149;
   }
   sp--;
dummy = (int32)(*sp);
   goto L174;
   L157:
   constant_ = &constants[128];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val2 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_160 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_160 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_160;
   }
   goto L174;
   L167:
   constant_ = &constants[129];
#ifndef PRE_INITIALIZE_CONSTANTS
   initializeStringConstant(constant_, sp);
#endif
   i_val2 = (int32) (pointer) stringConstants[constant_->value >> 16];
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_170 = enterMethodInterpreter(DEVICES_CONSOLE_PRINTLN, sp);
   if (rval_m_170 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_170;
   }
   L174:
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwClassCastException:
      excep = initializeException(sp, JAVA_LANG_CLASSCASTEXCEPTION, JAVA_LANG_CLASSCASTEXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[488], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Memory */
/* Method: reset */
RANGE const unsigned char vm_Memory_reset[11] PROGMEM = {
  0x2A,0x1B,0xB5,0x00,0x7B,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: vm.Memory */
/* Method: resize */
RANGE const unsigned char vm_Memory_resize[11] PROGMEM = {
  0x2A,0x1B,0xB5,0x00,0x7B,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: vm.Memory */
/* Method: startMemoryAreaTracking */
/* startMemoryAreaTracking
 * param : 
 * return: void
 */
int16 vm_Memory_startMemoryAreaTracking(int32 *fp)
{  
   int32* sp;
   int32 i_val0;
   int16 rval_m_6;
   int8 b_val0;
   int8 lsb_int8;
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val0 = (int32)heapArea;
   rval_m_6 = vm_Memory_updateMaxUsed(sp, i_val0);
   if (rval_m_6 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_6;
   }
   b_val0 = 1;
      lsb_int8 = b_val0;
      ((struct _staticClassFields_c *)classData) -> memoryAreaTrackingEnabled_f = lsb_int8;
   return -1;
}

/* Class: vm.Memory */
/* Method: switchToArea */
/* switchToArea
 * param : vm.Memory
 * return: vm.Memory
 */
int16 vm_Memory_switchToArea(int32 *fp)
{  
   int32 i_val0;
   int32 lsb_int32;
    int32 newScope;
    int32 previousMemoryArea;
   newScope = (int32)(*(fp + 0));
   previousMemoryArea = (int32)(*(fp + 1));
   i_val0 = (int32)currentMemoryArea;
   previousMemoryArea = i_val0;
   i_val0 = newScope;
      lsb_int32 = i_val0;
      currentMemoryArea = lsb_int32;
   i_val0 = previousMemoryArea;
   *((int32*)fp) = i_val0;
   return -1;
}

/* Class: vm.Memory */
/* Method: toString */
RANGE const unsigned char vm_Memory_toString[84] PROGMEM = {
  0xBB,0x00,0x6C,0x59,0xB7,0x00,0x61,0x98,0x4C,0x2B,0x2A,0xB4,0x00,0x7B,0x22,
  /* offset: 0, 96*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, name_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x64,0x99,0x57,0x2B,0x12,0x00,0x78,0xB7,0x00,0x64,0x99,0x57,0x2B,
  0x2A,0xB4,0x00,0x7B,0x20,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, size_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,0x00,0x62,0x9E,0x57,0x2B,0xBB,0x00,0x34,
  0x59,0x12,0x00,0x82,0xB7,0x00,0x67,0x63,0x2A,0xB4,0x00,0x7B,0x20,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Memory_c, free_f) - sizeof(Object)) << 3)) & 0xff),
  0xB7,
  0x00,0x69,0x69,0xB7,0x00,0x6E,0x72,0xB7,0x00,0x64,0x99,0x57,0x2B,0xB7,0x00,0x65,
  0xA3,0xB0,0x01,0x07
};

/* Class: vm.Memory */
/* Method: updateMaxUsed */
/* updateMaxUsed
 * param : vm.Memory
 * return: void
 */
int16 vm_Memory_updateMaxUsed(int32 *fp,     int32 m)
{  
   int32* sp;
   int32 i_val1;
   unsigned char* cobj;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int32 i_val0;
   int32 lsb_int32;
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val1 = m;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 1;
         goto throwNullPointer;
      }
   i_val1 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> memoryInfo_f;
      if (i_val1 == 0) {
         goto L53;
      }
   i_val1 = m;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f;
   i_val0 = m;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> memoryInfo_f;
cobj = (unsigned char *) (pointer)i_val0;
      if (cobj == 0) {
         pc = 24;
         goto throwNullPointer;
      }
   i_val0 = ((struct _vm_Memory_MemoryInfo_c *)HEAP_REF(cobj, unsigned char*)) -> maxUsed_f;
      if (i_val1 <= i_val0) {
         goto L53;
      }
   i_val1 = m;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> memoryInfo_f;
   i_val0 = m;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _vm_Memory_c *)HEAP_REF(cobj, unsigned char*)) -> free_f;
      lsb_int32 = i_val0;
cobj = (unsigned char *) (pointer)i_val1;
      if (cobj == 0) {
         pc = 47;
         goto throwNullPointer;
      }
      ((struct _vm_Memory_MemoryInfo_c *)HEAP_REF(cobj, unsigned char*)) -> maxUsed_f = lsb_int32;
   L53:
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[494], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Monitor */
/* Method: <init> */
RANGE const unsigned char vm_Monitor_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: vm.Monitor */
/* Method: attach */
RANGE const unsigned char vm_Monitor_attach[9] PROGMEM = {
  0x2A,0x2B,0xB7,0x01,0xF1,0x12,0xB1,0x01,0x03
};

/* Class: vm.Monitor */
/* Method: attachMonitor */
/* Class: vm.Monitor */
/* Method: getAttachedMonitor */
/* Class: vm.Monitor */
/* Method: getDefaultMonitor */
/* getDefaultMonitor
 * param : 
 * return: vm.Monitor
 */
int16 vm_Monitor_getDefaultMonitor(int32 *fp)
{  
   int32* sp;
   int16 rval_m_0;
   int32 i_val0;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_10;
   Object* i_obj;
    int32 sch;
   sp = &fp[3]; /* make room for local VM state on the stack */
   sp -= 0;
   rval_m_0 = enterMethodInterpreter(VM_MACHINE_GETCURRENTSCHEDULER, sp);
   if (rval_m_0 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_0;
   }
   sp--;
   sch = (int32)(*sp);
   i_val0 = sch;
      if (i_val0 == 0) {
         goto L27;
      }
   i_val0 = sch;
   *sp = (int32)i_val0;
   sp++;
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 80:
            methodIndex = 227;
            continue;
          case 135:
            methodIndex = 352;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_10 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_10 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_10;
      }
   sp--;
   *((int32*)fp) = (int32)(*sp);
   return -1;
   L27:
   i_val0 = 0;
   *((int32*)fp) = i_val0;
   return -1;
}

/* Class: vm.Monitor */
/* Method: lock */
/* Class: vm.Monitor */
/* Method: lock */
/* lock
 * param : vm.Monitor
 * return: void
 */
int16 vm_Monitor_lock_(int32 *fp)
{  
   int32* sp;
   int32 i_val0;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_1;
    int32 monitor;
   monitor = (int32)(*(fp + 0));
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val0 = monitor;
   *sp = (int32)i_val0;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 1;
         goto throwNullPointer;
      }
   rval_m_1 = enterMethodInterpreter(JAVAX_SAFETYCRITICAL_MONITOR_LOCK, sp);
   if (rval_m_1 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_1;
   }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[501], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Monitor */
/* Method: notify */
/* notify
 * param : java.lang.Object
 * return: void
 */
int16 vm_Monitor_notify(int32 *fp)
{  
   int32* sp;
   int16 rval_m_0;
   int32 i_val1;
   int32 i_val0;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_7;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
    int32 target;
    int32 sch;
   target = (int32)(*(fp + 0));
   sch = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   sp -= 0;
   rval_m_0 = enterMethodInterpreter(VM_MACHINE_GETCURRENTSCHEDULER, sp);
   if (rval_m_0 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_0;
   }
   sp--;
   sch = (int32)(*sp);
   i_val1 = sch;
   i_val0 = target;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 7;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 80:
            methodIndex = 230;
            continue;
          case 135:
            methodIndex = 354;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_7 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_7 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_7;
      }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[502], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Monitor */
/* Method: notifyAll */
/* notifyAll
 * param : java.lang.Object
 * return: void
 */
int16 vm_Monitor_notifyAll(int32 *fp)
{  
   int32* sp;
   int16 rval_m_0;
   int32 i_val1;
   int32 i_val0;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_7;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
    int32 target;
    int32 sch;
   target = (int32)(*(fp + 0));
   sch = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   sp -= 0;
   rval_m_0 = enterMethodInterpreter(VM_MACHINE_GETCURRENTSCHEDULER, sp);
   if (rval_m_0 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_0;
   }
   sp--;
   sch = (int32)(*sp);
   i_val1 = sch;
   i_val0 = target;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 7;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 80:
            methodIndex = 231;
            continue;
          case 135:
            methodIndex = 355;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_7 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_7 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_7;
      }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[503], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Monitor */
/* Method: unlock */
/* Class: vm.Monitor */
/* Method: unlock */
/* unlock
 * param : vm.Monitor
 * return: void
 */
int16 vm_Monitor_unlock_(int32 *fp)
{  
   int32* sp;
   int32 i_val0;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
   int16 rval_m_1;
    int32 monitor;
   monitor = (int32)(*(fp + 0));
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val0 = monitor;
   *sp = (int32)i_val0;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 1;
         goto throwNullPointer;
      }
   rval_m_1 = javax_safetycritical_Monitor_unlock(sp);
   if (rval_m_1 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_1;
   }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[505], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Monitor */
/* Method: wait */
/* wait
 * param : java.lang.Object
 * return: void
 */
int16 vm_Monitor_wait(int32 *fp)
{  
   int32* sp;
   int16 rval_m_0;
   int32 i_val1;
   int32 i_val0;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_7;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
    int32 target;
    int32 sch;
   target = (int32)(*(fp + 0));
   sch = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   sp -= 0;
   rval_m_0 = enterMethodInterpreter(VM_MACHINE_GETCURRENTSCHEDULER, sp);
   if (rval_m_0 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_0;
   }
   sp--;
   sch = (int32)(*sp);
   i_val1 = sch;
   i_val0 = target;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 7;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 80:
            methodIndex = 235;
            continue;
          case 135:
            methodIndex = 356;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_7 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_7 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_7;
      }
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[506], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Process$ProcessExecutor */
/* Method: <init> */
RANGE const unsigned char vm_Process_ProcessExecutor_init_[16] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0E,0x2A,0x2B,0xB5,0x00,0x28,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Process_ProcessExecutor_c, thisProcess_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Process_ProcessExecutor_c, thisProcess_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x03
};

/* Class: vm.Process$ProcessExecutor */
/* Method: run */
/* run
 * param : 
 * return: void
 */
int16 vm_Process_ProcessExecutor_run(int32 *fp)
{  
   int32* sp;
   int32 i_val1;
   int8 b_val0;
   unsigned char* cobj;
   int8 lsb_int8;
   int32 i_val0;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   int16 rval_m_22;
   int8 b_val1;
   int16 rval_m_54;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_58;
   Object* i_obj;
   int16 rval_m_83;
   int16 rval_m_88;
   int16 rval_m_108;
   int16 rval_m_120;
    int32 this;
    int32 t;
   this = (int32)(*(fp + 0));
   t = (int32)(*(fp + 1));
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val1 = this;
   b_val0 = 0;
      lsb_int8 = b_val0;
cobj = (unsigned char *) (pointer)i_val1;
      ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> isStarted_f = lsb_int8;
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> thisProcess_f;
   i_val0 = this;
cobj = (unsigned char *) (pointer)i_val0;
   i_val0 = ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> thisProcess_f;
      if (i_val1 == 0) {
         pc = 22;
         goto throwNullPointer;
      }
   rval_m_22 = vm_Process_transferTo(sp, i_val1, i_val0);
   if (rval_m_22 == -1) {
      ;
   }
   else
   {
      sp++;
      pc = 22;
   sp--;
      excep = rval_m_22;
      goto throwIt;
   }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   b_val1 = ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> isStarted_f;
      if (b_val1 != 0) {
         goto L47;
      }
   i_val1 = this;
   b_val0 = 1;
      lsb_int8 = b_val0;
cobj = (unsigned char *) (pointer)i_val1;
      ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> isStarted_f = lsb_int8;
   goto L127;
   L47:
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> thisProcess_f;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_54 = enterMethodInterpreter(VM_PROCESS_ACCESS_0, sp);
   if (rval_m_54 == -1) {
      sp += 1;
   }
   else
   {
      sp++;
      pc = 54;
   sp--;
      excep = rval_m_54;
      goto throwIt;
   }
      sp -= 1;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 58;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 114:
            methodIndex = 371;
            continue;
          case 143:
            methodIndex = 455;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_58 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_58 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         sp++;
         pc = 58;
   sp--;
         excep = rval_m_58;
         goto throwIt;
      }
   goto L102;
   L75:
   sp--;
   t = (int32)(*sp);
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> thisProcess_f;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   rval_m_83 = enterMethodInterpreter(VM_PROCESS_ACCESS_0, sp);
   if (rval_m_83 == -1) {
      sp += 1;
   }
   else
   {
      sp++;
      pc = 83;
   sp--;
      excep = rval_m_83;
      goto throwIt;
   }
   i_val1 = t;
   *sp = (int32)i_val1;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 88;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
          case 114:
            methodIndex = 370;
            continue;
          case 143:
            methodIndex = 449;
            continue;
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_88 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_88 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         sp++;
         pc = 88;
   sp--;
         excep = rval_m_88;
         goto throwIt;
      }
   L102:
   i_val1 = ((struct _staticClassFields_c *)classData) -> instance_f__f;
   *sp = (int32)i_val1;
   sp++;
   sp -= 1;
   i_obj = (Object*) (pointer) *sp;
      if (i_obj == 0) {
         pc = 108;
         goto throwNullPointer;
      }
   rval_m_108 = enterMethodInterpreter(VM_CLOCKINTERRUPTHANDLER_YIELD, sp);
   if (rval_m_108 == -1) {
      ;
   }
   else
   {
      sp++;
      pc = 108;
   sp--;
      excep = rval_m_108;
      goto throwIt;
   }
   i_val1 = this;
cobj = (unsigned char *) (pointer)i_val1;
   i_val1 = ((struct _vm_Process_ProcessExecutor_c *)HEAP_REF(cobj, unsigned char*)) -> thisProcess_f;
   i_val0 = 1;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   rval_m_120 = enterMethodInterpreter(VM_PROCESS_ACCESS_1, sp);
   if (rval_m_120 == -1) {
      ;
   }
   else
   {
      sp++;
      pc = 120;
   sp--;
      excep = rval_m_120;
      goto throwIt;
   }
   L124:
   yieldToScheduler(sp);
   goto L124;
   L127:
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[508], excep, pc);
      sp++;
   switch(handler_pc) {
      case 75:
         goto L75;
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

RANGE const ExceptionHandler ex_vm_Process_ProcessExecutor_run[1] PROGMEM = {
  { 47, 72, 75, 29}
};

/* Class: vm.Process$SP */
/* Method: <init> */
RANGE const unsigned char vm_Process_SP_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x08,0xB1,0x01,0x01
};

/* Class: vm.Process$SP */
/* Method: <init> */
RANGE const unsigned char vm_Process_SP_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xFD,0x12,0xB1,0x01,0x03
};

/* Class: vm.Process$X86_32SP */
/* Method: <init> */
RANGE const unsigned char vm_Process_X86_32SP_init_[9] PROGMEM = {
  0x2A,0x01,0xB7,0x01,0xFE,0x0B,0xB1,0x01,0x01
};

/* Class: vm.Process$X86_32SP */
/* Method: <init> */
RANGE const unsigned char vm_Process_X86_32SP_init__[8] PROGMEM = {
  0x2A,0xB7,0x01,0xFF,0x1A,0xB1,0x01,0x03
};

/* Class: vm.Process$X86_64SP */
/* Method: <init> */
RANGE const unsigned char vm_Process_X86_64SP_init_[9] PROGMEM = {
  0x2A,0x01,0xB7,0x01,0xFE,0x0B,0xB1,0x01,0x01
};

/* Class: vm.Process$X86_64SP */
/* Method: <init> */
RANGE const unsigned char vm_Process_X86_64SP_init__[8] PROGMEM = {
  0x2A,0xB7,0x02,0x01,0x1A,0xB1,0x01,0x03
};

/* Class: vm.Process */
/* Method: <init> */
/* <init>
 * param : vm.ProcessLogic, int[]
 * return: void
 */
int16 vm_Process_init_(int32 *fp)
{  
   int32* sp;
   int32 i_val3;
   int16 rval_m_1;
   int32 i_val2;
   unsigned char* cobj;
   int32 lsb_int32;
   int8 b_val2;
   int8 lsb_int8;
   int16 rval_m_35;
   int8 b_val3;
   int8 key_int8;
   int16 rval_m_85;
   int16 rval_m_104;
   unsigned short classIndex;
   unsigned short methodIndex;
   unsigned char methodVtableIndex;
   int16 rval_m_130;
   int16 excep;
   unsigned short handler_pc;
   unsigned short pc;
   Object* i_obj;
    int32 this;
    int32 logic;
    int32 stack;
   this = (int32)(*(fp + 0));
   logic = (int32)(*(fp + 1));
   stack = (int32)(*(fp + 2));
   sp = &fp[5]; /* make room for local VM state on the stack */
   i_val3 = this;
   *sp = (int32)i_val3;
   sp++;
   sp -= 1;
   rval_m_1 = enterMethodInterpreter(JAVA_LANG_OBJECT_INIT_, sp);
   if (rval_m_1 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_1;
   }
   i_val3 = this;
   i_val2 = logic;
      lsb_int32 = i_val2;
cobj = (unsigned char *) (pointer)i_val3;
      ((struct _vm_Process_c *)HEAP_REF(cobj, unsigned char*)) -> logic_f = lsb_int32;
   i_val3 = this;
   i_val2 = stack;
      lsb_int32 = i_val2;
cobj = (unsigned char *) (pointer)i_val3;
      ((struct _vm_Process_c *)HEAP_REF(cobj, unsigned char*)) -> stack_f = lsb_int32;
   i_val3 = this;
   b_val2 = 0;
      lsb_int8 = b_val2;
cobj = (unsigned char *) (pointer)i_val3;
      ((struct _vm_Process_c *)HEAP_REF(cobj, unsigned char*)) -> isFinished_f = lsb_int8;
   i_val3 = this;
   *sp = (int32)i_val3;
   sp++;
   if (handleNewClassIndex(sp, 40) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   i_val2 = this;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_35 = enterMethodInterpreter(VM_PROCESS_PROCESSEXECUTOR_INIT_, sp);
   if (rval_m_35 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_35;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _vm_Process_c *)HEAP_REF(cobj, unsigned char*)) -> processExecuter_f = lsb_int32;
   b_val3 = architecture;
   key_int8 = b_val3;
   switch (key_int8 - 1) {
   case 0:
      goto L79;
   case 1:
      goto L98;
   case 2:
      goto L98;
   default:
      goto L98;
   }
   L79:
   i_val3 = this;
   *sp = (int32)i_val3;
   sp++;
   if (handleNewClassIndex(sp, 8) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   i_val2 = 0;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_85 = enterMethodInterpreter(VM_PROCESS_X86_64SP_INIT__, sp);
   if (rval_m_85 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_85;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _vm_Process_c *)HEAP_REF(cobj, unsigned char*)) -> sp_f = lsb_int32;
   goto L114;
   L98:
   i_val3 = this;
   *sp = (int32)i_val3;
   sp++;
   if (handleNewClassIndex(sp, 107) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val3 = *(sp - 1);
   i_val2 = 0;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
   sp -= 2;
   rval_m_104 = enterMethodInterpreter(VM_PROCESS_X86_32SP_INIT__, sp);
   if (rval_m_104 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_104;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
   sp--;
cobj = (unsigned char *) (pointer) *sp;
      ((struct _vm_Process_c *)HEAP_REF(cobj, unsigned char*)) -> sp_f = lsb_int32;
   L114:
   i_val3 = ((struct _staticClassFields_c *)classData) -> stackAnalyser_f;
      if (i_val3 == 0) {
         goto L136;
      }
   i_val3 = ((struct _staticClassFields_c *)classData) -> stackAnalyser_f;
   i_val2 = stack;
   *sp = (int32)i_val3;
   sp++;
   *sp = (int32)i_val2;
   sp++;
      sp -= 2;
      i_obj = (Object*) (pointer) *sp;
         if (i_obj == 0) {
            pc = 130;
            goto throwNullPointer;
         }
         classIndex = getClassIndex(i_obj);
      methodIndex = (unsigned short)-1;
      while (methodIndex == (unsigned short)-1) {
        switch (classIndex) {
        }
        classIndex = pgm_read_word(&classes[classIndex].superClass);
      }
      rval_m_130 = dispatchInterface(methodIndex, &methodVtableIndex, sp);
      if (rval_m_130 == -1) {
         sp += methodVtableIndex;
      }
      else
      {
         fp[0] = *sp;
         return rval_m_130;
      }
   L136:
   return -1;
   throwNullPointer:
      excep = initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION, JAVA_LANG_NULLPOINTEREXCEPTION_INIT_);
      goto throwIt;
   throwIt:
      handler_pc = handleAthrow(&methods[515], excep, pc);
      sp++;
   switch(handler_pc) {
      case (unsigned short)-1: /* Not handled */
      default:
         fp[0] = *(sp - 1);
         return excep;
   }
}

/* Class: vm.Process */
/* Method: access$0 */
RANGE const unsigned char vm_Process_access_0[10] PROGMEM = {
  0x2A,0xB4,0x00,0x12,0x22,
  /* offset: 0, 0*/
(uint8)(((uint16)((offsetof(struct _vm_Process_c, logic_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Process_c, logic_f) - sizeof(Object)) << 3)) & 0xff),
  0xB0,0x01,0x03
};

/* Class: vm.Process */
/* Method: access$1 */
RANGE const unsigned char vm_Process_access_1[11] PROGMEM = {
  0x2A,0x1B,0xB5,0x00,0x12,0x08,
  /* offset: 0, -96*/
(uint8)(((uint16)((offsetof(struct _vm_Process_c, isFinished_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Process_c, isFinished_f) - sizeof(Object)) << 3)) & 0xff),
  0xB1,0x01,0x01
};

/* Class: vm.Process */
/* Method: executeWithStack */
/* Class: vm.Process */
/* Method: initialize */
RANGE const unsigned char vm_Process_initialize[21] PROGMEM = {
  0x2A,0xB4,0x00,0x12,0x22,
  /* offset: 0, 64*/
(uint8)(((uint16)((offsetof(struct _vm_Process_c, processExecuter_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Process_c, processExecuter_f) - sizeof(Object)) << 3)) & 0xff),
  0x2A,0xB4,0x00,0x12,0x22,
  /* offset: 0, 32*/
(uint8)(((uint16)((offsetof(struct _vm_Process_c, stack_f) - sizeof(Object)) << 3)) >> 8), (uint8)(((uint16)((offsetof(struct _vm_Process_c, stack_f) - sizeof(Object)) << 3)) & 0xff),
  0xB8,0x02,
  0x06,0x52,0xB1,0x01,0x01
};

/* Class: vm.Process */
/* Method: transfer */
/* Class: vm.Process */
/* Method: transferTo */
/* transferTo
 * param : vm.Process
 * return: void
 */
int16 vm_Process_transferTo(int32 *fp,     int32 this,     int32 nextProcess)
{  
   int32* sp;
   int32 i_val1;
   int32 i_val0;
   int16 rval_m_2;
   sp = &fp[4]; /* make room for local VM state on the stack */
   i_val1 = this;
   i_val0 = nextProcess;
   *sp = (int32)i_val1;
   sp++;
   *sp = (int32)i_val0;
   sp++;
   sp -= 2;
   rval_m_2 = n_vm_Process_transfer(sp);
   if (rval_m_2 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_2;
   }
   return -1;
}

/* Class: vm.RealtimeClock$DefaultRealtimeClock */
/* Method: <init> */
RANGE const unsigned char vm_RealtimeClock_DefaultRealtimeClock_init_[8] PROGMEM = {
  0x2A,0xB7,0x02,0x0E,0x08,0xB1,0x01,0x01
};

/* Class: vm.RealtimeClock$DefaultRealtimeClock */
/* Method: <init> */
RANGE const unsigned char vm_RealtimeClock_DefaultRealtimeClock_init__[8] PROGMEM = {
  0x2A,0xB7,0x02,0x0A,0x1C,0xB1,0x01,0x03
};

/* Class: vm.RealtimeClock$DefaultRealtimeClock */
/* Method: getCurrentTime */
RANGE const unsigned char vm_RealtimeClock_DefaultRealtimeClock_getCurrentTime[9] PROGMEM = {
  0x2B,0xB8,0x02,0x10,0x15,0x57,0xB1,0x01,0x03
};

/* Class: vm.RealtimeClock$DefaultRealtimeClock */
/* Method: getGranularity */
RANGE const unsigned char vm_RealtimeClock_DefaultRealtimeClock_getGranularity[7] PROGMEM = {
  0xB8,0x02,0x0F,0x10,0xAC,0x01,0x01
};

/* Class: vm.RealtimeClock */
/* Method: <init> */
RANGE const unsigned char vm_RealtimeClock_init_[8] PROGMEM = {
  0x2A,0xB7,0x00,0x48,0x0A,0xB1,0x01,0x01
};

/* Class: vm.RealtimeClock */
/* Method: access$0 */
RANGE const unsigned char vm_RealtimeClock_access_0[7] PROGMEM = {
  0xB8,0x02,0x14,0x30,0xAC,0x01,0x00
};

/* Class: vm.RealtimeClock */
/* Method: access$1 */
RANGE const unsigned char vm_RealtimeClock_access_1[8] PROGMEM = {
  0x2A,0xB8,0x02,0x15,0x33,0xAC,0x01,0x01
};

/* Class: vm.RealtimeClock */
/* Method: awaitNextTick */
/* Class: vm.RealtimeClock */
/* Method: getCurrentTime */
/* Class: vm.RealtimeClock */
/* Method: getGranularity */
/* Class: vm.RealtimeClock */
/* Method: getNativeResolution */
/* Class: vm.RealtimeClock */
/* Method: getNativeTime */
/* Class: vm.RealtimeClock */
/* Method: getRealtimeClock */
/* getRealtimeClock
 * param : 
 * return: vm.RealtimeClock
 */
int16 vm_RealtimeClock_getRealtimeClock(int32 *fp)
{  
   int32* sp;
   int32 i_val2;
   int8 b_val2;
   int8 key_int8;
   int16 rval_m_48;
   int32 lsb_int32;
   int32 i_val1;
   int16 rval_m_66;
   int16 rval_m_76;
   sp = &fp[3]; /* make room for local VM state on the stack */
   i_val2 = ((struct _staticClassFields_c *)classData) -> instance_f;
      if (i_val2 == 0) {
         goto L18;
      }
   i_val2 = ((struct _staticClassFields_c *)classData) -> instance_f;
   *((int32*)fp) = i_val2;
   return -1;
   L18:
   b_val2 = architecture;
   key_int8 = b_val2;
   switch (key_int8 - 3) {
   case 0:
      goto L44;
   default:
      goto L61;
   }
   L44:
   if (handleNewClassIndex(sp, 113) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   *sp = (int32)i_val2;
   sp++;
   sp -= 1;
   rval_m_48 = enterMethodInterpreter(DEVICES_CR16C_KT4585_CR16CREALTIMECLOCK_INIT_, sp);
   if (rval_m_48 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_48;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
      ((struct _staticClassFields_c *)classData) -> instance_f = lsb_int32;
   goto L76;
   L61:
   if (handleNewClassIndex(sp, 56) == 0) {
      fp[0] = *sp;
      return getClassIndex((Object*) (pointer) *sp);
   }
   sp++;
   i_val2 = *(sp - 1);
   i_val1 = 0;
   *sp = (int32)i_val2;
   sp++;
   *sp = (int32)i_val1;
   sp++;
   sp -= 2;
   rval_m_66 = enterMethodInterpreter(VM_REALTIMECLOCK_DEFAULTREALTIMECLOCK_INIT__, sp);
   if (rval_m_66 == -1) {
      ;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_66;
   }
   sp--;
      lsb_int32 = (int32)(*sp);
      ((struct _staticClassFields_c *)classData) -> instance_f = lsb_int32;
   L76:
   sp -= 0;
   rval_m_76 = vm_RealtimeClock_getRealtimeClock(sp);
   if (rval_m_76 == -1) {
      sp += 1;
   }
   else
   {
      fp[0] = *sp;
      return rval_m_76;
   }
   sp--;
   *((int32*)fp) = (int32)(*sp);
   return -1;
}

/* nwrite
 * param : byte[], int
 * return: void
 */
extern int16 n_devices_X86Writer_nwrite(int32 *sp);
/* <clinit>
 * param : 
 * return: void
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_CLASS_CLINIT_
int16 n_java_lang_Class_clinit_(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_CLASS_CLINIT_);
   return -1;
}
#else
extern int16 n_java_lang_Class_clinit_(int32 *sp);
#endif

/* desiredAssertionStatus
 * param : 
 * return: boolean
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS
int16 n_java_lang_Class_desiredAssertionStatus(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS);
   return -1;
}
#else
extern int16 n_java_lang_Class_desiredAssertionStatus(int32 *sp);
#endif

/* getComponentType
 * param : 
 * return: java.lang.Class
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_CLASS_GETCOMPONENTTYPE
int16 n_java_lang_Class_getComponentType(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE);
   return -1;
}
#else
extern int16 n_java_lang_Class_getComponentType(int32 *sp);
#endif

/* getName0
 * param : 
 * return: java.lang.String
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_CLASS_GETNAME0
int16 n_java_lang_Class_getName0(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_CLASS_GETNAME0);
   return -1;
}
#else
extern int16 n_java_lang_Class_getName0(int32 *sp);
#endif

/* getPrimitiveClass
 * param : java.lang.String
 * return: java.lang.Class
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_CLASS_GETPRIMITIVECLASS
int16 n_java_lang_Class_getPrimitiveClass(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS);
   return -1;
}
#else
extern int16 n_java_lang_Class_getPrimitiveClass(int32 *sp);
#endif

/* toString
 * param : 
 * return: java.lang.String
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_CLASS_TOSTRING
int16 n_java_lang_Class_toString(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_CLASS_TOSTRING);
   return -1;
}
#else
extern int16 n_java_lang_Class_toString(int32 *sp);
#endif

/* doubleToRawLongBits
 * param : double
 * return: long
 */
extern int16 n_java_lang_Double_doubleToRawLongBits(int32 *sp);
/* longBitsToDouble
 * param : long
 * return: double
 */
extern int16 n_java_lang_Double_longBitsToDouble(int32 *sp);
/* floatToRawIntBits
 * param : float
 * return: int
 */
extern int16 n_java_lang_Float_floatToRawIntBits(int32 *sp);
/* getClass
 * param : 
 * return: java.lang.Class
 */
extern int16 n_java_lang_Object_getClass(int32 *sp);
/* hashCode
 * param : 
 * return: int
 */
extern int16 n_java_lang_Object_hashCode(int32 *sp);
/* notify
 * param : 
 * return: void
 */
extern int16 n_java_lang_Object_notify(int32 *sp);
/* wait
 * param : long
 * return: void
 */
extern int16 n_java_lang_Object_wait_(int32 *sp);
/* arraycopy
 * param : java.lang.Object, int, java.lang.Object, int, int
 * return: void
 */
extern int16 n_java_lang_System_arraycopy(int32 *sp);
/* registerNatives
 * param : 
 * return: void
 */
extern int16 n_java_lang_System_registerNatives(int32 *sp);
/* <clinit>
 * param : 
 * return: void
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_THROWABLE_CLINIT_
int16 n_java_lang_Throwable_clinit_(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_THROWABLE_CLINIT_);
   return -1;
}
#else
extern int16 n_java_lang_Throwable_clinit_(int32 *sp);
#endif

/* fillInStackTrace
 * param : int
 * return: java.lang.Throwable
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE_
int16 n_java_lang_Throwable_fillInStackTrace_(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE_);
   return -1;
}
#else
extern int16 n_java_lang_Throwable_fillInStackTrace_(int32 *sp);
#endif

/* printStackTrace
 * param : 
 * return: void
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_THROWABLE_PRINTSTACKTRACE
int16 n_java_lang_Throwable_printStackTrace(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_THROWABLE_PRINTSTACKTRACE);
   return -1;
}
#else
extern int16 n_java_lang_Throwable_printStackTrace(int32 *sp);
#endif

/* newArray
 * param : java.lang.Class, int
 * return: java.lang.Object
 */
#ifndef EXCLUDESTUB_N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY
int16 n_java_lang_reflect_Array_newArray(int32 *sp)
{
   unimplemented_native_function(N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY);
   return -1;
}
#else
extern int16 n_java_lang_reflect_Array_newArray(int32 *sp);
#endif

/* attachMonitor
 * param : java.lang.Object
 * return: void
 */
extern int16 n_vm_Monitor_attachMonitor(int32 *sp);
/* getAttachedMonitor
 * param : java.lang.Object
 * return: java.lang.Object
 */
extern int16 n_vm_Monitor_getAttachedMonitor(int32 *sp);
/* executeWithStack
 * param : java.lang.Runnable, int[]
 * return: void
 */
extern int16 n_vm_Process_executeWithStack(int32 *sp);
/* transfer
 * param : vm.Process, vm.Process
 * return: void
 */
extern int16 n_vm_Process_transfer(int32 *sp);
/* awaitNextTick
 * param : 
 * return: void
 */
extern int16 n_vm_RealtimeClock_awaitNextTick(int32 *sp);
/* getNativeResolution
 * param : 
 * return: int
 */
extern int16 n_vm_RealtimeClock_getNativeResolution(int32 *sp);
/* getNativeTime
 * param : javax.realtime.AbsoluteTime
 * return: int
 */
extern int16 n_vm_RealtimeClock_getNativeTime(int32 *sp);

RANGE static const char methodName0[51] PROGMEM = "devices.CR16C.KT4585.CR16CInterruptDispatcher.init";
RANGE static const char methodName1[47] PROGMEM = "devices.CR16C.KT4585.CR16CRealtimeClock.<init>";
RANGE static const char methodName2[48] PROGMEM = "devices.CR16C.KT4585.CR16CRealtimeClock.disable";
RANGE static const char methodName3[47] PROGMEM = "devices.CR16C.KT4585.CR16CRealtimeClock.enable";
RANGE static const char methodName4[55] PROGMEM = "devices.CR16C.KT4585.CR16CRealtimeClock.getCurrentTime";
RANGE static const char methodName5[55] PROGMEM = "devices.CR16C.KT4585.CR16CRealtimeClock.getGranularity";
RANGE static const char methodName6[47] PROGMEM = "devices.CR16C.KT4585.CR16CRealtimeClock.handle";
RANGE static const char methodName7[43] PROGMEM = "devices.CR16C.KT4585.DeviceRegShort.<init>";
RANGE static const char methodName8[25] PROGMEM = "devices.Console.<clinit>";
RANGE static const char methodName9[25] PROGMEM = "devices.Console.getBytes";
RANGE static const char methodName10[24] PROGMEM = "devices.Console.println";
RANGE static const char methodName11[24] PROGMEM = "devices.Console.println";
RANGE static const char methodName12[25] PROGMEM = "devices.X86Writer.<init>";
RANGE static const char methodName13[7] PROGMEM = "nwrite";
RANGE static const char methodName14[24] PROGMEM = "devices.X86Writer.write";
RANGE static const char methodName15[40] PROGMEM = "devices.i86.I86InterruptDispatcher.init";
RANGE static const char methodName16[39] PROGMEM = "java.lang.AbstractStringBuilder.<init>";
RANGE static const char methodName17[39] PROGMEM = "java.lang.AbstractStringBuilder.append";
RANGE static const char methodName18[39] PROGMEM = "java.lang.AbstractStringBuilder.append";
RANGE static const char methodName19[39] PROGMEM = "java.lang.AbstractStringBuilder.append";
RANGE static const char methodName20[39] PROGMEM = "java.lang.AbstractStringBuilder.append";
RANGE static const char methodName21[43] PROGMEM = "java.lang.AbstractStringBuilder.appendNull";
RANGE static const char methodName22[55] PROGMEM = "java.lang.AbstractStringBuilder.ensureCapacityInternal";
RANGE static const char methodName23[47] PROGMEM = "java.lang.AbstractStringBuilder.expandCapacity";
RANGE static const char methodName24[37] PROGMEM = "java.lang.ArithmeticException.<init>";
RANGE static const char methodName25[48] PROGMEM = "java.lang.ArrayIndexOutOfBoundsException.<init>";
RANGE static const char methodName26[37] PROGMEM = "java.lang.ArrayStoreException.<init>";
RANGE static const char methodName27[32] PROGMEM = "java.lang.AssertionError.<init>";
RANGE static const char methodName28[9] PROGMEM = "<clinit>";
RANGE static const char methodName29[23] PROGMEM = "desiredAssertionStatus";
RANGE static const char methodName30[17] PROGMEM = "getComponentType";
RANGE static const char methodName31[9] PROGMEM = "getName0";
RANGE static const char methodName32[24] PROGMEM = "java.lang.Class.getName";
RANGE static const char methodName33[18] PROGMEM = "getPrimitiveClass";
RANGE static const char methodName34[9] PROGMEM = "toString";
RANGE static const char methodName35[36] PROGMEM = "java.lang.ClassCastException.<init>";
RANGE static const char methodName36[36] PROGMEM = "java.lang.ClassCastException.<init>";
RANGE static const char methodName37[26] PROGMEM = "java.lang.Double.<clinit>";
RANGE static const char methodName38[20] PROGMEM = "doubleToRawLongBits";
RANGE static const char methodName39[17] PROGMEM = "longBitsToDouble";
RANGE static const char methodName40[22] PROGMEM = "java.lang.Enum.<init>";
RANGE static const char methodName41[24] PROGMEM = "java.lang.Enum.hashCode";
RANGE static const char methodName42[24] PROGMEM = "java.lang.Enum.toString";
RANGE static const char methodName43[23] PROGMEM = "java.lang.Error.<init>";
RANGE static const char methodName44[23] PROGMEM = "java.lang.Error.<init>";
RANGE static const char methodName45[27] PROGMEM = "java.lang.Exception.<init>";
RANGE static const char methodName46[27] PROGMEM = "java.lang.Exception.<init>";
RANGE static const char methodName47[25] PROGMEM = "java.lang.Float.<clinit>";
RANGE static const char methodName48[18] PROGMEM = "floatToRawIntBits";
RANGE static const char methodName49[42] PROGMEM = "java.lang.IllegalArgumentException.<init>";
RANGE static const char methodName50[42] PROGMEM = "java.lang.IllegalArgumentException.<init>";
RANGE static const char methodName51[46] PROGMEM = "java.lang.IllegalMonitorStateException.<init>";
RANGE static const char methodName52[46] PROGMEM = "java.lang.IllegalMonitorStateException.<init>";
RANGE static const char methodName53[43] PROGMEM = "java.lang.IndexOutOfBoundsException.<init>";
RANGE static const char methodName54[43] PROGMEM = "java.lang.IndexOutOfBoundsException.<init>";
RANGE static const char methodName55[27] PROGMEM = "java.lang.Integer.<clinit>";
RANGE static const char methodName56[36] PROGMEM = "java.lang.Integer.formatUnsignedInt";
RANGE static const char methodName57[27] PROGMEM = "java.lang.Integer.getChars";
RANGE static const char methodName58[39] PROGMEM = "java.lang.Integer.numberOfLeadingZeros";
RANGE static const char methodName59[29] PROGMEM = "java.lang.Integer.stringSize";
RANGE static const char methodName60[30] PROGMEM = "java.lang.Integer.toHexString";
RANGE static const char methodName61[36] PROGMEM = "java.lang.Integer.toUnsignedString0";
RANGE static const char methodName62[24] PROGMEM = "java.lang.Long.<clinit>";
RANGE static const char methodName63[24] PROGMEM = "java.lang.Long.getChars";
RANGE static const char methodName64[26] PROGMEM = "java.lang.Long.stringSize";
RANGE static const char methodName65[24] PROGMEM = "java.lang.Math.<clinit>";
RANGE static const char methodName66[22] PROGMEM = "java.lang.Math.<init>";
RANGE static const char methodName67[19] PROGMEM = "java.lang.Math.max";
RANGE static const char methodName68[19] PROGMEM = "java.lang.Math.min";
RANGE static const char methodName69[27] PROGMEM = "java.lang.Math.powerOfTwoD";
RANGE static const char methodName70[44] PROGMEM = "java.lang.NegativeArraySizeException.<init>";
RANGE static const char methodName71[38] PROGMEM = "java.lang.NullPointerException.<init>";
RANGE static const char methodName72[24] PROGMEM = "java.lang.Object.<init>";
RANGE static const char methodName73[9] PROGMEM = "getClass";
RANGE static const char methodName74[9] PROGMEM = "hashCode";
RANGE static const char methodName75[7] PROGMEM = "notify";
RANGE static const char methodName76[26] PROGMEM = "java.lang.Object.toString";
RANGE static const char methodName77[22] PROGMEM = "java.lang.Object.wait";
RANGE static const char methodName78[5] PROGMEM = "wait";
RANGE static const char methodName79[34] PROGMEM = "java.lang.OutOfMemoryError.<init>";
RANGE static const char methodName80[34] PROGMEM = "java.lang.OutOfMemoryError.<init>";
RANGE static const char methodName81[34] PROGMEM = "java.lang.RuntimeException.<init>";
RANGE static const char methodName82[34] PROGMEM = "java.lang.RuntimeException.<init>";
RANGE static const char methodName83[50] PROGMEM = "java.lang.String$CaseInsensitiveComparator.<init>";
RANGE static const char methodName84[50] PROGMEM = "java.lang.String$CaseInsensitiveComparator.<init>";
RANGE static const char methodName85[26] PROGMEM = "java.lang.String.<clinit>";
RANGE static const char methodName86[24] PROGMEM = "java.lang.String.<init>";
RANGE static const char methodName87[24] PROGMEM = "java.lang.String.<init>";
RANGE static const char methodName88[24] PROGMEM = "java.lang.String.<init>";
RANGE static const char methodName89[24] PROGMEM = "java.lang.String.charAt";
RANGE static const char methodName90[27] PROGMEM = "java.lang.String.compareTo";
RANGE static const char methodName91[24] PROGMEM = "java.lang.String.equals";
RANGE static const char methodName92[26] PROGMEM = "java.lang.String.getChars";
RANGE static const char methodName93[26] PROGMEM = "java.lang.String.hashCode";
RANGE static const char methodName94[24] PROGMEM = "java.lang.String.length";
RANGE static const char methodName95[26] PROGMEM = "java.lang.String.toString";
RANGE static const char methodName96[25] PROGMEM = "java.lang.String.valueOf";
RANGE static const char methodName97[30] PROGMEM = "java.lang.StringBuffer.<init>";
RANGE static const char methodName98[30] PROGMEM = "java.lang.StringBuffer.append";
RANGE static const char methodName99[30] PROGMEM = "java.lang.StringBuffer.append";
RANGE static const char methodName100[30] PROGMEM = "java.lang.StringBuffer.append";
RANGE static const char methodName101[32] PROGMEM = "java.lang.StringBuffer.toString";
RANGE static const char methodName102[31] PROGMEM = "java.lang.StringBuilder.<init>";
RANGE static const char methodName103[31] PROGMEM = "java.lang.StringBuilder.<init>";
RANGE static const char methodName104[31] PROGMEM = "java.lang.StringBuilder.append";
RANGE static const char methodName105[31] PROGMEM = "java.lang.StringBuilder.append";
RANGE static const char methodName106[31] PROGMEM = "java.lang.StringBuilder.append";
RANGE static const char methodName107[31] PROGMEM = "java.lang.StringBuilder.append";
RANGE static const char methodName108[31] PROGMEM = "java.lang.StringBuilder.append";
RANGE static const char methodName109[31] PROGMEM = "java.lang.StringBuilder.append";
RANGE static const char methodName110[33] PROGMEM = "java.lang.StringBuilder.toString";
RANGE static const char methodName111[49] PROGMEM = "java.lang.StringIndexOutOfBoundsException.<init>";
RANGE static const char methodName112[26] PROGMEM = "java.lang.System.<clinit>";
RANGE static const char methodName113[10] PROGMEM = "arraycopy";
RANGE static const char methodName114[16] PROGMEM = "registerNatives";
RANGE static const char methodName115[9] PROGMEM = "<clinit>";
RANGE static const char methodName116[27] PROGMEM = "java.lang.Throwable.<init>";
RANGE static const char methodName117[27] PROGMEM = "java.lang.Throwable.<init>";
RANGE static const char methodName118[37] PROGMEM = "java.lang.Throwable.fillInStackTrace";
RANGE static const char methodName119[17] PROGMEM = "fillInStackTrace";
RANGE static const char methodName120[40] PROGMEM = "java.lang.Throwable.getLocalizedMessage";
RANGE static const char methodName121[31] PROGMEM = "java.lang.Throwable.getMessage";
RANGE static const char methodName122[16] PROGMEM = "printStackTrace";
RANGE static const char methodName123[29] PROGMEM = "java.lang.Throwable.toString";
RANGE static const char methodName124[47] PROGMEM = "java.lang.UnsupportedOperationException.<init>";
RANGE static const char methodName125[37] PROGMEM = "java.lang.VirtualMachineError.<init>";
RANGE static const char methodName126[37] PROGMEM = "java.lang.VirtualMachineError.<init>";
RANGE static const char methodName127[9] PROGMEM = "newArray";
RANGE static const char methodName128[36] PROGMEM = "java.lang.reflect.Array.newInstance";
RANGE static const char methodName129[36] PROGMEM = "java.util.AbstractCollection.<init>";
RANGE static const char methodName130[9] PROGMEM = "iterator";
RANGE static const char methodName131[38] PROGMEM = "java.util.AbstractCollection.toString";
RANGE static const char methodName132[30] PROGMEM = "java.util.AbstractList.<init>";
RANGE static const char methodName133[32] PROGMEM = "java.util.AbstractList.hashCode";
RANGE static const char methodName134[32] PROGMEM = "java.util.AbstractList.iterator";
RANGE static const char methodName135[31] PROGMEM = "java.util.ArrayList$Itr.<init>";
RANGE static const char methodName136[31] PROGMEM = "java.util.ArrayList$Itr.<init>";
RANGE static const char methodName137[47] PROGMEM = "java.util.ArrayList$Itr.checkForComodification";
RANGE static const char methodName138[32] PROGMEM = "java.util.ArrayList$Itr.hasNext";
RANGE static const char methodName139[29] PROGMEM = "java.util.ArrayList$Itr.next";
RANGE static const char methodName140[29] PROGMEM = "java.util.ArrayList.<clinit>";
RANGE static const char methodName141[27] PROGMEM = "java.util.ArrayList.<init>";
RANGE static const char methodName142[31] PROGMEM = "java.util.ArrayList.access$100";
RANGE static const char methodName143[24] PROGMEM = "java.util.ArrayList.add";
RANGE static const char methodName144[43] PROGMEM = "java.util.ArrayList.ensureCapacityInternal";
RANGE static const char methodName145[43] PROGMEM = "java.util.ArrayList.ensureExplicitCapacity";
RANGE static const char methodName146[25] PROGMEM = "java.util.ArrayList.grow";
RANGE static const char methodName147[33] PROGMEM = "java.util.ArrayList.hugeCapacity";
RANGE static const char methodName148[29] PROGMEM = "java.util.ArrayList.iterator";
RANGE static const char methodName149[25] PROGMEM = "java.util.ArrayList.size";
RANGE static const char methodName150[26] PROGMEM = "java.util.Arrays.<clinit>";
RANGE static const char methodName151[24] PROGMEM = "java.util.Arrays.<init>";
RANGE static const char methodName152[24] PROGMEM = "java.util.Arrays.copyOf";
RANGE static const char methodName153[24] PROGMEM = "java.util.Arrays.copyOf";
RANGE static const char methodName154[24] PROGMEM = "java.util.Arrays.copyOf";
RANGE static const char methodName155[29] PROGMEM = "java.util.Arrays.copyOfRange";
RANGE static const char methodName156[49] PROGMEM = "java.util.ConcurrentModificationException.<init>";
RANGE static const char methodName157[40] PROGMEM = "java.util.NoSuchElementException.<init>";
RANGE static const char methodName158[35] PROGMEM = "javax.realtime.AbsoluteTime.<init>";
RANGE static const char methodName159[35] PROGMEM = "javax.realtime.AbsoluteTime.<init>";
RANGE static const char methodName160[35] PROGMEM = "javax.realtime.AbsoluteTime.<init>";
RANGE static const char methodName161[35] PROGMEM = "javax.realtime.AbsoluteTime.<init>";
RANGE static const char methodName162[35] PROGMEM = "javax.realtime.AbsoluteTime.<init>";
RANGE static const char methodName163[32] PROGMEM = "javax.realtime.AbsoluteTime.add";
RANGE static const char methodName164[32] PROGMEM = "javax.realtime.AbsoluteTime.add";
RANGE static const char methodName165[48] PROGMEM = "javax.realtime.AbstractAsyncEventHandler.<init>";
RANGE static const char methodName166[42] PROGMEM = "javax.realtime.AperiodicParameters.<init>";
RANGE static const char methodName167[40] PROGMEM = "javax.realtime.AsyncEventHandler.<init>";
RANGE static const char methodName168[50] PROGMEM = "javax.realtime.AsyncEventHandler.handleAsyncEvent";
RANGE static const char methodName169[37] PROGMEM = "javax.realtime.AsyncEventHandler.run";
RANGE static const char methodName170[45] PROGMEM = "javax.realtime.BoundAsyncEventHandler.<init>";
RANGE static const char methodName171[28] PROGMEM = "javax.realtime.Clock.<init>";
RANGE static const char methodName172[38] PROGMEM = "javax.realtime.Clock.getRealtimeClock";
RANGE static const char methodName173[14] PROGMEM = "getResolution";
RANGE static const char methodName174[8] PROGMEM = "getTime";
RANGE static const char methodName175[41] PROGMEM = "javax.realtime.HighResolutionTime.<init>";
RANGE static const char methodName176[44] PROGMEM = "javax.realtime.HighResolutionTime.compareTo";
RANGE static const char methodName177[43] PROGMEM = "javax.realtime.HighResolutionTime.getClock";
RANGE static const char methodName178[50] PROGMEM = "javax.realtime.HighResolutionTime.getMilliseconds";
RANGE static const char methodName179[49] PROGMEM = "javax.realtime.HighResolutionTime.getNanoseconds";
RANGE static const char methodName180[43] PROGMEM = "javax.realtime.HighResolutionTime.hashCode";
RANGE static const char methodName181[47] PROGMEM = "javax.realtime.HighResolutionTime.isNormalized";
RANGE static const char methodName182[38] PROGMEM = "javax.realtime.HighResolutionTime.set";
RANGE static const char methodName183[38] PROGMEM = "javax.realtime.HighResolutionTime.set";
RANGE static const char methodName184[38] PROGMEM = "javax.realtime.HighResolutionTime.set";
RANGE static const char methodName185[48] PROGMEM = "javax.realtime.HighResolutionTime.setNormalized";
RANGE static const char methodName186[43] PROGMEM = "javax.realtime.HighResolutionTime.toString";
RANGE static const char methodName187[33] PROGMEM = "javax.realtime.MemoryArea.<init>";
RANGE static const char methodName188[33] PROGMEM = "javax.realtime.MemoryArea.<init>";
RANGE static const char methodName189[45] PROGMEM = "javax.realtime.MemoryArea.addContainedMemory";
RANGE static const char methodName190[45] PROGMEM = "javax.realtime.MemoryArea.getNamedMemoryArea";
RANGE static const char methodName191[45] PROGMEM = "javax.realtime.MemoryArea.getNamedMemoryArea";
RANGE static const char methodName192[55] PROGMEM = "javax.realtime.MemoryArea.getRemainingBackingstoreSize";
RANGE static const char methodName193[49] PROGMEM = "javax.realtime.MemoryArea.getRemainingMemorySize";
RANGE static const char methodName194[41] PROGMEM = "javax.realtime.MemoryArea.memoryConsumed";
RANGE static const char methodName195[48] PROGMEM = "javax.realtime.MemoryArea.removeContainedMemory";
RANGE static const char methodName196[40] PROGMEM = "javax.realtime.MemoryArea.removeMemArea";
RANGE static const char methodName197[40] PROGMEM = "javax.realtime.MemoryArea.resizeMemArea";
RANGE static const char methodName198[35] PROGMEM = "javax.realtime.MemoryArea.toString";
RANGE static const char methodName199[43] PROGMEM = "javax.realtime.NoHeapRealtimeThread.<init>";
RANGE static const char methodName200[41] PROGMEM = "javax.realtime.PeriodicParameters.<init>";
RANGE static const char methodName201[41] PROGMEM = "javax.realtime.PeriodicParameters.<init>";
RANGE static const char methodName202[44] PROGMEM = "javax.realtime.PeriodicParameters.getPeriod";
RANGE static const char methodName203[43] PROGMEM = "javax.realtime.PeriodicParameters.getStart";
RANGE static const char methodName204[41] PROGMEM = "javax.realtime.PriorityParameters.<init>";
RANGE static const char methodName205[46] PROGMEM = "javax.realtime.PriorityParameters.getPriority";
RANGE static const char methodName206[46] PROGMEM = "javax.realtime.PriorityParameters.setPriority";
RANGE static const char methodName207[40] PROGMEM = "javax.realtime.PriorityScheduler.<init>";
RANGE static const char methodName208[38] PROGMEM = "javax.realtime.RealtimeClock.<clinit>";
RANGE static const char methodName209[36] PROGMEM = "javax.realtime.RealtimeClock.<init>";
RANGE static const char methodName210[43] PROGMEM = "javax.realtime.RealtimeClock.getResolution";
RANGE static const char methodName211[43] PROGMEM = "javax.realtime.RealtimeClock.getResolution";
RANGE static const char methodName212[37] PROGMEM = "javax.realtime.RealtimeClock.getTime";
RANGE static const char methodName213[38] PROGMEM = "javax.realtime.RealtimeClock.instance";
RANGE static const char methodName214[43] PROGMEM = "javax.realtime.RealtimeClock.setResolution";
RANGE static const char methodName215[37] PROGMEM = "javax.realtime.RealtimeThread.<init>";
RANGE static const char methodName216[35] PROGMEM = "javax.realtime.RelativeTime.<init>";
RANGE static const char methodName217[35] PROGMEM = "javax.realtime.RelativeTime.<init>";
RANGE static const char methodName218[35] PROGMEM = "javax.realtime.RelativeTime.<init>";
RANGE static const char methodName219[35] PROGMEM = "javax.realtime.RelativeTime.<init>";
RANGE static const char methodName220[35] PROGMEM = "javax.realtime.RelativeTime.<init>";
RANGE static const char methodName221[40] PROGMEM = "javax.realtime.ReleaseParameters.<init>";
RANGE static const char methodName222[40] PROGMEM = "javax.realtime.ReleaseParameters.<init>";
RANGE static const char methodName223[32] PROGMEM = "javax.realtime.Scheduler.<init>";
RANGE static const char methodName224[43] PROGMEM = "javax.realtime.SchedulingParameters.<init>";
RANGE static const char methodName225[44] PROGMEM = "javax.safetycritical.CyclicScheduler.<init>";
RANGE static const char methodName226[55] PROGMEM = "javax.safetycritical.CyclicScheduler.getCurrentProcess";
RANGE static const char methodName227[55] PROGMEM = "javax.safetycritical.CyclicScheduler.getDefaultMonitor";
RANGE static const char methodName228[52] PROGMEM = "javax.safetycritical.CyclicScheduler.getNextProcess";
RANGE static const char methodName229[46] PROGMEM = "javax.safetycritical.CyclicScheduler.instance";
RANGE static const char methodName230[44] PROGMEM = "javax.safetycritical.CyclicScheduler.notify";
RANGE static const char methodName231[47] PROGMEM = "javax.safetycritical.CyclicScheduler.notifyAll";
RANGE static const char methodName232[50] PROGMEM = "javax.safetycritical.CyclicScheduler.processStart";
RANGE static const char methodName233[43] PROGMEM = "javax.safetycritical.CyclicScheduler.start";
RANGE static const char methodName234[42] PROGMEM = "javax.safetycritical.CyclicScheduler.stop";
RANGE static const char methodName235[42] PROGMEM = "javax.safetycritical.CyclicScheduler.wait";
RANGE static const char methodName236[37] PROGMEM = "javax.safetycritical.Launcher.<init>";
RANGE static const char methodName237[34] PROGMEM = "javax.safetycritical.Launcher.run";
RANGE static const char methodName238[42] PROGMEM = "javax.safetycritical.Launcher.startLevel0";
RANGE static const char methodName239[44] PROGMEM = "javax.safetycritical.Launcher.startLevel1_2";
RANGE static const char methodName240[48] PROGMEM = "javax.safetycritical.ManagedEventHandler.<init>";
RANGE static const char methodName241[49] PROGMEM = "javax.safetycritical.ManagedEventHandler.cleanUp";
RANGE static const char methodName242[62] PROGMEM = "javax.safetycritical.ManagedLongEventHandler.getCurrentMemory";
RANGE static const char methodName243[62] PROGMEM = "javax.safetycritical.ManagedLongEventHandler.setCurrentMemory";
RANGE static const char methodName244[55] PROGMEM = "javax.safetycritical.ManagedMemory$BackingStore.<init>";
RANGE static const char methodName245[57] PROGMEM = "javax.safetycritical.ManagedMemory$ImmortalMemory.<init>";
RANGE static const char methodName246[59] PROGMEM = "javax.safetycritical.ManagedMemory$ImmortalMemory.instance";
RANGE static const char methodName247[44] PROGMEM = "javax.safetycritical.ManagedMemory.<clinit>";
RANGE static const char methodName248[42] PROGMEM = "javax.safetycritical.ManagedMemory.<init>";
RANGE static const char methodName249[56] PROGMEM = "javax.safetycritical.ManagedMemory.allocateBackingStore";
RANGE static const char methodName250[41] PROGMEM = "javax.safetycritical.ManagedMemory.enter";
RANGE static const char methodName251[49] PROGMEM = "javax.safetycritical.ManagedMemory.executeInArea";
RANGE static const char methodName252[53] PROGMEM = "javax.safetycritical.ManagedMemory.getCurrentProcess";
RANGE static const char methodName253[47] PROGMEM = "javax.safetycritical.ManagedMemory.getDelegate";
RANGE static const char methodName254[46] PROGMEM = "javax.safetycritical.ManagedMemory.removeArea";
RANGE static const char methodName255[45] PROGMEM = "javax.safetycritical.ManagedMemory.resetArea";
RANGE static const char methodName256[46] PROGMEM = "javax.safetycritical.ManagedMemory.resizeArea";
RANGE static const char methodName257[44] PROGMEM = "javax.safetycritical.ManagedMemory.runEnter";
RANGE static const char methodName258[52] PROGMEM = "javax.safetycritical.ManagedMemory.runExecuteInArea";
RANGE static const char methodName259[58] PROGMEM = "javax.safetycritical.ManagedSchedMethods.createScjProcess";
RANGE static const char methodName260[58] PROGMEM = "javax.safetycritical.ManagedSchedMethods.createScjProcess";
RANGE static const char methodName261[52] PROGMEM = "javax.safetycritical.ManagedSchedMethods.getMission";
RANGE static const char methodName262[62] PROGMEM = "javax.safetycritical.ManagedSchedMethods.getPriorityParameter";
RANGE static const char methodName263[55] PROGMEM = "javax.safetycritical.ManagedSchedMethods.getScjProcess";
RANGE static const char methodName264[52] PROGMEM = "javax.safetycritical.ManagedSchedMethods.getStorage";
RANGE static const char methodName265[50] PROGMEM = "javax.safetycritical.ManagedSchedulableSet.<init>";
RANGE static const char methodName266[49] PROGMEM = "javax.safetycritical.ManagedSchedulableSet.addMS";
RANGE static const char methodName267[52] PROGMEM = "javax.safetycritical.ManagedSchedulableSet.contains";
RANGE static const char methodName268[67] PROGMEM = "javax.safetycritical.ManagedSchedulableSet.removeAperiodicHandlers";
RANGE static const char methodName269[58] PROGMEM = "javax.safetycritical.ManagedSchedulableSet.removeMSObject";
RANGE static const char methodName270[52] PROGMEM = "javax.safetycritical.ManagedSchedulableSet.toString";
RANGE static const char methodName271[42] PROGMEM = "javax.safetycritical.ManagedThread.<init>";
RANGE static const char methodName272[42] PROGMEM = "javax.safetycritical.ManagedThread.<init>";
RANGE static const char methodName273[43] PROGMEM = "javax.safetycritical.ManagedThread.cleanUp";
RANGE static const char methodName274[44] PROGMEM = "javax.safetycritical.ManagedThread.register";
RANGE static const char methodName275[41] PROGMEM = "javax.safetycritical.ManagedThread.sleep";
RANGE static const char methodName276[39] PROGMEM = "javax.safetycritical.MemoryInfo.<init>";
RANGE static const char methodName277[39] PROGMEM = "javax.safetycritical.MemoryInfo.<init>";
RANGE static const char methodName278[38] PROGMEM = "javax.safetycritical.Mission.<clinit>";
RANGE static const char methodName279[36] PROGMEM = "javax.safetycritical.Mission.<init>";
RANGE static const char methodName280[43] PROGMEM = "javax.safetycritical.Mission.addNewMission";
RANGE static const char methodName281[37] PROGMEM = "javax.safetycritical.Mission.cleanUp";
RANGE static const char methodName282[47] PROGMEM = "javax.safetycritical.Mission.getCurrentMission";
RANGE static const char methodName283[43] PROGMEM = "javax.safetycritical.Mission.getScjProcess";
RANGE static const char methodName284[42] PROGMEM = "javax.safetycritical.Mission.getSequencer";
RANGE static const char methodName285[11] PROGMEM = "initialize";
RANGE static const char methodName286[40] PROGMEM = "javax.safetycritical.Mission.runCleanup";
RANGE static const char methodName287[40] PROGMEM = "javax.safetycritical.Mission.runExecute";
RANGE static const char methodName288[43] PROGMEM = "javax.safetycritical.Mission.runInitialize";
RANGE static const char methodName289[43] PROGMEM = "javax.safetycritical.Mission.setMissionSeq";
RANGE static const char methodName290[48] PROGMEM = "javax.safetycritical.Mission.terminationPending";
RANGE static const char methodName291[44] PROGMEM = "javax.safetycritical.MissionMemory$1.<init>";
RANGE static const char methodName292[41] PROGMEM = "javax.safetycritical.MissionMemory$1.run";
RANGE static const char methodName293[44] PROGMEM = "javax.safetycritical.MissionMemory$2.<init>";
RANGE static const char methodName294[41] PROGMEM = "javax.safetycritical.MissionMemory$2.run";
RANGE static const char methodName295[44] PROGMEM = "javax.safetycritical.MissionMemory$3.<init>";
RANGE static const char methodName296[41] PROGMEM = "javax.safetycritical.MissionMemory$3.run";
RANGE static const char methodName297[42] PROGMEM = "javax.safetycritical.MissionMemory.<init>";
RANGE static const char methodName298[50] PROGMEM = "javax.safetycritical.MissionMemory.enterToCleanup";
RANGE static const char methodName299[50] PROGMEM = "javax.safetycritical.MissionMemory.enterToExecute";
RANGE static const char methodName300[53] PROGMEM = "javax.safetycritical.MissionMemory.enterToInitialize";
RANGE static const char methodName301[47] PROGMEM = "javax.safetycritical.MissionSequencer.<clinit>";
RANGE static const char methodName302[45] PROGMEM = "javax.safetycritical.MissionSequencer.<init>";
RANGE static const char methodName303[45] PROGMEM = "javax.safetycritical.MissionSequencer.<init>";
RANGE static const char methodName304[46] PROGMEM = "javax.safetycritical.MissionSequencer.cleanUp";
RANGE static const char methodName305[15] PROGMEM = "getNextMission";
RANGE static const char methodName306[55] PROGMEM = "javax.safetycritical.MissionSequencer.handleAsyncEvent";
RANGE static const char methodName307[48] PROGMEM = "javax.safetycritical.MissionSequencer.seqNotify";
RANGE static const char methodName308[46] PROGMEM = "javax.safetycritical.MissionSequencer.seqWait";
RANGE static const char methodName309[36] PROGMEM = "javax.safetycritical.Monitor.<init>";
RANGE static const char methodName310[40] PROGMEM = "javax.safetycritical.Monitor.getMonitor";
RANGE static const char methodName311[34] PROGMEM = "javax.safetycritical.Monitor.lock";
RANGE static const char methodName312[33] PROGMEM = "javax.safetycritical.Monitor.max";
RANGE static const char methodName313[38] PROGMEM = "javax.safetycritical.Monitor.setOwner";
RANGE static const char methodName314[36] PROGMEM = "javax.safetycritical.Monitor.unlock";
RANGE static const char methodName315[49] PROGMEM = "javax.safetycritical.PeriodicEventHandler.<init>";
RANGE static const char methodName316[42] PROGMEM = "javax.safetycritical.PriorityFrame.<init>";
RANGE static const char methodName317[46] PROGMEM = "javax.safetycritical.PriorityFrame.addProcess";
RANGE static const char methodName318[51] PROGMEM = "javax.safetycritical.PriorityFrame.removeFromQueue";
RANGE static const char methodName319[42] PROGMEM = "javax.safetycritical.PriorityQueue.<init>";
RANGE static const char methodName320[44] PROGMEM = "javax.safetycritical.PriorityQueue.exchange";
RANGE static const char methodName321[46] PROGMEM = "javax.safetycritical.PriorityQueue.extractMax";
RANGE static const char methodName322[40] PROGMEM = "javax.safetycritical.PriorityQueue.find";
RANGE static const char methodName323[49] PROGMEM = "javax.safetycritical.PriorityQueue.getScjProcess";
RANGE static const char methodName324[43] PROGMEM = "javax.safetycritical.PriorityQueue.heapify";
RANGE static const char methodName325[42] PROGMEM = "javax.safetycritical.PriorityQueue.insert";
RANGE static const char methodName326[40] PROGMEM = "javax.safetycritical.PriorityQueue.left";
RANGE static const char methodName327[49] PROGMEM = "javax.safetycritical.PriorityQueue.makeEmptyTree";
RANGE static const char methodName328[42] PROGMEM = "javax.safetycritical.PriorityQueue.parent";
RANGE static const char methodName329[42] PROGMEM = "javax.safetycritical.PriorityQueue.remove";
RANGE static const char methodName330[41] PROGMEM = "javax.safetycritical.PriorityQueue.right";
RANGE static const char methodName331[56] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.<init>";
RANGE static const char methodName332[60] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.addProcess";
RANGE static const char methodName333[64] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.getNextProcess";
RANGE static const char methodName334[63] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.getScjProcess";
RANGE static const char methodName335[64] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.makeEmptyQueue";
RANGE static const char methodName336[63] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.removeProcess";
RANGE static const char methodName337[60] PROGMEM = "javax.safetycritical.PriorityQueueForLockAndWait.reorderSet";
RANGE static const char methodName338[46] PROGMEM = "javax.safetycritical.PriorityScheduler.<init>";
RANGE static const char methodName339[55] PROGMEM = "javax.safetycritical.PriorityScheduler.addOuterMostSeq";
RANGE static const char methodName340[61] PROGMEM = "javax.safetycritical.PriorityScheduler.addProcessToLockQueue";
RANGE static const char methodName341[57] PROGMEM = "javax.safetycritical.PriorityScheduler.getCurrentProcess";
RANGE static const char methodName342[63] PROGMEM = "javax.safetycritical.PriorityScheduler.getProcessFromLockQueue";
RANGE static const char methodName343[56] PROGMEM = "javax.safetycritical.PriorityScheduler.insertReadyQueue";
RANGE static const char methodName344[48] PROGMEM = "javax.safetycritical.PriorityScheduler.instance";
RANGE static const char methodName345[44] PROGMEM = "javax.safetycritical.PriorityScheduler.move";
RANGE static const char methodName346[50] PROGMEM = "javax.safetycritical.PriorityScheduler.moveToNext";
RANGE static const char methodName347[52] PROGMEM = "javax.safetycritical.PriorityScheduler.processStart";
RANGE static const char methodName348[45] PROGMEM = "javax.safetycritical.PriorityScheduler.start";
RANGE static const char methodName349[44] PROGMEM = "javax.safetycritical.PriorityScheduler.stop";
RANGE static const char methodName350[50] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.<init>";
RANGE static const char methodName351[61] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.getDefaultMonitor";
RANGE static const char methodName352[61] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.getDefaultMonitor";
RANGE static const char methodName353[58] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.getNextProcess";
RANGE static const char methodName354[50] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.notify";
RANGE static const char methodName355[53] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.notifyAll";
RANGE static const char methodName356[48] PROGMEM = "javax.safetycritical.PrioritySchedulerImpl.wait";
RANGE static const char methodName357[42] PROGMEM = "javax.safetycritical.PrivateMemory.<init>";
RANGE static const char methodName358[60] PROGMEM = "javax.safetycritical.ScjAperiodicEventHandlerProcess.<init>";
RANGE static const char methodName359[67] PROGMEM = "javax.safetycritical.ScjAperiodicEventHandlerProcess.gotoNextState";
RANGE static const char methodName360[52] PROGMEM = "javax.safetycritical.ScjManagedThreadProcess.<init>";
RANGE static const char methodName361[59] PROGMEM = "javax.safetycritical.ScjManagedThreadProcess.gotoNextState";
RANGE static const char methodName362[58] PROGMEM = "javax.safetycritical.ScjManagedThreadProcess.switchToArea";
RANGE static const char methodName363[55] PROGMEM = "javax.safetycritical.ScjMissionSequencerProcess.<init>";
RANGE static const char methodName364[62] PROGMEM = "javax.safetycritical.ScjMissionSequencerProcess.gotoNextState";
RANGE static const char methodName365[58] PROGMEM = "javax.safetycritical.ScjOneShotEventHandlerProcess.<init>";
RANGE static const char methodName366[65] PROGMEM = "javax.safetycritical.ScjOneShotEventHandlerProcess.gotoNextState";
RANGE static const char methodName367[59] PROGMEM = "javax.safetycritical.ScjPeriodicEventHandlerProcess.<init>";
RANGE static const char methodName368[66] PROGMEM = "javax.safetycritical.ScjPeriodicEventHandlerProcess.gotoNextState";
RANGE static const char methodName369[41] PROGMEM = "javax.safetycritical.ScjProcess$1.<init>";
RANGE static const char methodName370[45] PROGMEM = "javax.safetycritical.ScjProcess$1.catchError";
RANGE static const char methodName371[38] PROGMEM = "javax.safetycritical.ScjProcess$1.run";
RANGE static const char methodName372[41] PROGMEM = "javax.safetycritical.ScjProcess$2.<init>";
RANGE static const char methodName373[51] PROGMEM = "javax.safetycritical.ScjProcess$2.handleAsyncEvent";
RANGE static const char methodName374[38] PROGMEM = "javax.safetycritical.ScjProcess$2.run";
RANGE static const char methodName375[40] PROGMEM = "javax.safetycritical.ScjProcess$2.yield";
RANGE static const char methodName376[57] PROGMEM = "javax.safetycritical.ScjProcess$ExceptionReporter.<init>";
RANGE static const char methodName377[57] PROGMEM = "javax.safetycritical.ScjProcess$ExceptionReporter.<init>";
RANGE static const char methodName378[54] PROGMEM = "javax.safetycritical.ScjProcess$ExceptionReporter.run";
RANGE static const char methodName379[39] PROGMEM = "javax.safetycritical.ScjProcess.<init>";
RANGE static const char methodName380[41] PROGMEM = "javax.safetycritical.ScjProcess.access$1";
RANGE static const char methodName381[41] PROGMEM = "javax.safetycritical.ScjProcess.access$2";
RANGE static const char methodName382[42] PROGMEM = "javax.safetycritical.ScjProcess.compareTo";
RANGE static const char methodName383[50] PROGMEM = "javax.safetycritical.ScjProcess.createIdleProcess";
RANGE static const char methodName384[42] PROGMEM = "javax.safetycritical.ScjProcess.getTarget";
RANGE static const char methodName385[46] PROGMEM = "javax.safetycritical.ScjProcess.gotoNextState";
RANGE static const char methodName386[41] PROGMEM = "javax.safetycritical.ScjProcess.runLogic";
RANGE static const char methodName387[41] PROGMEM = "javax.safetycritical.ScjProcess.setIndex";
RANGE static const char methodName388[43] PROGMEM = "javax.safetycritical.ScjProcess.setProcess";
RANGE static const char methodName389[43] PROGMEM = "javax.safetycritical.ScjProcess.setRelease";
RANGE static const char methodName390[38] PROGMEM = "javax.safetycritical.ScjProcess.start";
RANGE static const char methodName391[45] PROGMEM = "javax.safetycritical.ScjProcess.switchToArea";
RANGE static const char methodName392[41] PROGMEM = "javax.safetycritical.ScjProcess.toString";
RANGE static const char methodName393[41] PROGMEM = "javax.safetycritical.Services.setCeiling";
RANGE static const char methodName394[42] PROGMEM = "javax.safetycritical.SleepingQueue.<init>";
RANGE static const char methodName395[44] PROGMEM = "javax.safetycritical.SleepingQueue.exchange";
RANGE static const char methodName396[46] PROGMEM = "javax.safetycritical.SleepingQueue.extractMin";
RANGE static const char methodName397[40] PROGMEM = "javax.safetycritical.SleepingQueue.find";
RANGE static const char methodName398[49] PROGMEM = "javax.safetycritical.SleepingQueue.getScjProcess";
RANGE static const char methodName399[43] PROGMEM = "javax.safetycritical.SleepingQueue.heapify";
RANGE static const char methodName400[42] PROGMEM = "javax.safetycritical.SleepingQueue.insert";
RANGE static const char methodName401[40] PROGMEM = "javax.safetycritical.SleepingQueue.left";
RANGE static const char methodName402[49] PROGMEM = "javax.safetycritical.SleepingQueue.makeEmptyTree";
RANGE static const char methodName403[43] PROGMEM = "javax.safetycritical.SleepingQueue.minimum";
RANGE static const char methodName404[42] PROGMEM = "javax.safetycritical.SleepingQueue.parent";
RANGE static const char methodName405[42] PROGMEM = "javax.safetycritical.SleepingQueue.remove";
RANGE static const char methodName406[41] PROGMEM = "javax.safetycritical.SleepingQueue.right";
RANGE static const char methodName407[46] PROGMEM = "javax.safetycritical.StorageParameters.<init>";
RANGE static const char methodName408[45] PROGMEM = "javax.safetycritical.annotate.Phase.<clinit>";
RANGE static const char methodName409[43] PROGMEM = "javax.safetycritical.annotate.Phase.<init>";
RANGE static const char methodName410[30] PROGMEM = "javax.scj.util.Const.<clinit>";
RANGE static const char methodName411[45] PROGMEM = "javax.scj.util.Const.setDefaultErrorReporter";
RANGE static const char methodName412[46] PROGMEM = "javax.scj.util.DefaultSCJErrorReporter.<init>";
RANGE static const char methodName413[61] PROGMEM = "javax.scj.util.DefaultSCJErrorReporter.processExecutionError";
RANGE static const char methodName414[63] PROGMEM = "javax.scj.util.DefaultSCJErrorReporter.processOutOfMemoryError";
RANGE static const char methodName415[54] PROGMEM = "javax.scj.util.DefaultSCJErrorReporter.schedulerError";
RANGE static const char methodName416[45] PROGMEM = "javax.scj.util.SilentSCJErrorReporter.<init>";
RANGE static const char methodName417[60] PROGMEM = "javax.scj.util.SilentSCJErrorReporter.processExecutionError";
RANGE static const char methodName418[62] PROGMEM = "javax.scj.util.SilentSCJErrorReporter.processOutOfMemoryError";
RANGE static const char methodName419[53] PROGMEM = "javax.scj.util.SilentSCJErrorReporter.schedulerError";
RANGE static const char methodName420[45] PROGMEM = "test.TestSCJThreadMemory$BiggerObject.<init>";
RANGE static const char methodName421[45] PROGMEM = "test.TestSCJThreadMemory$BiggerObject.<init>";
RANGE static const char methodName422[38] PROGMEM = "test.TestSCJThreadMemory$MyApp.<init>";
RANGE static const char methodName423[38] PROGMEM = "test.TestSCJThreadMemory$MyApp.<init>";
RANGE static const char methodName424[44] PROGMEM = "test.TestSCJThreadMemory$MyApp.getSequencer";
RANGE static const char methodName425[42] PROGMEM = "test.TestSCJThreadMemory$MyMission.<init>";
RANGE static const char methodName426[42] PROGMEM = "test.TestSCJThreadMemory$MyMission.<init>";
RANGE static const char methodName427[46] PROGMEM = "test.TestSCJThreadMemory$MyMission.initialize";
RANGE static const char methodName428[44] PROGMEM = "test.TestSCJThreadMemory$MySequencer.<init>";
RANGE static const char methodName429[52] PROGMEM = "test.TestSCJThreadMemory$MySequencer.getNextMission";
RANGE static const char methodName430[52] PROGMEM = "test.TestSCJThreadMemory$MySequencer.getNextMission";
RANGE static const char methodName431[41] PROGMEM = "test.TestSCJThreadMemory$MySequencer.run";
RANGE static const char methodName432[44] PROGMEM = "test.TestSCJThreadMemory$SmallObject.<init>";
RANGE static const char methodName433[44] PROGMEM = "test.TestSCJThreadMemory$SmallObject.<init>";
RANGE static const char methodName434[40] PROGMEM = "test.TestSCJThreadMemory$Thread1.<init>";
RANGE static const char methodName435[39] PROGMEM = "test.TestSCJThreadMemory$Thread1.delay";
RANGE static const char methodName436[40] PROGMEM = "test.TestSCJThreadMemory$Thread1.doWork";
RANGE static const char methodName437[37] PROGMEM = "test.TestSCJThreadMemory$Thread1.run";
RANGE static const char methodName438[40] PROGMEM = "test.TestSCJThreadMemory$Thread2.<init>";
RANGE static const char methodName439[39] PROGMEM = "test.TestSCJThreadMemory$Thread2.delay";
RANGE static const char methodName440[40] PROGMEM = "test.TestSCJThreadMemory$Thread2.doWork";
RANGE static const char methodName441[37] PROGMEM = "test.TestSCJThreadMemory$Thread2.run";
RANGE static const char methodName442[34] PROGMEM = "test.TestSCJThreadMemory.access$0";
RANGE static const char methodName443[34] PROGMEM = "test.TestSCJThreadMemory.access$1";
RANGE static const char methodName444[30] PROGMEM = "test.TestSCJThreadMemory.main";
RANGE static const char methodName445[23] PROGMEM = "vm.Address32Bit.<init>";
RANGE static const char methodName446[18] PROGMEM = "vm.Address.<init>";
RANGE static const char methodName447[34] PROGMEM = "vm.ClockInterruptHandler.<clinit>";
RANGE static const char methodName448[32] PROGMEM = "vm.ClockInterruptHandler.<init>";
RANGE static const char methodName449[36] PROGMEM = "vm.ClockInterruptHandler.catchError";
RANGE static const char methodName450[33] PROGMEM = "vm.ClockInterruptHandler.disable";
RANGE static const char methodName451[32] PROGMEM = "vm.ClockInterruptHandler.enable";
RANGE static const char methodName452[32] PROGMEM = "vm.ClockInterruptHandler.handle";
RANGE static const char methodName453[36] PROGMEM = "vm.ClockInterruptHandler.initialize";
RANGE static const char methodName454[34] PROGMEM = "vm.ClockInterruptHandler.register";
RANGE static const char methodName455[29] PROGMEM = "vm.ClockInterruptHandler.run";
RANGE static const char methodName456[38] PROGMEM = "vm.ClockInterruptHandler.setScheduler";
RANGE static const char methodName457[43] PROGMEM = "vm.ClockInterruptHandler.startClockHandler";
RANGE static const char methodName458[31] PROGMEM = "vm.ClockInterruptHandler.yield";
RANGE static const char methodName459[25] PROGMEM = "vm.HardwareObject.<init>";
RANGE static const char methodName460[42] PROGMEM = "vm.InterruptDispatcher$NullHandler.<init>";
RANGE static const char methodName461[42] PROGMEM = "vm.InterruptDispatcher$NullHandler.<init>";
RANGE static const char methodName462[43] PROGMEM = "vm.InterruptDispatcher$NullHandler.disable";
RANGE static const char methodName463[42] PROGMEM = "vm.InterruptDispatcher$NullHandler.enable";
RANGE static const char methodName464[42] PROGMEM = "vm.InterruptDispatcher$NullHandler.handle";
RANGE static const char methodName465[32] PROGMEM = "vm.InterruptDispatcher.<clinit>";
RANGE static const char methodName466[28] PROGMEM = "vm.InterruptDispatcher.init";
RANGE static const char methodName467[33] PROGMEM = "vm.InterruptDispatcher.interrupt";
RANGE static const char methodName468[39] PROGMEM = "vm.InterruptDispatcher.registerHandler";
RANGE static const char methodName469[31] PROGMEM = "vm.Machine.getCurrentScheduler";
RANGE static const char methodName470[31] PROGMEM = "vm.Machine.setCurrentScheduler";
RANGE static const char methodName471[28] PROGMEM = "vm.Memory$MemoryInfo.<init>";
RANGE static const char methodName472[43] PROGMEM = "vm.Memory$MemoryInfo.increaseInstanceCount";
RANGE static const char methodName473[30] PROGMEM = "vm.Memory$MemoryInfo.toString";
RANGE static const char methodName474[17] PROGMEM = "vm.Memory.<init>";
RANGE static const char methodName475[17] PROGMEM = "vm.Memory.<init>";
RANGE static const char methodName476[19] PROGMEM = "vm.Memory.access$0";
RANGE static const char methodName477[19] PROGMEM = "vm.Memory.access$1";
RANGE static const char methodName478[24] PROGMEM = "vm.Memory.addMemoryArea";
RANGE static const char methodName479[25] PROGMEM = "vm.Memory.allocateInHeap";
RANGE static const char methodName480[25] PROGMEM = "vm.Memory.consumedMemory";
RANGE static const char methodName481[24] PROGMEM = "vm.Memory.executeInHeap";
RANGE static const char methodName482[18] PROGMEM = "vm.Memory.getBase";
RANGE static const char methodName483[31] PROGMEM = "vm.Memory.getCurrentMemoryArea";
RANGE static const char methodName484[22] PROGMEM = "vm.Memory.getHeapArea";
RANGE static const char methodName485[18] PROGMEM = "vm.Memory.getName";
RANGE static const char methodName486[28] PROGMEM = "vm.Memory.getNextMemoryName";
RANGE static const char methodName487[18] PROGMEM = "vm.Memory.getSize";
RANGE static const char methodName488[28] PROGMEM = "vm.Memory.reportMemoryUsage";
RANGE static const char methodName489[16] PROGMEM = "vm.Memory.reset";
RANGE static const char methodName490[17] PROGMEM = "vm.Memory.resize";
RANGE static const char methodName491[34] PROGMEM = "vm.Memory.startMemoryAreaTracking";
RANGE static const char methodName492[23] PROGMEM = "vm.Memory.switchToArea";
RANGE static const char methodName493[19] PROGMEM = "vm.Memory.toString";
RANGE static const char methodName494[24] PROGMEM = "vm.Memory.updateMaxUsed";
RANGE static const char methodName495[18] PROGMEM = "vm.Monitor.<init>";
RANGE static const char methodName496[18] PROGMEM = "vm.Monitor.attach";
RANGE static const char methodName497[14] PROGMEM = "attachMonitor";
RANGE static const char methodName498[19] PROGMEM = "getAttachedMonitor";
RANGE static const char methodName499[29] PROGMEM = "vm.Monitor.getDefaultMonitor";
RANGE static const char methodName500[5] PROGMEM = "lock";
RANGE static const char methodName501[16] PROGMEM = "vm.Monitor.lock";
RANGE static const char methodName502[18] PROGMEM = "vm.Monitor.notify";
RANGE static const char methodName503[21] PROGMEM = "vm.Monitor.notifyAll";
RANGE static const char methodName504[7] PROGMEM = "unlock";
RANGE static const char methodName505[18] PROGMEM = "vm.Monitor.unlock";
RANGE static const char methodName506[16] PROGMEM = "vm.Monitor.wait";
RANGE static const char methodName507[34] PROGMEM = "vm.Process$ProcessExecutor.<init>";
RANGE static const char methodName508[31] PROGMEM = "vm.Process$ProcessExecutor.run";
RANGE static const char methodName509[21] PROGMEM = "vm.Process$SP.<init>";
RANGE static const char methodName510[21] PROGMEM = "vm.Process$SP.<init>";
RANGE static const char methodName511[27] PROGMEM = "vm.Process$X86_32SP.<init>";
RANGE static const char methodName512[27] PROGMEM = "vm.Process$X86_32SP.<init>";
RANGE static const char methodName513[27] PROGMEM = "vm.Process$X86_64SP.<init>";
RANGE static const char methodName514[27] PROGMEM = "vm.Process$X86_64SP.<init>";
RANGE static const char methodName515[18] PROGMEM = "vm.Process.<init>";
RANGE static const char methodName516[20] PROGMEM = "vm.Process.access$0";
RANGE static const char methodName517[20] PROGMEM = "vm.Process.access$1";
RANGE static const char methodName518[17] PROGMEM = "executeWithStack";
RANGE static const char methodName519[22] PROGMEM = "vm.Process.initialize";
RANGE static const char methodName520[9] PROGMEM = "transfer";
RANGE static const char methodName521[22] PROGMEM = "vm.Process.transferTo";
RANGE static const char methodName522[45] PROGMEM = "vm.RealtimeClock$DefaultRealtimeClock.<init>";
RANGE static const char methodName523[45] PROGMEM = "vm.RealtimeClock$DefaultRealtimeClock.<init>";
RANGE static const char methodName524[53] PROGMEM = "vm.RealtimeClock$DefaultRealtimeClock.getCurrentTime";
RANGE static const char methodName525[53] PROGMEM = "vm.RealtimeClock$DefaultRealtimeClock.getGranularity";
RANGE static const char methodName526[24] PROGMEM = "vm.RealtimeClock.<init>";
RANGE static const char methodName527[26] PROGMEM = "vm.RealtimeClock.access$0";
RANGE static const char methodName528[26] PROGMEM = "vm.RealtimeClock.access$1";
RANGE static const char methodName529[14] PROGMEM = "awaitNextTick";
RANGE static const char methodName530[15] PROGMEM = "getCurrentTime";
RANGE static const char methodName531[15] PROGMEM = "getGranularity";
RANGE static const char methodName532[20] PROGMEM = "getNativeResolution";
RANGE static const char methodName533[14] PROGMEM = "getNativeTime";
RANGE static const char methodName534[34] PROGMEM = "vm.RealtimeClock.getRealtimeClock";


static RANGE const MethodInfo _methods[535] PROGMEM = {
  { 12, 1, 1, 45, 0, 0, 32, 0, devices_CR16C_KT4585_CR16CInterruptDispatcher_init, 0, methodName0 },
  { 226, 4, 1, 142, 0, 0, 64, 0, devices_CR16C_KT4585_CR16CRealtimeClock_init_, 0, methodName1 },
  { 226, 0, 1, 3, 0, 0, 32, 0, devices_CR16C_KT4585_CR16CRealtimeClock_disable, 0, methodName2 },
  { 226, 0, 1, 3, 0, 0, 32, 0, devices_CR16C_KT4585_CR16CRealtimeClock_enable, 0, methodName3 },
  { 226, 3, 2, 19, 0, 1, 32, 0, devices_CR16C_KT4585_CR16CRealtimeClock_getCurrentTime, 0, methodName4 },
  { 226, 1, 1, 6, 0, 0, 17, 0, devices_CR16C_KT4585_CR16CRealtimeClock_getGranularity, 0, methodName5 },
  { 226, 3, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))devices_CR16C_KT4585_CR16CRealtimeClock_handle, methodName6 },
  { 42, 4, 2, 17, 0, 1, 64, 0, devices_CR16C_KT4585_DeviceRegShort_init_, 0, methodName7 },
  { 34, 2, 1, 28, 0, 0, 32, 0, devices_Console_clinit_, 0, methodName8 },
  { 34, 4, 4, 64, 0, 2, 33, 0, devices_Console_getBytes, 0, methodName9 },
  { 34, 2, 1, 9, 0, 1, 32, 0, devices_Console_println, 0, methodName10 },
  { 34, 3, 3, 49, 0, 2, 32, 0, devices_Console_println_, 0, methodName11 },
  { 128, 1, 1, 8, 0, 0, 64, 0, devices_X86Writer_init_, 0, methodName12 },
  { 128, 0, 0, 0, 0, 2, 32, 0, 0, n_devices_X86Writer_nwrite, methodName13 },
  { 128, 2, 3, 9, 0, 2, 32, 0, devices_X86Writer_write, 0, methodName14 },
  { 180, 1, 1, 44, 0, 0, 32, 0, devices_i86_I86InterruptDispatcher_init, 0, methodName15 },
  { 254, 2, 2, 19, 0, 1, 64, 0, java_lang_AbstractStringBuilder_init_, 0, methodName16 },
  { 254, 5, 2, 44, 0, 1, 33, 0, java_lang_AbstractStringBuilder_append, 0, methodName17 },
  { 254, 3, 4, 91, 0, 1, 33, 0, java_lang_AbstractStringBuilder_append_, 0, methodName18 },
  { 254, 4, 5, 98, 0, 2, 33, 0, java_lang_AbstractStringBuilder_append__, 0, methodName19 },
  { 254, 5, 3, 73, 0, 1, 33, 0, java_lang_AbstractStringBuilder_append___, 0, methodName20 },
  { 254, 3, 3, 68, 0, 0, 33, 0, java_lang_AbstractStringBuilder_appendNull, 0, methodName21 },
  { 254, 2, 2, 22, 0, 1, 32, 0, java_lang_AbstractStringBuilder_ensureCapacityInternal, 0, methodName22 },
  { 254, 3, 3, 64, 0, 1, 32, 0, java_lang_AbstractStringBuilder_expandCapacity, 0, methodName23 },
  { 28, 1, 1, 8, 0, 0, 64, 0, java_lang_ArithmeticException_init_, 0, methodName24 },
  { 142, 1, 1, 8, 0, 0, 64, 0, java_lang_ArrayIndexOutOfBoundsException_init_, 0, methodName25 },
  { 234, 1, 1, 8, 0, 0, 64, 0, java_lang_ArrayStoreException_init_, 0, methodName26 },
  { 76, 1, 1, 8, 0, 0, 64, 0, java_lang_AssertionError_init_, 0, methodName27 },
  { 290, 0, 0, 0, 0, 0, 32, 0, 0, n_java_lang_Class_clinit_, methodName28 },
  { 290, 0, 0, 0, 0, 0, 1, 0, 0, n_java_lang_Class_desiredAssertionStatus, methodName29 },
  { 290, 0, 0, 0, 0, 0, 33, 0, 0, n_java_lang_Class_getComponentType, methodName30 },
  { 290, 0, 0, 0, 0, 0, 33, 0, 0, n_java_lang_Class_getName0, methodName31 },
  { 290, 3, 2, 30, 0, 0, 33, 0, java_lang_Class_getName, 0, methodName32 },
  { 290, 0, 0, 0, 0, 1, 33, 0, 0, n_java_lang_Class_getPrimitiveClass, methodName33 },
  { 290, 0, 0, 0, 0, 0, 33, 0, 0, n_java_lang_Class_toString, methodName34 },
  { 78, 1, 1, 8, 0, 0, 64, 0, java_lang_ClassCastException_init_, 0, methodName35 },
  { 78, 2, 2, 9, 0, 1, 64, 0, java_lang_ClassCastException_init__, 0, methodName36 },
  { 66, 1, 1, 15, 0, 0, 32, 0, java_lang_Double_clinit_, 0, methodName37 },
  { 66, 0, 0, 0, 0, 2, 22, 0, 0, n_java_lang_Double_doubleToRawLongBits, methodName38 },
  { 66, 0, 0, 0, 0, 2, 26, 0, 0, n_java_lang_Double_longBitsToDouble, methodName39 },
  { 192, 2, 3, 24, 0, 2, 64, 0, java_lang_Enum_init_, 0, methodName40 },
  { 192, 1, 1, 8, 0, 0, 17, 0, java_lang_Enum_hashCode, 0, methodName41 },
  { 192, 1, 1, 10, 0, 0, 33, 0, java_lang_Enum_toString, 0, methodName42 },
  { 72, 1, 1, 8, 0, 0, 64, 0, java_lang_Error_init_, 0, methodName43 },
  { 72, 2, 2, 9, 0, 1, 64, 0, java_lang_Error_init__, 0, methodName44 },
  { 48, 1, 1, 8, 0, 0, 64, 0, java_lang_Exception_init_, 0, methodName45 },
  { 48, 2, 2, 9, 0, 1, 64, 0, java_lang_Exception_init__, 0, methodName46 },
  { 0, 1, 1, 15, 0, 0, 32, 0, java_lang_Float_clinit_, 0, methodName47 },
  { 0, 0, 0, 0, 0, 1, 17, 0, 0, n_java_lang_Float_floatToRawIntBits, methodName48 },
  { 284, 1, 1, 8, 0, 0, 64, 0, java_lang_IllegalArgumentException_init_, 0, methodName49 },
  { 284, 2, 2, 9, 0, 1, 64, 0, java_lang_IllegalArgumentException_init__, 0, methodName50 },
  { 96, 1, 1, 8, 0, 0, 64, 0, java_lang_IllegalMonitorStateException_init_, 0, methodName51 },
  { 96, 2, 2, 9, 0, 1, 64, 0, java_lang_IllegalMonitorStateException_init__, 0, methodName52 },
  { 2, 1, 1, 8, 0, 0, 64, 0, java_lang_IndexOutOfBoundsException_init_, 0, methodName53 },
  { 2, 2, 2, 9, 0, 1, 64, 0, java_lang_IndexOutOfBoundsException_init__, 0, methodName54 },
  { 20, 4, 1, 1519, 0, 0, 32, 0, java_lang_Integer_clinit_, 0, methodName55 },
  { 20, 5, 8, 54, 0, 5, 17, 0, java_lang_Integer_formatUnsignedInt, 0, methodName56 },
  { 20, 4, 7, 144, 0, 3, 32, 0, java_lang_Integer_getChars, 0, methodName57 },
  { 20, 3, 2, 80, 0, 1, 17, 0, java_lang_Integer_numberOfLeadingZeros, 0, methodName58 },
  { 20, 3, 2, 26, 0, 1, 17, 0, java_lang_Integer_stringSize, 0, methodName59 },
  { 20, 2, 1, 9, 0, 1, 33, 0, java_lang_Integer_toHexString, 0, methodName60 },
  { 20, 5, 5, 53, 0, 2, 33, 0, java_lang_Integer_toUnsignedString0, 0, methodName61 },
  { 22, 1, 1, 15, 0, 0, 32, 0, java_lang_Long_clinit_, 0, methodName62 },
  { 22, 7, 11, 241, 0, 4, 32, 0, java_lang_Long_getChars, 0, methodName63 },
  { 22, 4, 5, 42, 0, 2, 17, 0, java_lang_Long_stringSize, 0, methodName64 },
  { 194, 2, 1, 76, 0, 0, 32, 0, java_lang_Math_clinit_, 0, methodName65 },
  { 194, 1, 1, 8, 0, 0, 64, 0, java_lang_Math_init_, 0, methodName66 },
  { 194, 2, 2, 13, 0, 2, 17, 0, java_lang_Math_max, 0, methodName67 },
  { 194, 2, 2, 13, 0, 2, 17, 0, java_lang_Math_min, 0, methodName68 },
  { 194, 4, 1, 52, 0, 1, 26, 0, java_lang_Math_powerOfTwoD, 0, methodName69 },
  { 124, 1, 1, 8, 0, 0, 64, 0, java_lang_NegativeArraySizeException_init_, 0, methodName70 },
  { 182, 1, 1, 8, 0, 0, 64, 0, java_lang_NullPointerException_init_, 0, methodName71 },
  { 288, 0, 1, 3, 0, 0, 64, 0, java_lang_Object_init_, 0, methodName72 },
  { 288, 0, 0, 0, 0, 0, 33, 0, 0, n_java_lang_Object_getClass, methodName73 },
  { 288, 0, 0, 0, 0, 0, 17, 0, 0, n_java_lang_Object_hashCode, methodName74 },
  { 288, 0, 0, 0, 0, 0, 32, 0, 0, n_java_lang_Object_notify, methodName75 },
  { 288, 2, 1, 69, 0, 0, 33, 0, java_lang_Object_toString, 0, methodName76 },
  { 288, 3, 1, 9, 0, 0, 32, 0, java_lang_Object_wait, 0, methodName77 },
  { 288, 0, 0, 0, 0, 2, 32, 0, 0, n_java_lang_Object_wait_, methodName78 },
  { 162, 1, 1, 8, 0, 0, 64, 0, java_lang_OutOfMemoryError_init_, 0, methodName79 },
  { 162, 2, 2, 9, 0, 1, 64, 0, java_lang_OutOfMemoryError_init__, 0, methodName80 },
  { 140, 1, 1, 8, 0, 0, 64, 0, java_lang_RuntimeException_init_, 0, methodName81 },
  { 140, 2, 2, 9, 0, 1, 64, 0, java_lang_RuntimeException_init__, 0, methodName82 },
  { 154, 1, 1, 8, 0, 0, 64, 0, java_lang_String_CaseInsensitiveComparator_init_, 0, methodName83 },
  { 154, 1, 2, 8, 0, 1, 64, 0, java_lang_String_CaseInsensitiveComparator_init__, 0, methodName84 },
  { 114, 3, 1, 27, 0, 0, 32, 0, java_lang_String_clinit_, 0, methodName85 },
  { 114, 3, 2, 22, 0, 1, 64, 0, java_lang_String_initFromCharArray, 0, methodName86 },
  { 114, 5, 4, 72, 0, 3, 64, 0, java_lang_String_init__, 0, methodName87 },
  { 114, 2, 3, 16, 0, 2, 64, 0, java_lang_String_init___, 0, methodName88 },
  { 114, 3, 2, 38, 0, 1, 13, 0, java_lang_String_charAt, 0, methodName89 },
  { 114, 2, 10, 97, 0, 1, 17, 0, java_lang_String_compareTo, 0, methodName90 },
  { 114, 3, 7, 101, 0, 1, 1, 0, java_lang_String_equals, 0, methodName91 },
  { 114, 6, 5, 74, 0, 4, 32, 0, java_lang_String_getChars, 0, methodName92 },
  { 114, 3, 4, 72, 0, 0, 17, 0, java_lang_String_hashCode, 0, methodName93 },
  { 114, 1, 1, 11, 0, 0, 17, 0, java_lang_String_length, 0, methodName94 },
  { 114, 1, 1, 4, 0, 0, 33, 0, java_lang_String_toString, 0, methodName95 },
  { 114, 1, 1, 75, 0, 1, 33, 0, java_lang_String_valueOf, 0, methodName96 },
  { 108, 2, 1, 10, 0, 0, 64, 0, java_lang_StringBuffer_init_, 0, methodName97 },
  { 108, 2, 2, 19, 0, 1, 161, 0, java_lang_StringBuffer_append_, 0, methodName98 },
  { 108, 2, 2, 9, 0, 1, 33, 0, java_lang_StringBuffer_append__, 0, methodName99 },
  { 108, 2, 2, 19, 0, 1, 161, 0, java_lang_StringBuffer_append___, 0, methodName100 },
  { 108, 4, 1, 55, 0, 0, 161, 0, java_lang_StringBuffer_toString, 0, methodName101 },
  { 52, 2, 1, 10, 0, 0, 64, 0, java_lang_StringBuilder_init_, 0, methodName102 },
  { 52, 3, 2, 23, 0, 1, 64, 0, java_lang_StringBuilder_init__, 0, methodName103 },
  { 52, 2, 2, 11, 0, 1, 33, 0, java_lang_StringBuilder_append_, 0, methodName104 },
  { 52, 2, 2, 11, 0, 1, 33, 0, java_lang_StringBuilder_append__, 0, methodName105 },
  { 52, 3, 3, 11, 0, 2, 33, 0, java_lang_StringBuilder_append___, 0, methodName106 },
  { 52, 2, 2, 13, 0, 1, 33, 0, java_lang_StringBuilder_append____, 0, methodName107 },
  { 52, 2, 2, 9, 0, 1, 33, 0, java_lang_StringBuilder_append_____, 0, methodName108 },
  { 52, 2, 2, 11, 0, 1, 33, 0, java_lang_StringBuilder_append______, 0, methodName109 },
  { 52, 5, 1, 26, 0, 0, 33, 0, java_lang_StringBuilder_toString, 0, methodName110 },
  { 40, 3, 2, 32, 0, 1, 64, 0, java_lang_StringIndexOutOfBoundsException_init_, 0, methodName111 },
  { 10, 1, 1, 41, 0, 0, 32, 0, java_lang_System_clinit_, 0, methodName112 },
  { 10, 0, 0, 0, 0, 5, 32, 0, 0, n_java_lang_System_arraycopy, methodName113 },
  { 10, 0, 0, 0, 0, 0, 32, 0, 0, n_java_lang_System_registerNatives, methodName114 },
  { 58, 0, 0, 0, 0, 0, 32, 0, 0, n_java_lang_Throwable_clinit_, methodName115 },
  { 58, 2, 1, 48, 0, 0, 64, 0, java_lang_Throwable_init_, 0, methodName116 },
  { 58, 2, 2, 56, 0, 1, 64, 0, java_lang_Throwable_init__, 0, methodName117 },
  { 58, 2, 1, 44, 0, 0, 161, 0, java_lang_Throwable_fillInStackTrace, 0, methodName118 },
  { 58, 0, 0, 0, 0, 1, 33, 0, 0, n_java_lang_Throwable_fillInStackTrace_, methodName119 },
  { 58, 1, 1, 8, 0, 0, 33, 0, java_lang_Throwable_getLocalizedMessage, 0, methodName120 },
  { 58, 1, 1, 10, 0, 0, 33, 0, java_lang_Throwable_getMessage, 0, methodName121 },
  { 58, 0, 0, 0, 0, 0, 32, 0, 0, n_java_lang_Throwable_printStackTrace, methodName122 },
  { 58, 2, 3, 56, 0, 0, 33, 0, java_lang_Throwable_toString, 0, methodName123 },
  { 132, 1, 1, 8, 0, 0, 64, 0, java_lang_UnsupportedOperationException_init_, 0, methodName124 },
  { 102, 1, 1, 8, 0, 0, 64, 0, java_lang_VirtualMachineError_init_, 0, methodName125 },
  { 102, 2, 2, 9, 0, 1, 64, 0, java_lang_VirtualMachineError_init__, 0, methodName126 },
  { 170, 0, 0, 0, 0, 2, 33, 0, 0, n_java_lang_reflect_Array_newArray, methodName127 },
  { 170, 2, 2, 9, 0, 2, 33, 0, java_lang_reflect_Array_newInstance, 0, methodName128 },
  { 188, 1, 1, 8, 0, 0, 64, 0, java_util_AbstractCollection_init_, 0, methodName129 },
  { 188, 0, 0, 0, 0, 0, 33, 0, 0, 0, methodName130 },
  { 188, 3, 4, 118, 0, 0, 33, 0, java_util_AbstractCollection_toString, 0, methodName131 },
  { 174, 2, 1, 16, 0, 0, 64, 0, java_util_AbstractList_init_, 0, methodName132 },
  { 174, 2, 4, 81, 0, 0, 17, 0, java_util_AbstractList_hashCode, 0, methodName133 },
  { 174, 4, 1, 10, 0, 0, 33, 0, java_util_AbstractList_iterator, 0, methodName134 },
  { 262, 2, 2, 44, 0, 1, 64, 0, java_util_ArrayList_Itr_init_, 0, methodName135 },
  { 262, 2, 3, 9, 0, 2, 64, 0, java_util_ArrayList_Itr_init__, 0, methodName136 },
  { 262, 2, 1, 35, 0, 0, 32, 0, java_util_ArrayList_Itr_checkForComodification, 0, methodName137 },
  { 262, 2, 1, 29, 0, 0, 1, 0, java_util_ArrayList_Itr_hasNext, 0, methodName138 },
  { 262, 4, 3, 90, 0, 0, 33, 0, java_util_ArrayList_Itr_next, 0, methodName139 },
  { 90, 1, 1, 12, 0, 0, 32, 0, java_util_ArrayList_clinit_, 0, methodName140 },
  { 90, 2, 1, 21, 0, 0, 64, 0, java_util_ArrayList_init_, 0, methodName141 },
  { 90, 1, 1, 10, 0, 1, 17, 0, java_util_ArrayList_access_100, 0, methodName142 },
  { 90, 5, 2, 44, 0, 1, 1, 0, java_util_ArrayList_add, 0, methodName143 },
  { 90, 2, 2, 33, 0, 1, 32, 0, java_util_ArrayList_ensureCapacityInternal, 0, methodName144 },
  { 90, 3, 2, 38, 0, 1, 32, 0, java_util_ArrayList_ensureExplicitCapacity, 0, methodName145 },
  { 90, 3, 4, 59, 0, 1, 32, 0, java_util_ArrayList_grow, 0, methodName146 },
  { 90, 2, 1, 32, 0, 1, 17, 0, java_util_ArrayList_hugeCapacity, 0, methodName147 },
  { 90, 4, 1, 13, 0, 0, 33, 0, java_util_ArrayList_iterator, 0, methodName148 },
  { 90, 1, 1, 10, 0, 0, 17, 0, java_util_ArrayList_size, 0, methodName149 },
  { 4, 1, 1, 23, 0, 0, 32, 0, java_util_Arrays_clinit_, 0, methodName150 },
  { 4, 1, 1, 8, 0, 0, 64, 0, java_util_Arrays_init_, 0, methodName151 },
  { 4, 6, 3, 24, 0, 2, 33, 0, java_util_Arrays_copyOf, 0, methodName152 },
  { 4, 3, 2, 17, 0, 2, 33, 0, java_util_Arrays_copyOf_, 0, methodName153 },
  { 4, 6, 4, 53, 0, 3, 33, 0, java_util_Arrays_copyOf__, 0, methodName154 },
  { 4, 6, 5, 75, 0, 3, 33, 0, java_util_Arrays_copyOfRange, 0, methodName155 },
  { 56, 1, 1, 8, 0, 0, 64, 0, java_util_ConcurrentModificationException_init_, 0, methodName156 },
  { 258, 1, 1, 8, 0, 0, 64, 0, java_util_NoSuchElementException_init_, 0, methodName157 },
  { 260, 4, 1, 10, 0, 0, 64, 0, javax_realtime_AbsoluteTime_init_, 0, methodName158 },
  { 260, 5, 4, 14, 0, 3, 64, 0, javax_realtime_AbsoluteTime_init__, 0, methodName159 },
  { 260, 5, 5, 12, 0, 4, 64, 0, javax_realtime_AbsoluteTime_init___, 0, methodName160 },
  { 260, 3, 2, 30, 0, 1, 64, 0, javax_realtime_AbsoluteTime_init____, 0, methodName161 },
  { 260, 5, 2, 11, 0, 1, 64, 0, javax_realtime_AbsoluteTime_init_____, 0, methodName162 },
  { 260, 5, 5, 51, 0, 4, 33, 0, javax_realtime_AbsoluteTime_add, 0, methodName163 },
  { 260, 5, 3, 60, 0, 2, 33, 0, javax_realtime_AbsoluteTime_add_, 0, methodName164 },
  { 256, 1, 1, 8, 0, 0, 64, 0, javax_realtime_AbstractAsyncEventHandler_init_, 0, methodName165 },
  { 186, 1, 1, 8, 0, 0, 64, 0, javax_realtime_AperiodicParameters_init_, 0, methodName166 },
  { 82, 1, 1, 8, 0, 0, 64, 0, javax_realtime_AsyncEventHandler_init_, 0, methodName167 },
  { 82, 0, 1, 1, 0, 0, 32, 0, javax_realtime_AsyncEventHandler_handleAsyncEvent, 0, methodName168 },
  { 82, 1, 1, 17, 0, 0, 32, 0, javax_realtime_AsyncEventHandler_run, 0, methodName169 },
  { 98, 1, 1, 8, 0, 0, 64, 0, javax_realtime_BoundAsyncEventHandler_init_, 0, methodName170 },
  { 216, 2, 2, 16, 0, 1, 64, 0, javax_realtime_Clock_init_, 0, methodName171 },
  { 216, 1, 1, 7, 0, 0, 33, 0, javax_realtime_Clock_getRealtimeClock, 0, methodName172 },
  { 216, 0, 0, 0, 0, 1, 33, 0, 0, 0, methodName173 },
  { 216, 0, 0, 0, 0, 1, 33, 0, 0, 0, methodName174 },
  { 208, 4, 5, 53, 0, 4, 64, 0, javax_realtime_HighResolutionTime_init_, 0, methodName175 },
  { 208, 4, 2, 141, 0, 1, 17, 0, javax_realtime_HighResolutionTime_compareTo, 0, methodName176 },
  { 208, 1, 1, 10, 0, 0, 33, 0, javax_realtime_HighResolutionTime_getClock, 0, methodName177 },
  { 208, 2, 1, 10, 0, 0, 22, 0, javax_realtime_HighResolutionTime_getMilliseconds, 0, methodName178 },
  { 208, 1, 1, 10, 0, 0, 17, 0, javax_realtime_HighResolutionTime_getNanoseconds, 0, methodName179 },
  { 208, 6, 3, 47, 0, 0, 17, 0, javax_realtime_HighResolutionTime_hashCode, 0, methodName180 },
  { 208, 4, 4, 42, 0, 3, 1, 0, javax_realtime_HighResolutionTime_isNormalized, 0, methodName181 },
  { 208, 3, 3, 19, 0, 2, 32, 0, javax_realtime_HighResolutionTime_set, 0, methodName182 },
  { 208, 4, 4, 39, 0, 3, 32, 0, javax_realtime_HighResolutionTime_set_, 0, methodName183 },
  { 208, 3, 2, 86, 0, 1, 32, 0, javax_realtime_HighResolutionTime_set__, 0, methodName184 },
  { 208, 5, 4, 0, 0, 3, 32, 0, 0, (int16 (*)(int32 *))javax_realtime_HighResolutionTime_setNormalized, methodName185 },
  { 208, 3, 1, 54, 0, 0, 33, 0, javax_realtime_HighResolutionTime_toString, 0, methodName186 },
  { 278, 6, 7, 0, 0, 4, 64, 0, 0, (int16 (*)(int32 *))javax_realtime_MemoryArea_init_, methodName187 },
  { 278, 3, 2, 40, 0, 1, 64, 0, javax_realtime_MemoryArea_init__, 0, methodName188 },
  { 278, 2, 2, 56, 0, 1, 32, 0, javax_realtime_MemoryArea_addContainedMemory, 0, methodName189 },
  { 278, 2, 1, 14, 0, 1, 33, 0, javax_realtime_MemoryArea_getNamedMemoryArea, 0, methodName190 },
  { 278, 2, 4, 65, 0, 2, 33, 0, javax_realtime_MemoryArea_getNamedMemoryArea_, 0, methodName191 },
  { 278, 2, 3, 82, 0, 0, 17, 0, javax_realtime_MemoryArea_getRemainingBackingstoreSize, 0, methodName192 },
  { 278, 1, 1, 13, 0, 0, 17, 0, javax_realtime_MemoryArea_getRemainingMemorySize, 0, methodName193 },
  { 278, 2, 1, 15, 0, 0, 22, 0, javax_realtime_MemoryArea_memoryConsumed, 0, methodName194 },
  { 278, 2, 3, 100, 0, 1, 32, 0, javax_realtime_MemoryArea_removeContainedMemory, 0, methodName195 },
  { 278, 2, 1, 25, 0, 0, 32, 0, javax_realtime_MemoryArea_removeMemArea, 0, methodName196 },
  { 278, 4, 3, 74, 0, 2, 32, 0, javax_realtime_MemoryArea_resizeMemArea, 0, methodName197 },
  { 278, 1, 1, 14, 0, 0, 33, 0, javax_realtime_MemoryArea_toString, 0, methodName198 },
  { 54, 3, 3, 10, 0, 2, 64, 0, javax_realtime_NoHeapRealtimeThread_init_, 0, methodName199 },
  { 164, 5, 3, 12, 0, 2, 64, 0, javax_realtime_PeriodicParameters_init_, 0, methodName200 },
  { 164, 4, 5, 207, 0, 4, 64, 0, javax_realtime_PeriodicParameters_init__, 0, methodName201 },
  { 164, 1, 1, 10, 0, 0, 33, 0, javax_realtime_PeriodicParameters_getPeriod, 0, methodName202 },
  { 164, 1, 1, 10, 0, 0, 33, 0, javax_realtime_PeriodicParameters_getStart, 0, methodName203 },
  { 120, 2, 2, 16, 0, 1, 64, 0, javax_realtime_PriorityParameters_init_, 0, methodName204 },
  { 120, 1, 1, 10, 0, 0, 17, 0, javax_realtime_PriorityParameters_getPriority, 0, methodName205 },
  { 120, 2, 2, 11, 0, 1, 32, 0, javax_realtime_PriorityParameters_setPriority, 0, methodName206 },
  { 252, 1, 1, 8, 0, 0, 64, 0, javax_realtime_PriorityScheduler_init_, 0, methodName207 },
  { 64, 1, 1, 12, 0, 0, 32, 0, javax_realtime_RealtimeClock_clinit_, 0, methodName208 },
  { 64, 2, 1, 9, 0, 0, 64, 0, javax_realtime_RealtimeClock_init_, 0, methodName209 },
  { 64, 3, 1, 17, 0, 0, 33, 0, javax_realtime_RealtimeClock_getResolution, 0, methodName210 },
  { 64, 4, 2, 54, 0, 1, 33, 0, javax_realtime_RealtimeClock_getResolution_, 0, methodName211 },
  { 64, 4, 2, 0, 0, 1, 33, 0, 0, (int16 (*)(int32 *))javax_realtime_RealtimeClock_getTime, methodName212 },
  { 64, 2, 1, 42, 0, 0, 33, 0, javax_realtime_RealtimeClock_instance, 0, methodName213 },
  { 64, 6, 4, 52, 0, 0, 33, 0, javax_realtime_RealtimeClock_setResolution, 0, methodName214 },
  { 50, 3, 3, 40, 0, 2, 64, 0, javax_realtime_RealtimeThread_init_, 0, methodName215 },
  { 248, 4, 1, 10, 0, 0, 64, 0, javax_realtime_RelativeTime_init_, 0, methodName216 },
  { 248, 5, 4, 14, 0, 3, 64, 0, javax_realtime_RelativeTime_init__, 0, methodName217 },
  { 248, 5, 5, 24, 0, 4, 64, 0, javax_realtime_RelativeTime_init___, 0, methodName218 },
  { 248, 5, 2, 22, 0, 1, 64, 0, javax_realtime_RelativeTime_init____, 0, methodName219 },
  { 248, 3, 2, 66, 0, 1, 64, 0, javax_realtime_RelativeTime_init_____, 0, methodName220 },
  { 240, 1, 1, 8, 0, 0, 64, 0, javax_realtime_ReleaseParameters_init_, 0, methodName221 },
  { 240, 4, 3, 40, 0, 2, 64, 0, javax_realtime_ReleaseParameters_init__, 0, methodName222 },
  { 92, 1, 1, 8, 0, 0, 64, 0, javax_realtime_Scheduler_init_, 0, methodName223 },
  { 238, 1, 1, 8, 0, 0, 64, 0, javax_realtime_SchedulingParameters_init_, 0, methodName224 },
  { 160, 2, 2, 21, 0, 0, 64, 0, javax_safetycritical_CyclicScheduler_init_, 0, methodName225 },
  { 160, 1, 1, 10, 0, 0, 33, 0, javax_safetycritical_CyclicScheduler_getCurrentProcess, 0, methodName226 },
  { 160, 1, 1, 4, 0, 0, 33, 0, javax_safetycritical_CyclicScheduler_getDefaultMonitor, 0, methodName227 },
  { 160, 2, 2, 87, 0, 0, 33, 0, javax_safetycritical_CyclicScheduler_getNextProcess, 0, methodName228 },
  { 160, 2, 1, 32, 0, 0, 33, 0, javax_safetycritical_CyclicScheduler_instance, 0, methodName229 },
  { 160, 0, 2, 3, 0, 1, 32, 0, javax_safetycritical_CyclicScheduler_notify, 0, methodName230 },
  { 160, 0, 2, 3, 0, 1, 32, 0, javax_safetycritical_CyclicScheduler_notifyAll, 0, methodName231 },
  { 160, 5, 2, 54, 0, 0, 32, 0, javax_safetycritical_CyclicScheduler_processStart, 0, methodName232 },
  { 160, 2, 2, 28, 0, 1, 32, 0, javax_safetycritical_CyclicScheduler_start, 0, methodName233 },
  { 160, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))javax_safetycritical_CyclicScheduler_stop, methodName234 },
  { 160, 0, 2, 3, 0, 1, 32, 0, javax_safetycritical_CyclicScheduler_wait, 0, methodName235 },
  { 18, 5, 4, 96, 0, 2, 64, 0, javax_safetycritical_Launcher_init_, 0, methodName236 },
  { 18, 1, 1, 25, 0, 0, 32, 0, javax_safetycritical_Launcher_run, 0, methodName237 },
  { 18, 2, 2, 30, 0, 0, 32, 0, javax_safetycritical_Launcher_startLevel0, 0, methodName238 },
  { 18, 2, 2, 43, 0, 0, 32, 0, javax_safetycritical_Launcher_startLevel1_2, 0, methodName239 },
  { 276, 9, 7, 233, 0, 3, 64, 0, javax_safetycritical_ManagedEventHandler_init_, 0, methodName240 },
  { 276, 1, 1, 20, 0, 0, 32, 0, javax_safetycritical_ManagedEventHandler_cleanUp, 0, methodName241 },
  { 100, 1, 1, 8, 0, 0, 33, 0, javax_safetycritical_ManagedLongEventHandler_getCurrentMemory, 0, methodName242 },
  { 100, 2, 2, 9, 0, 1, 32, 0, javax_safetycritical_ManagedLongEventHandler_setCurrentMemory, 0, methodName243 },
  { 158, 2, 2, 9, 0, 1, 64, 0, javax_safetycritical_ManagedMemory_BackingStore_init_, 0, methodName244 },
  { 198, 5, 2, 19, 0, 1, 64, 0, javax_safetycritical_ManagedMemory_ImmortalMemory_init_, 0, methodName245 },
  { 198, 1, 1, 23, 0, 0, 33, 0, javax_safetycritical_ManagedMemory_ImmortalMemory_instance, 0, methodName246 },
  { 14, 2, 1, 23, 0, 0, 32, 0, javax_safetycritical_ManagedMemory_clinit_, 0, methodName247 },
  { 14, 5, 5, 0, 0, 4, 64, 0, 0, (int16 (*)(int32 *))javax_safetycritical_ManagedMemory_init_, methodName248 },
  { 14, 3, 1, 22, 0, 1, 32, 0, javax_safetycritical_ManagedMemory_allocateBackingStore, 0, methodName249 },
  { 14, 3, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))javax_safetycritical_ManagedMemory_enter, methodName250 },
  { 14, 3, 3, 144, 0, 1, 32, 0, javax_safetycritical_ManagedMemory_executeInArea, 0, methodName251 },
  { 14, 1, 1, 31, 0, 0, 33, 0, javax_safetycritical_ManagedMemory_getCurrentProcess, 0, methodName252 },
  { 14, 1, 1, 10, 0, 0, 33, 0, javax_safetycritical_ManagedMemory_getDelegate, 0, methodName253 },
  { 14, 1, 1, 8, 0, 0, 32, 0, javax_safetycritical_ManagedMemory_removeArea, 0, methodName254 },
  { 14, 2, 1, 15, 0, 0, 32, 0, javax_safetycritical_ManagedMemory_resetArea, 0, methodName255 },
  { 14, 3, 3, 9, 0, 2, 32, 0, javax_safetycritical_ManagedMemory_resizeArea, 0, methodName256 },
  { 14, 2, 5, 199, 0, 2, 32, 0, javax_safetycritical_ManagedMemory_runEnter, 0, methodName257 },
  { 14, 2, 5, 377, 0, 2, 32, 0, javax_safetycritical_ManagedMemory_runExecuteInArea, 0, methodName258 },
  { 202, 3, 1, 25, 0, 1, 33, 0, javax_safetycritical_ManagedSchedMethods_createScjProcess, 0, methodName259 },
  { 202, 4, 2, 104, 0, 2, 33, 0, javax_safetycritical_ManagedSchedMethods_createScjProcess_, 0, methodName260 },
  { 202, 1, 1, 64, 0, 1, 33, 0, javax_safetycritical_ManagedSchedMethods_getMission, 0, methodName261 },
  { 202, 1, 1, 64, 0, 1, 33, 0, javax_safetycritical_ManagedSchedMethods_getPriorityParameter, 0, methodName262 },
  { 202, 1, 1, 64, 0, 1, 33, 0, javax_safetycritical_ManagedSchedMethods_getScjProcess, 0, methodName263 },
  { 202, 1, 1, 64, 0, 1, 33, 0, javax_safetycritical_ManagedSchedMethods_getStorage, 0, methodName264 },
  { 206, 2, 1, 48, 0, 0, 64, 0, javax_safetycritical_ManagedSchedulableSet_init_, 0, methodName265 },
  { 206, 3, 2, 60, 0, 1, 32, 0, javax_safetycritical_ManagedSchedulableSet_addMS, 0, methodName266 },
  { 206, 2, 3, 40, 0, 1, 1, 0, javax_safetycritical_ManagedSchedulableSet_contains, 0, methodName267 },
  { 206, 3, 2, 140, 0, 0, 32, 0, javax_safetycritical_ManagedSchedulableSet_removeAperiodicHandlers, 0, methodName268 },
  { 206, 3, 3, 212, 0, 1, 32, 0, javax_safetycritical_ManagedSchedulableSet_removeMSObject, 0, methodName269 },
  { 206, 3, 1, 36, 0, 0, 33, 0, javax_safetycritical_ManagedSchedulableSet_toString, 0, methodName270 },
  { 24, 4, 3, 11, 0, 2, 64, 0, javax_safetycritical_ManagedThread_init_, 0, methodName271 },
  { 24, 9, 7, 180, 0, 3, 64, 0, javax_safetycritical_ManagedThread_init__, 0, methodName272 },
  { 24, 1, 1, 20, 0, 0, 32, 0, javax_safetycritical_ManagedThread_cleanUp, 0, methodName273 },
  { 24, 2, 2, 20, 0, 0, 32, 0, javax_safetycritical_ManagedThread_register, 0, methodName274 },
  { 24, 4, 2, 123, 0, 1, 32, 0, javax_safetycritical_ManagedThread_sleep, 0, methodName275 },
  { 8, 1, 1, 8, 0, 0, 64, 0, javax_safetycritical_MemoryInfo_init_, 0, methodName276 },
  { 8, 2, 2, 32, 0, 1, 64, 0, javax_safetycritical_MemoryInfo_init__, 0, methodName277 },
  { 38, 1, 1, 16, 0, 0, 32, 0, javax_safetycritical_Mission_clinit_, 0, methodName278 },
  { 38, 2, 1, 32, 0, 0, 64, 0, javax_safetycritical_Mission_init_, 0, methodName279 },
  { 38, 3, 2, 55, 0, 1, 17, 0, javax_safetycritical_Mission_addNewMission, 0, methodName280 },
  { 38, 1, 1, 4, 0, 0, 1, 0, javax_safetycritical_Mission_cleanUp, 0, methodName281 },
  { 38, 1, 1, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))javax_safetycritical_Mission_getCurrentMission, methodName282 },
  { 38, 2, 2, 25, 0, 2, 33, 0, javax_safetycritical_Mission_getScjProcess, 0, methodName283 },
  { 38, 1, 1, 10, 0, 0, 33, 0, javax_safetycritical_Mission_getSequencer, 0, methodName284 },
  { 38, 0, 0, 0, 0, 0, 32, 0, 0, 0, methodName285 },
  { 38, 3, 3, 159, 0, 1, 32, 0, javax_safetycritical_Mission_runCleanup, 0, methodName286 },
  { 38, 3, 6, 148, 0, 0, 32, 0, javax_safetycritical_Mission_runExecute, 0, methodName287 },
  { 38, 3, 1, 103, 0, 0, 32, 0, javax_safetycritical_Mission_runInitialize, 0, methodName288 },
  { 38, 2, 2, 11, 0, 1, 32, 0, javax_safetycritical_Mission_setMissionSeq, 0, methodName289 },
  { 38, 1, 1, 10, 0, 0, 1, 0, javax_safetycritical_Mission_terminationPending, 0, methodName290 },
  { 280, 2, 2, 16, 0, 1, 64, 0, javax_safetycritical_MissionMemory_1_init_, 0, methodName291 },
  { 280, 1, 1, 20, 0, 0, 32, 0, javax_safetycritical_MissionMemory_1_run, 0, methodName292 },
  { 272, 2, 2, 16, 0, 1, 64, 0, javax_safetycritical_MissionMemory_2_init_, 0, methodName293 },
  { 272, 1, 1, 20, 0, 0, 32, 0, javax_safetycritical_MissionMemory_2_run, 0, methodName294 },
  { 274, 2, 2, 16, 0, 1, 64, 0, javax_safetycritical_MissionMemory_3_init_, 0, methodName295 },
  { 274, 2, 1, 27, 0, 0, 32, 0, javax_safetycritical_MissionMemory_3_run, 0, methodName296 },
  { 122, 5, 4, 64, 0, 3, 64, 0, javax_safetycritical_MissionMemory_init_, 0, methodName297 },
  { 122, 2, 2, 28, 0, 1, 32, 0, javax_safetycritical_MissionMemory_enterToCleanup, 0, methodName298 },
  { 122, 2, 2, 23, 0, 1, 32, 0, javax_safetycritical_MissionMemory_enterToExecute, 0, methodName299 },
  { 122, 2, 2, 23, 0, 1, 32, 0, javax_safetycritical_MissionMemory_enterToInitialize, 0, methodName300 },
  { 156, 1, 1, 23, 0, 0, 32, 0, javax_safetycritical_MissionSequencer_clinit_, 0, methodName301 },
  { 156, 4, 3, 13, 0, 2, 64, 0, javax_safetycritical_MissionSequencer_init_, 0, methodName302 },
  { 156, 6, 4, 202, 0, 3, 64, 0, javax_safetycritical_MissionSequencer_init__, 0, methodName303 },
  { 156, 1, 1, 19, 0, 0, 32, 0, javax_safetycritical_MissionSequencer_cleanUp, 0, methodName304 },
  { 156, 0, 0, 0, 0, 0, 33, 0, 0, 0, methodName305 },
  { 156, 3, 1, 493, 0, 0, 32, 0, javax_safetycritical_MissionSequencer_handleAsyncEvent, 0, methodName306 },
  { 156, 3, 1, 72, 0, 0, 160, 0, javax_safetycritical_MissionSequencer_seqNotify, 0, methodName307 },
  { 156, 1, 2, 57, 1, 0, 160, ex_javax_safetycritical_MissionSequencer_seqWait, javax_safetycritical_MissionSequencer_seqWait, 0, methodName308 },
  { 62, 2, 2, 29, 0, 1, 64, 0, javax_safetycritical_Monitor_init_, 0, methodName309 },
  { 62, 5, 2, 114, 0, 1, 33, 0, javax_safetycritical_Monitor_getMonitor, 0, methodName310 },
  { 62, 3, 3, 228, 0, 0, 32, 0, javax_safetycritical_Monitor_lock, 0, methodName311 },
  { 62, 2, 2, 13, 0, 2, 17, 0, javax_safetycritical_Monitor_max, 0, methodName312 },
  { 62, 2, 2, 11, 0, 1, 32, 0, javax_safetycritical_Monitor_setOwner, 0, methodName313 },
  { 62, 3, 3, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))javax_safetycritical_Monitor_unlock, methodName314 },
  { 84, 4, 4, 11, 0, 3, 64, 0, javax_safetycritical_PeriodicEventHandler_init_, 0, methodName315 },
  { 222, 4, 2, 72, 0, 1, 64, 0, javax_safetycritical_PriorityFrame_init_, 0, methodName316 },
  { 222, 4, 4, 238, 0, 1, 32, 0, javax_safetycritical_PriorityFrame_addProcess, 0, methodName317 },
  { 222, 2, 2, 51, 0, 1, 32, 0, javax_safetycritical_PriorityFrame_removeFromQueue, 0, methodName318 },
  { 268, 3, 2, 41, 0, 1, 64, 0, javax_safetycritical_PriorityQueue_init_, 0, methodName319 },
  { 268, 4, 4, 41, 0, 2, 32, 0, javax_safetycritical_PriorityQueue_exchange, 0, methodName320 },
  { 268, 4, 2, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))javax_safetycritical_PriorityQueue_extractMax, methodName321 },
  { 268, 2, 3, 42, 0, 1, 17, 0, javax_safetycritical_PriorityQueue_find, 0, methodName322 },
  { 268, 2, 4, 79, 0, 1, 33, 0, javax_safetycritical_PriorityQueue_getScjProcess, 0, methodName323 },
  { 268, 4, 5, 143, 0, 1, 32, 0, javax_safetycritical_PriorityQueue_heapify, 0, methodName324 },
  { 268, 5, 3, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))javax_safetycritical_PriorityQueue_insert, methodName325 },
  { 268, 2, 2, 6, 0, 1, 17, 0, javax_safetycritical_PriorityQueue_left, 0, methodName326 },
  { 268, 3, 3, 23, 0, 1, 32, 0, javax_safetycritical_PriorityQueue_makeEmptyTree, 0, methodName327 },
  { 268, 2, 2, 6, 0, 1, 17, 0, javax_safetycritical_PriorityQueue_parent, 0, methodName328 },
  { 268, 4, 3, 76, 0, 1, 32, 0, javax_safetycritical_PriorityQueue_remove, 0, methodName329 },
  { 268, 2, 2, 8, 0, 1, 17, 0, javax_safetycritical_PriorityQueue_right, 0, methodName330 },
  { 6, 2, 2, 47, 0, 1, 64, 0, javax_safetycritical_PriorityQueueForLockAndWait_init_, 0, methodName331 },
  { 6, 5, 6, 267, 0, 2, 32, 0, javax_safetycritical_PriorityQueueForLockAndWait_addProcess, 0, methodName332 },
  { 6, 3, 4, 83, 0, 1, 33, 0, javax_safetycritical_PriorityQueueForLockAndWait_getNextProcess, 0, methodName333 },
  { 6, 2, 4, 63, 0, 1, 33, 0, javax_safetycritical_PriorityQueueForLockAndWait_getScjProcess, 0, methodName334 },
  { 6, 3, 3, 23, 0, 1, 32, 0, javax_safetycritical_PriorityQueueForLockAndWait_makeEmptyQueue, 0, methodName335 },
  { 6, 3, 3, 71, 0, 1, 32, 0, javax_safetycritical_PriorityQueueForLockAndWait_removeProcess, 0, methodName336 },
  { 6, 5, 3, 78, 0, 1, 32, 0, javax_safetycritical_PriorityQueueForLockAndWait_reorderSet, 0, methodName337 },
  { 250, 7, 2, 176, 0, 0, 64, 0, javax_safetycritical_PriorityScheduler_init_, 0, methodName338 },
  { 250, 2, 3, 51, 0, 1, 32, 0, javax_safetycritical_PriorityScheduler_addOuterMostSeq, 0, methodName339 },
  { 250, 3, 3, 22, 0, 2, 32, 0, javax_safetycritical_PriorityScheduler_addProcessToLockQueue, 0, methodName340 },
  { 250, 1, 1, 10, 0, 0, 33, 0, javax_safetycritical_PriorityScheduler_getCurrentProcess, 0, methodName341 },
  { 250, 2, 2, 21, 0, 1, 33, 0, javax_safetycritical_PriorityScheduler_getProcessFromLockQueue, 0, methodName342 },
  { 250, 2, 2, 21, 0, 1, 32, 0, javax_safetycritical_PriorityScheduler_insertReadyQueue, 0, methodName343 },
  { 250, 2, 1, 32, 0, 0, 33, 0, javax_safetycritical_PriorityScheduler_instance, 0, methodName344 },
  { 250, 2, 3, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))javax_safetycritical_PriorityScheduler_move, methodName345 },
  { 250, 2, 2, 37, 0, 0, 32, 0, javax_safetycritical_PriorityScheduler_moveToNext, 0, methodName346 },
  { 250, 5, 2, 54, 0, 0, 32, 0, javax_safetycritical_PriorityScheduler_processStart, 0, methodName347 },
  { 250, 2, 1, 32, 0, 0, 32, 0, javax_safetycritical_PriorityScheduler_start, 0, methodName348 },
  { 250, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))javax_safetycritical_PriorityScheduler_stop, methodName349 },
  { 270, 1, 1, 8, 0, 0, 64, 0, javax_safetycritical_PrioritySchedulerImpl_init_, 0, methodName350 },
  { 270, 1, 1, 4, 0, 0, 33, 0, javax_safetycritical_PrioritySchedulerImpl_getDefaultMonitor, 0, methodName351 },
  { 270, 1, 1, 8, 0, 0, 33, 0, javax_safetycritical_PrioritySchedulerImpl_getDefaultMonitor_, 0, methodName352 },
  { 270, 2, 2, 95, 0, 0, 33, 0, javax_safetycritical_PrioritySchedulerImpl_getNextProcess, 0, methodName353 },
  { 270, 3, 4, 86, 0, 1, 32, 0, javax_safetycritical_PrioritySchedulerImpl_notify, 0, methodName354 },
  { 270, 3, 4, 111, 0, 1, 32, 0, javax_safetycritical_PrioritySchedulerImpl_notifyAll, 0, methodName355 },
  { 270, 3, 3, 101, 0, 1, 32, 0, javax_safetycritical_PrioritySchedulerImpl_wait, 0, methodName356 },
  { 176, 5, 5, 0, 0, 4, 64, 0, 0, (int16 (*)(int32 *))javax_safetycritical_PrivateMemory_init_, methodName357 },
  { 60, 3, 3, 10, 0, 2, 64, 0, javax_safetycritical_ScjAperiodicEventHandlerProcess_init_, 0, methodName358 },
  { 60, 2, 2, 133, 0, 1, 32, 0, javax_safetycritical_ScjAperiodicEventHandlerProcess_gotoNextState, 0, methodName359 },
  { 242, 3, 3, 10, 0, 2, 64, 0, javax_safetycritical_ScjManagedThreadProcess_init_, 0, methodName360 },
  { 242, 2, 2, 123, 0, 1, 32, 0, javax_safetycritical_ScjManagedThreadProcess_gotoNextState, 0, methodName361 },
  { 242, 1, 1, 34, 0, 0, 32, 0, javax_safetycritical_ScjManagedThreadProcess_switchToArea, 0, methodName362 },
  { 74, 3, 3, 10, 0, 2, 64, 0, javax_safetycritical_ScjMissionSequencerProcess_init_, 0, methodName363 },
  { 74, 2, 3, 171, 0, 1, 32, 0, javax_safetycritical_ScjMissionSequencerProcess_gotoNextState, 0, methodName364 },
  { 230, 3, 3, 10, 0, 2, 64, 0, javax_safetycritical_ScjOneShotEventHandlerProcess_init_, 0, methodName365 },
  { 230, 2, 2, 97, 0, 1, 32, 0, javax_safetycritical_ScjOneShotEventHandlerProcess_gotoNextState, 0, methodName366 },
  { 30, 3, 3, 10, 0, 2, 64, 0, javax_safetycritical_ScjPeriodicEventHandlerProcess_init_, 0, methodName367 },
  { 30, 2, 2, 136, 0, 1, 32, 0, javax_safetycritical_ScjPeriodicEventHandlerProcess_gotoNextState, 0, methodName368 },
  { 228, 2, 2, 16, 0, 1, 64, 0, javax_safetycritical_ScjProcess_1_init_, 0, methodName369 },
  { 228, 2, 3, 89, 1, 1, 32, ex_javax_safetycritical_ScjProcess_1_catchError, javax_safetycritical_ScjProcess_1_catchError, 0, methodName370 },
  { 228, 3, 3, 418, 2, 0, 32, ex_javax_safetycritical_ScjProcess_1_run, javax_safetycritical_ScjProcess_1_run, 0, methodName371 },
  { 232, 4, 4, 11, 0, 3, 64, 0, javax_safetycritical_ScjProcess_2_init_, 0, methodName372 },
  { 232, 1, 1, 8, 0, 0, 32, 0, javax_safetycritical_ScjProcess_2_handleAsyncEvent, 0, methodName373 },
  { 232, 1, 1, 17, 0, 0, 32, 0, javax_safetycritical_ScjProcess_2_run, 0, methodName374 },
  { 232, 0, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))javax_safetycritical_ScjProcess_2_yield, methodName375 },
  { 190, 1, 1, 8, 0, 0, 64, 0, javax_safetycritical_ScjProcess_ExceptionReporter_init_, 0, methodName376 },
  { 190, 1, 2, 8, 0, 1, 64, 0, javax_safetycritical_ScjProcess_ExceptionReporter_init__, 0, methodName377 },
  { 190, 2, 1, 30, 0, 0, 32, 0, javax_safetycritical_ScjProcess_ExceptionReporter_run, 0, methodName378 },
  { 168, 6, 3, 186, 0, 2, 64, 0, javax_safetycritical_ScjProcess_init_, 0, methodName379 },
  { 168, 2, 2, 9, 0, 2, 32, 0, javax_safetycritical_ScjProcess_access_1, 0, methodName380 },
  { 168, 1, 1, 10, 0, 1, 33, 0, javax_safetycritical_ScjProcess_access_2, 0, methodName381 },
  { 168, 2, 2, 216, 0, 1, 17, 0, javax_safetycritical_ScjProcess_compareTo, 0, methodName382 },
  { 168, 15, 2, 157, 0, 0, 33, 0, javax_safetycritical_ScjProcess_createIdleProcess, 0, methodName383 },
  { 168, 1, 1, 10, 0, 0, 33, 0, javax_safetycritical_ScjProcess_getTarget, 0, methodName384 },
  { 168, 0, 2, 3, 0, 1, 32, 0, javax_safetycritical_ScjProcess_gotoNextState, 0, methodName385 },
  { 168, 2, 2, 86, 0, 1, 32, 0, javax_safetycritical_ScjProcess_runLogic, 0, methodName386 },
  { 168, 2, 2, 11, 0, 1, 32, 0, javax_safetycritical_ScjProcess_setIndex, 0, methodName387 },
  { 168, 2, 2, 56, 0, 1, 32, 0, javax_safetycritical_ScjProcess_setProcess, 0, methodName388 },
  { 168, 5, 4, 236, 0, 1, 32, 0, javax_safetycritical_ScjProcess_setRelease, 0, methodName389 },
  { 168, 1, 1, 14, 0, 0, 32, 0, javax_safetycritical_ScjProcess_start, 0, methodName390 },
  { 168, 0, 1, 3, 0, 0, 32, 0, javax_safetycritical_ScjProcess_switchToArea, 0, methodName391 },
  { 168, 3, 1, 47, 0, 0, 33, 0, javax_safetycritical_ScjProcess_toString, 0, methodName392 },
  { 138, 3, 3, 19, 0, 2, 32, 0, javax_safetycritical_Services_setCeiling, 0, methodName393 },
  { 104, 3, 2, 41, 0, 1, 64, 0, javax_safetycritical_SleepingQueue_init_, 0, methodName394 },
  { 104, 4, 4, 41, 0, 2, 32, 0, javax_safetycritical_SleepingQueue_exchange, 0, methodName395 },
  { 104, 4, 2, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))javax_safetycritical_SleepingQueue_extractMin, methodName396 },
  { 104, 2, 3, 42, 0, 1, 17, 0, javax_safetycritical_SleepingQueue_find, 0, methodName397 },
  { 104, 2, 4, 0, 0, 1, 33, 0, 0, (int16 (*)(int32 *))javax_safetycritical_SleepingQueue_getScjProcess, methodName398 },
  { 104, 4, 5, 167, 0, 1, 32, 0, javax_safetycritical_SleepingQueue_heapify, 0, methodName399 },
  { 104, 5, 3, 149, 0, 1, 32, 0, javax_safetycritical_SleepingQueue_insert, 0, methodName400 },
  { 104, 2, 2, 6, 0, 1, 17, 0, javax_safetycritical_SleepingQueue_left, 0, methodName401 },
  { 104, 3, 3, 23, 0, 1, 32, 0, javax_safetycritical_SleepingQueue_makeEmptyTree, 0, methodName402 },
  { 104, 3, 1, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))javax_safetycritical_SleepingQueue_minimum, methodName403 },
  { 104, 2, 2, 6, 0, 1, 17, 0, javax_safetycritical_SleepingQueue_parent, 0, methodName404 },
  { 104, 4, 3, 76, 0, 1, 32, 0, javax_safetycritical_SleepingQueue_remove, 0, methodName405 },
  { 104, 2, 2, 8, 0, 1, 17, 0, javax_safetycritical_SleepingQueue_right, 0, methodName406 },
  { 118, 4, 10, 104, 0, 9, 64, 0, javax_safetycritical_StorageParameters_init_, 0, methodName407 },
  { 46, 4, 1, 120, 0, 0, 32, 0, javax_safetycritical_annotate_Phase_clinit_, 0, methodName408 },
  { 46, 3, 3, 10, 0, 2, 64, 0, javax_safetycritical_annotate_Phase_init_, 0, methodName409 },
  { 134, 6, 1, 205, 0, 0, 32, 0, javax_scj_util_Const_clinit_, 0, methodName410 },
  { 134, 2, 1, 16, 0, 0, 32, 0, javax_scj_util_Const_setDefaultErrorReporter, 0, methodName411 },
  { 218, 1, 1, 8, 0, 0, 64, 0, javax_scj_util_DefaultSCJErrorReporter_init_, 0, methodName412 },
  { 218, 3, 2, 38, 0, 1, 32, 0, javax_scj_util_DefaultSCJErrorReporter_processExecutionError, 0, methodName413 },
  { 218, 1, 2, 17, 0, 1, 32, 0, javax_scj_util_DefaultSCJErrorReporter_processOutOfMemoryError, 0, methodName414 },
  { 218, 3, 2, 27, 0, 1, 32, 0, javax_scj_util_DefaultSCJErrorReporter_schedulerError, 0, methodName415 },
  { 244, 1, 1, 8, 0, 0, 64, 0, javax_scj_util_SilentSCJErrorReporter_init_, 0, methodName416 },
  { 244, 0, 2, 3, 0, 1, 32, 0, javax_scj_util_SilentSCJErrorReporter_processExecutionError, 0, methodName417 },
  { 244, 0, 2, 3, 0, 1, 32, 0, javax_scj_util_SilentSCJErrorReporter_processOutOfMemoryError, 0, methodName418 },
  { 244, 0, 2, 3, 0, 1, 32, 0, javax_scj_util_SilentSCJErrorReporter_schedulerError, 0, methodName419 },
  { 282, 1, 1, 8, 0, 0, 64, 0, test_TestSCJThreadMemory_BiggerObject_init_, 0, methodName420 },
  { 282, 1, 2, 8, 0, 1, 64, 0, test_TestSCJThreadMemory_BiggerObject_init__, 0, methodName421 },
  { 136, 1, 1, 8, 0, 0, 64, 0, test_TestSCJThreadMemory_MyApp_init_, 0, methodName422 },
  { 136, 1, 2, 8, 0, 1, 64, 0, test_TestSCJThreadMemory_MyApp_init__, 0, methodName423 },
  { 136, 2, 1, 18, 0, 0, 33, 0, test_TestSCJThreadMemory_MyApp_getSequencer, 0, methodName424 },
  { 292, 1, 1, 8, 0, 0, 64, 0, test_TestSCJThreadMemory_MyMission_init_, 0, methodName425 },
  { 292, 1, 2, 8, 0, 1, 64, 0, test_TestSCJThreadMemory_MyMission_init__, 0, methodName426 },
  { 292, 5, 1, 66, 0, 0, 32, 0, test_TestSCJThreadMemory_MyMission_initialize, 0, methodName427 },
  { 94, 4, 1, 47, 0, 0, 64, 0, test_TestSCJThreadMemory_MySequencer_init_, 0, methodName428 },
  { 94, 1, 1, 8, 0, 0, 33, 0, test_TestSCJThreadMemory_MySequencer_getNextMission, 0, methodName429 },
  { 94, 3, 1, 55, 0, 0, 33, 0, test_TestSCJThreadMemory_MySequencer_getNextMission_, 0, methodName430 },
  { 94, 1, 1, 17, 0, 0, 32, 0, test_TestSCJThreadMemory_MySequencer_run, 0, methodName431 },
  { 144, 1, 1, 8, 0, 0, 64, 0, test_TestSCJThreadMemory_SmallObject_init_, 0, methodName432 },
  { 144, 1, 2, 8, 0, 1, 64, 0, test_TestSCJThreadMemory_SmallObject_init__, 0, methodName433 },
  { 210, 6, 3, 37, 0, 2, 64, 0, test_TestSCJThreadMemory_Thread1_init_, 0, methodName434 },
  { 210, 1, 2, 18, 1, 0, 32, ex_test_TestSCJThreadMemory_Thread1_delay, test_TestSCJThreadMemory_Thread1_delay, 0, methodName435 },
  { 210, 3, 4, 173, 0, 0, 32, 0, test_TestSCJThreadMemory_Thread1_doWork, 0, methodName436 },
  { 210, 1, 1, 15, 0, 0, 32, 0, test_TestSCJThreadMemory_Thread1_run, 0, methodName437 },
  { 212, 6, 3, 29, 0, 2, 64, 0, test_TestSCJThreadMemory_Thread2_init_, 0, methodName438 },
  { 212, 1, 2, 18, 1, 0, 32, ex_test_TestSCJThreadMemory_Thread2_delay, test_TestSCJThreadMemory_Thread2_delay, 0, methodName439 },
  { 212, 3, 4, 173, 0, 0, 32, 0, test_TestSCJThreadMemory_Thread2_doWork, 0, methodName440 },
  { 212, 1, 1, 15, 0, 0, 32, 0, test_TestSCJThreadMemory_Thread2_run, 0, methodName441 },
  { 184, 1, 1, 9, 0, 0, 17, 0, test_TestSCJThreadMemory_access_0, 0, methodName442 },
  { 184, 1, 1, 9, 0, 0, 17, 0, test_TestSCJThreadMemory_access_1, 0, methodName443 },
  { 184, 11, 4, 284, 0, 1, 32, 0, test_TestSCJThreadMemory_main, 0, methodName444 },
  { 220, 2, 2, 16, 0, 1, 64, 0, vm_Address32Bit_init_, 0, methodName445 },
  { 264, 1, 1, 8, 0, 0, 64, 0, vm_Address_init_, 0, methodName446 },
  { 286, 1, 1, 9, 0, 0, 32, 0, vm_ClockInterruptHandler_clinit_, 0, methodName447 },
  { 286, 5, 3, 42, 0, 2, 64, 0, vm_ClockInterruptHandler_init_, 0, methodName448 },
  { 286, 2, 2, 24, 0, 1, 32, 0, vm_ClockInterruptHandler_catchError, 0, methodName449 },
  { 286, 2, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_ClockInterruptHandler_disable, methodName450 },
  { 286, 2, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_ClockInterruptHandler_enable, methodName451 },
  { 286, 2, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_ClockInterruptHandler_handle, methodName452 },
  { 286, 4, 2, 0, 0, 2, 32, 0, 0, (int16 (*)(int32 *))vm_ClockInterruptHandler_initialize, methodName453 },
  { 286, 2, 1, 9, 0, 0, 32, 0, vm_ClockInterruptHandler_register, 0, methodName454 },
  { 286, 2, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_ClockInterruptHandler_run, methodName455 },
  { 286, 2, 2, 39, 0, 1, 32, 0, vm_ClockInterruptHandler_setScheduler, 0, methodName456 },
  { 286, 2, 2, 11, 0, 1, 32, 0, vm_ClockInterruptHandler_startClockHandler, 0, methodName457 },
  { 286, 1, 1, 8, 0, 0, 32, 0, vm_ClockInterruptHandler_yield, 0, methodName458 },
  { 204, 2, 2, 16, 0, 1, 64, 0, vm_HardwareObject_init_, 0, methodName459 },
  { 110, 1, 1, 8, 0, 0, 64, 0, vm_InterruptDispatcher_NullHandler_init_, 0, methodName460 },
  { 110, 1, 2, 8, 0, 1, 64, 0, vm_InterruptDispatcher_NullHandler_init__, 0, methodName461 },
  { 110, 0, 1, 3, 0, 0, 32, 0, vm_InterruptDispatcher_NullHandler_disable, 0, methodName462 },
  { 110, 0, 1, 3, 0, 0, 32, 0, vm_InterruptDispatcher_NullHandler_enable, 0, methodName463 },
  { 110, 0, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_InterruptDispatcher_NullHandler_handle, methodName464 },
  { 146, 1, 1, 9, 0, 0, 32, 0, vm_InterruptDispatcher_clinit_, 0, methodName465 },
  { 146, 3, 2, 42, 0, 0, 32, 0, vm_InterruptDispatcher_init, 0, methodName466 },
  { 146, 2, 1, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_InterruptDispatcher_interrupt, methodName467 },
  { 146, 3, 2, 0, 0, 2, 32, 0, 0, (int16 (*)(int32 *))vm_InterruptDispatcher_registerHandler, methodName468 },
  { 44, 1, 1, 9, 0, 0, 33, 0, vm_Machine_getCurrentScheduler, 0, methodName469 },
  { 44, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Machine_setCurrentScheduler, methodName470 },
  { 152, 2, 2, 48, 0, 1, 64, 0, vm_Memory_MemoryInfo_init_, 0, methodName471 },
  { 152, 3, 1, 19, 0, 0, 32, 0, vm_Memory_MemoryInfo_increaseInstanceCount, 0, methodName472 },
  { 152, 4, 2, 123, 0, 0, 33, 0, vm_Memory_MemoryInfo_toString, 0, methodName473 },
  { 246, 2, 3, 42, 0, 2, 64, 0, vm_Memory_init_, 0, methodName474 },
  { 246, 3, 4, 0, 0, 3, 64, 0, 0, (int16 (*)(int32 *))vm_Memory_init__, methodName475 },
  { 246, 1, 1, 10, 0, 1, 33, 0, vm_Memory_access_0, 0, methodName476 },
  { 246, 1, 1, 10, 0, 1, 17, 0, vm_Memory_access_1, 0, methodName477 },
  { 246, 3, 5, 0, 0, 1, 33, 0, 0, (int16 (*)(int32 *))vm_Memory_addMemoryArea, methodName478 },
  { 246, 4, 3, 0, 0, 1, 33, 0, 0, (int16 (*)(int32 *))vm_Memory_allocateInHeap, methodName479 },
  { 246, 1, 1, 10, 0, 0, 17, 0, vm_Memory_consumedMemory, 0, methodName480 },
  { 246, 1, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Memory_executeInHeap, methodName481 },
  { 246, 1, 1, 10, 0, 0, 17, 0, vm_Memory_getBase, 0, methodName482 },
  { 246, 1, 1, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))vm_Memory_getCurrentMemoryArea, methodName483 },
  { 246, 1, 1, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))vm_Memory_getHeapArea, methodName484 },
  { 246, 1, 1, 10, 0, 0, 33, 0, vm_Memory_getName, 0, methodName485 },
  { 246, 3, 3, 76, 0, 1, 33, 0, vm_Memory_getNextMemoryName, 0, methodName486 },
  { 246, 1, 1, 10, 0, 0, 17, 0, vm_Memory_getSize, 0, methodName487 },
  { 246, 3, 3, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_Memory_reportMemoryUsage, methodName488 },
  { 246, 2, 2, 11, 0, 1, 32, 0, vm_Memory_reset, 0, methodName489 },
  { 246, 2, 2, 11, 0, 1, 32, 0, vm_Memory_resize, 0, methodName490 },
  { 246, 1, 1, 0, 0, 0, 32, 0, 0, (int16 (*)(int32 *))vm_Memory_startMemoryAreaTracking, methodName491 },
  { 246, 1, 2, 0, 0, 1, 33, 0, 0, (int16 (*)(int32 *))vm_Memory_switchToArea, methodName492 },
  { 246, 4, 2, 84, 0, 0, 33, 0, vm_Memory_toString, 0, methodName493 },
  { 246, 2, 1, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Memory_updateMaxUsed, methodName494 },
  { 126, 1, 1, 8, 0, 0, 64, 0, vm_Monitor_init_, 0, methodName495 },
  { 126, 2, 2, 9, 0, 1, 32, 0, vm_Monitor_attach, 0, methodName496 },
  { 126, 0, 0, 0, 0, 1, 32, 0, 0, n_vm_Monitor_attachMonitor, methodName497 },
  { 126, 0, 0, 0, 0, 1, 33, 0, 0, n_vm_Monitor_getAttachedMonitor, methodName498 },
  { 126, 1, 1, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))vm_Monitor_getDefaultMonitor, methodName499 },
  { 126, 0, 0, 0, 0, 0, 32, 0, 0, 0, methodName500 },
  { 126, 1, 1, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Monitor_lock_, methodName501 },
  { 126, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Monitor_notify, methodName502 },
  { 126, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Monitor_notifyAll, methodName503 },
  { 126, 0, 0, 0, 0, 0, 32, 0, 0, 0, methodName504 },
  { 126, 1, 1, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Monitor_unlock_, methodName505 },
  { 126, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Monitor_wait, methodName506 },
  { 80, 2, 2, 16, 0, 1, 64, 0, vm_Process_ProcessExecutor_init_, 0, methodName507 },
  { 80, 2, 2, 0, 1, 0, 32, ex_vm_Process_ProcessExecutor_run, 0, (int16 (*)(int32 *))vm_Process_ProcessExecutor_run, methodName508 },
  { 150, 1, 1, 8, 0, 0, 64, 0, vm_Process_SP_init_, 0, methodName509 },
  { 150, 1, 2, 8, 0, 1, 64, 0, vm_Process_SP_init__, 0, methodName510 },
  { 214, 2, 1, 9, 0, 0, 64, 0, vm_Process_X86_32SP_init_, 0, methodName511 },
  { 214, 1, 2, 8, 0, 1, 64, 0, vm_Process_X86_32SP_init__, 0, methodName512 },
  { 16, 2, 1, 9, 0, 0, 64, 0, vm_Process_X86_64SP_init_, 0, methodName513 },
  { 16, 1, 2, 8, 0, 1, 64, 0, vm_Process_X86_64SP_init__, 0, methodName514 },
  { 36, 4, 3, 0, 0, 2, 64, 0, 0, (int16 (*)(int32 *))vm_Process_init_, methodName515 },
  { 36, 1, 1, 10, 0, 1, 33, 0, vm_Process_access_0, 0, methodName516 },
  { 36, 2, 2, 11, 0, 2, 32, 0, vm_Process_access_1, 0, methodName517 },
  { 36, 0, 0, 0, 0, 2, 32, 0, 0, n_vm_Process_executeWithStack, methodName518 },
  { 36, 2, 1, 21, 0, 0, 32, 0, vm_Process_initialize, 0, methodName519 },
  { 36, 0, 0, 0, 0, 2, 32, 0, 0, n_vm_Process_transfer, methodName520 },
  { 36, 2, 2, 0, 0, 1, 32, 0, 0, (int16 (*)(int32 *))vm_Process_transferTo, methodName521 },
  { 112, 1, 1, 8, 0, 0, 64, 0, vm_RealtimeClock_DefaultRealtimeClock_init_, 0, methodName522 },
  { 112, 1, 2, 8, 0, 1, 64, 0, vm_RealtimeClock_DefaultRealtimeClock_init__, 0, methodName523 },
  { 112, 1, 2, 9, 0, 1, 32, 0, vm_RealtimeClock_DefaultRealtimeClock_getCurrentTime, 0, methodName524 },
  { 112, 1, 1, 7, 0, 0, 17, 0, vm_RealtimeClock_DefaultRealtimeClock_getGranularity, 0, methodName525 },
  { 196, 1, 1, 8, 0, 0, 64, 0, vm_RealtimeClock_init_, 0, methodName526 },
  { 196, 1, 1, 7, 0, 0, 17, 0, vm_RealtimeClock_access_0, 0, methodName527 },
  { 196, 1, 1, 8, 0, 1, 17, 0, vm_RealtimeClock_access_1, 0, methodName528 },
  { 196, 0, 0, 0, 0, 0, 32, 0, 0, n_vm_RealtimeClock_awaitNextTick, methodName529 },
  { 196, 0, 0, 0, 0, 1, 32, 0, 0, 0, methodName530 },
  { 196, 0, 0, 0, 0, 0, 17, 0, 0, 0, methodName531 },
  { 196, 0, 0, 0, 0, 0, 17, 0, 0, n_vm_RealtimeClock_getNativeResolution, methodName532 },
  { 196, 0, 0, 0, 0, 1, 17, 0, 0, n_vm_RealtimeClock_getNativeTime, methodName533 },
  { 196, 3, 1, 0, 0, 0, 33, 0, 0, (int16 (*)(int32 *))vm_RealtimeClock_getRealtimeClock, methodName534 }
};

RANGE static const unsigned char string_constant_6[12] PROGMEM = { 0x2D,0x32,0x31,0x34,0x37,0x34,0x38,0x33,0x36,0x34,0x38,0x00 }; /* -2147483648 */
RANGE static const unsigned char long_constant_7[8] PROGMEM = { 0x80, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
RANGE static const unsigned char string_constant_8[21] PROGMEM = { 0x2D,0x39,0x32,0x32,0x33,0x33,0x37,0x32,0x30,0x33,0x36,0x38,0x35,0x34,0x37,0x37,
  0x35,0x38,0x30,0x38,0x00 }; /* -9223372036854775808 */
RANGE static const unsigned char string_constant_10[7] PROGMEM = { 0x64,0x6F,0x75,0x62,0x6C,0x65,0x00 }; /* double */
RANGE static const unsigned char string_constant_11[6] PROGMEM = { 0x66,0x6C,0x6F,0x61,0x74,0x00 }; /* float */
RANGE static const unsigned char string_constant_12[4] PROGMEM = { 0x69,0x6E,0x74,0x00 }; /* int */
RANGE static const unsigned char string_constant_20[5] PROGMEM = { 0x6C,0x6F,0x6E,0x67,0x00 }; /* long */
RANGE static const unsigned char long_constant_21[8] PROGMEM = { 0x0, 0x0, 0x0, 0x0, 0x7f, 0xff, 0xff, 0xff };
RANGE static const unsigned char long_constant_22[8] PROGMEM = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x64 };
RANGE static const unsigned char long_constant_23[8] PROGMEM = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0xa };
RANGE static const double double_constant_26 PROGMEM = -0.0;
RANGE static const unsigned char long_constant_27[8] PROGMEM = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x3, 0xff };
RANGE static const unsigned char long_constant_28[8] PROGMEM = { 0x7f, 0xf0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
RANGE static const unsigned char string_constant_29[2] PROGMEM = { 0x40,0x00 }; /* @ */
RANGE static const unsigned char string_constant_30[5] PROGMEM = { 0x6E,0x75,0x6C,0x6C,0x00 }; /* null */
RANGE static const unsigned char string_constant_31[28] PROGMEM = { 0x53,0x74,0x72,0x69,0x6E,0x67,0x20,0x69,0x6E,0x64,0x65,0x78,0x20,0x6F,0x75,0x74,
  0x20,0x6F,0x66,0x20,0x72,0x61,0x6E,0x67,0x65,0x3A,0x20,0x00 }; /* String index out of range:  */
RANGE static const unsigned char string_constant_32[3] PROGMEM = { 0x3A,0x20,0x00 }; /* :  */
RANGE static const unsigned char string_constant_33[3] PROGMEM = { 0x5B,0x5D,0x00 }; /* [] */
RANGE static const unsigned char string_constant_34[18] PROGMEM = { 0x28,0x74,0x68,0x69,0x73,0x20,0x43,0x6F,0x6C,0x6C,0x65,0x63,0x74,0x69,0x6F,0x6E,
  0x29,0x00 }; /* (this Collection) */
RANGE static const unsigned char string_constant_38[4] PROGMEM = { 0x20,0x3E,0x20,0x00 }; /*  >  */
RANGE static const unsigned char string_constant_39[15] PROGMEM = { 0x6E,0x75,0x6C,0x6C,0x20,0x70,0x61,0x72,0x61,0x6D,0x65,0x74,0x65,0x72,0x00 }; /* null parameter */
RANGE static const unsigned char string_constant_40[13] PROGMEM = { 0x74,0x69,0x6D,0x65,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x00 }; /* time is null */
RANGE static const unsigned char string_constant_41[15] PROGMEM = { 0x63,0x6C,0x6F,0x63,0x6B,0x20,0x6D,0x69,0x73,0x6D,0x61,0x74,0x63,0x68,0x00 }; /* clock mismatch */
RANGE static const unsigned char string_constant_44[31] PROGMEM = { 0x66,0x72,0x6F,0x6D,0x20,0x48,0x69,0x67,0x68,0x52,0x65,0x73,0x6F,0x6C,0x75,0x74,
  0x69,0x6F,0x6E,0x54,0x69,0x6D,0x65,0x20,0x3A,0x3A,0x20,0x73,0x65,0x74,0x00 }; /* from HighResolutionTime :: set */
RANGE static const unsigned char string_constant_45[12] PROGMEM = { 0x28,0x6D,0x73,0x2C,0x6E,0x73,0x29,0x20,0x3D,0x20,0x28,0x00 }; /* (ms,ns) = ( */
RANGE static const unsigned char string_constant_46[3] PROGMEM = { 0x2C,0x20,0x00 }; /* ,  */
RANGE static const unsigned char string_constant_47[2] PROGMEM = { 0x29,0x00 }; /* ) */
RANGE static const unsigned char string_constant_48[21] PROGMEM = { 0x20,0x20,0x20,0x4D,0x65,0x6D,0x6F,0x72,0x79,0x41,0x72,0x65,0x61,0x3A,0x20,0x74,
  0x68,0x72,0x6F,0x77,0x00 }; /*    MemoryArea: throw */
RANGE static const unsigned char string_constant_49[78] PROGMEM = { 0x74,0x68,0x72,0x6F,0x77,0x6E,0x20,0x66,0x72,0x6F,0x6D,0x20,0x4D,0x65,0x6D,0x6F,
  0x72,0x79,0x41,0x72,0x65,0x61,0x20,0x3A,0x3A,0x20,0x63,0x6F,0x6E,0x73,0x74,0x72,
  0x75,0x63,0x74,0x6F,0x72,0x20,0x3A,0x20,0x4F,0x75,0x74,0x20,0x6F,0x66,0x20,0x62,
  0x61,0x63,0x6B,0x69,0x6E,0x67,0x73,0x74,0x6F,0x72,0x65,0x20,0x65,0x78,0x63,0x65,
  0x70,0x74,0x69,0x6F,0x6E,0x3A,0x20,0x73,0x69,0x7A,0x65,0x3A,0x20,0x00 }; /* thrown from MemoryArea :: constructor : Out of backingstore exception: size:  */
RANGE static const unsigned char string_constant_50[20] PROGMEM = { 0x20,0x62,0x61,0x63,0x6B,0x69,0x6E,0x67,0x53,0x74,0x6F,0x72,0x65,0x53,0x69,0x7A,
  0x65,0x3A,0x20,0x00 }; /*  backingStoreSize:  */
RANGE static const unsigned char string_constant_51[8] PROGMEM = { 0x20,0x62,0x61,0x73,0x65,0x3A,0x20,0x00 }; /*  base:  */
RANGE static const unsigned char string_constant_52[19] PROGMEM = { 0x20,0x62,0x61,0x63,0x6B,0x69,0x6E,0x67,0x53,0x74,0x6F,0x72,0x65,0x45,0x6E,0x64,
  0x3A,0x20,0x00 }; /*  backingStoreEnd:  */
RANGE static const unsigned char string_constant_53[69] PROGMEM = { 0x74,0x68,0x72,0x6F,0x77,0x6E,0x20,0x66,0x72,0x6F,0x6D,0x20,0x4D,0x65,0x6D,0x6F,
  0x72,0x79,0x41,0x72,0x65,0x61,0x20,0x3A,0x3A,0x20,0x72,0x65,0x73,0x69,0x7A,0x65,
  0x4D,0x65,0x6D,0x20,0x3A,0x20,0x4F,0x75,0x74,0x20,0x6F,0x66,0x20,0x62,0x61,0x63,
  0x6B,0x69,0x6E,0x67,0x73,0x74,0x6F,0x72,0x65,0x20,0x65,0x78,0x63,0x65,0x70,0x74,
  0x69,0x6F,0x6E,0x20,0x00 }; /* thrown from MemoryArea :: resizeMem : Out of backingstore exception  */
RANGE static const unsigned char string_constant_54[28] PROGMEM = { 0x70,0x65,0x72,0x69,0x6F,0x64,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x20,0x6F,
  0x72,0x20,0x6E,0x6F,0x74,0x20,0x76,0x61,0x69,0x6C,0x64,0x00 }; /* period is null or not vaild */
RANGE static const unsigned char string_constant_55[30] PROGMEM = { 0x64,0x65,0x61,0x64,0x6C,0x69,0x6E,0x65,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,
  0x20,0x6F,0x72,0x20,0x6E,0x6F,0x74,0x20,0x76,0x61,0x69,0x6C,0x64,0x00 }; /* deadline is null or not vaild */
RANGE static const unsigned char string_constant_56[17] PROGMEM = { 0x70,0x72,0x69,0x6F,0x72,0x69,0x74,0x79,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x00 }; /* priority is null */
RANGE static const unsigned char string_constant_57[7] PROGMEM = { 0x4D,0x65,0x6D,0x54,0x72,0x6B,0x00 }; /* MemTrk */
RANGE static const unsigned char string_constant_58[16] PROGMEM = { 0x72,0x65,0x6C,0x65,0x61,0x73,0x65,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x00 }; /* release is null */
RANGE static const unsigned char string_constant_59[16] PROGMEM = { 0x73,0x74,0x6F,0x72,0x61,0x67,0x65,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x00 }; /* storage is null */
RANGE static const unsigned char string_constant_60[7] PROGMEM = { 0x50,0x76,0x74,0x4D,0x65,0x6D,0x00 }; /* PvtMem */
RANGE static const unsigned char string_constant_61[4] PROGMEM = { 0x49,0x6D,0x6D,0x00 }; /* Imm */
RANGE static const unsigned char string_constant_62[29] PROGMEM = { 0x65,0x78,0x65,0x63,0x75,0x74,0x65,0x49,0x6E,0x41,0x72,0x65,0x61,0x3A,0x20,0x6C,
  0x6F,0x67,0x69,0x63,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x00 }; /* executeInArea: logic is null */
RANGE static const unsigned char string_constant_63[31] PROGMEM = { 0x65,0x78,0x65,0x63,0x75,0x74,0x65,0x49,0x6E,0x41,0x72,0x65,0x61,0x3A,0x20,0x70,
  0x72,0x6F,0x63,0x65,0x73,0x73,0x20,0x69,0x73,0x20,0x6E,0x75,0x6C,0x6C,0x00 }; /* executeInArea: process is null */
RANGE static const unsigned char string_constant_64[22] PROGMEM = { 0x4D,0x53,0x53,0x65,0x74,0x2E,0x72,0x65,0x6D,0x6F,0x76,0x65,0x4D,0x53,0x4F,0x62,
  0x6A,0x65,0x63,0x74,0x20,0x00 }; /* MSSet.removeMSObject  */
RANGE static const unsigned char string_constant_65[31] PROGMEM = { 0x4D,0x53,0x53,0x65,0x74,0x2E,0x72,0x65,0x6D,0x6F,0x76,0x65,0x4D,0x53,0x4F,0x62,
  0x6A,0x65,0x63,0x74,0x3A,0x20,0x6D,0x73,0x43,0x6F,0x75,0x6E,0x74,0x20,0x00 }; /* MSSet.removeMSObject: msCount  */
RANGE static const unsigned char string_constant_66[10] PROGMEM = { 0x4D,0x69,0x73,0x73,0x69,0x6F,0x6E,0x3A,0x20,0x00 }; /* Mission:  */
RANGE static const unsigned char string_constant_67[10] PROGMEM = { 0x20,0x68,0x61,0x6E,0x64,0x6C,0x65,0x72,0x73,0x00 }; /*  handlers */
RANGE static const unsigned char string_constant_68[23] PROGMEM = { 0x4D,0x69,0x73,0x73,0x69,0x6F,0x6E,0x20,0x73,0x65,0x74,0x3A,0x20,0x74,0x6F,0x6F,
  0x20,0x73,0x6D,0x61,0x6C,0x6C,0x00 }; /* Mission set: too small */
RANGE static const unsigned char string_constant_69[7] PROGMEM = { 0x4D,0x69,0x73,0x4D,0x65,0x6D,0x00 }; /* MisMem */
RANGE static const unsigned char string_constant_70[7] PROGMEM = { 0x4D,0x53,0x2E,0x54,0x3A,0x20,0x00 }; /* MS.T:  */
RANGE static const unsigned char string_constant_71[14] PROGMEM = { 0x3B,0x20,0x23,0x4D,0x69,0x73,0x73,0x69,0x6F,0x6E,0x73,0x3A,0x20,0x00 }; /* ; #Missions:  */
RANGE static const unsigned char string_constant_72[13] PROGMEM = { 0x3B,0x20,0x6F,0x75,0x74,0x65,0x72,0x53,0x65,0x71,0x3A,0x20,0x00 }; /* ; outerSeq:  */
RANGE static const unsigned char string_constant_73[22] PROGMEM = { 0x4D,0x53,0x2E,0x73,0x65,0x71,0x4E,0x6F,0x74,0x69,0x66,0x79,0x20,0x6D,0x73,0x43,
  0x6F,0x75,0x6E,0x74,0x3A,0x00 }; /* MS.seqNotify msCount: */
RANGE static const unsigned char string_constant_74[26] PROGMEM = { 0x74,0x68,0x65,0x20,0x74,0x61,0x72,0x67,0x65,0x74,0x20,0x69,0x73,0x20,0x6E,0x6F,
  0x74,0x20,0x61,0x20,0x6C,0x6F,0x63,0x6B,0x3A,0x00 }; /* the target is not a lock: */
RANGE static const unsigned char string_constant_75[37] PROGMEM = { 0x20,0x20,0x20,0x20,0x4D,0x6F,0x6E,0x69,0x74,0x6F,0x72,0x2E,0x75,0x6E,0x6C,0x6F,
  0x63,0x6B,0x3A,0x20,0x55,0x50,0x53,0x2C,0x20,0x2D,0x20,0x6E,0x6F,0x74,0x20,0x6F,
  0x77,0x6E,0x65,0x72,0x00 }; /*     Monitor.unlock: UPS, - not owner */
RANGE static const unsigned char string_constant_76[18] PROGMEM = { 0x4E,0x6F,0x74,0x20,0x6F,0x77,0x6E,0x65,0x72,0x20,0x6F,0x6E,0x20,0x65,0x78,0x69,
  0x74,0x00 }; /* Not owner on exit */
RANGE static const unsigned char string_constant_77[55] PROGMEM = { 0x50,0x72,0x69,0x6F,0x72,0x69,0x74,0x79,0x46,0x72,0x61,0x6D,0x65,0x2E,0x61,0x64,
  0x64,0x50,0x72,0x6F,0x63,0x65,0x73,0x73,0x3A,0x20,0x61,0x6E,0x6F,0x74,0x68,0x65,
  0x72,0x20,0x73,0x63,0x68,0x65,0x64,0x75,0x6C,0x61,0x62,0x6C,0x65,0x20,0x6F,0x62,
  0x6A,0x65,0x63,0x74,0x20,0x3F,0x00 }; /* PriorityFrame.addProcess: another schedulable object ? */
RANGE static const unsigned char string_constant_78[2] PROGMEM = { 0x31,0x00 }; /* 1 */
RANGE static const unsigned char string_constant_79[15] PROGMEM = { 0x73,0x65,0x74,0x3A,0x20,0x74,0x6F,0x6F,0x20,0x73,0x6D,0x61,0x6C,0x6C,0x00 }; /* set: too small */
RANGE static const unsigned char string_constant_80[13] PROGMEM = { 0x53,0x63,0x6A,0x50,0x72,0x6F,0x63,0x65,0x73,0x73,0x3A,0x20,0x00 }; /* ScjProcess:  */
RANGE static const unsigned char string_constant_81[10] PROGMEM = { 0x2C,0x20,0x48,0x41,0x4E,0x44,0x4C,0x45,0x44,0x00 }; /* , HANDLED */
RANGE static const unsigned char string_constant_82[12] PROGMEM = { 0x53,0x63,0x6A,0x50,0x72,0x6F,0x63,0x65,0x73,0x73,0x3A,0x00 }; /* ScjProcess: */
RANGE static const unsigned char string_constant_83[9] PROGMEM = { 0x20,0x69,0x6E,0x64,0x65,0x78,0x3A,0x20,0x00 }; /*  index:  */
RANGE static const unsigned char long_constant_84[8] PROGMEM = { 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };
RANGE static const unsigned char string_constant_85[11] PROGMEM = { 0x49,0x4E,0x49,0x54,0x49,0x41,0x4C,0x49,0x5A,0x45,0x00 }; /* INITIALIZE */
RANGE static const unsigned char string_constant_86[8] PROGMEM = { 0x45,0x58,0x45,0x43,0x55,0x54,0x45,0x00 }; /* EXECUTE */
RANGE static const unsigned char string_constant_87[8] PROGMEM = { 0x43,0x4C,0x45,0x41,0x4E,0x55,0x50,0x00 }; /* CLEANUP */
RANGE static const unsigned char string_constant_88[4] PROGMEM = { 0x41,0x4C,0x4C,0x00 }; /* ALL */
RANGE static const unsigned char long_constant_94[8] PROGMEM = { 0x0, 0x0, 0x0, 0x0, 0x1f, 0x54, 0x5, 0x0 };
RANGE static const unsigned char string_constant_95[30] PROGMEM = { 0x53,0x43,0x4A,0x20,0x50,0x72,0x6F,0x63,0x65,0x73,0x73,0x20,0x65,0x78,0x65,0x63,
  0x75,0x74,0x69,0x6F,0x6E,0x20,0x65,0x72,0x72,0x6F,0x72,0x3A,0x20,0x00 }; /* SCJ Process execution error:  */
RANGE static const unsigned char string_constant_96[38] PROGMEM = { 0x4E,0x6F,0x20,0x6D,0x6F,0x72,0x65,0x20,0x49,0x6D,0x6D,0x6F,0x72,0x74,0x61,0x6C,
  0x4D,0x65,0x6D,0x6F,0x72,0x79,0x20,0x74,0x6F,0x20,0x70,0x72,0x69,0x6E,0x74,0x20,
  0x65,0x72,0x72,0x6F,0x72,0x00 }; /* No more ImmortalMemory to print error */
RANGE static const unsigned char string_constant_97[50] PROGMEM = { 0x50,0x6C,0x65,0x61,0x73,0x65,0x20,0x69,0x6E,0x63,0x72,0x65,0x61,0x73,0x65,0x20,
  0x49,0x6D,0x6D,0x6F,0x72,0x74,0x61,0x6C,0x4D,0x65,0x6D,0x6F,0x72,0x79,0x20,0x74,
  0x6F,0x20,0x73,0x65,0x65,0x20,0x66,0x75,0x6C,0x6C,0x20,0x72,0x65,0x70,0x6F,0x72,
  0x74,0x00 }; /* Please increase ImmortalMemory to see full report */
RANGE static const unsigned char string_constant_98[18] PROGMEM = { 0x53,0x63,0x68,0x65,0x64,0x75,0x6C,0x65,0x72,0x20,0x65,0x72,0x72,0x6F,0x72,0x3A,
  0x20,0x00 }; /* Scheduler error:  */
RANGE static const unsigned char string_constant_99[19] PROGMEM = { 0x4D,0x79,0x41,0x70,0x70,0x2E,0x67,0x65,0x74,0x53,0x65,0x71,0x75,0x65,0x6E,0x63,
  0x65,0x72,0x00 }; /* MyApp.getSequencer */
RANGE static const unsigned char string_constant_100[24] PROGMEM = { 0x2A,0x2A,0x20,0x4D,0x79,0x4D,0x69,0x73,0x73,0x69,0x6F,0x6E,0x2E,0x69,0x6E,0x69,
  0x74,0x69,0x61,0x6C,0x69,0x7A,0x65,0x00 }; /* ** MyMission.initialize */
RANGE static const unsigned char string_constant_101[27] PROGMEM = { 0x0A,0x4D,0x79,0x53,0x65,0x71,0x2E,0x67,0x65,0x74,0x4E,0x65,0x78,0x74,0x4D,0x69,
  0x73,0x73,0x69,0x6F,0x6E,0x3A,0x6E,0x75,0x6C,0x6C,0x00 }; /* \nMySeq.getNextMission:null */
RANGE static const unsigned char string_constant_102[22] PROGMEM = { 0x0A,0x4D,0x79,0x53,0x65,0x71,0x2E,0x67,0x65,0x74,0x4E,0x65,0x78,0x74,0x4D,0x69,
  0x73,0x73,0x69,0x6F,0x6E,0x00 }; /* \nMySeq.getNextMission */
RANGE static const unsigned char string_constant_103[25] PROGMEM = { 0x54,0x68,0x72,0x65,0x61,0x64,0x20,0x31,0x20,0x61,0x6C,0x6C,0x6F,0x63,0x61,0x74,
  0x69,0x6E,0x67,0x20,0x69,0x6E,0x3A,0x20,0x00 }; /* Thread 1 allocating in:  */
RANGE static const unsigned char string_constant_104[10] PROGMEM = { 0x54,0x31,0x20,0x46,0x61,0x69,0x6C,0x20,0x31,0x00 }; /* T1 Fail 1 */
RANGE static const unsigned char string_constant_105[16] PROGMEM = { 0x54,0x31,0x20,0x46,0x61,0x69,0x6C,0x20,0x32,0x2C,0x20,0x79,0x20,0x3D,0x20,0x00 }; /* T1 Fail 2, y =  */
RANGE static const unsigned char string_constant_106[7] PROGMEM = { 0x2C,0x20,0x78,0x20,0x3D,0x20,0x00 }; /* , x =  */
RANGE static const unsigned char string_constant_107[8] PROGMEM = { 0x54,0x68,0x72,0x65,0x61,0x64,0x31,0x00 }; /* Thread1 */
RANGE static const unsigned char string_constant_108[25] PROGMEM = { 0x54,0x68,0x72,0x65,0x61,0x64,0x20,0x32,0x20,0x61,0x6C,0x6C,0x6F,0x63,0x61,0x74,
  0x69,0x6E,0x67,0x20,0x69,0x6E,0x3A,0x20,0x00 }; /* Thread 2 allocating in:  */
RANGE static const unsigned char string_constant_109[10] PROGMEM = { 0x54,0x32,0x20,0x46,0x61,0x69,0x6C,0x20,0x31,0x00 }; /* T2 Fail 1 */
RANGE static const unsigned char string_constant_110[16] PROGMEM = { 0x54,0x32,0x20,0x46,0x61,0x69,0x6C,0x20,0x32,0x2C,0x20,0x79,0x20,0x3D,0x20,0x00 }; /* T2 Fail 2, y =  */
RANGE static const unsigned char string_constant_111[8] PROGMEM = { 0x54,0x68,0x72,0x65,0x61,0x64,0x32,0x00 }; /* Thread2 */
RANGE static const unsigned char string_constant_112[17] PROGMEM = { 0x73,0x6D,0x61,0x6C,0x6C,0x20,0x6F,0x62,0x6A,0x65,0x63,0x74,0x20,0x69,0x73,0x20,0x00 }; /* small object is  */
RANGE static const unsigned char string_constant_113[7] PROGMEM = { 0x20,0x62,0x79,0x74,0x65,0x73,0x00 }; /*  bytes */
RANGE static const unsigned char string_constant_114[18] PROGMEM = { 0x62,0x69,0x67,0x67,0x65,0x72,0x20,0x6F,0x62,0x6A,0x65,0x63,0x74,0x20,0x69,0x73,
  0x20,0x00 }; /* bigger object is  */
RANGE static const unsigned char long_constant_115[8] PROGMEM = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x7, 0xd2 };
RANGE static const unsigned char string_constant_116[62] PROGMEM = { 0x0A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x20,0x54,0x65,0x73,0x74,
  0x53,0x43,0x4A,0x54,0x68,0x72,0x65,0x61,0x64,0x4D,0x65,0x6D,0x6F,0x72,0x79,0x20,
  0x6D,0x61,0x69,0x6E,0x2E,0x62,0x65,0x67,0x69,0x6E,0x20,0x2A,0x2A,0x2A,0x2A,0x2A,
  0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x00 }; /* \n********** TestSCJThreadMemory main.begin ****************** */
RANGE static const unsigned char string_constant_117[60] PROGMEM = { 0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x20,0x54,0x65,0x73,0x74,0x53,0x43,
  0x4A,0x54,0x68,0x72,0x65,0x61,0x64,0x4D,0x65,0x6D,0x6F,0x72,0x79,0x20,0x6D,0x61,
  0x69,0x6E,0x2E,0x65,0x6E,0x64,0x20,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,
  0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x00 }; /* ********* TestSCJThreadMemory main.end ******************** */
RANGE static const unsigned char string_constant_118[2] PROGMEM = { 0x5B,0x00 }; /* [ */
RANGE static const unsigned char string_constant_119[2] PROGMEM = { 0x5D,0x00 }; /* ] */
RANGE static const unsigned char string_constant_120[10] PROGMEM = { 0x3A,0x20,0x73,0x69,0x7A,0x65,0x20,0x3D,0x20,0x00 }; /* : size =  */
RANGE static const unsigned char string_constant_121[14] PROGMEM = { 0x2C,0x20,0x6D,0x61,0x78,0x20,0x75,0x73,0x65,0x64,0x20,0x3D,0x20,0x00 }; /* , max used =  */
RANGE static const unsigned char string_constant_122[13] PROGMEM = { 0x42,0x61,0x63,0x6B,0x69,0x6E,0x67,0x53,0x74,0x6F,0x72,0x65,0x00 }; /* BackingStore */
RANGE static const unsigned char string_constant_123[8] PROGMEM = { 0x55,0x6E,0x6B,0x6E,0x6F,0x77,0x6E,0x00 }; /* Unknown */
RANGE static const unsigned char string_constant_124[5] PROGMEM = { 0x48,0x45,0x41,0x50,0x00 }; /* HEAP */
RANGE static const unsigned char string_constant_125[10] PROGMEM = { 0x0A,0x43,0x72,0x65,0x61,0x74,0x65,0x64,0x20,0x00 }; /* \nCreated  */
RANGE static const unsigned char string_constant_126[20] PROGMEM = { 0x20,0x6D,0x65,0x6D,0x6F,0x72,0x79,0x20,0x61,0x72,0x65,0x61,0x20,0x74,0x79,0x70,
  0x65,0x73,0x3A,0x00 }; /*  memory area types: */
RANGE static const unsigned char string_constant_127[27] PROGMEM = { 0x4D,0x61,0x78,0x20,0x62,0x61,0x63,0x6B,0x69,0x6E,0x67,0x20,0x73,0x74,0x6F,0x72,
  0x65,0x20,0x75,0x73,0x61,0x67,0x65,0x20,0x3D,0x20,0x00 }; /* Max backing store usage =  */
RANGE static const unsigned char string_constant_128[29] PROGMEM = { 0x4E,0x6F,0x20,0x63,0x72,0x65,0x61,0x74,0x65,0x64,0x20,0x6D,0x65,0x6D,0x6F,0x72,
  0x69,0x65,0x73,0x20,0x72,0x65,0x63,0x6F,0x72,0x64,0x65,0x64,0x00 }; /* No created memories recorded */
RANGE static const unsigned char string_constant_129[25] PROGMEM = { 0x4D,0x65,0x6D,0x6F,0x72,0x79,0x20,0x74,0x72,0x61,0x63,0x6B,0x69,0x6E,0x67,0x20,
  0x64,0x69,0x73,0x61,0x62,0x6C,0x65,0x64,0x00 }; /* Memory tracking disabled */
RANGE static const unsigned char string_constant_130[10] PROGMEM = { 0x2C,0x20,0x75,0x73,0x65,0x64,0x20,0x3D,0x20,0x00 }; /* , used =  */

RANGE static const ConstantInfo _constants[131] PROGMEM = {
 { CONSTANT_INTEGER, 16733190, 0, 0 },
 { CONSTANT_INTEGER, 16730490, 0, 0 },
 { CONSTANT_INTEGER, 16730480, 0, 0 },
 { CONSTANT_INTEGER, 16733186, 0, 0 },
 { CONSTANT_INTEGER, 100000000, 0, 0 },
 { CONSTANT_INTEGER, -2147483648, 0, 0 },
 { CONSTANT_STRING, 11, string_constant_6, 0 },
 { CONSTANT_LONG, 0, long_constant_7, 0 },
 { CONSTANT_STRING, 65556, string_constant_8, 0 },
 { CONSTANT_INTEGER, 2147483647, 0, 0 },
 { CONSTANT_STRING, 131078, string_constant_10, 0 },
 { CONSTANT_STRING, 196613, string_constant_11, 0 },
 { CONSTANT_STRING, 262147, string_constant_12, 0 },
 { CONSTANT_INTEGER, 99999, 0, 0 },
 { CONSTANT_INTEGER, 999999, 0, 0 },
 { CONSTANT_INTEGER, 9999999, 0, 0 },
 { CONSTANT_INTEGER, 99999999, 0, 0 },
 { CONSTANT_INTEGER, 999999999, 0, 0 },
 { CONSTANT_INTEGER, 65536, 0, 0 },
 { CONSTANT_INTEGER, 52429, 0, 0 },
 { CONSTANT_STRING, 327684, string_constant_20, 0 },
 { CONSTANT_LONG, 0, long_constant_21, 0 },
 { CONSTANT_LONG, 0, long_constant_22, 0 },
 { CONSTANT_LONG, 0, long_constant_23, 0 },
 { CONSTANT_CLASS, 97, 0, 0 },
 { CONSTANT_FLOAT, 0, 0, -0.0 },
 { CONSTANT_DOUBLE, 0, &double_constant_26, 0 },
 { CONSTANT_LONG, 0, long_constant_27, 0 },
 { CONSTANT_LONG, 0, long_constant_28, 0 },
 { CONSTANT_STRING, 393217, string_constant_29, 0 },
 { CONSTANT_STRING, 458756, string_constant_30, 0 },
 { CONSTANT_STRING, 524315, string_constant_31, 0 },
 { CONSTANT_STRING, 589826, string_constant_32, 0 },
 { CONSTANT_STRING, 655362, string_constant_33, 0 },
 { CONSTANT_STRING, 720913, string_constant_34, 0 },
 { CONSTANT_INTEGER, 2147483639, 0, 0 },
 { CONSTANT_CLASS, 2, 0, 0 },
 { CONSTANT_CLASS, 74, 0, 0 },
 { CONSTANT_STRING, 786435, string_constant_38, 0 },
 { CONSTANT_STRING, 851982, string_constant_39, 0 },
 { CONSTANT_STRING, 917516, string_constant_40, 0 },
 { CONSTANT_STRING, 983054, string_constant_41, 0 },
 { CONSTANT_INTEGER, 1000000, 0, 0 },
 { CONSTANT_INTEGER, -1000000, 0, 0 },
 { CONSTANT_STRING, 1048606, string_constant_44, 0 },
 { CONSTANT_STRING, 1114123, string_constant_45, 0 },
 { CONSTANT_STRING, 1179650, string_constant_46, 0 },
 { CONSTANT_STRING, 1245185, string_constant_47, 0 },
 { CONSTANT_STRING, 1310740, string_constant_48, 0 },
 { CONSTANT_STRING, 1376333, string_constant_49, 0 },
 { CONSTANT_STRING, 1441811, string_constant_50, 0 },
 { CONSTANT_STRING, 1507335, string_constant_51, 0 },
 { CONSTANT_STRING, 1572882, string_constant_52, 0 },
 { CONSTANT_STRING, 1638468, string_constant_53, 0 },
 { CONSTANT_STRING, 1703963, string_constant_54, 0 },
 { CONSTANT_STRING, 1769501, string_constant_55, 0 },
 { CONSTANT_STRING, 1835024, string_constant_56, 0 },
 { CONSTANT_STRING, 1900550, string_constant_57, 0 },
 { CONSTANT_STRING, 1966095, string_constant_58, 0 },
 { CONSTANT_STRING, 2031631, string_constant_59, 0 },
 { CONSTANT_STRING, 2097158, string_constant_60, 0 },
 { CONSTANT_STRING, 2162691, string_constant_61, 0 },
 { CONSTANT_STRING, 2228252, string_constant_62, 0 },
 { CONSTANT_STRING, 2293790, string_constant_63, 0 },
 { CONSTANT_STRING, 2359317, string_constant_64, 0 },
 { CONSTANT_STRING, 2424862, string_constant_65, 0 },
 { CONSTANT_STRING, 2490377, string_constant_66, 0 },
 { CONSTANT_STRING, 2555913, string_constant_67, 0 },
 { CONSTANT_STRING, 2621462, string_constant_68, 0 },
 { CONSTANT_STRING, 2686982, string_constant_69, 0 },
 { CONSTANT_STRING, 2752518, string_constant_70, 0 },
 { CONSTANT_STRING, 2818061, string_constant_71, 0 },
 { CONSTANT_STRING, 2883596, string_constant_72, 0 },
 { CONSTANT_STRING, 2949141, string_constant_73, 0 },
 { CONSTANT_STRING, 3014681, string_constant_74, 0 },
 { CONSTANT_STRING, 3080228, string_constant_75, 0 },
 { CONSTANT_STRING, 3145745, string_constant_76, 0 },
 { CONSTANT_STRING, 3211318, string_constant_77, 0 },
 { CONSTANT_STRING, 3276801, string_constant_78, 0 },
 { CONSTANT_STRING, 3342350, string_constant_79, 0 },
 { CONSTANT_STRING, 3407884, string_constant_80, 0 },
 { CONSTANT_STRING, 3473417, string_constant_81, 0 },
 { CONSTANT_STRING, 3538955, string_constant_82, 0 },
 { CONSTANT_STRING, 3604488, string_constant_83, 0 },
 { CONSTANT_LONG, 0, long_constant_84, 0 },
 { CONSTANT_STRING, 3670026, string_constant_85, 0 },
 { CONSTANT_STRING, 3735559, string_constant_86, 0 },
 { CONSTANT_STRING, 3801095, string_constant_87, 0 },
 { CONSTANT_STRING, 3866627, string_constant_88, 0 },
 { CONSTANT_INTEGER, 802000, 0, 0 },
 { CONSTANT_INTEGER, 100000, 0, 0 },
 { CONSTANT_INTEGER, 700000, 0, 0 },
 { CONSTANT_INTEGER, 200000, 0, 0 },
 { CONSTANT_INTEGER, 40000, 0, 0 },
 { CONSTANT_LONG, 0, long_constant_94, 0 },
 { CONSTANT_STRING, 3932189, string_constant_95, 0 },
 { CONSTANT_STRING, 3997733, string_constant_96, 0 },
 { CONSTANT_STRING, 4063281, string_constant_97, 0 },
 { CONSTANT_STRING, 4128785, string_constant_98, 0 },
 { CONSTANT_STRING, 4194322, string_constant_99, 0 },
 { CONSTANT_STRING, 4259863, string_constant_100, 0 },
 { CONSTANT_STRING, 4325402, string_constant_101, 0 },
 { CONSTANT_STRING, 4390933, string_constant_102, 0 },
 { CONSTANT_STRING, 4456472, string_constant_103, 0 },
 { CONSTANT_STRING, 4521993, string_constant_104, 0 },
 { CONSTANT_STRING, 4587535, string_constant_105, 0 },
 { CONSTANT_STRING, 4653062, string_constant_106, 0 },
 { CONSTANT_STRING, 4718599, string_constant_107, 0 },
 { CONSTANT_STRING, 4784152, string_constant_108, 0 },
 { CONSTANT_STRING, 4849673, string_constant_109, 0 },
 { CONSTANT_STRING, 4915215, string_constant_110, 0 },
 { CONSTANT_STRING, 4980743, string_constant_111, 0 },
 { CONSTANT_STRING, 5046288, string_constant_112, 0 },
 { CONSTANT_STRING, 5111814, string_constant_113, 0 },
 { CONSTANT_STRING, 5177361, string_constant_114, 0 },
 { CONSTANT_LONG, 0, long_constant_115, 0 },
 { CONSTANT_STRING, 5242941, string_constant_116, 0 },
 { CONSTANT_STRING, 5308475, string_constant_117, 0 },
 { CONSTANT_STRING, 5373953, string_constant_118, 0 },
 { CONSTANT_STRING, 5439489, string_constant_119, 0 },
 { CONSTANT_STRING, 5505033, string_constant_120, 0 },
 { CONSTANT_STRING, 5570573, string_constant_121, 0 },
 { CONSTANT_STRING, 5636108, string_constant_122, 0 },
 { CONSTANT_STRING, 5701639, string_constant_123, 0 },
 { CONSTANT_STRING, 5767172, string_constant_124, 0 },
 { CONSTANT_STRING, 5832713, string_constant_125, 0 },
 { CONSTANT_STRING, 5898259, string_constant_126, 0 },
 { CONSTANT_STRING, 5963802, string_constant_127, 0 },
 { CONSTANT_STRING, 6029340, string_constant_128, 0 },
 { CONSTANT_STRING, 6094872, string_constant_129, 0 },
 { CONSTANT_STRING, 6160393, string_constant_130, 0 }
};
Object* stringConstants[95] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

RANGE static const short _classInitializerSequence[13] PROGMEM = { 115, 65, 55, 8, 208, 410, 140, 247, 465, 447, 278, 301, 408 };

#if defined(PRE_INITIALIZE_EXCEPTIONS)
static ExceptionObject _exceptionObjects[17] = {
   { 1, 53, 0 }, 
   { 14, 24, 0 }, 
   { 28, 156, 0 }, 
   { 36, 43, 0 }, 
   { 38, 27, 0 }, 
   { 39, 35, 0 }, 
   { 48, 51, 0 }, 
   { 51, 125, 0 }, 
   { 62, 70, 0 }, 
   { 66, 124, 0 }, 
   { 70, 81, 0 }, 
   { 71, 25, 0 }, 
   { 81, 79, 0 }, 
   { 91, 71, 0 }, 
   { 117, 26, 0 }, 
   { 129, 157, 0 }, 
   { 142, 49, 0 }, 
};
#endif
const MethodInfo *methods = &_methods[0];
const ConstantInfo *constants;
#ifdef INVOKECLASSINITIALIZERS
const short* classInitializerSequence;
#endif
#if defined(PRE_INITIALIZE_EXCEPTIONS)
ExceptionObject* exceptionObjects;
#endif

unsigned char initMethods(void) {
   constants = &_constants[0];
#if defined(PRE_INITIALIZE_EXCEPTIONS)
   exceptionObjects = &_exceptionObjects[0];
#endif

#ifdef INVOKECLASSINITIALIZERS
   classInitializerSequence = &_classInitializerSequence[0];
#endif

   return 1;
}

