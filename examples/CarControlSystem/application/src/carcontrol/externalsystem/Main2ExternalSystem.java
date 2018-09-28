// *****************************************************************************
// File name  : Main2ExternalSystem.java
// Start date : 
// Programmer : Hans So.
// Java       : Java 
// Description: 
// *****************************************************************************

package carcontrol.externalsystem;

import javax.swing.border.*;

public class Main2ExternalSystem
{
  public static void main (String[] args)
  {
	//ExternalSystem es = new ExternalSystem();
	CommunicationService service = new CommunicationServiceImpl();
	
    int choice; 
    byte command;
    do
    {
      menu();
      choice = Cin.readInt();
      switch (choice)
      {
        case 1 :  // commands to car
          System.out.print("Type command: STOP(-1),START(0),PARK(1),REVERSE(2),DRIVE(3),ACCELERATE(4),BRAKE(5)");
          command = (byte)Cin.readInt();
          if (command >= -1 && command <= 5) {
        	  System.out.println("Send command to car: " + command);
        	  
        	  service.sendToCar(command);
          }
          else {
        	  System.out.println("Wrong command: " + command);
          }
          break;
        
        default :
          break;
      }
    }
    while (choice > 0);
    System.out.println("** External System End **");
  }
 
  private static void menu()
  {
    System.out.println("\n === Menu External System ===");
    System.out.println(" 1) Commands to car");
    System.out.println(" 0) Quit");
    System.out.print(" Type your choice: ");
  }
}
