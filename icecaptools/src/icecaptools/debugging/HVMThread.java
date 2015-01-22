package icecaptools.debugging;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public class HVMThread extends HVMDebugElement implements IThread {

    private IStackFrame[] stackFrames;

    @SuppressWarnings("unused")
    private static final int STEPRETURN = 2;

    protected HVMThread(IDebugTarget target) {
        super(target);
        stackFrames = null;
    }

    @Override
    public boolean canResume() {
        return ((HVMPOSIXDebugTarget) target).canResume();
    }

    @Override
    public boolean canSuspend() {
        return ((HVMPOSIXDebugTarget) target).canSuspend();
    }

    @Override
    public boolean isSuspended() {
        return ((HVMPOSIXDebugTarget) target).isSuspended();
    }

    @Override
    public void resume() throws DebugException {
        ((HVMPOSIXDebugTarget) target).resume();
    }

    @Override
    public void suspend() throws DebugException {
        ((HVMPOSIXDebugTarget) target).suspend();
    }

    @Override
    public boolean canStepInto() {
        return isSuspended();
    }

    @Override
    public boolean canStepOver() {
        return isSuspended();
    }

    @Override
    public boolean canStepReturn() {
        return false;
    }

    @Override
    public boolean isStepping() {
        return ((HVMPOSIXDebugTarget) target).isStepping();
    }

    @Override
    public void stepInto() throws DebugException {
        ((HVMPOSIXDebugTarget) target).stepInto();
    }

    @Override
    public void stepOver() throws DebugException {
        ((HVMPOSIXDebugTarget) target).stepOver();
    }

    @Override
    public void stepReturn() throws DebugException {
        System.out.println("HVMThread: stepReturn");

    }

    @Override
    public boolean canTerminate() {
        return ((HVMPOSIXDebugTarget) target).canTerminate();
    }

    @Override
    public boolean isTerminated() {
        return ((HVMPOSIXDebugTarget) target).isTerminated();
    }

    @Override
    public void terminate() throws DebugException {
        ((HVMPOSIXDebugTarget) target).terminate();
    }

    @Override
    public IStackFrame[] getStackFrames() throws DebugException {
        synchronized (getDebugTarget()) {
            if (stackFrames == null) {
                if (isSuspended()) {
                    stackFrames = ((HVMPOSIXDebugTarget) target).getStackFrames();
                    return stackFrames;
                }
            } else {
                if (isSuspended()) {
                    return stackFrames;
                }
            }
        }
        return new IStackFrame[0];
    }

    @Override
    public boolean hasStackFrames() throws DebugException {
        return isSuspended();
    }

    @Override
    public int getPriority() throws DebugException {
        System.out.println("HVMThread: getPriority");
        return 0;
    }

    @Override
    public IStackFrame getTopStackFrame() throws DebugException {
        if ((stackFrames != null) && (stackFrames.length > 0)) {
            return stackFrames[0];
        }
        return null;
    }

    @Override
    public String getName() throws DebugException {
        return ((HVMPOSIXDebugTarget) target).getName();
    }

    @Override
    public IBreakpoint[] getBreakpoints() {
        IBreakpoint activeBreakPoint = ((HVMPOSIXDebugTarget) target).getActiveBreakPoint();

        if (activeBreakPoint != null) {
            return new IBreakpoint[] { activeBreakPoint };
        } else {
            return null;
        }
    }

    public void clearStackFrames() {
        if (stackFrames != null) {
            stackFrames = null;
        }
    }
}
