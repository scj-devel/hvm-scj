package icecaptools;

import java.io.PrintStream;

public abstract class ResourceManager {

    private String[] resourcesToLoad = { "allocation_point.c", "loader/serializer.c", "loader/classesLoader.c", "loader/classes.h", "loader/io_allOS.c", "loader/io.h", "loader/io_i86.c", "loader/io_necv850.c", "loader/methodsLoader.c", "loader/methods.h", "allocation_point.h",
        "cr16c_interrupt.s", "arm7_interrupt.s", "arm_interrupt.s", "gc.c", "print.c", "gc.h", "icecapvm.c", "methodinterpreter.c", "methodinterpreter.h", "natives_allOS.c", "natives_ev3.c", "natives_beaglebone.c", "natives_arduino.c", "natives_avr.c", "natives_cr16c.c", "natives_i86.c", "ostypes.h", "types.h", "rom_access.c", "rom_access.h", "rom_heap.c", "rom_heap.h",
        "native_scj.c", "x86_32_interrupt.s", "avr_interrupt.s", "x86_64_interrupt.s", "x86_32_cygwin_interrupt.s", "x86_64_cygwin_interrupt.s" };

    public abstract IcecapIterator<StreamResource> getResources(PrintStream out);
    
    public abstract StreamResource getResource(PrintStream out, String resourceName);
    
    protected void setResourcesToLoad(String[] resources) {
    	this.resourcesToLoad = resources;
    	
    }
    
    protected String[] getResourcesToLoad(){
    	return resourcesToLoad;
    }
}
