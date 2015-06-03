#include "types.h"
#include "rom_heap.h"
#include "gc.h"
#include "methods.h"
#include "classes.h"

#include <stdio.h>

static unsigned char java_stack[JAVA_STACK_SIZE << 2];

int32* get_java_stack_base(int16 size) {
    return (int32*) &java_stack[0];
}

void initNatives(void) {
}

void sendbyte(unsigned char byte) {
    printf("%c", byte);
    fflush(stdout);
}

void mark_error(void) {
}

void mark_success(void) {
    printf("SUCCESS\n");
}

#if defined(N_DEVICES_SYSTEM_BLINK)
int32 n_devices_System_blink(int32 *sp) {
    return -1;
}
#endif

void init_compiler_specifics(void) {
    /* Nothing to initialize */
}

/* for testing */
#if defined(PUTHWFIELD_OPCODE_USED) || defined(WRITELONGTOIO_USED) || defined(WRITEINTTOIO_USED) || defined(WRITESHORTTOIO_USED) || defined(WRITEBYTETOIO_USED) || defined(WRITEBITTOIO_USED) || defined(GETHWFIELD_OPCODE_USED) || defined(READLONGFROMIO_USED) || defined(READINTFROMIO_USED) || defined(READSHORTFROMIO_USED) || defined(READBYTEFROMIO_USED) || defined(READBITFROMIO_USED)

static pointer saddress;
static unsigned short soffset;
int32 smsb;
static int32 slsb;
#endif

#if defined(PUTHWFIELD_OPCODE_USED) || defined(WRITELONGTOIO_USED) || defined(WRITEINTTOIO_USED) || defined(WRITESHORTTOIO_USED) || defined(WRITEBYTETOIO_USED) || defined(WRITEBITTOIO_USED)

#include <stdio.h>
void writeLongToIO(pointer address, unsigned short offset, int32 msb, int32 lsb) {
    saddress = address;
    soffset = offset;
    slsb = lsb;
    smsb = msb;
    printf("write long\n");
}

void writeIntToIO(pointer address, unsigned short offset, int32 lsb) {
    saddress = address;
    soffset = offset;
    slsb = lsb;
    printf("write int\n");
}

void writeShortToIO(pointer address, unsigned short offset, unsigned short lsb) {
#if defined(TEST_TESTHWOBJECT_MAIN)
    saddress = address;
    soffset = offset;
    slsb = lsb;
    printf("write short\n");
#else
    *(int16*) (address + (offset >> 3)) = lsb;
#endif
}

void writeByteToIO(pointer address, unsigned short offset, unsigned char lsb) {
    saddress = address;
    soffset = offset;
    slsb = lsb;
    printf("write byte\n");
}

void writeBitToIO(pointer address, unsigned short offset, unsigned char bit) {
    saddress = address;
    soffset = offset;
    slsb = bit;
    printf("write bit\n");
}
#endif

#if defined(GETHWFIELD_OPCODE_USED) || defined(READLONGFROMIO_USED) || defined(READINTFROMIO_USED) || defined(READSHORTFROMIO_USED) || defined(READBYTEFROMIO_USED) || defined(READBITFROMIO_USED)
void readLongFromIO(pointer address, unsigned short offset, int32* msb, int32* lsb) {
#if defined(TEST_TESTHWOBJECT_MAIN)
    if (saddress == address) {
        if (soffset == offset) {
            *msb = smsb;
            *lsb = slsb;
        }
    }
#else
    offset = offset >> 3;
    *lsb = *(int32*) (address + offset);
    *msb = *(int32*) (address + offset + 4);
#endif
}

int32 readIntFromIO(pointer address, unsigned short offset) {
#if defined(TEST_TESTHWOBJECT_MAIN)
    if (saddress == address) {
        if (soffset == offset) {
            return slsb;
        }
    }
    return 0;
#else
    return *(int32*) (address + (offset >> 3));
#endif
}

int16 readShortFromIO(pointer address, unsigned short offset) {
#if defined(TEST_TESTHWOBJECT_MAIN)
    if (saddress == address) {
        if (soffset == offset) {
            return slsb;
        }
    }
    return 0;
#else
    return *(int16*) (address + (offset >> 3));
#endif
}

int8 readByteFromIO(pointer address, unsigned short offset) {
#if defined(TEST_TESTHWOBJECT_MAIN)
    if (saddress == address) {
        if (soffset == offset) {
            return slsb;
        }
    }
    return 0;
#else
    return *(int8*) (address + (offset >> 3));
#endif
}

unsigned char readBitFromIO(pointer address, unsigned short offset) {
    if (saddress == address) {
        if (soffset == offset) {
            return slsb;
        }
    }
    return 0;
}
#endif

#if defined(N_DEVICES_SYSTEM_SNAPSHOT)
int32 n_devices_System_snapshot(int32 *sp) {
    return -1;
}
#endif

#ifdef USE_ROM_IMAGE
#else
unsigned char rom_writeable(void) {
    return 1;
}

unsigned char pheap[PHEAP_SIZE] PROGMEM;
#endif

#ifdef N_DEVICES_SYSTEM_LOCKROM
int32 n_devices_System_lockROM(int32 *sp) {
#ifdef FLASHSUPPORT
#ifdef USE_ROM_IMAGE
#else
    FILE* fp;
#if defined(WIN32) && defined(_DEBUG)
    fopen_s(&fp, "rom.c", "w");
#else
    fp = fopen("rom.c", "w");
#endif

    if (fp != 0) {
        uint16 rom_size = get_rom_heap_size();
        uint16 address;
        uint8 col_count = 0;

        fprintf(fp, "#include \"types.h\"\n\n");

        fprintf(fp, "unsigned char pheap[%d] PROGMEM = {\n  ", rom_size);

        for (address = 0; address < rom_size; address++) {

            uint8 next_byte;
            if (address < rom_size) {
                next_byte = get_rom_heap_address(address);
            } else {
                next_byte = 0;
            }

            fprintf(fp, "0x%x", next_byte);
            if (address + 1 < rom_size) {
                if (col_count < 8) {
                    fprintf(fp, ", ");
                } else {
                    fprintf(fp, ",\n  ");
                    col_count = 0;
                }
                col_count++;
            } else {
                fprintf(fp, "\n};\n\n");
            }
        }

        fprintf(fp, "unsigned char rom_writeable(void)\n");
        fprintf(fp, "{\n");
        fprintf(fp, "    return 0;\n");
        fprintf(fp, "}\n");

        fclose(fp);
    } else {
        perror("failed to open rom file");
    }
#endif
#endif
    return -1;
}
#endif

#ifndef __CYGWIN__

#if defined(N_TEST_TESTCAS_ATOMICREFERENCE_CAS)
extern void* getPointer(int32 val);

int32 n_test_TestCAS_AtomicReference_cas(int32 *sp) {
    unsigned char* target;
    int offset;
    unsigned int* field;
    unsigned int expect;
    unsigned int update;

    offset = sp[2];
    target = (unsigned char*) getPointer(sp[0]);
    field = (unsigned int*) (target + offset);

    expect = (unsigned int) sp[3];
    update = (unsigned int) sp[4];

    sp[0] = __sync_bool_compare_and_swap(field, expect, update);

    return -1;
}
#endif
#endif

#if defined(N_THREAD_JAVALANGTHREADSCHEDULER_JAVALANGTHREADMONITOR_INITIALIZEMUTEX)
#include <pthread.h>

#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
#include <errno.h>
#endif

int16 n_thread_JavaLangThreadScheduler_JavaLangThreadMonitor_initializeMutex(int32 *sp) {
    struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* monitor = HEAP_REF((struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* )(pointer )sp[0], struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c*);

    pthread_mutex_t *mutex = (pthread_mutex_t *) gc_allocateObject(sizeof(pthread_mutex_t) - sizeof(Object), 0);

    pthread_cond_t *cond = (pthread_cond_t *) gc_allocateObject(sizeof(pthread_cond_t) - sizeof(Object), 0);

    /*Behaviours for multicore version*/
	#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
    	pthread_mutexattr_t mattr;
        pthread_mutexattr_init(&mattr);

        int ret = pthread_mutexattr_settype(&mattr, PTHREAD_MUTEX_ERRORCHECK);
        if(ret != 0){
            printf("pthread_mutexattr_settype error %d\n", ret);
        }

        ret = pthread_mutexattr_setprioceiling(&mattr, (uint32)sp[1]);
        if(ret != 0){
            printf("pthread_mutexattr_setprioceiling error %d\n", ret);
        }

        if(pthread_mutex_init((pthread_mutex_t *) HEAP_REF(mutex, pthread_mutex_t *), &mattr) != 0){
			printf("initmutex errno: %d.\n",errno);
			return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
		}

		if(pthread_cond_init((pthread_cond_t *) HEAP_REF(cond, pthread_cond_t *), 0)!= 0){
			printf("initcond errno: %d.\n",errno);
			return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
		}
	#else
		pthread_mutex_init((pthread_mutex_t *) HEAP_REF(mutex, pthread_mutex_t *), 0);

		pthread_cond_init((pthread_cond_t *) HEAP_REF(cond, pthread_cond_t *), 0);
	#endif


    monitor->mutex_f = (uint32) (pointer) mutex;
    monitor->conditionVariable_f = (uint32) (pointer) cond;

    return -1;
}
#endif

#if defined(N_THREAD_JAVALANGTHREADSCHEDULER_JAVALANGTHREADMONITOR_ACQUIREMUTEX)
int16 n_thread_JavaLangThreadScheduler_JavaLangThreadMonitor_acquireMutex(int32 *sp) {
    struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* monitor = HEAP_REF((struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* )(pointer )sp[0], struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c*);

    pthread_mutex_t *mutex = (pthread_mutex_t *) (pointer) monitor->mutex_f;

    pthread_mutex_lock((pthread_mutex_t *) HEAP_REF(mutex, pthread_mutex_t *));

    return -1;
}
#endif

#if defined(N_THREAD_JAVALANGTHREADSCHEDULER_JAVALANGTHREADMONITOR_RELEASEMUTEX)
int16 n_thread_JavaLangThreadScheduler_JavaLangThreadMonitor_releaseMutex(int32 *sp) {
    struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* monitor = HEAP_REF((struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* )(pointer )sp[0], struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c*);

    pthread_mutex_t *mutex = (pthread_mutex_t *) (pointer) monitor->mutex_f;

    pthread_mutex_unlock((pthread_mutex_t *) HEAP_REF(mutex, pthread_mutex_t *));

    return -1;
}
#endif

#if defined(N_THREAD_JAVALANGTHREADSCHEDULER_NOTIFYONCONDITION)
int16 n_thread_JavaLangThreadScheduler_notifyOnCondition(int32 *sp)
{
    uint32* ptr;
    struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* monitor;
    Object* target = (Object*) (pointer) sp[0];
    pthread_cond_t *cond;

    ptr = (uint32*) ((unsigned char*) HEAP_REF(target, unsigned char*) - 4);

    monitor = HEAP_REF((struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* )(pointer )*ptr, struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c*);

    cond = HEAP_REF((pthread_cond_t *)(pointer)(monitor->conditionVariable_f), pthread_cond_t *);

    pthread_cond_signal(cond);

    return -1;
}
#endif

#if defined(N_THREAD_JAVALANGTHREADSCHEDULER_WAITONCONDITION)
int16 n_thread_JavaLangThreadScheduler_waitOnCondition(int32 *sp)
{
    uint32* ptr;
    struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* monitor;
    Object* target = (Object*) (pointer) sp[0];
    pthread_cond_t *cond;
    pthread_mutex_t *mutex;

    ptr = (uint32*) ((unsigned char*) HEAP_REF(target, unsigned char*) - 4);

    monitor = HEAP_REF((struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c* )(pointer )*ptr, struct _thread_JavaLangThreadScheduler_JavaLangThreadMonitor_c*);

    mutex = HEAP_REF((pthread_mutex_t *)(pointer)(monitor->mutex_f), pthread_mutex_t *);

    cond = HEAP_REF((pthread_cond_t *)(pointer)(monitor->conditionVariable_f), pthread_cond_t *);

    pthread_cond_wait(cond, mutex);

    return -1;
}
#endif

#if defined(N_JAVA_LANG_THREAD_START)
#include <pthread.h>
static pthread_mutex_t mem_mutex;

void init_memory_lock(void) {
    pthread_mutex_init(&mem_mutex, 0);
}

void lock_memory(void) {
    pthread_mutex_lock(&mem_mutex);
}

void unlock_memory(void) {
    pthread_mutex_unlock(&mem_mutex);
}
#else
void init_memory_lock(void) {
}

void lock_memory(void) {
}

void unlock_memory(void) {
}
#endif

#if defined(REPORTCYCLES)
__inline__ unsigned long get_cycle_count(void)
{
    unsigned long int x;
    __asm__ volatile (".byte 0x0f, 0x31" : "=A" (x));
    return x;
}
#endif

#if defined(N_JAVA_LANG_FLOAT_TOSTRING)
static char floatStr[20];
#include <stdio.h>
Object* createStringObject(int32 size, const char* data, int32* sp);
int32 _strlen(const char* str);

int16 n_java_lang_Float_toString(int32 *sp) {

    float f;
    unsigned char* floatObj = (unsigned char*) (pointer) sp[0];
    floatObj = floatObj + offsetof(struct _java_lang_Float_c, value_f);
    f = *(float*) HEAP_REF(floatObj, unsigned char*);
    sprintf(floatStr, "%f", f);
    sp[0] = (int32) (pointer) createStringObject(_strlen(floatStr), (const char*) floatStr, sp);
    return -1;
}
#endif

#if defined(N_JAVA_LANG_STRICTMATH_CEIL)
/* ceil
 * param : double
 * return: double
 */
#include <math.h>
int16 n_java_lang_StrictMath_ceil(int32 *sp) {
    double d = *(double*) sp;
    d = ceil(d);
    *(double*) sp = d;
    return -1;
}
#endif

#if defined(N_JAVA_LANG_STRICTMATH_COS)
#include <math.h>
/* cos
 * param : double
 * return: double
 */
int16 n_java_lang_StrictMath_cos(int32 *sp) {
    double d = *(double*) sp;
    d = cos(d);
    *(double*) sp = d;
    return -1;
}
#endif

#if defined(N_JAVA_LANG_STRICTMATH_SIN)
/* sin
 * param : double
 * return: double
 */
#include <math.h>
int16 n_java_lang_StrictMath_sin(int32 *sp) {
    double d = *(double*) sp;
    d = sin(d);
    *(double*) sp = d;
    return -1;
}
#endif

#if defined(N_JAVA_LANG_STRICTMATH_SQRT)
/* sqrt
 * param : double
 * return: double
 */
#include <math.h>
int16 n_java_lang_StrictMath_sqrt(int32 *sp) {
    double d = *(double*) sp;
    d = sqrt(d);
    *(double*) sp = d;
    return -1;
}
#endif

#if defined(ENABLE_DEBUG)
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <strings.h>
#include <unistd.h>

extern int requestResponseChannel;

extern unsigned char awaitCommandFromDebugger(int32* fp, unsigned short methodNumber, unsigned short pc);
extern void disconnectFromDebugger(void);

void stopProgram(int exitValue)
{
    disconnectFromDebugger();
    exit(exitValue);
}

void closeChannel(int channelID)
{
    close(channelID);
}

void closeStdout(void)
{
    ;
}

int32 connectToChannel(int32 channelID)
{
    int sockfd, newsockfd;
    socklen_t clilen;
    struct sockaddr_in* serv_addr;
    struct sockaddr_in* cli_addr;

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0)
    {
        perror("ERROR opening socket");
        stopProgram(1);
    }

    serv_addr = (struct sockaddr_in*)gc_allocateObject(sizeof(struct sockaddr_in) - sizeof(Object), 0);

    serv_addr = HEAP_REF(serv_addr, struct sockaddr_in*);
    serv_addr -> sin_family = AF_INET;
    serv_addr -> sin_addr.s_addr = INADDR_ANY;
    serv_addr -> sin_port = htons(channelID);

    if (bind(sockfd, (struct sockaddr *) serv_addr,
                    sizeof(struct sockaddr_in)) < 0)
    {
        perror("Cannot connect to debugger");
        stopProgram(1);
    }

    listen(sockfd,5);
    clilen = sizeof(struct sockaddr_in);
    cli_addr = (struct sockaddr_in *)gc_allocateObject(sizeof(struct sockaddr_in) - sizeof(Object), 0);

    cli_addr = HEAP_REF(cli_addr, struct sockaddr_in *);

    newsockfd = accept(sockfd, (struct sockaddr *)cli_addr, &clilen);
    if (newsockfd < 0)
    {
        perror("Cannot connect to debugger");
        stopProgram(1);
    }
    return newsockfd;
}

void readFromDebugger(unsigned char *buf, unsigned short length)
{
    unsigned char *buffer = buf;
    do
    {
        int count = read(requestResponseChannel, buffer, length);

        if (count == -1)
        {
            perror("Cannot read from debugger");
            stopProgram(1);
        }
        else
        {
            length -= count;
            buffer += count;
        }

    }while (length > 0);
}

void writeToDebugger(int channelID, const unsigned char *buf, unsigned short length)
{
    do
    {
        int count = write(channelID, buf, length);
        if (count == -1)
        {
            perror("Cannot write to debugger");
            stopProgram(1);
        }
        else
        {
            length -= count;
        }
    }while (length > 0);
}

void checkForRequests(int32* fp)
{
    int result;
    fd_set sockset;
    struct timeval timeout;
    timeout.tv_sec = 0;
    timeout.tv_usec = 0;

    FD_ZERO(&sockset);
    FD_SET(requestResponseChannel, &sockset);

    while ((result = select(requestResponseChannel + 1, &sockset, NULL, NULL, &timeout)) != 0)
    {
        if (result < 0)
        {
            perror("Cannot check for request availability");
            stopProgram(1);
        }
        else if (result == 1)
        {
            awaitCommandFromDebugger(fp, 0, 0);
        }
    }
}
#endif

#if defined(N_TEST_TESTPERFORMANCE_QUICKSORTC)
void quicksort(uint16* array, uint16 left, uint16 right) {
    if (array != 0) {
        uint16 low = left;
        uint16 high = right;
        uint16 pivot;

        if (low >= right) {
            return;
        }

        pivot = array[low + ((high - low) >> 1)];

        while (low <= high) {

            while (array[low] < pivot) {
                low++;
            }

            while (array[high] > pivot) {
                high--;
            }

            if (low <= high) {
                uint16 temp = array[low];
                array[low] = array[high];
                array[high] = temp;

                low++;
                high--;
            }
        }

        if (high < low) {
            uint16 T = high;
            high = low;
            low = T;
        }

        if (left < low)
        quicksort(array, left, low);
        if (high < right)
        quicksort(array, low == left ? low + 1 : low, right);
    }
}

uint16 n_test_TestPerformance_quicksortC(uint32 *sp)
{
    uint16* arr = (uint16*)HEAP_REF((pointer)sp[0], uint16*);

    quicksort(arr + 2, 0, arr[1] - 1);

    return -1;
}

#endif

#if defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
#include <unistd.h>
#include <pthread.h>
volatile uint8 systemTick;

static pthread_t tick_thread;

volatile unsigned char signal_stop;

static void *tick_thread_logic(void *ptr) {
    while (!signal_stop) {
        systemTick++;
        usleep(1000);
    }
    return 0;
}

void start_system_tick(void) {
    signal_stop = 0;
    if (pthread_create(&tick_thread, NULL, tick_thread_logic, NULL)) {
        fprintf(stderr, "Error creating system tick thread\n");
        return;
    }
}

void stop_system_tick(void) {
    signal_stop = 1;
    if (pthread_join(tick_thread, NULL)) {
        fprintf(stderr, "Error joining thread\n");
        return;
    }
}
#endif

#if defined(N_VM_REALTIMECLOCK_AWAITNEXTTICK)
#include <unistd.h>
int16 n_vm_RealtimeClock_awaitNextTick(int32 *sp) {
    while (systemTick == 0) {
        usleep(1000);
    }
    return -1;
}
#endif

