package icecaptools.compiler.aot;

import icecaptools.MethodOrFieldDesc;


public interface InvokeEmitter {
    void setup() throws Exception;
    String callSetup() throws Exception;
    void callSetupException() throws Exception;
    void performCall() throws Exception;
    void handleReturnValue() throws Exception;
    boolean isWithArgsEmitter();
    MethodOrFieldDesc getMethodDesc();
    void setMethodDesc(MethodOrFieldDesc methodDesc) throws Exception;
}
