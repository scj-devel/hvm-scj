package test.icecaptools;

import util.ReferenceOffsets;
import org.junit.Assert;
import org.junit.Test;

public class TestPackedArray {

    @Test
    public void testSimple() throws Exception {
        ReferenceOffsets a = new ReferenceOffsets();

        Assert.assertTrue(a.size() == 0);

        try {
            a.insert((short) -1);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
        }

        try {
            a.insert((short) -34);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
        }

        a.insert((short) 63);

        Assert.assertTrue(a.size() == 1);

        a.insert((short) 128);

        a.insert((short) 1000);

        Assert.assertTrue(a.size() == 3);

        a.insert((short) 1001);

        a.insert((short) 1002);

        a.insert((short) 1003);
        
        a.insert((short) 255);        

        a.insert((short) 1004);

        a.insert((short) 1005);

        a.insert((short) 1006);

        a.insert((short) 1007);

        Assert.assertEquals(a.size(), 11);
        
        Assert.assertEquals(3, a.byteOffsetsSize());
        
        Assert.assertEquals(8, a.shortOffsetsSize());
        
        a.startScanByteOffsets();
        
        short next;
        next = a.getNextByteOffset();
        Assert.assertEquals(63, next);
        
        next = a.getNextByteOffset();
        Assert.assertEquals(128, next);
        
        next = a.getNextByteOffset();
        Assert.assertEquals(255, next);
        
        a.startScanShortOffsets();
        
        next = a.getNextShortOffset();
        Assert.assertEquals(1000, next);

        next = a.getNextShortOffset();
        Assert.assertEquals(1001, next);
        
        next = a.getNextShortOffset();
        Assert.assertEquals(1002, next);
        
        next = a.getNextShortOffset();
        Assert.assertEquals(1003, next);

        next = a.getNextShortOffset();
        Assert.assertEquals(1004, next);

        next = a.getNextShortOffset();
        Assert.assertEquals(1005, next);

        next = a.getNextShortOffset();
        Assert.assertEquals(1006, next);

        next = a.getNextShortOffset();
        Assert.assertEquals(1007, next);
    }
}
