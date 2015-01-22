package icecaptools;

import java.util.Iterator;

public class ToIcecapIterator<T> implements IcecapIterator<T> {

    private Iterator<T> descs;

    public ToIcecapIterator(Iterator<T> descs)
    {
        this.descs = descs;
    }

    @Override
    public boolean hasNext() {
        return descs.hasNext();
    }

    @Override
    public T next() {
        return descs.next();
    }
}
