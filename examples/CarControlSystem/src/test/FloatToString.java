package test;

public class FloatToString {

	public static void main(String[] args) {
	  float x = 19.98f;
	  System.out.println("x = " + x);
	  
	  String result = floatToString (x);
	  
	  float y = 0.9180664062f;
	  int a = (int)y;
	  System.out.println("y = " + y + "; a = " + a);
	  
	}
	
	public static String floatToString (float x) {
		// Extract integer part
	    int ipart = (int) x;
	    
	    // Extract floating part
	    float fpart = x - ipart;
	    
	    System.out.println("ipart: " + ipart + "; fpart: " + fpart);
	    
	    int fourDecimals = Math.round(fpart * 10000);
	    
	    System.out.println("x = " + ipart + "." + fourDecimals); 
	    
	    return "";
	}

}
