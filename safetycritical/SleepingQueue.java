/**************************************************************************
 * File name  : SleepingQueue.java
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
 *************************************************************************/

package javax.safetycritical;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

/**
 * This <code>SleepingQueue</code> class holds the sleeping processes
 * in the <code>PriorityScheduler</code>. <br>
 * Time complexity of the methods <code>insert</code> and 
 * <code>extractMin</code> are O(log n).<br>
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
@SCJAllowed(Level.INFRASTRUCTURE)
class SleepingQueue 
{
	int heapSize;
	
	protected ScjProcess[] tree;

	private final int defalutCapacity = 5;

	public SleepingQueue() {
		heapSize = 0;
		tree = new ScjProcess[defalutCapacity + 1];
	}

	public SleepingQueue(int size) {
		heapSize = 0;
		tree = new ScjProcess[size + 1];
	}

	int parent(int i) {
		return i / 2;
	}

	int left(int i) {
		return 2 * i;
	}

	int right(int i) {
		return 2 * i + 1;
	}

	void exchange(int a, int b) {
		ScjProcess temp = tree[a];
		tree[a] = tree[b];
		tree[b] = temp;
	}

	void heapify(int i) {
		int l = left(i);
		int r = right(i);

		int smallest;

		if (l <= heapSize && tree[l].next.compareTo(tree[r].next) < 0)
			smallest = l;
		else
			smallest = i;

		if (r <= heapSize && tree[r].next.compareTo(tree[smallest].next) < 0)
			smallest = r;

		if (smallest != i) {
			exchange(i, smallest);
			heapify(smallest);
		}
	}

	public void insert(ScjProcess obj) {
		if (heapSize + 1 == tree.length)
			expandCapacity();

		heapSize++;
		int i = heapSize;
		int parent = parent(i);
		while (i > 1 && tree[parent].next.compareTo(obj.next) > 0) {
			tree[i] = tree[parent];
			i = parent;
			parent = parent(i);

		}
		tree[i] = obj;
	}

	public ScjProcess minimum() {
		if (heapSize > 0)
			return tree[1];
		else
			return null;
	}

	public ScjProcess extractMin() {
		if (heapSize < 1)
			return null;

		ScjProcess min = tree[1];
		tree[1] = tree[heapSize];
		heapSize--;
		heapify(1);

		return min;
	}

	/**
	 * Private method to expand capacity if full.
	 */
	protected void expandCapacity() {
		ScjProcess[] temp = new ScjProcess[tree.length * 2];

		for (int ct = 1; ct <= heapSize; ct++)
			temp[ct] = tree[ct];

		tree = temp;
	}

	/**
	 * Print out the contents of the priority queue. For testing only.
	 */
	public void print() {
		devices.Console.println("sQueue size = " + heapSize);
		for (int i = 1; i <= heapSize; i++) {
			devices.Console.println(tree[i].toString());
			//System.out.println(tree[i].toString());
		}
		//System.out.println("Count is: " + heapSize);
	}

	// For testing only
	public static void main(String[] args) {
		System.out.println("Main to SleepingQueue begin");

		SleepingQueue queue = new SleepingQueue();

		int n = 5;
		// int id = 1;
		for (int i = 0; i < n; i++) {
			// queue.insert(new ScjProcess (id++, new AbsoluteTime (i,0)));
			// queue.insert(new ScjProcess (id++, new AbsoluteTime (i,0)));
		}

		queue.print();

		for (int i = 0; i < 2 * n; i++) {
			System.out.println("====== remove ======");
			ScjProcess process = queue.extractMin();
			System.out.println("Removed: " + process);
			// queue.print();
		}
	}
}
