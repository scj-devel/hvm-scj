package test.icecaptools;

import icecaptools.BNode;
import icecaptools.BasicBNode;
import icecaptools.stackanalyser.ProducerConsumerStack;
import icecaptools.stackanalyser.ProducerConsumerCellInfo;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class TestAnnotationStack {

    @Test
    public void testAnnotationStack1() {
        ProducerConsumerStack stack = new ProducerConsumerStack();
        BNode testNode = new BasicBNode(0, "locationClass", "locationMethod", "locationMethodSignature");

        try {
            stack.push(testNode);
            stack.push(testNode);
            stack.push(testNode);
            stack.push(testNode);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(4, stack.size());
    }

    @Test
    public void testAnnotationStack2() {
        ProducerConsumerStack stack = new ProducerConsumerStack();
        BNode testNode1 = new BasicBNode(0, "locationClass", "locationMethod", "locationMethodSignature");

        try {
            stack.push(testNode1);
        } catch (Exception e) {
            Assert.fail();
        }

        ProducerConsumerCellInfo cellInfo = stack.getCell(0);

        ArrayList<BNode> producers = cellInfo.getProducers();

        Assert.assertEquals(1, producers.size());
        Assert.assertEquals(testNode1, producers.get(0));
    }

    @Test
    public void testAnnotationStack4() {
        BNode testNode1 = new BasicBNode(0, "locationClass", "locationMethod", "locationMethodSignature");
        ProducerConsumerStack entryStack = new ProducerConsumerStack();

        try {
            entryStack.push(testNode1);

            entryStack.push(testNode1);
            entryStack.push(testNode1);

            ProducerConsumerStack exitStack = entryStack.copy();

            Assert.assertFalse(entryStack == exitStack);
            Assert.assertTrue(entryStack.size() == exitStack.size());

            for (int i = 0; i < entryStack.size(); i++) {
                ProducerConsumerCellInfo left, right;
                left = entryStack.getCell(i);
                right = exitStack.getCell(i);
                Assert.assertFalse(left == right);

                ArrayList<BNode> leftProducers = left.getProducers();
                ArrayList<BNode> rightProducers = right.getProducers();

                Assert.assertTrue(leftProducers.size() == rightProducers.size());
                Assert.assertTrue(leftProducers.size() == 1);
                Assert.assertTrue(leftProducers.get(0) == rightProducers.get(0));
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testAnnotationStack5() {
        BNode testNode1 = new BasicBNode(0, "locationClass", "locationMethod", "locationMethodSignature");
        BNode testNode2 = new BasicBNode(0, "locationClass", "locationMethod", "locationMethodSignature");
        ProducerConsumerStack entryStack1 = new ProducerConsumerStack();
        ProducerConsumerStack entryStack2 = new ProducerConsumerStack();

        try {
            entryStack1.push(testNode1);

            entryStack1.push(testNode2);

            entryStack2.push(testNode1);
            entryStack2.push(testNode2);

            entryStack1.merge(entryStack2);
        } catch (Exception e) {
            Assert.fail();
        }

        int count = 0;
        for (int i = 0; i < entryStack1.size(); i++) {
            ProducerConsumerCellInfo cell = entryStack1.getCell(i);
            ArrayList<BNode> producers = cell.getProducers();

            Assert.assertTrue(producers.size() == 1);

            if (producers.get(0) == testNode1) {
                count++;
            } else if (producers.get(0) == testNode2) {
                count += 2;
            }
        }
        Assert.assertEquals(3, count);
    }

    @Test
    public void testAnnotationStack6() {
        BNode testNode1 = new BasicBNode(0, "locationClass", "locationMethod", "locationMethodSignature");
        BNode testNode2 = new BasicBNode(1, "locationClass", "locationMethod", "locationMethodSignature");
        ProducerConsumerStack entryStack1 = new ProducerConsumerStack();
        ProducerConsumerStack entryStack2 = new ProducerConsumerStack();

        try {
            entryStack1.push(testNode1);
            entryStack2.push(testNode2);
            boolean contuinueAnalysis = entryStack1.merge(entryStack2);
            Assert.assertTrue(contuinueAnalysis);
        } catch (Exception e) {
            Assert.fail();
        }

        entryStack1 = new ProducerConsumerStack();
        entryStack2 = new ProducerConsumerStack();

        try {
            entryStack1.push(testNode1);
            entryStack2.push(testNode1);
            boolean contuinueAnalysis = entryStack1.merge(entryStack2);
            Assert.assertFalse(contuinueAnalysis);
        } catch (Exception e) {
            Assert.fail();
        }

    }
}
