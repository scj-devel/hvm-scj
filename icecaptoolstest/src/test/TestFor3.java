package test;

public class TestFor3 {

    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		for(int i = -100, j = 100; i <= 100 && j > -101; i++, j--){
            if(i == 100){
                if(j == -100){
                    return null;
                }
            }
        }
		return args;
	}

}
