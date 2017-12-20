package test;

import vm.VMTest;

public class TestDAStore {

    /**
     * @param args
     */
    public static void main(String[] args) {
        boolean failed = test();
        VMTest.markResult(failed);
    }

    @SuppressWarnings("unused")
    public static boolean test() {
        double d = 1.0;
        double[] array = new double[10];
        
       for (int i = 0; i < array.length; i++)
       {
    	   array[i] = 1.01d * i;
       }
       
       double sum = 0.0d;
       for (int i = 0; i < array.length; i++)
       {
    	   sum = sum + array[i];
       }
       
       double diff = sum - 45.45d;
       
       if ((diff < 0.01d) && (diff > -0.01d))
       {
    	   return false;
       }
       else
       {
    	   return true;
       }
    }
}
