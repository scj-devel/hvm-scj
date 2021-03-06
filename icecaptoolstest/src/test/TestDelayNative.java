package test;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.RelativeTime;

import vm.Machine;
import vm.POSIX64BitMachineFactory;
import vm.VMTest;

public class TestDelayNative {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	Machine.setMachineFactory(new POSIX64BitMachineFactory());
        
    	Clock rtClock = Clock.getRealtimeClock();
        AbsoluteTime next = rtClock.getTime();
        RelativeTime duration = new RelativeTime(500, 0, rtClock);  //   60*1000 ==> takes about 1� minute !
        
        for (int i = 0; i < 3; i++) {
            next.add(duration, next);
            vm.RealtimeClock.delayUntilTime(next);
            devices.Console.println("tick");
        }
        VMTest.markResult(false);
    }
}
