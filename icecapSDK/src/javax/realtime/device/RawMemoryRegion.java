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
public class RawMemoryRegion
{
  private String name;
		
//  public static final String MEMORY_MAPPED_REGION  = "MEMORY_MAPPED_REGION";
//  public static final String IO_PORT_MAPPED_REGION = "IO_PORT_MAPPED_REGION";
	
  public RawMemoryRegion(String name) {
	  this.name = name;
  }
  
  public final String getName() {
    return null;
  }
  
  public static RawMemoryRegion getRegion(String name) {
    return null;
  }
  
  public static boolean isRawMemoryRegion(String name) {
    return false;
  }
  
  public final String toString() {
    return null;
  }
}
