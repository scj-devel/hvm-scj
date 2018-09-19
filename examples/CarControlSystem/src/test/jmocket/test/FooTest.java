package test.jmocket.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class FooTest 
{
    @Test
    public void testToString(@Mocked Foo foo)
    {
        new Expectations() {{ 
        	foo.getBar(); result = 42; 
        }};

        Assert.assertEquals("Bar: 42", foo.toString());
    }
}