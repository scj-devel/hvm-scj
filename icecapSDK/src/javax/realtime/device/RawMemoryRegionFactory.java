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
public interface RawMemoryRegionFactory {
	
	public RawByte createRawByte(long base, int count, int stride) 
			throws java.lang.SecurityException,
			javax.realtime.OffsetOutOfBoundsException, 
			javax.realtime.SizeOutOfBoundsException,
			javax.realtime.UnsupportedPhysicalMemoryException, 
			javax.realtime.MemoryTypeConflictException;

//	public RawByteReader createRawByteReader(long base, int count, int stride) 
//			throws java.lang.SecurityException,
//			javax.realtime.OffsetOutOfBoundsException, 
//			javax.realtime.SizeOutOfBoundsException,
//			javax.realtime.UnsupportedPhysicalMemoryException, 
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawByteWriter createRawByteWriter(long base, int count, int stride) 
//			throws java.lang.SecurityException,
//			javax.realtime.OffsetOutOfBoundsException, 
//			javax.realtime.SizeOutOfBoundsException,
//			javax.realtime.UnsupportedPhysicalMemoryException, 
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawInt createRawInt(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawIntReader createRawIntReader(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawIntWriter createRawIntWriter(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawLong createRawLong(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawLongReader createRawLongReader(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawLongWriter createRawLongWriter(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawShort createRawShort(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawShortReader createRawShortReader(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawShortWriter createRawShortWriter(long base, int count, int stride)
//			throws java.lang.SecurityException, 
//			javax.realtime.OffsetOutOfBoundsException,
//			javax.realtime.SizeOutOfBoundsException, 
//			javax.realtime.UnsupportedPhysicalMemoryException,
//			javax.realtime.MemoryTypeConflictException;
//
//	public RawMemoryRegion getRegion();
}
