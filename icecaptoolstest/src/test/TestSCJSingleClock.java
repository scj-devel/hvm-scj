package test;

import icecaptools.IcecapCompileMe;


public class TestSCJSingleClock {

    private static boolean error;

    public TestSCJSingleClock() {
    }

    public static void main(String[] args) {
        devices.Console.println("\n********** TestSCJClock main.begin ******************");

        TestSCJSingleClock app = new TestSCJSingleClock();
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
