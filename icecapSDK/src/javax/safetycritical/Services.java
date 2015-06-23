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
	public static void captureBackTrace(Throwable association) {
		// not implemented
	}

	/**
	 * 
	 * @return The current schedulable object.
	 */
	@SCJAllowed
	public static ManagedSchedulable currentManagedSchedulable() {
		// not tested
		return servicesBehavior.currentManagedSchedulable();
	}

	//	@SCJAllowed
	//	public static Level getDeploymentLevel()
	//	{
	//	  if (Launcher.level == 0)
	//	    return Level.LEVEL_0; 
	//	  if (Launcher.level == 1)
	//		return Level.LEVEL_1; 
	//	}

	@SCJAllowed(Level.LEVEL_1)
	public static int getDefaultCeiling() {
		return servicesBehavior.getDefaultCeiling();
	}

	@SCJAllowed(Level.LEVEL_1)
	public static void setCeiling(Object target, int ceiling) {
		servicesBehavior.setCeiling(target, ceiling);
	}
	
	public static AffinitySet[] getSchedulingAllocationDomains() {
		return AffinitySet.AFFINITY_SET;
	}

	@SCJAllowed(Level.LEVEL_0)
	public static void nanoSpin(int nanos) // Busy waiting
	{
		// not tested
		Clock clock = Clock.getRealtimeClock();
		AbsoluteTime time = new AbsoluteTime();
		AbsoluteTime next = new AbsoluteTime();

		clock.getTime(time);
		time.add(0, nanos, next);

		while (time.compareTo(next) < 0) // time < next
		{
			clock.getTime(time);
		}
	}
	
	
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
	

	// helping classes for multicore and singlecore version.
	static abstract class ServicesBehavior {

		abstract ManagedSchedulable currentManagedSchedulable();

		abstract int getDefaultCeiling();

		abstract void setCeiling(Object target, int ceiling);
		
		abstract String getNameOfCurrentMemoryArea(); 

	}

	static final class MulticoreBehavior extends ServicesBehavior {

		@Override
		ManagedSchedulable currentManagedSchedulable() {
			return Mission.missionBehaviour.getManageSched(OSProcess.getThreadID());
		}

		@Override
		int getDefaultCeiling() {
			return OSProcess.getMaxPriority();
		}

		@Override
		void setCeiling(Object target, int ceiling) {
			vm.Monitor monitor = MultiprocessorHelpingScheduler.getMultiprocessorMonitor(ceiling);
			monitor.attach(target);
		}

		@Override
		String getNameOfCurrentMemoryArea() {
			return OSProcess.getCurrentMemoryArea().getName();
		}

	}

	static final class SinglecoreBehavior extends ServicesBehavior {

		@Override
		ManagedSchedulable currentManagedSchedulable() {
			if (Launcher.level == 0)
				return CyclicScheduler.instance().seq;
			else
				return PriorityScheduler.instance().current.getTarget();
		}

		@Override
		int getDefaultCeiling() {
			return PriorityScheduler.instance().getMaxPriority();
		}

		@Override
		void setCeiling(Object target, int ceiling) {
			Monitor monitor = new Monitor(ceiling);
			monitor.attach(target);
			//devices.Console.println("Services.setCeiling");
		}

		@Override
		String getNameOfCurrentMemoryArea() {
			return vm.Memory.getCurrentMemoryArea().getName();
		}

	}
	
	
}
