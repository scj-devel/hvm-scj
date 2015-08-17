package javax.realtime.device;

import javax.realtime.OffsetOutOfBoundsException;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * An interface for a byte accessor object encapsulating the protocol for reading
 * bytes from raw memory. A byte accessor can always access at least one byte.
 * Each byte is transferred in a single atomic operation. Groups of bytes may be
 * transferred together; however, this is not required.
 * 
 * Objects of this type are created with the factory method createRawByteReader of
 * an object implementing an RawByteRegion. Each object references a range of elements
 * in the region starting at the base address provided to the factory method. 
 * The size provided to the factor method determines the number of elements accessible.
 * 
 *  Caching of the memory access is controlled by the factory that created this
 *  object. If the memory is not cached, this method guarantees serialized access.
 *  In other words, the memory access at the memory occurs in the same order as
 *  in the program. Multiple reads from the same location may not be coalesced.
 *  
 * @version 1.0; - July 2015
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@via.dk">hso@via.dk</A>
 */
@SCJAllowed
public interface RawByteReader extends RawMemory
{
  public int get(int offset, byte [] values)
      throws javax.realtime.OffsetOutOfBoundsException,
      java.lang.NullPointerException;
  
  public int get(int offset, byte [] values, int start, int count)
      throws javax.realtime.OffsetOutOfBoundsException,
      java.lang.ArrayIndexOutOfBoundsException,
      java.lang.NullPointerException;
  
  /**
   * Get the value of the element at index <code>offset</code> in this instance, the
   * address is base address + (offset * the stride).
   * When an exception is thrown, no data is transferred.
   * 
   * @param offset index in the memory region starting from the address specified 
   * in the associated factory method.
   * 
   * @return the value at the address specified.
   * 
   * @throws OffsetOutOfBoundsException when offset is negative or greater than or
   * equal to the number of elements in the raw memory region.
   */
  public byte getByte(int offset)
      throws javax.realtime.OffsetOutOfBoundsException;
  
  public byte getByte();
}
