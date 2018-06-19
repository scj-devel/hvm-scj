// ***************************************************
// File name  : Main2ObjectSizeCalculator.java
// Start date : 2016
// Programmer : Hans So.
// Java       : Java 1.7 
// Description: 
// ***************************************************

package util.objectsize;

public class Main2ObjectSizeCalculator
{

  public static void main (String[] args)
  {
    int a = 3;
    String s = "abc";
    
    try {
      long size = ObjectSizeCalculator.sizeOf(a);
      System.out.println("size of int 3: " + size );
      size = ObjectSizeCalculator.sizeOf(s);
      System.out.println("size of String abc: " + size );
    }
    catch (IllegalAccessException e) {
      System.out.println(e.getMessage());
    }

  }

}
