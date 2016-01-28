package test;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.RelativeTime;

import vm.Machine;
import vm.POSIX64BitMachineFactory;

public class TestDelayNative {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	Machine.setMachineFactory(new POSIX64BitMachineFactory());
        
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
