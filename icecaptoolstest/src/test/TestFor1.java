package test;

public class TestFor1 {
    public static void main(String[] args) {

        args = test(args);
    }

	public static String[] test(String[] args) {
		for(int i = 0; i <= 42; i++){
            if(i == 42){
                return null;
            }
        }
		return args;
	}
}
