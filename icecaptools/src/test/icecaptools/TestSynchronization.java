package test.icecaptools;

import icecaptools.BNode;
import icecaptools.CompilationSequence;
import icecaptools.FieldOffsetCalculator;
import icecaptools.MethodEntryPoints;
import icecaptools.RawByteCodes;
import icecaptools.compiler.DefaultIcecapCodeFormatter;
import icecaptools.compiler.DefaultIcecapProgressMonitor;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.compiler.OffsetPair;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.conversion.DependencyExtent;
import icecaptools.stackanalyser.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

import org.junit.Assert;
import org.junit.Test;

public class TestSynchronization  {

    @Test
    public void testMonitorEnterAnnotation() {

        ConversionConfiguration config = new ConversionConfiguration();
        
        config.setClassPath("/home/sek/workspace/icecaptools/bin");
        config.setInputPackage("test.icecapvm.minitests");
        config.setInputClass("TestThread1");

        CompilationSequence sequencer = new CompilationSequence();
        config.setCodeFormatter(new DefaultIcecapCodeFormatter());
        try {
            sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(), new DefaultCompilationRegistry(), "", false);
        } catch (Throwable e2) {
            Assert.fail();
        }

        DependencyExtent extent = sequencer.getDependencyExtent();
        
        MethodEntryPoints runMethod = extent.getMethod("test.icecapvm.minitests.TestThread1$Counter", "run", "()V");
        Assert.assertNotNull(runMethod);
        
        BNode monitorEnter = runMethod.getBNodeFromOriginalAddress(19);
        
        Assert.assertNotNull(monitorEnter);
        
        Assert.assertEquals(RawByteCodes.monitorenter_opcode, monitorEnter.getOpCode());

        try {
            ArrayList<String> types;
            types = Util.getCellType(runMethod, monitorEnter.getAinfo().entryStack.peek());
            Assert.assertNotNull(types);
            Assert.assertEquals(1, types.size());
            String producerType = types.get(0);
            Assert.assertEquals("java.lang.String", producerType);
        } catch (Exception e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testSizeCalcultion() {

        ConversionConfiguration config = new ConversionConfiguration();
        
        config.setClassPath("/home/sek/workspace/icecaptools/bin");
        config.setInputPackage("test.icecapvm.minitests");
        config.setInputClass("TestThread");

        CompilationSequence sequencer = new CompilationSequence();
        config.setCodeFormatter(new DefaultIcecapCodeFormatter());
        try {
            sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(), new DefaultCompilationRegistry(), "", false);
        } catch (Throwable e2) {
            Assert.fail();
        }

        List<String> classes = new LinkedList<String>();
        String className = "java.lang.String";

        classes.add(className);
        
        FieldOffsetCalculator foCalc = new FieldOffsetCalculator();
        
        try {
            foCalc.calculate(classes.iterator(), sequencer.getObserver());
        } catch (Exception e) {
           Assert.fail();
        }

        HashMap<JavaClass, OffsetPair> sizes = foCalc.getObjectSizes();
        
        try {
            JavaClass stringClass = Repository.lookupClass("java.lang.String");
            @SuppressWarnings("unused")
            OffsetPair offsetPair = sizes.get(stringClass);
            offsetPair = null;
        } catch (ClassNotFoundException e) {
            Assert.fail();
        }
    }
}
