package test;

import vm.VMTest;

public class TestStringBuffer1 {
    /*
     * private static class AbstractStringBuilder { protected char[] value1;
     * 
     * protected AbstractStringBuilder(int capacity) { value1 = new
     * char[capacity]; }
     * 
     * }
     * 
     * private static class StringBuffer extends AbstractStringBuilder { public
     * StringBuffer() { super(16); }
     * 
     * char getCharAt(int index) { return 'a'; }
     * 
     * }
     */
    public static void main(String args[]) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		StringBuffer buffer = new StringBuffer();
        buffer.append('H');
        buffer.append('e');
        buffer.append('l');
        buffer.append('l');
        buffer.append('o');
        buffer.append('W');
        buffer.append('o');
        buffer.append('r');
        buffer.append('l');
        buffer.append('d');
        buffer.append('!');
        
        if (buffer.length() == 11) {
            return false;
        }
        return true;
	}
}
