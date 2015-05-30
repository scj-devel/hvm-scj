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
public class RawMemoryRegion implements RawMemoryRegionFactory
{
  private String name;
	
  public RawMemoryRegion(String name) {
	   this.name = name;
  }
  
  public final String getName() {
    return name;
  }
  
  public static RawMemoryRegion getRegion(String name) {
	  if (name.equals(RawMemoryFactory.MEMORY_MAPPED_REGION.getName()))
		  return RawMemoryFactory.MEMORY_MAPPED_REGION;
	  else if (name.equals(RawMemoryFactory.IO_PORT_MAPPED_REGION.getName()))
		  return RawMemoryFactory.IO_PORT_MAPPED_REGION;
	  else 
		  return new RawMemoryRegion(name);
  }
  
  public static boolean isRawMemoryRegion(String name) {
    return false;
  }
  
  public final String toString() {
    return name;
  }
  
  public RawByte createRawByte(long base, int count, int stride) 
			throws java.lang.SecurityException,
			javax.realtime.OffsetOutOfBoundsException, 
			javax.realtime.SizeOutOfBoundsException,
			javax.realtime.UnsupportedPhysicalMemoryException, 
			javax.realtime.MemoryTypeConflictException
  {
	  return null;
  }
}
