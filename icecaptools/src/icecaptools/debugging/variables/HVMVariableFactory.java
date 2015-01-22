package icecaptools.debugging.variables;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IVariable;

public class HVMVariableFactory {

    public static IVariable getHVMVariable(IDebugTarget debugTarget, LocalVariable localVariable, Method method) {

        String sig = Utility.signatureToString(localVariable.getSignature());
        if ("byte".equals(sig)) {
            return new HVMByteVariable(debugTarget, localVariable, method);
        } else if ("char".equals(sig)) {
            return new HVMCharVariable(debugTarget, localVariable, method);
        } else if ("double".equals(sig)) {
            return new HVMDoubleVariable(debugTarget, localVariable, method);
        } else if ("float".equals(sig)) {
            return new HVMFloatVariable(debugTarget, localVariable, method);
        } else if ("int".equals(sig)) {
            return new HVMIntVariable(debugTarget, localVariable, method);
        } else if ("long".equals(sig)) {
            return new HVMLongVariable(debugTarget, localVariable, method);
        } else if ("short".equals(sig)) {
            return new HVMShortVariable(debugTarget, localVariable, method);
        } else if ("boolean".equals(sig)) {
            return new HVMBooleanVariable(debugTarget, localVariable, method);
        } else {
            return new HVMReferenceVariable(debugTarget, localVariable, method);
        }
    }
}
