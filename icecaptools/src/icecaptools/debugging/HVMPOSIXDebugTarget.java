package icecaptools.debugging;

import icecaptools.BNode;
import icecaptools.ClassfileUtils;
import icecaptools.CompilationSequence;
import icecaptools.ConverterJob;
import icecaptools.MethodAndClass;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodLocation;
import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.ByteCodePatcher;
import icecaptools.launching.CommonLauncherTab;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public class HVMPOSIXDebugTarget extends HVMDebugElement implements IDebugTarget {

    private static final int RESUME_EVENT = 11;
    private static final int TERMINATE_EVENT = 13;
    private static final int BREAKPOINT_ADD_EVENT = 14;
    private static final int BREAKPOINT_REMOVE_EVENT = 16;
    private static final int GET_STACKFRAMES_EVENT = 17;
    private static final int STACKFRAMES_START_EVENT = 18;
    @SuppressWarnings("unused")
    private static final int STACKFRAMES_END_EVENT = 19;
    private static final int GET_STACKVALUE_EVENT = 20;
    private static final int STEPOVER = 3;
    private static final int STEPINTO = 1;
    private static final int STEP_EVENT = 21;
    private static final int STEPCONTINUE = 4;

    private ILaunch launch;
    private IProcess targetProcess;
    private HVMThread[] threads;

    private EventDispatchJob eventDispatcher;

    private ILineBreakpoint activeBreakPoint;

    private HashMap<MethodLocation, ILineBreakpoint> breakPoints;

    private MethodLocation suspendedAt;
    private MethodEntryPoints suspendedAtEP;
    private MethodAndClass suspendedAtMethod;

    private DebugChannel tc;

    public HVMPOSIXDebugTarget(ILaunch launch, IProcess p, DebugChannel tc, IProgressMonitor monitor) throws Exception {
        super(null);
        this.launch = launch;
        this.targetProcess = p;
        target = this;
        threads = new HVMThread[1];
        threads[0] = new HVMThread(this);

        try {
            tc.connectToTarget(monitor);
        } catch (UnknownHostException e) {
            throw new Exception("Unable to connect to debug target");
        } catch (IOException e) {
            throw new Exception("Unable to connect to debug target");
        }

        clearSuspendedAt();

        breakPoints = new HashMap<MethodLocation, ILineBreakpoint>();
        eventDispatcher = new EventDispatchJob(tc.getEventChannel(), this);
        eventDispatcher.schedule();

        DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
        this.tc = tc;
    }

    private void clearSuspendedAt() {
        suspendedAt = null;
        suspendedAtEP = null;
        suspendedAtMethod = null;
    }

    private void sendRequest(byte event) throws IOException {
        OutputStream outputStream;
        outputStream = tc.getRequestOutputStream();
        outputStream.write(event);
        outputStream.flush();
    }

    private void sendRequest(short data) throws IOException {
        OutputStream outputStream;
        outputStream = tc.getRequestOutputStream();
        outputStream.write(data & 0xff);
        outputStream.write((data >> 8) & 0xff);
        outputStream.flush();
    }

    @Override
    public IDebugTarget getDebugTarget() {
        return this;
    }

    @Override
    public ILaunch getLaunch() {
        return launch;
    }

    public void started() {
        fireCreationEvent();
        installDeferredBreakpoints();
        try {
            resume();
        } catch (DebugException e) {
        }
    }

    @Override
    public boolean canTerminate() {
        return getProcess().canTerminate();
    }

    @Override
    public boolean isTerminated() {
        return getProcess().isTerminated();
    }

    @Override
    public void terminate() throws DebugException {
        try {
            sendRequest((byte) TERMINATE_EVENT);
            terminated();
        } catch (IOException e) {
            throw new DebugException(new IStatus() {

                private static final String message = "Sending TERMINATE request failed";

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
    }

    @Override
    public boolean canResume() {
        return !isTerminated() && isSuspended();
    }

    @Override
    public boolean canSuspend() {
        return !isTerminated() && !isSuspended();
    }

    @Override
    public boolean isSuspended() {
        return suspendedAt != null;
    }

    public void terminated() throws IOException {
        clearSuspendedAt();
        DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
        fireTerminateEvent();
        tc.disconnectFromTarget();
    }

    @Override
    public void resume() throws DebugException {
        try {
            if (isStepping()) {
                synchronized (this) {
                    sendRequest((byte) STEP_EVENT);
                    sendRequest((byte) STEPCONTINUE);
                }
            } else {
                sendRequest((byte) RESUME_EVENT);
            }
            resumed(DebugEvent.CLIENT_REQUEST);
            clearSuspendedAt();
        } catch (IOException e) {
            throw new DebugException(new IStatus() {

                private static final String message = "Sending RESUME request failed";

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
    }

    private void resumed(int detail) {
        fireResumeEvent(detail);
        threads[0].clearStackFrames();
    }

    @Override
    public void suspend() throws DebugException {
        System.out.println("suspend");
    }

    public String getClassNameFromBreakpoint(IBreakpoint breakpoint) throws CoreException {
        CompilationSequence compilationSequence = ConverterJob.mostRecentSequence;
        if (compilationSequence != null) {
            if (breakpoint instanceof ILineBreakpoint) {
                ILineBreakpoint lineBreakpoint = (ILineBreakpoint) breakpoint;
                IMarker marker = lineBreakpoint.getMarker();
                @SuppressWarnings("rawtypes")
                Map attributes = marker.getAttributes();
                Object typeName = attributes.get("org.eclipse.jdt.debug.core.typeName");
                if ((typeName != null) && (typeName instanceof String)) {
                    String tName = (String) typeName;
                    return tName;
                }
            }
        }
        return null;
    }

    @Override
    public void breakpointAdded(IBreakpoint breakpoint) {
        String className;
        try {
            className = getClassNameFromBreakpoint(breakpoint);
            if (className != null) {
                int lineNumber = ((ILineBreakpoint) breakpoint).getLineNumber();
                MethodLocation ml = ClassfileUtils.getMethodLocation(className, lineNumber, ConverterJob.mostRecentSequence);
                if (ml != null) {
                    breakPoints.put(ml, (ILineBreakpoint) breakpoint);
                    setBreakpointOnTarget(ml);
                }
            }
        } catch (CoreException e1) {
        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
        }
    }

    void setBreakpointOnTarget(MethodLocation ml) throws IOException {
        synchronized (getDebugTarget()) {
            sendRequest((byte) BREAKPOINT_ADD_EVENT);
            sendRequest(ml.getMethodNumber());
            sendRequest(ml.getAddress());
        }
    }

    private void installDeferredBreakpoints() {
        IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints("org.eclipse.jdt.debug");
        for (int i = 0; i < breakpoints.length; i++) {
            breakpointAdded(breakpoints[i]);
        }
    }

    @Override
    public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
        Iterator<Entry<MethodLocation, ILineBreakpoint>> entries = this.breakPoints.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<MethodLocation, ILineBreakpoint> entry = entries.next();
            if (entry.getValue() == breakpoint) {
                MethodLocation ml = entry.getKey();
                try {
                    synchronized (getDebugTarget()) {
                        sendRequest((byte) BREAKPOINT_REMOVE_EVENT);
                        sendRequest(ml.getMethodNumber());
                        sendRequest(ml.getAddress());
                    }
                    breakPoints.remove(ml);
                    return;
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
        System.out.println("breakpointChanged");
    }

    @Override
    public boolean canDisconnect() {
        return false;
    }

    @Override
    public void disconnect() throws DebugException {
    }

    @Override
    public boolean isDisconnected() {
        return false;
    }

    @Override
    public boolean supportsStorageRetrieval() {
        System.out.println("supportsStorageRetrieval");
        return false;
    }

    @Override
    public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
        System.out.println("getMemoryBlock");
        return null;
    }

    @Override
    public IProcess getProcess() {
        return targetProcess;
    }

    @Override
    public IThread[] getThreads() throws DebugException {
        return threads;
    }

    @Override
    public boolean hasThreads() throws DebugException {
        return true;
    }

    @Override
    public String getName() throws DebugException {
        try {
            String fName = getLaunch().getLaunchConfiguration().getAttribute(CommonLauncherTab.SOURCE_FOLDER, "");
            return fName;
        } catch (CoreException e) {
            throw new DebugException(new IStatus() {

                private static final String message = "Could not get name of launch configuration";

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
    }

    @Override
    public boolean supportsBreakpoint(IBreakpoint breakpoint) {
        return true;
    }

    /**
     * Notification the target has suspended for the given reason
     * 
     * @param detail
     *            reason for the suspend
     */
    private void suspended(int detail, MethodLocation ml) {
        this.suspendedAt = ml;
        fireSuspendEvent(detail);
    }

    public void breakPointHit(int methodNumber, int pc) throws CoreException, IOException {
        if (suspendedAt != null) {
            if (methodNumber == suspendedAt.getMethodNumber()) {
                BNode bnode = suspendedAtEP.getBNodeFromHVMAddress(pc);
                int originalPC = bnode.getOriginalAddress();
                int lineNumber = ClassfileUtils.getLineNumber(suspendedAtMethod.getMethod(), originalPC);
                if (lineNumber != suspendedAt.getLineNumber()) {
                    suspendedAt = new MethodLocation(methodNumber, pc, lineNumber);
                    suspended(DebugEvent.STEP_OVER, suspendedAt);
                } else {
                    sendRequest((byte) RESUME_EVENT);
                }
            } else {
                resume();
            }
        } else {
            MethodLocation ml = new MethodLocation(methodNumber, pc, -1);
            ILineBreakpoint breakPoint = breakPoints.get(ml);
            if (breakPoint != null) {
                activeBreakPoint = breakPoint;
                ml.setLineNumber(activeBreakPoint.getLineNumber());
                suspended(DebugEvent.BREAKPOINT, ml);
            } else {
                suspended(DebugEvent.STEP_INTO, ml);
            }
        }
    }

    public IBreakpoint getActiveBreakPoint() {
        return activeBreakPoint;
    }

    public IStackFrame[] getStackFrames() throws DebugException {
        LinkedList<HVMStackFrame> frames = new LinkedList<HVMStackFrame>();
        try {
            sendRequest((byte) GET_STACKFRAMES_EVENT);
            InputStream requestInputStream = tc.getRequestInputStream();
            int tag = requestInputStream.read();
            while (tag == STACKFRAMES_START_EVENT) {
                int methodNumber = requestInputStream.read();
                methodNumber |= requestInputStream.read() << 8;

                int pc = requestInputStream.read();
                pc |= requestInputStream.read() << 8;

                HVMStackFrame frame = new HVMStackFrame(threads[0], methodNumber, pc);
                frames.add(frame);
                tag = requestInputStream.read();
            }
        } catch (IOException e) {
            throw new DebugException(new IStatus() {

                private static final String message = "Could not get stack frames";

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
        IStackFrame[] framesArray = new IStackFrame[frames.size()];
        for (int i = 0; i < frames.size(); i++) {
            framesArray[i] = frames.get(i);
        }
        return framesArray;
    }

    public void getValueFromStack(short index, byte[] val) throws IOException {
        sendRequest((byte) GET_STACKVALUE_EVENT);
        sendRequest((byte) (index >> 8));
        sendRequest((byte) (index & 0xff));
        sendRequest((byte) val.length);

        InputStream requestInputStream = tc.getRequestInputStream();
        for (int i = 0; i < val.length; i++) {
            val[i] = (byte) requestInputStream.read();
        }
    }

    public void stepOver() throws DebugException {
        stepIntoOrOver((byte) STEPOVER);
    }

    private void stepIntoOrOver(byte StepType) throws DebugException {
        if (isSuspended()) {
            CompilationSequence compilationSequence = ConverterJob.mostRecentSequence;
            if (compilationSequence != null) {
                ByteCodePatcher patcher = compilationSequence.getPatcher();
                MethodOrFieldDesc m = patcher.getMethodDescriptor(suspendedAt.getMethodNumber());
                try {
                    suspendedAtMethod = ClassfileUtils.findMethod(m.getClassName(), m.getName(), m.getSignature());
                } catch (Exception e1) {
                }

                if (suspendedAtMethod != null) {
                    suspendedAtEP = compilationSequence.getDependencyExtent().getMethod(m.getClassName(), m.getName(), m.getSignature());
                    if (suspendedAtEP != null) {
                        try {
                            threads[0].clearStackFrames();
                            synchronized (this) {
                                sendRequest((byte) STEP_EVENT);
                                sendRequest((byte) StepType);
                                sendRequest(suspendedAt.getMethodNumber());
                                sendRequest(suspendedAt.getAddress());
                                return;
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            }
            resume();
        }
    }

    public boolean isStepping() {
        return suspendedAtEP != null;
    }

    public void stepInto() throws DebugException {
        stepIntoOrOver((byte) STEPINTO);
        clearSuspendedAt();
    }
}
