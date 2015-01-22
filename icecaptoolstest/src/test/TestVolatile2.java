package test;

public class TestVolatile2 {

    private static class VolatileObject
    {
        volatile public byte x;
        volatile public short y;
        volatile public VolatileObject next;
        public int z;
        volatile public long a;
        
        public VolatileObject(int inx)
        {
            x = (byte) (10 + inx);
            y = (short) (11 + inx);
            z = 123 + inx;
            a = 0x1FFFFFFFFL + inx;
        }
        
    }

    public static void main(String[] args) {
        VolatileObject head = new VolatileObject(1);
        VolatileObject tail = new VolatileObject(2);
        head.next = tail;
        tail.next = head;
        
        devices.System.snapshot();
        
        int sum = calc(head);
        
        if (sum == 298)
        {
            args = null;
        }
    }

    private static int calc(VolatileObject head) {
        int sum = (int) (head.x + head.y + head.z + head.a);
        head = head.next;
        sum += (int) (head.x + head.y + head.z + head.a);
        return sum;
    }

}
