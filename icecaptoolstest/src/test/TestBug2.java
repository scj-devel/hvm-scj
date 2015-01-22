package test;

public class TestBug2 {

    private int x;
    
    public TestBug2()
    {
        x = 43;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        TestBug2 bug2 = new TestBug2();
        bug2.threadMethod.run();
        if (bug2.x == 42)
        {
            args = null;
        }
    }

    private Runnable threadMethod = new Runnable() {
        public void run()
        {
                waitUntilNextFire();
        }
    };

    void waitUntilNextFire()
    {
        x = 42;
    }
    
}
