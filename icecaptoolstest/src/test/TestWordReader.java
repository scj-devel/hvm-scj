package test;

import vm.VMTest;

public class TestWordReader {

    private static class Random {

        private int m_w;
        private int m_z;
         
        public Random()
        {
            m_w = 42;
            m_z = 0xcafebabe;
        }
        
        public int getInt()
        {
            m_z = 36969 * (m_z & 65535) + (m_z >> 16);
            m_w = 18000 * (m_w & 65535) + (m_w >> 16);
            return (m_z << 16) + m_w;  /* 32-bit result */
        }

        public byte getByte() {
            byte result = (byte) (getInt() & 0xff);
            return result;
        }

        public byte getNonNegativeByte() {
            byte b = getByte();
            if (b < 0)
            {
                b = (byte) -b;
            }
            if (b < 0)
            {
                b = 0;
            }
            return b;
        }
    }

    
    private static class DataSet {

        private TextString[] elements;
        private short top;

        public DataSet() {
            elements = new TextString[1];
            top = 0;
        }

        public boolean contains(TextString target) {
            if (top > 0) {
                return binarySearch(target, (short)0, top);
            } else {
                return false;
            }
        }

        private boolean binarySearch(TextString target, short from, short to) {

            if (from == to - 1) {
                return elements[from].equals(target);
            }

            short middle = (short) ((to - from) / 2 + from);

            if (elements[from].equals(target)) {
                return true;
            }

            if (elements[middle].compareTo(target) > 0) {
                // search left
                return binarySearch(target, from, middle);
            } else {
                // search right
                return binarySearch(target, middle, to);
            }
        }

        public void add(TextString nextElement) {
            if (top < elements.length) {
                elements[top++] = nextElement;
                sort();
            } else {
                TextString[] newArray = new TextString[elements.length * 2];
                for (short i = 0; i < elements.length; i++) {
                    newArray[i] = elements[i];
                }
                elements = newArray;
                add(nextElement);
            }
        }

        private void sort() {
            short index = 0;

            while (index < top - 1) {
                if (elements[index].compareTo(elements[index + 1]) <= 0) {
                    index++;
                } else {
                    TextString temp = elements[index];
                    elements[index] = elements[index + 1];
                    elements[index + 1] = temp;
                    if (index > 0) {
                        index--;
                    }
                }
            }
        }

        public short size() {
            return top;
        }
    }

    private static class TextString  {
        private byte top;
        private char[] characters;

        public TextString(byte initialCapacity) {
            characters = new char[initialCapacity];
            top = 0;
        }

        public void add(char ascii) {
            if (top < characters.length) {
                characters[top++] = ascii;
            }
        }

        @Override
        public String toString() {
            return new String(characters);
        }

        public byte compareTo(TextString right) {
            TextString other = (TextString) right;
            byte index = 0;
            while ((index < top) && (index < other.top)) {
                char thisc, otherc;
                thisc = characters[index];
                otherc = other.characters[index];
                if (thisc < otherc) {
                    return -1;
                } else if (thisc > otherc) {
                    return 1;
                }
                index++;
            }

            if (top < other.top) {
                return -1;
            } else if (top > other.top) {
                return 1;
            }

            return 0;
        }

        public boolean equals(TextString right) {
            return compareTo(right) == 0;
        }
    }

    private static class WordGenerator {
        private short numberOfWordsToGenerate;
        private Random rand;

        public WordGenerator(short i) {
            numberOfWordsToGenerate = i;
            rand = new Random();
        }

        public TextString next() {
            TextString str = null;
            if (numberOfWordsToGenerate > 0) {
                byte length;

                do {
                    byte n = rand.getNonNegativeByte();
                    length = (byte) (n % 10);
                } while (length == 0);

                str = new TextString(length);
                while (length > 0) {
                    byte ascii = (byte) ((rand.getByte() % 28) + 'A');
                    str.add((char) ascii);
                    length--;
                }
                numberOfWordsToGenerate--;
            }
            return str;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    private static boolean test() {
        DataSet ds = new DataSet();

        WordGenerator wordGen = new WordGenerator((short) 17);

        TextString next = wordGen.next();

        while (next != null) {
            if (!ds.contains(next)) {
                ds.add(next);
            }
            next = wordGen.next();
        }
        devices.Console.println("ds contains " + ds.size() + " distinct words");
        //System.out.println("ds contains " + ds.size() + " distinct words");
        return false;
    }
}
