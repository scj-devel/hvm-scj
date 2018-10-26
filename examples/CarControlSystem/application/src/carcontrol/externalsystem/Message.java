package carcontrol.externalsystem;

public interface Message {
	
	byte STOP       = -1;
	byte START      = 0;
	byte PARK       = 1;
	byte REVERSE    = 2;
	byte DRIVE      = 3;
	byte ACCELERATE = 4;
	byte BRAKE      = 5;
	
	byte NEUTRAL    = 6;

}
