package icecaptools.debugging.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

public class BooleanValue extends NumberValue {

    public BooleanValue(IDebugTarget target, long nval) {
        super(target, nval);
    }

    @Override
    public String getValueString() throws DebugException {
        if (nval > 0)
        {
            return "true";
        }
        else
        {
            return "false";
        }
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "boolean";
    }
}
