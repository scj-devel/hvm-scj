package carcontrol.io;


public enum Command {	 
	 PARK (0), 
	 NEUTRAL (1), 
	 REVERSE (2), 
	 DRIVE (3),
	
	 BRAKE (4),
	 ACC (5),
	 
	 OFF (6),
	
	 DONOTHING (9);
	
	private int value;
	
	private static Command newCmd = DONOTHING;
	
	private Command (int val) {
		value = val;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Command getCommand (int x) {
		int newValue = 0;  
		if (x >= 48 && x <= 54) {
			newValue = x - 48; // 48 is ASCII value for 0 (zero)
			switch (newValue) {				
				case  0: newCmd = PARK; break;
				case  1: newCmd = NEUTRAL; break;
				case  2: newCmd = REVERSE; break;
				case  3: newCmd = DRIVE; break;
				
				case  4: newCmd = BRAKE; break;
				case  5: newCmd = ACC; break;				

				case  6: newCmd = OFF; break;
			}				
		} 
		else {
			newCmd = DONOTHING;		
		}		
			
		System.out.println("Command.getCommand: x:" + x + 
		                   "; newCmd: " + newCmd + "; value: " + newCmd.getValue());
		
		return newCmd;
	}
}
