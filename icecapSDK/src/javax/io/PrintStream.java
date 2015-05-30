package javax.io;

import java.io.OutputStream;

import javax.safetycritical.annotate.SCJAllowed;

import devices.System.DevicePrintStream;

public class PrintStream extends OutputStream {

	DevicePrintStream out;
	boolean errorState;
	
	@SCJAllowed
	public PrintStream(OutputStream out) {
		this.out = (DevicePrintStream)out;
		this.errorState = false;
	}
	
	public boolean checkError( ) {
		flush();
		return errorState;
	}
	
	public void close( ) {
		if (out != null)
			out.close();
	}
	
	public void flush( ) {
		if (out != null)
			out.flush();
	}
	
	public void print(int i) {
		out.print(i);
	}
	
	public void print(char[] s) {
		out.print(s);
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
		out.print(c);
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
		errorState = true;
	}
	
	public void write(byte [] buf, int off, int len) {
		out.write(buf, off, len);	
	}
	
	public void write(int b) {
		if (b > 255 || b < 0) {
			b = '?';
		}
		out.write(b);
	}

}



