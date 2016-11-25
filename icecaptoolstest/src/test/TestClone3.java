package test;

import vm.VMTest;

public class TestClone3 {

	private static class AClass implements Cloneable {
		byte x;
		short y;
		int z;
		long l;
		float f;
		double d;
		String s;
		AClass other;

		public AClass(byte x, short y, int z, long l, float f, double d, String s, AClass other) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.l = l;
			this.f = f;
			this.d = d;
			this.s = s;
			this.other = other;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}

	public static void main(String[] args) {
		boolean failed = true;
		AClass o0 = new AClass((byte) 12, (short) 16384, 0x7fffffff, 0x7fffffffffffffffL, 1.25f, 2.25d, "hello", null);
		o0.other = o0;

		try {
			AClass o1 = (AClass) o0.clone();
			if (o1 != o0) {
				if ((o0.x == o1.x) && (o0.y == o1.y) && (o0.z == o1.z)) {
					if ((o1.x == (byte) 12) && (o1.y == (short) 16384) && (o1.z == 0x7fffffff)) {
						if ((o0.l == o1.l) && (o0.f == o1.f) && (o0.d == o1.d)) {
							if ((o1.l == 0x7fffffffffffffffL) && (o1.f == 1.25f) && (o1.d == 2.25d)) {
								if (o0.s == o1.s) {
									if (o1.s.equals("hello")) {
										if (o1.other == o0) {
											failed = false;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (CloneNotSupportedException e) {
		}
		VMTest.markResult(failed);
	}
}
