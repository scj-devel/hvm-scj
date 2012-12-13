/**************************************************************************
 * File name  : ScjProcessScheduler.java
 * 
 * This file is part of our SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.79. 16 May 2011.
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
 *    
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/
package javax.safetycritical;

import javax.safetycritical.MissionSequencer.State;

import vm.Process;
import vm.ProcessScheduler;

/**
 * This process scheduler class extends the HVM <code>ProcessScheduler</code> class.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment - implementation issue: infrastructure class; not part of the SCJ
 *             specification.
 */
class ScjProcessScheduler implements ProcessScheduler {
	
	private int scjLevel;
	
	/**
	 * Creates a process scheduler with a stack.
	 * 
	 * @param stack The stack of the process scheduler
	 * @param level The SCJ Level 0 or 1.
	 */
	ScjProcessScheduler(int scjLevel) {
		this.scjLevel = scjLevel;
	}

	/**
	 * This method is called by the HVM for process scheduling: current process is
	 * pre-empted and put on an appropriate queue and the first process on ready
	 * queue is returned.
	 */
	@Override
	public final Process getNextProcess() {
		ScjProcess scjProcess;
		
		if (scjLevel == 0)
		{
			scjProcess = CyclicScheduler.instance().getCurrentProcess();
			
			
			if (scjProcess.target instanceof MissionSequencer && 
			    ((MissionSequencer)(scjProcess.target)).currState == State.END)
			{
			  return null;
			}
			
			return scjProcess.process;
		}
		else
		{
			scjProcess = PriorityScheduler.instance().move();
			
			if (scjProcess != null) {
				return scjProcess.process;
			} else {
				return null;
			}
		}
	}
}
