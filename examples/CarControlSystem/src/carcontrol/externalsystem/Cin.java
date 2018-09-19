//*************************************************************************
// File name  : Cin.java.
// Start date : 17-01-99.
// Programmer : Hans So.
// Java       : Java 1.4.2 
// Description: Simple input class
//
// Revision history:
// date     init comment
// 08/02/06 hso  - class is now final
//               - all method are declared static
//*************************************************************************

package carcontrol.externalsystem;

import java.io.*;
  
/**
 * A helper class that allows <CODE>int, long, double, float, 
 * char and String</CODE>  to be read from an input stream.<BR>
 * If a fault arises when reading the value, exceptions will be caught,
 * and a defult value will be returned.
 * <PRE>
 *   Lit: R.Winder & G. Roberts: Developing Java Software. Wiley. 1998, p.788.
 * </PRE>
 * @version 1.0; - 17th Jan. 1999
 * @author Hans Sondergaard and R. Winder
 */

public final class Cin
{  
  private static BufferedReader cin = 
    new BufferedReader (new InputStreamReader (System.in));

  /**
   * Read an <code>int</code> value from standard input.   
   * @return the read value; - default return value is 0.
   */
  public static int readInt ()
  {
    String input = "";
    int val = 0;
    
    try
    {
      input = cin.readLine();
      val = Integer.parseInt (input);
    }
    catch (IOException e) {}
    catch (NumberFormatException e) {}    
    return val;
  }
  
  /**
   * Read a <code>long</code> value from standard input.   
   * @return the read value; - default return value is 0L.
   */
  public static long readLong ()
  {
    String input = "";
    long val = 0L;
    
    try
    {
      input = cin.readLine();
      val = Long.parseLong (input);
    }
    catch (IOException e) {}
    catch (NumberFormatException e) {}    
    return val;
  }

  /**
   * Read a <code>double</code> value from standard input.   
   * @return the read value; - default return value is 0.0D.
   */
  public static double readDouble ()
  {
    String input = "";
    double val = 0.0D;
    
    try
    {
      input = cin.readLine();
      Double dobj = new Double (input);
      val = dobj.doubleValue();      
    }
    catch (IOException e) {}
    catch (NumberFormatException e) {}
    return val;
  }

  /**
   * Read a <code>float</code> value from standard input.   
   * @return the read value; - default return value is 0.0F.
   */
  public static float readFloat ()
  {
    String input = "";
    float val = 0.0F;
    
    try
    {
      input = cin.readLine();
      Float fobj = new Float (input);
      val = fobj.floatValue();
    }
    catch (IOException e) {}
    catch (NumberFormatException e) {}
    return val;
  }

  /**
   * Read a <code>char</code> value from standard input.   
   * @return the read value; - default return value is ' ', that is SPACE.
   */
  public static char readChar ()
  {    
    char val = ' ';
    
    try
    {
      val = (char)cin.read();
    }
    catch (IOException e) {}
    return val;
  }
 
  /**
   * Read a <code>String</code> value from standard input.   
   * @return the read value; - default return value is "", that is empty <code>String</code>.
   */ 
  public static String readString ()
  {    
    String val = "";
    
    try
    {
      val = cin.readLine();
    }
    catch (IOException e) {}
    return val;
  }
}
  
  
  