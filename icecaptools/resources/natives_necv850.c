#include "ostypes.h"
#include "types.h"
#include <typedef.h> 
#include "Rtos_if.h"  /*For tasks*/
#include "LED_Driver.h"

#include "methodinterpreter.h"
#include "gc.h"
#include "methods.h"

#define MODBUS_HVM_TASK_TIME 	500

extern const MethodInfo *methods;
extern int16 enterMethodInterpreter(unsigned short methodNumber, int32* sp);
extern unsigned char initMethods(void);
extern unsigned char initClasses(void);
extern void init_compiler_specifics(void);
extern void initNatives(void);

static int32 dispatchStack[50];

unsigned char init_vm(void) {
    init_compiler_specifics();
    initNatives();
    initGC();
    initClasses();
    initMethods();
    resetGC();
}

unsigned char dispatch_vm(unsigned char taskId, unsigned char tag) {
    dispatchStack[0] = taskId;
    dispatchStack[1] = tag;

    enterMethodInterpreter(TASKS_MODBUSDISPATCHER_DISPATCH, &dispatchStack[0]);

    return dispatchStack[0];
}

EXTERN void HVMExecutor(void) {
	if (dispatch_vm(TASK_ID_HVM, 0)) {
#if defined(__MODBUS__) || defined(__MODBUS_GSM__)
		_WAIT_T(TASK_ID_HVM, MilliSec2T_Tick(MODBUS_HVM_TASK_TIME));
#endif
	} else {
		_STOP_TASK(TASK_ID_HVM);
	}
}

/* InitHVMExecutor */
EXTERN void InitHVMExecutor(void) {
	init_vm();

#if defined(__MODBUS__) || defined(__MODBUS_GSM__)
	_RUN_TASK(TASK_ID_HVM);
#endif
}

void writeLongToIO(int32 address, unsigned short offset, int32 msb, int32 lsb) {
}

void writeIntToIO(int32 address, unsigned short offset, int32 lsb) {
}

void writeShortToIO(int32 address, unsigned short offset, unsigned short lsb) {
}

void writeByteToIO(int32 address, unsigned short offset, unsigned char lsb) {
}

unsigned short set_bit[3] = { 0x3FC0, /* SET1    7,(0xFFFF)[zero] */
0xFFFF, 0x007F /* JMP     [lp] */
};

unsigned short clear_bit[3] = { 0xBFC0, 0xFFFF, /* CLR1    7,(0xFFFF)[zero] */
0x007F /* JMP     [lp] */
};

#define MASK 0xC7FF

void writeBitToIO(int32 address, unsigned short offset, unsigned char bit) {
	uint16 opcode;
	unsigned short *code;
	void (*f)(void);

	if (offset < 8) {
		if (bit) {
			code = set_bit;
		} else {
			code = clear_bit;
		}

		f = (void(*)(void)) code;
		opcode = code[0];
		opcode &= 0xC7FF;
		opcode |= (offset << 11);
		code[1] = (uint16) address;
		f();
	}
}

void readLongFromIO(int32 address, unsigned short offset, int32* msb,
		int32* lsb) {
}

int32 readIntFromIO(int32 address, unsigned short offset) {
	return 0;
}

unsigned short readShortFromIO(int32 address, unsigned short offset) {
	return 0;
}

unsigned char readByteFromIO(int32 address, unsigned short offset) {
	return 0;
}

unsigned char readBitFromIO(int32 address, unsigned short offset) {
	return 0;
}

void printStr(char* str) {
	;
}

void init_compiler_specifics(void) {
	/* Nothing to initialize */
}

void initNatives(void) {
	;
}

unsigned char rom_writeable(void) {
	return 0;
}

