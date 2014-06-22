/**************************************************************************
 * File name  : ManagedMemory.java
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

import javax.realtime.MemoryArea;
import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

import vm.Memory;

/**
 * Managed memory is a scoped memory area that is managed by a mission.
 * 
 * @version 1.2; - December 2013
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@via.dk</A>
 */
@SCJAllowed
public abstract class ManagedMemory extends MemoryArea 
{
  private static ManagedMemory currentActiveArea = null;  
  
  /**
   * statically allocated Exception prevents memory area reference mismatches.
   */
  private static final IllegalArgumentException exception = 
      new IllegalArgumentException();

  // APR currentMemory is dynamically located through the currently running handler 
  private static final ManagedMemory getCurrentMemory() 
  {
    //return CurrentRunning.handler.privateMemory;
    return currentActiveArea;
  }

  private static final void setCurrentMemory(ManagedMemory m) 
  {
    //CurrentRunning.handler.privateMemory = m;
    currentActiveArea = m;
  }
  
  /**
   * This method is called once in javax.safetycritical.Launcher
   * @param size The size of the backing store used by the SCJ application
   */
  static void allocateBackingStore(int size) {
	backingStoreBase = Memory.allocateBackingStore(size);
	backingStoreEnd = backingStoreBase + size;
	/*devices.Console.println("MemoryArea allocateBackingStore: base: " 
	    + backingStoreBase + ";end: " + backingStoreEnd
		+ "; size: " + size);*/		
  }	

	
  static class ImmortalMemory extends ManagedMemory 
  {		
	ImmortalMemory(int sizeOfArea) {
	  super(sizeOfArea);
	}
	
	static ImmortalMemory instance() {
		return  (ImmortalMemory) head;
	}
  }
  
  /**
   * @param size is the number of free bytes in the memory area
   */
  ManagedMemory(int size) 
  {    
    super(size);
  } 
  
  /**
   * Makes this memory area the allocation context for the execution of the
   * <code>run()</code> method of the instance of <code>Runnable</code> given
   * in the constructor. <br>
   * During this period of execution, this memory area becomes the default
   * allocation context until another allocation context is selected or the
   * <code>Runnable</code>'s <code>run</code> method exits.
   * <p>
   * This method is like the <code>executeInArea</code> method, but extended
   * with cleanup and pointer reset.
   * 
   * @throws IllegalArgumentException if the caller is a schedulable object and
   *             <code>logic</code> is null.
   * 
   * @param logic is the <code>Runnable</code> object whose <code>run()</code>
   *            method shall be executed.
   */
  @SCJAllowed(Level.INFRASTRUCTURE)
  void enter(Runnable logic) throws IllegalArgumentException 
  {
	if (logic == null) throw exception; 

	ManagedMemory outer = getCurrentMemory();
	setCurrentMemory (this);

	outer.delegate.switchToArea(this.delegate);
	logic.run();
	this.delegate.switchToArea(outer.delegate);

	this.delegate.reset(0);
	setCurrentMemory (outer);
  }  
  
  /**
   * Executes <code>logic</code> in this memory area, with no cleanup and no
   * pointer reset at the end.
   * 
   * @param logic
   *   The Runnable object whose <code>run()</code> method shall be executed.
   * 
   * @throws IllegalArgumentException If <code>logic</code> is null.
   */
  @SCJAllowed
  public void executeInArea(Runnable logic) throws IllegalArgumentException 
  {
	if (logic == null) throw exception; 

	ManagedMemory outer = getCurrentMemory();
	Memory currentMem;

	if (outer == null) {
		currentMem = vm.Memory.getHeapArea();
	} else {
		currentMem = outer.delegate;
	}

	setCurrentMemory (this);

	currentMem.switchToArea(this.delegate);
	logic.run();
	this.delegate.switchToArea(currentMem);

	setCurrentMemory (outer);
  }
  	
  private static final ManagedMemory getOuterMemory(ManagedMemory m) 
  {
	if (m instanceof InnerPrivateMemory) return ((InnerPrivateMemory)m).prev;
	if (m instanceof PrivateMemory)return Mission.currMissSeq.missionMemory;
	if (m instanceof MissionMemory)return ImmortalMemory.instance();
	// since level 0 and 1 have no nested missions, the outer is immortal and stays there.
	return ImmortalMemory.instance();
  }
  
  /**
   * Invoke the run method of logic with a fresh private memory area that 
   * is immediately nested within the current <code>ManagedMemory</code> 
   * area, sized to provide <code>size</code> bytes of free memory 
   * as the current allocation area.
   * 
   * @param size is the number of free bytes within the inner-nested private memory.
   * 
   * @param logic provides the run method that is to be executed within 
   *    the inner-nested private memory area.
   */
  @SCJAllowed  
  public static void enterPrivateMemory(int size, Runnable logic) 
    throws IllegalStateException
  {
    /**
	 * prevMemory is the memory area at entry;
	 * prevFree is the free pointer before allocation of the private memory.
	 * If the current free has changed after running the logic, there has been allocation
	 * in the outer area, and the private memory cannot be released.
	 */
	if (logic == null) throw exception;
	
    ManagedMemory prev = getCurrentMemory();
    long prevFree = prev.memoryConsumed();
    
	InnerPrivateMemory inner = new InnerPrivateMemory(size);
	inner.prev = prev;
	
	setCurrentMemory(inner);
	inner.enter(logic);
	setCurrentMemory(prev);
	
	if (prev.memoryConsumed() != prevFree)
		prev.resetArea(prevFree);
        
	inner.removeArea();        
  }
  
  @SCJAllowed  
  public static void executeInAreaOf(Object obj, Runnable logic) 
  {
	if (obj == null || logic == null ) throw exception;
	  
	ManagedMemory target = (ManagedMemory)MemoryArea.getMemoryArea(obj);	
	ManagedMemory prev = getCurrentMemory();
	setCurrentMemory(target);
	target.executeInArea(logic);
	setCurrentMemory(prev);
  }  
 
  @SCJAllowed  
  public static void executeInOuterArea(Runnable logic) 
  {
    if (logic == null) throw exception;
    
	ManagedMemory prev = getCurrentMemory();
	ManagedMemory target = getOuterMemory(prev);
	setCurrentMemory(target);
	target.executeInArea(logic);
	setCurrentMemory(prev);
  }
  
  /**
   * 
   * @return the size of the remaining memory available to the current 
   *   ManagedMemory area.
   *  @scjComment the same as memoryRemaining() in MemoryArea?
   */
  @SCJAllowed   
  public long getRemainingBackingStore() 
  {
    return memoryRemaining();
  }
  
  /**
   * Resetting the number of available bytes. The parameter <code>newFree</code> is typically
   * acquired by a previous call of <code>memoryConsumed()</code>.
   * 
   * @param newFree the number.
   */
  private void resetArea(long newFree){
	this.delegate.reset((int) newFree);
  }
  
  void resetArea() {
	this.delegate.reset(0);
  }
  
  void removeArea() {
	this.removeMemArea();
  }
}
