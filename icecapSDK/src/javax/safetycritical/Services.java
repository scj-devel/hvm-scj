/**************************************************************************
 * File name: Services.java
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
package javax.safetycritical;

import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * This class provides a collection of static helper methods.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
@SCJAllowed
public class Services {

	static ServicesBehavior servicesBehavior = null;
	
	@SCJAllowed
	public static javax.safetycritical.annotate.Level getComplianceLevel() {
		// ToDo
		return null;
	}
	
	@SCJAllowed(Level.LEVEL_1)
	public static int getDefaultCeiling() {
		return servicesBehavior.getDefaultCeiling();
	}

	@SCJAllowed(Level.LEVEL_1)
	public static void setCeiling(Object target, int ceiling) {
		servicesBehavior.setCeiling(target, ceiling);
	}
	
	
//	@SCJAllowed
//	public static void captureBackTrace(Throwable association) {
//		// not implemented
//	}

	//	@SCJAllowed
	//	public static Level getDeploymentLevel()
	//	{
	//	  if (Launcher.level == 0)
	//	    return Level.LEVEL_0; 
	//	  if (Launcher.level == 1)
	//		return Level.LEVEL_1; 
	//	}	
	
	public static AffinitySet[] getSchedulingAllocationDomains() {
		return AffinitySet.AFFINITY_SET;
	}

//	@SCJAllowed(Level.LEVEL_0)
//	public static void nanoSpin(int nanos) // Busy waiting
//	{
//		// not tested
//		Clock clock = Clock.getRealtimeClock();
//		AbsoluteTime time = new AbsoluteTime();
//		AbsoluteTime next = new AbsoluteTime();
//
//		clock.getTime(time);
//		time.add(0, nanos, next);
//
//		while (time.compareTo(next) < 0) // time < next
//		{
//			clock.getTime(time);
//		}
//	}
	
	
	// for testing only
	public static int getCurrentCPUID(){
		return OSProcess.getCurrentCPUID();
	}
	
	public static int getAvailableCPUCount(){
		return OSProcess.getAvailableCPUCount();
	}
	
	public static String getNameOfCurrentMemoryArea() {
		return servicesBehavior.getNameOfCurrentMemoryArea();
	}
	
}


