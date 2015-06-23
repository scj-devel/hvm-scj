/*
 * methodinterpreter.c
 *
 *  Created on: Sep 16, 2010
 *      Author: user
 */
#include "methodinterpreter.h"
#include "gc.h"
#include "ostypes.h"
#include "classes.h"
#include "methods.h"
#include "rom_heap.h"
#include "rom_access.h"

#define REFERENCE_TYPE 12

extern ClassInfo *classes;
extern MethodInfo *methods;
extern ConstantInfo *constants;
extern unsigned char *classData;

extern Object* getClass(unsigned short classIndex);

#if defined(JAVA_LANG_THROWABLE_INIT_)
extern void reportStackTraceElement(unsigned short methodIndex,
		unsigned short pc);
extern void reportStackTraceIntro(unsigned short classIndex);
#endif

#if defined(PUTHWFIELD_OPCODE_USED)
extern void writeLongToIO(pointer address, unsigned short offset, int32 msb, int32 lsb);
extern void writeIntToIO(pointer address, unsigned short offset, int32 lsb);
extern void writeShortToIO(pointer address, unsigned short offset, unsigned short lsb);
extern void writeByteToIO(pointer address, unsigned short offset, unsigned char lsb);
extern void writeBitToIO(pointer address, unsigned short offset, unsigned char bit);
#endif

#if defined(GETHWFIELD_OPCODE_USED)
extern void readLongFromIO(pointer address, unsigned short offset, int32* msb, int32* lsb);
extern int32 readIntFromIO(pointer address, unsigned short offset);
extern unsigned short readShortFromIO(pointer address, unsigned short offset);
extern unsigned char readByteFromIO(pointer address, unsigned short offset);
extern unsigned char readBitFromIO(pointer address, unsigned short offset);
#endif

unsigned char getElementSize(unsigned short classIndex);
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
int32 imul(int32 a, int32 b) _NOINLINE_;
#endif

#if (defined(PC64) || defined(PC32) || defined(CR16C)) && defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
extern int16 yieldToScheduler(int32 *sp);
#endif

#if defined(ENABLE_DEBUG)
extern void checkForRequests(int32* fp);
extern void checkBreakpoint(int32* fp, unsigned short methodNumber, unsigned short pc);
#endif

extern void printStr(const char* str) _NOINLINE_;
extern void printByte(unsigned char byte);

extern unsigned short getClassIndex(Object* obj);
extern void setClassIndex(Object* obj, unsigned short classIndex);

unsigned char* createArrayFromElementSize(unsigned short classIndex,
		unsigned char elementSize, uint16 count FLASHARG(uint8 flash));
unsigned char handleNewClassIndex(int32* sp, unsigned short classIndex);

#if defined(WIDE_OPCODE_USED)
static signed short handleWide(int32* fp, int32* sp, unsigned char *method_code) _NOINLINE_;
#endif

unsigned char* createArray(unsigned short classIndex,
		uint16 count FLASHARG(uint8 flash)) _NOINLINE_;
unsigned char checkImplements(Object* object, unsigned short interfaceIndex) _NOINLINE_;
int32 lmul32(int32* sp, uint32 xmsb, uint32 xlsb, uint32 ymsb, uint32 ylsb);
int32 idiv(int32 a, int32 b) _NOINLINE_;

#if defined(IREM_OPCODE_USED) || defined(IMOD_USED)
int32 imod(int32 a, int32 b) _NOINLINE_;
#endif

#if defined(LSHL_OPCODE_USED) || defined(HANDLELSHL_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED)
static void lshl(uint32* xmsb, uint32* xlsb, unsigned char value);
#endif

#if defined(LSHR_OPCODE_USED) || defined(LUSHR_OPCODE_USED) || defined(HANDLELSHR_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED)
static void lshr(uint32* msi, uint32* lsi, unsigned char value);
#endif

#if defined(LCMP_OPCODE_USED) || defined(HANDLELCMP_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED)
static int32 lcmp(int32 value1msb, uint32 value1lsb, int32 value2msb,
		uint32 value2lsb);
#endif

void neg(uint32* msb, uint32* lsb) _NOINLINE_;
int16 initializeException(int32* sp, int16 exceptionClass,
		int16 exceptionInitMethod) _NOINLINE_;

#if defined(TABLESWITCH_OPCODE_USED)
static unsigned short handleTableSwitch(unsigned short pc, unsigned char* ptr, int32 index) _NOINLINE_;
#endif

#if defined(LOOKUPSWITCH_OPCODE_USED)
static unsigned short handleLookupSwitch(unsigned short pc, unsigned char* ptr, int32 key) _NOINLINE_;
#endif

void handleLongOperator(unsigned char code, int32* sp) _NOINLINE_;

#if defined(LNEG_OPCODE_USED)
static void handleLNEG(int32* sp) _NOINLINE_;
#endif

#if defined(NEW_OPCODE_USED)
static unsigned char handleNew(int32* sp, unsigned char *method_code) _NOINLINE_;
#endif

#if defined(MULTIANEWARRAY_OPCODE_USED)
static unsigned char handleMultianewarray(int32* sp, unsigned char *method_code) _NOINLINE_;
#endif

#if defined(JAVA_LANG_THROWABLE_INIT_)
unsigned short handleAthrow(const MethodInfo* method, unsigned short classIndex,
		unsigned short pc) _NOINLINE_;
#endif

#if defined(PUTFIELD_OPCODE_USED)
static unsigned char handlePutField(unsigned char* method_code, int32* sp) _NOINLINE_;
#endif

#if defined(PUTHWFIELD_OPCODE_USED)
static unsigned char handlePutHWField(unsigned char* method_code, int32* sp) _NOINLINE_;
#endif

#if defined(GETSTATIC_OPCODE_USED)
static unsigned short handleGetStatic(unsigned char* method_code, int32* sp) _NOINLINE_;
#endif

#if defined(PUTSTATIC_OPCODE_USED)
static unsigned short handlePutStatic(unsigned char* method_code, int32* sp) _NOINLINE_;
#endif

#if defined(GETFIELD_OPCODE_USED)
static signed char handleGetField(unsigned char* method_code, int32* sp) _NOINLINE_;
#endif

#if defined(GETHWFIELD_OPCODE_USED)
static signed char handleGetHWField(unsigned char* method_code, int32* sp) _NOINLINE_;
#endif

#if defined(AASTORE_OPCODE_USED) || defined(FASTORE_OPCODE_USED) || defined(BASTORE_OPCODE_USED) || defined(CASTORE_OPCODE_USED) || defined(SASTORE_OPCODE_USED) || defined(LASTORE_OPCODE_USED) || defined(IASTORE_OPCODE_USED)
static unsigned char handleAStore(int32* sp, unsigned char *method_code) _NOINLINE_;
#endif

#if defined(FALOAD_OPCODE_USED) || defined(AALOAD_OPCODE_USED) || defined(BALOAD_OPCODE_USED) || defined(CALOAD_OPCODE_USED) || defined(SALOAD_OPCODE_USED) || defined(LALOAD_OPCODE_USED) || defined(IALOAD_OPCODE_USED)
static unsigned char handleALoad(int32* sp, unsigned char *method_code) _NOINLINE_;
#endif

#if defined(DUP_X2_OPCODE_USED)
static void handleDupX2(int32* sp) _NOINLINE_;
#endif

#if defined(DUP2_X1_OPCODE_USED)
static void handleDup2X1(int32* sp) _NOINLINE_;
#endif

#if defined(DUP2_X2_OPCODE_USED)
static void handleDup2X2(int32* sp) _NOINLINE_;
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED)
static unsigned char handleLDC(int32* sp, unsigned char *method_code) _NOINLINE_;
#endif

#if defined(GC_GARBAGECOLLECTOR_NEWBARRIER_USED)
extern int16 gc_GarbageCollector_newBarrier(int32 *fp, int32 ref);
#endif

int32 handleLCMP(int32* sp) _NOINLINE_;
#if defined(INVOKE_CLONE_ONARRAY_USED) || defined(HANDLECLONEONARRAY_USED)
signed char handleCloneOnArray(int32* sp)
_NOINLINE_;
#endif
void handleLSHL(int32* sp) _NOINLINE_;
void handleLSHR(int32* sp) _NOINLINE_;
unsigned char handleLMULLDIVLREM(int32* sp, unsigned char code) _NOINLINE_;
static int32* pushStackFrame(unsigned short maxLocals,
		unsigned short currentMethodNumber, unsigned short pc, int32* fp,
		int32* sp);
unsigned short popStackFrame(int32** fp, int32** sp, const MethodInfo* method,
		unsigned short *pc);
unsigned char isSubClassOf(unsigned short subClass, unsigned short superClass);
unsigned char handleLDCWithIndex(int32* sp, unsigned short index);

#if (defined(AASTORE_OPCODE_USED) || defined(FASTORE_OPCODE_USED) || defined(BASTORE_OPCODE_USED) || defined(CASTORE_OPCODE_USED) || defined(SASTORE_OPCODE_USED) || defined(LASTORE_OPCODE_USED) || defined(IASTORE_OPCODE_USED) || defined(PUTFIELD_OPCODE_USED)) && defined(FLASHSUPPORT)
static unsigned char write_rom_data(unsigned char *object, unsigned char size, unsigned char isReference, unsigned char count, int32 msb, int32 lsb);
#endif

unsigned char handleMonitorEnterExit(Object* lockObj, unsigned char isEnter,
		int32* sp, const char* fromMethod);

#if defined(JAVA_LANG_THROWABLE_INIT_)
#define STACK_TRACE_SIZE 32
static unsigned short stackTrace[STACK_TRACE_SIZE];
static unsigned char stackTraceTop;
static void addStackElement(const MethodInfo *method, int32 pc) _NOINLINE_;
#endif

static int32 methodInterpreter(unsigned short currentMethodNumber, int32* fp) {
	unsigned char *method_code;
	int32* sp;
	unsigned char numArgs = 0;
	unsigned short callee;
	const MethodInfo* currentMethod;

	start: {
		currentMethod = &methods[currentMethodNumber];
		method_code = (unsigned char *) pgm_read_pointer(&currentMethod->code,
				unsigned char**);
		sp = &fp[pgm_read_word(&currentMethod->maxLocals) + 2]; /* make room for local VM state on the stack */
	}
	loop: while (1) {
		unsigned char code = pgm_read_byte(method_code);
#if defined(ENABLE_DEBUG)
		checkBreakpoint(fp, currentMethodNumber, method_code - (unsigned char *) pgm_read_pointer(&currentMethod->code, unsigned char**));
#endif
		switch (code) {
#if defined(ICONST_M1_OPCODE_USED) || defined(ICONST_0_OPCODE_USED) || defined(ICONST_1_OPCODE_USED) || defined(ICONST_2_OPCODE_USED) || defined(ICONST_3_OPCODE_USED) || defined(ICONST_4_OPCODE_USED) || defined(ICONST_5_OPCODE_USED)
		case ICONST_M1_OPCODE:
		case ICONST_0_OPCODE:
		case ICONST_1_OPCODE:
		case ICONST_2_OPCODE:
		case ICONST_3_OPCODE:
		case ICONST_4_OPCODE:
		case ICONST_5_OPCODE:
			*sp++ = code - ICONST_0_OPCODE;
			method_code++;
			continue;
#endif
#if defined(FCONST_0_OPCODE_USED) || defined(FCONST_1_OPCODE_USED) || defined(FCONST_2_OPCODE_USED) 
			case FCONST_0_OPCODE:
			case FCONST_1_OPCODE:
			case FCONST_2_OPCODE: {
				float f = (float) (code - FCONST_0_OPCODE);
				*(float*) sp = f;
				sp++;
				method_code++;
			}
			continue;
#endif
#if defined(ASTORE_OPCODE_USED) || defined(ISTORE_OPCODE_USED) || defined(FSTORE_OPCODE_USED)
		case ASTORE_OPCODE:
		case FSTORE_OPCODE:
		case ISTORE_OPCODE: {
			unsigned char byteVal = pgm_read_byte(++method_code);
			fp[byteVal] = *(--sp);
			method_code++;
		}
			continue;
#endif
#if defined(LSTORE_OPCODE_USED) || defined(DSTORE_OPCODE)
		case DSTORE_OPCODE:
		case LSTORE_OPCODE: {
			unsigned char byteVal;
			int32 lsb = *(--sp);
			int32 msb = *(--sp);
			byteVal = pgm_read_byte(++method_code);
			fp[byteVal] = msb;
			fp[byteVal + 1] = lsb;
			method_code++;
		}
			continue;
#endif
#if defined(ISTORE_0_OPCODE) || defined(ISTORE_1_OPCODE) || defined(ISTORE_2_OPCODE) || defined(ISTORE_3_OPCODE) 
		case ISTORE_0_OPCODE:
		case ISTORE_1_OPCODE:
		case ISTORE_2_OPCODE:
		case ISTORE_3_OPCODE:
			fp[code - ISTORE_0_OPCODE] = *(--sp);
			method_code++;
			continue;
#endif
#if defined(ASTORE_0_OPCODE) || defined(ASTORE_1_OPCODE) || defined(ASTORE_2_OPCODE) || defined(ASTORE_3_OPCODE) 
		case ASTORE_0_OPCODE:
		case ASTORE_1_OPCODE:
		case ASTORE_2_OPCODE:
		case ASTORE_3_OPCODE: {
			Object *object = (Object*) (pointer) *(--sp);
			fp[code - ASTORE_0_OPCODE] = (int32) (pointer) object;
			method_code++;
		}
			continue;
#endif
#if defined(BIPUSH_OPCODE_USED)
		case BIPUSH_OPCODE:
			*sp++ = (signed char) pgm_read_byte(++method_code);
			method_code++;
			continue;
#endif
#if defined(ALOAD_OPCODE_USED)
		case ALOAD_OPCODE: {
			unsigned char index = pgm_read_byte(++method_code);
			*sp++ = fp[index];
			method_code++;
			continue;
		}
#endif
#if defined(ILOAD_OPCODE_USED) || defined(FLOAD_OPCODE_USED)
		case ILOAD_OPCODE:
		case FLOAD_OPCODE: {
			unsigned char index = pgm_read_byte(++method_code);
			*sp++ = fp[index];
			method_code++;
			continue;
		}
#endif
#if defined(LLOAD_OPCODE_USED) || defined(DLOAD_OPCODE_USED)
		case DLOAD_OPCODE:
		case LLOAD_OPCODE: {
			unsigned char index = pgm_read_byte(++method_code);
			*sp++ = fp[index];
			*sp++ = fp[index + 1];
			method_code++;
		}
			continue;
#endif
#if defined(IF_ACMPEQ_OPCODE_USED) || defined(IF_ACMPNE_OPCODE_USED)
		case IF_ACMPEQ_OPCODE:
		case IF_ACMPNE_OPCODE: {
			int32 op2 = *(--sp);
			int32 op1 = *(--sp);
			unsigned char result;

			if (code == IF_ACMPEQ_OPCODE) {
				result = (op1 == op2);
			} else {
				result = (op1 != op2);
			}

			if (result) {
				unsigned char branchbyte1 = pgm_read_byte(method_code + 1);
				unsigned char branchbyte2 = pgm_read_byte(method_code + 2);
				signed short offset = (signed short) ((branchbyte1 << 8)
						| branchbyte2);
				method_code += offset;
			} else {
				method_code += 3;
			}
			continue;
		}
#endif
#if defined(ALOAD_0_OPCODE_USED) || defined(ALOAD_1_OPCODE_USED) || defined(ALOAD_2_OPCODE_USED) || defined(ALOAD_3_OPCODE_USED)
		case ALOAD_0_OPCODE:
		case ALOAD_1_OPCODE:
		case ALOAD_2_OPCODE:
		case ALOAD_3_OPCODE: {
			Object *object = (Object*) (pointer) fp[code - ALOAD_0_OPCODE];
			*sp++ = (int32) (pointer) object;
			method_code++;
		}
			continue;
#endif
#if defined(ILOAD_0_OPCODE_USED) || defined(ILOAD_1_OPCODE_USED) || defined(ILOAD_2_OPCODE_USED) || defined(ILOAD_3_OPCODE_USED)
		case ILOAD_0_OPCODE:
		case ILOAD_1_OPCODE:
		case ILOAD_2_OPCODE:
		case ILOAD_3_OPCODE: {
			int32 value = fp[code - ILOAD_0_OPCODE];
			*sp++ = value;
			method_code++;
		}
			continue;
#endif
#if defined(FLOAD_0_OPCODE_USED) || defined(FLOAD_1_OPCODE_USED) || defined(FLOAD_2_OPCODE_USED) || defined(FLOAD_3_OPCODE_USED)
			case FLOAD_0_OPCODE:
			case FLOAD_1_OPCODE:
			case FLOAD_2_OPCODE:
			case FLOAD_3_OPCODE: {
				float f = *(float*) &fp[code - FLOAD_0_OPCODE];
				*(float*) sp = f;
				sp++;
				method_code++;
				continue;
			}
#endif
#if defined(FSTORE_0_OPCODE_USED) || defined(FSTORE_1_OPCODE_USED) || defined(FSTORE_2_OPCODE_USED) || defined(FSTORE_3_OPCODE_USED)
			case FSTORE_0_OPCODE:
			case FSTORE_1_OPCODE:
			case FSTORE_2_OPCODE:
			case FSTORE_3_OPCODE: {
				float f;
				sp--;
				f = *(float*) sp;
				*((float*) &fp[code - FSTORE_0_OPCODE]) = f;
				method_code++;
				continue;
			}
#endif
#if defined(F2D_OPCODE_USED)
			case F2D_OPCODE: {
				double d = (double) (*(float*) (sp - 1));
				*(double*) (sp - 1) = d;
				sp++;
				method_code++;
			}
			continue;
#endif
#if defined(F2I_OPCODE_USED)
			case F2I_OPCODE: {
				int32 x = (int) (*(float*) (sp - 1));
				*(sp - 1) = x;
				method_code++;
			}
			continue;
#endif
#if defined(I2B_OPCODE_USED) || defined(I2C_OPCODE_USED)
		case I2B_OPCODE:
		case I2C_OPCODE: {
			unsigned char byteVal = *(--sp);
			*sp++ = byteVal;
			method_code++;
		}
			continue;
#endif
#if defined(I2D_OPCODE_USED)
			case I2D_OPCODE: {
				int32 lsb = *(--sp);
				*(double*) sp = (double) lsb;
				sp += 2;
				method_code++;
			}
			continue;
#endif
#if defined(I2F_OPCODE_USED)
			case I2F_OPCODE: {
				int32 lsb = *(--sp);
				*(float*) sp = (float) lsb;
				sp++;
				method_code++;
			}
			continue;
#endif
#if defined(I2L_OPCODE_USED)
		case I2L_OPCODE: {
			int32 lsb = *(--sp);
			if (lsb < 0) {
				*sp++ = -1;
			} else {
				*sp++ = 0x0;
			}
			*sp++ = lsb;
			method_code++;
		}
			continue;
#endif
#if defined(I2S_OPCODE_USED)
		case I2S_OPCODE: {
			short int result = *(--sp);
			*sp++ = result;
			method_code++;
			continue;
		}
#endif
#if defined(IADD_OPCODE_USED)
		case IADD_OPCODE: {
			int32 lsb = *(--sp);
			lsb += *(sp - 1);
			*(sp - 1) = lsb;
			method_code++;
		}
			continue;
#endif
#if defined(FNEG_OPCODE_USED)
			case FNEG_OPCODE: {
				float lsb = *(float*) (--sp);
				lsb = -lsb;
				*(float*) (sp) = lsb;
				sp++;
				method_code++;
			}
			continue;
#endif
#if defined(FADD_OPCODE_USED) || defined(FMUL_OPCODE_USED) || defined(FDIV_OPCODE_USED) || defined(FSUB_OPCODE_USED)
			case FSUB_OPCODE:
			case FADD_OPCODE:
			case FDIV_OPCODE:
			case FMUL_OPCODE: {
				float lsb = *(float*) (--sp);
				float msb = *(float*) (--sp);
				switch (code) {
					case FSUB_OPCODE:
					lsb = msb - lsb;
					break;
					case FADD_OPCODE:
					lsb += msb;
					break;
					case FMUL_OPCODE:
					lsb *= msb;
					break;
					case FDIV_OPCODE:
					lsb = msb / lsb;
					break;
				}
				*(float*) (sp) = lsb;
				sp++;
				method_code++;
			}
			continue;
#endif
#if defined(IAND_OPCODE_USED)
		case IAND_OPCODE: {
			int32 lsb = *(--sp);
			lsb &= *(sp - 1);
			*(sp - 1) = lsb;
			method_code++;
		}
			continue;
#endif
#if defined(LSUB_OPCODE_USED) || defined(LADD_OPCODE_USED) || defined(LOR_OPCODE_USED) || defined(LXOR_OPCODE_USED) || defined(LAND_OPCODE_USED)
		case LSUB_OPCODE:
		case LADD_OPCODE:
		case LOR_OPCODE:
		case LXOR_OPCODE:
		case LAND_OPCODE: {
			handleLongOperator(code, sp);
			sp = sp - 2;
			method_code++;
			continue;
		}
#endif
#if defined(IDIV_OPCODE_USED)
		case IDIV_OPCODE: {
			int32 res = *(--sp);
			if (res != 0) {
				res = idiv(*(sp - 1), res);
				*(sp - 1) = res;
				method_code++;
				continue;
			} else {
				initializeException(sp, JAVA_LANG_ARITHMETICEXCEPTION_var,
						JAVA_LANG_ARITHMETICEXCEPTION_INIT__var);
				sp++;
				break;
			}
		}
#endif
#if defined(IMUL_OPCODE_USED)
		case IMUL_OPCODE: {
			int32 res = *(--sp);
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
			res = imul(res, *(sp - 1));
#else
			res = res * (*(sp - 1));
#endif
			*(sp - 1) = res;
			method_code++;
			continue;
		}
#endif
#if defined(INEG_OPCODE_USED)
		case INEG_OPCODE: {
			int32 res = *(--sp);
			res = -res;
			*sp++ = res;
			method_code++;
			continue;
		}
#endif
#if defined(LNEG_OPCODE_USED)
		case LNEG_OPCODE: {
			handleLNEG(sp);
			method_code++;
			continue;
		}
#endif
#if defined(IOR_OPCODE_USED)
			case IOR_OPCODE: {
				int32 res = *(--sp);
				res |= *(sp - 1);
				*(sp - 1) = res;
				method_code++;
				continue;
			}
#endif
#if defined(IREM_OPCODE_USED)
			case IREM_OPCODE: {
				int32 res = *(--sp);
				if (res != 0) {
					res = imod(res, *(sp - 1));
					*(sp - 1) = res;
					method_code++;
					continue;
				} else {
					initializeException(sp, JAVA_LANG_ARITHMETICEXCEPTION_var, JAVA_LANG_ARITHMETICEXCEPTION_INIT__var);
					sp++;
					break;
				}
			}
#endif
#if defined(ISUB_OPCODE_USED)
		case ISUB_OPCODE: {
			int32 res = *(--sp);
			res = *(sp - 1) - res;
			*(sp - 1) = res;
			method_code++;
			continue;
		}
#endif
#if defined(IXOR_OPCODE_USED)
			case IXOR_OPCODE: {
				int32 res = *(--sp);
				res ^= *(sp - 1);
				*(sp - 1) = res;
				method_code++;
				continue;
			}
#endif
#if defined(IFNONNULL_OPCODE_USED) || defined(IFNULL_OPCODE_USED) || defined(IF_ICMPEQ_OPCODE_USED) || defined(IF_ICMPNE_OPCODE_USED) || defined(IF_ICMPLT_OPCODE_USED) || defined(IF_ICMPGE_OPCODE_USED) || defined(IF_ICMPGT_OPCODE_USED) || defined(IF_ICMPLE_OPCODE_USED) || defined(IFEQ_OPCODE_USED) || defined(IFNE_OPCODE_USED) || defined(IFLT_OPCODE_USED) || defined(IFGE_OPCODE_USED) || defined(IFGT_OPCODE_USED) || defined(IFLE_OPCODE_USED) || defined(GOTO_OPCODE_USED)
		case IFNONNULL_OPCODE:
		case IFNULL_OPCODE:
		case IF_ICMPEQ_OPCODE:
		case IF_ICMPNE_OPCODE:
		case IF_ICMPLT_OPCODE:
		case IF_ICMPGE_OPCODE:
		case IF_ICMPGT_OPCODE:
		case IF_ICMPLE_OPCODE:
		case IFEQ_OPCODE:
		case IFNE_OPCODE:
		case IFLT_OPCODE:
		case IFGE_OPCODE:
		case IFGT_OPCODE:
		case IFLE_OPCODE: {
			unsigned char byteVal;
			int32 lsb, msb;

			if ((code >= IF_ICMPEQ_OPCODE) && (code <= IF_ICMPLE_OPCODE)) {
				lsb = *(--sp);
			} else {
				lsb = 0;
			}
			msb = *(--sp);
			byteVal = 0;
			switch (code) {
			case IFNULL_OPCODE:
			case IFEQ_OPCODE:
			case IF_ICMPEQ_OPCODE:
				byteVal = (msb == lsb);
				break;
			case IFNONNULL_OPCODE:
			case IFNE_OPCODE:
			case IF_ICMPNE_OPCODE:
				byteVal = (msb != lsb);
				break;
			case IFLT_OPCODE:
			case IF_ICMPLT_OPCODE:
				byteVal = (msb < lsb);
				break;
			case IFGE_OPCODE:
			case IF_ICMPGE_OPCODE:
				byteVal = (msb >= lsb);
				break;
			case IFGT_OPCODE:
			case IF_ICMPGT_OPCODE:
				byteVal = (msb > lsb);
				break;
			case IFLE_OPCODE:
			case IF_ICMPLE_OPCODE:
				byteVal = (msb <= lsb);
				break;
			}
			if (!byteVal) {
				method_code += 3;
				continue;
			}
		}
			/* no break */
		case GOTO_OPCODE: {
			unsigned char branchbyte1 = pgm_read_byte(method_code + 1);
			unsigned char branchbyte2 = pgm_read_byte(method_code + 2);
			signed short int offset = (signed short int) ((branchbyte1 << 8)
					| branchbyte2);
#if (defined(PC64) || defined(PC32) || defined(CR16C)) && defined(VM_CLOCKINTERRUPTHANDLER_ENABLE_USED)
			if (offset <= 0) {
				yieldToScheduler(sp);
			}
#endif
#if defined(ENABLE_DEBUG)
			if (offset <= 0) {
				checkForRequests(fp);
			}
#endif
			method_code += offset;
			continue;
		}
#endif
#if defined(NEW_OPCODE_USED) || defined(INVOKEDYNAMIC_OPCODE_USED)
		case NEW_OPCODE:
		case INVOKEDYNAMIC_OPCODE:
			if (handleNew(sp, method_code) == 0) {
				sp++;
				break;
			}
			sp++;
			method_code += 3;
			continue;
#endif
#if defined(DUP_OPCODE_USED)
		case DUP_OPCODE:
			*sp = *(sp - 1);
			sp++;
			method_code++;
			continue;
#endif
#if defined(NOP_OPCODE_USED)
			case NOP_OPCODE:
			method_code++;
			continue;
#endif
		case RETURN_OPCODE:
		case ARETURN_OPCODE:
		case FRETURN_OPCODE:
		case LRETURN_OPCODE:
		case DRETURN_OPCODE:
		case IRETURN_OPCODE: {
			unsigned short pc;
			unsigned char minfo = pgm_read_byte(&currentMethod->minfo);
			unsigned char lambdaAdjustment =
					pgm_read_word(&currentMethod->classIndex) & 0x1;
			Object *this = (Object*) (pointer) fp[0];
			const char* fromMethod = (const char*) pgm_read_pointer(
					&currentMethod->name, const char**);
			int32 spm1 = *(sp - 1);
			int32 spm2 = *(sp - 2);

			currentMethodNumber = popStackFrame(&fp, &sp, currentMethod, &pc);
			sp -= lambdaAdjustment;

			currentMethod = &methods[currentMethodNumber];

			if (minfo >> 7) {
				if (!handleMonitorEnterExit(this, 0, sp, fromMethod)) {
					sp++;
					goto throwIt;
				}
			}

			if (currentMethodNumber != 0) {
				unsigned char numReturnValues = minfo & 0x3;
				if ((code == LRETURN_OPCODE) || (code == DRETURN_OPCODE)) {
					*(sp - numReturnValues) = spm2;
					*(sp - numReturnValues + 1) = spm1;
				} else {
					*(sp - numReturnValues) = spm1;
				}

				currentMethodNumber--;
				currentMethod = &methods[currentMethodNumber];
				method_code = (unsigned char *) pgm_read_pointer(
						&currentMethod->code, unsigned char**) + pc;
				code = pgm_read_byte(method_code);
				switch (code) {
				case INVOKEINTERFACE_OPCODE:
					method_code += 5 + 1
							+ (pgm_read_byte(method_code + 5) << 2);
					break;
				case INVOKEVIRTUAL_OPCODE:
					method_code += 4 + 1
							+ (pgm_read_byte(method_code + 4) << 2);
					break;
				default:
					method_code += 4;
				}
				continue;
			} else {
				if ((minfo & 0x3) > 0) {
					if ((code == LRETURN_OPCODE) || (code == DRETURN_OPCODE)) {
						fp[0] = spm2;
						fp[1] = spm1;
					} else {
						fp[0] = spm1;
					}
				}
				return -1;
			}
		}
#if defined(INVOKESTATIC_OPCODE_USED)
		case INVOKESTATIC_OPCODE: {
			callee = pgm_read_byte(method_code + 1) << 8;
			callee |= pgm_read_byte(method_code + 2);

			sp -= pgm_read_byte(&(&methods[callee])->numArgs);

			goto callMethod;
		}
#endif
		case INVOKESPECIAL_OPCODE: {
			Object* obj;

			callee = pgm_read_byte(method_code + 1) << 8;
			callee |= pgm_read_byte(method_code + 2);

			numArgs = pgm_read_byte(&(&methods[callee])->numArgs);

			sp -= (numArgs + 1);

			obj = (Object*) (pointer) *sp;
			if (obj == 0) {
				goto throwNullPointer;
			}

			goto callMethod;
		}
#if defined(INVOKEINTERFACE_OPCODE_USED) || defined(INVOKEVIRTUAL_OPCODE_USED)
		case INVOKEVIRTUAL_OPCODE:
		case INVOKEINTERFACE_OPCODE: {
			Object *object;
			unsigned char* mcode = method_code;
			unsigned short classIndex;
			unsigned short jumpTableSize;

			numArgs = pgm_read_byte(mcode + 3);

			sp -= (numArgs + 1);

			object = (Object *) (pointer) *sp;
			/* It could be adjusted here */
			if (object == 0) {
				goto throwNullPointer;
			}

			classIndex = getClassIndex(object);

			while (1) {
				if (code == INVOKEINTERFACE_OPCODE) {
					mcode = method_code + 5;
				} else {
					mcode = method_code + 4;
				}
				jumpTableSize = pgm_read_byte(mcode++);
				callee = 0;
				while (jumpTableSize > 0) {
					callee = pgm_read_byte(mcode++) << 8;
					callee |= pgm_read_byte(mcode++);
					if (callee == classIndex) {
						callee = pgm_read_byte(mcode++) << 8;
						callee |= pgm_read_byte(mcode++);
						goto callMethod;
					} else {
						mcode += 2;
					}
					jumpTableSize--;
				}
				classIndex = pgm_read_word(&classes[classIndex].superClass);
			}
			goto throwIt;
		}
#endif
#if defined(IINC_OPCODE_USED)
		case IINC_OPCODE: {
			unsigned char localVarNo = pgm_read_byte(++method_code);
			signed char constVal = pgm_read_byte(++method_code);

			fp[localVarNo] += constVal;
			method_code++;
			continue;
		}
#endif
#if defined(ACONST_NULL_OPCODE_USED)
		case ACONST_NULL_OPCODE:
			*sp++ = 0;
			method_code++;
			continue;
#endif
#if defined(INSTANCEOF_OPCODE_USED) || defined(CHECKCAST_OPCODE_USED)
		case INSTANCEOF_OPCODE:
		case CHECKCAST_OPCODE: {
			unsigned char throwException = 1;
			Object *object = (Object *) (pointer) *(sp - 1);
			if (object != 0) {
				unsigned char isInterface;
				unsigned char byte1 = pgm_read_byte(++method_code);
				unsigned char byte2 = pgm_read_byte(++method_code);
				unsigned short index = ((byte1 << 8) | byte2);

				isInterface = index & 0x1;

				index = index >> 1;

				if (isInterface == 0) {
					if (index != JAVA_LANG_OBJECT_var) {
						signed short subClassIndex = getClassIndex(object);
						throwException = !isSubClassOf(subClassIndex, index);
					} else {
						throwException = 0;
					}
				} else {
					throwException = checkImplements(object, index);
				}

				if (throwException && (code == CHECKCAST_OPCODE)) {
					initializeException(sp, JAVA_LANG_CLASSCASTEXCEPTION_var,
							JAVA_LANG_CLASSCASTEXCEPTION_INIT__var);
					sp++;
					break;
				}
			} else {
				method_code += 2;
			}
			method_code++;
			if (code == INSTANCEOF_OPCODE) {
				if (throwException) {
					*sp++ = 0;
				} else {
					*sp++ = 1;
				}
			}
			continue;
		}
#endif
#if defined(ISHR_OPCODE_USED)
			case ISHR_OPCODE: {
				unsigned char shift = (*(--sp)) & 0x1f;
				int32 value = *(--sp);
				*sp++ = value >> shift;
				method_code++;
				continue;
			}
#endif
#if defined(ISHL_OPCODE_USED)
		case ISHL_OPCODE: {
			unsigned char shift = (*(--sp)) & 0x1f;
			int32 value = *(--sp);
			value = value << shift;
			*sp++ = value;
			method_code++;
			continue;
		}
#endif
#if defined(SIPUSH_OPCODE_USED)
		case SIPUSH_OPCODE: {
			unsigned char byte1 = pgm_read_byte(++method_code);
			unsigned char byte2 = pgm_read_byte(++method_code);
			signed short value = (signed short) ((byte1 << 8) | byte2);
			*sp++ = value;
			method_code++;
			continue;
		}
#endif
#if defined(GETSTATIC_OPCODE_USED)
		case GETSTATIC_OPCODE: {
			unsigned short topInc = handleGetStatic(method_code, sp);

			if (topInc == (unsigned short) -1) {
				sp++;
				break;
			}
			sp += topInc;
			method_code += 6;
			continue;
		}
#endif
#if defined(PUTSTATIC_OPCODE_USED)
		case PUTSTATIC_OPCODE: {
			unsigned short topDec = handlePutStatic(method_code, sp);

			if (topDec == (unsigned short) -1) {
				sp++;
				break;
			}

			sp -= topDec;
			method_code += 6;
			continue;
		}
#endif
#if defined(PUTHWFIELD_OPCODE_USED) || defined(PUTFIELD_OPCODE_USED)
		case PUTHWFIELD_OPCODE:
		case PUTFIELD_OPCODE: {
			unsigned char topInc = 0;

			if (code == PUTFIELD_OPCODE) {
#if defined(PUTFIELD_OPCODE_USED)
				topInc = handlePutField(method_code, sp);
#endif
			} else {
#if defined(PUTHWFIELD_OPCODE_USED)
				topInc = handlePutHWField(method_code, sp);
#endif
			}

			if (topInc > 0) {
				sp -= topInc;
			} else {
				goto throwNullPointer;
			}
			method_code += 6;
			continue;
		}
#endif
#if defined(GETHWFIELD_OPCODE_USED) || defined(GETFIELD_OPCODE_USED)
		case GETHWFIELD_OPCODE:
		case GETFIELD_OPCODE: {
			signed char topInc = 0;

			if (code == GETFIELD_OPCODE) {
#if defined(GETFIELD_OPCODE_USED)
				topInc = handleGetField(method_code, sp);
#endif
			} else {
#if defined(GETHWFIELD_OPCODE_USED)
				topInc = handleGetHWField(method_code, sp);
#endif
			}

			sp += topInc;

			if (topInc < 0) {
				goto throwNullPointer;
			}
			method_code += 6;
			continue;
		}
#endif
#if defined(POP_OPCODE_USED)
		case POP_OPCODE: {
			sp--;
			method_code++;
			continue;
		}
#endif
#if defined(ANEWARRAY_OPCODE_USED) || defined(NEWFLASHARRAY_OPCODE_USED) || defined(NEWARRAY_OPCODE_USED)
		case ANEWARRAY_OPCODE:
		case NEWFLASHARRAY_OPCODE:
		case NEWARRAY_OPCODE: {
			int32 count = *(sp - 1);
			Object* array;

			unsigned short classIndex = pgm_read_byte(++method_code) << 8;
			classIndex |= pgm_read_byte(++method_code);

			array = (Object*) createArray(classIndex,
					(uint16) count FLASHARG((code == NEWFLASHARRAY_OPCODE)));

			if (array != 0) {
#if defined(GC_GARBAGECOLLECTOR_NEWBARRIER_USED)
				gc_GarbageCollector_newBarrier(sp, (int32)(pointer)array);
#endif
				*(sp - 1) = (int32) (pointer) array;
			} else {
				initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR_var,
						JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
				sp++;
				break;
			}
			method_code++;
			continue;
		}
#endif
#if defined(ARRAYLENGTH_OPCODE_USED)
		case ARRAYLENGTH_OPCODE: {
			unsigned char* array = (unsigned char*) (pointer) *(sp - 1);
			uint16* ptr;
			uint16 length;
			if (array != 0) {
				ptr = (uint16*) (HEAP_REF(array, unsigned char*)
						+ sizeof(Object));
#ifdef FLASHSUPPORT
				if (isRomRef((uint8*)array)) {
					length = get_rom_dword((uint8*)ptr);
				} else {
					length = *ptr;
				}
#else
				length = *ptr;
#endif
				*(sp - 1) = length;
				method_code++;
			} else {
				goto throwNullPointer;
			}
			continue;
		}
#endif
#if defined(AASTORE_OPCODE_USED) || defined(FASTORE_OPCODE_USED) || defined(BASTORE_OPCODE_USED) || defined(CASTORE_OPCODE_USED) || defined(SASTORE_OPCODE_USED) || defined(LASTORE_OPCODE_USED) || defined(IASTORE_OPCODE_USED)
		case AASTORE_OPCODE:
		case FASTORE_OPCODE:
		case BASTORE_OPCODE:
		case CASTORE_OPCODE:
		case SASTORE_OPCODE:
		case LASTORE_OPCODE:
		case IASTORE_OPCODE: {
			unsigned char topInc = handleAStore(sp, method_code);

			sp -= topInc;

			if (topInc == 0) {
				goto throwNullPointer;
			}
			method_code++;
			continue;
		}
#endif
#if defined(FALOAD_OPCODE_USED) || defined(AALOAD_OPCODE_USED) || defined(BALOAD_OPCODE_USED) || defined(CALOAD_OPCODE_USED) || defined(SALOAD_OPCODE_USED) || defined(LALOAD_OPCODE_USED) || defined(IALOAD_OPCODE_USED)
		case FALOAD_OPCODE:
		case AALOAD_OPCODE:
		case BALOAD_OPCODE:
		case CALOAD_OPCODE:
		case SALOAD_OPCODE:
		case LALOAD_OPCODE:
		case IALOAD_OPCODE: {
			unsigned char topInc = handleALoad(sp, method_code);

			sp -= (2 - topInc);

			if (topInc == 0) {
				goto throwNullPointer;
			}

			method_code++;
			continue;
		}
#endif
#if defined(POP2_OPCODE_USED)
			case POP2_OPCODE: {
				sp -= 2;
				method_code++;
				continue;
			}
#endif
#if defined(DUP_X1_OPCODE_USED)
		case DUP_X1_OPCODE: {
			int32 value1 = *(--sp);
			int32 value2 = *(--sp);
			*sp++ = value1;
			*sp++ = value2;
			*sp++ = value1;
			method_code++;
			continue;
		}
#endif
#if defined(DUP_X2_OPCODE_USED)
			case DUP_X2_OPCODE: {
				handleDupX2(sp);
				sp++;
				method_code++;
				continue;
			}
#endif
#if defined(DUP2_OPCODE_USED)
			case DUP2_OPCODE: {
				int32 value1 = *(--sp);
				int32 value2 = *(--sp);
				*sp++ = value2;
				*sp++ = value1;
				*sp++ = value2;
				*sp++ = value1;
				method_code++;
				continue;
			}
#endif
#if defined(DUP2_X1_OPCODE_USED)
			case DUP2_X1_OPCODE: {
				handleDup2X1(sp);
				sp += 2;
				method_code++;
				continue;
			}
#endif
#if defined(DUP2_X2_OPCODE_USED)
			case DUP2_X2_OPCODE: {
				handleDup2X2(sp);
				sp += 2;
				method_code++;
				continue;
			}
#endif
#if defined(SWAP_OPCODE_USED)
			case SWAP_OPCODE: {
				int32 value1 = *(--sp);
				int32 value2 = *(--sp);
				*sp++ = value1;
				*sp++ = value2;
				method_code++;
				continue;
			}
#endif
#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED)
		case LDC2_W_OPCODE:
		case LDC_W_OPCODE:
		case LDC_OPCODE: {
			unsigned char topInc = handleLDC(sp, method_code);
			if (topInc > 0) {
				sp += topInc;
				method_code += 3;
				continue;
			} else {
				sp++;
				goto throwIt;
			}
		}
#endif
#if defined(DCONST_0_OPCODE_USED) || defined(DCONST_1_OPCODE_USED)
			case DCONST_0_OPCODE:
			case DCONST_1_OPCODE:
			*(double*)sp = (double)(code - DCONST_0_OPCODE);
			method_code++;
			sp+=2;
			continue;
#endif
#if defined(LCONST_0_OPCODE_USED) || defined(LCONST_1_OPCODE_USED)
		case LCONST_0_OPCODE:
		case LCONST_1_OPCODE:
			*sp++ = 0;
			*sp++ = (code - LCONST_0_OPCODE);
			method_code++;
			continue;
#endif
#if defined(LCMP_OPCODE_USED)
		case LCMP_OPCODE: {
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
			int32 res = handleLCMP(sp);
#else
			int32 res;
			if (sizeof(long) == 8) {
				long x;
				long y;

				x = *(sp - 4);
				x = x << 16;
				x = x << 16;
				x = x | ((uint32) *(sp - 3));

				y = *(sp - 2);
				y = y << 16;
				y = y << 16;
				y = y | ((uint32) *(sp - 1));

				if (x < y) {
					res = -1;
				} else if (x == y) {
					res = 0;
				} else {
					res = 1;
				}
			} else {
				res = handleLCMP(sp);
			}
#endif
			sp -= 4;
			*sp++ = res;
			method_code++;
			continue;
		}
#endif
#if defined(INVOKE_CLONE_ONARRAY_USED)
			case INVOKE_CLONE_ONARRAY: {
				signed char excep = handleCloneOnArray(sp);

				if (excep < 0) {
					sp--;
					goto throwNullPointer;
				} else if (excep > 0) {
					initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR_var, JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
					sp++;
					break;
				} else {
					method_code += 4;
				}
			}
			continue;
#endif
#if defined(IUSHR_OPCODE_USED)
		case IUSHR_OPCODE: {
			unsigned char value2 = (*(--sp)) & 0x1f;
			uint32 value1 = *(--sp);
			uint32 result = value1 >> value2;
			*sp++ = result;
			method_code++;
			continue;
		}
#endif
#if defined(TABLESWITCH_OPCODE_USED)
			case TABLESWITCH_OPCODE: {
				unsigned short pc;
				method_code += 4;
				pc = handleTableSwitch(method_code - (unsigned char *) pgm_read_pointer(&currentMethod->code, unsigned char**), method_code, *(--sp));
				method_code = (unsigned char *) pgm_read_pointer(&currentMethod->code, unsigned char**) + pc;
				continue;
			}
#endif
#if defined(LOOKUPSWITCH_OPCODE_USED)
			case LOOKUPSWITCH_OPCODE: {
				unsigned short pc;
				method_code += 4;
				pc = handleLookupSwitch(method_code - (unsigned char *) pgm_read_pointer(&currentMethod->code, unsigned char**), method_code, *(--sp));
				method_code = (unsigned char *) pgm_read_pointer(&currentMethod->code, unsigned char**) + pc;
				continue;
			}
#endif
#if defined(LSTORE_0_OPCODE_USED) || defined(LSTORE_1_OPCODE_USED) || defined(LSTORE_2_OPCODE_USED) || defined(LSTORE_3_OPCODE_USED) || defined(DSTORE_0_OPCODE_USED) || defined(DSTORE_1_OPCODE_USED) || defined(DSTORE_2_OPCODE_USED) || defined(DSTORE_3_OPCODE_USED)
		case DSTORE_0_OPCODE:
		case DSTORE_1_OPCODE:
		case DSTORE_2_OPCODE:
		case DSTORE_3_OPCODE:
		case LSTORE_0_OPCODE:
		case LSTORE_1_OPCODE:
		case LSTORE_2_OPCODE:
		case LSTORE_3_OPCODE: {
			int index;
			int32 msi, lsi;
			if ((code >= LSTORE_0_OPCODE) && (code <= LSTORE_3_OPCODE)) {
				index = code - LSTORE_0_OPCODE;
			} else {
				index = code - DSTORE_0_OPCODE;
			}
			lsi = *(--sp);
			msi = *(--sp);
			fp[index] = msi;
			fp[index + 1] = lsi;
			method_code++;
		}
			continue;
#endif
#if defined(LLOAD_0_OPCODE_USED) || defined(LLOAD_1_OPCODE_USED) || defined(LLOAD_2_OPCODE_USED) || defined(LLOAD_3_OPCODE_USED)
		case LLOAD_0_OPCODE:
		case LLOAD_1_OPCODE:
		case LLOAD_2_OPCODE:
		case LLOAD_3_OPCODE: {
			int index = code - LLOAD_0_OPCODE;
			int32 msi, lsi;
			msi = fp[index];
			lsi = fp[index + 1];
			*sp++ = msi;
			*sp++ = lsi;
			method_code++;
		}
			continue;
#endif
#if defined(DLOAD_0_OPCODE_USED) || defined(DLOAD_1_OPCODE_USED) || defined(DLOAD_2_OPCODE_USED) || defined(DLOAD_3_OPCODE_USED)
			case DLOAD_0_OPCODE:
			case DLOAD_1_OPCODE:
			case DLOAD_2_OPCODE:
			case DLOAD_3_OPCODE: {
				unsigned char index = code - DLOAD_0_OPCODE;
				int32 msi, lsi;
				msi = fp[index];
				lsi = fp[index + 1];
				*sp++ = msi;
				*sp++ = lsi;
				method_code++;
			}
			continue;
#endif
#if defined(LSHL_OPCODE_USED)
		case LSHL_OPCODE: {
			handleLSHL(sp);
			sp--;
			method_code++;
		}
			continue;
#endif
#if defined(LSHR_OPCODE_USED) || defined(LUSHR_OPCODE_USED)
		case LUSHR_OPCODE:
		case LSHR_OPCODE: {
			handleLSHR(sp);
			sp--;
			method_code++;
		}
			continue;
#endif
#if defined(L2I_OPCODE_USED)
		case L2I_OPCODE: {
			int32 lsi = *(--sp);
			--sp;
			*sp++ = lsi;
			method_code++;
		}
			continue;
#endif
#if defined(D2F_OPCODE_USED)
			case D2F_OPCODE: {
				double d = *(double*) (sp - 2);
				sp -= 2;
				*(float*) sp = (float) d;
				sp += 1;
				method_code++;
				continue;
			}
#endif

#if defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED)
		case LREM_OPCODE:
		case LDIV_OPCODE:
		case LMUL_OPCODE: {
			unsigned char topInc = handleLMULLDIVLREM(sp, code);
			sp -= 4;
			if (topInc == 0) {
				initializeException(sp, JAVA_LANG_ARITHMETICEXCEPTION_var,
						JAVA_LANG_ARITHMETICEXCEPTION_INIT__var);
				sp++;
				break;
			}
			sp += topInc;
			method_code++;
		}
			continue;
#endif
#if defined(DMUL_OPCODE_USED) || defined(DDIV_OPCODE_USED) || defined(DADD_OPCODE_USED) || defined(DSUB_OPCODE_USED)
			case DMUL_OPCODE:
			case DDIV_OPCODE:
			case DADD_OPCODE:
			case DSUB_OPCODE: {
				double value1;
				double value2;
				sp -= 2;
				value1 = *(double*) sp;
				sp -= 2;
				value2 = *(double*) sp;
				switch (code) {
					case DMUL_OPCODE:
					*(double*) sp = value1 * value2;
					break;
					case DDIV_OPCODE:
					*(double*) sp = value1 / value2;
					break;
					case DADD_OPCODE:
					*(double*) sp = value1 + value2;
					break;
					case DSUB_OPCODE:
					*(double*) sp = value1 - value2;
					break;
				}
				sp += 2;
				method_code++;
				continue;
			}
#endif
#if defined(DCMPG_OPCODE_USED) || defined(DCMPL_OPCODE_USED)
			case DCMPG_OPCODE:
			case DCMPL_OPCODE: {
				double value1;
				double value2;
				int32 res = 0;
				sp -= 2;
				value1 = *(double*) sp;
				sp -= 2;
				value2 = *(double*) sp;
				if (value2 > value1) {
					res = 1;
				} else if (value1 > value2) {
					res = -1;
				}
				*sp++ = res;
				method_code++;
				continue;
			}
#endif
#if defined(FCMPG_OPCODE_USED) || defined(FCMPL_OPCODE_USED)
			case FCMPG_OPCODE:
			case FCMPL_OPCODE: {
				float value1;
				float value2;
				int32 res = 0;
				sp--;
				value1 = *(float*) sp;
				sp--;
				value2 = *(float*) sp;
				if (value2 > value1) {
					res = 1;
				} else if (value1 > value2) {
					res = -1;
				}
				*sp++ = res;
				method_code++;
				continue;
			}
#endif
#if defined(MONITORENTER_OPCODE_USED) || defined(MONITOREXIT_OPCODE_USED)
			case MONITORENTER_OPCODE:
			case MONITOREXIT_OPCODE: {
				sp--;
				if (handleMonitorEnterExit((Object*) (pointer) sp[0], *method_code == MONITORENTER_OPCODE, sp, (const char*)pgm_read_pointer(&currentMethod->name, const char**))) {
					method_code++;
					continue;
				} else {
					sp++;
					break;
				}
			}
#endif
		case ATHROW_OPCODE: {
			break;
		}
#if defined(WIDE_OPCODE_USED)
			case WIDE_OPCODE: {
				signed short topInc = handleWide(fp, sp, method_code);
				if (pgm_read_byte(method_code + 1) == IINC_OPCODE) {
					method_code += 6;
				} else {
					method_code += 4;
				}
				sp += topInc;
				continue;
			}
#endif
#if defined(MULTIANEWARRAY_OPCODE_USED)
			case MULTIANEWARRAY_OPCODE: {
				signed short topInc = handleMultianewarray(sp, method_code);
				method_code += 4;
				sp -= topInc;
				continue;
			}
#endif

		default:
			printStr("Unsupported byte code: ");
			printByte(code);
			return -2;
		}

		throwIt:

		{
#if defined(JAVA_LANG_THROWABLE_INIT_)
			unsigned short handler_pc;
			unsigned short pc = method_code
					- (unsigned char *) pgm_read_pointer(&currentMethod->code,
							unsigned char**);
			Object* exception = (Object*) (pointer) *(sp - 1);
			unsigned short classIndex = getClassIndex(exception);
			while ((handler_pc = handleAthrow(currentMethod, classIndex, pc))
					== (unsigned short) -1) {
				/* Not handled */
				sp--;
				currentMethodNumber = popStackFrame(&fp, &sp, currentMethod,
						&pc);
				if (currentMethodNumber == 0) {
					fp[0] = (int32) (pointer) exception;
					return classIndex;
				}
				currentMethodNumber--;
				currentMethod = &methods[currentMethodNumber];
			}
			sp = &fp[pgm_read_word(&currentMethod->maxLocals) + 2];
			*sp++ = (int32) (pointer) exception;
			pc = handler_pc;
			method_code = (unsigned char *) pgm_read_pointer(
					&currentMethod->code, unsigned char**) + pc;
#else
			while (1) {;};
#endif
		}
		continue;

		throwNullPointer:

		initializeException(sp, JAVA_LANG_NULLPOINTEREXCEPTION_var,
				JAVA_LANG_NULLPOINTEREXCEPTION_INIT__var);
		sp++;
		goto throwIt;

		callMethod: {
			const MethodInfo* methodInfo = &methods[callee];
			unsigned char minfo = pgm_read_byte(&methodInfo->minfo);
			unsigned char lambdaAdjustment =
					pgm_read_word(&methodInfo->classIndex) & 0x1; /* adjustment for lambda methods, if this is a lambda method */
			if (code != INVOKESTATIC_OPCODE) {
				if (minfo >> 7) {
					Object* this = (Object*) (pointer) sp[0];
					int32* spm = sp + numArgs + 1;
					if (!handleMonitorEnterExit(this, 1, spm,
							(const char*) pgm_read_pointer(&currentMethod->name,
									const char**))) {
						spm++;
						sp = spm;
						goto throwIt;
					}
				}
			}

			sp += lambdaAdjustment;

			if (pgm_read_pointer(&methodInfo->code, unsigned char**) == 0) {
				int16 (*nativeFunc)(int32 *fp);
				signed short excep;
				nativeFunc = (int16 (*)(int32 *fp)) (pointer) pgm_read_pointer(
						&methodInfo->nativeFunc, int16 (**)(int32 *fp));
				excep = nativeFunc(sp);
				if (excep == -1) {
					unsigned char numReturnArgs =
							pgm_read_byte(&methodInfo->minfo) & 0x3;

					minfo = (minfo >> 2) & 0xF;
					if (numReturnArgs == 1) {
						switch (minfo) {
						case 0:
						case 1:
							*sp = *(signed char*) sp;
							break;
						case 2:
							*sp = *(signed short*) sp;
							break;
						}
					}

					sp[0] = sp[lambdaAdjustment];

					sp += numReturnArgs;

					switch (code) {
					case INVOKEINTERFACE_OPCODE:
						method_code += 5 + 1
								+ (pgm_read_byte(method_code + 5) << 2);
						break;
					case INVOKEVIRTUAL_OPCODE:
						method_code += 4 + 1
								+ (pgm_read_byte(method_code + 4) << 2);
						break;
					default:
						method_code += 4;
					}
				} else {
					if (excep >= 0) {
						sp++;
						goto throwIt;
					} else {
						return excep;
					}
				}
			} else {
				unsigned short pc = method_code
						- (unsigned char *) pgm_read_pointer(
								&currentMethod->code, unsigned char**);
				fp = pushStackFrame(pgm_read_word(&methodInfo->maxLocals),
						currentMethodNumber, pc, fp, sp);
				currentMethodNumber = callee;
				goto start;
			}
		}
		goto loop;
	}
}

#if defined(WIDE_OPCODE_USED)
static signed short handleWide(int32* fp, int32* sp, unsigned char *method_code) {
	unsigned char opcode = pgm_read_byte(&method_code[1]);
	unsigned char indexbyte1 = pgm_read_byte(&method_code[2]);
	unsigned char indexbyte2 = pgm_read_byte(&method_code[3]);
	unsigned short index = ((indexbyte1 << 8) | indexbyte2);
	switch (opcode) {
		case IINC_OPCODE: {
			unsigned char constbyte1 = pgm_read_byte(&method_code[4]);
			unsigned char constbyte2 = pgm_read_byte(&method_code[5]);
			signed short constVal = (signed short) ((constbyte1 << 8) | constbyte2);
			fp[index] += constVal;
			return 0;
		}
		case ILOAD_OPCODE:
		case FLOAD_OPCODE:
		case ALOAD_OPCODE:
		*sp++ = fp[index];
		if (opcode == ALOAD_OPCODE) {
		}
		return 1;
		case LLOAD_OPCODE:
		case DLOAD_OPCODE:
		*sp++ = fp[index];
		*sp++ = fp[index + 1];
		return 2;
		case ISTORE_OPCODE:
		case FSTORE_OPCODE:
		case ASTORE_OPCODE:
		fp[index] = *(--sp);
		return -1;
		case LSTORE_OPCODE:
		case DSTORE_OPCODE: {
			int32 lsb = *(--sp);
			int32 msb = *(--sp);
			fp[index] = msb;
			fp[index + 1] = lsb;
			return -2;
		}
		case RET_OPCODE:
		break;
	}
	return 0;
}
#endif

#if defined(LNEG_OPCODE_USED)
static void handleLNEG(int32* sp) {
	uint32 lsb = *(--sp);
	uint32 msb = *(--sp);
	neg(&msb, &lsb);
	*sp++ = msb;
	*sp++ = lsb;
}
#endif

static void add(uint32* rmsb, uint32* rlsb, uint32 xmsb, uint32 xlsb) {
	uint32 temp;
	temp = *rlsb;

	*rlsb += xlsb;
	if (*rlsb < temp) {
		*rmsb += 1;
	}
	*rmsb += xmsb;
}

#if defined(LSHL_OPCODE_USED) || defined(HANDLELSHL_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED) || defined(N_VM_REALTIMECLOCK_GETNATIVERESOLUTION) || defined(N_VM_REALTIMECLOCK_GETNATIVETIME)
static void lshl(uint32* xmsb, uint32* xlsb, unsigned char value) {
	uint32 carry;

	if (value > 31) {
		*xmsb = *xlsb;
		*xlsb = 0;
		value = value - 32;
	}

	carry = 1 << value;
	carry = carry - 1;
	carry = carry << (32 - value);
	carry = *xlsb & carry;
	carry = carry >> (32 - value);

	*xlsb = *xlsb << value;
	*xmsb = *xmsb << value;
	*xmsb = *xmsb | carry;
}
#endif

#if defined(LSHR_OPCODE_USED) || defined(LUSHR_OPCODE_USED) || defined(HANDLELSHR_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED) || defined(N_VM_REALTIMECLOCK_GETNATIVERESOLUTION) || defined(N_VM_REALTIMECLOCK_GETNATIVETIME)
static void lshr(uint32* msi, uint32* lsi, unsigned char value) {
	unsigned int carry;
	if (value > 31) {
		*lsi = *msi;
		*msi = 0;
		value -= 32;
	}

	carry = (1 << value);
	carry = carry - 1;
	carry = *msi & carry;

	*msi = *msi >> value;
	*lsi = *lsi >> value;
	*lsi |= carry << (32 - value);
}
#endif

void neg(uint32* msb, uint32* lsb) {
	*msb = ~*msb;
	*lsb = ~*lsb;
	add(msb, lsb, 0, 1);
}

#if defined(LCMP_OPCODE_USED) || defined(HANDLELCMP_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED)
static int32 lcmp(int32 value1msb, uint32 value1lsb, int32 value2msb,
		uint32 value2lsb) {
	int32 res = 0;

	if (value1msb > value2msb) {
		res = 1;
	} else if (value1msb < value2msb) {
		res = -1;
	} else {
		if (value1lsb > value2lsb) {
			res = 1;
		} else if (value1lsb < value2lsb) {
			res = -1;
		} else {
			res = 0;
		}
	}
	return res;
}
#endif

#if defined(INSTANCEOF_OPCODE_USED) || defined(CHECKCAST_OPCODE_USED) || defined(CHECKIMPLEMENTS_USED)
unsigned char checkImplements(Object* object, unsigned short interfaceIndex) {
	unsigned short classIndex = getClassIndex(object);

	const unsigned short *interfaces = (unsigned short *) pgm_read_pointer(
			&classes[classIndex].interfaces, unsigned short**);

	if (interfaces) {
		unsigned short count;
		count = pgm_read_word(&interfaces[0]);
		while (count) {
			if (pgm_read_word(&interfaces[count]) == interfaceIndex) {
				return 0;
			}
			count--;
		}
	}

	return 1;
}
#endif

unsigned char getElementSize(unsigned short classIndex) {
	signed short objectSize = pgm_read_word(&classes[classIndex].dobjectSize);
	if (objectSize < 0) {
		return (unsigned char) -objectSize;
	} else {
		return 4; /* reference type */
	}
}

#if defined(CREATEMULTIDIMENSIONALARRAYS_USED) || defined(MULTIANEWARRAY_OPCODE_USED) || defined(ANEWARRAY_OPCODE_USED) || defined(NEWFLASHARRAY_OPCODE_USED) || defined(NEWARRAY_OPCODE_USED) || defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_CLASS_GETNAME0) || defined(INVOKE_CLONE_ONARRAY_USED) || defined(HANDLECLONEONARRAY_USED) || defined(CREATEARRAY_USED)
unsigned char* createArray(unsigned short classIndex,
		uint16 count FLASHARG(uint8 flash)) {
	unsigned char elementSize;

	elementSize = getElementSize(classIndex);

	return createArrayFromElementSize(classIndex, elementSize,
			count FLASHARG(flash));
}
#endif

#if defined(CREATEMULTIDIMENSIONALARRAYS_USED) || defined(MULTIANEWARRAY_OPCODE_USED) || defined(ANEWARRAY_OPCODE_USED) || defined(NEWFLASHARRAY_OPCODE_USED) || defined(NEWARRAY_OPCODE_USED) || defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_CLASS_GETNAME0) || defined(INVOKE_CLONE_ONARRAY_USED) || defined(HANDLECLONEONARRAY_USED) || defined(N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY) || defined(CREATEARRAY_USED)
unsigned char* createArrayFromElementSize(unsigned short classIndex,
		unsigned char elementSize, uint16 count FLASHARG(uint8 flash)) {
	unsigned char* array;
	uint32 size;

#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
	size = 2 + imul(elementSize, count);
#else
	size = 2 + (elementSize * count);
#endif

#ifdef FLASHSUPPORT
	if (flash) {
		array = (unsigned char*) gc_allocateObject(0, size);
	} else {
		array = (unsigned char*) gc_allocateObject(size, 0);
	}
#else
	array = (unsigned char*) gc_allocateObject(size, 0);
#endif

	if (array != 0) {
		setClassIndex((Object*) array, classIndex);
#ifdef FLASHSUPPORT
		if (flash) {
			set_rom_dword(array + sizeof(Object), count);
		} else {
			*(int32 *) (HEAP_REF(array, unsigned char*) + sizeof(Object)) = count;
		}
#else
		*(uint16 *) (HEAP_REF(array, unsigned char*) + sizeof(Object)) = count;
#endif
	}
	return array;
}
#endif

#if defined(IREM_OPCODE_USED) || defined(IDIV_OPCODE_USED) || defined(IDIV_USED) || defined(IMOD_USED)
int32 idiv(int32 x, int32 y) {
	int result = 0;
	unsigned int k, sum;
	unsigned char isMinus;

	if (x < 0) {
		x = ~x + 1;
		if (y < 0) {
			y = ~y + 1;
			isMinus = 0;
		} else {
			isMinus = 1;
		}
	} else {
		if (y < 0) {
			y = ~y + 1;
			isMinus = 1;
		} else {
			isMinus = 0;
		}
	}

	while (y <= x) {
		k = 1;
		sum = y;
		while ((sum << 1) <= x) {
			sum = sum << 1;
			k = k << 1;
		}
		x = x - sum;
		result += k;
	}

	if (isMinus) {
		result = ~result + 1;
	}

	return result;
}
#endif

#if defined(IREM_OPCODE_USED) || defined(IMOD_USED)
int32 imod(int32 a, int32 b) {
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
	return b - (imul(a, idiv(b, a)));
#else
	return b - (a * idiv(b, a));
#endif
}
#endif

#if defined(CREATEMULTIDIMENSIONALARRAYS_USED) || defined(MULTIANEWARRAY_OPCODE_USED) || defined(IMUL_OPCODE_USED) || defined(ANEWARRAY_OPCODE_USED) || defined(NEWFLASHARRAY_OPCODE_USED) || defined(NEWARRAY_OPCODE_USED) || defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED) || defined(N_JAVA_LANG_CLASS_GETNAME0) || defined(N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY) || defined(IREM_OPCODE_USED) || defined(IMOD_USED) || defined(AASTORE_OPCODE_USED) || defined(FASTORE_OPCODE_USED) || defined(BASTORE_OPCODE_USED) || defined(CASTORE_OPCODE_USED) || defined(SASTORE_OPCODE_USED) || defined(LASTORE_OPCODE_USED) || defined(IASTORE_OPCODE_USED) ||  defined(FALOAD_OPCODE_USED) || defined(AALOAD_OPCODE_USED) || defined(BALOAD_OPCODE_USED) || defined(CALOAD_OPCODE_USED) || defined(SALOAD_OPCODE_USED) || defined(LALOAD_OPCODE_USED) || defined(IALOAD_OPCODE_USED) || defined(INVOKE_CLONE_ONARRAY_USED) || defined(HANDLECLONEONARRAY_USED) || defined(N_JAVA_LANG_SYSTEM_ARRAYCOPY) || defined(CREATEARRAY_USED) || defined(IMUL_USED)
int32 imul(int32 x, int32 y) {
	int32 result = 0;
	unsigned char sign = 0;
	if (x < 0) {
		x = -x;
		sign = !sign;
	}
	if (y < 0) {
		y = -y;
		sign = !sign;
	}

	if (y != 0) {
		while (y > 1) {
			if ((y & 1) == 1) {
				result = result + x;
				y = y - 1;
			} else {
				x = x << 1;
				y = y >> 1;
			}
		}
		result = result + x;
	}
	if (sign) {
		result = -result;
	}
	return result;
}
#endif

#if defined(JAVA_LANG_THROWABLE_INIT_)
static void addStackElement(const MethodInfo *method, int32 pc) {
	unsigned short index = 0;

	while (&methods[index] != method) {
		index++;
	}

	if (stackTraceTop + 1 < STACK_TRACE_SIZE) {
		stackTrace[stackTraceTop++] = index;
		stackTrace[stackTraceTop++] = (unsigned short) pc;
	}
}

void handleException(unsigned short classIndex) {
	unsigned short count = 0;
	reportStackTraceIntro(classIndex);
	while (count < stackTraceTop) {
		unsigned short methodIndex;
		unsigned short pc;
		methodIndex = stackTrace[count];
		count++;
		pc = stackTrace[count];
		count++;
		reportStackTraceElement(methodIndex, pc);
	}
}
#endif

#if defined(JAVA_LANG_THROWABLE_INIT_)
#if defined(PRE_INITIALIZE_EXCEPTIONS)
extern ExceptionObject *exceptionObjects;
#else
extern Object* outOfMemoryException;
#endif
int16 initializeException(int32* sp, int16 exceptionClass,
		int16 exceptionInitMethod) {
#if defined(PRE_INITIALIZE_EXCEPTIONS)
	unsigned short index = 0;
	while (index < NUMRUNTIMEEXCEPTIONS) {
		ExceptionObject* next = exceptionObjects + index;

		if (next->classId == exceptionClass) {
			if (next->methodId == exceptionInitMethod) {
				*sp = (int32) (pointer) next->exception;
				return exceptionClass;
			}
		}
		index++;
	}
	return -1;
#else
	if (handleNewClassIndex(sp, exceptionClass)) {
		enterMethodInterpreter(exceptionInitMethod, sp);
		return exceptionClass;
	} else {
		sp[0] = (int32) (pointer) outOfMemoryException;
		return getClassIndex(outOfMemoryException);
	}
#endif
}
#else
extern void printShort(unsigned short c);
int16 initializeException(int32* sp, int16 exceptionClass, int16 exceptionInitMethod) {
	printStr("Exception occurred: ");
	printShort(exceptionClass);
	printStr("\n");
	while (1) {;};
	return -1;
}
#endif

#if defined(TABLESWITCH_OPCODE_USED)
static unsigned short handleTableSwitch(unsigned short pc, unsigned char* ptr, int32 index) {
	int32 defaultVal, lowVal, highVal, offset = 0;
	unsigned char byte1, byte2, byte3, byte4;
	unsigned short pcStart = pc - 4;

	/* ptr = &method_code[pc]; */
	byte1 = pgm_read_byte(ptr++);
	byte2 = pgm_read_byte(ptr++);
	byte3 = pgm_read_byte(ptr++);
	byte4 = pgm_read_byte(ptr++);
	defaultVal = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;
	byte1 = pgm_read_byte(ptr++);
	byte2 = pgm_read_byte(ptr++);
	byte3 = pgm_read_byte(ptr++);
	byte4 = pgm_read_byte(ptr++);
	lowVal = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;
	byte1 = pgm_read_byte(ptr++);
	byte2 = pgm_read_byte(ptr++);
	byte3 = pgm_read_byte(ptr++);
	byte4 = pgm_read_byte(ptr++);
	highVal = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;

	/* index = sp[--top]; */

	if ((index < lowVal) || (index > highVal)) {
		pc = pcStart + defaultVal;
	} else {
		while (index >= lowVal) {
			byte1 = pgm_read_byte(ptr++);
			byte2 = pgm_read_byte(ptr++);
			byte3 = pgm_read_byte(ptr++);
			byte4 = pgm_read_byte(ptr++);
			offset = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;
			index--;
		}
		pc = pcStart + offset;
	}
	return pc;
}
#endif

#if defined(LOOKUPSWITCH_OPCODE_USED)
static unsigned short handleLookupSwitch(unsigned short pc, unsigned char* ptr, int32 key) {
	int32 defaultVal, npairs, match, offset;
	unsigned char byte1, byte2, byte3, byte4;
	unsigned short pcStart = pc - 4;

	byte1 = pgm_read_byte(ptr++);
	byte2 = pgm_read_byte(ptr++);
	byte3 = pgm_read_byte(ptr++);
	byte4 = pgm_read_byte(ptr++);
	defaultVal = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;
	byte1 = pgm_read_byte(ptr++);
	byte2 = pgm_read_byte(ptr++);
	byte3 = pgm_read_byte(ptr++);
	byte4 = pgm_read_byte(ptr++);
	npairs = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;

	while (npairs > 0) {
		byte1 = pgm_read_byte(ptr++);
		byte2 = pgm_read_byte(ptr++);
		byte3 = pgm_read_byte(ptr++);
		byte4 = pgm_read_byte(ptr++);
		match = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;
		byte1 = pgm_read_byte(ptr++);
		byte2 = pgm_read_byte(ptr++);
		byte3 = pgm_read_byte(ptr++);
		byte4 = pgm_read_byte(ptr++);
		offset = ((int32) byte1 << 24) | ((int32) byte2 << 16) | (byte3 << 8) | byte4;
		if (match == key) {
			pc = pcStart + offset;
			npairs = -1;
		} else {
			npairs--;
		}
	}
	if (npairs == 0) {
		pc = pcStart + defaultVal;
	}

	return pc;
}
#endif

#if defined(LSUB_OPCODE_USED) || defined(LADD_OPCODE_USED) || defined(LOR_OPCODE_USED) || defined(LXOR_OPCODE_USED) || defined(LAND_OPCODE_USED) || defined(HANDLELONGOPERATOR_USED) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED)
void ladd(uint32* msb1, uint32* lsb1, uint32 msb2, uint32 lsb2) {
	uint32 temp = *lsb1;
	*lsb1 = *lsb1 + lsb2;
	if (*lsb1 < temp) {
		*msb1 += 1;
	}
	*msb1 = *msb1 + msb2;
}

static void _handleLongOperator(unsigned char code, int32* sp) {
	uint32 lsb1 = *(--sp);
	uint32 msb1 = *(--sp);
	uint32 lsb2 = *(--sp);
	uint32 msb2 = *(--sp);

	if (code == LSUB_OPCODE) {
		lsb1 = ~lsb1;
		msb1 = ~msb1;

		if (lsb1 == 0xFFFFFFFF) {
			msb1 = msb1 + 1;
		}
		lsb1 = lsb1 + 1;
		code = LADD_OPCODE;
	}
	if (code == LAND_OPCODE) {
		msb1 &= msb2;
		lsb1 &= lsb2;
	} else if (code == LOR_OPCODE) {
		msb1 |= msb2;
		lsb1 |= lsb2;
	} else if (code == LADD_OPCODE) {
		ladd(&msb1, &lsb1, msb2, lsb2);
	} else if (code == LXOR_OPCODE) {
		msb1 ^= msb2;
		lsb1 ^= lsb2;
	}
	*sp++ = msb1;
	*sp++ = lsb1;
}

void handleLongOperator(unsigned char code, int32* sp) {
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
	_handleLongOperator(code, sp);
#else
	if (sizeof(long) == 8) {
		long x;
		long y;

		x = *(sp - 4);
		x = x << 16;
		x = x << 16;
		x = x | ((uint32) *(sp - 3));

		y = *(sp - 2);
		y = y << 16;
		y = y << 16;
		y = y | ((uint32) *(sp - 1));
		switch (code) {
		case LSUB_OPCODE:
			x = x - y;
			break;
		case LAND_OPCODE:
			x = x & y;
			break;
		case LOR_OPCODE:
			x = x | y;
			break;
		case LADD_OPCODE:
			x = x + y;
			break;
		case LXOR_OPCODE:
			x = x ^ y;
			break;
		}
		*(sp - 4) = (x >> 16) >> 16;
		*(sp - 3) = x & 0xFFFFFFFF;
	} else {
		_handleLongOperator(code, sp);
	}
#endif
}
#endif

#if defined(VM_MACHINE_SETCURRENTSCHEDULER)
extern int16 n_vm_Monitor_attachMonitor(int32 *sp);
extern int16 vm_Monitor_getDefaultMonitor(int32 *sp);
#endif

unsigned char handleNewClassIndex(int32* sp, unsigned short classIndex) {
	unsigned short dobjectSize, pobjectSize;
	Object* object;
	unsigned char hasLock = classes[classIndex].hasLock;

	dobjectSize = pgm_read_word(&classes[classIndex].dobjectSize) >> 3;
	pobjectSize = pgm_read_word(&classes[classIndex].pobjectSize) >> 3;

	if (hasLock) {
		dobjectSize += 4;
	}
	object = gc_allocateObject(dobjectSize, pobjectSize);

	if (object != 0) {
		if (hasLock) {
#if defined(VM_MACHINE_SETCURRENTSCHEDULER)
			int16 res = vm_Monitor_getDefaultMonitor(sp);
			object = (Object*) (((unsigned char*) object) + 4);
			if (res == -1) {
				sp[1] = (int32) (pointer) object;
				n_vm_Monitor_attachMonitor(sp);
			}
#else
			object = (Object*) (((unsigned char*) object) + 4);
#endif
		}
		setClassIndex(object, classIndex);
		sp[0] = (int32) (pointer) object;

#if defined(GC_GARBAGECOLLECTOR_NEWBARRIER_USED)
		gc_GarbageCollector_newBarrier(sp, (int32)(pointer)object);
#endif
		return 1;
	}
	return 0;
}

#if defined(MULTIANEWARRAY_OPCODE_USED) || defined(CREATEMULTIDIMENSIONALARRAYS_USED)
Object* createMultiDimensionalArrays(int32* sp, unsigned char dimensions, unsigned short classIndex)
{
	Object* array;

	if (dimensions == 1)
	{
		array = (Object*) createArray(classIndex, (uint16) *(sp - dimensions) FLASHARG(flash));
	}
	else
	{
		uint16 count = (uint16) *(sp - dimensions);
		uint32* arrays = (uint32*)createArrayFromElementSize(classIndex, 4, count FLASHARG(flash));
		unsigned short index;
		for (index = 0; index < count; index++)
		{
			*(HEAP_REF(arrays, uint32*) + index + 1) = (uint32)(pointer)createMultiDimensionalArrays(sp, dimensions - 1, classIndex);
		}
		array = (Object*)arrays;
	}
	return array;
}

#endif

#if defined(MULTIANEWARRAY_OPCODE_USED)
static unsigned char handleMultianewarray(int32* sp, unsigned char *method_code) {
	unsigned short classIndex;
	unsigned char dimensions;
	Object* array;

	classIndex = pgm_read_byte(&method_code[1]) << 8;
	classIndex |= pgm_read_byte(&method_code[2]);

	dimensions = pgm_read_byte(&method_code[3]);

	array = createMultiDimensionalArrays(sp, dimensions, classIndex);

	*(sp - dimensions) = (int32)(pointer)array;
	return dimensions - 1;
}
#endif

#if defined(NEW_OPCODE_USED)
static unsigned char handleNew(int32* sp, unsigned char *method_code) {
	unsigned short classIndex;

	classIndex = pgm_read_byte(&method_code[1]) << 8;
	classIndex |= pgm_read_byte(&method_code[2]);
	classIndex = classIndex >> 1;

	if (handleNewClassIndex(sp, classIndex)) {
		return 1;
	} else {
		initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR_var,
				JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
		return 0;
	}
}
#endif

extern const uint8 tupac;
extern const unsigned char *inheritanceMatrix;

/* TODO: Do I need superclass in classinfo now? */
#if defined(INSTANCEOF_OPCODE_USED) || defined(CHECKCAST_OPCODE_USED) || defined(JAVA_LANG_THROWABLE_INIT_) || defined(PUTHWFIELD_OPCODE_USED) || defined(GETHWFIELD_OPCODE_USED)
unsigned char isSubClassOf(unsigned short subClass, unsigned short superClass) {
	uint32 bitIndex = (subClass << tupac) + superClass;
	uint32 byteIndex = bitIndex >> 3;
	unsigned char b = pgm_read_byte(inheritanceMatrix + byteIndex);
	b = (unsigned char) (b & (1 << (bitIndex & 0x7)));
	return b != 0;
}
#endif

#if defined(JAVA_LANG_THROWABLE_INIT_)
unsigned short handleAthrow(const MethodInfo* method, unsigned short classIndex,
		unsigned short pc) {
	unsigned char numExceptionHandlers = pgm_read_byte(
			&method->numExceptionHandlers);
	if (numExceptionHandlers > 0) {
		ExceptionHandler * handlers = (ExceptionHandler*) pgm_read_pointer(
				&method->handlers, ExceptionHandler**);
		unsigned char count = 0;
		while (count < numExceptionHandlers) {
			ExceptionHandler *handler = &handlers[count];
			if (isSubClassOf(classIndex, pgm_read_word(&handler->clazz))) {
				if (pgm_read_word(&handler->start_pc) <= pc) {
					if (pgm_read_word(&handler->end_pc) > pc) {
						stackTraceTop = 0;
						return pgm_read_word(&handler->handle_pc);
					}
				}
			}
			count++;
		}
	}
	addStackElement(method, pc);
	return (unsigned short) -1;
}
#endif

#if defined(PUTFIELD_OPCODE_USED) || defined(PUTHWFIELD_OPCODE_USED) || defined(PUTSTATIC_OPCODE_USED) || defined(GETSTATIC_OPCODE_USED) || defined(GETFIELD_OPCODE_USED) || defined(GETHWFIELD_OPCODE_USED)
static unsigned short getFieldInfo(unsigned char* method_code,
		unsigned short *offset, unsigned char *size) {
	unsigned char fsize;
	unsigned short foffset;

	unsigned short classIndex = pgm_read_byte(++method_code) << 8;
	classIndex |= pgm_read_byte(++method_code);
	fsize = pgm_read_byte(++method_code);
	foffset = pgm_read_byte(++method_code) << 8;
	foffset |= pgm_read_byte(++method_code);

	*offset = foffset;
	*size = fsize;

	return classIndex;
}
#endif

#if defined(GETSTATIC_OPCODE_USED) || defined(GETFIELD_OPCODE_USED) || defined(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS)
unsigned char getField(unsigned char *data, unsigned char size, int32* sp) {

	unsigned char topInc = 0;
	int32 value = 0;

	switch (size & 0xfc) {
	case 64: {
		value = *(int32*) (data + 4);
		*sp = value;
		topInc++;
		sp++;
	}
		/* no break */
	case 32:
		value = *(int32*) data;
		break;
	case 16: {
		int16 svalue = *(int16*) data;
		value = svalue;
		break;
	}
	case 8:
		value = *(int8*) data;
		break;
	}
	*sp = value;
	topInc++;
	return topInc;
}
#endif

#if defined(PUTSTATIC_OPCODE_USED) || defined(PUTFIELD_OPCODE_USED)
static void putField(unsigned char *data, unsigned char size, int32 lsb,
		int32 msb) {
	switch (size & 0xfc) {
	case 64:
		*(int32*) (data + 4) = msb;
		/* no break */
	case 32:
		*(int32*) data = lsb;
		break;
	case 16:
		*(int16*) data = lsb;
		break;
	case 8:
		*(int8*) data = lsb;
		break;
	}
}
#endif

#if defined(GC_GARBAGECOLLECTOR_WRITEBARRIER_USED)
extern int16 gc_GarbageCollector_writeBarrier(int32 *fp, int32 source, int32 oldRef);
#endif

#if defined(PUTFIELD_OPCODE_USED)
static unsigned char handlePutField(unsigned char* method_code, int32* sp) {
	unsigned char *object;
	int32 lsb;
	int32 msb = 0;
	unsigned short offset;
	unsigned char size;
	unsigned char count = 0;
	unsigned char* data;
#if FLASHSUPPORT
	unsigned short classIndex;

	classIndex =
#endif
	getFieldInfo(method_code, &offset, &size);

	lsb = *(--sp);
	count++;
	if ((size & 0xfc) > 32) {
		msb = *(--sp);
		count++;
	}

	object = (unsigned char *) (pointer) *(--sp);
	count++;

	if (object != 0) {
#if FLASHSUPPORT
		if (pgm_read_word(&classes[classIndex].pobjectSize) == 0) {
			object = &object[sizeof(Object) + (offset >> 3)];
		} else {
			unsigned char isVolatile = size & 0x1;
			unsigned char isReference = size & 0x2;

			offset = offset >> 3;

			if (isVolatile) {
				object = get_rom_data(object, offset);
				return write_rom_data(object, size >> 3, isReference, count, msb, lsb);
			} else {
				object = get_ram_data(object, offset);
			}
		}
#else
		data = &object[sizeof(Object) + (offset >> 3)];

#if defined(GC_GARBAGECOLLECTOR_WRITEBARRIER_USED)
		if (size & 0x02) {
			gc_GarbageCollector_writeBarrier(sp, (int32)(pointer)object, *((int32*) HEAP_REF(data, unsigned char*)));
		}
#endif

#endif
		putField(HEAP_REF(data, unsigned char*), size, lsb, msb);
	} else {
		count = 0;
	}
	return count;
}
#endif

#if defined(PUTHWFIELD_OPCODE_USED)
static unsigned char handlePutHWField(unsigned char* method_code, int32* sp) {
	unsigned char *data;
	int32 lsb;
	int32 msb = 0;
	unsigned short offset;
	unsigned char size;
	unsigned char count = 0;

	getFieldInfo(method_code, &offset, &size);

	lsb = *(--sp);
	count++;
	if (size > 32) {
		msb = *(--sp);
		count++;
	}

	data = (unsigned char *) (pointer) *(--sp);
	count++;

	if (data != 0) {
		unsigned short classIndex = getClassIndex((Object*) data);
		struct _vm_HardwareObject_c* hwObject;
#if defined(VM_ADDRESS64BIT_INIT_)
		Object *obj;
#endif
		struct _vm_Address32Bit_c* addressObj32bit;

		hwObject = (struct _vm_HardwareObject_c*) HEAP_REF(data, struct _vm_HardwareObject_c*);

#if defined(VM_ADDRESS64BIT_INIT_)
		obj = (Object*)HEAP_REF((pointer)(hwObject->address_f), Object*);

		if (*obj == VM_ADDRESS64BIT)
		{
			struct _vm_Address64Bit_c* addressObj;
			long address;
			addressObj = (struct _vm_Address64Bit_c*)obj;
			address = addressObj->lsbaddress_f;
			address = (address << 16) << 16;
			address |= addressObj->address_f;

			data = (unsigned char*)(pointer)address;
		}
		else
		{
#endif

			addressObj32bit = (struct _vm_Address32Bit_c*) HEAP_REF((pointer )(hwObject->address_f), struct _vm_Address32Bit_c*);
			data = (unsigned char*) (pointer) (addressObj32bit->address_f);
#if defined(VM_ADDRESS64BIT_INIT_)
		}
#endif

		if (((uint16)REFLECT_HEAPACCESSOR_var != (uint16)-1) && isSubClassOf(classIndex, REFLECT_HEAPACCESSOR_var)) {
			data = HEAP_REF(data, unsigned char *);
		}

		offset -= 32; /* Subtract the width of the address java int */

		switch (size) {
			case 64:
			writeLongToIO((pointer) data, offset, msb, lsb);
			break;
			case 32:
			writeIntToIO((pointer) data, offset, lsb);
			break;
			case 16:
			writeShortToIO((pointer) data, offset, lsb & 0xffff);
			break;
			case 8:
			writeByteToIO((pointer) data, offset, lsb & 0xff);
			break;
			case 1:
			writeBitToIO((pointer) data, offset, lsb & 0x1);
			break;
		}
	} else {
		count = 0;
	}
	return count;
}
#endif

#if (defined(GETFIELD_OPCODE_USED) || defined(FALOAD_OPCODE_USED) || defined(AALOAD_OPCODE_USED) || defined(BALOAD_OPCODE_USED) || defined(CALOAD_OPCODE_USED) || defined(SALOAD_OPCODE_USED) || defined(LALOAD_OPCODE_USED) || defined(IALOAD_OPCODE_USED)) && defined(FLASHSUPPORT)
static signed char read_rom_data(unsigned char *object, unsigned char size, unsigned char isReference, signed char count, int32* sp) {
	int32 lsb = 0;
	int32 msb = 0;

	switch (size) {
		case 8:
		msb = get_rom_dword(&object[4]);
		*sp = msb;
		sp++;
		count++;
		case 4:
		if (isReference) {
			lsb = get_rom_reference(object);
		} else {
			lsb = get_rom_dword(object);
		}
		break;
		case 2:
		lsb = (int16) get_rom_word(object);
		break;
		case 1:
		lsb = (int8) get_rom_byte(object);
		break;
	}
	*sp = lsb;
	sp++;
	count++;

	return count;
}
#endif

#if (defined(AASTORE_OPCODE_USED) || defined(FASTORE_OPCODE_USED) || defined(BASTORE_OPCODE_USED) || defined(CASTORE_OPCODE_USED) || defined(SASTORE_OPCODE_USED) || defined(LASTORE_OPCODE_USED) || defined(IASTORE_OPCODE_USED) || defined(PUTFIELD_OPCODE_USED)) && defined(FLASHSUPPORT)
static unsigned char write_rom_data(unsigned char *object, unsigned char size, unsigned char isReference, unsigned char count, int32 msb, int32 lsb) {
	switch (size) {
		case 8:
		if (set_rom_dword(&object[4], msb)) {
			return 0;
		}
		case 4:
		if (isReference) {
			if (set_rom_reference(object, lsb)) {
				return 0;
			}
		} else {
			if (set_rom_dword(object, lsb)) {
				return 0;
			}
		}
		break;
		case 2:
		if (set_rom_word(object, lsb)) {
			return 0;
		}
		break;
		case 1:
		if (set_rom_byte(object, lsb)) {
			return 0;
		}
		break;
	}
	return count;
}
#endif

#if defined(PUTSTATIC_OPCODE_USED)
static unsigned short handlePutStatic(unsigned char* method_code, int32* sp) {
	int32 lsb = *(--sp);
	int32 msb = 0;
	unsigned short offset;
	unsigned char size;
	unsigned char* data;
	unsigned char topDec = 0;

	getFieldInfo(method_code, &offset, &size);

	data = classData + (offset >> 3);

	if ((size >> 3) > 4) {
		msb = *(--sp);
		topDec++;
	}

	putField(data, size, lsb, msb);

	topDec++;
	return topDec;
}
#endif

#if defined(GETSTATIC_OPCODE_USED)
static unsigned short handleGetStatic(unsigned char* method_code, int32* sp) {
	unsigned char *data;
	unsigned short offset;
	unsigned char size;

	getFieldInfo(method_code, &offset, &size);

	data = classData + (offset >> 3);

	return getField(data, size, sp);
}
#endif

#if defined(GETFIELD_OPCODE_USED)
static signed char handleGetField(unsigned char* method_code, int32* sp) {
	unsigned char *data;
	signed char topInc = 0;
	unsigned short offset;
	unsigned char size;
#if FLASHSUPPORT
	unsigned short classIndex;

	classIndex =
#endif
	getFieldInfo(method_code, &offset, &size);

	data = (unsigned char *) (pointer) *(--sp);
	topInc--;

	if (data != 0) {
#if FLASHSUPPORT
		if (pgm_read_word(&classes[classIndex].pobjectSize) == 0) {
			data = &data[sizeof(Object) + (offset >> 3)];
		} else {
			unsigned char isVolatile = size & 0x1;
			unsigned char isReference = size & 0x2;

			offset = offset >> 3;
			if (isVolatile) {
				data = get_rom_data(data, offset);
				return read_rom_data(data, size >> 3, isReference, topInc, sp);
			} else {
				data = get_ram_data(data, offset);
			}
		}
#else
		data = &data[sizeof(Object) + (offset >> 3)];
#endif
		topInc += getField(HEAP_REF(data, unsigned char *), size, sp);
	}
	return topInc;
}
#endif

#if defined(GETHWFIELD_OPCODE_USED)
static signed char handleGetHWField(unsigned char* method_code, int32* sp) {
	unsigned char *data;
	int32 value = 0;
	signed char topInc = 0;
	unsigned short offset;
	unsigned char size;

	getFieldInfo(method_code, &offset, &size);

	data = (unsigned char *) (pointer) *(--sp);
	topInc--;

	if (data != 0) {
		unsigned short classIndex = getClassIndex((Object*) data);
		struct _vm_HardwareObject_c* hwObject;
#if defined(VM_ADDRESS64BIT_INIT_)
		Object *obj;
#endif
		struct _vm_Address32Bit_c* addressObj32bit;

		hwObject = (struct _vm_HardwareObject_c*) HEAP_REF(data, struct _vm_HardwareObject_c*);

#if defined(VM_ADDRESS64BIT_INIT_)
		obj = (Object*) HEAP_REF((pointer )(hwObject->address_f), Object*);

		if (*obj == VM_ADDRESS64BIT) {
			struct _vm_Address64Bit_c* addressObj;
			long address;
			addressObj = (struct _vm_Address64Bit_c*)obj;
			address = addressObj->lsbaddress_f;
			address = (address << 16) << 16;
			address |= addressObj->address_f;
			data = (unsigned char*) (pointer) address;
		} else {
#endif

			addressObj32bit = (struct _vm_Address32Bit_c*) HEAP_REF((pointer )(hwObject->address_f), struct _vm_Address32Bit_c*);
			data = (unsigned char*) (pointer) (addressObj32bit->address_f);
#if defined(VM_ADDRESS64BIT_INIT_)
		}
#endif
		if (((uint16)REFLECT_HEAPACCESSOR_var != (uint16)-1) && isSubClassOf(classIndex, REFLECT_HEAPACCESSOR_var)) {
			data = HEAP_REF(data, unsigned char *);
		}

		offset -= 32;
		switch (size) {
			case 64: {
				int32 msb = 0;
				readLongFromIO((pointer) data, offset, &msb, &value);
				*sp++ = msb;
				topInc++;
				break;
			}
			case 32:
			value = readIntFromIO((pointer) data, offset);
			break;
			case 16:
			value = (signed short) readShortFromIO((pointer) data, offset);
			break;
			case 8:
			value = (signed char) readByteFromIO((pointer) data, offset);
			break;
			case 1:
			value = readBitFromIO((pointer) data, offset);
			break;
		}
		*sp = value;
		topInc++;
	}
	return topInc;
}
#endif

#if defined(AASTORE_OPCODE_USED) || defined(FASTORE_OPCODE_USED) || defined(BASTORE_OPCODE_USED) || defined(CASTORE_OPCODE_USED) || defined(SASTORE_OPCODE_USED) || defined(LASTORE_OPCODE_USED) || defined(IASTORE_OPCODE_USED)
static unsigned char handleAStore(int32* sp, unsigned char *method_code) {
	int32 msb = 0, lsb = 0;
	int32 index;
	unsigned char* array;
	unsigned char elementSize;
	unsigned char* ptr;
	uint8 count = 3;

	lsb = *(--sp);

	if (pgm_read_byte(method_code) == LASTORE_OPCODE) {
		msb = *(--sp);
		count++;
	}

	index = *(--sp);
	array = (unsigned char*) (pointer) *(--sp);
	if (array != 0) {
		if (pgm_read_byte(method_code) == AASTORE_OPCODE) {
			elementSize = 4;
		} else {
			unsigned short classIndex = getClassIndex((Object*) array);
			elementSize = getElementSize(classIndex);
		}

#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
		ptr = HEAP_REF(array, unsigned char*) + sizeof(Object) + 2 + imul(elementSize, index);
#else
		ptr = HEAP_REF(array, unsigned char*) + sizeof(Object) + 2
				+ (elementSize * index);
#endif

#ifdef FLASHSUPPORT
		if (isRomRef(ptr)) {
			return write_rom_data(ptr, elementSize, pgm_read_byte(method_code) == AASTORE_OPCODE, count, msb, lsb);
		} else {
#endif
		switch (elementSize) {
		case 1:
			*ptr = lsb & 0xff;
			break;
		case 2:
			*((unsigned short *) ptr) = lsb & 0xffff;
			break;
		case 4:
#if defined(GC_GARBAGECOLLECTOR_WRITEBARRIER_USED)
			if (pgm_read_byte(method_code) == AASTORE_OPCODE)
			{
				gc_GarbageCollector_writeBarrier(sp, (int32)(pointer)array, *((int32*) ptr));
			}
#endif
			*((uint32 *) ptr) = lsb;
			break;
		case 8:
			*((uint32 *) ptr) = msb;
			ptr += 4;
			*((uint32 *) ptr) = lsb;
			break;
		}
		return count;
#ifdef FLASHSUPPORT
	}
#endif
	} else {
		return 0;
	}
}
#endif

#if defined(FALOAD_OPCODE_USED) || defined(AALOAD_OPCODE_USED) || defined(BALOAD_OPCODE_USED) || defined(CALOAD_OPCODE_USED) || defined(SALOAD_OPCODE_USED) || defined(LALOAD_OPCODE_USED) || defined(IALOAD_OPCODE_USED)
static unsigned char handleALoad(int32* sp, unsigned char *method_code) {
	int32 index = *(--sp);
	unsigned char* array = (unsigned char*) (pointer) *(--sp);
	uint8 count = 0;

	if (array != 0) {
		if (index
				< *(uint16*) (HEAP_REF(array, unsigned char*) + sizeof(Object))) {
			unsigned char elementSize;
			unsigned char* ptr;
			int32 lsb = 0, msb = 0;

			if (pgm_read_byte(method_code) == AALOAD_OPCODE) {
				elementSize = 4;
			} else {
				unsigned short classIndex = getClassIndex((Object*) array);
				elementSize = getElementSize(classIndex);
			}

#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
			ptr = (unsigned char*) HEAP_REF(array, unsigned char*) + sizeof(Object) + 2 + imul(elementSize, index);
#else
			ptr = (unsigned char*) HEAP_REF(array, unsigned char*)
					+ sizeof(Object) + 2 + (elementSize * index);
#endif

#ifdef FLASHSUPPORT
			if (isRomRef(ptr)) {
				return read_rom_data(ptr, elementSize, pgm_read_byte(method_code) == AALOAD_OPCODE, count, sp);
			} else {
#endif
			switch (elementSize) {
			case 1:
				lsb = *(signed char*) ptr;
				break;
			case 2:
				lsb = *((signed short *) ptr);
				break;
			case 4:
				lsb = *((uint32 *) ptr);
				break;
			case 8:
				msb = *((uint32 *) ptr);
				ptr += 4;
				lsb = *((uint32 *) ptr);
				break;
			}
			if (pgm_read_byte(method_code) == LALOAD_OPCODE) {
				*sp++ = msb;
				count++;
			}
			*sp++ = lsb;
			count++;
			return count;
#ifdef FLASHSUPPORT
		}
#endif
		}
	}
	return 0;
}
#endif

#if defined(DUP_X2_OPCODE_USED)
static void handleDupX2(int32* sp) {
	int32 value1 = *(--sp);
	int32 value2 = *(--sp);
	int32 value3 = *(--sp);
	*sp++ = value1;
	*sp++ = value3;
	*sp++ = value2;
	*sp++ = value1;
}
#endif

#if defined(DUP2_X1_OPCODE_USED)
static void handleDup2X1(int32* sp) {
	int32 value1 = *(--sp);
	int32 value2 = *(--sp);
	int32 value3 = *(--sp);
	*sp++ = value2;
	*sp++ = value1;
	*sp++ = value3;
	*sp++ = value2;
	*sp++ = value1;
}
#endif

#if defined(DUP2_X2_OPCODE_USED)
static void handleDup2X2(int32* sp) {
	int32 value1 = *(--sp);
	int32 value2 = *(--sp);
	int32 value3 = *(--sp);
	int32 value4 = *(--sp);
	*sp++ = value2;
	*sp++ = value1;
	*sp++ = value4;
	*sp++ = value3;
	*sp++ = value2;
	*sp++ = value1;
}
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED)
static unsigned char handleLDC(int32* sp, unsigned char *method_code) {
	unsigned short index;
	index = pgm_read_byte(++method_code) << 8;
	index |= pgm_read_byte(++method_code);
	return handleLDCWithIndex(sp, index);
}
#endif

#if defined(LDC2_W_OPCODE_USED) || defined(LDC_W_OPCODE_USED) || defined(LDC_OPCODE_USED) || defined(HANDLELDCWITHINDEX_USED)
extern Object* stringConstants[];

#ifndef PRE_INITIALIZE_CONSTANTS
extern int16 initializeStringConstant(const ConstantInfo* constant, int32* sp);
#endif

unsigned char handleLDCWithIndex(int32* sp, unsigned short index) {
	const ConstantInfo* constant;
	unsigned char count = 0;
	unsigned char type;

	constant = &constants[index];

	type = pgm_read_byte(&constant->type);
	if (type == CONSTANT_STRING) {
#ifndef PRE_INITIALIZE_CONSTANTS
		if (initializeStringConstant(constant, sp) == -1) {
#endif
		int32 stringID = pgm_read_dword(&constant->value) >> 16;
		Object *strObject = stringConstants[stringID];
		*sp++ = (int32) (pointer) strObject;
		count++;
#ifndef PRE_INITIALIZE_CONSTANTS
	} else {
		initializeException(sp, JAVA_LANG_OUTOFMEMORYERROR_var, JAVA_LANG_OUTOFMEMORYERROR_INIT__var);
	}
#endif
	} else if (type == CONSTANT_INTEGER) {
		int32 integerValue = pgm_read_dword(&constant->value);
		*sp++ = integerValue;
		count++;
	} else if (type == CONSTANT_FLOAT) {
		*(float*) sp = pgm_read_pointer(&constant->fvalue, float*);
		sp++;
		count++;
	} else if (type == CONSTANT_CLASS) {
		Object* class = getClass(pgm_read_pointer(&constant->value, int32*));
		*sp++ = (int32) (pointer) class;
		count++;
	} else if (type == CONSTANT_LONG) {
		int32 msi;
		int32 lsi;
		const unsigned char *data = (const unsigned char *) pgm_read_pointer(
				&constant->data, const void **);
		msi = ((int32) pgm_read_byte(data)) << 24;
		msi |= ((int32) pgm_read_byte(data +1)) << 16;
		msi |= pgm_read_byte(data + 2) << 8;
		msi |= pgm_read_byte(data + 3);

		lsi = ((int32) pgm_read_byte(data + 4)) << 24;
		lsi |= ((int32) pgm_read_byte(data + 5)) << 16;
		lsi |= pgm_read_byte(data + 6) << 8;
		lsi |= pgm_read_byte(data + 7);

		*sp++ = msi;
		count++;
		*sp++ = lsi;
		count++;
	} else if (type == CONSTANT_DOUBLE) {
		*(double*) sp = *(const double*) pgm_read_pointer(&constant->data,
				const void **);
		sp += 2;
		count += 2;
	}
	return count;
}
#endif

#if defined(N_VM_REALTIMECLOCK_GETNATIVERESOLUTION) || defined(N_VM_REALTIMECLOCK_GETNATIVETIME) || defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED)
static unsigned char sign(int32* msb, int32* lsb, signed char s) {
	if (*msb < 0) {
		neg((uint32*) msb, (uint32*) lsb);
		if (s == -1) {
			return 1;
		} else {
			return (unsigned char) -1;
		}
	} else {
		if (s == -1) {
			return (unsigned char) -1;
		} else {
			return 1;
		}
	}
}

static void sub(uint32* rmsb, uint32* rlsb, uint32 xmsb, uint32 xlsb) {
	if (*rlsb < xlsb) {
		*rmsb = *rmsb - 1;
	}
	*rlsb = *rlsb - xlsb;
	*rmsb = *rmsb - xmsb;
}
#endif

#if defined(LCMP_OPCODE_USED) || defined(HANDLELCMP_USED)
int32 handleLCMP(int32* sp) {
	uint32 value2lsb = *(--sp);
	int32 value2msb = *(--sp);

	uint32 value1lsb = *(--sp);
	int32 value1msb = *(--sp);

	return lcmp(value1msb, value1lsb, value2msb, value2lsb);
}
#endif

#if defined(INVOKE_CLONE_ONARRAY_USED) || defined(HANDLECLONEONARRAY_USED)
signed char handleCloneOnArray(int32* sp) {
	unsigned char elementSize;
	uint16* ptr;
	unsigned char* dst;
	unsigned char* src;
	uint16 count;
	unsigned char* clone = 0;

	unsigned char* array = (unsigned char*) (pointer) *(--sp);
	if (array != 0) {
		unsigned short classIndex = getClassIndex((Object*) array);

		ptr = (uint16*) (HEAP_REF(array, unsigned char*) + sizeof(Object));
		count = *ptr;

		clone = createArray(classIndex, count FLASHARG(0));

		if (clone != 0) {
			elementSize = getElementSize(classIndex);
			src = HEAP_REF(array, unsigned char*) + sizeof(Object) + 2;
			dst = HEAP_REF(clone, unsigned char*) + sizeof(Object) + 2;
#if defined(GLIBC_DOES_NOT_SUPPORT_MUL)
			count = imul(elementSize, count);
#else
			count = elementSize * count;
#endif
			while (count) {
				*dst++ = *src++;
				count--;
			}
			*sp++ = (int32) (pointer) clone;
			return 0;
		} else {
			return 1;
		}
	} else {
		return -1;
	}
}
#endif

#if defined(LSHL_OPCODE_USED) || defined(HANDLELSHL_USED)
void handleLSHL(int32* sp) {
	uint32 msi, lsi, value;
	value = *(--sp) & 0x3f;
	lsi = *(--sp);
	msi = *(--sp);

	lshl(&msi, &lsi, value);

	*sp++ = msi;
	*sp++ = lsi;
}
#endif

#if defined(LSHR_OPCODE_USED) || defined(LUSHR_OPCODE_USED) || defined(HANDLELSHR_USED)
void handleLSHR(int32* sp) {
	uint32 msi, lsi, value;
	value = *(--sp) & 0x3f;
	lsi = *(--sp);
	msi = *(--sp);

	lshr(&msi, &lsi, value);

	*sp++ = msi;
	*sp++ = lsi;
}
#endif

#if defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED) || defined(N_VM_REALTIMECLOCK_GETNATIVERESOLUTION) || defined(N_VM_REALTIMECLOCK_GETNATIVETIME)
int32 lmul32(int32* sp, uint32 xmsb, uint32 xlsb, uint32 ymsb, uint32 ylsb) {
	uint32 rmsb = 0, rlsb = 0;

	if (lcmp(xmsb, xlsb, 0, 0) != 0) {
		if (lcmp(ymsb, ylsb, 0, 0) != 0) {
			signed char s = sign((int32*) &xmsb, (int32*) &xlsb, 1);
			s = sign((int32*) &ymsb, (int32*) &ylsb, s);

			while (!((ymsb == 0) && (ylsb <= 1))) {
				if ((ylsb & 0x1) == 1) {
					add(&rmsb, &rlsb, xmsb, xlsb);
					sub(&ymsb, &ylsb, 0, 1);
				} else {
					lshl(&xmsb, &xlsb, 1);
					lshr(&ymsb, &ylsb, 1);
				}
			}

			add(&rmsb, &rlsb, xmsb, xlsb);

			if (s < 0) {
				neg(&rmsb, &rlsb);
			}
		}
	}
	*sp++ = rmsb;
	*sp++ = rlsb;
	return 2;
}
#endif

#if defined(LREM_OPCODE_USED) || defined(LDIV_OPCODE_USED) || defined(LMUL_OPCODE_USED) || defined(HANDLELMULLDIVLREM_USED) || defined(N_VM_REALTIMECLOCK_DELAYNATIVEUNTIL)
int32 ldiv32(int32* sp, uint32 xmsb, uint32 xlsb, uint32 ymsb, uint32 ylsb) {
	uint32 kmsb, klsb;
	uint32 smsb, slsb;
	uint32 tmsb, tlsb;
	uint32 rmsb = 0, rlsb = 0;
	int32 res;

	signed char s = sign((int32*) &xmsb, (int32*) &xlsb, 1);
	s = sign((int32*) &ymsb, (int32*) &ylsb, s);

	while (lcmp(ymsb, ylsb, xmsb, xlsb) <= 0) {
		kmsb = 0;
		klsb = 1;

		smsb = ymsb;
		slsb = ylsb;

		tmsb = smsb;
		tlsb = slsb;

		lshl(&tmsb, &tlsb, 1);

		res = lcmp(tmsb, tlsb, xmsb, xlsb);

		while (res <= 0) {
			smsb = tmsb;
			slsb = tlsb;

			tmsb = smsb;
			tlsb = slsb;

			lshl(&tmsb, &tlsb, 1);

			res = lcmp(tmsb, tlsb, xmsb, xlsb);

			lshl(&kmsb, &klsb, 1);
		}

		sub(&xmsb, &xlsb, smsb, slsb);
		add(&rmsb, &rlsb, kmsb, klsb);
	}

	if (s < 0) {
		neg(&rmsb, &rlsb);
	}

	*sp++ = rmsb;
	*sp++ = rlsb;
	return 2;
}

unsigned char lrem32(int32* sp, uint32 xmsb, uint32 xlsb, uint32 ymsb,
		uint32 ylsb) {
	int32 temp[2];

	ldiv32(&temp[0], xmsb, xlsb, ymsb, ylsb);

	lmul32(&temp[0], temp[0], temp[1], ymsb, ylsb);

	*sp++ = xmsb;
	*sp++ = xlsb;
	*sp++ = temp[0];
	*sp++ = temp[1];

	handleLongOperator(LSUB_OPCODE, sp);
	return 2;
}

unsigned char handleLMULLDIVLREM(int32* sp, unsigned char code) {
	int32 xmsb, xlsb, ymsb, ylsb;
	ylsb = *(--sp);
	ymsb = *(--sp);
	xlsb = *(--sp);
	xmsb = *(--sp);
	if (code == LMUL_OPCODE) {
		return lmul32(sp, xmsb, xlsb, ymsb, ylsb);
	} else {
		if (lcmp(ymsb, ylsb, 0, 0) != 0) {
			if (code == LDIV_OPCODE) {
				return ldiv32(sp, xmsb, xlsb, ymsb, ylsb);
			} else {
				return lrem32(sp, xmsb, xlsb, ymsb, ylsb);
			}
		} else {
			return 0;
		}
	}
}
#endif

static int32* pushStackFrame(unsigned short index,
		unsigned short currentMethodNumber, unsigned short pc, int32* fp,
		int32* sp) {
	unsigned short top;
	top = sp - fp;
	fp = sp;
	fp[index] = (int32) (currentMethodNumber + 1);
	fp[index + 1] = ((int32) pc << 16) | top;
	return fp;
}

unsigned short popStackFrame(int32** fp, int32** sp,
		const MethodInfo* currentMethod, unsigned short *pc) {
	unsigned short index;
	unsigned char numReturnValues;
	unsigned short top = (*sp) - (*fp);
	int32 pctop;
	unsigned short currentMethodNumber;

	index = pgm_read_word(&currentMethod->maxLocals);

	numReturnValues = pgm_read_byte(&currentMethod->minfo) & 0x3;

	currentMethodNumber = (*fp)[index];
	if (currentMethodNumber != 0) {
		pctop = (*fp)[index + 1];
		*pc = pctop >> 16;
		top = pctop;
		*fp = *fp - top;

		top += numReturnValues;

		*sp = *fp + top;

		return currentMethodNumber;
	} else {
		*sp = *fp + top;
		return 0;
	}
}

extern unsigned char vm_initialized;

int16 enterMethodInterpreter(unsigned short methodNumber, int32* sp) {
	const MethodInfo* currentMethod = &methods[methodNumber];
	if (vm_initialized) {
		if (pgm_read_pointer(&currentMethod->code, unsigned char**) == 0) {
			int16 (*nativeFunc)(int32 *fp);
			nativeFunc = (int16 (*)(int32 *fp)) (pointer) pgm_read_pointer(
					&currentMethod->nativeFunc, int16 (**)(int32 *fp));
			return nativeFunc(sp);
		} else {
			unsigned short index = pgm_read_word(&currentMethod->maxLocals);
			sp[index] = (int32) (pointer) 0;
			sp[index + 1] = 0;
			return methodInterpreter(methodNumber, sp);
		}
	} else {
		return JAVA_LANG_OUTOFMEMORYERROR_var;
	}
}

#if defined(DISPATCHINTERFACE_USED)
signed short dispatchInterface(unsigned short methodNumber,
		unsigned char *minfo, int32* sp) {
	const MethodInfo* methodInfo;

	methodInfo = &methods[methodNumber];

	/* TODO: This is not necessary, minfo must be known by aot compiler */
	*minfo = pgm_read_byte(&methodInfo->minfo) & 0x3;

	if (pgm_read_pointer(&methodInfo->code, unsigned char**) == 0) {
		int16 (*nativeFunc)(int32 *fp);
		nativeFunc = (int16 (*)(int32 *fp)) (pointer) pgm_read_pointer(
				&methodInfo->nativeFunc, int16 (**)(int32 *fp));
		return nativeFunc(sp);
	} else {
		return enterMethodInterpreter(methodNumber, sp);
	}
}
#endif

#if defined(VM_MONITOR_LOCK__USED)
extern int16 vm_Monitor_lock_(int32 *fp);
#endif

#if defined(VM_MONITOR_UNLOCK__USED)
extern int16 vm_Monitor_unlock_(int32 *fp);
#endif

unsigned char handleMonitorEnterExit(Object* this, unsigned char isEnter,
		int32* sp, const char* fromMethod) {
	uint32* ptr;
	Object* monitor;
	if (classes[getClassIndex(this)].hasLock) {
		ptr = (uint32*) ((unsigned char*) HEAP_REF(this, unsigned char*) - 4);
		monitor = (Object*) (pointer) *ptr;

		if (monitor != 0) {
			if (isEnter) {
#if defined(VM_MONITOR_LOCK__USED)
				*sp = (int32) (pointer) monitor;
				if (vm_Monitor_lock_(sp) != -1) {
					return 0;
				}
#endif
			} else {
#if defined(VM_MONITOR_UNLOCK__USED)
				*sp = (int32) (pointer) monitor;
				if (vm_Monitor_unlock_(sp) != -1) {
					return 0;
				}
#endif
			}
			return 1;
		} else {
			return 1;
		}
	} else {
		return 1;
	}
}

const char* getClassName(unsigned short classIndex) {
	return (const char*) pgm_read_pointer(&classes[classIndex].name,
			const char**);
}

const char* getMethodName(unsigned short methodIndex) {
	return (const char*) pgm_read_pointer(&methods[methodIndex].name,
			const char**);
}
