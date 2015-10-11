package javax.io;

import java.io.OutputStream;

import javax.safetycritical.annotate.SCJAllowed;

import devices.System.DevicePrintStream;

public class PrintStream extends OutputStream {

	DevicePrintStream out;
	
	@SCJAllowed
	public PrintStream(OutputStream out) {
		this.out = (DevicePrintStream)out;
	}
	
	public boolean checkError( ) {
		return false;
	}
	
	public void close( ) {
		
	}
	
	public void flush( ) {
		
	}
	
	public void print(int i) {
		out.print(i);
	}
	
	public void print(char [] s) {
		
	}
	
	public void print(Object obj) {
		
	}
	
	public void print(String s) {
		out.print(s);
	}
	
	public void print(long l) {
		
	}
	
	public void print(char c) {
		out.print(c + "");
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
		out.println(s);
	}
	
	public void println(Object x) {
		
	}
	
	public void println(long x) {
		
	}
	
	public void println( ) {
		out.println("");
	}
	
	protected void setError( ) {
		
	}
	
	public void write(byte [] buf, int off, int len) {
		
	}
	
	public void write(int b) {
		
	}

}



