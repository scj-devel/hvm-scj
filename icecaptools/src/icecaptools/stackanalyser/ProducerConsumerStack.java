package icecaptools.stackanalyser;

import icecaptools.BNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class ProducerConsumerStack {

    private Stack<ProducerConsumerCellInfo> stack;

    public ProducerConsumerStack() {
        stack = new Stack<ProducerConsumerCellInfo>();
    }

    public void push(BNode producer) throws Exception {
        ProducerConsumerCellInfo cell = new ProducerConsumerCellInfo();
        cell.addProducer(producer);
        stack.push(cell);
    }

    public ProducerConsumerCellInfo pop(BNode consumer) throws Exception {
        ProducerConsumerCellInfo cell = stack.pop();
        ArrayList<BNode> producers = cell.getProducers();
        for (int i = 0; i < producers.size(); i++)
        {
            BNode producer = producers.get(i);
            ProducerConsumerNodeInfo aInfo = producer.getAinfo();
            aInfo.addConsumer(stack.size(), consumer);
        }
        return cell;
    }

    public void addConsumer(int i, BNode consumer) throws Exception {
        stack.get(i).addConsumer(consumer);
    }

    public ProducerConsumerStack copy() throws Exception {
        ProducerConsumerStack newStack = new ProducerConsumerStack();
        for (int i = 0; i < stack.size(); i++) {
            ProducerConsumerCellInfo src = stack.get(i);
            ProducerConsumerCellInfo dst = new ProducerConsumerCellInfo();

            Iterator<BNode> producers = src.getProducers().iterator();
            while (producers.hasNext())
            {
                BNode producer = producers.next();
                dst.addProducer(producer);
            }
            
            BNode consumer = src.getConsumer();
            dst.addConsumer(consumer);

            newStack.stack.push(dst);
        }
        return newStack;
    }

    public boolean merge(ProducerConsumerStack other) throws Exception {
        boolean changes = false;
        for (int i = 0; i < stack.size(); i++) {
            ProducerConsumerCellInfo otherCellInfo = other.getCell(i);
            ProducerConsumerCellInfo thisCellInfo = getCell(i);

            Iterator<BNode> producers = otherCellInfo.getProducers().iterator();
            while (producers.hasNext())
            {
                BNode producer = producers.next();
                changes |= thisCellInfo.addProducer(producer);
            }
            BNode consumer = otherCellInfo.getConsumer();
            thisCellInfo.addConsumer(consumer);
        }
        return changes;
    }

    public int size() {
        return stack.size();
    }

    public ProducerConsumerCellInfo getCell(int i) {
        return stack.get(i);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (stack.size() > 0) {
            Iterator<ProducerConsumerCellInfo> iterator = stack.iterator();
            while (iterator.hasNext()) {
                buffer.append(iterator.next().toString());
                buffer.append('\n');
            }
        } else {
            buffer.append("---");
        }
        return buffer.toString();
    }

    public ProducerConsumerCellInfo peek() {
        return stack.peek();
    }
}
