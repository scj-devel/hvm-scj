package icecaptools.debugging;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class UnknownHVMValue extends HVMDebugElement implements IValue {

    public UnknownHVMValue(IDebugTarget target) {
        super(target);
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "Unknown";
    }

    @Override
    public String getValueString() throws DebugException {
        return "??";
    }

    @Override
    public boolean isAllocated() throws DebugException {
        return false;
    }

    @Override
    public IVariable[] getVariables() throws DebugException {
        return new IVariable[0];
    }

    @Override
    public boolean hasVariables() throws DebugException {
        return false;
    }
}
