package test.icecaptools;

import org.junit.Assert;
import org.junit.Test;

import icecaptools.HVMLoadedProperties;
import icecaptools.HVMProperties;
import icecaptools.compiler.NoDuplicatesMemorySegment;
import icecaptools.compiler.aot.LevelNRegisterAllocator;
import icecaptools.compiler.aot.Size;

public class TestLevelNRegisterAllocator {

    @Test
    public void testLevel1RegisterAllocator() {

        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 1, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.BYTE, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.SHORT, "44");
        regAlloc.push(Size.INT, "45");
        regAlloc.push(Size.INT, "46");
        output.append("   x = " + regAlloc.peekTop(1, Size.INT) + ";\n");
        output.append("   x = " + regAlloc.peekTop(2, Size.INT) + ";\n");
        output.append("   x = " + regAlloc.peekTop(3, Size.SHORT) + ";\n");
        regAlloc.pop("   x", Size.INT);
        regAlloc.pop("   x", Size.INT);
        regAlloc.pop("   x", Size.SHORT);
        regAlloc.pop("   x", Size.BYTE);
        regAlloc.pop("   x", Size.BYTE);
        regAlloc.push(Size.INT, "46");
        regAlloc.pop("   x", Size.INT);
    }

    @Test
    public void testLevel1RegisterAllocatorIsCached() {

        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 5, 5, false, new DefaultSPManipulator());

        Assert.assertFalse(regAlloc.isCached(0));
        Assert.assertFalse(regAlloc.isCached(1));
        
        regAlloc.push(Size.BYTE, "42");
        
        Assert.assertTrue(regAlloc.isCached(0));
        Assert.assertFalse(regAlloc.isCached(1));
        
        regAlloc.push(Size.BYTE, "43");
        Assert.assertTrue(regAlloc.isCached(0));
        Assert.assertTrue(regAlloc.isCached(1));
        
        regAlloc.push(Size.SHORT, "44");
        regAlloc.push(Size.INT, "45");
        regAlloc.push(Size.INT, "46");

        Assert.assertTrue(regAlloc.isCached(0));
        Assert.assertTrue(regAlloc.isCached(1));

        regAlloc.pop("   x", Size.INT);
        regAlloc.pop("   x", Size.INT);
        regAlloc.pop("   x", Size.SHORT);
        
        Assert.assertTrue(regAlloc.isCached(0));
        Assert.assertTrue(regAlloc.isCached(1));

        regAlloc.pop("   x", Size.BYTE);
        Assert.assertTrue(regAlloc.isCached(0));
        Assert.assertFalse(regAlloc.isCached(1));

        regAlloc.pop("   x", Size.BYTE);
        Assert.assertFalse(regAlloc.isCached(0));
        Assert.assertFalse(regAlloc.isCached(1));
    }
    
    @Test
    public void testLevel1RegisterAllocatorFlush() {

        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 1, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.BYTE, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.SHORT, "44");
        regAlloc.push(Size.INT, "45");
        regAlloc.push(Size.INT, "46");
        regAlloc.flush(true);
    }

    @Test
    public void testLevel2RegisterAllocator1() {

        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 2, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.BYTE, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.SHORT, "44");
        regAlloc.pop("   x", Size.SHORT);
        regAlloc.pop("   x", Size.BYTE);
        regAlloc.pop("   x", Size.BYTE);
    }

    @Test
    public void testLevel2RegisterAllocator2() {

        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 2, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.INT, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.BYTE, "44");

    }

    @Test
    public void testLevel2RegisterAllocatorFlush() {

        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 2, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.BYTE, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.SHORT, "44");
        regAlloc.flush(true);
    }

    @Test
    public void testCopy() {
        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 3, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.BYTE, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.SHORT, "44");

        LevelNRegisterAllocator copy = (LevelNRegisterAllocator) regAlloc.copy();

        StringBuffer copyOutput = new StringBuffer();
        copy.setOutput(copyOutput);

        StringBuffer thisOutput = new StringBuffer();
        regAlloc.setOutput(thisOutput);

        copy.flush(true);
        regAlloc.flush(true);

        Assert.assertEquals(copyOutput.toString(), thisOutput.toString());
    }

    @Test
    public void testEquals() {
        StringBuffer output = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator regAlloc = new LevelNRegisterAllocator(output, localVars, 3, 1, false, new DefaultSPManipulator());

        regAlloc.push(Size.BYTE, "42");
        regAlloc.push(Size.BYTE, "43");
        regAlloc.push(Size.SHORT, "44");

        LevelNRegisterAllocator copy = (LevelNRegisterAllocator) regAlloc.copy();
        Assert.assertTrue(regAlloc.equals(copy));

        copy.push(Size.BYTE, "1");

        Assert.assertFalse(regAlloc.equals(copy));
    }

    @Test
    public void testMerge() {
        StringBuffer output1 = new StringBuffer();

        StringBuffer output2 = new StringBuffer();

        HVMProperties props = new HVMLoadedProperties(System.getProperties());

        NoDuplicatesMemorySegment localVars = new NoDuplicatesMemorySegment(props);

        LevelNRegisterAllocator stack1 = new LevelNRegisterAllocator(output1, localVars, 2, 1, false, new DefaultSPManipulator());

        LevelNRegisterAllocator stack2 = new LevelNRegisterAllocator(output2, localVars, 2, 1, false, new DefaultSPManipulator());

        stack1.push(Size.INT, "42");

        Assert.assertFalse(stack1.equals(stack2));
        try {
            stack1.merge(stack2);
            Assert.assertTrue(stack1.equals(stack2));
        } catch (Exception e) {
            Assert.fail();
        }

        stack2.push(Size.INT, "42");
        Assert.assertFalse(stack1.equals(stack2));

        try {
            stack1.merge(stack2);
            Assert.assertTrue(stack1.equals(stack2));
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
