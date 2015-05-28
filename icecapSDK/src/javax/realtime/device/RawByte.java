package javax.realtime.device;

import javax.safetycritical.annotate.SCJAllowed;

/**
 * An interface for an object that can be used to access to a single byte. 
 * Read and write access to that byte is checked by the factory that creates the instance;
 * therefore, no access checking is provided by this interface. 
 * 
 * @version 1.0; - July 2015
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@via.dk">hso@via.dk</A>
 */
@SCJAllowed
public interface RawByte extends RawByteReader, RawByteWriter
{

}
