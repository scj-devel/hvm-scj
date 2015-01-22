package icecaptools.debugging.variables;

import icecaptools.debugging.HVMPOSIXDebugTarget;

import java.io.IOException;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;

public abstract class HVMNumberVariable extends HVMVariable {

    private IValue iValue;

    public HVMNumberVariable(IDebugTarget iDebugTarget, LocalVariable localVariable, Method method) {
        super(iDebugTarget, localVariable, method);
    }

    @Override
    public IValue getValue() throws DebugException {
        synchronized (getDebugTarget()) {
            if (iValue == null) {
                byte[] val = getValueBuffer();
                try {
                    short index = (short) localVariable.getIndex();
                    ((HVMPOSIXDebugTarget) target).getValueFromStack(index, val);
                    long ival = 0;
                    for (int i = val.length - 1; i >= 0; i--) {
                        ival = ival << 8;
                        ival |= (val[i] & 0xff);
                    }
                    this.iValue = getNumberValue(target, ival);
                    return iValue;
                } catch (IOException e) {
                    throw new DebugException(new IStatus() {

                        private static final String message = "Could not get number value";

                        @Override
                        public IStatus[] getChildren() {
                            return null;
                        }

                        @Override
                        public int getCode() {
                            return IStatus.ERROR;
                        }

                        @Override
                        public Throwable getException() {
                            return new Exception(message);
                        }

                        @Override
                        public String getMessage() {
                            return message;
                        }

                        @Override
                        public String getPlugin() {
                            return DebugPlugin.getUniqueIdentifier();
                        }

                        @Override
                        public int getSeverity() {
                            return IStatus.ERROR;
                        }

                        @Override
                        public boolean isMultiStatus() {
                            return false;
                        }

                        @Override
                        public boolean isOK() {
                            return false;
                        }

                        @Override
                        public boolean matches(int severityMask) {
                            return false;
                        };
                    });
                }
            } else {
                return iValue;
            }
        }
    }

    protected abstract IValue getNumberValue(IDebugTarget target, long ival);

    protected abstract byte[] getValueBuffer();
}
