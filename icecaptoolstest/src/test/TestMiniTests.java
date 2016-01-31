package test;

public class TestMiniTests {

	public static void main(String[] args) throws Exception {
		String[] failed = { "failed" };
		failed = check(TestAload.test());
		failed = check(TestCircularBuffer.test());
		failed = check(TestArrayList.test());
		failed = check(TestAstore.test());
		failed = check(TestCheckCast1.test());
		failed = check(TestCheckCast2.test());
		failed = check(TestCheckCast3.test());
		failed = check(TestClassClass.test());
		failed = check(TestClone1.test());
		failed = check(TestConstructorTypes.test());
		failed = check(TestDoWhile.test());
		failed = check(TestException1.test());
		failed = check(TestException2.test());
		failed = check(TestException3.test());
		failed = check(TestFor1.test());
		failed = check(TestFor2.test());
		failed = check(TestFor3.test());
		failed = check(TestIDIV1.test());
		failed = check(TestIF_ACMPEQ.test());
		failed = check(TestIf1.test());
		failed = check(TestIf2.test());
		failed = check(TestIf3.test());
		failed = check(TestIf4.test());
		failed = check(TestInnerClasses.test());
		failed = check(TestInstanceof.test());
		failed = check(TestInterface1.test());
		failed = check(TestInvoke1.test());
		failed = check(TestInvoke2.test());
		failed = check(TestInvoke3.test());
		failed = check(TestInvoke4.test());
		failed = check(TestInvoke5.test());
		failed = check(TestInvoke6.test());
		failed = check(TestInvokeStatic.test());
		failed = check(TestLDC.test());
		failed = check(TestLong1.test());
		failed = check(TestLong2.test());
		failed = check(TestMul1.test());
		failed = check(TestNewArray.test());
		failed = check(TestNumberTypes.test());
		failed = check(TestObjectToString.test());
		failed = check(TestPrimitiveClass.test());
		failed = check(TestProgram1.test());
		failed = check(TestPutGetField.test());
		failed = check(TestSimple.test(failed));
		failed = check(TestStaticField.test());
		failed = check(TestStaticFieldTypes.test());
		failed = check(TestStaticInitializers.test());
		failed = check(TestStaticInitializers1.test());
		failed = check(TestString1.test());
		failed = check(TestStringBuffer1.test());
		/* failed = check(TestStringBuffer3.test(failed)); */
		failed = check(TestTableSwitch.test());
		failed = check(TestWhile1.test());

		// --- failed = check(TestStringBuffer2.test(failed));
		// --- failed = check(TestNullPointerException.test(failed)); 

		args = null;
	}

	private static String[] check(boolean failed) throws Exception {
		if (failed) {
			throw new Exception("failed");
		}
		devices.System.resetMemory();

		return new String[1];
	}

	private static String[] check(String[] failed) throws Exception {
		if (failed != null) {
			throw new Exception("failed");
		}
		devices.System.resetMemory();

		return new String[1];
	}
}
