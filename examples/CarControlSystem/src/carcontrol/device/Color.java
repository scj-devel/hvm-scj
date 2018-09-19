package carcontrol.device;

public class Color {
	
	private static final int red = 1;
	private static final int yellow = 2;
	private static final int green = 3;
	private static final int blue = 4;
	
    private int value;
	
	private Color(int value) {
		this.value = value;
	}
	
	
	public static final Color RED = new Color(red);
	public static final Color YELLOW = new Color(yellow);
	public static final Color GREEN = new Color(green);
	public static final Color BLUE = new Color(blue);
}
