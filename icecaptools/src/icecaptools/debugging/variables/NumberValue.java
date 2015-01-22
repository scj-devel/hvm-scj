package icecaptools.debugging.variables;

import icecaptools.debugging.HVMDebugElement;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public abstract class NumberValue extends HVMDebugElement implements IValue {

    protected long nval;

    public NumberValue(IDebugTarget target, long nval) {
        super(target);
        this.nval = nval;
    }

    @Override
    public String getValueString() throws DebugException {
        return nval + "";
    }

    @Override
    public boolean isAllocated() throws DebugException {
        return false;
    }

    @Override
    public IVariable[] getVariables() throws DebugException {
        return null;
    }

    @Override
    public boolean hasVariables() throws DebugException {
        return false;
    }
}
