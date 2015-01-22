package icecaptools.debugging.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

public class LongValue extends NumberValue {

    public LongValue(IDebugTarget target, long ival) {
        super(target, ival);
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "long";
    }

}
