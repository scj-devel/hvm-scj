//*****************************************************************************
//File name  : IntTable.java.
//Start date : 05-02-2012.
//Programmer : Hans So.
//Java       : Java 1.6
//Description: 
//
//*****************************************************************************
package test.inttable;

//import org.junit.Test;

public class IntTable
{
  int[] table;  // instantiated in a constructor etc.
  int index;    // index is the first free place in the table;
                // it also counts the number of integers in table
  
  public IntTable (int length)
  {
    this.table = new int[length];
    this.index = 0;
  }
  
  /**
   * The element is inserted on the first free place in the table
   * Precondition:  The table is not full
   * Postcondition: The element has been inserted and now the table is not empty
   * @param element The element to insert 
   */
  public void insert (int element)
  {
    table[index++] = element;    
  }
  
  /**
   * Other methods:
   * removeLast
   * remove (int value) // removes one element
   * removeAll (int value) // removes all elements
   * find
   * isEmpty
   * isFull
   * getMax
   * getMin
   * howMany (int value)
   * sort  // sorts the table in ascendent order
   */
  
  /**
   * Removes an element value in the table.
   * If value not found in the table, nothing is done
   * 
   * @param value The element to remove
   */
  public void remove (int value)
  {
    int idx = findIndex (value);  
    if (idx >= 0)
    {
      index--;
      table[idx] = table[index];
    }      
  }
  
  private int findIndex (int value)
  {
    for (int i = 0; i < size(); i++)
    {
      if (table[i] == value)  // found
        return i;
    }
    return -1;  // not found
  }
  
  public void removeAll (int value)
  {
    int idx = findIndex (value);

    while (idx >= 0)
    {
      // remove one:
      index--;
      table[idx] = table[index];
      
      // find next:
      idx =  findIndex (value);
    }      
  }
  
  public int getValue (int idx)
  {
    return table[idx];
  }
  
  public int size ()
  {
    return index;
  }
  
  public int length()
  {
    return 2; //table.length;
  }
  
//  public int getMax()
//  {
//    return IntArray.maxOfArray(table, size());
//  }
//  
//  public void sort()
//  {
//    IntArray.selectionSort (table, size());
//    //Arrays.sort(table, 0, size()-1);
//  }
  
  public void print()
  {
    System.out.print("[ ");
    for (int i = 0; i < size(); i++)
      System.out.print(table[i] + " ");
    System.out.println("]");
  }
}
