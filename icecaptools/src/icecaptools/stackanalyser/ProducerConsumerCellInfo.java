package icecaptools.stackanalyser;

import java.util.ArrayList;
import java.util.Iterator;

import icecaptools.BNode;
import icecaptools.RawByteCodes;

public class ProducerConsumerCellInfo {

    private ArrayList<BNode> producers;
    private BNode stackcellConsumer;

    public ProducerConsumerCellInfo() {
        producers = new ArrayList<BNode>();
        stackcellConsumer = null;
    }

    public boolean addProducer(BNode producer) throws Exception {
        if (producer != null) {
            if (!producers.contains(producer)) {
                producers.add(producer);
                return true;
            }
        }
        return false;
    }

    public ArrayList<BNode> getProducers() {
        return producers;
    }

    public void addConsumer(BNode consumer) throws Exception {
        if (consumer != stackcellConsumer) {
            if (stackcellConsumer == null) {
                stackcellConsumer = consumer;
            } else {
                if (consumer != null) {
                    if (consumer.getOpCode() != RawByteCodes.pop_opcode) {
                        throw new Exception("Unexpected stack usage");
                    }
                }
            }
        }
    }

    public BNode getConsumer() {
        return stackcellConsumer;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(producers.size());
        buffer.append(", ");
        Iterator<BNode> producerIterator = producers.iterator();
        while (producerIterator.hasNext()) {
            buffer.append(producerIterator.next().toString());
            if (producerIterator.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
