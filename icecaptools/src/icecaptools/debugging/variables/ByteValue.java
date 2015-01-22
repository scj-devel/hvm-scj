package icecaptools.debugging.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

public class ByteValue extends NumberValue {

    public ByteValue(IDebugTarget target, byte ival) {
        super(target, ival);
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "byte";
    }

}
