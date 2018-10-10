/************************************************************************
 * File name  : Adapter.java
 * 
 * This file is part a SCJ Technology Compatibility Kit. 
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * It is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this TCK. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2016 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *************************************************************************/
package carcontrol.infrastructure;


import javax.io.PrintStream;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.Safelet;

import java.lang.Thread.UncaughtExceptionHandler;

import carcontrol.test.Test;
//import java.io.PrintStream;
import devices.System.DevicePrintStream;


/**
 * <code>Adapter</code> is the interface for linking test execution to a concrete platform.
 * 
 * 
 * @version 1.2; - February 2017.
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A> 
 */

public abstract class Adapter {
	
	
	private static Adapter adapter = null;
	
	/**
	 * A singleton method to get the concrete platform.
	 * 
	 * @return the concrete platform 
	 */
	public static Adapter instance() {
		
		if (adapter == null)
			createAdapter (VMConfiguration.platform);
		
		return adapter;
	}
	
	private static void createAdapter (AdapterType adapterType)  {
		
		if (adapterType == AdapterType.CAR_VM)
			adapter = new CarVM();	
		else 
			adapter = new HVM();
		
	}
	
	

	/**
	 * The <code>execute</code> method implements the logic
	 * that runs an instance of the test application on an SCJ infrastructure.
	 * 
	 * @param app is the test application class.
	 */
	public abstract void execute(Class app);
	
	/**
	 * The <code>setUncaughtExceptionHandler</code> method sets an exception handler
	 * for test applications. 
	 * <br>
	 * Note that the <code>Thread</code> part of a call to the handler is ignored by the test
	 * applications.
	 * 
	 * @param h the <code>UncaughtExceptionHandler</code> to be set.
	 */
	public abstract void setUncaughtExceptionHandler(UncaughtExceptionHandler h);
	
	/**
	 * The method to launch a test application at a given level.
	 * 
	 * @param level the SCJ level for running the test application.
	 * 
	 * @param app the test application.
	 * 
	 * @returns the safelet application of the instantiated application or null if it cannot be instantiated.
	 */
	public abstract Test launch (Level level, Class<? extends Safelet> app); 
	
	
	/**
	 * The <code>markResult</code> method is called at the end of a test application 
	 * to tell the VM the result of the test.
	 * If <code>errorCount > 0</code>, the test has failed.
	 * 
	 * @param errorCount is the number of errors of the test application.
	 */
	public abstract void markResult(int errorCount);
	
	/**
	 * This <code>PrintStream out</code> must be modified when the TCK is ported.
	 */
	public static PrintStream sysout = new PrintStream(new DevicePrintStream());
	
	
	//public static PrintStream out = new DevicePrintStream();
	
}

