package test;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.RelativeTime;

public class TestDelayNative {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Clock rtClock = Clock.getRealtimeClock();
        AbsoluteTime next = rtClock.getTime();
        RelativeTime duration = new RelativeTime(500, 0, rtClock);
        
        for (int i = 0; i < 3; i++) {
            next.add(duration, next);
            vm.RealtimeClock.delayNativeUntil(next);
            devices.Console.println("tick");
        }
        args = null;
    }
}
