package test;

import icecaptools.IcecapCVar;
import icecaptools.IcecapCompileMe;
import vm.VMTest;

public class TestCVar2 {

    @IcecapCVar(expression = "methods[TEST_TESTCVAR2_MAIN].numArgs", requiredIncludes = "#include \"types.h\"\n#include \"methods.h\"\nextern const MethodInfo *methods;\n")
    static byte numArg;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    @IcecapCompileMe
    private static boolean test() {
        if (numArg == 1)
        {
            return false;
        }
        return true;
    }

}
