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
			new Raw_MemoryMappedRegion_Default("MEMORY_MAPPED_REGION");
	
	public static final RawMemoryRegion IO_PORT_MAPPED_REGION = 
			new Raw_MemoryMappedRegion_Default("IO_PORT_MAPPED_REGION");
	
	public RawMemoryFactory() {
		
	}
	
	public static RawMemoryFactory getDefaultFactory() {
		return null;
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
		
		return null;
	}
	
	
	private static class Raw_MemoryMappedRegion_Default extends RawMemoryRegion { 
		
		Raw_MemoryMappedRegion_Default(String name) {
			super(name);
		}
		
	}
	
	private static class Raw_IOPortMappedRegion_Default extends RawMemoryRegion { 
		
		Raw_IOPortMappedRegion_Default(String name) {
			super(name);
		}
		
	}

}
