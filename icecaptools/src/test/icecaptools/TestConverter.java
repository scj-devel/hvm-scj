package test.icecaptools;

import icecaptools.AnalysisObserver;
import icecaptools.DefaultObserver;
import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.DefaultMethodObserver;
import icecaptools.compiler.VirtualTable;
import icecaptools.conversion.Converter;
import junit.framework.TestCase;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;

public class TestConverter extends TestCase {

    public static class TestObserver extends  DefaultObserver {
        @Override
        public boolean isMethodUsed(String className, String targetMethodName, String targetMethodSignature) {
            /*if ("test.icecaptools.minitests.Super".equals(className)) {
                if ("foo".equals(targetMethodName)) {
                    return false;
                }
            }*/
            return true;
        }
    }

    public void testCreateVTable() throws Exception {
        Converter converter = new Converter(System.out, new DefaultMethodObserver(), new DefaultCompilationRegistry(), false);
        ClassPath classPath = new ClassPath("C:\\home\\workspace\\icecaptools\\bin");

        SyntheticRepository repository = SyntheticRepository.getInstance(classPath);

        Repository.setRepository(repository);

        AnalysisObserver observer = new TestObserver();

        converter.setObserver(observer);

        VirtualTable vtableSub2 = VirtualTable.createVTable("test.icecaptools.minitests.Sub2", observer);

        VirtualTable vtableSub6 = VirtualTable.createVTable("test.icecaptools.minitests.Sub6", observer);

        MethodOrFieldDesc methodDesc = new MethodOrFieldDesc("test.icecaptools.minitests.Sub2", "foo", "()V");

        int index1 = vtableSub2.getIndexOf(methodDesc);

        methodDesc = new MethodOrFieldDesc("test.icecaptools.minitests.Sub6", "foo", "()V");

        int index2 = vtableSub6.getIndexOf(methodDesc);

        assertEquals(index1, index2);
    }

    public void testBug1() {
        ClassPath classPath = new ClassPath("C:\\home\\workspace\\icecaptools\\bin");

        SyntheticRepository repository = SyntheticRepository.getInstance(classPath);

        Repository.setRepository(repository);

        try {
            JavaClass clazz = Repository.lookupClass("java.net.InetAddress");
            AnalysisObserver observer = new TestObserver();
            Converter converter = new Converter(System.out, new DefaultMethodObserver(), new DefaultCompilationRegistry(), false);
            converter.setObserver(observer);
            converter.convertByteCode(null, clazz, "<clinit>", "()V", true);
        } catch (ClassNotFoundException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }

    public void testBug2() {
        // java.util.ResourceBundle: getClassContext
        ClassPath classPath = new ClassPath("C:\\home\\workspace\\icecaptools\\bin");

        SyntheticRepository repository = SyntheticRepository.getInstance(classPath);

        Repository.setRepository(repository);

        JavaClass clazz;
        try {
            clazz = Repository.lookupClass("java.util.ResourceBundle");
            AnalysisObserver observer = new TestObserver();
            Converter converter = new Converter(System.out, new DefaultMethodObserver(), new DefaultCompilationRegistry(), false);
            converter.setObserver(observer);
            converter.convertByteCode(null, clazz, "getClassContext", "()[Ljava/lang/Class;", true);
        } catch (ClassNotFoundException e) {
            fail();
        } catch (Exception e) {
            fail();
        }

    }
}
