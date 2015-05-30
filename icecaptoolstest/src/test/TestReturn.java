package test;

import vm.VMTest;

public class TestReturn {

	public void returnVoid(int n){
		if(n == 42){
			return;
		}else{
			while(true);
		}
	}

	public char returnChar(char c){
		return c;
	}

	public byte returnByte(byte n){
		return n;
	}

	public int returnInt(int n){
		return n;
	}

	public float returnFloat(float n){
		return n;
	}

	public boolean returnBoolean(boolean b){
		return b;
	}

	public long returnLong(long n){
		return n;
	}

	public int[] returnArrayInt(int[] a){
		return a;
	}

	public String[] returnArrayString(String[] a){
		return a;
	}

	public static void main(String[] args){
		TestReturn app = new TestReturn();

		app.returnVoid(42);
		if(app.returnChar('c') != 'c'){
			return;
		}

		if (app.returnByte((byte)42) != (byte)42){
			return;
		}

		if(app.returnInt(42) != 42){
			return;
		}

		if(app.returnFloat(42) != (float)42){
			return;
		}

		if(app.returnBoolean(false)){
			return;
		}

		if(app.returnLong(0xDEADBEEFL) != 0xDEADBEEFL){
			return;
		}

		int[] intArr = new int[] {0,1,2,3,4,5};
		if(!app.returnArrayInt(intArr).equals(intArr)){
			return;
		}

		String[] strArr = new String[] {"test", "java", "String", "array"};
		if(!app.returnArrayString(strArr).equals(strArr)){
			return;
		}

		VMTest.markResult(false);
	}
}
