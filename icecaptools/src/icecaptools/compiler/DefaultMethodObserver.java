package icecaptools.compiler;

import icecaptools.CanceledByUserException;
import icecaptools.RestartableMethodObserver;

public class DefaultMethodObserver implements RestartableMethodObserver {

    @Override
    public void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature, boolean report) throws CanceledByUserException {
        ;
    }

    @Override
    public void restart() {
    }

    @Override
    public void refresh() {
    }

}
