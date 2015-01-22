package icecaptools.compiler;

import org.apache.bcel.classfile.Method;

import icecaptools.compiler.aot.AOTCompiler;
import icecaptools.compiler.aot.AOTToolBox;

public class UserIncudesAwareFlowBasedCompiler extends AOTCompiler {

    private NoDuplicatesMemorySegment userIncludes;

    public UserIncudesAwareFlowBasedCompiler(Method javaMethod, byte[] methodCode, String uniqueMethodId, int methodNumber, NoDuplicatesMemorySegment requiredIncludes, NoDuplicatesMemorySegment userIncludes, AOTToolBox toolBox) {
        super(javaMethod, methodCode, uniqueMethodId, methodNumber, requiredIncludes, toolBox);
        this.userIncludes = userIncludes;
    }

    @Override
    public void addUserIncludes(NoDuplicatesMemorySegment requiredIncludes, String includes) {
        userIncludes.print(includes);
    }
}
