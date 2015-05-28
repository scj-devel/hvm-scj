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
public interface RawShortReader extends RawMemory
{
  public int get(int offset, short [] values)
      throws javax.realtime.OffsetOutOfBoundsException,
      java.lang.NullPointerException;
  
  public int get(int offset, short [] values, int start, int count)
      throws javax.realtime.OffsetOutOfBoundsException,
      java.lang.ArrayIndexOutOfBoundsException,
      java.lang.NullPointerException;
  
  public short getShort(int offset)
      throws javax.realtime.OffsetOutOfBoundsException;
  
  public short getShort();
}
