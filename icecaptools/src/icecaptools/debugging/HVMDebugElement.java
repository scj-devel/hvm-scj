package icecaptools.debugging;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

public abstract class HVMDebugElement extends PlatformObject implements IDebugElement {

    public static final String ID_HVM_DEBUG_MODEL = "icecaptools.launching.hvm";

    protected IDebugTarget target;
    
    protected HVMDebugElement(IDebugTarget target)
    {
        this.target = target;
    }
    
    public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
        if (adapter == IDebugElement.class) {
            return this;
        }
        return super.getAdapter(adapter);
    }
    
    @Override
    public String getModelIdentifier() {
        return ID_HVM_DEBUG_MODEL;
    }

    @Override
    public IDebugTarget getDebugTarget() {
        return target;
    }

    @Override
    public ILaunch getLaunch() {
        return getDebugTarget().getLaunch();
    }
    
    public void fireResumeEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
    }

    protected void fireCreationEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.CREATE));
    }   
    
    protected void fireTerminateEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
    }   

    protected void fireSuspendEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
    }
    
    protected void fireEvent(DebugEvent event) {
        DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] {event});
    }
    
}
