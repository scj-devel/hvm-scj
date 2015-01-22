package test;

public class TestBigInt {

    private static class FDBigInt
    {

        public FDBigInt(int i)
        {
            nWords = 1;
            data = new int[1];
            data[0] = i;
        }

        private FDBigInt(int ai[], int i)
        {
            data = ai;
            nWords = i;
        }

        public FDBigInt mult(int i)
        {
            long l = i;
            int ai[] = new int[l * ((long)data[nWords - 1] & 0xffffffffL) <= 0xfffffffL ? nWords : nWords + 1];
            long l1 = 0L;
            for(int j = 0; j < nWords; j++)
            {
                l1 += l * ((long)data[j] & 0xffffffffL);
                ai[j] = (int)l1;
                l1 >>>= 32;
            }

            if(l1 == 0L)
            {
                return new FDBigInt(ai, nWords);
            } else
            {
                ai[nWords] = (int)l1;
                return new FDBigInt(ai, nWords + 1);
            }
        }

        public FDBigInt add(FDBigInt fdbigint)
        {
            long l = 0L;
            int ai[];
            int ai1[];
            int j;
            int k;
            if(nWords >= fdbigint.nWords)
            {
                ai = data;
                j = nWords;
                ai1 = fdbigint.data;
                k = fdbigint.nWords;
            } else
            {
                ai = fdbigint.data;
                j = fdbigint.nWords;
                ai1 = data;
                k = nWords;
            }
            int ai2[] = new int[j];
            int i;
            for(i = 0; i < j; i++)
            {
                l += (long)ai[i] & 0xffffffffL;
                if(i < k)
                    l += (long)ai1[i] & 0xffffffffL;
                ai2[i] = (int)l;
                l >>= 32;
            }

            if(l != 0L)
            {
                int ai3[] = new int[ai2.length + 1];
                System.arraycopy(ai2, 0, ai3, 0, ai2.length);
                ai3[i++] = (int)l;
                return new FDBigInt(ai3, i);
            } else
            {
                return new FDBigInt(ai2, i);
            }
        }

        private static boolean dataInRangeIsZero(int i, int j, FDBigInt fdbigint)
        {
            while(i < j) 
                if(fdbigint.data[i++] != 0)
                    return false;
            return true;
        }

        public long longValue()
        {
            if(nWords <= 0)
                throw new AssertionError(nWords);
            if(nWords == 1)
                return (long)data[0] & 0xffffffffL;
            if(!dataInRangeIsZero(2, nWords, this))
                throw new AssertionError();
            if(data[1] < 0)
                throw new AssertionError();
            else
                return (long)data[1] << 32 | (long)data[0] & 0xffffffffL;
        }

        public String toString()
        {
            StringBuffer stringbuffer = new StringBuffer(30);
            stringbuffer.append('[');
            int i = Math.min(nWords - 1, data.length - 1);
            if(nWords > data.length)
                stringbuffer.append((new StringBuilder()).append("(").append(data.length).append("<").append(nWords).append("!)").toString());
            for(; i > 0; i--)
            {
                stringbuffer.append(Integer.toHexString(data[i]));
                stringbuffer.append(' ');
            }

            stringbuffer.append(Integer.toHexString(data[0]));
            stringbuffer.append(']');
            return new String(stringbuffer);
        }

        int nWords;
        int data[];
    }

    public static void main(String[] args) {
        FDBigInt bi = new FDBigInt(4);
        FDBigInt bii = new FDBigInt(2);
        bi = bi.mult(10);
        bi = bi.add(bii);
        if (bi.longValue() == 42L)
        {
            if (bi.toString().equals("[2a]"))
            {
               args = null;
            }
        }
    }
}
