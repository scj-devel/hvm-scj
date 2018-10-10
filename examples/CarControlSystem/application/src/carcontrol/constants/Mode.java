package carcontrol.constants;


public enum Mode {
	 OFF (-1),
	 PARKING (0), 
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
		switch (x) {
			case -1: return OFF;
			case  0: return PARKING;
			case  1: return NEUTRAL;
			case  2: return REVERSE;
			case  3: return DRIVE;
		}
		return null;
	}
}
