package test;

public class TestConstructorTypes {

	private static class Constructor {
		private boolean boolVal;
		private byte bVal;
		private int iVal;
		private short sVal;
		private long lVal;

		public Constructor() {

		}

		public Constructor(boolean val) {
			boolVal = val;
		}

		public Constructor(byte val) {
			bVal = val;
		}

		public Constructor(short val) {
			sVal = val;
		}

		public Constructor(int val) {
			iVal = val;
		}

		public Constructor(long val) {
			lVal = val;
		}

		public boolean isBoolVal() {
			return boolVal;
		}

		@SuppressWarnings("unused")
		public void setBoolVal(boolean boolVal) {
			this.boolVal = boolVal;
		}

		public byte getbVal() {
			return bVal;
		}

		@SuppressWarnings("unused")
		public void setbVal(byte bVal) {
			this.bVal = bVal;
		}

		public int getiVal() {
			return iVal;
		}

		@SuppressWarnings("unused")
		public void setiVal(int iVal) {
			this.iVal = iVal;
		}

		public short getsVal() {
			return sVal;
		}

		@SuppressWarnings("unused")
		public void setsVal(short sVal) {
			this.sVal = sVal;
		}

		public long getlVal() {
			return lVal;
		}

		@SuppressWarnings("unused")
		public void setlVal(long lVal) {
			this.lVal = lVal;
		}

	}

	public static void main(String[] args) {
		args = test(args);
	}

	@SuppressWarnings("unused")
	public static String[] test(String[] args) {
		Constructor emptyConstructor = new Constructor();
		Constructor boolConstructor = new Constructor(true);
		Constructor byteConstructor = new Constructor((byte) 255);
		Constructor shortConstructor = new Constructor((short) 65535);
		Constructor intConstructor = new Constructor((int) 12345678);
		Constructor longConstructor = new Constructor((long) 0x1122334455667788L);

		if (boolConstructor.isBoolVal()) {
			if (byteConstructor.getbVal() == (byte) 255) {
				if (shortConstructor.getsVal() == (short) 65535) {
					if (intConstructor.getiVal() == (int) 12345678) {
						if (longConstructor.getlVal() == (long) 0x1122334455667788L) {
							return null;
						}
					}
				}
			}
		}
		return args;
	}

}
