package devices;

public class System {
    public static void delay(int i) {
        while (i > 0)
        {
            i--;
        }
    }

    public static native void blink();

	public static native void snapshot();

	public static native void lockROM();

	public static native void resetMemory();
}
