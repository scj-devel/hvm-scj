package icecaptools.debugging;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class EventDispatchJob extends Job {

    private static final int START_EVENT = 10;
    private static final int TERMINATED_EVENT = 12;
    private static final int BREAKPOINT_HIT_EVENT = 15;

    private InputStream eventInputStream;

    private HVMPOSIXDebugTarget target;

    public EventDispatchJob(InputStream inputStream, HVMPOSIXDebugTarget target) {
        super("HVM Debug Event Dispatch");
        setSystem(true);
        this.eventInputStream = inputStream;
        this.target = target;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        int tag;

        while (true) {
            try {
                tag = eventInputStream.read();
                switch (tag) {
                case START_EVENT: {
                    target.started();
                    break;
                }
                case TERMINATED_EVENT: {
                    target.terminated();
                    return Status.OK_STATUS;
                }
                case BREAKPOINT_HIT_EVENT: {
                    int methodNumber = eventInputStream.read();
                    methodNumber |= eventInputStream.read() << 8;

                    int pc = eventInputStream.read();
                    pc |= eventInputStream.read() << 8;
                    try {
                        target.breakPointHit(methodNumber, pc);
                    } catch (CoreException e) {
                    }
                    break;
                }
                }
            } catch (IOException e) {
                return Status.OK_STATUS;
            }
        }
    }
}
