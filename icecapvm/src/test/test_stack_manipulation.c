#include <stdio.h>
#include "../ostypes.h"

/* gcc -O0 -DPC64 test_stack_manipulation.c ../x86_64_interrupt.s */

/* arm-none-linux-gnueabi-gcc -O0 -DPC64 test_stack_manipulation.c ../arm_interrupt.s */

extern pointer* get_stack_pointer(void);

#define STACKSIZE 8192

static uint32 stack[STACKSIZE];

extern pointer mainStackPointer;

static int fib(int n)
{
   if (n > 2)
   {
      return fib(n - 1) + fib(n - 2);
   }
   else
   {
      return 1;
   }
}

pointer dummy;

static void inter(void)
{
   mainStackPointer = (pointer) get_stack_pointer();
   set_stack_pointer((pointer*) & stack[STACKSIZE - 2]);
   printf("fib(10) = %d\n", fib(10));
   dummy = mainStackPointer;
   set_stack_pointer((pointer*) mainStackPointer);
   return;
}

int main(void)
{
   int x = 42;
   inter();
   printf("I'm back\n");
   if (x == 42)
   {
      printf("And all is well\n");
   }
   else
   {
      printf("Somebody has been sleeping in my bed?\n");
   }
   return 0;
}
