package javax.realtime.device;

import javax.safetycritical.annotate.SCJAllowed;

/**
 * 
 * @version 1.0; - July 2015
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@via.dk">hso@via.dk</A>
 */
@SCJAllowed
public class RawMemoryFactory {
	
	public static final RawMemoryRegion MEMORY_MAPPED_REGION  = 
			new RawMemoryMappedRegion_Default("MEMORY_MAPPED_REGION");
	
	public static final RawMemoryRegion IO_PORT_MAPPED_REGION = 
			new RawIOPortMappedRegion_Default("IO_PORT_MAPPED_REGION");
	
	static final RawMemoryFactory defaultFactory = new RawMemoryFactory_Default();
	
	
	public RawMemoryFactory() {
		
	}
	
	public static RawMemoryFactory getDefaultFactory() {
		return defaultFactory;
	}
	
	public void register(RawMemoryRegionFactory factory) 
		throws RegistrationException
	{
		
	}
	
	public void deregister(RawMemoryRegionFactory factory) 
			throws DeregistrationException
	{
			
	}
	
	public static RawByte createRawByte(RawMemoryRegion region, long base, int count, int stride) 
			throws java.lang.SecurityException,
			javax.realtime.OffsetOutOfBoundsException, 
			javax.realtime.SizeOutOfBoundsException,
			javax.realtime.UnsupportedPhysicalMemoryException, 
			javax.realtime.MemoryTypeConflictException
	{
		RawByte rawBytes = null;
		
		try {
			rawBytes = region.createRawByte(base, count, stride);
		}
		catch (Exception e) {
			
		}
		
		return rawBytes;
	}
	
	
	private static class RawMemoryMappedRegion_Default extends RawMemoryRegion { 
		
		RawMemoryMappedRegion_Default(String name) {
			super(name);
		}
		
		public RawByte createRawByte(long base, int count, int stride) 
				throws java.lang.SecurityException,
				javax.realtime.OffsetOutOfBoundsException, 
				javax.realtime.SizeOutOfBoundsException,
				javax.realtime.UnsupportedPhysicalMemoryException, 
				javax.realtime.MemoryTypeConflictException
	    {
		  return new RawByteMM(base, count, stride);
	    }
		
	}
	
	private static class RawIOPortMappedRegion_Default extends RawMemoryRegion { 
		
		RawIOPortMappedRegion_Default(String name) {
			super(name);
		}
		
		public RawByte createRawByte(long base, int count, int stride) 
				throws java.lang.SecurityException,
				javax.realtime.OffsetOutOfBoundsException, 
				javax.realtime.SizeOutOfBoundsException,
				javax.realtime.UnsupportedPhysicalMemoryException, 
				javax.realtime.MemoryTypeConflictException
	    {
		  return new RawByteATMega(base, count, stride);
		  
		  // or this class:
		  // return new RawByteATMegaNative(base, count, stride); 
		  
		  // later: a simple I/O RawByte class; perhaps for a serial port
	    }
		
	}
	
	private static class RawMemoryFactory_Default extends RawMemoryFactory {
		
		RawMemoryFactory_Default() {			
			super();
		}
	}

}
