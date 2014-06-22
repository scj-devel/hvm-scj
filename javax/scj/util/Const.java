/**************************************************************************
 * File name  : Const.java
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

package javax.scj.util;

import javax.realtime.Clock;
import javax.realtime.RelativeTime;

/**
 * Utility class with constants.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */

public final class Const
{
  private Const() { }
	
  public static final int SECS_TO_MILLIS = 1000;
  public static final int MICRO_TO_NANOS = 1000;
  public static final int MILLI_TO_NANOS = 1000*MICRO_TO_NANOS;
  public static final int SECS_TO_NANOS  = 1000*MILLI_TO_NANOS;  
  
  public static final int NAN0S_PER_MICRO = 1000;
  public static final int NANOS_PER_MILLI = 1000*NAN0S_PER_MICRO;
  
  /* Memories */
  
  public static final int BACKING_STORE_SIZE_DEFAULT  = 800*1000; 
  public static final int IMMORTAL_MEM_SIZE_DEFAULT   = 120*1000; 
  public static final int MISSION_MEM_SIZE_DEFAULT    = 200*1000; 
  public static final int PRIVATE_MEM_SIZE_DEFAULT    =  40*1000;
  
  public static int BACKING_STORE_SIZE = BACKING_STORE_SIZE_DEFAULT; 
  public static int IMMORTAL_MEM_SIZE  = IMMORTAL_MEM_SIZE_DEFAULT; 
  public static int MISSION_MEM_SIZE   = MISSION_MEM_SIZE_DEFAULT; 
  public static int PRIVATE_MEM_SIZE   = PRIVATE_MEM_SIZE_DEFAULT;
   
  /* Stacks */
  
  public static final int STACK_UNIT  =  1024;   // 256
  
  public static final int PRIORITY_SCHEDULER_STACK_SIZE_DEFAULT = 1*STACK_UNIT;  // 2*1024 
  public static final int IDLE_PROCESS_STACK_SIZE_DEFAULT       = 1*STACK_UNIT;  //1*256;
  public static final int HANDLER_STACK_SIZE_DEFAULT            = 2*STACK_UNIT;  // 2*1024
    
  public static int PRIORITY_SCHEDULER_STACK_SIZE =  PRIORITY_SCHEDULER_STACK_SIZE_DEFAULT;  // 2*1024
  public static int IDLE_PROCESS_STACK_SIZE       =  IDLE_PROCESS_STACK_SIZE_DEFAULT;
  public static int HANDLER_STACK_SIZE            =  HANDLER_STACK_SIZE_DEFAULT;  // 2*1024
  
  /* Queues */
  
  public static final int DEFAULT_QUEUE_SIZE_DEFAULT      =  20; 
  public static final int DEFAULT_HANDLER_NUMBER_DEFAULT  =  20;  
  
  public static final int DEFAULT_PRIORITY_QUEUE_SIZE_DEFAULT  =  20; 
  
  public static int DEFAULT_QUEUE_SIZE  =  DEFAULT_QUEUE_SIZE_DEFAULT;  
  public static int DEFAULT_HANDLER_NUMBER = DEFAULT_HANDLER_NUMBER_DEFAULT;
  
  public static int DEFAULT_PRIORITY_QUEUE_SIZE = DEFAULT_PRIORITY_QUEUE_SIZE_DEFAULT;
  public static int DEFAULT_SLEEPING_QUEUE_SIZE = DEFAULT_PRIORITY_QUEUE_SIZE_DEFAULT;
  
  public static final RelativeTime DEFAULT_TIME_INTERVAL =
	      new RelativeTime (1, 0, null); // one millis
	  
  public static final RelativeTime INFINITE_TIME =
      new RelativeTime (365*24*60*1000, 0, Clock.getRealtimeClock()); // 365*24*60 secs = 1 year
  
  public static final RelativeTime SUSPEND_TIME =
      new RelativeTime (0, 100*1000, null); // 100 micro_secs
	  

  public static final boolean TESTING = true;
  
  public static final int OUTSIDE_MEM_AREAS  = -1;
  public static final int IMMORTAL_MEM_LEVEL = 0;
  public static final int SEQUENCER_PRIVATE_MEM_LEVEL = 1;
  public static final int MISSION_MEM_LEVEL  = 2;
  public static final int PRIVATE_MEM_LEVEL  = 4;
  
  public static final int PRIORITY_SCHEDULING = 1;
  
}
