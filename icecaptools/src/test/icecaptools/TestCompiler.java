package test.icecaptools;

import icecaptools.AnalysisObserver;
import icecaptools.CompilationSequence;
import icecaptools.DefaultObserver;
import icecaptools.compiler.DefaultIcecapCodeFormatter;
import icecaptools.compiler.DefaultIcecapProgressMonitor;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.compiler.IDGenerator;
import icecaptools.compiler.VirtualTable;
import icecaptools.conversion.ConversionConfiguration;
import icecaptools.conversion.Converter;

import java.io.File;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;

import org.junit.Assert;
import org.junit.Test;

public class TestCompiler {

    @Test
    public void testFullCompilation() {
        ConversionConfiguration config = new ConversionConfiguration();
        
        config.setClassPath("/home/skr/icecapvm/tools/bin");
        config.setInputPackage("test");
        config.setInputClass("TestI2B");

        CompilationSequence sequencer = new CompilationSequence();
        config.setCodeFormatter(new DefaultIcecapCodeFormatter());
        try {
            sequencer.startCompilation(System.out, new DefaultMethodObserver(), config, new DefaultIcecapProgressMonitor(), new DefaultCompilationRegistry(), "", true);
        } catch (Throwable e2) {
            Assert.fail();
        }

        File classesFile = new File("classes.c");
        Assert.assertTrue(classesFile.exists());
        Assert.assertTrue(classesFile.length() > 0);
        classesFile.delete();
        classesFile = null;
        
        
        File methodsFile = new File("methods.c");
        Assert.assertTrue(methodsFile.exists());
        Assert.assertTrue(methodsFile.length() > 0);
        methodsFile.delete();
        methodsFile = null;
    }

    @Test
    public void testVTable() throws Exception {
        ClassPath classPath = new ClassPath("/home/skr/icecapvm/tools/bin");

        SyntheticRepository repository = SyntheticRepository.getInstance(classPath);

        Repository.setRepository(repository);

        JavaClass clazz = Repository.lookupClass("test.icecaptools.minitests.Sub2");

        AnalysisObserver observer = new DefaultObserver();

        Converter converter = new Converter(System.out, new DefaultMethodObserver(), new DefaultCompilationRegistry(), false);
        converter.setObserver(observer);
        VirtualTable vtable = VirtualTable.createVTable(clazz.getClassName(), observer);

        System.out.println(vtable.toString());
    }
    
    @Test
    public void testMethoID()  {
        String className;
        String methodName; 
        String signature;
        IDGenerator idGen = new IDGenerator();
        String id1, id2;
        
        className = "java.util.HashMap";
        methodName = "<init>";
        signature = "()V";
        
        id1 = idGen.getUniqueId(className, methodName, signature);
        
        className = "java.util.HashMap";
        methodName = "init";
        signature = "()V";
        
        id2 = idGen.getUniqueId(className, methodName, signature);
        
        Assert.assertFalse(id1.equals(id2));
    }
}
