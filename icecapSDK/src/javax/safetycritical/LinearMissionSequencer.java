/**************************************************************************
 * File name  : LinearMissionSequencer.java
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

import javax.realtime.ConfigurationParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.memory.ScopeParameters;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * A <code>LinearMissionSequencer</code> is a <code>MissionSequencer</code> 
 * that serves ... 
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
public class LinearMissionSequencer extends MissionSequencer {
	
	boolean repeat;
	Mission[] missions;
	int active = 0;

	@SuppressWarnings("unchecked")
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, ScopeParameters storage, 
			ConfigurationParameters config, 
			Mission mission,
			boolean repeat,
			String name)
			throws IllegalArgumentException, IllegalStateException {
		
		super(priority, storage, config, name);
		this.repeat = repeat;		
		this.missions = (Mission[])new Object[1];
		missions[0] = mission;
		
		// ToDo: exceptions: not finished; but the Draft is unclear, scj139.pdf from AP
	}
	
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, ScopeParameters storage, 
			ConfigurationParameters config, 
			Mission mission,
			boolean repeat)
			throws IllegalArgumentException, IllegalStateException {
		
		this(priority, storage, config, mission, repeat, "LinearMS");
	}
	
	@SuppressWarnings("unchecked")
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, ScopeParameters storage, 
			ConfigurationParameters config, 
			Mission[] missions,
			boolean repeat,
			String name)
			throws IllegalArgumentException, IllegalStateException {
		
		super(priority, storage, config, name);
		this.repeat = repeat;
		this.missions = (Mission[])new Object[missions.length];
		for (int i = 0; i < missions.length; i++)
			this.missions[i] = missions[i];
		
		// ToDo: exceptions: not finished; but the Draft is unclear, scj139.pdf from AP
	}
	
	@SCJAllowed
	public LinearMissionSequencer(PriorityParameters priority, ScopeParameters storage, 
			ConfigurationParameters config, 
			Mission[] missions,
			boolean repeat)
			throws IllegalArgumentException, IllegalStateException {
		
		this(priority, storage, config, missions, repeat, "LinearMSs");		
	}
	
	@SCJAllowed(Level.SUPPORT)
	protected final Mission getNextMission() { 
		if (repeat) {
			Mission miss = missions[active];
            active = (active + 1) % missions.length;
            return miss;
		}
		else
			return null;
	}
}



