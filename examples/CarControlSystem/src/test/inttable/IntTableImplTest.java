// *****************************************************************************
// File name  : IntTableImplTest.java
// Start date : Feb 18, 2012
// Programmer : Hans So.
// Java       : Java 1.6.0 
// Description: 
// *****************************************************************************

package test.inttable;

import junit.framework.TestCase;

public class IntTableImplTest extends TestCase
{
  private IntTable intTable; 
  
  protected void setUp () throws Exception
  {
    super.setUp();
    intTable = new IntTable (5);
  }

  public void testIntTable ()
  {
    //assertEquals (expected, actual)
    assertEquals (5, intTable.length());    
  }
  
  public void testInsert ()
  {
    //fail("Not yet implemented");
  }

  public void testRemove ()
  {
    for (int i = 0; i < 5; i++)
      intTable.insert(10 * i);
    
    intTable.remove (10); // element 1
    
    //assertEquals (expected, actual)
    assertEquals (4, intTable.size());
    assertEquals (40, intTable.getValue(1));
    
    intTable.remove (30);  // the last element
    assertEquals (3, intTable.size());
    
    intTable.remove (22);  // not in table
    assertEquals (3, intTable.size());  
  }

  public void testGetValue ()
  {
    //fail("Not yet implemented");
  }
  
  public void testSize ()
  {
    assertEquals (0, intTable.size());
  }

  public void testLength ()
  {
    assertEquals (5, intTable.length());
  }

  public void testGetMax ()
  {
    //fail("Not yet implemented");
  }
}
