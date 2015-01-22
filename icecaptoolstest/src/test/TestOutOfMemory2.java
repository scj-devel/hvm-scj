package test;

public class TestOutOfMemory2 {

    public static void main(String[] args) {
        args = test(args);
    }

    @SuppressWarnings("unused")
	public static String[] test(String[] args) {
		try {
            int i = 0;
            while (true) {
                int[] array = new int[i];
                i++;
            }
        } catch (OutOfMemoryError error) {
            return null;
        }
	}
}
