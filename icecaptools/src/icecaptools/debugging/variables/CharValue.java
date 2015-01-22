package icecaptools.debugging.variables;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;

public class CharValue extends NumberValue {

    public CharValue(IDebugTarget target, long nval) {
        super(target, nval);
    }

    @Override
    public String getValueString() throws DebugException {
        byte b = (byte)nval;
        char c = (char) b;
        return "" + c;
    }

    @Override
    public String getReferenceTypeName() throws DebugException {
        return "char";
    }
}
