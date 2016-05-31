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

	public void insert(ScjProcess obj) {
		if (heapSize + 1 == tree.length)
			throw new IndexOutOfBoundsException();

		heapSize++;
		int i = heapSize;
		while (i > 1 && insertCompare(i, obj)) {
			tree[i] = tree[parent(i)];
			i = parent(i);
		}
		tree[i] = obj;
	}
	
	protected ScjProcess extract() {
		if (heapSize < 1)
			return null;

		ScjProcess target = tree[1];
		tree[1] = tree[heapSize];
		heapSize--;
		heapify(1);

		return target;
	}
	
	public void remove(ScjProcess obj) {
		if (obj == null)
			return;
		int i = find(obj.index);
		if (i != -999) {
			tree[i] = tree[heapSize];
			heapSize--;
			heapify(i);
		}
	}

	private int find(int value) {
		for (int i = 1; i <= heapSize; i++) {
			if (tree[i].index == value)
				return i;
		}
		return -999;
	}
	protected abstract boolean heapifyCompare(int a, int b);
	
	protected abstract boolean insertCompare(int i, ScjProcess obj);
}
