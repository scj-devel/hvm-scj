package test;

public class TestIF_ACMPEQ {

    private static class FooClass {
        private int number;
        private FooClass reference;

        public FooClass(int number, FooClass reference) {
            super();
            this.number = number;
            this.reference = reference;
        }

        @SuppressWarnings("unused")
        public int getNumber() {
            return number;
        }

        @SuppressWarnings("unused")
        public void setNumber(int number) {
            this.number = number;
        }

        public FooClass getReference() {
            return reference;
        }

        public void setReference(FooClass reference) {
            this.reference = reference;
        }
    }

    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		FooClass myFoo = new FooClass(42, null);
        FooClass myOtherFoo = new FooClass(1337, myFoo);
        myFoo.setReference(myOtherFoo);

        if (myFoo != myOtherFoo) {
            if (myFoo.getReference() == myOtherFoo) {
                if (myOtherFoo.getReference() == myFoo) {
                    if (myFoo.getReference() == myFoo.getReference()) {
                        return null;
                    }
                }
            }
        }
        return args;
	}
}
