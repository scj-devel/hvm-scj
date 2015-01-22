package test;

public class TestMiniTests {
	
	public static void main(String[] args) throws Exception {
		String[] failed = { "failed" };
		failed = check(TestAload.test(failed));
		failed = check(TestCircularBuffer.test(failed));
		failed = check(TestArrayList.test(failed));
		failed = check(TestAstore.test(failed));
		failed = check(TestCheckCast1.test(failed));
		failed = check(TestCheckCast2.test(failed));
		failed = check(TestCheckCast3.test(failed));		
		failed = check(TestClassClass.test(failed));
		failed = check(TestClone1.test(failed));
		failed = check(TestConstructorTypes.test(failed));
		failed = check(TestDoWhile.test(failed));
		failed = check(TestException1.test(failed));
		failed = check(TestException2.test(failed));
		failed = check(TestException3.test(failed));
		failed = check(TestFor1.test(failed));
		failed = check(TestFor2.test(failed));
		failed = check(TestFor3.test(failed));
		failed = check(TestIDIV1.test(failed));
		failed = check(TestIF_ACMPEQ.test(failed));
		failed = check(TestIf1.test(failed));
		failed = check(TestIf2.test(failed));
		failed = check(TestIf3.test(failed));  
		failed = check(TestIf4.test(failed));
		failed = check(TestInnerClasses.test(failed)); 
		failed = check(TestInstanceof.test(failed));
		failed = check(TestInterface1.test(failed));
		failed = check(TestInvoke1.test(failed));  
		failed = check(TestInvoke2.test(failed)); 
		failed = check(TestInvoke3.test(failed));
		failed = check(TestInvoke4.test(failed));
		failed = check(TestInvoke5.test(failed));
		failed = check(TestInvoke6.test(failed)); 
		failed = check(TestInvokeStatic.test(failed)); 
		failed = check(TestLDC.test(failed));   
		failed = check(TestLong1.test(failed));
		failed = check(TestLong2.test(failed)); 
		failed = check(TestMul1.test(failed));
		failed = check(TestNewArray.test(failed));
		failed = check(TestNumberTypes.test(failed)); 
		failed = check(TestObjectToString.test(failed)); 
        failed = check(TestPrimitiveClass.test(failed));
        failed = check(TestProgram1.test(failed));
        failed = check(TestPutGetField.test(failed));
        failed = check(TestSimple.test(failed));
        failed = check(TestStaticField.test(failed));  
        failed = check(TestStaticFieldTypes.test(failed));
        failed = check(TestStaticInitializers.test(failed));
        failed = check(TestStaticInitializers1.test(failed));
        failed = check(TestString1.test(failed));
        failed = check(TestStringBuffer1.test(failed));
        /* failed = check(TestStringBuffer3.test(failed)); */
        failed = check(TestTableSwitch.test(failed));
        failed = check(TestWhile1.test(failed)); 

		// --- failed = check(TestStringBuffer2.test(failed));
		// --- failed = check(TestNullPointerException.test(failed)); 
		
		args = null;
	}

	private static String[] check(String[] failed) throws Exception {
		if (failed != null)
		{
			throw new Exception("failed");
		}
		devices.System.resetMemory();
		
		return new String[1];
	}
}
