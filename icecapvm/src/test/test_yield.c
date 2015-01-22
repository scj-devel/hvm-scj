#include <stdio.h>
#include <stdlib.h>
#include "../ostypes.h"

/* gcc -g -O0 -DPC64 test_yield.c ../x86_64_interrupt.s */
/* arm-none-linux-gnueabi-gcc -O0 -DPC32 test_yield.c ../arm_interrupt.s */

extern void _yield(void);
extern void set_stack_pointer(pointer* new_sp);
extern pointer* get_stack_pointer(void);

#define STACKSIZE 8192
static int32 stack1[STACKSIZE];
static int32 stack2[STACKSIZE];

int thread1ready = 0;
int thread2ready = 0;

pointer stackPointer;
pointer thread1sp;
pointer thread2sp;
pointer nextthread;
pointer* mainStack;

void _transfer(void) {
   if (stackPointer == 0)
   {
      printf("stackPointer not set!\n");
      exit(1);
   }
   if (thread1sp == 0)
   {
      thread1sp = stackPointer;
      return;
   }
   if (thread2sp == 0)
   {
      thread2sp = stackPointer;
      return;
   }

   if (nextthread == thread1sp)
   {
      thread2sp = stackPointer;
   }
   else if (nextthread == thread2sp)
   {
      thread1sp = stackPointer;
   }
   else if (nextthread == 0)
   {
      nextthread = thread1sp;
   }
   else 
   {
      printf("Unexpected next thread!\n");
      exit(1);      
   }

   stackPointer = nextthread;
}

static void thread1(void)
{
   int count = 0;
   while (count < 10)
   {
      printf("thread1\n");
      nextthread = thread2sp;
      _yield();
      count++;
   }
}

static void thread2(void)
{
   int count = 0;
   while (count < 10)
   {
      printf("thread2\n");
      nextthread = thread1sp;
      _yield();
      count++;
   }
}

static void startThread1(void)
{
   int started = 0;  
   _yield();
   if (started == 0)
   {
      started = 1;
   }
   else
   {
      thread1();
      printf("thread1 done\n");
      exit(1);
   }
}

static void startThread2(void)
{
   int started = 0;  
   _yield();
   if (started == 0)
   {
      started = 1;
   }
   else
   {
      thread2();
      printf("thread2 done\n");
      exit(1);
   }
}

int main(void)
{
   stackPointer = 0;
   nextthread = 0;
   mainStack = get_stack_pointer();
   set_stack_pointer((pointer*) & stack1[STACKSIZE - 2]);
   if (stackPointer == 0)
   {
      startThread1();
      set_stack_pointer((pointer*) & stack2[STACKSIZE - 2]);
      startThread2();
      set_stack_pointer((pointer*)mainStack);
      _yield();
   }
}

  

