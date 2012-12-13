/**************************************************************************
 * File name  : ScjProcess.java
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

import javax.scj.util.Const;
import javax.scj.util.Priorities;

import vm.Process;
import vm.VM;

/**
 * Defines the VM process context for an executing Java program.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment 
 *  - implementation issue: infrastructure class; not part of the SCJ specification.
 */
class ScjProcess implements Comparable<ScjProcess>
{
  Process process;
  
  ManagedEventHandler target;

  int state;
  
  Clock rtClock;
  AbsoluteTime next;  // next activation time
  
  RelativeTime start;
  RelativeTime period;
  
  interface State
  {    
    public final static byte NEW        = 0;
    public final static byte READY      = 1;
    public final static byte EXECUTING  = 2;
    public final static byte BLOCKED    = 3;
    public final static byte SLEEPING   = 4;
    public final static byte HANDLED    = 5;  
    public final static byte TERMINATED = 6;
  }
  
  protected ScjProcess()
  {
	  this.rtClock = Clock.getRealtimeClock();
	  this.next    = new AbsoluteTime (this.rtClock);
  }
  
  /**
   * The constructor initializes a new VM process object
   * @param target is the handler containing the handleAsyncEvent method to be executed
   * @param stack is the run time stack
   */
  ScjProcess(ManagedEventHandler handler, int[] stack)
  { 
	  this.rtClock = Clock.getRealtimeClock();
	  this.next    = new AbsoluteTime (this.rtClock);
	  this.target = handler;
	  this.state  = State.NEW;	
	  
	  this.process = VM.newProcess(
	      new Runnable() 
		      {
		        public void run()
		        { 
		           try {
			          //target.handleAsyncEvent();  // temporarily only
			          target.privateMemory.enter(new Runnable() 
			          {
			              public void run()
			              {  
			            	//devices.Console.println("process.run begin: " + target);			            	
			                target.handleAsyncEvent();
			              }
			          });
		           }
		           catch (Exception e) {
		        	   devices.Console.println("ScjProcess: exception -> " + e); 
		           }
		           finally {
		        	   if (target instanceof PeriodicEventHandler)
		        	   {
		        		 next.add(period, next); // next = next + period
		        	   } 
		        	   
		        	   state = State.HANDLED;
		        	   process.restart();  // HVM specific to let periodic handler continue
		           }
		        }
		      },
		   stack
		  );
	 
	  
	  rtClock.getTime(this.next);
	  
	  if (target instanceof PeriodicEventHandler)
	  {
	    this.start = ((PeriodicParameters)target.release).start;
	    this.period = ((PeriodicParameters)target.release).period;
	    next.add(start, next);  // next = next + start
	    //devices.Console.println("ScjProcess: start: " + next );
	  }
	  
	  this.target.process = this;
  }
 
 
 public String toString()
 {
   return ("ScjProcess:" + target);
 }
 
 /**
  * Compares this process with the parameter process. The ordering of the processes 
  * are done after growing priorities.
  */
 public int compareTo(ScjProcess process)
 {
   return (this.target.priority.getPriority() - process.target.priority.getPriority());
 }

 
 
 /**
  * Idle process is created and put in readyQueue, so that readyQueue will
  * never be empty.
  * Idle process has lowest priority. <br>
  * 
  * First implementation: 
  * Idle process is a periodic handler with "infinite" period.
  */
 
 static ScjProcess idleProcess;
 
 /**
  * Creates and returns the singleton idle process. If idle process is already
  * created, no new process is created.
  * @return Returns the singleton idle process.
  */
 public static ScjProcess createIdleProcess()
 {
   if (idleProcess == null)
   {  
	 // devices.Console.println("ScjProcess.idleProcess createIdleProcess");
	 
	 idleProcess = new ScjProcess (
       new PeriodicEventHandler (
           new PriorityParameters (Priorities.MIN_PRIORITY),
           new PeriodicParameters(
        		 new RelativeTime (Clock.getRealtimeClock()),// start (0,0)  
        		 Const.INFINITE_TIME),  // period 
           new StorageParameters (Const.PRIVATE_MEM_SIZE, null)  // size of private mem
       )
       {
         public void handleAsyncEvent()
         {
             yield();
         }
         
         private void yield()
         {
           // ToDo: what should be done here?
           int count = 0;
           while (true)
           {
        	   count++;
        	   if (count == 100000)
        	   {
        		   //devices.Console.println("--> idleProcess, count: " + count);
        		   count = 0;
        	   }
           }
         }
       },
       new int[Const.IDLE_PROCESS_STACK_SIZE]
     );
   }
   idleProcess.rtClock.getTime(idleProcess.next);
   //devices.Console.println("ScjProcess.idleProcess created: " + idleProcess);
   
   return idleProcess;
 }
}
