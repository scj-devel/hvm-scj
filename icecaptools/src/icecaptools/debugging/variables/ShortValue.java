package icecaptools.debugging.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

public class ShortValue extends NumberValue {

    public ShortValue(IDebugTarget target, short ival) {
        super(target, ival);
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "short";
    }
}
