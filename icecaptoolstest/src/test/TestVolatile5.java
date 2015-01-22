package test;

public class TestVolatile5 {

    public static final int size = 10;
    
    private static class ConstantData {
        public volatile int[] numbers;
        
        public ConstantData()
        {
            numbers = new int[size];
            
            for (int i = 0; i < size; i++)
            {
                numbers[i] = i;
            }
        }        
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ConstantData cdata = new ConstantData();
        devices.System.lockROM();

        if (cdata.numbers.length == size) {
            for (int i = 0; i < size; i++)
            {
                if (cdata.numbers[i] != i)
                {
                    return;
                }
            }
            args = null;
        }
    }
}

