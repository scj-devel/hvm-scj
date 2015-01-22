package icecaptools;

public interface RestartableMethodObserver extends MethodObserver {

    public void restart();

    public void refresh();
}
