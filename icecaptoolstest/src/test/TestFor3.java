package test;

import vm.VMTest;

public class TestFor3 {

    public static void main(String[] args) {
        VMTest.markResult(test());
    }

	public static boolean test() {
		for(int i = -100, j = 100; i <= 100 && j > -101; i++, j--){
            if(i == 100){
                if(j == -100){
                    return false;
                }
            }
        }
		return true;
	}

}
