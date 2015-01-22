package test;

public class TestCAS {

    public static class AbortException extends RuntimeException
    {
        private static final long serialVersionUID = 7589329953466133705L;

        public AbortException(String string)
        {
            super(string);
        }
    }

    public static class AtomicReference<V>
    {
        public V value;
        private static long valueOffset = 2;

        private static native boolean cas(Object target, long offset, Object expect, Object update);

        public AtomicReference(V initialValue)
        {
            value = initialValue;
        }

        public AtomicReference()
        {
        }

        public V get()
        {
            return value;
        }

        public void set(V newValue)
        {
            value = newValue;
        }

        public boolean compareAndSet(V expect, V update)
        {
            /*
            synchronized (this)
            {

                if (value == expect)
                {
                    value = update;
                    return true;
                }
                return false;
            }/*/
            return cas(this, valueOffset, expect, update);//*/
        }

        public V getAndSet(V newValue)
        {
            while (true)
            {
                V x = get();
                if (compareAndSet(x, newValue))
                {
                    return x;
                }
            }
        }

        @Override
        public String toString()
        {
            return String.valueOf(get());
        }
    }

    
    public static class Transaction
    {
        enum State {
            LIVE, COMMITTED, ABORTED
        };

        private final AtomicReference<State> state;
        private final int priority;

        public Transaction(int priority)
        {
            this.state = new AtomicReference<State>(State.LIVE);
            this.priority = priority;
        }

        public void commit() throws AbortException
        {
            if (!state.compareAndSet(State.LIVE, State.COMMITTED))
            {
                abort("Could not commit");
            }
        }

        public void abort(String reason) throws AbortException
        {
            throw new AbortException(reason);
        }

        public boolean isCommitted()
        {
            return (state.get() == State.COMMITTED);
        }

        public <T> T read(AtomicReference<TValue<T>> value) throws AbortException
        {
            TValue<T> v = value.get();

            if (v.owner != this)
            {
                TValue<T> result = new TValue<T>(this, v.oldValue, v.newValue);

                if (v.owner != null)
                {
                    if (v.owner.state.get() == State.LIVE && v.owner.priority > this.priority)
                    {
                        abort("Unable to preempt higher priority transaction");
                    }

                    v.owner.state.compareAndSet(State.LIVE, State.ABORTED);
                    if (v.owner.state.get() == State.COMMITTED)
                    {
                        result.oldValue = v.newValue;
                    }
                    else
                    {
                        result.newValue = v.oldValue;
                    }
                }

                if (!value.compareAndSet(v, result))
                {
                    abort("Someone else acquired the variable while reading");
                }
                v = result;
            }
            if (state.get() != State.LIVE)
            {
                abort("This transaction has been aborted by someone else");
            }
            return v.newValue;
        }

        public <T> void write(AtomicReference<TValue<T>> value, T newValue) throws AbortException
        {
            TValue<T> v = value.get();
            TValue<T> result = new TValue<T>(this, v.oldValue, newValue);

            if (v.owner != this && v.owner != null)
            {
                if (v.owner.state.get() == State.LIVE && v.owner.priority > this.priority)
                {
                    abort("Unable to preempt higher priority transaction");
                }

                v.owner.state.compareAndSet(State.LIVE, State.ABORTED);
                if (v.owner.state.get() == State.COMMITTED)
                {
                    result.oldValue = v.newValue;
                }
            }

            if (state.get() != State.LIVE)
            {
                abort("This transaction has been aborted by someone else");
            }

            if (!value.compareAndSet(v, result))
            {
                abort("Someone else acquired the variable while writing");
            }
        }
    }

    
    public static class TValue<T>
    {
        public final Transaction owner;
        public T oldValue, newValue;

        public TValue(Transaction owner, T oldValue, T newValue)
        {
            this.owner = owner;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public TValue(T initialValue)
        {
            this(null, initialValue, initialValue);
        }
    }

    
    /**
     * @param args
     */
    public static void main(String[] args) {
        TValue<Integer> test0 = new TValue<Integer>(0);
        TValue<Integer> test1 = new TValue<Integer>(1);
        
        AtomicReference<TValue<Integer>> test = new AtomicReference<TValue<Integer>>(test0);

        test.compareAndSet(test0, test1);
        
        test0 = test.get();
        //devices.Console.println(String.valueOf(test.get().newValue));
        if (test0 == test1)
        {
            args = null;
        }
        //*/
    }

}
