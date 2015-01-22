package test;

public class TestNullPointerException {

    private static class Super {
        @SuppressWarnings("unused")
        public int x;
    }

    private interface anInterface {
        int foo();
    }

    @SuppressWarnings("unused")
    private static class anImplementor implements anInterface {
        @Override
        public int foo() {
            return 0;
        }
    }

    public static void main(String[] args) {
        args = test(args);
    }

	public static String[] test(String[] args) {
		StringBuffer buffer = getStringBuffer();
        try {
            if (buffer.length() > 0) {
                return args;
            } else {
                return args;
            }
        } catch (NullPointerException ne) {
            Super a = getSuper();
            try {
                a.x = 42;
            } catch (NullPointerException ne1) {
                int[] array = getArray();

                try {
                    if (array.length == 0) {
                        return args;
                    } else {
                        return args;
                    }
                } catch (NullPointerException ne2) {
                    try {
                        if (array[0] == 42) {
                            return args;
                        } else {
                            return args;
                        }
                    } catch (NullPointerException ne3) {
                        try {
                            array = array.clone();
                        } catch (NullPointerException ne4) {
                            try {
                                array[0] = 10;
                            } catch (NullPointerException ne5) {
                                try {
                                    anInterface in = getAnImplementor();
                                    in.foo();
                                } catch (NullPointerException ne6) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return args;
	}

    private static anInterface getAnImplementor() {
        return null;
    }

    private static int[] getArray() {
        return null;
    }

    private static Super getSuper() {
        return null;
    }

    private static StringBuffer getStringBuffer() {
        return null;
    }

}
