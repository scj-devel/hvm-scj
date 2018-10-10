package carcontrol.io;

public class Queue {
	
  private byte[] queue;
  private int count, size;
  private int front, rear;
  /**
   * Creates an empty queue with places for <CODE>size</CODE> elements
   * @param size is the number of places in this queue
   * @precondition size > 0
   * @postcondition queue is empty
   */
  public Queue(int size)
  {
    queue = new byte[size];
    this.size = size;
    count = 0;
    rear = 0;
    front = 0;
  }
  /**
   * @param x is not null
   * @precondition queue is not full
   * @postcondition queue is not empty
   */
  public void put(byte x)
  {
    queue[rear] = x;
    rear = (rear + 1) % queue.length;
    count++;
  }
  /**
   * @precondition queue is not empty
   * @postcondition queue is not full
   */
  public byte take()
  {
    byte obj = queue[front];
    front = (front + 1) % queue.length;
    count--;
    return obj;
  }

  public boolean isEmpty()
  {
    return count == 0;
  }

  public boolean isFull()
  {
    return count == queue.length;
  }

  public int count()
  {
    return count;
  }

}
