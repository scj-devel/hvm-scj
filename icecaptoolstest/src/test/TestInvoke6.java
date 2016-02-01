package test;

import vm.VMTest;

public class TestInvoke6 {

    private abstract static class SuperSuperClass {
        abstract int bar();
    }

    private abstract static class SuperClass extends SuperSuperClass {
        abstract int foo();
    }

    private static class SubClass extends SuperClass {
        @Override
        int foo() {
            return 42;
        }

        @Override
        int bar() {
            return 41;
        }
    }

    public static void main(String[] args) {
    	VMTest.markResult(test());
    }

	public static boolean test() {
		SuperClass instanse = new SubClass();
        if (instanse.foo() == 42) {
            if (instanse.bar() == 41) {
                return false;
            }
        }
        return true;
	}
}
