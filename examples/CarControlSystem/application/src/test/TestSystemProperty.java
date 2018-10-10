package test;

public class TestSystemProperty {

	public static void main(String[] args) {
		String result = System.getProperty("java.io.tmpdir");
		System.out.println("java.io.tmpdir: " + result);
		
		result = System.getProperty("user.home");
		System.out.println("user.home: " + result);
	}

}
