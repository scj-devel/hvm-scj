package icecaptools.debugging.variables;

import icecaptools.debugging.HVMDebugElement;
import icecaptools.debugging.UnknownHVMValue;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class HVMVariable extends HVMDebugElement implements IVariable {

    protected LocalVariable localVariable;
    @SuppressWarnings("unused")
    private Method enclosingMethod;

    public HVMVariable(IDebugTarget iDebugTarget, LocalVariable localVariable, Method method) {
        super(iDebugTarget);
        this.localVariable = localVariable;
        this.enclosingMethod = method;
    }

    @Override
    public void setValue(String expression) throws DebugException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setValue(IValue value) throws DebugException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean supportsValueModification() {
        return false;
    }

    @Override
    public boolean verifyValue(String expression) throws DebugException {
        return false;
    }

    @Override
    public boolean verifyValue(IValue value) throws DebugException {
        return false;
    }

    @Override
    public IValue getValue() throws DebugException {
        return new UnknownHVMValue(getDebugTarget());
    }

    @Override
    public String getName() throws DebugException {
        return localVariable.getName();
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return null;
    }

    @Override
    public boolean hasValueChanged() throws DebugException {
        return false;
    }
}
