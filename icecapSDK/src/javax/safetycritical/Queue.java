package javax.safetycritical;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

@SCJAllowed(Level.INFRASTRUCTURE)
class Queue {
	protected int heapSize;

	protected ScjProcess[] tree;
	
	public Queue(int size) {
		heapSize = 0;
		tree = new ScjProcess[size + 1];
		makeEmptyTree(this.tree);		
	}
	
	private void makeEmptyTree(ScjProcess[] tree) {
		for (int i = 0; i < tree.length; i++)
			tree[i] = null;
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

}
