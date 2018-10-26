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
		int cmd = 0;
		if (x != 0) {
			cmd = x - 48; // 48 is ASCII value for 0 (zero)
		}

		System.out.println("Mode.getMode: x: " + x + "; cmd: " + cmd);
		
		switch (cmd) {
			case -1: return OFF;
			case  0: return PARK;
			case  1: return NEUTRAL;
			case  2: return REVERSE;
			case  3: return DRIVE;
		}
		return null;
	}
}
