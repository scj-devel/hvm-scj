package test;

import vm.VMTest;

public class TestFor1 {
    public static void main(String[] args) {

        VMTest.markResult(test());
    }

	public static boolean test() {
		for(int i = 0; i <= 42; i++){
            if(i == 42){
                return false;
            }
        }
		return true;
	}
}
