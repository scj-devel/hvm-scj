package test;

import vm.VMTest;

public class TestNewArray {

    /**
     * @param args
     */
    public static void main(String[] args) {
       VMTest.markResult(test());
    }

	@SuppressWarnings("unused")
    public static boolean test() {
		int array[];
        array = new int[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int res = 0;
        for (int i = 0; i < array.length; i++) {
            res += array[i];
        }
        if (array.length != 5) {
            return true;
        }

        char[][][] charMap;

        charMap = new char[][][] { { { 'A' }, { 'B', 'C', } },
                                   { { 'D' }, { 'E', } }, 
                                   { { 'F' }, { 'G', 'H', } } 
                                 };
        
        StringBuffer buffer = new StringBuffer();
        
        for (int i = 0; i < charMap.length; i++)
        {
            char[][] subMap = charMap[i];
            for (int j = 0; j < subMap.length; j++)
            {
                char[] subSubMap = subMap[j];
                for (int k = 0; k < subSubMap.length; k++)
                {
                    buffer.append(subSubMap[k]);
                }
            }
        }
        
        if (buffer.toString().equals("ABCDEFGH"))
        {
        	return false;
        }
        return true;
	}
}
