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
	
	public void print(char[] s) {
		for (int i = 0; i < s.length; i++)
			print(s[i]);
	}
	
	public void print(Object obj) {
		out.print(obj);
	}
	
	public void print(String s) {
		out.print(s);
	}
	
	public void print(long l) {
		out.print(l);
	}
	
	public void print(char c) {
		out.print(c + "");
	}
	
	public void print(boolean b) {
		out.print(b);
	}
	
	public void println(boolean b) {
		print(b);
		println();		
	}
	
	public void println(char c) {
		print(c);
		println();		
	}
	
	public void println(int i) {
		print(i);
		println();
	}
	
	public void println(char[] s) {
		print(s);
		println();
	}
	
	public void println(String s) {
		out.println(s);
	}
	
	public void println(Object obj) {
		print(obj);
		println();
	}
	
	public void println(long l) {
		print(l);
		println();
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



