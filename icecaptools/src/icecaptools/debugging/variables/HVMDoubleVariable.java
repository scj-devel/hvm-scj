package icecaptools.debugging.variables;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.eclipse.debug.core.model.IDebugTarget;

public class HVMDoubleVariable extends HVMVariable {
    public HVMDoubleVariable(IDebugTarget iDebugTarget, LocalVariable localVariable, Method method) {
        super(iDebugTarget, localVariable, method);
    }
}
