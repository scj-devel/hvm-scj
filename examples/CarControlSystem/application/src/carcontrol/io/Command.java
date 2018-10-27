package carcontrol.io;


public enum Command {
	 OFF (-1),
	 
	 PARK (0), 
	 NEUTRAL (1), 
	 REVERSE (2), 
	 DRIVE (3),
	 
	 BRAKE (4),
	 ACC (5);
	
	private int value;
	
	private Command (int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Command getCommand (int x) {
		int cmd = 0;  // later: remove or rewrite this
		if (x != 0) {
			cmd = x - 48; // 48 is ASCII value for 0 (zero)
		}

		System.out.println("Command.getCommand: x: " + x + "; cmd: " + cmd);
		
		switch (cmd) {
			case -1: return OFF;
			
			case  0: return PARK;
			case  1: return NEUTRAL;
			case  2: return REVERSE;
			case  3: return DRIVE;
			
			case  4: return BRAKE;
			case  5: return ACC;
		}
		return null;
	}
}
