package test.icecaptools;

import icecaptools.BNode;
import icecaptools.IcecapClassPath;
import icecaptools.IcecapRepository;
import icecaptools.LocalVariableUsageAnalyser;
import icecaptools.MethodEntryPoints;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.conversion.Converter;
import junit.framework.TestCase;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;
import org.junit.Assert;

public class TestLocalVariableUsageAnalyser extends TestCase {

    public void testSimple() {

        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestIf");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test1")) {
                    Converter converter = new Converter(System.out, new DefaultMethodObserver(), new DefaultCompilationRegistry(), false);

                    ClassPath classPath = new IcecapClassPath(".");

                    org.apache.bcel.util.Repository repository = new IcecapRepository(SyntheticRepository.getInstance(classPath));

                    Repository.setRepository(repository);

                    Repository.clearCache();

                    converter.setObserver(new TestConverter.TestObserver());
                    MethodEntryPoints entryPoints = converter.convertByteCode(null, clazz, method.getName(), method.getSignature(), false);
                    LocalVariableUsageAnalyser lvAnalyser = new LocalVariableUsageAnalyser(entryPoints);

                    lvAnalyser.analyse();

                    BNode astore_2 = entryPoints.getBNodeFromOriginalAddress(24);

                    Assert.assertTrue(astore_2.isRedundant());
                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 1);
    }
}
