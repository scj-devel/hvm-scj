package icecaptools.compiler.utils;

import java.util.Iterator;

public class EmptyStringIterator implements Iterator<String> {

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public String next() {
		return null;
	}

}
