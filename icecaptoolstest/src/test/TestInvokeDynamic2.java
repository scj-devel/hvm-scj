package test;


public class TestInvokeDynamic2 {

    private static interface Adder {
        int add(int x, int y);
    }
    
    public static void main(String[] args) {
    
        Adder adder1 = (x, y) -> {
            return x + y;
        };

        int x1 = adder1.add(40, 2);
        
        Adder adder2 = new Adder()
        {
            @Override
            public int add(int x, int y) {
                return x + y;
            }
        };
        
        int x2 = adder2.add(39, 3);
        
        if (x1 == x2)
        {
            args = null;
        }
    }
}
