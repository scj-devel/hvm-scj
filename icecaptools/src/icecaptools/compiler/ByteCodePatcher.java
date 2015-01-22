package icecaptools.compiler;

import icecaptools.AnalysisObserver;
import icecaptools.BNode;
import icecaptools.MethodOrFieldDesc;
import java.util.ArrayList;

public interface ByteCodePatcher {

    void patch(String className, String methodName, String methodSignature, byte[] currentMethodCode, AnalysisObserver observer, IDGenerator idGen) throws Exception;

    int getClassNumber(String clazz);
    
    String getClassName(int number);

    void registerFieldNumber(String currentClassName, FieldInfo field, int fieldNumber, boolean isStatic);

    void registerInterface(String implementor, String _interface);

    void registerMethodNumber(MethodOrFieldDesc currentMethod, int methodNumber, IDGenerator idGen);

    void registerClassNumber(String currentClassName, int classNumber);

    InterfaceManager getInterfaceManager();

    ArrayList<LDCConstant> getConstants();

    int getNumberOfClasses();

    MethodOrFieldDesc getMethodDescriptor(int methodNumber);

    public void registerByteCode(String className, String targetMethodName, String targetMethodSignature, BNode node);
    
    RequiredMethodVTableIndexManager getRequiredVTableIndices();
    
    FieldInfo[] getFieldOffsets();

    int getMethodNumber(MethodOrFieldDesc nextMethod, IDGenerator idGen);
}
