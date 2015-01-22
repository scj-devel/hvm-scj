package icecaptools;

public interface IcecapProgressMonitor {
    public void worked(String string);
    
    public boolean isCanceled();

    public void worked(int i);

    public void subTask(String string);
}
