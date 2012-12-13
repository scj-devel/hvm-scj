/**************************************************************************
 * File name  : CyclicExecutive.java
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

import vm.VM;

/**
 * A Level 0 SCJ application is comprised of one or more <code>CyclicExecutive</code> objects. <br>
 * Each <code>CyclicExecutive</code> is implemented as a subclass of this abstract class.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  
 */
@SCJAllowed
public abstract class CyclicExecutive extends Mission
{
  Clock rtClock;
  
  AbsoluteTime now;
  AbsoluteTime next;
  
  RelativeTime deltaTime;
  
  @SCJAllowed
  public CyclicExecutive ()
  {
	this.rtClock   = RealtimeClock.instance();
	
	this.now  = new AbsoluteTime (this.rtClock);
    this.next = new AbsoluteTime (this.rtClock);
    
    this.deltaTime = new RelativeTime (rtClock);
	
    devices.Console.println("CyclicExecutive.constructor");
  }

  /**
   * Returns the <code>CyclicSchedule</code> for this 
   * <code>CyclicExecutive</code> object. <br> 
   * This method is only invoked from the SCJ infrastructure. 
   * The infrastructure invokes <code>getSchedule</code> after control 
   * returns from <code>Mission.initialize</code>.
   * 
   * @param handlers for this cyclic schedule
   * @return A cyclic schedule for the handlers.
   */
  @SCJAllowed(Level.INFRASTRUCTURE)
  protected abstract CyclicSchedule getSchedule(PeriodicEventHandler[] handlers);
   
  /**
   * Implementation of a cyclic executive.
   * Is invoked in a mission memory scope.
   */
  void runExecute(MissionMemory mem)  
  // overrides the method in class Mission and is called in mission memory
  {
    devices.Console.println("  CyclicEx.runExec begin");
    
    final CyclicSchedule schedule = 
      getSchedule ((PeriodicEventHandler[]) mem.getHandlerPortal().eventHandlers.toArray());
    
    /**
     * local reference to frames
     */
    final CyclicSchedule.Frame[] frames = schedule.getFrames();
    
    class LocalRunnable implements Runnable  // Local class
    { 
      CyclicSchedule.Frame frame;
      int handlerNo;
      
      public void run()
      {
        frame.getHandlers()[handlerNo].handleAsyncEvent();
      }      
    };    
      
    LocalRunnable privateLogic = new LocalRunnable(); 
 
    /**
     * step is the minor cycle counter
     */
    int step = 0;
    
    rtClock.getTime (next);
        
    while (!missionTerminate)
    { 
      devices.Console.println("  CyclicEx.runExec begin while");
      
      CyclicSchedule.Frame frame = frames[step];
      PeriodicEventHandler[] handlers = frame.getHandlers();
      int n = handlers.length; 
      
      privateLogic.frame = frame;
      
      for (int handlerIdx = 0; handlerIdx < n; handlerIdx++)
      {  
        privateLogic.handlerNo = handlerIdx;
        handlers[handlerIdx].privateMemory.enter(privateLogic);
      }
      
      // wait for frame.duration to expire 
      waitForNextFrame (frame.getDuration());
      
      // index to next frame
      step = (step+1) % frames.length;
    }
    devices.Console.println("CyclicExec.runExec end: " + this);
  } 
  
  void runCleanup(MissionMemory mem)  
  //overrides the method in class Mission and is called in mission memory
  {
    devices.Console.println ("** CyclicExecutive.runCleanup");
    
    mem.getHandlerPortal().terminateHandlers();   
  }
  
  private void waitForNextFrame (RelativeTime duration)
  {
	next.add(duration, next);
	// ToDo:
    //devices.Console.println("CyclicExecutive.delayNativeUntil, next: " + next);
    //VMFct.delayNativeUntil (next); // wait until next (the absolute time); 
	//VMInterface.RealtimeClock.delayUntil(next);
	  
   
	// temporarily; later use absolute time; see above
	rtClock.getTime (now); 
    // deltaTime = next - now
    VM.delayNative(next.subtract(now, deltaTime)); 
  }
}

