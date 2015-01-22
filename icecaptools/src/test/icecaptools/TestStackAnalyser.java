package test.icecaptools;

import icecaptools.IcecapClassPath;
import icecaptools.IcecapRepository;
import icecaptools.stackanalyser.StackReferencesAnalyser;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;
import org.junit.Assert;

public class TestStackAnalyser extends TestCase {

    public void testStackUsage1() {
        Method javaMethod;

        ClassPath classPath = new IcecapClassPath("/home/sek/workspace/icecaptools/bin");

        IcecapRepository repository = new IcecapRepository(SyntheticRepository.getInstance(classPath));

        Repository.setRepository(repository);

        Repository.clearCache();

        try {
            JavaClass clazz = Repository.lookupClass("test.icecaptools.minitests.TestStackUsage1");
            Method[] methods = clazz.getMethods();

            for (int index = 0; index < methods.length; index++) {
                Method method = methods[index];
                if (method.getName().equals("foo")) {
                    String signature = method.getSignature();
                    if (signature.equals("(Ljava/lang/String;I)Ljava/lang/String;")) {
                        javaMethod = method;
                        StackReferencesAnalyser analyser = new StackReferencesAnalyser(javaMethod, clazz);

                        try {
                            analyser.analyseStackUsage();
                        } catch (Exception e) {
                            Assert.fail();
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            Assert.fail();
        }
    }

    public void testStackUsage2() {
        Method javaMethod;

        ClassPath classPath = new IcecapClassPath("/home/sek/workspace/icecaptools/bin");

        IcecapRepository repository = new IcecapRepository(SyntheticRepository.getInstance(classPath));

        Repository.setRepository(repository);

        Repository.clearCache();

        try {
            JavaClass clazz = Repository.lookupClass("test.icecaptools.minitests.TestStackUsage2");
            Method[] methods = clazz.getMethods();

            for (int index = 0; index < methods.length; index++) {
                Method method = methods[index];
                if (method.getName().equals("main")) {
                    String signature = method.getSignature();
                    if (signature.equals("([Ljava/lang/String;)V")) {
                        javaMethod = method;
                        StackReferencesAnalyser analyser = new StackReferencesAnalyser(javaMethod, clazz);

                        try {
                            analyser.analyseStackUsage();
                        } catch (Exception e) {
                            Assert.fail();
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            Assert.fail();
        }
    }

    public void testAll() {
        File file = new File(".");
        String location = null;
        try {
            location = file.getCanonicalPath();
        } catch (IOException e) {
            Assert.fail();
        }
        String[] path1 = { "bin", "test", "icecaptools", "minitests" };
        testPathContent(location, path1, "test.icecaptools.minitests.");
        String[] path2 = { "bin", "test", "icecapvm", "minitests" };
        testPathContent(location, path2, "test.icecapvm.minitests.");
        Assert.assertNotNull(location);
    }

    private void testPathContent(String location, String[] path, String package_name) {
        for (int i = 0; i < path.length; i++) {
            location = location + File.separatorChar + path[i];
        }
        File dir = new File(location);

        String[] children = dir.list();

        for (int i = 0; i < children.length; i++) {
            String child = children[i];
            if (!child.equals("properties")) {
                StringTokenizer tokenizer = new StringTokenizer(child, ".");
                String className = tokenizer.nextToken();

                JavaClass clazz;
                try {
                    clazz = Repository.lookupClass(package_name + className);
                    Method[] methods = clazz.getMethods();
                    for (int index = 0; index < methods.length; index++) {
                        Method method = methods[index];

                        StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);

                        try {
                            analyser.analyseStackUsage();
                        } catch (Exception e) {
                            Assert.fail();
                        }
                    }
                } catch (ClassNotFoundException e) {
                    Assert.fail();
                }
            }
        }
    }
}
