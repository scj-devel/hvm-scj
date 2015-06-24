package test.icecaptools.compiler.utils;

import icecaptools.compiler.utils.CompareableStringBuffer;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class TestCompareableStringBuffer {

	@Test
    public void testCompareableStringBuffer() {
        CompareableStringBuffer buffer1 = new CompareableStringBuffer();
        buffer1.append("abba");
        
        CompareableStringBuffer buffer2 = new CompareableStringBuffer();
        buffer2.append("abba");
        
        HashSet<CompareableStringBuffer> names = new HashSet<CompareableStringBuffer>();
        names.add(buffer1);
        Assert.assertTrue(names.contains(buffer1));
        
        Assert.assertTrue(buffer1.equals(buffer2));
        
        Assert.assertTrue(names.contains(buffer2));
    }
}
