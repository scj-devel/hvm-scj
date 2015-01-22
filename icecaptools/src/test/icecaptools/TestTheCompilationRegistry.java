package test.icecaptools;

import org.junit.Assert;

import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.CompilationRegistry;
import junit.framework.TestCase;

public class TestTheCompilationRegistry extends TestCase {

    public void testSimple() {
        CompilationRegistry creg = new CompilationRegistry();

        MethodOrFieldDesc m1 = new MethodOrFieldDesc("class1", "method1", "sig1");
        MethodOrFieldDesc m2 = new MethodOrFieldDesc("class1", "method2", "sig2");
        MethodOrFieldDesc m3 = new MethodOrFieldDesc("class2", "method3", "sig3");
        MethodOrFieldDesc m4 = new MethodOrFieldDesc("class4", "method4", "sig4");
        MethodOrFieldDesc m5 = new MethodOrFieldDesc("class4", "method4", "sig4");

        creg.toggleMethodCompilation(m1);
        creg.toggleMethodCompilation(m2);
        creg.toggleMethodCompilation(m3);
        creg.toggleMethodCompilation(m4);
        creg.toggleMethodCompilation(m5);

        String encoded = creg.encodeToString();
        Assert.assertNotNull(encoded);

        creg.initializeFromString(encoded);

        Assert.assertTrue(creg.isClassCompiled("class1"));
        Assert.assertTrue(creg.isClassCompiled("class2"));
        Assert.assertFalse(creg.isClassCompiled("class4"));
        Assert.assertFalse(creg.isClassCompiled("class5"));
        Assert.assertTrue(creg.isMethodCompiled(m1));
        Assert.assertTrue(creg.isMethodCompiled(m2));
        Assert.assertTrue(creg.isMethodCompiled(m3));
        Assert.assertFalse(creg.isMethodCompiled(m4));
        Assert.assertFalse(creg.isMethodCompiled(m5));
    }

    public void testEmptyRegistry() {

        CompilationRegistry creg = new CompilationRegistry();

        MethodOrFieldDesc m1 = new MethodOrFieldDesc("class1", "method1", "sig1");

        creg.toggleMethodCompilation(m1);
        creg.toggleMethodCompilation(m1);
        
        String encoded = creg.encodeToString();
        Assert.assertEquals("0!", encoded);
        
        creg.initializeFromString(encoded);
        
        Assert.assertFalse(creg.isMethodCompiled(m1));
    }
}
