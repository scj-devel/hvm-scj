/*
 * test_process.c
 *
 *  Created on: Oct 16, 2012
 *      Author: sek
 *
 *      PC: gcc -Wall -pedantic -g -O0 -DPC64 -DPRINTFSUPPORT -DVM_HVMPROCESSSEQUENCER_INTERRUPT ../icecapvm.c ../natives_allOS.c ../natives_i86.c ../print.c ../x86_64_interrupt.s test_process.c
 */
#include "../types.h"
#include "../ostypes.h"

extern void _yield(void);
extern void printStr(const char* str);

#define STACK_SIZE 768

extern int32 *schedulerStack;
extern int16 schedulerStackSize;
extern int32* processInitialStack;
extern int16 processInitialStackSize;
extern pointer initialStackPointer;

extern pointer stackPointer;

static unsigned char scheduler_stack[STACK_SIZE];
static unsigned char thread1_stack[STACK_SIZE];
static unsigned char thread2_stack[STACK_SIZE];

static pointer thread1StackPointer;
static pointer thread2StackPointer;
static pointer mainStackPointer;

static int testCount;

extern const MethodInfo* getMainMethod(void) {
    return 0;
}

void initGC(void) {
    ;
}

unsigned char initClasses(void) {
    ;
}

unsigned char initMethods(void) {
    ;
}

const char* getClassName(unsigned short classIndex) {
    return 0;
}

const char* getMethodName(unsigned short methodIndex) {
    return 0;
}

void handleException(unsigned short classIndex) {
    ;
}

#define STARTTHREAD1 10
#define STARTTHREAD2 11
#define SCHEDULETHREAD1 12
#define SCHEDULETHREAD2 13

static int state;

int16 enterMethodInterpreter(const MethodInfo* method, int32* sp) {
    schedulerStack = (int32 *) &scheduler_stack[0];
    *(schedulerStack + 1) = 0;
    schedulerStackSize = STACK_SIZE;

    state = STARTTHREAD1;

    testCount = 0;

    printStr("start\n");
    _yield();

    if (testCount == 29)
    {
        printStr("SUCCESS!\n");
    }
    else
    {
        printStr("FAILURE\n");
    }
    return -1;
}



static void thread1(void) {
    while (1) {
        testCount = testCount + 2;
        printStr("Thread1\n");
        _yield();
    }
}

static void thread2(void) {
    while (1) {
        testCount = testCount + 1;
        printStr("Thread2\n");
        _yield();
    }
}

void (*nextThread)(void);

int16 vm_HVMProcessSequencer_interrupt(int32 *fp, int32 this) {
    static int count = 0;
    switch (state) {
    case STARTTHREAD1:
        mainStackPointer = stackPointer;
        nextThread = thread1;
        processInitialStack = (int32*)&thread1_stack[0];
        processInitialStackSize = STACK_SIZE;
        state = STARTTHREAD2;
        stackPointer = initialStackPointer;
        printStr("start 1\n");
        break;
    case STARTTHREAD2:
        thread1StackPointer = stackPointer;
        nextThread = thread2;
        processInitialStack = (int32*)&thread2_stack[0];
        processInitialStackSize = STACK_SIZE;
        state = SCHEDULETHREAD1;
        stackPointer = initialStackPointer;
        printStr("start 2\n");
        break;
    case SCHEDULETHREAD1:
        thread2StackPointer = stackPointer;
        state = SCHEDULETHREAD2;
        stackPointer = thread1StackPointer;
        printStr("switch to 1\n");
        break;
    case SCHEDULETHREAD2:
        count++;
        thread1StackPointer = stackPointer;
        state = SCHEDULETHREAD1;
        stackPointer = thread2StackPointer;
        printStr("switch to 2\n");
        break;
    }
    if (count == 9)
    {
        stackPointer = mainStackPointer;
        printStr("switch to main\n");
    }
    return -1;
}

int16 vm_HVMProcessSequencer_dispatchRunnable(int32 *fp) {
    printStr("dispatch\n");
    nextThread();
    return -1;
}
