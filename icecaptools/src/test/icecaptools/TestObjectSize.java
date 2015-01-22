package test.icecaptools;

import icecaptools.DefaultObserver;
import icecaptools.FieldOffsetCalculator;
import icecaptools.compiler.FieldInfo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestObjectSize {

    @Test
    public void testSimple() throws Exception {
        List<String> classes = new LinkedList<String>();
        DefaultObserver observer = new DefaultObserver();
        String className = "test.icecapvm.minitests.TestVolatile2$VolatileObject";

        classes.add(className);

        FieldOffsetCalculator foCalc = new FieldOffsetCalculator();
        
        foCalc.calculate(classes.iterator(), observer);

        List<FieldInfo> objectFields = foCalc.getObjectFields(className);
        Assert.assertEquals(5, objectFields.size());
        
        Iterator<FieldInfo> fields = objectFields.iterator();
        while (fields.hasNext())
        {
            FieldInfo fieldInfo = fields.next();
            if (fieldInfo.getName().equals("x"))
            {
                Assert.assertEquals(0, fieldInfo.getOffset());
            }
            else if (fieldInfo.getName().equals("y"))
            {
                Assert.assertEquals(8, fieldInfo.getOffset());                
            }
            else if (fieldInfo.getName().equals("z"))
            {
            }
        }        
    }
}
