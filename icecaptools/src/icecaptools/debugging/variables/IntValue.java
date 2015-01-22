package icecaptools.debugging.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

public class IntValue extends NumberValue {

    public IntValue(IDebugTarget target, int ival) {
        super(target, ival);
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "int";
    }
}
