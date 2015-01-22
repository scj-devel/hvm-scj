package test;

public class TestLock {

    Object lock;

    TestLock()
    {
        lock = this;
    }
    
    void hey() {
        synchronized (lock) {
            devices.Console.println("Hey");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new TestLock().hey();
        args = null;
    }
}
