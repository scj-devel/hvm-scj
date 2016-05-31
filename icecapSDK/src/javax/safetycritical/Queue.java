package javax.safetycritical;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.SCJAllowed;

@SCJAllowed(Level.INFRASTRUCTURE)
abstract class Queue {
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

	void heapify(int i) {
		int l = left(i);
		int r = right(i);

		int target;

		if (l <= heapSize && heapifyCompare(tree[l].compareTo(tree[r]), 0))
			target = l;
		else
			target = i;

		if (r <= heapSize && heapifyCompare(tree[r].compareTo(tree[target]), 0))
			target = r;

		if (target != i) {
			exchange(i, target);
			heapify(target);
		}
	}

	protected abstract boolean heapifyCompare(int a, int b);
	
	protected abstract boolean insertCompare(int a, int b);
}
