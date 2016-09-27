/**************************************************************************
 * File name  : PriorityQueue.java
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
 *  date    init  comment
 *  31Jan12 HSO   In this impl., the place with index 0 (zero) is not used.
 *                The methods and algorithms are from Cormen et al.
 *                Introduction to Algorithms. MIT Press. 1990.
 *  Dec2012 HSO   Uses indexes instead of references to ScjProcesses
 *************************************************************************/

package javax.safetycritical;

import javax.scj.util.Const;

import util.StringUtil;

/**
 * This <code>PriorityQueue</code> class holds the ready processes
 * in the <code>PriorityScheduler</code>. <br>
 * Time complexity of the methods <code>insert</code> and 
 * <code>extractMax</code> are O(log n).<br>
 * The class is package protected because it is not part of the SCJ 
 * specification.
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
class PriorityQueue extends Queue {

	public PriorityQueue(int size) {
		super(size);
	}

	protected boolean heapifyCompare(int a, int b) {
		return a > b;
	}
	
	protected boolean insertCompare(int i, ScjProcess obj) {
		return tree[parent(i)].compareTo(obj) < 0;
	}

	public ScjProcess extractMax() {
		return extract();
	}

	/**
	 * Print out the contents of the priority queue.
	 * For testing only.
	 */
	public void print() {
		devices.Console.println(StringUtil.constructString("readyQueue size = ", heapSize));
		//		for (int i = 1; i <= heapSize; i++) {
		//			devices.Console.println((getScjProcess(tree[i]).toString()));
		//			//System.out.println (tree[i].toString());
		//		}
		//devices.Console.println("\n");
		//System.out.println ("Count is: " + heapSize);
	}

	// For testing only; does not work with index
	public static void main(String[] args) {
		System.out.println("Main to PriorityQueue begin");

		PriorityQueue queue = new PriorityQueue(Const.DEFAULT_PRIORITY_QUEUE_SIZE_DEFAULT);

		int n = 5;
		@SuppressWarnings("unused")
		int priority = 1;
		for (int i = 0; i < n; i++) {
			//queue.insert(new ScjProcess (new PriorityParameters(priority++), i));
		}

		queue.print();

		for (int i = 0; i < 2 * n; i++) {
			System.out.println("====== remove ======");
			ScjProcess process = queue.extractMax();
			System.out.println("Removed: " + process);
			//queue.print();
		}
	}
}
