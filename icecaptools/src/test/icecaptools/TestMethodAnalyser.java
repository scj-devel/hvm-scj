package test.icecaptools;

import icecaptools.ClassManager;
import icecaptools.RawByteCodes;
import icecaptools.RawByteCodes.RawBytecode;
import icecaptools.RawByteCodes.Raw_aaload;
import icecaptools.stackanalyser.AbstractStack;
import icecaptools.stackanalyser.AbstractStack.StackCell;
import icecaptools.stackanalyser.ArrayRefType;
import icecaptools.stackanalyser.StackReferencesAnalyser;
import icecaptools.stackanalyser.NonRefType;
import icecaptools.stackanalyser.RefType;
import icecaptools.stackanalyser.RefType.RefState;
import icecaptools.stackanalyser.UnknownType;

import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.Assert;

public class TestMethodAnalyser extends TestCase {

    public static class TestManager implements ClassManager {

        private void unimplemented() {
            System.out.println("unimplemented");
        }

        @Override
        public boolean skipMethodHack(String clazz, String targetMethodName, String targetMethodSignature) {
            unimplemented();
            return false;
        }
    }

    public void testMergeStackCell() {
        StackCell src = new StackCell();
        Assert.assertNotNull(src.content);
        Assert.assertTrue(src.content instanceof UnknownType);

        StackCell other = new StackCell();
        other.content = new RefType();

        try {
            src.merge(other);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertFalse(other.content == src.content);
        Assert.assertTrue(src.content instanceof RefType);

        src = new StackCell();
        Assert.assertNotNull(src.content);
        Assert.assertTrue(src.content instanceof UnknownType);

        other = new StackCell();
        other.content = new NonRefType();

        try {
            src.merge(other);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertFalse(other.content == src.content);
        Assert.assertTrue(src.content instanceof NonRefType);

        src = new StackCell();
        Assert.assertNotNull(src.content);
        Assert.assertTrue(src.content instanceof UnknownType);

        other = new StackCell();
        Raw_aaload bcode = new RawByteCodes.Raw_aaload();
        other.content = new ArrayRefType(bcode);

        try {
            src.merge(other);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertFalse(other.content == src.content);
        Assert.assertTrue(src.content instanceof ArrayRefType);

        // all other merge scenarios need to be covered
        src = new StackCell();
        RefType srcContent = new RefType();
        src.content = srcContent;

        other = new StackCell();

        try {
            src.merge(other);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(src.content == srcContent);

        src = new StackCell();
        srcContent = new RefType();
        src.content = srcContent;

        other = new StackCell();
        other.content = new RefType();

        try {
            src.merge(other);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(src.content == srcContent);

        src = new StackCell();
        src.content = new RefType();

        other = new StackCell();
        other.content = new ArrayRefType(bcode);

        try {
            src.merge(other);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(src.content != other.content);
        Assert.assertTrue(src.content instanceof ArrayRefType);

        src = new StackCell();
        src.content = new RefType();

        other = new StackCell();
        other.content = new NonRefType();

        try {
            src.merge(other);
            Assert.fail();
        } catch (Exception e) {

        }

        try {
            other.merge(src);
            Assert.fail();
        } catch (Exception e) {

        }

        src = new StackCell();
        src.content = new ArrayRefType(bcode);

        other = new StackCell();
        other.content = new NonRefType();

        try {
            src.merge(other);
            Assert.fail();
        } catch (Exception e) {
        }

        try {
            other.merge(src);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    public void testCopyStackCell() {
        StackCell src = new StackCell();
        Assert.assertNotNull(src.content);
        Assert.assertTrue(src.content instanceof UnknownType);

        StackCell copy = src.copy();
        Assert.assertFalse(copy == src);
        Assert.assertFalse(copy.content == src.content);

        Assert.assertTrue(copy.content instanceof UnknownType);

        src = new StackCell();
        src.content = new RefType();
        copy = src.copy();

        Assert.assertFalse(copy.content == src.content);

        Assert.assertTrue(copy.content instanceof RefType);

        Assert.assertTrue(((RefType) src.content).getState() == RefState.UNKNOWN);
        Assert.assertTrue(((RefType) copy.content).getState() == RefState.UNKNOWN);

        ((RefType) src.content).setState(RefState.NONNULL);

        copy = src.copy();
        Assert.assertTrue(((RefType) copy.content).getState() == RefState.NONNULL);

        ((RefType) src.content).setState(RefState.NULL);

        copy = src.copy();
        Assert.assertTrue(((RefType) copy.content).getState() == RefState.NULL);

        src = new StackCell();
        src.content = new NonRefType();
        copy = src.copy();
        Assert.assertTrue(copy.content instanceof NonRefType);

        src = new StackCell();
        copy = src.copy();
        Assert.assertTrue(copy.content instanceof UnknownType);

        src = new StackCell();
        Raw_aaload bcode = new RawByteCodes.Raw_aaload();
        src.content = new ArrayRefType(bcode);
        copy = src.copy();
        Assert.assertTrue(copy.content instanceof ArrayRefType);

        Iterator<RawBytecode> pushers = ((ArrayRefType) copy.content).getPushers();
        int count = 0;
        while (pushers.hasNext()) {
            Assert.assertTrue(pushers.next() == bcode);
            count++;
        }
        Assert.assertTrue(count == 1);
    }

    // ANTTestInvokeSpecial
    public void testReferenceAnalyserInvokeSpecial() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestInvokeSpecial");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test1")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt4 = analyser.getStackLayout(4);

                    StackCell top = stackAt4.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    didIt++;
                }
                if (method.getName().equals("test2")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt7 = analyser.getStackLayout(7);

                    StackCell top = stackAt7.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt8 = analyser.getStackLayout(8);

                    top = stackAt8.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt11 = analyser.getStackLayout(11);

                    top = stackAt11.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt16 = analyser.getStackLayout(16);

                    top = stackAt16.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NULL);

                    AbstractStack stackAt17 = analyser.getStackLayout(17);

                    top = stackAt17.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.UNKNOWN);

                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 2);
    }

    public void testReferenceAnalyserArrayAccess1() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.TestProgram1");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("quicksort")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt24 = analyser.getStackLayout(24);

                    StackCell top = stackAt24.getAt(stackAt24.getSize() - 2);
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 1);
    }
    
    public void testReferenceAnalyserArrayAccess2() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestArray1");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("testArrayAccess")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt5 = analyser.getStackLayout(5);

                    StackCell top = stackAt5.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt10 = analyser.getStackLayout(10);

                    top = stackAt10.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);
                    
                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 1);
    }

    
    public void testReferenceAnalyserArraySimple() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestArraySimple");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt21 = analyser.getStackLayout(21);

                    StackCell top = stackAt21.peek();
                    Assert.assertTrue(top.content instanceof ArrayRefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt11 = analyser.getStackLayout(11);

                    top = stackAt11.peek();
                    Assert.assertTrue(top.content instanceof ArrayRefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 1);
    }

    public void testReferenceAnalyserArraySimple1() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("java.lang.IllegalArgumentException");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("<init>")) {
                    
                    String signature = method.getSignature();

                    if ("(Ljava/lang/String;)V".equals(signature))
                    {
                        StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                        analyser.analyseStackUsage();

                        AbstractStack stackAt1 = analyser.getStackLayout(1);

                        StackCell top = stackAt1.peek();
                        Assert.assertTrue(top.content instanceof RefType);
                        Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);
                     
                        AbstractStack stackAt2 = analyser.getStackLayout(2);

                        top = stackAt2.peek();
                        Assert.assertTrue(top.content instanceof RefType);
                        Assert.assertTrue(((RefType) top.content).getState() == RefState.UNKNOWN);

                        
                        didIt++;
                    }
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 1);
    }
    
    public void testReferenceAnalyserIf() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestIf");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test1")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt5 = analyser.getStackLayout(5);

                    StackCell top = stackAt5.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NULL);

                    AbstractStack stackAt17 = analyser.getStackLayout(17);

                    top = stackAt17.peek();
                    Assert.assertTrue(top.content instanceof ArrayRefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.UNKNOWN);

                    didIt++;
                }
                if (method.getName().equals("test2")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt8 = analyser.getStackLayout(8);

                    StackCell top = stackAt8.peek();
                    Assert.assertTrue(top.content instanceof ArrayRefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt17 = analyser.getStackLayout(17);

                    top = stackAt17.peek();
                    Assert.assertTrue(top.content instanceof ArrayRefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 2);
    }

    public void testReferenceAnalyserIf1() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestIf");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test3")) {

                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt7 = analyser.getStackLayout(7);

                    StackCell top = stackAt7.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt15 = analyser.getStackLayout(15);

                    top = stackAt15.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt19 = analyser.getStackLayout(19);

                    top = stackAt19.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.UNKNOWN);

                    AbstractStack stackAt21 = analyser.getStackLayout(21);

                    top = stackAt21.peek();
                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.UNKNOWN);

                    didIt++;
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt == 1);
    }

    public void testReferenceAnalyserSimple() {
        JavaClass clazz;
        boolean didIt = false;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestArray");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stringAt4 = analyser.getStackLayout(4);

                    StackCell top = stringAt4.peek();

                    Assert.assertTrue(top.content instanceof RefType);

                    RefType topContent = (RefType) top.content;

                    Assert.assertTrue(topContent.getState() == RefState.NONNULL);

                    AbstractStack stringAt21 = analyser.getStackLayout(21);

                    top = stringAt21.peek();

                    Assert.assertTrue(top.content instanceof RefType);

                    topContent = (RefType) top.content;

                    Assert.assertTrue(topContent.getState() == RefState.NONNULL);

                    didIt = true;

                }
            }
            clazz = null;
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertTrue(didIt);
    }

    public void testReferenceAnalyserArrayLength() {
        JavaClass clazz;
        boolean didIt = false;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestArrayLength");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt3 = analyser.getStackLayout(3);

                    StackCell top = stackAt3.peek();

                    Assert.assertTrue(top.content instanceof RefType);

                    RefType topContent = (RefType) top.content;

                    Assert.assertTrue(topContent.getState() == RefState.NONNULL);

                    AbstractStack stackAt5 = analyser.getStackLayout(5);

                    top = stackAt5.peek();

                    Assert.assertTrue(top.content instanceof RefType);

                    topContent = (RefType) top.content;

                    Assert.assertTrue(topContent.getState() == RefState.NONNULL);
                    didIt = true;

                }
            }
            clazz = null;
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(didIt);
    }

    public void testMethodAnalyser1() {

        try {
            JavaClass clazz = Repository.lookupClass("java.util.concurrent.locks.AbstractQueuedSynchronizer");

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().contains("acquireQueued")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();
                }
            }
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public void testReferenceAnalyserInvokeSimple() {
        JavaClass clazz;
        boolean didIt = false;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestInvokeVirtual");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stringAt1 = analyser.getStackLayout(1);

                    StackCell top = stringAt1.peek();

                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.UNKNOWN);

                    AbstractStack stringAt5 = analyser.getStackLayout(5);

                    top = stringAt5.peek();

                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stringAt9 = analyser.getStackLayout(9);

                    top = stringAt9.peek();

                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stringAt13 = analyser.getStackLayout(13);

                    top = stringAt13.peek();

                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    AbstractStack stringAt17 = analyser.getStackLayout(17);

                    top = stringAt17.peek();

                    Assert.assertTrue(top.content instanceof RefType);
                    Assert.assertTrue(((RefType) top.content).getState() == RefState.NONNULL);

                    didIt = true;

                }
            }
            clazz = null;
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertTrue(didIt);
    }

    public void testReferenceAnalyserPutGetField() {
        JavaClass clazz;
        boolean didIt = false;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestPutGetField");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("test")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt3 = analyser.getStackLayout(3);

                    StackCell objRef = stackAt3.getAt(stackAt3.getSize() - 2);

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.UNKNOWN);

                    AbstractStack stackAt7 = analyser.getStackLayout(7);

                    objRef = stackAt7.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt8 = analyser.getStackLayout(8);

                    objRef = stackAt8.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt14 = analyser.getStackLayout(14);

                    objRef = stackAt14.getAt(stackAt14.getSize() - 2);

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt18 = analyser.getStackLayout(18);

                    objRef = stackAt18.getAt(stackAt18.getSize() - 1);

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    didIt = true;
                }
            }
            clazz = null;
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertTrue(didIt);
    }

    public void testReferenceAnalyserThis() {
        JavaClass clazz;
        int didIt = 0;
        try {
            clazz = Repository.lookupClass("test.icecapvm.minitests.ANTTestThis");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("foo")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt0 = analyser.getStackLayout(0);

                    StackCell objRef = stackAt0.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt1 = analyser.getStackLayout(1);

                    objRef = stackAt1.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    didIt++;
                }

                if (method.getName().equals("bar")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();

                    AbstractStack stackAt0 = analyser.getStackLayout(0);

                    StackCell objRef = stackAt0.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt1 = analyser.getStackLayout(1);

                    objRef = stackAt1.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt3 = analyser.getStackLayout(3);

                    objRef = stackAt3.getAt(stackAt3.getSize() - 2);

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    AbstractStack stackAt7 = analyser.getStackLayout(7);

                    objRef = stackAt7.peek();

                    Assert.assertTrue(objRef.content instanceof RefType);
                    Assert.assertTrue(((RefType) objRef.content).getState() == RefState.NONNULL);

                    didIt++;
                }
            }
            clazz = null;
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }

        Assert.assertTrue(didIt == 2);
    }

    public void testMethodAnalyser2() {

        try {
            JavaClass clazz = Repository.lookupClass("java.util.TreeMap$KeySet");

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().contains("comparator")) {
                    StackReferencesAnalyser analyser = new StackReferencesAnalyser(method, clazz);
                    analyser.analyseStackUsage();
                }
            }
        } catch (ClassNotFoundException e) {
            Assert.fail();
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
