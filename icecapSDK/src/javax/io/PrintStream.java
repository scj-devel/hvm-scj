package javax.io;

import java.io.OutputStream;

import javax.safetycritical.annotate.SCJAllowed;

public class PrintStream extends OutputStream {

	@SCJAllowed
	public PrintStream(OutputStream out) {
		
	}
	
	public boolean checkError( ) {
		return false;
	}
	
	public void close( ) {
		
	}
	
	public void flush( ) {
		
	}
	
	public void print(int i) {
		devices.Console.print(i);
	}
	
	public void print(char [] s) {
		
	}
	
	public void print(Object obj) {
		
	}
	
	public void print(String s) {
		devices.Console.print(s);
	}
	
	public void print(long l) {
		
	}
	
	public void print(char c) {
		devices.Console.print(c + "");
	}
	
	public void print(boolean b) {
		
	}
	
	public void println(boolean x) {
		
	}
	
	public void println(char c) {
		print(c);
		println();		
	}
	
	public void println(int i) {
		print(i);
		println();
	}
	
	public void println(char [] x) {
		
	}
	
	public void println(String s) {
		devices.Console.println(s);
	}
	
	public void println(Object x) {
		
	}
	
	public void println(long x) {
		
	}
	
	public void println( ) {
		devices.Console.println("");
	}
	
	protected void setError( ) {
		
	}
	
	public void write(byte [] buf, int off, int len) {
		
	}
	
	public void write(int b) {
		
	}

}



