package icecaptools;

import org.eclipse.core.runtime.IProgressMonitor;

public class IcecapEclipseProgressMonitor implements IcecapProgressMonitor {

    private IProgressMonitor progressMonitor;

    public IcecapEclipseProgressMonitor(IProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }

    @Override
    public void worked(String item) {
        progressMonitor.subTask(item);
        progressMonitor.worked(1);
    }

    @Override
    public boolean isCanceled() {
        return progressMonitor.isCanceled();
    }
    
    public IProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

    @Override
    public void worked(int i) {
        progressMonitor.worked(i);
    }

    @Override
    public void subTask(String string) {
        progressMonitor.subTask(string);
    }
}
