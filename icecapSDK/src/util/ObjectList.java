package util;

import java.util.Iterator;

public class ObjectList {

	private Object[] elements;
	private int top;

	public ObjectList() {
		elements = new Object[10];
		top = 0;
	}

	public void add(Object element) {
		elements[top++] = element;
	}

	public Iterator<Object> iterator() {
		return new ObjectIterator();
	}

	private class ObjectIterator implements Iterator<Object> {
		int next;

		ObjectIterator() {
			next = 0;
		}

		@Override
		public boolean hasNext() {
			return next < top;
		}

		@Override
		public Object next() {
			Object n = elements[next++];
			return n;
		}

		
		public void remove() {
			// TODO Auto-generated method stub
			
		}
	}
}
