package javax.realtime.device;

import javax.safetycritical.annotate.SCJAllowed;

/**
 * An interface for all memory accessor objects.
 * 
 * @version 1.0; - July 2015
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@via.dk">hso@via.dk</A>
 */
@SCJAllowed
public interface RawMemory
{
  public long getAddress();
  public int getSize();
  public int getStride();
}
