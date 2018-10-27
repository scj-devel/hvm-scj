package carcontrol.constants;


public enum Mode {
	 OFF (-1),
	 PARK (0), 
	 NEUTRAL (1), 
	 REVERSE (2), 
	 DRIVE (3);	
	
	private int value;
	
	private Mode (int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Mode getMode (int x) {
		
		System.out.println("Mode.getMode: x: " + x);
		
		switch (x) {
			case -1: return OFF;
			case  0: return PARK;
			case  1: return NEUTRAL;
			case  2: return REVERSE;
			case  3: return DRIVE;
		}
		return null;
	}	
	
//	public static Mode CmdToMode (Command cmd) {		
//		switch (cmd) {
//			case OFF:     return Mode.OFF;
//			case PARK:    return Mode.PARK;
//			case NEUTRAL: return Mode.NEUTRAL;
//			case REVERSE: return Mode.REVERSE;
//			case DRIVE:   return Mode.DRIVE;
//		}
//		return null;
//	
//		
//	}
}
