#include "natives.h"

extern unsigned char readByte();
extern void printStr(const char* str);
fptr readNativeFunc(void) {
    unsigned char b = readByte();
    switch (b) {
    case N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE_NUM:
        return n_java_lang_Throwable_fillInStackTrace;
    }
    printStr("Unsupported native function");
    return 0;
}
