// *****************************************************************************
// File name  : Main2OuterSystem.java
// Start date : 
// Programmer : Hans So.
// Java       : Java 
// Description: 
// *****************************************************************************

package via.embedded.carcontrol.outersystem;


public class Main2OuterSystem
{
  public static void main (String[] args)
  {
    int choice; 
    int command;
    do
    {
      menu();
      choice = Cin.readInt();
      switch (choice)
      {
        case 1 :  // start car
          System.out.print("Start car; type 0");
          command = Cin.readInt();
          if (command == 0) {
        	  System.out.println("Send command to car: " + command); 
          }
          else {
        	  System.out.println("Wrong command from Start: " + command);
          }
          break;
        case 2 :  // parking
          System.out.print("Parking: type 1 (neutral) or -1 (key off)" );
          command = Cin.readInt();
          if (command == 1 || command == -1) {
        	  System.out.println("Send command to car: " + command); 
          }
          else {
        	  System.out.println("Wrong command from Parking: " + command);
          }          
          break;
        default :
          break;
      }
    }
    while (choice > 0);
    System.out.println("** Outer System End **");
  }
 
  private static void menu()
  {
    System.out.println("\n === Menu Outer System ===");
    System.out.println(" 1) Start car");
    System.out.println(" 2) Parking -> neutral or key off");
    System.out.println(" 0) Quit");
    System.out.print(" Type your choice: ");
  }
}
