#include "../../../include/common/include/bitsdef.h"
#include "../../../include/common/include/map_480_gcc.h"
#include "../../../include/mac/startup/include/interrupts.h"
#include "ostypes.h"
#include "types.h"
#include "methods.h"

#define Px_1_SET 0x02
#define Px_1_RESET 0x02

static void delay(int time);

unsigned char cstack[0x400] __attribute__ ((section (".data")));
unsigned char istack[0x100] __attribute__ ((section (".data")));
unsigned char java_stack[JAVA_STACK_SIZE << 2] __attribute__ ((section (".data")));

int32* get_java_stack_base(int16 size) {
    return (int32*) &java_stack[0];
}

void blink(int time) {
    SET_FREEZE_REG |= FRZ_WDOG;

    P2_MODE_REG &= ~P2_1_MODE;
    P2_DIR_REG |= Px_1_DIR;

    while (1) {
        P2_SET_DATA_REG |= Px_1_SET;
        delay(time);
        P2_RESET_DATA_REG |= Px_1_RESET;
        delay(time);
    }
}

static void delay(int time) {
    volatile int i;
    for (i = 0; i < time; i++) {
        /* asm("nop"); */
    }
}

#ifdef USE_ROM_IMAGE
#else
unsigned char rom_writeable(void) {
    return 1;
}

unsigned char pheap[PHEAP_SIZE] PROGMEM;
#endif

static void handleUARTTXInterrupt(void);
static void handleTimer1Interrupt(void);

void printStr(const char* str);

void initNatives(void) {
    SET_FREEZE_REG |= FRZ_WDOG;

    init_interrupt_handling();

    CLK_AMBA_REG = (1 << 5) | (0 << 3) | (1 << 0);

    CLK_PER10_DIV_REG &= ~0x7;
    CLK_PER10_DIV_REG |= 0x2;

    P0_MODE_REG |= 0x0001;
    P0_DIR_REG |= 0x0003;

    set_interrupt_handler(21, handleUARTTXInterrupt);
    set_interrupt_handler(24, handleTimer1Interrupt);

    __set_PSR_I_bit();
    __enable_interrupt();
}

unsigned long strlen(const char *STR) {
    int count = 0;
    while (*STR++) {
        count++;
    }
    return count;
}

void* memcpy(void *OUT, const void *IN, unsigned long N) {
    void *r = OUT;
    unsigned char *src = (unsigned char *) IN;
    unsigned char *dst = (unsigned char *) OUT;

    while (N--) {
        *dst++ = *src++;
    }

    return r;
}

void *memset(void *DST, int C, unsigned long LENGTH) {
    void *r = DST;
    unsigned char *dst = (unsigned char *) DST;

    while (LENGTH--) {
        *dst++ = (unsigned char) C;
    }
    return r;
}

void initSegmentsFP(void) {
    ;
}

void initSegmentsPP(void) {
    ;
}

void initSegmentsServ(void) {
    ;
}

void PrintStatus(UByte StatusMessageLevel, char *msg) {
    ;
}

void mark_error(void) {
}

void mark_success(void) {
}

int32 isrMethodStack[50];

extern int16 vm_InterruptDispatcher_interrupt(int32 *fp);

static void handleUARTTXInterrupt(void) {
#if defined(VM_INTERRUPTDISPATCHER_INTERRUPT)
    if (VM_INTERRUPTDISPATCHER_INTERRUPT != -1) {
        isrMethodStack[0] = 21;
        vm_InterruptDispatcher_interrupt(&isrMethodStack[0]);
    }
#endif
}

static void handleTimer1Interrupt(void) {
#if defined(VM_INTERRUPTDISPATCHER_INTERRUPT)
    if (VM_INTERRUPTDISPATCHER_INTERRUPT != -1) {
        isrMethodStack[0] = 24;
        vm_InterruptDispatcher_interrupt(&isrMethodStack[0]);
    }
#endif
}


void sendbyte(unsigned char byte) {
    UART_RX_TX_REG = byte;
    UART_CTRL_REG |= 0x2;
    UART_CLEAR_TX_INT_REG = 0;
    while ((UART_CTRL_REG & 0x22) == 0x2) {
        ;
    }
}

#if 0
static void InitSystem(void) {
    asm("loadw	0xf4812:m,r0");
    /* BAT_CTRL_REG */
    asm("orw	$0x1:s,r0");
    asm("storw	r0,0xff4812:l");
    /* BAT_CTRL_REG */
    asm("loadw	0xf4812:m,r0");
    /* BAT_CTRL_REG */
    asm("andw	$0xfff3:m,r0");
    asm("storw	r0,0xff4812:l");
    /* BAT_CTRL_REG */
    asm("loadw	0xf4812:m,r0");
    /* BAT_CTRL_REG */
    asm("orw	$0x8:s,r0");
    asm("storw	r0,0xff4812:l");
    /* BAT_CTRL_REG */
    asm("loadw	0xf481c:m,r0");
    /* CP_CTRL_REG */
    asm("orw	$0x3:s,r0");
    asm("storw	r0,0xff481c:l");
    /* CP_CTRL_REG */
    asm("movw	$0x7f:m,r0");
    asm("storw	r0,0xff5806:l");
    /* CODEC_TONE_REG */
}
#endif

void writeLongToIO(int32 address, unsigned short offset, int32 msb, int32 lsb) {
    ; /* not relevant for CR16C */
}

void writeIntToIO(int32 address, unsigned short offset, int32 lsb) {
    ; /* not relevant for CR16C */
}

void writeShortToIO(int32 address, unsigned short offset, unsigned short lsb) {
    unsigned short *ptr;
    address += offset >> 3;
    ptr = (unsigned short*) (pointer) (address);
    *ptr = lsb;
}

void writeByteToIO(int32 address, unsigned short offset, unsigned char lsb) {
    unsigned char *ptr;
    address += offset >> 3;
    ptr = (unsigned char*) (pointer) (address);
    *ptr = lsb;
}

void writeBitToIO(int32 address, unsigned short offset, unsigned char bit) {

}

void readLongFromIO(int32 address, unsigned short offset, int32* msb, int32* lsb) {
    *msb = 0; /* not relevant for CR16C */
    *lsb = 0;
}

int32 readIntFromIO(int32 address, unsigned short offset) {
    return 0; /* not relevant for CR16C */
}

unsigned short readShortFromIO(int32 address, unsigned short offset) {
    unsigned short *ptr;
    address += offset >> 3;
    ptr = (unsigned short*) (pointer) (address);
    return *ptr;
}

unsigned char readByteFromIO(int32 address, unsigned short offset) {
    unsigned char *ptr;
    address += offset >> 3;
    ptr = (unsigned char*) (pointer) (address);
    return *ptr;
}

unsigned char readBitFromIO(int32 address, unsigned short offset) {
    return 0;
}

void init_memory_lock(void) {
}

void lock_memory(void) {
}

void unlock_memory(void) {
}

#if defined(MONITORENTER_OPCODE_USED) || defined(MONITOREXIT_OPCODE_USED) || defined(HANDLEMONITORENTEREXIT_USED) || defined(N_JAVA_LANG_STRINGBUFFER_APPEND)
void native_lock(Object* lockObj) {
    ;
}

void native_unlock(Object* lockObj) {
    ;
}
#endif

#if defined(N_JAVAX_SCJ_UTIL_VMFCT_GETNATIVERESOLUTION)
int16 n_javax_scj_util_VMFct_getNativeResolution(int32 *sp) {
    return -1;
}
#endif

/* getNativeTime
 * param : javax.safetycritical.AbsoluteTime
 * return: int
 */
#if defined(N_JAVAX_SCJ_UTIL_VMFCT_GETNATIVETIME)
int16 n_javax_scj_util_VMFct_getNativeTime(int32 *sp) {
    return -1;
}
#endif

