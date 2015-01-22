package icecaptools;


public interface MethodObserver {

    void methodCodeUsed(String className, String targetMethodName, String targetMethodSignature, boolean report) throws CanceledByUserException;

}
