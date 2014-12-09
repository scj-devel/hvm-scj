package test.icecaptools;

import icecaptools.JavaArrayClass;
import icecaptools.NewList;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.conversion.Converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMiniTests {

    private Converter converter;
    private ConversionConfiguration config;
    
    @Before
    public void setUp() {
        try {
            converter = new Converter(System.out, new DefaultMethodObserver(), new DefaultCompilationRegistry(), false);
        } catch (ClassNotFoundException e) {
            Assert.fail();
        }
        config = new ConversionConfiguration();
        config.setClassPath("/home/skr/icecapvm/tools/bin");
        config.setInputPackage("test.icecaptools.minitests");        
    }

    @Test
    public void testTest1() {
        config.setInputClass("Test1");
        NewList newList = converter.startConversion(config);

        Assert.assertEquals("java/lang/IllegalMonitorStateException", newList.toString());
    }

    @Test
    public void testTest2() {

        config.setInputClass("Test2");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/Test2");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest3() {

        config.setInputClass("Test3");
        NewList newList = converter.startConversion(config);

        Assert.assertEquals("", newList.toString());
    }

    @Test
    public void testTest4() {

        config.setInputClass("Test4");
        NewList newList = converter.startConversion(config);
        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/A");
        expected.addElement("test/icecaptools/minitests/B");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest5() {
        config.setInputClass("Test5");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/C");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest6() {

        config.setInputClass("Test6");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/C");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest7() {

        config.setInputClass("Test7");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/A");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest8() {

        config.setInputClass("Test8");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/B");
        expected.addElement("test/icecaptools/minitests/C");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest9() {

        config.setInputClass("Test9");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/B");
        expected.addElement("test/icecaptools/minitests/E");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest10() {

        config.setInputClass("Test10");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ClassCastException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/C");
        expected.addElement("test/icecaptools/minitests/Sub1");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest11() {

        config.setInputClass("Test11");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/D");
        expected.addElement("test/icecaptools/minitests/Sub2");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest12() {

        config.setInputClass("Test12");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/D");
        expected.addElement("test/icecaptools/minitests/F");
        expected.addElement("test/icecaptools/minitests/Sub2");
        expected.addElement("test/icecaptools/minitests/Sub3");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest13() {

        config.setInputClass("Test13");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/C");
        expected.addElement("test/icecaptools/minitests/D");
        expected.addElement("test/icecaptools/minitests/Sub4");
        expected.addElement("test/icecaptools/minitests/Sub5");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest14() {

        config.setInputClass("Test14");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/StringBuffer");

        Assert.assertEquals(expected.toString(), newList.toString());
    }
    
    @Test
    public void testTest15() {

        config.setInputClass("Test15");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArithmeticException");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/ArrayStoreException");
        expected.addElement("java/lang/AssertionError");
        expected.addElement("java/lang/Character");
        expected.addElement("java/lang/CharacterData00");
        expected.addElement("java/lang/CharacterData01");
        expected.addElement("java/lang/CharacterData02");
        expected.addElement("java/lang/CharacterData0E");
        expected.addElement("java/lang/CharacterDataLatin1");
        expected.addElement("java/lang/CharacterDataPrivateUse");
        expected.addElement("java/lang/CharacterDataUndefined");
        expected.addElement("java/lang/Class");
        expected.addElement("java/lang/ClassCastException");
        expected.addElement("java/lang/IllegalArgumentException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/Integer");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/NumberFormatException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("java/lang/String$CaseInsensitiveComparator");
        expected.addElement("java/lang/StringBuffer");
        expected.addElement("java/lang/StringBuilder");
        expected.addElement("java/lang/StringIndexOutOfBoundsException");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest16() {

        Object[] array1 = new Object[2];

        Assert.assertTrue(JavaArrayClass.isArrayClass(array1.getClass().getName()));

        String elementType = JavaArrayClass.getElementType(array1.getClass().getName());

        Assert.assertTrue(JavaArrayClass.isReferenceClass(elementType));

        elementType = JavaArrayClass.getReferredType(elementType);

        Assert.assertEquals("java.lang.Object", elementType);

        Object[][] array2 = new Object[2][2];

        String className = array2.getClass().getName();

        while (JavaArrayClass.isArrayClass(className)) {
            className = JavaArrayClass.getElementType(className);
        }

        Assert.assertTrue(JavaArrayClass.isReferenceClass(className));

        className = JavaArrayClass.getReferredType(className);

        Assert.assertEquals("java.lang.Object", className);

        config.setInputClass("Test16");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Object");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("test/icecaptools/minitests/D");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest17() {

        config.setInputClass("Test17");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();
        expected.addElement("test/icecaptools/minitests/F");
        expected.addElement("test/icecaptools/minitests/C");
        expected.addElement("test/icecaptools/minitests/D");

        Assert.assertTrue(expected.lessThanOrEquals(newList));
    }

    @Test
    public void testTest18() {

        config.setInputClass("Test18");
        NewList newList = converter.startConversion(config);

        Assert.assertTrue(newList.size() > 0);

        NewList expected = new NewList();

        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("test/icecaptools/minitests/B");
        expected.addElement("test/icecaptools/minitests/E");
        expected.addElement("test/icecaptools/minitests/F");
        expected.addElement("test/icecaptools/minitests/Implementor1");
        expected.addElement("test/icecaptools/minitests/Implementor2");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest19() {

        config.setInputClass("Test19");
        NewList newList = converter.startConversion(config);

        Assert.assertTrue(newList.size() > 0);

        NewList expected = new NewList();

        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("test/icecaptools/minitests/C");
        expected.addElement("test/icecaptools/minitests/Sub1");
        expected.addElement("test/icecaptools/minitests/Sub2");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest20() {

        config.setInputClass("Test20");
        NewList newList = converter.startConversion(config);

        Assert.assertTrue(newList.size() > 0);

        NewList expected = new NewList();

        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("test/icecaptools/minitests/C");
        expected.addElement("test/icecaptools/minitests/Sub1");
        expected.addElement("test/icecaptools/minitests/Sub2");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest21() {

        config.setInputClass("Test21");
        NewList newList = converter.startConversion(config);

        Assert.assertTrue(newList.size() > 0);

        NewList expected = new NewList();
        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/Exception");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("test/icecaptools/minitests/C");
        expected.addElement("test/icecaptools/minitests/Sub1");
        expected.addElement("test/icecaptools/minitests/Sub2");

        Assert.assertEquals(expected.toString(), newList.toString());
    }

    @Test
    public void testTest25() {

        config.setInputClass("Test25");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Object");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ArithmeticException");
        expected.addElement("java/lang/ArrayIndexOutOfBoundsException");
        expected.addElement("java/lang/ArrayStoreException");
        expected.addElement("java/lang/Character");
        expected.addElement("java/lang/Class");
        expected.addElement("java/lang/ClassCastException");
        expected.addElement("java/lang/IllegalArgumentException");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/InternalError");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("java/lang/StringBuilder");
        expected.addElement("java/lang/StringIndexOutOfBoundsException");
        expected.addElement("java/util/LinkedList");
        expected.addElement("java/util/LinkedList$Entry");
        expected.addElement("java/util/NoSuchElementException");
        expected.addElement("java/util/StringTokenizer");

        String[] actual = newList.getElementsAsArray();
        String[] exp = expected.getElementsAsArray();
        
        Assert.assertEquals(actual.length, exp.length);
        
        for (int i = 0; i < exp.length; i++)
        {
            if (!actual[i].equals(exp[i]))
            {
                System.out.println("failed: [" + actual[i] + "]!=[" + exp[i] + "]");
            }
        }
    }

    @Test
    public void testTest26() {

        config.setInputClass("TestException");
        NewList newList = converter.startConversion(config);

        NewList expected = new NewList();

        expected.addElement("java.lang.Class");
        expected.addElement("java.lang.Throwable");
        expected.addElement("java/lang/ClassCastException");
        expected.addElement("java/lang/Exception");
        expected.addElement("java/lang/IllegalMonitorStateException");
        expected.addElement("java/lang/NegativeArraySizeException");
        expected.addElement("java/lang/NullPointerException");
        expected.addElement("java/lang/OutOfMemoryError");
        expected.addElement("java/lang/String");
        expected.addElement("test/icecaptools/minitests/Sub1");
        
        Assert.assertEquals(expected.toString(), newList.toString());
    }

    /*
     * public void testTest27() {
     * converter.setInputFolder("/home/skr/icecapvm/tools/bin");
     * converter.setInputPackage("java.lang");
     * converter.setInputClass("System");
     * converter.setEntryPointMethodName("initializeSystemClass");
     * converter.setEntryPointMethodSignature("()V");
     * 
     * converter.startConversion();
     * 
     * }
     */
}
