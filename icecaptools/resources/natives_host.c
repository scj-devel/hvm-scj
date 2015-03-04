#include "natives.h"

#include <stdio.h>

#include <stdlib.h>

extern void dumpByte(unsigned char b);
void dumpNativeFunc(int16(*nativeFunc)(int32 *sp), const char* functionName) {
    if (nativeFunc == n_devices_X86Writer_nwrite) {
        dumpByte(N_DEVICES_X86WRITER_NWRITE_NUM);
    } else if (nativeFunc == n_java_io_BufferedInputStream_init_) {
        dumpByte(N_JAVA_IO_BUFFEREDINPUTSTREAM_INIT__NUM);
    } else if (nativeFunc == n_java_io_BufferedOutputStream_init_) {
        dumpByte(N_JAVA_IO_BUFFEREDOUTPUTSTREAM_INIT__NUM);
    } else if (nativeFunc == n_java_io_FileDescriptor_clinit_) {
        dumpByte(N_JAVA_IO_FILEDESCRIPTOR_CLINIT__NUM);
    } else if (nativeFunc == n_java_io_FileInputStream_init_) {
        dumpByte(N_JAVA_IO_FILEINPUTSTREAM_INIT__NUM);
    } else if (nativeFunc == n_java_io_FileOutputStream_init_) {
        dumpByte(N_JAVA_IO_FILEOUTPUTSTREAM_INIT__NUM);
    } else if (nativeFunc == n_java_io_File_clinit_) {
        dumpByte(N_JAVA_IO_FILE_CLINIT__NUM);
    } else if (nativeFunc == n_java_io_File_equals) {
        dumpByte(N_JAVA_IO_FILE_EQUALS_NUM);
    } else if (nativeFunc == n_java_io_File_exists) {
        dumpByte(N_JAVA_IO_FILE_EXISTS_NUM);
    } else if (nativeFunc == n_java_io_File_getCanonicalPath) {
        dumpByte(N_JAVA_IO_FILE_GETCANONICALPATH_NUM);
    } else if (nativeFunc == n_java_io_File_getName) {
        dumpByte(N_JAVA_IO_FILE_GETNAME_NUM);
    } else if (nativeFunc == n_java_io_File_hashCode) {
        dumpByte(N_JAVA_IO_FILE_HASHCODE_NUM);
    } else if (nativeFunc == n_java_io_File_init_) {
        dumpByte(N_JAVA_IO_FILE_INIT__NUM);
    } else if (nativeFunc == n_java_io_File_init__) {
        dumpByte(N_JAVA_IO_FILE_INIT___NUM);
    } else if (nativeFunc == n_java_io_File_isAbsolute) {
        dumpByte(N_JAVA_IO_FILE_ISABSOLUTE_NUM);
    } else if (nativeFunc == n_java_io_File_toString) {
        dumpByte(N_JAVA_IO_FILE_TOSTRING_NUM);
    } else if (nativeFunc == n_java_io_PrintStream_flush) {
        dumpByte(N_JAVA_IO_PRINTSTREAM_FLUSH_NUM);
    } else if (nativeFunc == n_java_io_PrintStream_init_) {
        dumpByte(N_JAVA_IO_PRINTSTREAM_INIT__NUM);
    } else if (nativeFunc == n_java_io_PrintStream_init__) {
        dumpByte(N_JAVA_IO_PRINTSTREAM_INIT___NUM);
    } else if (nativeFunc == n_java_io_PrintStream_println) {
        dumpByte(N_JAVA_IO_PRINTSTREAM_PRINTLN_NUM);
    } else if (nativeFunc == n_java_lang_ClassLoader_NativeLibrary_findBuiltinLib) {
        dumpByte(N_JAVA_LANG_CLASSLOADER_NATIVELIBRARY_FINDBUILTINLIB_NUM);
    } else if (nativeFunc == n_java_lang_ClassLoader_NativeLibrary_load) {
        dumpByte(N_JAVA_LANG_CLASSLOADER_NATIVELIBRARY_LOAD_NUM);
    } else if (nativeFunc == n_java_lang_ClassLoader_registerNatives) {
        dumpByte(N_JAVA_LANG_CLASSLOADER_REGISTERNATIVES_NUM);
    } else if (nativeFunc == n_java_lang_Class_clinit_) {
        dumpByte(N_JAVA_LANG_CLASS_CLINIT__NUM);
    } else if (nativeFunc == n_java_lang_Class_desiredAssertionStatus) {
        dumpByte(N_JAVA_LANG_CLASS_DESIREDASSERTIONSTATUS_NUM);
    } else if (nativeFunc == n_java_lang_Class_forName) {
        dumpByte(N_JAVA_LANG_CLASS_FORNAME_NUM);
    } else if (nativeFunc == n_java_lang_Class_getClassLoader) {
        dumpByte(N_JAVA_LANG_CLASS_GETCLASSLOADER_NUM);
    } else if (nativeFunc == n_java_lang_Class_getComponentType) {
        dumpByte(N_JAVA_LANG_CLASS_GETCOMPONENTTYPE_NUM);
    } else if (nativeFunc == n_java_lang_Class_getConstructor) {
        dumpByte(N_JAVA_LANG_CLASS_GETCONSTRUCTOR_NUM);
    } else if (nativeFunc == n_java_lang_Class_getModifiers) {
        dumpByte(N_JAVA_LANG_CLASS_GETMODIFIERS_NUM);
    } else if (nativeFunc == n_java_lang_Class_getName0) {
        dumpByte(N_JAVA_LANG_CLASS_GETNAME0_NUM);
    } else if (nativeFunc == n_java_lang_Class_getPrimitiveClass) {
        dumpByte(N_JAVA_LANG_CLASS_GETPRIMITIVECLASS_NUM);
    } else if (nativeFunc == n_java_lang_Class_getTypeName) {
        dumpByte(N_JAVA_LANG_CLASS_GETTYPENAME_NUM);
    } else if (nativeFunc == n_java_lang_Class_toString) {
        dumpByte(N_JAVA_LANG_CLASS_TOSTRING_NUM);
    } else if (nativeFunc == n_java_lang_Double_doubleToRawLongBits) {
        dumpByte(N_JAVA_LANG_DOUBLE_DOUBLETORAWLONGBITS_NUM);
    } else if (nativeFunc == n_java_lang_Double_longBitsToDouble) {
        dumpByte(N_JAVA_LANG_DOUBLE_LONGBITSTODOUBLE_NUM);
    } else if (nativeFunc == n_java_lang_Float_floatToRawIntBits) {
        dumpByte(N_JAVA_LANG_FLOAT_FLOATTORAWINTBITS_NUM);
    } else if (nativeFunc == n_java_lang_Object_getClass) {
        dumpByte(N_JAVA_LANG_OBJECT_GETCLASS_NUM);
    } else if (nativeFunc == n_java_lang_Object_hashCode) {
        dumpByte(N_JAVA_LANG_OBJECT_HASHCODE_NUM);
    } else if (nativeFunc == n_java_lang_StringBuilder_append) {
        dumpByte(N_JAVA_LANG_STRINGBUILDER_APPEND_NUM);
    } else if (nativeFunc == n_java_lang_System_arraycopy) {
        dumpByte(N_JAVA_LANG_SYSTEM_ARRAYCOPY_NUM);
    } else if (nativeFunc == n_java_lang_System_initProperties) {
        dumpByte(N_JAVA_LANG_SYSTEM_INITPROPERTIES_NUM);
    } else if (nativeFunc == n_java_lang_System_mapLibraryName) {
        dumpByte(N_JAVA_LANG_SYSTEM_MAPLIBRARYNAME_NUM);
    } else if (nativeFunc == n_java_lang_System_registerNatives) {
        dumpByte(N_JAVA_LANG_SYSTEM_REGISTERNATIVES_NUM);
    } else if (nativeFunc == n_java_lang_System_setErr0) {
        dumpByte(N_JAVA_LANG_SYSTEM_SETERR0_NUM);
    } else if (nativeFunc == n_java_lang_System_setIn0) {
        dumpByte(N_JAVA_LANG_SYSTEM_SETIN0_NUM);
    } else if (nativeFunc == n_java_lang_System_setOut0) {
        dumpByte(N_JAVA_LANG_SYSTEM_SETOUT0_NUM);
    } else if (nativeFunc == n_java_lang_ThreadGroup_add) {
        dumpByte(N_JAVA_LANG_THREADGROUP_ADD_NUM);
    } else if (nativeFunc == n_java_lang_ThreadGroup_toString) {
        dumpByte(N_JAVA_LANG_THREADGROUP_TOSTRING_NUM);
    } else if (nativeFunc == n_java_lang_Thread_clinit_) {
        dumpByte(N_JAVA_LANG_THREAD_CLINIT__NUM);
    } else if (nativeFunc == n_java_lang_Thread_currentThread) {
        dumpByte(N_JAVA_LANG_THREAD_CURRENTTHREAD_NUM);
    } else if (nativeFunc == n_java_lang_Thread_getThreadGroup) {
        dumpByte(N_JAVA_LANG_THREAD_GETTHREADGROUP_NUM);
    } else if (nativeFunc == n_java_lang_Thread_run) {
        dumpByte(N_JAVA_LANG_THREAD_RUN_NUM);
    } else if (nativeFunc == n_java_lang_Thread_toString) {
        dumpByte(N_JAVA_LANG_THREAD_TOSTRING_NUM);
    } else if (nativeFunc == n_java_lang_Throwable_clinit_) {
        dumpByte(N_JAVA_LANG_THROWABLE_CLINIT__NUM);
    } else if (nativeFunc == n_java_lang_Throwable_fillInStackTrace_) {
        dumpByte(N_JAVA_LANG_THROWABLE_FILLINSTACKTRACE__NUM);
    } else if (nativeFunc == n_java_lang_Throwable_initCause) {
        dumpByte(N_JAVA_LANG_THROWABLE_INITCAUSE_NUM);
    } else if (nativeFunc == n_java_lang_Throwable_init___) {
        dumpByte(N_JAVA_LANG_THROWABLE_INIT____NUM);
    } else if (nativeFunc == n_java_lang_Throwable_printStackTrace) {
        dumpByte(N_JAVA_LANG_THROWABLE_PRINTSTACKTRACE_NUM);
    } else if (nativeFunc == n_java_lang_reflect_Array_newArray) {
        dumpByte(N_JAVA_LANG_REFLECT_ARRAY_NEWARRAY_NUM);
    } else if (nativeFunc == n_java_security_AccessController_doPrivileged) {
        dumpByte(N_JAVA_SECURITY_ACCESSCONTROLLER_DOPRIVILEGED_NUM);
    } else if (nativeFunc == n_reflect_Unboxing_reportBoolean) {
        dumpByte(N_REFLECT_UNBOXING_REPORTBOOLEAN_NUM);
    } else if (nativeFunc == n_reflect_Unboxing_reportByte) {
        dumpByte(N_REFLECT_UNBOXING_REPORTBYTE_NUM);
    } else if (nativeFunc == n_reflect_Unboxing_reportCharacter) {
        dumpByte(N_REFLECT_UNBOXING_REPORTCHARACTER_NUM);
    } else if (nativeFunc == n_reflect_Unboxing_reportInt) {
        dumpByte(N_REFLECT_UNBOXING_REPORTINT_NUM);
    } else if (nativeFunc == n_reflect_Unboxing_reportLong) {
        dumpByte(N_REFLECT_UNBOXING_REPORTLONG_NUM);
    } else if (nativeFunc == n_reflect_Unboxing_reportShort) {
        dumpByte(N_REFLECT_UNBOXING_REPORTSHORT_NUM);
    } else if (nativeFunc == n_sun_misc_SharedSecrets_clinit_) {
        dumpByte(N_SUN_MISC_SHAREDSECRETS_CLINIT__NUM);
    } else if (nativeFunc == n_sun_misc_SharedSecrets_setJavaLangAccess) {
        dumpByte(N_SUN_MISC_SHAREDSECRETS_SETJAVALANGACCESS_NUM);
    } else if (nativeFunc == n_sun_misc_Signal_clinit_) {
        dumpByte(N_SUN_MISC_SIGNAL_CLINIT__NUM);
    } else if (nativeFunc == n_sun_misc_Signal_equals) {
        dumpByte(N_SUN_MISC_SIGNAL_EQUALS_NUM);
    } else if (nativeFunc == n_sun_misc_Signal_handle) {
        dumpByte(N_SUN_MISC_SIGNAL_HANDLE_NUM);
    } else if (nativeFunc == n_sun_misc_Signal_hashCode) {
        dumpByte(N_SUN_MISC_SIGNAL_HASHCODE_NUM);
    } else if (nativeFunc == n_sun_misc_Signal_init_) {
        dumpByte(N_SUN_MISC_SIGNAL_INIT__NUM);
    } else if (nativeFunc == n_sun_misc_Signal_toString) {
        dumpByte(N_SUN_MISC_SIGNAL_TOSTRING_NUM);
    } else if (nativeFunc == n_sun_misc_VM_booted) {
        dumpByte(N_SUN_MISC_VM_BOOTED_NUM);
    } else if (nativeFunc == n_sun_misc_VM_clinit_) {
        dumpByte(N_SUN_MISC_VM_CLINIT__NUM);
    } else if (nativeFunc == n_sun_misc_VM_initializeOSEnvironment) {
        dumpByte(N_SUN_MISC_VM_INITIALIZEOSENVIRONMENT_NUM);
    } else if (nativeFunc == n_sun_misc_VM_saveAndRemoveProperties) {
        dumpByte(N_SUN_MISC_VM_SAVEANDREMOVEPROPERTIES_NUM);
    } else if (nativeFunc == n_sun_misc_Version_clinit_) {
        dumpByte(N_SUN_MISC_VERSION_CLINIT__NUM);
    } else if (nativeFunc == n_sun_misc_Version_init) {
        dumpByte(N_SUN_MISC_VERSION_INIT_NUM);
    } else if (nativeFunc == n_sun_reflect_ConstructorAccessor_newInstance) {
        dumpByte(N_SUN_REFLECT_CONSTRUCTORACCESSOR_NEWINSTANCE_NUM);
    } else if (nativeFunc == n_sun_reflect_ReflectionFactory_GetReflectionFactoryAction_init_) {
        dumpByte(N_SUN_REFLECT_REFLECTIONFACTORY_GETREFLECTIONFACTORYACTION_INIT__NUM);
    } else if (nativeFunc == n_sun_reflect_ReflectionFactory_GetReflectionFactoryAction_run) {
        dumpByte(N_SUN_REFLECT_REFLECTIONFACTORY_GETREFLECTIONFACTORYACTION_RUN_NUM);
    } else if (nativeFunc == n_sun_reflect_ReflectionFactory_newConstructorAccessor) {
        dumpByte(N_SUN_REFLECT_REFLECTIONFACTORY_NEWCONSTRUCTORACCESSOR_NUM);
    } else if (nativeFunc == n_sun_reflect_ReflectionFactory_setLangReflectAccess) {
        dumpByte(N_SUN_REFLECT_REFLECTIONFACTORY_SETLANGREFLECTACCESS_NUM);
    } else if (nativeFunc == n_sun_reflect_Reflection_clinit_) {
        dumpByte(N_SUN_REFLECT_REFLECTION_CLINIT__NUM);
    } else if (nativeFunc == n_sun_reflect_Reflection_ensureMemberAccess) {
        dumpByte(N_SUN_REFLECT_REFLECTION_ENSUREMEMBERACCESS_NUM);
    } else if (nativeFunc == n_sun_reflect_Reflection_getCallerClass) {
        dumpByte(N_SUN_REFLECT_REFLECTION_GETCALLERCLASS_NUM);
    } else if (nativeFunc == n_sun_reflect_Reflection_quickCheckMemberAccess) {
        dumpByte(N_SUN_REFLECT_REFLECTION_QUICKCHECKMEMBERACCESS_NUM);
    } else {
        printf("Unsupported native function (%s)\n", functionName);
        exit(3);
    }
}

int16 n_devices_X86Writer_nwrite(int32 *sp) {
   return -1;
}

int16 n_java_io_BufferedInputStream_init_(int32 *sp) {
   return -1;
}

int16 n_java_io_BufferedOutputStream_init_(int32 *sp) {
   return -1;
}

int16 n_java_io_FileDescriptor_clinit_(int32 *sp) {
   return -1;
}

int16 n_java_io_FileInputStream_init_(int32 *sp) {
   return -1;
}

int16 n_java_io_FileOutputStream_init_(int32 *sp) {
   return -1;
}

int16 n_java_io_File_clinit_(int32 *sp) {
   return -1;
}

int16 n_java_io_File_equals(int32 *sp) {
   return -1;
}

int16 n_java_io_File_exists(int32 *sp) {
   return -1;
}

int16 n_java_io_File_getCanonicalPath(int32 *sp) {
   return -1;
}

int16 n_java_io_File_getName(int32 *sp) {
   return -1;
}

int16 n_java_io_File_hashCode(int32 *sp) {
   return -1;
}

int16 n_java_io_File_init_(int32 *sp) {
   return -1;
}

int16 n_java_io_File_init__(int32 *sp) {
   return -1;
}

int16 n_java_io_File_isAbsolute(int32 *sp) {
   return -1;
}

int16 n_java_io_File_toString(int32 *sp) {
   return -1;
}

int16 n_java_io_PrintStream_flush(int32 *sp) {
   return -1;
}

int16 n_java_io_PrintStream_init_(int32 *sp) {
   return -1;
}

int16 n_java_io_PrintStream_init__(int32 *sp) {
   return -1;
}

int16 n_java_io_PrintStream_println(int32 *sp) {
   return -1;
}

int16 n_java_lang_ClassLoader_NativeLibrary_findBuiltinLib(int32 *sp) {
   return -1;
}

int16 n_java_lang_ClassLoader_NativeLibrary_load(int32 *sp) {
   return -1;
}

int16 n_java_lang_ClassLoader_registerNatives(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_clinit_(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_desiredAssertionStatus(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_forName(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getClassLoader(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getComponentType(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getConstructor(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getModifiers(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getName0(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getPrimitiveClass(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_getTypeName(int32 *sp) {
   return -1;
}

int16 n_java_lang_Class_toString(int32 *sp) {
   return -1;
}

int16 n_java_lang_Double_doubleToRawLongBits(int32 *sp) {
   return -1;
}

int16 n_java_lang_Double_longBitsToDouble(int32 *sp) {
   return -1;
}

int16 n_java_lang_Float_floatToRawIntBits(int32 *sp) {
   return -1;
}

int16 n_java_lang_Object_getClass(int32 *sp) {
   return -1;
}

int16 n_java_lang_Object_hashCode(int32 *sp) {
   return -1;
}

int16 n_java_lang_StringBuilder_append(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_arraycopy(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_initProperties(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_mapLibraryName(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_registerNatives(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_setErr0(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_setIn0(int32 *sp) {
   return -1;
}

int16 n_java_lang_System_setOut0(int32 *sp) {
   return -1;
}

int16 n_java_lang_ThreadGroup_add(int32 *sp) {
   return -1;
}

int16 n_java_lang_ThreadGroup_toString(int32 *sp) {
   return -1;
}

int16 n_java_lang_Thread_clinit_(int32 *sp) {
   return -1;
}

int16 n_java_lang_Thread_currentThread(int32 *sp) {
   return -1;
}

int16 n_java_lang_Thread_getThreadGroup(int32 *sp) {
   return -1;
}

int16 n_java_lang_Thread_run(int32 *sp) {
   return -1;
}

int16 n_java_lang_Thread_toString(int32 *sp) {
   return -1;
}

int16 n_java_lang_Throwable_clinit_(int32 *sp) {
   return -1;
}

int16 n_java_lang_Throwable_fillInStackTrace_(int32 *sp) {
   return -1;
}

int16 n_java_lang_Throwable_initCause(int32 *sp) {
   return -1;
}

int16 n_java_lang_Throwable_init___(int32 *sp) {
   return -1;
}

int16 n_java_lang_Throwable_printStackTrace(int32 *sp) {
   return -1;
}

int16 n_java_lang_reflect_Array_newArray(int32 *sp) {
   return -1;
}

int16 n_java_security_AccessController_doPrivileged(int32 *sp) {
   return -1;
}

int16 n_reflect_Unboxing_reportBoolean(int32 *sp) {
   return -1;
}

int16 n_reflect_Unboxing_reportByte(int32 *sp) {
   return -1;
}

int16 n_reflect_Unboxing_reportCharacter(int32 *sp) {
   return -1;
}

int16 n_reflect_Unboxing_reportInt(int32 *sp) {
   return -1;
}

int16 n_reflect_Unboxing_reportLong(int32 *sp) {
   return -1;
}

int16 n_reflect_Unboxing_reportShort(int32 *sp) {
   return -1;
}

int16 n_sun_misc_SharedSecrets_clinit_(int32 *sp) {
   return -1;
}

int16 n_sun_misc_SharedSecrets_setJavaLangAccess(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Signal_clinit_(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Signal_equals(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Signal_handle(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Signal_hashCode(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Signal_init_(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Signal_toString(int32 *sp) {
   return -1;
}

int16 n_sun_misc_VM_booted(int32 *sp) {
   return -1;
}

int16 n_sun_misc_VM_clinit_(int32 *sp) {
   return -1;
}

int16 n_sun_misc_VM_initializeOSEnvironment(int32 *sp) {
   return -1;
}

int16 n_sun_misc_VM_saveAndRemoveProperties(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Version_clinit_(int32 *sp) {
   return -1;
}

int16 n_sun_misc_Version_init(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_ConstructorAccessor_newInstance(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_ReflectionFactory_GetReflectionFactoryAction_init_(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_ReflectionFactory_GetReflectionFactoryAction_run(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_ReflectionFactory_newConstructorAccessor(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_ReflectionFactory_setLangReflectAccess(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_Reflection_clinit_(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_Reflection_ensureMemberAccess(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_Reflection_getCallerClass(int32 *sp) {
   return -1;
}

int16 n_sun_reflect_Reflection_quickCheckMemberAccess(int32 *sp) {
   return -1;
}

