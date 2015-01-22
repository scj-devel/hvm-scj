package test.icecaptools;

import icecaptools.stackanalyser.AbstractStack;

import org.junit.Assert;
import org.junit.Test;

public class TestAbstractStack  {

    @Test
    public void testAbstractStack1() {
        AbstractStack stack = new AbstractStack();

        byte[] info = stack.getStackInfo();
        Assert.assertEquals(0, info.length);

        stack.pushRef();
        info = stack.getStackInfo();
        Assert.assertEquals(1, info.length);

        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushRef();

        info = stack.getStackInfo();
        Assert.assertEquals(1, info.length);

        stack.pushRef();

        info = stack.getStackInfo();
        Assert.assertEquals(2, info.length);
    }

    @Test
    public void testAbstractStack2() {
        AbstractStack stack = new AbstractStack();

        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushNonRef();
        stack.pushNonRef();
        stack.pushNonRef();
        stack.pushNonRef();

        byte[] info = stack.getStackInfo();

        Assert.assertEquals(1, info.length);

        Assert.assertEquals(0x0f, info[0]);

        stack = new AbstractStack();

        stack.pushRef();
        stack.pushNonRef();
        stack.pushRef();
        stack.pushNonRef();
        stack.pushRef();
        stack.pushNonRef();
        stack.pushRef();
        stack.pushNonRef();

        info = stack.getStackInfo();

        Assert.assertEquals(1, info.length);

        Assert.assertEquals(0x55, info[0]);

        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushRef();
        stack.pushNonRef();
        stack.pushNonRef();
        stack.pushNonRef();
        stack.pushNonRef();

        info = stack.getStackInfo();

        Assert.assertEquals(2, info.length);

        Assert.assertEquals(0x0f, info[1]);
    }
}
