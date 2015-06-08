#define _GNU_SOURCE
#include <sched.h>

#include "types.h"
#include "methods.h"
#include "classes.h"
#include "ostypes.h"
#include "gc.h"
#include "allocation_point.h"

#ifdef PRINTFSUPPORT
#include <stdio.h>
#endif

extern const ClassInfo *classes;

#if defined(N_JAVA_LANG_CLASS_NEWINSTANCE) || defined(N_JAVA_LANG_REFLECT_METHOD_INVOKE) || defined(N_JAVA_LANG_REFLECT_CONSTRUCTOR_NEWINSTANCE) || defined(N_JAVA_LANG_CLASS_GETMETHOD) || defined(N_JAVA_LANG_CLASS_GETCONSTRUCTOR) || defined(ENABLE_DEBUG)
extern const MethodInfo *methods;
#else
#endif

extern VMMemory* currentMemoryArea;

extern unsigned char getElementSize(unsigned short aType);
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_CLASS_GETNAME0)
Object* createStringObject(int32 size, const char* data, int32* sp);
#endif
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
extern int32 imul(int32 a, int32 b);
#endif
extern void initGC(void);
extern void printStr(const char* str);
extern void printROMStr(const char* str);
extern void printShort(unsigned short c);
extern unsigned short getClassIndex(Object* obj);
extern void setClassIndex(Object* obj, unsigned short classIndex);
extern unsigned char handleNewClassIndex(int32* sp, unsigned short classIndex);
extern int16 enterMethodInterpreter(unsigned short methodNumber, int32* sp);

#if defined(N_JAVA_LANG_CLASS_NEWINSTANCE) || defined(N_JAVA_LANG_THREAD_START) || (defined(JAVA_LANG_THROWABLE_INIT_) &&  defined(PRE_INITIALIZE_EXCEPTIONS)) || defined(N_JAVA_LANG_CLASS_GETMETHOD)
extern int16 initializeException(int32* sp, int16 exceptionClass,
		int16 exceptionInitMethod);
#endif

#if defined(INVOKECLASSINITIALIZERS)
int16 invokeClassInitializers(int32* sp);
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
int16 initializeConstants(int32* sp);
#endif

Object* getClass(unsigned short classIndex);

#if defined(ENABLE_DEBUG)
typedef struct _methodLocation
{
	unsigned short pc;
}MethodLocation;

MethodLocation* breakPoints;
#endif

VMMemory* heapArea;

#if defined(SUPPORTGC)
unsigned char SUPPORTGC_var = 1;
#else
#if defined(REFLECT_CLASSINFO_INIT_)
unsigned char SUPPORTGC_var = 0;
#endif
#endif

#ifdef AVR
#if defined(VM_CLOCKINTERRUPTHANDLER_INTERRUPT)
uint8 workingRegister1;
uint8 workingRegister2;
uint8 workingRegister3;
uint8 workingRegister4;
uint8 workingRegister5;
#endif
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(GETCLASS_USED)
static Object *head;
#endif

#if defined (N_JAVA_LANG_SYSTEM_GETPROPERTY)
extern int16 devices_System_getProperty(int32 *fp, int32 key);
int16 n_java_lang_System_getProperty(int32 *sp) {
	devices_System_getProperty(sp, sp[0]);
	return -1;
}
#endif

#if defined (N_JAVA_LANG_SYSTEM_SETOUT)
extern const unsigned char *classData;
int16 n_java_lang_System_setOut(int32 *sp) {
	((struct _staticClassFields_c *) classData)->out_f = (uint32) sp[0];
	return -1;
}
#endif

#if defined (N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined (N_JAVA_LANG_CLASS_GETNAME0) || defined(N_JAVA_LANG_FLOAT_TOSTRING)
int32 _strlen(const char* str) {
	int32 count = 0;
	while (*str++) {
		count++;
	}
	return count;
}
#endif

#if defined(N_SUN_MISC_VM_ISBOOTED)
int16 n_sun_misc_VM_isBooted(int32 *sp)
{
	sp[0] = 1;
	return -1;
}
#endif

#if defined(N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE)
int16 n_java_lang_Double_longBitsToDouble(int32 *sp) {
	return -1;
}
#endif

#if defined(N_JAVA_LANG_THREAD_CURRENTTHREAD)
int16 n_java_lang_Thread_currentThread(int32 *sp)
{
	sp[0] = 0;
	return -1;
}
#endif

#if defined(N_SUN_MISC_VM_GETSAVEDPROPERTY)
int16 n_sun_misc_VM_getSavedProperty(int32 *sp)
{
	sp[0] = 0;
	return -1;
}
#endif

#if defined(N_JAVA_LANG_STRINGBUFFER_APPEND)
/* append
 * param : float
 * return: java.lang.StringBuffer
 */
extern unsigned char handleMonitorEnterExit(Object* lockObj, unsigned char isEnter);
int16 n_java_lang_StringBuffer_append(int32 *sp) {
	handleMonitorEnterExit((Object*) (pointer) sp[0], 0);
	return -1;
}
#endif

#if defined(N_JAVA_LANG_STRINGBUILDER_APPEND)
/* append
 * param : float
 * return: java.lang.StringBuffer
 */
int16 n_java_lang_StringBuilder_append(int32 *sp) {
	/* handleMonitorEnterExit((Object*)(pointer)sp[0], 0); */
	return -1;
}
#endif

#if defined(N_JAVA_LANG_STRING_INIT_)
extern unsigned char* createArray(unsigned short classIndex, uint16 count FLASHARG(uint8 flash));
int16 n_java_lang_String_init_(int32 *sp) {
	unsigned char *charArrayObject;
	unsigned char* bytes = (unsigned char*) (pointer) sp[1];
	int32 offset = sp[2];
	int32 nb = sp[3];
	int32 count;

	charArrayObject = createArray((unsigned short) _C, (uint16) nb FLASHARG(0));

	if (charArrayObject != 0) {

		bytes = bytes + 4;

		for (count = 0; count < nb; count++) {
			*((int32 *) (HEAP_REF(charArrayObject, unsigned char *) + sizeof(Object) + 2) + count) = *(HEAP_REF(bytes, unsigned char*) + count + offset);
		}

		*(sp + 1) = (int32) (pointer) charArrayObject;

		enterMethodInterpreter(JAVA_LANG_STRING_INITFROMCHARARRAY, sp);
	} else {
	}
	return -1;
}
#endif

/* getName0
 * param :
 * return: java.lang.String
 */
#ifdef N_JAVA_LANG_CLASS_GETNAME0
int16 n_java_lang_Class_getName0(int32 *sp) {
	Object* this = (Object*) (pointer) sp[0];
	int32 classIndex;
	const char* className;
	Object* stringObject;

	classIndex = getClassIndex(this);

	if (classIndex == JAVA_LANG_CLASS) {
		classIndex = *(unsigned short *) ((unsigned char*) HEAP_REF(this,
						Object*) + sizeof(Object));

		className = (char*) pgm_read_pointer(&classes[classIndex].name, char**);

		stringObject = createStringObject(_strlen(className), className, sp);

		sp[0] = (int32) (pointer) stringObject;
	}
	return -1;
}
#endif

/* write
 * param : byte[]
 * return: void
 */
#ifdef N_DEVICES_X86WRITER_NWRITE
static void print_to_stdout(unsigned char* src, unsigned char* buffer,
		int32 buffersize, int32 nb) {
	int32 count;
	int32 length;

	while (nb > 0) {
		if (nb < buffersize) {
			length = nb;
		} else {
			length = buffersize - 1;
		}
		count = 0;
		while (count < length) {
			buffer[count] = src[count];
			count++;
		}
		buffer[count] = '\0';
		printStr((char*) buffer);
		src = src + length;
		nb = nb - length;
	}
}

int16 n_devices_X86Writer_nwrite(int32 *sp) {
	unsigned char buffer[16];
	unsigned char* src;
	int32 length;

	src = HEAP_REF((unsigned char* ) (pointer ) sp[0], unsigned char*);
	length = sp[1];
	src = src + sizeof(Object) + 2;

	print_to_stdout(src, &buffer[0], 16, length);
	return -1;
}
#endif

#if defined(N_JAVA_LANG_THROWABLE_CLINIT_)
int16 n_java_lang_Throwable_clinit_(int32 *sp) {
	return -1;
}
#endif

/* fillInStackTrace
 * param :
 * return: java.lang.Throwable
 */
#ifdef N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE
int16 n_java_lang_Throwable_fillInStackTrace(int32 *sp) {
	/* Return 'this', don't fill in anything */
	sp[0] = *(sp - 1);
	return -1;
}
#endif

/* fillInStackTrace
 * param : int
 * return: java.lang.Throwable
 */
#ifdef N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE_
int16 n_java_lang_Throwable_fillInStackTrace_(int32 *sp) {
	/* Return 'this', don't fill in anything */
	sp[0] = *(sp - 1);
	return -1;
}
#endif

/* desiredAssertionStatus
 * param :
 * return: boolean
 */
#ifdef N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS
int16 n_java_lang_Class_desiredAssertionStatus(int32 *sp) {
	sp[0] = 1;
	return -1;
}
#endif

/* testNative
 * param :
 * return: int
 */
#ifdef N_TEST_TESTINVOKENATIVE1_SUPER_TESTNATIVE
int16 n_test_TestInvokeNative1_Super_testNative(int32 *sp) {
	sp[0] = 42;
	return -1;
}
#endif

/* testNative
 * param :
 * return: int
 */
#ifdef N_TEST_TESTINVOKENATIVE1_SUPER_TESTNATIVESTATIC
int16 n_test_TestInvokeNative1_Super_testNativeStatic(int32 *sp) {
	sp[0] = 42;
	return -1;
}
#endif

/* testNative
 * param :
 * return: int
 */
#ifdef N_TEST_TESTINVOKENATIVE2_SUPER_TESTNATIVESTATIC
int16 n_test_TestInvokeNative2_Super_testNativeStatic(int32 *sp) {
	sp[0] = 42;
	return -1;
}
#endif

/* getPrimitiveClass
 * param : java.lang.String
 * return: java.lang.Class
 */
#ifdef N_JAVA_LANG_CLASS_GETPRIMITIVECLASS
static int32 _streq(char* str1, char* str2) {
	int32 length1 = _strlen(str1);
	int32 length2 = _strlen(str2);
	if (length1 == length2) {
		int32 count = 0;
		while (count < length1) {
			if (str1[count] != str2[count]) {
				return 0;
			}
			count++;
		}
		return 1;
	} else {
		return 0;
	}
}

extern unsigned char getField(unsigned char *data, unsigned char size,
		int32* sp);

#if defined(N_JAVA_LANG_CLASS_GETMETHOD) || defined(N_JAVA_LANG_CLASS_FORNAME)
#define BUFFER_LENGTH 128
#else
#define BUFFER_LENGTH 10
#endif
static char buffer[BUFFER_LENGTH];

static char* getCString(unsigned char* strObj) {
	int32 string_offset;
	int32 string_count;
	int32 count;
	int32 offset;
	unsigned char* string_valuep = 0;
	unsigned char* string_value = 0;

	offset = offsetof(struct _java_lang_String_c, value_f);
	getField(HEAP_REF(strObj + offset, unsigned char*), 32,
			(int32*) &string_valuep);
	string_value = (unsigned char*) (pointer) string_valuep;

#if defined(JAVA_LANG_STRING_OFFSET_offset)
	offset = offsetof(struct _java_lang_String_c, offset_f);
	getField(HEAP_REF(strObj + offset, unsigned char*), 32, &string_offset);
#else
	string_offset = 0;
#endif

#if defined(JAVA_LANG_STRING_COUNT_offset)
	offset = offsetof(struct _java_lang_String_c, count_f);
	getField(HEAP_REF(strObj + offset, unsigned char*), 32, &string_count);
#else
	string_count = *((uint16*) (pointer) HEAP_REF(string_valuep, unsigned char*)
			+ 1);
#endif

	if (string_count >= BUFFER_LENGTH - 1) {
		string_count = BUFFER_LENGTH - 1;
	}

	count = 0;

	string_value = string_value + sizeof(Object) + 2 + (string_offset << 2);
	while (count < string_count) {
		int32 value;
		getField(HEAP_REF(string_value, unsigned char*), 32, &value);
		buffer[count++] = (unsigned char) value;
		string_value += 4;
	}

	buffer[count] = '\0';
	return buffer;
}

int16 n_java_lang_Class_getPrimitiveClass(int32 *sp) {
	int32 classIndex;
	unsigned char* strObj = (unsigned char*) (pointer) sp[0];
	char* buffer = getCString(strObj);

	if (_streq((char*) buffer, "int")) {
		classIndex = JAVA_LANG_INTEGER_var;
	} else if (_streq((char*) buffer, "byte")) {
		classIndex = JAVA_LANG_BYTE_var;
	} else if (_streq((char*) buffer, "short")) {
		classIndex = JAVA_LANG_SHORT_var;
	} else if (_streq((char*) buffer, "boolean")) {
		classIndex = JAVA_LANG_BOOLEAN_var;
	} else if (_streq((char*) buffer, "long")) {
		classIndex = JAVA_LANG_LONG_var;
	} else {
		classIndex = JAVA_LANG_OBJECT_var;
	}
	{
		Object* class = getClass(classIndex);
		if (class != 0) {
			sp[0] = (int32) (pointer) getClass(classIndex);
			return -1;
		} else {
			return JAVA_LANG_OUTOFMEMORYERROR_var;
		}
	}
}
#endif

/* currentTimeMillis
 * param :
 * return: long
 */
#ifdef N_JAVA_LANG_SYSTEM_CURRENTTIMEMILLIS
#if defined(DEVICES_SYSTEM_CURRENTTIMEMILLIS_USED)
extern int16 devices_System_currentTimeMillis(int32 *fp);
#endif
int16 n_java_lang_System_currentTimeMillis(int32 *sp) {
#if defined(DEVICES_SYSTEM_CURRENTTIMEMILLIS_USED)
	return devices_System_currentTimeMillis(sp);
#else
	sp[0] = 0;
	sp[1] = 0;
	return -1;
#endif
}
#endif

void* getPointer(int32 val) {
	return (void*) (pointer) val;
}

/* arraycopy
 * param : java.lang.Object, int, java.lang.Object, int, int
 * return: void
 */
#ifdef N_JAVA_LANG_SYSTEM_ARRAYCOPY
static void arraycopy(unsigned char* src, unsigned short srcPos,
		unsigned char* dst, unsigned short dstPos, unsigned short length) {
	unsigned char elementSize;
	unsigned short count;

	elementSize = getElementSize(getClassIndex((Object*) src));

#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
	src = HEAP_REF(src, unsigned char*) + sizeof(Object) + 2 + imul(srcPos, elementSize);
	dst = HEAP_REF(dst, unsigned char*) + sizeof(Object) + 2 + imul(dstPos, elementSize);

	count = imul(length, elementSize);
#else
	src = HEAP_REF(src, unsigned char*) + sizeof(Object) + 2
	+ (srcPos * elementSize);
	dst = HEAP_REF(dst, unsigned char*) + sizeof(Object) + 2
	+ (dstPos * elementSize);

	count = length * elementSize;
#endif

	while (count > 0) {
		*dst++ = *src++;
		count--;
	}
}

int16 n_java_lang_System_arraycopy(int32 *sp) {
	unsigned char* src;
	unsigned short srcPos;
	unsigned char* dst;
	unsigned short dstPos;
	unsigned short length;

	src = (unsigned char*) getPointer(sp[0]);
	srcPos = sp[1];
	dst = (unsigned char*) getPointer(sp[2]);
	dstPos = sp[3];
	length = sp[4];

	arraycopy(src, srcPos, dst, dstPos, length);

	return -1;
}
#endif

/* getClass
 * param :
 * return: java.lang.Class
 */
#ifdef N_JAVA_LANG_OBJECT_GETCLASS
int16 n_java_lang_Object_getClass(int32 *sp) {
	Object* obj = (Object*) (pointer) sp[0];
	unsigned short classIndex = getClassIndex(obj);
	obj = getClass(classIndex);
	if (obj != 0) {
		sp[0] = (int32) (pointer) getClass(classIndex);
		return -1;
	} else {
		return JAVA_LANG_OUTOFMEMORYERROR_var;
	}
}
#endif

#if defined(N_JAVA_LANG_CLASS_GETSUPERCLASS)
int16 n_java_lang_Class_getSuperclass(int32 *sp) {
	Object* class = (Object*) (pointer) sp[0];
	uint16 classIndex = *(unsigned short *) ((unsigned char*) HEAP_REF(class, Object*) + sizeof(Object));
	sp[0] = (int32) (pointer) getClass(classes[classIndex].superClass);
	return -1;
}
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(GETCLASS_USED)

#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
#include <stdlib.h>
#include <pthread.h>
#endif

static VMMemory* current = 0;
static uint8 scopeCount = 0;

#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
extern pthread_key_t key;
#endif

static void pushDefaultArea(void) {
	if (heapArea != 0) {
		if (scopeCount == 0) {
			#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
				current = pthread_getspecific(key);
			    pthread_setspecific(key, heapArea);
			#else
				current = currentMemoryArea;
				currentMemoryArea = heapArea;
			#endif

		}
		scopeCount++;
	}
}

static void popDefaultArea(void) {
	if (heapArea != 0) {
		if (scopeCount == 1) {
			#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
			 	pthread_setspecific(key, current);
			#else
			    currentMemoryArea = current;
			#endif

		}
		scopeCount--;
	}
}

static Object* gc_allocateObjectInArea(unsigned short dobjectSize,
		unsigned short pobjectSize) {
	Object *obj = 0;

	pushDefaultArea();

	obj = gc_allocateObject(dobjectSize, pobjectSize);

	popDefaultArea();

	return obj;
}

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_CLASS_GETNAME0)
extern unsigned char* createArray(unsigned short classIndex,
		uint16 count FLASHARG(uint8 flash));
static Object* initializeStringObject(int32* sp, unsigned char *charArrayObject) {
	unsigned short dobjectSize, pobjectSize, classIndex;
	Object* stringObject;

	classIndex = (unsigned short) JAVA_LANG_STRING_var;
	dobjectSize = pgm_read_word(&classes[classIndex].dobjectSize) >> 3;
	pobjectSize = pgm_read_word(&classes[classIndex].pobjectSize) >> 3;
	stringObject = gc_allocateObject(dobjectSize, pobjectSize);
	setClassIndex(stringObject, classIndex);

	*sp = (int32) (pointer) stringObject;
	*(sp + 1) = (int32) (pointer) charArrayObject;

	enterMethodInterpreter(JAVA_LANG_STRING_INITFROMCHARARRAY_var, sp);

	return stringObject;
}

Object* createStringObject(int32 size, const char* data, int32* sp) {
	unsigned char *charArrayObject;
	int32 count;
	Object* object;

	pushDefaultArea();
	charArrayObject = createArray((unsigned short) _C_var,
			(uint16) size FLASHARG(0));

	if (charArrayObject != 0) {

		for (count = 0; count < size; count++) {
			*((int32 *) (HEAP_REF(charArrayObject, unsigned char *)
							+ sizeof(Object) + 2) + count) = pgm_read_byte(
					data + count);
		}
		object = initializeStringObject(sp, charArrayObject);
	} else {
		object = 0;
	}
	popDefaultArea();
	return object;
}
#endif

Object* getClass(unsigned short classIndex) {
	Object *class = head;
	while (class != 0) {
		if (*(unsigned short *) ((unsigned char*) HEAP_REF(class, Object*)
						+ sizeof(Object)) == classIndex) {
			return class;
		} else {
			class = *(Object **) ((unsigned char*) HEAP_REF(class, Object*)
					- sizeof(Object*));
		}
	}
	{
		unsigned short dobjectSize =
		pgm_read_word(&classes[JAVA_LANG_CLASS_var].dobjectSize) >> 3;
		unsigned short pobjectSize =
		pgm_read_word(&classes[JAVA_LANG_CLASS_var].pobjectSize) >> 3;

		class = gc_allocateObjectInArea(dobjectSize, pobjectSize);

		if (class != 0) {
			class = (Object *) ((unsigned char*) class + sizeof(Object*));
			setClassIndex(class, (unsigned short) JAVA_LANG_CLASS_var);
			*(unsigned short *) ((unsigned char*) HEAP_REF(class, Object*)
					+ sizeof(Object)) = classIndex;
			*(Object **) ((unsigned char*) HEAP_REF(class, Object*)
					- sizeof(Object*)) = head;
			head = class;
		} else {
			return 0;
		}
	}
	return head;
}
#endif

#if defined(N_JAVA_LANG_CLASS_NEWINSTANCE)
static int16 newInstance(int32* sp, unsigned short classIndex) {
	if (handleNewClassIndex(sp, classIndex)) {
		uint16 i;

		for (i = 0; i < NUMBEROFMETHODS; i++) {
			const MethodInfo* methodInfo = &methods[i];
			if ((methodInfo->classIndex >> 1) == classIndex) {
				unsigned char minfo = pgm_read_byte(&methodInfo->minfo) >> 6;
				if ((minfo & 0x1) && (methodInfo->numArgs == 0)) {
					return enterMethodInterpreter(i, sp);
				}
			}
		}
		return -1;
	} else {
		return initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR_var, JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
	}
}
#endif

#ifdef N_JAVA_LANG_CLASS_NEWINSTANCE
int16 n_java_lang_Class_newInstance(int32* sp) {
	Object *class = (Object *) (pointer) sp[0];
	unsigned short classIndex = *(unsigned short *) ((unsigned char*) HEAP_REF(class, Object *) + sizeof(Object));

	return newInstance(sp, classIndex);
}
#endif

/* getComponentType
 * param :
 * return: java.lang.Class
 */
#ifdef N_JAVA_LANG_CLASS_GETCOMPONENTTYPE
int16 n_java_lang_Class_getComponentType(int32 *sp) {
	Object *obj;
	unsigned short classIndex;
	Object* componentType;

	obj = (Object*) (pointer) sp[0];
	classIndex = getClassIndex(obj);
	componentType = 0;
	if (classIndex == (unsigned short) JAVA_LANG_CLASS_var) {
		classIndex = *(unsigned short *) ((unsigned char*) HEAP_REF(obj, Object*) + sizeof(Object));
		if (pgm_read_byte(&classes[classIndex].dimension) != 0) {
			signed short componentTypeClasIndex;
			componentTypeClasIndex = pgm_read_word(&classes[classIndex].dobjectSize);
			if (componentTypeClasIndex < -2) {
				componentTypeClasIndex = -componentTypeClasIndex;
			}
			componentType = getClass(componentTypeClasIndex);
		}
	}

	if (componentType != 0) {
		sp[0] = (int32) (pointer) componentType;
		return -1;
	} else {
		return JAVA_LANG_OUTOFMEMORYERROR_var;
	}
}
#endif

/* clone
 * param :
 * return: java.lang.Object
 */
#ifdef N_JAVA_LANG_OBJECT_CLONE
int16 n_java_lang_Object_clone(int32 *sp) {
	Object* obj = (Object*) (pointer) sp[0];
	unsigned short classIndex = getClassIndex(obj);
	unsigned short dobjectSize;
	unsigned short pobjectSize;
	Object* clone;

	dobjectSize = pgm_read_word(&classes[classIndex].dobjectSize) >> 3;
	pobjectSize = pgm_read_word(&classes[classIndex].pobjectSize) >> 3;

	clone = gc_allocateObject(dobjectSize, pobjectSize);

	if (clone != 0) {
		unsigned char *src, *dst;
		src = (unsigned char *) HEAP_REF(obj, Object*);
		dst = (unsigned char *) HEAP_REF(clone, Object*);
		dobjectSize += sizeof(Object);

		while (dobjectSize) {
			*dst++ = *src++;
			dobjectSize--;
		}
		sp[0] = (int32) (pointer) clone;
		return -1;
	} else {
		return JAVA_LANG_OUTOFMEMORYERROR_var;
	}
}
#endif

/* hashCode
 * param :
 * return: int
 */
#ifdef N_JAVA_LANG_OBJECT_HASHCODE
int16 n_java_lang_Object_hashCode(int32 *sp) {
	Object *this = (Object*) (pointer) sp[0];
	int32 hashCode = (int32) (pointer) this;
	sp[0] = hashCode;
	return -1;
}
#endif

/* doPrivileged
 * param : java.security.PrivilegedAction
 * return: java.lang.Object
 */
#ifdef N_JAVA_SECURITY_ACCESSCONTROLLER_DOPRIVILEGED
extern int16 devices_AccessController_doPrivileged(int32 *fp, int32 action);

int16 n_java_security_AccessController_doPrivileged(int32 *sp) {
	devices_AccessController_doPrivileged(sp, sp[0]);

	return -1;
}
#endif

/* newArray
 * param : java.lang.Class, int
 * return: java.lang.Object
 */
#ifdef N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY
extern unsigned char* createArrayFromElementSize(unsigned short classIndex,
		unsigned char elementSize, uint16 count FLASHARG(uint8 flash));

int16 n_java_lang_reflect_Array_newArray(int32 *sp) {
	Object* class = (Object*) (pointer) sp[0];
	int32 size = sp[1];
	unsigned short classIndex = *(unsigned short *) ((unsigned char*) HEAP_REF(class, Object*) + sizeof(Object));
	unsigned char* array = 0;
	unsigned char elementSize = 0;

	if (classIndex == JAVA_LANG_OBJECT_var) {
		elementSize = 4;
	} else if (classIndex == JAVA_LANG_INTEGER_var) {
		elementSize = 4;
	} else if (classIndex == JAVA_LANG_SHORT_var) {
		elementSize = 2;
	} else if (classIndex == JAVA_LANG_BYTE_var) {
		elementSize = 1;
	} else if (classIndex == JAVA_LANG_BOOLEAN_var) {
		elementSize = 1;
	} else if (classIndex == JAVA_LANG_LONG_var) {
		elementSize = 8;
	}

	if (elementSize) {
		array = createArrayFromElementSize(classIndex, 4, size FLASHARG(0));
	}

	if (array != 0) {
		sp[0] = (int32) (pointer) array;
		return -1;
	} else {
		return JAVA_LANG_OUTOFMEMORYERROR_var;
	}
}
#endif

/* toString
 * param : 
 * return: java.lang.String
 */
#ifdef N_JAVA_LANG_THREAD_TOSTRING
int16 n_java_lang_Thread_toString(int32 *sp) {
	sp[0] = 0;
	return -1;
}
#endif

#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)

#include <stdlib.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/timerfd.h>
#include <errno.h>
#include <string.h>
#include <pthread.h>

pthread_key_t key_schedulable_object;

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_SETAFFINITY)
int16 n_javax_safetycritical_OSProcess_setAffinity(int32 *sp){
 	int size = sp[0]+1;
 	int *p = HEAP_REF((int* ) (pointer ) sp[1], int*);

 	cpu_set_t cs;
 	CPU_ZERO(&cs);
 	int i = 1;
 	for(; i<size;i++){
 		CPU_SET(p[i], &cs);
 	}
 	pthread_setaffinity_np(pthread_self(), sizeof(cs), &cs);

 	printf("p[0-4]: %d, %d, %d, %d, %d\n", p[0], p[1], p[2], p[3], p[4]);
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_SETOMMSAFFINITYSET)
int16 n_javax_safetycritical_OSProcess_setOMMSAffinitySet(int32 *sp){
 	int level = sp[0];
 	if(level != 2){
 		int processor = sched_getcpu();
 		cpu_set_t cs;
 		CPU_ZERO(&cs);
 		CPU_SET(processor, &cs);
 		int ret = pthread_setaffinity_np(pthread_self(), sizeof(cs), &cs);
 		if( ret != 0 ){
 			printf("pthread_setaffinity_np ret: %d. \n",ret);
        	return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
 		}
 	}
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_ISPROCESSORINSET)
int16 n_javax_safetycritical_OSProcess_isProcessorInSet(int32 *sp){
 	int processor = sp[0];
 	cpu_set_t cs;
 	CPU_ZERO(&cs);
 	pthread_getaffinity_np(pthread_self(), sizeof(cs), &cs);

 	sp[0] = CPU_ISSET(processor, &cs);
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_GETALLCPUCOUNT)
int16 n_javax_safetycritical_OSProcess_getAllCPUCount(int32 *sp){
 	sp[0] = get_nprocs_conf();
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_GETAVAILABLECPUCOUNT)
int16 n_javax_safetycritical_OSProcess_getAvailableCPUCount(int32 *sp){
	cpu_set_t cs;
 	CPU_ZERO(&cs);
 	pthread_getaffinity_np(pthread_self(), sizeof(cs), &cs);
 	sp[0] = CPU_COUNT(&cs);
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_GETCURRENTCPUID)
int16 n_javax_safetycritical_OSProcess_getCurrentCPUID(int32 *sp){
	sp[0] = sched_getcpu();
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_GETTHREADID)
int16 n_javax_safetycritical_OSProcess_getThreadID(int32 *sp){
	int id = (int) pthread_getspecific(key_schedulable_object);
	sp[0] = id;
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_REQUESTTERMINATION_C)
int16 n_javax_safetycritical_OSProcess_requestTermination_c(int32 *sp){
	struct _java_lang_Thread_c* thread = HEAP_REF((struct _java_lang_Thread_c* ) (pointer ) sp[0], struct _java_lang_Thread_c*);
    pthread_t *thr = HEAP_REF((pthread_t *)(pointer)thread->name_f, pthread_t *);
    int ret = pthread_cancel(*thr);
    if ( ret != 0 ){
        printf("pcancel errno: %d. ret: %d. \n",errno,ret);
        return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
    }
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_TESTCANCEL_C)
int16 n_javax_safetycritical_OSProcess_testCancel_c(int32 *sp){
    pthread_testcancel();
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_SETTIMERFD)
int16 n_javax_safetycritical_OSProcess_setTimerfd(int32 *sp){
	if(sp[0] < 1){
		return -1;
	}

	long long start_time  =  (long long) sp[1] << 32 | sp[2];
	unsigned int ns;
	unsigned int sec;
	struct itimerspec itval;

	sec = start_time / 1000000000;
	ns = start_time % 1000000000;
	if(ns == 0 && sec == 0){
		ns++;
	}

	itval.it_interval.tv_sec = sec;
	itval.it_interval.tv_nsec = ns;
	itval.it_value.tv_sec = sec;
	itval.it_value.tv_nsec = ns;

	int ret = timerfd_settime(sp[0], 0, &itval, NULL);
    if(ret != 0 && errno != EBADF){
        printf("timer set errno: %d. file: %d.\n",errno, sp[0]);
        return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
    }
    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_SETMEMORYAREA )
int16 n_javax_safetycritical_OSProcess_setMemoryArea(int32 *sp){
    VMMemory* currMem;
    currMem = (int32)(*(sp + 0));
	pthread_setspecific(key, currMem);
	return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_GETCURRENTMEMORYAREA)
int16 n_javax_safetycritical_OSProcess_getCurrentMemoryArea(int32 *sp){
	int32 *currentMemory = (int32 *) pthread_getspecific(key);
    sp[0] = currentMemory;
	return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_SETOUTERMOSTMISSIONSEQUENCER)
int16 n_javax_safetycritical_OSProcess_setOuterMostMissionSequencer(int32 *sp)
{
	pthread_setspecific(key_schedulable_object, -11);
    int policyformain = SCHED_FIFO;
    struct sched_param parammain;
    parammain.sched_priority = sp[0];

    if ( pthread_setschedparam(pthread_self(), policyformain, &parammain) != 0 ){
        printf("psetmain errno: %d.\n",errno);
        return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
    }

    return -1;
}
#endif

#if defined(N_JAVAX_SAFETYCRITICAL_OSPROCESS_INITSPECIFICID)
int16 n_javax_safetycritical_OSProcess_initSpecificID(int32 *sp)
{
	pthread_key_create(&key_schedulable_object, NULL);
	pthread_setspecific(key_schedulable_object, -99);
    return -1;
}
#endif

/*Periodic Parameters*/
struct periodic_info {
    int timer_fd;
};

/*Initialize Periodic Parameters*/
int make_periodic(long period, struct periodic_info *info) {
	int ret;
	unsigned int ns;
	unsigned int sec;
	int fd;
	struct itimerspec itval;

	/* Create the timer */
	fd = timerfd_create(CLOCK_MONOTONIC, 0);

	info->timer_fd = fd;
	if (fd == -1){
        printf("timercreate errno: %d.\n",errno);
		return fd;
	}

	/* Make the timer periodic */
	sec = period / 1000000000;
	ns = period % 1000000000;
	itval.it_interval.tv_sec = sec;
	itval.it_interval.tv_nsec = ns;
	itval.it_value.tv_sec = sec;
	itval.it_value.tv_nsec = ns;
	ret = timerfd_settime(fd, 0, &itval, NULL);
    if(ret != 0){
        printf("timer set errno: %d. ret: %d.\n",errno, ret);
    }

	return ret;
}

/*Make thread wait for the given period*/
void wait_period(struct periodic_info *info) {
	long long missed;
	int ret;

	/* Wait for the next timer event. If we have missed any the
	 number is written to "missed" */
	ret = read(info->timer_fd, &missed, sizeof(missed));
	if (ret == -1) {
		printf("read errno: %d. fd: %d.\n",errno,info->timer_fd);
	}
}

/*Thread args, holds the info of a thread*/
struct thread_args {
    int isPeriodic;
    long long start;
    long long period;
    Object* target;
    VMMemory* memory;
    int id;
    struct _javax_safetycritical_OSProcess_MyThread_c* thread;
};

/*close open file for timer*/
void open_file_cleanup_handler(void *arg) {
	int *fd = (int*)arg;
	if ( close(*fd) != 0 ){
        printf("close errno: %d.\n",errno);
    }
}

extern int16 thread_ThreadUtils_dispatchRunnable(int32 *fp);
void scj_multicore_thread_executor(void* arg){
		/*get thread args*/
	struct thread_args* args = (struct thread_args*) arg;

	/*set thread current memory area : should be a mission memory*/
	if ( pthread_setspecific(key,args->memory) != 0 ){
        printf("getspec errno: %d.\n",errno);
    }

    /*set thread id*/
    pthread_setspecific(key_schedulable_object, args->id);

    /*create thread stack*/
	int32 *threadJavaStack = HEAP_REF((int32 *) gc_allocateObject(/*16384*/8*1024, 0), int32 *);
	threadJavaStack[0] = (int32) (pointer) args->target;

    /*Periodic Event Handler*/
	if(args->isPeriodic == 99){
		struct periodic_info info;
		struct periodic_info startInfo;
		int started = 1;

		if(args->start > 0){
			if(make_periodic(args->start, &startInfo) != 0){
				printf("make_periodic start errno: %d. fd: %d.\n",errno);
			}
		}

		if(make_periodic(args->period, &info) != 0){
			printf("make_periodic period errno: %d. fd: %d.\n",errno);
		}

		pthread_cleanup_push(open_file_cleanup_handler, &info.timer_fd);
		while (1) {
			if(args->start>0 && started){
				wait_period(&startInfo);
				started = 0;
				close(startInfo.timer_fd);
			}
    		thread_ThreadUtils_dispatchRunnable(threadJavaStack);
			wait_period(&info);
		}
		pthread_cleanup_pop(1);
	}
	/*OneShot Event handler*/
	else if(args->isPeriodic == 98){
		struct periodic_info startInfo;
		struct itimerspec old_value;
		make_periodic(args->start, &startInfo);
		args->thread->startTimer_c_f = startInfo.timer_fd;

		pthread_cleanup_push(open_file_cleanup_handler, &startInfo.timer_fd);
        while(1){
        	timerfd_gettime(startInfo.timer_fd, &old_value);
        	if(old_value.it_value.tv_sec != 0 || old_value.it_value.tv_nsec != 0){
				wait_period(&startInfo);
        	}

            pthread_testcancel();
            thread_ThreadUtils_dispatchRunnable(threadJavaStack);
            pthread_testcancel();
        }
        pthread_cleanup_pop(1);
	}
	/*Aperiodic Event handler*/
	else if (args->isPeriodic == 97){
        while(1){
            thread_ThreadUtils_dispatchRunnable(threadJavaStack);
            pthread_testcancel();
        }
    }
    /*Mission Sequencer and Managed Thread*/
    else{
    	thread_ThreadUtils_dispatchRunnable(threadJavaStack);
	}
}

static void *startThread(void* arg);
void scj_multicore_thread_starter(int32 *sp){
	/*Get scj thread and its info object*/
	struct _javax_safetycritical_OSProcess_MyThread_c* thread = HEAP_REF((struct _javax_safetycritical_OSProcess_MyThread_c* ) (pointer ) sp[0], struct _javax_safetycritical_OSProcess_MyThread_c*);
	struct _javax_safetycritical_OSProcess_ThreadInfo_c* threadInfo = HEAP_REF((struct _javax_safetycritical_OSProcess_ThreadInfo_c* ) (pointer ) thread->info_f, struct _javax_safetycritical_OSProcess_ThreadInfo_c*);

	/*set thread args info*/
	struct thread_args* args = HEAP_REF((struct thread_args*) gc_allocateObject(sizeof(struct thread_args), 0), struct thread_args*);
	args->target = (Object*) (pointer) thread->target_f;
	args->id = thread->id_f;
	args->thread = thread;
	args->isPeriodic = threadInfo->isPeriodic_f;
	args->start = (long long) threadInfo->lsbstart_f << 32 | threadInfo->start_f;
	args->period = (long long) threadInfo->lsbperiod_f << 32 | threadInfo->period_f;
	args->memory = pthread_getspecific(key);

	/*get thread priority*/
	int priority = threadInfo->priority_f;

	/*Set thread attrs*/
	int policy = SCHED_FIFO;
	struct sched_param param;
	param.sched_priority = priority;
	pthread_attr_t t1_attr;
	pthread_attr_init(&t1_attr);

	/*Set scheduling policy*/
	if ( pthread_attr_setschedpolicy(&t1_attr, policy) != 0 ) {
		printf("psetpolicy errno: %d.\n",errno);
		return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	}

	/*set thread priority*/
	if ( pthread_attr_setschedparam(&t1_attr, &param) != 0 ){
		printf("psetschparam errno: %d.\n",errno);
		return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	}

	/*set pthread inheritance attribute*/
	if ( pthread_attr_setinheritsched(&t1_attr, PTHREAD_EXPLICIT_SCHED) != 0 ){
		printf("psetinherit errno: %d.\n",errno);
		return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	}

	/*set affinity*/
	cpu_set_t cs;
	CPU_ZERO(&cs);
	int *p = HEAP_REF((int* ) (pointer ) thread->processors_f, int*);
	int size = thread->sizeOfProcessor_f + 1;
	int i = 1;
	for(;i<size; i++){
		CPU_SET(p[i], &cs);
	}
	int ret = pthread_attr_setaffinity_np(&t1_attr, sizeof(cs), &cs);
	if(ret != 0){
		printf("pthread_attr_setaffinity_np errno: %d.\n",ret);
		return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	}

	/*Start thread*/
	pthread_t *thr = (pthread_t *) gc_allocateObject(sizeof(pthread_t), 0);
	thread->name_f = (uint32) (pointer) thr; /* hack: store the thread data in the object at some unused spot */

	if (pthread_create(HEAP_REF(thr, pthread_t *), &t1_attr, startThread, (void*)args) != 0) {
		printf("pcreate errno: %d.\n",errno);
		return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	}
}

#endif

#if defined(N_JAVA_LANG_THREAD_START)
#include <pthread.h>

int16 n_java_lang_Thread_join(int32 *sp) {
	struct _java_lang_Thread_c* thread = HEAP_REF((struct _java_lang_Thread_c* ) (pointer ) sp[0], struct _java_lang_Thread_c*);
	pthread_t *thr = HEAP_REF((pthread_t *)(pointer)thread->name_f, pthread_t *);

	if (pthread_join(*thr, 0) != 0) {
		return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
	}

	return -1;
}

extern int16 thread_ThreadUtils_dispatchRunnable(int32 *fp);
static void *startThread(void* arg) {
	#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
		scj_multicore_thread_executor(arg);
	#else
		int32 *threadJavaStack = HEAP_REF((int32 *) gc_allocateObject(16384, 0), int32 *);
		threadJavaStack[0] = (int32) (pointer) arg;
		thread_ThreadUtils_dispatchRunnable(threadJavaStack);
	#endif

	return 0;
}

int16 n_java_lang_Thread_start(int32 *sp) {
	#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
		scj_multicore_thread_starter(sp);
	#else
		struct _java_lang_Thread_c* thread = HEAP_REF((struct _java_lang_Thread_c* ) (pointer ) sp[0], struct _java_lang_Thread_c*);
		Object* target = (Object*) (pointer) thread->target_f;
		pthread_t *thr = (pthread_t *) gc_allocateObject(sizeof(pthread_t), 0);

		thread->name_f = (uint32) (pointer) thr; /* hack: store the thread data in the object at some unused spot */

		if (pthread_create(HEAP_REF(thr, pthread_t *), 0, startThread, target) != 0) {
			return initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var, JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
		}
	#endif

	return -1;
}
#endif

#if defined(N_JAVA_LANG_THREAD_INIT_) || defined(N_JAVA_LANG_THREAD_INIT_DEFAULT)
int16 n_java_lang_Thread_init_(int32 *sp) {
	struct _java_lang_Thread_c* thread = HEAP_REF((struct _java_lang_Thread_c* ) (pointer ) sp[0], struct _java_lang_Thread_c*);
	thread->target_f = (uint32) sp[1];
	return -1;
}
#endif

#if defined(N_JAVA_LANG_THREAD_INIT_DEFAULT)
int16 n_java_lang_Thread_init_default(int32 *sp)
{
	sp[1] = sp[0];
	n_java_lang_Thread_init_(sp);
	return -1;
}
#endif

#if defined(N_TEST_TESTCVAR1_GETIVAR)
/* getCVar
 * param :
 * return: int
 */
int32 ivar;

int16 n_test_TestCVar1_getIVar(int32 *sp)
{
	sp[0] = ivar;
	return -1;
}
#endif

#if defined(N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS)
int16 n_java_lang_Float_floatToRawIntBits(int32 *sp) {
	return -1;
}
#endif

#if defined(N_JAVA_LANG_SYSTEM_REGISTERNATIVES)
int16 n_java_lang_System_registerNatives(int32 *sp) {
	return -1;
}
#endif

#if defined(N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS)
int16 n_java_lang_Double_doubleToRawLongBits(int32 *sp) {
	return -1;
}
#endif

#if defined(N_SUN_SECURITY_ACTION_GETBOOLEANACTION_INIT_)
int16 n_sun_security_action_GetBooleanAction_init_(int32 *sp) {
	return -1;
}
#endif

#if defined(N_JAVA_LANG_SYSTEM_NANOTIME)
int16 n_java_lang_System_nanoTime(int32 *sp) {
	return -1;
}
#endif

#if defined(N_JAVA_LANG_SYSTEM_IDENTITYHASHCODE)
int16 n_java_lang_System_identityHashCode(int32 *sp) {
	return -1;
}
#endif

#if defined(N_TEST_TESTCVAR1_GETBVAR)
/* getCVar
 * param :
 * return: int
 */
int8 bvar;

int16 n_test_TestCVar1_getBVar(int32 *sp)
{
	sp[0] = bvar;
	return -1;
}
#endif

#if defined(N_TEST_TESTCVAR1_GETSVAR)
/* getCVar
 * param :
 * return: int
 */
int16 svar;

int16 n_test_TestCVar1_getSVar(int32 *sp)
{
	sp[0] = svar;
	return -1;
}
#endif

#if defined(N_TEST_TESTCVAR1_GETLVAR)
/* getCVar
 * param :
 * return: int
 */
#if defined(PC64)
unsigned long lvar;
#else
unsigned long long lvar;
#endif

int16 n_test_TestCVar1_getLVar(int32 *sp)
{
	sp[1] = lvar;
	sp[0] = lvar >> 32;
	return -1;
}
#endif

pointer stackPointer = 0;

#if defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED) && defined(VM_INTERRUPTDISPATCHER_INTERRUPT_USED)
extern void handleException(unsigned short classIndex);
#endif

#if defined(VM_PROCESS_EXECUTEWITHSTACK)
extern void set_stack_pointer();
#endif

#if defined(VM_CLOCKINTERRUPTHANDLER_HANDLE) || defined(VM_PROCESS_INITIALIZE)
static struct _vm_Process_c* currentProcess;
static struct _vm_Process_c* nextProcess;
static int32 *jsp;
#endif

void _transfer(void) {
#if defined(VM_CLOCKINTERRUPTHANDLER_HANDLE) || defined(VM_PROCESS_INITIALIZE)
	uint32 nextSpObject = nextProcess->sp_f;
	uint32 currentSpObject = currentProcess->sp_f;

	pointer* nextSp = (pointer*) ((unsigned char*) (pointer) nextSpObject + sizeof(Object));
	pointer* currentSp = (pointer*) ((unsigned char*) (pointer) currentSpObject + sizeof(Object));

	nextSp = HEAP_REF(nextSp, pointer*);
	currentSp = HEAP_REF(currentSp, pointer*);

	*currentSp++ = HEAP_UNREF(stackPointer, pointer);
	*currentSp = (pointer) HEAP_UNREF(jsp, int32 *);
	stackPointer = HEAP_REF(*nextSp, pointer);
#endif
}

#if defined(VM_CLOCKINTERRUPTHANDLER_HANDLE) || defined(VM_PROCESS_INITIALIZE)
extern void _yield(void);

int16 n_vm_Process_transfer(int32 *sp) {
	jsp = sp;
	currentProcess = HEAP_REF((struct _vm_Process_c* ) (pointer ) sp[0], struct _vm_Process_c*);
	nextProcess = HEAP_REF((struct _vm_Process_c* ) (pointer ) sp[1], struct _vm_Process_c*);

	_yield();

	return -1;
}
#endif

#if defined(VM_PROCESS_EXECUTEWITHSTACK)
static int32* stack;
extern pointer* get_stack_pointer(void);
extern int16 vm_Process_ProcessExecutor_run(int32 *fp);

static pointer *tcsp;
static pointer *csp;

static void executeWithStack(uint16 stackSize) {
#ifndef AVR
	if (sizeof(pointer) == 4) {
#endif
		csp = (pointer*) (stack + stackSize - 2);
		csp -= 14;
#ifndef AVR
	} else {
		unsigned long SP;
		SP = (unsigned long) (stack + stackSize - 3);
		while ((SP - sizeof(pointer)) % 16) {
			SP--;
		}
		csp = (pointer*) SP;
	}
#endif
	tcsp = get_stack_pointer();

	stackPointer = (pointer) csp;
	set_stack_pointer();

	vm_Process_ProcessExecutor_run(stack);

	stackPointer = (pointer) tcsp;
	set_stack_pointer();

	return;
}

int16 n_vm_Process_executeWithStack(int32 *sp) {
	Object* runnable = (Object*) (pointer) sp[0];
	uint16 stackSize;
	uint8 cropped = 0;
	pointer alignedStack;

	stack = HEAP_REF((int32* ) (pointer ) sp[1], int32*);

	stackSize = *((uint16*) stack + 1);

	stack++; /* Stack is an array. Scroll past header & count */

	alignedStack = (pointer)stack;
	/* Make stack be 4 byte aligned */
	while (alignedStack & 0x3) {
		alignedStack++;
		cropped++;
	};

	if (cropped)
	{
		stackSize--;
		stack = (int32*)alignedStack;
	}

	*stack = (int32) (pointer) runnable;

	executeWithStack(stackSize);

	return -1;
}
#endif

#if defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
extern int16 vm_InterruptDispatcher_interrupt(int32 *fp, int8 n);

int8 hvmClockReady = 0;
extern volatile uint8 systemTick;

int16 yieldToScheduler(int32 *sp) {
	if (systemTick > 0) {
		systemTick = 0;
		if (hvmClockReady) {
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined(N_JAVA_LANG_OBJECT_GETCLASS)
			if (scopeCount == 0) {
#endif
#if defined(VM_INTERRUPTDISPATCHER_INTERRUPT_USED)
				int16 excep;
				excep = vm_InterruptDispatcher_interrupt(sp, HVM_CLOCK);
				if (excep >= 0) {
					handleException(excep);
				}
#endif
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined(N_JAVA_LANG_OBJECT_GETCLASS)
			}
#endif
		}
	}
	return -1;
}
#endif
#if defined(N_VM_MONITOR_ATTACHMONITOR)
int16 n_vm_Monitor_attachMonitor(int32 *sp) {
	uint32* ptr;
	Object* target = (Object*) (pointer) sp[1];
	Object* monitor = (Object*) (pointer) sp[0];

	ptr = (uint32*) ((unsigned char*) HEAP_REF(target, unsigned char*) - 4);
	*ptr = (uint32) (pointer) monitor;
	return -1;
}
#endif

#if defined(N_VM_MONITOR_GETATTACHEDMONITOR)
int16 n_vm_Monitor_getAttachedMonitor(int32 *sp) {
	uint32* ptr;
	Object* target = (Object*) (pointer) sp[0];

	ptr = (uint32*) ((unsigned char*) HEAP_REF(target, unsigned char*) - 4);
	sp[0] = (int32)*ptr;
	return -1;
}
#endif

#if defined(N_JAVA_LANG_OBJECT_NOTIFY)
#if defined(VM_MONITOR_NOTIFY_USED)
extern int16 vm_Monitor_notify(int32 *fp, int32 target);
#endif
int16 n_java_lang_Object_notify(int32 *sp) {
#if defined(VM_MONITOR_NOTIFY_USED)
	return vm_Monitor_notify(sp, sp[0]);
#else
	return -1;
#endif
}
#endif

#if defined(N_JAVA_LANG_OBJECT_NOTIFYALL)
#if defined(VM_MONITOR_NOTIFYALL_USED)
extern int16 vm_Monitor_notifyAll(int32 *fp, int32 target);
#endif
int16 n_java_lang_Object_notifyAll(int32 *sp) {
#if defined(VM_MONITOR_NOTIFYALL_USED)
	return vm_Monitor_notifyAll(sp, sp[0]);
#else
	return -1;
#endif
}
#endif

#if defined(N_JAVA_LANG_OBJECT_WAIT_)
#if defined(VM_MONITOR_WAIT_USED)
extern int16 vm_Monitor_wait(int32 *fp, int32 target);
#endif
int16 n_java_lang_Object_wait_(int32 *sp) {
#if defined(VM_MONITOR_WAIT_USED)
	return vm_Monitor_wait(sp, sp[0]);
#else
	return -1;
#endif
}
#endif

extern void initNatives(void);
extern unsigned char initMethods(void);
extern unsigned char initClasses(void);

unsigned char vm_initialized;

#ifdef SUPPORT_LOADING
extern unsigned char loadApp();
#endif

unsigned char init_vm(void) {
#if defined(ENABLE_DEBUG)
	unsigned short index;
#endif
	vm_initialized = 0;
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined(N_JAVA_LANG_OBJECT_GETCLASS)
	head = 0;
#endif
	initNatives();
	initGC();

#if defined(ENABLE_DEBUG)
	breakPoints = (MethodLocation*)gc_allocateObject((sizeof(struct _methodLocation) * NUMBEROFMETHODS) - sizeof(Object), 0);
	breakPoints = HEAP_REF(breakPoints, MethodLocation* );
	for (index = 0; index < NUMBEROFMETHODS; index++)
	{
		(breakPoints + index) -> pc = -1;
	}
#endif

	if (initMethods()) {
#ifdef SUPPORT_LOADING
		loadApp();
#endif
		if (initClasses()) {
			vm_initialized = 1;
			return 1;
		}
	}
	return 0;
}

#if defined(JAVA_LANG_THROWABLE_INIT_)
#if defined(PRE_INITIALIZE_EXCEPTIONS)
extern ExceptionObject* exceptionObjects;

int16 initializeExceptions(int32* sp) {
	unsigned short index = 0;
	while (index < NUMRUNTIMEEXCEPTIONS) {
		ExceptionObject* next = exceptionObjects + index;

		if (handleNewClassIndex(sp, next->classId)) {
			enterMethodInterpreter(next->methodId, sp);
			next->exception = (Object*) (pointer) sp[0];
		} else {
			return initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR_var, JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
		}
		index++;
	}
	return -1;
}
#else
Object* outOfMemoryException;
int16 initializeExceptions(int32* sp) {
	if ((uint16) JAVA_LANG_OUTOFMEMORYERROR_var != (uint16) -1) {
		if (handleNewClassIndex(sp, JAVA_LANG_OUTOFMEMORYERROR_var)) {
			enterMethodInterpreter(JAVA_LANG_OUTOFMEMORYERROR_INIT__var, sp);
			outOfMemoryException = (Object*) (pointer) sp[0];
		} else {
			return JAVA_LANG_OUTOFMEMORYERROR_var;
		}
	}
	return -1;
}
#endif
#else
int16 initializeExceptions(int32* sp) {
	return -1;
}
#endif
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
int16 initializeStringConstant(const ConstantInfo* constant, int32* sp);
extern ConstantInfo *constants;
extern Object* stringConstants[];
int16 initializeConstants(int32* sp) {
#if defined(PRE_INITIALIZE_CONSTANTS)
	unsigned short index = 0;
	while (index < NUMBEROFCONSTANTS_var) {
		const ConstantInfo* constant;

		constant = &constants[index];

		if (pgm_read_byte(&constant->type) == CONSTANT_STRING) {
			int16 result = initializeStringConstant(constant, sp);
			if (result != -1) {
				return result;
			}
		}
		index++;
	}
#endif
	return -1;
}

int16 initializeStringConstant(const ConstantInfo* constant, int32* sp) {
	uint16 stringID = pgm_read_dword(&constant->value) >> 16;
	if (stringConstants[stringID] == 0) {
		Object *stringObject;

		uint16 length = pgm_read_dword(&constant->value) & 0xffff;
		stringObject = createStringObject(length,
				(const char *) pgm_read_pointer(&constant->data, const void**),
				sp);

		if (stringObject != 0) {
			stringConstants[stringID] = stringObject;
		} else {
			return JAVA_LANG_OUTOFMEMORYERROR_var;
		}
	}
	return -1;
}
#endif

#if defined(INVOKECLASSINITIALIZERS)
static int16 invokeClassInitializer(unsigned short methodIndex, int32* sp) {
	int16 excep;
	excep = enterMethodInterpreter(methodIndex, sp);
	if (excep > -1) {
		return excep;
	}
	return -1;
}

extern const short* classInitializerSequence;

int16 invokeClassInitializers(int32* sp) {
	unsigned short current = 0;

	while (current < NUMBEROFCLASSINITIALIZERS_var) {
		int16 excep;
		excep = invokeClassInitializer(
				pgm_read_word(classInitializerSequence + current), sp);
		if (excep > -1) {
			return excep;
		}
		current++;
	}
	return -1;
}
#endif

#if defined(N_TEST_TESTINVOKESTATIC2_APIDEMO1)
int16 n_test_TestInvokeStatic2_ApiDemo1(int32 *sp) {
	return -1;
}
#endif

#if defined(VM_CLOCKINTERRUPTHANDLER_INITIALIZE) || defined(VM_PROCESS_INIT__USED) || defined(VM_REALTIMECLOCK_GETREALTIMECLOCK_USED) || defined(REFLECT_CLASSINFO_GETCLASSINFOFROMARCHITECTURE) || defined(REFLECT_METHODINFO_GETMETHODINFO) || defined(REFLECT_STATICREFINFO_GETOFFSETS)
#define X86_64 1
#define X86_32 2
#define CR16_C 3
#define ATMEGA2560 4

#if defined(PC32)
unsigned char architecture = X86_32;
#else
#if defined(PC64)
unsigned char architecture = X86_64;
#else
#if defined(CR16C)
unsigned char architecture = CR16_C;
#else
#if defined(ATMEGA2560)
unsigned char architecture = ATMEGA2560;
#else
#error Unsupported architecture for Tasks
#endif
#endif
#endif
#endif
#endif

#if defined(REFLECT_OBJECTINFO_GETADDRESS)
int16 n_reflect_ObjectInfo_getAddress(int32 *sp) {
	return -1;
}
#endif

#ifdef N_DEVICES_SYSTEM_RESETMEMORY
extern uint32 HVMfree;
int16 n_devices_System_resetMemory(int32 *sp) {
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_OBJECT_GETCLASS) || defined(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS) || defined(N_JAVA_LANG_OBJECT_GETCLASS)
	head = 0;
#endif
	initGC();

	#if defined(JAVAX_SAFETYCRITICAL_LAUNCHMULTICORE_INIT_)
		HEAP_REF(pthread_getspecific(key),VMMemory*)->free = JAVA_STACK_SIZE;
	#else
        HEAP_REF(currentMemoryArea,VMMemory*)->free = JAVA_STACK_SIZE;
	#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
	initializeConstants(sp);
#endif
#if defined(INVOKECLASSINITIALIZERS)
	invokeClassInitializers(sp);
#endif
	initializeExceptions(sp);
	return -1;
}
#endif

#if defined(JAVA_LANG_THROWABLE_INIT_)
extern const char* getClassName(unsigned short classIndex);
extern const char* getMethodName(unsigned short methodIndex);

void reportStackTraceIntro(unsigned short classIndex) {
	printROMStr("Exception in thread \"\" ");
	printROMStr(getClassName(classIndex));
	printROMStr("\n");
}

void reportStackTraceElement(unsigned short methodIndex, unsigned short pc) {
	printROMStr("   at ");
	printROMStr(getMethodName(methodIndex));
	printROMStr("(:");
	printShort(pc);
	printROMStr(")\n");
}
#endif

#if defined(N_REFLECT_UNBOXING_REPORTBOOLEAN_USED)
/* reportBoolean
 * param : boolean
 * return: void
 */
static unsigned char booleanValue;
int16 n_reflect_Unboxing_reportBoolean(int32 *sp) {
	booleanValue = sp[0];
	return -1;
}
#endif

#if defined(N_REFLECT_UNBOXING_REPORTBYTE_USED)
/* reportByte
 * param : byte
 * return: void
 */
static unsigned char byteValue;
int16 n_reflect_Unboxing_reportByte(int32 *sp) {
	byteValue = sp[0];
	return -1;
}
#endif

#if defined(N_REFLECT_UNBOXING_REPORTCHARACTER_USED)
/* reportCharacter
 * param : char
 * return: void
 */
static unsigned char characterValue;
int16 n_reflect_Unboxing_reportCharacter(int32 *sp) {
	characterValue = sp[0];
	return -1;
}
#endif

#if defined(N_REFLECT_UNBOXING_REPORTINT_USED)
/* reportInt
 * param : int
 * return: void
 */
static int32 intValue;
int16 n_reflect_Unboxing_reportInt(int32 *sp) {
	intValue = sp[0];
	return -1;
}
#endif

#if defined(N_REFLECT_UNBOXING_REPORTLONG_USED)
/* reportLong
 * param : long
 * return: void
 */
static int32 longValueLsb;
static int32 longValueMsb;
int16 n_reflect_Unboxing_reportLong(int32 *sp) {
	longValueLsb = sp[0];
	longValueMsb = sp[1];
	return -1;
}
#endif

#if defined(N_REFLECT_UNBOXING_REPORTSHORT_USED)
/* reportShort
 * param : short
 * return: void
 */
static int32 shortValue;
int16 n_reflect_Unboxing_reportShort(int32 *sp) {
	shortValue = sp[0];
	return -1;
}
#endif

#if defined(N_JAVA_LANG_REFLECT_METHOD_INVOKE) || defined(N_JAVA_LANG_REFLECT_CONSTRUCTOR_NEWINSTANCE)
#define METHODINVOKATIONSTACKSIZE 1024

extern int16 reflect_Unboxing_unbox(int32 *fp);
extern int16 reflect_Unboxing_boxBoolean(int32 *fp);
extern int16 reflect_Unboxing_boxByte(int32 *fp);
extern int16 reflect_Unboxing_boxShort(int32 *fp);
extern int16 reflect_Unboxing_boxCharacter(int32 *fp);
extern int16 reflect_Unboxing_boxInteger(int32 *fp);
extern int16 reflect_Unboxing_boxLong(int32 *fp);

extern int16 enterMethodInterpreter(unsigned short methodNumber, int32* sp);

static int32 fp[METHODINVOKATIONSTACKSIZE];

int16 n_java_lang_reflect_Method_invoke(int32 *sp) {
	Object *method = (Object *) (pointer) sp[0];
	uint32 *args = (uint32 *) HEAP_REF((pointer )sp[2], uint32 *);

	uint16 methodIndex;
	uint16 numArgs;
	uint16 index;
	int16 returnValue;

	methodIndex = *(HEAP_REF(method, uint16*) + 1);
	numArgs = methods[methodIndex].numArgs;
	args++;
	fp[0] = sp[1];
	for (index = 0; index < numArgs; index++) {
		if (*args != 0) {
			uint16 classIndex;
			classIndex = getClassIndex((Object*) (pointer) (*args));
			fp[index + 1] = *args;
			reflect_Unboxing_unbox(&fp[index + 1]);
			switch (classIndex) {
				case JAVA_LANG_INTEGER:
				fp[index + 1] = intValue;
				break;
				case JAVA_LANG_BYTE:
				fp[index + 1] = byteValue;
				break;
				case JAVA_LANG_SHORT:
				fp[index + 1] = shortValue;
				break;
				case JAVA_LANG_BOOLEAN:
				fp[index + 1] = booleanValue;
				break;
				case JAVA_LANG_LONG:
				fp[index + 1] = longValueLsb;
				fp[index + 2] = longValueMsb;
				index++;
				break;
				case JAVA_LANG_CHARACTER:
				fp[index + 1] = characterValue;
				break;
			}
		}
		args++;
	}
	returnValue = enterMethodInterpreter(methodIndex, &fp[0]);
	if (returnValue == -1) {
		unsigned char numReturnArgs = methods[methodIndex].minfo & 0x3;
		if (numReturnArgs > 0) {
			switch ((methods[methodIndex].minfo >> 2) & 0xF) {
				case 0:
				reflect_Unboxing_boxBoolean(&fp[0]);
				break;
				case 1:
				reflect_Unboxing_boxByte(&fp[0]);
				break;
				case 2:
				reflect_Unboxing_boxShort(&fp[0]);
				break;
				case 3:
				reflect_Unboxing_boxCharacter(&fp[0]);
				break;
				case 4:
				reflect_Unboxing_boxInteger(&fp[0]);
				break;
				case 5:
				reflect_Unboxing_boxLong(&fp[0]);
				break;
			}
		}
		sp[0] = fp[0];
	} else {
		if (JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION != -1) {
#if defined(JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION_INIT_)
			unsigned short dobjectSize = pgm_read_word(&classes[JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION].dobjectSize) >> 3;
			unsigned short pobjectSize = pgm_read_word(&classes[JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION].pobjectSize) >> 3;
			Object* ite;
			ite = gc_allocateObject(dobjectSize, pobjectSize);
			setClassIndex(ite, (unsigned short) JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION);
			sp[0] = (int32) (pointer) ite;
			sp[1] = fp[0];
			enterMethodInterpreter(JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION_INIT_, sp);
			return JAVA_LANG_REFLECT_INVOCATIONTARGETEXCEPTION;
#endif
		}
	}
	return returnValue;
}
#endif

#if defined(N_JAVA_LANG_CLASS_GETMETHOD)
int16 n_java_lang_Class_getMethod(int32 *sp) {
	Object *class = (Object*) (pointer) sp[0];
	uint16 classIndex = *(unsigned short *) ((unsigned char*) HEAP_REF(class, Object*) + sizeof(Object));
	const char* className;

	while (1) {
		uint16 i, lengthClassName;

		className = classes[classIndex].name;
		lengthClassName = _strlen(className);

		for (i = 0; i < NUMBEROFMETHODS; i++) {
			const char* src = methods[i].name;
			const char* dst = className;
			uint16 count = 0;
			uint16 lengthMethodName = _strlen(src);
			while ((count < lengthClassName) && (count < lengthMethodName)) {
				if (*src != *dst) {
					break;
				}
				src++;
				dst++;
				count++;
			}
			if (count == lengthClassName) {
				unsigned char* strObj = (unsigned char*) (pointer) sp[1];
				dst = getCString(strObj);
				if (*src != '.') {
					count = lengthMethodName + 1;
				}
				src++;
				count++;
				while (count < lengthMethodName) {
					if (*src != *dst) {
						break;
					}
					src++;
					dst++;
					count++;
				}
				if (count == lengthMethodName) {
					uint16* array = (uint16 *) HEAP_REF((pointer )sp[2], uint16 *);
					uint16 length = 0;
					if (array != 0) {
						length = *(array + 1);
					}
					if (length == methods[i].numArgs) {
						if (JAVA_LANG_REFLECT_METHOD != -1) {
							Object *method;
							uint16 *ptr;
							unsigned short dobjectSize = pgm_read_word(&classes[JAVA_LANG_REFLECT_METHOD].dobjectSize) >> 3;
							unsigned short pobjectSize = pgm_read_word(&classes[JAVA_LANG_REFLECT_METHOD].pobjectSize) >> 3;

							method = gc_allocateObjectInArea(dobjectSize, pobjectSize);
							setClassIndex(method, (unsigned short) JAVA_LANG_REFLECT_METHOD);
							ptr = method + 1;
							*HEAP_REF(ptr, uint16 *) = i;
							sp[0] = (int32) (pointer) method;
							return -1;
						}
					}
				}
			}
		}
		if (classes[classIndex].superClass == -1) {
			return initializeException(sp, JAVA_LANG_NOSUCHMETHODEXCEPTION, JAVA_LANG_NOSUCHMETHODEXCEPTION_INIT_);
		} else {
			classIndex = classes[classIndex].superClass;
		}
	}
}
#endif

#if defined(N_JAVA_LANG_CLASS_GETCONSTRUCTOR)
int16 n_java_lang_Class_getConstructor(int32 *sp) {
	Object *cls = HEAP_REF((Object* )(pointer )sp[0], Object*);
	Object *argsarray = HEAP_REF((Object* )(pointer )sp[1], Object*);

	uint16 classIndex = *(unsigned short *) ((unsigned char*) cls
			+ sizeof(Object));
	uint16 count = *((uint16*) argsarray + 1);

	uint16 i;

	for (i = 0; i < NUMBEROFMETHODS; i++) {
		const MethodInfo* methodInfo = &methods[i];
		if ((methodInfo->classIndex >> 1) == classIndex) {
			unsigned char minfo = pgm_read_byte(&methodInfo->minfo) >> 6;
			if ((minfo & 0x1) && (methodInfo->numArgs == count)) {
				unsigned short dobjectSize =
				pgm_read_word(
						&classes[JAVA_LANG_REFLECT_CONSTRUCTOR].dobjectSize)
				>> 3;
				unsigned short pobjectSize =
				pgm_read_word(
						&classes[JAVA_LANG_REFLECT_CONSTRUCTOR].pobjectSize)
				>> 3;

				Object* constructor = gc_allocateObjectInArea(dobjectSize,
						pobjectSize);

				if (constructor != 0) {
					setClassIndex(constructor,
							(unsigned short) JAVA_LANG_REFLECT_CONSTRUCTOR);
					*(unsigned short *) ((unsigned char*) HEAP_REF(constructor,
									Object*) + sizeof(Object)) = i;
					*(unsigned short *) ((unsigned char*) HEAP_REF(constructor,
									Object*) + sizeof(Object) + sizeof(unsigned short)) =
					classIndex;
					sp[0] = (int32) (pointer) constructor;
				}
				return -1;
			}
		}
	}
	sp[0] = 0;
	return -1;
}
#endif

#if defined(N_JAVA_LANG_REFLECT_CONSTRUCTOR_NEWINSTANCE)
int16 n_java_lang_reflect_Constructor_newInstance(int32 *sp) {
	uint16 classIndex;
	Object* constructor = HEAP_REF((Object* )(pointer )sp[0], Object*);
	uint32 *args = (uint32 *) HEAP_REF((pointer )sp[1], uint32 *);
	classIndex = *(unsigned short *) ((unsigned char*) constructor + sizeof(Object) + sizeof(unsigned short));

	handleNewClassIndex(sp, classIndex);
	sp[2] = (int32) (pointer) HEAP_UNREF(args, uint32 *);
	sp[1] = sp[0];
	sp[0] = (int32) (pointer) HEAP_UNREF(constructor, Object*);
	return n_java_lang_reflect_Method_invoke(sp);
}
#endif

#if defined(N_JAVA_LANG_CLASS_FORNAME)
/* forName
 * param : java.lang.String
 * return: java.lang.Class
 */
int16 n_java_lang_Class_forName(int32 *sp) {
	unsigned char* strObj = (unsigned char*) (pointer) sp[0];
	char* str = getCString(strObj);
	uint16 i;

	for (i = 0; i < NUMBEROFCLASSES; i++) {
		if (_streq(str, (char*) classes[i].name)) {
			sp[0] = (int32) (pointer) getClass(i);
			return -1;
		}
	}
	sp[0] = 0;
	return -1;
}
#endif

void unimplemented_native_function(uint16 methodID) {
	printStr("warning: unimplemented function executed [");
	printShort(methodID);
	printStr("]\n");
}

#if defined(ENABLE_DEBUG)

int32 requestResponseChannel;
static int eventChannel;

void breakPointHit(unsigned short methodNumber, unsigned short pc);
unsigned char awaitCommandFromDebugger(int32* fp, unsigned short methodNumber, unsigned short pc);

void sendOnChannel(int channelID, unsigned char event);

extern void closeChannel(int channelID);
extern int32 connectToChannel(int32 channelID);
extern void writeToDebugger(int channelID, const unsigned char *buf, unsigned short length);
extern void readFromDebugger(unsigned char *buf, unsigned short length);
extern void stopProgram(int exitValue);
extern void closeStdout(void);

void sendTerminatedEvent(void);

void connectToDebugger(void)
{
	requestResponseChannel = connectToChannel(REQUESTRESPONSECHANNEL);
	eventChannel = connectToChannel(EVENTCHANNEL);
}

void disconnectFromDebugger(void)
{
	sendTerminatedEvent();
	closeChannel(requestResponseChannel);
	closeChannel(eventChannel);
	closeStdout();
}

void sendTerminatedEvent(void)
{
	sendOnChannel(eventChannel, TERMINATED_EVENT);
}

void sendStartEvent(void)
{
	sendOnChannel(eventChannel, START_EVENT);
}

void sendOnChannel(int channelID, unsigned char event)
{
	unsigned char buf = event;
	writeToDebugger(channelID, &buf, 1);
}

void sendMethodNumberAndPC(int channelID, unsigned short methodNumber, unsigned short pc)
{
	unsigned char buf;
	buf = methodNumber & 0xFF;
	writeToDebugger(channelID, &buf, 1);
	buf = methodNumber >> 8;
	writeToDebugger(channelID, &buf, 1);

	buf = pc & 0xFF;
	writeToDebugger(channelID, &buf, 1);
	buf = pc >> 8;
	writeToDebugger(channelID, &buf, 1);
}

void readMethodNumberAndPC(unsigned short *methodNumber, unsigned short *pc)
{
	unsigned char byte;

	readFromDebugger(&byte, 1);
	*methodNumber = byte;
	readFromDebugger(&byte, 1);
	*methodNumber |= (byte << 8);

	readFromDebugger(&byte, 1);
	*pc = byte;
	readFromDebugger(&byte, 1);
	*pc |= (byte << 8);
}

void addBreakpoint(unsigned short methodNumber, unsigned short pc)
{
	(breakPoints + methodNumber) -> pc = pc;
}

void removeBreakpoint(unsigned short methodNumber, unsigned short pc)
{
	(breakPoints + methodNumber) -> pc = -1;
}

unsigned char stepState = STEPCONTINUE;
unsigned short targetMethod;
unsigned short targetPC;
void checkBreakpoint(int32* fp, unsigned short methodNumber, unsigned short pc)
{
	unsigned char shouldBreak = 0;
	switch (stepState)
	{
		case STEPINTO:
		if (methodNumber != targetMethod)
		{
			shouldBreak = 1;
		}
		break;
		case STEPRETURN:
		if ((methodNumber == targetMethod) && (pc == targetPC))
		{
			shouldBreak = 1;
		}
		break;
		case STEPOVER:
		if ((methodNumber == targetMethod) && (pc != targetPC))
		{
			shouldBreak = 1;
		}
		break;
		case STEPCONTINUE:
		if ((breakPoints + methodNumber) -> pc == pc)
		{
			shouldBreak = 1;
		}
		break;
	}
	if (shouldBreak)
	{
		breakPointHit(methodNumber, pc);
		while (awaitCommandFromDebugger(fp, methodNumber, pc) != RESUME_EVENT) {;}
	}
}

extern unsigned short popStackFrame(int32** fp, int32** sp, const MethodInfo* currentMethod, unsigned short *pc);

unsigned char awaitCommandFromDebugger(int32* fp, unsigned short methodNumber, unsigned short pc)
{
	unsigned char event;

	readFromDebugger(&event, 1);

	switch (event)
	{
		case TERMINATE_EVENT:
		{
			printROMStr("Terminate on request from debugger");
			stopProgram(1);
		}
		case BREAKPOINT_ADD_EVENT:
		{
			unsigned short methodNumber;
			unsigned short pc;

			readMethodNumberAndPC(&methodNumber, &pc);

			addBreakpoint(methodNumber, pc);
			break;
		}
		case STEP_EVENT:
		{
			readFromDebugger(&stepState, 1);

			if (stepState != STEPCONTINUE)
			{
				readMethodNumberAndPC(&targetMethod, &targetPC);
			}

			event = RESUME_EVENT;
			break;
		}
		case RESUME_EVENT:
		{
			break;
		}
		case BREAKPOINT_REMOVE_EVENT:
		{
			unsigned short methodNumber;
			unsigned short pc;

			readMethodNumberAndPC(&methodNumber, &pc);

			removeBreakpoint(methodNumber, pc);
			break;
		}
		case GET_STACKFRAMES_EVENT:
		{
			int32* sp;
			const MethodInfo* currentMethod;

			sendOnChannel(requestResponseChannel, STACKFRAMES_START_EVENT);
			sendMethodNumberAndPC(requestResponseChannel, methodNumber, pc);

			currentMethod = &methods[methodNumber];

			while ((methodNumber = popStackFrame(&fp, &sp, currentMethod, &pc)) != 0)
			{
				methodNumber--;
				sendOnChannel(requestResponseChannel, STACKFRAMES_START_EVENT);
				sendMethodNumberAndPC(requestResponseChannel, methodNumber, pc);
				currentMethod = &methods[methodNumber];
			}
			sendOnChannel(requestResponseChannel, STACKFRAMES_END_EVENT);
			break;
		}
		case GET_STACKVALUE_EVENT:
		{
			unsigned short index;
			unsigned char count;
			int32 value;

			readFromDebugger(&count, 1);
			index = count << 8;
			readFromDebugger(&count, 1);
			index |= count;

			readFromDebugger(&count, 1);

			if (count > 4)
			{
				value = fp[index + 1];
			}
			else
			{
				value = 0;
			}

			while (count > 4)
			{
				sendOnChannel(requestResponseChannel, value & 0xff);
				value = value >> 8;
				count--;
			}
			value = fp[index];
			while (count > 0)
			{
				sendOnChannel(requestResponseChannel, value & 0xff);
				value = value >> 8;
				count--;
			}
			break;
		}
		default:
		{
			printROMStr("Unexpected event from debugger");
			stopProgram(1);
		}
	}
	return event;
}

void breakPointHit(unsigned short methodNumber, unsigned short pc)
{
	sendOnChannel(eventChannel, BREAKPOINT_HIT_EVENT);

	sendMethodNumberAndPC(eventChannel, methodNumber, pc);
}

#endif

void unimplemented(int16 mid) {
	if (mid > 0) {
		printROMStr("unimplemented ");
		printShort(mid);
		printROMStr("\n");
	}
}
