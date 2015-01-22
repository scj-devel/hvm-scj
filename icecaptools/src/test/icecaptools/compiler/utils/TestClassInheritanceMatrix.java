package test.icecaptools.compiler.utils;

import unitTest.Assert;
import icecaptools.compiler.utils.ClassInheritanceMatrix;
import junit.framework.TestCase;

public class TestClassInheritanceMatrix extends TestCase {

    public void testSimple() {
        ClassInheritanceMatrix matrix = new ClassInheritanceMatrix(10);

        matrix.setInherits(0, 0);
        matrix.setInherits(1, 1);
        matrix.setInherits(3, 4);
        matrix.setInherits(9, 8);
        matrix.setInherits(9, 9);

        Assert.assertTrue(matrix.getInherits(0, 0));
        Assert.assertTrue(matrix.getInherits(1, 1));
        Assert.assertTrue(matrix.getInherits(3, 4));
        Assert.assertTrue(matrix.getInherits(9, 8));
        Assert.assertTrue(matrix.getInherits(9, 9));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!match(i, j, 0, 0)) {
                    if (!match(i, j, 1, 1)) {
                        if (!match(i, j, 3, 4)) {
                            if (!match(i, j, 9, 8)) {
                                if (!match(i, j, 9, 9)) {
                                    Assert.assertFalse(matrix.getInherits(i, j));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void testSimple1() {
        ClassInheritanceMatrix matrix = new ClassInheritanceMatrix(105);

        Assert.assertFalse(matrix.getInherits(0, 7));
        matrix.setInherits(0, 7);
        Assert.assertTrue(matrix.getInherits(0, 7));
    }

    public void testAll1() {
        ClassInheritanceMatrix matrix = new ClassInheritanceMatrix(105);

        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 105; j++) {
                Assert.assertFalse(matrix.getInherits(i, j));
            }
        }

        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 105; j++) {
                matrix.setInherits(i, j);
            }
        }

        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 105; j++) {
                if (!matrix.getInherits(i, j)) {
                    System.out.println("(i, j) = (" + i + ", " + j + ") failed");
                    Assert.fail();
                }
            }
        }
    }

    public void testAll2() {
        ClassInheritanceMatrix matrix = new ClassInheritanceMatrix(105);

        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 105; j++) {
                Assert.assertFalse(matrix.getInherits(i, j));
            }
        }

        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 105; j++) {
                if (i % 2 == 0) {
                    matrix.setInherits(i, j);
                }
            }
        }

        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 105; j++) {

                if (i % 2 == 0) {
                    if (!matrix.getInherits(i, j)) {
                        System.out.println("(i, j) = (" + i + ", " + j + ") failed");
                        Assert.fail();
                    }
                } else {
                    if (matrix.getInherits(i, j)) {
                        System.out.println("(i, j) = (" + i + ", " + j + ") failed");
                        Assert.fail();
                    }
                }
            }
        }
    }

    private boolean match(int i, int j, int k, int l) {
        if (i == k) {
            if (j == l) {
                return true;
            }
        }
        return false;
    }
}
