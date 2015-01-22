package icecaptools.stackanalyser;

import icecaptools.BNode;

public class ProducerConsumerNodeInfo {
    public ProducerConsumerStack entryStack;
    public ProducerConsumerStack exitStack;
    
    public void addConsumer(int i, BNode consumer) throws Exception {
        exitStack.addConsumer(i, consumer);
    }
}
