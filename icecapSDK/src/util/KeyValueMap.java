package util;

public class KeyValueMap {

	private String[] keys;
	private Object[] values;

	private byte top;

	public KeyValueMap() {
		keys = new String[10];
		values = new Object[10];
		top = 0;
	}

	public Object get(String key) {
		for (byte i = 0; i < top; i++) {
			if (keys[i].equals(key)) {
				return values[i];
			}
		}
		return null;
	}

	public void put(String key, Object value) {
		keys[top] = key;
		values[top++] = value;
	}

	public void remove(String key) {
		for (byte i = 0; i < top; i++) {
			if (keys[i].equals(key)) {
				keys[i] = keys[top - 1];
				values[i] = values[top - 1];
				top--;
			}
		}
	}
}
