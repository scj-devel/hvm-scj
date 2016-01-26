/**************************************************************************
 * File name  : Launcher.java
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
 *   
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/
package javax.safetycritical;

import javax.realtime.MemoryArea;
import javax.scj.util.Const;

import vm.MachineFactory;
import vm.Memory;
import vm.POSIX64BitMachineFactory;

/**
 * This class is used by an application class to launch a Level 0 or a Level 1
 * application.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, <A
 *         HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 *         Hans S&oslash;ndergaard, VIA University College, Denmark, <A
 *         HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 * 
 * @scjComment - The class is not part of the SCJ specification.
 */
public abstract class Launcher implements Runnable {
	Safelet<?> app;
	protected MachineFactory mFactory;
	static int level;
	static boolean useOS = false;

	Launcher(Safelet<?> app, int level) {
		this(app, level, false, new POSIX64BitMachineFactory());
	}

	Launcher(Safelet<?> app, int level, MachineFactory mFactory) {
		this(app, level, false, mFactory);
	}
	
	Launcher(Safelet<?> app, int level, boolean useOS) {
		this(app, level, useOS, new POSIX64BitMachineFactory());
	}
	
	Launcher(Safelet<?> app, int level, boolean useOS, MachineFactory mFactory) {
		if (level < 0 || level > 2 || app == null) {
			throw new IllegalArgumentException();
		}
		this.mFactory = mFactory;
		this.app = app;
		Launcher.level = level;
		Launcher.useOS = useOS;		
		init();
		createImmortalMemory();
	}
	
	public void run() {
		app.initializeApplication();
		start();
	}

	private void createImmortalMemory(){
		ManagedMemory.allocateBackingStore(Const.OVERALL_BACKING_STORE);

		if (Memory.memoryAreaTrackingEnabled) {
			new PrivateMemory(Const.MEMORY_TRACKER_AREA_SIZE, Const.MEMORY_TRACKER_AREA_SIZE,
					MemoryArea.overAllBackingStore, "MemTrk");
		}
		
		ManagedMemory immortalMem = new ImmortalMemory(Const.IMMORTAL_MEM);
		immortalMem.executeInArea(this);
		//immortalMem.removeArea();
	}
	
	protected abstract void init();
	
	protected abstract void start(); 
	
	
	
	
}
