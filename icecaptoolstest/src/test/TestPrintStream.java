package test;

import java.io.OutputStream;

import javax.io.PrintStream;
import javax.microedition.io.ConsoleConnection;

public class TestPrintStream {
	
	 public static void main(String[] args) {
	       
	       
		 	test_PrintStream();
		 	
	        args = null; 
	    }
	 
	 static String str = "HSO works";
	 
	 static void test_PrintStream() {
		 
		 ConsoleConnection console = null;
		 try {
			  console = new ConsoleConnection("");
		 }
		 catch ( javax.microedition.io.ConnectionNotFoundException e) {
			 
		 }
		 
		 OutputStream out = console.openOutputStream();
		 
		 PrintStream printStr = new PrintStream (out);
		 
		 printStr.println(str);
		 
		 printStr.close();
		 
		 //devices.Console.println(str);
	 }

}



