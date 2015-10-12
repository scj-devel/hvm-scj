package test;

import java.io.OutputStream;

import javax.io.PrintStream;
import javax.microedition.io.ConsoleConnection;

public class TestPrintStream {
	
	 public static void main(String[] args) {	       
	       
		 	int result = test_PrintStream();
		 	
		 	if (result >= 0)
	          args = null; 
	 }
	 
	 static String str = "HSO works";
	 
	 static int test_PrintStream() {
		 
		 ConsoleConnection console = null;
		 try {
			  console = new ConsoleConnection("");
		 
		 
			 OutputStream out = console.openOutputStream();
			 
			 PrintStream printStr = new PrintStream (out);
			 
			 printStr.println(str);
			 printStr.println(123);
			 printStr.println('a');	
			 
			 boolean b = true;
			 printStr.println(b);	
			 
			 printStr.println(false);
			 
			 long l = 1234567887654321L;
			 printStr.println(l);	
			 
			 char[] s = {'a', 'b', 'c', 'd'};
			 printStr.println(s);
			 
			 Object obj = null;
			 printStr.println(obj);
			 
			 Item item = new Item("AAA", 111);
			 printStr.println(item);
			 
			 byte bt = 3;
			 printStr.write(bt);
			 printStr.println();
			 
			 
			 byte[] bytes = {70, 71, 72, 73, 74};
			 printStr.write (bytes, 1, 3);
			 printStr.println();
			 
			 printStr.close();
			 
			 return 0;
		 }
		 catch ( javax.microedition.io.ConnectionNotFoundException e) {
			 return -1;
		 }
	 }
	 
	 static class Item {
		 String name;
		 int no;
		 
		 Item (String name, int no) {
			 this.name = name;
			 this.no = no;
		 }
		 
		 public String toString() {
			 return "(" + name + ", " + no + ")" ;
		 }
	 }

}



