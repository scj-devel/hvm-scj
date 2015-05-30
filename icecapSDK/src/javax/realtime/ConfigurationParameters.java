/**************************************************************************
 * File name  : ConfigurationParameters.java
 * 
 * This file is part a SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.94 25 June 2013.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This SCJ Level 0 and Level 1 implementation is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the  
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this SCJ Level 0 and Level 1 implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *************************************************************************/
package javax.realtime;

import javax.safetycritical.annotate.SCJAllowed;

/**
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 */
public class ConfigurationParameters {
	
	MemoryArea area;
	int messageLength;
	int stackTraceLength;
	long[] sizes;
	
	@SCJAllowed
	public ConfigurationParameters(MemoryArea area,
		int messageLength,
		int stackTraceLength,
		long[] sizes)
	{
		if (messageLength < -1)
			throw new IllegalArgumentException("messageLength not legal");
		if (stackTraceLength < -1)
			throw new IllegalArgumentException("maxIstackTraceLengthmmortal not legal");
		this.area = area;
		this.messageLength = messageLength;
		this.stackTraceLength = stackTraceLength;
		this.sizes = sizes;
	}
	
	public long[] getSizes()  {  // HSO: added
		return sizes;
	}
	
	// used for JML annotation only (not public)
	int getMessageLength() {
		return messageLength;
	}
	
	// used for JML annotation only (not public)
	int getStackTraceLength() {
		return stackTraceLength;
	}

}


