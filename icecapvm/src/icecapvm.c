#include "types.h"
#include "ostypes.h"
#include "methods.h"
#include "classes.h"

/* compile (PC): gcc -Wall -pedantic -g -O0 -DPC64 -DPRINTFSUPPORT classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c x86_64_interrupt.s -lpthread */

/* compile (PC with flash): gcc -Wall -pedantic -g -O0 -DPC64 -DPRINTFSUPPORT -DUSE_ROM_IMAGE classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c rom_access.c rom.c */

/* compile (AVR): avr-gcc -Wall -Os -fpack-struct -fshort-enums -std=gnu99 -funsigned-char -funsigned-bitfields -mmcu=atmega2560 -DF_CPU=10000000UL classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_avr.c rom_heap.c rom_access.c -o a.elf */

/* compile (PC, SCJ): gcc -Wall -pedantic -g -O0 -DPC64 -DPRINTFSUPPORT -DJAVA_HEAP_SIZE=131072 classes.c  icecapvm.c  methodinterpreter.c  methods.c gc.c natives_allOS.c natives_i86.c rom_heap.c allocation_point.c rom_access.c native_scj.c print.c -lpthread x86_64_interrupt.s -lrt */

#include "methodinterpreter.h"
#include "gc.h"

#if defined(JAVA_LANG_THROWABLE_INIT_)
extern void handleException(unsigned short classIndex);
#endif
extern void mark_error(void);
extern void mark_success(void);
extern unsigned char init_vm(void);
extern int32* get_java_stack_base(int16 size);
extern int16 initializeExceptions(int32* sp);

#if defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
extern void start_system_tick(void);
extern void stop_system_tick(void);
#endif

#if defined(INVOKECLASSINITIALIZERS)
extern int16 invokeClassInitializers(int32* sp);
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
int16 initializeConstants(int32* sp);
#endif

#if defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
pointer mainStackPointer;
extern pointer stackPointer;
extern void set_stack_pointer();
extern pointer* get_stack_pointer(void);
#endif

#if defined(REPORTCYCLES)
extern void papi_mark(void);
extern void papi_start(void);
#endif

#if defined(ENABLE_DEBUG)
void disconnectFromDebugger(void);
void connectToDebugger(void);
void sendStartEvent(void);
unsigned char awaitCommandFromDebugger(int32* fp, unsigned short methodNumber, unsigned short pc);
#endif

#define	ERROR 1
#define SUCCESS 0

#if defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
static Object temp;
static int32* mainMethodJavaStack;
#endif

extern void init_compiler_specifics(void);

int run_vm(void) {
#if !defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
	Object temp;
	int32* mainMethodJavaStack;
#endif

	int16 execp = 0;
	/* Required for certain compilers. */
	init_compiler_specifics();

	/* Function below allocates the initial heap. This is done in
	 * initDefaultRAMAllocationPoint in allocation_point.c
	 */
	init_vm();

#if defined(ENABLE_DEBUG)
	connectToDebugger();
	sendStartEvent();
	while (awaitCommandFromDebugger(0, 0, 0) != RESUME_EVENT) {;}
#endif

	/* Allocating the main stack is delegated to the target specific function
	 * 'get_java_stack_base'. On some architectures/environments it is located
	 * at fixed positions in certain compiler specific sections. The implementor
	 * can allocate the stack in the heap if so desired.
	 * */
	mainMethodJavaStack = get_java_stack_base(JAVA_STACK_SIZE);

#if defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
	/* If more threads are started we give the main thread a new C stack pointer.
	 * In case of no other threads running the main thread just inherits the
	 * current C stack.
	 *
	 * In this case we save the current C stack so we may restore it later. This
	 * is required to terminate the process properly.
	 */
	mainStackPointer = (pointer) get_stack_pointer();

	/* mainMethodJavaStack contains both Java and C stack. Java stack grows
	 * upwards from the beginning, C stack downwards from the end. */
	stackPointer = (pointer) &mainMethodJavaStack[JAVA_STACK_SIZE - 2];
	/* 'set_stack_pointer' sets the C stack */
	stackPointer = (pointer) & mainMethodJavaStack[JAVA_STACK_SIZE - 2];
	set_stack_pointer();
#endif

#if defined(REPORTCYCLES)
	papi_start();
	papi_mark();
	papi_mark();
	papi_mark();
	papi_mark();
	papi_mark();
	papi_mark();
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
	execp = initializeConstants(mainMethodJavaStack);
	if (execp == -1) {
#endif

		execp = initializeExceptions(mainMethodJavaStack);
		if (execp == -1) {

#if defined(INVOKECLASSINITIALIZERS)
			execp = invokeClassInitializers(mainMethodJavaStack);
			if (execp == -1) {
#endif
				/* This is only for testing. All tests will write 0 (null) to
				 * '*mainMethodJavaStack' if the test is successful.
				 */
				*mainMethodJavaStack = (int32) (pointer) &temp;

#if defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
				start_system_tick();
#endif

#if defined(DEVICES_SYSTEM_INITIALIZESYSTEMCLASS)
				execp = enterMethodInterpreter(
				DEVICES_SYSTEM_INITIALIZESYSTEMCLASS, mainMethodJavaStack);
				if (execp == -1) {
#endif
						/* Start the VM */
						execp = enterMethodInterpreter(mainMethodIndex,
								mainMethodJavaStack);
#if defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
						stop_system_tick();
#endif
#if defined(DEVICES_SYSTEM_INITIALIZESYSTEMCLASS)
				}
#endif
			}
			/* TODO: use executeWithStack instead */
#if defined(INVOKECLASSINITIALIZERS)
		}
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
	}
#endif

#if defined(REPORTCYCLES)
	papi_mark();
#endif

	mark_error();

	if (execp >= 0) {
#if defined(JAVA_LANG_THROWABLE_INIT_)
		handleException(execp);
#endif
#if defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
		/* Restore C stack pointer. Otherwise we could not return from here properly */
		stackPointer = (pointer) mainStackPointer;
		set_stack_pointer();
#endif
		return ERROR;
	}

#if defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
	/* Restore C stack pointer. Otherwise we could not return from here properly */
	stackPointer = (pointer) mainStackPointer;
	set_stack_pointer();
#endif

#if defined(ENABLE_DEBUG)
	disconnectFromDebugger();
#endif

	if (*mainMethodJavaStack) {
		return ERROR;
	} else {
		mark_success();
		return SUCCESS;
	}

	return 0;
}

/* TODO: Use the Process concept to handle main and main processes */
#ifndef EXCLUDEMAIN
int main(void) {
	return run_vm();
}
#endif

