package test;

import icecaptools.IcecapCompileMe;


public class TestSCJClock {

    private static boolean error;

    public TestSCJClock() {
    }

    public static void main(String[] args) {
        devices.Console.println("\n********** TestSCJClock main.begin ******************");

        TestSCJClock app = new TestSCJClock();
        app.run();

        devices.Console.println("\n********** TestSCJClock main.end ******************");
        if (!error)
        {
            args = null;
        }
    }
    
    @IcecapCompileMe
    public void run() {
        long millis = 0;
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(millis);
        String str = strBuilder.toString();
        if (str.equals("0"))
        {
            error = false;
        }
        else
        {
            error = true;
        }
    }
}
