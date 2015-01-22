package icecaptools.debugging.variables;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;

public class HVMShortVariable extends HVMNumberVariable {

    public HVMShortVariable(IDebugTarget iDebugTarget, LocalVariable localVariable, Method method) {
        super(iDebugTarget, localVariable, method);
    }

    @Override
    protected IValue getNumberValue(IDebugTarget target, long ival) {
        return new ShortValue(target, (short)ival);
    }

    @Override
    protected byte[] getValueBuffer() {
        return new byte[2];
    }
}
