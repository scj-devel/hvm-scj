package test;

public class TestTrie {

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

        public byte length() {
            return top;
        }

        public char charAt(short i) {
            return characters[i];
        }
    }
    
    private static class Trie {
        private char c;
        private Trie right;
        private Trie down;
        private boolean wordEndingHere;

        public Trie() {
            c = 0;
        }

        public void insert(TextString str) {
            if (str.length() > 0) {
                insert(str, (short) 0);
            }
        }

        private void insert(TextString str, short i) {
            char next = str.charAt(i);

            if (c == 0) {
                c = next;
            } else {
                if (c != next) {
                    if (right == null) {
                        right = new Trie();
                    }
                    right.insert(str, i);
                    return;
                }
            }

            if (i + 1 == str.length()) {
                wordEndingHere = true;
            } else {
                if (down == null) {
                    down = new Trie();
                }
                down.insert(str, (short) (i + 1));
            }
        }

        public boolean member(TextString str) {
            if (str.length() > 0) {
                return member(str, (short) 0);
            } else {
                return false;
            }
        }

        public boolean member(TextString str, short i) {
            if (str.charAt(i) == c) {
                if (i + 1 == str.length()) {
                    return wordEndingHere;
                } else {
                    if (down != null) {
                        return down.member(str, (short) (i + 1));
                    }
                }
            } else {
                if (right != null) {
                    return right.member(str, i);
                }
            }
            return false;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        if (!failed) {
            args = null;
        }
    }

    private static boolean test() {
        Trie root = new Trie();
        
        TextString Abba = new TextString((byte) 4);
        Abba.add('A');
        Abba.add('b');
        Abba.add('b');
        Abba.add('a');

        TextString Abbx = new TextString((byte) 4);
        Abbx.add('A');
        Abbx.add('b');
        Abbx.add('b');
        Abbx.add('x');
        
        TextString Abb = new TextString((byte) 3);
        Abb.add('A');
        Abb.add('b');
        Abb.add('b');
        
        TextString Abbab = new TextString((byte) 5);
        Abbab.add('A');
        Abbab.add('b');
        Abbab.add('b');
        Abbab.add('a');
        Abbab.add('b');
        
        TextString Horse = new TextString((byte) 5);
        Horse.add('H');
        Horse.add('o');
        Horse.add('r');
        Horse.add('s');
        Horse.add('e');
        
        TextString Ab = new TextString((byte) 2);
        Ab.add('A');
        Ab.add('b');
        
        TextString Abx = new TextString((byte) 3);
        Abb.add('A');
        Abb.add('b');
        Abb.add('x');
        
        TextString hello = new TextString((byte) 5);
        Horse.add('h');
        Horse.add('e');
        Horse.add('l');
        Horse.add('l');
        Horse.add('o');
        
        TextString Abbabb = new TextString((byte) 6);
        Abbabb.add('A');
        Abbabb.add('b');
        Abbabb.add('b');
        Abbabb.add('a');
        Abbabb.add('b');
        Abbabb.add('b');
        
        root.insert(Abba);
        
        root.insert(Abbx);

        root.insert(Abb);

        root.insert(Abbab);

        root.insert(Horse);

        if (root.member(Abba)) {
            if (root.member(Abbx)) {
                if (!root.member(Ab)) {
                    if (!root.member(Abx)) {
                        if (!root.member(hello)) {
                            if (root.member(Abb)) {
                                if (root.member(Abbab)) {
                                    if (!root.member(Abbabb)) {
                                        if (root.member(Horse)) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
