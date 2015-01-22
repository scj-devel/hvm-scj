package test;

public class TestVolatile1 {

    public static class VolatileObject1 {
        public volatile int x;
        public int y;
    }

    public static class VolatileObject2 {
        public volatile int a;
        public volatile int b;

        public VolatileObject2() {
            a = 40;
            b = 2;
        }
    }

    public static void main(String[] args) {
        VolatileObject1 vobj1 = new VolatileObject1();
        vobj1.x = 10;
        vobj1.y = 32;

        if (vobj1.x + vobj1.y == 42) {
            VolatileObject2 vobj2 = new VolatileObject2();

            if (vobj2.a + vobj2.b == 42) {
                args = null;
            }
        }
    }
}
