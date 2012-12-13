/**************************************************************************
 * File name  : AsyncEventHandler.java
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

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * In SCJ, all asynchronous events must have their handlers bound during
 * the initialization phase. <br>
 * The binding is permanent. Thus, the <code>AsyncEventHandler</code>
 * constructors are hidden from public view.
 * 
 *  @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  - SCJ issue: <code>handleAsyncEvent</code> should be abstract. <br>
 *  - SCJ issue: The class is superfluous, all handlers are <code>ManagedEventHandler</code>s. <br>
 *  - SCJ issue: Constructors are superfluous <br>
 */
public abstract class AsyncEventHandler implements Schedulable
{
  AsyncEventHandler() { }
  
  /**
   * This method is overridden by the application to provide the handling
   * code
   */
  @SCJAllowed(Level.LEVEL_1)  
  public abstract void handleAsyncEvent ();
}

