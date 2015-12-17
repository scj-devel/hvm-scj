package test;

import java.net.URI;
import java.net.URISyntaxException;

public class TestURI {

	public static void main(String[] args) {
		System.out.println("Hello!");
		
		try {
			URI uri = new URI("http://www.icelab.dk");
			System.out.println("Created URI:[" + uri.toString() + "]");
			System.out.println("Scheme is: [" + uri.getScheme() + "]");
		} catch (URISyntaxException e) {
			System.out.println("Failed to create URI");
		}
		
		args = null;
	}
}
